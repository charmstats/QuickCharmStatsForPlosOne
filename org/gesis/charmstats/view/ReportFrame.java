package org.gesis.charmstats.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.beans.PropertyVetoException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.gesis.charmstats.ActionCommandID;
import org.gesis.charmstats.ActionCommandText;
import org.gesis.charmstats.IdentifiedParameter;
import org.gesis.charmstats.model.AttributeLink;
import org.gesis.charmstats.model.AttributeMap;
import org.gesis.charmstats.model.AttributeMapType;
import org.gesis.charmstats.model.CStatsModel;
import org.gesis.charmstats.model.Category;
import org.gesis.charmstats.model.CharacteristicLink;
import org.gesis.charmstats.model.CharacteristicMap;
import org.gesis.charmstats.model.Characteristics;
import org.gesis.charmstats.model.Comment;
import org.gesis.charmstats.model.ConDimension;
import org.gesis.charmstats.model.ConSpecification;
import org.gesis.charmstats.model.Concept;
import org.gesis.charmstats.model.Definition;
import org.gesis.charmstats.model.InstanceMap;
import org.gesis.charmstats.model.InstanceMapType;
import org.gesis.charmstats.model.InstanceType;
import org.gesis.charmstats.model.Literature;
import org.gesis.charmstats.model.Measurement;
import org.gesis.charmstats.model.OperaIndicator;
import org.gesis.charmstats.model.OperaPrescription;
import org.gesis.charmstats.model.Participant;
import org.gesis.charmstats.model.ParticipantRole;
import org.gesis.charmstats.model.Person;
import org.gesis.charmstats.model.ProContent;
import org.gesis.charmstats.model.Project;
import org.gesis.charmstats.model.Question;
import org.gesis.charmstats.model.Study;
import org.gesis.charmstats.model.TabDummy;
import org.gesis.charmstats.model.User;
import org.gesis.charmstats.model.Value;
import org.gesis.charmstats.model.Variable;
import org.gesis.charmstats.model.WorkStepInstance;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class ReportFrame extends InternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
//	/* RESOURCES */
//	public static final String CONTROL_BOX_ICON	= "org/gesis/charmstats/Resources/report.GIF";
	
	public static final String BUNDLE_R	= "org.gesis.charmstats.resources.ReportBundle";
	public static final String TITLE	= "report_frame_title";
	public static final String TEMPLATE	= "report_template";
	public static final String PRINT	= "report_print";
	public static final String SAVE		= "report_save";
	public static final String PROJECT	= "report_project";
	public static final String IMPORT	= "report_import";
	public static final String DEFAULT	= "report_default";
	public static final String EDIT		= "report_edit";
	
	public static final String AUTHOR	= "report_author";
	public static final String EDITOR	= "report_editor";
	
	public static final String BLUEPRINT		= "blueprint";
	public static final String SOURCE_VARIABLE	= "source_variable";
	
	/* Unused in QuickCharmStats */
//	private static final String BUNDLE_D	= "org.gesis.charmstats.resources.DesktopBundle";
//	private static final String INVERSE		= "bd_inverse";
//	private static final String DIFF		= "bd_diff";
//	private static final String BIAS_RATING	= "bd_bias_rating";
//	private static final String MISSINGS	= "bd_missings";
//	private static final String C_PROC		= "bd_c_proc";
//	private static final String PREFERENCE	= "bd_preference";
	
	private static final String BUNDLE_F	= "org.gesis.charmstats.resources.FrameBundle";
	private static final String FILE_NAME	= "file_name";
	
	public static final String NO_PRJ_NAME = "";
	public static final String EMPTY = "";
	
	/*
	 *	Fields
	 */
	JPanel			form;
	JEditorPane		reportPane;
	JPanel			buttonPanel				= new JPanel();
	JPanel			sidePanel				= new JPanel();
	JButton			printBtn;
	JButton			saveBtn;
	JButton			editBtn;
	JButton			projectBtn;
	JButton			importBtn;
	JButton			defaultBtn;
	
	Object			localeAddenda;
	CStatsModel		model;
	
	String 			template;
	Boolean			useDefaultTemplate		= true;
	String			userDefinedTemplate;
	
	static MessageFormat head = new MessageFormat("");
    static MessageFormat foot = new MessageFormat("");
    
    Connection		_connection;
	
	/*
	 *	Constructor
	 */
	/**
	 * @param dimension
	 * @param location
	 * @param visible
	 * @param resizable
	 * @param maximizable
	 * @param iconifiable
	 * @param closable
	 * @param locale
	 * @param al
	 * @param addenda
	 */
	public ReportFrame(
			Dimension dimension,
			Point location,
			boolean visible, 
			boolean resizable,
			boolean maximizable,
			boolean iconifiable,
			boolean closable,
			Locale locale,
			ActionListener al,
			Connection con,
			Object addenda) {
		super(dimension, location, visible, resizable, maximizable, iconifiable, closable);

		_connection = con;
		
		localeAddenda	= addenda;
		
		currentLocale	= locale;
		resourceBundle	= ResourceBundle.getBundle(BUNDLE_R, currentLocale);
		
		setTitle(resourceBundle.getString(TITLE));
		
		ImageIcon icon = createImageIcon(CStatsGUI.REPORT_ICON,"");
		setFrameIcon(icon);
		
		form		= new JPanel();
		form.setLayout(new BorderLayout());
		
	    reportPane	= new JEditorPane();
	    reportPane.setEditable(false);
	    reportPane.setContentType("text/html");
	    
		/* Build Button Panel */
	    projectBtn	= new JButton(resourceBundle.getString(PROJECT));
	    projectBtn.addActionListener(
	    		new java.awt.event.ActionListener() {
        			/* (non-Javadoc)
        			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
        			 */
        			public void actionPerformed(java.awt.event.ActionEvent evt) {
        				projectTemplate();
        			}
        		}
	    );
	    importBtn	= new JButton(resourceBundle.getString(IMPORT));
	    importBtn.addActionListener(
	    		new java.awt.event.ActionListener() {
        			/* (non-Javadoc)
        			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
        			 */
        			public void actionPerformed(java.awt.event.ActionEvent evt) {
        				importTemplate();
        			}
        		}
	    );
	    defaultBtn	= new JButton(resourceBundle.getString(DEFAULT));
	    defaultBtn.addActionListener(
	    		new java.awt.event.ActionListener() {
        			/* (non-Javadoc)
        			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
        			 */
        			public void actionPerformed(java.awt.event.ActionEvent evt) {
        				useDefaultTemplate();
        			}
        		}
	    );
		printBtn	= new JButton(resourceBundle.getString(PRINT));
        printBtn.addActionListener(
        		new java.awt.event.ActionListener() {
        			/* (non-Javadoc)
        			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
        			 */
        			public void actionPerformed(java.awt.event.ActionEvent evt) {
        				filePrint();
        			}
        		}
        );
		saveBtn		= new JButton(resourceBundle.getString(SAVE));
		saveBtn.setActionCommand(ActionCommandText.BTN_FRM_REP_SAVE_REPORT);
        saveBtn.addActionListener(al);
//        saveBtn.addActionListener(
//        		new java.awt.event.ActionListener() {
//        			/* (non-Javadoc)
//        			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
//        			 */
//        			public void actionPerformed(java.awt.event.ActionEvent evt) {
//        				saveReport();
//        			}
//        		}
//        );
		editBtn		= new JButton(resourceBundle.getString(EDIT));
        editBtn.addActionListener(
        		new java.awt.event.ActionListener() {
        			/* (non-Javadoc)
        			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
        			 */
        			public void actionPerformed(java.awt.event.ActionEvent evt) {
        				editReport();
        			}
        		}
        );
        buttonPanel.add(editBtn);
		buttonPanel.add(printBtn);
		buttonPanel.add(saveBtn);
		
		GridLayout experimentLayout = new GridLayout(0,1);
		sidePanel.setLayout(experimentLayout);
		sidePanel.add(defaultBtn);
		sidePanel.add(projectBtn);
		sidePanel.add(importBtn);	
		for (int filler=0; filler<6; filler++) {
			JButton invBtn = new JButton(""); invBtn.setVisible(false);
			sidePanel.add(invBtn);
		}
	    
		form.add(sidePanel, BorderLayout.WEST);
        JScrollPane reportScrollPane = new JScrollPane(reportPane);
        reportScrollPane.getVerticalScrollBar().setUnitIncrement(16);
	    form.add(reportScrollPane, BorderLayout.CENTER);
	    form.add(buttonPanel, BorderLayout.SOUTH);
	    
	    setContentPane(form);
	    
	    buildReport(resourceBundle, addenda);
	}

	/*
	 *	Methods
	 */
	/**
	 * @param locale
	 */
	protected void changeLanguage(Locale locale) {
		ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_R, locale);
		
		currentLocale = locale;
		resourceBundle = bundle;
		
		setTitle(bundle.getString(TITLE));
		buildReport(bundle, localeAddenda);
		importBtn.setText(resourceBundle.getString(IMPORT));
		projectBtn.setText(resourceBundle.getString(PROJECT));
		defaultBtn.setText(resourceBundle.getString(DEFAULT));
		editBtn.setText(resourceBundle.getString(EDIT));
		printBtn.setText(resourceBundle.getString(PRINT));
 		saveBtn.setText(resourceBundle.getString(SAVE));
	}
	
	/**
	 * @param f
	 */
	protected void changeFont(Font f) {
		UIManager.put("InternalFrame.titleFont", f);
		javax.swing.SwingUtilities.updateComponentTreeUI(this);
		
		importBtn.setFont(f);
		projectBtn.setFont(f);
		defaultBtn.setFont(f);
		editBtn.setFont(f);
		
		printBtn.setFont(f);
 		saveBtn.setFont(f);
	}
	
	/**
	 * @param resourceBundle
	 * @param addenda
	 */
	private void buildReport(ResourceBundle resourceBundle, Object addenda) {
		model					= ((CStatsModel)addenda);
		
		User	user			= ((CStatsModel)addenda).getUser();
		Project	project			= ((CStatsModel)addenda).getProject();
		
		if (!NO_PRJ_NAME.equals(model.getProject().getName())) {			
			if (useDefaultTemplate)	
				template = resourceBundle.getString(TEMPLATE);
			else
				template = userDefinedTemplate;
			
			template = insertUserName(template, user);	
			template = insertListOfAuthors(template, project);
			template = insertNiceListOfAuthors(template, project);
			
			template = insertTargetVariableName(template, project);
			template = insertTargetVariableLabel(template, project);
			
			template = insertSyntaxSPSS(template, project);
			template = insertSyntaxSPSSNumberedList(template, project);
			template = insertCrossTabSPSS(template, project);
			template = insertSyntaxSTATA(template, project);
			template = insertSyntaxSTATANumberedList(template, project);
			template = insertCrossTabSTATA(template, project);
			template = insertSyntaxSAS(template, project);
			template = insertSyntaxMPLUS(template, project);
			
			template = insertProjectName(template, project);
			template = insertProjectSummary(template, project);
			
			template = insertConceptName(template, project);
			template = insertConceptDef(template, project);
			
			template = insertListOfComments(template, project);
			template = insertSomeOfComments(template, project);
			
			template = insertListOfLiterature(template, project);
			
			template = insertListOfAllInstances(template, project);
			template = insertListOfCSInstances(template, project);
			template = insertListOfOSInstances(template, project);
			template = insertListOfDCInstances(template, project);
			
			template = insertMeasurement(template, project);
			template = insertMeasurementName(template, project);
			template = insertMeasurementSamplingLevel(template, project);
			template = insertMeasurementMeasuementLevel(template, project);
			template = insertMeasurementResponseOption(template, project);
			template = insertMeasurementDefinition(template, project);
			template = insertMeasurementDataset(template, project);
			template = insertMeasurementPID(template, project);
			
			template = insertListOfDimensions(template, project);
			template = insertListOfIndicators(template, project);
			template = insertListOfVariables(template, project);
			template = insertVariablesNumberedList(template, project);
			template = insertListOfSourceDatasetStudyDescriptions(template, project);
			
			template = insertListOfInstanceMappings_Whole(template, project);
			template = insertListOfInstanceMappings(template, project);
			template = insertListOfAttributeMappings(template, project);
			template = insertListOfCharacteristicMappings(template, project);
			
			/* Unused in QuickCharmStats */
//			template = insertComparisons(template, project);
			
			template = insertAllProjectsFromDB(template);
			template = insertAllVariablesFromDB(template);
			template = insertAllMeasurementsFromDB(template);
			
			template = insertSmallLogo(template);
			template = insertMediumLogo(template);
			template = insertLargeLogo(template);
			template = insertMappingGraph(template, project);
			template = insertUserPictures(template, project); 
		
			reportPane.setContentType("text/html");
			
		} else {
			template = EMPTY;

			reportPane.setContentType("text/plain");
		}
		
		String fullTemplate = template;
		reportPane.setText(fullTemplate);
		reportPane.setCaretPosition(0); 
	}
	
	/**
	 * @param template
	 * @return
	 */
	private String insertSmallLogo(String template) { // former insertLogo
		ResourceBundle	resourceBundle	= ResourceBundle.getBundle(BUNDLE_F, currentLocale);
		String			file_name		= resourceBundle.getString(FILE_NAME);
		
		String path_0 = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		path_0 = path_0.replace("/", "\\\\"); // use File.separator in CharmStatsPro
		path_0 = path_0.replace(file_name, ""); // filename stored in resource FrameBundle
		path_0 = path_0 + "resources\\\\pictures\\\\";		
		
		return template.replaceAll("#SMALL_LOGO#", insertVariableSizedPicture(template, path_0+"quick-charmstats-java.png", 100, 113)); 
	}
	private String insertMediumLogo(String template) {
		ResourceBundle	resourceBundle	= ResourceBundle.getBundle(BUNDLE_F, currentLocale);
		String			file_name		= resourceBundle.getString(FILE_NAME);
		
		String path_0 = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		path_0 = path_0.replace("/", "\\\\"); // use File.separator in CharmStatsPro
		path_0 = path_0.replace(file_name, ""); // filename stored in resource FrameBundle
		path_0 = path_0 + "resources\\\\pictures\\\\";
		
		return template.replaceAll("#MEDIUM_LOGO#", insertVariableSizedPicture(template, path_0+"quick-charmstats-java.png", 150, 168)); 
	}
	private String insertLargeLogo(String template) {
		ResourceBundle	resourceBundle	= ResourceBundle.getBundle(BUNDLE_F, currentLocale);
		String			file_name		= resourceBundle.getString(FILE_NAME);
		
		String path_0 = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		path_0 = path_0.replace("/", "\\\\"); // use File.separator in CharmStatsPro
		path_0 = path_0.replace(file_name, ""); // filename stored in resource FrameBundle
		path_0 = path_0 + "resources\\\\pictures\\\\";
		
		return template.replaceAll("#LARGE_LOGO#", insertVariableSizedPicture(template, path_0+"quick-charmstats-java.png", 200, 226)); 
	}
	
	private String insertVariableSizedPicture(String template, String file, int width, int height) {
		return "<img src=\"file:"+ file +"\" width="+ width +" height="+ height +"></img>";
	}
	
	/**
	 * @param template
	 * @return
	 */
	private String insertMappingGraph(String template, Project project) {
		ResourceBundle	resourceBundle	= ResourceBundle.getBundle(BUNDLE_F, currentLocale);
		String			file_name		= resourceBundle.getString(FILE_NAME);
		
		String path_0 = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		path_0 = path_0.replace("/", "\\\\"); // use File.separator in CharmStatsPro
		path_0 = path_0.replace(file_name, ""); // filename stored in resource FrameBundle
		path_0 = path_0 + "reports\\\\projects\\\\"+ project.getName() +"\\\\graphs\\\\";
		
		return template.replaceAll("#MAPPING_GRAPH#", "<img src=\"file:"+path_0+"mapping.png\"></img>");
	}
	
	/**
	 * @param template
	 * @return
	 */
	private String insertUserPictures(String template, Project project) {
		ResourceBundle	resourceBundle	= ResourceBundle.getBundle(BUNDLE_F, currentLocale);
		String			file_name		= resourceBundle.getString(FILE_NAME);
		
		String path_0 = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		path_0 = path_0.replace("/", "\\\\"); // use File.separator in CharmStatsPro
		path_0 = path_0.replace(file_name, ""); // filename stored in resource FrameBundle
		path_0 = path_0 + "reports\\\\projects\\\\"+ project.getName() +"\\\\graphs\\\\";
		
//		for (int i=1; i<100; i++) {
//		String number_extension = "_"+ (i < 10 ? "0" : "") + i;
		for (int i=1; i<1000; i++) {
		String number_extension = "_"+ (i < 10 ? "00" : (i < 100 ? "0" : "")) + i;
			
			File file = new File(path_0 +"user_picture"+ number_extension + ".png");
		     
			if(file.exists() && !file.isDirectory()) { 
				template = template.replaceAll("#USER_PICTURE"+ number_extension +"#", "<img src=\"file:"+path_0+"user_picture"+ number_extension +".png\"></img>");
				
			} 
		}
		
		return template;
	}
	
	/**
	 * @param template
	 * @param project
	 * @return
	 */
	private String insertListOfAuthors(String template, Project project) {
		ArrayList<Participant> participants = project.getParticipants();
		
		String authorList = new String();
		
		Iterator<Participant> parIterator;
		/* Project-Owner first */
		parIterator = participants.iterator();		
		while (parIterator.hasNext()) {
			Participant par = parIterator.next();
			
			if (ParticipantRole.PROJECT_OWNER.equals(par.getRole())) {
				User user = new User();
				user.setEntityID(par.getUserID());
				user.entityLoad(_connection);
				
				authorList += resourceBundle.getString(AUTHOR)+ ": "+ user.getName() +"<BR>\n";
			}
			
		}
		/* Other Editors */
		parIterator = participants.iterator();		
		while (parIterator.hasNext()) {
			Participant par = parIterator.next();
			
			if (ParticipantRole.EDITOR.equals(par.getRole())) {
				User user = new User();
				user.setEntityID(par.getUserID());
				user.entityLoad(_connection);
				
				authorList += resourceBundle.getString(EDITOR)+ ": "+ user.getName() +"<BR>\n";
			}
			
		}
		
		return template.replaceAll("#AUTHORS#", (authorList.isEmpty() ? "-" : authorList)); 
	}
	private String insertNiceListOfAuthors(String template, Project project) {
		ArrayList<Participant> participants = project.getParticipants();
		
		String authorList = new String();
		
		Iterator<Participant> parIterator;
		/* Project-Owner first */
		parIterator = participants.iterator();		
		while (parIterator.hasNext()) {
			Participant par = parIterator.next();
			
			if (ParticipantRole.PROJECT_OWNER.equals(par.getRole())) {
				User user = new User();
				user.setEntityID(par.getUserID());
				user.entityLoad(_connection);
				
				authorList += user.getName() +"<BR>\n";
			}
			
			if (parIterator.hasNext())
				authorList += ", ";
			
		}
		return template.replaceAll("#NICE_LIST_OF_AUTHORS#", (authorList.isEmpty() ? "-" : authorList)); /* TODO */
	}
	
	/**
	 * @param template
	 * @param user
	 * @return
	 */
	private String insertUserName(String template, User user) {
		String userName = user.getName();
		
		return template.replaceAll("#USER#", (userName.isEmpty() ? "-" : userName)); 
	}
	/**
	 * @param template
	 * @param project
	 * @return
	 */
	private String insertProjectName(String template, Project project) {
		String projectName	= project.getName();
		
		return template.replaceAll("#PROJECT_NAME#",	(projectName.isEmpty() ? "-" : projectName));
	}
	/**
	 * @param template
	 * @param project
	 * @return
	 */
	private String insertProjectSummary(String template, Project project) {
//		String projectNote	= project.getContent().getSummary().getText();
		String projectSummary	= project.getSummary().getText();
		
		return template.replaceAll("#PROJECT_SUMMARY#",	(projectSummary.isEmpty() ? "-" : projectSummary));
	}
	/**
	 * @param template
	 * @param project
	 * @return
	 */
	private String insertConceptName(String template, Project project) {
		Concept		concept = project.getConcept();
		
		String conceptName	= concept.getDefaultLabel().getLabel().getTextualContent();
		
		return template.replaceAll("#CONCEPT_NAME#",	(conceptName.isEmpty() ? "-" : conceptName));
	}
	/**
	 * @param template
	 * @param project
	 * @return
	 */
	private String insertConceptDef(String template, Project project) {
		Concept		concept	= project.getConcept();
		
		String conceptDef	= concept.getDefaultDescription().getDescription().getTextualContent();
		
		return template.replaceAll("#CONCEPT_DEFINITION#",	(conceptDef.isEmpty()	? "-" : conceptDef));
	}
	/**
	 * @param template
	 * @param project
	 * @return
	 */
	private String insertListOfComments(String template, Project project) {
		String listOfComments;
		
		listOfComments = 
				 "<BR><BR>\n"+ getTabProjectComment(project)
				+"<BR><BR>\n"+ getTabConceptComment(project)
				+"<BR><BR>\n"+ getTabLiteratureComment(project)
				+"<BR><BR>\n"+ getTabMeasurementComment(project)
				+"<BR><BR>\n"+ getTabDimensionComment(project)
				+"<BR><BR>\n"+ getTabSpecificationComment(project)
				+"<BR><BR>\n"+ getTabMapDimensionInstanceComment(project)
				+"<BR><BR>\n"+ getTabMapDimensionAttributeComment(project)
				+"<BR><BR>\n"+ getTabMapDimensionCharComment(project)
				+"<BR><BR>\n"+ getTabOSInstanceComment(project)
				+"<BR><BR>\n"+ getTabIndicatorComment(project)
				+"<BR><BR>\n"+ getTabPrescriptionComment(project)
				+"<BR><BR>\n"+ getTabMapIndicatorInstanceComment(project)
				+"<BR><BR>\n"+ getTabMapIndicatorAttributeComment(project)
				+"<BR><BR>\n"+ getTabMapIndicatorCharComment(project)
				+"<BR><BR>\n"+ getTabSearchVariableComment(project)
				+"<BR><BR>\n"+ getTabCompareVariablesComment(project)
				+"<BR><BR>\n"+ getTabCompareValuesComment(project)
				+"<BR><BR>\n"+ getTabDRInstanceComment(project)
				+"<BR><BR>\n"+ getTabVariableComment(project)
				+"<BR><BR>\n"+ getTabValueComment(project)
				+"<BR><BR>\n"+ getTabMapVariableInstanceComment(project)
				+"<BR><BR>\n"+ getTabMapVariableAttributeComment(project)
				+"<BR><BR>\n"+ getTabMapVariableCharComment(project);
				
		return template.replaceAll("#ALL_COMMENTS#", listOfComments);
	}
	/**
	 * @param template
	 * @param project
	 * @return
	 */
	private String insertSomeOfComments(String template, Project project) {
		
		template = template.replaceAll("#PROJECT_COMMENT#", "<BR><BR>\n"+ getTabProjectComment(project));
		template = template.replaceAll("#PROJECT_COMMENT_WITHOUT_SUBJECT#", getTabProjectCommentWithoutSubject(project));
		template = template.replaceAll("#CONCEPT_COMMENT#", "<BR><BR>\n"+ getTabConceptComment(project));
		template = template.replaceAll("#CONCEPT_COMMENT_WITHOUT_SUBJECT#", getTabConceptCommentWithoutSubject(project));
		template = template.replaceAll("#LIT_COMMENT#", "<BR><BR>\n"+ getTabLiteratureComment(project));
		template = template.replaceAll("#TARGET_COMMENT#", "<BR><BR>\n"+ getTabMeasurementComment(project));
		template = template.replaceAll("#TARGET_COMMENT_WITHOUT_SUBJECT#", getMeasurementCommentWithoutSubject(project));
		template = template.replaceAll("#DIM_COMMENT#", "<BR><BR>\n"+ getTabDimensionComment(project));
		template = template.replaceAll("#SPEC_COMMENT#", "<BR><BR>\n"+ getTabSpecificationComment(project));
		template = template.replaceAll("#MAP_DIM_INS_COMMENT#", "<BR><BR>\n"+ getTabMapDimensionInstanceComment(project));
		template = template.replaceAll("#MAP_DIM_ATTR_COMMENT#", "<BR><BR>\n"+ getTabMapDimensionAttributeComment(project));
		template = template.replaceAll("#MAP_DIM_CHAR_COMMENT#", "<BR><BR>\n"+ getTabMapDimensionCharComment(project));
		template = template.replaceAll("#OS_INS_COMMENT#", "<BR><BR>\n"+ getTabOSInstanceComment(project));
		template = template.replaceAll("#IND_COMMENT#", "<BR><BR>\n"+ getTabIndicatorComment(project));
		template = template.replaceAll("#PRES_COMMENT#", "<BR><BR>\n"+ getTabPrescriptionComment(project));
		template = template.replaceAll("#MAP_IND_INS_COMMENT#", "<BR><BR>\n"+ getTabMapIndicatorInstanceComment(project));
		template = template.replaceAll("#MAP_IND_ATTR_COMMENT#", "<BR><BR>\n"+ getTabMapIndicatorAttributeComment(project));
		template = template.replaceAll("#MAP_IND_CHAR_COMMENT#", "<BR><BR>\n"+ getTabMapIndicatorCharComment(project));
		template = template.replaceAll("#SEARCH_VAR_COMMENT#", "<BR><BR>\n"+ getTabSearchVariableComment(project));
		template = template.replaceAll("#COMPARE_VAR_COMMENT#", "<BR><BR>\n"+ getTabCompareVariablesComment(project));
		template = template.replaceAll("#COMPARE_VAL_COMMENT#", "<BR><BR>\n"+ getTabCompareValuesComment(project));
		template = template.replaceAll("#DC_INST_COMMENT#", "<BR><BR>\n"+ getTabDRInstanceComment(project));
		template = template.replaceAll("#DC_VAR_COMMENT#", "<BR><BR>\n"+ getTabVariableComment(project));
		template = template.replaceAll("#DC_VAL_COMMENT#", "<BR><BR>\n"+ getTabValueComment(project));
		template = template.replaceAll("#DC_INST_MAP_COMMENT#", "<BR><BR>\n"+ getTabMapVariableInstanceComment(project));
		template = template.replaceAll("#DC_ATTR_MAP_COMMENT#", "<BR><BR>\n"+ getTabMapVariableAttributeComment(project));
		template = template.replaceAll("#DC_CHAR_MAP_COMMENT#", "<BR><BR>\n"+ getTabMapVariableCharComment(project));
				
		return template;
	}
	/**
	 * @param project
	 * @return
	 */
	private String getTabProjectComment(Project project) {
		ProContent	content	= project.getContent();
		TabDummy	dummy	= content.getTabProject();
		
		ResourceBundle	resourceBundle	= ResourceBundle.getBundle(BUNDLE_R, currentLocale);
		String			subject			= resourceBundle.getString(ActionCommandText.BTN_PRJ_STP_PRJ_TAB_NOTE);
		
		return "<b>"+ subject +"</b><BR>\n"+ dummy.getComment().getText();		
	}
	/**
	 * @param project
	 * @return
	 */
	private String getTabProjectCommentWithoutSubject(Project project) {
		ProContent	content	= project.getContent();
		TabDummy	dummy	= content.getTabProject();
		
		String value = "";
		if ((dummy.getComment() != null) &&
				(dummy.getComment().getText() != null))
			value = (dummy.getComment().getText().isEmpty() ? "-" : dummy.getComment().getText());
			
		return value;		
	}
	/**
	 * @param project
	 * @return
	 */
	private String getTabConceptComment(Project project) {
		ProContent	content	= project.getContent();
		TabDummy	dummy	= content.getTabConcept();
		
		ResourceBundle	resourceBundle	= ResourceBundle.getBundle(BUNDLE_R, currentLocale);
		String			subject			= resourceBundle.getString(ActionCommandText.BTN_PRJ_STP_CON_TAB_NOTE);
		
		return "<b>"+ subject +"</b><BR>\n"+ dummy.getComment().getText();		
	}
	/**
	 * @param project
	 * @return
	 */
	private String getTabConceptCommentWithoutSubject(Project project) {
		ProContent	content	= project.getContent();
		TabDummy	dummy	= content.getTabConcept();
				
		String value = "";
		if ((dummy.getComment() != null) &&
				(dummy.getComment().getText() != null))
			value = (dummy.getComment().getText().isEmpty() ? "-" : dummy.getComment().getText());
			
		return value;		
	}
	/**
	 * @param project
	 * @return
	 */
	private String getTabLiteratureComment(Project project) {
		ProContent	content	= project.getContent();
		TabDummy	dummy	= content.getTabLiterature();
		
		ResourceBundle	resourceBundle	= ResourceBundle.getBundle(BUNDLE_R, currentLocale);
		String			subject			= resourceBundle.getString(ActionCommandText.BTN_PRJ_STP_LIT_TAB_NOTE);
		
		return "<b>"+ subject +"</b><BR>\n"+ dummy.getComment().getText();		
	}
	/**
	 * @param project
	 * @return
	 */
	private String getTabMeasurementComment(Project project) {
		ProContent	content	= project.getContent();
		TabDummy	dummy	= content.getTabMeasurement();
		
		ResourceBundle	resourceBundle	= ResourceBundle.getBundle(BUNDLE_R, currentLocale);
		String			subject			= resourceBundle.getString(ActionCommandText.BTN_TGT_STP_TGT_TAB_NOTE);
		
		return "<b>"+ subject +"</b><BR>\n"+ dummy.getComment().getText();		
	}
	/**
	 * @param project
	 * @return
	 */
	@SuppressWarnings("unused")
	private String getTabMeasurementCommentWithoutSubject(Project project) {
		ProContent	content	= project.getContent();
		TabDummy	dummy	= content.getTabMeasurement();
				
		String value = "";
		if ((dummy.getComment() != null) &&
				(dummy.getComment().getText() != null))
			value = (dummy.getComment().getText().isEmpty() ? "-" : dummy.getComment().getText());
			
		return value;		
	}
	/**
	 * @param project
	 * @return
	 */
	private String getMeasurementCommentWithoutSubject(Project project) {
		ProContent	content	= project.getContent();
		Measurement mea = content.getMeasurement();
				
		String value = "-";
		if ((mea.getComment() != null) &&
				(mea.getComment().getText() != null))
			value = (mea.getComment().getText().isEmpty() ? "-" : mea.getComment().getText());
			
		return value;		
	}
	/**
	 * @param project
	 * @return
	 */
	private String getTabDimensionComment(Project project) {
		ProContent	content	= project.getContent();
		TabDummy	dummy	= content.getTabDimension();
		
		ResourceBundle	resourceBundle	= ResourceBundle.getBundle(BUNDLE_R, currentLocale);
		String			subject			= resourceBundle.getString(ActionCommandText.BTN_CON_STP_DIM_TAB_NOTE);
		
		return "<b>"+ subject +"</b><BR>\n"+ dummy.getComment().getText();		
	}
	/**
	 * @param project
	 * @return
	 */
	private String getTabSpecificationComment(Project project) {
		ProContent	content	= project.getContent();
		TabDummy	dummy	= content.getTabSpecification();
		
		ResourceBundle	resourceBundle	= ResourceBundle.getBundle(BUNDLE_R, currentLocale);
		String			subject			= resourceBundle.getString(ActionCommandText.BTN_CON_STP_SPE_TAB_NOTE);
		
		return "<b>"+ subject +"</b><BR>\n"+ dummy.getComment().getText();		
	}
	/**
	 * @param project
	 * @return
	 */
	private String getTabMapDimensionInstanceComment(Project project) {
		ProContent	content	= project.getContent();
		TabDummy	dummy	= content.getTabMapDimensionInstance();
		
		ResourceBundle	resourceBundle	= ResourceBundle.getBundle(BUNDLE_R, currentLocale);
		String			subject			= resourceBundle.getString(ActionCommandText.BTN_CON_STP_MAP_INS_NOTE);
		
		return "<b>"+ subject +"</b><BR>\n"+ dummy.getComment().getText();		
	}
	/**
	 * @param project
	 * @return
	 */
	private String getTabMapDimensionAttributeComment(Project project) {
		ProContent	content	= project.getContent();
		TabDummy	dummy	= content.getTabMapDimensionAttribute();
		
		ResourceBundle	resourceBundle	= ResourceBundle.getBundle(BUNDLE_R, currentLocale);
		String			subject			= resourceBundle.getString(ActionCommandText.BTN_CON_STP_MAP_ATR_NOTE);
		
		return "<b>"+ subject +"</b><BR>\n"+ dummy.getComment().getText();		
	}
	/**
	 * @param project
	 * @return
	 */
	private String getTabMapDimensionCharComment(Project project) {
		ProContent	content	= project.getContent();
		TabDummy	dummy	= content.getTabMapDimensionChar();
		
		ResourceBundle	resourceBundle	= ResourceBundle.getBundle(BUNDLE_R, currentLocale);
		String			subject			= resourceBundle.getString(ActionCommandText.BTN_CON_STP_MAP_CHA_NOTE);
		
		return "<b>"+ subject +"</b><BR>\n"+ dummy.getComment().getText();		
	}
	/**
	 * @param project
	 * @return
	 */
	private String getTabOSInstanceComment(Project project) {
		ProContent	content	= project.getContent();
		TabDummy	dummy	= content.getTabOSInstance();
		
		ResourceBundle	resourceBundle	= ResourceBundle.getBundle(BUNDLE_R, currentLocale);
		String			subject			= resourceBundle.getString(ActionCommandText.BTN_OPE_STP_INS_TAB_NOTE);
		
		return "<b>"+ subject +"</b><BR>\n"+ dummy.getComment().getText();		
	}
	/**
	 * @param project
	 * @return
	 */
	private String getTabIndicatorComment(Project project) {
		ProContent	content	= project.getContent();
		TabDummy	dummy	= content.getTabIndicator();
		
		ResourceBundle	resourceBundle	= ResourceBundle.getBundle(BUNDLE_R, currentLocale);
		String			subject			= resourceBundle.getString(ActionCommandText.BTN_OPE_STP_IND_TAB_NOTE);
		
		return "<b>"+ subject +"</b><BR>\n"+ dummy.getComment().getText();		
	}
	/**
	 * @param project
	 * @return
	 */
	private String getTabPrescriptionComment(Project project) {
		ProContent	content	= project.getContent();
		TabDummy	dummy	= content.getTabPrescription();
		
		ResourceBundle	resourceBundle	= ResourceBundle.getBundle(BUNDLE_R, currentLocale);
		String			subject			= resourceBundle.getString(ActionCommandText.BTN_OPE_STP_PRE_TAB_NOTE);
		
		return "<b>"+ subject +"</b><BR>\n"+ dummy.getComment().getText();		
	}
	/**
	 * @param project
	 * @return
	 */
	private String getTabMapIndicatorInstanceComment(Project project) {
		ProContent	content	= project.getContent();
		TabDummy	dummy	= content.getTabMapIndicatorInstance();
		
		ResourceBundle	resourceBundle	= ResourceBundle.getBundle(BUNDLE_R, currentLocale);
		String			subject			= resourceBundle.getString(ActionCommandText.BTN_OPE_STP_MAP_INS_NOTE);
		
		return "<b>"+ subject +"</b><BR>\n"+ dummy.getComment().getText();		
	}
	/**
	 * @param project
	 * @return
	 */
	private String getTabMapIndicatorAttributeComment(Project project) {
		ProContent	content	= project.getContent();
		TabDummy	dummy	= content.getTabMapIndicatorAttribute();
		
		ResourceBundle	resourceBundle	= ResourceBundle.getBundle(BUNDLE_R, currentLocale);
		String			subject			= resourceBundle.getString(ActionCommandText.BTN_OPE_STP_MAP_ATR_NOTE);
		
		return "<b>"+ subject +"</b><BR>\n"+ dummy.getComment().getText();		
	}
	/**
	 * @param project
	 * @return
	 */
	private String getTabMapIndicatorCharComment(Project project) {
		ProContent	content	= project.getContent();
		TabDummy	dummy	= content.getTabMapIndicatorChar();
		
		ResourceBundle	resourceBundle	= ResourceBundle.getBundle(BUNDLE_R, currentLocale);
		String			subject			= resourceBundle.getString(ActionCommandText.BTN_OPE_STP_MAP_CHA_NOTE);
		
		return "<b>"+ subject +"</b><BR>\n"+ dummy.getComment().getText();		
	}
	/**
	 * @param project
	 * @return
	 */
	private String getTabSearchVariableComment(Project project) {
		ProContent	content	= project.getContent();
		TabDummy	dummy	= content.getTabSearchVariable();
		
		ResourceBundle	resourceBundle	= ResourceBundle.getBundle(BUNDLE_R, currentLocale);
		String			subject			= resourceBundle.getString(ActionCommandText.BTN_SEA_STP_SEA_TAB_NOTE);
		
		return "<b>"+ subject +"</b><BR>\n"+ dummy.getComment().getText();		
	}
	/**
	 * @param project
	 * @return
	 */
	private String getTabCompareVariablesComment(Project project) {
		ProContent	content	= project.getContent();
		TabDummy	dummy	= content.getTabCompareVariables();
		
		ResourceBundle	resourceBundle	= ResourceBundle.getBundle(BUNDLE_R, currentLocale);
		String			subject			= resourceBundle.getString(ActionCommandText.BTN_SEA_STP_COM_TAB_NOTE);
		
		return "<b>"+ subject +"</b><BR>\n"+ dummy.getComment().getText();		
	}
	/**
	 * @param project
	 * @return
	 */
	private String getTabCompareValuesComment(Project project) {
		ProContent	content	= project.getContent();
		TabDummy	dummy	= content.getTabCompareValues();
		
		ResourceBundle	resourceBundle	= ResourceBundle.getBundle(BUNDLE_R, currentLocale);
		String			subject			= resourceBundle.getString(ActionCommandText.BTN_SEA_STP_VAL_TAB_NOTE);
		
		return "<b>"+ subject +"</b><BR>\n"+ dummy.getComment().getText();		
	}
	/**
	 * @param project
	 * @return
	 */
	private String getTabDRInstanceComment(Project project) {
		ProContent	content	= project.getContent();
		TabDummy	dummy	= content.getTabDRInstance();
		
		ResourceBundle	resourceBundle	= ResourceBundle.getBundle(BUNDLE_R, currentLocale);
		String			subject			= resourceBundle.getString(ActionCommandText.BTN_DAT_STP_INS_TAB_NOTE);
		
		return "<b>"+ subject +"</b><BR>\n"+ dummy.getComment().getText();		
	}
	/**
	 * @param project
	 * @return
	 */
	private String getTabVariableComment(Project project) {
		ProContent	content	= project.getContent();
		TabDummy	dummy	= content.getTabVariable();
		
		ResourceBundle	resourceBundle	= ResourceBundle.getBundle(BUNDLE_R, currentLocale);
		String			subject			= resourceBundle.getString(ActionCommandText.BTN_DAT_STP_VAR_TAB_NOTE);
		
		return "<b>"+ subject +"</b><BR>\n"+ dummy.getComment().getText();		
	}
	/**
	 * @param project
	 * @return
	 */
	private String getTabValueComment(Project project) {
		ProContent	content	= project.getContent();
		TabDummy	dummy	= content.getTabValue();
		
		ResourceBundle	resourceBundle	= ResourceBundle.getBundle(BUNDLE_R, currentLocale);
		String			subject			= resourceBundle.getString(ActionCommandText.BTN_DAT_STP_VAL_TAB_NOTE);
		
		return "<b>"+ subject +"</b><BR>\n"+ dummy.getComment().getText();		
	}
	/**
	 * @param project
	 * @return
	 */
	private String getTabMapVariableInstanceComment(Project project) {
		ProContent	content	= project.getContent();
		TabDummy	dummy	= content.getTabMapVariableInstance();
		
		ResourceBundle	resourceBundle	= ResourceBundle.getBundle(BUNDLE_R, currentLocale);
		String			subject			= resourceBundle.getString(ActionCommandText.BTN_DAT_STP_MAP_INS_NOTE);
		
		return "<b>"+ subject +"</b><BR>\n"+ dummy.getComment().getText();		
	}
	/**
	 * @param project
	 * @return
	 */
	private String getTabMapVariableAttributeComment(Project project) {
		ProContent	content	= project.getContent();
		TabDummy	dummy	= content.getTabMapVariableAttribute();
		
		ResourceBundle	resourceBundle	= ResourceBundle.getBundle(BUNDLE_R, currentLocale);
		String			subject			= resourceBundle.getString(ActionCommandText.BTN_DAT_STP_MAP_ATR_NOTE);
		
		return "<b>"+ subject +"</b><BR>\n"+ dummy.getComment().getText();		
	}
	/**
	 * @param project
	 * @return
	 */
	private String getTabMapVariableCharComment(Project project) {
		ProContent	content	= project.getContent();
		TabDummy	dummy	= content.getTabMapVariableChar();
		
		ResourceBundle	resourceBundle	= ResourceBundle.getBundle(BUNDLE_R, currentLocale);
		String			subject			= resourceBundle.getString(ActionCommandText.BTN_DAT_STP_MAP_CHA_NOTE);
		
		return "<b>"+ subject +"</b><BR>\n"+ dummy.getComment().getText();		
	}
	
	private String insertAllProjectsFromDB(String template) {
		/* Unused in QuickCharmStats */
		
		return template.replaceAll("#ALL_PROJECTS#", "");
	}
	/**
	 * @param template
	 * @param project
	 * @return
	 */
	/* Unused in QuickCharmStats */
//	private String insertComparisons(String template, Project project) {
//		String comparisons = "";
//		
//		ResourceBundle	resourceBundle	= ResourceBundle.getBundle(BUNDLE_R, currentLocale);
//		String			blueprint		= resourceBundle.getString(BLUEPRINT);
//		String			variable		= resourceBundle.getString(SOURCE_VARIABLE);
//		
//		ResourceBundle	resourceBundle2	= ResourceBundle.getBundle(BUNDLE_D, currentLocale);
//		String			inverse			= resourceBundle2.getString(INVERSE);
//		String			diff			= resourceBundle2.getString(DIFF);
//		String			bias_rating		= resourceBundle2.getString(BIAS_RATING);
//		String			missings		= resourceBundle2.getString(MISSINGS);
//		String			c_proc			= resourceBundle2.getString(C_PROC);
//		String			preference		= resourceBundle2.getString(PREFERENCE);
//		
//		Iterator<AttributeComp> comp_iter = project.getContent().getComps().iterator();
//		while (comp_iter.hasNext()) {
//			AttributeComp comp = comp_iter.next();
//			
//			OperaIndicator	ind = ((OperaIndicator)comp.getTargetAttribute().getAttribute());
//			Variable		var = ((Variable)comp.getSourceAttribute().getAttribute());
//			BiasMetadata	bias= comp.getBiasMetadata();
//			
//			comparisons += 
//				"<b>"+ blueprint 	+" </b>"+ ind.getLabel() +" ["+ ind.getLevel() +"|"+ ind.getType() +"]<BR>\n"+
//				"<b>"+ variable 	+" </b>"+ var.getName() +"("+ var.getLabel() +" ["+ var.getLevel() +"|"+ var.getType() +"|"+ var.getMeasureType() +"]) <BR>\n<BR>\n"+
//				"<b>"+ inverse		+": </b>"+ resourceBundle.getString((bias.isInverse() ? "true":"false"))	+"<BR>\n"+	// Inverse 			boolean
//				"<b>"+ diff 		+": </b>"+ resourceBundle.getString((bias.isDifference() ? "true":"false"))	+"<BR>\n"+	// Diff. in Metric	boolean
//				"<b>"+ bias_rating	+" </b>"+ bias.getRating() 													+"<BR>\n"+	// Bias Rating		CV
//				"<b>"+ missings		+" </b>"+ (bias.getMissings().isEmpty() ? "-":bias.getMissings())			+"<BR>\n"+	// Missing categories	String
//				"<b>"+ c_proc		+" </b>"+ bias.getStandCoding()												+"<BR>\n"+	// Stand. C. Proc.	CV
//				"<b>"+ preference	+" </b>"+ bias.getPreference()												+"<BR>\n"+	// Preference		CV
//				"<BR>\n<BR>\n<BR>\n";
//		}
//		
//		return template.replaceAll("#COMPARISONS#", comparisons);
//	}
	
	/**
	 * @param template
	 * @param project
	 * @return
	 */
	private String insertListOfLiterature(String template, Project project) {
		String literature = "";
		
//		ArrayList<Literature>	literatures		= project.getContent().getLiteratures();
		ArrayList<Literature>	literatures		= project.getConcept().getLiteratures();
		
		if (literatures != null) {
			Iterator<Literature>	it_literature	= literatures.iterator();
			while (it_literature.hasNext()) {
				Literature lit = it_literature.next();
				
				String				listOfAuthors = "";
				ArrayList<Person>	authors		= lit.getAuthors();
				Iterator<Person>	it_person	= authors.iterator();
				while (it_person.hasNext()) {
					Person person = it_person.next();
					
					listOfAuthors += person.getName();
					if (it_person.hasNext())
						listOfAuthors += ", ";
				}
				
				String				year 			= "";
				SimpleDateFormat	dateformatYYYY	= new SimpleDateFormat("yyyy");
				if (lit.getDate() instanceof java.sql.Date)
					year = dateformatYYYY.format(lit.getDate());
			
				
				String				title			= lit.getTitle();
				
				String 				issue			= lit.getIssue();
				
				String				place			= lit.getPlace();
				
				literature += listOfAuthors;
				literature += "("+ year +"):";
				literature += title + ".";
				literature += " "+ issue + "., ";
				literature += place + "<BR>\n"; 
				
			}
		}
		
		return template.replaceAll("#LITERATURE#", literature);
	}
	
	/**
	 * @param template
	 * @param project
	 * @return
	 */
	private String insertTargetVariableName(String template, Project project) {
		String		var_name	= project.getTargetName();
		
		if (!(var_name instanceof String))
				var_name = "";
		
		return template.replaceAll("#TARGET_NAME#", var_name);
	}
	
	/**
	 * @param template
	 * @param project
	 * @return
	 */
	private String insertTargetVariableLabel(String template, Project project) {
		String		var_label	= project.getTargetLabel();
		
		if (!(var_label instanceof String))
			var_label = "";
		
		return template.replaceAll("#TARGET_LABEL#", var_label);
	}
	/**
	 * @param template
	 * @param project
	 * @return
	 */
	private String insertListOfAllInstances(String template, Project project) {
		ProContent	content		= project.getContent();
		String 		instances	= "";
		
		ResourceBundle	resourceBundle	= ResourceBundle.getBundle(BUNDLE_R, currentLocale);
		
		Iterator<WorkStepInstance> instIterator = content.getLayers().iterator();
		
		if (instIterator.hasNext()) {
			instances += "<UL>";
			while (instIterator.hasNext()) {
				WorkStepInstance instance = instIterator.next();
				instances += "<LI>"+ resourceBundle.getString(instance.getType().toString().replace(" ", "_")) +": "+ (instance.getLabel() +"<BR>\n");
			}
			instances += "</UL>";
		}
		
		return template.replaceAll("#ALL_INSTANCES#", instances);
	}
	
	/**
	 * @param template
	 * @param project
	 * @return
	 */
	private String insertListOfCSInstances(String template, Project project) {
		ProContent	content		= project.getContent();
		String 		instances	= "";
		
		Iterator<WorkStepInstance> instIterator = content.getLayers(InstanceType.CONCEPTUAL).iterator();
		
		if (instIterator.hasNext()) {
			instances += "<UL>";
			while (instIterator.hasNext()) {
				WorkStepInstance instance = instIterator.next();
				instances += "<LI>"+ (instance.getLabel() +"<BR>\n");
			}
			instances += "</UL>";
		}
		
		return template.replaceAll("#CS_INSTANCES#", instances);
	}
	
	/**
	 * @param template
	 * @param project
	 * @return
	 */
	private String insertListOfOSInstances(String template, Project project) {
		ProContent	content		= project.getContent();
		String 		instances	= "";
		
		Iterator<WorkStepInstance> instIterator = content.getLayers(InstanceType.OPERATIONAL).iterator();
		
		if (instIterator.hasNext()) {
			instances += "<UL>";
			while (instIterator.hasNext()) {
				WorkStepInstance instance = instIterator.next();
				instances += "<LI>"+ (instance.getLabel() + "<BR>\n");
			}
			instances += "</UL>";
		}
		
		return template.replaceAll("#OS_INSTANCES#", instances);
	}
	
	/**
	 * @param template
	 * @param project
	 * @return
	 */
	private String insertListOfDCInstances(String template, Project project) {
		ProContent	content		= project.getContent();
		String 		instances	= "";
		
		Iterator<WorkStepInstance> instIterator = content.getLayers(InstanceType.DATA_CODING).iterator();
		
		if (instIterator.hasNext()) {
			instances += "<UL>";
			while (instIterator.hasNext()) {
				WorkStepInstance instance = instIterator.next();
				instances += "<LI>"+ (instance.getLabel() + "<BR>\n");
			}
			instances += "</UL>";
		}
		
		return template.replaceAll("#DC_INSTANCES#", instances);
	}
	/**
	 * @param template
	 * @param project
	 * @return
	 */
	private String insertMeasurement(String template, Project project) {
		ProContent	content		= project.getContent();
		Measurement measurement	= content.getMeasurement();
		
		String measurements	= "<UL><LI>"+ measurement.getName() +", "+ measurement.getLevel() /*+", "+ measurement.getType()*/ +"<BR>\n";
		
		if ((measurement.getDefinition() instanceof Definition) &&
				(!measurement.getDefinition().getText().isEmpty())) {
			measurements += "<BR>\nDef.:";
			measurements += "<BR>\n"+ measurement.getDefinition().getText() +"<BR>\n"+"<BR>\n";
		}
		
		if ((measurement.getComment() instanceof Comment) &&
				(!measurement.getComment().getText().isEmpty()))
			measurements += "<BR>\n"+ measurement.getComment().getText() +"<BR>\n"+"<BR>\n";
				
		measurements += content.getLayers(InstanceType.PROJECT_SETUP).get(0) +"<BR>\n";
		Iterator<CharacteristicLink> charLinkIterator = content.getCharacteristicLinksByAttribute(content.getMeasurement()).iterator();
		
		if (charLinkIterator.hasNext()) {
			measurements += "<UL>";
			while (charLinkIterator.hasNext()) {
				CharacteristicLink charLink = charLinkIterator.next();
				
				measurements += "<LI>"+ ((Category)charLink.getCharacteristic()).getCode() +":"+ ((Category)charLink.getCharacteristic()).getLabel() +"</BLOCKQUOTE><BR>\n";
			}
			measurements += "</UL>";
		}
		measurements += "</UL>";
		
		return template.replaceAll("#MEASUREMENT#", (measurements != null ? measurements : ""));
	}
	private String insertMeasurementName(String template, Project project) {
		ProContent	content		= project.getContent();
		Measurement measurement	= content.getMeasurement();
		
		String measurements	= measurement.getName();
				
		return template.replaceAll("#MEASUREMENT_NAME#", (measurements != null ? measurements : ""));
	}
	private String insertMeasurementSamplingLevel(String template, Project project) {
		ProContent	content		= project.getContent();
		Measurement measurement	= content.getMeasurement();
				
		String BUNDLE	= "org.gesis.charmstats.resources.ModelBundle";
		
		/** Resources for the default locale */
		ResourceBundle res = 
			ResourceBundle.getBundle(BUNDLE, currentLocale);
		
		return template.replaceAll("#MEASUREMENT_SAMPLING_LEVEL#", res.getString(measurement.getLevel().getLabel()));
	}
	private String insertMeasurementMeasuementLevel(String template, Project project) {
		ProContent	content		= project.getContent();
		Measurement measurement	= content.getMeasurement();
				
		String BUNDLE	= "org.gesis.charmstats.resources.ModelBundle";
		
		/** Resources for the default locale */
		ResourceBundle res = 
			ResourceBundle.getBundle(BUNDLE, currentLocale);
		
		return template.replaceAll("#MEASUREMENT_LEVEL#", res.getString(measurement.getKind().getLabel()));
	}
	private String insertMeasurementResponseOption(String template, Project project) {
		ProContent	content		= project.getContent();
		Measurement measurement	= content.getMeasurement();
		
		String responseOptions = "";
		
		responseOptions += "<table>";
		Iterator<CharacteristicLink> charLinkIterator = content.getCharacteristicLinksByAttribute(measurement).iterator();				
		while (charLinkIterator.hasNext()) {
			CharacteristicLink charLink = charLinkIterator.next();
			
			responseOptions += "<tr>";
			responseOptions += "<td align=\"right\">"+ ((Category)charLink.getCharacteristic()).getCode() +":</td>";
			responseOptions += "<td align=\"right\">"+ ((Category)charLink.getCharacteristic()).getLabel() +"</td>";
			responseOptions += "</tr>";
		}
		responseOptions += "</table>";
		
		return template.replaceAll("#MEASUREMENT_RESPONSE_OPTION#", (responseOptions != null ? responseOptions : ""));
	}
	private String insertMeasurementDefinition(String template, Project project) {
		ProContent	content		= project.getContent();
		Measurement measurement	= content.getMeasurement();
				
		String value = "-";
		if ((measurement.getDefinition() instanceof Definition) &&
				(!measurement.getDefinition().getText().isEmpty())) {
			value = (measurement.getDefinition().getText().isEmpty() ? "-" : measurement.getDefinition().getText());
		}
		
		return template.replaceAll("#MEASUREMENT_DEFINITION#", value);
	}
	private String insertMeasurementDataset(String template, Project project) {
		ProContent	content		= project.getContent();
		Measurement measurement	= content.getMeasurement();
				
		String datasetText = "-";
		if ((measurement.getDataset() instanceof String) &&
				(!measurement.getDataset().isEmpty()))
			datasetText = measurement.getDataset();
		
		return template.replaceAll("#TARGET_DATASET#", datasetText);
	}
	private String insertMeasurementPID(String template, Project project) {
		ProContent	content		= project.getContent();
		Measurement measurement	= content.getMeasurement();
				
		String pidText = "-";
		if ((measurement.getPID() instanceof String) &&
				(!measurement.getPID().isEmpty()))
			pidText = measurement.getPID();
		
		return template.replaceAll("#TARGET_PID#", pidText);
	}
	private String insertAllMeasurementsFromDB(String template) {
		/* Unused in QuickCharmStats */
		
		return template.replaceAll("#ALL_MEASUREMENTS#", "");
	}
	/**
	 * @param template
	 * @param project
	 * @return
	 */
	private String insertListOfDimensions(String template, Project project) {
		ProContent	content	= project.getContent();
		
		return template.replaceAll("#DIMENSIONS#", getDimensions(content));
	}
	/**
	 * @param template
	 * @param project
	 * @return
	 */
	private String insertListOfIndicators(String template, Project project) {
		ProContent	content	= project.getContent();
		
		return template.replaceAll("#BLUEPRINTS#", getIndicators(content));
	}
	/**
	 * @param template
	 * @param project
	 * @return
	 */
	private String insertListOfVariables(String template, Project project) {
		ProContent	content	= project.getContent();
		
		return template.replaceAll("#VARIABLES#", getVariables(content));
	}
	private String insertListOfSourceDatasetStudyDescriptions(String template, Project project) {
		String studyDescriptions = "";
		
		Iterator<Variable> varIterator = project.getContent().getVariables().iterator();
		while (varIterator.hasNext()) {
			Variable variable = varIterator.next();
			
			Question	quest				= variable.getSource();
			Study		study				= ((quest != null) ? quest.getSource() : null);	
			
			
			String		variableDataset		= "-";
			String		studytitle			= "-";
			String		doi					= "-";
			String		studyarea			= "-";
			String		dateOfCollection	= "-";
			String		population			= "-";
			String		selection			= "-";
			String		collectionMethod	= "-";
			String		collectors			= "-";
			String		sourceFile			= "-";
			String		dataset				= "-";
			String		variableComments	= "-";
			
			
			if (study != null) {				
				if ((variable.getDataset() != null) &&
						(!variable.getDataset().isEmpty()))
					variableDataset = variable.getDataset();
						
				if ((study.getTitle() != null) &&
						(!study.getTitle().isEmpty()))
					studytitle = study.getTitle();
			
				if ((study.getDOI() != null) &&
						(!study.getDOI().isEmpty()))
					doi = study.getDOI();			
			
				if ((study.getStudyArea() != null) &&
						(!study.getStudyArea().isEmpty()))
					studyarea = study.getStudyArea();
			
				if ((study.getDateOfCollection() != null) &&
						(!study.getDateOfCollection().isEmpty()))
					dateOfCollection = study.getDateOfCollection();
				
				if ((study.getPopulation() != null) &&
						(!study.getPopulation().isEmpty()))
					population = study.getPopulation();
				
				if ((study.getSelection() != null) &&
						(!study.getSelection().isEmpty()))
					selection = study.getSelection();
				
				if ((study.getCollectionMethod() != null) &&
						(!study.getCollectionMethod().isEmpty()))
					collectionMethod = study.getCollectionMethod();
				
				if ((study.getCollectors() != null) &&
						(!study.getCollectors().isEmpty()))
					collectors = study.getCollectors();
				
				if ((study.getSourceFile() != null) &&
						(!study.getSourceFile().isEmpty()))
					sourceFile = study.getSourceFile();
				
				if ((study.getDataset() != null) &&
						(!study.getDataset().isEmpty()))
					dataset = study.getDataset();
				
				// Link to codebook
				
				// Link to online questionnaire
				
				// Link to other online doc
				
				if ((variable.getComment() != null) &&
						(!variable.getComment().getText().isEmpty()))
					variableComments = variable.getComment().getText();
				
				studyDescriptions += "<B>Source variable dataset</B>: "+ variableDataset + "<BR>";
				studyDescriptions += "<B>Study title: </B>"+ studytitle +"<BR>";
				studyDescriptions += "<B>DOI: </B>"+ doi +"<BR>";
				studyDescriptions += "<B>Study area: </B>"+ studyarea +"<BR>";			
				studyDescriptions += "<B>Date of collection: </B>"+ dateOfCollection +"<BR>";
				studyDescriptions += "<B>Population: </B>"+ population +"<BR>";
				studyDescriptions += "<B>Selection: </B>"+ selection +"<BR>";
				studyDescriptions += "<B>Collection Method: </B>"+ collectionMethod +"<BR>";
				studyDescriptions += "<B>Collectors: </B>"+ collectors +"<BR>";
				studyDescriptions += "<B>Source file: </B>"+ sourceFile +"<BR>";
				studyDescriptions += "<B>Dataset: </B>"+ dataset +"<BR>";
				studyDescriptions += "<B>Link to online codebook:</B> - <BR>";
				studyDescriptions += "<B>Link to online questionnaire:</B> -<BR>";
				studyDescriptions += "<B>Link to other online documentation:</B> -<BR>";
				studyDescriptions += "<BR>";
				studyDescriptions += "<B>Source variable comments: </B>"+ variableComments +"<BR>";
				
			}
		}
		return template.replaceAll("#SOURCE_VARIABLES_DATASET_DESCRIPTION#", (studyDescriptions.isEmpty() ? "-" : studyDescriptions));
	}
	/**
	 * @param template
	 * @param project
	 * @return
	 */
	private String insertVariablesNumberedList(String template, Project project) {
		String variableText = "";
		int i = 1;
			
		Iterator<Variable> varIterator = project.getContent().getVariables().iterator();
		while (varIterator.hasNext()) {
			Variable variable = varIterator.next();
			
			String definitionText = "-";
			if ((variable.getDefinition() instanceof Definition) &&
					(!variable.getDefinition().getText().isEmpty()))
				definitionText = variable.getDefinition().getText();
			
//			String commentText = "-";
//			if ((variable.getComment() instanceof Comment) &&
//					(!variable.getComment().getText().isEmpty()))
//				commentText = variable.getComment().getText();
			
//			String datasetText = "-";
//			if ((variable.getDataset() instanceof String) &&
//					(!variable.getDataset().isEmpty()))
//				datasetText = variable.getDataset();
			
//			String pidText = "-";
//			if ((variable.getPID() instanceof String) &&
//					(!variable.getPID().isEmpty()))
//				pidText = variable.getPID();
			
//			String datadateText = "-";
//			if ((variable.getDataDate() instanceof String) &&
//					(!variable.getDataDate().isEmpty()))
//				datadateText = variable.getDataDate();
			
			String dataCodingInstanceText = "-";
			AttributeLink link = project.getContent().getLinkByAttribute(variable);
			if (link != null) {
				WorkStepInstance instance = link.getInstance();
				
				if (instance != null)
					dataCodingInstanceText = instance.getLabel();
			}
			
			variableText += "<B>Source variable</B>: ("+ i + ") <BR>";
			variableText += "<B>Name: </B>"+ variable.getName() +"<BR>";
			variableText += "<B>Label: </B>"+ variable.getLabel() +"<BR>";
			variableText += "<B>Sampling level: </B>"+ variable.getLevel() +"<BR>";			
			variableText += "<B>Measurement level: </B>"+ variable.getMeasureType() +"<BR>";
			variableText += "<B>DataCoding instance: </B>"+ dataCodingInstanceText +"<BR>";
			variableText += "<BR>";
			variableText += "<B>Response Options and coding:</B><BR>";
			variableText += "<Blockquote>";
			
			String responseOptions = "";
			
			responseOptions += "<table>";
			Iterator<CharacteristicLink> charLinkIterator = project.getContent().getCharacteristicLinksByAttribute(variable).iterator();				
			while (charLinkIterator.hasNext()) {
				CharacteristicLink charLink = charLinkIterator.next();
				
				responseOptions += "<tr>";
				responseOptions += "<td align=\"right\">"+ ((Value)charLink.getCharacteristic()).getValue() +":</td>";
				responseOptions += "<td align=\"right\">"+ ((Value)charLink.getCharacteristic()).getLabel() +"</td>";
				responseOptions += "</tr>";
			}
			responseOptions += "</table>";
				
			variableText += responseOptions +"<BR>";
			variableText += "</Blockquote>";
			variableText += "<B>Definition: </B>"+ definitionText +"<BR>";
//			variableText += "<B>Comments: </B>"+ commentText +"<BR>";
//			variableText += "<B>Dataset: </B>"+ datasetText +"<BR>";
//			variableText += "<B>PID: </B>"+ pidText +"<BR>";
//			variableText += "<B>Date Last accessed: </B>"+ datadateText +"<BR>";
			
			Question quest = variable.getSource();
			if (quest != null) {
				String questionText			= quest.getText();
				String questionInstruction	= quest.getInstruction();
				
				variableText += "<B>Text: </B>"+ (questionText.isEmpty() ? "-" : questionText) +"<BR>";
				variableText += "<B>Instruction: </B>"+ (questionInstruction.isEmpty() ? "-" : questionInstruction) +"<BR>";
			}
			
			i++;
		}
		
		return template.replaceAll("#VARIABLE_NUMBERED_LIST#",	(variableText.isEmpty() ? "" : variableText));
	}
	private String insertAllVariablesFromDB(String template) {
		/* Unused in QuickCharmStats */
		
		return template.replaceAll("#ALL_VARIABLES#", "");
	}
	/**
	 * @param template
	 * @param project
	 * @return
	 */
	private String insertListOfInstanceMappings_Whole(String template, Project project) {
		ProContent	content	= project.getContent();
		
		return template.replaceAll("#INST_MAPPINGS_REK#", getThreeStepMapperListList(content)); //getInstanceMapsRekList(content));
	}
	/**
	 * @param template
	 * @param project
	 * @return
	 */
	private String insertListOfInstanceMappings(String template, Project project) {
		ProContent	content	= project.getContent();
		
		return template.replaceAll("#INST_MAPPINGS#", getInstanceMapsList(content));
	}
	/**
	 * @param template
	 * @param project
	 * @return
	 */
	private String insertListOfAttributeMappings(String template, Project project) {
		ProContent	content	= project.getContent();
		
		return template.replaceAll("#ATTR_MAPPINGS#", getAttributeMapsList(content));
	}
	/**
	 * @param template
	 * @param project
	 * @return
	 */
	private String insertListOfCharacteristicMappings(String template, Project project) {
		ProContent	content	= project.getContent();
		
		return template.replaceAll("#CHAR_MAPPINGS#", getCharMapsList(content));
	}
	/**
	 * @param template
	 * @param project
	 * @return
	 */
	private String insertSyntaxSPSS(String template, Project project) {
		String syntaxText = "";
				
		Iterator<InstanceMap> itInstanceMap = project.getContent().getInstanceMaps().iterator();
		while (itInstanceMap.hasNext()) {
			InstanceMap instMap = itInstanceMap.next();
			
			Iterator<AttributeMap> itAttributeMap = project.getContent().getMapsByType(AttributeMapType.ASSIGNED_VARIABLE).iterator();
			while (itAttributeMap.hasNext()) {
				AttributeMap attrMap = itAttributeMap.next();
				
				if ((attrMap.getBelongsTo() instanceof InstanceMap) &&
						(attrMap.getBelongsTo().equals(instMap))) {
					ArrayList<Characteristics[]> mappings = getThreeStepMapperListList(project.getContent(), instMap, attrMap);
					syntaxText += createSPSS(((Variable)attrMap.getSourceAttribute().getAttribute()).getName(), project.getTargetName(), project.getTargetLabel(), mappings);
				}
			}
		}
		
		return template.replaceAll("#SYNTAX_SPSS#",	(syntaxText.isEmpty() ? "" : syntaxText));
	}
	/**
	 * @param template
	 * @param project
	 * @return
	 */
	private String insertSyntaxSPSSNumberedList(String template, Project project) {
		String syntaxText = "";
		int i = 1;
				
		Iterator<InstanceMap> itInstanceMap = project.getContent().getInstanceMaps().iterator();
		while (itInstanceMap.hasNext()) {
			InstanceMap instMap = itInstanceMap.next();
						
			Iterator<AttributeMap> itAttributeMap = project.getContent().getMapsByType(AttributeMapType.ASSIGNED_VARIABLE).iterator();
			while (itAttributeMap.hasNext()) {
				AttributeMap attrMap = itAttributeMap.next();
				
				if ((attrMap.getBelongsTo() instanceof InstanceMap) &&
						(attrMap.getBelongsTo().equals(instMap))) {
					syntaxText += "<B>SPSS</B>: ("+ i + ")";
					
					ArrayList<Characteristics[]> mappings = getThreeStepMapperListList(project.getContent(), instMap, attrMap);
					syntaxText += createSPSS(((Variable)attrMap.getSourceAttribute().getAttribute()).getName(), project.getTargetName(), project.getTargetLabel(), mappings);
					
					i++;
				}
			}			
		}
		
		return template.replaceAll("#SYNTAX_SPSS_NUMBERED_LIST#",	(syntaxText.isEmpty() ? "" : syntaxText));
	}
	/**
	 * @param template
	 * @param project
	 * @return
	 */
	private String insertSyntaxSTATA(String template, Project project) {
		String syntaxText = "";

		Iterator<InstanceMap> itInstanceMap = project.getContent().getInstanceMaps().iterator();
		while (itInstanceMap.hasNext()) {
			InstanceMap instMap = itInstanceMap.next();
			
			Iterator<AttributeMap> itAttributeMap = project.getContent().getMapsByType(AttributeMapType.ASSIGNED_VARIABLE).iterator();
			while (itAttributeMap.hasNext()) {
				AttributeMap attrMap = itAttributeMap.next();
				
				if ((attrMap.getBelongsTo() instanceof InstanceMap) &&
						(attrMap.getBelongsTo().equals(instMap))) {
					ArrayList<Characteristics[]> mappings = getThreeStepMapperListList(project.getContent(), instMap, attrMap);
					syntaxText += createSTATA(((Variable)attrMap.getSourceAttribute().getAttribute()).getName(), project.getTargetName(), project.getTargetLabel(), mappings);
				}
			}
		}
		
		return template.replaceAll("#SYNTAX_STATA#",	(syntaxText.isEmpty() ? "" : syntaxText));
	}
	/**
	 * @param template
	 * @param project
	 * @return
	 */
	private String insertSyntaxSTATANumberedList(String template, Project project) {
		String syntaxText = "";
		int i = 1;

		Iterator<InstanceMap> itInstanceMap = project.getContent().getInstanceMaps().iterator();
		while (itInstanceMap.hasNext()) {
			InstanceMap instMap = itInstanceMap.next();
			
			Iterator<AttributeMap> itAttributeMap = project.getContent().getMapsByType(AttributeMapType.ASSIGNED_VARIABLE).iterator();
			while (itAttributeMap.hasNext()) {
				AttributeMap attrMap = itAttributeMap.next();
				
				if ((attrMap.getBelongsTo() instanceof InstanceMap) &&
						(attrMap.getBelongsTo().equals(instMap))) {
					syntaxText += "<B>Stata</B>: ("+ i + ")";
					
					ArrayList<Characteristics[]> mappings = getThreeStepMapperListList(project.getContent(), instMap, attrMap);
					syntaxText += createSTATA(((Variable)attrMap.getSourceAttribute().getAttribute()).getName(), project.getTargetName(), project.getTargetLabel(), mappings);
					
					i++;
				}
			}
		}
		
		return template.replaceAll("#SYNTAX_STATA_NUMBERED_LIST#",	(syntaxText.isEmpty() ? "" : syntaxText));
	}
	/**
	 * @param template
	 * @param project
	 * @return
	 */
	private String insertSyntaxSAS(String template, Project project) {
		String syntaxText = "";
		
		Iterator<InstanceMap> itInstanceMap = project.getContent().getInstanceMaps().iterator();
		while (itInstanceMap.hasNext()) {
			InstanceMap instMap = itInstanceMap.next();
			
			Iterator<AttributeMap> itAttributeMap = project.getContent().getMapsByType(AttributeMapType.ASSIGNED_VARIABLE).iterator();
			while (itAttributeMap.hasNext()) {
				AttributeMap attrMap = itAttributeMap.next();
				
				if ((attrMap.getBelongsTo() instanceof InstanceMap) &&
						(attrMap.getBelongsTo().equals(instMap))) {
					ArrayList<Characteristics[]> mappings = getThreeStepMapperListList(project.getContent(), instMap, attrMap);
					syntaxText += createSAS(((Variable)attrMap.getSourceAttribute().getAttribute()).getName(), project.getTargetName(), project.getTargetLabel(), mappings);
				}
			}
		}
		
		return template.replaceAll("#SYNTAX_SAS#",	(syntaxText.isEmpty() ? "" : syntaxText));
	}
	/**
	 * @param template
	 * @param project
	 * @return
	 */
	private String insertSyntaxMPLUS(String template, Project project) {
		String syntaxText = "";
		
		Iterator<InstanceMap> itInstanceMap = project.getContent().getInstanceMaps().iterator();
		while (itInstanceMap.hasNext()) {
			InstanceMap instMap = itInstanceMap.next();
			
			Iterator<AttributeMap> itAttributeMap = project.getContent().getMapsByType(AttributeMapType.ASSIGNED_VARIABLE).iterator();
			while (itAttributeMap.hasNext()) {
				AttributeMap attrMap = itAttributeMap.next();
				
				if ((attrMap.getBelongsTo() instanceof InstanceMap) &&
						(attrMap.getBelongsTo().equals(instMap))) {
					ArrayList<Characteristics[]> mappings = getThreeStepMapperListList(project.getContent(), instMap, attrMap);
					syntaxText += createMPLUS(((Variable)attrMap.getSourceAttribute().getAttribute()).getName(), project.getTargetName(), project.getTargetLabel(), mappings);
				}
			}
		}
		
		return template.replaceAll("#SYNTAX_MPLUS#",	(syntaxText.isEmpty() ? "" : syntaxText));
	}
	
	
	private String insertCrossTabSPSS(String template, Project project) {
		String syntaxText = "";

		Iterator<InstanceMap> itInstanceMap = project.getContent().getInstanceMaps().iterator();
		while (itInstanceMap.hasNext()) {
			InstanceMap instMap = itInstanceMap.next();
			
			Iterator<AttributeMap> itAttributeMap = project.getContent().getMapsByType(AttributeMapType.ASSIGNED_VARIABLE).iterator();
			while (itAttributeMap.hasNext()) {
				AttributeMap attrMap = itAttributeMap.next();
				
				if ((attrMap.getBelongsTo() instanceof InstanceMap) &&
						(attrMap.getBelongsTo().equals(instMap))) {
					ArrayList<Characteristics[]> mappings = getThreeStepMapperListList(project.getContent(), instMap, attrMap);
					syntaxText += createCrossTabSPSS(((Variable)attrMap.getSourceAttribute().getAttribute()).getName(), project.getTargetName(), project.getTargetLabel(), mappings);
				}
			}
		}
		
		return template.replaceAll("#CROSS_TAB_SPSS#",	(syntaxText.isEmpty() ? "" : syntaxText));
	}
	
	private String insertCrossTabSTATA(String template, Project project) {
		String syntaxText = "";

		Iterator<InstanceMap> itInstanceMap = project.getContent().getInstanceMaps().iterator();
		while (itInstanceMap.hasNext()) {
			InstanceMap instMap = itInstanceMap.next();
			
			Iterator<AttributeMap> itAttributeMap = project.getContent().getMapsByType(AttributeMapType.ASSIGNED_VARIABLE).iterator();
			while (itAttributeMap.hasNext()) {
				AttributeMap attrMap = itAttributeMap.next();
				
				if ((attrMap.getBelongsTo() instanceof InstanceMap) &&
						(attrMap.getBelongsTo().equals(instMap))) {
					ArrayList<Characteristics[]> mappings = getThreeStepMapperListList(project.getContent(), instMap, attrMap);
					syntaxText += createCrossTabSTATA(((Variable)attrMap.getSourceAttribute().getAttribute()).getName(), project.getTargetName(), project.getTargetLabel(), mappings);
				}
			}
		}
		
		return template.replaceAll("#CROSS_TAB_STATA#",	(syntaxText.isEmpty() ? "" : syntaxText));
	}
	/**
	 * @param content
	 * @return
	 */
	private String getThreeStepMapperListList(ProContent content) {
		String instanceMappingsRek	= "";
		Iterator<InstanceMap> instMapIteratorRek = content.getInstanceMaps().iterator();
		
		ArrayList<Characteristics[]> threeStepMapper = new ArrayList<Characteristics[]>();
		
		if (instMapIteratorRek.hasNext()) {
			instanceMappingsRek += "<UL>";
			while (instMapIteratorRek.hasNext()) {
				InstanceMap instanceMap = instMapIteratorRek.next();
				
				if (instanceMap.getType().equals(InstanceMapType.DATA_RECODING)) {
					instanceMappingsRek += "<LI>"+ instanceMap.getSourceInstance() +" => "+ instanceMap.getTargetInstance() + "<BR>\n";
									
					Iterator<AttributeMap> attrMapIterator = content.getMaps().iterator();					
					if (attrMapIterator.hasNext()) {
						instanceMappingsRek += "<UL>";
						while (attrMapIterator.hasNext()) {
							AttributeMap attributeMap = attrMapIterator.next();
							if ((attributeMap.getBelongsTo() instanceof InstanceMap) &&
									(attributeMap.getBelongsTo().getEntityID() == instanceMap.getEntityID())) {
								instanceMappingsRek += "<LI>"+ attributeMap.getSourceAttribute() +" => "+ attributeMap.getTargetAttribute() + "<BR>\n";
								
								Iterator<CharacteristicMap> charMapIterator = content.getCharacteristicMaps().iterator();								
								if (charMapIterator.hasNext()) {
									instanceMappingsRek += "<UL>";
									while (charMapIterator.hasNext()) {
										CharacteristicMap charMap = charMapIterator.next();
										if ((charMap.getBelongsTo() instanceof AttributeMap) &&
												(charMap.getBelongsTo().getEntityID() == attributeMap.getEntityID())) {
											instanceMappingsRek += "<LI>"+ charMap.getSourceCharacteristic() +" => "+ charMap.getTargetCharacteristic() + "<BR>\n";
											
											CharacteristicMap osCharMap = content.getCharacteristicMapByCharacteristic(charMap.getTargetCharacteristic());
											
											if (!(osCharMap instanceof CharacteristicMap))
												osCharMap = new CharacteristicMap();
											
											CharacteristicMap csCharMap = 
													(osCharMap.getTargetCharacteristic() instanceof CharacteristicLink ? content.getCharacteristicMapByCharacteristic(osCharMap.getTargetCharacteristic()): new CharacteristicMap());
											
											Characteristics[] maps = new Characteristics[6];
											maps[0] = charMap.getSourceCharacteristic().getCharacteristic();
											maps[1] = charMap.getTargetCharacteristic() instanceof CharacteristicLink ? charMap.getTargetCharacteristic().getCharacteristic() : null; // new;
											maps[2] = osCharMap.getSourceCharacteristic() instanceof CharacteristicLink ? osCharMap.getSourceCharacteristic().getCharacteristic(): null; // new
											maps[3] = osCharMap.getTargetCharacteristic() instanceof CharacteristicLink ? osCharMap.getTargetCharacteristic().getCharacteristic(): null;
											maps[4] = csCharMap.getSourceCharacteristic() instanceof CharacteristicLink ? csCharMap.getSourceCharacteristic().getCharacteristic(): null;
											maps[5] = csCharMap.getTargetCharacteristic() instanceof CharacteristicLink ? csCharMap.getTargetCharacteristic().getCharacteristic(): null;
											
											threeStepMapper.add(maps);
											
											instanceMappingsRek += maps[0]+","+maps[1]+","+maps[2]+","+maps[3]+","+maps[4]+","+maps[5];
										}										 
									}
									instanceMappingsRek += "</UL>";
								}								
							}
						}
						instanceMappingsRek += "</UL>";
					}
				}
			}
			instanceMappingsRek += "</UL>";
		}
		
		
		
		return instanceMappingsRek;
	}
	
	
	/**
	 * @param content
	 * @return
	 */
	private String getDimensions(ProContent content) {
		String dimensions	= "";
		Iterator<ConDimension> dimIterator = content.getDimensions().iterator();
		
		if (dimIterator.hasNext()) {
			dimensions += "<UL>";
			while (dimIterator.hasNext()) {
				ConDimension dimension = dimIterator.next();
				dimensions += "<LI>"+ (dimension.getLabel() +", "+ dimension.getLevel() +"<BR>\n");
				
				if ((content.getLinkByAttribute(dimension) instanceof AttributeLink) &&
						(content.getLinkByAttribute(dimension).getInstance() instanceof WorkStepInstance))
					dimensions += content.getLinkByAttribute(dimension).getInstance().getLabel();
				
				dimensions += "<BR>\n";
				
				Iterator<CharacteristicLink> charLinkIterator = content.getCharacteristicLinksByAttribute(dimension).iterator();
				
				if (charLinkIterator.hasNext()) {
					dimensions += "<UL>";
					while (charLinkIterator.hasNext()) {
						CharacteristicLink charLink = charLinkIterator.next();
						
						dimensions += "<LI>"+ ((ConSpecification)charLink.getCharacteristic()).getLabel() +"</BLOCKQUOTE><BR>\n";
					}
					dimensions += "</UL>";
				}
			}
			dimensions += "</UL>";
		}
		
		return dimensions;
	}
	/**
	 * @param content
	 * @return
	 */
	private String getIndicators(ProContent content) {
		String indicators	= "";
		Iterator<OperaIndicator> indIterator = content.getIndicators().iterator();
		
		if (indIterator.hasNext()) {
			indicators += "<UL>";
			while (indIterator.hasNext()) {
				OperaIndicator indicator = indIterator.next();
				indicators += "<LI>"+ (indicator.getLabel() +", "+ indicator.getLevel() +", "+ indicator.getType() +"<BR>\n");
				
				if ((content.getLinkByAttribute(indicator) instanceof AttributeLink) &&
						(content.getLinkByAttribute(indicator).getInstance() instanceof WorkStepInstance))
					indicators += content.getLinkByAttribute(indicator).getInstance().getLabel();
				
				indicators += "<BR>\n";
								
				Iterator<CharacteristicLink> charLinkIterator = content.getCharacteristicLinksByAttribute(indicator).iterator();
				
				if (charLinkIterator.hasNext()) {
					indicators += "<UL>";
					while (charLinkIterator.hasNext()) {
						CharacteristicLink charLink = charLinkIterator.next();
						
						indicators += "<LI>"+ ((OperaPrescription)charLink.getCharacteristic()).getValue() +":"+ ((OperaPrescription)charLink.getCharacteristic()).getLabel() +"</BLOCKQUOTE><BR>\n";
					}
					indicators += "</UL>";
				}
			}
			indicators += "</UL>";
		}
		
		return indicators;
	}
	/**
	 * @param content
	 * @return
	 */
	private String getVariables(ProContent content) {
		String variables	= "";
		Iterator<Variable> varIterator = content.getVariables().iterator();
		
		if (varIterator.hasNext()) {
			variables += "<UL>";
			while (varIterator.hasNext()) {
				Variable variable = varIterator.next();
				variables += "<LI>"+ (variable.getName() +"("+ variable.getLabel() +"), "+ variable.getLevel() +", "+ variable.getMeasureType() +", "+ variable.getType() +"<BR>\n");
				
				if ((variable.getDefinition() instanceof Definition) &&
						(!variable.getDefinition().getText().isEmpty())) {
					variables += "<BR>\nDef.:";
					variables += "<BR>\n"+ variable.getDefinition().getText() +"<BR>\n"+"<BR>\n";
				}
				
				if ((variable.getComment() instanceof Comment) &&
						(!variable.getComment().getText().isEmpty()))
					variables += "<BR>\n"+ variable.getComment().getText() +"<BR>\n"+"<BR>\n";
				
				if ((content.getLinkByAttribute(variable) instanceof AttributeLink) &&
						(content.getLinkByAttribute(variable).getInstance() instanceof WorkStepInstance))
					variables += content.getLinkByAttribute(variable).getInstance().getLabel();
				
				variables += "<BR>\n";
				
				Iterator<CharacteristicLink>  charLinkIterator = content.getCharacteristicLinksByAttribute(variable).iterator();
				
				if (charLinkIterator.hasNext()) {
					variables += "<UL>";
					while (charLinkIterator.hasNext()) {
						CharacteristicLink charLink = charLinkIterator.next();
						
						variables += "<LI>"+ ((Value)charLink.getCharacteristic()).getValue() +":"+ ((Value)charLink.getCharacteristic()).getLabel() +"</BLOCKQUOTE><BR>\n";
					}
					variables += "</UL>";
				}
				
				Question question = variable.getSource();
				if (question instanceof Question) {
					variables += question.getText() +"<BR>\n";
				}
				
				Study study = question.getSource();
				if (study instanceof Study) {
					variables += study.getTitle() +"<BR>\n";
					variables += study.getDateOfCollection() +", "+ study.getStudyArea() +", "+ study.getPopulation() +", "+ study.getSelection() +"<BR>\n";
				}
				
			}
			variables += "</UL>";
		}
		
		return variables;
	}
	
	/**
	 * @param content
	 * @return
	 */
	@SuppressWarnings("unused")
	private String getInstanceMapsRekList(ProContent content) {
		String instanceMappingsRek	= "";
		Iterator<InstanceMap> instMapIteratorRek = content.getInstanceMaps().iterator();
		
		if (instMapIteratorRek.hasNext()) {
			instanceMappingsRek += "<UL>";
			while (instMapIteratorRek.hasNext()) {
				InstanceMap instanceMap = instMapIteratorRek.next();
				instanceMappingsRek += "<LI>"+ instanceMap.getSourceInstance() +" => "+ instanceMap.getTargetInstance() + "<BR>\n";
								
				Iterator<AttributeMap> attrMapIterator = content.getMaps().iterator();					
				if (attrMapIterator.hasNext()) {
					instanceMappingsRek += "<UL>";
					while (attrMapIterator.hasNext()) {
						AttributeMap attributeMap = attrMapIterator.next();
						if ((attributeMap.getBelongsTo() instanceof InstanceMap) &&
								(attributeMap.getBelongsTo().getEntityID() == instanceMap.getEntityID())) {
							instanceMappingsRek += "<LI>"+ attributeMap.getSourceAttribute() +" => "+ attributeMap.getTargetAttribute() + "<BR>\n";
							
							Iterator<CharacteristicMap> charMapIterator = content.getCharacteristicMaps().iterator();								
							if (charMapIterator.hasNext()) {
								instanceMappingsRek += "<UL>";
								while (charMapIterator.hasNext()) {
									CharacteristicMap charMap = charMapIterator.next();
									if ((charMap.getBelongsTo() instanceof AttributeMap) &&
											(charMap.getBelongsTo().getEntityID() == attributeMap.getEntityID())) {
										instanceMappingsRek += "<LI>"+ charMap.getSourceCharacteristic() +" => "+ charMap.getTargetCharacteristic() + "<BR>\n";	
									}										 
								}
								instanceMappingsRek += "</UL>";
							}								
						}
					}
					instanceMappingsRek += "</UL>";
				}
			}
			instanceMappingsRek += "</UL>";
		}
		
		return instanceMappingsRek;
	}
	/**
	 * @param content
	 * @return
	 */
	private String getInstanceMapsList(ProContent content) {
		String instanceMappings	= "";
		Iterator<InstanceMap> instMapIterator = content.getInstanceMaps().iterator();
		
		if (instMapIterator.hasNext()) {
			instanceMappings += "<UL>";
			while (instMapIterator.hasNext()) {
				InstanceMap instanceMap = instMapIterator.next();
				instanceMappings += "<LI>"+ instanceMap.getSourceInstance() +" => "+ instanceMap.getTargetInstance() + "<BR>\n"; 
			}
			instanceMappings += "</UL>";
		}
		
		return instanceMappings;
	}
	/**
	 * @param content
	 * @return
	 */
	private String getAttributeMapsList(ProContent content) {
		String attributeMappings	= "";
		Iterator<AttributeMap> attrMapIterator = content.getMaps().iterator();
		
		if (attrMapIterator.hasNext()) {
			attributeMappings += "<UL>";
			while (attrMapIterator.hasNext()) {
				AttributeMap attributeMap = attrMapIterator.next();
				attributeMappings += "<LI>"+ attributeMap.getSourceAttribute() +" => "+ attributeMap.getTargetAttribute() + "<BR>\n"; 
			}
			attributeMappings += "</UL>";
		}
		
		return attributeMappings;
	}
	/**
	 * @param content
	 * @return
	 */
	private String getCharMapsList(ProContent content) {
		String charMappings	= "";
		Iterator<CharacteristicMap> charMapIterator = content.getCharacteristicMaps().iterator();
		
		if (charMapIterator.hasNext()) {
			charMappings += "<UL>";
			while (charMapIterator.hasNext()) {
				CharacteristicMap charMap = charMapIterator.next();
				charMappings += "<LI>"+ charMap.getSourceCharacteristic() +" => "+ charMap.getTargetCharacteristic() + "<BR>\n"; 
			}
			charMappings += "</UL>";
		}
		
		return charMappings;
	}
	
	/*
	 *	FrameReport.update handles the update of the project report!
	 */
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.InternalFrame#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		IdentifiedParameter parameter = null;
		
		if (arg instanceof IdentifiedParameter) {
			parameter = (IdentifiedParameter)arg;
		}
		
		if (parameter != null) {
			Object[]	addenda		= parameter.getParameters();
			
			switch (parameter.getID()) {
				case ActionCommandID.BTN_TB_SHOW_FORM:
					try {
						this.setMaximum(false);
						this.updateUI();
					} catch (PropertyVetoException e) {
						System.err.println("BTN_TB_SHOW_FORM:FrameReport");
					}
					break;
				case ActionCommandID.BTN_TB_SHOW_GRAPH:
					try {
						this.setMaximum(false);
						this.updateUI();
					} catch (PropertyVetoException e) {
						System.err.println("BTN_TB_SHOW_GRAPH:FrameReport");
					}
					break;
				case ActionCommandID.BTN_TB_SHOW_REPORT:
					try {
						this.setSelected(true);
						this.setMaximum(true);
						this.updateUI();
					} catch (PropertyVetoException e) {
						System.err.println("BTN_TB_SHOW_GRAPH:FrameReport");
					}
					break;
				case ActionCommandID.MOD_MOD:
					if (addenda[0] instanceof CStatsModel) {
						model = (CStatsModel)addenda[0];
						
						buildReport(resourceBundle, addenda[0]);
					}
					break;
				case ActionCommandID.CMD_USER_LOGIN:
					/* DoNothing Yet */
					break;
				case ActionCommandID.CMD_USER_LOGOFF:
					if (addenda[0] instanceof CStatsModel) {
						model = ((CStatsModel)addenda[0]);
						
						buildReport(resourceBundle, addenda[0]);
					}
					break;
				case ActionCommandID.BTN_DAT_STP_MAP_CHA_CONFIRM: 
				case ActionCommandID.BTN_TGT_STP_TGT_TAB_AUTO: 
				case ActionCommandID.CMD_PRJ_OPEN_PROJECT:
					if (addenda[0] instanceof CStatsModel) {
						model = ((CStatsModel)addenda[0]);
						
						buildReport(resourceBundle, addenda[0]);
					}
					break;
				case ActionCommandID.CMD_PRJ_REMOVE:
				case ActionCommandID.CMD_PRJ_CLOSE:
					if (addenda[0] instanceof CStatsModel) {
						model = ((CStatsModel)addenda[0]);
						
						buildReport(resourceBundle, addenda[0]);
					}
					break;
				case ActionCommandID.BTN_FRM_REP_SAVE_REPORT:
					saveReport(model.getProject());
					break;
				default:
					break;
			}			
		}	
		
	}
	
	/**
	 * 
	 */
	protected void projectTemplate() {
		JFileChooser.setDefaultLocale(currentLocale);
	    JFileChooser	fc		= new JFileChooser ();

	    if ((model.getProject().getProjectTemplate() instanceof String) &&
	    		(!model.getProject().getProjectTemplate().equals("")))
	    	fc.setSelectedFile(new File(model.getProject().getProjectTemplate()));
	    
	    FileFilter		filter	= new FileNameExtensionFilter("Report Template", "tmpl");
    
	    fc.addChoosableFileFilter(filter);	    
	    fc.setFileFilter(filter);
	    
	    fc.setDialogTitle(resourceBundle.getString(IMPORT));
	    
	    int returnVal = fc.showOpenDialog(null);
	    if (returnVal == JFileChooser.APPROVE_OPTION) {
	    	File 		file	= fc.getSelectedFile();
	    	
	    	model.getProject().setProjectTemplate(file.getAbsolutePath());
	    	
	    	try {
				userDefinedTemplate = loadFileToString(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
	    	
			useDefaultTemplate = false;
			buildReport(resourceBundle, model);
	    }
	    
	    reportPane.setEditable(false);
	}
	
	/**
	 * 
	 */
	protected void importTemplate() {
		JFileChooser.setDefaultLocale(currentLocale);
	    JFileChooser	fc		= new JFileChooser ();	    		    
	    FileFilter		filter	= new FileNameExtensionFilter("Report Template", "tmpl");
    
	    fc.addChoosableFileFilter(filter);	    
	    fc.setFileFilter(filter);
	    
	    fc.setDialogTitle(resourceBundle.getString(IMPORT));
	    
	    ResourceBundle	resourceBundle	= ResourceBundle.getBundle(BUNDLE_F, currentLocale);
		String			file_name		= resourceBundle.getString(FILE_NAME);
	    
		String path_0 = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		path_0 = path_0.replace("/", "\\\\"); // use File.separator in CharmStatsPro
		path_0 = path_0.replace(file_name, ""); // filename stored in resource FrameBundle
		path_0 = path_0 + "reports\\\\templates\\\\";
	    fc.setCurrentDirectory (new File (path_0));
	    
	    int returnVal = fc.showOpenDialog(null);
	    if (returnVal == JFileChooser.APPROVE_OPTION) {
	    	File 		file	= fc.getSelectedFile();
	    	
	    	try {
				userDefinedTemplate = loadFileToString(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
	    	
			useDefaultTemplate = false;
			buildReport(resourceBundle, model);
	    }
	    
	    reportPane.setEditable(false);
	}
	
	/**
	 * 
	 */
	protected void useDefaultTemplate() {
		useDefaultTemplate = true;
		buildReport(resourceBundle, model);
		
		reportPane.setEditable(false);
	}
	
	/**
	 * 
	 */
	protected void filePrint() { 
		
		print(this.reportPane);
	}
	
	/**
	 * @param pane
	 */
	protected void print(JEditorPane pane) {
		/* TODO How to change the printDialog-Language? */
		
		PrinterJob pj = PrinterJob.getPrinterJob();
		if(pj.printDialog()) {
			try {
				pj.setPrintable(pane.getPrintable(head, foot));
	            pj.print();
			} catch (PrinterException e) {
				JOptionPane.showMessageDialog(
						null, e.getMessage(), "PrinterException:",  JOptionPane.ERROR_MESSAGE);
					  	
				/* DEMO ONLY */
				System.err.println("PrinterException: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @return
	 */
	protected boolean saveReport(Project project) {
		SyntaxFilter fSyntaxFilter = new SyntaxFilter (); 
		File fFile = new File ("report.html"); 
  
		JComponent.setDefaultLocale(currentLocale);
		
	    JFileChooser fc = new JFileChooser (); 

	    // Start in current directory
//	    fc.setCurrentDirectory (new File ("."));
	    
	    ResourceBundle	resourceBundle	= ResourceBundle.getBundle(BUNDLE_F, currentLocale);
		String			file_name		= resourceBundle.getString(FILE_NAME);
	    
		String path_0 = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		path_0 = path_0.replace("/", "\\\\"); // use File.separator in CharmStatsPro
		path_0 = path_0.replace(file_name, ""); // filename stored in resource FrameBundle
		path_0 = path_0 + "reports\\\\projects\\\\";
//	    fc.setCurrentDirectory (new File (path_0));
		
		
		String path_1 = path_0 + project.getName(); 
		String path_2 = path_1 +"\\\\graphs\\\\";
		
		File dir = new File (path_2);
		if (!dir.exists()) {
			try{
			    if(dir.mkdirs()) {
			    	dir = new File (path_2);
			    	
//			    	File graphs_dir = new File (path_1 + "graphs\\\\");
//			    	graphs_dir.mkdir();
			    } else {
			        dir = new File (path_0);
			    }
			} catch(Exception e){
			    e.printStackTrace();
			} 
		}
		dir = new File (path_1);
		
		fc.setCurrentDirectory (dir);

		
	    // Set filter for Java source files.
	    fc.setFileFilter (fSyntaxFilter); 

	    // Set to a default name for save.
	    fc.setSelectedFile (fFile); 

	    // Open chooser dialog
	    int result = fc.showSaveDialog(this);

	    if (result == JFileChooser.CANCEL_OPTION) {
	    	return true;
	    } else if (result == JFileChooser.APPROVE_OPTION) {
	    	fFile = fc.getSelectedFile ();
	        if (fFile.exists ()) {
	        	int response = JOptionPane.showConfirmDialog (null,
	        			"Overwrite existing file?","Confirm Overwrite",
	        			JOptionPane.OK_CANCEL_OPTION,
	        			JOptionPane.QUESTION_MESSAGE);
	        	if (response == JOptionPane.CANCEL_OPTION) return false;
	        }
	        return writeFile (fFile, this.reportPane.getText());
	    } else {
	    	return false;
	    }
	}

	private class SyntaxFilter extends javax.swing.filechooser.FileFilter
	{
		/* (non-Javadoc)
		 * @see javax.swing.filechooser.FileFilter#accept(java.io.File)
		 */
		public boolean accept (File f) {
			return f.getName().toLowerCase().endsWith(".html") || f.isDirectory();
		}
	 
		/* (non-Javadoc)
		 * @see javax.swing.filechooser.FileFilter#getDescription()
		 */
		public String getDescription () {
			return "HTML files (*.html)";
		}
	}	
	
	/**
	 * 
	 */
	protected void editReport() {
		reportPane.setEditable(true);
	}
	
	/**
	 * @param content
	 * @param selectedMapping
	 * @param straitenedMapping
	 * @return
	 */
	private ArrayList<Characteristics[]> getThreeStepMapperListList(ProContent content, InstanceMap selectedMapping, AttributeMap straitenedMapping) {
		ArrayList<Characteristics[]> threeStepMapper = new ArrayList<Characteristics[]>();
		
		if (selectedMapping.getType().equals(InstanceMapType.DATA_RECODING)) {
			if ((straitenedMapping.getBelongsTo() instanceof InstanceMap) &&
					(straitenedMapping.getBelongsTo().getEntityID() == selectedMapping.getEntityID())) {
				Iterator<CharacteristicMap> charMapIterator = content.getCharacteristicMaps().iterator();								
				if (charMapIterator.hasNext()) {
					while (charMapIterator.hasNext()) {
						CharacteristicMap charMap = charMapIterator.next();
						
						if ((charMap.getBelongsTo() instanceof AttributeMap) &&
								(charMap.getBelongsTo().getEntityID() == straitenedMapping.getEntityID())) {
							
							CharacteristicMap osCharMap = content.getCharacteristicMapByCharacteristic(charMap.getTargetCharacteristic());
							
							if (!(osCharMap instanceof CharacteristicMap))
								osCharMap = new CharacteristicMap();
							
							CharacteristicMap csCharMap = 
									(osCharMap.getTargetCharacteristic() instanceof CharacteristicLink ? content.getCharacteristicMapByCharacteristic(osCharMap.getTargetCharacteristic()): new CharacteristicMap());
							
							Characteristics[] maps = new Characteristics[6];
							maps[0] = charMap.getSourceCharacteristic().getCharacteristic();
							maps[1] = charMap.getTargetCharacteristic() instanceof CharacteristicLink ? charMap.getTargetCharacteristic().getCharacteristic() : null;
							maps[2] = osCharMap.getSourceCharacteristic() instanceof CharacteristicLink ? osCharMap.getSourceCharacteristic().getCharacteristic(): null;
							maps[3] = osCharMap.getTargetCharacteristic() instanceof CharacteristicLink ? osCharMap.getTargetCharacteristic().getCharacteristic(): null;
							maps[4] = csCharMap.getSourceCharacteristic() instanceof CharacteristicLink ? csCharMap.getSourceCharacteristic().getCharacteristic(): null;
							maps[5] = csCharMap.getTargetCharacteristic() instanceof CharacteristicLink ? csCharMap.getTargetCharacteristic().getCharacteristic(): null;
							
							threeStepMapper.add(maps);
						}										 
					}
				}								
			}
		}
		
		return threeStepMapper;
	}
	
	/**
	 * @param oldVarName
	 * @param newVarName
	 * @param newVarLabel
	 * @param mappings
	 * @return
	 */
	private String createSPSS(String oldVarName, 
			String newVarName, String newVarLabel, ArrayList<Characteristics[]> mappings) {
		
		String syntaxText = "<BR>\nRECODE "+ oldVarName +" ";
		
		Iterator<Characteristics[]> mappingIter = mappings.iterator();
		while (mappingIter.hasNext()) {
			Characteristics[] map = mappingIter.next();
			
			if ((map[0] instanceof Value) 
					&& (map[5] instanceof Category)) {
				if (!((Value)map[0]).isMissing())
					syntaxText += "("+ ((Value)map[0]).getValue() +"="+ ((Category)map[5]).getCode() +") ";	
				else
					syntaxText += "("+ ((Value)map[0]).getValue() +"=SYSMIS) ";
			}
		}
		
		syntaxText += "INTO "+ newVarName +".<BR>\n";
		syntaxText += "VARIABLE LABELS "+ newVarName +" '"+ newVarLabel +"'.<BR>\n";
		syntaxText += "VALUE LABELS\n";
		syntaxText += newVarName;
		/* reuse */mappingIter = mappings.iterator();
		Set<Category> set = new HashSet<Category>(); 
		while (mappingIter.hasNext()) {
			Characteristics[] map = mappingIter.next();
			
			if ((map[0] instanceof Value) 
					&& (map[5] instanceof Category)) {				
				set.add((Category)map[5]); 
			}
		}

		Iterator<Category> categoryIter = set.iterator();
		while (categoryIter.hasNext()) {
			Category cat = categoryIter.next();
			
			syntaxText += "\n"+ cat.getCode() +" '"+ cat.getLabel().replace("'", "") +"'";	
		}

		syntaxText += ".\n";
		
		syntaxText += "EXECUTE.<BR>\n";
		
		return syntaxText;
	}
	
	private String createCrossTabSPSS(String oldVarName, 
			String newVarName, String newVarLabel, ArrayList<Characteristics[]> mappings) {
		
		String syntaxText = "<BR>\nFREQUENCIES VARIABLES="+ oldVarName;
		syntaxText += "<BR>\n /ORDER=ANALYSIS.";
		
		syntaxText += "<BR>\nRECODE "+ oldVarName +" ";
		
		Iterator<Characteristics[]> mappingIter = mappings.iterator();
		while (mappingIter.hasNext()) {
			Characteristics[] map = mappingIter.next();
			
			if ((map[0] instanceof Value) 
					&& (map[5] instanceof Category)) {
				if (!((Value)map[0]).isMissing())
					syntaxText += "("+ ((Value)map[0]).getValue() +"="+ ((Category)map[5]).getCode() +") ";	
				else
					syntaxText += "("+ ((Value)map[0]).getValue() +"=SYSMIS) ";
			}
		}
		
		syntaxText += "INTO "+ newVarName +".<BR>\n";
		syntaxText += "VARIABLE LABELS "+ newVarName +" '"+ newVarLabel +"'.<BR>\n";
		syntaxText += "EXECUTE.<BR>\n";
		
		syntaxText += "<BR>\nCROSSTABS";
		syntaxText += "<BR>\n /TABLES="+ oldVarName +" BY "+ newVarName;
		syntaxText += "<BR>\n /FORMAT=AVALUE TABLES";
		syntaxText += "<BR>\n /CELLS=COUNT";
		syntaxText += "<BR>\n /COUNT=ROUND CELL<BR>\n";
		
		return syntaxText;
	}
	
	/**
	 * @param oldVarName
	 * @param newVarName
	 * @param newVarLabel
	 * @param mappings
	 * @return
	 */
	private String createSTATA(String oldVarName, 
			String newVarName, String newVarLabel, ArrayList<Characteristics[]> mappings) {
		
//		String syntaxText = "<BR>\n. recode "+ oldVarName +" ";
		String syntaxText = "<BR>\ngenerate "+ newVarName +" = "+ oldVarName;
		syntaxText += "<BR>\nrecode "+ newVarName +" ";
		Iterator<Characteristics[]> mappingIter = mappings.iterator();
		if (mappingIter.hasNext()) {
			Characteristics[] map = mappingIter.next();
			
			if ((map[0] instanceof Value) 
					&& (map[5] instanceof Category)) {
				if (!((Value)map[0]).isMissing())
					syntaxText += "("+ ((Value)map[0]).getValue() +" = "+ ((Category)map[5]).getCode() +") ";
				else
					syntaxText += "("+ ((Value)map[0]).getValue() +" = .) ";
			}
		}
		while (mappingIter.hasNext()) {
			Characteristics[] map = mappingIter.next();
			
			if ((map[0] instanceof Value) 
					&& (map[5] instanceof Category)) {
//				syntaxText += "\t";
				if (!((Value)map[0]).isMissing())	
					syntaxText += "("+ ((Value)map[0]).getValue() +" = "+ ((Category)map[5]).getCode() +") ";
				else
					syntaxText += "("+ ((Value)map[0]).getValue() +" = .) ";
				
			}
		}
//		syntaxText += ", gen("+ newVarName +") label("+ newVarLabel +")";
		syntaxText += "<BR>\n";
		syntaxText += "label variable "+ newVarName +" \""+ newVarLabel +"\"";
		
		
		/* label values: */
		/* reuse */mappingIter = mappings.iterator();
		Set<Category> set = new HashSet<Category>(); 
		while (mappingIter.hasNext()) {
			Characteristics[] map = mappingIter.next();
			
			if ((map[0] instanceof Value) 
					&& (map[5] instanceof Category))				
				set.add((Category)map[5]);
		}
		
		Iterator<Category> categoryIter = set.iterator();
		if (categoryIter.hasNext())
			syntaxText += "<BR>\nlabel define "+ newVarName +" ";
		while (categoryIter.hasNext()) {
			Category cat = categoryIter.next();
			
			syntaxText += cat.getCode() +" \""+ cat.getLabel().replace("'", "") +" \" ";
		}
		syntaxText += "<BR>\nlabel value "+ newVarName +" "+ newVarName +"<BR>\n";
		/* label values END */
		
			
		return syntaxText;
	}
	
	private String createCrossTabSTATA(String oldVarName, 
			String newVarName, String newVarLabel, ArrayList<Characteristics[]> mappings) {
		
		String syntaxText = "<BR>\ntab "+ oldVarName +", mis";
		
		syntaxText += "<BR>\ngenerate "+ newVarName +" = "+ oldVarName;
		syntaxText += "<BR>\nrecode "+ newVarName +" ";
		Iterator<Characteristics[]> mappingIter = mappings.iterator();
		if (mappingIter.hasNext()) {
			Characteristics[] map = mappingIter.next();
			
			if ((map[0] instanceof Value) 
					&& (map[5] instanceof Category)) {
				if (!((Value)map[0]).isMissing())
					syntaxText += "("+ ((Value)map[0]).getValue() +" = "+ ((Category)map[5]).getCode() +") ";
				else
					syntaxText += "("+ ((Value)map[0]).getValue() +" = .) ";
			}
		}
		while (mappingIter.hasNext()) {
			Characteristics[] map = mappingIter.next();
			
			if ((map[0] instanceof Value) 
					&& (map[5] instanceof Category)) {
				if (!((Value)map[0]).isMissing())	
					syntaxText += "("+ ((Value)map[0]).getValue() +" = "+ ((Category)map[5]).getCode() +") ";
				else
					syntaxText += "("+ ((Value)map[0]).getValue() +" = .) ";
				
			}
		}
		syntaxText += "<BR>\n";
		syntaxText += "label variable "+ newVarName +" \""+ newVarLabel +"\"";
		
		syntaxText += "<BR>\ntab "+ oldVarName +" "+ newVarName +", mis";
			
		return syntaxText;
	}
	
	/**
	 * @param oldVarName
	 * @param newVarName
	 * @param newVarLabel
	 * @param mappings
	 * @return
	 */
	private String createSAS(String oldVarName, 
			String newVarName, String newVarLabel, ArrayList<Characteristics[]> mappings) {
		
		String syntaxText = "<BR>\nDATA "+ newVarName +"Temp;\r\n";
		syntaxText += "\tSET replace_with_DATA_FILE;\r\n";
		
		syntaxText += "\t"+ newVarName +" = .;\r\n";
		Iterator<Characteristics[]> mappingIter = mappings.iterator();
		while (mappingIter.hasNext()) {
			Characteristics[] map = mappingIter.next();
			
			if ((map[0] instanceof Value) 
					&& (map[5] instanceof Category)) {
				if (!((Value)map[0]).isMissing())
					syntaxText += "\tIF ("+ oldVarName +"="+ ((Value)map[0]).getValue() +") THEN "+ newVarName +" = "+ ((Category)map[5]).getCode() +";\r\n ";	
				else
					syntaxText += "\tIF ("+ oldVarName +"="+ ((Value)map[0]).getValue() +") THEN "+ newVarName +" = .;\r\n ";
			}
		}
		
		syntaxText += "RUN.";
		
		return syntaxText;
	}
	
	/**
	 * @param oldVarName
	 * @param newVarName
	 * @param newVarLabel
	 * @param mappings
	 * @return
	 */
	private String createMPLUS(String oldVarName, 
			String newVarName, String newVarLabel, ArrayList<Characteristics[]> mappings) {
		
		String syntaxText = "<BR>\nDEFINE: \r\n";
		
		Iterator<Characteristics[]> mappingIter = mappings.iterator();
		while (mappingIter.hasNext()) {
			Characteristics[] map = mappingIter.next();
			
			if ((map[0] instanceof Value) 
					&& (map[5] instanceof Category)) {
				syntaxText += "IF "+ oldVarName +" EQ "+ ((Value)map[0]).getValue() +" THEN "+ newVarName +" = "+ ((Category)map[5]).getCode() +";\r\n ";
			}
		}
		
		return syntaxText;
	}

	
	/**
	 * @param file
	 * @param dataString
	 * @return
	 */
	private /*static*/ boolean writeFile (File file, String dataString) {
		try {
			PrintWriter out = new PrintWriter (file);
			
			out.print (dataString);
			out.flush ();
			out.close ();
		 } catch (IOException e) {
			JOptionPane.showMessageDialog(
					null, e.getMessage(), "IOException:",  JOptionPane.ERROR_MESSAGE);
					  	
			/* DEMO ONLY */
			System.err.println("IOException: " + e.getMessage());
			e.printStackTrace();
			 
			return false;
		 }
		 return true;
	 }
	

	/**
	 * @param file
	 * @return
	 * @throws IOException
	 */
	private String loadFileToString(File file) throws IOException {
		StringBuffer content = new StringBuffer();
		BufferedReader reader = null;
 
		try {
			reader = new BufferedReader(new FileReader(file));
			String s = null;
 
			while ((s = reader.readLine()) != null) {
				content.append(s).append(System.getProperty("line.separator"));
			}
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				throw e;
			}
		}
		
		return content.toString();
	}

	protected static ImageIcon createImageIcon(String resourceURL,
			String description) {
		java.net.URL imgURL = ToolBar.class.getClassLoader().getResource(resourceURL);
		if (imgURL != null) {
	   		return new ImageIcon(imgURL, description);
	   	} else {
	   		System.err.println("Couldn't find file: " + resourceURL);
	   		return null;
	   	}
	}
}

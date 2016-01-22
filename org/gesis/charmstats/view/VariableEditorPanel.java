package org.gesis.charmstats.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.gesis.charmstats.ActionCommandText;
import org.gesis.charmstats.controller.CStatsCtrl;
import org.gesis.charmstats.model.CharacteristicLink;
import org.gesis.charmstats.model.CharacteristicLinkType;
import org.gesis.charmstats.model.Comment;
import org.gesis.charmstats.model.Definition;
import org.gesis.charmstats.model.MeasurementLevel;
import org.gesis.charmstats.model.Question;
import org.gesis.charmstats.model.Study;
import org.gesis.charmstats.model.TypeOfData;
import org.gesis.charmstats.model.Value;
import org.gesis.charmstats.model.Variable;
import org.gesis.charmstats.model.VariableType;

/**
 * 
 * @author	Martin Friedrichs
 * @since	0.9.2
 *
 */
public class VariableEditorPanel extends JPanel implements FocusListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String BUNDLE			= "org.gesis.charmstats.resources.DesktopBundle";
	public static final String ERROR_BUNDLE 	= "org.gesis.charmstats.resources.ErrorMessagesBundle";
	public static final String VE_TITLE			= "ve_title";
	public static final String VE_NAME			= "ve_name";
	public static final String VE_LABEL			= "ve_label";
	public static final String VE_TYPE			= "ve_type";
	public static final String VE_LEVEL			= "ve_level";
	public static final String VE_MEASURE		= "ve_measure";
	public static final String VE_SAVE			= "ve_save";
	public static final String VE_RESET			= "ve_reset";
	public static final String VE_COMMENT		= "comment";
	
	public static final String VE_ACCEPT		= "ve_accept";
	public static final String VE_CANCEL		= "ve_cancel";
	public static final String VE_HELP			= "ve_help";
	public static final String VE_NO_HELP		= "ve_no_help";
	public static final String VE_VARIABLE		= "ve_variable";
	public static final String VE_QUESTION		= "ve_question";
	public static final String VE_QUEST_TEXT	= "ve_quest_text";
	public static final String VE_QUEST_INST	= "ve_quest_inst";
	public static final String VE_STUDY			= "ve_study";
	public static final String VE_STUDY_TITLE 	= "ve_study_title";
	public static final String VE_STUDY_AREA	= "ve_study_area";
	public static final String VE_DATE_COLL 	= "ve_date_coll";
	public static final String VE_POPULATION 	= "ve_population";
	public static final String VE_SELECTION 	= "ve_selection";
	public static final String VE_COLL_METH 	= "ve_coll_meth";
	public static final String VE_COLLECTORS 	= "ve_collectors";
	public static final String VE_SOURCE_FILE 	= "ve_source_file";
	public static final String VE_DATASET 		= "ve_dataset";
	public static final String VE_DOI 			= "ve_doi";
	public static final String VE_DEFINITION 	= "ve_definition"; 
	
	public static final String VE_QUEST_NAME	= "ve_quest_name";
	public static final String VE_QUEST_INT		= "ve_quest_int";
	public static final String VE_QUEST_DEF		= "ve_quest_def";
	
	public static final String VE_REUSE		= "ve_reuse";
	
	public static final String EMPTY		= "";
	public static final String PROTO_D_V	= "XXXXXXXXXXXXXXX";
	
	private static final int NAME_TF_LG		= 15;
	private static final int LABEL_TF_LG	= 15;
	
	private static final String VALUE_ID			= "Value_ID";
	@SuppressWarnings("unused")
	private static final int	VALUE_ID_COLUMN		= 0;
	private static final String OBJECT				= "Object";
	private static final int	OBJECT_COLUMN		= 1;
	private static final String VALUE				= "value_col";
	private static final int	VALUE_COLUMN		= 2;
	private static final String LABEL				= "label_col";
	private static final int 	LABEL_COLUMN		= 3;
	private static final String IS_MISSING			= "is_missing_col";
	private static final int	IS_MISSING_COLUMN	= 4;
	private static final String LINK				= "link";
	private static final int	LINK_COLUMN			= 5;
	
	private static final int 	VIEWPORT_WIDTH		= 600;
	private static final int 	VIEWPORT_HEIGHT		=  94;							
	private static final int 	ROW_HEIGHT			=  28;
	
	private String 				ADD					= "ve_add_btn";
	
	/*
	 *	Fields
	 */
	Locale					currentLocale;
	ResourceBundle			resourceBundle;
	Font					currentFont;
	
	JPanel 					formular;
	
	JTabbedPane				tabbedPane;	
	
	@SuppressWarnings("unused")
	private JPanel			namingPnl;
	private JLabel			nameLbl;
	private JTextField		nameTF;
	private JLabel			labelLbl;
	private JTextField		labelTF;
	
	private JPanel			metadataSep;
	private JPanel			metadataPnl;
	private JLabel			typeLbl;
	private JComboBox		typeCB;
	private JLabel			levelLbl;
	private JComboBox		levelCB;
	private JLabel			measureLbl;
	private JComboBox		measureCB;
	
	private JPanel			tablePnl;
	private JTable			inputTbl;
	private DefaultTableModel	inputTblModel;
	
	private JPanel			buttonPnl;
	
	private JPanel			tableButtonPnl;
	private JButton			addBtn;
	private JButton			commentBtn;
	private JButton			definitionBtn; 
	private JButton			datasetBtn; 
	private JLabel			emptyLbl; 
	
	
	private JPanel			questionPnl;
	private JLabel			questTextLbl;
	private JScrollPane		questTextScrollPnl;
	private JTextArea		questTextTA;
	private JLabel			questInstLbl;
	private JScrollPane		questInstScrollPnl;
	private JTextArea		questInstTA;	
	private JLabel			questNameLbl;
	private JTextField		questNameTF;
	private JLabel			questIntLbl;
	private JScrollPane		questIntScrollPnl;
	private JTextArea		questIntTA;
	private JLabel			questDefLbl;
	private JScrollPane		questDefScrollPnl;
	private JTextArea		questDefTA;
	
	
	private JPanel			studyPnl;
	private JPanel			studyEditorPnl;
	private JLabel			studyTitleLbl;
	private JTextField		studyTitleTF;
	private JLabel			studyDOILbl;
	private JTextField		studyDOITF;
	private JLabel			studyAreaLbl;
	private JScrollPane		studyAreaScrollPnl;
	private JTextArea		studyAreaTA;
	private JLabel			studyDateOfCollLbl;
	private JTextField		studyDateOfCollTF;
	private JLabel			studyPopulationLbl;
	private JScrollPane		studyPopulationScrollPnl;
	private JTextArea		studyPopulationTA;
	private JLabel			studySelectionLbl;
	private JScrollPane		studySelectionScrollPnl;
	private JTextArea		studySelectionTA;
	private JLabel			studyCollMethLbl;
	private JScrollPane		studyCollMethScrollPnl;
	private JTextArea		studyCollMethTA;
	private JLabel			studyCollectorsLbl;
	private JScrollPane		studyCollectorsScrollPnl;
	private JTextArea		studyCollectorsTA;
	private JLabel			studySourceFileLbl;
	private JTextField		studySourceFileTF;
	private JLabel			studyDatasetLbl;
	private JTextField		studyDatasetTF;
	private JLabel			studyDefLbl;
	private JScrollPane		studyDefScrollPnl;
	private JTextArea		studyDefTA;
	
	private JPanel			studyReusePnl;
	private JCheckBox		studyReuse;
	
	
	private JPanel			windowButtonSep;
	private JPanel			windowButtonPnl;
	@SuppressWarnings("unused")
	private JButton			saveBtn;
	private JButton			resetBtn;
	
	private Vector<VariableType>		typeLabels;
	private Vector<TypeOfData>			levelLabels;
	private Vector<MeasurementLevel>	measureLabels;
	
	JButton			callHelpBtn;
	
	Variable 					variable;
	Variable 					var_clone; 
	Question					que_clone; 
	Study						std_clone; 
	Comment 					comment;
	Definition					definition;
	List<CharacteristicLink> 	links;
	List<CharacteristicLink> 	lnk_clone; 
	
	final ActionListener fal;

	/*
	 *	Constructor
	 */
	/**
	 * @param var
	 * @param links
	 * @param l
	 * @param f
	 */
	public VariableEditorPanel (Variable var, List<CharacteristicLink> links, Locale l, Font f, ActionListener al) {
		currentLocale	= l;
		resourceBundle	= ResourceBundle.getBundle(BUNDLE, currentLocale);
		currentFont		= f;
		
		this.variable	= var;
		this.links		= links;
		
		fal = al;
		
		if (variable.getType().equals(VariableType.NONE))
			variable.setType(VariableType.ORIGINAL);
		if (variable.getLevel().equals(TypeOfData.NONE))
			variable.setLevel(TypeOfData.INDIVIDUAL);
		if (variable.getMeasureType().equals(MeasurementLevel.NONE))
			variable.setMeasureType(MeasurementLevel.NOMINAL);

		var_clone = new Variable();
		var_clone.setName(variable.getName());
		var_clone.setLabel(variable.getLabel());
		var_clone.setType(variable.getType());
		var_clone.setLevel(variable.getLevel());
		var_clone.setMeasureType(variable.getMeasureType());
		
		lnk_clone = new ArrayList<CharacteristicLink>();
		Iterator<CharacteristicLink> it =  links.iterator();
		while (it.hasNext()) {
			CharacteristicLink link = it.next();
			
			if (link.getAttribute().equals(variable)) {
				if (link.getCharacteristic() instanceof Value) {
					Value val = (Value)link.getCharacteristic();
					
					Value cval = new Value();
					cval.setEntityID(val.getEntityID());
					cval.setValue(val.getValue());
					cval.setLabel(val.getLabel());
					cval.setMissing(val.isMissing());
					
					CharacteristicLink clink = new CharacteristicLink();
					clink.setEntityID(link.getEntityID());
					clink.setAttribute(variable);
					clink.setCharacteristic(cval);
					
					lnk_clone.add(clink);
				}
			}
		}
		
		que_clone = new Question();
		if (variable.getSource() instanceof Question) {
			Question question = variable.getSource();
			
			que_clone.setText(question.getText());
			que_clone.setInstruction(question.getInstruction());
			que_clone.setName(question.getName());
			que_clone.setIntent(question.getIntent());
			que_clone.setDefinition(question.getDefinition());
		}
		
		std_clone = new Study(); 
		if (variable.getSource() instanceof Question) {
			Question question = variable.getSource();
			
			if (question.getSource() instanceof Study) {
				Study study = question.getSource();
				
				std_clone.setTitle(study.getTitle());
				std_clone.setDOI(study.getDOI());
				std_clone.setStudyArea(study.getStudyArea());
				std_clone.setDateOfCollection(study.getDateOfCollection());
				std_clone.setPopulation(study.getPopulation());
				std_clone.setSelection(study.getSelection());
				std_clone.setCollectionMethod(study.getCollectionMethod());
				std_clone.setCollectors(study.getCollectors());
				std_clone.setSourceFile(study.getSourceFile());
				std_clone.setDataset(study.getDataset());
				std_clone.getDefinition().setText(study.getDefinition().getText());
			}
		}
		
		buildLayout(fal);
	}
	
	/*
	 *	Methods
	 */
	/**
	 * 
	 */
	private void buildLayout(ActionListener al) {		
		GridLayout		gridLayout_v	= new GridLayout(0,5);
		GridBagLayout	gridBagLayout_q	= new GridBagLayout();
		GridBagLayout	gridBagLayout_s = new GridBagLayout();
		
		/* Name */
		nameLbl = new JLabel(resourceBundle.getString(VE_NAME));
		nameLbl.setFont(currentFont);
		
		nameTF	= new JTextField(NAME_TF_LG);
		nameTF.setFont(currentFont);
		nameTF.addFocusListener(this);
		
		/* Abbr. */
		labelLbl = new JLabel(resourceBundle.getString(VE_LABEL));
		labelLbl.setFont(currentFont);
		
		labelTF	= new JTextField(LABEL_TF_LG);
		labelTF.setFont(currentFont);
		labelTF.addFocusListener(this);
						
		/* Type */
		typeLbl = new JLabel(resourceBundle.getString(VE_TYPE));
		typeLbl.setFont(currentFont);
		
		typeLabels = new Vector<VariableType>();
		for( VariableType t: VariableType.values() ) {
			t.setLocale(currentLocale);
			
			typeLabels.add(t);
		}
		
		typeCB = new JComboBox(typeLabels);
		typeCB.setFont(currentFont);
		typeCB.addFocusListener(this);
				
		/* Level */
		levelLbl = new JLabel(resourceBundle.getString(VE_LEVEL));
		levelLbl.setFont(currentFont);
		
		levelLabels = new Vector<TypeOfData>();
		for( TypeOfData l: TypeOfData.values() ) {
			l.setLocale(currentLocale);
			
			levelLabels.add(l);
		}
		
		levelCB = new JComboBox(levelLabels);
		levelCB.setFont(currentFont);
		levelCB.addFocusListener(this);
				
		/* Source */
		measureLbl = new JLabel(resourceBundle.getString(VE_MEASURE));
		measureLbl.setFont(currentFont);
		
		measureLabels = new Vector<MeasurementLevel>();
		for( MeasurementLevel s: MeasurementLevel.values() ) {
			s.setLocale(currentLocale);
			
			measureLabels.add(s);
		}
		
		measureCB = new JComboBox(measureLabels);
		measureCB.setFont(currentFont);
		measureCB.addFocusListener(this);
								
		/* Build Metadata Panel */
		JPanel metadataContentPnl = new JPanel();
		metadataContentPnl.setLayout(gridLayout_v);
		
		metadataContentPnl.add(nameLbl);
		metadataContentPnl.add(labelLbl);
		metadataContentPnl.add(typeLbl);
		metadataContentPnl.add(levelLbl);
		metadataContentPnl.add(measureLbl);
		metadataContentPnl.add(nameTF);
		metadataContentPnl.add(labelTF);
		metadataContentPnl.add(typeCB);
		metadataContentPnl.add(levelCB);
		metadataContentPnl.add(measureCB);
		
		metadataSep = new JPanel();
		metadataSep.setLayout(new BoxLayout(metadataSep, BoxLayout.Y_AXIS));
		
		metadataPnl = new JPanel();
		metadataPnl.setLayout(new FlowLayout(FlowLayout.LEFT));
		metadataPnl.add(metadataContentPnl);
		
		metadataSep.add(metadataPnl);
		metadataSep.add(Box.createVerticalStrut(5));
		metadataSep.add(new JSeparator(SwingConstants.HORIZONTAL));
		metadataSep.add(Box.createVerticalStrut(5));
		
		/* Buttons: */
//		saveBtn = new JButton(resourceBundle.getString(VE_SAVE));
//		saveBtn.setFont(currentFont);
//		saveBtn.addActionListener( 
//				new ActionListener() {
//					public void actionPerformed(ActionEvent e) {
//						saveBtnAction(e);
//					}
//				}
//		);
		
		resetBtn = new JButton(resourceBundle.getString(VE_RESET));
		resetBtn.setFont(currentFont);
		resetBtn.addActionListener( 
				new ActionListener() {
					/* (non-Javadoc)
					 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
					 */
					public void actionPerformed(ActionEvent e) {
						resetBtnAction(e);
					}
				}
		);
		
		commentBtn = new JButton(resourceBundle.getString(VE_COMMENT));
		commentBtn.setFont(currentFont);
		commentBtn.addActionListener( 
				new ActionListener() {
					/* (non-Javadoc)
					 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
					 */
					public void actionPerformed(ActionEvent e) {
						addEditorComment(variable);
					}
				}
		);
		
		emptyLbl = new JLabel("        ");
		emptyLbl.setFont(currentFont);
		
		definitionBtn = new JButton(resourceBundle.getString(VE_DEFINITION));
		definitionBtn.setFont(currentFont);
		definitionBtn.addActionListener( 
				new ActionListener() {
					/* (non-Javadoc)
					 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
					 */
					public void actionPerformed(ActionEvent e) {
						addEditorDefinition(variable);
					}
				}
		);
		
		datasetBtn = new JButton(resourceBundle.getString(VE_DATASET));
		datasetBtn.setFont(currentFont);
		datasetBtn.addActionListener( 
				new ActionListener() {
					/* (non-Javadoc)
					 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
					 */
					public void actionPerformed(ActionEvent e) {
						addEditorDataset(variable);
					}
				}
		);
		
		/* buttonPnl */
		windowButtonSep = new JPanel();
		windowButtonSep.setLayout(new BoxLayout(windowButtonSep, BoxLayout.Y_AXIS));
		windowButtonSep.add(Box.createVerticalStrut(5));
		windowButtonSep.add(new JSeparator(SwingConstants.HORIZONTAL));
		
		windowButtonSep.add(Box.createVerticalStrut(5));
		windowButtonPnl = new JPanel();
		windowButtonPnl.setLayout(new FlowLayout(FlowLayout.RIGHT));
//		windowButtonPnl.add(saveBtn);
//		windowButtonPnl.add(resetBtn);
		windowButtonPnl.add(commentBtn);
		windowButtonPnl.add(definitionBtn);
		windowButtonPnl.add(datasetBtn);
		windowButtonPnl.add(emptyLbl);
		windowButtonPnl.add(resetBtn);
		
		windowButtonSep.add(windowButtonPnl);
		
		tablePnl	= buildTablePanel();
		
		addBtn		= new JButton(resourceBundle.getString(ADD));
		addBtn.addActionListener( 
				new ActionListener() {
					/* (non-Javadoc)
					 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
					 */
					public void actionPerformed(ActionEvent e) {
						addBtnAction(e);
					}
				}
		);
		
		tableButtonPnl	= new JPanel(new FlowLayout(FlowLayout.TRAILING));
		tableButtonPnl.add(addBtn);

		buttonPnl = new JPanel(new BorderLayout());
		buttonPnl.add(tableButtonPnl, BorderLayout.NORTH);
		buttonPnl.add(windowButtonSep, BorderLayout.SOUTH);

		/* this */
		formular = new JPanel();
		formular.setLayout(new BorderLayout());
		formular.add(metadataSep, BorderLayout.NORTH);
		formular.add(tablePnl, BorderLayout.CENTER);
		formular.add(buttonPnl, BorderLayout.SOUTH);
		
		callHelpBtn = new JButton();
		callHelpBtn.setActionCommand(ActionCommandText.CMD_HELP_HELP_EDIT_VARIABLE);
		callHelpBtn.addActionListener(al);
		callHelpBtn.setVisible(false);
		callHelpBtn.setEnabled(true);
		
		/*
		 *	Question
		 */
		questionPnl = new JPanel();
		questionPnl.setLayout(gridBagLayout_q);
		
		questTextLbl = new JLabel(resourceBundle.getString(VE_QUEST_TEXT));
		questTextLbl.setFont(currentFont);
		
		questTextTA	= new JTextArea(5, 60);
		questTextTA.setFont(currentFont);
		questTextTA.addFocusListener(this);
		questTextTA.setLineWrap(true);
		questTextTA.setWrapStyleWord(true);
		
		questTextScrollPnl = new JScrollPane(questTextTA);
		questTextScrollPnl.getVerticalScrollBar().setUnitIncrement(16);
		
		questInstLbl = new JLabel(resourceBundle.getString(VE_QUEST_INST));
		questInstLbl.setFont(currentFont);
		
		questInstTA	= new JTextArea(5, 60);
		questInstTA.setFont(currentFont);
		questInstTA.addFocusListener(this);
		questInstTA.setLineWrap(true);
		questInstTA.setWrapStyleWord(true);
		
		questInstScrollPnl = new JScrollPane(questInstTA);
		questInstScrollPnl.getVerticalScrollBar().setUnitIncrement(16);

		questNameLbl = new JLabel(resourceBundle.getString(VE_QUEST_NAME));
		questNameLbl.setFont(currentFont);
		
		questNameTF = new JTextField(60);
		questNameTF.setFont(currentFont);
		
		questIntLbl = new JLabel(resourceBundle.getString(VE_QUEST_INT));
		questIntLbl.setFont(currentFont);
		
		questIntTA	= new JTextArea(5, 60);
		questIntTA.setFont(currentFont);
		questIntTA.addFocusListener(this);
		questIntTA.setLineWrap(true);
		questIntTA.setWrapStyleWord(true);
		
		questIntScrollPnl = new JScrollPane(questIntTA);
		questIntScrollPnl.getVerticalScrollBar().setUnitIncrement(16);
		
		questDefLbl = new JLabel(resourceBundle.getString(VE_QUEST_DEF));
		questDefLbl.setFont(currentFont);
		
		questDefTA	= new JTextArea(5, 60);
		questDefTA.setFont(currentFont);
		questDefTA.addFocusListener(this);
		questDefTA.setLineWrap(true);
		questDefTA.setWrapStyleWord(true);
		
		questDefScrollPnl = new JScrollPane(questDefTA);
		questDefScrollPnl.getVerticalScrollBar().setUnitIncrement(16);
		
		JLabel[] questLabels = {
				questNameLbl, 
				questTextLbl, 
				questInstLbl,
				questIntLbl, 
				questDefLbl 
				};
		
		Component[] questControls = {
				questNameTF, 
				questTextScrollPnl, 
				questInstScrollPnl,
				questIntScrollPnl, 
				questDefScrollPnl 
				};
		
		addLabelTextControlAsRows(questLabels, questControls, gridBagLayout_q, questionPnl);
		
		/*
		 *	Study
		 */
		studyPnl = new JPanel();
//		studyPnl.setLayout(gridBagLayout_s);
		studyPnl.setLayout(new BorderLayout());
		
		studyEditorPnl = new JPanel();
		studyEditorPnl.setLayout(gridBagLayout_s);
		
		studyTitleLbl = new JLabel(resourceBundle.getString(VE_STUDY_TITLE));
		studyTitleLbl.setFont(currentFont);
		
		studyTitleTF = new JTextField();
		studyTitleTF.setFont(currentFont);
		studyTitleTF.addFocusListener(this);
		
		studyDOILbl = new JLabel(resourceBundle.getString(VE_DOI));
		studyDOILbl.setFont(currentFont);
		
		studyDOITF = new JTextField();
		studyDOITF.setFont(currentFont);
		studyDOITF.addFocusListener(this);
		
		studyAreaLbl = new JLabel(resourceBundle.getString(VE_STUDY_AREA));
		studyAreaLbl.setFont(currentFont);
		
		studyAreaTA = new JTextArea(3, 60);
		studyAreaTA.setFont(currentFont);
		studyAreaTA.addFocusListener(this);
		studyAreaTA.setLineWrap(true);
		studyAreaTA.setWrapStyleWord(true);
		
		studyAreaScrollPnl = new JScrollPane(studyAreaTA);
		studyAreaScrollPnl.getVerticalScrollBar().setUnitIncrement(16);
		
		studyDateOfCollLbl = new JLabel(resourceBundle.getString(VE_DATE_COLL));
		studyDateOfCollLbl.setFont(currentFont);
		
		studyDateOfCollTF = new JTextField();
		studyDateOfCollTF.setFont(currentFont);
		studyDateOfCollTF.addFocusListener(this);
		
		studyPopulationLbl = new JLabel(resourceBundle.getString(VE_POPULATION));
		studyPopulationLbl.setFont(currentFont);
		
		studyPopulationTA = new JTextArea(3, 60);
		studyPopulationTA.setFont(currentFont);
		studyPopulationTA.addFocusListener(this);
		studyPopulationTA.setLineWrap(true);
		studyPopulationTA.setWrapStyleWord(true);
		
		studyPopulationScrollPnl = new JScrollPane(studyPopulationTA);
		studyPopulationScrollPnl.getVerticalScrollBar().setUnitIncrement(16);
		
		studySelectionLbl = new JLabel(resourceBundle.getString(VE_SELECTION));
		studySelectionLbl.setFont(currentFont);
		
		studySelectionTA = new JTextArea(3, 60);
		studySelectionTA.setFont(currentFont);
		studySelectionTA.addFocusListener(this);
		studySelectionTA.setLineWrap(true);
		studySelectionTA.setWrapStyleWord(true);
		
		studySelectionScrollPnl = new JScrollPane(studySelectionTA);
		studySelectionScrollPnl.getVerticalScrollBar().setUnitIncrement(16);
		
		studyCollMethLbl = new JLabel(resourceBundle.getString(VE_COLL_METH));
		studyCollMethLbl.setFont(currentFont);
		
		studyCollMethTA = new JTextArea(3, 60);
		studyCollMethTA.setFont(currentFont);
		studyCollMethTA.addFocusListener(this);
		studyCollMethTA.setLineWrap(true);
		studyCollMethTA.setWrapStyleWord(true);
		
		studyCollMethScrollPnl = new JScrollPane(studyCollMethTA);
		studyCollMethScrollPnl.getVerticalScrollBar().setUnitIncrement(16);
		
		studyCollectorsLbl = new JLabel(resourceBundle.getString(VE_COLLECTORS));
		studyCollectorsLbl.setFont(currentFont);
		
		studyCollectorsTA = new JTextArea(3, 60);
		studyCollectorsTA.setFont(currentFont);
		studyCollectorsTA.addFocusListener(this);
		studyCollectorsTA.setLineWrap(true);
		studyCollectorsTA.setWrapStyleWord(true);
		
		studySourceFileLbl = new JLabel(resourceBundle.getString(VE_SOURCE_FILE));
		studySourceFileLbl.setFont(currentFont);
		
		studySourceFileTF = new JTextField();
		studySourceFileTF.setFont(currentFont);
		studySourceFileTF.addFocusListener(this);
		
		studyDatasetLbl = new JLabel(resourceBundle.getString(VE_DATASET));
		studyDatasetLbl.setFont(currentFont);
		
		studyDatasetTF = new JTextField();
		studyDatasetTF.setFont(currentFont);
		studyDatasetTF.addFocusListener(this);
		
		studyCollectorsScrollPnl = new JScrollPane(studyCollectorsTA);
		studyCollectorsScrollPnl.getVerticalScrollBar().setUnitIncrement(16);

		studyDefLbl = new JLabel(resourceBundle.getString(VE_QUEST_DEF));
		studyDefLbl.setFont(currentFont);
		
		studyDefTA	= new JTextArea(5, 60);
		studyDefTA.setFont(currentFont);
		studyDefTA.addFocusListener(this);
		studyDefTA.setLineWrap(true);
		studyDefTA.setWrapStyleWord(true);
		
		studyDefScrollPnl = new JScrollPane(studyDefTA);
		studyDefScrollPnl.getVerticalScrollBar().setUnitIncrement(16);
		
		FlowLayout reusePanelLayout = new FlowLayout();
		reusePanelLayout.setAlignment(FlowLayout.RIGHT);
		studyReusePnl = new JPanel(reusePanelLayout);
		
		
		studyReuse = new JCheckBox(resourceBundle.getString(VE_REUSE));
		studyReuse.setFont(currentFont);
		
		studyReusePnl.add(studyReuse);
		
		JLabel[] studyLabels = {
				studyTitleLbl,
				studyDOILbl,
				studyAreaLbl,
				studyDateOfCollLbl, 
				studyPopulationLbl, 
				studySelectionLbl, 
				studyCollMethLbl, 
				studyCollectorsLbl,
				studySourceFileLbl,
				studyDatasetLbl,
				studyDefLbl}; 
		
		Component[] studyControls = {
				studyTitleTF,
				studyDOITF,
				studyAreaScrollPnl,
				studyDateOfCollTF, 
				studyPopulationScrollPnl, 
				studySelectionScrollPnl, 
				studyCollMethScrollPnl, 
				studyCollectorsScrollPnl,
				studySourceFileTF,
				studyDatasetTF,
				studyDefScrollPnl}; 
		
		addLabelTextControlAsRows(studyLabels, studyControls, gridBagLayout_s, studyEditorPnl);
		
		studyPnl.add(studyEditorPnl, BorderLayout.CENTER);
		studyPnl.add(studyReusePnl, BorderLayout.SOUTH);
		
		/*
		 *	Put together:
		 */
		tabbedPane = new JTabbedPane();
		tabbedPane.setFont(currentFont);
		tabbedPane.addTab(resourceBundle.getString(VE_VARIABLE), null, formular, EMPTY);
		tabbedPane.addTab(resourceBundle.getString(VE_QUESTION), null, questionPnl, EMPTY);
		tabbedPane.addTab(resourceBundle.getString(VE_STUDY), null, studyPnl, EMPTY);
		
		fillModel();
	}
	
	/**
	 * @return
	 */
	JPanel buildTablePanel() {
		JPanel panel;
				
		inputTblModel		= buildTableModel();
		inputTbl			= buildTable(inputTblModel);
		inputTbl.getColumnModel().getColumn(VALUE_COLUMN).setIdentifier(VALUE);
		inputTbl.getColumnModel().getColumn(LABEL_COLUMN).setIdentifier(LABEL);
		inputTbl.getColumnModel().getColumn(IS_MISSING_COLUMN).setIdentifier(IS_MISSING);
		inputTbl.removeColumn(inputTbl.getColumn(VALUE_ID));
		inputTbl.removeColumn(inputTbl.getColumn(OBJECT)); 
		inputTbl.removeColumn(inputTbl.getColumn(LINK)); 
		@SuppressWarnings("unused")
		ExcelAdapter myAd	= new ExcelAdapter(inputTbl);
		panel				= new JPanel(new BorderLayout());
		JScrollPane scrollPane = new JScrollPane(inputTbl);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setMinimumSize(new Dimension(VIEWPORT_WIDTH, VIEWPORT_HEIGHT*3)); 
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		panel.add(
			scrollPane, BorderLayout.CENTER
		);
		
		return panel;
	}
	
	/**
	 * @return
	 */
	DefaultTableModel buildTableModel() {
		
		DefaultTableModel tableModel = new DefaultTableModel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			/* (non-Javadoc)
			 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
			 */
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int column) {
				Class returnValue;
				if (this.getRowCount() > 0)
					if ((column >= 0) && (column < getColumnCount())) {
						if (getValueAt(0, column) != null) 
							returnValue = getValueAt(0, column).getClass();
						else 
							returnValue = Object.class; 
	                } else {
	                	returnValue = Object.class;
	                }
				else
                	returnValue = Object.class;
				
                return returnValue;
			}
		};
            
		tableModel.addColumn(VALUE_ID);
		tableModel.addColumn(OBJECT); /* NEW */
		tableModel.addColumn(resourceBundle.getString(VALUE));		
		tableModel.addColumn(resourceBundle.getString(LABEL));
		tableModel.addColumn(resourceBundle.getString(IS_MISSING));
		tableModel.addColumn(LINK); /* NEW */
        
        return tableModel;
	}
	
	/**
	 * @param tableModel
	 * @return
	 */
	JTable buildTable(DefaultTableModel tableModel) {
		
		JTable table = new JTable(tableModel) {
           	/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			/* (non-Javadoc)
			 * @see javax.swing.JTable#getCellRenderer(int, int)
			 */
			@SuppressWarnings("rawtypes")
			public TableCellRenderer getCellRenderer(int row, int column) {
				TableColumn tableColumn = getColumnModel().getColumn(column);
				TableCellRenderer renderer = tableColumn.getCellRenderer();
				if (renderer == null) {
					Class c = getColumnClass(column);
					if( c.equals(Object.class) )
					{
						Object o = getValueAt(row,column);
						if( o != null )
							c = getValueAt(row,column).getClass();
					}
					renderer = getDefaultRenderer(c);
				}
				return renderer;
			}
			
			/* (non-Javadoc)
			 * @see javax.swing.JTable#getCellEditor(int, int)
			 */
			@SuppressWarnings("rawtypes")
			public TableCellEditor getCellEditor(int row, int column) {
				TableColumn tableColumn = getColumnModel().getColumn(column);
				TableCellEditor editor = tableColumn.getCellEditor();
				if (editor == null) {
					Class c = getColumnClass(column);
					if( c.equals(Object.class) )
					{
						Object o = getValueAt(row,column);
						if( o != null )
							c = getValueAt(row,column).getClass();
					}
					editor = getDefaultEditor(c);
				}
				return editor;
			}
			
			/* (non-Javadoc)
			 * @see javax.swing.JTable#prepareRenderer(javax.swing.table.TableCellRenderer, int, int)
			 */
			public Component prepareRenderer(TableCellRenderer renderer,int row, int col) {
				Component component = super.prepareRenderer(renderer, row, col);
		        JComponent jComponent = (JComponent)component;
		        if (component == jComponent) {
		        	if (this.getValueAt(row, col) != null)
		        		jComponent.setToolTipText((String)this.getValueAt(row, col).toString());
		        	
		        	if (isRowSelected(row))
		        		jComponent.setBackground(Color.yellow);		        	
		        	else
		        		jComponent.setBackground(Color.white);
		        	jComponent.setForeground(Color.black);
		        }
		        return component;
			}
        };
       
		table.setPreferredScrollableViewportSize(new Dimension(VIEWPORT_WIDTH, VIEWPORT_HEIGHT));
		table.setRowHeight(ROW_HEIGHT);
        
        table.setDefaultRenderer(JComponent.class, new JComponentCellRenderer());
		table.setDefaultEditor(JComponent.class, new JComponentCellEditor());
	    
		table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		
        return table;
	}
	
	class JComponentCellRenderer implements TableCellRenderer {
		
	    public Component getTableCellRendererComponent(JTable table, Object value,
	    		boolean isSelected, boolean hasFocus, int row, int column) {
	        return (JComponent)value;
	    }
	}
		
	/**
	 * @param link
	 * @param val
	 */
	public void addValueRow(CharacteristicLink link, Value val) {
		this.inputTblModel.addRow(
		new Object[]{
			val.getEntityID(),
			val,
			val.getValue(),
			val.getLabel(),
			val.isMissing(),
			link
		});
			
	if (!inputTbl.getAutoCreateRowSorter())
		inputTbl.setAutoCreateRowSorter(true);			
	}
	
	/**
	 * @param e
	 */
	protected void addBtnAction(ActionEvent e) {
		this.addValueRow(null, new Value()); 
	}
	
	/**
	 * 
	 */
	private void saveChanges() {
		/* Variable */
		variable.setName(nameTF.getText());
		variable.setLabel(labelTF.getText());
		variable.setType((VariableType)typeCB.getSelectedItem());
		variable.setLevel((TypeOfData)levelCB.getSelectedItem());
		variable.setMeasureType((MeasurementLevel)measureCB.getSelectedItem());
		variable.setComment(comment);
		
		/* Value */
		int rowCount = inputTblModel.getRowCount();
		for (int i=0; i<rowCount; i++) {
			if (inputTblModel.getValueAt(i, OBJECT_COLUMN) instanceof Value) {
				Value val = (Value)inputTblModel.getValueAt(i, OBJECT_COLUMN);
				
				val.setValue((String)inputTblModel.getValueAt(i, VALUE_COLUMN));
				val.setLabel((String)inputTblModel.getValueAt(i, LABEL_COLUMN));
				val.setMissing((Boolean)inputTblModel.getValueAt(i, IS_MISSING_COLUMN));
				
				Object obj = inputTblModel.getValueAt(i, LINK_COLUMN);
				if (!(obj instanceof CharacteristicLink)) {
					CharacteristicLink link = new CharacteristicLink();
					link.setAttribute(variable);
					link.setCharacteristic(val);
					link.setType(CharacteristicLinkType.VALUE);
					
					links.add(link);
				}
			}
		}
		
		/* Question */
		if (variable.getSource() instanceof Question) {
			variable.getSource().setText(questTextTA.getText());
			variable.getSource().setInstruction(questInstTA.getText());

			variable.getSource().setName(questNameTF.getText());
			variable.getSource().setIntent(questIntTA.getText());
			variable.getSource().getDefinition().setText(questDefTA.getText());

		}
		
		/* Study */
		if (variable.getSource() instanceof Question) {
			Question question = variable.getSource();
			
			if (question.getSource() instanceof Study) {
				Study study = question.getSource();
				
				study.setTitle(studyTitleTF.getText());
				study.setDOI(studyDOITF.getText());
				study.setStudyArea(studyAreaTA.getText());
				study.setDateOfCollection(studyDateOfCollTF.getText());
				study.setPopulation(studyPopulationTA.getText());
				study.setSelection(studySelectionTA.getText());
				study.setCollectionMethod(studyCollMethTA.getText());
				study.setCollectors(studyCollectorsTA.getText());
				study.setSourceFile(studySourceFileTF.getText());
				study.setDataset(studyDatasetTF.getText());
				study.getDefinition().setText(studyDefTA.getText());
			}
		}
	}
	
	/**
	 * 
	 */
	private void fillModel() {
		/* Variable */
		nameTF.setText(variable.getName());
		labelTF.setText(variable.getLabel());
		typeCB.setSelectedItem(variable.getType());
		levelCB.setSelectedItem(variable.getLevel());
		measureCB.setSelectedItem(variable.getMeasureType());
		
		/* Value */
		int rowCount = inputTblModel.getRowCount();
		for (int i=rowCount-1; i>=0; i--)
			inputTblModel.removeRow(i);
		
		Iterator<CharacteristicLink> it =  links.iterator();
		while (it.hasNext()) {
			CharacteristicLink link = it.next();
			
			if (link.getAttribute().equals(variable)) {
				if (link.getCharacteristic() instanceof Value) {
					Value val = (Value)link.getCharacteristic();
					
					this.addValueRow(link, val);
				}
			}
		}
		
		/* Question */
		if (variable.getSource() instanceof Question) {
			Question question = variable.getSource();
			
			questTextTA.setText(question.getText());
			questInstTA.setText(question.getInstruction());
			questNameTF.setText(question.getName());
			questIntTA.setText(question.getIntent());
			questDefTA.setText(question.getDefinition().getText());
			
		}

		/* Study */
		if (variable.getSource() instanceof Question) {
			Question question = variable.getSource();
			
			if (question.getSource() instanceof Study) {
				Study study = question.getSource();
				
				studyTitleTF.setText(study.getTitle());
				studyDOITF.setText(study.getDOI());
				studyAreaTA.setText(study.getStudyArea());
				studyDateOfCollTF.setText(study.getDateOfCollection());
				studyPopulationTA.setText(study.getPopulation());
				studySelectionTA.setText(study.getSelection());
				studyCollMethTA.setText(study.getCollectionMethod());
				studyCollectorsTA.setText(study.getCollectors());
				studySourceFileTF.setText(study.getSourceFile());
				studyDatasetTF.setText(study.getDataset());
				studyDefTA.setText(study.getDefinition().getText());
			}
		}
	}
	
//	protected void saveBtnAction(ActionEvent e) {
//		saveChanges();
//	}
	
	/**
	 * @param e
	 */
	protected void resetBtnAction(ActionEvent e) {
		variable.setName(var_clone.getName());
		variable.setLabel(var_clone.getLabel());
		variable.setType(var_clone.getType());
		variable.setLevel(var_clone.getLevel());
		variable.setMeasureType(var_clone.getMeasureType());
		
		List<CharacteristicLink> rem_links = new ArrayList<CharacteristicLink>();
		Iterator<CharacteristicLink> it =  links.iterator();
		while (it.hasNext()) {
			CharacteristicLink link = it.next();
			
			CharacteristicLink clink = getCharacteristicLinkByID(link.getEntityID());
			if (clink != null) {
				if (link.getAttribute().equals(variable)) {
					if (link.getCharacteristic() instanceof Value) {
						Value val = (Value)link.getCharacteristic();
												
						val.setValue(((Value)clink.getCharacteristic()).getValue());
						val.setLabel(((Value)clink.getCharacteristic()).getLabel());
						val.setMissing(((Value)clink.getCharacteristic()).isMissing());
					}
				}				
			} else {
				rem_links.add(link);
			}
		}
		it =  rem_links.iterator();
		while (it.hasNext()) {
			CharacteristicLink link = it.next();
			
			links.remove(link);
		}
		
		if (variable.getSource() instanceof Question) {
			Question question = variable.getSource();
			
			question.setText(que_clone.getText());
			question.setInstruction(que_clone.getInstruction());
			question.setName(que_clone.getName());
			question.setIntent(que_clone.getIntent());
			question.setDefinition(que_clone.getDefinition());
		}
		
		if (variable.getSource() instanceof Question) {
			Question question = variable.getSource();
			
			if (question.getSource() instanceof Study) {
				Study study = question.getSource();
				
				study.setTitle(std_clone.getTitle());
				study.setDOI(std_clone.getDOI());
				study.setStudyArea(std_clone.getStudyArea());
				study.setDateOfCollection(std_clone.getDateOfCollection());
				study.setPopulation(std_clone.getPopulation());
				study.setSelection(std_clone.getSelection());
				study.setCollectionMethod(std_clone.getCollectionMethod());
				study.setCollectors(std_clone.getCollectors());
				study.setSourceFile(std_clone.getSourceFile());
				study.setDataset(std_clone.getDataset());
				study.getDefinition().setText(std_clone.getDefinition().getText());
			}
		}
		
		fillModel();
	}
	
	/**
	 * @return
	 */
	public boolean goEdit() {
		boolean isDefault = false;
		
		final String[] DIALOG_BUTTONS_TITLES	= {resourceBundle.getString(VE_SAVE), resourceBundle.getString(VE_ACCEPT), resourceBundle.getString(VE_HELP)};
		
		int retValue = 2; 
		while (retValue != 1) 
	   	{
	  		retValue = JOptionPane.showOptionDialog(
	  				((CStatsCtrl)fal).getView().getAppFrame(), // null, 
		   			tabbedPane,
		   			resourceBundle.getString(VE_TITLE),
		            JOptionPane.OK_OPTION,
		            JOptionPane.PLAIN_MESSAGE,
		            null, 
		            DIALOG_BUTTONS_TITLES, 
		            DIALOG_BUTTONS_TITLES[0]
		       	);
		    
	  		if (retValue == 0) {
	  			isDefault = studyReuse.isSelected();
	  			
	  			saveChanges();
	  		}
	  		
	  		/* 09.12.14: */
	  		if (retValue == 1) {
				int n = saveVariableDialog();
				
				switch (n) {
					case 0:
			  			isDefault = studyReuse.isSelected();			  			
			  			saveChanges();
					case 1:
						break;
				}
	  		}
	  		
	  		if (retValue == 2) {
	  			callHelpBtn.doClick();
	  		}
	   	}
	  	
	  	return isDefault;
	}
	
	public int saveVariableDialog() {
		Object[] options = {resourceBundle.getString("yes"),resourceBundle.getString("no")};
		
		return JOptionPane.showOptionDialog(((CStatsCtrl)fal).getView().getAppFrame(),
				resourceBundle.getString("save_var_quest"),
				resourceBundle.getString(VE_QUESTION), 
			    JOptionPane.YES_NO_OPTION,
			    JOptionPane.QUESTION_MESSAGE,
			    null,
			    options,
			    options[1]);
	}
	
	/**
	 * @param labels
	 * @param textControls
	 * @param gridBagLyout
	 * @param container
	 */
	private void addLabelTextControlAsRows(	JLabel[] labels,
			Component[] textControls,
			GridBagLayout gridBagLyout,
			Container container) {
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.EAST;
		
		int numberOfLabels = labels.length;
		
		for (int i = 0; i < numberOfLabels; i++) {
			c.gridwidth = GridBagConstraints.RELATIVE;
			c.fill = GridBagConstraints.NONE;
			c.weightx = 0.0;
			c.insets = new Insets(0, 0, 6, 0);
			container.add(labels[i], c);
			
			c.gridwidth = GridBagConstraints.REMAINDER;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 1.0;
			c.insets = new Insets(0, 0, 6, 0);
			container.add(textControls[i], c);
		}
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
	 */
	@Override
	public void focusGained(FocusEvent e) {
		if(e.getSource() instanceof JTextField) {
			((JTextField)e.getSource()).setBackground(Color.yellow);
		}
		if(e.getSource() instanceof JTextArea) {
			((JTextArea)e.getSource()).setBackground(Color.yellow);
		}
		if(e.getSource() instanceof JComboBox) {
			((JComboBox)e.getSource()).setBackground(Color.yellow);
		}
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
	 */
	@Override
	public void focusLost(FocusEvent e) {
		if(e.getSource() instanceof JTextField) {
			((JTextField)e.getSource()).setBackground(Color.white);
		}
		if(e.getSource() instanceof JTextArea) {
			((JTextArea)e.getSource()).setBackground(Color.white);
		}
		if(e.getSource() instanceof JComboBox) {
			((JComboBox)e.getSource()).setBackground(Color.white);
		}
	}
	
	/**
	 * @param var
	 */
	public void addEditorComment(Variable var) {
		String commentText = null;
		comment = var.getComment();
		
		String[] argValue = null;
		if (comment != null) {
			argValue = new String[2];
			argValue[0] = comment.getText();
			argValue[1] = comment.getSubject();
		}
		
		/*
		 *	 Open the pop-up window and wait for return value
		 */
		ResourceBundle resourceBundle	= ResourceBundle.getBundle(ERROR_BUNDLE, currentLocale);
		
		String dialogTitle = 
			(!(comment != null) ?  "add_comment" : "edit_comment");	
		Object o = new CommentDialog(((CStatsCtrl)fal).getView().getAppFrame(), resourceBundle.getString(dialogTitle), argValue, false, true, false, true, currentLocale, currentFont).getValue();
		
		/*
		 *	Handle Answer
		 */
		if (o != null) {
			commentText = ((String[])o)[0];
		}
		
		if (commentText != null) {
			if (comment != null) {
				comment.setText(commentText);
			} else {
				comment = new Comment();
				comment.setText(commentText);
				var.setComment(comment);
			}
		}
	}
	
	/**
	 * @param var
	 */
	public void addEditorDataset(Variable var) {
		String dataset	= var.getDataset();
		String pid		= var.getPID();
		String datadate	= var.getDataDate();
		
		String[] argValue = new String[3];
		argValue[0] = dataset;
		argValue[1] = pid;
		argValue[2] = datadate;	
		
		/*
		 *	 Open the pop-up window and wait for return value
		 */
		ResourceBundle resourceBundle	= ResourceBundle.getBundle(BUNDLE, currentLocale);
		
		Object o = new DatasetDialog(((CStatsCtrl)fal).getView().getAppFrame(), resourceBundle.getString(VE_DATASET), argValue, true, true, false, true, currentLocale, currentFont).getValue();
		
		/*
		 *	Handle Answer
		 */
		if (o != null) {
			var.setDataset(((String[])o)[0]);
			var.setPID(((String[])o)[1]);
			var.setDataDate(((String[])o)[2]);
		}
	}
	
	public void addEditorDefinition(Variable var) {
		String definitionText = null;
		definition = var.getDefinition();
		
		String argValue = "";
		if (definition != null) {			
			argValue = definition.getText();
			
		}
		
		/*
		 *	 Open the pop-up window and wait for return value
		 */
		ResourceBundle resourceBundle	= ResourceBundle.getBundle(ERROR_BUNDLE, currentLocale);
		
		String dialogTitle = 
			(!(definition != null) ?  "add_definition" : "edit_definition");	
		Object o = new DefinitionDialog(((CStatsCtrl)fal).getView().getAppFrame(), resourceBundle.getString(dialogTitle), argValue, true, false, true, currentLocale, currentFont).getValue();
		
		/*
		 *	Handle Answer
		 */
		if (o != null) {
			definitionText = (String)o;
		}
		
		if (definitionText != null) {
			if (definition != null) {
				definition.setText(definitionText);
			} else {
				definition = new Definition();
				definition.setText(definitionText);
				var.setDefinition(definition);
			}
		}
	}
	
	public CharacteristicLink getCharacteristicLinkByID(int id) {
		if (lnk_clone != null) {
			Iterator<CharacteristicLink> iterator = lnk_clone.iterator();
							
			while(iterator.hasNext()) {
				CharacteristicLink link = iterator.next();
					
				if (link.getEntityID() == id)
					return link;
			}
		}
	
		return null;
	}
}


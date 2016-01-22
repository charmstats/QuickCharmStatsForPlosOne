package org.gesis.charmstats.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.gesis.charmstats.ActionCommandText;
import org.gesis.charmstats.controller.CStatsCtrl;
import org.gesis.charmstats.model.AttributeLink;
import org.gesis.charmstats.model.AttributeMap;
import org.gesis.charmstats.model.CStatsModel;
import org.gesis.charmstats.model.Category;
import org.gesis.charmstats.model.CharacteristicLink;
import org.gesis.charmstats.model.CharacteristicMap;
import org.gesis.charmstats.model.Characteristics;
import org.gesis.charmstats.model.InstanceMap;
import org.gesis.charmstats.model.InstanceMapType;
import org.gesis.charmstats.model.ProContent;
import org.gesis.charmstats.model.Project;
import org.gesis.charmstats.model.Value;
import org.gesis.charmstats.model.Variable;

/**
 * 
 * @author	Martin Friedrichs
 * @since	0.6
 *
 */
public class ExportSyntaxDialog extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String BUNDLE	= "org.gesis.charmstats.resources.ExportSyntaxBundle";	
	public static final String ES_TITLE		= "es_title";
	public static final String ES_MAPPING	= "es_mapping";
	public static final String ES_VARIABLE	= "es_variable";
	public static final String ES_CREATE	= "es_create";
	public static final String ES_PRINT		= "es_print";
	public static final String ES_SAVE		= "es_save";
	public static final String ES_EDIT		= "es_edit";
	public static final String ES_ACCEPT	= "es_accept";
	public static final String ES_CANCEL	= "es_cancel";
	public static final String ES_HELP		= "es_help";
	public static final String ES_NO_HELP	= "es_no_help";
	public static final String ES_SPSS		= "es_spss";
	public static final String ES_STATA		= "es_stata";
	public static final String ES_SAS		= "es_sas";
	public static final String ES_MPLUS		= "es_mplus";
	public static final String ES_NAME		= "es_name";
	public static final String ES_LABEL		= "es_label";
	public static final String ES_NEW_VAR	= "es_new_var";
	
	private static final String BUNDLE_F	= "org.gesis.charmstats.resources.FrameBundle";
	private static final String FILE_NAME	= "file_name";
	
	public static final String EMPTY		= "";
	
	
	/*
	 *	Fields
	 */
	Locale 		  	currentLocale;
	ResourceBundle	resourceBundle;
	Font			currentFont;
	
	ArrayList<Characteristics[]> threeStepMapper = new ArrayList<Characteristics[]>();
	
	Project		project;
	ProContent	content;
	
	JPanel 			form					= new JPanel();
	JLabel			mappingSelectionLbl;
	@SuppressWarnings("rawtypes")
	JComboBox		mappingSelectionCB;
	JLabel			variableSelectionLbl;
	@SuppressWarnings("rawtypes")
	JComboBox		variableSelectionCB;
	JPanel			newVariablePanel		= new JPanel();
	JLabel			nameLbl;
	JTextField		nameTF					= new JTextField(18);
	JLabel			labelLbl;
	JTextField		labelTF					= new JTextField(24);
	JPanel			resultPanel				= new JPanel();
	JPanel			createPanel				= new JPanel();
	JButton			spssBtn;
	JButton			stataBtn;
	JButton			sasBtn;
	JButton			mplusBtn;
	JScrollPane		syntaxOutputScrollPane;
	JTextArea		syntaxOutput;
	JPanel			buttonPanel				= new JPanel();
	JPanel			sidePanel				= new JPanel();
	JButton			printBtn;
	JButton			saveBtn;
	JButton			editBtn;
	
	JButton			callHelpBtn;
	
	String			extensionType;
	
	int 			returnValue;
	
	final ActionListener fal;
	
	static MessageFormat head = new MessageFormat("");
    static MessageFormat foot = new MessageFormat("");
	
	/*
	 *	Constructor
	 */
	/**
	 * @param model
	 * @param locale
	 * @param f
	 */
	public ExportSyntaxDialog (CStatsModel model, Locale locale, Font f, ActionListener al) {
		currentLocale	= locale;
		resourceBundle	= ResourceBundle.getBundle(BUNDLE, currentLocale);
		currentFont		= f;

		fal				= al;
		
		buildLayout(model, fal);
	}

	/*
	 *	Methods
	 */
	/**
	 * @param locale
	 */
	public void changeLanguage(Locale locale) {
		currentLocale	= locale;
		resourceBundle	= ResourceBundle.getBundle(BUNDLE, currentLocale);
		
		/* TODO */		
	}
	
	/**
	 * @param model
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void buildLayout(CStatsModel model, ActionListener al) {
		project = model.getProject();
		content = project.getContent();
		
		/* InstanceMapping */
		mappingSelectionLbl = new JLabel(resourceBundle.getString(ES_MAPPING));
		mappingSelectionLbl.setFont(currentFont);
		Iterator<InstanceMap> instMapIterator = 
				content.getInstanceMapsByType(InstanceMapType.DATA_RECODING).iterator();		
		Vector<InstanceMap> instMapList = new Vector<InstanceMap>();
		while (instMapIterator.hasNext()) {
			instMapList.add(instMapIterator.next());
		}
		mappingSelectionCB = new JComboBox(instMapList);
		mappingSelectionCB.setFont(currentFont);
				
		/* Variable */
		variableSelectionLbl = new JLabel(resourceBundle.getString(ES_VARIABLE));
		variableSelectionLbl.setFont(currentFont);
		variableSelectionCB = new JComboBox();
		variableSelectionCB.setFont(currentFont);
		variableSelectionCB.setEnabled(false);
		
		mappingSelectionCB.addActionListener(new java.awt.event.ActionListener() {
			/* (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				if (((JComboBox)e.getSource()).getSelectedItem() instanceof InstanceMap) {
					InstanceMap selectedMapping = (InstanceMap)((JComboBox)e.getSource()).getSelectedItem();
					
					Iterator<AttributeMap> attrMapIterator = content.getMaps().iterator();
					variableSelectionCB.removeAllItems();
					while (attrMapIterator.hasNext()) {
						AttributeMap attributeMap = attrMapIterator.next();
						
						if ((attributeMap.getBelongsTo() instanceof InstanceMap) &&
								(attributeMap.getBelongsTo().getEntityID() == selectedMapping.getEntityID())) {
							variableSelectionCB.addItem(attributeMap);
						}
					}
					
					if (variableSelectionCB.getItemCount() > 0)
						variableSelectionCB.setSelectedIndex(0);
					
					syntaxOutput.setText(EMPTY);
					
					variableSelectionCB.setEnabled(true);
				}
			}
		});
						
		/* Build Selection Panel */
		GridBagLayout	gridBagLayout	= new GridBagLayout();
		JPanel			selectionPanel	= new JPanel(false);
		selectionPanel.setLayout(gridBagLayout);
		
		JLabel[]	exportSyntaxControlLabels	= {mappingSelectionLbl, variableSelectionLbl};
		Component[] exportSyntaxTextControls	= {mappingSelectionCB, variableSelectionCB};
		
		addLabelTextControlAsRows(exportSyntaxControlLabels,
								  exportSyntaxTextControls,
								  gridBagLayout,
								  selectionPanel);
		
		/* Build Variable Panel */
		nameLbl		= new JLabel(resourceBundle.getString(ES_NAME));
		nameLbl.setFont(currentFont);
		labelLbl	= new JLabel(resourceBundle.getString(ES_LABEL));
		labelLbl.setFont(currentFont);
		
		newVariablePanel.setBorder(
				BorderFactory.createCompoundBorder(
					BorderFactory.createTitledBorder(resourceBundle.getString(ES_NEW_VAR)), 
					BorderFactory.createEmptyBorder(5, 5, 5, 5)
				)
			);
		
		nameTF.setText(project.getTargetName());
		nameTF.setFont(currentFont);
		labelTF.setText(project.getTargetLabel());
		labelTF.setFont(currentFont);
		
		newVariablePanel.add(nameLbl);
		newVariablePanel.add(nameTF);
		newVariablePanel.add(labelLbl);
		newVariablePanel.add(labelTF);

		/* Build Create Panel */
		spssBtn	= new JButton(resourceBundle.getString(ES_SPSS));
		spssBtn.setFont(currentFont);
		spssBtn.addActionListener(
        		new java.awt.event.ActionListener() {
        			/* (non-Javadoc)
        			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
        			 */
        			public void actionPerformed(java.awt.event.ActionEvent evt) {
        				if ((mappingSelectionCB.getSelectedItem() instanceof InstanceMap) &&
        						(variableSelectionCB.getSelectedItem() instanceof AttributeMap)) {
        					InstanceMap selectedMapping = (InstanceMap)mappingSelectionCB.getSelectedItem();
        					AttributeMap straitenedMapping = (AttributeMap)variableSelectionCB.getSelectedItem();
        					
	        				getThreeStepMapperListList(content, selectedMapping, straitenedMapping);
	        				
	        				String oldVarName = "";
	        				if (variableSelectionCB.getSelectedItem() instanceof AttributeMap) {
	        					AttributeMap attr_map = (AttributeMap)variableSelectionCB.getSelectedItem();
	        					
	        					if (attr_map.getSourceAttribute() instanceof AttributeLink) {
	        						AttributeLink attr_link = attr_map.getSourceAttribute();
	        						
	        						if (attr_link.getAttribute() instanceof Variable)
	        							oldVarName = ((Variable)attr_link.getAttribute()).getName();
	        					}
	        				}
	        				String newVarName = nameTF.getText();
	        				String newVarLabel = labelTF.getText();
	        					        				
	        				extensionType = "sps";
	        				createSPSS(oldVarName, newVarName, newVarLabel, threeStepMapper);
	        			}
        			}
        		}
	        );
		stataBtn	= new JButton(resourceBundle.getString(ES_STATA));
		stataBtn.setFont(currentFont);
		stataBtn.addActionListener(
        		new java.awt.event.ActionListener() {
        			/* (non-Javadoc)
        			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
        			 */
        			public void actionPerformed(java.awt.event.ActionEvent evt) {
        				if ((mappingSelectionCB.getSelectedItem() instanceof InstanceMap) &&
        						(variableSelectionCB.getSelectedItem() instanceof AttributeMap)) {
        					InstanceMap selectedMapping = (InstanceMap)mappingSelectionCB.getSelectedItem();
        					AttributeMap straitenedMapping = (AttributeMap)variableSelectionCB.getSelectedItem();
        					
	        				getThreeStepMapperListList(content, selectedMapping, straitenedMapping);
	        				
	        				String oldVarName = "";
	        				if (variableSelectionCB.getSelectedItem() instanceof AttributeMap) {
	        					AttributeMap attr_map = (AttributeMap)variableSelectionCB.getSelectedItem();
	        					
	        					if (attr_map.getSourceAttribute() instanceof AttributeLink) {
	        						AttributeLink attr_link = attr_map.getSourceAttribute();
	        						
	        						if (attr_link.getAttribute() instanceof Variable)
	        							oldVarName = ((Variable)attr_link.getAttribute()).getName();
	        					}
	        				}
	        				String newVarName = nameTF.getText();
	        				String newVarLabel = labelTF.getText();
	        					
	        				extensionType = "do";
        					createSTATA(oldVarName, newVarName, newVarLabel, threeStepMapper);
	        			}
        			}
        		}
	        );
		sasBtn		= new JButton(resourceBundle.getString(ES_SAS));
		sasBtn.setFont(currentFont);
		sasBtn.addActionListener(
        		new java.awt.event.ActionListener() {
        			/* (non-Javadoc)
        			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
        			 */
        			public void actionPerformed(java.awt.event.ActionEvent evt) {
        				if ((mappingSelectionCB.getSelectedItem() instanceof InstanceMap) &&
        						(variableSelectionCB.getSelectedItem() instanceof AttributeMap)) {
        					InstanceMap selectedMapping = (InstanceMap)mappingSelectionCB.getSelectedItem();
        					AttributeMap straitenedMapping = (AttributeMap)variableSelectionCB.getSelectedItem();
        					
	        				getThreeStepMapperListList(content, selectedMapping, straitenedMapping);
	        				
	        				String oldVarName = "";
	        				if (variableSelectionCB.getSelectedItem() instanceof AttributeMap) {
	        					AttributeMap attr_map = (AttributeMap)variableSelectionCB.getSelectedItem();
	        					
	        					if (attr_map.getSourceAttribute() instanceof AttributeLink) {
	        						AttributeLink attr_link = attr_map.getSourceAttribute();
	        						
	        						if (attr_link.getAttribute() instanceof Variable)
	        							oldVarName = ((Variable)attr_link.getAttribute()).getName();
	        					}
	        				}
	        				String newVarName = nameTF.getText();
	        				String newVarLabel = labelTF.getText();
	        					     
	        				extensionType = "txt";
        					createSAS(oldVarName, newVarName, newVarLabel, threeStepMapper);
	        			}
        			}
        		}
	        );
		sasBtn.setVisible(false);
		mplusBtn	= new JButton(resourceBundle.getString(ES_MPLUS));
		mplusBtn.setFont(currentFont);
		mplusBtn.addActionListener(
        		new java.awt.event.ActionListener() {
        			/* (non-Javadoc)
        			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
        			 */
        			public void actionPerformed(java.awt.event.ActionEvent evt) {
        				if ((mappingSelectionCB.getSelectedItem() instanceof InstanceMap) &&
        						(variableSelectionCB.getSelectedItem() instanceof AttributeMap)) {
        					InstanceMap selectedMapping = (InstanceMap)mappingSelectionCB.getSelectedItem();
        					AttributeMap straitenedMapping = (AttributeMap)variableSelectionCB.getSelectedItem();
        					
	        				getThreeStepMapperListList(content, selectedMapping, straitenedMapping);
	        				
	        				String oldVarName = "";
	        				if (variableSelectionCB.getSelectedItem() instanceof AttributeMap) {
	        					AttributeMap attr_map = (AttributeMap)variableSelectionCB.getSelectedItem();
	        					
	        					if (attr_map.getSourceAttribute() instanceof AttributeLink) {
	        						AttributeLink attr_link = attr_map.getSourceAttribute();
	        						
	        						if (attr_link.getAttribute() instanceof Variable)
	        							oldVarName = ((Variable)attr_link.getAttribute()).getName();
	        					}
	        				}
	        				String newVarName = nameTF.getText();
	        				String newVarLabel = labelTF.getText();
	        				
	        				extensionType = "txt";
        					createMPLUS(oldVarName, newVarName, newVarLabel, threeStepMapper);
	        			}
        			}
        		}
	        );
		mplusBtn.setVisible(false);
			    
		/* Build Button Panel */
		printBtn	= new JButton(resourceBundle.getString(ES_PRINT));
		printBtn.setFont(currentFont);
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
		saveBtn		= new JButton(resourceBundle.getString(ES_SAVE));
		saveBtn.setFont(currentFont);
        saveBtn.addActionListener(
        		new java.awt.event.ActionListener() {
        			/* (non-Javadoc)
        			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
        			 */
        			public void actionPerformed(java.awt.event.ActionEvent evt) {
        				saveReport();
        			}
        		}
        );
		editBtn		= new JButton(resourceBundle.getString(ES_EDIT));
		editBtn.setFont(currentFont);
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
		
	    createPanel.add(buttonPanel);
		
		/* Build Output Panel */
		syntaxOutput 			= new JTextArea( 6, 64);
		syntaxOutput.setFont(currentFont);
		syntaxOutput.setEditable(false);
		syntaxOutputScrollPane	= new JScrollPane(syntaxOutput);
		syntaxOutputScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		syntaxOutputScrollPane.getVerticalScrollBar().setUnitIncrement(16);
		
		/* Build Result Panel */
		resultPanel.setLayout(new BorderLayout());
		resultPanel.add(newVariablePanel, BorderLayout.NORTH);
		resultPanel.add(syntaxOutputScrollPane, BorderLayout.CENTER);
		resultPanel.add(createPanel, BorderLayout.SOUTH);
		
		/* Build Form */
		form.setLayout(new BorderLayout());
		
		callHelpBtn = new JButton();
		callHelpBtn.setActionCommand(ActionCommandText.CMD_HELP_HELP_EXPORT_SYNTAX);
		callHelpBtn.addActionListener(al);
		callHelpBtn.setVisible(false);
		callHelpBtn.setEnabled(true);
		
		GridLayout experimentLayout = new GridLayout(0,1);
		sidePanel.setLayout(experimentLayout);
		sidePanel.add(spssBtn);
		sidePanel.add(stataBtn);
		sidePanel.add(sasBtn);
		sidePanel.add(mplusBtn);
		for (int filler=0; filler<1; filler++) {
			JButton invBtn = new JButton(""); invBtn.setVisible(false);
			sidePanel.add(invBtn);
		}
	    
		form.add(sidePanel, BorderLayout.WEST);
		form.add(selectionPanel, BorderLayout.NORTH);
		form.add(resultPanel, BorderLayout.CENTER);
		
		/* Build Dialog */
		this.add(form);
		
		if (mappingSelectionCB.getItemCount() > 0)
			mappingSelectionCB.setSelectedIndex(0);
		
	    /*
         *	Wait for input
         */
		final String[] DIALOG_BUTTONS_TITLES	= {resourceBundle.getString(ES_ACCEPT), resourceBundle.getString(ES_HELP)}; 
		
		int retValue = 1;
    	while (retValue == 1) 
    	{ 
    		retValue = JOptionPane.showOptionDialog(
    					((CStatsCtrl)al).getView().getAppFrame(),  
    					form,
    					resourceBundle.getString(ES_TITLE),
    					JOptionPane.OK_OPTION, 
    					JOptionPane.QUESTION_MESSAGE,
    					null, 
    					DIALOG_BUTTONS_TITLES, 
    					DIALOG_BUTTONS_TITLES[0]
    		);
    		
    		if (retValue == 1)
    			callHelpBtn.doClick();
    	}
	}
	
	/**
	 * 
	 */
	public void handleExitChoice() {
		if (returnValue == 0) {
			/* TODO */
		} else {
			/* TODO */
		}
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
											Container container)
	{

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
	
	/**
	 * 
	 */
	protected void editReport() {
		syntaxOutput.setEditable(true);
	}
	
	/**
	 * @return
	 */
	protected boolean saveReport() {
		SyntaxFilter fSyntaxFilter = new SyntaxFilter ();
		
		File fFile = new File ("syntax."+this.extensionType); 
  
		JComponent.setDefaultLocale(currentLocale);
		
	    JFileChooser fc = new JFileChooser (); 

		ResourceBundle	resourceBundle	= ResourceBundle.getBundle(BUNDLE_F, currentLocale);
		String			file_name		= resourceBundle.getString(FILE_NAME);
		
		String path_0 = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		path_0 = path_0.replace("/", "\\\\"); // use File.separator in CharmStatsPro
		path_0 = path_0.replace(file_name, ""); // filename stored in resource FrameBundle
		path_0 = path_0 + "exports\\\\";
		path_0 = path_0 + "syntax\\\\";
		
		
		String path_1 = path_0 + project.getName() +"\\\\";
		
		File dir = new File (path_1);
		if (!dir.exists()) {
			try{
			    if(dir.mkdir()) {
			    	dir = new File (path_1);
			    } else {
			        dir = new File (path_0);
			    }
			} catch(Exception e){
			    e.printStackTrace();
			} 
		}
		
		fc.setCurrentDirectory (dir);
		
	    // Set filter for Java source files.
	    fc.setFileFilter (fSyntaxFilter); 

	    // Set to a default name for save.
	    fc.setSelectedFile (fFile); 

	    // Open chooser dialog
	    int result = fc.showSaveDialog(((CStatsCtrl)fal).getView().getAppFrame());

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
	        return writeFile (fFile, syntaxOutput.getText());
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
			String extension = "";
			
			if (spssBtn.isSelected())
				extension = ".sps";
			if (stataBtn.isSelected())
				extension = ".do";
			if (sasBtn.isSelected())
				extension = ".txt";
			if (mplusBtn.isSelected())
				extension = ".txt";
			
			return f.getName().toLowerCase().endsWith(extension) || f.isDirectory();
		}
	 
		/* (non-Javadoc)
		 * @see javax.swing.filechooser.FileFilter#getDescription()
		 */
		public String getDescription () {
			String description = "";
					
			if (spssBtn.isSelected())
				description = "SPSS Syntax files (*.sps)";
			if (stataBtn.isSelected())
				description = "STATA Program files (*.do)";
			if (sasBtn.isSelected())
				description = "SAS Syntax files (*.txt)";
			if (mplusBtn.isSelected())
				description = "MPLUS Syntax files (*.txt)";
				
			return description;
		}
	}
		
	/**
	 * @param file
	 * @param dataString
	 * @return
	 */
	private boolean writeFile (File file, String dataString) {
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
	 * 
	 */
	protected void filePrint() { 
		
		print(this.syntaxOutput);
	}
	
	/**
	 * @param pane
	 */
	protected void print(JTextArea pane) {
		
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
	 * @param content
	 * @param selectedMapping
	 * @param straitenedMapping
	 */
	private void getThreeStepMapperListList(ProContent content, InstanceMap selectedMapping, AttributeMap straitenedMapping) {
		threeStepMapper = new ArrayList<Characteristics[]>();
		
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
	}
	
	/**
	 * @param oldVarName
	 * @param newVarName
	 * @param newVarLabel
	 * @param mappings
	 */
	private void createSPSS(String oldVarName, 
			String newVarName, String newVarLabel, ArrayList<Characteristics[]> mappings) {
		
		String syntaxText = "RECODE "+ oldVarName +" ";
		
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
		
		syntaxText += "INTO "+ newVarName +".\n";
		syntaxText += "VARIABLE LABELS "+ newVarName +" '"+ newVarLabel +"'.\n";
		
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
		
		syntaxText += "EXECUTE.";
		
		syntaxOutput.setText(syntaxText);
		syntaxOutput.setCaretPosition(0); 
	}
	
	/**
	 * @param oldVarName
	 * @param newVarName
	 * @param newVarLabel
	 * @param mappings
	 */
	private void createSTATA(String oldVarName, 
			String newVarName, String newVarLabel, ArrayList<Characteristics[]> mappings) {
		
		String syntaxText = "generate "+ newVarName +" = "+ oldVarName +"\r\n";
		syntaxText += "recode "+ newVarName +" ";
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
		
		syntaxText += "\r\n";
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
			syntaxText += "\nlabel define "+ newVarName +" ";
		while (categoryIter.hasNext()) {
			Category cat = categoryIter.next();
			
			syntaxText += cat.getCode() +" \""+ cat.getLabel().replace("'", "") +" \" ";
		}
		syntaxText += "\nlabel value "+ newVarName +" "+ newVarName +"\n";
		/* label values END */
		
			
		syntaxOutput.setText(syntaxText);
		syntaxOutput.setCaretPosition(0); 
	}
	
	/**
	 * @param oldVarName
	 * @param newVarName
	 * @param newVarLabel
	 * @param mappings
	 */
	private void createSAS(String oldVarName, 
			String newVarName, String newVarLabel, ArrayList<Characteristics[]> mappings) {
		
		String syntaxText = "DATA "+ newVarName +"Temp;\r\n";
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
		
		syntaxOutput.setText(syntaxText);
	}
	
	/**
	 * @param oldVarName
	 * @param newVarName
	 * @param newVarLabel
	 * @param mappings
	 */
	private void createMPLUS(String oldVarName, 
			String newVarName, String newVarLabel, ArrayList<Characteristics[]> mappings) {
		
		String syntaxText = "DEFINE: \r\n";
		
		Iterator<Characteristics[]> mappingIter = mappings.iterator();
		while (mappingIter.hasNext()) {
			Characteristics[] map = mappingIter.next();
			
			if ((map[0] instanceof Value) 
					&& (map[5] instanceof Category)) {
				syntaxText += "IF "+ oldVarName +" EQ "+ ((Value)map[0]).getValue() +" THEN "+ newVarName +" = "+ ((Category)map[5]).getCode() +";\r\n ";
			}
		}
		
		syntaxOutput.setText(syntaxText);
	}
}

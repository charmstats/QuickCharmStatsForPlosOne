package org.gesis.charmstats.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.gesis.charmstats.ActionCommandText;
import org.gesis.charmstats.model.CStatsModel;
import org.gesis.charmstats.model.Question;
import org.gesis.charmstats.model.Study;
import org.gesis.charmstats.model.Value;
import org.gesis.charmstats.model.Variable;

/**
 *	@author	Martin Friedrichs
 *	@since	0.9.7.4
 *
 */
public class TabStudy extends Tab {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String VARIABLE				= "variable_lbl";
	
	public static final String VE_STUDY					= "ve_study";
	public static final String VE_STUDY_TITLE 			= "ve_study_title";
	public static final String VE_STUDY_AREA 			= "ve_study_area";
	public static final String VE_DATE_COLL 			= "ve_date_coll";
	public static final String VE_POPULATION 			= "ve_population";
	public static final String VE_SELECTION 			= "ve_selection";
	public static final String VE_COLL_METH 			= "ve_coll_meth";
	public static final String VE_COLLECTORS 			= "ve_collectors";
	public static final String VE_SOURCE_FILE 			= "ve_source_file";
	public static final String VE_DATASET 				= "ve_dataset";
	public static final String VE_DOI 					= "ve_doi";
	public static final String VE_QUEST_DEF				= "ve_quest_def";
	
	private static final String	BLANK_STRING			= new String(new char[32]).replace('\0', ' ');
	
	JPanel				variablePanel;
	JLabel				variableLabel;
	JComboBox			variableComboBox;
	
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
	private JLabel			questDefLbl;
	private JScrollPane		questDefScrollPnl;
	private JTextArea		questDefTA;

	JPanel				studyPanel;
	
	Font	currentFont;
	
	
	/*
	 *	Constructor
	 */
	/**
	 * @param locale
	 */
	public TabStudy(Locale locale) {
		super(locale);
		
		setName("TabStudy");
	}

	public TabStudy(ActionListener al, Locale locale) {
		this(locale);
		
		currentLocale	= locale;
		resourceBundle	= ResourceBundle.getBundle(BUNDLE, currentLocale);
		currentFont		= this.getFont();

		/* Create Form Components */
		buildStudyPanel();
		
		/* Add Form Components to Form Panel */
		formPanel.add(studyPanel);
		
		/* Setup Buttons */
		backButton.setActionCommand(ActionCommandText.BTN_DAT_STP_STU_TAB_BACK);
		backButton.addActionListener(al);
		resetButton.setActionCommand(ActionCommandText.BTN_DAT_STP_STU_TAB_RESET);
		resetButton.addActionListener(al);
		resetButton.setVisible(false);
		resetButton.setEnabled(false);
		noteButton.setActionCommand(ActionCommandText.BTN_DAT_STP_STU_TAB_NOTE);
		noteButton.addActionListener(al);
		nextButton.setActionCommand(ActionCommandText.BTN_DAT_STP_STU_TAB_NEXT);
		nextButton.addActionListener(al);
		
		setPanelIdx(5);
		
		changeLanguage(locale);
	}
	
	public void changeLanguage(Locale locale) {
		super.changeLanguage(locale);
		
		ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, locale);
		studyTitleLbl.setText(bundle.getString(VE_STUDY_TITLE));
		studyDOILbl.setText(bundle.getString(VE_DOI));
		studyAreaLbl.setText(bundle.getString(VE_STUDY_AREA));
		studyDateOfCollLbl.setText(bundle.getString(VE_DATE_COLL));
		studyPopulationLbl.setText(bundle.getString(VE_POPULATION));
		studySelectionLbl.setText(bundle.getString(VE_SELECTION));
		studyCollMethLbl.setText(bundle.getString(VE_COLL_METH));
		studyCollectorsLbl.setText(bundle.getString(VE_COLLECTORS));
		studySourceFileLbl.setText(bundle.getString(VE_SOURCE_FILE));		
		studyDatasetLbl.setText(bundle.getString(VE_DATASET));
		questDefLbl.setText(bundle.getString(VE_QUEST_DEF));
	}
	
	public void changeFont(Font f) {
		super.changeFont(f);
		
		variableLabel.setFont(f);
		variableComboBox.setFont(f);
		
		currentFont = f;
		
		studyTitleLbl.setFont(f);
		studyTitleTF.setFont(f);
		studyDOILbl.setFont(f);
		studyDOITF.setFont(f);
		studyAreaLbl.setFont(f);
		studyAreaTA.setFont(f);
		studyDateOfCollLbl.setFont(f);
		studyDateOfCollTF.setFont(f);
		studyPopulationLbl.setFont(f);
		studyPopulationTA.setFont(f);		
		studySelectionLbl.setFont(f);
		studySelectionTA.setFont(f);
		studyCollMethLbl.setFont(f);
		studyCollMethTA.setFont(f);
		studyCollectorsLbl.setFont(f);
		studyCollectorsTA.setFont(f);
		studySourceFileLbl.setFont(f);
		studySourceFileTF.setFont(f);
		studyDatasetLbl.setFont(f);
		studyDatasetTF.setFont(f);
		
		questDefLbl.setFont(f);
		questDefTA.setFont(f);
	}

	void buildStudyPanel() {
		variablePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    variableLabel = new JLabel(resourceBundle.getString(VARIABLE));
	    variableComboBox = new JComboBox();
	    variableComboBox.addItem(BLANK_STRING);
	    variableComboBox.addActionListener (new ActionListener () {
	    	public void actionPerformed(ActionEvent e) {
		    	if (e.getSource() != null) {
		    		if ( ((JComboBox)e.getSource()).getSelectedItem() != null) {
		    			if (((JComboBox)e.getSource()).getSelectedItem() instanceof Variable) {
		    				Variable var = (Variable)((JComboBox)e.getSource()).getSelectedItem();
		    				
		    				Question quest = var.getSource();
		    				
		    				if (quest instanceof Question) {
		    					Study stu = quest.getSource();
		    					
		    					if (stu instanceof Study) {
		    						studyTitleTF.setText(stu.getTitle());
		    						studyDOITF.setText(stu.getDOI());
		    						studyAreaTA.setText(stu.getStudyArea());		    						
		    						studyDateOfCollTF.setText(stu.getDateOfCollection());
		    						studyPopulationTA.setText(stu.getPopulation());
		    						studySelectionTA.setText(stu.getSelection());
		    						studyCollMethTA.setText(stu.getCollectionMethod());
		    						studyCollectorsTA.setText(stu.getCollectors());
		    						studySourceFileTF.setText(stu.getSourceFile());
		    						studyDatasetTF.setText(stu.getDataset());
		    						questDefTA.setText(stu.getDefinition().getText());
		    					}		    						
		    				}
		    			}
		    		}
		    	}
	    	}
	    });
		variableComboBox.addFocusListener(this);
        
        variablePanel.add(variableLabel);
        variablePanel.add(variableComboBox);
        
		/*
		 *	Study
		 */
        GridBagLayout	gridBagLayout_s = new GridBagLayout();
        
		studyPnl = new JPanel();
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
			
		questDefLbl = new JLabel(resourceBundle.getString(VE_QUEST_DEF));
		questDefLbl.setFont(currentFont);
		
		questDefTA	= new JTextArea(5, 60);
		questDefTA.setFont(currentFont);
		questDefTA.addFocusListener(this);
		questDefTA.setLineWrap(true);
		questDefTA.setWrapStyleWord(true);
		
		questDefScrollPnl = new JScrollPane(questDefTA);
		questDefScrollPnl.getVerticalScrollBar().setUnitIncrement(16);
		
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
				questDefLbl, 
				};
		
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
				questDefScrollPnl 
				};
		
		addLabelTextControlAsRows(studyLabels, studyControls, gridBagLayout_s, studyEditorPnl);
       
        studyPanel = new JPanel();
        studyPanel.setLayout(new BorderLayout());
        studyPanel.add(variablePanel, BorderLayout.NORTH);
        studyPanel.add(studyEditorPnl, BorderLayout.CENTER);
        
	}
	
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
	
	public void addVariableEntry(Variable var) {
		variableComboBox.addItem(var);
	}

	public void setDefaults(Object defaults) {
		CStatsModel model;
		
		if (defaults instanceof CStatsModel)
			model = (CStatsModel)defaults;
		else {
			model = new CStatsModel();
			
			model.getProject().getContent().setValues(new ArrayList<Value>());
		}
		
		fillModel(model);		
	}
	
	public void fillModel(CStatsModel model) {
		DefaultComboBoxModel boxModel = (DefaultComboBoxModel)variableComboBox.getModel();
		boxModel.removeAllElements();
		variableComboBox.addItem(BLANK_STRING);
		ArrayList<Variable> variables = model.getProject().getContent().getImportedVariables();		
		Iterator<Variable> varIterator = variables.iterator();
		while(varIterator.hasNext()) {
			Variable var = varIterator.next();
			
			addVariableEntry(var);
		}
		
		studyTitleTF.setEnabled(false);
		studyDOITF.setEnabled(false);
		studyAreaTA.setEnabled(false);		    						
		studyDateOfCollTF.setEnabled(false);
		studyPopulationTA.setEnabled(false);
		studySelectionTA.setEnabled(false);
		studyCollMethTA.setEnabled(false);
		studyCollectorsTA.setEnabled(false);
		studySourceFileTF.setEnabled(false);
		studyDatasetTF.setEnabled(false);
		questDefTA.setEnabled(false); 
		
		if (variableComboBox.getItemCount() > 1)
			variableComboBox.setSelectedIndex(1);
	}

}

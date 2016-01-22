package org.gesis.charmstats.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import org.gesis.charmstats.model.Value;
import org.gesis.charmstats.model.Variable;

/**
 *	@author	Martin Friedrichs
 *	@since	0.9.7.4
 *
 */
public class TabQuestion extends Tab {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String VARIABLE				= "variable_lbl";
	
	public static final String VE_QUESTION				= "ve_question";
	public static final String VE_QUEST_TEXT			= "ve_quest_text";
	public static final String VE_QUEST_INST			= "ve_quest_inst";
	public static final String VE_QUEST_NAME			= "ve_quest_name";
	public static final String VE_QUEST_INT				= "ve_quest_int";
	public static final String VE_QUEST_DEF				= "ve_quest_def";
	
	private static final String	BLANK_STRING			= new String(new char[32]).replace('\0', ' ');
	
	JPanel					variablePanel;
	JLabel					variableLabel;
	JComboBox				variableComboBox;
	
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
	
	JPanel					questionPanel;
	
	
	Font					currentFont;
	
	
	/*
	 *	Constructor
	 */
	/**
	 * @param locale
	 */
	public TabQuestion(Locale locale) {
		super(locale);
		
		setName("TabQuestion");
	}
	
	public TabQuestion(ActionListener al, Locale locale) {
		this(locale);
		
		currentLocale	= locale;
		resourceBundle	= ResourceBundle.getBundle(BUNDLE, currentLocale);
		currentFont		= this.getFont();
		
		/* Create Form Components */
		buildQuestionPanel();
		
		/* Add Form Components to Form Panel */
		formPanel.add(questionPanel);
		
		/* Setup Buttons */
		backButton.setActionCommand(ActionCommandText.BTN_DAT_STP_QUE_TAB_BACK);
		backButton.addActionListener(al);
		resetButton.setActionCommand(ActionCommandText.BTN_DAT_STP_QUE_TAB_RESET);
		resetButton.addActionListener(al);
		resetButton.setVisible(false);
		resetButton.setEnabled(false);
		noteButton.setActionCommand(ActionCommandText.BTN_DAT_STP_QUE_TAB_NOTE);
		noteButton.addActionListener(al);
		nextButton.setActionCommand(ActionCommandText.BTN_DAT_STP_QUE_TAB_NEXT);
		nextButton.addActionListener(al);
		
		setPanelIdx(5);
		
		changeLanguage(locale);
	}
	
	public void changeLanguage(Locale locale) {
		super.changeLanguage(locale);

		ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, locale);		
		questTextLbl.setText(bundle.getString(VE_QUEST_TEXT));
		questInstLbl.setText(bundle.getString(VE_QUEST_INST));
		questNameLbl.setText(bundle.getString(VE_QUEST_NAME));
		questIntLbl.setText(bundle.getString(VE_QUEST_INT));
		questDefLbl.setText(bundle.getString(VE_QUEST_DEF));
	}
	
	public void changeFont(Font f) {
		super.changeFont(f);
		
		variableLabel.setFont(f);
		variableComboBox.setFont(f);
		
		currentFont = f;
		
		questTextLbl.setFont(f);
		questTextTA.setFont(f);
		questInstLbl.setFont(f);
		questInstTA.setFont(f);
		
		questNameLbl.setFont(f);
		questNameTF.setFont(f);
		questIntLbl.setFont(f);
		questIntTA.setFont(f);
		questDefLbl.setFont(f);
		questDefTA.setFont(f);
	}
	
	void buildQuestionPanel() {
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
		    					questTextTA.setText(quest.getText());
		    					questInstTA.setText(quest.getInstruction());
		    					questNameTF.setText(quest.getName());
		    					questIntTA.setText(quest.getIntent());
		    					questDefTA.setText(quest.getDefinition().getText());
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
		 *	Question
		 */
        GridBagLayout	gridBagLayout_q	= new GridBagLayout();
        
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
        
        
        questionPanel = new JPanel();
        questionPanel.setLayout(new BorderLayout());
        questionPanel.add(variablePanel, BorderLayout.NORTH);
        questionPanel.add(questionPnl, BorderLayout.CENTER);
        
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
		
		questTextTA.setEnabled(false);
		questInstTA.setEnabled(false);
		
		questNameTF.setEnabled(false);
		questIntTA.setEnabled(false);
		questDefTA.setEnabled(false);
		
		if (variableComboBox.getItemCount() > 1)
			variableComboBox.setSelectedIndex(1);
	}
	
}


package org.gesis.charmstats.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.gesis.charmstats.ActionCommandText;
import org.gesis.charmstats.model.CStatsModel;
import org.gesis.charmstats.model.Concept;
import org.gesis.charmstats.model.Description;
import org.gesis.charmstats.model.FormattedText;
import org.gesis.charmstats.model.Label;
import org.gesis.charmstats.model.MultLangText;
import org.gesis.charmstats.model.Project;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class TabConcept extends Tab implements FocusListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String TC_TEXTCONTROLPANE 	= "concept_definition";
	public static final String TC_CONCEPT 			= "concept";
	public static final String TC_DEFINITION 		= "notes";
	
	/*
	 *	Fields
	 */
	JPanel		textControlsPane;
	JLabel		conceptLbl;
	JTextField	conceptTF;
	JLabel		definitionLbl;
	JScrollPane	definitionJScrollPane;
	JTextArea	definitionTA;
	
	/*
	 *	Constructor
	 */
	/**
	 * @param locale
	 */
	public TabConcept(Locale locale) {
		super(locale);
		
		setName("TabConcept");
	}
	
	/**
	 * @param al
	 * @param locale
	 * @param addenda
	 */
	public TabConcept(ActionListener al, Locale locale, Object addenda) {
		this(locale);
		
		currentLocale	= locale;
		resourceBundle	= ResourceBundle.getBundle(BUNDLE, currentLocale);
		
		/* Create Form Components */
		conceptLbl = new JLabel(resourceBundle.getString(TC_CONCEPT)+":");

		conceptTF = new JTextField(32);
		conceptTF.setName("conConceptTF");
		conceptTF.addFocusListener(this);
		
		if (addenda instanceof Project) {
//			Concept concept = ((Project)addenda).getContent().getConcept();
			Concept concept = ((Project)addenda).getConcept();
			conceptTF.setText((concept != null ? concept.getDefaultLabel().getLabel().getTextualContent() : ""));
		}
		
		definitionLbl = new JLabel(resourceBundle.getString(TC_DEFINITION)+":");
		
		definitionTA = new JTextArea(22, 95); 
		definitionTA.setName("conDefinitionTA");
		definitionTA.addFocusListener(this);
		definitionTA.setLineWrap(true);
		definitionTA.setWrapStyleWord(true);
		
		if (addenda instanceof Project) {
//			Concept concept = ((Project)addenda).getContent().getConcept();
			Concept concept = ((Project)addenda).getConcept();
			definitionTA.setText((concept != null ? concept.getDefaultDescription().getDescription().getTextualContent() : ""));
		}
		
		definitionJScrollPane = new JScrollPane(definitionTA);
		definitionJScrollPane.getVerticalScrollBar().setUnitIncrement(16);

		JLabel[]		textControlLabels	= {conceptLbl, definitionLbl};		
		Component[] 	textControls		= {conceptTF, definitionJScrollPane};	
		GridBagLayout	gridBagLayout		= new GridBagLayout();	
		textControlsPane					= new JPanel();						
		textControlsPane.setLayout(gridBagLayout);			
		
		addLabelTextControlAsRows(textControlLabels,
								  textControls,
								  gridBagLayout,
								  textControlsPane,
								  false);
		
		textControlsPane.setBorder(
			BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(resourceBundle.getString(TC_TEXTCONTROLPANE)+":"),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)
			)
		);
		
		/* Add Form Components to Form Panel */
		formPanel.setLayout(new BorderLayout());
		
		formPanel.add(textControlsPane, BorderLayout.NORTH);
		
		/* Setup Buttons */
		backButton.setActionCommand(ActionCommandText.BTN_PRJ_STP_CON_TAB_BACK);
		backButton.addActionListener(al);
		resetButton.setActionCommand(ActionCommandText.BTN_PRJ_STP_CON_TAB_RESET);
		resetButton.addActionListener(al);
		resetButton.setVisible(true);
		resetButton.setEnabled(false);
		noteButton.setActionCommand(ActionCommandText.BTN_PRJ_STP_CON_TAB_NOTE);
		noteButton.addActionListener(al);
		nextButton.setActionCommand(ActionCommandText.BTN_PRJ_STP_CON_TAB_NEXT);
		nextButton.addActionListener(al);
		
		setPanelIdx(0);
		
		changeLanguage(locale);
	}

	/*
	 *	Methods
	 */
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.Tab#changeLanguage(java.util.Locale)
	 */
	public void changeLanguage(Locale locale) {
		super.changeLanguage(locale);
		
		ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, locale);		
		textControlsPane.setBorder(
			BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(bundle.getString(TC_TEXTCONTROLPANE)+":"),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)
			)
		);
		conceptLbl.setText(bundle.getString(TC_CONCEPT)+":");
		definitionLbl.setText(bundle.getString(TC_DEFINITION)+":");
	}
	
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.Tab#changeFont(java.awt.Font)
	 */
	public void changeFont(Font f) {
		super.changeFont(f);
		
		conceptTF.setFont(f);
		definitionTA.setFont(f);
		
		conceptLbl.setFont(f);
		definitionLbl.setFont(f);
		
		UIManager.put("TitledBorder.font", f);
		javax.swing.SwingUtilities.updateComponentTreeUI(textControlsPane);
	}
	
	/**
	 * @param labels
	 * @param textControls
	 * @param gridBagLyout
	 * @param container
	 * @param insets
	 */
	private void addLabelTextControlAsRows(	JLabel[] labels,
		 	Component[] textControls,
		 	GridBagLayout gridBagLyout,
		 	Container container,
		 	boolean insets)
	{

		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.EAST;

		int numberOfLabels = labels.length;
		
		for (int i = 0; i < numberOfLabels; i++) {
			c.gridwidth = GridBagConstraints.RELATIVE;
			c.fill = GridBagConstraints.NONE;
			c.weightx = 0.0;
			if (insets)
				c.insets = new Insets(0, 0, 3, 0);
			container.add(labels[i], c);
			
			c.gridwidth = GridBagConstraints.REMAINDER;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 1.0;
			if (insets)
				c.insets = new Insets(0, 0, 3, 0);
			container.add(textControls[i], c);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.Tab#setDefaults(java.lang.Object)
	 */
	public void setDefaults(Object defaults) {
		CStatsModel model;
		
		if (defaults instanceof CStatsModel)
			model = (CStatsModel)defaults;
		else {
			model = new CStatsModel();
			
//			model.getProject().getContent().getConcept().getDefaultLabel().setLabel(new MultLangText(""));
//			model.getProject().getContent().getConcept().getDefaultDescription().setDescription(new FormattedText(""));
			model.getProject().getConcept().getDefaultLabel().setLabel(new MultLangText(""));
			model.getProject().getConcept().getDefaultDescription().setDescription(new FormattedText(""));

		}
		
		fillModel(model);
	}
	
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.Tab#fillModel(org.gesis.charmstats.model.CStatsModel)
	 */
	public void fillModel(CStatsModel model) {
		Project project;
		
		if (model instanceof CStatsModel)
			project = ((CStatsModel)model).getProject();
		else
			project = new Project();
				
//		if (project.getContent().getConcept().getDefaultLabel() instanceof Label )
//			conceptTF.setText(project.getContent().getConcept().getDefaultLabel().getLabel().getTextualContent());
		if (project.getConcept().getDefaultLabel() instanceof Label )
			conceptTF.setText(project.getConcept().getDefaultLabel().getLabel().getTextualContent());
			
//		if (project.getContent().getConcept().getDefaultDescription() instanceof Description)
//			definitionTA.setText(project.getContent().getConcept().getDefaultDescription().getDescription().getTextualContent());
		if (project.getConcept().getDefaultDescription() instanceof Description)
			definitionTA.setText(project.getConcept().getDefaultDescription().getDescription().getTextualContent());
			
		if ((project.getFinishedSince() != null) ||	
				(!project.isEditedByUser())) {				
			conceptTF.setEnabled(false);
			definitionTA.setEnabled(false);
		} else {
			conceptTF.setEnabled(true);
			definitionTA.setEnabled(true);			
		}
	}

	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.Tab#focusGained(java.awt.event.FocusEvent)
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
	 * @see org.gesis.charmstats.view.Tab#focusLost(java.awt.event.FocusEvent)
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
	 * 
	 */
	public void handleFocusLost() {
		
	}
}

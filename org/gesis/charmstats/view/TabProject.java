package org.gesis.charmstats.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.undo.UndoManager;

import org.gesis.charmstats.ActionCommandText;
import org.gesis.charmstats.model.CStatsModel;
import org.gesis.charmstats.model.Project;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class TabProject extends Tab implements FocusListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String TP_PROJECT_NAME	= "project_name";
	public static final String TP_PROJECT_NOTES	= "project_notes";
	
	/*
	 *	Fields
	 */
	JPanel prjNamePane;
	JPanel prjNotePane;
	
	JTextField	prjNameTF;
	JTextArea	prjSummaryTA;
		
	/*
	 *	Constructor
	 */
	/**
	 * @param locale
	 */
	public TabProject(Locale locale) {
		super(locale);
		
		setName("TabProject");
	}
	
	/**
	 * @param al
	 * @param locale
	 * @param addenda
	 */
	public TabProject(ActionListener al, Locale locale, Object addenda) {
		this(locale);
		
		currentLocale	= locale;
		resourceBundle	= ResourceBundle.getBundle(BUNDLE, currentLocale);
				
		/* Create Form Components */
		createNamePane();
		createNotePane();
		
		
		if (addenda instanceof CStatsModel) {
			setDefaults(((CStatsModel)addenda).getProject());
		}
		
		/* Add Form Components to Form Panel */
		formPanel.setLayout(new BorderLayout());
		
		formPanel.add(prjNamePane, BorderLayout.NORTH);
		formPanel.add(prjNotePane, BorderLayout.CENTER);
		
		/* Setup Buttons */
		backButton.setActionCommand(ActionCommandText.BTN_PRJ_STP_PRJ_TAB_BACK);
		backButton.addActionListener(al);
		backButton.setVisible(false);
		backButton.setEnabled(false);
		resetButton.setActionCommand(ActionCommandText.BTN_PRJ_STP_PRJ_TAB_RESET);
		resetButton.addActionListener(al);
		resetButton.setVisible(true);
		resetButton.setEnabled(false);
		noteButton.setActionCommand(ActionCommandText.BTN_PRJ_STP_PRJ_TAB_NOTE);
		noteButton.addActionListener(al);
		noteButton.setEnabled(false);
		nextButton.setActionCommand(ActionCommandText.BTN_PRJ_STP_PRJ_TAB_NEXT);
		nextButton.addActionListener(al);
		nextButton.setEnabled(false);
		
		setPanelIdx(0);
		enableElements(false);
		
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
		prjNamePane.setBorder(
			BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(bundle.getString(TP_PROJECT_NAME)+":"),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)
			)
		);
		prjNotePane.setBorder(
			BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(bundle.getString(TP_PROJECT_NOTES)+":"),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)
			)
		);
	}
	
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.Tab#changeFont(java.awt.Font)
	 */
	public void changeFont(Font f) {
		super.changeFont(f);
		
		prjNameTF.setFont(f);
		prjSummaryTA.setFont(f);
		
		UIManager.put("TitledBorder.font", f);
		javax.swing.SwingUtilities.updateComponentTreeUI(prjNamePane);
		javax.swing.SwingUtilities.updateComponentTreeUI(prjNotePane);
	}
	
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.Tab#enableElements(boolean)
	 */
	public void enableElements(boolean enable) {
		
		prjNameTF.setEnabled(enable);
		prjSummaryTA.setEnabled(enable);
		this.validate();
	}
	
	/**
	 * 
	 */
	private void createNamePane() {
		prjNameTF = new JTextField(32);
		prjNameTF.setName("prjNameTF");
		prjNameTF.addFocusListener(this);
		prjNamePane = new JPanel(new FlowLayout(FlowLayout.LEFT));
		prjNamePane.add(prjNameTF);		
		prjNamePane.setBorder(
			BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(resourceBundle.getString(TP_PROJECT_NAME)+":"),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)
			)
		);		
	}
	
	/**
	 * 
	 */
	private void createNotePane() {
		prjSummaryTA = new JTextArea(19, 100); 
		prjSummaryTA.setName("prjSummaryTA");
		prjSummaryTA.addFocusListener(this);
		prjSummaryTA.setLineWrap(true);
		prjSummaryTA.setWrapStyleWord(true);
		prjNotePane = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JScrollPane prjNoteScrollPane = new JScrollPane(prjSummaryTA);
		prjNoteScrollPane.getVerticalScrollBar().setUnitIncrement(16);
		prjNotePane.add(prjNoteScrollPane);		
		prjNotePane.setBorder(
			BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(resourceBundle.getString(TP_PROJECT_NOTES)+":"),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)
			)
		);				
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
			
			model.getProject().setName("");
//			model.getProject().getContent().getSummary().setText("");
			model.getProject().getSummary().setText("");
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
						
		prjNameTF.setText(project.getName());
//		prjSummaryTA.setText(project.getContent().getSummary().getText());
		prjSummaryTA.setText(project.getSummary().getText());
		
		if ((model.getProject().getFinishedSince() != null) ||	
				(!model.getProject().isEditedByUser())) {		
			prjNameTF.setEnabled(false);
			prjSummaryTA.setEnabled(false);
		} else {
			prjNameTF.setEnabled(true);
			prjSummaryTA.setEnabled(true);
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
}

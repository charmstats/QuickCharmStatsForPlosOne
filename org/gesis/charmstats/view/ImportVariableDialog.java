package org.gesis.charmstats.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.gesis.charmstats.ActionCommandText;
import org.gesis.charmstats.controller.CStatsCtrl;
import org.gesis.charmstats.model.Variable;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class ImportVariableDialog extends JFrame implements FocusListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String BUNDLE					= "org.gesis.charmstats.resources.DesktopBundle";
	
	public static final String IMP_VAR_TITLE			= "imp_var_title";
	
	public static final String TSV_ACCEPT				= "imp_accept";
	public static final String TSV_CANCEL				= "tsv_cancel";
	public static final String TSV_HELP					= "tsv_help";
	public static final String IMP_NO_HELP				= "me_no_help";
	
	public static final String VARIABLE_LBL				= "Variable: ";
	public static final String IMPORT_VARIABLE			= "ImportVariable";
	
	public static final String IMPORT_VARIABLE_TOOLTIP	= "Variable";
	
	public static final String EMPTY 					= "";
	
	
	/*
	 *	Fields
	 */
	JPanel 		formular = new JPanel();
	
	JPanel 		chooseParametersPanel;
	
	JComboBox	variablesCB;
	JList		selections;
	
	JButton			callHelpBtn;
	
	Locale 		  	currentLocale;
	ResourceBundle	resourceBundle;
	
	/*
	 *	Constructor
	 */
	/**
	 * @param varList
	 * @param locale
	 */
	public ImportVariableDialog(ArrayList<Variable> varList, Locale locale, ActionListener al) {
		importVariable(varList, locale, al);
	}
	
	/*
	 *	Methods
	 */
	/**
	 * @param varList
	 * @param locale
	 */
	private void importVariable(ArrayList<Variable> varList, Locale locale, ActionListener al) {
		Vector<Variable> myVector = new Vector<Variable>();
		
		currentLocale	= locale;
		resourceBundle	= ResourceBundle.getBundle(BUNDLE, currentLocale);
		
		callHelpBtn = new JButton();
		callHelpBtn.setActionCommand(ActionCommandText.CMD_HELP_HELP_IMPORT_VARIABLE);
		callHelpBtn.addActionListener(al);
		callHelpBtn.setVisible(false);
		callHelpBtn.setEnabled(true);
		
		this.setTitle(resourceBundle.getString(IMP_VAR_TITLE));
		
		Iterator<Variable> iterator_v = varList.iterator();	
		while (iterator_v.hasNext()) {
			Variable v = iterator_v.next();
			
			myVector.add(v);
		}
		variablesCB = new JComboBox(myVector);
		//AutoCompleteDecorator.decorate(variablesCB);
		//variablesCB.isEditable();
		variablesCB.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXX");
		variablesCB.addFocusListener(this);
		
		JPanel variablePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		variablePanel.add(variablesCB);
		
		chooseParametersPanel = new JPanel(new FlowLayout());
		chooseParametersPanel.add(variablePanel);
		
		JPanel innerBtnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JButton selectBtn = new JButton(">");
		selectBtn.addActionListener(
				new ActionListener() {
					/* (non-Javadoc)
					 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
					 */
					public void actionPerformed(ActionEvent e) {
						acceptBtnAction(e, variablesCB.getSelectedItem(), selections);
					}
				}
		);
		JButton returnBtn = new JButton("<");
		returnBtn.addActionListener(
				new ActionListener() {
					/* (non-Javadoc)
					 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
					 */
					public void actionPerformed(ActionEvent e) {
						toggleBtnAction(e, selections);
					}
				}
		);
		innerBtnPanel.add(returnBtn);
		innerBtnPanel.add(selectBtn);
		innerBtnPanel.setAlignmentY(CENTER_ALIGNMENT);
		
		JPanel outerBtnPanel = new JPanel();
		outerBtnPanel.setLayout(new BoxLayout(outerBtnPanel, BoxLayout.Y_AXIS));
		outerBtnPanel.add(Box.createVerticalGlue());
		outerBtnPanel.add(innerBtnPanel);
		outerBtnPanel.add(Box.createVerticalGlue());
		
		
		DefaultListModel selectedModel = new DefaultListModel();
		selections = new JList(selectedModel);
		selections.setName(IMPORT_VARIABLE);
		selections.setVisibleRowCount(10);
		selections.setFixedCellWidth(150);
		selections.setFixedCellHeight(12);
		selections.setMinimumSize(new Dimension(150, 150));
		selections.addFocusListener(this);
		
		@SuppressWarnings("unused")
		DefaultListModel listModel = 
			(DefaultListModel) selections.getModel();	
		
		formular.setLayout(new FlowLayout());
		formular.add(chooseParametersPanel);
		formular.add(outerBtnPanel);
	    JScrollPane importVarScrollPane = new JScrollPane(selections);
	    importVarScrollPane.getVerticalScrollBar().setUnitIncrement(16);
		formular.add(importVarScrollPane);

		formular.setBorder(
				BorderFactory.createCompoundBorder(
						BorderFactory.createTitledBorder(EMPTY),
						BorderFactory.createEmptyBorder(5, 5, 5, 5)
				)
		);
		
        /*
         *	Wait for input
         */
		final String[] DIALOG_BUTTONS_TITLES	= {resourceBundle.getString(TSV_ACCEPT), resourceBundle.getString(TSV_CANCEL), resourceBundle.getString(TSV_HELP)};
		
		int retValue = 2;
	  	while (retValue == 2) 
	   	{
	  		retValue = JOptionPane.showOptionDialog(
    			((CStatsCtrl)al).getView().getAppFrame(), 
    			formular,
    			resourceBundle.getString(IMP_VAR_TITLE),
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, 
                DIALOG_BUTTONS_TITLES, 
                DIALOG_BUTTONS_TITLES[0]
            );
	  		
            if (retValue == 1) {
            	DefaultListModel listModel2 = (DefaultListModel) selections.getModel();
            	listModel2.clear();
            }
            
	  		if (retValue == 2) {
//	  			JOptionPane.showMessageDialog(_view.getAppFrame(), resourceBundle.getString(IMP_NO_HELP));
	  			callHelpBtn.doClick();
	  		}
    	}
	}

	/**
	 * @param e
	 * @param cell
	 * @param targetList
	 */
	protected void acceptBtnAction(ActionEvent e, Object cell, JList targetList) {		
		DefaultListModel listModel = (DefaultListModel) targetList.getModel();
		
		listModel.clear(); 
		
		listModel.addElement((Variable)cell);						
	}
	
	/**
	 * @param e
	 * @param sourceList
	 */
	@SuppressWarnings("deprecation")
	protected void toggleBtnAction(ActionEvent e, JList sourceList) {
		
		Object[] selected = sourceList.getSelectedValues();
		DefaultListModel listModel;
		
		listModel = (DefaultListModel) sourceList.getModel();
		for (int i=0; i < selected.length; i++) {
			listModel.removeElement(selected[i]);
		}
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
	 */
	@Override
	public void focusGained(FocusEvent e) {
		if (e.getSource() instanceof JList) {
			((JList)e.getSource()).setBackground(Color.yellow);
		}
		if (e.getSource() instanceof JComboBox) {
			((JComboBox)e.getSource()).setBackground(Color.yellow);
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
	 */
	@Override
	public void focusLost(FocusEvent e) {
		if (e.getSource() instanceof JList) {
			((JList)e.getSource()).setBackground(Color.white);
		}
		if (e.getSource() instanceof JComboBox) {
			((JComboBox)e.getSource()).setBackground(Color.white);
		}
	}

	/**
	 * @return
	 */
	public ArrayList<Variable> getImportedVariables() {
		ArrayList<Variable> variables = new ArrayList<Variable>();
		
		DefaultListModel listModel = (DefaultListModel) selections.getModel();
		for (int i=0; i < listModel.size(); i++) {
			variables.add((Variable)listModel.get(i));
		}
		
		return variables;
	}
	
}

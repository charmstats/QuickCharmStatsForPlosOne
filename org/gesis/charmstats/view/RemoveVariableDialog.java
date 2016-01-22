package org.gesis.charmstats.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import org.gesis.charmstats.ActionCommandText;
import org.gesis.charmstats.controller.CStatsCtrl;
import org.gesis.charmstats.model.Variable;

public class RemoveVariableDialog extends JFrame implements FocusListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String BUNDLE				= "org.gesis.charmstats.resources.DesktopBundle";
	
	public static final String RVC_TITLE			= "rvc_title";
	
	public static final String RVC_ACCEPT			= "rmc_accept";
	public static final String OVC_CANCEL			= "omc_cancel";
	public static final String OVC_HELP				= "omc_help";
	public static final String OVC_NO_HELP			= "omc_no_help";
	
	public static final String OVC_VARIABLE_LBL		= "ovc_variable_lbl";
	
	public static final String EMPTY 				= "";
	
	/*
	 *	Fields
	 */
	JPanel 		formular = new JPanel();
	
	JComboBox	variablesCB;
	
	JPanel 		varNotePane;
	JTextPane	varNote; 
	
	JButton			callHelpBtn;
	
	Locale 		  	currentLocale;
	ResourceBundle	resourceBundle;
	
	/*
	 *	Constructor
	 */
	/**
	 * @param variables
	 * @param locale
	 * @param f
	 */
	RemoveVariableDialog(List<Variable> variables, Locale locale, Font f, ActionListener al) {
		removeVariable(variables, locale, f, al);
	}
	
	/**
	 * @param variables
	 * @param locale
	 * @param f
	 */
	private void removeVariable(List<Variable> variables, Locale locale, Font f, ActionListener al) {
		Vector<Object> myVector = new Vector<Object>();
		
		currentLocale	= locale;
		resourceBundle	= ResourceBundle.getBundle(BUNDLE, currentLocale);
		
		callHelpBtn = new JButton();
		callHelpBtn.setActionCommand(ActionCommandText.CMD_HELP_HELP_OPEN_VARIABLE);
		callHelpBtn.addActionListener(al);
		callHelpBtn.setVisible(false);
		callHelpBtn.setEnabled(true);
		
		/* DoNothing, if no variables are available: */
		if ((!(variables != null)) ||
				(variables.size() < 1))
			return;
		
		this.setTitle(resourceBundle.getString(RVC_TITLE));
		
		Iterator<Variable> iterator = variables.iterator();
		myVector.add(EMPTY);
		while(iterator.hasNext()) {
			Variable v = iterator.next();
			
			myVector.add(v);
		}
		variablesCB = new JComboBox(myVector);
		variablesCB.setFont(f);
		variablesCB.addFocusListener(this);
		variablesCB.addActionListener(new java.awt.event.ActionListener() {
            /* (non-Javadoc)
             * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                variablesCBActionPerformed(evt);
            }
        });
		
		JPanel overallPanel = new JPanel();
		overallPanel.setLayout(new BoxLayout(overallPanel, BoxLayout.Y_AXIS));
		
		JPanel variablePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel variablesLbl = new JLabel(resourceBundle.getString(OVC_VARIABLE_LBL));
		variablesLbl.setFont(f);
		variablePanel.add(variablesLbl);
		variablePanel.add(variablesCB);
		
		varNote = new JTextPane();
		varNote.setContentType("text/html");
		varNote.setBounds(0, 0, 645, 100);
		
		varNotePane = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JScrollPane varNoteScrollPane = new JScrollPane(varNote);
		varNoteScrollPane.setPreferredSize(new Dimension(645, 100)); 
		varNoteScrollPane.getVerticalScrollBar().setUnitIncrement(16);
		varNotePane.add(varNoteScrollPane);		
				
		overallPanel.add(variablePanel);
		overallPanel.add(varNotePane);
				
		formular.setLayout(new FlowLayout());
		formular.add(overallPanel);

		formular.setBorder(
				BorderFactory.createCompoundBorder(
					BorderFactory.createTitledBorder(EMPTY),
					BorderFactory.createEmptyBorder(5, 5, 5, 5)
				)
		);
		
		final String[] DIALOG_BUTTONS_TITLES	= {resourceBundle.getString(RVC_ACCEPT), resourceBundle.getString(OVC_CANCEL), resourceBundle.getString(OVC_HELP)};
		
        /*
         *	Wait for input
         */
		int retValue = 2;
	  	while (retValue == 2) 
	   	{
	  		retValue = JOptionPane.showOptionDialog(		
	  			((CStatsCtrl)al).getView().getAppFrame(), // null, 
    			formular,
    			resourceBundle.getString(RVC_TITLE),
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, 
                DIALOG_BUTTONS_TITLES, 
                DIALOG_BUTTONS_TITLES[0]
	  		);
	  		
	  		if (retValue == 1) {
	  			variablesCB.setSelectedIndex(0);
	  	    }
	  		
	  		if (retValue == 2) {
//	  			JOptionPane.showMessageDialog(_view.getAppFrame(), resourceBundle.getString(OVC_NO_HELP));
	  			callHelpBtn.doClick();
	  		}
	  		
	  		/* Reset after CloseButton to Unselected State */
	  		if (retValue == -1) {
	  			variablesCB.setSelectedIndex(0);
	  		}
	   	}
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
	 */
	@Override
	public void focusGained(FocusEvent e) {
		if(e.getSource() instanceof JComboBox) {
			((JComboBox)e.getSource()).setBackground(Color.yellow);
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
	 */
	@Override
	public void focusLost(FocusEvent e) {
		if(e.getSource() instanceof JComboBox) {
			((JComboBox)e.getSource()).setBackground(Color.white);
		}
	}
	
    /**
     * @param evt
     */
    private void variablesCBActionPerformed(java.awt.event.ActionEvent evt) {
    	varNote.setText(EMPTY);
    	
    	if (evt.getSource() instanceof JComboBox) {
    		Object object = ((JComboBox)evt.getSource()).getSelectedItem();
    		
    		if (object instanceof Variable) {

    			String output = "<b>"+object.toString()+"</b> ";
    			String sqlString = Variable.getDraftSQLForStudy(((Variable)object).getEntityID());
    			String studyName = CStatsCtrl.searchForProjectName(sqlString);
    			output += "from <b>" + ((studyName.length() > 0) ? studyName : "unknown") + "; </b>";
    			
    			String measurementLevel = ((Variable)object).getLevel().toString();
    			output += "Level: <b>" + ((measurementLevel.length() > 0) ? measurementLevel : "unknown") + "; </b>";
    			
    			sqlString = Variable.getDraftSQLForValueList(((Variable)object).getEntityID());
    			String valueList = CStatsCtrl.searchForVariableValues(sqlString);
    			valueList = valueList.replace("<html>", "");
    			valueList = valueList.replace("</html>", "");
    			output += valueList;
    			
    			if ( (((Variable)object).getDefinition() != null) &&
    					(((Variable)object).getDefinition().getText() != null) &&
    					!((Variable)object).getDefinition().getText().isEmpty()
    					)
    				output += ((Variable)object).getDefinition().getText();
    			    			
    			varNote.setText(output);
    			varNote.setCaretPosition(0);
    		}
    	}
    }

	/**
	 * @return
	 */
	public Variable getChosenVariable() {
		Variable var = new Variable(); var.setEntityID(-1);
		
		if (variablesCB.getSelectedItem() != null) {
			if (variablesCB.getSelectedItem() instanceof Variable)
				var = (Variable)variablesCB.getSelectedItem();
		}
		
		return var;
	}

}


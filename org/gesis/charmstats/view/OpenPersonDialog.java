package org.gesis.charmstats.view;

import java.awt.Color;
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
import javax.swing.JTextArea;

import org.gesis.charmstats.ActionCommandText;
import org.gesis.charmstats.model.Person;

/**
 * 
 * @author	Martin Friedrichs
 * @since	0.9.2 / CharmStatsPro only
 *
 */
public class OpenPersonDialog extends JFrame implements FocusListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String BUNDLE				= "org.gesis.charmstats.resources.DesktopBundle";
	
	public static final String OPC_TITLE			= "opec_title";
	
	public static final String OPC_ACCEPT			= "omc_accept";
	public static final String OPC_CANCEL			= "omc_cancel";
	public static final String OPC_HELP				= "omc_help";
	public static final String OPC_NO_HELP			= "omc_no_help";
	
	public static final String OPC_PRSN_LBL			= "opec_prsn_lbl";
	
	public static final String EMPTY 				= "";
	
	/*
	 *	Fields
	 */
	JPanel 		formular = new JPanel();
	
	JComboBox	prsnsCB;
	
	JPanel 		prsnNotePane;
	JTextArea	prsnNote;
	
	JButton			callHelpBtn;
	
	Locale 		  	currentLocale;
	ResourceBundle	resourceBundle;
	
	/*
	 *	Constructor
	 */
	/**
	 * @param prsns
	 * @param locale
	 * @param f
	 */
	OpenPersonDialog(List<Person> prsns, Locale locale, Font f, ActionListener al) {
		openPerson(prsns, locale, f, al);
	}
	
	/**
	 * @param prsns
	 * @param locale
	 * @param f
	 */
	private void openPerson(List<Person> prsns, Locale locale, Font f, ActionListener al) {
		Vector<Object> myVector = new Vector<Object>();
		
		currentLocale	= locale;
		resourceBundle	= ResourceBundle.getBundle(BUNDLE, currentLocale);
		
		callHelpBtn = new JButton();
		callHelpBtn.setActionCommand(ActionCommandText.CMD_HELP_HELP_ADD_PERSON_OPEN);
		callHelpBtn.addActionListener(al);
		callHelpBtn.setVisible(false);
		callHelpBtn.setEnabled(true);
		
		/* DoNothing, if no persons are available: */
		if ((!(prsns != null)) ||
				(prsns.size() < 1))
			return;
		
		this.setTitle(resourceBundle.getString(OPC_TITLE));
		
		Iterator<Person> iterator = prsns.iterator();
		myVector.add(EMPTY);
		while(iterator.hasNext()) {
			Person p = iterator.next();
			
			myVector.add(p);
		}
		prsnsCB = new JComboBox(myVector);
		prsnsCB.setFont(f);
		prsnsCB.addFocusListener(this);
		prsnsCB.addActionListener(new java.awt.event.ActionListener() {
            /* (non-Javadoc)
             * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prsnsCBActionPerformed(evt);
            }
        });
		
		JPanel overallPanel = new JPanel();
		overallPanel.setLayout(new BoxLayout(overallPanel, BoxLayout.Y_AXIS));
		
		JPanel personPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel prsnsLbl = new JLabel(resourceBundle.getString(OPC_PRSN_LBL));
		prsnsLbl.setFont(f);
		personPanel.add(prsnsLbl);
		personPanel.add(prsnsCB);
		
		prsnNote = new JTextArea(5, 80);
		prsnNote.setEnabled(false);
		prsnNote.setLineWrap(true);
		prsnNote.setWrapStyleWord(true);
		prsnNotePane = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JScrollPane prsnNoteScrollPane = new JScrollPane(prsnNote);
		prsnNoteScrollPane.getVerticalScrollBar().setUnitIncrement(16);
		prsnNotePane.add(prsnNoteScrollPane);		
				
		overallPanel.add(personPanel);
		overallPanel.add(prsnNotePane);
				
		formular.setLayout(new FlowLayout());
		formular.add(overallPanel);

		formular.setBorder(
				BorderFactory.createCompoundBorder(
					BorderFactory.createTitledBorder(EMPTY),
					BorderFactory.createEmptyBorder(5, 5, 5, 5)
				)
		);
		
		final String[] DIALOG_BUTTONS_TITLES	= {resourceBundle.getString(OPC_ACCEPT), resourceBundle.getString(OPC_CANCEL), resourceBundle.getString(OPC_HELP)};
		
        /*
         *	Wait for input
         */
		int retValue = 2;
	  	while (retValue == 2) 
	   	{
	  		retValue = JOptionPane.showOptionDialog(		
    			null, 
    			formular,
    			resourceBundle.getString(OPC_TITLE),
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, 
                DIALOG_BUTTONS_TITLES, 
                DIALOG_BUTTONS_TITLES[0]
	  		);
	  		
	  		if (retValue == 1) {
	  			prsnsCB.setSelectedIndex(0);
	  	    }
	  		
	  		if (retValue == 2) {
//	  			JOptionPane.showMessageDialog(_view.getAppFrame(), resourceBundle.getString(OPC_NO_HELP));
	  			callHelpBtn.doClick();
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
    private void prsnsCBActionPerformed(java.awt.event.ActionEvent evt) {
    	prsnNote.setText(EMPTY);
    	
    	if (evt.getSource() instanceof JComboBox) {
    		Object object = ((JComboBox)evt.getSource()).getSelectedItem();
    		
    		if (object instanceof Person) {
    			prsnNote.setText(EMPTY);
    		}
    	}
    }

	/**
	 * @return
	 */
	public Person getChosenPerson() {
		Person prsn = new Person(); prsn.setEntityID(-1);
		
		if (prsnsCB.getSelectedItem() != null) {
			if (prsnsCB.getSelectedItem() instanceof Person)
				prsn = (Person)prsnsCB.getSelectedItem();
		}
		
		return prsn;
	}

}

package org.gesis.charmstats.view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Connection;
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
import org.gesis.charmstats.model.Project;
import org.gesis.charmstats.model.User;

/**
 * 
 * @author	Martin Friedrichs
 * @since	0.9.3
 *
 */
public class ReleaseLockDialog extends JFrame implements FocusListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String BUNDLE				= "org.gesis.charmstats.resources.DesktopBundle";
	
	public static final String RLC_TITLE			= "rlc_title";
	
	public static final String RLC_ACCEPT			= "tsv_accept";
	public static final String RLC_CANCEL			= "tsv_cancel";
	public static final String RLC_HELP				= "tsv_help";
	public static final String RLC_NO_HELP			= "omc_no_help";
	
	public static final String RLC_PAIRINGS_LBL		= "rlc_pairings_lbl";
	public static final String RELEASE_LOCK			= "ReleaseLock";
	
	public static final String EMPTY 				= "";
	
	/*
	 *	Fields
	 */
	JPanel 		formular = new JPanel();
	
	JComboBox	pairingsCB;
	
	JPanel 		pairNotePane;
	JTextArea	pairingNote;
	
	JButton			callHelpBtn;
	
	Locale 		  	currentLocale;
	ResourceBundle	resourceBundle;
	
	/*
	 *	Constructor
	 */
	/**
	 * @param projects
	 * @param users
	 * @param locale
	 * @param f
	 * @param con
	 */
	ReleaseLockDialog(List<Integer> projects, List<Integer> users, Locale locale, Font f, Connection con, ActionListener al) {
		releaseLock(projects, users, locale, f, con, al);
	}
	
	/**
	 * @param projects
	 * @param users
	 * @param locale
	 * @param f
	 * @param con
	 */
	private void releaseLock(List<Integer> projects, List<Integer> users, Locale locale, Font f, Connection con, ActionListener al) {
		Vector<Object> myVector = new Vector<Object>();
		int counter = 0;
		
		currentLocale	= locale;
		resourceBundle	= ResourceBundle.getBundle(BUNDLE, currentLocale);
		
		callHelpBtn = new JButton();
		callHelpBtn.setActionCommand(ActionCommandText.CMD_HELP_HELP_RELEASE_LOCK);
		callHelpBtn.addActionListener(al);
		callHelpBtn.setVisible(false);
		callHelpBtn.setEnabled(true);
		
		/* DoNothing, if no projects are available: */
		if ((!(projects != null)) ||
				(projects.size() < 1))
			return;
		
		this.setTitle(resourceBundle.getString(RLC_TITLE));
		
		Iterator<Integer> iterator = projects.iterator();
		myVector.add(EMPTY);
		
		while(iterator.hasNext()) {
			Integer i = iterator.next();
			Integer j = users.get(counter);
			
			String prjName = Project.searchForProjectName(i, con);
			String usrName = User.searchForUserName(j, con);
			
			myVector.add("["+ i +":"+ j +"] "+ prjName +":"+ usrName); counter++;
		}
		pairingsCB = new JComboBox(myVector);
		pairingsCB.setFont(f);
		pairingsCB.addFocusListener(this);
		pairingsCB.addActionListener(new java.awt.event.ActionListener() {
	        /* (non-Javadoc)
	         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	         */
	        public void actionPerformed(java.awt.event.ActionEvent evt) {
	            pairingsCBActionPerformed(evt);
	        }
	    });
		
		JPanel overallPanel = new JPanel();
		overallPanel.setLayout(new BoxLayout(overallPanel, BoxLayout.Y_AXIS));
		
		JPanel pairingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel pairingsLbl = new JLabel(resourceBundle.getString(RLC_PAIRINGS_LBL));
		pairingsLbl.setFont(f);
		pairingPanel.add(pairingsLbl);
		pairingPanel.add(pairingsCB);
		
		pairingNote = new JTextArea(5, 80);
		pairingNote.setEnabled(false);
		pairingNote.setLineWrap(true);
		pairingNote.setWrapStyleWord(true);
		pairNotePane = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JScrollPane pairNoteScrollPane = new JScrollPane(pairingNote);
		pairNoteScrollPane.getVerticalScrollBar().setUnitIncrement(16);
		pairNotePane.add(pairNoteScrollPane);		
				
		overallPanel.add(pairingPanel);
		overallPanel.add(pairNotePane);
				
		formular.setLayout(new FlowLayout());
		formular.add(overallPanel);
	
		formular.setBorder(
				BorderFactory.createCompoundBorder(
					BorderFactory.createTitledBorder(EMPTY),
					BorderFactory.createEmptyBorder(5, 5, 5, 5)
				)
		);
		
		final String[] DIALOG_BUTTONS_TITLES	= {resourceBundle.getString(RLC_ACCEPT), resourceBundle.getString(RLC_CANCEL), resourceBundle.getString(RLC_HELP)};
		
	    /*
	     *	Wait for input
	     */
		int retValue = 2;
	  	while (retValue == 2) 
	   	{
	  		retValue = JOptionPane.showOptionDialog(		
				null, 
				formular,
				resourceBundle.getString(RLC_TITLE),
	            JOptionPane.OK_CANCEL_OPTION,
	            JOptionPane.QUESTION_MESSAGE,
	            null, 
	            DIALOG_BUTTONS_TITLES, 
	            DIALOG_BUTTONS_TITLES[0]
	  		);
	  		
	  		if (retValue == 1) {
	  			pairingsCB.setSelectedIndex(0);
	  	    }
	  		if (retValue == 2) {
	  			callHelpBtn.doClick();
	  		}

	  		if (retValue == -1) {
	  			pairingsCB.setSelectedIndex(0);
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
	private void pairingsCBActionPerformed(java.awt.event.ActionEvent evt) {
		pairingNote.setText(EMPTY);
		
		if (evt.getSource() instanceof JComboBox) {
			Object object = ((JComboBox)evt.getSource()).getSelectedItem();
			
			if (object instanceof Project) {
				pairingNote.setText(((Project)object).loadProjectNotes());
			}
		}
	}
	
	/**
	 * @return
	 */
	public Integer getChosenPairing() {
		Integer pairing = -1;
		
		if (pairingsCB.getSelectedItem() != null) {
			if ((pairingsCB.getSelectedItem() instanceof String) &&
					((String)pairingsCB.getSelectedItem()).length() > 0)
				pairing = pairingsCB.getSelectedIndex();
		}
		
		return pairing;
	}

}


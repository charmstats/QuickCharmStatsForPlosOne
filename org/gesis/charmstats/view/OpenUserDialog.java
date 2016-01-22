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
import org.gesis.charmstats.model.User;

/**
 * 
 * @author	Martin Friedrichs
 * @since	0.9.2 / CharmStatsPro only
 *
 */
public class OpenUserDialog extends JFrame implements FocusListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String BUNDLE				= "org.gesis.charmstats.resources.DesktopBundle";
	
	public static final String OUC_TITLE			= "ouc_title";
	
	public static final String OUC_ACCEPT			= "omc_accept";
	public static final String OUC_CANCEL			= "omc_cancel";
	public static final String OUC_HELP				= "omc_help";
	public static final String OUC_NO_HELP			= "omc_no_help";
	
	public static final String OUC_USER_LBL			= "ouc_user_lbl";
	
	public static final String EMPTY 				= "";
	
	/*
	 *	Fields
	 */
	JPanel 		formular = new JPanel();
	
	JComboBox	usersCB;
	
	JPanel 		userNotePane;
	JTextArea	userNote;
	
	JButton			callHelpBtn;
	
	Locale 		  	currentLocale;
	ResourceBundle	resourceBundle;
	
	/*
	 *	Constructor
	 */
	/**
	 * @param users
	 * @param locale
	 * @param f
	 */
	OpenUserDialog(List<User> users, Locale locale, Font f, ActionListener al) {
		openUser(users, locale, f, al);
	}
	
	/**
	 * @param users
	 * @param locale
	 * @param f
	 */
	private void openUser(List<User> users, Locale locale, Font f, ActionListener al) {
		Vector<Object> myVector = new Vector<Object>();
		
		currentLocale	= locale;
		resourceBundle	= ResourceBundle.getBundle(BUNDLE, currentLocale);
		
		callHelpBtn = new JButton();
		callHelpBtn.setActionCommand(ActionCommandText.CMD_HELP_HELP_ADD_USER_OPEN);
		callHelpBtn.addActionListener(al);
		callHelpBtn.setVisible(false);
		callHelpBtn.setEnabled(true);
		
		/* DoNothing, if no users are available: */
		if ((!(users != null)) ||
				(users.size() < 1))
			return;
		
		this.setTitle(resourceBundle.getString(OUC_TITLE));
		
		Iterator<User> iterator = users.iterator();
		myVector.add(EMPTY);
		while(iterator.hasNext()) {
			User u = iterator.next();
			
			myVector.add(u);
		}
		usersCB = new JComboBox(myVector);
		usersCB.setFont(f);
		usersCB.addFocusListener(this);
		usersCB.addActionListener(new java.awt.event.ActionListener() {
            /* (non-Javadoc)
             * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usersCBActionPerformed(evt);
            }
        });
		
		JPanel overallPanel = new JPanel();
		overallPanel.setLayout(new BoxLayout(overallPanel, BoxLayout.Y_AXIS));
		
		JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel usersLbl = new JLabel(resourceBundle.getString(OUC_USER_LBL));
		usersLbl.setFont(f);
		userPanel.add(usersLbl);
		userPanel.add(usersCB);
		
		userNote = new JTextArea(5, 80);
		userNote.setEnabled(false);
		userNote.setLineWrap(true);
		userNote.setWrapStyleWord(true);
		userNotePane = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JScrollPane usrNoteScrollPane = new JScrollPane(userNote);
		usrNoteScrollPane.getVerticalScrollBar().setUnitIncrement(16);
		userNotePane.add(usrNoteScrollPane);		
				
		overallPanel.add(userPanel);
		overallPanel.add(userNotePane);
				
		formular.setLayout(new FlowLayout());
		formular.add(overallPanel);

		formular.setBorder(
				BorderFactory.createCompoundBorder(
					BorderFactory.createTitledBorder(EMPTY),
					BorderFactory.createEmptyBorder(5, 5, 5, 5)
				)
		);
		
		final String[] DIALOG_BUTTONS_TITLES	= {resourceBundle.getString(OUC_ACCEPT), resourceBundle.getString(OUC_CANCEL), resourceBundle.getString(OUC_HELP)};
		
        /*
         *	Wait for input
         */
		int retValue = 2;
	  	while (retValue == 2) 
	   	{
	  		retValue = JOptionPane.showOptionDialog(		
    			null, 
    			formular,
    			resourceBundle.getString(OUC_TITLE),
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, 
                DIALOG_BUTTONS_TITLES, 
                DIALOG_BUTTONS_TITLES[0]
	  		);
	  		
	  		if (retValue == 1) {
	  			usersCB.setSelectedIndex(0);
	  	    }
	  		
	  		if (retValue == 2) {
//	  			JOptionPane.showMessageDialog(_view.getAppFrame(), resourceBundle.getString(OUC_NO_HELP));
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
    private void usersCBActionPerformed(java.awt.event.ActionEvent evt) {
    	userNote.setText(EMPTY);
    	
    	if (evt.getSource() instanceof JComboBox) {
    		Object object = ((JComboBox)evt.getSource()).getSelectedItem();
    		
    		if (object instanceof User) {
    			userNote.setText(EMPTY);
    		}
    	}
    }

	/**
	 * @return
	 */
	public User getChosenUser() {
		User usr = new User(); usr.setEntityID(-1);
		
		if (usersCB.getSelectedItem() != null) {
			if (usersCB.getSelectedItem() instanceof User)
				usr = (User)usersCB.getSelectedItem();
		}
		
		return usr;
	}

}

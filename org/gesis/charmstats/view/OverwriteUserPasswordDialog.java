package org.gesis.charmstats.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.gesis.charmstats.ActionCommandText;
import org.gesis.charmstats.controller.CStatsCtrl;
import org.gesis.charmstats.model.User;

/**
 * 
 * @author	Martin Friedrichs
 * @since	0.9.3
 * 
 */
public class OverwriteUserPasswordDialog extends JFrame implements FocusListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String BUNDLE						= "org.gesis.charmstats.resources.DesktopBundle";
	public static final String MAN_PWD_TITLE				= "man_pwd_title";
	public static final String MAN_PWD_USERNAME				= "man_pwd_username";
	public static final String MAN_PWD_NEW_PASSWORD			= "man_pwd_new_password";
	public static final String MAN_PWD_RPT_PASSWORD			= "man_pwd_rpt_password";
	public static final String ME_NO_HELP					= "me_no_help";
	
	public static final String NO_USR_SELECTED				= "man_no_usr_selected";
	public static final String NO_PASSWORD					= "man_pwd_no_pwd";
	public static final String WRONG_PASSWORD				= "man_pwd_wrong_pwd";
	
	public static final String TSV_ACCEPT					= "tsv_accept";
	public static final String TSV_CANCEL					= "tsv_cancel";
	public static final String TSV_HELP						= "tsv_help";
	
	public static final String EMPTY_STRING 				= "";
	public static final String PROTOTYPE_DISPLAY_VALUE		= "XXXXXXXXXXXXXXXXXXXX";
	
	/*
	 *	Fields
	 */
	JPanel 			formular;
	JPanel			formularContent;
	
	JLabel			usrNameLbl;
	JComboBox		usrCB;
	JLabel			usrPasswordLbl;
	JLabel			rptPasswordLbl;
	JPasswordField	usrPasswordPWF;
	JPasswordField	rptPasswordPWF;
	
	JButton			callHelpBtn;
	
	Locale 		  	currentLocale;
	ResourceBundle	resourceBundle;
	java.sql.Connection		con;
	
	Boolean			isAccepted = false;
	User 			none;
	
	/*
	 *	Constructor
	 */
	/**
	 * @param locale
	 * @param _usr
	 * @param _usrList
	 * @param _connection
	 * @param f
	 */
	public OverwriteUserPasswordDialog(Locale locale, User _usr, List<User> _usrList, Connection _connection, Font f, ActionListener al) {
		this.con = _connection;
		overwritePassword(_usr, _usrList, locale, f, al);		
	}
	
	/*
	 *	Methods
	 */
	/**
	 * @param _usr
	 * @param _usrList
	 * @param locale
	 * @param f
	 */
	private void overwritePassword(User _usr, List<User> _usrList, Locale locale, Font f, ActionListener al) {
		currentLocale	= locale;
		resourceBundle	= ResourceBundle.getBundle(BUNDLE, currentLocale);
		
		Vector<User>	usrVector	= new Vector<User>();
		
		setTitle(resourceBundle.getString(MAN_PWD_TITLE));
		
		formular = new JPanel();
		formularContent = new JPanel(false);
		
		callHelpBtn = new JButton();
		callHelpBtn.setActionCommand(ActionCommandText.CMD_HELP_HELP_OVERWRITE_PASSWORD);
		callHelpBtn.addActionListener(al);
		callHelpBtn.setVisible(false);
		callHelpBtn.setEnabled(true);
		
		usrNameLbl = new JLabel(resourceBundle.getString(MAN_PWD_USERNAME));
		usrNameLbl.setFont(f);
		Iterator<User> iterator_u = _usrList.iterator();
		none = new User();
			none.setName(EMPTY_STRING);
		usrVector.add(none);
		while (iterator_u.hasNext()) {
			User u = iterator_u.next();
			
			usrVector.add(u);
		}
		usrCB = new JComboBox(usrVector);
		usrCB.setFont(f);
		//AutoCompleteDecorator.decorate(userCB);
		//userCB.isEditable();
		usrCB.setPrototypeDisplayValue(PROTOTYPE_DISPLAY_VALUE);
		usrCB.addFocusListener(this);
		
		usrPasswordLbl = new JLabel(resourceBundle.getString(MAN_PWD_NEW_PASSWORD)); 
		usrPasswordLbl.setFont(f);
		usrPasswordPWF = new JPasswordField(10);
		usrPasswordPWF.setFont(f);
		usrPasswordPWF.addFocusListener(this);
		
		rptPasswordLbl = new JLabel(resourceBundle.getString(MAN_PWD_RPT_PASSWORD));
		rptPasswordLbl.setFont(f);
		rptPasswordPWF = new JPasswordField(10);
		rptPasswordPWF.setFont(f);
		rptPasswordPWF.addFocusListener(this);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		formularContent.setLayout(gridBagLayout);
		
		JLabel[] loginTextControlLabels = {usrNameLbl, usrPasswordLbl, rptPasswordLbl};
		Component[] loginTextControls = {usrCB, usrPasswordPWF, rptPasswordPWF};
		
		addLabelTextControlAsRows(loginTextControlLabels,
								  loginTextControls,
								  gridBagLayout,
								  formularContent);
		
		formular.setLayout(new FlowLayout());
		formular.add(formularContent);
		
		formular.setBorder(
				BorderFactory.createCompoundBorder(
						BorderFactory.createTitledBorder(EMPTY_STRING),
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
	    			null, 
	    			formular,
	    			resourceBundle.getString(MAN_PWD_TITLE),
	                JOptionPane.OK_CANCEL_OPTION,
	                JOptionPane.QUESTION_MESSAGE,
	                null, 
	                DIALOG_BUTTONS_TITLES, 
	                DIALOG_BUTTONS_TITLES[0]
	        	); 
	  		
	  		if (retValue == 2) {
//	  			JOptionPane.showMessageDialog(_view.getAppFrame(), resourceBundle.getString(ME_NO_HELP));
	  			callHelpBtn.doClick();
	  		}
			
	  		if (retValue == 0) {
	  			isAccepted = true;
	  			
	  			if (none.equals(usrCB.getSelectedItem())) {
					JOptionPane.showMessageDialog(((CStatsCtrl)al).getView().getAppFrame(), resourceBundle.getString(NO_USR_SELECTED)); retValue = 2;
					isAccepted = false;
	  			} else
	  				_usr.setEntityID(((User)usrCB.getSelectedItem()).getEntityID());
	  			
				String password_new = new String(usrPasswordPWF.getPassword());
				String password_rpt = new String(rptPasswordPWF.getPassword());
							
				if ((password_new.isEmpty()) ||
						(password_rpt.isEmpty())) {
					JOptionPane.showMessageDialog(((CStatsCtrl)al).getView().getAppFrame(), resourceBundle.getString(NO_PASSWORD)); retValue = 2;
					isAccepted = false;
				}
				
				if (!password_new.equals(password_rpt)) {
					JOptionPane.showMessageDialog(((CStatsCtrl)al).getView().getAppFrame(), resourceBundle.getString(WRONG_PASSWORD)); retValue = 2;
					isAccepted = false;
				}
	  		}	 
    	}
	  	
	}

	/**
	 * @return
	 */
	public Boolean isAccepted() {
		return isAccepted;
	}

	/**
	 * @return
	 */
	public String getPassword() {
		return new String(usrPasswordPWF.getPassword());
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
	
	/* (non-Javadoc)
	 * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
	 */
	@Override
	public void focusGained(FocusEvent e) {
		if(e.getSource() instanceof JTextField) {
			((JTextField)e.getSource()).setBackground(Color.yellow);
		}	
	}

	/* (non-Javadoc)
	 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
	 */
	@Override
	public void focusLost(FocusEvent e) {
		if(e.getSource() instanceof JTextField) {
			((JTextField)e.getSource()).setBackground(Color.white);
		}
	}
}

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
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.gesis.charmstats.ActionCommandText;
import org.gesis.charmstats.LoginPreference;
import org.gesis.charmstats.controller.CStatsCtrl;
import org.gesis.charmstats.model.User;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class LoginDialog  extends JDialog implements FocusListener{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String BUNDLE			= "org.gesis.charmstats.resources.DesktopBundle";
	
    static final String LD_DIALOG_TITLE 		= "ld_dialog_title";
	static final String LD_USERNAME_LABEL		= "ld_username_label";
	static final String LD_PASSWORD_LABEL		= "ld_password_label";
	static final String LD_REMEMBER_ME			= "ld_remember_me";
	public static final String ME_NO_HELP		= "me_no_help";
	
	public static final String LD_ACCEPT		= "ld_accept";
	public static final String LD_CANCEL		= "tsv_cancel";
	public static final String LD_HELP			= "tsv_help";
	
	/*
	 *	Fields
	 */
	private User loginUser = User.getAnonymousUser();
	
	private LoginPreference pref;
	
	JButton			callHelpBtn;
	
	Locale 		  	currentLocale;
	ResourceBundle	resourceBundle;
	Font			currentFont;
	
	/*
	 *	Constructor
	 */
    /**
     * @param pref
     * @param locale
     * @param f
     */
    public LoginDialog(LoginPreference pref, Locale locale, Font f, ActionListener al) {
		currentLocale	= locale;
		resourceBundle	= ResourceBundle.getBundle(BUNDLE, currentLocale);
		currentFont		= f;
		
		this.pref = pref;
    	
        getLoginInput(al);
    }
    
	/*
	 *	Methods
	 */
    /**
     * 
     */
    void getLoginInput(ActionListener al) {
    	Font font = new Font("Lucida Sans", Font.BOLD, currentFont.getSize());//12);
    
		callHelpBtn = new JButton();
		callHelpBtn.setActionCommand(ActionCommandText.CMD_HELP_HELP_LOGIN);
		callHelpBtn.addActionListener(al);
		callHelpBtn.setVisible(false);
		callHelpBtn.setEnabled(true);
		
    	JLabel usernameLbl = new JLabel(resourceBundle.getString(LD_USERNAME_LABEL), JLabel.RIGHT);
    	usernameLbl.setFont(font);
    	JLabel passwordLbl = new JLabel(resourceBundle.getString(LD_PASSWORD_LABEL), JLabel.RIGHT);
    	passwordLbl.setFont(font);
    	
    	JPanel rememberPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
    	JCheckBox rememberMeCBox = new JCheckBox(resourceBundle.getString(LD_REMEMBER_ME));
    	rememberMeCBox.setFont(currentFont);
    	rememberMeCBox.setSelected(pref.getRememberMe());
    	rememberPane.add(rememberMeCBox);
    	
    	JTextField usernameTF = new JTextField(10);
    	usernameTF.setText(pref.getName());
    	usernameTF.setFont(font);
    	usernameTF.addFocusListener(this);
    	usernameTF.requestFocusInWindow();
    	JTextField passwordPwdF = new JPasswordField(10);
    	passwordPwdF.setText(pref.getPassword());
    	passwordPwdF.setFont(font);
    	passwordPwdF.addFocusListener(this);
    	
		GridBagLayout gridBagLayout = new GridBagLayout();
		JPanel loginPane = new JPanel(false);
		loginPane.setLayout(gridBagLayout);
		
		JLabel[] loginTextControlLabels = {usernameLbl, passwordLbl};
		Component[] loginTextControls = {usernameTF, passwordPwdF};
		
		addLabelTextControlAsRows(loginTextControlLabels,
								  loginTextControls,
								  gridBagLayout,
								  loginPane);

		final String[] DIALOG_BUTTONS_TITLES	= {resourceBundle.getString(LD_ACCEPT), resourceBundle.getString(LD_CANCEL), resourceBundle.getString(LD_HELP)};
		
		JPanel fullLoginPane = new JPanel(false);
		fullLoginPane.setLayout(new BorderLayout());
		fullLoginPane.add(loginPane, BorderLayout.CENTER);
		fullLoginPane.add(rememberPane, BorderLayout.SOUTH);
				
		int retValue = 2;
	  	while (retValue == 2)
	  	{ 
	  		retValue = JOptionPane.showOptionDialog(
    			((CStatsCtrl)al).getView().getAppFrame(), // null, 
    			fullLoginPane,
    			resourceBundle.getString(LD_DIALOG_TITLE),
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, 
                DIALOG_BUTTONS_TITLES, 
                usernameTF /*DIALOG_BUTTONS_TITLES[0]*/
        	); 
	  		
	  		if (retValue == 2) {
//	  			JOptionPane.showMessageDialog(_view.getAppFrame(), resourceBundle.getString(ME_NO_HELP));
	  			callHelpBtn.doClick();
	  		}
	  		
	  		if (retValue == 1)
	  			return;
	  	}
    	    	
    	loginUser = new User();
        loginUser.setName(usernameTF.getText());
        loginUser.setPassword(passwordPwdF.getText());
        
        if (rememberMeCBox.isSelected())
        	pref.setPreference(rememberMeCBox.isSelected(), usernameTF.getText(), passwordPwdF.getText());
        else
        	pref.setPreference(false, "", "");
        
    }
    
	/**
	 * @return
	 */
	public User getLoginUser() {
		return loginUser;
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
	public void focusGained(FocusEvent e){
		if(e.getSource() instanceof JTextField) {
			((JTextField)e.getSource()).setBackground(Color.yellow);
		}
	}
	/* (non-Javadoc)
	 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
	 */
	public void focusLost(FocusEvent e){
		if(e.getSource() instanceof JTextField) {
			((JTextField)e.getSource()).setBackground(Color.white);
		}
	}
}

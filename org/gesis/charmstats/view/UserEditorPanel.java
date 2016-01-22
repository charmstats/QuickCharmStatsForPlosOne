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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.gesis.charmstats.ActionCommandText;
import org.gesis.charmstats.controller.CStatsCtrl;
import org.gesis.charmstats.model.Person;
import org.gesis.charmstats.model.User;
import org.gesis.charmstats.model.UserRole;

/**
 * 
 * @author	Martin Friedrichs
 * @since	0.9.2
 *
 */
public class UserEditorPanel extends JPanel implements FocusListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String BUNDLE						= "org.gesis.charmstats.resources.DesktopBundle";
	
	public static final String MAN_USR_TITLE				= "edit_usr_title";
	public static final String MAN_USR_PERSON				= "man_usr_person";
	public static final String MAN_USR_USERNAME				= "man_usr_username";
	public static final String MAN_USR_PASSWORD				= "man_usr_password";
	public static final String MAN_RPT_PASSWORD				= "man_rpt_password";
	public static final String MAN_USR_ROLE					= "man_user_role";
	public static final String CRE_USR_TAB					= "cre_usr_tab";
	public static final String MAN_USR_TAB					= "man_usr_tab";
	public static final String MAN_USR_ACCEPT				= "man_usr_accept";
	public static final String NO_PASSWORD					= "man_usr_no_pwd";
	public static final String WRONG_PASSWORD				= "man_usr_wrong_pwd";
	public static final String NO_PERSON					= "man_usr_no_person";
	public static final String NO_USR_NAME					= "man_usr_no_usr_name";
	public static final String NO_USR_ROLE					= "man_usr_no_usr_role";
	public static final String MAN_USR_ADD_PERSON			= "man_usr_add_person";
	public static final String ME_RESET						= "me_reset";
	public static final String ME_NO_HELP					= "me_no_help";
	
	public static final String TSV_ACCEPT					= "man_usr_accept";
	public static final String TSV_CANCEL					= "tsv_cancel";
	public static final String TSV_HELP						= "tsv_help";
	
	public static final String EMPTY_STRING 				= "";
	public static final String PROTOTYPE_DISPLAY_VALUE		= "XXXXXXXXXXXXXXXXXXXX";
	
	
	/*
	 *	Fields
	 */
	JPanel			overallPanel;
	JPanel 			formular;
	
	JPanel			editUsrPane;
	JPanel			addPersonPane;
	
	JLabel			personLbl;
	JComboBox		personsCB;
	JButton			addPersonBtn;
	JLabel			usrNameLbl;
	JTextField		usrNameTF;
//	JLabel			usrPasswordLbl;
//	JLabel			rptPasswordLbl;
//	JPasswordField	usrPasswordPWF;
//	JPasswordField	rptPasswordPWF;
	JLabel			usrRoleLbl;
	JComboBox		usrRolesCB;
	
	JPanel			buttonPanel;
	private JButton	resetBtn;
	
	JButton			callHelpBtn;
	
	static Random generator = new Random();
	
	Locale 		  	currentLocale;
	ResourceBundle	resourceBundle;
	Font			currentFont;
	java.sql.Connection		con;
	
	ArrayList<Person>	personList;
	
	final ActionListener fal;
	
	Boolean			isAccepted = false;
	
	User			user;

	/*
	 *	Constructor
	 */
	/**
	 * @param usr
	 * @param prsnList
	 * @param _connection
	 * @param locale
	 * @param f
	 */
	public UserEditorPanel(User usr, ArrayList<Person> prsnList, java.sql.Connection _connection, Locale locale, Font f, ActionListener al) {
		this.con = _connection;
		this.currentFont = f;
		
		this.user = usr;
		
		fal = al;
		
		editUser(usr, prsnList, locale, f, fal);
	}

	/*
	 *	Methods
	 */
	/**
	 * @param usr
	 * @param prsnList
	 * @param locale
	 * @param f
	 */
	private void editUser(User usr, ArrayList<Person> prsnList, Locale locale, Font f, ActionListener al) {
		Vector<UserRole>userRoleVector	= new Vector<UserRole>();
		Vector<Person>	personVector	= new Vector<Person>();
		
		currentLocale	= locale;
		resourceBundle	= ResourceBundle.getBundle(BUNDLE, currentLocale);
		
		personList		= prsnList;
		
		formular		= new JPanel();
		
		callHelpBtn = new JButton();
		callHelpBtn.setActionCommand(ActionCommandText.CMD_HELP_HELP_EDIT_USER);
		callHelpBtn.addActionListener(al);
		callHelpBtn.setVisible(false);
		callHelpBtn.setEnabled(true);
		
		personLbl		= new JLabel(resourceBundle.getString(MAN_USR_PERSON));
		personLbl.setFont(f);
		Iterator<Person> iterator_p = prsnList.iterator();
		Person none = new Person();
		none.setName(EMPTY_STRING);
		personVector.add(none);
		while (iterator_p.hasNext()) {
			Person p = iterator_p.next();
			
			personVector.add(p);
		}
		
		addPersonPane = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
	
		personsCB = new JComboBox(personVector);
		personsCB.setFont(f);
		personsCB.setPrototypeDisplayValue(PROTOTYPE_DISPLAY_VALUE);
		personsCB.addFocusListener(this);
		
		addPersonBtn = new JButton(resourceBundle.getString(MAN_USR_ADD_PERSON));
		addPersonBtn.setFont(f);
		addPersonBtn.addActionListener(new java.awt.event.ActionListener() {
			/* (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addPersonBtnActionPerformed(con, fal);
			}
		});
		
		addPersonPane.add(personsCB);
		addPersonPane.add(addPersonBtn);
		
		usrNameLbl = new JLabel(resourceBundle.getString(MAN_USR_USERNAME));
		usrNameLbl.setFont(f);
		usrNameTF = new JTextField(10);
		usrNameTF.setFont(f);
		usrNameTF.addFocusListener(this);
		
//		usrPasswordLbl = new JLabel(resourceBundle.getString(MAN_USR_PASSWORD));
//		usrPasswordLbl.setFont(f);
//		usrPasswordPWF = new JPasswordField(10);
//		usrPasswordPWF.setFont(f);
//		usrPasswordPWF.addFocusListener(this);
//		
//		rptPasswordLbl = new JLabel(resourceBundle.getString(MAN_RPT_PASSWORD));
//		rptPasswordLbl.setFont(f);
//		rptPasswordPWF = new JPasswordField(10);
//		rptPasswordPWF.setFont(f);
//		rptPasswordPWF.addFocusListener(this);
		
		usrRoleLbl = new JLabel(resourceBundle.getString(MAN_USR_ROLE));
		usrRoleLbl.setFont(f);
		UserRole[] userRoles = UserRole.values();
		ArrayList<UserRole> userRoleList = new ArrayList<UserRole>();
		Collections.addAll(userRoleList, userRoles); 
		Iterator<UserRole> iterator_u = userRoleList.iterator();	
		while (iterator_u.hasNext()) {
			UserRole u = iterator_u.next();
			u.setLocale(currentLocale);
			
			userRoleVector.add(u);
		}
		usrRolesCB = new JComboBox(userRoleVector);
		usrRolesCB.setFont(f);
		usrRolesCB.setPrototypeDisplayValue(PROTOTYPE_DISPLAY_VALUE);
		usrRolesCB.addFocusListener(this);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		
		editUsrPane = new JPanel(false);
		editUsrPane.setLayout(gridBagLayout);
		
		JLabel[] loginTextControlLabels = {personLbl, usrNameLbl, usrRoleLbl}; //, usrPasswordLbl, rptPasswordLbl};
		Component[] loginTextControls = {addPersonPane, usrNameTF, usrRolesCB}; //, usrPasswordPWF, rptPasswordPWF};
		
		addLabelTextControlAsRows(loginTextControlLabels,
								  loginTextControls,
								  gridBagLayout,
								  editUsrPane);
				
		formular.setLayout(new FlowLayout());
		formular.add(editUsrPane);
		
		formular.setBorder(
				BorderFactory.createCompoundBorder(
						BorderFactory.createTitledBorder(EMPTY_STRING),
						BorderFactory.createEmptyBorder(5, 5, 5, 5)
				)
		);
		
		resetBtn = new JButton(resourceBundle.getString(ME_RESET));
		resetBtn.setFont(currentFont);
		resetBtn.addActionListener( 
				new ActionListener() {
					/* (non-Javadoc)
					 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
					 */
					public void actionPerformed(ActionEvent e) {
						resetBtnAction(e);
					}
				}
		);
		buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(resetBtn);
		
		overallPanel = new JPanel();
		overallPanel.setLayout(new BorderLayout());
		overallPanel.add(formular, BorderLayout.CENTER);
		overallPanel.add(buttonPanel, BorderLayout.SOUTH);	
		
		fillModel();
	}
	
	/**
	 * @param e
	 */
	protected void resetBtnAction(ActionEvent e) {
		fillModel(); 
	}
	
	/**
	 * @return
	 */
	public Boolean isAccepted() {
		return isAccepted;
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
		if(e.getSource() instanceof JComboBox) {
			((JComboBox)e.getSource()).setBackground(Color.yellow);
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
		if(e.getSource() instanceof JComboBox) {
			((JComboBox)e.getSource()).setBackground(Color.white);
		}
	}
	
	MouseListener acceptMouseListener = new MouseAdapter() {
		/* (non-Javadoc)
		 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
		 */
		@SuppressWarnings("unused")
		public void mouseClicked(MouseEvent mouseEvent) {
			Person 		person;
//			String 		password;
			String 		userName;
			UserRole	userRole;
			
//			String password_inp = new String(usrPasswordPWF.getPassword());
//			String password_rpt = new String(rptPasswordPWF.getPassword());
//			
//			if ((password_inp.isEmpty()) ||
//					(password_rpt.isEmpty())) {
//				JOptionPane.showMessageDialog(_view.getAppFrame(), resourceBundle.getString(NO_PASSWORD)); return;
//			}
//			
//			if (!password_inp.equals(password_rpt)) {
//				JOptionPane.showMessageDialog(_view.getAppFrame(), resourceBundle.getString(WRONG_PASSWORD)); return;
//			}
//			
//			password = password_inp;
			
			if (personsCB.getSelectedIndex() < 1) {
				JOptionPane.showMessageDialog(((CStatsCtrl)fal).getView().getAppFrame(), resourceBundle.getString(NO_PERSON)); return;
			}
			
			if (personsCB.getSelectedIndex() > 0) {
				person = (Person)personsCB.getSelectedItem();
			} 
			
			if (usrNameTF.getText().isEmpty()) {
				JOptionPane.showMessageDialog(((CStatsCtrl)fal).getView().getAppFrame(), resourceBundle.getString(NO_USR_NAME)); return;
			}
				
			userName = usrNameTF.getText();
					
			if (usrRolesCB.getSelectedIndex() < 1) {
				JOptionPane.showMessageDialog(((CStatsCtrl)fal).getView().getAppFrame(), resourceBundle.getString(NO_USR_ROLE)); return;
			}
					
			userRole = (UserRole)usrRolesCB.getSelectedItem();
		}
	};
	
	public static int generateNegID() {
		int id = generator.nextInt();
		while (id >= -1) {
			id = generator.nextInt();
		}
		return id;
	}

	/**
	 * @param con
	 */
	private void addPersonBtnActionPerformed(java.sql.Connection con, ActionListener al) {
	   AddPersonDialog im = new AddPersonDialog(personList, currentLocale, currentFont, al);
	   
	   if (im.isAccepted()) {	   
		   Person newPerson = im.getAddedPerson();
		   Boolean storeStatus = newPerson.entityStore(con);
		
		   DefaultComboBoxModel model = (DefaultComboBoxModel) personsCB.getModel();
		   model.addElement(newPerson);
		   personsCB.setModel(model);
		
			if (storeStatus)
				try {
					con.commit();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(
							null, e.getMessage(), "SQLException:",  JOptionPane.ERROR_MESSAGE);
					  	
					/* DEMO ONLY */
					System.err.println("SQLException: " + e.getMessage());
					e.printStackTrace();
				}
	   	}
		
	}
	
	/**
	 * 
	 */
	public void goEdit() {
	       /*
         *	Wait for input
         */
		final String[] DIALOG_BUTTONS_TITLES	= {resourceBundle.getString(TSV_ACCEPT), resourceBundle.getString(TSV_CANCEL), resourceBundle.getString(TSV_HELP)};
		
		int retValue = 2;
	  	while (retValue == 2)
	  	{ 
	  		retValue = JOptionPane.showOptionDialog(
    			null, 
    			overallPanel,
    			resourceBundle.getString(MAN_USR_TITLE),
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
	  		
			if (personsCB.getSelectedIndex() < 1) {
				JOptionPane.showMessageDialog(((CStatsCtrl)fal).getView().getAppFrame(), resourceBundle.getString(NO_PERSON)); retValue = 2;
			}
			
			if (usrNameTF.getText().isEmpty()) {
				JOptionPane.showMessageDialog(((CStatsCtrl)fal).getView().getAppFrame(), resourceBundle.getString(NO_USR_NAME)); retValue = 2;
			}
			
			if (usrRolesCB.getSelectedIndex() < 1) {
				JOptionPane.showMessageDialog(((CStatsCtrl)fal).getView().getAppFrame(), resourceBundle.getString(NO_USR_ROLE)); retValue = 2;
			}
			
	  		if (retValue == 0) {
	  			saveChanges();
	  		}	 
			
    		isAccepted = true;
    	}	
	}
	
	/**
	 * 
	 */
	private void fillModel() {
		/* User */
		for (int i=0; i<personsCB.getComponentCount(); i++) {
			Object item = personsCB.getItemAt(i);
			
			if (item instanceof Person) {
				if ((((Person)item ).getEntityID()) == user.getPerson().getEntityID())
					personsCB.setSelectedIndex(i);
			}
		}
		usrNameTF.setText(user.getName());
		usrRolesCB.setSelectedItem(user.getUserRole());
	}
	
	/**
	 * 
	 */
	private void saveChanges() {
		/* User */
		user.setPerson((Person)personsCB.getSelectedItem());
		user.setName(usrNameTF.getText());
		user.setUserRole((UserRole)this.usrRolesCB.getSelectedItem());
	}

}

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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.gesis.charmstats.ActionCommandText;
import org.gesis.charmstats.controller.CStatsCtrl;
import org.gesis.charmstats.model.Person;
import org.gesis.charmstats.model.PersonRole;
import org.gesis.charmstats.model.PersonType;

/**
 * 
 * @author Martin Friedrichs
 * @since	0.9.2 / CharmStatsPro only
 *
 */
public class AddPersonDialog extends JFrame implements FocusListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String BUNDLE						= "org.gesis.charmstats.resources.DesktopBundle";
	
	public static final String MAN_PRSN_TITLE				= "man_prsn_title";
	public static final String CRE_PRSN_TAB					= "cre_prsn_tab";
	public static final String MAN_PRSN_TAB					= "man_prsn_tab";
	public static final String MAN_PRSN_NAME				= "man_prsn_name";
	public static final String MAN_PRSN_TYPE				= "man_prsn_type";
	public static final String MAN_PRSN_ROLE				= "man_prsn_role";
	public static final String MAN_PRSN_COUNTRY				= "man_prsn_country";
	public static final String MAN_PRSN_CITY				= "man_prsn_city";
	public static final String MAN_PRSN_ZIP_CODE			= "man_prsn_zip_code";
	public static final String MAN_PRSN_STREET				= "man_prsn_street";
	public static final String MAN_PRSN_STREET_NUMBER		= "man_prsn_street_number";
	public static final String MAN_PRSN_PHONE				= "man_prsn_phone";
	public static final String MAN_PRSN_EMAIL				= "man_prsn_email";
	public static final String MAN_PRSN_INSTANT_MESSAGING	= "man_prsn_instant_messaging";
	public static final String MAN_PRSN_DATE_OF_BIRTH		= "man_prsn_date_of_birth";
	public static final String MAN_PRSN_PLACE_OF_BIRTH		= "man_prsn_place_of_birth";
	public static final String MAN_PRSN_DEPARTMENT			= "man_prsn_department";
	public static final String MAN_PRSN_LOGO_URI			= "man_prsn_logo_uri";	
	public static final String NO_PRSN_NAME					= "man_prsn_no_prsn_name";
	public static final String NO_PRSN_TYPE					= "man_prsn_no_prsn_type";
	public static final String NO_PRSN_ROLE					= "man_prsn_no_prsn_role";
	public static final String ME_NO_HELP					= "me_no_help";
	
	public static final String TSV_ACCEPT					= "man_prsn_accept";
	public static final String TSV_CANCEL					= "tsv_cancel";
	public static final String TSV_HELP						= "tsv_help";
	
	public static final String EMPTY_STRING 				= "";
	public static final String PROTOTYPE_DISPLAY_VALUE		= "XXXXXXXXXXXXXXXXXXXX";
															  

	
	/*
	 *	Fields
	 */
	JPanel 			formular;
	
//	JTabbedPane		tabbedPane;	
//	JPanel			createPrsnTab;
	JPanel			addPrsnPane;
//	JPanel			managePrsnTab;
//	JPanel			managePrsnPane;

	JLabel			prsnNameLbl;
	JTextField		prsnNameTF;
	JLabel			prsnTypeLbl;
	JComboBox		prsnTypeCB;
	JLabel			prsnRoleLbl;
	JComboBox		prsnRoleCB;
	JLabel			prsnCountryLbl;
	JComboBox		prsnCountryCB;
	JLabel			prsnCityLbl;
	JTextField		prsnCityTF;
	JLabel			prsnZipCodeLbl;
	JTextField		prsnZipCodeTF;
	JLabel			prsnStreetLbl;
	JTextField		prsnStreetTF;
	JLabel			prsnStreetNumberLbl;
	JTextField		prsnStreetNumberTF;
	JLabel			prsnPhoneLbl;
	JTextField		prsnPhoneTF;
	JLabel			prsnEMailLbl;
	JTextField		prsnEMailTF;
	JLabel			prsnIMessLbl;
	JTextField		prsnIMessTF;
	JLabel			prsnDateOfBirthLbl;
	JFormattedTextField	prsnDateOfBirthTF;
	JLabel			prsnPlaceOfBirthLbl;
	JTextField		prsnPlaceOfBirthTF;
	JLabel			prsnDepartmentLbl;
	JTextField		prsnDepartmentTF;
	JLabel			prsnLogoUriLbl;
	JTextField		prsnLogoUriTF;
	
	JButton			callHelpBtn;
	
	static Random generator = new Random();
	
	Locale 		  	currentLocale;
	ResourceBundle	resourceBundle;
	
	Boolean			isAccepted = false;
	
	/*
	 *	Constructor
	 */
	/**
	 * @param prsnList
	 * @param locale
	 * @param f
	 */
	public AddPersonDialog(ArrayList<Person> prsnList, Locale locale, Font f, ActionListener al) {
		managePerson(prsnList, locale, f, al);
	}
	
	/*
	 *	Methods
	 */
	/**
	 * @param prsnList
	 * @param locale
	 * @param f
	 */
	private void managePerson(ArrayList<Person> prsnList, Locale locale, Font f, ActionListener al) {
		Vector<PersonType>	personTypeVector	= new Vector<PersonType>();
		Vector<PersonRole>	personRoleVector	= new Vector<PersonRole>();
		
		currentLocale	= locale;
		resourceBundle	= ResourceBundle.getBundle(BUNDLE, currentLocale);
		
		setTitle(resourceBundle.getString(MAN_PRSN_TITLE));
		
		formular = new JPanel();
		
		callHelpBtn = new JButton();
		callHelpBtn.setActionCommand(ActionCommandText.CMD_HELP_HELP_ADD_PERSON);
		callHelpBtn.addActionListener(al);
		callHelpBtn.setVisible(false);
		callHelpBtn.setEnabled(true);
		
//		tabbedPane = new JTabbedPane();
//		tabbedPane.setFont(f);
//		createPrsnTab = new JPanel();
//		managePrsnTab = new JPanel();
		
		prsnNameLbl = new JLabel(resourceBundle.getString(MAN_PRSN_NAME));
		prsnNameLbl.setFont(f);
		prsnNameTF = new JTextField(10);
		prsnNameTF.setFont(f);
		prsnNameTF.addFocusListener(this);
		
		prsnTypeLbl = new JLabel(resourceBundle.getString(MAN_PRSN_TYPE));
		prsnTypeLbl.setFont(f);
		PersonType[] personTypes = PersonType.values();
		ArrayList<PersonType> personTypeList = new ArrayList<PersonType>();
		Collections.addAll(personTypeList, personTypes); 
		Iterator<PersonType> iterator_p = personTypeList.iterator();	
		while (iterator_p.hasNext()) {
			PersonType p = iterator_p.next();
			p.setLocale(currentLocale);
			
			personTypeVector.add(p);
		}
		prsnTypeCB = new JComboBox(personTypeVector);
		prsnTypeCB.setFont(f);
		//AutoCompleteDecorator.decorate(prsnTypeCB);
		//prsnTypeCB.isEditable();
		prsnTypeCB.setPrototypeDisplayValue(PROTOTYPE_DISPLAY_VALUE);
		prsnTypeCB.addFocusListener(this);
		
		prsnRoleLbl = new JLabel(resourceBundle.getString(MAN_PRSN_ROLE));
		prsnRoleLbl.setFont(f);
		PersonRole[] personRoles = PersonRole.values();
		ArrayList<PersonRole> personRoleList = new ArrayList<PersonRole>();
		Collections.addAll(personRoleList, personRoles); 
		Iterator<PersonRole> iterator_r = personRoleList.iterator();	
		while (iterator_r.hasNext()) {
			PersonRole r = iterator_r.next();
			r.setLocale(currentLocale);
			
			personRoleVector.add(r);
		}
		prsnRoleCB = new JComboBox(personRoleVector);
		prsnRoleCB.setFont(f);
		//AutoCompleteDecorator.decorate(prsnRoleCB);
		//prsnRoleCB.isEditable();
		prsnRoleCB.setPrototypeDisplayValue(PROTOTYPE_DISPLAY_VALUE);
		prsnRoleCB.addFocusListener(this);
		
		/* Country: To Do Later */
		
		prsnCityLbl = new JLabel(resourceBundle.getString(MAN_PRSN_CITY));
		prsnCityLbl.setFont(f);
		prsnCityTF = new JTextField(10);
		prsnCityTF.setFont(f);
		prsnCityTF.addFocusListener(this);
		
		prsnZipCodeLbl = new JLabel(resourceBundle.getString(MAN_PRSN_ZIP_CODE));
		prsnZipCodeLbl.setFont(f);
		prsnZipCodeTF = new JTextField(10);
		prsnZipCodeTF.setFont(f);
		prsnZipCodeTF.addFocusListener(this);
		
		prsnStreetLbl = new JLabel(resourceBundle.getString(MAN_PRSN_STREET));
		prsnStreetLbl.setFont(f);
		prsnStreetTF = new JTextField(10);
		prsnStreetTF.setFont(f);
		prsnStreetTF.addFocusListener(this);
		
		prsnStreetNumberLbl = new JLabel(resourceBundle.getString(MAN_PRSN_STREET_NUMBER));
		prsnStreetNumberLbl.setFont(f);
		prsnStreetNumberTF = new JTextField(10);
		prsnStreetNumberTF.setFont(f);
		prsnStreetNumberTF.addFocusListener(this);
		
		prsnPhoneLbl = new JLabel(resourceBundle.getString(MAN_PRSN_PHONE));
		prsnPhoneLbl.setFont(f);
		prsnPhoneTF = new JTextField(10);
		prsnPhoneTF.setFont(f);
		prsnPhoneTF.addFocusListener(this);
		
		prsnEMailLbl = new JLabel(resourceBundle.getString(MAN_PRSN_EMAIL));
		prsnEMailLbl.setFont(f);
		prsnEMailTF = new JTextField(10);
		prsnEMailTF.setFont(f);
		prsnEMailTF.addFocusListener(this);
		
		prsnIMessLbl = new JLabel(resourceBundle.getString(MAN_PRSN_INSTANT_MESSAGING));
		prsnIMessLbl.setFont(f);
		prsnIMessTF = new JTextField(10);
		prsnIMessTF.setFont(f);
		prsnIMessTF.addFocusListener(this);
		
		prsnDateOfBirthLbl = new JLabel(resourceBundle.getString(MAN_PRSN_DATE_OF_BIRTH));
		prsnDateOfBirthLbl.setFont(f);
		prsnDateOfBirthTF = new JFormattedTextField();
		prsnDateOfBirthTF.setFont(f);
		prsnDateOfBirthTF.addFocusListener(this);
		
		prsnPlaceOfBirthLbl = new JLabel(resourceBundle.getString(MAN_PRSN_PLACE_OF_BIRTH));
		prsnPlaceOfBirthLbl.setFont(f);
		prsnPlaceOfBirthTF = new JTextField(10);
		prsnPlaceOfBirthTF.setFont(f);
		prsnPlaceOfBirthTF.addFocusListener(this);
		
		prsnDepartmentLbl = new JLabel(resourceBundle.getString(MAN_PRSN_DEPARTMENT));
		prsnDepartmentLbl.setFont(f);
		prsnDepartmentTF = new JTextField(10);
		prsnDepartmentTF.setFont(f);
		prsnDepartmentTF.addFocusListener(this);
		
		prsnLogoUriLbl = new JLabel(resourceBundle.getString(MAN_PRSN_LOGO_URI));
		prsnLogoUriLbl.setFont(f);
		prsnLogoUriTF = new JTextField(10);
		prsnLogoUriTF.setFont(f);
		prsnLogoUriTF.addFocusListener(this);
		
		addPrsnPane = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		
		GridBagLayout gridBagLayout = new GridBagLayout();
//		createPrsnTab = new JPanel(false);
//		createPrsnTab.setLayout(new FlowLayout(FlowLayout.LEFT));
//		createPrsnTab.setPreferredSize(new Dimension(440, 476)); 
		
		addPrsnPane = new JPanel(false);
		addPrsnPane.setLayout(gridBagLayout);
		
		JLabel[] loginTextControlLabels = {prsnNameLbl, prsnTypeLbl, prsnRoleLbl, 
				prsnCityLbl, prsnZipCodeLbl, prsnStreetLbl, prsnStreetNumberLbl, 
				prsnPhoneLbl, prsnEMailLbl, prsnIMessLbl,
				prsnDateOfBirthLbl, prsnPlaceOfBirthLbl,
				prsnDepartmentLbl, prsnLogoUriLbl};
		Component[] loginTextControls = {prsnNameTF, prsnTypeCB, prsnRoleCB,
				prsnCityTF, prsnZipCodeTF, prsnStreetTF, prsnStreetNumberTF, 
				prsnPhoneTF, prsnEMailTF, prsnIMessTF,
				prsnDateOfBirthTF, prsnPlaceOfBirthTF,
				prsnDepartmentTF, prsnLogoUriTF};
				
		addLabelTextControlAsRows(loginTextControlLabels,
								  loginTextControls,
								  gridBagLayout,
								  addPrsnPane);
		
//		createPrsnTab.add(addPrsnPane);
		
//		tabbedPane.addTab(resourceBundle.getString(CRE_PRSN_TAB), null, createPrsnTab, EMPTY_STRING);
//		tabbedPane.addTab(resourceBundle.getString(MAN_PRSN_TAB), null, managePrsnTab, EMPTY_STRING);
//		tabbedPane.setEnabledAt(1, false);
		
		formular.setLayout(new FlowLayout());
//		formular.add(tabbedPane);
		formular.add(this.addPrsnPane);
		
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
    			resourceBundle.getString(MAN_PRSN_TITLE),
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
	  			
				if (prsnNameTF.getText().isEmpty()) {
					JOptionPane.showMessageDialog(((CStatsCtrl)al).getView().getAppFrame(), resourceBundle.getString(NO_PRSN_NAME)); retValue = 2;
					isAccepted = false;
				}
				
				if (prsnTypeCB.getSelectedIndex() < 1) {
					JOptionPane.showMessageDialog(((CStatsCtrl)al).getView().getAppFrame(), resourceBundle.getString(NO_PRSN_TYPE)); retValue = 2;
					isAccepted = false;
				}
				
				if (prsnRoleCB.getSelectedIndex() < 1) {
					JOptionPane.showMessageDialog(((CStatsCtrl)al).getView().getAppFrame(), resourceBundle.getString(NO_PRSN_ROLE)); retValue = 2;
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
	public Person getAddedPerson() {
		Person newPerson = new Person();
		
		newPerson.setName(prsnNameTF.getText());
		newPerson.setType((PersonType)this.prsnTypeCB.getSelectedItem());
		newPerson.setRole((PersonRole)this.prsnRoleCB.getSelectedItem());
		
		return newPerson;
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
		public void mouseClicked(MouseEvent mouseEvent) {
//			Person 		person;
//			String 		password;
//			String 		userName;
////			UserRole	userRole;
//			
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
//			
//			if (prsnTypeCB.getSelectedIndex() < 1) {
//				JOptionPane.showMessageDialog(_view.getAppFrame(), resourceBundle.getString(NO_PRSN_NAME)); return;
//			}
//			
//			if (prsnTypeCB.getSelectedIndex() > 0) {
//				person = (Person)prsnTypeCB.getSelectedItem();
//			} 
//			
//			if (prsnNameTF.getText().isEmpty()) {
//				JOptionPane.showMessageDialog(_view.getAppFrame(), resourceBundle.getString(NO_PRSN_TYPE)); return;
//			}
//				
//			userName = prsnNameTF.getText();
//					
//			if (prsnRolesCB.getSelectedIndex() < 1) {
//				JOptionPane.showMessageDialog(_view.getAppFrame(), resourceBundle.getString(NO_PRSN_ROLE)); return;
//			}
//					
//			userRole = (UserRole)prsnRolesCB.getSelectedItem();
		}
	};
	
	/**
	 * @return
	 */
	public static int generateNegID() {
		int id = generator.nextInt();
		while (id >= -1) {
			id = generator.nextInt();
		}
		return id;
	}
}

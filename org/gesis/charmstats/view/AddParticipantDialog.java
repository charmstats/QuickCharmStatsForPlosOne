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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
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
import javax.swing.JTextField;

import org.gesis.charmstats.ActionCommandText;
import org.gesis.charmstats.controller.CStatsCtrl;
import org.gesis.charmstats.model.Participant;
import org.gesis.charmstats.model.ParticipantRole;
import org.gesis.charmstats.model.Person;
import org.gesis.charmstats.model.Project;
import org.gesis.charmstats.model.User;

/**
 * 
 * @author Martin Friedrichs
 * @since	0.9.2 / CharmStatsPro only
 *
 */
public class AddParticipantDialog extends JFrame implements FocusListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String BUNDLE						= "org.gesis.charmstats.resources.DesktopBundle";
	
	public static final String MAN_PAR_TITLE				= "man_par_title";
	public static final String MAN_PAR_PRJNAME				= "man_usr_prjname";
	public static final String MAN_PAR_USERNAME				= "man_usr_username";
	public static final String MAN_PAR_ROLE					= "man_par_role";
	public static final String CRE_PAR_TAB					= "cre_par_tab";
	public static final String MAN_PAR_TAB					= "man_par_tab";
	public static final String MAN_PAR_ACCEPT				= "man_par_accept";
	public static final String NO_USR						= "man_par_no_usr";
	public static final String NO_PRTCPNT_ROLE				= "man_par_no_prtcpnt_role";
	public static final String ME_NO_HELP					= "me_no_help";
	
	
	public static final String TSV_ACCEPT					= "man_par_accept";
	public static final String TSV_CANCEL					= "tsv_cancel";
	public static final String TSV_HELP						= "tsv_help";
	
	public static final String EMPTY_STRING 				= "";
	public static final String PROTOTYPE_DISPLAY_VALUE		= "XXXXXXXXXXXXXXXXXXXX";

	
	/*
	 *	Fields
	 */
	JPanel 			formular;
	
//	JTabbedPane		tabbedPane;	
//	JPanel			addPrtcpntTab;
	JPanel			addPrtcpntPane;
//	JPanel			managePrtcpntTab;
//	JPanel			managePrtcpntPane;
	
	JLabel			prjNameLbl;
	JTextField		prjNameTF;
	JLabel			userLbl;
	JComboBox		userCB;
	JLabel			prtcpntRoleLbl;
	JComboBox		prtcpntRolesCB;
	
	JButton			callHelpBtn;
	
	Locale 		  	currentLocale;
	ResourceBundle	resourceBundle;
	
	Project			project;
	Person			person;
	ParticipantRole role;
	
	Boolean			isAccepted = false;
	
	/*
	 *	Constructor
	 */
	/**
	 * @param usrList
	 * @param prj
	 * @param locale
	 * @param f
	 */
	public AddParticipantDialog(ArrayList<User> usrList, Project prj, Locale locale, Font f, ActionListener al) {
		manageParticipant(usrList, prj, locale, f, al);
	}
	
	/*
	 *	Methods
	 */
	/**
	 * @param usrList
	 * @param prj
	 * @param locale
	 * @param f
	 */
	private void manageParticipant(ArrayList<User> usrList, Project prj, Locale locale, Font f, ActionListener al) {
		project = prj;
		Vector<ParticipantRole>	prtcpntRoleVector	= new Vector<ParticipantRole>();
		Vector<User>			usrVector			= new Vector<User>();
		
		currentLocale	= locale;
		resourceBundle	= ResourceBundle.getBundle(BUNDLE, currentLocale);
		
		setTitle(resourceBundle.getString(MAN_PAR_TITLE));
		
		formular = new JPanel();
		
		callHelpBtn = new JButton();
		callHelpBtn.setActionCommand(ActionCommandText.CMD_HELP_HELP_ADD_PARTICIPANT);
		callHelpBtn.addActionListener(al);
		callHelpBtn.setVisible(false);
		callHelpBtn.setEnabled(true);
		
//		tabbedPane			= new JTabbedPane();
//		tabbedPane.setFont(f);
//		addPrtcpntTab		= new JPanel();
//		managePrtcpntTab	= new JPanel();
		
		userLbl = new JLabel(resourceBundle.getString(MAN_PAR_USERNAME));
		userLbl.setFont(f);
		Iterator<User> iterator_u = usrList.iterator();
		User none = new User();
			none.setName(EMPTY_STRING);
		usrVector.add(none);
		while (iterator_u.hasNext()) {
			User u = iterator_u.next();
			
			usrVector.add(u);
		}
		userCB = new JComboBox(usrVector);
		userCB.setFont(f);
		//AutoCompleteDecorator.decorate(userCB);
		//userCB.isEditable();
		userCB.setPrototypeDisplayValue(PROTOTYPE_DISPLAY_VALUE);
		userCB.addFocusListener(this);
		
		prjNameLbl = new JLabel(resourceBundle.getString(MAN_PAR_PRJNAME));
		prjNameLbl.setFont(f);
		prjNameTF = new JTextField(10);
		prjNameTF.setFont(f);
		prjNameTF.setText(prj.getName());
		prjNameTF.setEditable(false);
		prjNameTF.addFocusListener(this);
		
		prtcpntRoleLbl = new JLabel(resourceBundle.getString(MAN_PAR_ROLE));
		prtcpntRoleLbl.setFont(f);
		ParticipantRole[] prtcpntRoles = ParticipantRole.values();
		ArrayList<ParticipantRole> prtcpntRoleList = new ArrayList<ParticipantRole>();
		Collections.addAll(prtcpntRoleList, prtcpntRoles); 
		Iterator<ParticipantRole> iterator_p = prtcpntRoleList.iterator();	
		while (iterator_p.hasNext()) {
			ParticipantRole p = iterator_p.next();
			p.setLocale(currentLocale);
			
			prtcpntRoleVector.add(p);
		}
		prtcpntRolesCB = new JComboBox(prtcpntRoleVector);
		prtcpntRolesCB.setFont(f);
		//AutoCompleteDecorator.decorate(prtcpntRolesCB);
		//prtcpntRolesCB.isEditable();
		prtcpntRolesCB.setPrototypeDisplayValue(PROTOTYPE_DISPLAY_VALUE);
		prtcpntRolesCB.addFocusListener(this);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
//		addPrtcpntTab = new JPanel(false);
//		addPrtcpntTab.setLayout(new FlowLayout(FlowLayout.LEFT));
//		addPrtcpntTab.setPreferredSize(new Dimension(400, 225));
		
		addPrtcpntPane = new JPanel(false);
		addPrtcpntPane.setLayout(gridBagLayout);
		
		JLabel[] loginTextControlLabels = {prjNameLbl, userLbl, prtcpntRoleLbl};
		Component[] loginTextControls = {prjNameTF, userCB, prtcpntRolesCB};
		
		addLabelTextControlAsRows(loginTextControlLabels,
								  loginTextControls,
								  gridBagLayout,
								  addPrtcpntPane);
		
//		addPrtcpntTab.add(addPrtcpntPane);
				
//		tabbedPane.addTab(resourceBundle.getString(CRE_PAR_TAB), null, addPrtcpntTab, EMPTY_STRING);
//		tabbedPane.addTab(resourceBundle.getString(MAN_PAR_TAB), null, managePrtcpntTab, EMPTY_STRING);
//		tabbedPane.setEnabledAt(1, false);
		
		formular.setLayout(new FlowLayout());
//		formular.add(tabbedPane);
		formular.add(addPrtcpntPane);
		
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
    			resourceBundle.getString(MAN_PAR_TITLE),
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
	  			
				if (userCB.getSelectedIndex() < 1) {
					JOptionPane.showMessageDialog(((CStatsCtrl)al).getView().getAppFrame(), resourceBundle.getString(NO_USR)); retValue = 2;
					isAccepted = false;
				}
		  		
				if (prtcpntRolesCB.getSelectedIndex() < 1) {
					JOptionPane.showMessageDialog(((CStatsCtrl)al).getView().getAppFrame(), resourceBundle.getString(NO_PRTCPNT_ROLE)); retValue = 2;
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
	public Participant getAddedParticipant() {
		Participant newParticipant = new Participant();
		
		newParticipant.setProject(project);
		newParticipant.setUser((User)userCB.getSelectedItem());
		newParticipant.setRole((ParticipantRole)prtcpntRolesCB.getSelectedItem());
		
		return newParticipant;
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

}

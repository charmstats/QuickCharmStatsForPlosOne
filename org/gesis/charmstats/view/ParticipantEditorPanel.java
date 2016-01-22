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
 * @author	Martin Friedrichs
 * @since	0.9.2 / CharmStatsPro only
 *
 */
public class ParticipantEditorPanel extends JFrame implements FocusListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String BUNDLE						= "org.gesis.charmstats.resources.DesktopBundle";
	
	public static final String MAN_PAR_TITLE				= "edit_par_title";
	public static final String MAN_PAR_PRJNAME				= "man_usr_prjname";
	public static final String MAN_PAR_USERNAME				= "man_usr_username";
	public static final String MAN_PAR_ROLE					= "man_par_role";
	public static final String CRE_PAR_TAB					= "cre_par_tab";
	public static final String MAN_PAR_TAB					= "man_par_tab";
	public static final String MAN_PAR_ACCEPT				= "man_par_accept";
	public static final String NO_USR						= "man_par_no_usr";
	public static final String NO_PRTCPNT_ROLE				= "man_par_no_prtcpnt_role";
	public static final String ME_RESET						= "me_reset";
	public static final String ME_NO_HELP					= "me_no_help";
	
	public static final String TSV_ACCEPT					= "man_par_accept";
	public static final String TSV_CANCEL					= "tsv_cancel";
	public static final String TSV_HELP						= "tsv_help";
	
	public static final String EMPTY_STRING 				= "";
	public static final String PROTOTYPE_DISPLAY_VALUE		= "XXXXXXXXXXXXXXXXXXXX";

	
	/*
	 *	Fields
	 */
	JPanel			overallPanel;
	JPanel 			formular;

	JPanel			editPrtcpntPane;
	
	JLabel			prjNameLbl;
	JTextField		prjNameTF;
	JLabel			userLbl;
	JComboBox		userCB;
	JLabel			prtcpntRoleLbl;
	JComboBox		prtcpntRolesCB;
	
	JPanel			buttonPanel;
	private JButton	resetBtn;
	
	Locale 		  	currentLocale;
	ResourceBundle	resourceBundle;
	Font			currentFont;
	java.sql.Connection		con;
	
	Project			project;
	Person			person;
	ParticipantRole role;
	
	JButton			callHelpBtn;
	
	Boolean			isAccepted = false;
	
	Participant		part;
	
	final ActionListener fal;
	
	/*
	 *	Constructor
	 */
	/**
	 * @param prt
	 * @param prj
	 * @param _connection
	 * @param locale
	 * @param f
	 */
	public ParticipantEditorPanel(Participant prt, Project prj, java.sql.Connection _connection, Locale locale, Font f, ActionListener al) {
		this.con = _connection;
		this.currentFont = f;
		
		this.part = prt;
		this.project = prj;
		
		fal = al;
		
		editParticipant(prt, prj, locale, f, al);
	}
	
	/*
	 *	Methods
	 */
	/**
	 * @param prt
	 * @param prj
	 * @param locale
	 * @param f
	 */
	private void editParticipant(Participant prt, Project prj, Locale locale, Font f, ActionListener al) {
		Vector<ParticipantRole>	prtcpntRoleVector	= new Vector<ParticipantRole>();
		Vector<User>			usrVector			= new Vector<User>();
		
		currentLocale	= locale;
		resourceBundle	= ResourceBundle.getBundle(BUNDLE, currentLocale);
		
		setTitle(resourceBundle.getString(MAN_PAR_TITLE));
		
		formular = new JPanel();
		
		callHelpBtn = new JButton();
		callHelpBtn.setActionCommand(ActionCommandText.CMD_HELP_HELP_EDIT_PARTICIPANT);
		callHelpBtn.addActionListener(al);
		callHelpBtn.setVisible(false);
		callHelpBtn.setEnabled(true);
		
		userLbl = new JLabel(resourceBundle.getString(MAN_PAR_USERNAME));
		userLbl.setFont(f);
		usrVector.add(part.getUser());
		userCB = new JComboBox(usrVector);
		userCB.setFont(f);
		userCB.setPrototypeDisplayValue(PROTOTYPE_DISPLAY_VALUE);
		userCB.addFocusListener(this);
		userCB.setEnabled(false);
		
		prjNameLbl = new JLabel(resourceBundle.getString(MAN_PAR_PRJNAME));
		prjNameLbl.setFont(f);
		prjNameTF = new JTextField(10);
		prjNameTF.setFont(f);
		prjNameTF.setText(prj.getName());
		prjNameTF.setEditable(false);
		prjNameTF.addFocusListener(this);
		prjNameTF.setEnabled(false);
		
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
		prtcpntRolesCB.setPrototypeDisplayValue(PROTOTYPE_DISPLAY_VALUE);
		prtcpntRolesCB.addFocusListener(this);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		
		editPrtcpntPane = new JPanel(false);
		editPrtcpntPane.setLayout(gridBagLayout);
		
		JLabel[] loginTextControlLabels = {prjNameLbl, userLbl, prtcpntRoleLbl};
		Component[] loginTextControls = {prjNameTF, userCB, prtcpntRolesCB};
		
		addLabelTextControlAsRows(loginTextControlLabels,
								  loginTextControls,
								  gridBagLayout,
								  editPrtcpntPane);
		
		formular.setLayout(new FlowLayout());
		formular.add(editPrtcpntPane);
		
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
	  		
			if (prtcpntRolesCB.getSelectedIndex() < 1) {
				JOptionPane.showMessageDialog(((CStatsCtrl)fal).getView().getAppFrame(), resourceBundle.getString(NO_PRTCPNT_ROLE)); retValue = 2;
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
		/* Participant */
		userCB.setSelectedItem(part.getUser());
		prjNameTF.setText(project.getName());
		prtcpntRolesCB.setSelectedItem(part.getRole());
	}
	
	/**
	 * 
	 */
	private void saveChanges() {
		/* Participant */
		part.setRole((ParticipantRole)this.prtcpntRolesCB.getSelectedItem());
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


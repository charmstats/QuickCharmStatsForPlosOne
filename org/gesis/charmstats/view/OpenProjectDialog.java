package org.gesis.charmstats.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Iterator;
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
import org.gesis.charmstats.model.Description;
import org.gesis.charmstats.model.Label;
import org.gesis.charmstats.model.Project;

/**
 * 
 * @author	Martin Friedrichs
 * @since	0.1
 *
 */
public class OpenProjectDialog extends JFrame implements FocusListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String BUNDLE				= "org.gesis.charmstats.resources.DesktopBundle";
	
	public static final String OPC_TITLE			= "opc_title";
	
	public static final String OPC_ACCEPT			= "opc_accept";
	public static final String OPC_CANCEL			= "tsv_cancel";
	public static final String OPC_HELP				= "tsv_help";
	public static final String OPC_NO_HELP			= "omc_no_help";
	
	public static final String OPC_PROJECT_LBL		= "opc_project_lbl";
	public static final String OPEN_PROJECT			= "OpenProject";
	
	public static final String FP_TARGET_VARIABLE		= "fp_target_var";
	public static final String FP_DEFINITION			= "fp_definition";
	public static final String FP_CONCEPT				= "fp_concept";

	
	public static final String EMPTY 				= "";
	
	/*
	 *	Fields
	 */
	JPanel 		formular = new JPanel();
	
	JComboBox	projectsCB;
	
	JPanel 		prjNotePane;
	JTextPane	projectNote; 
	
	JButton			callHelpBtn;
	
	Locale 		  	currentLocale;
	ResourceBundle	resourceBundle;
	
	/*
	 *	Constructor
	 */
	/**
	 * @param projects
	 * @param locale
	 * @param f
	 */
	OpenProjectDialog(ArrayList<Project> projects, Locale locale, Font f, ActionListener al) {
		openProject(projects, locale, f, al);
	}
	
	/**
	 * @param projects
	 * @param locale
	 * @param f
	 */
	private void openProject(ArrayList<Project> projects, Locale locale, Font f, ActionListener al) {
		Vector<Object> myVector = new Vector<Object>();
		
		currentLocale	= locale;
		resourceBundle	= ResourceBundle.getBundle(BUNDLE, currentLocale);
		
		callHelpBtn = new JButton();
		callHelpBtn.setActionCommand(ActionCommandText.CMD_HELP_HELP_OPEN_PROJECT);
		callHelpBtn.addActionListener(al);
		callHelpBtn.setVisible(false);
		callHelpBtn.setEnabled(true);
		
		/* DoNothing, if no projects are available: */
		if ((!(projects != null)) ||
				(projects.size() < 1))
			return;
		
		this.setTitle(resourceBundle.getString(OPC_TITLE));
		
		Iterator<Project> iterator = projects.iterator();
		myVector.add(EMPTY);
		while(iterator.hasNext()) {
			Project p = iterator.next();
			
			myVector.add(p);
		}
		projectsCB = new JComboBox(myVector);
		projectsCB.setFont(f);
		projectsCB.addFocusListener(this);
		projectsCB.addActionListener(new java.awt.event.ActionListener() {
            /* (non-Javadoc)
             * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                projectsCBActionPerformed(evt);
            }
        });
		
		JPanel overallPanel = new JPanel();
		overallPanel.setLayout(new BoxLayout(overallPanel, BoxLayout.Y_AXIS));
		
		JPanel projectPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel projectsLbl = new JLabel(resourceBundle.getString(OPC_PROJECT_LBL));
		projectsLbl.setFont(f);
		projectPanel.add(projectsLbl);
		projectPanel.add(projectsCB);
		
		projectNote = new JTextPane();
		projectNote.setContentType("text/html");
		projectNote.setBounds(0, 0, 645, 100);
		
		prjNotePane = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JScrollPane prjNoteScrollPane = new JScrollPane(projectNote);
		prjNoteScrollPane.setPreferredSize(new Dimension(645, 100)); 
		prjNoteScrollPane.getVerticalScrollBar().setUnitIncrement(16);
		prjNotePane.add(prjNoteScrollPane);		
				
		overallPanel.add(projectPanel);
		overallPanel.add(prjNotePane);
				
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
    			((CStatsCtrl)al).getView().getAppFrame(), 
    			formular,
    			resourceBundle.getString(OPC_TITLE),
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, 
                DIALOG_BUTTONS_TITLES, 
                DIALOG_BUTTONS_TITLES[0]
	  		);
	  		
	  		if (retValue == 1) {
	  			projectsCB.setSelectedIndex(0);
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
    private void projectsCBActionPerformed(java.awt.event.ActionEvent evt) {
    	projectNote.setText(EMPTY);
    	
    	if (evt.getSource() instanceof JComboBox) {
    		Object object = ((JComboBox)evt.getSource()).getSelectedItem();
    		
    		if (object instanceof Project) {
    			Project prj = (Project)object;
    			
    			String prjTitel = prj.getName();
    			
    			StringBuilder sb = new StringBuilder(prj.getSummary().getText());
    			
				int i = 0;
				while (i + 80 < sb.length() && (i = sb.lastIndexOf(" ", i + 80)) != -1) {
				    sb.replace(i, i + 1, "\n<BR>");
				}
				
				String prjAbstract = resourceBundle.getString(FP_DEFINITION)+ ":<BR><I>"+ sb.toString() +"</I>";
				
				String varName = prj.getTargetName();
				String varLabel = prj.getTargetLabel();
				
				String conName = "-";
				if (prj.getConcept().getDefaultLabel() instanceof Label )
					conName = prj.getConcept().getDefaultLabel().getLabel().getTextualContent();
				String conDefinition = "-";
				if (prj.getConcept().getDefaultDescription() instanceof Description) {
					sb = new StringBuilder(prj.getConcept().getDefaultDescription().getDescription().getTextualContent());

					i = 0;
					while (i + 80 < sb.length() && (i = sb.lastIndexOf(" ", i + 80)) != -1) {
						sb.replace(i, i + 1, "\n<BR>");
					}
				
					conDefinition = "<BR><I>"+ sb.toString() +"</I>";
				}
			
    			String output = 
    					"<html><B>"+prjTitel+"</B><BR>"+ prjAbstract +"<hr />"+ 
    					resourceBundle.getString(FP_TARGET_VARIABLE)+varName +" / \""+ varLabel +"\"<hr />"+ 
    					resourceBundle.getString(FP_CONCEPT) +" \""+ conName +"\""+ conDefinition +"</html>";
    			

    			projectNote.setText(output);
    			projectNote.setCaretPosition(0);
    		}
    	}
    }

	/**
	 * @return
	 */
	public Project getChosenProject() {
		Project project = new Project(-1);
		
		if (projectsCB.getSelectedItem() != null) {
			if (projectsCB.getSelectedItem() instanceof Project)
				project = (Project)projectsCB.getSelectedItem();
		}
		
		return project;
	}

}

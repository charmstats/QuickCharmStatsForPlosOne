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
import org.gesis.charmstats.model.Participant;

/**
 * 
 * @author	Martin Friedrichs
 * @since	0.9.2 / CharmStatsPro only
 *
 */
public class OpenParticipantDialog extends JFrame implements FocusListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String BUNDLE				= "org.gesis.charmstats.resources.DesktopBundle";
	
	public static final String OPC_TITLE			= "opac_title";
	
	public static final String OPC_ACCEPT			= "omc_accept";
	public static final String OPC_CANCEL			= "omc_cancel";
	public static final String OPC_HELP				= "omc_help";
	public static final String OPC_NO_HELP			= "omc_no_help";
	
	public static final String OPC_PART_LBL			= "opac_part_lbl";
	
	public static final String EMPTY 				= "";
	
	/*
	 *	Fields
	 */
	JPanel 		formular = new JPanel();
	
	JComboBox	partsCB;
	
	JPanel 		partNotePane;
	JTextArea	partNote;
	
	JButton			callHelpBtn;
	
	Locale 		  	currentLocale;
	ResourceBundle	resourceBundle;
	
	/*
	 *	Constructor
	 */
	/**
	 * @param parts
	 * @param locale
	 * @param f
	 */
	OpenParticipantDialog(List<Participant> parts, Locale locale, Font f, ActionListener al) {
		openParticipant(parts, locale, f, al);
	}
	
	/**
	 * @param parts
	 * @param locale
	 * @param f
	 */
	private void openParticipant(List<Participant> parts, Locale locale, Font f, ActionListener al) {
		Vector<Object> myVector = new Vector<Object>();
		
		currentLocale	= locale;
		resourceBundle	= ResourceBundle.getBundle(BUNDLE, currentLocale);
		
		callHelpBtn = new JButton();
		callHelpBtn.setActionCommand(ActionCommandText.CMD_HELP_HELP_ADD_PARTICIPANT_OPEN);
		callHelpBtn.addActionListener(al);
		callHelpBtn.setVisible(false);
		callHelpBtn.setEnabled(true);
		
		/* DoNothing, if no participants are available: */
		if ((!(parts != null)) ||
				(parts.size() < 1))
			return;
		
		this.setTitle(resourceBundle.getString(OPC_TITLE));
		
		Iterator<Participant> iterator = parts.iterator();
		myVector.add(EMPTY);
		while(iterator.hasNext()) {
			Participant p = iterator.next();
			
			myVector.add(p);
		}
		partsCB = new JComboBox(myVector);
		partsCB.setFont(f);
		partsCB.addFocusListener(this);
		partsCB.addFocusListener(this);
		partsCB.addActionListener(new java.awt.event.ActionListener() {
            /* (non-Javadoc)
             * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                partsCBActionPerformed(evt);
            }
        });
		
		JPanel overallPanel = new JPanel();
		overallPanel.setLayout(new BoxLayout(overallPanel, BoxLayout.Y_AXIS));
		
		JPanel participantPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel partsLbl = new JLabel(resourceBundle.getString(OPC_PART_LBL));
		partsLbl.setFont(f);
		participantPanel.add(partsLbl);
		participantPanel.add(partsCB);
		
		partNote = new JTextArea(5, 80);
		partNote.setEnabled(false);
		partNote.setLineWrap(true);
		partNote.setWrapStyleWord(true);
		partNotePane = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JScrollPane partNoteScrollPane = new JScrollPane(partNote);
		partNoteScrollPane.getVerticalScrollBar().setUnitIncrement(16);
		partNotePane.add(partNoteScrollPane);		
				
		overallPanel.add(participantPanel);
		overallPanel.add(partNotePane);
				
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
	  			partsCB.setSelectedIndex(0);
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
    private void partsCBActionPerformed(java.awt.event.ActionEvent evt) {
    	partNote.setText(EMPTY);
    	
    	if (evt.getSource() instanceof JComboBox) {
    		Object object = ((JComboBox)evt.getSource()).getSelectedItem();
    		
    		if (object instanceof Participant) {
    			partNote.setText(EMPTY);
    		}
    	}
    }

	/**
	 * @return
	 */
	public Participant getChosenParticipant() {
		Participant part = new Participant(); part.setEntityID(-1);
		
		if (partsCB.getSelectedItem() != null) {
			if (partsCB.getSelectedItem() instanceof Participant)
				part = (Participant)partsCB.getSelectedItem();
		}
		
		return part;
	}

}

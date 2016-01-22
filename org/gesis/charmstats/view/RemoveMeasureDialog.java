package org.gesis.charmstats.view;

import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.JTextPane;

import org.gesis.charmstats.ActionCommandText;
import org.gesis.charmstats.controller.CStatsCtrl;
import org.gesis.charmstats.model.Measurement;

public class RemoveMeasureDialog extends JFrame implements FocusListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String BUNDLE				= "org.gesis.charmstats.resources.DesktopBundle";
	
	public static final String RMC_TITLE			= "rmc_title";
	
	public static final String RVC_ACCEPT			= "rmc_accept";
	public static final String OVC_CANCEL			= "omc_cancel";
	public static final String OVC_HELP				= "omc_help";
	public static final String OVC_NO_HELP			= "omc_no_help";
	
	public static final String OMC_MEASURE_LBL		= "omc_measure_lbl";
	
	public static final String EMPTY 				= "";
	
	/*
	 *	Fields
	 */
	JPanel 		formular = new JPanel();
	
	JComboBox	measuresCB;
	
	JPanel 		meaNotePane;
	JTextPane	meaNote; 
	
	JButton			callHelpBtn;
	
	Locale 		  	currentLocale;
	ResourceBundle	resourceBundle;
	
	/*
	 *	Constructor
	 */
	/**
	 * @param variables
	 * @param locale
	 * @param f
	 */
	RemoveMeasureDialog(List<Measurement> measures, Locale locale, Font f, ActionListener al) {
		removeMeasure(measures, locale, f, al);
	}
	
	/**
	 * @param variables
	 * @param locale
	 * @param f
	 */
	private void removeMeasure(List<Measurement> measures, Locale locale, Font f, ActionListener al) {
		Vector<Object> myVector = new Vector<Object>();
		
		currentLocale	= locale;
		resourceBundle	= ResourceBundle.getBundle(BUNDLE, currentLocale);
		
		callHelpBtn = new JButton();
		callHelpBtn.setActionCommand(ActionCommandText.CMD_HELP_HELP_OPEN_VARIABLE);
		callHelpBtn.addActionListener(al);
		callHelpBtn.setVisible(false);
		callHelpBtn.setEnabled(true);
		
		/* DoNothing, if no measures are available: */
		if ((!(measures != null)) ||
				(measures.size() < 1))
			return;
		
		this.setTitle(resourceBundle.getString(RMC_TITLE));
		
		Iterator<Measurement> iterator = measures.iterator();
		myVector.add(EMPTY);
		while(iterator.hasNext()) {
			Measurement m = iterator.next();
			
			myVector.add(m);
		}
		measuresCB = new JComboBox(myVector);
		measuresCB.setFont(f);
		measuresCB.addFocusListener(this);
		measuresCB.addActionListener(new java.awt.event.ActionListener() {
            /* (non-Javadoc)
             * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                measuresCBActionPerformed(evt);
            }
        });
		
		JPanel overallPanel = new JPanel();
		overallPanel.setLayout(new BoxLayout(overallPanel, BoxLayout.Y_AXIS));
		
		JPanel measurePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel measuresLbl = new JLabel(resourceBundle.getString(OMC_MEASURE_LBL));
		measuresLbl.setFont(f);
		measurePanel.add(measuresLbl);
		measurePanel.add(measuresCB);
		
		meaNote = new JTextPane();
		meaNote.setContentType("text/html");
		meaNote.setBounds(0, 0, 645, 100);
		
		meaNotePane = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JScrollPane meaNoteScrollPane = new JScrollPane(meaNote);
		meaNoteScrollPane.setPreferredSize(new Dimension(645, 100)); 
		meaNoteScrollPane.getVerticalScrollBar().setUnitIncrement(16);
		meaNotePane.add(meaNoteScrollPane);		
				
		overallPanel.add(measurePanel);
		overallPanel.add(meaNotePane);
				
		formular.setLayout(new FlowLayout());
		formular.add(overallPanel);

		formular.setBorder(
				BorderFactory.createCompoundBorder(
					BorderFactory.createTitledBorder(EMPTY),
					BorderFactory.createEmptyBorder(5, 5, 5, 5)
				)
		);
		
		final String[] DIALOG_BUTTONS_TITLES	= {resourceBundle.getString(RVC_ACCEPT), resourceBundle.getString(OVC_CANCEL), resourceBundle.getString(OVC_HELP)};
		
        /*
         *	Wait for input
         */
		int retValue = 2;
	  	while (retValue == 2) 
	   	{
	  		retValue = JOptionPane.showOptionDialog(		
	  			((CStatsCtrl)al).getView().getAppFrame(), // null, 
    			formular,
    			resourceBundle.getString(RMC_TITLE),
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, 
                DIALOG_BUTTONS_TITLES, 
                DIALOG_BUTTONS_TITLES[0]
	  		);
	  		
	  		if (retValue == 1) {
	  			measuresCB.setSelectedIndex(0);
	  	    }
	  		
	  		if (retValue == 2) {
	  			callHelpBtn.doClick();
	  		}
	  		
	  		/* Reset after CloseButton to Unselected State */
	  		if (retValue == -1) {
	  			measuresCB.setSelectedIndex(0);
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
    private void measuresCBActionPerformed(java.awt.event.ActionEvent evt) {
    	meaNote.setText(EMPTY);
    	
    	if (evt.getSource() instanceof JComboBox) {
    		Object object = ((JComboBox)evt.getSource()).getSelectedItem();
    		
    		if (object instanceof Measurement) {

    			String output = "<b>"+object.toString()+"</b> ";
    			String sqlString = Measurement.getDraftSQLForProject(((Measurement)object).getEntityID());
    			String studyName = CStatsCtrl.searchForProjectName(sqlString);
    			output += "from <b>" + ((studyName.length() > 0) ? studyName : "unknown") + "; </b>";
    			
    			String measurementLevel = ((Measurement)object).getLevel().toString();
    			output += "Level: <b>" + ((measurementLevel.length() > 0) ? measurementLevel : "unknown") + "; </b>";
    			
    			sqlString = Measurement.getDraftSQLForCategoryList(((Measurement)object).getEntityID());
    			String categoryList = CStatsCtrl.searchForMeasurementCategories(sqlString);
    			categoryList = categoryList.replace("<html>", "");
    			categoryList = categoryList.replace("</html>", "");
    			output += categoryList;
    			
    			if ( (((Measurement)object).getDefinition() != null) &&
    					(((Measurement)object).getDefinition().getText() != null) &&
    					!((Measurement)object).getDefinition().getText().isEmpty()
    					)
    				output += ((Measurement)object).getDefinition().getText();
    			    			
    			meaNote.setText(output);
    			meaNote.setCaretPosition(0);
    		}
    	}
    }

	/**
	 * @return
	 */
	public Measurement getChosenMeasurement() {
		Measurement mea = new Measurement(); mea.setEntityID(-1);
		
		if (measuresCB.getSelectedItem() != null) {
			if (measuresCB.getSelectedItem() instanceof Measurement)
				mea = (Measurement)measuresCB.getSelectedItem();
		}
		
		return mea;
	}

}

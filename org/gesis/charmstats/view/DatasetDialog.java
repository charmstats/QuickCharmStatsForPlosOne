package org.gesis.charmstats.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *	@author	Martin Friedrichs
 *	@since	0.9.9
 *
 */
public class DatasetDialog extends JDialog implements FocusListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String BUNDLE		= "org.gesis.charmstats.resources.DesktopBundle";
	
	public static final String DIALOG_TITLE	= "dia_dat_tit";
	public static final String DATASET		= "dia_dat_nam";
	public static final String DOI			= "dia_dat_doi";
	public static final String DATE			= "dia_dat_dat";
	public static final String RETURN		= "dia_dat_ret";
	public static final String CANCEL		= "dia_dat_can";
	
	/*
	 *	Fields
	 */
	Locale				currentLocale;
	ResourceBundle		resourceBundle;
	
	private JPanel		jControlPane;
	private JPanel		jContentPane;
	private JScrollPane	dataScrollPane;
	private JTextArea	jTAData;
    private JPanel		doiPanel;
    private JLabel		jLblDOI;
    private JTextField	jTFDOI;
    private JPanel		datePanel;
    private JLabel		jLblDate;
    private JTextField	jTFDate;
 
    private JPanel		buttonPanel;
    private JButton		jBtnCancel;
    private JButton		jBtnReturn;

    private String[] returnValue = {"","",""};

    /** Creates new form TextEditorDialog */
    /**
     * @param parent
     * @param titel
     * @param argValue
     * @param date
     * @param modal
     * @param isPublished
     * @param isEditedByUser
     * @param locale
     * @param f
     */
    public DatasetDialog(java.awt.Frame parent, String titel, String[] argValue, boolean date, boolean modal, boolean isPublished, boolean isEditedByUser, Locale locale, Font f) {	
        super(parent, titel, modal);
        
        currentLocale	= locale;
        
        initComponents(date, argValue, isPublished, isEditedByUser, f);	
    }

    /** This method is called from within the constructor to
     * initialize the form.
     */
    /**
     * @param date
     * @param argValue
     * @param isPublished
     * @param isEditedByUser
     * @param f
     */
    private void initComponents(Boolean date, String[] argValue, boolean isPublished, boolean isEditedByUser, Font f) {
    	resourceBundle = ResourceBundle.getBundle(BUNDLE, currentLocale);
    	setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    	
        jControlPane	= new JPanel(new BorderLayout());
        jContentPane	= new JPanel(new BorderLayout());

        jTAData			= new JTextArea(6, 48);
        jTAData.setFont(f);
    	if ((argValue != null) &&
    			(argValue.length > 0))
    		jTAData.setText(argValue[0]);
		jTAData.setLineWrap(true);
		jTAData.setWrapStyleWord(true);
        dataScrollPane	= new JScrollPane(jTAData);
        
        jLblDOI		= new JLabel(resourceBundle.getString(DOI));
        jLblDOI.setFont(f);
        jTFDOI		= new JTextField(40);
        jTFDOI.setFont(f);
        doiPanel	= new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        jLblDOI.setEnabled(true);
        jTFDOI.setEnabled(true);
    	if ((argValue != null) &&
    			(argValue.length > 1))
    		jTFDOI.setText(argValue[1]);
        
        jLblDate		= new JLabel(resourceBundle.getString(DATE));
        jLblDate.setFont(f);
        jTFDate		= new JTextField(40);
        jTFDate.setFont(f);
        datePanel	= new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        jLblDate.setEnabled(date);
        jTFDate.setEnabled(date);
    	if ((argValue != null) &&
    			(argValue.length > 2))
    		jTFDate.setText(argValue[2]);
 
        jBtnCancel		= new JButton();
        jBtnCancel.setFont(f);
        jBtnReturn		= new JButton();
        jBtnReturn.setFont(f);
        buttonPanel		= new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        doiPanel.add(jLblDOI);
        doiPanel.add(jTFDOI);
        jTFDOI.addFocusListener(this);
        
        jContentPane.add(doiPanel, BorderLayout.NORTH);
        
    	datePanel.add(jLblDate);
    	datePanel.add(jTFDate);
    	jTFDate.addFocusListener(this);
    	
    	jContentPane.add(datePanel, BorderLayout.SOUTH);
        
        jTAData.addFocusListener(this);      
        dataScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        dataScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        jContentPane.add(dataScrollPane, BorderLayout.CENTER);

        jControlPane.add(jContentPane, BorderLayout.CENTER);
        
        jBtnCancel.setText(resourceBundle.getString(CANCEL));
        jBtnCancel.addActionListener(new java.awt.event.ActionListener() {
            /* (non-Javadoc)
             * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jBtnReturn.setText(resourceBundle.getString(RETURN));
        jBtnReturn.addActionListener(new java.awt.event.ActionListener() {
            /* (non-Javadoc)
             * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        buttonPanel.add(jBtnReturn);
        buttonPanel.add(jBtnCancel);
        
        jTFDOI.setEditable(!isPublished && isEditedByUser);
        jTAData.setEditable(!isPublished && isEditedByUser);
        jTFDate.setEditable(!isPublished && isEditedByUser && date);
        jBtnReturn.setEnabled(!isPublished && isEditedByUser);	
        
        jControlPane.add(buttonPanel, BorderLayout.SOUTH);
 
        this.add(jControlPane);
        pack();
    }

    /* Return null when clicking the Cancel button */    
    /**
     * @param evt
     */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
    	returnValue = null;
    	
    	dispose();
    }
    
    /* Create and return a new string when clicking the Return button */
    /**
     * @param evt
     */
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
    	if (jTAData != null) {
    		returnValue[0] = jTAData.getText();
    	}
    	if (jTFDOI != null) {
    		returnValue[1] = jTFDOI.getText();
    	}
    	if (jTFDate != null) {
    		returnValue[2] = jTFDate.getText();
    	}
   	
    	dispose();
    }

    /**
     * This is the method, what produces the return object.
     * @return Return the created String instance.
     */
    /**
     * @return
     */
    public String[] getValue() {
    	this.setVisible(true);
        
        return returnValue;
    }
    
	/* (non-Javadoc)
	 * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
	 */
	public void focusGained(FocusEvent e){
		if(e.getSource() instanceof JTextArea) {
			((JTextArea)e.getSource()).setBackground(Color.yellow);
		}
		if(e.getSource() instanceof JTextField) {
			((JTextField)e.getSource()).setBackground(Color.yellow);
		}
	}
	/* (non-Javadoc)
	 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
	 */
	public void focusLost(FocusEvent e){
		if(e.getSource() instanceof JTextArea) {
			((JTextArea)e.getSource()).setBackground(Color.white);
		}
		if(e.getSource() instanceof JTextField) {
			((JTextField)e.getSource()).setBackground(Color.white);
		}
	}

}


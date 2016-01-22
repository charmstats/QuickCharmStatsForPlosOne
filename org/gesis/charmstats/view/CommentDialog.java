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
 *	@since	0.1
 *
 */
public class CommentDialog extends JDialog implements FocusListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String BUNDLE		= "org.gesis.charmstats.resources.DesktopBundle";
	
	public static final String DIALOG_TITLE	= "dia_com_tit";
	public static final String SUBJECT		= "dia_com_sub";
	public static final String RETURN		= "dia_com_ret";
	public static final String CANCEL		= "dia_com_can";
	
	/*
	 *	Fields
	 */
	Locale				currentLocale;
	ResourceBundle		resourceBundle;
	
	private JPanel		jControlPane;
    private JPanel		subjectPanel;
    private JLabel		jLblSubject;
    private JTextField	jTFSubject;
    private JScrollPane	textScrollPane;
    private JTextArea	jTAText;
    private JPanel		buttonPanel;
    private JButton		jBtnCancel;
    private JButton		jBtnReturn;

    private String[] returnValue = {"",""};

    /** Creates new form TextEditorDialog */
    /**
     * @param parent
     * @param titel
     * @param argValue
     * @param subject
     * @param modal
     * @param isPublished
     * @param isEditedByUser
     * @param locale
     * @param f
     */
    public CommentDialog(java.awt.Frame parent, String titel, String[] argValue, boolean subject, boolean modal, boolean isPublished, boolean isEditedByUser, Locale locale, Font f) {	
        super(parent, titel, modal);
        
        currentLocale	= locale;
        
        initComponents(subject, argValue, isPublished, isEditedByUser, f);	
    }

    /** This method is called from within the constructor to
     * initialize the form.
     */
    /**
     * @param subject
     * @param argValue
     * @param isPublished
     * @param isEditedByUser
     * @param f
     */
    private void initComponents(Boolean subject, String[] argValue, boolean isPublished, boolean isEditedByUser, Font f) {
    	resourceBundle = ResourceBundle.getBundle(BUNDLE, currentLocale);
    	setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    	
        jControlPane	= new JPanel(new BorderLayout());

        jLblSubject		= new JLabel(resourceBundle.getString(SUBJECT));
        jLblSubject.setFont(f);
        jTFSubject		= new JTextField(40);
        jTFSubject.setFont(f);
        subjectPanel	= new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        jLblSubject.setEnabled(subject);
        jTFSubject.setEnabled(subject);
    	if ((argValue != null) &&
    			(argValue.length > 1))
    		jTFSubject.setText(argValue[1]);
 
        jTAText			= new JTextArea(6, 48);
        jTAText.setFont(f);
    	if ((argValue != null) &&
    			(argValue.length > 0))
    		jTAText.setText(argValue[0]);
		jTAText.setLineWrap(true);
		jTAText.setWrapStyleWord(true);
        textScrollPane	= new JScrollPane(jTAText);

        jBtnCancel		= new JButton();
        jBtnCancel.setFont(f);
        jBtnReturn		= new JButton();
        jBtnReturn.setFont(f);
        buttonPanel		= new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
    	subjectPanel.add(jLblSubject);
    	subjectPanel.add(jTFSubject);
    	jTFSubject.addFocusListener(this);
    	
    	jControlPane.add(subjectPanel, BorderLayout.NORTH);
        
        jTAText.addFocusListener(this);      
        textScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        textScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        jControlPane.add(textScrollPane, BorderLayout.CENTER);

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
        
        jTAText.setEditable(!isPublished && isEditedByUser);		
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
    	if (jTAText != null) {
    		returnValue[0] = jTAText.getText();
    	}
    	if (jTFSubject != null) {
    		returnValue[1] = jTFSubject.getText(); // jTAText wrong, corrected on 14.09.15 
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

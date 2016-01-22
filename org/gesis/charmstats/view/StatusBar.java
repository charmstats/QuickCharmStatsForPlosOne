package org.gesis.charmstats.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class StatusBar extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String BUNDLE	= "org.gesis.charmstats.resources.StatusBarBundle";
	
	public static final String READY	= "sb_ready";
	
	/*
	 *	Fields
	 */
	Locale 		  	currentLocale;
	ResourceBundle	resourceBundle; 
	
	private JLabel leftSideStatus	= new JLabel();
	private JLabel rightSideStatus	= new JLabel();
	
	/*
	 *	Constructor
	 */
    /**
     * @param locale
     */
    public StatusBar(Locale locale) {
    	super();
        
		currentLocale	= locale;
		resourceBundle	= ResourceBundle.getBundle(BUNDLE, currentLocale);
        
        setLayout(new BorderLayout());
        add(leftSideStatus, BorderLayout.WEST);
        add(rightSideStatus, BorderLayout.EAST);
        
        setLeftSideMessage(resourceBundle.getString(READY));
    }
        
    /*
     *	Methods
     */
    /**
     * @param message
     */
    public void setLeftSideMessage(String message) {
    	leftSideStatus.setText(message);
    }
    
    /**
     * @param message
     */
    public void setRightSideMessage(String message) {
    	rightSideStatus.setText(message);
    }
    
	/**
	 * @param locale
	 */
	protected void changeLanguage(Locale locale) {
		ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, locale);
		
		setLeftSideMessage(bundle.getString(READY));
	}
	
	/**
	 * @param f
	 */
	protected void changeFont(Font f) {
		leftSideStatus.setFont(f);
		rightSideStatus.setFont(f);
	}
}

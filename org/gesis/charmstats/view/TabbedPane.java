package org.gesis.charmstats.view;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JTabbedPane;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class TabbedPane extends JTabbedPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 812385749206912099L;
	
	/*
	 *	Fields
	 */
	Locale 		  	currentLocale;
	ResourceBundle	resourceBundle;
	
	private Tab 	selectedTab;
	
	/*
	 *	Constructor
	 */
	/**
	 * 
	 */
	public TabbedPane() {
		super();
	}
	
    /**
     * @return
     */
    public Tab getSelectedTab() {
    	return selectedTab;
    }
    
    /**
     * @param tab
     */
    public void setSelectedTab(Tab tab) {
    	selectedTab = tab;
    }
	
}

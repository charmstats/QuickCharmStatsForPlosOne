package org.gesis.charmstats.model;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public enum PersonRole {
	NONE			(0, "pe_r_none"),
	AUTHOR			(1, "pe_r_author"),
	EDITOR			(2, "pe_r_editor"), 
	CERTIFIED_USER	(4, "pe_r_certified_user");
	
	/*
	 *	Fields
	 */
	public static final String BUNDLE	= "org.gesis.charmstats.resources.ModelBundle"; 
	
	private final int		id;
	private final String	label;
	
	private Locale			locale;
	
	/** Resources for the default locale */
	private ResourceBundle res = 
		ResourceBundle.getBundle(BUNDLE);
	
	/*
	 *	Constructor
	 */
	PersonRole(int id, String label) {
        this.id		= id;
        this.label	= label;
    }
	
	/*
	 *	Methods
	 */
	public void setLocale(Locale loc) {
		this.locale = loc;
		this.res = ResourceBundle.getBundle(BUNDLE, locale);
	}
	
	public String toString() {
		return res.getString(getLabel());
	}
	
	public int getID() {
		return id;
	}

	public String getLabel() {
		return label;
	}
	
	public static PersonRole getItem(int id) {
		PersonRole item = null;
		
		switch (id) {
			case 0:
				item = NONE;
				break;
			case 1:
				item = AUTHOR;
				break;
			case 2:
				item = EDITOR;
				break;
			case 4:
				item = CERTIFIED_USER;
				break;
			default:
				item = NONE;
		}
		
		return item;
	}
}

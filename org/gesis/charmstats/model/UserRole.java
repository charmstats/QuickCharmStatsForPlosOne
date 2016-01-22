package org.gesis.charmstats.model;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public enum UserRole {
	NONE	( 0, "us_r_none"),
	ADMIN	( 1, "us_r_admin"),
	USER	( 2, "us_r_user"),
	TROUBLE	( 4, "us_r_trouble");
	
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
	/**
	 * @param id
	 * @param label
	 */
	UserRole(int id, String label) {
        this.id		= id;
        this.label	= label;
    }
	
	/*
	 *	Methods
	 */
	/**
	 * @param loc
	 */
	public void setLocale(Locale loc) {
		this.locale = loc;
		this.res = ResourceBundle.getBundle(BUNDLE, locale);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	public String toString() {
		return res.getString(getLabel());
	}
	
	/**
	 * @return
	 */
	public int getID() {
		return id;
	}

	/**
	 * @return
	 */
	public String getLabel() {
		return label;
	}
 
	/**
	 * @param id
	 * @return
	 */
	public static UserRole getItem(int id) {
		UserRole item = null;
		
		switch (id) {
			case 0:
				item = NONE;
				break;
			case 1:
				item = ADMIN;
				break;
			case 2:
				item = USER;
				break;
			case 4:
				item = TROUBLE;
				break;
			default:
				item = NONE;

		}
		
		return item;
	}
}
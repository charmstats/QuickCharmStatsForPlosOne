package org.gesis.charmstats.persistence;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public enum SearchBooleanOperator {
	NONE	(0, "sb_sbo_none"),
	AND		(1, "sb_sbo_and"), 
	OR		(2, "sb_sbo_or"), 
	NOT		(4, "sb_sbo_not");
		
	/*
	 *	Fields
	 */
	public static final String BUNDLE	= "org.gesis.charmstats.resources.ModelBundle";
	
	private final int		id;
	private final String	label;
	
	private Locale		locale;
	
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
	SearchBooleanOperator(int id, String label) {
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
	
	/** @return the locale-dependent message */
	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	public String toString() {
	    return res.getString(getLabel());
	}
	
//	public String toString() {
//		return label;
//	}
	
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
	public static SearchBooleanOperator getItem(int id) {
		SearchBooleanOperator item = null;
		
		switch (id) {
			case 1:
				item = AND;
				break;
			case 2:
				item = OR;
				break;
			default:
				item = NOT;
		}
		
		return item;
	}
}
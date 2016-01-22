package org.gesis.charmstats.model;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public enum VariableType {
	NONE		(  0, "va_t_none"),
	ORIGINAL	(  1, "va_t_orig"),
    HARMONIZED	(  2, "va_t_harm"),
    TECHNICAL	(  4, "va_t_tech"),
    WEIGHT		(  8, "va_t_wt"),
    FILTER		( 16, "va_t_filt"),
	FILTER_S	( 32, "va_t_filt_area"),
	FILTER_C	( 64, "va_t_filt_date"),
	FILTER_P	(128, "va_t_filt_pop");
	
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
	VariableType(int id, String label) {
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
//		return getLabel();
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
	public static VariableType getItem(int id) {
		VariableType item = null;
		
    	switch (id) {
    		case 0:
    			item = NONE;
    			break;
    		case 1:
    			item = ORIGINAL;
    			break;
    		case 2:
    			item = HARMONIZED;
    			break;
    		case 4:
    			item = TECHNICAL;
    			break;
    		case 8:
    			item = WEIGHT;
    			break;
    		case 16:
    			item = FILTER;
    			break;
    		default:
    			item = NONE;
    	}
    	
		return item;
	}
	
}

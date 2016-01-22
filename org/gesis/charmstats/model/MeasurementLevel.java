package org.gesis.charmstats.model;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 *	This is an Entity-Class
 * 
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public enum MeasurementLevel {
	NONE		( 0, 	"me_l_none"),
	NOMINAL		( 1, 	"me_l_nom"),
	ORDINAL		( 2, 	"me_l_ord"),
	INTERVAL	( 4,	"me_l_int"),
	RATIO		( 8,	"me_l_rat"),
	CONTINUOUS	(16,	"me_l_con");
	
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
	MeasurementLevel (int id, String label) {
		this.id = id;
		this.label = label;
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
	public static MeasurementLevel getItem(int id) {
		MeasurementLevel item = null;
		
    	switch (id) {
    		case 0:
    			item = NONE;
    			break;
    		case 1:
    			item = NOMINAL;
    			break;
    		case 2:
    			item = ORDINAL;
    			break;
    		case 4:
    			item = INTERVAL;
    			break;
    		case 8:
    			item = RATIO;
    			break;
    		case 16:
    			item = CONTINUOUS;
    			break;
    		default:
    			item = NONE;
    	}
    	
		return item;
	}
}

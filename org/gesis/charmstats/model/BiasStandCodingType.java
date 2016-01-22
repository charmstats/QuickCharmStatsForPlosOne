package org.gesis.charmstats.model;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 
 *	@author Martin Friedrichs
 *	@since	0.1
 *
 */
public enum BiasStandCodingType {
	NONE		( 0, "bs_none");
	
	/*
	 *	Fields
	 */
	public static final String BUNDLE	= "org.gesis.charmstats.resources.ModelBundle";
	
	private final int 		id;
	private final String	label;
	
	private Locale			locale;
	
	/** Resources for the default locale */
	private ResourceBundle res = 
		ResourceBundle.getBundle(BUNDLE);
	
	/**
	 * 	Constructor
	 * 
	 *	@param id
	 *	@param label
	 */
	BiasStandCodingType(int id, String label) {
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
	public static BiasStandCodingType getItem(int id) {
		BiasStandCodingType item = null;
		
		switch (id) {
			case  0:
				item = NONE;
				break;
			default:
				item = NONE;
		}
		
		return item;
	}
	
}

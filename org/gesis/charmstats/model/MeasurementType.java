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
public enum MeasurementType {
	NONE			( 0, "me_t_none"),
	CLASSIFICATION 	( 1, "me_t_class"), 
	SCALE 			( 2, "me_t_scale"), 
	INDEX			( 4, "me_t_index");
	
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
	MeasurementType(int id, String label) {
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
	public static MeasurementType getItem(int id) {
		MeasurementType item = null;
		
		switch (id) {
			case 0:
				item = NONE;
				break;
			case 1:
				item = CLASSIFICATION;
				break;
			case 2:
				item = SCALE;
				break;
			case 4:
				item = INDEX;
				break;
			default:
				item = NONE;
		}
		
		return item;
	}
	
}

package org.gesis.charmstats.model;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 
 *	@author Martin Friedrichs
 *	@since	0.1, known former as "MeasurementLevel
 *
 */
public enum TypeOfData {
	NONE		(0, "t_d_none"),
	INDIVIDUAL 	(1, "t_d_ind"), 
	AGGREGATE 	(2, "t_d_agg");
	
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
	TypeOfData(int id, String label) {
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
	public static TypeOfData getItem(int id) {
		TypeOfData item = null;
		
		switch (id) {
			case 0:
				item = NONE;
				break;		
			case 1:
				item = INDIVIDUAL;
				break;
			case 2:
				item = AGGREGATE;
				break;
//			case 4:
//				item = MESO;
//				break;
			default:
				item = NONE;
		}
		
		return item;
	}
	
}

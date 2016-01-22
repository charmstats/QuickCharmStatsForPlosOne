package org.gesis.charmstats.model;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 
 *	@author Martin Friedrichs
 *	@since	0.1
 *
 */
public enum BiasRatingType {
	NONE		( 0, "br_none"),
	IDENTICAL	( 1, "br_ident"),
	SIM_N_COM	( 2, "br_sim_com"),  
	SIM_N_IN	( 4, "br_sim_in" ),
	TO_BE_TRANS	( 8, "br_to_trans"),
	DIFFERENT	(99, "br_diff");
	
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
	 *	Constructor
	 *
	 *	@param id
	 *	@param label
	 */
	BiasRatingType(int id, String label) {
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
	public static BiasRatingType getItem(int id) {
		BiasRatingType item = null;
		
		switch (id) {
			case  0:
				item = NONE;
				break;
			case  1:
				item = IDENTICAL;
				break;
			case  2:
				item = SIM_N_COM;
				break;
			case  4:
				item = SIM_N_IN;
				break;
			case  8:
				item = TO_BE_TRANS;
				break;
			case 99:
				item = DIFFERENT;
				break;
			default:
				item = NONE;
		}
		
		return item;
	}
}

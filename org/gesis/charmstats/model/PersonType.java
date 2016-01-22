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
public enum PersonType {
	NONE		(0, "pe_t_none"),
	INDIVIDUAL	(1, "pe_t_individual"),
	INSTITUTION	(2, "pe_t_institution"),
	RES_GROUP	(4, "pe_t_research_group");
	
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
	PersonType(int id, String label) {
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
	public static PersonType getItem(int id) {
		PersonType item = null;
		
		switch (id) {
			case 0:
				item = NONE;
				break;
			case 1:
				item = INDIVIDUAL;
				break;
			case 2:
				item = INSTITUTION;
				break;
			case 4:
				item = RES_GROUP;
				break;
			default:
				item = NONE;
		}
		
		return item;
	}
}

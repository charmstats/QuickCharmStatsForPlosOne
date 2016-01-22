package org.gesis.charmstats.model;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public enum ParticipantRole {
	NONE			( 0, "pa_r_none"),
	PROJECT_OWNER	( 1, "pa_r_project_owner"),
	EDITOR			( 2, "pa_r_editor"),
	FORMER_MEMBER	( 4, "pa_r_former");
	
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
	ParticipantRole(int id, String label) {
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
	public static ParticipantRole getItem(int id) {
		ParticipantRole item = null;
		
		switch (id) {
			case 0:
				item = NONE;
				break;
			case 1:
				item = PROJECT_OWNER;
				break;
			case 2:
				item = EDITOR;
				break;
			case 4:
				item = FORMER_MEMBER;
				break;
			default:
				item = NONE;

		}
		
		return item;
	}
}

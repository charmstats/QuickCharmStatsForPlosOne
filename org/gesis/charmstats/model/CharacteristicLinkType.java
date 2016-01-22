package org.gesis.charmstats.model;

/**
 * 
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public enum CharacteristicLinkType {
	NONE			( 0, ""),
	CATEGORY		( 1, "Category"),
	SPECIFICATION	( 2, "Specification"),
	PRESCRIPTION	( 4, "Prescription"),
	VALUE			( 8, "Value");
	
	/*
	 *	Fields
	 */
	private final int		id;
	private final String	label;
	
	/**
	 *	Constructor
	 * 
	 *	@param id
	 *	@param label
	 */
	CharacteristicLinkType(int id, String label) {
		this.id		= id;
		this.label	= label;
	}
	
	/*
	 *	Methods
	 */
	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	public String toString() {
		return getLabel();
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
	public static CharacteristicLinkType getItem(int id) {
		CharacteristicLinkType item = null;
		
		switch (id) {
			case 0:
				item = NONE;
				break;
			case 1:
				item = CATEGORY;
				break;
			case 2:
				item = SPECIFICATION;
				break;
			case 4:
				item = PRESCRIPTION;
				break;
			case 8:
				item = VALUE;
				break;
			default:
				item = NONE;
		}
		
		return item;
	}
}

package org.gesis.charmstats.model;

/**
 * 
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public enum CharacteristicMapType {
	NONE				( 0, ""),
	SPECIFICATION		( 1, "Specification"),
	PRESCRIPTION		( 2, "Prescription"),
	VALUE				( 4, "Value");	
	/*
	 *	Fields
	 */
	private final int		id;
	private final String	label;
	

	/**
	 * Constructor
	 * 
	 * @param id
	 * @param label
	 */
	CharacteristicMapType(int id, String label) {
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
	public static CharacteristicMapType getItem(int id) {
		CharacteristicMapType item = null;
		
		switch (id) {
			case 0:
				item = NONE;
				break;
			case 1:
				item = SPECIFICATION;
				break;
			case 2:
				item = PRESCRIPTION;
				break;
			case 4:
				item = VALUE;
				break;
			default:
				item = NONE;
		}
		
		return item;
	}

}

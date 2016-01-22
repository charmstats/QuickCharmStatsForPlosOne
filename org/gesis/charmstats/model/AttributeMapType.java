package org.gesis.charmstats.model;

/**
 * 
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public enum AttributeMapType {
	NONE				( 0, ""),
	SPECIFICATION		( 1, "Specification"), 
	VALUE				( 2, "Value"),
	ASSIGNED_INDICATOR	( 4, "Assigned Indicator"),
	ASSIGNED_VARIABLE	( 8, "Assigned Variable"),
	ALGORITHM			(16, "Algorithm"),
	RECODE				(32, "Recode"); 
	
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
	AttributeMapType(int id, String label) {
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
	public static AttributeMapType getItem(int id) {
		AttributeMapType item = null;
		
		switch (id) {
			case 0:
				item = NONE;
				break;
			case 1:
				item = SPECIFICATION;
				break;
			case 2:
				item = VALUE;
				break;
			case 4:
				item = ASSIGNED_INDICATOR;
				break;
			case 8:
				item = ASSIGNED_VARIABLE;
				break;
			case 16:
				item = ALGORITHM;
				break;
			case 32:
				item = RECODE;
				break;
			default:
				item = NONE;
		}
		
		return item;
	}
	
}

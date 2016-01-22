package org.gesis.charmstats.model;

/**
 * 
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public enum InstanceMapType {
	NONE			( 0, ""),
	CONCEPTUAL		( 1, "Conceptual Step"), 
	OPERATIONAL		( 2, "Operational Step"),
	DATA_RECODING	( 4, "Data Re-Coding Step"); 

	/*
	 *	Fields
	 */
	private final int		id;
	private final String	label;
	
	/*
	 *	Constructor
	 */
	/**
	 * @param id
	 * @param label
	 */
	InstanceMapType(int id, String label) {
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
	public static InstanceMapType getItem(int id) {
		InstanceMapType item = null;
		
		switch (id) {
			case 0:
				item = NONE;
				break;
			case 1:
				item = CONCEPTUAL;
				break;
			case 2:
				item = OPERATIONAL;
				break;
			case 4:
				item = DATA_RECODING;
				break;
			default:
				item = NONE;
		}
		
		return item;
	}
}

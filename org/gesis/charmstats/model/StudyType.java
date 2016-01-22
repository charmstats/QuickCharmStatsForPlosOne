package org.gesis.charmstats.model;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public enum StudyType {
	NONE		( 0, "None");
	
	/*
	 *	Fields
	 */
	private final int 		id;
	private final String	label;
	
	/*
	 *	Constructor
	 */
	/**
	 * @param id
	 * @param label
	 */
	StudyType(int id, String label) {
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
	public static StudyType getItem(int id) {
		StudyType item = null;
		
		switch (id) {
			case 0:
				item = NONE;
				break;
			default:
				item = NONE;

		}
		
		return item;
	}
	
}

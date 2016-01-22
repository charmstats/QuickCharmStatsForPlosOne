package org.gesis.charmstats.persistence;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public enum RefRelationtype {
	ASSIGNED	(1, "Assigned"), 
	CONTAINED	(2, "Contained"), 
	REFERENCED	(4, "Referenced");
	
	static final int entity_id = 9991; 
	
	/*
	 *	Fields
	 */
	private final int		id;
	private final String	default_label;
	
	/*
	 *	Constructor
	 */
	/**
	 * @param id
	 * @param default_label
	 */
	RefRelationtype(int id, String default_label) {
        this.id				= id;
        this.default_label	= default_label;
    }
	
	/*
	 *	toString
	 */
	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	public String toString() {
		return getDefaultLabel();
	}
	
	/*
	 *	Methods
	 */
	/**
	 * @return
	 */
	public int getID() {
		return id;
	}

	/**
	 * @return
	 */
	public String getDefaultLabel() {
		return default_label;
	}
 
	/**
	 * @param id
	 * @return
	 */
	public static RefRelationtype getItem(int id) {
		RefRelationtype refRelationtype = null;
		
		switch (id) {
			case 1:
				refRelationtype = ASSIGNED;
				break;
			case 2:
				refRelationtype = CONTAINED;
				break;
			case 4:
			default:
				refRelationtype = REFERENCED;
		}
		
		return refRelationtype;
	}
}

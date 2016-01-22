package org.gesis.charmstats.model;

/**
 * 
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public enum CommentType {
	NONE	(0, "None");
	
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
	CommentType(int id, String label) {
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
	public static CommentType getItem(int id) {
		CommentType item = null;
		
		switch (id) {
			case 0:
				item = NONE;
				break;
			/* TODO */
			default:
				item = NONE;
		}
		
		return item;
	}
	
}

package org.gesis.charmstats.model;

/**
 * 
 * @author Martin Friedrichs
 * @since	0.1
 *
 */
public enum QuestionType { 
	NONE		( 0, "None"),
	SIMPLE		( 1, "Simple"),
	MULTIPLE	( 2, "Multiple");
	
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
	QuestionType(int id, String label) {
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
	public static QuestionType getItem(int id) {
		QuestionType item = null;
		
    	switch (id) {
    		case 0:
    			item = NONE;
    			break;
    		case 1:
    			item = SIMPLE;
    			break;
    		case 2:
    			item = MULTIPLE;
    			break;
    		default:
    			item = NONE;
    	}
    	
		return item;
	}

}

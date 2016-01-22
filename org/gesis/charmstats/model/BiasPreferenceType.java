package org.gesis.charmstats.model;

/**
 * 
 *	@author Martin Friedrichs
 *	@since	0.1
 *
 */
public enum BiasPreferenceType {
	NONE			(  0, "  0%"),
	A_QUARTER		( 25, " 25%"),
	A_THIRD			( 33, " 33%"),
	A_HALF			( 50, " 50%"),
	TWO_THIRD		( 66, " 66%"),
	THREE_QUARTER	( 75, " 75%"),
	FULL			(100, "100%");
	
	/*
	 *	Fields
	 */
	private final int 		id;
	private final String	label;
	
	/**
	 *	Constructor
	 * 
	 *	@param id
	 *	@param label
	 */
	BiasPreferenceType(int id, String label) {
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
	
	public static BiasPreferenceType getItem(int id) {
		BiasPreferenceType item = null;
		
		switch (id) {
			case   0:
				item = NONE;
				break;
			case  25:
				item = A_QUARTER;
				break;
			case  33:
				item = A_THIRD;
				break;
			case  50:
				item = A_HALF;
				break;
			case  66:
				item = TWO_THIRD;
				break;
			case  75:
				item = THREE_QUARTER;
				break;
			case 100:
				item = FULL;
				break;				
			default:
				item = NONE;
		}
		
		return item;
	}
	
}

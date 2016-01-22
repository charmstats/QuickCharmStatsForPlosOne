package org.gesis.charmstats.model;

/**
 * 
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public enum InstanceType {
	NONE				( 0, ""),
	PROJECT_SETUP		( 1, "Project Setup"),
	CONCEPTUAL			( 2, "Conceptual"), 
	OPERATIONAL			( 4, "Operational"),
	SEARCH_N_COMPARE	( 8, "SearchNCompare"),	
	DATA_CODING			(16, "Data Re-Coding"),
	VISUALIZE			(32, "Visualize");
	
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
	InstanceType(int id, String label) {
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
	public static InstanceType getItem(int id) {
		InstanceType item = null;
		
		switch (id) {
			case  0:
				item = NONE;
				break;
			case  1:
				item = PROJECT_SETUP;
				break;
			case  2:
				item = CONCEPTUAL;
				break;
			case  4:
				item = OPERATIONAL;
				break;
			case  8:
				item = SEARCH_N_COMPARE;
				break;
			case 16:
				item = DATA_CODING;
				break;
			case 32:
				item = VISUALIZE;
				break;
			default:
				item = NONE;
		}
		
		return item;
	}
	
}

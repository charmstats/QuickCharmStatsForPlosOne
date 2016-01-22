package org.gesis.charmstats.model;

/**
 * 
 * @author	Martin Friedrichs
 * @since	0.1
 */
public enum AttributeLinkType {
	NONE		( 0, ""),
	MEASUREMENT	( 1, "Measurement"),
	DIMENSION	( 2, "Dimension"),  
	INDICATOR	( 4, "Indicator"), 
	VARIABLE	( 8, "Variable"), 
	COMPARISON	(16, "Comparison");
	
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
	AttributeLinkType(int id, String label) {
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
	
	public static AttributeLinkType getItem(int id) {
		AttributeLinkType item = null;
		
		switch (id) {
			case 0:
				item = NONE;
				break;
			case 1:
				item = MEASUREMENT;
				break;
			case 2:
				item = DIMENSION;
				break;
			case 4:
				item = INDICATOR;
				break;
			case 8:
				item = VARIABLE;
				break;
			default:
				item = NONE;
		}
		
		return item;
	}
	
}

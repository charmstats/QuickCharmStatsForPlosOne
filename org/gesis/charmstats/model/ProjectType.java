package org.gesis.charmstats.model;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public enum ProjectType {
	NONE					( 0, ""),
	HARMONIZATION_PROJECT	( 1, "Harmonization"), 
	DOCUMENTATION_CSI		( 2, "Documentation"), 
	CONCEPTUAL_BASIS_CSI	( 4, "Conceptual Basis");
 
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
	ProjectType(int id, String label) {
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
	public static ProjectType getItem(int id) {
		ProjectType item = null;
		
		switch (id) {
			case 0:
				item = NONE;
				break;
			case 1:
				item = HARMONIZATION_PROJECT;
				break;
			case 2:
				item = DOCUMENTATION_CSI;
				break;
			case 4:
				item = CONCEPTUAL_BASIS_CSI;
				break;
			default:
				item = NONE;
		}
		
		return item;
	}
	
}

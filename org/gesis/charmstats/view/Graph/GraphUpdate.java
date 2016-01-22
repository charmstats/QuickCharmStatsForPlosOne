package org.gesis.charmstats.view.Graph;

/**
 * 
 * @author	Martin Friedrichs
 * @since	0.8.1
 *
 */
public enum GraphUpdate {
	DONE_NOTHING("done nothing"),
	LAYER_REFRESH("layer refresh");

	/*
	 *	Fields
	 */
	private final String _action;
	
	/*
	 *	Constructor
	 */
	/**
	 * @param action
	 */
	GraphUpdate(String action) {
		_action = action;
	}

	/*
	 *	Methods
	 */
	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	public String toString() {
		return _action;
	}
	
}

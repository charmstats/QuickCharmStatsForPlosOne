package org.gesis.charmstats;

/**
 * 
 * @author	Martin Friedrichs
 * @since	0.1
 *
 */
public class IdentifiedParameter {
	
	/*
	 *	Fields
	 */
	private int			id;
	private Object[]	parameters;

	/*
	 *	Constructor
	 */
	/**
	 * @param id
	 */
	public IdentifiedParameter(int id) {
		this.setID(id);
	}

	/*
	 *	Methods
	 */
	/* ID */
	/**
	 * @param id
	 */
	public void setID(int id) {
		this.id = id;
	}

	/**
	 * @return
	 */
	public int getID() {
		return id;
	}

	/* Parameter(s) */
	/**
	 * @param parameters
	 */
	public void setParameters(Object[] parameters) {
		this.parameters = parameters;
	}

	/**
	 * @return
	 */
	public Object[] getParameters() {
		return parameters;
	}
	
}

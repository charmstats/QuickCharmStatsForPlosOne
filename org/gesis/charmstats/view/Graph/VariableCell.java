package org.gesis.charmstats.view.Graph;

import org.gesis.charmstats.model.Variable;
import org.jgraph.graph.DefaultGraphCell;

/**
 * 
 * @author	Martin Friedrichs
 * @since	0.8.1
 *
 */
public class VariableCell extends DefaultGraphCell {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 *	Fields
	 */
	private Variable variable = null;
	
	/*
	 *	Constructor
	 */
	/**
	 * 
	 */
	public VariableCell() {
		this (null);
	}

	/**
	 * @param userObject
	 */
	public VariableCell(Object userObject) {
		super (userObject);
		
		if (userObject instanceof Variable)
			setVariable((Variable)userObject);
	}
	
	/*
	 *	Methods
	 */
	/**
	 * @param variable
	 */
	public void setVariable(Variable variable) {
		this.variable = variable;
	}

	/**
	 * @return
	 */
	public Variable getVariable() {
		return variable;
	}

}

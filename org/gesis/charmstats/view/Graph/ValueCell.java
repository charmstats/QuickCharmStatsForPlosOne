package org.gesis.charmstats.view.Graph;

import org.jgraph.graph.DefaultGraphCell;

/**
 * 
 * @author	Martin Friedrichs
 * @since	0.8.1
 *
 */
public class ValueCell extends DefaultGraphCell  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 *	Empty Constructor
	 */
	/**
	 * 
	 */
	public ValueCell() {
		this (null);
	}
	
	/*
	 *	Construct Cell for userObject
	 */
	/**
	 * @param userObject
	 */
	public ValueCell(Object userObject) {
		super (userObject);
	}
	
}

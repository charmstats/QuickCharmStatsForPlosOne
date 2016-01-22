package org.gesis.charmstats.view.Graph;

import org.jgraph.graph.DefaultGraphCell;

/**
 * 
 * @author	Martin Friedrichs
 * @since	0.8.1
 *
 */
public class RoundedCell extends DefaultGraphCell {
	
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
	public RoundedCell() {
		this (null);
	}
	
	/*
	 *	Construct Cell for Userobject
	 */
	/**
	 * @param userObject
	 */
	public RoundedCell(Object userObject) {
		super (userObject);
	}
}



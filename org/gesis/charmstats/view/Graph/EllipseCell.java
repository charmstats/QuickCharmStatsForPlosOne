package org.gesis.charmstats.view.Graph;

import org.jgraph.graph.DefaultGraphCell;

/**
 * 
 * @author	Martin Friedrichs
 * @since	0.8.1
 *
 */
public class EllipseCell extends DefaultGraphCell {
	
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
	public EllipseCell() {
		this (null);
	}
	
	/*
	 *	Construct Cell for Userobject
	 */
	/**
	 * @param userObject
	 */
	public EllipseCell(Object userObject) {
		super (userObject);
	}
}

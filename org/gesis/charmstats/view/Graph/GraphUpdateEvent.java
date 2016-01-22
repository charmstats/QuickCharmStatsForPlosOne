package org.gesis.charmstats.view.Graph;

import java.util.EventObject;

/**
 * 
 * @author	Martin Friedrichs
 * @since	0.8.1
 *
 */
public class GraphUpdateEvent extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * Field(s)
	 */
	private GraphUpdate _graphUpdate;

	/*
	 * Constructor
	 */
	/**
	 * @param source
	 * @param graphUpdate
	 */
	public GraphUpdateEvent(Object source, GraphUpdate graphUpdate) {
		super(source);

		_graphUpdate = graphUpdate;
	}

	/*
	 * Method(s)
	 */
	/**
	 * @return
	 */
	public GraphUpdate graphUpdate() {
		return _graphUpdate;
	}
	
	/**
	 * @return
	 */
	public GraphUpdate getGraphUpdate() {
		return _graphUpdate;
	}

}

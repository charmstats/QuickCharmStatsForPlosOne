package org.gesis.charmstats.view.Graph;

import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.GraphConstants;

/**
 * 
 * @author	Martin Friedrichs
 * @since	0.8.1
 *
 */
public class GEdge  extends DefaultEdge {

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
	public GEdge() {
		super();
		
		int arrow = GraphConstants.ARROW_CLASSIC;
		
		GraphConstants.setLineEnd(this.getAttributes(), arrow);
		GraphConstants.setEndFill(this.getAttributes(), true);
		GraphConstants.setLabelAlongEdge(this.getAttributes(), true);
		GraphConstants.setBendable(this.getAttributes(), false);
	}

}

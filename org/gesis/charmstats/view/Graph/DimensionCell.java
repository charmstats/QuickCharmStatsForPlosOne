package org.gesis.charmstats.view.Graph;

import org.gesis.charmstats.model.ConDimension;
import org.jgraph.event.GraphLayoutCacheEvent;
import org.jgraph.event.GraphLayoutCacheListener;
import org.jgraph.graph.DefaultGraphCell;

/**
 * 
 * @author	Martin Friedrichs
 * @since	0.8.1
 *
 */
public class DimensionCell extends DefaultGraphCell implements GraphLayoutCacheListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 *	Fields
	 */
	private ConDimension	dimension = null;	
	
	/*
	 *	Constructor
	 */
	/**
	 * 
	 */
	public DimensionCell() {
		super();
	}
	
	/**
	 * @param userObject
	 */
	public DimensionCell(Object userObject) {
		super(userObject);
		
		if (userObject instanceof ConDimension)
			setDimension((ConDimension)userObject);
	}

	/*
	 *	Methods
	 */
	/* (non-Javadoc)
	 * @see javax.swing.tree.DefaultMutableTreeNode#toString()
	 */
	public String toString() {
		if (dimension != null) 
			return dimension.toString();
		else
			return super.toString();
	}
	
	/**
	 * @param dimension
	 */
	public void setDimension(ConDimension dimension) {
		this.dimension = dimension;
	}

	/**
	 * @return
	 */
	public ConDimension getDimension() {
		return dimension;
	}

	/* (non-Javadoc)
	 * @see org.jgraph.event.GraphLayoutCacheListener#graphLayoutCacheChanged(org.jgraph.event.GraphLayoutCacheEvent)
	 */
	@Override
	public void graphLayoutCacheChanged(GraphLayoutCacheEvent arg0) {
		// Auto-generated method stub
		
		/* DoNothing */
	}

}

package org.gesis.charmstats.view.Graph;

import org.gesis.charmstats.model.ConSpecification;
import org.jgraph.event.GraphLayoutCacheEvent;
import org.jgraph.event.GraphLayoutCacheListener;
import org.jgraph.graph.DefaultGraphCell;

/**
 * 
 * @author	Martin Friedrichs
 * @since	0.8.1
 *
 */
public class SpecificationCell extends DefaultGraphCell implements GraphLayoutCacheListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 *	Fields
	 */
	private ConSpecification specification = null;
	
	/*
	 *	Constructor
	 */
	/**
	 * 
	 */
	public SpecificationCell() {
		super();
	}
	
	/**
	 * @param userObject
	 */
	public SpecificationCell(Object userObject) {
		super (userObject);
		
		if (userObject instanceof ConSpecification)
			setSpecification((ConSpecification)userObject);
	}
	
	/*
	 *	Methods
	 */
	/* (non-Javadoc)
	 * @see javax.swing.tree.DefaultMutableTreeNode#toString()
	 */
	public String toString() {
		if (specification != null)
			return specification.toString();
		else
			return super.toString();
	}
	
	/**
	 * @param specification
	 */
	public void setSpecification(ConSpecification specification) {
		this.specification = specification;		
	}

	/**
	 * @return
	 */
	public ConSpecification getSpecification() {
		return specification;
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


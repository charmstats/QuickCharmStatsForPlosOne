package org.gesis.charmstats.view.Graph;

import org.jgraph.event.GraphLayoutCacheEvent;
import org.jgraph.event.GraphLayoutCacheListener;
import org.jgraph.graph.DefaultGraphCell;

/**
 * 
 * @author Martin Friedrichs
 *
 */
public class LabelCell extends DefaultGraphCell implements GraphLayoutCacheListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 *	Fields
	 */
	private String	label = "";	
	
	/*
	 *	Constructor
	 */
	/**
	 * 
	 */
	public LabelCell() {
		super();
	}
	
	/**
	 * @param userObject
	 */
	public LabelCell(Object userObject) {
		super(userObject);
		
		if (userObject instanceof String)
			setLabel((String)userObject);
	}

	/*
	 *	Methods
	 */
	/* (non-Javadoc)
	 * @see javax.swing.tree.DefaultMutableTreeNode#toString()
	 */
	public String toString() {
		if (label != null) 
			return label;
		else
			return super.toString();
	}
	
	/**
	 * @param label
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return
	 */
	public String getLabel() {
		return label;
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


package org.gesis.charmstats.view.Graph;

import java.awt.event.MouseEvent;

import org.gesis.charmstats.model.Category;
import org.gesis.charmstats.model.ConDimension;
import org.jgraph.event.GraphModelEvent;
import org.jgraph.event.GraphModelListener;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.GraphModel;

/**
 * 
 * @author	Martin Friedrichs
 * @since	0.8.1
 *
 */
public class ConceptualJGraph extends GJGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param model
	 */
	public ConceptualJGraph(GraphModel model) {
		super(model);

		// Add a ModelListener to the Model
		this.getModel().addGraphModelListener(new ModelListener());
	}
	
	/* expanding and collapsing: */
	/**
	 * @param model
	 * @param cache
	 */
	public ConceptualJGraph(GraphModel model, GraphLayoutCache cache) {
		super(model, cache);

		// Add a ModelListener to the Model
		this.getModel().addGraphModelListener(new ModelListener());
	}
	
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.Graph.GJGraph#getToolTipText(java.awt.event.MouseEvent)
	 */
	public String getToolTipText(MouseEvent e)
	{
		if (e != null) {
			// Fetch Cell under Mousepointer
			Object c = getFirstCellForLocation(e.getX(), e.getY());
			if (c != null)
				// Convert Cell to String and Return
				return convertValueToString(c);
		}
		return null;
	}
	
	public class ModelListener implements GraphModelListener {

		/* (non-Javadoc)
		 * @see org.jgraph.event.GraphModelListener#graphChanged(org.jgraph.event.GraphModelEvent)
		 */
		@Override
		public void graphChanged(GraphModelEvent e) {
			/*
			 *	In jGraph, changing the label of a cell, replaces the former userobject with a string.
			 *	Restoring the former userobject, together with the textual change, will be done here:
			 */
			if (e.getChange().getChanged() != null) {
				for (int i=0; i<e.getChange().getChanged().length; i++) {
					if (e.getChange().getChanged()[i] instanceof DimensionCell) {
						DimensionCell cell = (DimensionCell)e.getChange().getChanged()[i];
						ConDimension dim = cell.getDimension();
						dim.setLabel(cell.toString());
						cell.setUserObject(dim);
						
//						Map attributeMap = new HashMap();
//						GraphConstants.setGradientColor(attributeMap, Color.blue);
//					
//						Map nested = new Hashtable();
//						nested.put(cell, attributeMap);
					}
					if (e.getChange().getChanged()[i] instanceof CategoryCell) {
						CategoryCell cell = (CategoryCell)e.getChange().getChanged()[i];
						Category cat = cell.getCategory();
						cat.setLabel(cell.toString());
						cell.setUserObject(cat);
					}
				}
			}
		}
	}

}

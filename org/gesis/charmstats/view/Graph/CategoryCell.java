package org.gesis.charmstats.view.Graph;

import org.gesis.charmstats.model.Category;
import org.jgraph.event.GraphLayoutCacheEvent;
import org.jgraph.event.GraphLayoutCacheListener;
import org.jgraph.graph.DefaultGraphCell;

/**
 * 
 * @author	Martin Friedrichs
 * @since	0.8.1
 *
 */
public class CategoryCell extends DefaultGraphCell implements
		GraphLayoutCacheListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * Fields
	 */
	private Category category = null;

	/*
	 * Constructor
	 */
	/**
	 * 
	 */
	public CategoryCell() {
		super();
	}

	/**
	 * @param userObject
	 */
	public CategoryCell(Object userObject) {
		super(userObject);

		if (userObject instanceof Category)
			setCategory((Category) userObject);
	}

	/*
	 * Methods
	 */
	/* (non-Javadoc)
	 * @see javax.swing.tree.DefaultMutableTreeNode#toString()
	 */
	public String toString() {
		if (category != null)
			return category.toString();
		else
			return super.toString();
	}

	/**
	 * @param category
	 */
	public void setCategory(Category category) {
		this.category = category;
	}

	/**
	 * @return
	 */
	public Category getCategory() {
		return category;
	}

	/* (non-Javadoc)
	 * @see org.jgraph.event.GraphLayoutCacheListener#graphLayoutCacheChanged(org.jgraph.event.GraphLayoutCacheEvent)
	 */
	@Override
	public void graphLayoutCacheChanged(GraphLayoutCacheEvent arg0) {
		// Auto-generated method stub
	}

}

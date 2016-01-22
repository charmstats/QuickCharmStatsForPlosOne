package org.gesis.charmstats.view.Graph;

import org.gesis.charmstats.model.OperaIndicator;
import org.jgraph.graph.DefaultGraphCell;

/**
 * 
 * @author	Martin Friedrichs
 * @since	0.8.1
 *
 */
public class IndicatorCell  extends DefaultGraphCell {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 *	Fields
	 */
	private OperaIndicator indicator = null;
	
	/*
	 *	Constructor
	 */
	/**
	 * 
	 */
	public IndicatorCell() {
		this (null);
	}

	/**
	 * @param userObject
	 */
	public IndicatorCell(Object userObject) {
		super (userObject);
		
		if (userObject instanceof OperaIndicator)
			setIndicator((OperaIndicator)userObject);
	}

	/*
	 *	Methods
	 */
	/* (non-Javadoc)
	 * @see javax.swing.tree.DefaultMutableTreeNode#toString()
	 */
	public String toString() {
		if (indicator != null)			
			return indicator.toString();
		else
			return super.toString();
	}
	
	/**
	 * @param indicator
	 */
	public void setIndicator(OperaIndicator indicator) {
		this.indicator = indicator;
	}

	/**
	 * @return
	 */
	public OperaIndicator getIndicator() {
		return indicator;
	}

}

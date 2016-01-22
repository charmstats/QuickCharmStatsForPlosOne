package org.gesis.charmstats.view.Graph;

import org.gesis.charmstats.model.Measurement;
import org.jgraph.event.GraphLayoutCacheEvent;
import org.jgraph.event.GraphLayoutCacheListener;
import org.jgraph.graph.DefaultGraphCell;

/**
 * 
 * @author	Martin Friedrichs
 * @since	0.8.1
 *
 */
public class MeasurementCell  extends DefaultGraphCell implements GraphLayoutCacheListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 *	Fields
	 */
	private Measurement measurement = null;
	
	/*
	 *	Constructor
	 */
	/**
	 * 
	 */
	public MeasurementCell() {
		super();	
	}

	/**
	 * @param userObject
	 */
	public MeasurementCell(Object userObject) {
		super(userObject);
		
		if (userObject instanceof Measurement) {
			setMeasurement((Measurement)userObject);
		}	
	}

	/*
	 *	Methods
	 */
	/* (non-Javadoc)
	 * @see javax.swing.tree.DefaultMutableTreeNode#toString()
	 */
	public String toString() {
				
		if (measurement != null)
			return measurement.getName();
		else
			return super.toString();
	}
	
	/**
	 * @param measurement
	 */
	public void setMeasurement(Measurement measurement) {
		this.measurement = measurement;
	}

	/**
	 * @return
	 */
	public Measurement getMeasurement() {
		return measurement;
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

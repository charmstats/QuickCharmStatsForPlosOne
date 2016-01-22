package org.gesis.charmstats.view.Graph;

import org.gesis.charmstats.model.OperaPrescription;
import org.jgraph.event.GraphLayoutCacheEvent;
import org.jgraph.event.GraphLayoutCacheListener;
import org.jgraph.graph.DefaultGraphCell;

/**
 * 
 * @author	Martin Friedrichs
 * @since	0.8.1
 *
 */
public class PrescriptionCell extends DefaultGraphCell implements GraphLayoutCacheListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 *	Fields
	 */
	private OperaPrescription prescription = null;
	
	/*
	 *	Constructor
	 */
	/**
	 * 
	 */
	public PrescriptionCell() {
		super();
	}
	
	/**
	 * @param userObject
	 */
	public PrescriptionCell(Object userObject) {
		super (userObject);
		
		if (userObject instanceof OperaPrescription)
			setPrescription((OperaPrescription)userObject);
	}
	
	/*
	 *	Methods
	 */
	/* (non-Javadoc)
	 * @see javax.swing.tree.DefaultMutableTreeNode#toString()
	 */
	public String toString() {
		if (prescription != null)
			return prescription.toString();
		else
			return super.toString();
	}
	
	/**
	 * @param prescription
	 */
	public void setPrescription(OperaPrescription prescription) {
		this.prescription = prescription;		
	}

	/**
	 * @return
	 */
	public OperaPrescription getPrescription() {
		return prescription;
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

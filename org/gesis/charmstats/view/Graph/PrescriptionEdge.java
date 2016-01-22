package org.gesis.charmstats.view.Graph;

import org.gesis.charmstats.model.CharacteristicLink;
import org.gesis.charmstats.model.OperaPrescription;

/**
 * 
 * @author	Martin Friedrichs
 * @since	0.8.1
 *
 */
public class PrescriptionEdge extends GEdge {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final static String EMPTY = "";

	/*
	 *	Fields
	 */
	private OperaPrescription	prescription	= null;
	private String				transformation	= EMPTY;
	
	/*
	 *	Constructor
	 */
	/**
	 * 
	 */
	public PrescriptionEdge() {
		this (null);
	}
	
	/**
	 * @param userObject
	 */
	public PrescriptionEdge(Object userObject) {
		super ();
		
		if (userObject instanceof OperaPrescription) {
			this.setPrescription((OperaPrescription)userObject);
		}
	}
	
	/**
	 * @param link
	 */
	public PrescriptionEdge(CharacteristicLink link) {
		super();
		
		if (link.getCharacteristic() instanceof OperaPrescription) {
			this.setPrescription((OperaPrescription)userObject);
		}
	}
	
	/*
	 *	Methods
	 */
	/* (non-Javadoc)
	 * @see javax.swing.tree.DefaultMutableTreeNode#toString()
	 */
	public String toString() {
		if (prescription != null) {
			String stringValue = 
				prescription.toString();
			
			if ((transformation != null) &
					(!transformation.equals(EMPTY)))
				stringValue += "(" + transformation + ")";
			
			return stringValue;
		}
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

	/**
	 * @return
	 */
	public String getLabel() {
		return prescription.getLabel();
	}

	/**
	 * @param transformation
	 */
	public void setTransformation(String transformation) {
		this.transformation = transformation;
	}

	/**
	 * @return
	 */
	public String getTransformation() {
		return this.transformation;
	}

}

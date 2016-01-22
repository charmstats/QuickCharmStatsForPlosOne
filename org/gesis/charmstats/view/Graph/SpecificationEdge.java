package org.gesis.charmstats.view.Graph;

import org.gesis.charmstats.model.CharacteristicLink;
import org.gesis.charmstats.model.ConSpecification;

/**
 * 
 * @author	Martin Friedrichs
 * @since	0.8.1
 *
 */
public class SpecificationEdge extends GEdge {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final static String EMPTY = "";

	/*
	 *	Fields
	 */
	private ConSpecification	specification	= null;
	private String				transformation	= EMPTY;
	
	/*
	 *	Constructor
	 */
	/**
	 * 
	 */
	public SpecificationEdge() {
		this (null);
	}
	
	/**
	 * @param userObject
	 */
	public SpecificationEdge(Object userObject) {
		super ();
		
		if (userObject instanceof ConSpecification) {
			this.setSpecification((ConSpecification)userObject);
		}
	}
	
	/**
	 * @param link
	 */
	public SpecificationEdge(CharacteristicLink link) {
		super();
		
		if (link.getCharacteristic() instanceof ConSpecification) {
			this.setSpecification((ConSpecification)userObject);
		}
	}
	
	/*
	 *	Methods
	 */
	/* (non-Javadoc)
	 * @see javax.swing.tree.DefaultMutableTreeNode#toString()
	 */
	public String toString() {
		if (specification != null) {
			String stringValue = 
				specification.toString();
			
			if ((transformation != null) &
					(!transformation.equals(EMPTY)))
				stringValue += "(" + transformation + ")";
			
			return stringValue;
		}
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

	/**
	 * @return
	 */
	public String getLabel() {
		return specification.getLabel();
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

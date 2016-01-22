package org.gesis.charmstats.view.Graph;

import org.gesis.charmstats.model.AttributeLink;
import org.gesis.charmstats.model.AttributeMap;
import org.gesis.charmstats.model.Attributes;
import org.gesis.charmstats.model.ConDimension;
import org.gesis.charmstats.model.Measurement;
import org.gesis.charmstats.model.OperaIndicator;
import org.gesis.charmstats.model.Variable;

/**
 * 
 * @author	Martin Friedrichs
 * @since	0.8.1
 *
 */
public class AssignmentEdge extends GEdge {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final static String EMPTY = "";

	/*
	 *	Fields
	 */
	private Attributes	attribute		= null;
	private String		transformation	= EMPTY;
	
	/*
	 *	Constructor
	 */
	/**
	 * 
	 */
	public AssignmentEdge() {
		this (null);
	}
	
	/**
	 * @param userObject
	 */
	public AssignmentEdge(Object userObject) {
		super ();
		
		if (userObject instanceof Attributes) {
			this.setAttribute((Attributes)userObject);
		}
	}
	
	/**
	 * @param map
	 */
	public AssignmentEdge(AttributeMap map) {
		super();
		
		if ((map.getTargetAttribute() instanceof AttributeLink) &&
				(map.getTargetAttribute().getAttribute() instanceof Attributes)){
			this.setAttribute((Attributes)map.getTargetAttribute().getAttribute());
		}
	}
	
	/*
	 *	Methods
	 */
	/**
	 * @param attribute
	 */
	public void setAttribute(Attributes attribute) {
		this.attribute = attribute;
	}

	/**
	 * @return
	 */
	public Attributes getAttribute() {
		return attribute;
	}

	/**
	 * @return
	 */
	public String getLabel() {
		String label = "";
		
		if (attribute instanceof Measurement)
			label = ((Measurement)attribute).getName();		
		if (attribute instanceof ConDimension)
			label = ((ConDimension)attribute).getLabel();
		if (attribute instanceof OperaIndicator)
			label = ((OperaIndicator)attribute).getLabel();
		if (attribute instanceof Variable)
			label = ((Variable)attribute).getLabel();
		
		return label;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.tree.DefaultMutableTreeNode#toString()
	 */
	public String toString() {
		if (attribute != null) {
			String stringValue = 
				attribute.toString();
			
			if ((transformation != null) &
					(!transformation.equals(EMPTY)))
				stringValue += "(" + transformation + ")";
			
			return stringValue;
		}
		else
			return super.toString();
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



package org.gesis.charmstats.view.Graph;

import org.gesis.charmstats.model.CharacteristicLink;
import org.gesis.charmstats.model.Value;

/**
 * 
 * @author	Martin Friedrichs
 * @since	0.8.1
 *
 */
public class ValueEdge extends GEdge {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final static String EMPTY = "";

	/*
	 *	Fields
	 */
	private Value	value			= null;
	private String	transformation	= EMPTY;
	
	/*
	 *	Constructor
	 */
	/**
	 * 
	 */
	public ValueEdge() {
		this (null);
	}
	
	/**
	 * @param userObject
	 */
	public ValueEdge(Object userObject) {
		super ();
		
		if (userObject instanceof Value) {
			this.setValue((Value)userObject);
		}
	}
	
	/**
	 * @param link
	 */
	public ValueEdge(CharacteristicLink link) {
		super();
		
		if (link.getCharacteristic() instanceof Value) {
			this.setValue((Value)userObject);
		}
	}
	
	/*
	 *	Methods
	 */
	/* (non-Javadoc)
	 * @see javax.swing.tree.DefaultMutableTreeNode#toString()
	 */
	public String toString() {
		if (value != null) {
			String stringValue = 
				value.toString();
			
			if ((transformation != null) &
					(!transformation.equals(EMPTY)))
				stringValue += "(" + transformation + ")";
			
			return stringValue;
		}
		else
			return super.toString();
	}
	
	/**
	 * @param value
	 */
	public void setValue(Value value) {
		this.value = value;
	}

	/**
	 * @return
	 */
	public Value getValue() {
		return value;
	}

	/**
	 * @return
	 */
	public String getLabel() {
		return value.getLabel();
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


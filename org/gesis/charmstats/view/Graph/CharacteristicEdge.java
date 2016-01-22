package org.gesis.charmstats.view.Graph;

import org.gesis.charmstats.model.Category;
import org.gesis.charmstats.model.CharacteristicLink;
import org.gesis.charmstats.model.CharacteristicMap;
import org.gesis.charmstats.model.Characteristics;
import org.gesis.charmstats.model.ConSpecification;
import org.gesis.charmstats.model.OperaPrescription;
import org.gesis.charmstats.model.Value;

/**
 * 
 * @author	Martin Friedrichs
 * @since	0.8.1
 *
 */
public class CharacteristicEdge extends GEdge {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final static String EMPTY = "";

	/*
	 *	Fields
	 */
	private Characteristics	characteristic	= null;
	private String			transformation	= EMPTY;
	
	/*
	 *	Constructor
	 */
	/**
	 * 
	 */
	public CharacteristicEdge() {
		this (null);
	}
	
	/**
	 * @param userObject
	 */
	public CharacteristicEdge(Object userObject) {
		super ();
		
		if (userObject instanceof Characteristics) {
			this.setCharacteristic((Characteristics)userObject);
		}
	}
	
	/**
	 * @param map
	 */
	public CharacteristicEdge(CharacteristicMap map) {
		super();
		
		if ((map.getTargetCharacteristic() instanceof CharacteristicLink) &&
				(map.getTargetCharacteristic().getCharacteristic() instanceof Characteristics)){
			this.setCharacteristic((Characteristics)map.getTargetCharacteristic().getCharacteristic());
		}
	}
	
	/*
	 *	Methods
	 */
	/* (non-Javadoc)
	 * @see javax.swing.tree.DefaultMutableTreeNode#toString()
	 */
	public String toString() {
		if (characteristic != null) {
			String stringValue = 
				characteristic.toString();
			
			if ((transformation != null) &
					(!transformation.equals(EMPTY)))
				stringValue += "(" + transformation + ")";
			
			return stringValue;
		}
		else
			return super.toString();
	}
	
	/**
	 * @param characteristic
	 */
	public void setCharacteristic(Characteristics characteristic) {
		this.characteristic = characteristic;
	}

	/**
	 * @return
	 */
	public Characteristics getCharacteristic() {
		return characteristic;
	}

	/**
	 * @return
	 */
	public String getLabel() {
		String label = "";
		
		if (characteristic instanceof Category)
			label = ((Category)characteristic).getLabel();		
		if (characteristic instanceof ConSpecification)
			label = ((ConSpecification)characteristic).getLabel();
		if (characteristic instanceof OperaPrescription)
			label = ((OperaPrescription)characteristic).getLabel();	
		if (characteristic instanceof Value)
			label = ((Value)characteristic).getLabel();
		
		return label;
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

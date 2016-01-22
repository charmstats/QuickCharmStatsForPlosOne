package org.gesis.charmstats.view.Graph;

import org.gesis.charmstats.model.Category;
import org.gesis.charmstats.model.CharacteristicLink;

/**
 * 
 * @author	Martin Friedrichs
 * @since	0.8.1
 *
 */
public class CategoryEdge extends GEdge {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final static String EMPTY = "";

	/*
	 *	Fields
	 */
	private Category	category		= null;
	private String		transformation	= EMPTY;
	
	/*
	 *	Constructor
	 */
	/**
	 * 
	 */
	public CategoryEdge() {
		this (null);
	}
	
	/**
	 * @param userObject
	 */
	public CategoryEdge(Object userObject) {
		super ();
		
		if (userObject instanceof Category) {
			this.setCategory((Category)userObject);
		}
	}
	
	/**
	 * @param link
	 */
	public CategoryEdge(CharacteristicLink link) {
		super();
		
		if (link.getCharacteristic() instanceof Category) {
			this.setCategory((Category)userObject);
		}
	}
	
	/*
	 *	Methods
	 */
	/* (non-Javadoc)
	 * @see javax.swing.tree.DefaultMutableTreeNode#toString()
	 */
	public String toString() {
		if (category != null) {
			String stringValue = 
				category.toString();
			
			if ((transformation != null) &
					(!transformation.equals(EMPTY)))
				stringValue += "(" + transformation + ")";
			
			return stringValue;
		}
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

	/**
	 * @return
	 */
	public String getLabel() {
		return category.getLabel();
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


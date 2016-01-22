package org.gesis.charmstats.persistence;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public abstract class DBReference extends DBEntity {

	/*
	 *	Fields
	 */
	protected Object			referenced;
	protected RefRelationtype	refRelationtype;
	
	/*
	 *	Constructor
	 */
	
	/*
	 *	Methods
	 */
	/**
	 * @param referenced
	 */
	public void setReferenced(Object referenced) {
		this.referenced = referenced;
	}

	/**
	 * @return
	 */
	public Object getReferenced() {
		return referenced;
	}

	/**
	 * @return
	 */
	public int getReferencedEntityTypeID() {
		int entityTypeID = -1;
		
		if (getReferenced() instanceof DBEntity)
			entityTypeID = ((DBEntity)getReferenced()).getEntityType().getID();
		
		return entityTypeID;
	}
	
	/**
	 * @return
	 */
	public int getReferencedID() {
		int id = -1;
		
		if (getReferenced() instanceof DBEntity)
			id = ((DBEntity)getReferenced()).getEntityID();	
		
		return id;
	}
	
	/**
	 * @param refRelationtype
	 */
	public void setRefRelationtype(RefRelationtype refRelationtype) {
		this.refRelationtype = refRelationtype;
	}

	/**
	 * @return
	 */
	public RefRelationtype getRefRelationtype() {
		return refRelationtype;
	}
	
}

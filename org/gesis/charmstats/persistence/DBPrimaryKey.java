package org.gesis.charmstats.persistence;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public interface DBPrimaryKey {

	/*
	 *	Methods
	 */
	/**
	 * @param id
	 */
	public void 		setEntityID(int id);
	/**
	 * @return
	 */
	public int 			getEntityID();
	
	/**
	 * @return
	 */
	public EntityType	getEntityType();
	
}

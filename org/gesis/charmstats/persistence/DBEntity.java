package org.gesis.charmstats.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public abstract class DBEntity implements DBPrimaryKey {

	/*
	 *	Column names and other text constants
	 */
	public static final String cn_uuid			= "uuid";
	
	/*
	 *	Fields 
	 */
	protected 	String		sqlQuery;
	protected 	Statement	stmt;
	protected	PreparedStatement prepStmt;
	protected 	ResultSet	rsltst;
	
	protected	int			entity_id;
	protected	EntityType 	entity_type;
	
	protected	String		uuid;
	
	/*
	 *	Constructor
	 */
	/**
	 * 
	 */
	public DBEntity () {
		super();
		
		entity_id	= -1;
		entity_type	= EntityType.NONE;
				
		uuid = String.valueOf(UUID.randomUUID());
	}
	
	/*
	 *	Methods
	 */
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.persistence.DBPrimaryKey#setEntityID(int)
	 */
	public void setEntityID(int id) {
		entity_id = id;	
	}
	
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.persistence.DBPrimaryKey#getEntityID()
	 */
	public int getEntityID() {
		return entity_id;		
	}

	/* (non-Javadoc)
	 * @see org.gesis.charmstats.persistence.DBPrimaryKey#getEntityType()
	 */
	public EntityType getEntityType() {
		return entity_type;	
	}
	
	/* uuid */
	/**
	 * @param uuid
	 */
	public void setUUID(String uuid) {
		this.uuid = uuid;
	}
	
	/**
	 * @return
	 */
	public String getUUID() {
		return uuid;
	}
	
	/*
	 *	Load and Store to DB
	 */
	/**
	 * @param connection
	 * @return
	 */
	public boolean entityLoad(Connection connection) {
		boolean loadStatus = false;
		
		return loadStatus;
	}
	
	/**
	 * @param connection
	 * @return
	 */
	public boolean entityLoadByUUID(Connection connection) {
		boolean loadStatus = false;
		
		return loadStatus;
	}
	
	/**
	 * @param connection
	 * @return
	 */
	public boolean entityStore(Connection connection) {
		boolean storeStatus = false;
			
		return storeStatus;
	}
	
	/**
	 * @param connection
	 * @return
	 */
	public boolean entityRemove(Connection connection) {
		boolean removeStatus = false;
		
		return removeStatus;
	}
	
}

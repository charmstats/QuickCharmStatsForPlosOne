package org.gesis.charmstats.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.gesis.charmstats.persistence.DBEntity;
import org.gesis.charmstats.persistence.DBPrimaryKey;
import org.gesis.charmstats.persistence.DBReference;
import org.gesis.charmstats.persistence.EntityType;

/**
 *	This is an Entity-Class
 * 
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class ValueRef extends DBReference  {
	
	/* charmstats.value_refs (MySQL)
	`entity`		   smallint(5) unsigned NOT NULL default '0',
	`entry`			   int(11)				NOT NULL default '0',
	`value`		   	   int(11)	   			NOT NULL,
	`ref_relationship` int(11)	   					 default NULL,
	PRIMARY KEY  (`entity`,`entry`,`value`)
	*/
	
	/*
	 *	Column names and other text constants
	 */
	public static final String table_name		= "value_refs";
	public static final String primary_key		= "value";
	public static final String blank			= " ";
	public static final String comma			= ",";
	public static final String equals_sign		= "=";
	public static final String cn_tab_id		= "entity";
	public static final String cn_tab_ent_id	= "entry";	
	public static final String cn_val			= "value";
	public static final String cn_ref_rel_id	= "ref_relationship";
	
	/*
	 *	Fields
	 */
	
	/*	DBReferences:
	 *	protected Object referenced;
	 */
	private int		dsEntityTypeID;
	private int 	dsEntryID;
	
	private Value	value;
	private int		dsValueID;

	/*	DBReferences:
	 *	protected RefRelationtype refRelationtype; 
	 */
	
	/*
	 *	Constructor
	 */
	/**
	 * 
	 */
	public ValueRef () {
		super();
		
		entity_type = EntityType.VAL_REFS;
		
		setEntityID(-1);
	}
	
	/**
	 * @param referenced
	 */
	public ValueRef (Object referenced) {
		this();
		
		setReferenced(referenced);
	}
	
	/*
	 *	Methods
	 */
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
	 * @param dsValueID
	 */
	public void setValueID(int dsValueID) {
		this.dsValueID = dsValueID;
	}

	/**
	 * @return
	 */
	public int getValueID() {
		return dsValueID;
	}

	/**
	 * @param dsEntityTypeID
	 */
	public void setDsEntityTypeID(int dsEntityTypeID) {
		this.dsEntityTypeID = dsEntityTypeID;
	}

	/**
	 * @return
	 */
	public int getDsEntityTypeID() {
		return dsEntityTypeID;
	}

	/**
	 * @param dsEntryID
	 */
	public void setDsEntryID(int dsEntryID) {
		this.dsEntryID = dsEntryID;
	}

	/**
	 * @return
	 */
	public int getDsEntryID() {
		return dsEntryID;
	}
		
	/*
	 *	Load and Store to DB
	 */
	/**
	 * @param connection
	 * @return
	 */
	public ArrayList<Integer> loadReferences(Connection connection) {		
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		if (connection.equals(null)) {
			list = null;
			JOptionPane.showMessageDialog(
					null, "No Connection to DataBase!", "Error:", JOptionPane.ERROR_MESSAGE);
							
			/* DEMO ONLY */
			System.err.println("Error: No Connection to DataBase!");
			
			return list;
		}
		
		if ((((DBEntity)getReferenced()).getEntityType().getID() > 0) &&
				(((DBEntity)getReferenced()).getEntityID() > 0)) {
			try {
				stmt = connection.createStatement();
				
				sqlQuery = "SELECT" +blank+ primary_key +blank+
					"FROM" +blank+ table_name +blank+
				    "WHERE" +blank+ cn_tab_id +blank+ equals_sign +blank+ 
				    	getReferencedEntityTypeID() +blank+
				    "AND" +blank+ cn_tab_ent_id +blank+ equals_sign +blank+ 
				    	getReferencedID();
				
				rsltst = stmt.executeQuery(sqlQuery);
				
				while ( rsltst.next() ) {					
					list.add(rsltst.getInt(primary_key));
				}
				
				stmt.close();
		
		    } catch (SQLException e) {
		    	JOptionPane.showMessageDialog(
		    			null, e.getMessage(), "SQLException:",  JOptionPane.ERROR_MESSAGE);
		    		  	
		    	/* DEMO ONLY */
		    	System.err.println("SQLException: " + e.getMessage());
		    	e.printStackTrace();
		        
		    	list = null;
		    }
		}
		
		return list;
	}
	
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.persistence.DBEntity#entityLoad(java.sql.Connection)
	 */
	public boolean entityLoad(Connection connection) {

		boolean loadStatus = false;
		
		java.sql.Statement stmt;
		ResultSet rSet;
		
		if (connection.equals(null)) {
			loadStatus = false;
			JOptionPane.showMessageDialog(
					null, "No Connection to DataBase!", "Error:", JOptionPane.ERROR_MESSAGE);
							
			/* DEMO ONLY */
			System.err.println("Error: No Connection to DataBase!");
			
			return loadStatus;
		}
		
		try {
			if(!connection.isClosed()) {
				stmt = connection.createStatement();
					
				String sqlQuery = "SELECT" +blank+ cn_val +blank+
					"FROM" +blank+ table_name +blank+
					"WHERE" +blank+ cn_val +blank+ equals_sign +blank+
						getValue().getEntityID() + blank +
					"AND" +blank+ cn_tab_id +blank+ equals_sign +blank+ 
		    			getReferencedEntityTypeID() + blank +
		    		"AND" +blank+ cn_tab_ent_id +blank+ equals_sign +blank + 
		    			getReferencedID();
					
				rSet = stmt.executeQuery( sqlQuery );
					
				while (rSet.next()) {
					loadStatus = true;
				}
				
				stmt.close();
			} else {
				loadStatus = false;
				JOptionPane.showMessageDialog(
						null, "No open Connection!", "Error:", JOptionPane.ERROR_MESSAGE);
									
				/* DEMO ONLY */
				System.err.println("Error: No open Connection!");
			}
		} catch (SQLException e) {
			loadStatus = false;
			JOptionPane.showMessageDialog(
					null, e.getMessage(), "SQLException:",  JOptionPane.ERROR_MESSAGE);
				  	
			/* DEMO ONLY */
			System.err.println("SQLException: " + e.getMessage());
			e.printStackTrace();
		}
		
		return loadStatus;
	}
	
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.persistence.DBEntity#entityStore(java.sql.Connection)
	 */
	public boolean entityStore(Connection connection) {

		boolean storeStatus = true;
		int rows = -1;
		
		if (connection.equals(null)) {
			storeStatus = false;
			JOptionPane.showMessageDialog(
					null, "No Connection to DataBase!", "Error:", JOptionPane.ERROR_MESSAGE);
							
			/* DEMO ONLY */
			System.err.println("Error: No Connection to DataBase!");
			
			return storeStatus;
		}

		if ((this.value.getEntityID() > 0) && 
			(((DBPrimaryKey) this.getReferenced()).getEntityID() > 0)) {
			
			if (!this.entityLoad(connection)) {
				/*
				 *	Insert
				 */
				try {
					connection.setAutoCommit(false);
					
					if (storeStatus) {
						/*
						 *	Use Insert-Statement
						 */		
						sqlQuery = "INSERT INTO value_refs (entity, entry, value, ref_relationship";
						sqlQuery += ") VALUES ("
							+ "'"	+ this.getReferencedEntityTypeID()	+ "',"
							+ "'"	+ this.getReferencedID()			+ "',"
							+ "'"	+ this.getValue().getEntityID()		+ "',"
							+ "'"	+ this.getRefRelationtype().getID()	+ "'";
						sqlQuery += ")";
						
						stmt = connection.createStatement();
						rows = stmt.executeUpdate(sqlQuery);
						
						if (rows < 1)
							storeStatus = false;
						else {
							/* DoNothing */
						}
					
						stmt.close();
					}
					
				} catch (SQLException e) {
			    	storeStatus = false;
			    	JOptionPane.showMessageDialog(
			    			null, e.getMessage(), "SQLException:",  JOptionPane.ERROR_MESSAGE);
			    		  	
			    	/* DEMO ONLY */
			    	System.err.println("SQLException: " + e.getMessage());
			    	e.printStackTrace();
				}
				
				storeStatus = true;
			}
		}
	    
		if (!storeStatus) {
	    	try {
				connection.rollback();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(
						null, e.getMessage(), "SQLException:",  JOptionPane.ERROR_MESSAGE);
					  	
				/* DEMO ONLY */
				System.err.println("SQLException: " + e.getMessage());
				e.printStackTrace();
			}
		}

		return storeStatus;
	}
	
    /* (non-Javadoc)
     * @see org.gesis.charmstats.persistence.DBEntity#entityRemove(java.sql.Connection)
     */
    public boolean entityRemove(Connection connection) {
    	
    	boolean removeStatus = true;
    	int rows = -1;
    	
		if (connection.equals(null)) {
			removeStatus = false;
			JOptionPane.showMessageDialog(
					null, "No Connection to DataBase!", "Error:", JOptionPane.ERROR_MESSAGE);
							
			/* DEMO ONLY */
			System.err.println("Error: No Connection to DataBase!");
			
			return removeStatus;
		}
				
		try {
			sqlQuery = "DELETE" +blank+
				"FROM" +blank+ table_name +blank+
				"WHERE" +blank+ cn_val +blank+ equals_sign +blank+ 
					getValue().getEntityID() +blank+
				"AND" +blank+ cn_tab_id +blank+ equals_sign +blank+ 
					((DBEntity)this.getReferenced()).getEntityType().getID() +blank+
				"AND" +blank+ cn_tab_ent_id +blank+ equals_sign +blank+ 
					((DBEntity)this.getReferenced()).getEntityID();
			
			stmt = connection.createStatement();
			rows = stmt.executeUpdate(sqlQuery);
			
			if (rows < 1)				
				removeStatus = false;
			
			stmt.close();
		} catch (SQLException e) {
	    	removeStatus = false;
	    	JOptionPane.showMessageDialog(
	    			null, e.getMessage(), "SQLException:",  JOptionPane.ERROR_MESSAGE);
	    		  	
	    	/* DEMO ONLY */
	    	System.err.println("SQLException: " + e.getMessage());
	    	e.printStackTrace();
		}
		
    	return removeStatus;
    }
    
}

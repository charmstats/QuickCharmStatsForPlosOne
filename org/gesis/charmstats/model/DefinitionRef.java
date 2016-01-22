package org.gesis.charmstats.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.gesis.charmstats.persistence.DBPrimaryKey;
import org.gesis.charmstats.persistence.DBReference;
import org.gesis.charmstats.persistence.EntityType;

/**
 *	This is an Entity-Class 
 * 
 *	@author Martin Friedrichs
 *	@since	0.5
 *
 */
public class DefinitionRef extends DBReference {

	/*
	 *	Column names and other text constants
	 */
	public static final String table_name = 	"definition_refs";
	public static final String primary_key = 	"definition";
	public static final String blank = 			" ";
	public static final String comma = 			",";
	public static final String equals_sign = 	"=";
	public static final String cn_tab_id = 		"entity";
	public static final String cn_tab_ent_id = 	"entry";	
	public static final String cn_def =			"definition";
	public static final String cn_ref_rel_id = 	"ref_relationship";
	
	/*
	 *	Fields
	 */
	
	/*	DBReferences:
	 *	protected Object referenced; // `entity`, `entry` (id)
	 */
	private int 		dsEntityID;
	private int 		dsEntryID;
	
	private Definition	definition;
	private int 		dsDefinitionID;
	
	/*	DBReferences:
	 *	protected RefRelationtype refRelationtype; // `ref_relationship`
	 */
	
	/**
	 *	Constructor 
	 */
	public DefinitionRef () {
		super();	
		entity_type = EntityType.DEFINITION_REF;
		
		setEntityID(-1);
	}
	
	/**
	 * @param referenced
	 */
	public DefinitionRef (Object referenced) {
		this();
		
		setReferenced(referenced);
	}
	
	/*
	 *	Methods
	 */
	/**
	 * @param definition
	 */
	public void setDefinition(Definition definition) {
		this.definition = definition;
	}

	/**
	 * @return
	 */
	public Definition getDefinition() {
		return definition;
	}
	
	/**
	 * @param dsDefinitionID
	 */
	public void setDefinitionID(int dsDefinitionID) {
		this.dsDefinitionID = dsDefinitionID;
	}

	/**
	 * @return
	 */
	public int getDefinitionID() {
		return dsDefinitionID;
	}

	/**
	 * @param dsEntityID
	 */
	public void setDsEntityID(int dsEntityID) {
		this.dsEntityID = dsEntityID;
	}

	/**
	 * @return
	 */
	public int getDsEntityID() {
		return dsEntityID;
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
		
		if ((getReferencedEntityTypeID() > 0) &&
			(getReferencedID() > 0)) {
			try {
				stmt = connection.createStatement();
				
				sqlQuery = "SELECT" + blank + primary_key + blank +
					"FROM" + blank + table_name + blank +
				    "WHERE" + blank + cn_tab_id + blank + equals_sign + blank + 
				    	getReferencedEntityTypeID() + blank +
				    "AND" + blank + cn_tab_ent_id + blank + equals_sign + blank + 
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
					
				String sqlQuery = "SELECT definition " +
					"FROM definition_refs " +
					"WHERE definition=\"" + this.definition.getEntityID() + "\"" +
					"AND entity=\"" + this.getReferencedEntityTypeID()+ "\"" +
					"AND entry=\"" + this.getReferencedID() + "\"";
					
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

		if ((this.definition.getEntityID() > 0) && 
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
						sqlQuery = "INSERT INTO definition_refs (entity, entry, definition, ref_relationship";
						sqlQuery += ") VALUES ("
							+ "'"	+ this.getReferencedEntityTypeID()	+ "',"
							+ "'"	+ this.getReferencedID()	+ "',"
							+ "'"	+ this.getDefinition().getEntityID()	+ "',"
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
			    	
			    	/* DEMO ONLY */
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
	
    /**
     * @param connection
     * @return
     */
    public boolean entityRemoveSingle(Connection connection) {
    	
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
			sqlQuery = "DELETE from " + table_name +" "+
				"WHERE " + DefinitionRef.cn_def +"="+ this.getDefinition().getEntityID() +" "+
				"AND " + DefinitionRef.cn_tab_id +"="+ getReferencedEntityTypeID() +" "+
				"AND " + DefinitionRef.cn_tab_ent_id +"="+ getReferencedID();
			
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

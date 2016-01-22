package org.gesis.charmstats.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.gesis.charmstats.persistence.DBEntity;
import org.gesis.charmstats.persistence.DBReference;
import org.gesis.charmstats.persistence.EntityType;
import org.gesis.charmstats.persistence.RefRelationtype;

/**
 *	This is an Entity-Class
 * 
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class LiteratureRef extends DBReference {
	
	/* charmstats.literature_refs (MySQL)
		`entity`			smallint(5) unsigned NOT NULL default '0',
		`entry`				int(11)				 NOT NULL default '0',
		`literature`		int(11)	   			 NOT NULL,
		`ref_relationship`	int(11)	   					  default NULL,
		PRIMARY KEY  (`entity`,`entry`,`literature`)
	*/
	
	/*
	 *	Column names and other text constants
	 */
	public static final String table_name = 	"literature_refs";
	public static final String primary_key = 	"literature";
	public static final String blank = 			" ";
	public static final String comma = 			",";
	public static final String equals_sign = 	"=";
	public static final String equals =		 	"=";
	public static final String cn_tab_id = 		"entity";
	public static final String cn_tab_ent_id = 	"entry";	
	public static final String cn_lit =			"literature";
	public static final String cn_ref_rel_id = 	"ref_relationship";
	
	/*
	 *	Fields
	 */
	
	/*	DBReferences:
	 *	protected Object referenced; 
	 */
	private int 		dsEntityTypeID;
	private int 		dsEntryID;
	
	private Literature	literature;
	private int 		dsLiteratureID;
	
	/*	DBReferences:
	 *	protected RefRelationtype refRelationtype; 
	 */
	
	/*
	 *	Constructor
	 */
	/**
	 * 
	 */
	public LiteratureRef() {
		super();
		
		entity_type = EntityType.LITERATURE_REF;
		
		setEntityID(-1);
	}
	
	/**
	 * @param referenced
	 */
	public LiteratureRef(Object referenced) {
		this();
		
		setReferenced(referenced);
	}
	
	/*
	 *	Methods
	 */
	/* Literature */
	/**
	 * @param literature
	 */
	public void setLiterature(Literature literature) {
		this.literature = literature;
	}

	/**
	 * @return
	 */
	public Literature getLiterature() {
		return literature;
	}
	
	/**
	 * @param dsLiteratureID
	 */
	public void setLiteratureID(int dsLiteratureID) {
		this.dsLiteratureID = dsLiteratureID;
	}

	/**
	 * @return
	 */
	public int getLiteratureID() {
		return dsLiteratureID;
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
	
	/*RefRelationType, ref_relationtype_id:*/
	/**
	 * @param refRelationType
	 */
	public void setRefRelationType(RefRelationtype refRelationType) {
		this.refRelationtype = refRelationType;
	}

	/**
	 * @return
	 */
	public RefRelationtype getRefRelationType() {
		return refRelationtype;
	}
	
	/**
	 * @param connection
	 * @return
	 */
	public ArrayList<Integer> loadReferences(Connection connection) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		if (connection.equals(null)) {
			list = null;
			JOptionPane.showMessageDialog(null, "Error! No Connection to DataBase.");
			
			return list;
		}
		
		if ((((DBEntity)getReferenced()).getEntityType().getID() > 0) &&
				(((DBEntity)getReferenced()).getEntityID() > 0)) {
			try {
				stmt = connection.createStatement();
				
				sqlQuery = "SELECT" + blank + cn_lit + blank +
					"FROM" + blank + table_name + blank +
				    "WHERE" + blank + cn_tab_id + blank + equals + blank + 
				    	((DBEntity)this.getReferenced()).getEntityType().getID() + blank +
				    "AND" + blank + cn_tab_ent_id + blank + equals + blank + 
				    	((DBEntity)this.getReferenced()).getEntityID();
				
				rsltst = stmt.executeQuery(sqlQuery);
				
				while (rsltst.next()) {					
					list.add(rsltst.getInt(cn_lit));
				}
				
				stmt.close();
		
		    } catch (SQLException e) {
		        System.err.println("SQLException: " + e.getMessage());
		        
		    	list = null;
		    }
		}
		
		return list;
	}
    	
    /**
     * @param connection
     * @return
     */
    public boolean entitiesRemove(Connection connection) {
    	
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
			
		if (this.getReferenced() != null) {
			try {
				sqlQuery = "DELETE FROM" + blank + table_name + blank +
			    	"WHERE" + blank + cn_tab_id + blank + equals + blank + ((DBEntity)this.getReferenced()).getEntityType().getID() + blank +
			    	"AND" + blank + cn_tab_ent_id + blank + equals + blank + ((DBEntity)this.getReferenced()).getEntityID();
				
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
		}
		
    	return removeStatus;
    }
	
	/*
	 *	Load and Store to DB
	 */
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
				
				String sqlQuery = "SELECT literature " +
						"FROM literature_refs " +
						"WHERE literature=\"" + getLiterature().getEntityID() + "\"" +
						"AND entity=\"" + ((DBEntity)this.getReferenced()).getEntityType().getID() + "\"" +
						"AND entry=\"" + ((DBEntity)this.getReferenced()).getEntityID() + "\"";
					
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

		if (!entityLoad(connection)) {
			try {
				connection.setAutoCommit(false);
										
				if (storeStatus) {
					/*
					 *	Use Insert-Statement
					 */		
					sqlQuery = "INSERT INTO literature_refs "
						+ "(entity, entry, " 
						+ "literature, ref_relationship) "
						+ "VALUES "
						+ "('"	+ ((DBEntity)this.getReferenced()).getEntityType().getID()	+ "',"
						+ "'"	+ ((DBEntity)this.getReferenced()).getEntityID()		+ "',"  
						+ "'"	+ this.getLiterature().getEntityID()					+ "'," 
						+ "'"	+ this.getRefRelationtype().getID()				+ "')";
					
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
}

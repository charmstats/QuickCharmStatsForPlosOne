package org.gesis.charmstats.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.gesis.charmstats.persistence.DBPrimaryKey;
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
public class CategoryRef extends DBReference {
	
	/* charmstats.category_refs (MySQL)
		`entity`		   smallint(5) unsigned NOT NULL default '0',
		`entry`			   int(11)				NOT NULL default '0',
		`category`		   int(11)	   			NOT NULL,
		`ref_relationship` int(11)	   					 default NULL,
		PRIMARY KEY  (`entity`,`entry`,`category`)
	*/
	
	/*
	 *	Column names and other text constants
	 */
	public static final String table_name = 	"category_refs";
	public static final String primary_key = 	"category";
	public static final String blank = 			" ";
	public static final String comma = 			",";
	public static final String equals_sign = 	"=";
	public static final String cn_tab_id = 		"entity";
	public static final String cn_tab_ent_id = 	"entry";	
	public static final String cn_cat =			"category";
	public static final String cn_ref_rel_id = 	"ref_relationship";
	
	/*
	 *	Fields
	 */
	/*	DBReferences:
	 *	protected Object referenced; // `table_id`, `table_entry_id` (id)
	 */
	private int 	dsEntityTypeID;
	private int 	dsEntryID;
	
	private Category category;
	private int 	dsCategoryID;

	/*	DBReferences:
	 *	protected RefRelationtype refRelationtype; // `ref_relationtype_id`
	 */
	
	/**
	 *	Constructor 
	 */
	public CategoryRef () {
		super();
		
		entity_type = EntityType.CATEGORY_REFS;
		
		setEntityID(-1);
	}
	
	/**
	 * @param referenced
	 */
	public CategoryRef (Object referenced) {
		this();
		
		setReferenced(referenced);
	}
	
	/*
	 *	Methods
	 */

	
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
	 * @param dsCategoryID
	 */
	public void setCategoryID(int dsCategoryID) {
		this.dsCategoryID = dsCategoryID;
	}

	/**
	 * @return
	 */
	public int getCategoryID() {
		return dsCategoryID;
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
				    	this.getReferencedEntityTypeID() + blank +
				    "AND" + blank + cn_tab_ent_id + blank + equals_sign + blank + 
				    	this.getReferencedID();
				
				rsltst = stmt.executeQuery(sqlQuery);
				
				while (rsltst.next()) {					
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
					
				String sqlQuery = "SELECT category, entity, entry, ref_relationship " +
					"FROM category_refs " +
					"WHERE category=\"" + this.getEntityID() + "\"";
				
				rSet = stmt.executeQuery( sqlQuery );
					
				while (rSet.next()) {
					this.setCategoryID(rSet.getInt(cn_cat));
					setDsEntityTypeID(rSet.getInt(cn_tab_id));
					setDsEntryID(rSet.getInt(cn_tab_ent_id));
					setRefRelationType(RefRelationtype.getItem(rSet.getInt(cn_ref_rel_id)));
					
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

		
		if ((this.category.getEntityID() > 0) && 
				(((DBPrimaryKey) this.getReferenced()).getEntityID() > 0)) {

			if (this.getEntityID() == 0)
				this.setEntityID(this.getCategory().getEntityID());
			
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
						sqlQuery = "INSERT INTO category_refs (entity, entry, category, ref_relationship";
						sqlQuery += ") VALUES ("
							+ "'"	+ this.getReferencedEntityTypeID()	+ "',"
							+ "'"	+ this.getReferencedID()	+ "',"
							+ "'"	+ this.getCategory().getEntityID()	+ "',"
							+ "'"	+ this.getRefRelationtype().getID()	+ "'";
						sqlQuery += ")";
						
						stmt = connection.createStatement();
						rows = stmt.executeUpdate(sqlQuery);
						
						if (rows < 1)
							storeStatus = false;
					
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
		
		if (entity_id > 0) {
			try {
				sqlQuery = "DELETE from category_refs " +
					"WHERE category = " + this.getCategoryID();
				
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
			    	"WHERE" + blank + cn_tab_id + blank + equals_sign + blank + getReferencedEntityTypeID() + blank +
			    	"AND" + blank + cn_tab_ent_id + blank + equals_sign + blank + getReferencedID();
				
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
}

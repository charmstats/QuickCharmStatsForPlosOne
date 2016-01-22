package org.gesis.charmstats.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import org.gesis.charmstats.persistence.DBEntity;
import org.gesis.charmstats.persistence.DBField;

/**
 *	This is an Entity-Class
 * 
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class WorkStepInstance extends DBEntity {

	/* charmstats.instances (MySQL)
	`id`	   		  int(11)	   NOT NULL auto_increment,
	`label`			  varchar(255) 			default NULL,
	`type`			  int(11) 				default NULL,
	`published_since` datetime 				default NULL,
	`obsolete_since`  datetime 				default NULL,
	`description`	  varchar(255) 			default NULL,
	PRIMARY KEY  (`id`)
	*/
	
	public static final String TABLE		= "instances";
	public static final String PRIMARY_KEY	= "id";
	
	/*
	 *	Fields
	 */
	private String 			label;
	private InstanceType	type;
	private String			description;
	
	/*
	 *	Constructor
	 */
	/**
	 * 
	 */
	public WorkStepInstance() {
		super();
		
		setEntityID(-1);
		setLabel(new String());
	}
	
	/**
	 * @param id
	 */
	public WorkStepInstance(int id) {
		super();
		
		setEntityID(id);
	}
	
	/**
	 * @param type
	 */
	public WorkStepInstance(InstanceType type) {
		this();
		
		setType(type);
	}
	
	/*
	 *	Methods
	 */
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return label;
	}
	
	/* Label */
	/**
	 * @param label
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	
	/**
	 * @return
	 */
	public String getLabel() {
		return label;
	}
	
	/* Type */
	/**
	 * @param type
	 */
	public void setType(InstanceType type) {
		this.type = type;
	}
	
	/**
	 * @return
	 */
	public InstanceType getType() {
		return type;
	}

	/* Description */
	/**
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
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
					
				String sqlQuery = "SELECT "+
					"label, type, description "+
					"FROM "+ TABLE +" "+
					"WHERE "+ PRIMARY_KEY +" = "+ this.getEntityID();
				
				rSet = stmt.executeQuery( sqlQuery );
					
				while (rSet.next()) {
					label		= rSet.getString("label");					
					type		= InstanceType.getItem(rSet.getInt("type"));			
					description	= rSet.getString("description");
										
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

		
		if (entity_id > 0) {
			/*
			 *	Update
			 */
			try {
				connection.setAutoCommit(false);
				
				sqlQuery = "UPDATE "+ TABLE +" SET "+
					"label = ?, "+
					"type = ?, "+
					"description = ? "+
					"WHERE id = ?";
				
				prepStmt = connection.prepareStatement(sqlQuery);
				prepStmt.setString(1, DBField.INS_LAB.truncate(this.getLabel()));
				prepStmt.setInt(2, this.getType().getID());
				prepStmt.setString(3, DBField.INS_DES.truncate(this.getDescription()));
				prepStmt.setInt(4, this.getEntityID());
				
				rows = prepStmt.executeUpdate();
				
				if (rows < 1)				
					storeStatus = false;
				
				prepStmt.close();
		    } catch (SQLException e) {
		    	storeStatus = false;
		    	JOptionPane.showMessageDialog(
		    		null, e.getMessage(), "SQLException:",  JOptionPane.ERROR_MESSAGE);
		    	  	
		    	/* DEMO ONLY */
		    	System.err.println("SQLException: " + e.getMessage());
		    	e.printStackTrace();
		    }
		} else {
			/*
			 *	Insert
			 */
			try {
				connection.setAutoCommit(false);
								
				if (storeStatus) {
					/*
					 *	Use Insert-Statement
					 */		
					sqlQuery = "INSERT INTO "+ TABLE +" (label, type, description"+
						") VALUES ("+
						"?, ?, ?"+
						")";
					
					prepStmt = connection.prepareStatement(sqlQuery);
					prepStmt.setString(1, DBField.INS_LAB.truncate(this.getLabel()));
					prepStmt.setInt(2, this.getType().getID());
					prepStmt.setString(3, DBField.INS_DES.truncate(this.getDescription()));
					
					rows = prepStmt.executeUpdate();
					
					if (rows < 1)
						storeStatus = false;
					else {					
						rsltst = prepStmt.getGeneratedKeys();
		
					    if (rsltst.next()) {
					        this.setEntityID(rsltst.getInt(1));
					    } else {
					    	storeStatus = false;
					    }
					    rsltst.close();
					}
				
					prepStmt.close();
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
				sqlQuery = "DELETE from "+ TABLE +" "+
					"WHERE "+ PRIMARY_KEY +" = "+ this.getEntityID();
				
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

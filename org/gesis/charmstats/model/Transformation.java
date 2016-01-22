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
public class Transformation extends DBEntity {

	/* charmstats.transformations (MySQL)
		`id`   int(11)		 NOT NULL auto_increment,
		`code` varchar(2048) 		  default NULL,
		PRIMARY KEY  (`id`)
	*/
	
	public static final String TABLE		= "transformations";
	public static final String PRIMARY_KEY	= "id";
	
	/*
	 *	Fields
	 */
	private String code;
	
	/*
	 *	Constructor
	 */
	/**
	 * 
	 */
	public Transformation() {
		super();		
	}
	
	/*
	 *	Methods
	 */
	/**
	 * @return
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
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
					"code "+
					"FROM "+ TABLE + " " +
					"WHERE "+ PRIMARY_KEY +" = "+ this.getEntityID();
					
				rSet = stmt.executeQuery( sqlQuery );
					
				while (rSet.next()) {
					code		= rSet.getString("code");					
										
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
					"code = '"+ DBField.TRA_COD.truncate(getCode()) +"' "+
				    "WHERE "+ PRIMARY_KEY +" = "+ this.getEntityID();
					
				stmt = connection.createStatement();
				rows = stmt.executeUpdate(sqlQuery);
				
				if (rows < 1)				
					storeStatus = false;
				
				stmt.close();
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
					sqlQuery = "INSERT INTO "+ TABLE +" (code"+
						") VALUES ("+
						"'"+ DBField.TRA_COD.truncate(getCode()) +"'"+
						")";
					
					stmt = connection.createStatement();
					rows = stmt.executeUpdate(sqlQuery);
					
					if (rows < 1)
						storeStatus = false;
					else {					
					    rsltst = stmt.getGeneratedKeys();
		
					    if (rsltst.next()) {
					        this.setEntityID(rsltst.getInt(1));
					    } else {
					    	storeStatus = false;
					    }
					    rsltst.close();
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

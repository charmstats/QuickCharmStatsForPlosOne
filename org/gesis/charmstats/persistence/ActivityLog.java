package org.gesis.charmstats.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import org.gesis.charmstats.model.User;

/**
 * 
 *	This is an Entity-Class
 * 
 *	@author Martin Friedrichs
 *	@since	0.9.3
 *
 */
public class ActivityLog extends DBEntity {

	/*
	 *	Column names and other text constants
	 */
	
	/*
	 *	Fields
	 */
	private int		actor;			/* Actor  :	User */
	private String	action;			/* Action :	ActionCommandText */	
	private String	action_details;	/* Details:	misc. Parameters */	
	
	/*
	 *	Constructor
	 */
	
	/*
	 *	Methods
	 */
	/* Actor */
	/**
	 * @return the ID of the user invoking the action
	 */
	public int getWho() {
		return actor;
	}
	/**
	 * @param usr the user who is invoking the action
	 */
	public void setWho(User usr) {
		this.actor = usr.getEntityID();
	}
	
	/* Action */
	/**
	 * @return the ActionCommandText of the invoked action
	 */
	public String getWhat() {
		return action;
	}
	/**
	 * @param what the ActionCommandText of the invoked action
	 */
	public void setWhat(String what) {
		this.action = what;
	}
	
	/* Details */
	/**
	 * @return
	 */
	public String getDetails() {
		return action_details;
	}
	/**
	 * @param with 
	 */
	public void setDetails(String with) {
		this.action_details = with;
	}

	/*
	 *	Load and Store to DB
	 */
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
								
				if (storeStatus) {
					sqlQuery = "UPDATE activity_logs SET "
						+ "actor = ?"
						+ ", action = ?"
						+ ", action_details = ?";
					sqlQuery += " WHERE id = ?";
					
					prepStmt = connection.prepareStatement(sqlQuery);
					prepStmt.setInt(1, getWho());
					prepStmt.setString(2, DBField.ACT_LOG_ACTION.truncate(getWhat()));
					prepStmt.setString(3, DBField.ACT_LOG_DETAILS.truncate(getDetails()));
					prepStmt.setInt(4, this.getEntityID());
					
					rows = prepStmt.executeUpdate();
					
					if (rows < 1)				
						storeStatus = false;
					
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
					sqlQuery = "INSERT INTO activity_logs (actor, action, action_details";
						sqlQuery += ") VALUES (";
						sqlQuery += "?, ?, ?";
						sqlQuery += ")";
						
					prepStmt = connection.prepareStatement(sqlQuery);
					prepStmt.setInt(1, getWho());
					prepStmt.setString(2, DBField.ACT_LOG_ACTION.truncate(getWhat()));
					prepStmt.setString(3, DBField.ACT_LOG_DETAILS.truncate(getDetails()));
					
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
				
				if (storeStatus) {
					try {
						connection.commit();
					} catch (SQLException e) {
						e.printStackTrace();
					}
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

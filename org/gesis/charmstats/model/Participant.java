package org.gesis.charmstats.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.gesis.charmstats.persistence.DBEntity;
import org.gesis.charmstats.persistence.EntityType;

/**
 *	This is an Entity-Class
 * 
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class Participant extends DBEntity {
	
	/* charmstats.participants (MySQL)
	  `id`		int(11) NOT NULL auto_increment,
	  `project`	int(11)			 default NULL,
	  `user`	int(11)			 default NULL,
	  `role`	int(11)			 default NULL,
	  PRIMARY KEY  (`id`)
	*/
	
	/*
	 *	Fields
	 */
	private Project 		project;
	private User			user;	
	private ParticipantRole role;
	
	/*
	 *	Constructor
	 */
	/**
	 * 
	 */
	public Participant () {
		super();
		
		entity_type = EntityType.PARTICIPANTS;		
	}
	
	/*
	 *	Methods
	 */
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		if ((user != null) &&
				(project != null))
			return user.getName() +"("+ project.getName() +")";
			
		/*In case of fault:*/
		return super.toString();
	}
	
	/**
	 * @param project
	 */
	public void setProject(Project project) {
		this.project = project;
	}

	/**
	 * @return
	 */
	public int getProjectID() {
		return project.getEntityID();
	}
	
	/**
	 * @param user
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	/**
	 * @return
	 */
	public int getUserID() {
		return user.getEntityID();
	}
	
	/* Used only for ProjectOwner Handling: */
	/**
	 * @return
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param role
	 */
	public void setRole(ParticipantRole role) {
		this.role = role;
	}

	/**
	 * @return
	 */
	public int getRoleID() {
		return role.getID();
	}
	
	/**
	 * @return
	 */
	public ParticipantRole getRole() {
		return role;
	}

	/*
	 *	Load and Store to DB
	 */
	/**
	 * @param connection
	 * @return
	 */
	public ArrayList<Integer> listParticipants(Connection connection) {
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		if (connection.equals(null)) {
			list = null;
			JOptionPane.showMessageDialog(
					null, "No Connection to DataBase!", "Error:", JOptionPane.ERROR_MESSAGE);
							
			/* DEMO ONLY */
			System.err.println("Error: No Connection to DataBase!");
			
			return list;
		}
		
		if (getProjectID() > -1) { 
			try {
				stmt = connection.createStatement();
				
				sqlQuery = "SELECT id FROM participants WHERE project = "+ getProjectID();
				
				rsltst = stmt.executeQuery(sqlQuery);
				
				while ( rsltst.next() ) {					
					list.add(rsltst.getInt("id"));
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
					
				String sqlQuery = "SELECT p.user, p.role " +
					"FROM participants p " +
					"WHERE p.id=\"" + getEntityID() + "\"";	
				
				rSet = stmt.executeQuery( sqlQuery );
					
				while (rSet.next()) {

					int user_id = rSet.getInt("user");
					if (!rSet.wasNull()) {
						User user = new User();
						user.setEntityID(user_id);
						user.entityLoad(connection);
						setUser(user);
					}
					int roleType = rSet.getInt("role");										
					if (!rSet.wasNull()) {
						setRole(ParticipantRole.getItem(roleType));
					}
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

		
		if (getEntityID() > 0) {
			/*
			 *	Update
			 */
			try {
				connection.setAutoCommit(false);
				
				if (storeStatus) {
					sqlQuery = "UPDATE participants SET "
						+ "role = '" 	+ this.getRole().getID()						+ "' "
					    + "WHERE id = " + this.getEntityID();
						
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
					sqlQuery = "INSERT INTO participants (project, user, role) VALUES ("
						+ "'"	+ this.getProjectID()					+ "',"
						+ "'"	+ this.getUserID()						+ "',"
						+ "'"	+ this.getRoleID() + "')";
					
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
			
			storeStatus = true;
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

package org.gesis.charmstats.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.gesis.charmstats.Basket;
import org.gesis.charmstats.persistence.DBEntity;
import org.gesis.charmstats.persistence.DBField;
import org.gesis.charmstats.persistence.EntityType;

/**
 *	This is an Entity-Class
 * 
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class User extends DBEntity {
	
	/* charmstats.users (MySQL)
	`id`	   int(11)	   NOT NULL auto_increment,
	`person`   int(11)				default NULL,
	`name`	   varchar(64) 			default NULL,
 	`password` varchar(64) 			default NULL,
	`role`	   int(11) 				default NULL,
	PRIMARY KEY  (`id`)
	*/
	
//	static final EntityType entity_type = EntityType.USERS;
	static final User 		anonymous	= new User(0, "anonymous");
			
	/*
	 *	Fields 
	 */
	private Person	person;
	private String	name;		
	private String	password;
	private UserRole userRole;
	
	private ArrayList<Project> finProjects = new ArrayList<Project>();
	private ArrayList<Project> unfinProjects = new ArrayList<Project>();
	
	private Basket basket = new Basket();

	
	/*
	 *	Constructor
	 */
	/**
	 * 
	 */
	public User () {
		super();
		
		entity_type = EntityType.USERS;

		basket.setOwner(this); 
	}
	
	/**
	 * @param id
	 * @param name
	 */
	public User (int id, String name) {
		this ();
		
		setEntityID(id);
		setName(name);
	}
	
	/*
	 *	Methods
	 */
	/* EntityType */
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.persistence.DBEntity#getEntityType()
	 */
	public EntityType getEntityType() {
		return entity_type;	
	}

	/* Name */
	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/* Password */
	/**
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/* Utility */
	/**
	 * @return
	 */
	static public User getAnonymousUser() {
		return User.anonymous;
	}

	/* Basket */
	/**
	 * @param basket
	 */
	public void setBasket(Basket basket) {
		this.basket = basket;
	}
	
	/**
	 * @return
	 */
	public Basket getBasket() {
		return basket;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		if (name != null)
			return name;
			
		/*In case of fault:*/
		return super.toString();
	}
	
	/* List of Users */
	/**
	 * @param con
	 * @return
	 */
	public static ArrayList<User> getAllUsers(Connection con) {
		ArrayList<User> users = new ArrayList<User>();
		
		java.sql.Statement stmt;
		ResultSet rSet;
		
		if (con != null) {
			try {
				if(!con.isClosed()) {
					stmt = con.createStatement();
					
					String sqlQuery = "select usr.id, usr.name, usr.person "
						+ "from users usr";
					
					rSet = stmt.executeQuery( sqlQuery );
					
					while (rSet.next()) {
						User user = new User(rSet.getInt("id"), rSet.getString("name"));
						user.entityLoad(con);
						
						users.add(user);
					}
					
					stmt.close();
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(
						null, e.getMessage(), "SQLException:",  JOptionPane.ERROR_MESSAGE);
					  	
				/* DEMO ONLY */
				System.err.println("SQLException: " + e.getMessage());
				e.printStackTrace();
			}
		}
		
		return users;
	}
	
	/**
	 * @param con
	 * @param role
	 * @return
	 */
	public static ArrayList<User> getAllUsersWithRole(Connection con, UserRole role) {
		ArrayList<User> users = new ArrayList<User>();
		
		java.sql.Statement stmt;
		ResultSet rSet;
		
		if (con != null) {
			try {
				if(!con.isClosed()) {
					stmt = con.createStatement();
					
					String sqlQuery = "select usr.id, usr.name "
						+ "from users usr "
						+ "where usr.role = " + role.getID();
					
					rSet = stmt.executeQuery( sqlQuery );
					
					while (rSet.next()) {
						User user = new User(rSet.getInt("id"), rSet.getString("name"));
						
						users.add(user);
					}
					
					stmt.close();
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(
						null, e.getMessage(), "SQLException:",  JOptionPane.ERROR_MESSAGE);
					  	
				/* DEMO ONLY */
				System.err.println("SQLException: " + e.getMessage());
				e.printStackTrace();
			}
		}
		
		return users;
	}
	
	/* Publ. Projects */
	/**
	 * former setPublProjects
	 * 
	 * @param projects
	 */
	public void setFinProjects(ArrayList<Project> projects) {
		finProjects = projects;
	}
	
	/**
	 * former getPublProjects
	 * 
	 * @return
	 */
	public ArrayList<Project> getFinProjects() {
		return finProjects;
	}
	
	/* Unpubl. Projects. */
	/**
	 * former setUnpublProjects
	 * 
	 * @param projects
	 */
	public void setUnfinProjects(ArrayList<Project> projects) {
		unfinProjects = projects;
	}
	
	/**
	 * former getUnpublProjects
	 * 
	 * @return
	 */
	public ArrayList<Project> getUnfinProjects() {
		return unfinProjects;
	}
	
	/**
	 * addUnpublProject
	 * 
	 * @param pro
	 */
	public void addUnfinProject(Project pro) {
		unfinProjects.add(pro);
	}
	
	/* Person */
	/**
	 * @param person
	 */
	public void setPerson(Person person) {
		this.person = person;
	}

	/**
	 * @return
	 */
	public Person getPerson() {
		return person;
	}
	
	/* UserRole */
	/**
	 * @param userRole
	 */
	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	/**
	 * @return
	 */
	public UserRole getUserRole() {
		return userRole;
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
		
		if (loadStatus)
			loadStatus = basket.entityLoad(connection);
		
		try {
			if(!connection.isClosed()) {
				stmt = connection.createStatement();
				
				String sqlQuery = "SELECT person, name, role " +	
					"FROM users " +
					"WHERE id=\"" + this.getEntityID() + "\"";
					
				rSet = stmt.executeQuery( sqlQuery );
					
				while (rSet.next()) {
					person = new Person();
					person.setEntityID(rSet.getInt("person"));
					person.entityLoad(connection);
					
					this.setName(rSet.getString("name"));
					
					setUserRole(UserRole.getItem(rSet.getInt("role")));

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
				
				if (storeStatus) {
					sqlQuery = "UPDATE users SET "
						+ "person = '"		+ this.getPerson().getEntityID()						+ "', "
						+ "name = '"		+ DBField.USE_NAM.truncate(this.getName())			+ "', "
						+ "password = '"	+ DBField.USE_PAS.truncate(this.getPassword())		+ "', "						
						+ "role = '" 	+ this.getUserRole().getID()						+ "' "
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
					sqlQuery = "INSERT INTO users (person, name, password, role ";
					sqlQuery += ") VALUES ("
						+ "'"	+ this.getPerson().getEntityID()						+ "',"
						+ "'"	+ DBField.USE_NAM.truncate(this.getName(	))		+ "',"
						+ "'"	+ DBField.USE_PAS.truncate(this.getPassword())		+ "',"
						+ "'"	+ this.getUserRole().getID()							+ "'";
					sqlQuery += ")"; 
					
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
	
	/**
	 * @param connection
	 * @return
	 */
	public boolean storePassword(Connection connection) {

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
					sqlQuery = "UPDATE users SET "
						+ "password = '"	+ DBField.USE_PAS.truncate(this.getPassword())		+ "' "						
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
	public boolean editStore(Connection connection) {

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
					sqlQuery = "UPDATE users SET "
						+ "person = '"		+ this.getPerson().getEntityID()						+ "', "
						+ "name = '"		+ DBField.USE_NAM.truncate(this.getName())			+ "', "						
						+ "role = '" 	+ this.getUserRole().getID()						+ "' "
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
					sqlQuery = "INSERT INTO users (person, name, password, role ";
					sqlQuery += ") VALUES ("
						+ "'"	+ this.getPerson().getEntityID()						+ "',"
						+ "'"	+ DBField.USE_NAM.truncate(this.getName(	))		+ "',"
						+ "'"	+ this.getUserRole().getID()							+ "'";
					sqlQuery += ")"; 
					
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

	/**
	 * @param con
	 * @return
	 */
	public boolean checkUserAccount(Connection con) {
		boolean registration = false;
		
		java.sql.Statement stmt;
		ResultSet rSet;
		
		if (con != null) {
			try {
				if(!con.isClosed()) {
					stmt = con.createStatement();
					
					String sqlQuery = "SELECT id, person, role " +
						"FROM users " +
						"WHERE name=\"" + this.getName() + "\" " +
						"AND password=\"" + this.getPassword() + "\"";
					
					rSet = stmt.executeQuery(sqlQuery);
					
					while (rSet.next()) {
						this.setEntityID(rSet.getInt("id"));
						
						Person person = new Person();
						person.setEntityID(rSet.getInt("person"));
						person.entityLoad(con);
						
						this.setPerson(person);
						
						registration = true;
					}
					
					stmt.close();
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(
						null, e.getMessage(), "SQLException:",  JOptionPane.ERROR_MESSAGE);
					  	
				/* DEMO ONLY */
				System.err.println("SQLException: " + e.getMessage());
				e.printStackTrace();
			}
		}
		
		return registration;
	}
	
	/**
	 * @param id
	 * @param con
	 * @return
	 */
	public static String searchForUserName(int id, Connection con) {
		String retString = "";
		
		java.sql.Statement stmt;
		ResultSet rSet;
		
		if (con.equals(null)) {
			JOptionPane.showMessageDialog(
					null, "No Connection to DataBase!", "Error:", JOptionPane.ERROR_MESSAGE);
							
			/* DEMO ONLY */
			System.err.println("Error: No Connection to DataBase!");
			
			return "";
		}
		
		try {
			if (!con.isClosed()) {
				stmt = con.createStatement();
					
				String sql = "select name from users where id = "+ id;
				rSet = stmt.executeQuery( sql );
				
				while (rSet.next()) {					
					retString = rSet.getString("name");
				}
				
				stmt.close();
			} else {
				JOptionPane.showMessageDialog(
						null, "No open Connection!", "Error:", JOptionPane.ERROR_MESSAGE);
									
				/* DEMO ONLY */
				System.err.println("Error: No open Connection!");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(
					null, e.getMessage(), "SQLException:",  JOptionPane.ERROR_MESSAGE);
				  	
			/* DEMO ONLY */
			System.err.println("SQLException: " + e.getMessage());
			e.printStackTrace();
		}	   
					
		return retString;
	}
}

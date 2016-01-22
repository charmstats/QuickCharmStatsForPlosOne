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
public class InstanceLink extends DBEntity {

	/* charmstats.instance_links (MySQL)
		`id`	   int(11) NOT NULL auto_increment,
		`project`  int(11)			default NULL,
		`instance` int(11)			default NULL,
		`type`	   int(11)			default NULL,
		PRIMARY KEY  (`id`)
	*/
	
	public static final String TABLE		= "instance_links";
	public static final String PRIMARY_KEY	= "id";
	
	/*
	 *	Fields
	 */
	private Project				project;			/*Reference to containing Project*/
	private WorkStepInstance 	instance;			/*Reference to stored Layer*/
	private InstanceLinkType	workStepRefType;
	
	private int					dsProjectID;
	private int 				dsInstanceID;
	
	/*
	 *	Constructor
	 */
	/**
	 * 
	 */
	public InstanceLink () {
		super();
		
		entity_type = EntityType.INSTANCE_LINKS;
	}
	
	/**
	 * @param parameter
	 */
	public InstanceLink (WorkStepInstance parameter) {
		this();
		
		setInstance(parameter);
	}
	
	/**
	 * @param parameter
	 */
	public InstanceLink (Project parameter) {
		this();
		
		setProject(parameter);
	}
	
	/*
	 *	Methods
	 */
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		if ((instance != null) &&
				(project != null)) {
			return instance.toString(); 
		}
		
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
	public Project getProject() {
		return project;
	}
	
	/**
	 * @param instance
	 */
	public void setInstance(WorkStepInstance instance) {
		this.instance = instance;
	}

	/**
	 * @return
	 */
	public WorkStepInstance getInstance() {
		return instance;
	}

	/**
	 * @param workStepType
	 */
	public void setWorkStepRefType(InstanceLinkType workStepType) {
		this.workStepRefType = workStepType;
	}

	/**
	 * @return
	 */
	public InstanceLinkType getWorkStepRefType() {
		return workStepRefType;
	}
	
	
	/**
	 * @param dsProjectID
	 */
	public void setDsProjectID(int dsProjectID) {
		this.dsProjectID = dsProjectID;
	}

	/**
	 * @return
	 */
	public int getDsProjectID() {
		return dsProjectID;
	}
	
	/**
	 * @param dsInstanceID
	 */
	public void setDsInstanceID(int dsInstanceID) {
		this.dsInstanceID = dsInstanceID;
	}

	/**
	 * @return
	 */
	public int getDsInstanceID() {
		return dsInstanceID;
	}

	/*
	 *	Load and Store to DB
	 */
	/**
	 * @param connection
	 * @return
	 */
	public ArrayList<Integer> loadLinks(Connection connection) {
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		if (connection.equals(null)) {
			list = null;
			JOptionPane.showMessageDialog(
					null, "No Connection to DataBase!", "Error:", JOptionPane.ERROR_MESSAGE);
			
	    	/* DEMO ONLY */
	        System.err.println("Error: No Connection to DataBase!");
			
			return list;
		}
		
		if ((this.getProject() instanceof Project) &&
				(this.getProject().getEntityID() > 0)) {
			try {
				stmt = connection.createStatement();
				
				sqlQuery = "SELECT " + PRIMARY_KEY +
					" FROM "+ TABLE +
				    " WHERE project = " + this.getProject().getEntityID();

				rsltst = stmt.executeQuery(sqlQuery);
				
				while ( rsltst.next() ) {					
					list.add(rsltst.getInt(PRIMARY_KEY));
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
	
	/*
	 *	TODO after successful load of InstanceLink set project from outside entityLoad !!!
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
					"project, instance, type "+
					"FROM "+ TABLE +" "+
					"WHERE "+ PRIMARY_KEY +" = "+ this.getEntityID();
				
				rSet = stmt.executeQuery( sqlQuery );
					
				while (rSet.next()) {
					setDsProjectID(rSet.getInt("project"));
					setDsInstanceID(rSet.getInt("instance"));
					setWorkStepRefType(InstanceLinkType.getItem(rSet.getInt("type")));
					
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
					"project = '" 			+ this.getProject().getEntityID()			+ "', " +
					"instance = '" 			+ this.getInstance().getEntityID()	+ "', " +
					"type = '" 				+ this.getWorkStepRefType().getID()			+ "' " +
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
					sqlQuery = "INSERT INTO "+ TABLE +" ("+
					"project, "+
					"instance, "+
					"type" +
					") VALUES (" 
						+ "'"	+ this.getProject().getEntityID()			+ "',"
						+ "'"	+ this.getInstance().getEntityID()	+ "',"
						+ "'"	+ this.getWorkStepRefType().getID()			+ "'";
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

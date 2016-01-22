package org.gesis.charmstats.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.gesis.charmstats.persistence.DBEntity;

/**
 * 
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class InstanceMap  extends DBEntity {

	/* charmstats.instance_maps (MySQL)
		`id`		   int(11) NOT NULL auto_increment,
		`src_instance` int(11)			default NULL,
		`tgt_instance` int(11)			default NULL,
		`type`		   int(11)			default NULL,
		`belongs_to`   int(11)			default NULL,
		PRIMARY KEY  (`id`)
	*/
	
	/*
	 *	Fields
	 */
	private InstanceLink		sourceInstance;		/*Reference to stored WorkStepInstance (Source)*/
	private InstanceLink		targetInstance;		/*Reference to stored WorkStepInstance (Target)*/
	private InstanceMapType		type;
	private Project				belongsTo;			/*Reference to Project the W.S.I.Map is part of */
	
	private	int					dsProject;
	private int 				dsSourceInstanceType;
	private int 				dsSourceInstanceEntry;
	private int 				dsTargetInstanceType;
	private int 				dsTargetInstanceEntry;
	
	/*
	 *	Constructor
	 */
	/**
	 * 
	 */
	public InstanceMap() {
		super();
		
		setEntityID(-1);
	}
	
	/**
	 * @param id
	 */
	public InstanceMap(int id) {
		this();
		
		setEntityID(id);
	}
	
	/**
	 * @param source
	 * @param target
	 * @param type
	 */
	public InstanceMap(InstanceLink source, InstanceLink target, InstanceMapType type) {
		this();
		
		setSourceInstance(source);
		setTargetInstance(target);
		setType(type);
	}
	
	/*
	 *	Methods
	 */
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return this.getSourceInstance() + "=>" + this.getTargetInstance();
	}
	
	/**
	 * @return
	 */
	public InstanceLink getSourceInstance() {
		return sourceInstance;
	}

	/**
	 * @param sourceInstance
	 */
	public void setSourceInstance(InstanceLink sourceInstance) {
		this.sourceInstance = sourceInstance;
	}

	/**
	 * @return
	 */
	public InstanceLink getTargetInstance() {
		return targetInstance;
	}

	/**
	 * @param targetInstance
	 */
	public void setTargetInstance(InstanceLink targetInstance) {
		this.targetInstance = targetInstance;
	}
	
	/**
	 * @return
	 */
	public InstanceMapType	getType() {
		return type;
	}

	/**
	 * @param instanceMapType
	 */
	public void setType(InstanceMapType instanceMapType) {
		this.type = instanceMapType;
	}

	/**
	 * @return
	 */
	public Project getBelongsTo() {
		return belongsTo;
	}

	/**
	 * @param belongsTo
	 */
	public void setBelongsTo(Project belongsTo) {
		this.belongsTo = belongsTo;
	}

	/**
	 * @return
	 */
	public int getDsProject() {
		return dsProject;
	}

	/**
	 * @param dsProject
	 */
	public void setDsProject(int dsProject) {
		this.dsProject = dsProject;
	}

	/**
	 * @return
	 */
	public int getDsSourceInstanceType() {
		return dsSourceInstanceType;
	}

	/**
	 * @param dsSourceInstanceType
	 */
	public void setDsSourceInstanceType(int dsSourceInstanceType) {
		this.dsSourceInstanceType = dsSourceInstanceType;
	}

	/**
	 * @return
	 */
	public int getDsSourceInstanceEntry() {
		return dsSourceInstanceEntry;
	}

	/**
	 * @param dsSourceInstanceEntry
	 */
	public void setDsSourceInstanceEntry(int dsSourceInstanceEntry) {
		this.dsSourceInstanceEntry = dsSourceInstanceEntry;
	}

	/**
	 * @return
	 */
	public int getDsTargetInstanceType() {
		return dsTargetInstanceType;
	}

	/**
	 * @param dsTargetInstanceType
	 */
	public void setDsTargetInstanceType(int dsTargetInstanceType) {
		this.dsTargetInstanceType = dsTargetInstanceType;
	}

	/**
	 * @return
	 */
	public int getDsTargetInstanceEntry() {
		return dsTargetInstanceEntry;
	}

	/**
	 * @param dsTargetInstanceEntry
	 */
	public void setDsTargetInstanceEntry(int dsTargetInstanceEntry) {
		this.dsTargetInstanceEntry = dsTargetInstanceEntry;
	}
	
	/*
	 *	Load and Store to DB
	 */
	/**
	 * @param connection
	 * @return
	 */
	public ArrayList<Integer> loadMaps(Connection connection) {
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		if (connection.equals(null)) {
			list = null;
			JOptionPane.showMessageDialog(
					null, "No Connection to DataBase!", "Error:", JOptionPane.ERROR_MESSAGE);
			
	    	/* DEMO ONLY */
	        System.err.println("Error: No Connection to DataBase!");
			
			return list;
		}
		
		if ((getBelongsTo() instanceof Project) && 
				(getBelongsTo().getEntityID() > 0)) {
			try {
				stmt = connection.createStatement();
				
				sqlQuery = "SELECT id " +
					"FROM instance_maps " +
				    "WHERE belongs_to = " + getBelongsTo().getEntityID();

				rsltst = stmt.executeQuery(sqlQuery);
				
				while (rsltst.next()) {					
					list.add(rsltst.getInt("id"));
				}
				
				stmt.close();		
		    } catch (SQLException e) {
		    	list = null;
		    	JOptionPane.showMessageDialog(
		    			null, e.getMessage(), "SQLException:",  JOptionPane.ERROR_MESSAGE);
		    	
		    	/* DEMO ONLY */
		        System.err.println("SQLException: " + e.getMessage());	    	
		    	e.printStackTrace();
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
					
				String sqlQuery = "SELECT belongs_to, " + 
					"src_instance, " +
					"tgt_instance, " +
					"type " +
					"FROM instance_maps " +
					"WHERE id=\"" + this.getEntityID() + "\"";
				
				rSet = stmt.executeQuery( sqlQuery );
					
				while (rSet.next()) {
					setDsProject(rSet.getInt("belongs_to"));
					setDsSourceInstanceEntry(rSet.getInt("src_instance"));
					setDsTargetInstanceEntry(rSet.getInt("tgt_instance"));
					setType(InstanceMapType.getItem(rSet.getInt("type")));
					
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
				
				sqlQuery = "UPDATE instance_maps SET " 
					+ "belongs_to = " 			+ ((getBelongsTo() != null) 	? getBelongsTo().getEntityID() 		: "NULL")	+ ", "
					+ "src_instance = "			+ ((getSourceInstance() != null)? getSourceInstance().getEntityID()	: "NULL") 	+ ", "			
					+ "tgt_instance = "			+ ((getTargetInstance() != null)? getTargetInstance().getEntityID() : "NULL") 	+ ", "
					+ "type = '"			 	+ this.getType().getID()					+ "' "
				    + "WHERE id = "	+ this.getEntityID();
				
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
					sqlQuery = "INSERT INTO instance_maps (";
					sqlQuery += "belongs_to, ";
					sqlQuery += "src_instance, ";
					sqlQuery += "tgt_instance, ";
					sqlQuery += "type";
					sqlQuery += ") VALUES (" 
						+ ((getBelongsTo() != null) 	? getBelongsTo().getEntityID() 		: "NULL")	+","
						+ ((getSourceInstance() != null)? getSourceInstance().getEntityID()	: "NULL")	+","
						+ ((getTargetInstance() != null)? getTargetInstance().getEntityID() : "NULL")	+","
						+ "'"	+ getType().getID()					+ "'";
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
			JOptionPane.showMessageDialog(null, "Error! No Connection to DataBase.");
			
			return removeStatus;
		}
		
		if (entity_id > 0) {
			try {
				sqlQuery = "DELETE from instance_maps " +
					"WHERE id = " + this.getEntityID();
				
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

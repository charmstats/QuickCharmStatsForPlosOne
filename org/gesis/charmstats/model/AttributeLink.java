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
public class AttributeLink extends DBEntity {
	
	/* charmstats.attribute_links (MySQL)
		`id`		  int(11) NOT NULL auto_increment,
		`instance`	  int(11)		   default NULL,
		`attr_entity` int(11)		   default NULL,
		`attr_entry`  int(11)		   default NULL,
		`type`		  int(11)		   default NULL,
		PRIMARY KEY  (`id`)
	*/
	
	/*
	 *	Fields
	 */
	private WorkStepInstance	instance;			/*Reference to containing Layer*/
	private Attributes			attribute;			/*Reference to stored Attribute*/
	private AttributeLinkType	type;
	
	private int 				dsInstanceID;
	private int 				dsEntityTypeID;
	private int 				dsEntryID;
		
	/**
	 *	Constructor
	 */
	public AttributeLink() {
		super();
		
		entity_type = EntityType.ATTRIBUTE_LINKS;
	}
	
	/**
	 * @param parameter
	 */
	public AttributeLink(Attributes parameter) {
		this();
		
		setAttribute(parameter);
	}
	
	/**
	 * @param parameter
	 */
	public AttributeLink(WorkStepInstance parameter) {
		this();
		
		setInstance(parameter);
	}
		
	/*
	 *	Methods
	 */
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		if ((attribute != null) &&
				(instance != null)) {
			return attribute.toString();
		}
		
		/*In case of fault:*/
		return super.toString();
	}
	
	/*reference, pro_layer_id:*/
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
	
	/*attribute, table_id + table_entry_id:*/
	/**
	 * @param attribute
	 */
	public void setAttribute(Attributes attribute) {
		this.attribute = attribute;
	}

	/**
	 * @return
	 */
	public Attributes getAttribute() {
		return attribute;
	}

	/*attributeLinkType, attr_link_type_id:*/
	/**
	 * @param attributeLinkType
	 */
	public void setAttributeLinkType(AttributeLinkType attributeLinkType) {
		this.type = attributeLinkType;
	}

	/**
	 * @return
	 */
	public AttributeLinkType getAttributeLinkType() {
		return type;
	}
	

	/**
	 * @param dsProLayerID
	 */
	public void setDsProLayerID(int dsProLayerID) {
		this.dsInstanceID = dsProLayerID;
	}

	/**
	 * @return
	 */
	public int getDsProLayerID() {
		return dsInstanceID;
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
		
		if (this.getInstance().getEntityID() > 0) {
			try {
				stmt = connection.createStatement();
				
				sqlQuery = "SELECT id " +
					"FROM attribute_links " +
				    "WHERE instance = " + getInstance().getEntityID();

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
					
				String sqlQuery = "SELECT instance, attr_entity, attr_entry, type " +
					"FROM attribute_links " +
					"WHERE id=\"" + this.getEntityID() + "\"";
				
				rSet = stmt.executeQuery( sqlQuery );
					
				while (rSet.next()) {
					setDsProLayerID(rSet.getInt("instance"));
					setDsEntityTypeID(rSet.getInt("attr_entity"));
					setDsEntryID(rSet.getInt("attr_entry"));
					setAttributeLinkType(AttributeLinkType.getItem(rSet.getInt("type")));
					
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
								
				sqlQuery = "UPDATE attribute_links SET " 
					+ "instance = '"	+ this.getInstance().getEntityID()		+ "', "
					+ "attr_entity = '"		+ this.getAttribute().getEntityType().getID() 		+ "', "
					+ "attr_entry = '" 		+ this.getAttribute().getEntityID()		+ "', "
					+ "type = '" 		+ this.getAttributeLinkType().getID()	+ "' "
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
					sqlQuery = "INSERT INTO attribute_links (";
					sqlQuery += "instance, ";
					sqlQuery += "attr_entity, ";
					sqlQuery += "attr_entry, ";
					sqlQuery += "type";
					sqlQuery += ") VALUES (" 
						+ "'"	+ this.getInstance().getEntityID()			+ "',"
						+ "'"	+ this.getAttribute().getEntityType().getID()		+ "',"
						+ "'"	+ this.getAttribute().getEntityID()			+ "',"
						+ "'"	+ this.getAttributeLinkType().getID()	+ "'";
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
				sqlQuery = "DELETE from attribute_links " +
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

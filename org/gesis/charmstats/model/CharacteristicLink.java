package org.gesis.charmstats.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
public class CharacteristicLink extends DBEntity {

	/* charmstats.characteristic_links (MySQL)
		`id`		  int(11) NOT NULL auto_increment,
		`attr_entity` int(11)		   default NULL,
		`attr_entry`  int(11)		   default NULL,
		`char_entity` int(11)		   default NULL,
		`char_entry`  int(11)		   default NULL,
		`type`		  int(11)		   default NULL,
		PRIMARY KEY  (`id`)
	*/
	
	/*
	 *	 
	 */
	public static final String TABLE		= "characteristic_links";
	public static final String PRIMARY_KEY	= "id";
	
	/*
	 *	Fields
	 */
	private Attributes				attribute;		/*Reference to containing Attribute*/
	private Characteristics			characteristic;	/*Reference to stored Characteristic*/
	private CharacteristicLinkType	type;
	
	private int 					dsAttrEntityID;
	private int 					dsAttrEntryID;
	private int 					dsCharEntityID;
	private int 					dsCharEntryID;
	
	
	/**
	 *	Constructor 
	 */
	public CharacteristicLink() {
		super();
		
		entity_type = EntityType.CHARACTERISTIC_LINKS;
	}
	
	/**
	 * @param parameter
	 */
	public CharacteristicLink(Characteristics parameter) {
		this();
		
		setCharacteristic(parameter);
	}
	
	/**
	 * @param parameter
	 */
	public CharacteristicLink(Attributes parameter) {
		this();
		
		setAttribute(parameter);
	}
		
	/*
	 *	Methods
	 */
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		if ((characteristic != null) &&
				(attribute != null)) {
			return characteristic.toString();
		}
		
		/*In case of fault:*/
		return super.toString();
	}

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
	
	/**
	 * @return
	 */
	public Characteristics getCharacteristic() {
		return characteristic;
	}
	
	/**
	 * @param characteristic
	 */
	public void setCharacteristic(Characteristics characteristic) {
		this.characteristic = characteristic;
	}
	
	/**
	 * @return
	 */
	public CharacteristicLinkType getType() {
		return type;
	}
	
	/**
	 * @param type
	 */
	public void setType(CharacteristicLinkType type) {
		this.type = type;
	}
	
	/**
	 * @param dsAttrEntityID
	 */
	public void setDsAttrEntityID(int dsAttrEntityID) {
		this.dsAttrEntityID = dsAttrEntityID;
	}

	/**
	 * @return
	 */
	public int getDsAttrEntityID() {
		return dsAttrEntityID;
	}
	
	/**
	 * @param dsAttrEntryID
	 */
	public void setDsAttrEntryID(int dsAttrEntryID) {
		this.dsAttrEntryID = dsAttrEntryID;
	}

	/**
	 * @return
	 */
	public int getDsAttrEntryID() {
		return dsAttrEntryID;
	}

	/**
	 * @param dsCharEntityID
	 */
	public void setDsCharEntityID(int dsCharEntityID) {
		this.dsCharEntityID = dsCharEntityID;
	}

	/**
	 * @return
	 */
	public int getDsCharEntityID() {
		return dsCharEntityID;
	}

	/**
	 * @param dsCharEntryID
	 */
	public void setDsCharEntryID(int dsCharEntryID) {
		this.dsCharEntryID = dsCharEntryID;
	}

	/**
	 * @return
	 */
	public int getDsCharEntryID() {
		return dsCharEntryID;
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
		
		if (this.getAttribute().getEntityID() > 0) {
			try {
				stmt = connection.createStatement();
				
				sqlQuery = "SELECT "+ PRIMARY_KEY + " " +
					"FROM "+ TABLE  + " " +
				    "WHERE attr_entry = " + getAttribute().getEntityID() +
					" AND attr_entity = " + getAttribute().getEntityType().getID();
				
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
					
				String sqlQuery = "SELECT attr_entity, attr_entry, char_entity, char_entry, type "+
					"FROM "+ TABLE + " " +
					"WHERE "+ PRIMARY_KEY +" = "+ this.getEntityID();
				
				rSet = stmt.executeQuery( sqlQuery );
					
				while (rSet.next()) {
					setDsAttrEntityID(rSet.getInt("attr_entity"));
					setDsAttrEntryID(rSet.getInt("attr_entry"));
					setDsCharEntityID(rSet.getInt("char_entity"));
					setDsCharEntryID(rSet.getInt("char_entry"));
					setType(CharacteristicLinkType.getItem(rSet.getInt("type")));
					
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
					"attr_entity = '" 				+ this.getAttribute().getEntityType().getID()			+"', "+
					"attr_entry = '" 				+ this.getAttribute().getEntityID()			+"', "+
					"char_entity = '"					+ this.getCharacteristic().getEntityType().getID() 	+"', "+
					"char_entry = '" 			+ this.getCharacteristic().getEntityID()	+"', "+
					"type = '" 			+ this.getType().getID()	+"' "+
				    "WHERE "+ PRIMARY_KEY +" = "	+ this.getEntityID();
				
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
					"attr_entity, "+
					"attr_entry, "+
					"char_entity, "+
					"char_entry, "+
					"type"+
					") VALUES ("+ 
						"'"+ this.getAttribute().getEntityType().getID()	+"',"+
						"'"+ this.getAttribute().getEntityID()				+"',"+
						"'"+ this.getCharacteristic().getEntityType().getID()		+"',"+
						"'"+ this.getCharacteristic().getEntityID()		+"',"+
						"'"+ this.getType().getID()	+"'";
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

	public static List<CharacteristicLink> getList(int entityID, Connection _connection) {
		List<CharacteristicLink> list = new ArrayList<CharacteristicLink>();
		
		if (_connection.equals(null)) {
			list = null;
			JOptionPane.showMessageDialog(
					null, "No Connection to DataBase!", "Error:", JOptionPane.ERROR_MESSAGE);
							
			/* DEMO ONLY */
			System.err.println("Error: No Connection to DataBase!");
			
			return list;
		}
		
		try {
			Statement	stmt		= _connection.createStatement();			
			String		sqlQuery	= "SELECT id FROM characteristic_links WHERE ";
				sqlQuery += "";
			ResultSet	rsltst		= stmt.executeQuery(sqlQuery);
			
			while ( rsltst.next() ) {					
				int charLinkID = rsltst.getInt("id");
				
				CharacteristicLink link = new CharacteristicLink(); 
				link.setEntityID(charLinkID);
				link.entityLoad(_connection);
				
				list.add(link);
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
		
		return list;
	}
}

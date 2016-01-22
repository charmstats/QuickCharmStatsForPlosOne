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
public class CharacteristicMap   extends DBEntity {

	/* charmstats.characteristic_maps (MySQL)
		`id`					int(11) NOT NULL auto_increment,
		`source_characteristic` int(11)			 default NULL,
		`target_characteristic` int(11)			 default NULL,
		`type`					int(11)			 default NULL,
		`belongs_to`			int(11)			 default NULL,
		PRIMARY KEY  (`id`)
	*/
	
	/*
	 *	Fields
	 */
	private CharacteristicLink		sourceCharacteristic;	/*Reference to stored Characteristic (Source)*/
	private CharacteristicLink		targetCharacteristic;	/*Reference to stored Characteristic (Target)*/
	private CharacteristicMapType	type;
	private AttributeMap			belongsTo;				/*Reference to AttributeMap the CharacteristicMap is part of */
	
	private int					dsAttributeMap;
	private int 				dsSourceCharType;
	private int 				dsSourceCharEntry;
	private int 				dsTargetCharType;
	private int 				dsTargetCharEntry;

	
	/**
	 *	Constructor 
	 */
	public CharacteristicMap() {
		super();
		
		setEntityID(-1);
	}
	
	/**
	 * @param id
	 */
	public CharacteristicMap(int id) {
		super();
		
		setEntityID(id);
	}
	
	/**
	 * @param source
	 * @param target
	 * @param type
	 */
	public CharacteristicMap(CharacteristicLink source, CharacteristicLink target, CharacteristicMapType type) {
		this();
		
		setSourceCharacteristic(source);
		setTargetCharacteristic(target);
		setType(type);
	}
	
	/*
	 *	Methods
	 */
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		if ((sourceCharacteristic != null) &&
				(targetCharacteristic != null)) {
			return   
				sourceCharacteristic.toString() + " => " + targetCharacteristic.toString();
		}
		
		/*In case of fault:*/
		return super.toString();
	}
	
	/**
	 * @return
	 */
	public CharacteristicLink getSourceCharacteristic() {
		return sourceCharacteristic;
	}

	/**
	 * @param sourceCharacteristic
	 */
	public void setSourceCharacteristic(CharacteristicLink sourceCharacteristic) {
		this.sourceCharacteristic = sourceCharacteristic;
	}

	/**
	 * @return
	 */
	public CharacteristicLink getTargetCharacteristic() {
		return targetCharacteristic;
	}

	/**
	 * @param targetCharacteristic
	 */
	public void setTargetCharacteristic(CharacteristicLink targetCharacteristic) {
		this.targetCharacteristic = targetCharacteristic;
	}
	
	/**
	 * @return
	 */
	public CharacteristicMapType getType() {
		return type;
	}

	/**
	 * @param type
	 */
	public void setType(CharacteristicMapType type) {
		this.type = type;
	}

	/**
	 * @return
	 */
	public AttributeMap getBelongsTo() {
		return belongsTo;
	}

	/**
	 * @param belongsTo
	 */
	public void setBelongsTo(AttributeMap belongsTo) {
		this.belongsTo = belongsTo;
	}

	/**
	 * @return
	 */
	public int getDsAttributeMap() {
		return dsAttributeMap;
	}

	/**
	 * @param dsAttributeMap
	 */
	public void setDsAttributeMap(int dsAttributeMap) {
		this.dsAttributeMap = dsAttributeMap;
	}

	/**
	 * @param dsType
	 */
	public void setDsSourceCharType(int dsType) {
		this.dsSourceCharType = dsType;
	}

	/**
	 * @return
	 */
	public int getDsSourceCharType() {
		return dsSourceCharType;
	}
	
	/**
	 * @param dsEntry
	 */
	public void setDsSourceCharEntry(int dsEntry) {
		this.dsSourceCharEntry = dsEntry;
	}

	/**
	 * @return
	 */
	public int getDsSourceCharEntry() {
		return dsSourceCharEntry;
	}

	/**
	 * @param dsType
	 */
	public void setDsTargetCharType(int dsType) {
		this.dsTargetCharType = dsType;
	}

	/**
	 * @return
	 */
	public int getDsTargetCharType() {
		return dsTargetCharType;
	}
	
	/**
	 * @param dsEntry
	 */
	public void setDsTargetCharEntry(int dsEntry) {
		this.dsTargetCharEntry = dsEntry;
	}

	/**
	 * @return
	 */
	public int getDsTargetCharEntry() {
		return dsTargetCharEntry;
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
		
		if (getBelongsTo().getEntityID() > 0) {
			try {
				stmt = connection.createStatement();
				
				sqlQuery = "SELECT id " +
					"FROM characteristic_maps " +
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
					"source_characteristic, " +
					"target_characteristic, " +
					"type " +
					"FROM characteristic_maps " +
					"WHERE id=\"" + this.getEntityID() + "\"";
				
				rSet = stmt.executeQuery( sqlQuery );
					
				while (rSet.next()) {
					setDsAttributeMap(rSet.getInt("belongs_to"));
					setDsSourceCharEntry(rSet.getInt("source_characteristic"));
					setDsTargetCharEntry(rSet.getInt("target_characteristic"));
					setType(CharacteristicMapType.getItem(rSet.getInt("type")));
					
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
//			if (this.getBelongsTo() != null) {
			
				try {
					connection.setAutoCommit(false);
					
					sqlQuery = "UPDATE characteristic_maps SET " 
						+ "belongs_to = " 				+ ((getBelongsTo() != null) ?getBelongsTo().getEntityID() : "NULL")			+ ", "
						+ "source_characteristic = " 	+ this.getSourceCharacteristic().getEntityID() 	+ ", "			
						+ "target_characteristic = "	+ ((getTargetCharacteristic() != null) ? getTargetCharacteristic().getEntityID() : "NULL") 	+ " "
//						+ "type = "			 		+ this.getType().getID()					+ " "
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
			
//			}
		} else {
			/*
			 *	Insert
			 */
//			if (this.getBelongsTo() != null) {
				
				try {
					connection.setAutoCommit(false);
					
					if (storeStatus) {
						sqlQuery = "INSERT INTO characteristic_maps (";
						sqlQuery += "belongs_to, ";
						sqlQuery += "source_characteristic, ";
						sqlQuery += "target_characteristic, ";
						sqlQuery += "type";
						sqlQuery += ") VALUES (" 
							+ ((getBelongsTo() != null) ?getBelongsTo().getEntityID() : "NULL")				+ ","
							+ getSourceCharacteristic().getEntityID()	+ ","
							+ ((getTargetCharacteristic() != null) ? getTargetCharacteristic().getEntityID() : "NULL")	+ ","
							+ getType().getID();
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
			
//			}
			
//			storeStatus = true;
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
				sqlQuery = "DELETE from characteristic_maps " +
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

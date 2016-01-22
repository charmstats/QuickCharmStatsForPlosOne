package org.gesis.charmstats.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import org.gesis.charmstats.persistence.DBField;
import org.gesis.charmstats.persistence.EntityType;

/**
 *	This is an Entity-Class
 * 
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class Value extends Characteristics {
	
	/* charmstats.valuez (MySQL)
		`id`	   			 int(11)	  NOT NULL auto_increment,
		`label`				 varchar(255)		   default NULL,
		`value` 			 varchar(20)		   default NULL,
		`valid` 			 tinyint(1)			   default NULL,
		`frequency`			 int(11)			   default NULL,
		`frequency_weighted` int(11)			   default NULL,
		`relative_frequency` float				   default NULL,
		`is_missing` 		 tinyint(1)			   default '0',  
	PRIMARY KEY  (`id`)
	*/
	
	/*
	 *	Fields
	 */
	private String	label;
	
	private String	value;
	private boolean	valid;
	private int		frequency;
	private int		freq_weighted;
	private float	relative_frequency;
	private boolean	isMissing;
		
	/*
	 *	Constructor
	 */
	/**
	 * 
	 */
	public Value() {
		super();
		
		entity_type = EntityType.DATA_VALUES;
		
		setMissing(false);
	}
	
	/**
	 * @param id
	 */
	public Value(int id) {
		this();
		
		setEntityID(id); 
	}
	
	/**
	 * @param id
	 * @param label
	 */
	public Value(int id, String label) {
		this(id);
		
		setLabel(label); 
	}
	
	/**
	 * @param id
	 * @param value
	 * @param label
	 */
	public Value(int id, String value, String label) {
		this(id, label);
		
		setValue(value); 
	}
	
	/*
	 *	Methods
	 */
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return getValue() +" "+ (getLabel() != null ? getLabel():""); 
	
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

	/* Value */
	/**
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return
	 */
	public String getValue() {
		return value;
	}

	/* Valid */
	/**
	 * @param valid
	 */
	public void setValid(boolean valid) {
		this.valid = valid;
	}

	/**
	 * @return
	 */
	public boolean isValid() {
		return valid;
	}
	
	// mySQL only:
	/**
	 * @return
	 */
	public int getValid() {
		return (valid ? 1 : 0);
	}

	/* Frequency */
	/**
	 * @param frequency
	 */
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	/**
	 * @return
	 */
	public int getFrequency() {
		return frequency;
	}

	/* Freq. Weight */
	/**
	 * @param freq_weighted
	 */
	public void setFreqWeighted(int freq_weighted) {
		this.freq_weighted = freq_weighted;
	}

	/**
	 * @return
	 */
	public int getFreqWeighted() {
		return freq_weighted;
	}

	/* Freq. Relative */
	/**
	 * @param relative_frequency
	 */
	public void setFreqRelative(float relative_frequency) {
		this.relative_frequency = relative_frequency;
	}

	/**
	 * @return
	 */
	public float getFreqRelative() {
		return relative_frequency;
	}
	
	/* Missig */
	/**
	 * @return
	 */
	public boolean isMissing() {
		return isMissing;
	}
	
	// mySQL only:
	/**
	 * @return
	 */
	public int getMissing() {
		return (isMissing ? 1 : 0);
	}

	/**
	 * @param isMissing
	 */
	public void setMissing(boolean isMissing) {
		this.isMissing = isMissing;
	}
	
	/*
	 *	Load and Store to DB
	 */
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.persistence.DBEntity#entityLoad(java.sql.Connection)
	 */
	public boolean entityLoad(Connection connection) {

		boolean loadStatus = false;
		
		/*
		 *	Select
		 */
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
					
				String sqlQuery = "SELECT label, value, valid, frequency, frequency_weighted, relative_frequency, is_missing " +
					"FROM valuez " +
					"WHERE id=\"" + this.getEntityID() + "\"";
				
				rSet = stmt.executeQuery( sqlQuery );
					
				while (rSet.next()) {
					this.setLabel(rSet.getString("label"));
					this.setValue(rSet.getString("value"));
					
					this.setValid(rSet.getBoolean("valid"));
					
					this.setFrequency(rSet.getInt("frequency"));
					this.setFreqWeighted(rSet.getInt("frequency_weighted"));
					this.setFreqRelative(((Double)rSet.getDouble("relative_frequency")).floatValue());
					
					this.setMissing(rSet.getBoolean("is_missing"));

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
				
				sqlQuery = "UPDATE valuez SET " 
					+ "label = ?, "
					+ "value = ?, "
					+ "valid = ?, "
					+ "frequency = ?, "
					+ "frequency_weighted = ?, "
					+ "relative_frequency = ?, "
					+ "is_missing = ? "
					+ "WHERE id = ?";					
				
				prepStmt = connection.prepareStatement(sqlQuery);
				prepStmt.setString(1, DBField.VAL_LAB.truncate(this.getLabel()));
				prepStmt.setString(2, DBField.VAL_VAL.truncate(this.getValue()));
				prepStmt.setInt(3, this.getValid());
				prepStmt.setInt(4, this.getFrequency());
				prepStmt.setInt(5, this.getFreqWeighted());
				prepStmt.setFloat(6, this.getFreqRelative());
				prepStmt.setInt(7, this.getMissing());
				prepStmt.setInt(8, this.getEntityID());
				
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
					sqlQuery = "INSERT INTO valuez (label, value";
					sqlQuery += ", valid";
					sqlQuery += ", frequency, frequency_weighted, relative_frequency";
					sqlQuery += ", is_missing";
					sqlQuery += ") VALUES ("
						+ "?, ?, ?, ?, ?, ?, ?"; 
					sqlQuery += ")";
					
					prepStmt = connection.prepareStatement(sqlQuery);
					prepStmt.setString(1, DBField.VAL_LAB.truncate(this.getLabel()));
					prepStmt.setString(2, DBField.VAL_VAL.truncate(this.getValue()));
					prepStmt.setInt(3, this.getValid());
					prepStmt.setInt(4, this.getFrequency());
					prepStmt.setInt(5, this.getFreqWeighted());
					prepStmt.setFloat(6, this.getFreqRelative());
					prepStmt.setInt(7, this.getMissing());
					
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
				
				if (removeStatus) {
					sqlQuery = "DELETE from valuez " +
						"WHERE id = " + this.getEntityID();
					
					stmt = connection.createStatement();
					rows = stmt.executeUpdate(sqlQuery);
					
					if (rows < 1)				
						removeStatus = false;
					
					stmt.close();
				}
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

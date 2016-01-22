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
public class Category extends Characteristics { 
	
	/* charmstats.categories (MySQL)
		`id`		 int(11) 	  NOT NULL auto_increment,
		`code`		 varchar(255)		   default NULL,
		`label`		 varchar(255)		   default NULL,
		`level`		 int(11)			   default NULL,
		`is_missing` tinyint(1)			   default '0',
		PRIMARY KEY  (`id`)
	*/
	
	/*
	 *	Fields
	 */
	private String		code;
	private String		label;
	private int			level;
	private boolean		isMissing;

	private Definition	definition;
	
	/**
	 *	Constructor 
	 */
	public Category() {
		super();
		
		entity_type = EntityType.CATEGORIES;
	
		setCode(new String());
		setLabel(new String());
		
		setDefinition(new Definition());
		getDefinition().setReference(this);
		
		setMissing(false);
	}
	
	/**
	 * @param pk
	 */
	public Category(int pk) {
		this();
		
		this.setEntityID(pk); 
	}
	
	/**
	 * @param pk
	 * @param label
	 */
	public Category(int pk, String label) {
		this(pk);
		
		this.setLabel(label);
	}
	
	/**
	 * @param pk
	 * @param code
	 * @param label
	 */
	public Category(int pk, String code, String label) {
		this(pk, label);
		
		setCode(code);
	}
	
	/*
	 *	Methods
	 */
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		if (getLabel() != null)
			return getLabel();
		
		return super.toString();
	}
	
	/**
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return
	 */
	public String getCode() {
		return code;
	}

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
	
	/**
	 * @param level
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * @return
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @param definition
	 */
	public void setDefinition(Definition definition) {
		this.definition = definition;
	}
	
	/**
	 * @return
	 */
	public Definition getDefinition() {
		return definition;
	}
	
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
			if (!connection.isClosed()) {
				stmt = connection.createStatement();
					
				String sqlQuery = "SELECT code, label, level, is_missing " +
					"FROM categories " +
					"WHERE id=\"" + this.getEntityID() + "\"";
				
				rSet = stmt.executeQuery( sqlQuery );
					
				while (rSet.next()) {
					this.setCode(rSet.getString("code"));
					this.setLabel(rSet.getString("label"));
					
					this.setLevel(rSet.getInt("level"));
					
					this.setMissing(rSet.getBoolean("is_missing"));

					loadStatus = true;
				}
				
				/* Handle Definition: */
				sqlQuery = "SELECT d.id " +
						"FROM definitions d " +
						"WHERE d.object_entity=\"" + this.getEntityType().getID() +"\" " +
							"AND d.object_entry=\"" + this.getEntityID() + "\" ";
				
				rSet = stmt.executeQuery( sqlQuery );
				
				while (rSet.next()) {
					setDefinition(new Definition());
					getDefinition().setEntityID(rSet.getInt("id"));				
					if (loadStatus) {
						loadStatus = getDefinition().entityLoad(connection);
						getDefinition().setReference(this);
					}
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
				
				storeStatus = definition.entityStore(connection);
				
				sqlQuery = "UPDATE categories SET "
					+ "label = ?, "
					+ "code = ?, "
					+ "level = ?, "
					+ "is_missing = ? "
					+ "WHERE id = ?";
					
				prepStmt = connection.prepareStatement(sqlQuery);
				prepStmt.setString(1, DBField.CAT_LAB.truncate(this.getLabel()));
				prepStmt.setString(2, DBField.CAT_COD.truncate(this.getCode()));
				prepStmt.setInt(3, this.getLevel());
				prepStmt.setInt(4, this.getMissing());
				prepStmt.setInt(5, this.getEntityID());
				
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
					/*
					 *	Use Insert-Statement
					 */							
					sqlQuery = "INSERT INTO categories (label, code, level, is_missing";
					sqlQuery += ") VALUES ("
						+ "?, ?, ?, ?";
					sqlQuery += ")";
					
					prepStmt = connection.prepareStatement(sqlQuery);
					prepStmt.setString(1, DBField.CAT_LAB.truncate(this.getLabel()));
					prepStmt.setString(2, DBField.CAT_COD.truncate(this.getCode()));
					prepStmt.setInt(3, this.getLevel());
					prepStmt.setInt(4, this.getMissing());
					
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
					
					if (storeStatus)
						storeStatus = definition.entityStore(connection);
				
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
			if (definition instanceof Definition)
				definition.entityRemove(connection);
			
			try {
				sqlQuery = "DELETE from categories " +
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

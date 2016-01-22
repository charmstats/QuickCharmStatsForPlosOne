package org.gesis.charmstats.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

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
public class Description extends DBEntity {

	/*
	 *	Column names and other text constants
	 */
	public static final String table_name		= "descriptions";
	public static final String primary_key		= "id";
	public static final String select			= "SELECT ";
	public static final String from				= " FROM ";
	public static final String update			= "UPDATE ";
	public static final String set				= " SET ";
	public static final String insert_into		= "INSERT INTO ";
	public static final String values			= " VALUES ";
	public static final String delete_from		= "DELETE FROM ";
	public static final String where			= " WHERE ";
	public static final String apostroph		= "'";
	public static final String blank			= " ";
	public static final String comma			= ", ";
	public static final String equals			= " = ";
	public static final String quote			= "\"";
	public static final String cn_for_txt		= "formated_text";
	public static final String cn_is_pre		= "is_preferred";
	
	/*
	 *	Fields
	 */
	private FormattedText	textual_content;
	private Boolean			is_preferred;
	
	/**
	 *	Constructor 
	 */
	public Description () {
		super();
		
		entity_type = EntityType.LABELS;
		
		setDescription(new String());
		setIsPreferred(false);
	}
	
	/**
	 * @param label
	 */
	public Description (String label) {
		this();
		
		this.setDescription(label);
	}
	
	/*
	 *	Methods
	 */
	/**
	 * @param label
	 */
	public void setDescription(String label) {
		this.textual_content = new FormattedText(label);
	}
	
	/**
	 * @param label
	 */
	public void setDescription(FormattedText label) {
		this.textual_content = label;
	}

	/**
	 * @return
	 */
	public FormattedText getDescription() {
		return textual_content;
	}

	/**
	 * @param is_preferred
	 */
	public void setIsPreferred(Boolean is_preferred) {
		this.is_preferred = is_preferred;
	}
	
	/**
	 * @return
	 */
	public Boolean isPreferred() {
		return is_preferred;
	}
	
	// use before mySQL insert / update only:
	/**
	 * @return
	 */
	public int getPreferrence() {
		return (isPreferred() ? 1 : 0);
	}
	
	/*
	 *	Load and Store to DB
	 */
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.persistence.DBEntity#entityLoad(java.sql.Connection)
	 */
	public boolean entityLoad(Connection connection) {

		boolean loadStatus = true;
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
					
				String sqlQuery = select+cn_for_txt+comma
					+cn_is_pre
					+from+table_name
					+where+primary_key+equals+getEntityID();
					
				rSet = stmt.executeQuery( sqlQuery );
					
				while (rSet.next()) {
					if (loadStatus) {
						int id = rSet.getInt(cn_for_txt);
						
						FormattedText formatedText = new FormattedText();
						formatedText.setEntityID(id);
						loadStatus = formatedText.entityLoad(connection);
						
						if (loadStatus)
							setDescription(formatedText);
					}
					if (loadStatus)
						setIsPreferred(rSet.getBoolean(cn_is_pre));
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

				if (storeStatus)
					storeStatus = getDescription().entityStore(connection);
				
				if (storeStatus) {
					sqlQuery = update+table_name+set
						+cn_for_txt	+equals+getDescription().getEntityID()+comma
						+cn_is_pre	+equals+getPreferrence()
					    +where+primary_key+equals+getEntityID();
						
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
					/* Extension Base */
					if (storeStatus)
						storeStatus = getDescription().entityStore(connection);
													
					if (storeStatus) {
						sqlQuery = insert_into+table_name+blank+"("
						+cn_for_txt+comma
						+cn_is_pre
						+")"+values+"("
						+getDescription().getEntityID()+comma
						+getPreferrence()+comma
						+")";
						
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
				sqlQuery = delete_from+table_name
					+where+primary_key+equals+getEntityID();
				
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

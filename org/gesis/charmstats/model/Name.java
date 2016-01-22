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
public class Name extends DBEntity {

	/*
	 *	Column names and other text constants
	 */
	public static final String table_name		= "names";
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
	public static final String cn_mul_lan_txt	= "mult_lang_text";
	public static final String cn_is_pre		= "is_preferred";
	
	/*
	 *	Fields
	 */
	private MultLangText	textual_content;
	private Boolean			is_preferred;
	
	/*
	 *	Constructor
	 */
	/**
	 * 
	 */
	public Name () {
		super();
		
		entity_type = EntityType.LABELS;
		
		setTextualContent(new String());
		setIsPreferred(false);
	}
	
	/**
	 * @param label
	 */
	public Name (String label) {
		this();
		
		this.setTextualContent(label);
	}
	
	/*
	 *	Methods
	 */
	/**
	 * @param name
	 */
	public void setTextualContent(String name) {
		this.textual_content = new MultLangText(name);
	}
	
	/**
	 * @param name
	 */
	public void setTextualContent(MultLangText name) {
		this.textual_content = name;
	}

	/**
	 * @return
	 */
	public MultLangText getTextualContent() {
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
					
				String sqlQuery = select+cn_mul_lan_txt+comma
					+cn_is_pre
					+from+table_name
					+where+primary_key+equals+getEntityID();
					
				rSet = stmt.executeQuery( sqlQuery );
					
				while (rSet.next()) {
					if (loadStatus) {
						int id = rSet.getInt(cn_mul_lan_txt);
						
						MultLangText multLangText = new MultLangText();
						multLangText.setEntityID(id);
						loadStatus = multLangText.entityLoad(connection);
						
						if (loadStatus)
							setTextualContent(multLangText);
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
					storeStatus = getTextualContent().entityStore(connection);
				
				if (storeStatus) {
					sqlQuery = update+table_name+set
						+cn_mul_lan_txt	+equals+getTextualContent().getEntityID()+comma
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
						storeStatus = getTextualContent().entityStore(connection);
													
					if (storeStatus) {
						sqlQuery = insert_into+table_name+blank+"("
						+cn_mul_lan_txt+comma
						+cn_is_pre
						+")"+values+"("
						+getTextualContent().getEntityID()+comma
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

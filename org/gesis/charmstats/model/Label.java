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
public class Label extends DBEntity {

	/*
	 *	Column names and other text constants
	 */
	public static final String table_name		= "labels";
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
	public static final String cn_loc_var		= "location_variant";
	public static final String cn_val_for_dat	= "valid_for_date";	
	public static final String cn_typ			= "type";
	public static final String cn_max_len		= "max_length";
	
	/*
	 *	Fields
	 */
	private MultLangText	textual_content;
	private Boolean			is_preferred;
	
	private String 			location_variant;	
	private String			valid_for_date;
	private String 			type;				
	private int				max_length;			
	
	/*
	 *	Constructor
	 */
	/**
	 * 
	 */
	public Label () {
		super();
		
		entity_type = EntityType.LABELS;
		
		setLabel(new String());
		setIsPreferred(false);
		
		setLocationVariant(new String());
		setValidForDate(new String());
		setType(new String());
		setMaxLength(0);
	}
	
	/**
	 * @param label
	 */
	public Label (String label) {
		this();
		
		this.setLabel(label);
	}
	
	/*
	 *	Methods
	 */
	/**
	 * @param label
	 */
	public void setLabel(String label) {
		this.textual_content = new MultLangText(label);
	}
	
	/**
	 * @param label
	 */
	public void setLabel(MultLangText label) {
		this.textual_content = label;
	}

	/**
	 * @return
	 */
	public MultLangText getLabel() {
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
	
	/**
	 * @param location_variant
	 */
	public void setLocationVariant(String location_variant) {
		this.location_variant = location_variant;
	}
	
	/**
	 * @return
	 */
	public String getLocationVariant() {
		return location_variant;
	}

	/**
	 * @param valid_for_date
	 */
	public void setValidForDate(String valid_for_date) {
		this.valid_for_date = valid_for_date;
	}

	/**
	 * @return
	 */
	public String getValidForDate() {
		return valid_for_date;
	}

	/**
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * @return
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param max_length
	 */
	public void setMaxLength(int max_length) {
		this.max_length = max_length;
	}
	
	/**
	 * @return
	 */
	public int getMaxLength() {
		return max_length;
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
					+cn_is_pre+comma
					+cn_loc_var+comma
					+cn_val_for_dat+comma	
					+cn_typ+comma
					+cn_max_len
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
							setLabel(multLangText);
					}
					if (loadStatus) {
						setIsPreferred(rSet.getBoolean(cn_is_pre));
						setLocationVariant(rSet.getString(cn_loc_var));
						setValidForDate(rSet.getString(cn_val_for_dat));
						setType(rSet.getString(cn_typ));
						setMaxLength(rSet.getInt(cn_max_len));
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

				if (storeStatus)
					storeStatus = getLabel().entityStore(connection);
				
				if (storeStatus) {
					sqlQuery = update+table_name+set
						+cn_mul_lan_txt	+equals+getLabel().getEntityID()+comma
						+cn_is_pre		+equals+getPreferrence()+comma
						+cn_loc_var		+equals+getLocationVariant()+comma
						+cn_val_for_dat	+equals+getValidForDate()+comma
						+cn_typ			+equals+getType()+comma
						+cn_max_len		+equals+getMaxLength()
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
						storeStatus = getLabel().entityStore(connection);
													
					if (storeStatus) {
						sqlQuery = insert_into+table_name+blank+"("
						+cn_mul_lan_txt+comma
						+cn_is_pre+comma
						+cn_loc_var+comma
						+cn_val_for_dat+comma
						+cn_typ+comma
						+cn_max_len	
						+")"+values+"("
						+getLabel().getEntityID()+comma
						+getPreferrence()+comma
						+getLocationVariant()+comma
						+getValidForDate()+comma
						+getType()+comma
						+getMaxLength()
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

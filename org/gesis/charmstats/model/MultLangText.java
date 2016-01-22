package org.gesis.charmstats.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import org.gesis.charmstats.persistence.DBEntity;
import org.gesis.charmstats.persistence.DBField;
import org.gesis.charmstats.persistence.EntityType;

/**
 *	This is an Entity-Class
 * 
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class MultLangText extends DBEntity {

	/*
	 *	Column Name and other Constants
	 */
	public static final String table_name		= "mult_lang_texts";
	public static final String primary_key		= "id";
	public static final String apostroph		= "'";
	public static final String blank			= " ";
	public static final String comma			= ",";
	public static final String equals			= "=";
	public static final String quote			= "\"";
	public static final String cn_txt_con		= "textual_content";
	public static final String cn_language		= "language";	
	public static final String cn_tra_lat_ed	= "translated";
	public static final String cn_tra_lat_abl	= "translatable";
	
	/*
	 *	Fields
	 */
	private	String		textual_content;	// xs:string
	
	private String		language;			// xml:lang
	private boolean		translated;			// xs:boolean
	private boolean		translatable;		// xs:boolean
	
	/*
	 *	Constructor
	 */
	/**
	 * 
	 */
	public MultLangText() {
		super();
		
		entity_type = EntityType.MULT_LANG_TEXTS;
		setEntityID(-1);
		
		setTranslated(false);
		setTranslatable(true);		
	}
	
	/**
	 * @param textual_content
	 */
	public MultLangText(String textual_content) {
		this();
		
		setTextualContent(textual_content);
	}
	
	/**
	 * @param pk
	 */
	public MultLangText(Integer pk) {
		this();
		
		setEntityID(pk);
	}
		
	/*
	 *	Methods
	 */
	/**
	 * @param textual_content
	 */
	public void setTextualContent(String textual_content) {
		this.textual_content = textual_content;
	}
	
	/**
	 * @return
	 */
	public String getTextualContent() {
		return textual_content;
	}

	/**
	 * @param language
	 */
	public void setLanguage(String language) {
		this.language = language;
	}
	
	/**
	 * @return
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param translated
	 */
	public void setTranslated(boolean translated) {
		this.translated = translated;
	}

	/**
	 * @return
	 */
	public boolean isTranslated() {
		return translated;
	}
		
	// use before mySQL insert / update only:
	/**
	 * @return
	 */
	public int getTranslated() {
		return (isTranslated() ? 1 : 0);
	}

	/**
	 * @param translatable
	 */
	public void setTranslatable(boolean translatable) {
		this.translatable = translatable;
	}

	/**
	 * @return
	 */
	public boolean isTranslatable() {
		return translatable;
	}
		
	// use before mySQL insert / update only:
	/**
	 * @return
	 */
	public int getTranslatable() {
		return (isTranslatable() ? 1 : 0);
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
			if(!connection.isClosed()) {
				stmt = connection.createStatement();
					
				String sqlQuery = "SELECT "+ cn_txt_con +","+ cn_language +","+ cn_tra_lat_ed +","+ cn_tra_lat_abl +
					" FROM "+ table_name +
					" WHERE "+ primary_key +" = "+ getEntityID();
					
				rSet = stmt.executeQuery(sqlQuery);
					
				while (rSet.next()) {
					setTextualContent(rSet.getString(cn_txt_con));
					setLanguage(rSet.getString(cn_language));
					setTranslated(rSet.getBoolean(cn_tra_lat_ed));
					setTranslatable(rSet.getBoolean(cn_tra_lat_abl));
					
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
				
				sqlQuery = "UPDATE "+ table_name +" SET "+ 
					cn_txt_con +" = '"+ DBField.MLT_TXT_CON.truncate(getTextualContent()) +"' "+
				    "WHERE "+ primary_key +" = "+ getEntityID();
				
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
					
					/*
					 *	Use Insert-Statement
					 */					
					if (storeStatus) {
						sqlQuery = "INSERT INTO "+ table_name +
							" ("+ cn_txt_con +","+ cn_language +","+ cn_tra_lat_ed +","+ cn_tra_lat_abl +
							") VALUES ("+
							"'"+ DBField.MLT_TXT_CON.truncate(getTextualContent()) +"',"+
							"'"+ getLanguage() +"',"+
							"'"+ getTranslated() +"',"+
							"'"+ getTranslatable() +"'"+
							")";
						
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
				sqlQuery = "DELETE from "+ table_name +
					"WHERE "+ primary_key +" = "+ getEntityID();
				
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

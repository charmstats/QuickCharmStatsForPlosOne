package org.gesis.charmstats.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;

import org.gesis.charmstats.persistence.DBEntity;
import org.gesis.charmstats.persistence.DBField;
import org.gesis.charmstats.persistence.EntityType;

/**
 * 
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class Keyword extends DBEntity {
	
	/* charmstats.keywords (MySQL)
		`id`				int(11)		 NOT NULL auto_increment,
		`keyword`			varchar(255) 		  default NULL,
		`lang_code`			varchar(20)			  default NULL,
		`lang_code_norm`	varchar(20)			  default NULL,
		`translatable`		tinyint(1)			  default '1',
		`translated`		tinyint(1)			  default '0',
		`translated_origin` int(11)				  default NULL,
	PRIMARY KEY  (`id`)
	*/
	
	/*
	 *	Column names and other text constants
	 */
	public static final String table_name		= "keywords";
	public static final String primary_key		= "id";
	public static final String blank			= " ";
	public static final String comma			= ",";
	public static final String equals			= "=";
	public static final String cn_key_id		= "id";
	public static final String cn_tra_ori		= "translation_origin";
	public static final String cn_tra			= "translation";
	public static final String cn_tra_abl		= "translatable";
	public static final String cn_lan_cod_nor	= "lang_code_norm";
	public static final String cn_lan_cod		= "lang_code";
	public static final String cn_key			= "keyword";
	public static final String cn_voc			= "vocabulary";
	
	/*
	 *	Fields
	 */
	private String	keyword;
	private String	vocabulary;
	
	private String	lang_code;
	private String	lang_code_norm;	
	private boolean translatable;
	private boolean translated;
	private Keyword	translated_origin;
	
	/*
	 *	Constructor
	 */
	/**
	 * 
	 */
	public Keyword () {
		super();
		
		entity_type = EntityType.KEYWORDS;
		
		setKeyword(new String());
		setVocabulary(new String());
		
		setLangCode(new String());
		setLangCodeNorm(new String());		
		setTranslatable(true);
		setTranslated(false);
		setTranslatedOrigin(null);
	}
	
	/**
	 * @param id
	 */
	public Keyword (int id) {
		this();
		
		setEntityID(id);
	}
	
	/**
	 * @param id
	 * @param keyword
	 */
	public Keyword (int id, String keyword) {
		this(id);
		
		setKeyword(keyword);
	}
	
	/*
	 *	Methods
	 */	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		if (getKeyword() != null)
			return getKeyword();
		
		return super.toString();
	}
	
	/* Keyword */
	/**
	 * @param keyword
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	/**
	 * @return
	 */
	public String getKeyword() {
		return keyword;
	}
	
	/* Vocabulary */
	/**
	 * @param keyword
	 */
	public void setVocabulary(String vocabulary) {
		this.vocabulary = vocabulary;
	}
	
	/**
	 * @return
	 */
	public String getVocabulary() {
		return vocabulary;
	}
	
	/* LangCode */
	/**
	 * @param lang_code
	 */
	public void setLangCode(String lang_code) {
		this.lang_code = lang_code;
	}
	
	/**
	 * @return
	 */
	public String getLangCode() {
		return lang_code;
	}
	
	/* LangCodeNorm */
	/**
	 * @param lang_code_norm
	 */
	public void setLangCodeNorm(String lang_code_norm) {
		this.lang_code_norm = lang_code_norm;
	}
	
	/**
	 * @return
	 */
	public String getLangCodeNorm() {
		return lang_code_norm;
	}
	
	/* Translatable */
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
	
	/* Translated */
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
	
	/* TranslatedOrigin */
	/**
	 * @param translated_origin
	 */
	public void setTranslatedOrigin(Keyword translated_origin) {
		this.translated_origin = translated_origin;
	}
	
	/**
	 * @return
	 */
	public Keyword getTranslatedOrigin() {
		return translated_origin;
	}
	
	/* Utility */
	/**
	 * @param keywords
	 * @return
	 */
	public boolean isFoundIn(ArrayList<Keyword> keywords) {
		
		if (keywords != null) {
			Iterator<Keyword> iterator = keywords.iterator();
							
			while(iterator.hasNext()) {
				Keyword key = iterator.next();
				
				if ((key.equals(this)))
					return true;
			}
		}
	
		return false;
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
					
				String sqlQuery = "SELECT" + blank + cn_key + comma + blank + cn_voc + comma + blank +
					cn_tra_ori + comma + blank +
					cn_tra + comma + blank +
					cn_tra_abl + comma + blank +
					cn_lan_cod_nor + comma + blank +
					cn_lan_cod + blank +
					"FROM" + blank + table_name + blank +
					"WHERE" + blank + primary_key + equals + "\"" + this.getEntityID() + "\"";
				
				rSet = stmt.executeQuery( sqlQuery );
					
				while (rSet.next()) {
					setTranslatedOrigin(new Keyword(rSet.getInt(Keyword.cn_tra_ori)));
					if (getTranslatedOrigin().getEntityID() > 0)
						getTranslatedOrigin().entityLoad(connection);
					setTranslated(rSet.getBoolean(Keyword.cn_tra));
					setTranslatable(rSet.getBoolean(Keyword.cn_tra_abl));
					setLangCodeNorm(rSet.getString(Keyword.cn_lan_cod_nor));
					setLangCode(rSet.getString(Keyword.cn_lan_cod));
					setKeyword(rSet.getString(Keyword.cn_key));
					setVocabulary(rSet.getString(Keyword.cn_voc));

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

		
		if (getEntityID() > 0) {
			/*
			 *	Update
			 */
			try {
				connection.setAutoCommit(false);
				
				sqlQuery = "UPDATE" + blank + table_name + blank + "SET" + blank
					+ cn_key + blank + equals + "'" + DBField.KEY_KEY.truncate(getKeyword()) + "'" + comma + blank
					+ cn_voc + blank + equals + "'" + DBField.KEY_VOC.truncate(getVocabulary()) + "'" + comma + blank
					+ (getTranslatedOrigin() != null ? 
							cn_tra_ori + blank + equals + "'" + getTranslatedOrigin().getEntityID() + "'" + comma + blank : "")
					+ cn_tra + blank + equals + "'" + (isTranslated()? 1:0) + "'" + comma + blank
					+ cn_tra_abl + blank + equals + "'" + (isTranslatable()? 1:0) + "'" + comma + blank
					+ cn_lan_cod_nor + blank + equals + "'" + DBField.KEY_LAN_COD_NOR.truncate(getLangCodeNorm()) + "'" + comma + blank
					+ cn_lan_cod + blank + equals + "'" + DBField.KEY_LAN_COD.truncate(getLangCode()) + "'" + blank
				    + "WHERE id = " + getEntityID();
				
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
					sqlQuery = "INSERT INTO keywords (keyword, translation_origin, translation, translatable, lang_code_norm, lang_code";
					sqlQuery += ") VALUES ("
						+ "'"	+ DBField.KEY_KEY.truncate(getKeyword()) + "',"
						+ "'"	+ DBField.KEY_VOC.truncate(getVocabulary()) + "',"
						+ (getTranslatedOrigin() != null ? 
							"'" + getTranslatedOrigin().getEntityID() + "'," : '0' + ",")
						+ (isTranslated() ? '1' : '0') + ","
						+ (isTranslatable() ? '1' : '0') + ","
						+ "'"	+ DBField.KEY_LAN_COD_NOR.truncate(getLangCodeNorm()) + "',"
						+ "'"	+ DBField.KEY_LAN_COD.truncate(getLangCode()) + "'";
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
			try {
				if (removeStatus) {
					KeywordRef ref = new KeywordRef();
					ref.setKeyword(this);
					
					removeStatus = ref.entityRemove(connection);
				}
					
				sqlQuery = "DELETE from keywords " +
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

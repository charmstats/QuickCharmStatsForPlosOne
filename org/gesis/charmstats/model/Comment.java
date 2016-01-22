package org.gesis.charmstats.model;

import java.sql.Connection;
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
public class Comment extends DBEntity {
	
	/* charmstats.comments (MySQL)
		`id`				int(11)				 NOT NULL auto_increment,
		`subject`			varchar(255)				  default NULL,
		`text`				varchar(2048)		 		  default NULL,
		`type`				int(11)				 		  default NULL,
		`object_entity` 	smallint(5) unsigned		  default NULL,
		`object_entry`		int(11)						  default NULL,
		`project`			int(11)						  default NULL,
		`lang_code`			varchar(20)					  default NULL,
		`lang_code_norm`	varchar(20)					  default NULL,
		`translatable`		tinyint(1)					  default '1',
		`translated`		tinyint(1)					  default '0',
		`translated_origin` int(11)						  default NULL,
		PRIMARY KEY  (`id`)
	*/
	
	public static final String TABLE = "comments";
	public static final String PRIMARY_KEY = "id";
	
	public static final int MAX_SIZE = 255;
	
	/*
	 *	Fields
	 */
	private String		subject;
	private String		text;
	private CommentType type;
	
	private Object 		reference;	
	
	private String		lang_code;
	private String		lang_code_norm;	
	private boolean 	translatable;
	private boolean 	translated;
	private Comment		translated_origin;
	
	/**
	 *	Constructor 
	 */
	public Comment () {
		super();
		
		entity_type = EntityType.COMMENTS;
		
		setSubject(new String());
		setText(new String());
		setType(CommentType.NONE);
	
		setReference(this); // Dummy
		
		setLangCode(new String());
		setLangCodeNorm(new String());	
		setTranslatable(true);
		setTranslated(false);
		setTranslatedOrigin(null);
	}
	
	/*
	 *	Methods
	 */
	/**
	 * @param subject
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	/**
	 * @return
	 */
	public String getSubject() {
		return subject;
	}
	
	/**
	 * @param text
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * @return
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * @param type
	 */
	public void setType(CommentType type) {
		this.type = type;
	}
	
	/**
	 * @return
	 */
	public CommentType getType() {
		return type;
	}
	
	/**
	 * @param reference
	 */
	public void setReference(Object reference) {
		this.reference = reference;
	}
	
	/**
	 * @return
	 */
	public Object getReference() {
		return reference;
	}
	
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
	
	/**
	 * @param translated_origin
	 */
	public void setTranslatedOrigin(Comment translated_origin) {
		this.translated_origin = translated_origin;
	}
	
	/**
	 * @return
	 */
	public Comment getTranslatedOrigin() {
		return translated_origin;
	}

    /**
     * @param commentText
     * @return
     */
    @SuppressWarnings("unused")
	private String resize(String commentText) {
		return commentText.substring(0, java.lang.Math.min(this.getText().length(), MAX_SIZE));
	}
    
    /**
     * @param string
     * @param maxlength
     * @return
     */
    @SuppressWarnings("unused")
	private String truncate(String string, int maxlength ) {
    	return  string.substring(0, java.lang.Math.min(string.length(), maxlength));
    }
	
	/*
	 *	Load and Store to DB
	 */
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.persistence.DBEntity#entityLoad(java.sql.Connection)
	 */
	public boolean entityLoad(Connection connection) {

		boolean loadStatus = false;
		
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
					
				String sqlQuery = "SELECT text " +
					"FROM comments " +
					"WHERE id=\"" + this.getEntityID() + "\"";
					
				rsltst = stmt.executeQuery( sqlQuery );
					
				while (rsltst.next()) {
					this.setText(rsltst.getString("text"));

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
				
				sqlQuery = "UPDATE comments SET "
					+ "text = ?, "
					+ "object_entity = ?, "
					+ "object_entry = ? "
					+ "WHERE id = ?";
				
				prepStmt = connection.prepareStatement(sqlQuery);
				prepStmt.setString(1, DBField.CMT_TXT.truncate(this.getText()));
				prepStmt.setInt(2, ((DBEntity)getReference()).getEntityType().getID());
				prepStmt.setInt(3, ((DBEntity)getReference()).getEntityID());
				prepStmt.setInt(4, getEntityID());
				
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
					sqlQuery = "INSERT INTO comments (text, object_entity, object_entry";
					sqlQuery += ") VALUES ("
						+ "?, ?, ?";
					sqlQuery += ")";
					
					prepStmt = connection.prepareStatement(sqlQuery);
					prepStmt.setString(1, DBField.CMT_TXT.truncate(this.getText()));
					prepStmt.setInt(2, ((DBEntity)getReference()).getEntityType().getID());
					prepStmt.setInt(3, ((DBEntity)getReference()).getEntityID());
					
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
    
}

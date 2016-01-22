package org.gesis.charmstats.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;

import org.gesis.charmstats.persistence.DBEntity;
import org.gesis.charmstats.persistence.DBField;
import org.gesis.charmstats.persistence.EntityType;
import org.gesis.charmstats.persistence.RefRelationtype;

/**
 *	This is an Entity-Class
 * 
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class Literature extends DBEntity {
	
	/* charmstats.literatures (MySQL)
		`id`				int(11)		 NOT NULL auto_increment,
		`title`				varchar(255)		  default NULL,
		`publisher`			varchar(255)		  default NULL,
		`date`				date				  default NULL,
		`other`				varchar(255)		  default NULL,
		`issue`				varchar(255)		  default NULL,
		`place`				varchar(255)		  default NULL,
		`source`			varchar(255)		  default NULL,
		`pages`				varchar(255)		  default NULL,
		`webaddress`		varchar(255)		  default NULL,
		`author_et_al`		tinyint(1)			  default '0',
		`editor_et_al`		tinyint(1)			  default '0', 
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
	public static final String table_name		= "literatures";
	public static final String primary_key		= "id";
	public static final String blank			= " ";
	public static final String comma			= ",";
	public static final String equals			= "=";
	public static final String cn_tra_ori		= "translated_origin";
	public static final String cn_tra			= "translated";
	public static final String cn_tra_abl		= "translatable";
	public static final String cn_lan_cod_nor	= "lang_code_norm";
	public static final String cn_lan_cod		= "lang_code";
	
	/*
	 *	Fields
	 */
	private ArrayList<Person>	authors		= new ArrayList<Person>();
	private boolean				authorEtAl;
	private Date				date;
	private String				title;
	private String				source;
	private ArrayList<Person>	editors		= new ArrayList<Person>();
	private boolean				editorEtAl;
	private String				publisher;
	private String				place;
	private String				issue;
	private String				pages;
	private String				webaddress;	
	
	private String				other;
	
	private String				lang_code;
	private String				lang_code_norm;	
	private boolean 			translatable;
	private boolean 			translated;
	private Literature			translated_origin;
	
	/*
	 *	Constructor
	 */
	/**
	 * 
	 */
	public Literature () {
		super();
		
		entity_type = EntityType.LITERATURES;
	}
	
	/**
	 * @param id
	 */
	public Literature (int id) {
		this();
		
		setEntityID(id);
	}

	/*
	 *	Methods
	 */
	/* Author */
	/**
	 * @param authors
	 */
	public void setAuthors(ArrayList<Person> authors) {
		this.authors = authors;
	}
	
	/**
	 * @param author
	 */
	public void addAuthor(Person author) {
		authors.add(author);
	}
	
	/**
	 * @return
	 */
	public ArrayList<Person> getAuthors() {
		return authors;
	}
	
	/**
	 * @param index
	 * @return
	 */
	public Person getAuthorByIndex(int index) {
		Person result = null;
		
		try {
			result = authors.get(index);
		} catch (IndexOutOfBoundsException e) {
	    	/* DEMO ONLY */
	    	System.err.println("SQLException: " + e.getMessage());
	    	e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * @param author_id
	 * @return
	 */
	public Person getAuthorByID(int author_id) {
		Person result = null;
		
		Iterator<Person> it_author = authors.iterator();
			
		while (it_author.hasNext()) {
			Person author = it_author.next();
			
			if (author.getEntityID() == author_id) {
				result = author; break;
			}
		}
		
		return result;
	}
	
	/* Author et al*/
	/**
	 * @param authorEtAl
	 */
	public void setAuthorEtAl(boolean authorEtAl) {
		this.authorEtAl = authorEtAl;
	}
	
	// mySQL only:
	/**
	 * @param authorEtAl
	 */
	public void setAuthorEtAl(int authorEtAl) {
		this.authorEtAl = (authorEtAl > 0);
	}
	
	/**
	 * @return
	 */
	public boolean hasAuthorEtAl() {
		return authorEtAl;
	}
	
	// mySQL only:
	/**
	 * @return
	 */
	public int getAuthorEtAl() {
		return (authorEtAl ? 1 : 0);
	}
	
	/* Date */
	/**
	 * @param date
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	
	/**
	 * @return
	 */
	public Date getDate() {
		return date;
	}
	
	/* Title */
	/**
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * @return
	 */
	public String getTitle() {
		return title;
	}
	
	/* Source */
	/**
	 * @param source
	 */
	public void setSource(String source) {
		this.source = source;
	}
	
	/**
	 * @return
	 */
	public String getSource() {
		return source;
	}
	
	/* Editors */
	/**
	 * @param newEditors
	 */
	public void setEditors(ArrayList<Person> newEditors) {
		this.editors = newEditors;
	} 
	
	/**
	 * @param editor
	 */
	public void addEditor(Person editor) {
		editors.add(editor);
	}
	
	/**
	 * @return
	 */
	public ArrayList<Person> getEditors() {
		return editors;
	}
	
	/**
	 * @param index
	 * @return
	 */
	public Person getEditorByIndex(int index) {
		Person result = null;
		
		try {
			result = editors.get(index);
		} catch (IndexOutOfBoundsException e) {
	    	/* DEMO ONLY */
	    	System.err.println("SQLException: " + e.getMessage());
	    	e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * @param author_id
	 * @return
	 */
	public Person getEditorByID(int author_id) {
		Person result = null;
		
		Iterator<Person> it_editor = editors.iterator();
			
		while (it_editor.hasNext()) {
			Person editor = it_editor.next();
			
			if (editor.getEntityID() == author_id) {
				result = editor; break;
			}
		}
		
		return result;
	}
	
	/* Editor et al*/
	/**
	 * @param editorEtAl
	 */
	public void setEditorEtAl(boolean editorEtAl) {
		this.editorEtAl = editorEtAl;
	}
	
	// mySQL only:
	/**
	 * @param editorEtAl
	 */
	public void setEditorEtAl(int editorEtAl) {
		this.editorEtAl = (editorEtAl > 0);
	}
	
	/**
	 * @return
	 */
	public boolean hasEditorEtAl() {
		return editorEtAl;
	}
	
	// mySQL only:
	/**
	 * @return
	 */
	public int getEditorEtAl() {
		return (editorEtAl ? 1 : 0);
	}
	
	/* Publisher */
	/**
	 * @param publisher
	 */
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
	/**
	 * @return
	 */
	public String getPublisher() {
		return publisher;
	}
	
	/* Place */
	/**
	 * @param place
	 */
	public void setPlace(String place) {
		this.place = place;
	}
	
	/**
	 * @return
	 */
	public String getPlace() {
		return place;
	}
	
	/* Issue, former Edition */
	/**
	 * @param edition
	 */
	public void setIssue(String edition) {
		this.issue = edition;
	}
	
	/**
	 * @return
	 */
	public String getIssue() {
		return issue;
	}

	/* Pages */
	/**
	 * @param pages
	 */
	public void setPages(String pages) {
		this.pages = pages;
	}
	
	/**
	 * @return
	 */
	public String getPages() {
		return pages;
	}
	
	/* Webaddress */
	/**
	 * @param webaddress
	 */
	public void setWebaddress(String webaddress) {
		this.webaddress = webaddress;		
	}
	
	/**
	 * @return
	 */
	public String getWebaddress() {		
		return webaddress;
	}

	/* Other, now deprecated */
	/**
	 * @param other
	 */
	public void setOther(String other) {
		this.other = other;
	}
	
	/**
	 * @return
	 */
	public String getOther() {
		return other;
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
	public void setTranslatedOrigin(Literature translated_origin) {
		this.translated_origin = translated_origin;
	}
	
	/**
	 * @return
	 */
	public Literature getTranslatedOrigin() {
		return translated_origin;
	}
	
	/* Utility */
	/* Without a title, a literature will not be stored! 20.04.2012, MF */
	/**
	 * @return
	 */
	public boolean notEmpty() {
		return (title.length() > 0);
	}
	
	/*
	 *	Load and Store to DB
	 */
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.persistence.DBEntity#entityLoad(java.sql.Connection)
	 */
	public boolean entityLoad(Connection connection) {

		boolean				loadStatus	= false;
		java.sql.Statement	stmt;
		ResultSet			rSet;
		
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
					
				/* Load Authors */
				PersonRef aut_ref = new PersonRef();
				aut_ref.setRole(PersonRole.AUTHOR);
				aut_ref.setReferenced(this);
						
				ArrayList<Integer> aut_list = aut_ref.loadReferences(connection);
				Iterator<Integer> aut_iterator = aut_list.iterator();
					
				while (aut_iterator.hasNext()) {
					int index = aut_iterator.next();
						
					Person author = new Person();
					author.setEntityID(index);
					author.entityLoad(connection);
						
					if (author != null)
						this.addAuthor(author);					
				}
				
				/* Load Editors */
				PersonRef edi_ref = new PersonRef();
				edi_ref.setRole(PersonRole.EDITOR);
				edi_ref.setReferenced(this);
						
				ArrayList<Integer> edi_list = edi_ref.loadReferences(connection);
				Iterator<Integer> edi_iterator = edi_list.iterator();
					
				while (edi_iterator.hasNext()) {
					int index = edi_iterator.next();
						
					Person editor = new Person();
					editor.setEntityID(index);
					editor.entityLoad(connection);
						
					if (editor != null)
						this.addEditor(editor);					
				}
				
				/* Load Literature */
				String sqlQuery = "SELECT author_et_al, date, title, source, editor_et_al, publisher, place, issue, pages, webaddress, " +
					cn_tra_ori + comma + blank +
					cn_tra + comma + blank +
					cn_tra_abl + comma + blank +
					cn_lan_cod_nor + comma + blank +
					cn_lan_cod + blank +
					"FROM literatures " +
					"WHERE id=\"" + this.getEntityID() +"\"";
				
				rSet = stmt.executeQuery( sqlQuery );
					
				while (rSet.next()) {
					setAuthorEtAl(rSet.getBoolean("author_et_al"));
					date		= rSet.getDate("date");
					title		= rSet.getString("title");
					source		= rSet.getString("source");
					setEditorEtAl(rSet.getBoolean("editor_et_al"));
					publisher	= rSet.getString("publisher");
					place		= rSet.getString("place"); 
					issue		= rSet.getString("issue"); 
					pages		= rSet.getString("pages");
					webaddress	= rSet.getString("webaddress");
					setTranslated(rSet.getBoolean(cn_tra));
					setTranslatable(rSet.getBoolean(cn_tra_abl));
					setLangCode(rSet.getString(cn_lan_cod));
					setLangCodeNorm(rSet.getString(cn_lan_cod_nor));
					
					loadStatus = true;
					
					int ds_tra_ori = rSet.getInt(cn_tra_ori);
					Literature tra_ori = null;
					if (ds_tra_ori > 0) {
						tra_ori = new Literature(ds_tra_ori);
						loadStatus = tra_ori.entityLoad(connection);
					}
					setTranslatedOrigin(tra_ori);
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
				
				/* Store Literature */
				sqlQuery = "UPDATE literatures SET "
					+ "author_et_al = ?, "
					+ "date = ?, "
					+ "title = ?, "
					+ "source = ?, "
					+ "editor_et_al = ?, "
					+ "publisher = ?, "
					+ "place = ?, "
					+ "issue = ?, " 
 					+ "pages = ?, "
					+ "webaddress = ? "
				    + "WHERE id = ?";					
				
				prepStmt = connection.prepareStatement(sqlQuery);
				prepStmt.setInt(1, this.getAuthorEtAl());
				if (getDate()  != null)
					prepStmt.setDate(2, new java.sql.Date(getDate().getTime()));
				else
					prepStmt.setNull(2, java.sql.Types.DATE);
				prepStmt.setString(3, DBField.LIT_TIT.truncate(this.getTitle()));
				prepStmt.setString(4, DBField.LIT_SOU.truncate(this.getSource()));
				prepStmt.setInt(5, this.getEditorEtAl());
				prepStmt.setString(6, DBField.LIT_PUB.truncate(this.getPublisher()));
				prepStmt.setString(7, DBField.LIT_PLA.truncate(this.getPlace()));
				prepStmt.setString(8, DBField.LIT_ISS.truncate(this.getIssue()));
				prepStmt.setString(9, DBField.LIT_PAG.truncate(this.getPages()));
				prepStmt.setString(10, DBField.LIT_WEB.truncate(this.getWebaddress()));
				prepStmt.setInt(11, this.getEntityID());
				
				rows = prepStmt.executeUpdate();
				
				if (rows < 1)				
					storeStatus = false;
				
				prepStmt.close();
					
				/* Store Authors */
				if (storeStatus) {
					/* Delete References first */
					PersonRef ref = new PersonRef(); 
					ref.setRole(PersonRole.AUTHOR);					
					ref.setReferenced(this);
					
					ref.entitiesRemove(connection);
					
					/* Store thereafter */
					Iterator<Person> iterator = authors.iterator();			
					while(iterator.hasNext()) {
						Person author = iterator.next();

						if (storeStatus) {
							PersonRef authorRef = new PersonRef(); 
							authorRef.setRole(PersonRole.AUTHOR);
							
							authorRef.setPerson(author);
							authorRef.setReferenced(this);
							authorRef.setRefRelationtype(RefRelationtype.REFERENCED);
							
							storeStatus = authorRef.entityStore(connection);
						}
					}
				}
				
				/* Store Editors */
				if (storeStatus) {
					/* Delete References first */
					PersonRef ref = new PersonRef();
					ref.setRole(PersonRole.EDITOR);
					ref.setReferenced(this);
					
					ref.entitiesRemove(connection);
					
					/* Store thereafter */
					Iterator<Person> iterator = editors.iterator();			
					while(iterator.hasNext()) {
						Person editor = iterator.next();

						if (storeStatus) {
							PersonRef editorRef = new PersonRef();
							editorRef.setRole(PersonRole.EDITOR);
							
							editorRef.setPerson(editor);
							editorRef.setReferenced(this);
							editorRef.setRefRelationtype(RefRelationtype.REFERENCED);
							
							storeStatus = editorRef.entityStore(connection);
						}
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
			
		} else {
			/*
			 *	Insert
			 */
			try {
				connection.setAutoCommit(false);
				
				/* Store Literature */
				if (storeStatus) {
					/*
					 *	Use Insert-Statement
					 */					
					sqlQuery = "INSERT INTO literatures (author_et_al, date, title, source, editor_et_al, publisher, place, issue, pages, webaddress";
					sqlQuery += ") VALUES ("
						+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?";
					sqlQuery += ")";
					
					prepStmt = connection.prepareStatement(sqlQuery);
					prepStmt.setInt(1, this.getAuthorEtAl());
					if (getDate()  != null)
						prepStmt.setDate(2, new java.sql.Date(getDate().getTime()));
					else
						prepStmt.setNull(2, java.sql.Types.DATE);
					prepStmt.setString(3, DBField.LIT_TIT.truncate(this.getTitle()));
					prepStmt.setString(4, DBField.LIT_SOU.truncate(this.getSource()));
					prepStmt.setInt(5, this.getEditorEtAl());
					prepStmt.setString(6, DBField.LIT_PUB.truncate(this.getPublisher()));
					prepStmt.setString(7, DBField.LIT_PLA.truncate(this.getPlace()));
					prepStmt.setString(8, DBField.LIT_ISS.truncate(this.getIssue()));
					prepStmt.setString(9, DBField.LIT_PAG.truncate(this.getPages()));
					prepStmt.setString(10, DBField.LIT_WEB.truncate(this.getWebaddress()));
					
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
				
				/* Store Authors */
				if (storeStatus) {
					/* Delete References first */
					PersonRef ref = new PersonRef();
					ref.setRole(PersonRole.AUTHOR);
					ref.setReferenced(this);
					
					ref.entitiesRemove(connection);
					
					/* Store thereafter */
					Iterator<Person> iterator = authors.iterator();			
					while(iterator.hasNext()) {
						Person author = iterator.next();
	
						if (storeStatus) {
							PersonRef authorRef = new PersonRef();
							authorRef.setRole(PersonRole.AUTHOR);
							
							authorRef.setPerson(author);
							authorRef.setReferenced(this);
							authorRef.setRefRelationtype(RefRelationtype.REFERENCED);
							
							storeStatus = authorRef.entityStore(connection);
						}
					}
				}
				
				/* Store Editors */
				if (storeStatus) {
					/* Delete References first */
					PersonRef ref = new PersonRef();
					ref.setRole(PersonRole.EDITOR);					
					ref.setReferenced(this);
					
					ref.entitiesRemove(connection);
					
					/* Store thereafter */
					Iterator<Person> iterator = editors.iterator();			
					while(iterator.hasNext()) {
						Person editor = iterator.next();
	
						if (storeStatus) {
							PersonRef editorRef = new PersonRef();
							editorRef.setRole(PersonRole.EDITOR);
							
							editorRef.setPerson(editor);
							editorRef.setReferenced(this);
							editorRef.setRefRelationtype(RefRelationtype.REFERENCED);
							
							storeStatus = editorRef.entityStore(connection);
						}
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
}

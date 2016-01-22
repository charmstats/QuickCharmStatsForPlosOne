package org.gesis.charmstats.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

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
public class OperaIndicator extends Attributes {
	
	/* charmstats.opera_indicators (MySQL)
		`id`		  int(11)	   NOT NULL auto_increment,
		`label`		  varchar(255) 			default NULL,
		`level`		  int(11)				default NULL,
		`measure`	  int(11)				default NULL,
		PRIMARY KEY  (`id`)
	*/
	
	public static final String COMMENT = "Comment";
	public static final String NO_COMMENT = "No Comment";
	public static final String ID = "ID";
	public static final String LABEL = "Label";
	public static final String LEVEL = "Level";
	public static final String NO_LEVEL = "No Level";	
	public static final String MEASURE_TYPE = "Measure";
	public static final String NO_MEASURE_TYPE = "No Measure";	
	public static final String VALUES = "Value(s)";
	public static final String NO_VALUES = "No Value(s)";
	public static final String QUESTIONS = "Question(s)";
	public static final String NO_QUESTIONS = "No Question(s)";
	public static final String EMPTY = "";

	/*
	 *	Fields
	 */
	private String 				label;
	
	public  TypeOfData			level;
	public  MeasurementLevel	type;
	private Definition 			definition;	
	private Comment 			comment;
	private ArrayList<Keyword>	keywords;
	
	private ArrayList<Question>	questions;
	
	/*
	 *	Constructor
	 */
	/**
	 * 
	 */
	public OperaIndicator() {
		super ();
		
		entity_type = EntityType.OPERA_INDICATORS;
		
		setLabel(new String());
		
		setLevel(TypeOfData.NONE);
		setType(MeasurementLevel.NONE);
		setDefinition(new Definition());
		getDefinition().setReference(this);
		setComment(new Comment());
		getComment().setReference(this);
		setKeywords(new ArrayList<Keyword>());
		
		setQuestions(new ArrayList<Question>());
	}
	
	/**
	 * @param id
	 */
	public OperaIndicator(int id) {
		this ();
		
		setEntityID(id);
	}
	
	/**
	 * @param id
	 * @param label
	 */
	public OperaIndicator(int id, String label) {
		this (id);
		
		setLabel(label); 
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

	/* Level */
	/**
	 * @param level
	 */
	public void setLevel(TypeOfData level) {
		this.level = level;
	}

	/**
	 * @return
	 */
	public TypeOfData getLevel() {
		return level;
	}
	
	/* Type */
	/**
	 * @param measureLevel
	 */
	public void setType(MeasurementLevel measureLevel) {
		this.type = measureLevel;
	}

	/**
	 * @return
	 */
	public MeasurementLevel getType() {
		return type;
	}
	
	/* Definition */
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.model.Attributes#setDefinition(org.gesis.charmstats.model.Definition)
	 */
	public void setDefinition(Definition definition) {
		this.definition = definition;
	}
	
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.model.Attributes#getDefinition()
	 */
	public Definition getDefinition() {
		return definition;
	}

	/* Comment */
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.model.Attributes#setComment(org.gesis.charmstats.model.Comment)
	 */
	public void setComment(Comment comment) {
		this.comment = comment;
	}

	/* (non-Javadoc)
	 * @see org.gesis.charmstats.model.Attributes#getComment()
	 */
	public Comment getComment() {
		return comment;
	}

	/* Keyword */
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.model.Attributes#setKeyword(org.gesis.charmstats.model.Keyword)
	 */
	public void setKeyword(Keyword keyword) {
		keywords.clear();
		keywords.add(keyword);
	}
	
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.model.Attributes#getKeyword()
	 */
	public Keyword getKeyword() {
		return ((keywords.size() < 1) ? null : keywords.get(0));
	}
	
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.model.Attributes#setKeywords(java.util.ArrayList)
	 */
	public void setKeywords(ArrayList<Keyword> keywords) {
		this.keywords = keywords;
	}
	
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.model.Attributes#addKeyword(org.gesis.charmstats.model.Keyword)
	 */
	public void addKeyword(Keyword keyword) {
		this.keywords.add(keyword);
	}

	/* (non-Javadoc)
	 * @see org.gesis.charmstats.model.Attributes#getKeywords()
	 */
	public ArrayList<Keyword> getKeywords() {
		return keywords;
	}
	
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.model.Attributes#hasKeyword(org.gesis.charmstats.model.Keyword)
	 */
	public boolean hasKeyword(Keyword argValue) {
		
		Iterator<Keyword> iterator = keywords.iterator();
		while(iterator.hasNext()) {
			Keyword key = iterator.next();
			
			if ( (key != null) &&
					(key.equals(argValue))) {
				return true;
			}
		}
		
		return false;
	}
	
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.model.Attributes#getKeywordByIndex(int)
	 */
	public Keyword getKeywordByIndex(int index) {
		return this.keywords.get(index);
	}
	
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.model.Attributes#getKeywordByID(int)
	 */
	public Keyword getKeywordByID(int id) {
		
		Iterator<Keyword> iterator = keywords.iterator();
		while(iterator.hasNext()) {
			Keyword keyword = iterator.next();
			
			if ((keyword != null) &&
					(keyword.getEntityID() == id)) {
				return keyword;
			}
		}
	
		return null;
	}
	
	/* Question */
	/**
	 * @param questions
	 */
	public void setQuestions(ArrayList<Question> questions) {
		this.questions = questions;
	}

	/**
	 * @return
	 */
	public ArrayList<Question> getQuestions() {
		return questions;
	}

	/**
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Vector getValuesTable() {
		return new Vector(); // TODO should return list of Prescriptions, unimplemented while unused
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
					
				String sqlQuery = "SELECT label, level, measure " +
					"FROM opera_indicators " +
					"WHERE id=\"" + this.getEntityID() + "\"";
					
				rSet = stmt.executeQuery( sqlQuery );
					
				while (rSet.next()) {
					this.setLabel(rSet.getString("label"));					
					this.setLevel(TypeOfData.getItem(rSet.getInt("level")));
					this.setType(MeasurementLevel.getItem(rSet.getInt("measure")));

					loadStatus = true;
				}
				
				/* Handle Comment */
				sqlQuery = "SELECT c.id " +
						"FROM comments c " +
						"WHERE c.object_entity=\"" + this.getEntityType().getID() +"\" " +
							"AND c.object_entry=\"" + this.getEntityID() + "\" ";
				
				rSet = stmt.executeQuery( sqlQuery );
				
				while (rSet.next()) {
					setComment(new Comment());
					getComment().setEntityID(rSet.getInt("id"));				
					if (loadStatus)
						loadStatus = getComment().entityLoad(connection);
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
		
		if (loadStatus) {
			KeywordRef ref = new KeywordRef();
			ref.setReferenced(this);
					
			ArrayList<Integer> list = ref.loadReferences(connection);
			Iterator<Integer> iterator_i = list.iterator();
				
			while (iterator_i.hasNext()) {
				int index = iterator_i.next();
					
				Keyword key = new Keyword();
				key.setEntityID(index);
				key.entityLoad(connection);
					
				if ((key != null) && (!this.hasKeyword(key))) {
					this.addKeyword(key);
				}
			}			
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

		
		if (this.getEntityID() > 0) {
			/*
			 *	Update
			 */
			try {
				connection.setAutoCommit(false);
				
				storeStatus = definition.entityStore(connection);
				
				storeStatus = comment.entityStore(connection);
				
				sqlQuery = "UPDATE opera_indicators SET " 
					+ "label = ?, "
					+ "level = ?, "
					+ "measure = ? "
					+ "WHERE id = ?";
				
				prepStmt = connection.prepareStatement(sqlQuery);
				prepStmt.setString(1, DBField.IND_LAB.truncate(this.getLabel()));
				prepStmt.setInt(2, this.getLevel().getID());
				prepStmt.setInt(3, this.getType().getID());
				prepStmt.setInt(4, this.getEntityID());
				
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
					sqlQuery = "INSERT INTO opera_indicators (label, level, measure";
					sqlQuery += ") VALUES ("
						+ "?, ?, ?";
					sqlQuery += ")";
					
					prepStmt = connection.prepareStatement(sqlQuery);
					prepStmt.setString(1, DBField.IND_LAB.truncate(this.getLabel()));
					prepStmt.setInt(2, this.getLevel().getID());
					prepStmt.setInt(3, this.getType().getID());
					
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
				
				if (storeStatus)
					storeStatus = comment.entityStore(connection);
					
				if (storeStatus)
					storeStatus = definition.entityStore(connection);
									
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
			if (removeStatus)
				if (definition != null)
					removeStatus = definition.entityRemove(connection);
			
			if (removeStatus)
				if (comment != null)
					removeStatus = comment.entityRemove(connection);
			
			if (removeStatus)
				if (keywords != null) {
					Iterator<Keyword> iterator_k = keywords.iterator();
						
					while (iterator_k.hasNext()) {
						Keyword key = iterator_k.next();
						
						if (removeStatus)
							removeStatus = key.entityRemove(connection);
					}			
				}
			
			try {
				sqlQuery = "DELETE from opera_indicators " +
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
    
    /**
     * @param id
     * @return
     */
    public static String getDraftSQLForProject(int id) {
    	String retString;
    	
		retString =
			"select distinct p.name " +
			"from projects p, instance_links il, attribute_links al, opera_indicators oi " +
			"where p.id = il.project " +
			"and il.instance = al.instance " + 
			"and al.attr_entity = 720 " + 
			"and al.attr_entry = " + id;
		
		return retString;
    }
    
	/**
	 * @param proType
	 * @param meaLevel
	 * @return
	 */
	public static String getDraftSQLForAllProjectsThroughLabel(int proType, int meaLevel) {
		String retString;
		
		retString =
			"(select distinct p.id " +
			"from projects p, instance_links il, attribute_links al, opera_indicators oi " +
			"where p.published_since is not null " +
			"and p.id = il.project ";
			
		if (proType > 0)
			retString +=
				"and p.type = " + proType + " ";
		
		if (meaLevel > 0)
			retString +=
				"and oi.level = " + meaLevel + " ";
			
		retString +=
			"and il.instance = al.instance " + 
			"and al.attr_entity = 720 " + 
			"and al.attr_entry = oi.id " + 
			"and oi.label REGEXP '<TEXT>' " + 
			") ";
		
		return retString;
	}
	
	/**
	 * @param proType
	 * @param meaLevel
	 * @return
	 */
	public static String getDraftSQLForAllProjectsThroughDefinition(int proType, int meaLevel) {
		String retString;
		
		retString =
			"(select distinct p.id " + 
			"from projects p, instance_links il, attribute_links al, opera_indicators oi, definitions d " + 
			"where p.published_since is not null " +
			"and p.id = il.project ";
			
		if (proType > 0)
			retString +=
				"and p.type = " + proType + " ";
		
		if (meaLevel > 0)
			retString +=
				"and oi.level = " + meaLevel + " ";
			
		retString += 
			"and il.instance = al.instance " + 
			"and al.attr_entity = 720 " + 
			"and al.attr_entry = oi.id " + 
			"and oi.id = d.object_entry " +
			"and d.object_entity = 720 " +
			"and d.text REGEXP '<TEXT>' " + 
			") ";
		
		return retString;
	}
	
	/**
	 * @param proType
	 * @param meaLevel
	 * @return
	 */
	public static String getDraftSQLForAllProjectsThroughComment(int proType, int meaLevel) {
		String retString;
		
		retString =
			"(select distinct p.id " + 
			"from projects p, instance_links il, attribute_links al, opera_indicators oi, comments c " + 
			"where p.published_since is not null " +
			"and p.id = il.project ";
			
		if (proType > 0)
			retString +=
				"and p.type = " + proType + " ";
		
		if (meaLevel > 0)
			retString +=
				"and oi.level = " + meaLevel + " ";
			
		retString += 
			"and il.instance = al.instance " + 
			"and al.attr_entity = 720 " + 
			"and al.attr_entry = oi.id " + 
			"and oi.id = c.object_entry " +
			"and c.object_entity = 720 " +
			"and c.text REGEXP '<TEXT>' "; 
		
		return retString;
	}
	
	/**
	 * @param proType
	 * @param meaLevel
	 * @return
	 */
	public static String getDraftSQLForAllProjectsThroughKeyword(int proType, int meaLevel) {
		String retString;
		
		retString =
			"(select distinct p.id " + 
			"from projects p, instance_links il, attribute_links al, opera_indicators oi, keyword_refs kr, keywords k " + 
			"where p.published_since is not null " +
			"and p.id = il.project ";
			
		if (proType > 0)
			retString +=
				"and p.type = " + proType + " ";
		
		if (meaLevel > 0)
			retString +=
				"and oi.level = " + meaLevel + " ";
			
		retString += 
			"and il.instance = al.instance " + 
			"and al.attr_entity = 720 " + 
			"and al.attr_entry_id = oi.id " +
			"and oi.id = kr.entry " +
			"and kr.entity = 720 " + 
			"and kr.keyword = k.id " + 
			"and k.keyword REGEXP '<TEXT>' " + 
			") ";
		
		return retString;
	}
		
	/**
	 * @param meaLevel
	 * @return
	 */
	public static String getDraftSQLForIndicatorLabel(int meaLevel) {
		String retString;
		
		retString =
			"(select distinct oi.id " +
			"from opera_indicators oi, attribute_links al, instance_links il, projects p " +
			"where oi.label REGEXP '<TEXT>' ";
			
		if (meaLevel > 0)
			retString +=
				"and oi.level = " + meaLevel + " ";
		
		retString +=
			"and oi.id = al.attr_entry " +
			"and al.attr_entity = 720 " +
			"and al.instance = il.instance " +
			"and il.project = p.id " + 
			"and p.published_since is not null) ";
		
		return retString;
	}
	
	/**
	 * @param meaLevel
	 * @return
	 */
	public static String getDraftSQLForIndicatorDefinition(int meaLevel) {
		String retString;
		
		retString =
			"(select distinct oi.id " +
			"from opera_indicators oi, definitions d, attribute_links al, instance_links il, projects p " +
			"where oi.id = d.object_entry " +
			"and d.object_entity = 720 " +
			"and oi.id = al.attr_entry " +
			"and al.attr_entity = 720 " +
			"and al.instance = il.instance " +
			"and il.project = p.id " + 
			"and p.published_since is not null ";
			
		if (meaLevel > 0)
			retString +=
				"and oi.level = " + meaLevel + " ";
			
		retString +=
			"and d.text REGEXP '<TEXT>') ";
		
		return retString;
	}
	
	/**
	 * @param meaLevel
	 * @return
	 */
	public static String getDraftSQLForIndicatorComment(int meaLevel) {
		String retString;
		
		retString =
			"(select distinct oi.id " +
			"from opera_indicators oi, comments c, attribute_links al, instance_links il, projects p " +
			"where oi.id = c.object_entry " +
			"and c.object_entity = 720 " +
			"and oi.id = al.attr_entry " +
			"and al.attr_entity = 720 " +
			"and al.instance = il.instance " +
			"and il.project = p.id " + 
			"and p.published_since is not null ";
			
		if (meaLevel > 0)
			retString +=
				"and oi.level = " + meaLevel + " ";
			
		retString += 
			"and c.text REGEXP '<TEXT>') ";
		
		return retString;
	}
	
	/**
	 * @param meaLevel
	 * @return
	 */
	public static String getDraftSQLForIndicatorKeyword(int meaLevel) {
		String retString;
		
		retString =
			"(select distinct oi.id " +
			"from opera_indicators oi, keyword_refs kr, keywords k, attribute_links al, instance_links il, projects p " +
			"where kr.entity = 720 ";
			
		if (meaLevel > 0)
			retString +=
				"and oi.level = " + meaLevel + " ";
			
		retString +=
			"and kr.entry = oi.id " +
			"and kr.keyword = k.id " +
			"and k.keyword REGEXP '<TEXT>' " +
			"and oi.id = al.attr_entry " +
			"and al.table_id = 720 " +
			"and al.instance = il.instance " +
			"and il.project = p.id " + 
			"and p.published_since is not null) ";
		
		return retString;
	}

}

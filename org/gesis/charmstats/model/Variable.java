package org.gesis.charmstats.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
public class Variable extends Attributes implements Comparable {

	/* charmstats.variables (MySQL)
		`id`	   	 int(11)	  NOT NULL auto_increment,
	  	`name`		 varchar(255) 		   default NULL,
	  	`label`		 varchar(255) 		   default NULL,
	  	`temporal`	 tinyint(1) 		   default '0',
	  	`geographic` tinyint(1) 		   default '0',
	  	`weight`	 tinyint(1) 		   default '0',
	  	`nominal`	 tinyint(1) 		   default '0',
	  	`ordinal`	 tinyint(1) 		   default '0',
	  	`continuous` tinyint(1) 		   default '0',
	  	`type`		 int(11) 			   default NULL,
	  	`level`		 int(11) 			   default NULL,
	  	`measure`	 int(11) 			   default NULL,
	  	`question`	 int(11) 			   default NULL,
		PRIMARY KEY  (`id`)
	*/
	
	public static final String NO_COMMENT = "No Comment";
	public static final String ID = "ID";
	public static final String LABEL = "Label";
	public static final String NO_LEVEL = "No Level";	
	public static final String TYPE = "Type";
	public static final String NO_TYPE = "No Type";
	public static final String VALUES = "Value(s)";
	public static final String NO_VALUES = "No Value(s)";
	public static final String EMPTY = "";
	
	/*
	 *	Fields
	 */
	String						name;
	String						label;
	
	private Object				reference;
/*	TODO: remove elements from variable data model on the database in 1.0 */
//	private boolean 			temporal;
//	private boolean 			geographic;
//	private boolean 			weight;		// VariableType.WEIGHT
//	private boolean 			nominal;	// MeasurementLevel.NOMINAL
//	private boolean 			ordinal;	// MeasurementLevel.ORDINAL
//	private boolean 			continuous;	// MeasurementLevel.CONTINUOUS
	
	public VariableType 		type;
	public TypeOfData			level;
	public MeasurementLevel		measureType;
	
	private String				dataset;
	private String				pid;
	private String 				datadate;
	
	private Definition			definition		= new Definition();
	private Comment 			comment			= new Comment();
	private ArrayList<Keyword>	keywords		= new ArrayList<Keyword>();
		
	private Question			source;
	private int					source_id;
	
	/*
	 *	Constructor
	 */
	/**
	 * 
	 */
	public Variable() {
		super();
		
		entity_type = EntityType.VARIABLES;
		
		source = new Question();
		
		setType(VariableType.NONE);
		setLevel(TypeOfData.NONE);
		setMeasureType(MeasurementLevel.NONE);
		setDataset(new String());
		setPID(new String());
		setDataDate(new String());
		setDefinition(new Definition());
		getDefinition().setReference(this);
		setComment(new Comment());
		getComment().setReference(this);
		setKeywords(new ArrayList<Keyword>());
	}
	
	/**
	 * @param id
	 */
	public Variable(int id) {
		this();
		
		setEntityID(id); 
	}
	
	/**
	 * @param id
	 * @param label
	 */
	public Variable(int id, String label) {
		this(id);
		 
		setLabel(label);
	}
	
	/*
	 *	Methods
	 */
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		if (getName() != null)
			return getName();
		
		return super.toString();
	}
	
	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return
	 */
	public String getName() {
		return name;
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
	 * @param type
	 */
	public void setType(VariableType type) {
		this.type = type;
	}

	/**
	 * @return
	 */
	public VariableType getType() {
		return type;
	}
	
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
	
	/**
	 * @param measureLevel
	 */
	public void setMeasureType(MeasurementLevel measureLevel) {
		this.measureType = measureLevel;
	}

	/**
	 * @return
	 */
	public MeasurementLevel getMeasureType() {
		return measureType;
	}

	/* Dataset */
	/**
	 * @param dataset
	 */
	public void setDataset(String dataset) {
		this.dataset = dataset;
		
	}
	
	/**
	 * @return
	 */
	public String getDataset() {
		return dataset;
	}
	
	/* PID */
	/**
	 * @param pid
	 */
	public void setPID(String pid) {
		this.pid = pid;
		
	}
	
	/**
	 * @return
	 */
	public String getPID() {
		return pid;
	}
	
	/* Published Since */
	/**
	 * @param finished_since
	 */
	public void setDataDate(String datadate) {
		this.datadate = datadate;
	}

	/**
	 * @return
	 */
	public String getDataDate() {
		return datadate;
	}
	
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
	
	/**
	 * @param index
	 * @return
	 */
	public Keyword getKeyword(int index) {
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
	

	/**
	 * @param connection
	 * @return
	 */
	public Question getQuestion(Connection connection) {
//		Question question = null;
		
//		QuestionRef ref = new QuestionRef();
//		ref.setReferenced(this);
//		if (ref.entityLoad(connection))
//			question = ref.getQuestion();
		
		Question question = new Question();
		question.setEntityID(source_id);
		question.entityLoad(connection);
				
		return question;
	}

	/**
	 * @param connection
	 * @return
	 */
	public Study getStudy(Connection connection) {
//		Study study = null;		
//		
//		Question question = getQuestion(connection);
//		
//		StudyRef ref = new StudyRef();
//		ref.setReferenced(question);
//		if (ref.entityLoad(connection))
//			study = ref.getStudy();
		
		Study study = new Study();
		study.setEntityID(getSource().getSourceID());
		study.entityLoad(connection);
		
		return study;
	}
		
	/**
	 * @param id
	 * @return
	 */
	public static String getDraftSQLForProject(int id) {
    	String retString;
    	
		retString =
			"select distinct p.name " +
			"from projects p, instance_links il, attribute_links al, variables v " +
			"where p.id = il.project " +
			"and il.instance = al.instance " + 
			"and al.attr_entity = 800 " + 
			"and al.attr_entry = " + id;
		
		return retString;
    }
	    
    /**
     * @param id
     * @return
     */
    public static String getDraftSQLForStudy(int id) {
    	String retString;
    	
		retString =
			"select distinct s.title " +
			"from studies s, questions q, variables v " +
			"where s.id = q.study " +
			"and q.id = v.question " +
			"and v.id = " + id;
		
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
			"from projects p, instance_links il, attribute_links al, variables v " +
			"where p.published_since is not null " +
			"and p.id = il.project ";
			
		if (proType > 0)
			retString +=
				"and p.type = " + proType + " ";
		
		if (meaLevel > 0)
			retString +=
				"and v.level = " + meaLevel + " ";
			
		retString +=
			"and il.instance = al.instance " + 
			"and al.attr_entity = 800 " + 
			"and al.attr_entry = v.id " + 
			"and v.label REGEXP '<TEXT>' " +
			") ";
			
		return retString;
	}
	
	/**
	 * @param proType
	 * @param meaLevel
	 * @return
	 */
	public static String getDraftSQLForAllProjectsThroughName(int proType, int meaLevel) {
		String retString;
		
		retString =
			"(select distinct p.id " +
			"from projects p, instance_links il, attribute_links al, variables v " +
			"where p.published_since is not null " +
			"and p.id = il.project ";
			
		if (proType > 0)
			retString +=
				"and p.type = " + proType + " ";
		
		if (meaLevel > 0)
			retString +=
				"and v.level = " + meaLevel + " ";
			
		retString +=
			"and il.instance = al.instance " + 
			"and al.attr_entity = 800 " + 
			"and al.attr_entry = v.id " + 
			"and v.name REGEXP '<TEXT>' " +
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
			"from projects p, instance_links il, attribute_links al, variables v, definitions d " + 
			"where p.published_since is not null " +
			"and p.id = il.project ";
			
			if (proType > 0)
				retString +=
					"and p.type = " + proType + " ";
			
			if (meaLevel > 0)
				retString +=
					"and v.level = " + meaLevel + " ";
				
			retString +=
			"and il.instance = al.instance " + 
			"and al.attr_entity = 800 " + 
			"and al.attr_entry = v.id " + 
			"and v.id = d.object_entry " +
			"and d.object_entity = 800 " +
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
			"from projects p, instance_links il, attribute_links al, variables v, comments c " + 
			"where p.published_since is not null " +
			"and p.id = il.project ";
			
		if (proType > 0)
			retString +=
				"and p.type = " + proType + " ";
		
		if (meaLevel > 0)
			retString +=
				"and v.level = " + meaLevel + " ";
			
		retString += 
			"and il.instance = al.instance " + 
			"and al.attr_entity = 800 " + 
			"and al.attr_entry = v.id " + 
			"and v.id = c.object_entry " +
			"and c.object_entity = 800 " +
			"and c.text REGEXP '<TEXT>' " + 
			") ";
				
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
			"from projects p, instance_links il, attribute_links al, variables v, keyword_refs kr, keywords k " + 
			"where p.published_since is not null " +
			"and p.id = il.project ";
			
		if (proType > 0)
			retString +=
				"and p.type = " + proType + " ";
		
		if (meaLevel > 0)
			retString +=
				"and v.level = " + meaLevel + " ";
			
		retString += 
			"and il.instance = al.instance " + 
			"and al.attr_entity = 800 " + 
			"and al.attr_entry = v.id " +
			"and v.id = kr.entry " +
			"and kr.entity = 800 " + 
			"and kr.keyword = k.id " + 
			"and k.keyword REGEXP '<TEXT>' " + 
			") ";
				
		return retString;
	}
	
	/**
	 * @param meaLevel
	 * @return
	 */
	public static String getDraftSQLForVariableLabel(int meaLevel) {
		String retString;
		
		retString =
			"(select distinct v.id " +
			"from variables v " +
			"where v.label REGEXP '<TEXT>'";
			
		if (meaLevel > 0)
			retString +=
				" and v.level = " + meaLevel;
		
		retString += ") ";
						
		return retString;
	}
	
	/**
	 * @param meaLevel
	 * @return
	 */
	public static String getDraftSQLForVariableName(int meaLevel) {
		String retString;
		
		retString =
			"(select distinct v.id " +
			"from variables v " +
			"where v.name REGEXP '<TEXT>'";
			
		if (meaLevel > 0)
			retString +=
				" and v.level = " + meaLevel;
		
		retString += ") ";
						
		return retString;
	}
	
	/**
	 * @param meaLevel
	 * @return
	 */
	public static String getDraftSQLForVariableDefinition(int meaLevel) {
		String retString;
		
		retString =
			"(select distinct v.id " +
			"from variables v, definitions d " +
			"where v.id = d.object_entry " +
			"and d.object_entity = 720 "; 
			
		if (meaLevel > 0)
			retString +=
				"and v.level = " + meaLevel + " ";
			
		retString += 
			"and d.text REGEXP '<TEXT>') ";
		
		return retString;
	}
	
	/**
	 * @param meaLevel
	 * @return
	 */
	public static String getDraftSQLForVariableComment(int meaLevel) {
		String retString;
		
		retString =
			"(select distinct v.id " +
			"from variables v, comments c " +
			"where v.id = c.object_entry " +
			"and c.object_entity = 800 ";
			
		if (meaLevel > 0)
			retString +=
				"and v.level = " + meaLevel + " ";
			
		retString += 
			"and c.text REGEXP '<TEXT>') ";
		
		return retString;
	}
	
	/**
	 * @param meaLevel
	 * @return
	 */
	public static String getDraftSQLForVariableKeyword(int meaLevel) {
		String retString;
		
		retString =
			"(select distinct v.id " +
			"from variables v, keyword_refs kr, keywords k " +
			"where kr.entity = 800 ";
			
		if (meaLevel > 0)
			retString +=
				"and v.level = " + meaLevel + " ";
			
		retString += 
			"and kr.entry = v.id " +
			"and kr.keyword = k.id " +
			"and k.keyword REGEXP '<TEXT>') ";
		
		return retString;
	}

	/**
	 * @return
	 */
	public Question getSource() {
		return source;
	}

	/**
	 * @param source
	 */
	public void setSource(Question source) {
		this.source = source;
	}
	
	/**
	 * @return
	 */
	public int getSourceID() {
		return source_id;
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
					
				String sqlQuery = "SELECT name, label, " + 
					"type, level, measure, " +
					"question, dataset, pid, datadate " +
					"FROM variables " +
					"WHERE id=\"" + this.getEntityID() + "\"";
					
				rSet = stmt.executeQuery( sqlQuery );
					
				while (rSet.next()) {
					this.setName(rSet.getString("name"));
					this.setLabel(rSet.getString("label"));
					
					this.setType(VariableType.getItem(rSet.getInt("type")));
					this.setLevel(TypeOfData.getItem(rSet.getInt("level")));
					this.setMeasureType(MeasurementLevel.getItem(rSet.getInt("measure")));
					source_id = rSet.getInt("question");
					
					this.setDataset(rSet.getString("dataset"));
					this.setPID(rSet.getString("pid"));
					this.setDataDate(rSet.getString("datadate"));

					loadStatus = true;
				}
				
				/* Handle Question */
				if (source_id > 0) {
					getSource().setEntityID(source_id);
					getSource().entityLoad(connection);
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
	
	/**
	 * @param _connection
	 * @return
	 */
	public boolean entityLoadByDesignator(Connection _connection) {
		boolean loadStatus = false;
		
		java.sql.Statement stmt;
		ResultSet rSet;
		
		if (_connection.equals(null)) {
			loadStatus = false;
			JOptionPane.showMessageDialog(
					null, "No Connection to DataBase!", "Error:", JOptionPane.ERROR_MESSAGE);
							
			/* DEMO ONLY */
			System.err.println("Error: No Connection to DataBase!");
			
			return loadStatus;
		}
		
		try {
			if(!_connection.isClosed()) {
				stmt = _connection.createStatement();
					
				String sqlQuery = "SELECT id, " + 
					"type, level, measure, " +
					"question " +
					"FROM variables " +
					"WHERE name=\"" + escapeQuote(this.getName()) + "\" " +
					"AND label =\"" + escapeQuote(this.getLabel()) + "\"";
									
				rSet = stmt.executeQuery( sqlQuery );
					
				while (rSet.next()) {
					this.setEntityID(rSet.getInt("id"));
					
					this.setType(VariableType.getItem(rSet.getInt("type")));
					this.setLevel(TypeOfData.getItem(rSet.getInt("level")));
					this.setMeasureType(MeasurementLevel.getItem(rSet.getInt("measure")));
					source_id = rSet.getInt("question");

					loadStatus = true;
				}
				
				/* Handle Question */
				if (source_id > 0) {
					getSource().setEntityID(source_id);
					getSource().entityLoad(_connection);
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
				/* Remove Definition of Variable */
				if (definition != null)	
					definition.entityRemove(connection);
				
				/* Remove Comment to Variable */
				if (comment != null) 
					comment.entityRemove(connection);
				
//				/* Remove Literatures from Variable */
//				if ((literatures != null) &&
//						!literatures.isEmpty()) {
//					Iterator<Literature> litIter = literatures.iterator();
//					
//					while(litIter.hasNext()) {
//						Literature lit = litIter.next();
//						
//						lit.entityRemove(connection);
//					}
//				}
				
				/* Remove Variable */
				try {
					sqlQuery = "DELETE from variables " +
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
	 * @param connection
	 * @return
	 */
	public boolean storeImport(Connection connection) {

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
				
				if (comment instanceof Comment) {
					comment.setReference(this);
					storeStatus = comment.entityStore(connection);
				}
				
				if (definition instanceof Definition) {
					definition.setReference(this);
					storeStatus = definition.entityStore(connection);
				}
				
				if (getSource() != null)
					storeStatus = getSource().storeImport(connection);
				
				sqlQuery = "UPDATE variables SET "
					+ "name = ?, "
					+ "label = ?, "
					+ "type = ?,"
					+ "level = ?,"
					+ "measure = ?,"
					+ "question = ?, "
					+ "dataset = ?, "
					+ "pid = ?, "
					+ "datadate = ? "
					+ "WHERE id = ?";
					
				prepStmt = connection.prepareStatement(sqlQuery);
				prepStmt.setString(1, DBField.VAR_NAM.truncate(getName()));
				prepStmt.setString(2, DBField.VAR_LAB.truncate(getLabel()));
				prepStmt.setInt(3, getType().getID());
				prepStmt.setInt(4, getLevel().getID());
				prepStmt.setInt(5, getMeasureType().getID());
				prepStmt.setInt(6, getSource().getEntityID());
				prepStmt.setString(7, DBField.VAR_DAT_SET.truncate(getDataset()));
				prepStmt.setString(8, DBField.VAR_PID.truncate(getPID()));				
				prepStmt.setString(9, DBField.VAR_DAT_DAT.truncate(getDataDate()));
				prepStmt.setInt(10, getEntityID());
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
				
				if (getSource() != null)
					storeStatus = getSource().storeImport(connection);
				
				if (storeStatus) {
					/*
					 *	Use Insert-Statement
					 */		
					sqlQuery = "INSERT INTO variables (name, label, ";
						sqlQuery += "type, level, measure, ";
						sqlQuery += "question, dataset, pid, datadate";
					sqlQuery += ") VALUES ("
						+ "?, ?, ?, ?, ?, ?, ?, ?, ?";
					sqlQuery += ")";
					
					prepStmt = connection.prepareStatement(sqlQuery);
					prepStmt.setString(1, DBField.VAR_NAM.truncate(getName()));
					prepStmt.setString(2, DBField.VAR_LAB.truncate(getLabel()));
					prepStmt.setInt(3, getType().getID());
					prepStmt.setInt(4, getLevel().getID());
					prepStmt.setInt(5, getMeasureType().getID());
					prepStmt.setInt(6, getSource().getEntityID());
					prepStmt.setString(7, DBField.VAR_DAT_SET.truncate(getDataset()));
					prepStmt.setString(8, DBField.VAR_PID.truncate(getPID()));
					prepStmt.setString(9, DBField.VAR_DAT_DAT.truncate(getDataDate()));
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
					
					if (comment instanceof Comment) {
						comment.setReference(this);
						storeStatus = comment.entityStore(connection);
					}
					
					if (definition instanceof Definition) {
						definition.setReference(this);
						storeStatus = definition.entityStore(connection);
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

	/**
	 * @param entityID
	 * @return
	 */
	public static String getDraftSQLForValueList(int entityID) {
		String retString;
		
		retString =
			"select distinct v.value, v.label " +
			"from characteristic_links cl, valuez v " +
			"where v.id = cl.char_entry " +
			"and cl.char_entity = 810 " +
			"and cl.attr_entity = 800 " +
			"and cl.attr_entry = " + entityID;
		
		return retString;
	}
	
	/**
	 * @param text
	 * @return
	 */
	private String escapeQuote(String text) {
		String returnValue;
		
		returnValue = text.replaceAll("\"","\\\\\"");
		returnValue = returnValue.replaceAll("\'", "\\\\\'");
		
		return returnValue;
	}
	
	/**
	 * @param _connection
	 * @return
	 */
	public static List<Variable> getList(Connection _connection) {		
		List<Variable> list = new ArrayList<Variable>();
		
		if (_connection.equals(null)) {
			list = null;
			JOptionPane.showMessageDialog(
					null, "No Connection to DataBase!", "Error:", JOptionPane.ERROR_MESSAGE);
							
			/* DEMO ONLY */
			System.err.println("Error: No Connection to DataBase!");
			
			return list;
		}
		
		try {
			Statement	stmt		= _connection.createStatement();			
			String		sqlQuery	= "SELECT id FROM variables";
			ResultSet	rsltst		= stmt.executeQuery(sqlQuery);
			
			while ( rsltst.next() ) {					
				int variableID = rsltst.getInt("id");
				
				Variable var = new Variable(); 
				var.setEntityID(variableID);
				var.entityLoad(_connection);
				
				list.add(var);
			}
			
			stmt.close();
	
	    } catch (SQLException e) {
	    	JOptionPane.showMessageDialog(
	    			null, e.getMessage(), "SQLException:",  JOptionPane.ERROR_MESSAGE);
	    		  	
	    	/* DEMO ONLY */
	    	System.err.println("SQLException: " + e.getMessage());
	    	e.printStackTrace();
	        
	    	list = null;
	    }
		
		return list;
	}

	public static String getDraftSQLForAllProjects(int id) {
		String retString;
		
		retString =
			"(select distinct p.id " +
			"from projects p, instance_links il, attribute_links al, variables v "+
			"where p.id = il.project "+
			"and il.instance = al.instance "+ 
			"and al.attr_entity = 800 "+ 
			"and al.attr_entry = v.id "+ 
			"and v.id = "+ id+
			") ";
			
		return retString;
	}
	
	@Override
	public int compareTo(Object arg0) {		
		if (arg0 instanceof Variable) {
			return this.getName().compareTo(((Variable)arg0).getName());
		}
		
		return 0;
	}
}


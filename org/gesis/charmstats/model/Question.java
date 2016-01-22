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
 *	This is an Entity-Class
 * 
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class Question extends DBEntity {
	
	/* charmstats.questions (MySQL)
	  `id`				   int(11)	     NOT NULL auto_increment,
	  `text` 			   varchar(255) 		  default NULL,
	  `instruction` 	   varchar(2048) 		  default NULL,
	  `intent` 			   varchar(255) 		  default NULL,
	  `concept` 		   int(11) 				  default NULL,
	  `type` 			   int(11) 				  default NULL,
	  `response_domain`    int(11) 				  default NULL,
	  `default_definition` int(11) 				  default NULL,
	  `default_comment`    int(11) 				  default NULL,
	  `study` 			   int(11) 				  default NULL,
	  PRIMARY KEY  (`id`)
	*/
	
	/*
	 *	Fields
	 */
	private String				name;			/* ddi 3.1 D:QuestionItemType, D:MultipleQuestionItemType */	
	private String				text;			/* ddi 3.1 D:QuestionItemType, D:MultipleQuestionItemType */
	private String				instruction;	/* Interviewer Instruction */	
	private String				intent;			/* ddi 3.1 D:QuestionItemType, D:MultipleQuestionItemType */
	private Concept				concept;		/* ddi 3.1 D:QuestionItemType, D:MultipleQuestionItemType */
	private Measurement			response_domain;/* ddi 3.1 D:QuestionItemType */
	private QuestionType		type;
		
	private Question			parent;			// if filled: "this" is a sub-question
	private Study				source;
	private int					source_id;
	
	private Definition			definition;
	private Comment 			comment;
	private ArrayList<Keyword>	keywords;

	/*
	 *	Constructor
	 */
	/**
	 * 
	 */
	public Question () {
		super();
		
		entity_type = EntityType.QUESTIONS;
		
		parent		= null;
		source		= new Study();
		
		definition	= new Definition();
		comment		= new Comment();
		keywords	= new ArrayList<Keyword>();
	}
	
	/*
	 *	Methods
	 */
	/* Name */
	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/* Text */
	/**
	 * @return
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * @param text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/* Instruction */
	/**
	 * @return
	 */
	public String getInstruction() {
		return instruction;
	}
	
	/**
	 * @param instruction
	 */
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	/* Intent */
	/**
	 * @return
	 */
	public String getIntent() {
		return intent;
	}
	
	/**
	 * @param intent
	 */
	public void setIntent(String intent) {
		this.intent = intent;
	}

	/* Concept */
	/**
	 * @return
	 */
	public Concept getConcept() {
		return concept;
	}
	
	/**
	 * @param concept
	 */
	public void setConcept(Concept concept) {
		this.concept = concept;
	}
		
	/* ResponseDomain */
	/**
	 * @return
	 */
	public Measurement getResponseDomain() {
		return response_domain;
	}
	
	/**
	 * @param response_domain
	 */
	public void setResponseDomain(Measurement response_domain) {
		this.response_domain = response_domain;
	}
	
	/* Type */
	/**
	 * @return
	 */
	public QuestionType getType() {
		return type;
	}
	
	/**
	 * @param type
	 */
	public void setType(QuestionType type) {
		this.type = type;
	}

	/* Parent */
	/**
	 * @return
	 */
	public Question getParent() {
		return parent;
	}

	/**
	 * @param parent
	 */
	public void setParent(Question parent) {
		this.parent = parent;
	}

	/* Source */
	/**
	 * @return
	 */
	public Study getSource() {
		return source;
	}

	/**
	 * @param source
	 */
	public void setSource(Study source) {
		this.source = source;
	}
	
	/**
	 * @return
	 */
	public int getSourceID() {
		return source_id;
	}
	
	/* Utility */
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
		study.setEntityID(getSourceID());
		study.entityLoad(connection);
		
		return study;
	}
	
	/**
	 * @return
	 */
	public boolean isSubquestion() {
		return (parent instanceof Question);
	}

	/* Definition */
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
	
	/* Comment */
	/**
	 * @param comment
	 */
	public void setComment(Comment comment) {
		this.comment = comment;
	}

	/**
	 * @return
	 */
	public Comment getComment() {
		return comment;
	}

	/* Keyword */
	/**
	 * @param keyword
	 */
	public void setKeyword(Keyword keyword) {
		keywords.clear();
		keywords.add(keyword);
	}
	
	/**
	 * @return
	 */
	public Keyword getKeyword() {
		return ((keywords.size() < 1) ? null : keywords.get(0));
	}
	
	/**
	 * @param keywords
	 */
	public void setKeywords(ArrayList<Keyword> keywords) {
		this.keywords = keywords;
	}
	
	/**
	 * @param keyword
	 */
	public void addKeyword(Keyword keyword) {
		this.keywords.add(keyword);
	}

	/**
	 * @return
	 */
	public ArrayList<Keyword> getKeywords() {
		return keywords;
	}
	
	/**
	 * @param argValue
	 * @return
	 */
	public boolean hasKeyword(Keyword argValue) {
		
		Iterator<Keyword> iterator = keywords.iterator();
		while(iterator.hasNext()) {
			Keyword key = iterator.next();
			
			if ((key != null) &&
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
	
	/**
	 * @param id
	 * @return
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
					
				String sqlQuery = "SELECT q.name, q.text, q.instruction, q.intent, q.type, q.study " + 
					"FROM questions q " +
					"WHERE q.id=\"" + this.getEntityID() +"\"";
				
				rSet = stmt.executeQuery( sqlQuery );
					
				while (rSet.next()) {
					setName(rSet.getString("name")); 
					setText(rSet.getString("text"));
					setInstruction(rSet.getString("instruction"));
					setIntent(rSet.getString("intent")); 
					setType(QuestionType.getItem(rSet.getInt("type")));
					
					source_id = rSet.getInt("study");

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
				
				/* Handle Study */
				if (source_id > 0) {
					getSource().setEntityID(source_id);
					getSource().entityLoad(connection);
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
		@SuppressWarnings("unused")
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
			/* DoNothing */
			
			storeStatus = true;
		} else {
			/*
			 *	Insert
			 */
			/* DoNothing */
			
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
				
				if (definition instanceof Definition) {
					definition.setReference(this);
					storeStatus = definition.entityStore(connection);
				}
				
				if (getSource() != null)
					storeStatus = getSource().storeImport(connection);
				
				sqlQuery = "UPDATE questions SET "
					+ "text = ?, "
					+ "instruction = ?, "
					+ "name = ?, "
					+ "intent = ?, "
					+ "study = ? "
					+ "WHERE id = ?";
					
				prepStmt = connection.prepareStatement(sqlQuery);
				prepStmt.setString(1, DBField.QUE_TXT.truncate(getText()));
				prepStmt.setString(2, DBField.QUE_INS.truncate(getInstruction()));
				prepStmt.setString(3, DBField.QUE_NAM.truncate(getName()));
				prepStmt.setString(4, DBField.QUE_INT.truncate(getIntent()));
				prepStmt.setInt(5, getSource().getEntityID()); 
				prepStmt.setInt(6, getEntityID()); 
				
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
			
			storeStatus = true;
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
					sqlQuery = "INSERT INTO questions (name, text, instruction, intent, study"; 
					sqlQuery += ") VALUES ("
						+ "?, ?, ?, ?, ?"; 
					sqlQuery += ")";
					
					prepStmt = connection.prepareStatement(sqlQuery);
					prepStmt.setString(1, DBField.QUE_NAM.truncate(getName())); 
					prepStmt.setString(2, DBField.QUE_TXT.truncate(getText())); 
					prepStmt.setString(3, DBField.QUE_INS.truncate(getInstruction())); 
					prepStmt.setString(4, DBField.QUE_INT.truncate(getIntent())); 
					prepStmt.setInt(5, getSource().getEntityID()); 
					
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
						if (definition instanceof Definition) {
							definition.setReference(this);
							storeStatus = definition.entityStore(connection);
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
	
}

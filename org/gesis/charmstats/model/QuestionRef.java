package org.gesis.charmstats.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.gesis.charmstats.persistence.DBEntity;
import org.gesis.charmstats.persistence.DBPrimaryKey;
import org.gesis.charmstats.persistence.DBReference;
import org.gesis.charmstats.persistence.EntityType;

/**
 *	This is an Entity-Class
 * 
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class QuestionRef extends DBReference {
	
	/* charmstats.question_refs (MySQL)
	`entity`		   smallint(5) unsigned NOT NULL default '0',
	`entry`			   int(11)				NOT NULL default '0',
	`question`	   	   int(11)	   			NOT NULL default '0', TODO default '0' only here and at concept_refs
	`ref_relationship` int(11)	   					 default NULL,
	PRIMARY KEY  (`entity`,`entry`,`question`)
	*/
	
	/*
	 *	Column names and other text constants
	 */
	public static final String table_name = 	"question_refs";
	public static final String primary_key = 	"question";
	public static final String blank = 			" ";
	public static final String comma = 			",";
	public static final String equals_sign = 	"=";
	public static final String cn_tab_id = 		"entity";
	public static final String cn_tab_ent_id = 	"entry";	
	public static final String cn_ref_typ = 	"reference_type";
	public static final String cn_que =			"question";
	public static final String cn_ref_rel_id = 	"ref_relationship";
	
	/*
	 *	Fields
	 */
	
	/*	DBReferences:
	 *	protected Object referenced; 
	 */
	private int 		dsEntityTypeID;
	private int 		dsEntryID;
	
	private Question	question;
	private int 		dsQuestionID;
	
	private String		name;	
//	private Reference	type;
	
	/*	DBReferences:
	 *	protected RefRelationtype refRelationtype; 
	 */
	
	/*
	 *	Constructor
	 */
	/**
	 * 
	 */
	public QuestionRef() {
		super();
		
		entity_type = EntityType.QUEST_REFERENCES;
		question = new Question();
		
		setEntityID(-1);
	}
	
	/**
	 * @param referenced
	 */
	public QuestionRef(Object referenced) {
		this();
		
		setReferenced(referenced);
	}
	
	/*
	 *	Methods
	 */
	/* Question */
	/**
	 * @param question
	 */
	public void setQuestion(Question question) {
		this.question = question;
	}

	/**
	 * @return
	 */
	public Question getQuestion() {
		return question;
	}
	
	/**
	 * @param dsQuestionID
	 */
	public void setQuestionID(int dsQuestionID) {
		this.dsQuestionID = dsQuestionID;
	}

	/**
	 * @return
	 */
	public int getQuestionID() {
		return dsQuestionID;
	}
	
	/* Name */
	/**
	 * @param element_name
	 */
	public void setElementName(String element_name) {
		this.name = element_name;
	}

	/**
	 * @return
	 */
	public String getElementName() {
		return name;
	}

	/**
	 * @param dsEntityTypeID
	 */
	public void setDsEntityTypeID(int dsEntityTypeID) {
		this.dsEntityTypeID = dsEntityTypeID;
	}

	/**
	 * @return
	 */
	public int getDsEntityTypeID() {
		return dsEntityTypeID;
	}

	/**
	 * @param dsEntryID
	 */
	public void setDsEntryID(int dsEntryID) {
		this.dsEntryID = dsEntryID;
	}

	/**
	 * @return
	 */
	public int getDsEntryID() {
		return dsEntryID;
	}

	/**
	 * @return
	 */
	public int getEntityTypeID() {
		int table_id = -1;
		
		if (getReferenced() instanceof DBEntity )
			table_id = ((DBEntity)getReferenced()).getEntityType().getID();
		
		return table_id;
	}
	
	/**
	 * @return
	 */
	public int getEntryID() {
		int table_entry_id = -1;
		
		if (getReferenced() instanceof DBEntity )
			table_entry_id = ((DBEntity)getReferenced()).getEntityID();	
		
		return table_entry_id;
	}
	
	/*
	 *	Load and Store to DB
	 */
	/**
	 * @param connection
	 * @return
	 */
	public ArrayList<Integer> getList(Connection connection) {
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		if (connection.equals(null)) {
			list = null;
			JOptionPane.showMessageDialog(
					null, "No Connection to DataBase!", "Error:", JOptionPane.ERROR_MESSAGE);
							
			/* DEMO ONLY */
			System.err.println("Error: No Connection to DataBase!");
			
			return list;
		}
		
		if ((this.getElementName() != "") &&
			(this.getReferenced() != null) &&
			(((DBEntity)this.getReferenced()).getEntityType().getID() > 0) &&
			(((DBEntity)this.getReferenced()).getEntityID() > 0)) {
			try {
				stmt = connection.createStatement();
				
				sqlQuery = "SELECT" + blank + primary_key + blank +
					"FROM" + blank + table_name + blank +
				    "WHERE" + blank + cn_tab_id + blank + equals_sign + blank + 
				    	((DBEntity)this.getReferenced()).getEntityType().getID() + blank +
				    "AND" + blank + cn_tab_ent_id + blank + equals_sign + blank + 
				    	((DBEntity)this.getReferenced()).getEntityID();
				
				rsltst = stmt.executeQuery(sqlQuery);
				
				while ( rsltst.next() ) {					
					list.add(rsltst.getInt(primary_key));
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
		}
		
		return list;
	}
	
	/**
	 * @param connection
	 * @return
	 */
	public ArrayList<Integer> loadReferences(Connection connection) {
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		if (connection.equals(null)) {
			list = null;
			JOptionPane.showMessageDialog(
					null, "No Connection to DataBase!", "Error:", JOptionPane.ERROR_MESSAGE);
							
			/* DEMO ONLY */
			System.err.println("Error: No Connection to DataBase!");
			
			return list;
		}
		
		if ((((DBEntity)getReferenced()).getEntityType().getID() > 0) &&
			(((DBEntity)getReferenced()).getEntityID() > 0)) {
			try {
				stmt = connection.createStatement();
				
				sqlQuery = "SELECT" + blank + cn_que + blank +
					"FROM" + blank + table_name + blank +
				    "WHERE" + blank + cn_tab_id + blank + equals_sign + blank + 
				    	((DBEntity)this.getReferenced()).getEntityType().getID() + blank +
				    "AND" + blank + cn_tab_ent_id + blank + equals_sign + blank + 
				    	((DBEntity)this.getReferenced()).getEntityID();
				
				rsltst = stmt.executeQuery(sqlQuery);
				
				while ( rsltst.next() ) {					
					list.add(rsltst.getInt(cn_que));
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
		}
		
		return list;
	}
	
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
					
				String sqlQuery = "SELECT question " +
					"FROM question_refs " +
					"WHERE question=\"" + this.question.getEntityID() + "\"" +
					"AND entity=\"" + this.getEntityTypeID()+ "\"" +
					"AND entry=\"" + this.getEntryID() + "\"";
					
				rSet = stmt.executeQuery( sqlQuery );
					
				while (rSet.next()) {
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

		if ((this.question.getEntityID() > 0) && 
				(((DBPrimaryKey) this.getReferenced()).getEntityID() > 0)) {
		
			if (!this.entityLoad(connection)) {
				/*
				 *	Insert
				 */	
				try {
					connection.setAutoCommit(false);
									
					if (storeStatus) {
						/*
						 *	Use Insert-Statement
						 */				
						sqlQuery = "INSERT INTO question_refs (entity, entry, question, ref_relationship" 
							+") VALUES (" 
							+" '"	+ this.getEntityTypeID()			+ "',"
							+ "'"	+ this.getEntryID()					+ "',"
							+ "'"	+ this.getQuestion().getEntityID()	+ "'," 
							+ "'"	+ this.getRefRelationtype().getID()	+ "'";
						sqlQuery +=	")";
						
						stmt = connection.createStatement();
						rows = stmt.executeUpdate(sqlQuery);
						
						if (rows < 1)
							storeStatus = false;
						else {
							/* DoNothing */
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
    public boolean entityRemoveSingle(Connection connection) {
    	
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
					
		try {
			sqlQuery = "DELETE from " + table_name +" "+
				"WHERE " + QuestionRef.primary_key +"="+ this.getQuestion().getEntityID() +" "+
				"AND " + QuestionRef.cn_tab_id +"="+ ((DBEntity)this.getReferenced()).getEntityType().getID() +" "+
				"AND " + QuestionRef.cn_tab_ent_id +"="+ ((DBEntity)this.getReferenced()).getEntityID();
			
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

		
    	return removeStatus;
    }
}

package org.gesis.charmstats.model;

import java.sql.Connection;
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
@Deprecated
public class StudyRef extends DBReference {
	
	/* charmstats.study_refs (MySQL) 
	`entity`		   int(11) NOT NULL,
  	`entry`			   int(11) NOT NULL,
  	`study`			   int(11) NOT NULL,
  	`ref_relationship` int(11)			 default NULL,
  	PRIMARY KEY  (`entity`,`entry`,`study`)
	*/
	
	/*
	 *	Column names and other text constants
	 */
	public static final String table_name 		= "study_refs";
	public static final String primary_key		= "study";
	public static final String blank			= " ";
	public static final String comma			= ",";
	public static final String equals_sign		= "=";
	public static final String cn_tab_id		= "entity";
	public static final String cn_tab_ent_id	= "entry";
	public static final String cn_stu			= "study";
	public static final String cn_ref_rel_id	= "ref_relationship";
	
	/*
	 *	Fields
	 */
	
	/*	DBReferences:
	 *	protected Object referenced;
	 */
	private int		dsEntityTypeID;
	private int		dsEntryID;
	
	private Study	study;
	private int		dsStudyID;

	/*	DBReferences:
	 *	protected RefRelationtype refRelationtype;
	 */	
	/*
	 *	Constructor
	 */
	/**
	 * 
	 */
	public StudyRef() {
		super();
		
		entity_type = EntityType.STUDY_REFS;
		study = new Study();
		
		setEntityID(-1);
	}
	
	/**
	 * @param referenced
	 */
	public StudyRef(Object referenced) {
		this();
		
		setReferenced(referenced);
	}
	
	/*
	 *	Methods
	 */
	/**
	 * @param study
	 */
	public void setStudy(Study study) {
		this.study = study;
	}

	/**
	 * @return
	 */
	public Study getStudy() {
		return study;
	}

	/**
	 * @param dsStudyID
	 */
	public void setStudyID(int dsStudyID) {
		this.dsStudyID = dsStudyID;
	}

	/**
	 * @return
	 */
	public int getStudyID() {
		return dsStudyID;
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
	
	/*
	 *	Load and Store to DB
	 */
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
				
				sqlQuery = "SELECT" +blank+ primary_key +blank+
					"FROM" +blank+ table_name +blank+
				    "WHERE" +blank+ cn_tab_id +blank+ equals_sign +blank+ 
				    	getReferencedEntityTypeID() +blank+
				    "AND" +blank+ cn_tab_ent_id +blank+ equals_sign +blank+ 
				    	getReferencedID();
				
				rsltst = stmt.executeQuery(sqlQuery);
				
				while ( rsltst.next() ) {					
					list.add(rsltst.getInt(cn_stu));
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
	
//	public ArrayList<Integer> getUniverseID(Connection connection) {
//		
//		ArrayList<Integer> list = new ArrayList<Integer>();
//		
//		if (connection.equals(null)) {
//			list = null;
//			JOptionPane.showMessageDialog(
//					null, "No Connection to DataBase!", "Error:", JOptionPane.ERROR_MESSAGE);
//							
//			/* DEMO ONLY */
//			System.err.println("Error: No Connection to DataBase!");
//			
//			return list;
//		}
//		
//		if ((((DBEntity)getReferenced()).getEntityType().getID() > 0) &&
//			(getStudy().getEntityID() > 0)) {
//			try {
//				stmt = connection.createStatement();
//				
//				sqlQuery = "SELECT" + blank + cn_tab_ent_id + blank +
//					"FROM" + blank + table_name + blank +
//				    "WHERE" + blank + cn_tab_id + blank + equals_sign + blank + 
//				    	((DBEntity)this.getReferenced()).getEntityType().getID() + blank +
//				    "AND" + blank + cn_stu + blank + equals_sign + blank + 
//				    	this.getStudy().getEntityID();
//				
//				rsltst = stmt.executeQuery(sqlQuery);
//				
//				while ( rsltst.next() ) {					
//					list.add(rsltst.getInt(cn_tab_ent_id));
//				}
//				
//				stmt.close();
//		
//		    } catch (SQLException e) {
//		    	JOptionPane.showMessageDialog(
//		    			null, e.getMessage(), "SQLException:",  JOptionPane.ERROR_MESSAGE);
//		    		  	
//		    	/* DEMO ONLY */
//		    	System.err.println("SQLException: " + e.getMessage());
//		    	e.printStackTrace();
//		        
//		    	list = null;
//		    }
//		}
//		
//		return list;
//	}
	
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.persistence.DBEntity#entityLoad(java.sql.Connection)
	 */
	public boolean entityLoad(Connection connection) {

		boolean loadStatus = false;
		
		ArrayList<Integer> id_list = this.loadReferences(connection);
		if (id_list.size() > 0) {
			study = new Study();
			study.setEntityID(id_list.get(0));
			study.entityLoad(connection);
			loadStatus = true;
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

		
		if ((this.study.getEntityID() > 0) && 
			(((DBPrimaryKey) this.getReferenced()).getEntityID() > 0)) {
			/*
			 *	Update
			 */
			/*
			 *	TODO
			 */
			
			storeStatus = true;
		} else {
			/*
			 *	Insert
			 */
			/*
			 *	TODO
			 */
			
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

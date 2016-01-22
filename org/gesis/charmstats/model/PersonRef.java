package org.gesis.charmstats.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.gesis.charmstats.persistence.DBEntity;
import org.gesis.charmstats.persistence.DBReference;
import org.gesis.charmstats.persistence.EntityType;
import org.gesis.charmstats.persistence.RefRelationtype;

/**
 *	This is an Entity-Class
 * 
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class PersonRef extends DBReference {

	/* charmstats.person_refs (MySQL)
	`entity`		   smallint(5) unsigned NOT NULL default '0',
	`entry`			   int(11)				NOT NULL default '0',
	`role`			   int(11)				NOT NULL default '0', // hint: role only in this class
	`person`	   	   int(11)	   			NOT NULL,
	`ref_relationship` int(11)	   					 default NULL,
	PRIMARY KEY  (`entity`,`entry`,`person`)
	*/
	
	/*
	 *	Column names and other text constants
	 */
	public static final String table_name = 	"person_refs";
	public static final String primary_key = 	"person";
	public static final String blank = 			" ";
	public static final String comma = 			",";
	public static final String equals_sign = 	"=";
	public static final String equals =		 	"=";
	public static final String cn_tab_id = 		"entity";
	public static final String cn_tab_ent_id = 	"entry";	
	public static final String cn_per =			"person";
	public static final String cn_rol =			"role";
	public static final String cn_ref_rel_id = 	"ref_relationship";
	
	/*
	 *	Fields
	 */
	
	/*	DBReferences:
	 *	protected Object referenced; 
	 */
	private int 		dsEntityTypeID;
	private int 		dsEntryID;
	
	private Person		person;
	private int 		dsPersonID;
	
	private PersonRole	role;
	private int			dsRoleID;
	
	/*	DBReferences:
	 *	protected RefRelationtype refRelationtype; 
	 */
	
	/*
	 *	Constructor
	 */
	/**
	 * 
	 */
	public PersonRef() {
		super();
		
		entity_type = EntityType.PERSON_REF;
		
		setEntityID(-1);
	}
	
	/**
	 * @param referenced
	 */
	public PersonRef(Object referenced) {
		this();
		
		setReferenced(referenced);
	}
	
	/*
	 *	Methods
	 */
	/* Author */
	/**
	 * @param person
	 */
	public void setPerson(Person person) {
		this.person = person;
	}

	/**
	 * @return
	 */
	public Person getPerson() {
		return person;
	}
	
	/**
	 * @param dsPersonID
	 */
	public void setPersonID(int dsPersonID) {
		this.dsPersonID = dsPersonID;
	}

	/**
	 * @return
	 */
	public int getPersonID() {
		return dsPersonID;
	}

	/* Role (optional) */
	/**
	 * @param role
	 */
	public void setRole(PersonRole role) {
		this.role = role;
	}
	
	/**
	 * @return
	 */
	public PersonRole getRole() {
		return role;
	}
	
	/**
	 * @param dsRoleID
	 */
	public void setRoleID(int dsRoleID) {
		this.dsRoleID = dsRoleID;
	}
	
	/**
	 * @return
	 */
	public int getRoleID(){
		return dsRoleID;
	}
	
	/* Referenced Object */
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
	
	/*RefRelationType, ref_relationtype_id:*/
	/**
	 * @param refRelationType
	 */
	public void setRefRelationType(RefRelationtype refRelationType) {
		this.refRelationtype = refRelationType;
	}

	/**
	 * @return
	 */
	public RefRelationtype getRefRelationType() {
		return refRelationtype;
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
				
				String sqlQuery = "SELECT" +blank+cn_per+comma+cn_rol+comma+cn_tab_id+comma+cn_tab_ent_id+comma+cn_ref_rel_id+blank+
						"FROM" +blank+table_name+blank+
						"WHERE" +blank+primary_key+equals+this.getEntityID();
					
				rSet = stmt.executeQuery( sqlQuery );
						
				while (rSet.next()) {
					setPersonID(rSet.getInt(cn_per));
					setRoleID(rSet.getInt(cn_rol));
					setDsEntityTypeID(rSet.getInt(cn_tab_id));
					setDsEntryID(rSet.getInt(cn_tab_ent_id));
					setRefRelationType(RefRelationtype.getItem(rSet.getInt(cn_ref_rel_id)));
					
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

		if (this.getEntityID() > 0) {
			try {
				connection.setAutoCommit(false);

				if ((storeStatus) &&
						(this.getPerson() != null )) {
					storeStatus = this.getPerson().entityStore(connection);
				} else {
					storeStatus = false;
				}
					
				if (storeStatus) {
					/*
					 *	Use Update-Statement 
					 */
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
			try {
				connection.setAutoCommit(false);
				
				if ((storeStatus) &&
						(getPerson() != null )) {
					storeStatus = getPerson().entityStore(connection);
				} else {
					storeStatus = false;
				}
					
				if (storeStatus) {
					/*
					 *	Use Insert-Statement
					 */		
					sqlQuery = "INSERT INTO person_refs "
						+ "(entity, entry, " 
						+ "person, role, ref_relationship) "
						+ "VALUES "
						+ "('"	+ ((DBEntity)this.getReferenced()).getEntityType().getID()	+ "',"
						+ "'"	+ ((DBEntity)this.getReferenced()).getEntityID()			+ "',"  
						+ "'"	+ getPerson().getEntityID()									+ "',"
						+ "'"	+ getRole().getID()											+ "',"
						+ "'"	+ this.getRefRelationtype().getID()							+ "')";
					
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
    public boolean entitiesRemove(Connection connection) {
    	
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
			
		if (this.getReferenced() != null) {
			try {
				sqlQuery = "DELETE FROM" + blank + table_name + blank +
			    	"WHERE" + blank + cn_tab_id + blank + equals + blank + ((DBEntity)this.getReferenced()).getEntityType().getID() + blank +
			    	"AND" + blank + cn_tab_ent_id + blank + equals + blank + ((DBEntity)this.getReferenced()).getEntityID() + blank +
			    	"AND" + blank + cn_rol + blank + equals + blank + this.getRole().getID();
				
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
	public ArrayList<Integer> loadReferences(Connection connection) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		if (connection.equals(null)) {
			list = null;
			JOptionPane.showMessageDialog(null, "Error! No Connection to DataBase.");
			
			return list;
		}
		
		if ((((DBEntity)getReferenced()).getEntityType().getID() > 0) &&
				(((DBEntity)getReferenced()).getEntityID() > 0)) {
			try {
				stmt = connection.createStatement();
				
				sqlQuery = "SELECT" + blank + cn_per + blank +
					"FROM" + blank + table_name + blank +
				    "WHERE" + blank + cn_tab_id + blank + equals + blank + 
				    	((DBEntity)this.getReferenced()).getEntityType().getID() + blank +
				    "AND" + blank + cn_tab_ent_id + blank + equals + blank + 
				    	((DBEntity)this.getReferenced()).getEntityID();
				
				if ((getRole() instanceof PersonRole) &&
						(getRole() != PersonRole.NONE))
					sqlQuery += blank + "AND" + blank + cn_rol + blank + equals + blank +
						getRole().getID();					
				
				rsltst = stmt.executeQuery(sqlQuery);
				
				while (rsltst.next()) {					
					list.add(rsltst.getInt(cn_per));
				}
				
				stmt.close();
		
		    } catch (SQLException e) {
		        System.err.println("SQLException: " + e.getMessage());
		        
		    	list = null;
		    }
		}
		
		return list;
	}
}

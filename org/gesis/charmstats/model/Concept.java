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
public class Concept extends DBEntity {

	/* charmstats.concepts (MySQL)
		`id`				  int(11)		NOT NULL auto_increment,
		`name`				  varchar(255) 			 default NULL,
		`label`				  varchar(255) 			 default NULL,
		`description`		  varchar(2048) 		 default NULL,
		`has_similar_concept` tinyint(1) 			 default '0',
		`is_characteristic`	  tinyint(1) 			 default '0',
		PRIMARY KEY  (`id`)
	*/
	
	/*
	 *	Column names and other text constants
	 */
	public static final String table_name	= "concepts";
	public static final String primary_key	= "id";
	public static final String select		= "SELECT ";
	public static final String from			= " FROM ";
	public static final String update		= "UPDATE ";
	public static final String set			= " SET ";
	public static final String insert_into	= "INSERT INTO ";
	public static final String values		= " VALUES ";
	public static final String delete_from	= "DELETE FROM ";
	public static final String where		= " WHERE ";
	public static final String apostroph	= "'";
	public static final String blank		= " ";
	public static final String comma		= ", ";
	public static final String equals		= " = ";
	public static final String quote		= "\"";
	public static final String cn_nam		= "name";
	public static final String cn_lab		= "label";
	public static final String cn_des		= "description";
	public static final String cn_is_cha	= "is_characteristic";	
	
	public static final String CONCEPT		= "Concept";
	public static final String NO_CONCEPT	= "No Concept";
	
	/*
	 *	Fields
	 */
	private ArrayList<Name>			names				= new ArrayList<Name>();
	private ArrayList<Label> 		labels				= new ArrayList<Label>();
	private ArrayList<Description>	descriptions		= new ArrayList<Description>();
	private ArrayList<Concept>		similarConcepts		= new ArrayList<Concept>();
	private Boolean					isCharacteristic	= false;
	
	private ArrayList<Literature>	literatures;
	
	/**
	 *	Constructor 
	 */
	public Concept() {
		super();
		setEntityID(-1);
		
		entity_type = EntityType.CONCEPTS;	
	}
		
	/**
	 * @param label
	 * @param description
	 */
	public Concept (Label label, Description description) {
		this();
		
		setDefaultName(new Name(label.getLabel().getTextualContent()));
		setDefaultLabel(label);
		setDefaultDescription(description);
		setIsCharacteristic(false);
	}
	
	/**
	 * @param id
	 */
	public Concept(int id) {
		this();		
		setEntityID(id);
	}
	
	/*
	 *	Methods
	 */
	/*
	 *	NAME
	 */
	/**
	 * @param defaultName
	 */
	public void setDefaultName(Name defaultName) {
		if (names.isEmpty())
			names.add(defaultName);
		else
			names.add(0, defaultName);
	}
	
	/**
	 * @param defaultName
	 */
	public void setDefaultName(String defaultName) {
		Name name = new Name(defaultName);
		
		setDefaultName(name);
	}
	
	/**
	 * @return
	 */
	public Name getDefaultName() {
		Name result = null;
		
		if (!names.isEmpty())
			result = names.get(0);
		
		return result;
	}
	
	/**
	 * @param name
	 */
	public void addName(Name name) {
		if (name.isPreferred())
			setDefaultName(name);
		else
			names.add(name);
	}
	
	/**
	 * @return
	 */
	public Name getName() {
		return getDefaultName();
	}

	/**
	 * @param idx
	 * @return
	 */
	public Name getNameByIndex(int idx) {
		Name result = null;
		
		if ((!names.isEmpty()) &&
				(0 <= idx) && (idx < names.size()))
			result = names.get(idx);
		
		return result;
	}
	
	/**
	 * @param id
	 * @return
	 */
	public Name getNameByID(int id) {
		Name result = null;
		
		Iterator<Name> iterator = names.iterator();
		while (iterator.hasNext()) {
			Name name = iterator.next();
			
			if (name.getEntityID() == id) {
				result = name;
				break;
			}
		}
		
		return result;
	}
	
	/**
	 * @param name
	 * @return
	 */
	public boolean removeName(Name name) {
		Boolean result = false;
		
		if (!names.isEmpty())	
			result = names.remove(name);
		
		return result; 
	}
	
	/**
	 * @param idx
	 * @return
	 */
	public boolean removeNameByIndex(int idx) {
		return removeName(getNameByIndex(idx));
	}
	
	/**
	 * @param id
	 * @return
	 */
	public boolean removeNameByID(int id) {
		return removeName(getNameByID(id));
	}
	
	/**
	 * 
	 */
	public void clearNames() {
		names.clear();
	}
	
	/*
	 *	LABEL
	 */
	/**
	 * @param defaultLabel
	 */
	public void setDefaultLabel(Label defaultLabel) {
		if (labels.isEmpty())
			labels.add(defaultLabel);
		else
			labels.set(0, defaultLabel);
	}
	
	/**
	 * @param defaultLabel
	 */
	public void setDefaultLabel(String defaultLabel) {
		Label label = new Label(defaultLabel);
		
		setDefaultLabel(label);
	}

	/**
	 * @return
	 */
	public Label getDefaultLabel() {
		Label result = null;
		
		if (!labels.isEmpty())
			result = labels.get(0);
		
		return result;
	}
	
	/**
	 * @param label
	 */
	public void addLabel(Label label) {
		if (label.isPreferred())
			setDefaultLabel(label);
		else
			labels.add(label);
	}

	/**
	 * @return
	 */
	public Label getLabel() {
		return getDefaultLabel();
	}
	
	/**
	 * @param idx
	 * @return
	 */
	public Label getLabelByIndex(int idx) {
		Label result = null;
		
		if ((!labels.isEmpty()) &&
				(0 <= idx) && (idx < labels.size()))
			result = labels.get(idx);
		
		return result;
	}

	/**
	 * @param id
	 * @return
	 */
	public Label getLabelByID(int id) {
		Label result = null;
		
		Iterator<Label> iterator = labels.iterator();
		while (iterator.hasNext()) {
			Label label = iterator.next();
			
			if (label.getEntityID() == id) {
				result = label;
				break;
			}
		}
		
		return result;
	}
	
	/**
	 * @param label
	 * @return
	 */
	public boolean removeLabel(Label label) {
		Boolean result = false;
		
		if (!labels.isEmpty())	
			result = labels.remove(label);
		
		return result; 
	}
	
	/**
	 * @param idx
	 * @return
	 */
	public boolean removeLabelByIndex(int idx) {
		return removeLabel(getLabelByIndex(idx));
	}
	
	/**
	 * @param id
	 * @return
	 */
	public boolean removeLabelByID(int id) {
		return removeLabel(getLabelByID(id));
	}

	/**
	 * 
	 */
	public void clearLabels() {
		labels.clear();
	}
	
	/*
	 *	DESCRIPTION
	 */
	/**
	 * @param defaultDescription
	 */
	public void setDefaultDescription(Description defaultDescription) {
		if (descriptions.isEmpty())
			descriptions.add(defaultDescription);
		else
			descriptions.set(0, defaultDescription);
	}
	
	/**
	 * @param defaultDescription
	 */
	public void setDefaultDescription(String defaultDescription) {
		Description description =  new Description(defaultDescription);
		
		setDefaultDescription(description);
	}

	/**
	 * @return
	 */
	public Description getDefaultDescription() {
		Description result = null;
		
		if (!descriptions.isEmpty())
			result = descriptions.get(0);
		
		return result;
	}
	
	/**
	 * @param description
	 */
	public void addDescription(Description description) {
		if (description.isPreferred())
			setDefaultDescription(description);
		else
			descriptions.add(description);
	}

	/**
	 * @return
	 */
	public Description getDescription() {
		return getDefaultDescription();
	}
	
	/**
	 * @param idx
	 * @return
	 */
	public Description getDescriptionByIndex(int idx) {
		Description result = null;
		
		if ((!descriptions.isEmpty()) &&
				(0 <= idx) && (idx < descriptions.size()))
			result = descriptions.get(idx);
		
		return result;
	}
	
	/**
	 * @param id
	 * @return
	 */
	public Description getDescriptionByID(int id) {
		Description result = null;
		
		Iterator<Description> iterator = descriptions.iterator();
		while (iterator.hasNext()) {
			Description description = iterator.next();
			
			if (description.getEntityID() == id) {
				result = description;
				break;
			}
		}
		
		return result;
	}
	
	/**
	 * @param description
	 * @return
	 */
	public boolean removeDescription(Description description) {
		Boolean result = false;
		
		if (!descriptions.isEmpty())	
			result = descriptions.remove(description);
		
		return result; 
	}
	
	/**
	 * @param idx
	 * @return
	 */
	public boolean removeDescriptionByIndex(int idx) {
		return removeDescription(getDescriptionByIndex(idx));
	}
	
	/**
	 * @param id
	 * @return
	 */
	public boolean removeDescriptionByID(int id) {
		return removeDescription(getDescriptionByID(id));
	}
	
	/**
	 * 
	 */
	public void clearDescriptions() {
		descriptions.clear();
	}
	
	/*
	 *	SIMILAR CONCEPT
	 */
	/**
	 * @param concept
	 */
	public void addSimilarConcept(Concept concept) {
		similarConcepts.add(concept);
	}

	/**
	 * @param idx
	 * @return
	 */
	public Concept getSimilarConceptByIndex(int idx) {
		Concept result = null;
		
		if ((!similarConcepts.isEmpty()) &&
				(0 <= idx) && (idx < similarConcepts.size()))
			result = similarConcepts.get(idx);
		
		return result;
	}
	
	/**
	 * @param id
	 * @return
	 */
	public Concept getSimilarConceptByID(int id) {
		Concept result = null;
		
		Iterator<Concept> iterator = similarConcepts.iterator();
		while (iterator.hasNext()) {
			Concept concept = iterator.next();
			
			if (concept.getEntityID() == id) {
				result = concept;
				break;
			}
		}
		
		return result;
	}
	
	/**
	 * @param concept
	 * @return
	 */
	public boolean removeSimilarConcept(Concept concept) {
		Boolean result = false;
		
		if (!similarConcepts.isEmpty())	
			result = similarConcepts.remove(concept);
		
		return result; 
	}
	
	/**
	 * @param idx
	 * @return
	 */
	public boolean removeSimilarConceptByIndex(int idx) {
		return removeSimilarConcept(getSimilarConceptByIndex(idx));
	}
	
	/**
	 * @param id
	 * @return
	 */
	public boolean removeSimilarConceptByID(int id) {
		return removeSimilarConcept(getSimilarConceptByID(id));
	}
	
	/**
	 * 
	 */
	public void clearSimilarConcepts() {
		similarConcepts.clear();
	}

	/*
	 *	IS_CHARACTERISTIC
	 */
	/**
	 * @param isCharacteristic
	 */
	public void setIsCharacteristic(Boolean isCharacteristic) {
		this.isCharacteristic = isCharacteristic;
	}
	
	/**
	 * @return
	 */
	public Boolean isCharacteristic() {
		return isCharacteristic;
	}
	// mySQL only:
	/**
	 * @return
	 */
	public int getCharacteristic() {
		return (isCharacteristic ? 1 : 0);
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
					
				/*
				// Use this in CharmStatsPro:
				if (loadStatus) {
					LiteratureRef ref_lit = new LiteratureRef();
					ref_lit.setReferenced(this);
							
					ArrayList<Integer> list = ref_lit.loadReferences(connection);
					Iterator<Integer> it_lit = list.iterator();
						
					while (it_lit.hasNext()) {
						int id = it_lit.next();
							
						Literature literature = new Literature();
						literature.setEntityID(id);

						if (loadStatus)
							loadStatus = literature.entityLoad(connection);
							
						if (loadStatus) 
							if (literature != null)
								this.addLiterature(literature);					
					}
				}
				*/
				
				String sqlQuery = select+cn_nam+comma
						+cn_lab+comma
						+cn_des+comma	
						+cn_is_cha
						+from+table_name
						+where+primary_key+equals+getEntityID();
				
				rSet = stmt.executeQuery(sqlQuery);
					
				while (rSet.next()) {
					setDefaultName(rSet.getString(cn_nam));
					setDefaultLabel(rSet.getString(cn_lab));
					setDefaultDescription(rSet.getString(cn_des));
					setIsCharacteristic(rSet.getBoolean(cn_is_cha));
					
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
	
	/* Literature */
	/**
	 * @param literatures
	 */
	public void setLiteratures(ArrayList<Literature> literatures) {
		this.literatures = literatures;
	}
	
	/**
	 * @param literature
	 */
	public void addLiterature(Literature literature) {
		literatures.add(literature);
	}
	
	/**
	 * @return
	 */
	public ArrayList<Literature> getLiteratures() {
		return literatures;
	}
	
	/**
	 * @param index
	 * @return
	 */
	public Literature getLiteratureByIndex(int index) {
		Literature result = null;
		
		try {
			result = literatures.get(index);
		} catch (IndexOutOfBoundsException e) {
	    	/* DEMO ONLY */
	    	System.err.println("SQLException: " + e.getMessage());
	    	e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * @param literature_id
	 * @return
	 */
	public Literature getLiteratureByID(int literature_id) {
		Literature result = null;
		
		Iterator<Literature> it_lit = literatures.iterator();
			
		while (it_lit.hasNext()) {
			Literature literature = it_lit.next();
			
			if (literature.getEntityID() == literature_id) {
				result = literature; break;
			}
		}
		
		return result;
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
				
				/*
				// Use this in CharmStatsPro:
				if (storeStatus) {
					Iterator<Literature> it_literature = getLiteratures().iterator();
					
					while (it_literature.hasNext()) {
						Literature literature = it_literature.next();
						
						if (literature.notEmpty()) {
							if (storeStatus)
								storeStatus = literature.entityStore(connection);
							
							if (storeStatus) {
								LiteratureRef literatureRef = new LiteratureRef();
								literatureRef.setLiterature(literature);
								literatureRef.setReferenced(this);
								literatureRef.setRefRelationtype(RefRelationtype.REFERENCED);
								
								storeStatus = literatureRef.entityStore(connection);
							}
						}
					}
				}
				*/		
				
				if (storeStatus) {
					sqlQuery = update+table_name+set
						+cn_nam		+equals+" ?"+comma+" "
						+cn_lab		+equals+" ?"+comma+" "
						+cn_des		+equals+" ?"+comma+" "
						+cn_is_cha	+equals+" ?"+" "
						+where+primary_key+equals+" ?";
						
					prepStmt = connection.prepareStatement(sqlQuery);
					prepStmt.setString(1, DBField.CON_NAM.truncate(getDefaultName().getTextualContent().getTextualContent()));
					prepStmt.setString(2, DBField.CON_LAB.truncate(getDefaultLabel().getLabel().getTextualContent()));
					prepStmt.setString(3, DBField.CON_DES.truncate(getDefaultDescription().getDescription().getTextualContent()));
					prepStmt.setInt(4, getCharacteristic());
					prepStmt.setInt(5, getEntityID());
					
					rows = prepStmt.executeUpdate();
					
					if (rows < 1)				
						storeStatus = false;
					
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
			
		} else {
			/*
			 *	Insert
			 */
			try {
				connection.setAutoCommit(false);
				
				if (storeStatus) {
					sqlQuery = insert_into+table_name+blank+"("
						+cn_nam+comma
						+cn_lab+comma
						+cn_des+comma
						+cn_is_cha
						+")"+values+"("
						+"?, ?, ?, ?"
						+")";
					
					prepStmt = connection.prepareStatement(sqlQuery);
					prepStmt.setString(1, DBField.CON_NAM.truncate(getDefaultName().getTextualContent().getTextualContent()));
					prepStmt.setString(2, DBField.CON_LAB.truncate(getDefaultLabel().getLabel().getTextualContent()));
					prepStmt.setString(3, DBField.CON_DES.truncate(getDefaultDescription().getDescription().getTextualContent()));
					prepStmt.setInt(4, getCharacteristic());
					
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
				sqlQuery = delete_from+table_name
					+where+primary_key+equals+getEntityID();
				
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
			"from projects p, concept_refs cr " +
			"where p.published_since is not null " +
			"and p.id = cr.entry " + 
			"and cr.entity = 100  " + 
			"and cr.concept = " + id;
		
		return retString;
    }
	 
	/**
	 * @return
	 */
	public static String getDraftSQLForAllProjectsThroughLabel() {
		String retString;
		
		retString =
			"(select distinct p.id " +
			"from projects p, concept_refs cr, concepts c " +
			"where p.published_since is not null " +
			"and p.id = cr.entry " + 
			"and cr.entity = 100  " + 
			"and cr.concept = c.id " +
			"and c.label REGEXP '<TEXT>' " +
			") ";
		
		return retString;
	}
	
	/**
	 * @return
	 */
	public static String getDraftSQLForAllProjectsThroughDefinition() {
		String retString;
		
		retString =
			"(select distinct p.id " +
			"from projects p, concept_refs cr, concepts c " +
			"where p.published_since is not null " +
			"and p.id = cr.entry " + 
			"and cr.entity = 100  " + 
			"and cr.concept = c.id " +
			"and c.description REGEXP '<TEXT>' " +
			") ";
		
		return retString;
	}

	/**
	 * @return
	 */
	public static String getDraftSQLForConceptLabel() {
		String retString;
		
		retString =
			"(select distinct c.id " +
			"from concepts c, concept_refs cr, projects p " +
			"where p.published_since is not null " + 
			"and cr.entry = p.id " +
			"and cr.entity = 100 " +
			"and cr.concept = c.id " +
			"and c.label REGEXP '<TEXT>') ";
			
		return retString;
	}
		
	/**
	 * @return
	 */
	public static String getDraftSQLForConceptDefinition() {
		String retString;
		
		retString =
			"(select distinct c.id " +
			"from concepts c, concept_refs cr, projects p " +
			"where p.published_since is not null " + 
			"and cr.entry = p.id " + 
			"and cr.entity = 100 " +
			"and cr.concept = c.id " +
			"and c.description REGEXP '<TEXT>') ";
			
		return retString;
	}
}

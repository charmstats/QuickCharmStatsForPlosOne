package org.gesis.charmstats.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.ListModel;

import org.gesis.charmstats.persistence.DBField;
import org.gesis.charmstats.persistence.EntityType;

/**
 *	This is an Entity-Class
 * 
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class ConDimension extends Attributes {
	
	/* charmstats.con_dimensions (MySQL)
		`id`	int(11) 	 NOT NULL auto_increment,
		`label` varchar(255) 		  default NULL,
		`level` int(11)				  default NULL,
		PRIMARY KEY  (`id`)
	*/

	public static final String ID			= "ID";
	public static final String LABEL		= "Label";
	public static final String COMMENT		= "Comment";
	public static final String NO_COMMENT	= "No Comment";
	public static final String LEVEL		= "Level";
	public static final String NO_LEVEL		= "No Level";	
	public static final String TABLE		= "con_dimensions";
	public static final String PRIMARY_KEY	= "id";
	
	/*
	 *	Fields
	 */
	private String 				label;
	
	private TypeOfData			level;	
	private Definition 			definition;
	private Comment 			comment;
	private ArrayList<Keyword>	keywords;
	
	private ListModel specsTable = null;
	
	/**
	 *	Constructor 
	 */
	public ConDimension() {
		super ();
		
		entity_type = EntityType.CON_DIMENSIONS;
		
		setLabel(new String());
		
		setLevel(TypeOfData.NONE);	
		setDefinition(new Definition());
		getDefinition().setReference(this);
		setComment(new Comment());
		getComment().setReference(this);
		setKeywords(new ArrayList<Keyword>());
		
		setSpecsTable(new DefaultListModel());
	}
	
	/**
	 * @param id
	 */
	public ConDimension(int id) {
		this ();
		
		setEntityID(id);
	}
	
	/**
	 * @param id
	 * @param label
	 */
	public ConDimension(int id, String label) {
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

	/* Definiton */
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
	
	/**
	 * @param keywords
	 */
	public void addKeywords(ArrayList<Keyword> keywords) {
		this.keywords.addAll(keywords);
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
			
			if (key.equals(argValue)) 
				return true;
		}
		
		return false;
	}
	
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.model.Attributes#getKeywordByIndex(int)
	 */
	public Keyword getKeywordByIndex(int index) {
		try {
			return keywords.get(index);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.model.Attributes#getKeywordByID(int)
	 */
	public Keyword getKeywordByID(int id) {		
		Iterator<Keyword> iterator = keywords.iterator();
		while(iterator.hasNext()) {
			Keyword keyword = iterator.next();

			if (keyword.getEntityID() == id)
				return keyword;			
		}
	
		return null;
	}
	
	/**
	 * @param label
	 * @return
	 */
	public Keyword getKeywordByLabel(String label) {
		Iterator<Keyword> iterator = keywords.iterator();			
		while(iterator.hasNext()) {
			Keyword keyword = iterator.next();
			
			if (keyword.getKeyword().equals(label))
				return keyword;
		}
		
		return null;
	}
	
	/**
	 * @param keyword
	 * @return
	 */
	public boolean removeKeyword(Keyword keyword) {
		return keywords.remove(keyword);
	}
	
	/**
	 * @param id
	 * @return
	 */
	public boolean removeKeywordByID(int id) {
		return keywords.remove(getKeywordByID(id));
	}
	
	/**
	 * @param label
	 * @return
	 */
	public boolean removeKeywordByLabel(String label) {
		return keywords.remove(getKeywordByLabel(label));
	}
	
	/**
	 * @param index
	 * @return
	 */
	public boolean removeKeywordByIndex(int index) {
		try {
			return (keywords.remove(index) != null) ? true : false;
		} catch (IndexOutOfBoundsException e) {
			return false;
		}
	}
	
	/**
	 * 
	 */
	public void clearKeywords() {
		keywords.clear();
	}
	
	/* Specifications */
	/**
	 * @param parameters
	 */
	public void setSpecs(ArrayList<ConSpecification> parameters) {
		if (parameters != null) {
			
			Iterator<ConSpecification> iterator = parameters.iterator();
			while(iterator.hasNext()) {
				addSpec(iterator.next());	
			}
		}
	}
	
	/**
	 * @param parameter
	 */
	public void addSpec(ConSpecification parameter) {
		if (parameter != null )
			addToSpecsTable(parameter);
	}
	
	/**
	 * @return
	 */
	public ArrayList<ConSpecification> getSpecs() {
		ArrayList<ConSpecification> values = new ArrayList<ConSpecification>();
		
		for (int i=0; i<specsTable.getSize(); i++) {
			values.add((ConSpecification)specsTable.getElementAt(i));
		}
		
		return values;
	}
	
	/**
	 * @param parameter
	 */
	public void removeSpec(ConSpecification parameter) {
		if (parameter != null) {
			removeFromSpecsTable(parameter);
		}
	}
	
	/**
	 * @param argValue
	 * @return
	 */
	public boolean hasSpec(ConSpecification argValue) {
		
		for (int i=0; i<specsTable.getSize(); i++) {
			ConSpecification specification = (ConSpecification)specsTable.getElementAt(i);
			
			if ( (specification != null) &&
					(specification.equals(argValue))) {
				return true;
			}
		}
			
		return false;
	}
	
	/**
	 * @param specsTable
	 */
	public void setSpecsTable(ListModel specsTable) {
		this.specsTable = specsTable;
	}
	
	/* Called from addSpec(Conspecification parameter) */
	/**
	 * @param parameter
	 */
	public void addToSpecsTable(ConSpecification parameter) {
		((DefaultListModel)this.specsTable).addElement(parameter);
	}
	
	/**
	 * @param parameter
	 */
	public void removeFromSpecsTable(ConSpecification parameter) {
		((DefaultListModel)this.specsTable).removeElement(parameter);
	}
	
	/**
	 * @return
	 */
	public ListModel getSpecsTable() {
		return specsTable;
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
					
				String sqlQuery = "SELECT label, level " +
					"FROM con_dimensions " +
					"WHERE id=\"" + this.getEntityID() + "\"";
					
				rSet = stmt.executeQuery( sqlQuery );
					
				while (rSet.next()) {
					this.setLabel(rSet.getString("label"));
					this.setLevel(TypeOfData.getItem(rSet.getInt("level")));
					
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
			ConSpecificationRef ref = new ConSpecificationRef();
			ref.setReferenced(this);
					
			ArrayList<Integer> list = ref.loadReferences(connection);
			Iterator<Integer> iterator_i = list.iterator();
				
			while (iterator_i.hasNext()) {
				int index = iterator_i.next();
					
				ConSpecification spec = new ConSpecification();
				spec.setEntityID(index);
				spec.entityLoad(connection);
					
				if ((spec != null) && (!this.hasSpec(spec))) {
					this.addSpec(spec);
				}
			}			
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

		
		if (entity_id > 0) {
			/*
			 *	Update
			 */
			try {
				connection.setAutoCommit(false);
				
				storeStatus = definition.entityStore(connection);
				
				storeStatus = comment.entityStore(connection);
				
				sqlQuery = "UPDATE con_dimensions SET "
					+ "label = ?, "
					+ "level = ? "
					+ "WHERE id = ?";
				
				prepStmt = connection.prepareStatement(sqlQuery);
				prepStmt.setString(1, DBField.DIM_LAB.truncate(this.getLabel()));
				prepStmt.setInt(2, this.getLevel().getID());
				prepStmt.setInt(3, this.getEntityID());
				
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
					sqlQuery = "INSERT INTO con_dimensions (label, level";
					sqlQuery += ") VALUES ("
						+ "?, ?";
					sqlQuery += ")";
					
					prepStmt = connection.prepareStatement(sqlQuery);
					prepStmt.setString(1, DBField.DIM_LAB.truncate(this.getLabel()));
					prepStmt.setInt(2, this.getLevel().getID());
					
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
				if (keywords != null) {
					Iterator<Keyword> iterator_k = keywords.iterator();
						
					while (iterator_k.hasNext()) {
						Keyword key = iterator_k.next();
						
						if (removeStatus)
							removeStatus = key.entityRemove(connection);
					}			
				}
			
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
    
    /**
     * @param id
     * @return
     */
    public static String getDraftSQLForProject(int id) {
    	String retString;
    	
		retString =
			"select distinct p.name " +
			"from projects p, instance_links il, attribute_links al, con_dimensions cd " +
			"where p.id = il.project " +
			"and il.instance = al.instance " + 
			"and al.attr_entity = 610 " + 
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
			"from projects p, instance_links il, attribute_links al, con_dimensions cd " +
			"where p.published_since is not null " +
			"and p.id = il.project ";
			
		if (proType > 0)
			retString +=
				"and p.type = " + proType + " ";
		
		if (meaLevel > 0)
			retString +=
				"and cd.level = " + meaLevel + " ";
			
		retString +=
			"and il.instance = al.instance " + 
			"and al.attr_entity = 610 " + 
			"and al.attr_entry = cd.id " + 
			"and cd.label REGEXP '<TEXT>' " + 
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
			"from projects p, instance_links il, attribute_links al, con_dimensions cd, definitions d " + 
			"where p.published_since is not null " +
			"and p.id = il.project ";
			
		if (proType > 0)
			retString +=
				"and p.type = " + proType + " ";
		
		if (meaLevel > 0)
			retString +=
				"and cd.level = " + meaLevel + " ";
			
		retString += 
			"and il.instance = al.instance " + 
			"and al.attr_entity = 610 " + 
			"and al.attr_entry = cd.id " + 
			"and cd.id = d.object_entry " +
			"and d.object_entity = 610 " +
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
			"from projects p, instance_links il, attribute_links al, con_dimensions cd, comments c " + 
			"where p.published_since is not null " +
			"and p.id = il.project ";
			
		if (proType > 0)
			retString +=
				"and p.type = " + proType + " ";
		
		if (meaLevel > 0)
			retString +=
				"and cd.level = " + meaLevel + " ";
			
		retString += 
			"and il.instance = al.instance " + 
			"and al.attr_entity = 610 " + 
			"and al.attr_entry = cd.id " + 
			"and cd.id = c.object_entry " +
			"and c.object_entity = 610 " +
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
			"from projects p, instance_links_il, attribute_links al, con_dimensions cd, keyword_refs kr, keywords k " + 
			"where p.published_since is not null " +
			"and p.id = il.project ";
		
		if (proType > 0)
			retString +=
				"and p.type = " + proType + " ";
		
		if (meaLevel > 0)
			retString +=
				"and cd.level = " + meaLevel + " ";
		
		retString +=
			"and il.instance = al.instance " + 
			"and al.attr_entity = 610 " + 
			"and al.attr_entry = cd.id " +
			"and cd.id = kr.entry " +
			"and kr.entity = 610 " + 
			"and kr.keyword = k.id " + 
			"and k.keyword REGEXP '<TEXT>' " + 
			") ";
		
		return retString;
	}

	/**
	 * @param meaLevel
	 * @return
	 */
	public static String getDraftSQLForDimensionLabel(int meaLevel) {
		String retString;
		
		retString =
			"(select distinct cd.id " +
			"from con_dimensions cd, attribute_links al, instance_links il, projects p " +
			"where cd.label REGEXP '<TEXT>' " +
			"and cd.id = al.attr_entry " +
			"and al.attr_entity = 610 " +
			"and al.instance = il.instance " +
			"and il.project = p.id " + 
			"and p.published_since is not null ";
			
		if (meaLevel > 0)
			retString +=
				"and cd.level = " + meaLevel + " ";
		
		retString += ") ";
		
		return retString;
	}
	
	/**
	 * @param meaLevel
	 * @return
	 */
	public static String getDraftSQLForDimensionDefinition(int meaLevel) {
		String retString;
		
		retString =
			"(select distinct cd.id " +
			"from con_dimensions cd, definitions d, attribute_links al, instant_links il, projects p " +
			"where cd.id = d.object_entry " +
			"and d.object_entity = 610 " +
			"and cd.id = al.attr_entry " +
			"and al.attr_entity = 610 " +
			"and al.instance = il.instance " +
			"and il.project = p.id " + 
			"and p.published_since is not null ";
			
		if (meaLevel > 0)
			retString +=
				"and cd.level = " + meaLevel + " ";
			
		retString +=
			"and d.text REGEXP '<TEXT>') ";
		
		return retString;
	}
	
	/**
	 * @param meaLevel
	 * @return
	 */
	public static String getDraftSQLForDimensionComment(int meaLevel) {
		String retString;
		
		retString =
			"(select distinct cd.id " +
			"from con_dimensions cd, comments c, attribute_links al, instance_links il, projects p " +
			"where cd.id = c.object_entry " +
			"and c.object_entity = 610 " +
			"and cd.id = al.attr_entry " +
			"and al.attr_entity = 610 " +
			"and al.instance = il.instance " +
			"and il.project = p.id " + 
			"and p.published_since is not null ";
			
		if (meaLevel > 0)
			retString +=
				"and cd.level = " + meaLevel + " ";
			
		retString +=
			"and c.text REGEXP '<TEXT>') ";
		
		return retString;
	}
	
	/**
	 * @param meaLevel
	 * @return
	 */
	public static String getDraftSQLForDimensionKeyword(int meaLevel) {
		String retString;
		
		retString =
			"(select distinct cd.id " +
			"from con_dimensions cd, keyword_refs kr, keywords k, attribute_links al, instance_links il, projects p " +
			"where kr.entity = 610 ";
			
		if (meaLevel > 0)
			retString +=
				"and cd.level = " + meaLevel + " ";
			
		retString +=
			"and kr.entry = cd.id " +
			"and kr.keyword = k.id " +
			"and k.keyword REGEXP '<TEXT>' " +
			"and cd.id = al.attr_entry " +
			"and al.table_id = 610 " +
			"and al.instance = il.instance " +
			"and il.project = p.id " + 
			"and p.published_since is not null) ";
		
		return retString;
	}
	
}

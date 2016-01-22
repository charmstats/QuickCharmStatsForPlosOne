package org.gesis.charmstats.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
public class Measurement extends Attributes implements Comparable {
	
	/* charmstats.measurements (MySQL)
		`id`		  int(11)	   NOT NULL auto_increment,
		`label`		  varchar(256) 			default NULL,
		`abbr`		  varchar(64)			default NULL,
		`type`		  int(11)				default NULL,
		`level`		  int(11)				default NULL,
		`source`	  int(11)				default NULL,
		`kind`		  int(11)				default '1',
		`is_template` tinyint(1)			default '0',
		PRIMARY KEY  (`id`)
	*/
	
	/*
	 *	Fields
	 */
	private String				name;	
	private String 				label;
	
	private MeasurementType		type;
	private TypeOfData			level;
	private MeasurementSource	source;
	private MeasurementLevel	kind;
	
	private String				dataset;
	private String				pid;
	
	private Definition			definition		= new Definition();
	private Comment 			comment			= new Comment();
	
	private boolean				is_template;
	
	/*
	 *	Constructor
	 */
	/**
	 * 
	 */
	public Measurement() {
		super();
		
		entity_type = EntityType.MEASUREMENTS;
		
		setType(MeasurementType.CLASSIFICATION);
		setLevel(TypeOfData.AGGREGATE);
		setSource(MeasurementSource.PROJECT);
		setKind(MeasurementLevel.NOMINAL);
		setDataset(new String());
		setPID(new String());
		setComment(new Comment());
		getComment().setReference(this);
				
		setIsTemplate(false);
	}
	
	/**
	 * @param type
	 */
	public Measurement(MeasurementType type) {
		this();
		
		setType(type);
	}
	
	/**
	 * @param id
	 * @param label
	 */
	public Measurement(int id, String label) {
		this ();
		
		setEntityID(id);
		setName(label);
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

	/* Label */
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
	
	/* Abbreviation */
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

	/* MeasurementType */
	/**
	 * @param type
	 */
	public void setType(MeasurementType type) {
		this.type = type;
	}

	/**
	 * @return
	 */
	public MeasurementType getType() {
		return type;
	}

	/* MeasurementLevel */
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

	/* MeasurementLevel, Kind */
	/**
	 * @param level
	 */
	public void setKind(MeasurementLevel level) {
		this.kind = level;
		
	}
	
	/**
	 * @return
	 */
	public MeasurementLevel getKind() {
		return kind;
	}
	
	/* Source */
	/**
	 * @return
	 */
	public MeasurementSource getSource() {
		return source;
	}

	/**
	 * @param source
	 */
	public void setSource(MeasurementSource source) {
		this.source = source;
	}
	
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.model.Attributes#setComment(org.gesis.charmstats.model.Comment)
	 */
	public void setComment(Comment comment) {
		this.comment = comment;
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
	 * @see org.gesis.charmstats.model.Attributes#getComment()
	 */
	public Comment getComment() {
		return comment;
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
	
	/* is template */
	/**
	 * @param is_template
	 */
	public void setIsTemplate(boolean is_template) {
		this.is_template = is_template;
	}
	
	// mySQL only:
	/**
	 * @param is_template
	 */
	public void setIsTemplate(int is_template) {
		this.is_template = (is_template > 0);
	}

	/**
	 * @return
	 */
	public boolean isTemplate() {
		return is_template;
	}
	
	// mySQL only:
	/**
	 * @return
	 */
	public int getIsTemplateImported() {
		return (is_template ? 1 : 0);
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
					
				String sqlQuery = "SELECT name, label, type, level, source, kind, is_template, dataset, pid " +
					"FROM measurements " +
					"WHERE id=\"" + this.getEntityID() + "\"";
					
				rSet = stmt.executeQuery( sqlQuery );
					
				while (rSet.next()) {
					this.setName(rSet.getString("name"));
					this.setLabel(rSet.getString("label"));
					this.setType(MeasurementType.getItem(rSet.getInt("type")));
					this.setLevel(TypeOfData.getItem(rSet.getInt("level")));
					this.setSource(MeasurementSource.getItem(rSet.getInt("source")));
					this.setKind(MeasurementLevel.getItem(rSet.getInt("kind")));
					this.setIsTemplate(rSet.getInt("is_template"));
					this.setDataset(rSet.getString("dataset"));
					this.setPID(rSet.getString("pid"));

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
				
				comment.setReference(this);
				storeStatus = comment.entityStore(connection);
				
				if (definition instanceof Definition) {
					definition.setReference(this);
					storeStatus = definition.entityStore(connection);
				}
				
				sqlQuery = "UPDATE measurements SET "
					+ "name = ?,"
					+ "label = ?,"
					+ "type = ?,"
					+ "level = ?,"
					+ "source = ?,"
					+ "kind = ?,"
					+ "is_template = ?, "
					+ "dataset = ?, "
					+ "pid = ? "
					+ "WHERE id = ?";
				
				prepStmt = connection.prepareStatement(sqlQuery);
				prepStmt.setString(1, DBField.MEA_NAM.truncate(getName()));
				prepStmt.setString(2, DBField.MEA_LAB.truncate(getLabel()));
				prepStmt.setInt(3, getType().getID());
				prepStmt.setInt(4, getLevel().getID());
				prepStmt.setInt(5, getSource().getID());
				prepStmt.setInt(6, getKind().getID());
				prepStmt.setInt(7, getIsTemplateImported());
				prepStmt.setString(8, DBField.MEA_DAT_SET.truncate(getDataset()));
				prepStmt.setString(9, DBField.MEA_PID.truncate(getPID()));
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
				
				if (storeStatus) {
					/*
					 *	Use Insert-Statement
					 */		
					sqlQuery = "INSERT INTO measurements (name, label, type, level, source, kind, is_template, dataset, pid";
					sqlQuery += ") VALUES ("
						+ "?, ?, ?, ?, ?, ?, ?, ?, ?";
					sqlQuery += ")";
					
					prepStmt = connection.prepareStatement(sqlQuery);
					prepStmt.setString(1, DBField.MEA_NAM.truncate(getName()));
					prepStmt.setString(2, DBField.MEA_LAB.truncate(getLabel()));
					prepStmt.setInt(3, getType().getID());
					prepStmt.setInt(4, getLevel().getID());
					prepStmt.setInt(5, getSource().getID());
					prepStmt.setInt(6, getKind().getID());
					prepStmt.setInt(7, getIsTemplateImported());
					prepStmt.setString(8, DBField.MEA_DAT_SET.truncate(getDataset()));
					prepStmt.setString(9, DBField.MEA_PID.truncate(getPID()));
					
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
					
					comment.setReference(this);
					storeStatus = comment.entityStore(connection);
					
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
 * @param id
 * @return
 */
public static String getDraftSQLForProject(int id) {
    	String retString;
    	
		retString =
			"select distinct p.name " +
			"from projects p, measurements m, attribute_links al, instance_links il " +
			"where p.published_since is not null " +
			"and p.id = il.project " +
			"and il.instance = al.instance " +
			"and al.attr_entity = 500 " +
			"and al.attr_entry = m.id " +
			"and m.id = " + id;
		
		return retString;
    }
	   
   /**
 * @param meaType
 * @param meaLevel
 * @return
 */
public static String getDraftSQLForAllProjectThroughLabel(int meaType, int meaLevel) {
	   String retString;
		
	   retString =
			"(select distinct p.id " +
			"from projects p, measurements m, attribute_links al, instance_links il " +
			"where p.published_since is not null " +
			"and p.id = il.project " +
			"and il.instance = al.instance " +
			"and al.attr_entity = 500 " +
			"and al.attr_entry = m.id " +			
			"and m.label REGEXP '<TEXT>' ";
		
	   if (meaType > 0)
			retString +=
				"and m.kind = " + meaType + " ";
		
		if (meaLevel > 0)
			retString +=
				"and m.level = " + meaLevel + " ";
		
		retString += ") ";
		
		return retString;
	}
   
	/**
	 * @param meaType
	 * @param meaLevel
	 * @return
	 */
	public static String getDraftSQLForAllProjectThroughAttr(int meaType, int meaLevel) {
		String retString;
			
		retString =
			"(select distinct p.id " +
			"from projects p, measurements m, attribute_links al, instance_links il " +
			"where p.id = il.project " +
			"and il.instance = al.instance " +
			"and al.attr_entity = 500 " +
			"and al.attr_entry = m.id " +
			"and m.label REGEXP '<TEXT>' ";
			
		if (meaType > 0)
			retString +=
				"and m.kind = " + meaType + " ";
			
		if (meaLevel > 0) {
			retString +=
				"and m.level = " + meaLevel + " ";
		}
			
		retString += ") ";
			
		return retString;
	}
	
	/**
	 * @param meaType
	 * @param meaLevel
	 * @return
	 */
	public static String getDraftSQLForMeasurementName(int meaType, int meaLevel) {
		String retString;
		
		retString =
			"(select distinct m.id " +
			"from measurements m, projects p, attribute_links al, instance_links il " +
			"where m.name REGEXP '<TEXT>' ";
	
		if (meaType > 0)
			retString +=
				"and m.kind = " + meaType + " ";
		
		if (meaLevel > 0)
			retString +=
				"and m.level = " + meaLevel + " ";
		
		retString +=
			"and m.source = 2 " +
			"and m.id = al.attr_entry " +
			"and al.attr_entity = 500 " +
			"and al.instance = il.instance " +
			"and il.project = p.id " + 
			"and p.published_since is not null) ";
		
		retString += " UNION ";
		
		retString +=
			"(select distinct m.id " +
			"from measurements m " +
			"where m.name REGEXP '<TEXT>' ";
				
		if (meaType > 0)
			retString +=
				"and m.kind = " + meaType + " ";
					
		if (meaLevel > 0)
			retString +=
				"and m.level = " + meaLevel + " ";
					
		retString +=
			"and m.source = 1) ";

		return retString;
	}
	
	public static String getDraftSQLForMeasurementLabel(int meaType, int meaLevel) {
		String retString;
		
		retString =
			"(select distinct m.id " +
			"from measurements m, projects p, attribute_links al, instance_links il " +
			"where m.label REGEXP '<TEXT>' ";
	
		if (meaType > 0)
			retString +=
				"and m.kind = " + meaType + " ";
		
		if (meaLevel > 0)
			retString +=
				"and m.level = " + meaLevel + " ";
		
		retString +=
			"and m.source = 2 " +
			"and m.id = al.attr_entry " +
			"and al.attr_entity = 500 " +
			"and al.instance = il.instance " +
			"and il.project = p.id " + 
			"and p.published_since is not null) ";
		
		retString += " UNION ";
		
		retString +=
			"(select distinct m.id " +
			"from measurements m " +
			"where m.label REGEXP '<TEXT>' ";
				
		if (meaType > 0)
			retString +=
				"and m.kind = " + meaType + " ";
					
		if (meaLevel > 0)
			retString +=
				"and m.level = " + meaLevel + " ";
					
		retString +=
			"and m.source = 1) ";

		return retString;
	}

	/**
	 * @param meaType
	 * @param meaLevel
	 * @return
	 */
	public static String getDraftSQLForMeasurementAttr(int meaType, int meaLevel) {
		String retString;
		
		retString =
			"(select distinct m.id " +
			"from measurements m, projects p, attribute_links al, instance_links il " +
			"where ";
		
		if (meaType > 0)
			retString +=
				"m.kind = " + meaType + " ";
		
		if (meaLevel > 0) {
			
			if (meaType > 0)
				retString += " and ";
				
			retString +=
				"m.level = " + meaLevel + " ";
		}
		
		if ((meaType > 0) || (meaLevel > 0))
			retString += " and ";
			
		
		retString +=
			"m.source = 2 " +
			"and m.id = al.attr_entry " +
			"and al.attr_entity = 500 " +
			"and al.instance = il.instance " +
			"and il.project = p.id " + 
			"and p.published_since is not null) ";
		
		retString += " UNION ";
		
		retString +=
				"(select distinct m.id " +
				"from measurements m " +
				"where ";
			
		if (meaType > 0)
			retString +=
				"m.kind = " +  meaType + " ";
		
		if (meaLevel > 0) {
			
			if (meaType > 0)
				retString += " and ";
				
			retString +=
				"m.level = " + meaLevel + " ";
		}
		
		if ((meaType > 0) || (meaLevel > 0))
			retString += " and ";
			
		
		retString +=
			"m.source = 1) ";

		return retString;
	}
	
	/**
	 * @param entityID
	 * @return
	 */
	public static String getDraftSQLForCategoryList(int entityID) {
		String retString;
		
		retString =
			"select distinct c.code, c.label " +
			"from characteristic_links cl, categories c " +
			"where c.id = cl.char_entry " +
			"and cl.char_entity = 9888 " +
			"and cl.attr_entity = 500 " +
			"and cl.attr_entry = " + entityID;
		
		return retString;
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
					"label, type, level, source, kind, is_template " +
					"FROM measurements " +
					"WHERE name=\"" + escapeQuote(this.getName()) + "\" ";
				
				rSet = stmt.executeQuery( sqlQuery );
					
				while (rSet.next()) {
					this.setEntityID(rSet.getInt("id"));
					
					this.setLabel(rSet.getString("label"));
					this.setType(MeasurementType.getItem(rSet.getInt("type")));
					this.setLevel(TypeOfData.getItem(rSet.getInt("level")));
					this.setSource(MeasurementSource.getItem(rSet.getInt("source")));
					this.setKind(MeasurementLevel.getItem(rSet.getInt("kind")));
					this.setIsTemplate(rSet.getInt("is_template"));

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
				/* Remove Definition of Measurement */
				if (definition != null)	
					definition.entityRemove(connection);
				
				/* Remove Comment to Measurement */
				if (comment != null) 
					comment.entityRemove(connection);
				
//				/* Remove Literatures from Measurement */
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
					sqlQuery = "DELETE from measurements " +
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
					
					sqlQuery = "UPDATE measurements SET "
						+ "name = ?,"
						+ "label = ?,"
						+ "type = ?,"
						+ "level = ?,"
						+ "source = ?,"
						+ "kind = ?,"
						+ "is_template = ?, "
						+ "dataset = ?, "
						+ "pid = ? "
						+ "WHERE id = ?";
					
					prepStmt = connection.prepareStatement(sqlQuery);
					prepStmt.setString(1, DBField.MEA_NAM.truncate(getName()));
					prepStmt.setString(2, DBField.MEA_LAB.truncate(getLabel()));
					prepStmt.setInt(3, getType().getID());
					prepStmt.setInt(4, getLevel().getID());
					prepStmt.setInt(5, getSource().getID());
					prepStmt.setInt(6, getKind().getID());
					prepStmt.setInt(7, getIsTemplateImported());
					prepStmt.setString(8, DBField.MEA_DAT_SET.truncate(getDataset()));
					prepStmt.setString(9, DBField.MEA_PID.truncate(getPID()));
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
					
					if (storeStatus) {
						/*
						 *	Use Insert-Statement
						 */		
						sqlQuery = "INSERT INTO measurements (name, label, type, level, source, kind, is_template, dataset, pid";
						sqlQuery += ") VALUES ("
							+ "?, ?, ?, ?, ?, ?, ?, ?, ?";
						sqlQuery += ")";
						
						prepStmt = connection.prepareStatement(sqlQuery);
						prepStmt.setString(1, DBField.MEA_NAM.truncate(getName()));
						prepStmt.setString(2, DBField.MEA_LAB.truncate(getLabel()));
						prepStmt.setInt(3, getType().getID());
						prepStmt.setInt(4, getLevel().getID());
						prepStmt.setInt(5, getSource().getID());
						prepStmt.setInt(6, getKind().getID());
						prepStmt.setInt(7, getIsTemplateImported());
						prepStmt.setString(8, DBField.MEA_DAT_SET.truncate(getDataset()));
						prepStmt.setString(9, DBField.MEA_PID.truncate(getPID()));
						
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
	 * @param _connection
	 * @return
	 */
	public static List<Measurement> getList(Connection _connection) {		
		List<Measurement> list = new ArrayList<Measurement>();
		
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
			String		sqlQuery	= "SELECT id FROM measurements";
			ResultSet	rsltst		= stmt.executeQuery(sqlQuery);
			
			while ( rsltst.next() ) {					
				int measureID = rsltst.getInt("id");
				
				Measurement mea = new Measurement(); 
				mea.setEntityID(measureID);
				mea.entityLoad(_connection);
				
				list.add(mea);
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
			"from projects p, measurements m, attribute_links al, instance_links il " +
			"where p.id = il.project " +
			"and il.instance = al.instance " +
			"and al.attr_entity = 500 " +
			"and al.attr_entry = m.id " +
			"and m.id = "+ id+
			") ";
			
		return retString;
	}
	
	@Override
	public int compareTo(Object arg0) {		
		if (arg0 instanceof Measurement) {
			return this.getName().compareTo(((Measurement)arg0).getName());
		}
		
		return 0;
	}
}

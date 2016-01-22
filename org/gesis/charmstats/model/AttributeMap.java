package org.gesis.charmstats.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;

import org.gesis.charmstats.persistence.DBEntity;

/**
 *	This is an Entity-Class
 *
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class AttributeMap extends DBEntity {

	/* charmstats.attribute_maps (MySQL)
		`id`				  int(11) NOT NULL auto_increment,
		`src_attribute`		  int(11) 		   default NULL,
		`src_transformation`  int(11)		   default NULL,
		`tgt_attribute`		  int(11)		   default NULL,
		`tgt_transformation`  int(11)		   default NULL,
		`rslt_transformation` int(11)		   default NULL,
		`type`				  int(11)		   default NULL,
		`belongs_to`		  int(11)		   default NULL,
		PRIMARY KEY  (`id`)
	*/
	/*
	 *	Fields
	 */
	private AttributeLink		sourceAttribute;		/*Reference to stored Attribute / WorkStepInstance (Source)*/	
	private Transformation		sourceTransformation;	/*Transformation*/
	private AttributeLink		targetAttribute;		/*Reference to stored Attribute / WorkStepInstance (Target)*/
	private Transformation		targetTransformation;	/*Transformation*/
	private Transformation		resultTransformation;	/*Transformation*/
	private AttributeMapType	attributeMapType;
	private InstanceMap			belongsTo;				/*Reference to W.S.I.Map the Attr.Map is part of */
	
	/* NOT TO BE STORED */
	private WorkStepInstance	filter;
	
	private int 				dsInstanceMap;
	private int 				dsSourceAttribute;
	private int 				dsSourceTransformation;
	private int 				dsTargetAttribute;
	private int 				dsTargetTransformation;
	private int 				dsResultTransformation;
	
	/**
	 *	Constructor 
	 */
	public AttributeMap() {
		super();		
	}
	
	/**
	 * @param id
	 */
	public AttributeMap(int id) {
		this();		
		
		setEntityID(id);
	}
	
	/**
	 * @param sourceAttribute
	 * @param targetAttribute
	 */
	public AttributeMap(
			AttributeLink sourceAttribute, AttributeLink targetAttribute) {
		this();
		
		setSourceAttribute(sourceAttribute);
		setTargetAttribute(targetAttribute);		
	}
	
	/*
	 *	Methods
	 */
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		if ((sourceAttribute != null) &&
				(targetAttribute != null)) {
			return   
				sourceAttribute.toString() + " => " + targetAttribute.toString();
		}
		
		/*In case of fault:*/
		return super.toString();
	}
	
	/*sourceAttribute, src_attr_type + src_attr_entry:*/
	/**
	 * @param attribute
	 */
	public void setSourceAttribute(AttributeLink attribute) {
		this.sourceAttribute = attribute;
	}

	/**
	 * @return
	 */
	public AttributeLink getSourceAttribute() {
		return sourceAttribute;
	}
	
	/*sourceTransformation, src_transformation:*/
	/**
	 * @param transformation
	 */
	public void setSourceTransformation(String transformation) {
		if (sourceTransformation != null)
			this.sourceTransformation.setCode(transformation);
		else if ((transformation != null) 
				&& (!transformation.equals(""))) {
			sourceTransformation = new Transformation();
			this.sourceTransformation.setCode(transformation);
		}	
	}

	/**
	 * @return
	 */
	public String getSourceTransformationText() {
		if (sourceTransformation != null)
			return sourceTransformation.getCode();
		
		return "";
	}
	
	/**
	 * @return
	 */
	public Transformation getSourceTransformation() {
		return sourceTransformation;
	}
	
	/*targetAttribute, tgt_attr_type + tgt_attr_entry:*/
	/**
	 * @param attribute
	 */
	public void setTargetAttribute(AttributeLink attribute) {
		this.targetAttribute = attribute;
	}

	/**
	 * @return
	 */
	public AttributeLink getTargetAttribute() {
		return targetAttribute;
	}
	
	/*targetTransformation, tgt_transformation:*/
	/**
	 * @param transformation
	 */
	public void setTargetTransformation(String transformation) {
		if (targetTransformation != null)
			this.targetTransformation.setCode(transformation);
		else if ((transformation != null) 
				&& (!transformation.equals(""))) {
			targetTransformation = new Transformation();
			this.targetTransformation.setCode(transformation);
		}
	}

	/**
	 * @return
	 */
	public String getTargetTransformationText() {
		if (targetTransformation != null)
			return targetTransformation.getCode();
		
		return "";
	}
	
	/**
	 * @return
	 */
	public Transformation getTargetTransformation() {
		return targetTransformation;
	}
	
	/*resultTransformation, rslt_transformation:*/
	/**
	 * @param transformation
	 */
	public void setResultTransformation(String transformation) {
		if (resultTransformation != null)
			this.resultTransformation.setCode(transformation);
		else if ((transformation != null) 
				&& (!transformation.equals(""))) {
			resultTransformation = new Transformation();
			this.resultTransformation.setCode(transformation);
		}
	}

	/**
	 * @return
	 */
	public String getResultTransformationText() {
		if (resultTransformation != null)
			return resultTransformation.getCode();
		
		return null;
	}
	
	/**
	 * @return
	 */
	public Transformation getResultTransformation() {
		return resultTransformation;
	}

	/*attributeMapType, attr_map_type_id:*/
	/**
	 * @param attributeMapType
	 */
	public void setAttributeMapType(AttributeMapType attributeMapType) {
		this.attributeMapType = attributeMapType;
	}

	/**
	 * @return
	 */
	public AttributeMapType getAttributeMapType() {
		return attributeMapType;
	}
	
	/**
	 * @return
	 */
	public InstanceMap getBelongsTo() {
		return belongsTo;
	}

	/**
	 * @param belongsTo
	 */
	public void setBelongsTo(InstanceMap belongsTo) {
		this.belongsTo = belongsTo;
	}

	/**
	 * @param dsBelongsTo
	 */
	public void setDsBelongsTo(int dsBelongsTo) {
		this.dsInstanceMap = dsBelongsTo;
	}

	/**
	 * @return
	 */
	public int getDsBelongsTo() {
		return dsInstanceMap;
	}

	/**
	 * @param dsAttr
	 */
	public void setDsSourceAttribute(int dsAttr) {
		this.dsSourceAttribute = dsAttr;
	}

	/**
	 * @return
	 */
	public int getDsSourceAttribute() {
		return dsSourceAttribute;
	}
	
	/**
	 * @param dsTrans
	 */
	public void setDsSourceTransformation(int dsTrans) {
		this.dsSourceTransformation = dsTrans;
	}

	/**
	 * @return
	 */
	public int getDsSourceTransformation() {
		return dsSourceTransformation;
	}
	
	/**
	 * @param dsAttr
	 */
	public void setDsTargetAttribute(int dsAttr) {
		this.dsTargetAttribute = dsAttr;
	}

	/**
	 * @return
	 */
	public int getDsTargetAttribute() {
		return dsTargetAttribute;
	}
	
	/**
	 * @param dsTrans
	 */
	public void setDsTargetTransformation(int dsTrans) {
		this.dsTargetTransformation = dsTrans;
	}

	/**
	 * @return
	 */
	public int getDsTargetTransformation() {
		return dsTargetTransformation;
	}
	
	/**
	 * @param dsTrans
	 */
	public void setDsResultTransformation(int dsTrans) {
		this.dsResultTransformation = dsTrans;
	}

	/**
	 * @return
	 */
	public int getDsResultTransformation() {
		return dsResultTransformation;
	}
		
	/**
	 * @param ext_maps
	 * @return
	 */
	public boolean isFoundIn(ArrayList<AttributeMap> ext_maps) {
 		
		if (ext_maps != null) {
			Iterator<AttributeMap> iterator = ext_maps.iterator();
							
			while (iterator.hasNext()) {
				AttributeMap map = iterator.next();
				
				if ((getSourceAttribute() != null) && (getTargetAttribute() != null)) {
					if ((getSourceAttribute().equals(map.getSourceAttribute())) &&
							(getTargetAttribute().equals(map.getTargetAttribute())))
						return true;
				}
			}
		}
	
		return false;
	}
	
	/**
	 * @param maps
	 * @param map
	 * @return
	 */
	static public boolean hasMap(ArrayList<AttributeMap> maps, AttributeMap map) {
		if ((maps != null) &&
				(map != null))
		{
			Iterator<AttributeMap> iterator = maps.iterator();
			
			boolean sameSourceAttribute;
			boolean sameTargetAttribute;
			boolean sameAttributeMapType;
			
			while (iterator.hasNext()) {
				AttributeMap it_map = iterator.next();
								
				sameSourceAttribute = true;
				if (it_map.getSourceAttribute() != null)
					sameSourceAttribute =  it_map.getSourceAttribute().equals(map.getSourceAttribute());
				else if (map.getSourceAttribute() != null)
					sameSourceAttribute = false;
								
				sameTargetAttribute = true;
				if (it_map.getTargetAttribute() != null)
					sameTargetAttribute =  it_map.getTargetAttribute().equals(map.getTargetAttribute());
				else if (map.getTargetAttribute() != null)
					sameTargetAttribute = false;
								
				sameAttributeMapType = true;
				if (it_map.getAttributeMapType() != null)
					sameAttributeMapType =  it_map.getAttributeMapType().equals(map.getAttributeMapType());
				else if (map.getAttributeMapType() != null)
					sameAttributeMapType = false;
						
				
				if (sameSourceAttribute && 
						sameTargetAttribute &&
						sameAttributeMapType)
					return true;				
			}
		}
		
		return false;
	}
	
	/**
	 * @return
	 */
	public WorkStepInstance getFilter() {
		return filter;
	}


	/**
	 * @param filter
	 */
	public void setFilter(WorkStepInstance filter) {
		this.filter = filter;
	}
	
	/*
	 *	Load and Store to DB
	 */
	/**
	 * @param connection
	 * @return
	 */
	public ArrayList<Integer> loadMaps(Connection connection) {
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		if (connection.equals(null)) {
			list = null;
			JOptionPane.showMessageDialog(
					null, "No Connection to DataBase!", "Error:", JOptionPane.ERROR_MESSAGE);
			
	    	/* DEMO ONLY */
	        System.err.println("Error: No Connection to DataBase!");
			
			return list;
		}
		
		if (getBelongsTo().getEntityID() > 0) {
			try {
				stmt = connection.createStatement();
				
				sqlQuery = "SELECT id " +
					"FROM attribute_maps " +
				    "WHERE belongs_to = " + getBelongsTo().getEntityID();

				rsltst = stmt.executeQuery(sqlQuery);
				
				while (rsltst.next()) {					
					list.add(rsltst.getInt("id"));
				}
				
				stmt.close();		
		    } catch (SQLException e) {
		    	list = null;
		    	JOptionPane.showMessageDialog(
		    			null, e.getMessage(), "SQLException:",  JOptionPane.ERROR_MESSAGE);
		    	
		    	/* DEMO ONLY */
		        System.err.println("SQLException: " + e.getMessage());	    	
		    	e.printStackTrace();
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
					
				String sqlQuery = "SELECT belongs_to, " + 
					"src_attribute, src_transformation, " +
					"tgt_attribute, tgt_transformation, " +
					"rslt_transformation, " +
					"type " +
					"FROM attribute_maps " +
					"WHERE id=\"" + this.getEntityID() + "\"";
				
				rSet = stmt.executeQuery( sqlQuery );
					
				while (rSet.next()) {
					setDsBelongsTo(rSet.getInt("belongs_to"));
					setDsSourceAttribute(rSet.getInt("src_attribute"));
					setSourceTransformation(rSet.getString("src_transformation"));
					setDsTargetAttribute(rSet.getInt("tgt_attribute"));
					setTargetTransformation(rSet.getString("tgt_transformation"));
					setResultTransformation(rSet.getString("rslt_transformation"));
					setAttributeMapType(AttributeMapType.getItem(rSet.getInt("type")));
					
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

		
		if (entity_id > 0) {
			/*
			 *	Update
			 */
			try {
				connection.setAutoCommit(false);
				
				sqlQuery = "UPDATE attribute_maps SET " 
					+ "belongs_to = " 			+ ((getBelongsTo() != null) ? getBelongsTo().getEntityID() : "NULL") + ", "
					+ "src_attribute = " 		+ this.getSourceAttribute().getEntityID() 		+ ", ";				
				
				if (getSourceTransformation() != null) {
					sqlQuery = sqlQuery + "src_transformation = "	+ this.getSourceTransformation().getEntityID()	+ ", ";
				};
				
				sqlQuery = sqlQuery + "tgt_attribute = "			
					+ ((getTargetAttribute() != null) ? getTargetAttribute().getEntityID() : "NULL") 	+ ", ";
								
				if (getTargetTransformation() != null) {
					sqlQuery = sqlQuery + "tgt_transformation = "	+ this.getTargetTransformation().getEntityID()	+ ", ";
				};
				
				if (getResultTransformation() != null) {
					sqlQuery = sqlQuery + "rslt_transformation = "	+ this.getResultTransformation().getEntityID()	+ ", ";
				};
				
				sqlQuery = sqlQuery + "type = " 		+ this.getAttributeMapType().getID()			+ " "
				    + "WHERE id = "	+ this.getEntityID();
				
				stmt = connection.createStatement();
				rows = stmt.executeUpdate(sqlQuery);
				
				if (rows < 1)				
					storeStatus = false;
				
				stmt.close();
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
					sqlQuery = "INSERT INTO attribute_maps (";
					sqlQuery += "src_attribute, ";
					sqlQuery += "src_transformation, ";
					sqlQuery += "tgt_attribute, ";
					sqlQuery += "tgt_transformation, ";
					sqlQuery += "rslt_transformation, ";
					sqlQuery += "type, ";
					sqlQuery += "belongs_to";
					sqlQuery += ") VALUES (" 																				
						+ getSourceAttribute().getEntityID()														+","
						+ ((getSourceTransformation() != null) ? getSourceTransformation().getEntityID() : "NULL")	+","
						+ ((getTargetAttribute() != null) ? getTargetAttribute().getEntityID() : "NULL")			+","
						+ ((getTargetTransformation() != null) ? getTargetTransformation().getEntityID() : "NULL")	+","
						+ ((getResultTransformation() != null) ? getResultTransformation().getEntityID() : "NULL")	+","
						+ getAttributeMapType().getID() + ","
						+ ((getBelongsTo() != null) ? getBelongsTo().getEntityID() : "NULL");
					sqlQuery += ")";					
					
					stmt = connection.createStatement();
					rows = stmt.executeUpdate(sqlQuery);
					
					if (rows < 1)
						storeStatus = false;
					else {					
					    rsltst = stmt.getGeneratedKeys();
		
					    if (rsltst.next()) {
					        this.setEntityID(rsltst.getInt(1));
					    } else {
					    	storeStatus = false;
					    }
					    rsltst.close();
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
			
//			storeStatus = true;
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
			JOptionPane.showMessageDialog(null, "Error! No Connection to DataBase.");
			
			return removeStatus;
		}
		
		if (entity_id > 0) {
			try {
				sqlQuery = "DELETE from attribute_maps " +
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
	
}


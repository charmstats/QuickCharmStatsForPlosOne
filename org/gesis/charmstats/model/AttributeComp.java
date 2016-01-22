package org.gesis.charmstats.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.gesis.charmstats.persistence.DBEntity;

/**
 *	This is an Entity-Class 
 *
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class AttributeComp extends DBEntity {

	/* charmstats.attribute_comps (MySQL)
		`id`			int(11) NOT NULL auto_increment,
		`src_attribute` int(11)			 default NULL,
		`tgt_attribute` int(11)			 default NULL,
		`bias_metadata` int(11)			 default NULL,
		`belongs_to` 	int(11)			 default NULL,
		PRIMARY KEY  (`id`)
	*/
	
	/*
	 *	Column names and other text constants
	 */
	public static final String table_name		= "attribute_comps";
	public static final String primary_key		= "id";	
	public static final String blank			= " ";
	public static final String comma			= ",";
	public static final String equals			= "=";
	public static final String cn_src_att		= "src_attribute";
	public static final String cn_tgt_att		= "tgt_attribute";	
	public static final String cn_bia_met		= "bias_metadata";
	public static final String cn_bel_to		= "belongs_to";
	
	/*
	 *	Fields
	 */
	private AttributeLink	sourceAttribute;	/*Reference to stored Attribute / WorkStepInstance (Source)*/	
	private AttributeLink	targetAttribute;	/*Reference to stored Attribute / WorkStepInstance (Target)*/
	private BiasMetadata	biasMetadata;
	
	private Project			belongsTo;
	
	private int				ds_src_att_id;
	private int				ds_tgt_att_id;
	private int				ds_bia_met_id;
	
	/*
	 *	Constructor
	 */
	
	/*
	 *	Methods
	 */
	/* sourceAttribute */
	/**
	 * @return
	 */
	public AttributeLink getSourceAttribute() {
		return sourceAttribute;
	}
	
	/**
	 * @param sourceAttribute
	 */
	public void setSourceAttribute(AttributeLink sourceAttribute) {
		this.sourceAttribute = sourceAttribute;
	}
	
	/* targetAttribute */
	/**
	 * @return
	 */
	public AttributeLink getTargetAttribute() {
		return targetAttribute;
	}
	
	/**
	 * @param targetAttribute
	 */
	public void setTargetAttribute(AttributeLink targetAttribute) {
		this.targetAttribute = targetAttribute;
	}
	
	/* biasMetadata */
	/**
	 * @return
	 */
	public BiasMetadata getBiasMetadata() {
		return biasMetadata;
	}
	
	/**
	 * @param biasMetadata
	 */
	public void setBiasMetadata(BiasMetadata biasMetadata) {
		this.biasMetadata = biasMetadata;
	}
	
	/* belongsTo */
	/**
	 * @return
	 */
	public Project getBelongsTo() {
		return belongsTo;
	}

	/**
	 * @param belongsTo
	 */
	public void setBelongsTo(Project belongsTo) {
		this.belongsTo = belongsTo;
	}
	
	/* ds_src_att_id */
	/**
	 * @return
	 */
	public int getDsSourceAttributeEntry() {
		return this.ds_src_att_id;
	}

	/* ds_tgt_att_id */
	/**
	 * @return
	 */
	public int getDsTargetAttributeEntry() {
		return this.ds_tgt_att_id;
	}
	
	/*
	 *	Load and Store to DB
	 */
	/**
	 * @param connection
	 * @return
	 */
	public ArrayList<Integer> loadComps(Connection connection) {
		ArrayList<Integer> list = new ArrayList<Integer>();

		if (connection.equals(null)) {
			list = null;
			JOptionPane.showMessageDialog(null, "No Connection to DataBase!",
					"Error:", JOptionPane.ERROR_MESSAGE);

			/* DEMO ONLY */
			System.err.println("Error: No Connection to DataBase!");

			return list;
		}

		if (getBelongsTo() instanceof Project)
			if (getBelongsTo().getEntityID() > 0) {
				try {
					if (!connection.isClosed()) {
						stmt = connection.createStatement();

						sqlQuery = "SELECT" + blank + primary_key + blank
								+ "FROM" + blank + table_name + blank + "WHERE"
								+ blank + cn_bel_to + blank + equals + blank
								+ getBelongsTo().getEntityID();

						rsltst = stmt.executeQuery(sqlQuery);

						while (rsltst.next()) {
							list.add(rsltst.getInt("id"));
						}

						stmt.close();
					} else {
						JOptionPane.showMessageDialog(null,
								"No open Connection!", "Error:",
								JOptionPane.ERROR_MESSAGE);

						/* DEMO ONLY */
						System.err.println("Error: No open Connection!");
					}
				} catch (SQLException e) {
					list = null;
					JOptionPane.showMessageDialog(null, e.getMessage(),
							"SQLException:", JOptionPane.ERROR_MESSAGE);

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
					
				String sqlQuery = "SELECT"+blank+
					cn_src_att+comma+blank+cn_tgt_att+comma+blank+cn_bia_met+blank+
					"FROM"+blank+table_name+blank +
					"WHERE"+blank+primary_key+equals+this.getEntityID();
					
				rSet = stmt.executeQuery( sqlQuery );
					
				while (rSet.next()) {
					ds_src_att_id = rSet.getInt(cn_src_att);
					ds_tgt_att_id = rSet.getInt(cn_tgt_att);
					ds_bia_met_id = rSet.getInt(cn_bia_met);
					loadStatus = true;
					
					if (loadStatus) {
						biasMetadata = new BiasMetadata();
						biasMetadata.setEntityID(ds_bia_met_id);
						
						loadStatus = biasMetadata.entityLoad(connection);
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
				
				if (storeStatus)
					storeStatus = biasMetadata.entityStore(connection);
				
				if (storeStatus) {
					sqlQuery = "UPDATE"+blank+ table_name +blank+"SET"+blank+
						cn_src_att+blank+equals+blank+"'"+ this.getSourceAttribute().getEntityID() +"'"+comma+blank+
						cn_tgt_att+blank+equals+blank+"'"+ this.getTargetAttribute().getEntityID() +"'"+comma+blank+
						cn_bia_met+blank+equals+blank+"'"+ this.getBiasMetadata().getEntityID() +"'"+comma+blank+
						cn_bel_to+blank+equals+blank+"'"+ this.getBelongsTo().getEntityID() +"'"+blank+
					    "WHERE"+blank+ primary_key +blank+equals+blank+ this.getEntityID();
						
					stmt = connection.createStatement();
					rows = stmt.executeUpdate(sqlQuery);
					
					if (rows < 1)				
						storeStatus = false;
					
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
		} else {
			/*
			 *	Insert
			 */
			try {
				connection.setAutoCommit(false);
								
				if (storeStatus)
					storeStatus = biasMetadata.entityStore(connection);
				
				if (storeStatus) {
					/*
					 *	Use Insert-Statement
					 */		
					sqlQuery = "INSERT INTO"+blank+ table_name +blank+"("+cn_src_att+comma+blank+cn_tgt_att+comma+blank+cn_bia_met+comma+blank+cn_bel_to+
						")"+blank+"VALUES"+blank+"("+
						"'"+ this.getSourceAttribute().getEntityID() +"'"+comma+
						"'"+ this.getTargetAttribute().getEntityID() +"'"+comma+
						"'"+ this.getBiasMetadata().getEntityID() +"'"+comma+
						"'"+ this.getBelongsTo().getEntityID() +"'"+
						")";
					
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
				if (removeStatus)
					removeStatus = biasMetadata.entityRemove(connection);
				
				if (removeStatus) {
					sqlQuery = "DELETE from"+blank+table_name+blank+
						"WHERE"+blank+primary_key+blank+equals+blank+this.getEntityID();
					
					stmt = connection.createStatement();
					rows = stmt.executeUpdate(sqlQuery);
					
					if (rows < 1)				
						removeStatus = false;
					
					stmt.close();
				}
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

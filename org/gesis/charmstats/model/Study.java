package org.gesis.charmstats.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

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
public class Study extends DBEntity {
	
	/* charmstats.studies (MySQL)
	  `id`					  int(11)	   NOT NULL auto_increment,
	  `title`				  varchar(256) 		 	default NULL,
	  `type` 				  int(11)				default NULL,
	  `institution`			  varchar(255)			default NULL,
	  `copyright`			  varchar(255)			default NULL,
	  `primary_investigators` varchar(2048)			default NULL,
	  `date_of_collection` 	  datetime				default NULL,
	  `study_area`			  varchar(255)			default NULL,
	  `population`			  varchar(255)			default NULL,
	  `selection`			  varchar(255)			default NULL,
	  `collection_method`	  varchar(255)			default NULL,
	  `collectors`			  varchar(255)			default NULL,
	  PRIMARY KEY  (`id`)
	*/
	
	/*
	 *	Column names and other text constants
	 */
	public static final String table_name = 	"studies";
	public static final String primary_key = 	"id";
	public static final String blank = 			" ";
	public static final String comma = 			",";
	public static final String equals_sign = 	"=";
	public static final String cn_tit			= "title";
	public static final String cn_typ			= "type";
	public static final String cn_doi			= "doi";
	public static final String cn_ins			= "institution";
	public static final String cn_cop 			= "copyright";	
	public static final String cn_pri_inv		= "primary_investigators";
	public static final String cn_dat_of_col	= "date_of_collection";
	public static final String cn_stu_are		= "study_area";
	public static final String cn_pop			= "population";
	public static final String cn_sel			= "selection";
	public static final String cn_col_met		= "collection_method";
	public static final String cn_col			= "collectors";
	public static final String cn_src_fil		= "source_file";
	public static final String cn_dat			= "dataset"; 
	
	/*
	 *	Fields
	 */
	private String 				title;	
	private StudyType			type;
	private	String				doi;
	
	private String				institution;
	private String				copyright;
	private String				primary_investigators;
//	private Person				primary_investigator;
//	private ArrayList<Person>	primary_investigators; 
	private String				date_of_collection;
	private String				study_area;
	private String				population;
	private String				selection;
	private String				collection_method;
	private String 				collectors;
	private String				source_file;
	private String				dataset;
	
	private Definition			definition;

	// universe includes study area, population, collection date
	// universe beinhaltet Untersuchungsgebiet, Grundgesamtheit und Erhebungszeitraum
	// private UniverseOld			universe; 


	/*
	 *	Constructor
	 */
	/**
	 * 
	 */
	public Study () {
		super();
		
		// universe = new UniverseOld();
		definition	= new Definition();
		
		entity_type = EntityType.STUDIES;
	}
	
	/*
	 *	Methods
	 */
	/* Label */
	/**
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	/* Category */
	/**
	 * @param type
	 */
	public void setType(StudyType type) {
		this.type = type;
	}

	/**
	 * @return
	 */
	public StudyType getType() {
		return type;
	}
	
	/* DOI */
	/**
	 * @param doi
	 */
	public void setDOI(String doi) {
		this.doi = doi;
	}
	
	/**
	 * @return
	 */
	public String getDOI() {
		return doi;
	}

	/* Institution */
	/**
	 * @param institution
	 */
	public void setInstitution(String institution) {
		this.institution = institution;
	}

	/**
	 * @return
	 */
	public String getInstitution() {
		return institution;
	}

	/* Copyright */
	/**
	 * @param copyright
	 */
	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	/**
	 * @return
	 */
	public String getCopyright() {
		return copyright;
	}

	/* Primary Investigators */
	/**
	 * @param primary_investigators
	 */
	public void setPrimaryInvestigators(String primary_investigators) {
		this.primary_investigators = primary_investigators;
	}

	/**
	 * @return
	 */
	public String getPrimaryInvestigators() {
		return primary_investigators;
	}

	/* Date of Collection */
	/**
	 * @param date_of_collection
	 */
	public void setDateOfCollection(String date_of_collection) {
		this.date_of_collection = date_of_collection;
	}

	/**
	 * @return
	 */
	public String getDateOfCollection() {
		return date_of_collection;
	}
	
	/* Study Area */
	/**
	 * @return
	 */
	public String getStudyArea() {
		return study_area;
	}

	/**
	 * @param study_area
	 */
	public void setStudyArea(String study_area) {
		this.study_area = study_area;
	}

	/* Population */
	/**
	 * @return
	 */
	public String getPopulation() {
		return population;
	}

	/**
	 * @param population
	 */
	public void setPopulation(String population) {
		this.population = population;
	}

	/* Selection */
	/**
	 * @return
	 */
	public String getSelection() {
		return selection;
	}

	/**
	 * @param selection
	 */
	public void setSelection(String selection) {
		this.selection = selection;
	}

	/* Collection Method */
	/**
	 * @return
	 */
	public String getCollectionMethod() {
		return collection_method;
	}

	/**
	 * @param collection_method
	 */
	public void setCollectionMethod(String collection_method) {
		this.collection_method = collection_method;
	}
	
	/* Collectors */
	/**
	 * @return
	 */
	public String getCollectors() {
		return collectors;
	}

	/**
	 * @param collectors
	 */
	public void setCollectors(String collectors) {
		this.collectors = collectors;
	}
	
	/* Source file */
	/**
	 * @param source_file
	 */
	public void setSourceFile(String source_file) {
		this.source_file = source_file;
	}
	
	/**
	 * @return
	 */
	public String getSourceFile() {
		return source_file;
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

//	/* Universe */
//	public UniverseOld getUniverse(Connection connection) {
//		UniverseOld uni = null;
//		
//		StudyRef ref = new StudyRef();
//		ref.setReferenced(new UniverseOld());
//		ref.setStudy(this);
////		ArrayList<Integer> unis = ref.getUniverseID(connection);
////		if (unis.size() > 0) {
////			uni = new Universe(); uni.setID(unis.get(0));
////			uni.entityLoad(connection);
////		}
//		
//		return uni;
//	}
//	
//	public UniverseOld getUniverse() {
//		return universe;
//	}
//	
//	public UniverseOld getTempUniverse() {
//		UniverseOld universe = null;
//		
//		Iterator<UniverseOld> subunis_it = this.universe.getSubUniverse().iterator();
//		while(subunis_it.hasNext()) {
//			@SuppressWarnings("unused")
//			UniverseOld uni_next = subunis_it.next();
////			if (uni_next.getCategorie().getID() == 4)
////				universe = uni_next;
//		}
//		
//		return universe;
//	}
//	
//	public UniverseOld getGeoUniverse() {
//		UniverseOld universe = null;
//		
//		Iterator<UniverseOld> subunis_it = this.universe.getSubUniverse().iterator();
//		while(subunis_it.hasNext()) {
//			@SuppressWarnings("unused")
//			UniverseOld uni_next = subunis_it.next();
////			if (uni_next.getCategorie().getID() == 1)
////				universe = uni_next;
//		}
//		
//		return universe;
//	}
//
//	public void setUniverse(UniverseOld universe) {
//		this.universe = universe;
//	}

//	public String getDefaultLabel() {
//		// Auto-generated method stub
//		return null;
//	}

	public Definition getDefinition() {
		return definition;
	}

	public void setDefinition(Definition definition) {
		this.definition = definition;
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
					
				String sqlQuery = "SELECT" +blank+cn_tit+comma+cn_typ+comma+cn_doi+comma+
						cn_ins+comma+cn_cop+comma+cn_pri_inv+comma+
						cn_dat_of_col+comma+cn_stu_are+comma+cn_pop+comma+cn_sel+comma+cn_col_met+comma+cn_col+comma+
						cn_src_fil+comma+cn_dat+blank+
					"FROM" +blank+table_name+blank+
					"WHERE" +blank+primary_key+blank +"=\"" + this.getEntityID() +"\"";
				
				rSet = stmt.executeQuery( sqlQuery );
					
				while (rSet.next()) {
					setTitle(rSet.getString(cn_tit));
					setType(StudyType.getItem(rSet.getInt(cn_typ)));
					setDOI(rSet.getString(cn_doi));
					setInstitution(rSet.getString(cn_ins));
					setCopyright(rSet.getString(cn_cop));
					setPrimaryInvestigators(rSet.getString(cn_pri_inv));
					setDateOfCollection(rSet.getString(cn_dat_of_col));
					setStudyArea(rSet.getString(cn_stu_are));
					setPopulation(rSet.getString(cn_pop));
					setSelection(rSet.getString(cn_sel));
					setCollectionMethod(rSet.getString(cn_col_met));
					setCollectors(rSet.getString(cn_col));
					setSourceFile(rSet.getString(cn_src_fil));
					setDataset(rSet.getString(cn_dat));

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
				
				sqlQuery = "UPDATE studies SET "
					+ "title = ?, "
					+ "doi = ?, "
					+ "date_of_collection = ?, "	
					+ "study_area = ?, "
					+ "population = ?, "
					+ "selection = ?, "
					+ "collection_method = ?, "
					+ "collectors = ?, "
					+ "source_file = ?, "
					+ "dataset = ? "
					+ "WHERE id = ?";
					
				prepStmt = connection.prepareStatement(sqlQuery);
				prepStmt.setString( 1, DBField.STU_TIT.truncate(getTitle()));
				prepStmt.setString( 2, DBField.STU_DOI.truncate(getDOI()));
				prepStmt.setString( 3, DBField.STU_DAT_OF_COL.truncate(getDateOfCollection()));
				prepStmt.setString( 4, DBField.STU_STU_ARE.truncate(getStudyArea()));
				prepStmt.setString( 5, DBField.STU_POP.truncate(getPopulation()));
				prepStmt.setString( 6, DBField.STU_SEL.truncate(getSelection()));
				prepStmt.setString( 7, DBField.STU_COL_MET.truncate(getCollectionMethod()));
				prepStmt.setString( 8, DBField.STU_COL.truncate(getCollectors()));
				prepStmt.setString( 9, DBField.STU_SRC_FIL.truncate(getSourceFile()));
				prepStmt.setString(10, DBField.STU_DAT.truncate(getDataset()));
				prepStmt.setInt(11, getEntityID());
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

				if (storeStatus) {
					/*
					 *	Use Insert-Statement
					 */		
					sqlQuery = "INSERT INTO studies (title, doi, ";
					sqlQuery += "date_of_collection, study_area, population, selection, collection_method, collectors, ";
					sqlQuery += "source_file, dataset";
					sqlQuery += ") VALUES ("
						+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?";
					sqlQuery += ")";
					prepStmt = connection.prepareStatement(sqlQuery);
					prepStmt.setString( 1, DBField.STU_TIT.truncate(getTitle()));
					prepStmt.setString( 2, DBField.STU_DOI.truncate(getDOI()));
					prepStmt.setString( 3, DBField.STU_DAT_OF_COL.truncate(getDateOfCollection()));
					prepStmt.setString( 4, DBField.STU_STU_ARE.truncate(getStudyArea()));
					prepStmt.setString( 5, DBField.STU_POP.truncate(getPopulation()));
					prepStmt.setString( 6, DBField.STU_SEL.truncate(getSelection()));
					prepStmt.setString( 7, DBField.STU_COL_MET.truncate(getCollectionMethod()));
					prepStmt.setString( 8, DBField.STU_COL.truncate(getCollectors()));
					prepStmt.setString( 9, DBField.STU_SRC_FIL.truncate(getSourceFile()));
					prepStmt.setString(10, DBField.STU_DAT.truncate(getDataset()));
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

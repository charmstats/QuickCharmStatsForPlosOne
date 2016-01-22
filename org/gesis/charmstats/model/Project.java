package org.gesis.charmstats.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.swing.JOptionPane;

import org.gesis.charmstats.controller.CStatsCtrl;
import org.gesis.charmstats.persistence.DBEntity;
import org.gesis.charmstats.persistence.DBField;
import org.gesis.charmstats.persistence.EntityType;
import org.gesis.charmstats.persistence.RefRelationtype;

/**
 *	This is an Entity-Class
 * 
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class Project extends DBEntity implements Comparable {

	/* charmstats.projects (MySQL)
	  `id`					 int(11) NOT NULL auto_increment,
	  `name`				 varchar(255)	  default NULL,
	  `type`				 int(11) 		  default NULL,
	  `active`				 tinyint(1) 	  default '0',
	  `saved`				 tinyint(1) 	  default '0',
	  `published_since` 	 datetime 		  default NULL,
	  `obsolete_since`		 datetime 		  default NULL,
	  `progress`			 int(11) 		  default NULL,
	  `target_name`			 varchar(255) 	  default NULL,
	  `target_label`		 varchar(255) 	  default NULL,
	  `measurement_imported` tinyint(1) 	  default '0',
	  `template_path` 		 varchar(256) 	  default NULL,
	  PRIMARY KEY  (`id`)
	*/
	
	/*
	 *	Column names and other text constants
	 */
	public static final String PROJECT		= "Project";
	public static final String TYPE			= "Type";
	public static final String MEASUREMENT	= "Measurement";
	public static final String CONCEPT		= "Concept";
	public static final String OBSOLETE		= "Obsolete since";
	public static final String PUBLISHED	= "Publishd since";
	
	public static final String NEW_VERSION	= "0.1";	
	
	/*
	 *	Fields
	 */
	private String 					name;
	private ProjectType 			type;
	
	private String					doi;				/* new in 0.9.9 */
	
	private Comment					summary;
	private Concept					concept;
	
	private String					project_version;	/* version of project */
	private String 					model_version;		/* version of the generated model */
	
	private ProContent 				content; 
	
	private boolean 				active;
	private boolean 				saved;
	
	private Date 					finished_since;		/* former published_since */
	private Date 					obsolete_since;
	private Date 					submitted_since;	/* new in 0.9.9 */
	private Date					published_since;	/* new in 0.9.9 */
	
	private boolean					editedByUser;
	
	private Progress				progress; 
	
	private ArrayList<Participant> 	participants;
	
	private String					var_name;
	private String					var_label;
	
	private boolean					measurement_imported;
	
	private User					projectOwner;
	
	private String					ddi_sup_exp_var_scheme_uuid;
	private String					ddi_sup_exp_var_uuid;
	private String					ddi_sup_imp_var_scheme_uuid;
	private String					ddi_sup_exp_que_scheme_uuid;
	private String					ddi_sup_exp_que_uuid;
	private String					ddi_sup_imp_que_scheme_uuid;
	
	private String					projectTemplate					= ""; // Only path is stored
	
	/*
	 *	Constructor
	 */
	/* Used by 'New Project': */
	/**
	 * 
	 */
	public Project() {
		super();
		
		setEditedByUser(true);	

		entity_type = EntityType.PROJECTS;
		
		setName(new String());
		setType(ProjectType.NONE);
		setDOI(new String());
		
		setVersion(NEW_VERSION); 
		
		setContent(new ProContent());
//		getContent().setConcept(new Concept(new Label(""),new Description("")));
		setConcept(new Concept(new Label(""),new Description("")));
		getContent().setMeasurement(new Measurement());
		Comment proNote = new Comment();
		proNote.setReference(this);
//		getContent().setSummary(proNote);
		setSummary(proNote);
		setMeasurementImported(false);
		
		ddi_sup_exp_var_scheme_uuid = String.valueOf(UUID.randomUUID());
		ddi_sup_exp_var_uuid		= String.valueOf(UUID.randomUUID());
		ddi_sup_imp_var_scheme_uuid = String.valueOf(UUID.randomUUID());
		ddi_sup_exp_que_scheme_uuid = String.valueOf(UUID.randomUUID());
		ddi_sup_exp_que_uuid		= String.valueOf(UUID.randomUUID());
		ddi_sup_imp_que_scheme_uuid = String.valueOf(UUID.randomUUID());
		
		/* Tab Comments */
		TabDummy tabDum = new TabDummy(EntityType.TAB_PROJECT, getEntityID());
		getContent().setTabProject(tabDum);
		
		tabDum = new TabDummy(EntityType.TAB_CONCEPT, getEntityID()); 
		getContent().setTabConcept(tabDum);
		
		tabDum = new TabDummy(EntityType.TAB_LITERATURE, getEntityID());
		getContent().setTabLiterature(tabDum);
		
		tabDum = new TabDummy(EntityType.TAB_MEASUREMENT, getEntityID());
		getContent().setTabMeasurement(tabDum);
		
		tabDum = new TabDummy(EntityType.TAB_DIMENSION, getEntityID());
		getContent().setTabDimension(tabDum);
		
		tabDum = new TabDummy(EntityType.TAB_SPECIFICATION, getEntityID());
		getContent().setTabSpecification(tabDum);
		
		tabDum = new TabDummy(EntityType.TAB_MAP_DIMENSION_INSTANCE, getEntityID());
		getContent().setTabMapDimensionInstance(tabDum);
		
		tabDum = new TabDummy(EntityType.TAB_MAP_DIMENSION_ATTRIBUTE, getEntityID());
		getContent().setTabMapDimensionAttribute(tabDum);
		
		tabDum = new TabDummy(EntityType.TAB_MAP_DIMENSION_CHAR, getEntityID());
		getContent().setTabMapDimensionChar(tabDum);
		
		tabDum = new TabDummy(EntityType.TAB_OS_INSTANCE, getEntityID());
		getContent().setTabOSInstance(tabDum);
		
		tabDum = new TabDummy(EntityType.TAB_INDICATOR, getEntityID());
		getContent().setTabIndicator(tabDum);
		
		tabDum = new TabDummy(EntityType.TAB_PRESCRIPTION, getEntityID());
		getContent().setTabPrescription(tabDum);
		
		tabDum = new TabDummy(EntityType.TAB_MAP_INDICATOR_INSTANCE, getEntityID());
		getContent().setTabMapIndicatorInstance(tabDum);
		
		tabDum = new TabDummy(EntityType.TAB_MAP_INDICATOR_ATTRIBUTE, getEntityID());
		getContent().setTabMapIndicatorAttribute(tabDum);
		
		tabDum = new TabDummy(EntityType.TAB_MAP_INDICATOR_CHAR, getEntityID());
		getContent().setTabMapIndicatorChar(tabDum);
		
		tabDum = new TabDummy(EntityType.TAB_SEARCH_VARIABLE, getEntityID());
		getContent().setTabSearchVariable(tabDum);
		
		tabDum = new TabDummy(EntityType.TAB_COMPARE_VARIABLES, getEntityID());
		getContent().setTabCompareVariables(tabDum);
		
		tabDum = new TabDummy(EntityType.TAB_COMPARE_VALUES, getEntityID());
		getContent().setTabCompareValues(tabDum);
		
		tabDum = new TabDummy(EntityType.TAB_DR_INSTANCE, getEntityID());
		getContent().setTabDRInstance(tabDum);
		
		tabDum = new TabDummy(EntityType.TAB_VARIABLE, getEntityID());
		getContent().setTabVariable(tabDum);
		
		tabDum = new TabDummy(EntityType.TAB_VALUE, getEntityID());
		getContent().setTabValue(tabDum);
		
		tabDum = new TabDummy(EntityType.TAB_MAP_VARIABLE_INSTANCE, getEntityID());
		getContent().setTabMapVariableInstance(tabDum);
		
		tabDum = new TabDummy(EntityType.TAB_MAP_VARIABLE_ATTRIBUTE, getEntityID());
		getContent().setTabMapVariableAttribute(tabDum);
		
		tabDum = new TabDummy(EntityType.TAB_MAP_VARIABLE_CHAR, getEntityID());
		getContent().setTabMapVariableChar(tabDum);
		
		
		WorkStepInstance visualize = new WorkStepInstance(InstanceType.VISUALIZE);
		visualize.setLabel("Visualize");
		visualize.setEntityID(-100);
		getContent().addLayer(visualize);
		InstanceLink visualizeRef = new InstanceLink(visualize);
		visualizeRef.setWorkStepRefType(InstanceLinkType.VISUALIZE);
		visualizeRef.setProject(this);
		visualizeRef.setEntityID(-100);
		getContent().addInstanceRef(visualizeRef);
		
		WorkStepInstance setup = new WorkStepInstance(InstanceType.PROJECT_SETUP); 
		setup.setLabel("Setup");
		setup.setEntityID(-101);
		getContent().addLayer(setup);
		InstanceLink setupRef = new InstanceLink(setup);
		setupRef.setWorkStepRefType(InstanceLinkType.PROJECT_SETUP);
		setupRef.setProject(this);
		setupRef.setEntityID(-101);
		getContent().addInstanceRef(setupRef);
		
		WorkStepInstance conceptual = new WorkStepInstance(InstanceType.CONCEPTUAL); 
		conceptual.setLabel("Conceptual");
		conceptual.setEntityID(-102);
		getContent().addLayer(conceptual);
		InstanceLink conceptualRef = new InstanceLink(conceptual); 
		conceptualRef.setWorkStepRefType(InstanceLinkType.CONCEPTUAL);
		conceptualRef.setProject(this);
		conceptualRef.setEntityID(-102);
		getContent().addInstanceRef(conceptualRef);	
		
		InstanceMap conceptualMap = new InstanceMap(conceptualRef, setupRef, InstanceMapType.CONCEPTUAL);
		conceptualMap.setBelongsTo(this);
		conceptualMap.setEntityID(-102);
		getContent().addInstanceMap(conceptualMap); 
		
		AttributeLink link = new AttributeLink();
		link.setEntityID(-100);			
		link.setAttribute(getContent().getMeasurement());
		link.setAttributeLinkType(AttributeLinkType.MEASUREMENT);
		link.setInstance(setup);
		
		setParticipants(new ArrayList<Participant>());
		getConcept().setLiteratures(new ArrayList<Literature>());
		
		active = 	false;
		saved = 	false;
		
		setFinishedSince(null);
		setObsoleteSince(null);
		setProgress(new Progress());		
		getProgress().setProjectSetupStepTabbedPanelAvailable(true);
		getProgress().setProjectTabDone(true);
		
	}
	
	/* Used by 'Open Project': */
	/**
	 * @param connection
	 * @param pk
	 */
	public Project(Connection connection, int pk) {
		this();
		
		setEntityID(pk);
		
		if (getEntityID() > 0)
			entityLoad(connection);
	}
	
	/* Used by loadLayers etc. in ProContent: */
	/**
	 * @param pk
	 */
	public Project(int pk) {
		this();
		
		this.setEntityID(pk);
	}
	
	/*
	 *	Methods
	 */
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return getName();
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
	
	/* Note */
	/**
	 * @param summary
	 */
	public void setSummary(Comment summary) {
		this.summary = summary;
	}
	
	/**
	 * @return
	 */
	public Comment getSummary() {	 
		return summary;
	}
	
	/**
	 * @param concept
	 */
	public void setConcept(Concept concept) {
		this.concept = concept;
	}
	
	/**
	 * @return
	 */
	public Concept getConcept() {
		return concept;
	}
	
	/* Type */
	/**
	 * @param type
	 */
	public void setType(ProjectType type) {
		this.type = type;
	}

	/**
	 * @return
	 */
	public ProjectType getType() {
		return type;
	}
	
	/* Version */
	/**
	 * @param version
	 */
	public void setVersion(String version) {
		this.project_version = version;
	}

	/**
	 * @return
	 */
	public String getVersion() {
		return project_version;
	}
	
	/* Version */
	/**
	 * @param version
	 */
	public void setModelVersion(String version) {
		this.model_version = version;
	}

	/**
	 * @return
	 */
	public String getModelVersion() {
		return model_version;
	}
	
	/* Content */
	/**
	 * @param content
	 */
	public void setContent(ProContent content) {
		this.content = content;
	}

	/**
	 * @return
	 */
	public ProContent getContent() {
		return content;
	}

	/* Participant */
	/**
	 * @param participants
	 */
	public void setParticipants(ArrayList<Participant> participants) {
		this.participants = participants;
	}
	
	/**
	 * @param participant
	 */
	public void addParticipant(Participant participant) {
		participants.add(participant);
	}
	
	/**
	 * @return
	 */
	public ArrayList<Participant> getParticipants() {
		return participants;
	}
	
	/**
	 * @param index
	 * @return
	 */
	public Participant getParticipantByIndex(int index) {
		Participant result = null;
		
		try {
			result = participants.get(index);
		} catch (IndexOutOfBoundsException e) {
	    	/* DEMO ONLY */
	    	System.err.println("SQLException: " + e.getMessage());
	    	e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * @param participant_id
	 * @return
	 */
	public Participant getParticipantByID(int participant_id) {
		Participant result = null;
		
		Iterator<Participant> it_participant = participants.iterator();
			
		while (it_participant.hasNext()) {
			Participant participant = it_participant.next();
			
			if (participant.getEntityID() == participant_id) {
				result = participant; break;
			}
		}
		
		return result;
	}
	
	/**
	 * @param user
	 * @return
	 */
	public Participant getParticipantByUser(User user) {
		Participant result = null;
		
		Iterator<Participant> it_participant = participants.iterator();
			
		while (it_participant.hasNext()) {
			Participant participant = it_participant.next();
			
			if (participant.getUserID() == user.getEntityID()) {
				result = participant; break;
			}
		}
		
		return result;
	}
			
	/* active */
	/**
	 * @param active
	 */
	public void setActivity(boolean active) {
		this.active = active;
	}

	// mySQL only:
	/**
	 * @param active
	 */
	public void setActivity(int active) {
		this.active = (active > 0);
	}
	
	/**
	 * @return
	 */
	public boolean isActive() {
		return active;
	}
	
	// mySQL only:
	/**
	 * @return
	 */
	public int getActive() {
		return (active ? 1 : 0);
	}

	/* saved */
	/**
	 * @param changed
	 */
	public void setSaved(boolean changed) {
		this.saved = changed;
	}
	
	// mySQL only:
	/**
	 * @param saved
	 */
	public void setSaved(int saved) {
		this.saved = (saved > 0);
	}

	/**
	 * @return
	 */
	public boolean isSaved() {
		return saved;
	}
	
	// mySQL only:
	/**
	 * @return
	 */
	public int getSaved() {
		return (saved ? 1 : 0);
	}
	
	/* Published Since */
	/**
	 * former setPublishedSince
	 * 
	 * @param finished_since
	 */
	public void setFinishedSince(Date finished_since) {
		this.finished_since = finished_since;
	}

	/**
	 * former getPublishedSince
	 * 
	 * @return
	 */
	public Date getFinishedSince() {
		return finished_since;
	}

	/* Obsolete Since */
	/**
	 * @param obsolete_since
	 */
	public void setObsoleteSince(Date obsolete_since) {
		this.obsolete_since = obsolete_since;
	}

	/**
	 * @return
	 */
	public Date getObsoleteSince() {
		return obsolete_since;
	}
	
	/* Submitted Since */
	/**
	 * Set from the online libray 
	 * 
	 * @param submitted_since
	 */
	public void setSubmittedSince(Date submitted_since) {
		this.submitted_since = submitted_since;
	}

	/**
	 * 
	 * @return
	 */
	public Date getSubmittedSince() {
		return submitted_since;
	}
	
	/* Published Since */
	/**
	 * Set from the online libray 
	 * 
	 * @param published_since
	 */
	public void setPublishedSince(Date published_since) {
		this.published_since = published_since;
	}

	/**
	 * 
	 * @return
	 */
	public Date getPublishedSince() {
		return published_since;
	}
	
	/* Progress */
	/**
	 * @return
	 */
	public Progress getProgress() {
		return progress;
	}

	/**
	 * @param progress
	 */
	public void setProgress(Progress progress) {
		this.progress = progress;
	}
	
	/* Target Name */
	/**
	 * @param name
	 */
	public void setTargetName(String name) {
		this.var_name = name;
	}

	/**
	 * @return
	 */
	public String getTargetName() {
		return var_name;
	}

	/* Target Label */
	/**
	 * @param label
	 */
	public void setTargetLabel(String label) {
		this.var_label = label;
	}

	/**
	 * @return
	 */
	public String getTargetLabel() {
		return var_label;
	}
	
	/* measurement imported */
	/**
	 * @param measurement_imported
	 */
	public void setMeasurementImported(boolean measurement_imported) {
		this.measurement_imported = measurement_imported;
	}
	
	// mySQL only:
	/**
	 * @param measurement_inmported
	 */
	public void setMeasurementImported(int measurement_inmported) {
		this.measurement_imported = (measurement_inmported > 0);
	}

	/**
	 * @return
	 */
	public boolean isMeasurementImported() {
		return measurement_imported;
	}
	
	// mySQL only:
	/**
	 * @return
	 */
	public int getMeasurementImported() {
		return (measurement_imported ? 1 : 0);
	}
	
	
	/* DOI */
	/**
	 * @return
	 */
	public String getDOI() {
		return doi;
	}

	/**
	 * @param doi
	 */
	public void setDOI(String doi) {
		this.doi = doi;
	}	
	
	/*
	 *	DDI Export Variable Scheme UUID
	 */
	/**
	 * @return
	 */
	public String getDDISupExpVarSchemeUUId() {
		return ddi_sup_exp_var_scheme_uuid;
	}

	/**
	 * @param ddi_sup_exp_var_scheme_uuid
	 */
	public void setDDISupExpVarSchemeUUId(String ddi_sup_exp_var_scheme_uuid) {
		this.ddi_sup_exp_var_scheme_uuid = ddi_sup_exp_var_scheme_uuid;
	}
	
	/*
	 *	DDI Export Variable  UUID
	 */
	/**
	 * @return
	 */
	public String getDDISupExpVarUUId() {
		return ddi_sup_exp_var_uuid;
	}

	/**
	 * @param ddi_sup_exp_var_uuid
	 */
	public void setDDISupExpVarUUId(String ddi_sup_exp_var_uuid) {
		this.ddi_sup_exp_var_uuid = ddi_sup_exp_var_uuid;
	}

	/*
	 *	DDI Import Variable Scheme UUID
	 */
	/**
	 * @return
	 */
	public String getDDISupImpVarSchemeUUId() {
		return ddi_sup_imp_var_scheme_uuid;
	}

	/**
	 * @param ddi_sup_imp_var_scheme_uuid
	 */
	public void setDDISupImpVarSchemeUUId(String ddi_sup_imp_var_scheme_uuid) {
		this.ddi_sup_imp_var_scheme_uuid = ddi_sup_imp_var_scheme_uuid;
	} 
	
	/*
	 *	DDI Export Question Scheme UUID
	 */
	/**
	 * @return
	 */
	public String getDDISupExpQueSchemeUUId() {
		return ddi_sup_exp_que_scheme_uuid;
	}

	/**
	 * @param ddi_sup_exp_que_scheme_uuid
	 */
	public void setDDISupExpQueSchemeUUId(String ddi_sup_exp_que_scheme_uuid) {
		this.ddi_sup_exp_que_scheme_uuid = ddi_sup_exp_que_scheme_uuid;
	}

	/*
	 *	DDI Export Question  UUID
	 */
	/**
	 * @return
	 */
	public String getDDISupExpQueUUId() {
		return ddi_sup_exp_que_uuid;
	}

	/**
	 * @param ddi_sup_exp_que_uuid
	 */
	public void setDDISupExpQueUUId(String ddi_sup_exp_que_uuid) {
		this.ddi_sup_exp_que_uuid = ddi_sup_exp_que_uuid;
	}

	/*
	 *	DDI Import Question Scheme UUID
	 */
	/**
	 * @return
	 */
	public String getDDISupImpQueSchemeUUId() {
		return ddi_sup_imp_que_scheme_uuid;
	}

	/**
	 * @param ddi_sup_imp_que_scheme_uuid
	 */
	public void setDDISupImpQueSchemeUUId(String ddi_sup_imp_que_scheme_uuid) {
		this.ddi_sup_imp_que_scheme_uuid = ddi_sup_imp_que_scheme_uuid;
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
					
				String sqlQuery = "SELECT p.name, p.type, p.published_since, p.obsolete_since, p.progress, p.target_name, p.target_label, p.measurement_imported, p.template_path, p.version, p.model_version, p.doi " +
						"FROM projects p " +
						"WHERE p.id=\"" + this.getEntityID() + "\"";	
				
				rSet = stmt.executeQuery( sqlQuery );
					
				while (rSet.next()) {
					this.setName(rSet.getString("name"));
					int projectType = rSet.getInt("type");
					if (!rSet.wasNull()) {
						this.setType(ProjectType.getItem(projectType));
					}
					this.setVersion(rSet.getString("version")); 
					this.setModelVersion(rSet.getString("model_version"));
					this.setDOI(rSet.getString("doi"));
					loadStatus = true; 
					
					int progress_id = rSet.getInt("progress");
					progress.setEntityID(progress_id);			
					loadStatus = 
						progress.entityLoad(connection);
					
					setFinishedSince(rSet.getDate("published_since"));
					setObsoleteSince(rSet.getDate("obsolete_since"));
					
					setTargetName(rSet.getString("target_name"));
					setTargetLabel(rSet.getString("target_label"));
					
					setMeasurementImported(rSet.getInt("measurement_imported"));
					
					setProjectTemplate(rSet.getString("template_path"));
				}
						
				/*
				 *	Summary
				 */
				sqlQuery = "SELECT c.id " +
						"FROM comments c " +
						"WHERE c.object_entity=\"" + getEntityType().getID() +"\" " +
							"AND c.object_entry=\"" + getEntityID() + "\" ";
				
				rSet = stmt.executeQuery( sqlQuery );
				
				while (rSet.next()) {
					setSummary(new Comment());
					getSummary().setEntityID(rSet.getInt("id"));				
					if (loadStatus)
						loadStatus = summary.entityLoad(connection);
					getSummary().setReference(this);
				}
				
				/*
				 *	Concept
				 */
				sqlQuery = "SELECT c.concept " +
					"FROM concept_refs c " +
					"WHERE c.entity=\"" + getEntityType().getID() +"\" " +
						"AND c.entry=\"" + getEntityID() + "\" ";	
				
				rSet = stmt.executeQuery( sqlQuery );
					
				while (rSet.next()) {
					setConcept(new Concept());
					getConcept().setEntityID(rSet.getInt("concept"));					
					if (loadStatus)
						loadStatus = getConcept().entityLoad(connection);
				}
				
				
				Participant par = new Participant();
				par.setProject(this);
				ArrayList<Integer> participants = par.listParticipants(connection);
				
				Iterator<Integer> iter_par = participants.iterator();
				while (iter_par.hasNext()) {
					Participant participant = new Participant();
					participant.setEntityID(iter_par.next());
					participant.entityLoad(connection);
					
					participant.setProject(this);
					
					if (!participant.getRole().equals(ParticipantRole.FORMER_MEMBER))
						addParticipant(participant);
				}
				
				Iterator<Participant> users = getParticipants().iterator();
				while (users.hasNext()) {
					Participant possibleOwner = users.next();
					
					if (possibleOwner.getRole().equals(ParticipantRole.PROJECT_OWNER))
						setProjectOwner(possibleOwner.getUser());
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
	
	/**
	 * @param connection
	 * @return
	 */
	public boolean loadProjectHeader(Connection connection) {

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
					
//				String sqlQuery = "SELECT p.name, p.type, p.published_since, p.obsolete_since, p.progress, p.target_name, p.target_label, p.measurement_imported, p.template_path, p.version, p.model_version " +
//					"FROM projects p " +
//					"WHERE p.id=\"" + this.getEntityID() + "\"";
				String sqlQuery = "SELECT p.name, p.type, p.published_since, p.obsolete_since, p.progress, p.target_name, p.target_label, p.measurement_imported, p.template_path, p.version, p.model_version, p.doi " +
						"FROM projects p " +
						"WHERE p.id=\"" + this.getEntityID() + "\"";
				
				rSet = stmt.executeQuery( sqlQuery );
					
				while (rSet.next()) {
					this.setName(rSet.getString("name"));
					int projectType = rSet.getInt("type");
					if (!rSet.wasNull()) {
						this.setType(ProjectType.getItem(projectType));
					}
					this.setVersion(rSet.getString("version")); 
					this.setModelVersion(rSet.getString("model_version"));
					this.setDOI(rSet.getString("doi"));
					loadStatus = true; 
					
					int progress_id = rSet.getInt("progress");
					progress.setEntityID(progress_id);			
					loadStatus = 
						progress.entityLoad(connection);
					
					setFinishedSince(rSet.getDate("published_since"));
					setObsoleteSince(rSet.getDate("obsolete_since"));
					
					setTargetName(rSet.getString("target_name"));
					setTargetLabel(rSet.getString("target_label"));
					
					setMeasurementImported(rSet.getInt("measurement_imported"));
					
					setProjectTemplate(rSet.getString("template_path"));
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
					storeStatus = progress.entityStore(connection);
				
				if (storeStatus)
					storeStatus = getSummary().entityStore(connection);
				
				if (storeStatus)
					storeStatus = getConcept().entityStore(connection);
								
				if (storeStatus) {
					sqlQuery = "UPDATE projects SET "
						+ "name = ?"
						+ ", type = ?"
						+ ", active = ?"
						+ ", saved = ?"
						+ ", target_name = ?"
						+ ", target_label = ?"
						+ ", measurement_imported = ?"
						+ ", published_since = ?"
						+ ", obsolete_since = ?"
						+ ", template_path = ?"
						+ ", version = ?" 
						+ ", model_version = ?"
//						+ ", uuid = ?";
						+ ", doi = ?";
					sqlQuery += " WHERE id = ?";
					
					prepStmt = connection.prepareStatement(sqlQuery);
					prepStmt.setString(1, DBField.PRO_NAM.truncate(getName()));
					if (getType() != null)
						prepStmt.setInt(2, getType().getID());
					else
						prepStmt.setNull(2, java.sql.Types.INTEGER);
					prepStmt.setInt(3, getActive());
					prepStmt.setInt(4, getSaved());
					prepStmt.setString(5, DBField.PRO_TAR_NAM.truncate(getTargetName()));
					prepStmt.setString(6, DBField.PRO_TAR_LAB.truncate(getTargetLabel()));
					prepStmt.setInt(7, getMeasurementImported());
					if (getFinishedSince()  != null)
						prepStmt.setDate(8, new java.sql.Date(getFinishedSince().getTime()));
					else
						prepStmt.setNull(8, java.sql.Types.DATE);
					if (getObsoleteSince()  != null)
						prepStmt.setDate(9, new java.sql.Date(getObsoleteSince().getTime()));
					else
						prepStmt.setNull(9, java.sql.Types.DATE);
					prepStmt.setString(10, DBField.PRO_TEM_PAT.truncate(getProjectTemplate()));
					prepStmt.setString(11, DBField.PRO_VER.truncate(getVersion())); 
					prepStmt.setString(12, DBField.PRO_MOD_VER.truncate(getModelVersion())); 
					prepStmt.setString(13, DBField.PRO_DOI.truncate(getDOI()));
					prepStmt.setInt(14, this.getEntityID());
					
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
				
				if (storeStatus)
					storeStatus = progress.entityStore(connection);
				
				if (storeStatus) {
					/*
					 *	Use Insert-Statement
					 */		
//					sqlQuery = "INSERT INTO projects (name, type, progress, target_name, target_label, measurement_imported, template_path, version, model_version ";
//					sqlQuery += ") VALUES ("
//						+ "?, ?, ?, ?, ?, ?, ?, ?, ?";
//					sqlQuery += ")";
					sqlQuery = "INSERT INTO projects (name, type, progress, target_name, target_label, measurement_imported, template_path, version, model_version, doi ";
					sqlQuery += ") VALUES ("
						+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?";
					sqlQuery += ")";

					
					prepStmt = connection.prepareStatement(sqlQuery);
					prepStmt.setString(1, DBField.PRO_NAM.truncate(getName()));
					if (getType() != null)
						prepStmt.setInt(2, getType().getID());
					else
						prepStmt.setNull(2, java.sql.Types.INTEGER);
					prepStmt.setInt(3, this.getProgress().getEntityID());
					prepStmt.setString(4, DBField.PRO_TAR_NAM.truncate(getTargetName()));
					prepStmt.setString(5, DBField.PRO_TAR_LAB.truncate(getTargetLabel()));
					prepStmt.setInt(6, getMeasurementImported());
					prepStmt.setString(7, DBField.PRO_TEM_PAT.truncate(getProjectTemplate()));
					prepStmt.setString(8, DBField.PRO_VER.truncate(getVersion())); 
					prepStmt.setString(9, DBField.PRO_MOD_VER.truncate(getModelVersion()));
					prepStmt.setString(10, DBField.PRO_DOI.truncate(getDOI()));
					
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
					storeStatus = getSummary().entityStore(connection);
				
				if (storeStatus)
					storeStatus = getConcept().entityStore(connection);
				
				if (storeStatus) {
					ConceptRef conceptRef = new ConceptRef();
					conceptRef.setConcept(getConcept());
					conceptRef.setReferenced(this);
					conceptRef.setRefRelationtype(RefRelationtype.REFERENCED); /* ONLY FOR DEMO */
					
					storeStatus = conceptRef.entityStore(connection);
				}
				
				if (storeStatus) {
					Participant participant = participants.get(0);
					
					storeStatus = participant.entityStore(connection);
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
				sqlQuery = "DELETE from projects " +
					"WHERE id = " + this.getEntityID();
				
				stmt = connection.createStatement();
				rows = stmt.executeUpdate(sqlQuery);
				
				if (rows < 1)				
					removeStatus = false;
				
				sqlQuery = "DELETE from participants " +
						"WHERE project = " + this.getEntityID();
					
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
    
	/* List of Projects */
	/**
	 * @param con
	 * @return
	 */
	public static ArrayList<Project> getAllProjects(Connection con) {
		ArrayList<Project> projects = new ArrayList<Project>();
		
		java.sql.Statement stmt;
		ResultSet rSet;
		
		if (con != null) {
			try {
				if(!con.isClosed()) {
					stmt = con.createStatement();
					
					String sqlQuery = "select prj.id, prj.name "
						+ "from projects prj";
					
					rSet = stmt.executeQuery( sqlQuery );
					
					while (rSet.next()) {
						Project prj = new Project(rSet.getInt("id"));
						prj.setName(rSet.getString("name"));
						
						projects.add(prj);
					}
					
					stmt.close();
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(
						null, e.getMessage(), "SQLException:",  JOptionPane.ERROR_MESSAGE);
					  	
				/* DEMO ONLY */
				System.err.println("SQLException: " + e.getMessage());
				e.printStackTrace();
			}
		}
		
		return projects;
	}
	
	/**
	 * former getAllPublProjects
	 * 
	 * @param con
	 * @return
	 */
	public static ArrayList<Project> getAllFinProjects(Connection con) {
		ArrayList<Project> publProjects = new ArrayList<Project>();
		
		java.sql.Statement stmt;
		ResultSet rSet;
		
		if (con != null) {
			try {
				if (!con.isClosed()) {
					stmt = con.createStatement();
					
					String sqlQuery = "select pro.id, pro.name, pro.target_name, pro.target_label "
						+ "from projects pro "
						+ "where pro.published_since is not null" +" "
						+ "order by pro.name"; 					
					rSet = stmt.executeQuery( sqlQuery );
					
					while (rSet.next()) {
						Project project = new Project(rSet.getInt("id"));
						project.setName(rSet.getString("name"));
						
						project.setTargetName(rSet.getString("target_name"));
						project.setTargetLabel(rSet.getString("target_label"));
						
						java.sql.Statement innerStmt;
						ResultSet innerRSet;
						String innerSqlQuery = "SELECT c.id " +
								"FROM comments c " +
								"WHERE c.object_entity=\"" + project.getEntityType().getID() +"\" " +
									"AND c.object_entry=\"" + project.getEntityID() + "\" ";
						
						innerStmt = con.createStatement();
						innerRSet = innerStmt.executeQuery(innerSqlQuery);
						
						while (innerRSet.next()) {
							project.setSummary(new Comment());
							project.getSummary().setEntityID(innerRSet.getInt("id"));				
							project.getSummary().entityLoad(con);
							project.getSummary().setReference(project);
						}
						innerStmt.close();
						
						/*
						 *	Concept
						 */
						sqlQuery = "SELECT c.concept " +
							"FROM concept_refs c " +
							"WHERE c.entity=\"" + project.getEntityType().getID() +"\" " +
								"AND c.entry=\"" + project.getEntityID() + "\" ";	
						
						java.sql.Statement innerstmt = con.createStatement();
						ResultSet innerrSet = innerstmt.executeQuery( sqlQuery );
							
						while (innerrSet.next()) {
							project.setConcept(new Concept());
							project.getConcept().setEntityID(innerrSet.getInt("concept"));												
							project.getConcept().entityLoad(con);
						}
						innerstmt.close();
						
						publProjects.add(project);
					}
					
					stmt.close();
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(
						null, e.getMessage(), "SQLException:",  JOptionPane.ERROR_MESSAGE);
					  	
				/* DEMO ONLY */
				System.err.println("SQLException: " + e.getMessage());
				e.printStackTrace();
			}
		}
		
		return publProjects;
	}
	
	/**
	 * former getUnpublProjects
	 * 
	 * @param con
	 * @param user
	 * @return
	 */
	public static ArrayList<Project> getUnfinProjects(Connection con, User user) {
		ArrayList<Project> unfinProjects = new ArrayList<Project>();
		
		java.sql.Statement stmt;
		ResultSet rSet;
		
		if (con != null) {
			try {
				if(!con.isClosed()) {
					stmt = con.createStatement();
					
					String sqlQuery = "select pro.id, pro.name, pro.target_name, pro.target_label "
						+ "from projects pro, participants par, users usr "
						+ "where pro.published_since is null " 
						+ "and pro.id = par.project "
						+ "and par.role != 4 "
						+ "and par.user = usr.id "
						+ "and usr.id = "+ user.getEntityID() +" "
						+ "order by pro.name"; // 
					
					rSet = stmt.executeQuery( sqlQuery );
					
					while (rSet.next()) {
						Project project = new Project(rSet.getInt("id"));
						project.setName(rSet.getString("name"));
						project.setTargetName(rSet.getString("target_name"));
						project.setTargetLabel(rSet.getString("target_label"));
						
						java.sql.Statement innerStmt;
						ResultSet innerRSet;
						String innerSqlQuery = "SELECT c.id " +
								"FROM comments c " +
								"WHERE c.object_entity=\"" + project.getEntityType().getID() +"\" " +
									"AND c.object_entry=\"" + project.getEntityID() + "\" ";
						
						innerStmt = con.createStatement();
						innerRSet = innerStmt.executeQuery(innerSqlQuery);
						
						while (innerRSet.next()) {
//							project.getContent().setSummary(new Comment());
//							project.getContent().getSummary().setEntityID(innerRSet.getInt("id"));				
//							project.getContent().getSummary().entityLoad(con);
//							project.getContent().getSummary().setReference(project);
							project.setSummary(new Comment());
							project.getSummary().setEntityID(innerRSet.getInt("id"));				
							project.getSummary().entityLoad(con);
							project.getSummary().setReference(project);
						}
						innerStmt.close();
												
						unfinProjects.add(project);
					}
					
					stmt.close();
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(
						null, e.getMessage(), "SQLException:",  JOptionPane.ERROR_MESSAGE);
					  	
				/* DEMO ONLY */
				System.err.println("SQLException: " + e.getMessage());
				e.printStackTrace();
			}
		}
		
		return unfinProjects;
	}
	
	/**
	 * former getPublProjects
	 * 
	 * @param con
	 * @param user
	 * @return
	 */
	public static ArrayList<Project> getFinProjects(Connection con, User user) {
		ArrayList<Project> publProjects = new ArrayList<Project>();
		
		java.sql.Statement stmt;
		ResultSet rSet;
		
		if (con != null) {
			try {
				if (!con.isClosed()) {
					stmt = con.createStatement();
					
					String sqlQuery = "select pro.id, pro.name, pro.target_name, pro.target_label "
						+ "from projects pro, participants par, users usr "
						+ "where pro.published_since is not null " 
						+ "and pro.id = par.project "
						+ "and par.role != 4 "
						+ "and par.user = usr.id "
						+ "and usr.id = " + user.getEntityID() +" "
						+ "order by pro.name"; 
					
					rSet = stmt.executeQuery( sqlQuery );
					
					while (rSet.next()) {
						Project project = new Project(rSet.getInt("id"));
						project.setName(rSet.getString("name"));
						project.setTargetName(rSet.getString("target_name"));
						project.setTargetLabel(rSet.getString("target_label"));
						
						java.sql.Statement innerStmt;
						ResultSet innerRSet;
						String innerSqlQuery = "SELECT c.id " +
								"FROM comments c " +
								"WHERE c.object_entity=\"" + project.getEntityType().getID() +"\" " +
									"AND c.object_entry=\"" + project.getEntityID() + "\" ";
						
						innerStmt = con.createStatement();
						innerRSet = innerStmt.executeQuery(innerSqlQuery);
						
						while (innerRSet.next()) {
							project.setSummary(new Comment());
							project.getSummary().setEntityID(innerRSet.getInt("id"));				
							project.getSummary().entityLoad(con);
							project.getSummary().setReference(project);
						}
						innerStmt.close();
						
						publProjects.add(project);
					}
					
					stmt.close();
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(
						null, e.getMessage(), "SQLException:",  JOptionPane.ERROR_MESSAGE);
					  	
				/* DEMO ONLY */
				System.err.println("SQLException: " + e.getMessage());
				e.printStackTrace();
			}
		}
		
		return publProjects;
	}

	/**
	 * @return
	 */
	public static String getDraftSQLForAllProjectsThroughLabel() {
		String retString;
		
		retString =
			"(select distinct p.id " +
			"from projects p " +
			"where p.name REGEXP '<TEXT>' " +
			"and p.published_since is not null " +
			") ";
		
		return retString;
	}
	
	/**
	 * @param con
	 * @param projects
	 * @param users
	 */
	public void getEditedProjects(Connection con, List<Integer> projects, List<Integer> users) {
		java.sql.Statement stmt;
		ResultSet rSet;
		
		if (con != null) {
			try {
				if (!con.isClosed()) {
					stmt = con.createStatement();
					
					String sqlQuery = "select edi.project_id, edi.user_id "
						+ "from edited_projects edi";
					
					rSet = stmt.executeQuery( sqlQuery );
					
					while (rSet.next()) {
						projects.add(rSet.getInt("project_id"));
						users.add(rSet.getInt("user_id"));
					} 
					
					stmt.close();
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(
						null, e.getMessage(), "SQLException:",  JOptionPane.ERROR_MESSAGE);
					  	
				/* DEMO ONLY */
				System.err.println("SQLException: " + e.getMessage());
				e.printStackTrace();
			}
		}		
	}
	
	/**
	 * @param con
	 * @param user
	 * @param pk
	 * @return
	 */
	public int getEditingUser(Connection con, User user, int pk) {
		int retValue = -1;
		
		java.sql.Statement stmt;
		ResultSet rSet;
		
		if (con != null) {
			try {
				if (!con.isClosed()) {
					stmt = con.createStatement();
					
					String sqlQuery = "select edi.user_id "
						+ "from edited_projects edi "
						+ "where edi.project_id = "+ pk;
					
					rSet = stmt.executeQuery( sqlQuery );
					
					if (rSet.next()) {
						retValue = rSet.getInt("user_id");
					} else {
						retValue = -1;
					}
					
					stmt.close();
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(
						null, e.getMessage(), "SQLException:",  JOptionPane.ERROR_MESSAGE);
					  	
				/* DEMO ONLY */
				System.err.println("SQLException: " + e.getMessage());
				e.printStackTrace();
			}
		}
		
		return retValue;		
	}
	
	/**
	 * @param con
	 * @param user
	 * @param pk
	 * @return
	 */
	public boolean getEditedByUser(Connection con, User user, int pk) {
		boolean retValue = true;
		
		java.sql.Statement stmt;
		ResultSet rSet;
		
		if (con != null) {
			try {
				if (!con.isClosed()) {
					stmt = con.createStatement();
					
					String sqlQuery = "select edi.user_id "
						+ "from edited_projects edi "
						+ "where edi.project_id = "+ pk;
					
					rSet = stmt.executeQuery( sqlQuery );
					
					if (rSet.next()) {
//						int user_id = rSet.getInt("user_id");
//						User anotherUser = new User();
//						anotherUser.setEntityID(user_id);
//						anotherUser.entityLoad(con);
//						
//						JOptionPane.showMessageDialog(_view.getAppFrame(), "Project already opened by User "+anotherUser.getName()+"!\nIt will be opened in Read-only Mode now...", "Warning", JOptionPane.OK_CANCEL_OPTION);
						
						retValue = false;						
					} else {
						retValue = true;
					}
					
					stmt.close();
					
					if (retValue) {
						if (!isFinished())
							retValue = setEditedByUser(con, user, pk); 
					}
				}
			} catch (SQLException e) {
//				JOptionPane.showMessageDialog(
//						null, e.getMessage(), "SQLException:",  JOptionPane.ERROR_MESSAGE);
					  	
				/* DEMO ONLY */
				System.err.println("SQLException: " + e.getMessage());
				e.printStackTrace();
			}
		}
		
		return retValue;
	}
	
	/**
	 * @param con
	 * @param user
	 * @param pk
	 * @return
	 */
	public boolean setEditedByUser(Connection con, User user, int pk) {
		boolean storeStatus = true;
		int rows = -1;
		
		if (con.equals(null)) {
			storeStatus = false;
			JOptionPane.showMessageDialog(
					null, "No Connection to DataBase!", "Error:", JOptionPane.ERROR_MESSAGE);
							
			/* DEMO ONLY */
			System.err.println("Error: No Connection to DataBase!");
			
			return storeStatus;
		}

		/*
		 *	Insert
		 */
		try {
			con.setAutoCommit(false);
			
			if (storeStatus) {
				/*
				 *	Use Insert-Statement
				 */		
				sqlQuery = "INSERT INTO edited_projects (project_id, user_id) VALUES ("
					+ "'"	+ pk					+ "',"
					+ "'"	+ user.getEntityID()	+ "')";
				
				stmt = con.createStatement();
				rows = stmt.executeUpdate(sqlQuery);
				
				if (rows < 1)
					storeStatus = false;
				
				stmt.close();
			}
			
		} catch (SQLException e) {
	    	storeStatus = false;
//	    	JOptionPane.showMessageDialog(
//	    			null, e.getMessage(), "SQLException:",  JOptionPane.ERROR_MESSAGE);
	    		  	
	    	/* DEMO ONLY */
	    	System.err.println("SQLException: " + e.getMessage());
	    	e.printStackTrace();
		}
	    
		if (!storeStatus) {
	    	try {
				con.rollback();
			} catch (SQLException e) {
//				JOptionPane.showMessageDialog(
//						null, e.getMessage(), "SQLException:",  JOptionPane.ERROR_MESSAGE);
					  	
				/* DEMO ONLY */
				System.err.println("SQLException: " + e.getMessage());
				e.printStackTrace();
			}
		} else
			try {
				con.commit();
			} catch (SQLException e) {
				storeStatus = false; // Without commit project isn't locked!
//				JOptionPane.showMessageDialog(
//						null, e.getMessage(), "SQLException:",  JOptionPane.ERROR_MESSAGE);
				  	
				/* DEMO ONLY */
				System.err.println("SQLException: " + e.getMessage());
				e.printStackTrace();
			}
		
		return storeStatus;
	}
	
	/**
	 * @param con
	 * @param user
	 * @param pk
	 * @return
	 */
	public boolean removeEditedByUser(Connection con, User user, int pk) {
    	boolean removeStatus = true;
    	int rows = -1;
    	
		if (con.equals(null)) {
			removeStatus = false;
			JOptionPane.showMessageDialog(
					null, "No Connection to DataBase!", "Error:", JOptionPane.ERROR_MESSAGE);
			
	    	/* DEMO ONLY */
	        System.err.println("Error: No Connection to DataBase!");
			
			return removeStatus;
		}
		
		try {
			con.setAutoCommit(false);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		if (entity_id > 0) {
			try {
				
				sqlQuery = "DELETE from edited_projects "+
					"WHERE project_id = " + pk +" "+
					"AND user_id = " + user.getEntityID();
				
				stmt = con.createStatement();
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
		
		if (removeStatus)
			try {
				con.commit();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(
						null, e.getMessage(), "SQLException:",  JOptionPane.ERROR_MESSAGE);
				  	
				/* DEMO ONLY */
				System.err.println("SQLException: " + e.getMessage());
				e.printStackTrace();
			}
		
		return removeStatus;
	}

	/**
	 * former isPublished
	 * 
	 * @return
	 */
	public boolean isFinished() {
		return (finished_since != null);
	}

	/**
	 * @param editedByUser
	 */
	public void setEditedByUser(boolean editedByUser) {
		this.editedByUser = editedByUser;
	}
	
	/**
	 * @return
	 */
	public boolean isEditedByUser() {
		return editedByUser;
	}

	/* ProjectOwner */
	/**
	 * @param projectOwner
	 */
	public void setProjectOwner(User projectOwner) {
		this.projectOwner = projectOwner;
	}
	
	/**
	 * @return
	 */
	public User getProjectOwner() {
		return projectOwner;
	}
	
	/* ProjectTemplate */
	/**
	 * @param path
	 */
	public void setProjectTemplate(String path) {
		this.projectTemplate = path;
	}
	
	/**
	 * @return
	 */
	public String getProjectTemplate() {
		return projectTemplate;
	}
	
	/**
	 * @param id
	 * @return
	 */
	public static String getDraftSQLForNote(int id) {
	   	String retString;
	    	
		retString = 
			"SELECT c.id " +
			"FROM comments c " +
			"WHERE c.object_entity=\"" + EntityType.PROJECTS.getID() +"\" " +
			"AND c.object_entry=\"" + id + "\" ";
		
		return retString;
    }

	/**
	 * @return
	 */
	public String loadProjectNotes() {
		String sqlString	= Project.getDraftSQLForNote(this.getEntityID());
		String projectNote	= getProjectNote(sqlString);
		
		return projectNote;
	}
	
	/**
	 * @param sql
	 * @return
	 */
	private String getProjectNote(String sql) {
		
		return CStatsCtrl.searchForProjectNote(sql);
	}
	
	/**
	 * @param id
	 * @param con
	 * @return
	 */
	public static String searchForProjectName(int id, Connection con) {
		String retString = "";
		
		java.sql.Statement stmt;
		ResultSet rSet;
		
		if (con.equals(null)) {
			JOptionPane.showMessageDialog(
					null, "No Connection to DataBase!", "Error:", JOptionPane.ERROR_MESSAGE);
							
			/* DEMO ONLY */
			System.err.println("Error: No Connection to DataBase!");
			
			return "";
		}
		
		try {
			if (!con.isClosed()) {
				stmt = con.createStatement();
					
				String sql = "select name from projects where id = "+ id;
				rSet = stmt.executeQuery( sql );
				
				while (rSet.next()) {					
					retString = rSet.getString("name");
				}
				
				stmt.close();
			} else {
				JOptionPane.showMessageDialog(
						null, "No open Connection!", "Error:", JOptionPane.ERROR_MESSAGE);
									
				/* DEMO ONLY */
				System.err.println("Error: No open Connection!");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(
					null, e.getMessage(), "SQLException:",  JOptionPane.ERROR_MESSAGE);
				  	
			/* DEMO ONLY */
			System.err.println("SQLException: " + e.getMessage());
			e.printStackTrace();
		}	   
					
		return retString;
	}
	
	/**
	 * former unpublish
	 * 
	 * @param entity_id
	 * @param connection
	 * @return
	 */
	public static boolean unfinish(int entity_id, Connection connection) {
		boolean unfinishStatus = true;
		int rows = -1;
		
		if (connection.equals(null)) {
			unfinishStatus = false;
			JOptionPane.showMessageDialog(
					null, "No Connection to DataBase!", "Error:", JOptionPane.ERROR_MESSAGE);
							
			/* DEMO ONLY */
			System.err.println("Error: No Connection to DataBase!");
			
			return unfinishStatus;
		}

		if (entity_id > 0) {
			/*
			 *	Update
			 */
			try {
				connection.setAutoCommit(false);
								
				if (unfinishStatus) {
					String  sqlQuery = "UPDATE projects SET "
						+ "published_since = ?";
					sqlQuery += " WHERE id = ?";
					
					PreparedStatement prepStmt = connection.prepareStatement(sqlQuery);
					prepStmt.setNull(1, Types.DATE);
					prepStmt.setInt(2, entity_id);
					
					rows = prepStmt.executeUpdate();
					
					if (rows < 1)				
						unfinishStatus = false;
					
					prepStmt.close();
				}
		    } catch (SQLException e) {
		    	unfinishStatus = false;
		    	JOptionPane.showMessageDialog(
		    			null, e.getMessage(), "SQLException:",  JOptionPane.ERROR_MESSAGE);
		    		  	
		    	/* DEMO ONLY */
		    	System.err.println("SQLException: " + e.getMessage());
		    	e.printStackTrace();
		    }
		}
		
		return unfinishStatus;
	}
	
	@Override
	public int compareTo(Object arg0) {		
		if (arg0 instanceof Variable) {
			return this.getName().compareTo(((Project)arg0).getName());
		}
		
		return 0;
	}
}

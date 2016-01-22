package org.gesis.charmstats.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import org.gesis.charmstats.persistence.DBEntity;

/**
 *	This is an Entity-Class
 * 
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class Progress extends DBEntity {

	/* charmstats.progresses (MySQL)
	  `id`				  					   int(11)	  NOT NULL auto_increment,
	  `projectSetupStepTabbedPanelAvailable`   tinyint(1)		   default '1',
	  `projectTabDone`						   tinyint(1) 		   default '0',
	  `conceptTabDone`						   tinyint(1) 		   default '0',
	  `referenceTabDone`					   tinyint(1) 		   default '0',
	  `measurementTabDone`					   tinyint(1) 		   default '0',
	  `conceptualStepTabbedPanelAvailable`	   tinyint(1) 		   default '0',
	  `dimensionTabDone`					   tinyint(1) 		   default '0',
	  `specificationTabDone`				   tinyint(1) 		   default '0',
	  `mapDimensionInstanceTabDone`			   tinyint(1) 		   default '0',
	  `mapDimensionAttributeTabDone`		   tinyint(1) 		   default '0',
	  `mapDimensionChaTabDone`				   tinyint(1) 		   default '0',
	  `operationalStepTabbedPanelAvailable`	   tinyint(1) 		   default '0',
	  `osInstanceTabDone`					   tinyint(1) 		   default '0',
	  `indicatorTabDone`					   tinyint(1) 		   default '0',
	  `prescriptionTabDone`					   tinyint(1) 		   default '0',
	  `mapIndicatorInstanceTabDone`			   tinyint(1) 		   default '0',
	  `mapIndicatorAttributeTabDone`		   tinyint(1) 		   default '0',
	  `mapIndicatorChaTabDone`				   tinyint(1) 		   default '0',
	  `searchNCompareStepTabbedPanelAvailable` tinyint(1) 		   default '0',
	  `searchTabDone`						   tinyint(1) 		   default '0',
	  `compareTabDone`						   tinyint(1) 		   default '0',
	  `dataRecodingStepTabbedPanelAvailable`   tinyint(1) 		   default '0',
	  `drInstanceTabDone`					   tinyint(1) 		   default '0',
	  `variableTabDone`						   tinyint(1) 		   default '0',
	  `valueTabDone`						   tinyint(1) 		   default '0',
	  `mapVariableInstanceTabDone`			   tinyint(1) 		   default '0',
	  `mapVariableAttributeTabDone`			   tinyint(1) 		   default '0',
	  `mapVariableChaTabDone`				   tinyint(1) 		   default '0',
	  `targetVariableStepTabbedPanelAvailable` tinyint(1) 		   default '0',
	  PRIMARY KEY  (`id`)
	*/
	
	/*
	 *	Fields
	 */
	private boolean projectSetupStepTabbedPanelAvailable;
	private boolean	projectTabDone;
	private boolean	conceptTabDone;  
	private boolean	referenceTabDone;
	private boolean targetVariableStepTabbedPanelAvailable;
	private boolean	measurementTabDone;
	private boolean	conceptualStepTabbedPanelAvailable;
	private boolean	dimensionTabDone;
	private boolean	specificationTabDone;
	private boolean	mapDimensionInstanceTabDone;
	private boolean	mapDimensionAttributeTabDone;
	private boolean	mapDimensionChaTabDone;
	private boolean operationalStepTabbedPanelAvailable;
	private boolean	osInstanceTabDone;
	private boolean	indicatorTabDone;
	private boolean	prescriptionTabDone;
	private boolean	mapIndicatorInstanceTabDone;
	private boolean	mapIndicatorAttributeTabDone;
	private boolean	mapIndicatorChaTabDone;
	private boolean searchNCompareStepTabbedPanelAvailable;
	private boolean	searchTabDone;
	private boolean	compareTabDone;
	private boolean compareValuesTabDone; 
	private boolean dataRecodingStepTabbedPanelAvailable;
	private boolean	drInstanceTabDone;
	private boolean	variableTabDone;
	private boolean	valueTabDone;
	private boolean questionTabDone; 
	private boolean studyTabDone; 
	private boolean	mapVariableInstanceTabDone;
	private boolean	mapVariableAttributeTabDone;
	private boolean	mapVariableChaTabDone;
	
	/*
	 *	Constructor
	 */
	/**
	 * 
	 */
	public Progress () {
		super();
		
		setProjectSetupStepTabbedPanelAvailable(false);
		setProjectTabDone(false);
		setConceptTabDone(false);
		setReferenceTabDone(false);
		setMeasurementTabDone(false);
		setConceptualStepTabbedPanelAvailable(false);
		setDimensionTabDone(false);
		setSpecificationTabDone(false);
		setMapDimensionInstanceTabDone(false);
		setMapDimensionAttributeTabDone(false);
		setMapDimensionChaTabDone(false);
		setOperationalStepTabbedPanelAvailable(false);
		setOsInstanceTabDone(false);
		setIndicatorTabDone(false);
		setPrescriptionTabDone(false);
		setMapIndicatorInstanceTabDone(false);
		setMapIndicatorAttributeTabDone(false);
		setMapIndicatorChaTabDone(false);
		setSearchNCompareStepTabbedPanelAvailable(false);
		setSearchTabDone(false);
		setComparisonTabDone(false);
		setCompareValuesTabDone(false);
		setDataRecodingStepTabbedPanelAvailable(false);
		setDrInstanceTabDone(false);
		setVariableTabDone(false);
		setValueTabDone(false);
		setQuestionTabDone(false); 
		setStudyTabDone(false); 
		setMapVariableInstanceTabDone(false);
		setMapVariableAttributeTabDone(false);
		setMapVariableChaTabDone(false);
	}
		
	/*
	 *	Methods
	 */
	/**
	 * @return
	 */
	public boolean isProjectSetupStepTabbedPanelAvailable() {
		return projectSetupStepTabbedPanelAvailable;
	}
	// mySQL only:
	/**
	 * @return
	 */
	public int getProjectSetupStepTabbedPanelAvailable() {
		return (projectSetupStepTabbedPanelAvailable ? 1 : 0);
	}
	/**
	 * @param available
	 */
	public void setProjectSetupStepTabbedPanelAvailable(boolean available) {
		this.projectSetupStepTabbedPanelAvailable = available;
	}
	// mySQL only:
	/**
	 * @param available
	 */
	public void setProjectSetupStepTabbedPanelAvailable(int available) {
		this.projectSetupStepTabbedPanelAvailable = (available > 0);
	}
	
	/**
	 * @return
	 */
	public boolean isProjectTabDone() {
		return projectTabDone;
	}
	// mySQL only:
	/**
	 * @return
	 */
	public int getProjectTabDone() {
		return (projectTabDone ? 1 : 0);
	}
	/**
	 * @param done
	 */
	public void setProjectTabDone(boolean done) {
		this.projectTabDone = done;
	}
	// mySQL only:
	/**
	 * @param done
	 */
	public void setProjectTabDone(int done) {
		this.projectTabDone = (done > 0);
	}
	
	/**
	 * @return
	 */
	public boolean isConceptTabDone() {
		return conceptTabDone;
	}
	// mySQL only:
	/**
	 * @return
	 */
	public int getConceptTabDone() {
		return (conceptTabDone ? 1 : 0);
	}
	/**
	 * @param done
	 */
	public void setConceptTabDone(boolean done) {
		this.conceptTabDone = done;
	}
	// mySQL only:
	/**
	 * @param done
	 */
	public void setConceptTabDone(int done) {
		this.conceptTabDone = (done > 0);
	}
	
	/**
	 * @return
	 */
	public boolean isReferenceTabDone() {
		return referenceTabDone;
	}
	// mySQL only:
	/**
	 * @return
	 */
	public int getReferenceTabDone() {
		return (referenceTabDone ? 1 : 0);
	}
	/**
	 * @param done
	 */
	public void setReferenceTabDone(boolean done) {
		this.referenceTabDone = done;
	}
	// mySQL only:
	/**
	 * @param done
	 */
	public void setReferenceTabDone(int done) {
		this.referenceTabDone = (done > 0);
	}
		
	/**
	 * @return
	 */
	public boolean isTargetVariableStepTabbedPanelAvailable() {
		return targetVariableStepTabbedPanelAvailable;
	}
	// mySQL only:
	/**
	 * @return
	 */
	public int getTargetVariableStepTabbedPanelAvailable() {
		return (targetVariableStepTabbedPanelAvailable ? 1 : 0);
	}
	/**
	 * @param available
	 */
	public void setTargetVariableStepTabbedPanelAvailable(boolean available) {
		this.targetVariableStepTabbedPanelAvailable = available;
	}
	// mySQL only:
	/**
	 * @param available
	 */
	public void setTargetVariableStepTabbedPanelAvailable(int available) {
		this.targetVariableStepTabbedPanelAvailable = (available > 0);
	}
	
	/**
	 * @return
	 */
	public boolean isMeasurementTabDone() {
		return measurementTabDone;
	}
	// mySQL only:
	/**
	 * @return
	 */
	public int getMeasurementTabDone() {
		return (measurementTabDone ? 1 : 0);
	}
	/**
	 * @param done
	 */
	public void setMeasurementTabDone(boolean done) {
		this.measurementTabDone = done;
	}
	// mySQL only:
	/**
	 * @param done
	 */
	public void setMeasurementTabDone(int done) {
		this.measurementTabDone = (done > 0);
	}
	
	/**
	 * @return
	 */
	public boolean isConceptualStepTabbedPanelAvailable() {
		return conceptualStepTabbedPanelAvailable;
	}
	// mySQL only:
	/**
	 * @return
	 */
	public int getConceptualStepTabbedPanelAvailable() {
		return (conceptualStepTabbedPanelAvailable ? 1 : 0);
	}
	/**
	 * @param available
	 */
	public void setConceptualStepTabbedPanelAvailable(boolean available) {
		this.conceptualStepTabbedPanelAvailable = available;
	}
	// mySQL only:
	/**
	 * @param available
	 */
	public void setConceptualStepTabbedPanelAvailable(int available) {
		this.conceptualStepTabbedPanelAvailable = (available > 0);
	}
	
	/**
	 * @return
	 */
	public boolean isDimensionTabDone() {
		return dimensionTabDone;
	}
	// mySQL only:
	/**
	 * @return
	 */
	public int getDimensionTabDone() {
		return (dimensionTabDone ? 1 : 0);
	}
	/**
	 * @param done
	 */
	public void setDimensionTabDone(boolean done) {
		this.dimensionTabDone = done;
	}
	// mySQL only:
	/**
	 * @param done
	 */
	public void setDimensionTabDone(int done) {
		this.dimensionTabDone = (done > 0);
	}
	
	/**
	 * @return
	 */
	public boolean isSpecificationTabDone() {
		return specificationTabDone;
	}
	// mySQL only:
	/**
	 * @return
	 */
	public int getSpecificationTabDone() {
		return (specificationTabDone ? 1 : 0);
	}
	/**
	 * @param done
	 */
	public void setSpecificationTabDone(boolean done) {
		this.specificationTabDone = done;
	}
	// mySQL only:
	/**
	 * @param done
	 */
	public void setSpecificationTabDone(int done) {
		this.specificationTabDone = (done > 0);
	}
		
	/**
	 * @return
	 */
	public boolean isMapDimensionInstanceTabDone() {
		return mapDimensionInstanceTabDone;
	}
	// mySQL only:
	/**
	 * @return
	 */
	public int getMapDimensionInstanceTabDone() {
		return (mapDimensionInstanceTabDone ? 1 : 0);
	}
	/**
	 * @param done
	 */
	public void setMapDimensionInstanceTabDone(boolean done) {
		this.mapDimensionInstanceTabDone = done;
	}
	// mySQL only:
	/**
	 * @param done
	 */
	public void setMapDimensionInstanceTabDone(int done) {
		this.mapDimensionInstanceTabDone = (done > 0);
	}
	
	/**
	 * @return
	 */
	public boolean isMapDimensionAttributeTabDone() {
		return mapDimensionAttributeTabDone;
	}
	// mySQL only:
	/**
	 * @return
	 */
	public int getMapDimensionAttributeTabDone() {
		return (mapDimensionAttributeTabDone ? 1 : 0);
	}
	/**
	 * @param done
	 */
	public void setMapDimensionAttributeTabDone(boolean done) {
		this.mapDimensionAttributeTabDone = done;
	}
	// mySQL only:
	/**
	 * @param done
	 */
	public void setMapDimensionAttributeTabDone(int done) {
		this.mapDimensionAttributeTabDone = (done > 0);
	}
	
	/**
	 * @return
	 */
	public boolean isMapDimensionChaTabDone() {
		return mapDimensionChaTabDone;
	}
	// mySQL only:
	/**
	 * @return
	 */
	public int getMapDimensionChaTabDone() {
		return (mapDimensionChaTabDone ? 1 : 0);
	}
	/**
	 * @param done
	 */
	public void setMapDimensionChaTabDone(boolean done) {
		this.mapDimensionChaTabDone = done;
	}
	// mySQL only:
	/**
	 * @param done
	 */
	public void setMapDimensionChaTabDone(int done) {
		this.mapDimensionChaTabDone = (done > 0);
	}
	
	/**
	 * @return
	 */
	public boolean isOperationalStepTabbedPanelAvailable() {
		return operationalStepTabbedPanelAvailable;
	}
	// mySQL only:
	/**
	 * @return
	 */
	public int getOperationalStepTabbedPanelAvailable() {
		return (operationalStepTabbedPanelAvailable ? 1 : 0);
	}
	/**
	 * @param available
	 */
	public void setOperationalStepTabbedPanelAvailable(boolean available) {
		this.operationalStepTabbedPanelAvailable = available;
	}
	// mySQL only:
	/**
	 * @param available
	 */
	public void setOperationalStepTabbedPanelAvailable(int available) {
		this.operationalStepTabbedPanelAvailable = (available > 0);
	}
	
	/**
	 * @return
	 */
	public boolean isOsInstanceTabDone() {
		return osInstanceTabDone;
	}
	// mySQL only:
	/**
	 * @return
	 */
	public int getOsInstanceTabDone() {
		return (osInstanceTabDone ? 1 : 0);
	}
	/**
	 * @param done
	 */
	public void setOsInstanceTabDone(boolean done) {
		this.osInstanceTabDone = done;
	}
	// mySQL only:
	/**
	 * @param done
	 */
	public void setOsInstanceTabDone(int done) {
		this.osInstanceTabDone = (done > 0);
	}
	
	/**
	 * @return
	 */
	public boolean isIndicatorTabDone() {
		return indicatorTabDone;
	}
	// mySQL only:
	/**
	 * @return
	 */
	public int getIndicatorTabDone() {
		return (indicatorTabDone ? 1 : 0);
	}
	/**
	 * @param done
	 */
	public void setIndicatorTabDone(boolean done) {
		this.indicatorTabDone = done;
	}
	// mySQL only:
	/**
	 * @param done
	 */
	public void setIndicatorTabDone(int done) {
		this.indicatorTabDone = (done > 0);
	}
	
	/**
	 * @return
	 */
	public boolean isPrescriptionTabDone() {
		return prescriptionTabDone;
	}
	// mySQL only:
	/**
	 * @return
	 */
	public int getPrescriptionTabDone() {
		return (prescriptionTabDone ? 1 : 0);
	}
	/**
	 * @param done
	 */
	public void setPrescriptionTabDone(boolean done) {
		this.prescriptionTabDone = done;
	}
	// mySQL only:
	/**
	 * @param done
	 */
	public void setPrescriptionTabDone(int done) {
		this.prescriptionTabDone = (done > 0);
	}
	
	/**
	 * @return
	 */
	public boolean isMapIndicatorInstanceTabDone() {
		return mapIndicatorInstanceTabDone;
	}
	// mySQL only:
	/**
	 * @return
	 */
	public int getMapIndicatorInstanceTabDone() {
		return (mapIndicatorInstanceTabDone ? 1 : 0);
	}
	/**
	 * @param done
	 */
	public void setMapIndicatorInstanceTabDone(boolean done) {
		this.mapIndicatorInstanceTabDone = done;
	}
	// mySQL only:
	/**
	 * @param done
	 */
	public void setMapIndicatorInstanceTabDone(int done) {
		this.mapIndicatorInstanceTabDone = (done > 0);
	}
	
	/**
	 * @return
	 */
	public boolean isMapIndicatorAttributeTabDone() {
		return mapIndicatorAttributeTabDone;
	}
	// mySQL only:
	/**
	 * @return
	 */
	public int getMapIndicatorAttributeTabDone() {
		return (mapIndicatorAttributeTabDone ? 1 : 0);
	}
	/**
	 * @param done
	 */
	public void setMapIndicatorAttributeTabDone(boolean done) {
		this.mapIndicatorAttributeTabDone = done;
	}
	// mySQL only:
	/**
	 * @param done
	 */
	public void setMapIndicatorAttributeTabDone(int done) {
		this.mapIndicatorAttributeTabDone = (done > 0);
	}
	
	/**
	 * @return
	 */
	public boolean isMapIndicatorChaTabDone() {
		return mapIndicatorChaTabDone;
	}
	// mySQL only:
	/**
	 * @return
	 */
	public int getMapIndicatorChaTabDone() {
		return (mapIndicatorChaTabDone ? 1 : 0);
	}
	/**
	 * @param done
	 */
	public void setMapIndicatorChaTabDone(boolean done) {
		this.mapIndicatorChaTabDone = done;
	}
	// mySQL only:
	/**
	 * @param done
	 */
	public void setMapIndicatorChaTabDone(int done) {
		this.mapIndicatorChaTabDone = (done > 0);
	}
	
	/**
	 * @return
	 */
	public boolean isSearchNCompareStepTabbedPanelAvailable() {
		return searchNCompareStepTabbedPanelAvailable;
	}
	// mySQL only:
	/**
	 * @return
	 */
	public int getSearchNCompareStepTabbedPanelAvailable() {
		return (searchNCompareStepTabbedPanelAvailable ? 1 : 0);
	}
	/**
	 * @param available
	 */
	public void setSearchNCompareStepTabbedPanelAvailable(boolean available) {
		this.searchNCompareStepTabbedPanelAvailable = available;
	}
	// mySQL only:
	/**
	 * @param available
	 */
	public void setSearchNCompareStepTabbedPanelAvailable(int available) {
		this.searchNCompareStepTabbedPanelAvailable = (available > 0);
	}
	
	/**
	 * @return
	 */
	public boolean isSearchTabDone() {
		return searchTabDone;
	}
	// mySQL only:
	/**
	 * @return
	 */
	public int getSearchTabDone() {
		return (searchTabDone ? 1 : 0);
	}
	/**
	 * @param done
	 */
	public void setSearchTabDone(boolean done) {
		this.searchTabDone = done;
	}
	// mySQL only:
	/**
	 * @param done
	 */
	public void setSearchTabDone(int done) {
		this.searchTabDone = (done > 0);
	}
	
	/**
	 * @return
	 */
	public boolean isComparisonTabDone() {
		return compareTabDone;
	}
	// mySQL only:
	/**
	 * @return
	 */
	public int getComparisonTabDone() {
		return (compareTabDone ? 1 : 0);
	}
	/**
	 * @param done
	 */
	public void setComparisonTabDone(boolean done) {
		this.compareTabDone = done;
	}
	// mySQL only:
	/**
	 * @param done
	 */
	public void setComparisonTabDone(int done) {
		this.compareTabDone = (done > 0);
	}
	
	/**
	 * @return
	 */
	public boolean isCompareValuesTabDone() {
		return compareValuesTabDone;
	}
	// mySQL only:
	/**
	 * @return
	 */
	public int getCompareValuesTabDone() {
		return (compareValuesTabDone ? 1 : 0);
	}
	/**
	 * @param done
	 */
	public void setCompareValuesTabDone(boolean done) {
		this.compareValuesTabDone = done;
	}
	// mySQL only:
	/**
	 * @param done
	 */
	public void setCompareValuesTabDone(int done) {
		this.compareValuesTabDone = (done > 0);
	}
	
	/**
	 * @return
	 */
	public boolean isDataRecodingStepTabbedPanelAvailable() {
		return dataRecodingStepTabbedPanelAvailable;
	}
	// mySQL only:
	/**
	 * @return
	 */
	public int getDataRecodingStepTabbedPanelAvailable() {
		return (dataRecodingStepTabbedPanelAvailable ? 1 : 0);
	}
	/**
	 * @param available
	 */
	public void setDataRecodingStepTabbedPanelAvailable(boolean available) {
		this.dataRecodingStepTabbedPanelAvailable = available;
	}
	// mySQL only:
	/**
	 * @param available
	 */
	public void setDataRecodingStepTabbedPanelAvailable(int available) {
		this.dataRecodingStepTabbedPanelAvailable = (available > 0);
	}
	
	/**
	 * @return
	 */
	public boolean isDrInstanceTabDone() {
		return drInstanceTabDone;
	}
	// mySQL only:
	/**
	 * @return
	 */
	public int getDrInstanceTabDone() {
		return (drInstanceTabDone ? 1 : 0);
	}
	/**
	 * @param done
	 */
	public void setDrInstanceTabDone(boolean done) {
		this.drInstanceTabDone = done;
	}
	// mySQL only:
	/**
	 * @param done
	 */
	public void setDrInstanceTabDone(int done) {
		this.drInstanceTabDone = (done > 0);
	}
	
	/**
	 * @return
	 */
	public boolean isVariableTabDone() {
		return variableTabDone;
	}
	// mySQL only:
	/**
	 * @return
	 */
	public int getVariableTabDone() {
		return (variableTabDone ? 1 : 0);
	}
	/**
	 * @param done
	 */
	public void setVariableTabDone(boolean done) {
		this.variableTabDone = done;
	}
	// mySQL only:
	/**
	 * @param done
	 */
	public void setVariableTabDone(int done) {
		this.variableTabDone = (done > 0);
	}
	
	/**
	 * @return
	 */
	public boolean isValueTabDone() {
		return valueTabDone;
	}
	// mySQL only:
	/**
	 * @return
	 */
	public int getValueTabDone() {
		return (valueTabDone ? 1 : 0);
	}
	/**
	 * @param done
	 */
	public void setValueTabDone(boolean done) {
		this.valueTabDone = done;
	}
	// mySQL only:
	/**
	 * @param done
	 */
	public void setValueTabDone(int done) {
		this.valueTabDone = (done > 0);
	}
	
	/**
	 * @return
	 */
	public boolean isQuestionTabDone() {
		return questionTabDone;
	}
	// mySQL only:
	/**
	 * @return
	 */
	public int getQuestionTabDone() {
		return (questionTabDone ? 1 : 0);
	}
	/**
	 * @param done
	 */
	public void setQuestionTabDone(boolean done) {
		this.questionTabDone = done;
	}
	// mySQL only:
	/**
	 * @param done
	 */
	public void setQuestionTabDone(int done) {
		this.questionTabDone = (done > 0);
	}
	
	/**
	 * @return
	 */
	public boolean isStudyTabDone() {
		return studyTabDone;
	}
	// mySQL only:
	/**
	 * @return
	 */
	public int getStudyTabDone() {
		return (studyTabDone ? 1 : 0);
	}
	/**
	 * @param done
	 */
	public void setStudyTabDone(boolean done) {
		this.studyTabDone = done;
	}
	// mySQL only:
	/**
	 * @param done
	 */
	public void setStudyTabDone(int done) {
		this.studyTabDone = (done > 0);
	}
	// 05.06.16
	
	/**
	 * @return
	 */
	public boolean isMapVariableInstanceTabDone() {
		return mapVariableInstanceTabDone;
	}
	// mySQL only:
	/**
	 * @return
	 */
	public int getMapVariableInstanceTabDone() {
		return (mapVariableInstanceTabDone ? 1 : 0);
	}
	/**
	 * @param done
	 */
	public void setMapVariableInstanceTabDone(boolean done) {
		this.mapVariableInstanceTabDone = done;
	}
	// mySQL only:
	/**
	 * @param done
	 */
	public void setMapVariableInstanceTabDone(int done) {
		this.mapVariableInstanceTabDone = (done > 0);
	}
	
	/**
	 * @return
	 */
	public boolean isMapVariableAttributeTabDone() {
		return mapVariableAttributeTabDone;
	}
	// mySQL only:
	/**
	 * @return
	 */
	public int getMapVariableAttributeTabDone() {
		return (mapVariableAttributeTabDone ? 1 : 0);
	}
	/**
	 * @param done
	 */
	public void setMapVariableAttributeTabDone(boolean done) {
		this.mapVariableAttributeTabDone = done;
	}
	// mySQL only:
	/**
	 * @param done
	 */
	public void setMapVariableAttributeTabDone(int done) {
		this.mapVariableAttributeTabDone = (done > 0);
	}
	
	/**
	 * @return
	 */
	public boolean isMapVariableChaTabDone() {
		return mapVariableChaTabDone;
	}
	// mySQL only:
	/**
	 * @return
	 */
	public int getMapVariableChaTabDone() {
		return (mapVariableChaTabDone ? 1 : 0);
	}
	/**
	 * @param done
	 */
	public void setMapVariableChaTabDone(boolean done) {
		this.mapVariableChaTabDone = done;
	}
	// mySQL only:
	/**
	 * @param done
	 */
	public void setMapVariableChaTabDone(int done) {
		this.mapVariableChaTabDone = (done > 0);
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
					
				String sqlQuery = "SELECT " +
					"projectSetupStepTabbedPanelAvailable, projectTabDone, conceptTabDone, referenceTabDone, " +
					"targetVariableStepTabbedPanelAvailable, measurementTabDone, " +
					"conceptualStepTabbedPanelAvailable, dimensionTabDone, specificationTabDone, mapDimensionInstanceTabDone, mapDimensionAttributeTabDone, mapDimensionChaTabDone, " +
					"operationalStepTabbedPanelAvailable, osInstanceTabDone, indicatorTabDone, prescriptionTabDone, mapIndicatorInstanceTabDone, mapIndicatorAttributeTabDone, mapIndicatorChaTabDone, " +
					"searchNCompareStepTabbedPanelAvailable, searchTabDone, compareTabDone, compareValuesTabDone, " +
					"dataRecodingStepTabbedPanelAvailable, drInstanceTabDone, variableTabDone, valueTabDone, questionTabDone, studyTabDone, mapVariableInstanceTabDone, mapVariableAttributeTabDone, mapVariableChaTabDone " +
					"FROM progresses " +
					"WHERE id=\"" + this.getEntityID() + "\"";	
				
				rSet = stmt.executeQuery( sqlQuery );
					
				while (rSet.next()) {
					this.setProjectSetupStepTabbedPanelAvailable(rSet.getInt("projectSetupStepTabbedPanelAvailable"));
					this.setProjectTabDone(rSet.getInt("projectTabDone"));
					this.setConceptTabDone(rSet.getInt("conceptTabDone"));
					this.setReferenceTabDone(rSet.getInt("referenceTabDone"));
					
					this.setTargetVariableStepTabbedPanelAvailable(rSet.getInt("targetVariableStepTabbedPanelAvailable"));
					this.setMeasurementTabDone(rSet.getInt("measurementTabDone"));
					
					this.setConceptualStepTabbedPanelAvailable(rSet.getInt("conceptualStepTabbedPanelAvailable"));
					this.setDimensionTabDone(rSet.getInt("dimensionTabDone"));
					this.setSpecificationTabDone(rSet.getInt("specificationTabDone"));
					this.setMapDimensionInstanceTabDone(rSet.getInt("mapDimensionInstanceTabDone"));
					this.setMapDimensionAttributeTabDone(rSet.getInt("mapDimensionAttributeTabDone"));
					this.setMapDimensionChaTabDone(rSet.getInt("mapDimensionChaTabDone"));
					
					this.setOperationalStepTabbedPanelAvailable(rSet.getInt("operationalStepTabbedPanelAvailable"));
					this.setOsInstanceTabDone(rSet.getInt("osInstanceTabDone"));
					this.setIndicatorTabDone(rSet.getInt("indicatorTabDone"));
					this.setPrescriptionTabDone(rSet.getInt("prescriptionTabDone"));
					this.setMapIndicatorInstanceTabDone(rSet.getInt("mapIndicatorInstanceTabDone"));
					this.setMapIndicatorAttributeTabDone(rSet.getInt("mapIndicatorAttributeTabDone"));
					this.setMapIndicatorChaTabDone(rSet.getInt("mapIndicatorChaTabDone"));
					
					this.setSearchNCompareStepTabbedPanelAvailable(rSet.getInt("searchNCompareStepTabbedPanelAvailable"));
					this.setSearchTabDone(rSet.getInt("searchTabDone"));
					this.setComparisonTabDone(rSet.getInt("compareTabDone"));
					this.setCompareValuesTabDone(rSet.getInt("compareValuesTabDone"));
					
					this.setDataRecodingStepTabbedPanelAvailable(rSet.getInt("dataRecodingStepTabbedPanelAvailable"));
					this.setDrInstanceTabDone(rSet.getInt("drInstanceTabDone"));
					this.setVariableTabDone(rSet.getInt("variableTabDone"));
					this.setValueTabDone(rSet.getInt("valueTabDone"));
					this.setQuestionTabDone(rSet.getInt("questionTabDone"));
					this.setStudyTabDone(rSet.getInt("studyTabDone"));
					this.setMapVariableInstanceTabDone(rSet.getInt("mapVariableInstanceTabDone"));
					this.setMapVariableAttributeTabDone(rSet.getInt("mapVariableAttributeTabDone"));
					this.setMapVariableChaTabDone(rSet.getInt("mapVariableChaTabDone"));
					
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
				
				if (storeStatus) {
					sqlQuery = "UPDATE progresses SET "
						+ " projectSetupStepTabbedPanelAvailable = "	+ getProjectSetupStepTabbedPanelAvailable()
						+ ", projectTabDone = "							+ getProjectTabDone()
						+ ", conceptTabDone = "							+ getConceptTabDone()
						+ ", referenceTabDone = "						+ getReferenceTabDone()
						+ ", targetVariableStepTabbedPanelAvailable = "	+ getTargetVariableStepTabbedPanelAvailable()
						+ ", measurementTabDone = "						+ getMeasurementTabDone()
						+ ", conceptualStepTabbedPanelAvailable = "		+ getConceptualStepTabbedPanelAvailable()
						+ ", dimensionTabDone = "						+ getDimensionTabDone()
						+ ", specificationTabDone = "					+ getSpecificationTabDone()
						+ ", mapDimensionInstanceTabDone = "			+ getMapDimensionInstanceTabDone()
						+ ", mapDimensionAttributeTabDone = "			+ getMapDimensionAttributeTabDone()
						+ ", mapDimensionChaTabDone = "					+ getMapDimensionChaTabDone()
						+ ", operationalStepTabbedPanelAvailable = "	+ getOperationalStepTabbedPanelAvailable()
						+ ", osInstanceTabDone = "						+ getOsInstanceTabDone()
						+ ", indicatorTabDone = "						+ getIndicatorTabDone()
						+ ", prescriptionTabDone = "					+ getPrescriptionTabDone()
						+ ", mapIndicatorInstanceTabDone = "			+ getMapIndicatorInstanceTabDone()
						+ ", mapIndicatorAttributeTabDone = "			+ getMapIndicatorAttributeTabDone()
						+ ", mapIndicatorChaTabDone = "					+ getMapIndicatorChaTabDone()
						+ ", searchNCompareStepTabbedPanelAvailable = "	+ getSearchNCompareStepTabbedPanelAvailable()
						+ ", searchTabDone = "							+ getSearchTabDone()
						+ ", compareTabDone = "							+ getComparisonTabDone()
						+ ", compareValuesTabDone = "					+ getCompareValuesTabDone()
						+ ", dataRecodingStepTabbedPanelAvailable = "	+ getDataRecodingStepTabbedPanelAvailable()
						+ ", drInstanceTabDone = "						+ getDrInstanceTabDone()
						+ ", variableTabDone = "						+ getVariableTabDone()
						+ ", valueTabDone = "							+ getValueTabDone()
						+ ", questionTabDone = "						+ getQuestionTabDone()
						+ ", studyTabDone = "							+ getStudyTabDone()
						+ ", mapVariableInstanceTabDone = "				+ getMapVariableInstanceTabDone()
						+ ", mapVariableAttributeTabDone = "			+ getMapVariableAttributeTabDone()
						+ ", mapVariableChaTabDone = "					+ getMapVariableChaTabDone();
					sqlQuery += " WHERE id = " + getEntityID();
					
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
				
				if (storeStatus) {
					/*
					 *	Use Insert-Statement
					 */		
					sqlQuery = "INSERT INTO progresses (";
					sqlQuery += "projectSetupStepTabbedPanelAvailable,"	+ 
							"projectTabDone,conceptTabDone,referenceTabDone," +
							"targetVariableStepTabbedPanelAvailable," +
							"measurementTabDone," +
							"conceptualStepTabbedPanelAvailable," +
							"dimensionTabDone,specificationTabDone,mapDimensionInstanceTabDone,mapDimensionAttributeTabDone,mapDimensionChaTabDone," +
							"operationalStepTabbedPanelAvailable," +
							"osInstanceTabDone,indicatorTabDone,prescriptionTabDone,mapIndicatorInstanceTabDone,mapIndicatorAttributeTabDone,mapIndicatorChaTabDone," +
							"searchNCompareStepTabbedPanelAvailable," +
							"searchTabDone,compareTabDone,compareValuesTabDone," +
							"dataRecodingStepTabbedPanelAvailable," +
							"drInstanceTabDone,variableTabDone,valueTabDone,questionTabDone,studyTabDone,mapVariableInstanceTabDone,mapVariableAttributeTabDone,mapVariableChaTabDone";
					sqlQuery += ") VALUES (" +
							getProjectSetupStepTabbedPanelAvailable()	+ "," +
							getProjectTabDone()							+ "," +
							getConceptTabDone()							+ "," +
							getReferenceTabDone()						+ "," +
							getTargetVariableStepTabbedPanelAvailable() + "," +
							getMeasurementTabDone()						+ "," +
							getConceptualStepTabbedPanelAvailable()		+ "," +
							getDimensionTabDone()						+ "," +
							getSpecificationTabDone()					+ "," +
							getMapDimensionInstanceTabDone()			+ "," +
							getMapDimensionAttributeTabDone()			+ "," +
							getMapDimensionChaTabDone()					+ "," +
							getOperationalStepTabbedPanelAvailable()	+ "," +
							getOsInstanceTabDone()						+ "," +
							getIndicatorTabDone()						+ "," +
							getPrescriptionTabDone()					+ "," +
							getMapIndicatorInstanceTabDone()			+ "," +
							getMapIndicatorAttributeTabDone()			+ "," +
							getMapIndicatorChaTabDone()					+ "," +
							getSearchNCompareStepTabbedPanelAvailable()	+ "," +
							getSearchTabDone()							+ "," +
							getComparisonTabDone()						+ "," +
							getCompareValuesTabDone()					+ "," +
							getDataRecodingStepTabbedPanelAvailable()	+ "," +
							getDrInstanceTabDone()						+ "," +
							getVariableTabDone()						+ "," +
							getValueTabDone()							+ "," +
							getQuestionTabDone()						+ "," +
							getStudyTabDone()							+ "," +
							getMapVariableInstanceTabDone()				+ "," +
							getMapVariableAttributeTabDone()			+ "," +
							getMapVariableChaTabDone();
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

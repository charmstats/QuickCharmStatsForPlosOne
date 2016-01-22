package org.gesis.charmstats.view;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.event.ChangeListener;

import org.gesis.charmstats.model.CStatsModel;
import org.gesis.charmstats.model.Project;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class TabbedPaneDataReCodingStep extends TabbedPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String BUNDLE				= "org.gesis.charmstats.resources.DesktopBundle";
	
	public static final String TPDRCS_DR_INSTANCE			= "dr_instance";
	public static final String TPDRCS_VARIABLE				= "variable";
	public static final String TPDRCS_VALUES				= "values";
	public static final String TPDRCS_QUESTION				= "question"; 
	public static final String TPDRCS_STUDY					= "study"; 
	public static final String TPDRCS_MAPPING_INSTANCES		= "map_variable_instances";
	public static final String TPDRCS_MAPPING_ATTRIBUTES	= "map_variable_attributes";
	public static final String TPDRCS_MAPPING_VALUES		= "map_variable_values";
	
	public static final int	DR__INS_TAB_IDX = 0;
	public static final int	VAR_TAB_IDX = 1;
	public static final int	VAL_TAB_IDX = 2;
	public static final int QUE_TAB_IDX = 3; 
	public static final int STU_TAB_IDX = 4; 
	public static final int	MAP_VAR_INS_TAB_IDX = 5; // 3
	public static final int	MAP_VAR_ATR_TAB_IDX = 6; // 4
	public static final int	MAP_VAR_CHA_TAB_IDX = 7; // 5
	
	/*
	 *	Fields
	 */
	TabDRInstance			drInstanceTab;
	TabVariable				variableTab;
	TabValues				valuesTab;
	TabQuestion				questionTab; 
	TabStudy				studyTab; 
	TabMapVariableInstance	mapInstanceTab;
	TabMapVariableAttribute	mapAttributeTab;
	TabMapVariableChar		mapValueTab;
	
	/*
	 *	Constructor
	 */
	/**
	 * 
	 */
	public TabbedPaneDataReCodingStep() {
		super();
	}
	
	/**
	 * @param al
	 * @param fal
	 * @param cl
	 * @param locale
	 * @param addenda
	 */
	public TabbedPaneDataReCodingStep(ActionListener al, ActionListener fal,ChangeListener cl, Locale locale, Object addenda) {
		this();
		
		Project project = ((CStatsModel)addenda).getProject();
		
		currentLocale	= locale;
		resourceBundle	= ResourceBundle.getBundle(BUNDLE, currentLocale);
		
		drInstanceTab	= new TabDRInstance(al, fal, locale);
		variableTab		= new TabVariable(((CStatsModel)addenda), al, locale);
		valuesTab		= new TabValues(al, locale);
		questionTab		= new TabQuestion(al, locale); 
		studyTab		= new TabStudy(al, locale); 
		mapInstanceTab	= new TabMapVariableInstance(((CStatsModel)addenda), al, locale);
		mapAttributeTab	= new TabMapVariableAttribute(((CStatsModel)addenda), al, locale);
		mapValueTab		= new TabMapVariableChar(((CStatsModel)addenda), al, locale);
		
		drInstanceTab.fillModel((CStatsModel)addenda);
		variableTab.fillModel((CStatsModel)addenda);
			
		insertTab(resourceBundle.getString(TPDRCS_DR_INSTANCE), null, drInstanceTab, "", DR__INS_TAB_IDX);
		insertTab(resourceBundle.getString(TPDRCS_VARIABLE), null, variableTab, "", VAR_TAB_IDX);
		insertTab(resourceBundle.getString(TPDRCS_VALUES), null, valuesTab, "", VAL_TAB_IDX);
		insertTab(resourceBundle.getString(TPDRCS_QUESTION), null, questionTab, "", QUE_TAB_IDX); 
		insertTab(resourceBundle.getString(TPDRCS_STUDY), null, studyTab, "", STU_TAB_IDX); 
		insertTab(resourceBundle.getString(TPDRCS_MAPPING_INSTANCES), null, mapInstanceTab, "", MAP_VAR_INS_TAB_IDX);
		insertTab(resourceBundle.getString(TPDRCS_MAPPING_ATTRIBUTES), null, mapAttributeTab, "", MAP_VAR_ATR_TAB_IDX);
		insertTab(resourceBundle.getString(TPDRCS_MAPPING_VALUES), null, mapValueTab, "", MAP_VAR_CHA_TAB_IDX);
		
		this.setEnabledAt(DR__INS_TAB_IDX, ((project != null) ? project.getProgress().isDrInstanceTabDone() : false));
		this.setEnabledAt(VAR_TAB_IDX, ((project != null) ? project.getProgress().isVariableTabDone() : false));
		this.setEnabledAt(VAL_TAB_IDX, ((project != null) ? project.getProgress().isValueTabDone() : false));
		this.setEnabledAt(QUE_TAB_IDX, ((project != null) ? project.getProgress().isQuestionTabDone() : false)); 
		this.setEnabledAt(STU_TAB_IDX, ((project != null) ? project.getProgress().isStudyTabDone() : false)); 
		this.setEnabledAt(MAP_VAR_INS_TAB_IDX, ((project != null) ? project.getProgress().isMapVariableInstanceTabDone() : false));
		this.setEnabledAt(MAP_VAR_ATR_TAB_IDX, ((project != null) ? project.getProgress().isMapVariableAttributeTabDone() : false));
		this.setEnabledAt(MAP_VAR_CHA_TAB_IDX, ((project != null) ? project.getProgress().isMapVariableChaTabDone() : false));
		
		
		this.setSelectedTab(drInstanceTab);
		
		this.addChangeListener(cl);
	}
	
	/**
	 * @param model
	 */
	public void enableTabs(CStatsModel model) {
		Project project = model.getProject();
		
		this.setEnabledAt(DR__INS_TAB_IDX, ((project != null) ? project.getProgress().isDrInstanceTabDone() : false));
		this.setEnabledAt(VAR_TAB_IDX, ((project != null) ? project.getProgress().isVariableTabDone() : false));
		this.setEnabledAt(VAL_TAB_IDX, ((project != null) ? project.getProgress().isValueTabDone() : false));
		this.setEnabledAt(QUE_TAB_IDX, ((project != null) ? project.getProgress().isQuestionTabDone() : false)); 
		this.setEnabledAt(STU_TAB_IDX, ((project != null) ? project.getProgress().isStudyTabDone() : false)); 
		this.setEnabledAt(MAP_VAR_INS_TAB_IDX, ((project != null) ? project.getProgress().isMapVariableInstanceTabDone() : false));
		this.setEnabledAt(MAP_VAR_ATR_TAB_IDX, ((project != null) ? project.getProgress().isMapVariableAttributeTabDone() : false));
		this.setEnabledAt(MAP_VAR_CHA_TAB_IDX, ((project != null) ? project.getProgress().isMapVariableChaTabDone() : false));
	}
	
	/**
	 * 
	 */
	public void disableTabs() {
		this.setEnabledAt(DR__INS_TAB_IDX, false);
		this.setEnabledAt(VAR_TAB_IDX, false);
		this.setEnabledAt(VAL_TAB_IDX, false);
		this.setEnabledAt(QUE_TAB_IDX, false); 
		this.setEnabledAt(STU_TAB_IDX, false); 
		this.setEnabledAt(MAP_VAR_INS_TAB_IDX, false);
		this.setEnabledAt(MAP_VAR_ATR_TAB_IDX, false);
		this.setEnabledAt(MAP_VAR_CHA_TAB_IDX, false);
	}

	/*
	 *	Methods
	 */
	/**
	 * @param locale
	 */
	public void changeLanguage(Locale locale) {
		ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, locale);
		
		setTitleAt(DR__INS_TAB_IDX, bundle.getString(TPDRCS_DR_INSTANCE));
		drInstanceTab.changeLanguage(locale);
		setTitleAt(VAR_TAB_IDX, bundle.getString(TPDRCS_VARIABLE));
		variableTab.changeLanguage(locale);
		setTitleAt(VAL_TAB_IDX, bundle.getString(TPDRCS_VALUES));
		valuesTab.changeLanguage(locale);
		setTitleAt(QUE_TAB_IDX, bundle.getString(TPDRCS_QUESTION)); 
		questionTab.changeLanguage(locale); 
		setTitleAt(STU_TAB_IDX, bundle.getString(TPDRCS_STUDY)); 
		studyTab.changeLanguage(locale); 
		setTitleAt(MAP_VAR_INS_TAB_IDX, bundle.getString(TPDRCS_MAPPING_INSTANCES));
		mapInstanceTab.changeLanguage(locale);
		setTitleAt(MAP_VAR_ATR_TAB_IDX, bundle.getString(TPDRCS_MAPPING_ATTRIBUTES));
		mapAttributeTab.changeLanguage(locale);
		setTitleAt(MAP_VAR_CHA_TAB_IDX, bundle.getString(TPDRCS_MAPPING_VALUES));
		mapValueTab.changeLanguage(locale);
	}

	/**
	 * @param f
	 */
	public void changeFont(Font f) {
		this.setFont(f);
		this.updateUI();
		
		drInstanceTab.changeFont(f);
		variableTab.changeFont(f);
		valuesTab.changeFont(f);
		questionTab.changeFont(f); 
		studyTab.changeFont(f); 
		mapInstanceTab.changeFont(f);
		mapAttributeTab.changeFont(f);
		mapValueTab.changeFont(f);
	}
}

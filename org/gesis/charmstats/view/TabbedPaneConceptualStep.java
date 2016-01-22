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
 *	@since	0.1 / CharmStatsPro only
 *
 */
public class TabbedPaneConceptualStep extends TabbedPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String BUNDLE				= "org.gesis.charmstats.resources.DesktopBundle";
	
	public static final String TPCS_DIMENSION			= "dimension";
	public static final String TPCS_SPECIFICATION		= "specification";
	public static final String TPCS_MAPPING_INSTANCES	= "map_dimension_instances";
	public static final String TPCS_MAPPING_ATTRIBUTES	= "map_dimension_attributes";
	public static final String TPCS_MAPPING_VALUES		= "map_dimension_values";
	
	public static final int	DIM_TAB_IDX = 0;
	public static final int	SPE_TAB_IDX = 1;
	public static final int	MAP_DIM_INS_TAB_IDX = 2;
	public static final int	MAP_DIM_ATR_TAB_IDX = 3;
	public static final int	MAP_DIM_CHA_TAB_IDX = 4;
	
	/*
	 *	Fields
	 */
	TabDimension				dimensionTab;
	TabSpecifications			specificationTab;
	TabMapDimensionInstance		mapInstanceTab;
	TabMapDimensionAttribute	mapAttributeTab;
	TabMapDimensionChar			mapValueTab;
	
	/*
	 *	Constructor
	 */
	/**
	 * 
	 */
	public TabbedPaneConceptualStep() {
		super();
	}
	
	/**
	 * @param al
	 * @param cl
	 * @param locale
	 * @param addenda
	 */
	public TabbedPaneConceptualStep(ActionListener al, ChangeListener cl, Locale locale, Object addenda) {
		this();
		
		Project project = ((CStatsModel)addenda).getProject();
		
		currentLocale	= locale;
		resourceBundle	= ResourceBundle.getBundle(BUNDLE, currentLocale);		
		
		dimensionTab	= new TabDimension(al, locale);
		specificationTab	= new TabSpecifications(al, locale);
		mapInstanceTab		= new TabMapDimensionInstance(((CStatsModel)addenda), al, locale);
		mapAttributeTab	= new TabMapDimensionAttribute(((CStatsModel)addenda), al, locale);
		mapValueTab			= new TabMapDimensionChar(((CStatsModel)addenda), al, locale);
		
		dimensionTab.fillModel((CStatsModel)addenda);
		specificationTab.fillModel((CStatsModel)addenda);
			
		insertTab(resourceBundle.getString(TPCS_DIMENSION), null, dimensionTab, "", DIM_TAB_IDX);
		insertTab(resourceBundle.getString(TPCS_SPECIFICATION), null, specificationTab, "", SPE_TAB_IDX);
		insertTab(resourceBundle.getString(TPCS_MAPPING_INSTANCES), null, mapInstanceTab, "", MAP_DIM_INS_TAB_IDX);
		insertTab(resourceBundle.getString(TPCS_MAPPING_ATTRIBUTES), null, mapAttributeTab, "", MAP_DIM_ATR_TAB_IDX);
		insertTab(resourceBundle.getString(TPCS_MAPPING_VALUES), null, mapValueTab, "", MAP_DIM_CHA_TAB_IDX);
		
		this.setEnabledAt(DIM_TAB_IDX, ((project != null) ? project.getProgress().isDimensionTabDone() : false));
		this.setEnabledAt(SPE_TAB_IDX, ((project != null) ? project.getProgress().isSpecificationTabDone() : false));
		this.setEnabledAt(MAP_DIM_INS_TAB_IDX, ((project != null) ? project.getProgress().isMapDimensionInstanceTabDone() : false));
		this.setEnabledAt(MAP_DIM_ATR_TAB_IDX, ((project != null) ? project.getProgress().isMapDimensionAttributeTabDone() : false));
		this.setEnabledAt(MAP_DIM_CHA_TAB_IDX, ((project != null) ? project.getProgress().isMapDimensionChaTabDone() : false));
		
		this.setSelectedTab(dimensionTab);
		
		this.addChangeListener(cl);
	}
	
	/**
	 * @param model
	 */
	public void enableTabs(CStatsModel model) {
		Project project = model.getProject();
		
		this.setEnabledAt(DIM_TAB_IDX, ((project != null) ? project.getProgress().isDimensionTabDone() : false));
		this.setEnabledAt(SPE_TAB_IDX, ((project != null) ? project.getProgress().isSpecificationTabDone() : false));
		this.setEnabledAt(MAP_DIM_INS_TAB_IDX, ((project != null) ? project.getProgress().isMapDimensionInstanceTabDone() : false));
		this.setEnabledAt(MAP_DIM_ATR_TAB_IDX, ((project != null) ? project.getProgress().isMapDimensionAttributeTabDone() : false));
		this.setEnabledAt(MAP_DIM_CHA_TAB_IDX, ((project != null) ? project.getProgress().isMapDimensionChaTabDone() : false));
	}
	
	/**
	 * 
	 */
	public void disableTabs() {
		this.setEnabledAt(DIM_TAB_IDX, false);
		this.setEnabledAt(SPE_TAB_IDX, false);
		this.setEnabledAt(MAP_DIM_INS_TAB_IDX, false);
		this.setEnabledAt(MAP_DIM_ATR_TAB_IDX, false);
		this.setEnabledAt(MAP_DIM_CHA_TAB_IDX, false);
	}
	
	/*
	 *	Methods
	 */
	/**
	 * @param locale
	 */
	public void changeLanguage(Locale locale) {
		ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, locale);
		
		setTitleAt(DIM_TAB_IDX, bundle.getString(TPCS_DIMENSION));
		dimensionTab.changeLanguage(locale);
		setTitleAt(SPE_TAB_IDX, bundle.getString(TPCS_SPECIFICATION));
		specificationTab.changeLanguage(locale);
		setTitleAt(MAP_DIM_INS_TAB_IDX, bundle.getString(TPCS_MAPPING_INSTANCES));
		mapInstanceTab.changeLanguage(locale);
		setTitleAt(MAP_DIM_ATR_TAB_IDX, bundle.getString(TPCS_MAPPING_ATTRIBUTES));
		mapAttributeTab.changeLanguage(locale);
		setTitleAt(MAP_DIM_CHA_TAB_IDX, bundle.getString(TPCS_MAPPING_VALUES));
		mapValueTab.changeLanguage(locale);
	}
	
	/**
	 * @param f
	 */
	public void changeFont(Font f) {
		this.setFont(f);
		this.updateUI();
		
		dimensionTab.changeFont(f);		
		specificationTab.changeFont(f);		
		mapInstanceTab.changeFont(f);		
		mapAttributeTab.changeFont(f);		
		mapValueTab.changeFont(f);
	}
	
}

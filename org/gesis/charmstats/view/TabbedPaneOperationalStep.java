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
public class TabbedPaneOperationalStep extends TabbedPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8007633900038680875L;
	
	public static final String BUNDLE				= "org.gesis.charmstats.resources.DesktopBundle";
	
	public static final String TPOS_OS_INSTANCE			= "os_instance";
	public static final String TPOS_INDICATOR			= "indicator";
	public static final String TPOS_PRESCRIPTION		= "prescription";
	public static final String TPOS_MAPPING_INSTANCES	= "map_indicator_instances";
	public static final String TPOS_MAPPING_ATTRIBUTES	= "map_indicator_attributes";
	public static final String TPOS_MAPPING_VALUES		= "map_indicator_values";
	
	public static final int	OS__INS_TAB_IDX = 0;
	public static final int	IND_TAB_IDX = 1;
	public static final int	PRE_TAB_IDX = 2;
	public static final int	MAP_IND_INS_TAB_IDX = 3;
	public static final int	MAP_IND_ATR_TAB_IDX = 4;
	public static final int	MAP_IND_CHA_TAB_IDX = 5;
	
	/*
	 *	Fields
	 */
	TabOSInstance				osInstanceTab;
	TabIndicator				indicatorTab;
	TabPrescriptions			prescriptionTab;
	TabMapIndicatorInstance		mapInstanceTab;
	TabMapIndicatorAttribute	mapAttributeTab;
	TabMapIndicatorChar			mapValueTab;
	
	/*
	 *	Constructor
	 */
	/**
	 * 
	 */
	public TabbedPaneOperationalStep() {
		super();
	}
	
	/**
	 * @param al
	 * @param cl
	 * @param locale
	 * @param addenda
	 */
	public TabbedPaneOperationalStep(ActionListener al, ChangeListener cl, Locale locale, Object addenda) {
		this();
		
		Project project = ((CStatsModel)addenda).getProject();
		
		currentLocale	= locale;
		resourceBundle	= ResourceBundle.getBundle(BUNDLE, currentLocale);
		
		osInstanceTab	= new TabOSInstance(al, locale);
		indicatorTab	= new TabIndicator(((CStatsModel)addenda),al, locale);
		prescriptionTab	= new TabPrescriptions(al, locale);
		mapInstanceTab		= new TabMapIndicatorInstance(((CStatsModel)addenda), al, locale);
		mapAttributeTab	= new TabMapIndicatorAttribute(((CStatsModel)addenda), al, locale);
		mapValueTab			= new TabMapIndicatorChar(((CStatsModel)addenda), al, locale);
		
		osInstanceTab.fillModel((CStatsModel)addenda);
		indicatorTab.fillModel((CStatsModel)addenda);
		prescriptionTab.fillModel((CStatsModel)addenda);
			
		insertTab(resourceBundle.getString(TPOS_OS_INSTANCE), null, osInstanceTab, "", OS__INS_TAB_IDX);
		insertTab(resourceBundle.getString(TPOS_INDICATOR), null, indicatorTab, "", IND_TAB_IDX);
		insertTab(resourceBundle.getString(TPOS_PRESCRIPTION), null, prescriptionTab, "", PRE_TAB_IDX);
		insertTab(resourceBundle.getString(TPOS_MAPPING_INSTANCES), null, mapInstanceTab, "", MAP_IND_INS_TAB_IDX);
		insertTab(resourceBundle.getString(TPOS_MAPPING_ATTRIBUTES), null, mapAttributeTab, "", MAP_IND_ATR_TAB_IDX);
		insertTab(resourceBundle.getString(TPOS_MAPPING_VALUES), null, mapValueTab, "", MAP_IND_CHA_TAB_IDX);
			
		this.setEnabledAt(OS__INS_TAB_IDX, ((project != null) ? project.getProgress().isOsInstanceTabDone() : false));
		this.setEnabledAt(IND_TAB_IDX, ((project != null) ? project.getProgress().isIndicatorTabDone() : false));
		this.setEnabledAt(PRE_TAB_IDX, ((project != null) ? project.getProgress().isPrescriptionTabDone() : false));
		this.setEnabledAt(MAP_IND_INS_TAB_IDX, ((project != null) ? project.getProgress().isMapIndicatorInstanceTabDone() : false));
		this.setEnabledAt(MAP_IND_ATR_TAB_IDX, ((project != null) ? project.getProgress().isMapIndicatorAttributeTabDone() : false));
		this.setEnabledAt(MAP_IND_CHA_TAB_IDX, ((project != null) ? project.getProgress().isMapIndicatorChaTabDone() : false));
		
		this.setSelectedTab(osInstanceTab);
		
		this.addChangeListener(cl);
	}
	
	/**
	 * @param model
	 */
	public void enableTabs(CStatsModel model) {
		Project project = model.getProject();
		
		this.setEnabledAt(OS__INS_TAB_IDX, ((project != null) ? project.getProgress().isOsInstanceTabDone() : false));
		this.setEnabledAt(IND_TAB_IDX, ((project != null) ? project.getProgress().isIndicatorTabDone() : false));
		this.setEnabledAt(PRE_TAB_IDX, ((project != null) ? project.getProgress().isPrescriptionTabDone() : false));
		this.setEnabledAt(MAP_IND_INS_TAB_IDX, ((project != null) ? project.getProgress().isMapIndicatorInstanceTabDone() : false));
		this.setEnabledAt(MAP_IND_ATR_TAB_IDX, ((project != null) ? project.getProgress().isMapIndicatorAttributeTabDone() : false));
		this.setEnabledAt(MAP_IND_CHA_TAB_IDX, ((project != null) ? project.getProgress().isMapIndicatorChaTabDone() : false));
	}
	
	/**
	 * 
	 */
	public void disableTabs() {
		this.setEnabledAt(OS__INS_TAB_IDX, false);
		this.setEnabledAt(IND_TAB_IDX, false);
		this.setEnabledAt(PRE_TAB_IDX, false);
		this.setEnabledAt(MAP_IND_INS_TAB_IDX, false);
		this.setEnabledAt(MAP_IND_ATR_TAB_IDX, false);
		this.setEnabledAt(MAP_IND_CHA_TAB_IDX, false);
	}

	/*
	 *	Methods
	 */
	/**
	 * @param locale
	 */
	public void changeLanguage(Locale locale) {
		ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, locale);
		
		setTitleAt(OS__INS_TAB_IDX, bundle.getString(TPOS_OS_INSTANCE));
		osInstanceTab.changeLanguage(locale);
		setTitleAt(IND_TAB_IDX, bundle.getString(TPOS_INDICATOR));
		indicatorTab.changeLanguage(locale);
		setTitleAt(PRE_TAB_IDX, bundle.getString(TPOS_PRESCRIPTION));
		prescriptionTab.changeLanguage(locale);
		setTitleAt(MAP_IND_INS_TAB_IDX, bundle.getString(TPOS_MAPPING_INSTANCES));
		mapInstanceTab.changeLanguage(locale);
		setTitleAt(MAP_IND_ATR_TAB_IDX, bundle.getString(TPOS_MAPPING_ATTRIBUTES));
		mapAttributeTab.changeLanguage(locale);
		setTitleAt(MAP_IND_CHA_TAB_IDX, bundle.getString(TPOS_MAPPING_VALUES));
		mapValueTab.changeLanguage(locale);
	}
	
	/**
	 * @param f
	 */
	public void changeFont(Font f) {
		this.setFont(f);
		this.updateUI();
		
		osInstanceTab.changeFont(f);
		indicatorTab.changeFont(f);
		prescriptionTab.changeFont(f);
		mapInstanceTab.changeFont(f);
		mapAttributeTab.changeFont(f);
		mapValueTab.changeFont(f);
	}

}

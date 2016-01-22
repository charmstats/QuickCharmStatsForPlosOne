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
public class TabbedPaneSearchNCompareStep extends TabbedPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = -98382996770726244L;
	
	public static final String BUNDLE	= "org.gesis.charmstats.resources.DesktopBundle";
	
	public static final String TPSNCS_SEARCH	= "search_variable";
	public static final String TPSNCS_COMPARE	= "compare_variables";
	public static final String TPSNCS_VALUES	= "compare_values";		
	
	public static final int	SEA_TAB_IDX = 0;
	public static final int	COM_TAB_IDX = 1;
	public static final int VAL_TAB_IDX = 2;
	
	/*
	 *	Fields
	 */
	TabSearchVariable	searchVariableTab;
	TabCompareMetadata	compareVariablesTab;
	TabCompareValues	compareValuesTab;
	
	/*
	 *	Constructor
	 */
	/**
	 * 
	 */
	public TabbedPaneSearchNCompareStep() {
		super();
	}
	
	/**
	 * @param al
	 * @param cl
	 * @param locale
	 * @param addenda
	 */
	public TabbedPaneSearchNCompareStep(ActionListener al, ChangeListener cl, Locale locale, Object addenda) {
		this();
				
		currentLocale	= locale;
		resourceBundle	= ResourceBundle.getBundle(BUNDLE, currentLocale);
			
		searchVariableTab = new TabSearchVariable(al, locale);
		compareVariablesTab = new TabCompareMetadata((CStatsModel)addenda, al, locale);
		compareValuesTab = new TabCompareValues((CStatsModel)addenda, al, locale);
		
		insertTab(resourceBundle.getString(TPSNCS_SEARCH), null, searchVariableTab, "", SEA_TAB_IDX);
		insertTab(resourceBundle.getString(TPSNCS_COMPARE), null, compareVariablesTab, "", COM_TAB_IDX);
		insertTab(resourceBundle.getString(TPSNCS_VALUES), null, compareValuesTab, "", VAL_TAB_IDX);
		
		Project project = ((CStatsModel)addenda).getProject();
		
		this.setEnabledAt(SEA_TAB_IDX, ((project != null) ? project.getProgress().isSearchTabDone() : false));
		this.setEnabledAt(COM_TAB_IDX, ((project != null) ? project.getProgress().isComparisonTabDone() : false));
		this.setEnabledAt(VAL_TAB_IDX, ((project != null) ? project.getProgress().isCompareValuesTabDone() : false));
		
		this.setSelectedTab(searchVariableTab);
		
		this.addChangeListener(cl);
	}
	
	/**
	 * @param model
	 */
	public void enableTabs(CStatsModel model) {
		Project project = model.getProject();
		
		this.setEnabledAt(SEA_TAB_IDX, ((project != null) ? project.getProgress().isSearchTabDone() : false));
		this.setEnabledAt(COM_TAB_IDX, ((project != null) ? project.getProgress().isComparisonTabDone() : false));
		this.setEnabledAt(VAL_TAB_IDX, ((project != null) ? project.getProgress().isCompareValuesTabDone() : false));
	}
	
	/**
	 * 
	 */
	public void disableTabs() {
		this.setEnabledAt(SEA_TAB_IDX, false);
		this.setEnabledAt(COM_TAB_IDX, false);
		this.setEnabledAt(VAL_TAB_IDX, false);
	}

	/*
	 *	Methods
	 */
	/**
	 * @param locale
	 */
	public void changeLanguage(Locale locale) {
		ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, locale);
		
		setTitleAt(SEA_TAB_IDX, bundle.getString(TPSNCS_SEARCH));
		searchVariableTab.changeLanguage(locale);	
		setTitleAt(COM_TAB_IDX, bundle.getString(TPSNCS_COMPARE));
		compareVariablesTab.changeLanguage(locale);
		setTitleAt(VAL_TAB_IDX, bundle.getString(TPSNCS_VALUES));
		compareValuesTab.changeLanguage(locale);
	}
	
	/**
	 * @param f
	 */
	public void changeFont(Font f) {
		this.setFont(f);
		this.updateUI();
		
		searchVariableTab.changeFont(f);	
		compareVariablesTab.changeFont(f);
		compareValuesTab.changeFont(f);
	}

}

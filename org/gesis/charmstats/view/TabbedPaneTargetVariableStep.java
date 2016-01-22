package org.gesis.charmstats.view;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.event.ChangeListener;

import org.gesis.charmstats.model.CStatsModel;
import org.gesis.charmstats.model.Project;

/**
 * 
 *	@author	Martin Friedrichs
 *	@since	0.6.2
 *
 */
public class TabbedPaneTargetVariableStep extends TabbedPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String BUNDLE				= "org.gesis.charmstats.resources.DesktopBundle";
	
	public static final String TPTVS_MEASUREMENT		= "measurement";
	
	public static final int	MEA_TAB_IDX = 0;
	
	/*
	 *	Fields
	 */
	TabMeasurement	measurementTab;
	
	/*
	 *	Constructor
	 */
	/**
	 * 
	 */
	public TabbedPaneTargetVariableStep() {
		super();
	}
	
	/**
	 * @param al
	 * @param cl
	 * @param locale
	 * @param addenda
	 */
	public TabbedPaneTargetVariableStep(ActionListener al, ChangeListener cl, Locale locale, Object addenda) {
		this();
		
		Project project = ((CStatsModel)addenda).getProject();
		
		currentLocale	= locale;
		resourceBundle	= ResourceBundle.getBundle(BUNDLE, currentLocale);		
		
		measurementTab	= new TabMeasurement(al, locale, null, null, addenda);
		
		measurementTab.fillModel((CStatsModel)addenda);
			
		insertTab(resourceBundle.getString(TPTVS_MEASUREMENT), null, measurementTab, "", MEA_TAB_IDX);
		
		this.setEnabledAt(MEA_TAB_IDX, ((project != null) ? project.getProgress().isMeasurementTabDone() : false));
		
		this.setSelectedTab(measurementTab);
		
		this.addChangeListener(cl);
	}
	
	/**
	 * @param model
	 */
	public void enableTabs(CStatsModel model) {
		Project project = model.getProject();
		
		this.setEnabledAt(MEA_TAB_IDX, ((project != null) ? project.getProgress().isMeasurementTabDone() : false));
	}
	
	/**
	 * 
	 */
	public void disableTabs() {
		this.setEnabledAt(MEA_TAB_IDX, false);
	}
	
	/*
	 *	Methods
	 */
	/**
	 * @param locale
	 */
	public void changeLanguage(Locale locale) {
		ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, locale);
		
		setTitleAt(MEA_TAB_IDX, bundle.getString(TPTVS_MEASUREMENT));
		measurementTab.changeLanguage(locale);
	}
	
	/**
	 * @param f
	 */
	public void changeFont(Font f) {
		this.setFont(f);
		this.updateUI();
		
		measurementTab.changeFont(f);
	}
}

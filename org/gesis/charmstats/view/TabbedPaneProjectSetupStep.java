package org.gesis.charmstats.view;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.event.ChangeListener;
import javax.swing.undo.UndoManager;

import org.gesis.charmstats.model.CStatsModel;
import org.gesis.charmstats.model.Project;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class TabbedPaneProjectSetupStep extends TabbedPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3092671069176380675L;
	
	public static final String BUNDLE		= "org.gesis.charmstats.resources.DesktopBundle";
	
	public static final String TPPSS_PROJECT		= "project";
	public static final String TPPSS_CONCEPT		= "concept";
	public static final String TPPSS_LITERATURE	= "literature";
	
	public static final int	PRO_TAB_IDX = 0;
	public static final int	CON_TAB_IDX = 1;
	public static final int	REF_TAB_IDX = 2;
	
	/*
	 *	Fields
	 */
	TabProject		projectTab;
	TabConcept		conceptTab;
	TabLiterature	literatureTab;
	
	/*
	 *	Constructor
	 */
	/**
	 * 
	 */
	public TabbedPaneProjectSetupStep() {
		super();
	}
	
	/**
	 * @param al
	 * @param cl
	 * @param locale
	 * @param addenda
	 */
	public TabbedPaneProjectSetupStep(ActionListener al, ChangeListener cl, Locale locale, Object addenda) {
		this();
		
		Project project = ((CStatsModel)addenda).getProject();
				
		currentLocale	= locale;
		resourceBundle	= ResourceBundle.getBundle(BUNDLE, currentLocale);
			
		projectTab		= new TabProject(al, locale, addenda);
		conceptTab		= new TabConcept(al, locale, addenda);
		literatureTab	= new TabLiterature(al, locale, addenda);
		
		insertTab(resourceBundle.getString(TPPSS_PROJECT), null, projectTab, "", PRO_TAB_IDX);
		insertTab(resourceBundle.getString(TPPSS_CONCEPT), null, conceptTab, "", CON_TAB_IDX);
		insertTab(resourceBundle.getString(TPPSS_LITERATURE), null, literatureTab, "", REF_TAB_IDX);
		
		this.setEnabledAt(PRO_TAB_IDX, ((project != null) ? project.getProgress().isProjectTabDone() : false));
		this.setEnabledAt(CON_TAB_IDX, ((project != null) ? project.getProgress().isConceptTabDone() : false));
		this.setEnabledAt(REF_TAB_IDX, ((project != null) ? project.getProgress().isReferenceTabDone() : false));
		
		this.setSelectedTab(projectTab);
		
		this.addChangeListener(cl); 
	}
	
	/**
	 * @param model
	 */
	public void enableTabs(CStatsModel model) {
		Project project = model.getProject();
		
		this.setEnabledAt(PRO_TAB_IDX, ((project != null) ? project.getProgress().isProjectTabDone() : false));
		this.setEnabledAt(CON_TAB_IDX, ((project != null) ? project.getProgress().isConceptTabDone() : false));
		this.setEnabledAt(REF_TAB_IDX, ((project != null) ? project.getProgress().isReferenceTabDone() : false));
	}
	
	/**
	 * 
	 */
	public void disableTabs() {
		this.setEnabledAt(PRO_TAB_IDX, false);
		this.setEnabledAt(CON_TAB_IDX, false);
		this.setEnabledAt(REF_TAB_IDX, false);
	}

	/**
	 * @param locale
	 */
	public void changeLanguage(Locale locale) {
		ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, locale);
		
		setTitleAt(0, bundle.getString(TPPSS_PROJECT));
		projectTab.changeLanguage(locale);	
		setTitleAt(1, bundle.getString(TPPSS_CONCEPT));
		conceptTab.changeLanguage(locale);
		setTitleAt(2, bundle.getString(TPPSS_LITERATURE));
		literatureTab.changeLanguage(locale);
	}
	
	/**
	 * @param f
	 */
	public void changeFont(Font f) {
		this.setFont(f);
		this.updateUI();
		
		projectTab.changeFont(f);	
		conceptTab.changeFont(f);
		literatureTab.changeFont(f);
	}
		
}

package org.gesis.charmstats.view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.undo.UndoManager;

import org.gesis.charmstats.ActionCommandText;
import org.gesis.charmstats.model.CStatsModel;
import org.gesis.charmstats.model.Project;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class TabbedPaneForm extends TabbedPane implements ChangeListener, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2852400766624359524L;
	
	public static final String BUNDLE	= "org.gesis.charmstats.resources.DesktopBundle";
	
	public static final String TPF_PRO	= "project_setup_step";
	public static final String TPF_TAR	= "target_variable_step";
	public static final String TPF_CON	= "conceptual_step";
	public static final String TPF_OPE	= "operational_step";
	public static final String TPF_SEA	= "search_compare_step";
	public static final String TPF_DAT	= "data_recode_step";
	
	public static final int	PRO_SET_TAB_IDX = 0;
	public static final int TAR_VAR_TAB_IDX	= 1;
	public static final int CON_STE_TAB_IDX	= 2; 
	public static final int OPE_STE_TAB_IDX	= 3; 
	public static final int SEA_COM_STE_TAB_IDX	= 4; 
	public static final int	DAT_REC_STE_TAB_IDX = 5; 
	
	/*
	 *	Fields
	 */
	TabbedPaneProjectSetupStep		projectSetupTab;
	TabbedPaneTargetVariableStep	targetVariableTab;
	TabbedPaneConceptualStep		conceptualStepTab;
	TabbedPaneOperationalStep		operationalStepTab;
	TabbedPaneSearchNCompareStep	searchNCompareTab;
	TabbedPaneDataReCodingStep		dataRecodingTab;

	/*
	 *	Constructor
	 */
	/**
	 * 
	 */
	public TabbedPaneForm() {
		super();
	}
	
	/**
	 * @param al
	 * @param cl
	 * @param locale
	 * @param addenda
	 */
	public TabbedPaneForm(ActionListener al, ChangeListener cl, Locale locale, Object addenda) { 
		this();
		
		Project project = ((CStatsModel)addenda).getProject();
		
		currentLocale	= locale;
		resourceBundle	= ResourceBundle.getBundle(BUNDLE, currentLocale);
		
		projectSetupTab = new TabbedPaneProjectSetupStep(al, cl, locale, addenda);
		targetVariableTab = new TabbedPaneTargetVariableStep(al, cl, locale, addenda);
		conceptualStepTab = new TabbedPaneConceptualStep(al, cl, locale, addenda);
		operationalStepTab = new TabbedPaneOperationalStep(al, cl, locale, addenda);
		searchNCompareTab = new TabbedPaneSearchNCompareStep(al, cl, locale, addenda);
		dataRecodingTab = new TabbedPaneDataReCodingStep(al, this, cl, locale, addenda);
		
		insertTab(resourceBundle.getString(TPF_PRO),	null,	projectSetupTab,	"", PRO_SET_TAB_IDX);
		insertTab(resourceBundle.getString(TPF_TAR),	null,	targetVariableTab,	"", TAR_VAR_TAB_IDX);
		insertTab(resourceBundle.getString(TPF_CON),	null, 	conceptualStepTab,	"", CON_STE_TAB_IDX);
		insertTab(resourceBundle.getString(TPF_OPE),	null, 	operationalStepTab,	"", OPE_STE_TAB_IDX);        
		insertTab(resourceBundle.getString(TPF_SEA),	null,	searchNCompareTab,	"", SEA_COM_STE_TAB_IDX);
		insertTab(resourceBundle.getString(TPF_DAT),	null, 	dataRecodingTab,	"", DAT_REC_STE_TAB_IDX);		
				
		this.setEnabledAt(PRO_SET_TAB_IDX, 		((project != null) ? project.getProgress().isProjectSetupStepTabbedPanelAvailable() : false));
		this.setEnabledAt(TAR_VAR_TAB_IDX, 		((project != null) ? project.getProgress().isTargetVariableStepTabbedPanelAvailable() : false));
		this.setEnabledAt(CON_STE_TAB_IDX, 		((project != null) ? project.getProgress().isConceptualStepTabbedPanelAvailable() : false));
		this.setEnabledAt(OPE_STE_TAB_IDX, 		((project != null) ? project.getProgress().isOperationalStepTabbedPanelAvailable() : false));
		this.setEnabledAt(SEA_COM_STE_TAB_IDX,	((project != null) ? project.getProgress().isSearchNCompareStepTabbedPanelAvailable() : false));
		this.setEnabledAt(DAT_REC_STE_TAB_IDX,	((project != null) ? project.getProgress().isDataRecodingStepTabbedPanelAvailable() : false));
        
		operationalStepTab.setSelectedIndex(5);
		
		this.addChangeListener(this);  
	}
	
	/**
	 * @param model
	 */
	public void enableTabs(CStatsModel model) {
		Project project = model.getProject();
		
		this.setEnabledAt(PRO_SET_TAB_IDX, 		((project != null) ? project.getProgress().isProjectSetupStepTabbedPanelAvailable() : false));
		this.setEnabledAt(TAR_VAR_TAB_IDX, 		((project != null) ? project.getProgress().isTargetVariableStepTabbedPanelAvailable() : false));
		this.setEnabledAt(CON_STE_TAB_IDX, 		((project != null) ? project.getProgress().isConceptualStepTabbedPanelAvailable() : false));
		this.setEnabledAt(OPE_STE_TAB_IDX, 		((project != null) ? project.getProgress().isOperationalStepTabbedPanelAvailable() : false));
		this.setEnabledAt(SEA_COM_STE_TAB_IDX,	((project != null) ? project.getProgress().isSearchNCompareStepTabbedPanelAvailable() : false));
		this.setEnabledAt(DAT_REC_STE_TAB_IDX,	((project != null) ? project.getProgress().isDataRecodingStepTabbedPanelAvailable() : false));
	}
	
	/**
	 * 
	 */
	public void disableTabs() {
		this.setEnabledAt(PRO_SET_TAB_IDX, 		false);
		this.setEnabledAt(TAR_VAR_TAB_IDX, 		false);
		this.setEnabledAt(CON_STE_TAB_IDX, 		false);
		this.setEnabledAt(OPE_STE_TAB_IDX, 		false);
		this.setEnabledAt(SEA_COM_STE_TAB_IDX,	false);
		this.setEnabledAt(DAT_REC_STE_TAB_IDX,	false);
	}
	
	/**
	 * 
	 */
	public void disableAllTabs() {
		projectSetupTab.disableTabs();
		targetVariableTab.disableTabs();
		conceptualStepTab.disableTabs();
		operationalStepTab.disableTabs();
		searchNCompareTab.disableTabs();
		dataRecodingTab.disableTabs(); 
		
		disableTabs();
	}
	
	/**
	 * @param model
	 */
	public void enableAllTabs(CStatsModel model) {
		projectSetupTab.enableTabs(model);
		targetVariableTab.enableTabs(model);
		conceptualStepTab.enableTabs(model);
		operationalStepTab.enableTabs(model);
		searchNCompareTab.enableTabs(model);
		dataRecodingTab.enableTabs(model);
		
		enableTabs(model);
	}
		
	/*
	 *	Methods
	 */
	/**
	 * @param locale
	 */
	public void changeLanguage(Locale locale) {
		ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, locale);
		
		setTitleAt(PRO_SET_TAB_IDX, bundle.getString(TPF_PRO));
		projectSetupTab.changeLanguage(locale);
		setTitleAt(TAR_VAR_TAB_IDX, bundle.getString(TPF_TAR));
		targetVariableTab.changeLanguage(locale);
		setTitleAt(CON_STE_TAB_IDX, bundle.getString(TPF_CON));
		conceptualStepTab.changeLanguage(locale);
		setTitleAt(OPE_STE_TAB_IDX, bundle.getString(TPF_OPE));
		operationalStepTab.changeLanguage(locale);
		setTitleAt(SEA_COM_STE_TAB_IDX, bundle.getString(TPF_SEA));
		searchNCompareTab.changeLanguage(locale);
		setTitleAt(DAT_REC_STE_TAB_IDX, bundle.getString(TPF_DAT));
		dataRecodingTab.changeLanguage(locale);
	}
	
	/**
	 * @param f
	 */
	public void changeFont(Font f) {
		this.setFont(f);
		this.updateUI();
		
		projectSetupTab.changeFont(f);
		targetVariableTab.changeFont(f);
		conceptualStepTab.changeFont(f);
		operationalStepTab.changeFont(f);
		searchNCompareTab.changeFont(f);
		dataRecodingTab.changeFont(f);
	}
	
    /* (non-Javadoc)
     * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
     */
    public void stateChanged( ChangeEvent e ) {  
    }
    
    /**
     * @return
     */
    public TabSearchVariable getSearchTab() {
    	return searchNCompareTab.searchVariableTab;   	
    }

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		actionHandler(e);		
	}
	
	/**
	 * @param actionCommand
	 */
	private void actionHandler(Object actionCommand) {
		String	cmd	= null;		
		
		if (actionCommand != null) {
			if (actionCommand instanceof ActionEvent) {
				cmd = ((ActionEvent)actionCommand).getActionCommand();
			} else {
				cmd = actionCommand.toString();
			}
		}
		
		if (cmd == null) {
			/* DoNothing */						
		} else if (cmd.equals(ActionCommandText.BTN_DAT_STP_ADD_INS)) {
			disableAllTabs();
		} 
	}
        
}

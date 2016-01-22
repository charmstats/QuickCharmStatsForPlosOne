package org.gesis.charmstats.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Observable;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JViewport;
import javax.swing.UIManager;
import javax.swing.event.ChangeListener;
import javax.swing.undo.UndoManager;

import org.gesis.charmstats.ActionCommandID;
import org.gesis.charmstats.IdentifiedParameter;
import org.gesis.charmstats.model.Attributes;
import org.gesis.charmstats.model.CStatsModel;
import org.gesis.charmstats.model.WorkStepInstance;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class FormFrame extends InternalFrame {

//	/* RESOURCES */
//	public static final String CONTROL_BOX_ICON	= "org/gesis/charmstats/Resources/form.GIF";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String BUNDLE	= "org.gesis.charmstats.resources.DesktopBundle";
	public static final String TITLE	= "form_frame_title";
	
	/*
	 *	Fields
	 */
	JTabbedPane	tabbedPane;
	
	@SuppressWarnings("rawtypes")
	HashMap		hashmap;
	
	/*
	 *	Constructor
	 */
	/**
	 * @param dimension
	 * @param location
	 * @param visible
	 * @param resizable
	 * @param maximizable
	 * @param iconifiable
	 * @param closable
	 * @param locale
	 * @param al
	 * @param cl
	 * @param addenda
	 */
	public FormFrame(
			Dimension dimension,
			Point location,
			boolean visible, 
			boolean resizable,
			boolean maximizable,
			boolean iconifiable,
			boolean closable,
			Locale locale,
			ActionListener al,
			ChangeListener cl,
			Object addenda) {
		super(dimension, location, visible, resizable, maximizable, iconifiable, closable);
		        
		currentLocale	= locale;
		resourceBundle	= ResourceBundle.getBundle(BUNDLE, currentLocale);
		
		setTitle(resourceBundle.getString(TITLE));
		
		ImageIcon icon = createImageIcon(CStatsGUI.FORM_ICON,"");
		setFrameIcon(icon);
		
		setLayout(new BorderLayout());
		
		tabbedPane = buildTabbedPane(al, cl, locale, addenda);		
		tabbedPane.addChangeListener(cl);
		
		add(tabbedPane, BorderLayout.CENTER);
		
		hashmap = buildHashMap(); 
	}

	/*
	 *	Methods
	 */
	/**
	 * @param al
	 * @param cl
	 * @param locale
	 * @param addenda
	 * @return
	 */
	private JTabbedPane buildTabbedPane(ActionListener al, ChangeListener cl, Locale locale, Object addenda) {
		JTabbedPane pane = new TabbedPaneForm(al, cl, locale, addenda);
        
		return pane;
	}
	
	/**
	 * @return
	 */
	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}
	
	/**
	 * @return
	 */
	public TabCompareValues getCompareValuesTab() {
		JTabbedPane tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.SEA_COM_STE_TAB_IDX);
		tabPane.setSelectedIndex(TabbedPaneSearchNCompareStep.VAL_TAB_IDX);
		TabCompareValues tabComVal = (TabCompareValues)tabPane.getSelectedComponent();
		
		tabPane.setSelectedIndex(TabbedPaneSearchNCompareStep.SEA_TAB_IDX);
		
		return tabComVal;
	}
	
	/**
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private HashMap buildHashMap() {
		HashMap<String, Tab> hashmap = new HashMap<String, Tab>();
		
		JTabbedPane tabPane = null;
		Tab 		tab		= null;
		
		for (int i=0; i<tabbedPane.getComponentCount(); i++) {
			tabPane = (JTabbedPane) tabbedPane.getComponentAt(i);
			
			for (int j=0; j<tabPane.getComponentCount(); j++) {
				if (tabPane.getComponentAt(j) instanceof JScrollPane) 
					tab =  (Tab)((JViewport)((JScrollPane)tabPane.getComponentAt(j)).getComponent(0)).getComponent(0);
				else
					tab =  (Tab)tabPane.getComponentAt(j);
				
				hashmap.put(tab.getName(), tab);
			}
		}
		
		return hashmap;
	}
	
	/**
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Tab> getHashMap() {
		return hashmap;
	}
	
	/**
	 * @param locale
	 */
	protected void changeLanguage(Locale locale) {
		ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, locale);
		
		setTitle(bundle.getString(TITLE));
		((TabbedPaneForm)tabbedPane).changeLanguage(locale);
	}
	
	/**
	 * @param f
	 */
	protected void changeFont(Font f) {
		UIManager.put("InternalFrame.titleFont", f);
		javax.swing.SwingUtilities.updateComponentTreeUI(this);
		
		((TabbedPaneForm)tabbedPane).changeFont(f);
	}
	
	/*
	 *	FrameForm.update handles the visual effect of the back/next buttons!
	 */
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.InternalFrame#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		IdentifiedParameter parameter = (IdentifiedParameter)arg;
		
		if (parameter != null) {
			Object[] addenda = parameter.getParameters();

			CStatsModel model = null;
			if (addenda[0] instanceof CStatsModel)
				model = (CStatsModel)addenda[0];
			
			Object argument = addenda[0];
			
			JTabbedPane tabPane = null;
			switch (parameter.getID()) {
				case ActionCommandID.BTN_TB_SHOW_FORM:
					try {
						this.setSelected(true);
						this.setMaximum(true);
						this.updateUI();
					} catch (PropertyVetoException e) {
						System.err.println("BTN_TB_SHOW_FORM:FrameForm");
					}
					break;
				case ActionCommandID.BTN_TB_SHOW_GRAPH:
					try {
						this.setMaximum(false);
						this.updateUI();
					} catch (PropertyVetoException e) {
						System.err.println("BTN_TB_SHOW_GRAPH:FrameForm");
					}
					break;
				case ActionCommandID.BTN_TB_SHOW_REPORT:
					try {
						this.setMaximum(false);
						this.updateUI();
					} catch (PropertyVetoException e) {
						System.err.println("BTN_TB_SHOW_REPORT:FrameForm");
					}
					break;
				case ActionCommandID.CMD_PRJ_NEW_PROJECT:
					traverseTabPanes(model, tabbedPane);
					
					tabbedPane.setEnabledAt(TabbedPaneForm.PRO_SET_TAB_IDX, true);
					tabbedPane.setSelectedIndex(TabbedPaneForm.PRO_SET_TAB_IDX);
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.PRO_SET_TAB_IDX);
					tabPane.setEnabledAt(TabbedPaneProjectSetupStep.PRO_TAB_IDX, true);
					tabPane.setSelectedIndex(TabbedPaneProjectSetupStep.PRO_TAB_IDX);
					
					if (tabPane.getSelectedComponent() instanceof Tab) {
						((Tab)tabPane.getSelectedComponent()).enableElements(true);
					}
					break;
				case ActionCommandID.BTN_DAT_STP_MAP_CHA_CONFIRM: 
					traverseTabPanes(model, tabbedPane);
					
					((TabbedPaneForm)tabbedPane).enableTabs(model);
					
					tabbedPane.setSelectedIndex(TabbedPaneForm.DAT_REC_STE_TAB_IDX);
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.DAT_REC_STE_TAB_IDX);
					tabPane.setSelectedIndex(TabbedPaneDataReCodingStep.MAP_VAR_CHA_TAB_IDX);
					break;

				case ActionCommandID.CMD_PRJ_FINISH:
					traverseTabPanes(model, tabbedPane);
					
					((TabbedPaneForm)tabbedPane).enableTabs(model);
					
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.PRO_SET_TAB_IDX);
					tabPane.setSelectedIndex(TabbedPaneProjectSetupStep.PRO_TAB_IDX);
					
					if (tabPane.getSelectedComponent() instanceof Tab) {
						if ((model.getProject().getFinishedSince() != null) ||	
								(!model.getProject().isEditedByUser())) {		
							((Tab)tabPane.getSelectedComponent()).enableElements(false);
						} else {
							((Tab)tabPane.getSelectedComponent()).enableElements(true);
						}
					}
					break;
				case ActionCommandID.BTN_TGT_STP_TGT_TAB_AUTO:
					traverseTabPanes(model, tabbedPane);
					
					((TabbedPaneForm)tabbedPane).enableTabs(model);
					
					tabbedPane.setSelectedIndex(TabbedPaneForm.DAT_REC_STE_TAB_IDX);
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.DAT_REC_STE_TAB_IDX);
					tabPane.setSelectedIndex(TabbedPaneDataReCodingStep.MAP_VAR_CHA_TAB_IDX);
					
					break;
				case ActionCommandID.CMD_PRJ_OPEN_PROJECT:
					traverseTabPanes(model, tabbedPane);
					
					((TabbedPaneForm)tabbedPane).enableTabs(model);
					
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.PRO_SET_TAB_IDX);
					tabPane.setSelectedIndex(TabbedPaneProjectSetupStep.PRO_TAB_IDX);
					
					if (tabPane.getSelectedComponent() instanceof Tab) {
						if ((model.getProject().getFinishedSince() != null) ||	
								(!model.getProject().isEditedByUser())) {		
							((Tab)tabPane.getSelectedComponent()).enableElements(false);
						} else {
							((Tab)tabPane.getSelectedComponent()).enableElements(true);
						}
						
					}
					break;
				case ActionCommandID.CMD_PRJ_REMOVE:
				case ActionCommandID.CMD_PRJ_CLOSE:
					traverseTabPanes(model, tabbedPane);
					
					((TabbedPaneForm)tabbedPane).enableTabs(model);
					
					tabbedPane.setEnabledAt(TabbedPaneForm.PRO_SET_TAB_IDX, false);
					tabbedPane.setSelectedIndex(TabbedPaneForm.PRO_SET_TAB_IDX);
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.PRO_SET_TAB_IDX);
					tabPane.setEnabledAt(TabbedPaneProjectSetupStep.PRO_TAB_IDX, false);
					tabPane.setSelectedIndex(TabbedPaneProjectSetupStep.PRO_TAB_IDX);
					
					if (tabPane.getSelectedComponent() instanceof Tab) {
						((Tab)tabPane.getSelectedComponent()).enableElements(false);
					}
					break;
				case ActionCommandID.BTN_PRJ_STP_PRJ_TAB_BACK:
					/* DO NOTHING */
					break;
				case ActionCommandID.BTN_PRJ_STP_PRJ_TAB_RESET:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.PRO_SET_TAB_IDX);
					tabPane.setSelectedIndex(TabbedPaneProjectSetupStep.PRO_TAB_IDX);
					TabProject tabPro = (TabProject)tabPane.getSelectedComponent();
					tabPro.setDefaults(argument);
					
					break;
				case ActionCommandID.BTN_PRJ_STP_PRJ_TAB_NEXT:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.PRO_SET_TAB_IDX);
					tabPane.setEnabledAt(TabbedPaneProjectSetupStep.CON_TAB_IDX, true);
					tabPane.setSelectedIndex(TabbedPaneProjectSetupStep.CON_TAB_IDX);
										
					break;
				case ActionCommandID.BTN_PRJ_STP_CON_TAB_BACK:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.PRO_SET_TAB_IDX);
					tabPane.setSelectedIndex(TabbedPaneProjectSetupStep.PRO_TAB_IDX);
					
					break;
				case ActionCommandID.BTN_PRJ_STP_CON_TAB_RESET:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.PRO_SET_TAB_IDX);
					tabPane.setSelectedIndex(TabbedPaneProjectSetupStep.CON_TAB_IDX);
					TabConcept tabCon = (TabConcept)tabPane.getSelectedComponent();
					tabCon.setDefaults(argument);
					
					break;
				case ActionCommandID.BTN_PRJ_STP_CON_TAB_NEXT:
					// in QuickCharmStats Lit is not accessed
//					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.PRO_SET_TAB_IDX);
//					tabPane.setEnabledAt(TabbedPaneProjectSetupStep.REF_TAB_IDX, true);
//					tabPane.setSelectedIndex(TabbedPaneProjectSetupStep.REF_TAB_IDX);
					// instead this is used:
					tabbedPane.setEnabledAt(TabbedPaneForm.TAR_VAR_TAB_IDX, true);
					tabbedPane.setSelectedIndex(TabbedPaneForm.TAR_VAR_TAB_IDX);
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.TAR_VAR_TAB_IDX);
					tabPane.setEnabledAt(TabbedPaneTargetVariableStep.MEA_TAB_IDX, true);
					tabPane.setSelectedIndex(TabbedPaneTargetVariableStep.MEA_TAB_IDX);
					
					break;
				case ActionCommandID.BTN_PRJ_STP_LIT_TAB_BACK:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.PRO_SET_TAB_IDX);
					tabPane.setSelectedIndex(TabbedPaneProjectSetupStep.CON_TAB_IDX);
					
					break;
				case ActionCommandID.BTN_PRJ_STP_LIT_TAB_RESET:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.PRO_SET_TAB_IDX);
					tabPane.setSelectedIndex(TabbedPaneProjectSetupStep.REF_TAB_IDX);
					TabLiterature tabLit = (TabLiterature)tabPane.getSelectedComponent();
					tabLit.setDefaults(argument);
					
					break;
				case ActionCommandID.BTN_PRJ_STP_LIT_TAB_NEXT:					
					tabbedPane.setEnabledAt(TabbedPaneForm.TAR_VAR_TAB_IDX, true);
					tabbedPane.setSelectedIndex(TabbedPaneForm.TAR_VAR_TAB_IDX);
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.TAR_VAR_TAB_IDX);
					tabPane.setEnabledAt(TabbedPaneTargetVariableStep.MEA_TAB_IDX, true);
					tabPane.setSelectedIndex(TabbedPaneTargetVariableStep.MEA_TAB_IDX);
					
					break;
				case ActionCommandID.BTN_TGT_STP_TGT_TAB_BACK:
					tabbedPane.setSelectedIndex(TabbedPaneForm.PRO_SET_TAB_IDX);
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.PRO_SET_TAB_IDX);
//					tabPane.setSelectedIndex(TabbedPaneProjectSetupStep.REF_TAB_IDX);					
					tabPane.setSelectedIndex(TabbedPaneProjectSetupStep.CON_TAB_IDX);
					
					break;
				case ActionCommandID.BTN_TGT_STP_TGT_TAB_RESET:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.TAR_VAR_TAB_IDX);
					tabPane.setSelectedIndex(TabbedPaneTargetVariableStep.MEA_TAB_IDX);
					TabMeasurement tabMea = (TabMeasurement)tabPane.getSelectedComponent();
					tabMea.setDefaults(argument);
					
					break;
				case ActionCommandID.BTN_TGT_STP_TGT_TAB_IMP:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.TAR_VAR_TAB_IDX);
					tabPane.setSelectedIndex(TabbedPaneTargetVariableStep.MEA_TAB_IDX);
					TabMeasurement tab = (TabMeasurement)tabPane.getSelectedComponent();
					tab.setDefaults(model);
					
					break;
				case ActionCommandID.BTN_TGT_STP_TGT_TAB_NEXT:
					if (!model.getIsBrowsing()) {
						tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.TAR_VAR_TAB_IDX);
						tabPane.setEnabledAt(TabbedPaneTargetVariableStep.MEA_TAB_IDX, false);
					
						tabbedPane.setEnabledAt(TabbedPaneForm.TAR_VAR_TAB_IDX, false);
					}
					
					tabbedPane.setEnabledAt(TabbedPaneForm.CON_STE_TAB_IDX, true);
					tabbedPane.setSelectedIndex(TabbedPaneForm.CON_STE_TAB_IDX);
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.CON_STE_TAB_IDX);
					tabPane.setEnabledAt(TabbedPaneConceptualStep.DIM_TAB_IDX, true);
					tabPane.setSelectedIndex(TabbedPaneConceptualStep.DIM_TAB_IDX);
					
					break;					
				case ActionCommandID.BTN_CON_STP_DIM_TAB_BACK:
					tabbedPane.setSelectedIndex(TabbedPaneForm.TAR_VAR_TAB_IDX);
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.TAR_VAR_TAB_IDX);
					tabPane.setSelectedIndex(TabbedPaneTargetVariableStep.MEA_TAB_IDX);
					
					break;
				case ActionCommandID.BTN_CON_STP_DIM_TAB_RESET:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.CON_STE_TAB_IDX);
					tabPane.setSelectedIndex(TabbedPaneConceptualStep.DIM_TAB_IDX);
					TabDimension tabDim = (TabDimension)tabPane.getSelectedComponent();
					tabDim.setDefaults(argument);

					break;
				case ActionCommandID.BTN_CON_STP_DIM_TAB_NEXT:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.CON_STE_TAB_IDX);
					tabPane.setEnabledAt(TabbedPaneConceptualStep.DIM_TAB_IDX, false);
					
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.CON_STE_TAB_IDX);
					tabPane.setEnabledAt(TabbedPaneConceptualStep.SPE_TAB_IDX, true);
					tabPane.setSelectedIndex(TabbedPaneConceptualStep.SPE_TAB_IDX);
					
					break;
				case ActionCommandID.BTN_CON_STP_SPE_TAB_BACK:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.CON_STE_TAB_IDX);
					tabPane.setSelectedIndex(TabbedPaneConceptualStep.DIM_TAB_IDX);
					
					break;
				case ActionCommandID.BTN_CON_STP_SPE_TAB_RESET:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.CON_STE_TAB_IDX);
					tabPane.setSelectedIndex(TabbedPaneConceptualStep.SPE_TAB_IDX);
					TabSpecifications tabSpe = (TabSpecifications)tabPane.getSelectedComponent();
					tabSpe.setDefaults(argument);

					break;
				case ActionCommandID.BTN_CON_STP_SPE_TAB_NEXT:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.CON_STE_TAB_IDX);
					tabPane.setEnabledAt(TabbedPaneConceptualStep.SPE_TAB_IDX, false);
					
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.CON_STE_TAB_IDX);
					tabPane.setEnabledAt(TabbedPaneConceptualStep.MAP_DIM_INS_TAB_IDX, true);
					tabPane.setSelectedIndex(TabbedPaneConceptualStep.MAP_DIM_INS_TAB_IDX);
					
					break;
				case ActionCommandID.BTN_CON_STP_MAP_INS_BACK:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.CON_STE_TAB_IDX);
					tabPane.setSelectedIndex(TabbedPaneConceptualStep.SPE_TAB_IDX);
					
					break;
				case ActionCommandID.BTN_CON_STP_MAP_INS_RESET:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.CON_STE_TAB_IDX);
					tabPane.setSelectedIndex(TabbedPaneConceptualStep.MAP_DIM_INS_TAB_IDX);
					TabMapDimensionInstance tabMapDimIns = (TabMapDimensionInstance)tabPane.getSelectedComponent();
					tabMapDimIns.setDefaults(argument);
					
					break;
				case ActionCommandID.BTN_CON_STP_MAP_INS_NEXT:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.CON_STE_TAB_IDX);
					tabPane.setEnabledAt(TabbedPaneConceptualStep.MAP_DIM_INS_TAB_IDX, false);
					
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.CON_STE_TAB_IDX);
					tabPane.setEnabledAt(TabbedPaneConceptualStep.MAP_DIM_ATR_TAB_IDX, true);
					tabPane.setSelectedIndex(TabbedPaneConceptualStep.MAP_DIM_ATR_TAB_IDX);
					
					break;
				case ActionCommandID.BTN_CON_STP_MAP_ATR_BACK:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.CON_STE_TAB_IDX);
					tabPane.setSelectedIndex(TabbedPaneConceptualStep.MAP_DIM_INS_TAB_IDX);
					
					break;
				case ActionCommandID.BTN_CON_STP_MAP_ATR_RESET:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.CON_STE_TAB_IDX);
					tabPane.setSelectedIndex(TabbedPaneConceptualStep.MAP_DIM_ATR_TAB_IDX);
					TabMapDimensionAttribute tabMapDimAtr = (TabMapDimensionAttribute)tabPane.getSelectedComponent();
					tabMapDimAtr.setDefaults(argument);
					
					break;
				case ActionCommandID.BTN_CON_STP_MAP_ATR_NEXT:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.CON_STE_TAB_IDX);
					tabPane.setEnabledAt(TabbedPaneConceptualStep.MAP_DIM_ATR_TAB_IDX, false);
					
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.CON_STE_TAB_IDX);
					tabPane.setEnabledAt(TabbedPaneConceptualStep.MAP_DIM_CHA_TAB_IDX, true);
					tabPane.setSelectedIndex(TabbedPaneConceptualStep.MAP_DIM_CHA_TAB_IDX);
					
					break;
				case ActionCommandID.BTN_CON_STP_MAP_CHA_BACK:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.CON_STE_TAB_IDX);
					tabPane.setSelectedIndex(TabbedPaneConceptualStep.MAP_DIM_ATR_TAB_IDX);
					
					break;
				case ActionCommandID.BTN_CON_STP_MAP_CHA_RESET:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.CON_STE_TAB_IDX);
					tabPane.setSelectedIndex(TabbedPaneConceptualStep.MAP_DIM_CHA_TAB_IDX);
					TabMapDimensionChar tabMapDimCha = (TabMapDimensionChar)tabPane.getSelectedComponent();
					tabMapDimCha.setDefaults(argument);
					
					break;
				case ActionCommandID.BTN_CON_STP_MAP_CHA_NEXT:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.CON_STE_TAB_IDX);
					tabPane.setEnabledAt(TabbedPaneConceptualStep.MAP_DIM_CHA_TAB_IDX, false);
					
					tabbedPane.setEnabledAt(TabbedPaneForm.CON_STE_TAB_IDX, false);
					
					tabbedPane.setEnabledAt(TabbedPaneForm.OPE_STE_TAB_IDX, true);
					tabbedPane.setSelectedIndex(TabbedPaneForm.OPE_STE_TAB_IDX);
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.OPE_STE_TAB_IDX);
					tabPane.setEnabledAt(TabbedPaneOperationalStep.OS__INS_TAB_IDX, true);
					tabPane.setSelectedIndex(TabbedPaneOperationalStep.OS__INS_TAB_IDX);
					
					break;	
				case ActionCommandID.BTN_OPE_STP_INS_TAB_BACK:
					tabbedPane.setSelectedIndex(TabbedPaneForm.CON_STE_TAB_IDX);
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.CON_STE_TAB_IDX);
					tabPane.setSelectedIndex(TabbedPaneConceptualStep.MAP_DIM_CHA_TAB_IDX);
					
					break;
				case ActionCommandID.BTN_OPE_STP_INS_TAB_RESET:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.OPE_STE_TAB_IDX);
					tabPane.setSelectedIndex(TabbedPaneOperationalStep.OS__INS_TAB_IDX);
					TabOSInstance tabOSIns = (TabOSInstance)tabPane.getSelectedComponent();
					tabOSIns.setDefaults(argument);
					
					break;
				case ActionCommandID.BTN_OPE_STP_INS_TAB_NEXT:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.OPE_STE_TAB_IDX);
					tabPane.setEnabledAt(TabbedPaneOperationalStep.OS__INS_TAB_IDX, false);
					
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.OPE_STE_TAB_IDX);
					tabPane.setEnabledAt(TabbedPaneOperationalStep.IND_TAB_IDX, true);
					tabPane.setSelectedIndex(TabbedPaneOperationalStep.IND_TAB_IDX);
					
					break;
				case ActionCommandID.BTN_OPE_STP_IND_TAB_BACK:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.OPE_STE_TAB_IDX);
					tabPane.setSelectedIndex(TabbedPaneOperationalStep.OS__INS_TAB_IDX);
					
					break;
				case ActionCommandID.BTN_OPE_STP_IND_TAB_RESET:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.OPE_STE_TAB_IDX);
					tabPane.setSelectedIndex(TabbedPaneOperationalStep.MAP_IND_INS_TAB_IDX);
					TabIndicator tabInd = (TabIndicator)tabPane.getSelectedComponent();
					tabInd.setDefaults(argument);
					
					break;
				case ActionCommandID.BTN_OPE_STP_IND_TAB_NEXT:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.OPE_STE_TAB_IDX);
					tabPane.setEnabledAt(TabbedPaneOperationalStep.IND_TAB_IDX, false);
					
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.OPE_STE_TAB_IDX);
					tabPane.setEnabledAt(TabbedPaneOperationalStep.PRE_TAB_IDX, true);
					tabPane.setSelectedIndex(TabbedPaneOperationalStep.PRE_TAB_IDX);
					
					break;
				case ActionCommandID.BTN_OPE_STP_PRE_TAB_BACK:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.OPE_STE_TAB_IDX);
					tabPane.setSelectedIndex(TabbedPaneOperationalStep.IND_TAB_IDX);
					
					break;
				case ActionCommandID.BTN_OPE_STP_PRE_TAB_RESET:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.OPE_STE_TAB_IDX);
					tabPane.setSelectedIndex(TabbedPaneOperationalStep.PRE_TAB_IDX);
					TabPrescriptions tabPre = (TabPrescriptions)tabPane.getSelectedComponent();
					tabPre.setDefaults(argument);
					
					break;
				case ActionCommandID.BTN_OPE_STP_PRE_TAB_NEXT:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.OPE_STE_TAB_IDX);
					tabPane.setEnabledAt(TabbedPaneOperationalStep.PRE_TAB_IDX, false);
					
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.OPE_STE_TAB_IDX);
					tabPane.setEnabledAt(TabbedPaneOperationalStep.MAP_IND_INS_TAB_IDX, true);
					tabPane.setSelectedIndex(TabbedPaneOperationalStep.MAP_IND_INS_TAB_IDX);
					
					break;
				case ActionCommandID.BTN_OPE_STP_MAP_INS_BACK:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.OPE_STE_TAB_IDX);
					tabPane.setSelectedIndex(TabbedPaneOperationalStep.PRE_TAB_IDX);
					
					break;
				case ActionCommandID.BTN_OPE_STP_MAP_INS_RESET:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.OPE_STE_TAB_IDX);
					tabPane.setSelectedIndex(TabbedPaneOperationalStep.MAP_IND_INS_TAB_IDX);
					TabMapIndicatorInstance tabMapIndIns = (TabMapIndicatorInstance)tabPane.getSelectedComponent();
					tabMapIndIns.setDefaults(argument);
					
					break;
				case ActionCommandID.BTN_OPE_STP_MAP_INS_NEXT:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.OPE_STE_TAB_IDX);
					tabPane.setEnabledAt(TabbedPaneOperationalStep.MAP_IND_INS_TAB_IDX, false);
					
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.OPE_STE_TAB_IDX);
					tabPane.setEnabledAt(TabbedPaneOperationalStep.MAP_IND_ATR_TAB_IDX, true);
					tabPane.setSelectedIndex(TabbedPaneOperationalStep.MAP_IND_ATR_TAB_IDX);
					
					break;
				case ActionCommandID.BTN_OPE_STP_MAP_ATR_BACK:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.OPE_STE_TAB_IDX);
					tabPane.setSelectedIndex(TabbedPaneOperationalStep.MAP_IND_INS_TAB_IDX);
					
					break;
				case ActionCommandID.BTN_OPE_STP_MAP_ATR_RESET:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.OPE_STE_TAB_IDX);
					tabPane.setSelectedIndex(TabbedPaneOperationalStep.MAP_IND_ATR_TAB_IDX);
					TabMapIndicatorAttribute tabMapIndAttr = (TabMapIndicatorAttribute)tabPane.getSelectedComponent();
					tabMapIndAttr.setDefaults(argument);
					
					break;
				case ActionCommandID.BTN_OPE_STP_MAP_ATR_NEXT:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.OPE_STE_TAB_IDX);
					tabPane.setEnabledAt(TabbedPaneOperationalStep.MAP_IND_ATR_TAB_IDX, false);
					
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.OPE_STE_TAB_IDX);
					tabPane.setEnabledAt(TabbedPaneOperationalStep.MAP_IND_CHA_TAB_IDX, true);
					tabPane.setSelectedIndex(TabbedPaneOperationalStep.MAP_IND_CHA_TAB_IDX);
					
					break;
				case ActionCommandID.BTN_OPE_STP_MAP_CHA_BACK:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.OPE_STE_TAB_IDX);
					tabPane.setSelectedIndex(TabbedPaneOperationalStep.MAP_IND_ATR_TAB_IDX);
					
					break;
				case ActionCommandID.BTN_OPE_STP_MAP_CHA_RESET:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.OPE_STE_TAB_IDX);
					tabPane.setSelectedIndex(TabbedPaneOperationalStep.MAP_IND_CHA_TAB_IDX);
					TabMapIndicatorChar tabMapIndChar = (TabMapIndicatorChar)tabPane.getSelectedComponent();
					tabMapIndChar.setDefaults(argument);
					
					break;
				case ActionCommandID.BTN_OPE_STP_MAP_CHA_NEXT:
					// In QuickCharmStats not used:
//					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.OPE_STE_TAB_IDX);
//					tabPane.setEnabledAt(TabbedPaneOperationalStep.MAP_IND_CHA_TAB_IDX, false);
//					
//					tabbedPane.setEnabledAt(TabbedPaneForm.OPE_STE_TAB_IDX, false);
					// In QuickCharmStats not used END
					
					Boolean isAutocomplete = false;					
					if (model instanceof CStatsModel)
						isAutocomplete = model.getIsAutocomplete();
						
					if (isAutocomplete) {						
						tabbedPane.setSelectedIndex(TabbedPaneForm.TAR_VAR_TAB_IDX);
						tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.TAR_VAR_TAB_IDX);
						tabPane.setSelectedIndex(TabbedPaneTargetVariableStep.MEA_TAB_IDX);
						
						model.setIsAutocomplete(false); // not really MVC-like 
					} else {
						// in QuickCharmStats Search'n'Compare is not used
//						tabbedPane.setEnabledAt(TabbedPaneForm.SEA_COM_STE_TAB_IDX, true);
//						tabbedPane.setSelectedIndex(TabbedPaneForm.SEA_COM_STE_TAB_IDX);
//						tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.SEA_COM_STE_TAB_IDX);
//						tabPane.setEnabledAt(TabbedPaneSearchNCompareStep.SEA_TAB_IDX, true);
//						tabPane.setSelectedIndex(TabbedPaneSearchNCompareStep.SEA_TAB_IDX);
						// instead use this:				
						tabbedPane.setEnabledAt(TabbedPaneForm.DAT_REC_STE_TAB_IDX, true);
						tabbedPane.setSelectedIndex(TabbedPaneForm.DAT_REC_STE_TAB_IDX);
						tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.DAT_REC_STE_TAB_IDX);
						tabPane.setEnabledAt(TabbedPaneDataReCodingStep.DR__INS_TAB_IDX, true);
						tabPane.setSelectedIndex(TabbedPaneDataReCodingStep.DR__INS_TAB_IDX);
					}
					
					break;
					
				case ActionCommandID.BTN_SEA_STP_SEA_TAB_BACK:
					tabbedPane.setSelectedIndex(TabbedPaneForm.OPE_STE_TAB_IDX);
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.OPE_STE_TAB_IDX);
					tabPane.setSelectedIndex(TabbedPaneOperationalStep.MAP_IND_CHA_TAB_IDX);
					
					break;
				case ActionCommandID.BTN_SEA_STP_SEA_TAB_RESET:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.SEA_COM_STE_TAB_IDX);
					tabPane.setSelectedIndex(TabbedPaneSearchNCompareStep.SEA_TAB_IDX);
					TabSearchVariable tabSea = (TabSearchVariable)tabPane.getSelectedComponent();
					tabSea.setDefaults(argument);
					
					break;
				case ActionCommandID.BTN_SEA_STP_SEA_TAB_NEXT:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.SEA_COM_STE_TAB_IDX);
					tabPane.setEnabledAt(TabbedPaneSearchNCompareStep.COM_TAB_IDX, true);
					tabPane.setSelectedIndex(TabbedPaneSearchNCompareStep.COM_TAB_IDX);
					
					break;			
				case ActionCommandID.BTN_SEA_STP_COM_TAB_BACK:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.SEA_COM_STE_TAB_IDX);
					tabPane.setSelectedIndex(TabbedPaneSearchNCompareStep.SEA_TAB_IDX);
					
					break;
				case ActionCommandID.BTN_SEA_STP_COM_TAB_RESET:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.SEA_COM_STE_TAB_IDX);
					tabPane.setSelectedIndex(TabbedPaneSearchNCompareStep.COM_TAB_IDX);
					TabCompareMetadata tabCom = (TabCompareMetadata)tabPane.getSelectedComponent();
					tabCom.setDefaults(argument);
					
					break;
				case ActionCommandID.BTN_SEA_STP_COM_TAB_IMP:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.SEA_COM_STE_TAB_IDX);
					tabPane.setSelectedIndex(TabbedPaneSearchNCompareStep.COM_TAB_IDX);
					tabCom = (TabCompareMetadata)tabPane.getSelectedComponent();
					tabCom.setDefaults(model);
					
					if (addenda[1] instanceof WorkStepInstance) {
						WorkStepInstance instance = (WorkStepInstance)addenda[1];
						tabCom.operaModelCB.setSelectedItem(instance);
					}
					if (addenda[2] instanceof Attributes) {
						Attributes attribute = (Attributes)addenda[2];
						tabCom.indicatorCB.setSelectedItem(attribute);
					}
					
					break;
				case ActionCommandID.BTN_SEA_STP_COM_TAB_NEXT:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.SEA_COM_STE_TAB_IDX);
					tabPane.setEnabledAt(TabbedPaneSearchNCompareStep.VAL_TAB_IDX, true);
					tabPane.setSelectedIndex(TabbedPaneSearchNCompareStep.VAL_TAB_IDX);
					
					break;			
				case ActionCommandID.BTN_SEA_STP_VAL_TAB_BACK:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.SEA_COM_STE_TAB_IDX);
					tabPane.setSelectedIndex(TabbedPaneSearchNCompareStep.COM_TAB_IDX);
					
					break;
				case ActionCommandID.BTN_SEA_STP_VAL_TAB_RESET:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.SEA_COM_STE_TAB_IDX);
					tabPane.setSelectedIndex(TabbedPaneSearchNCompareStep.VAL_TAB_IDX);
					TabCompareValues tabComVal = (TabCompareValues)tabPane.getSelectedComponent();
					tabComVal.setDefaults(argument);
					
					break;
				case ActionCommandID.BTN_SEA_STP_VAL_TAB_NEXT:
					tabbedPane.setEnabledAt(TabbedPaneForm.DAT_REC_STE_TAB_IDX, true);
					tabbedPane.setSelectedIndex(TabbedPaneForm.DAT_REC_STE_TAB_IDX);
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.DAT_REC_STE_TAB_IDX);
					tabPane.setEnabledAt(TabbedPaneDataReCodingStep.DR__INS_TAB_IDX, true);
					tabPane.setSelectedIndex(TabbedPaneDataReCodingStep.DR__INS_TAB_IDX);
					
					break;					
				case ActionCommandID.BTN_DAT_STP_INS_TAB_BACK:
					// in QuickCharmStats Search'n'Compare is not used 
//					tabbedPane.setSelectedIndex(TabbedPaneForm.SEA_COM_STE_TAB_IDX);
//					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.SEA_COM_STE_TAB_IDX);
//					tabPane.setSelectedIndex(TabbedPaneSearchNCompareStep.VAL_TAB_IDX);
					// instead use this
					tabbedPane.setSelectedIndex(TabbedPaneForm.OPE_STE_TAB_IDX);
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.OPE_STE_TAB_IDX);
					tabPane.setSelectedIndex(TabbedPaneOperationalStep.MAP_IND_CHA_TAB_IDX);
					
					break;
				case ActionCommandID.BTN_DAT_STP_INS_TAB_RESET:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.DAT_REC_STE_TAB_IDX);
					tabPane.setSelectedIndex(TabbedPaneDataReCodingStep.DR__INS_TAB_IDX);
					TabDRInstance tabDRIns = (TabDRInstance)tabPane.getSelectedComponent();
					tabDRIns.setDefaults(argument);
					
					break;
				case ActionCommandID.BTN_DAT_STP_INS_TAB_NEXT:
					if (!model.getIsBrowsing()) {
						tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.DAT_REC_STE_TAB_IDX);
						tabPane.setEnabledAt(TabbedPaneDataReCodingStep.DR__INS_TAB_IDX, false);					
					}
					
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.DAT_REC_STE_TAB_IDX);
					tabPane.setEnabledAt(TabbedPaneDataReCodingStep.VAR_TAB_IDX, true);
					tabPane.setSelectedIndex(TabbedPaneDataReCodingStep.VAR_TAB_IDX);
					
					break;
				case ActionCommandID.BTN_DAT_STP_VAR_TAB_BACK:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.DAT_REC_STE_TAB_IDX);
					tabPane.setSelectedIndex(TabbedPaneDataReCodingStep.DR__INS_TAB_IDX);
					
					break;
				case ActionCommandID.BTN_DAT_STP_VAR_TAB_RESET:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.DAT_REC_STE_TAB_IDX);
					tabPane.setSelectedIndex(TabbedPaneDataReCodingStep.VAR_TAB_IDX);
					TabVariable tabVar = (TabVariable)tabPane.getSelectedComponent();
					tabVar.setDefaults(argument);
					
					break;
				case ActionCommandID.BTN_DAT_STP_VAR_TAB_IMP:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.DAT_REC_STE_TAB_IDX);
					tabPane.setSelectedIndex(TabbedPaneDataReCodingStep.VAR_TAB_IDX);
					TabVariable tabVariable = (TabVariable)tabPane.getSelectedComponent();
					tabVariable.setDefaults(model);
					
					if (addenda[1] instanceof WorkStepInstance) {
						WorkStepInstance instance = (WorkStepInstance)addenda[1];
						tabVariable.drInstanceComboBox.setSelectedItem(instance);
					}
					
					break;
				case ActionCommandID.BTN_DAT_STP_VAR_TAB_NEXT:
					if (!model.getIsBrowsing()) {
						tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.DAT_REC_STE_TAB_IDX);
						tabPane.setEnabledAt(TabbedPaneDataReCodingStep.VAR_TAB_IDX, false);
					}
					
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.DAT_REC_STE_TAB_IDX);
					tabPane.setEnabledAt(TabbedPaneDataReCodingStep.VAL_TAB_IDX, true);
					tabPane.setSelectedIndex(TabbedPaneDataReCodingStep.VAL_TAB_IDX);
					
					break;
				case ActionCommandID.BTN_DAT_STP_VAL_TAB_BACK:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.DAT_REC_STE_TAB_IDX);
					tabPane.setSelectedIndex(TabbedPaneDataReCodingStep.VAR_TAB_IDX);
					
					break;
				case ActionCommandID.BTN_DAT_STP_VAL_TAB_RESET:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.DAT_REC_STE_TAB_IDX);
					tabPane.setSelectedIndex(TabbedPaneDataReCodingStep.VAL_TAB_IDX);
					TabValues tabVal = (TabValues)tabPane.getSelectedComponent();
					tabVal.setDefaults(argument);
					
					break;
				case ActionCommandID.BTN_DAT_STP_VAL_TAB_NEXT:
					if (!model.getIsBrowsing()) {
						tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.DAT_REC_STE_TAB_IDX);
						tabPane.setEnabledAt(TabbedPaneDataReCodingStep.VAL_TAB_IDX, false);
					}
					
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.DAT_REC_STE_TAB_IDX);
					tabPane.setEnabledAt(TabbedPaneDataReCodingStep.QUE_TAB_IDX, true);
					tabPane.setSelectedIndex(TabbedPaneDataReCodingStep.QUE_TAB_IDX);
									
					break;
									
				case ActionCommandID.BTN_DAT_STP_QUE_TAB_BACK:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.DAT_REC_STE_TAB_IDX);
					tabPane.setSelectedIndex(TabbedPaneDataReCodingStep.VAL_TAB_IDX);
					
					break;
				case ActionCommandID.BTN_DAT_STP_QUE_TAB_RESET:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.DAT_REC_STE_TAB_IDX);
					tabPane.setSelectedIndex(TabbedPaneDataReCodingStep.QUE_TAB_IDX);
					TabQuestion tabQue = (TabQuestion)tabPane.getSelectedComponent();
					tabQue.setDefaults(argument);
					
					break;
				case ActionCommandID.BTN_DAT_STP_QUE_TAB_NEXT:
					if (!model.getIsBrowsing()) {
						tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.DAT_REC_STE_TAB_IDX);
						tabPane.setEnabledAt(TabbedPaneDataReCodingStep.QUE_TAB_IDX, false);
					}
					
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.DAT_REC_STE_TAB_IDX);
					tabPane.setEnabledAt(TabbedPaneDataReCodingStep.STU_TAB_IDX, true);
					tabPane.setSelectedIndex(TabbedPaneDataReCodingStep.STU_TAB_IDX);
									
					break;
				case ActionCommandID.BTN_DAT_STP_STU_TAB_BACK:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.DAT_REC_STE_TAB_IDX);
					tabPane.setSelectedIndex(TabbedPaneDataReCodingStep.QUE_TAB_IDX);
					
					break;
				case ActionCommandID.BTN_DAT_STP_STU_TAB_RESET:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.DAT_REC_STE_TAB_IDX);
					tabPane.setSelectedIndex(TabbedPaneDataReCodingStep.STU_TAB_IDX);
					TabStudy tabStu = (TabStudy)tabPane.getSelectedComponent();
					tabStu.setDefaults(argument);
					
					break;
				case ActionCommandID.BTN_DAT_STP_STU_TAB_NEXT:
					if (!model.getIsBrowsing()) {
						tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.DAT_REC_STE_TAB_IDX);
						tabPane.setEnabledAt(TabbedPaneDataReCodingStep.STU_TAB_IDX, false);
					}
					
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.DAT_REC_STE_TAB_IDX);
					tabPane.setEnabledAt(TabbedPaneDataReCodingStep.MAP_VAR_INS_TAB_IDX, true);
					tabPane.setSelectedIndex(TabbedPaneDataReCodingStep.MAP_VAR_INS_TAB_IDX);
									
					break;	
				
					
				case ActionCommandID.BTN_DAT_STP_MAP_INS_BACK:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.DAT_REC_STE_TAB_IDX);
					tabPane.setSelectedIndex(TabbedPaneDataReCodingStep.STU_TAB_IDX);
					
					break;
				case ActionCommandID.BTN_DAT_STP_MAP_INS_RESET:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.DAT_REC_STE_TAB_IDX);
					tabPane.setSelectedIndex(TabbedPaneDataReCodingStep.MAP_VAR_INS_TAB_IDX);
					TabMapVariableInstance tabMapVarInst = (TabMapVariableInstance)tabPane.getSelectedComponent();
					tabMapVarInst.setDefaults(argument);
					
					break;
				case ActionCommandID.BTN_DAT_STP_MAP_INS_NEXT:
					if (!model.getIsBrowsing()) {
						tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.DAT_REC_STE_TAB_IDX);
						tabPane.setEnabledAt(TabbedPaneDataReCodingStep.MAP_VAR_INS_TAB_IDX, false);
					}
					
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.DAT_REC_STE_TAB_IDX);
					tabPane.setEnabledAt(TabbedPaneDataReCodingStep.MAP_VAR_ATR_TAB_IDX, true);
					tabPane.setSelectedIndex(TabbedPaneDataReCodingStep.MAP_VAR_ATR_TAB_IDX);					
					
					break;
				case ActionCommandID.BTN_DAT_STP_MAP_ATR_BACK:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.DAT_REC_STE_TAB_IDX);
					tabPane.setSelectedIndex(TabbedPaneDataReCodingStep.MAP_VAR_INS_TAB_IDX);
					
					break;
				case ActionCommandID.BTN_DAT_STP_MAP_ATR_RESET:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.DAT_REC_STE_TAB_IDX);
					tabPane.setSelectedIndex(TabbedPaneDataReCodingStep.MAP_VAR_ATR_TAB_IDX);
					TabMapVariableAttribute tabMapVarAttr = (TabMapVariableAttribute)tabPane.getSelectedComponent();
					tabMapVarAttr.setDefaults(argument);
					
					break;
				case ActionCommandID.BTN_DAT_STP_MAP_ATR_NEXT:
					if (!model.getIsBrowsing()) {
						tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.DAT_REC_STE_TAB_IDX);
						tabPane.setEnabledAt(TabbedPaneDataReCodingStep.MAP_VAR_ATR_TAB_IDX, false);
					}
					
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.DAT_REC_STE_TAB_IDX);
					tabPane.setEnabledAt(TabbedPaneDataReCodingStep.MAP_VAR_CHA_TAB_IDX, true);
					tabPane.setSelectedIndex(TabbedPaneDataReCodingStep.MAP_VAR_CHA_TAB_IDX);
					
					break;
				case ActionCommandID.BTN_DAT_STP_MAP_CHA_BACK:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.DAT_REC_STE_TAB_IDX);
					tabPane.setSelectedIndex(TabbedPaneDataReCodingStep.MAP_VAR_ATR_TAB_IDX);
					
					break;
				case ActionCommandID.BTN_DAT_STP_MAP_CHA_RESET:
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.DAT_REC_STE_TAB_IDX);
					tabPane.setSelectedIndex(TabbedPaneDataReCodingStep.MAP_VAR_CHA_TAB_IDX);
					TabMapVariableChar tabMapVarChar = (TabMapVariableChar)tabPane.getSelectedComponent();
					tabMapVarChar.setDefaults(argument);
					
					break;
				case ActionCommandID.BTN_DAT_STP_MAP_CHA_NEXT:
					tabbedPane.setSelectedIndex(TabbedPaneForm.TAR_VAR_TAB_IDX);
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.TAR_VAR_TAB_IDX);
					tabPane.setSelectedIndex(TabbedPaneTargetVariableStep.MEA_TAB_IDX);
					
					break;
				case ActionCommandID.CMD_USER_LOGIN:
					
					break;
				case ActionCommandID.CMD_USER_LOGOFF:
					traverseTabPanes(model, tabbedPane);
					
					((TabbedPaneForm)tabbedPane).enableTabs(model);
					
					tabbedPane.setEnabledAt(TabbedPaneForm.PRO_SET_TAB_IDX, false);
					tabbedPane.setSelectedIndex(TabbedPaneForm.PRO_SET_TAB_IDX);
					tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.PRO_SET_TAB_IDX);
					tabPane.setEnabledAt(TabbedPaneProjectSetupStep.PRO_TAB_IDX, false);
					tabPane.setSelectedIndex(TabbedPaneProjectSetupStep.PRO_TAB_IDX);
					
					if (tabPane.getSelectedComponent() instanceof Tab) {
						((Tab)tabPane.getSelectedComponent()).enableElements(false);
						
						((Tab)tabPane.getSelectedComponent()).resetButton.setEnabled(false);
						((Tab)tabPane.getSelectedComponent()).noteButton.setEnabled(false);
						((Tab)tabPane.getSelectedComponent()).nextButton.setEnabled(false);
					}
					break;
				default:
			}
		}	
	}
	
	/**
	 * @param model
	 * @param tabPanes
	 */
	private void traverseTabPanes(CStatsModel model, JTabbedPane tabPanes) {
		for(int i=0; i<tabPanes.getTabCount(); i++) {
			if (tabPanes.getComponent(i) instanceof JTabbedPane) {
				if (tabPanes.getComponent(i) instanceof TabbedPaneProjectSetupStep)
					((TabbedPaneProjectSetupStep)tabPanes.getComponent(i)).enableTabs(model);
				if (tabPanes.getComponent(i) instanceof TabbedPaneTargetVariableStep)
					((TabbedPaneTargetVariableStep)tabPanes.getComponent(i)).enableTabs(model);
				if (tabPanes.getComponent(i) instanceof TabbedPaneConceptualStep)
					((TabbedPaneConceptualStep)tabPanes.getComponent(i)).enableTabs(model);
				if (tabPanes.getComponent(i) instanceof TabbedPaneOperationalStep)
					((TabbedPaneOperationalStep)tabPanes.getComponent(i)).enableTabs(model);
				if (tabPanes.getComponent(i) instanceof TabbedPaneSearchNCompareStep)
					((TabbedPaneSearchNCompareStep)tabPanes.getComponent(i)).enableTabs(model);
				if (tabPanes.getComponent(i) instanceof TabbedPaneDataReCodingStep)
					((TabbedPaneDataReCodingStep)tabPanes.getComponent(i)).enableTabs(model);
				
				traverseTabPanes(model, (JTabbedPane)tabPanes.getComponent(i));
			} else {								
				((Tab)tabPanes.getComponent(i)).setDefaults(model);
			}
		}
	}
	
	protected static ImageIcon createImageIcon(String resourceURL,
			String description) {
		java.net.URL imgURL = ToolBar.class.getClassLoader().getResource(resourceURL);
		if (imgURL != null) {
	   		return new ImageIcon(imgURL, description);
	   	} else {
	   		System.err.println("Couldn't find file: " + resourceURL);
	   		return null;
	   	}
	}
}

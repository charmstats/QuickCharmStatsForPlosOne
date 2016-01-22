package org.gesis.charmstats.view;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import org.gesis.charmstats.ActionCommandText;
import org.gesis.charmstats.model.CStatsModel;
import org.gesis.charmstats.model.InstanceMap;
import org.gesis.charmstats.model.InstanceMapType;
import org.gesis.charmstats.model.InstanceType;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class TabMapVariableInstance extends Tab {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/* ToolTip: */
	private static final String SELECT_TAB_TOOLTIP	= "mi_select_tab_tt";
	private static final String NEXT_BTN_TOOLTIP	= "mi_next_btn_tt";
	
	/*
	 *	Fields
	 */
	JPanel 				formular;
	
	JScrollPane			table0ScrollPane;
	WSInstanceMapTable	select0Table;
	
	CStatsModel model;
	
	/*
	 *	Constructors
	 */
	/**
	 * @param locale
	 */
	public TabMapVariableInstance(Locale locale) {
		super(locale);
		
		setName("TabMapVariableInstance");
	}

	/**
	 * @param model
	 * @param al
	 * @param locale
	 */
	public TabMapVariableInstance(CStatsModel model, ActionListener al,
			Locale locale) {
		this(locale);
		
		currentLocale	= locale;
		resourceBundle	= ResourceBundle.getBundle(BUNDLE, currentLocale);
		
		this.model = model;
		
		/* Create Form Components */
		JPanel recodeDLInstancePanel = (JPanel)buildRecodeDLInstancePanel(model);
		setDefaults(model.getProject());
		
		/* Add Form Components to Form Panel */
		formPanel.add(recodeDLInstancePanel);
		
		/* Setup Buttons */
		backButton.setActionCommand(ActionCommandText.BTN_DAT_STP_MAP_INS_BACK);
		backButton.addActionListener(al);
//		resetButton.setActionCommand(ActionCommandText.BTN_DAT_STP_MAP_INS_RESET);
//		resetButton.addActionListener(al);
		noteButton.setActionCommand(ActionCommandText.BTN_DAT_STP_MAP_INS_NOTE);
		noteButton.addActionListener(al);
		nextButton.setActionCommand(ActionCommandText.BTN_DAT_STP_MAP_INS_NEXT);
		nextButton.addActionListener(al);
		
		setPanelIdx(5); // 4
		
		changeLanguage(locale);
	}

	/*
	 *	Methods
	 */
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.Tab#changeLanguage(java.util.Locale)
	 */
	public void changeLanguage(Locale locale) {
		super.changeLanguage(locale);
		
		select0Table.changeLanguage(locale);
				
		/* ToolTip: */
		ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, locale);
		
		select0Table.setToolTipText(bundle.getString(SELECT_TAB_TOOLTIP));
		nextButton.setToolTipText(bundle.getString(NEXT_BTN_TOOLTIP));

	}
	
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.Tab#changeFont(java.awt.Font)
	 */
	public void changeFont(Font f) {
		super.changeFont(f);
		
		select0Table.changeFont(f);
	}
	
	/**
	 * @param model
	 * @return
	 */
	private JComponent buildRecodeDLInstancePanel(CStatsModel model) {
		
		/* Table 0 */
		/*
		 *	selectTable presents a list of source work step instances in its rows 
		 *	and a combo box filled with target work step instances.
		 * 
		 */
		select0Table = new WSInstanceMapTable(model, InstanceType.DATA_CODING, InstanceType.OPERATIONAL, resourceBundle);
		table0ScrollPane = new JScrollPane(select0Table);
		table0ScrollPane.getVerticalScrollBar().setUnitIncrement(16);
		
		ListSelectionModel table0SelectionModel = select0Table.table.getSelectionModel();
	    table0SelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    
		/* Put all together */
		formular = new JPanel();
		formular.setLayout(new BoxLayout(formular, BoxLayout.Y_AXIS));
		formular.add(table0ScrollPane);
		
		return formular;
	}
	
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.Tab#setDefaults(java.lang.Object)
	 */
	public void setDefaults(Object defaults) {
		CStatsModel model;
		
		if (defaults instanceof CStatsModel)
			model = (CStatsModel)defaults;
		else {
			model = new CStatsModel();
			
			model.getProject().getContent().setInstanceMaps(new ArrayList<InstanceMap>()); 
		}
		
		fillModel(model);
	}

	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.Tab#fillModel(org.gesis.charmstats.model.CStatsModel)
	 */
	public void fillModel(CStatsModel model) {
		select0Table.tableModel.setRowCount(0);
		select0Table.addRows(model.getProject(), InstanceMapType.DATA_RECODING);
		
		if ((model.getProject().getFinishedSince() != null) ||	
				(!model.getProject().isEditedByUser())) {		
			select0Table.setTableEnabled(false);
		} else {
			select0Table.setTableEnabled(true);
		}
	}
}

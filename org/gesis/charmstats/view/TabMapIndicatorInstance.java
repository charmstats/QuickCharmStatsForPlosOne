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
 *	@since	0.1 / CharmStatsPro only
 *
 */
public class TabMapIndicatorInstance extends Tab  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	public TabMapIndicatorInstance(Locale locale) {
		super(locale);
		
		setName("TabMapIndicatorInstance");
	}

	/**
	 * @param model
	 * @param al
	 * @param locale
	 */
	public TabMapIndicatorInstance(CStatsModel model, ActionListener al,
			Locale locale) {
		this(locale);
		
		currentLocale	= locale;
		resourceBundle	= ResourceBundle.getBundle(BUNDLE, currentLocale);

		this.model = model;
		
		/* Create Form Components */
		JPanel recodeOLInstancePanel = (JPanel)buildRecodeOLInstancePanel(model);
		setDefaults(model.getProject());
		
		/* Add Form Components to Form Panel */
		formPanel.add(recodeOLInstancePanel);
		
		/* Setup Buttons */
		backButton.setActionCommand(ActionCommandText.BTN_OPE_STP_MAP_INS_BACK);
		backButton.addActionListener(al);
//		resetButton.setActionCommand(ActionCommandText.BTN_OPE_STP_MAP_INS_RESET);
//		resetButton.addActionListener(al);
		noteButton.setActionCommand(ActionCommandText.BTN_OPE_STP_MAP_INS_NOTE);
		noteButton.addActionListener(al);
		nextButton.setActionCommand(ActionCommandText.BTN_OPE_STP_MAP_INS_NEXT);
		nextButton.addActionListener(al);
		
		setPanelIdx(3); // 2
		
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
	private JComponent buildRecodeOLInstancePanel(CStatsModel model) {
		
		/* Table 0 */
		/*
		 *	selectTable presents a list of source work step instances in its rows 
		 *	and a combo box filled with target work step instances.
		 * 
		 */
		select0Table = new WSInstanceMapTable(model, InstanceType.OPERATIONAL, InstanceType.CONCEPTUAL, resourceBundle);
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
		select0Table.addRows(model.getProject(), InstanceMapType.OPERATIONAL);
		
		if ((model.getProject().getFinishedSince() != null) ||	
				(!model.getProject().isEditedByUser())) {		
			select0Table.setTableEnabled(false);
		} else {
			select0Table.setTableEnabled(true);
		}
	}
}

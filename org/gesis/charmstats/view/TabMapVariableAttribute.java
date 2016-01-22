package org.gesis.charmstats.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.gesis.charmstats.ActionCommandText;
import org.gesis.charmstats.model.AttributeMap;
import org.gesis.charmstats.model.AttributeMapType;
import org.gesis.charmstats.model.CStatsModel;
import org.gesis.charmstats.model.InstanceLink;
import org.gesis.charmstats.model.InstanceMap;
import org.gesis.charmstats.model.InstanceMapType;
import org.gesis.charmstats.model.OperaIndicator;
import org.gesis.charmstats.model.Project;
import org.gesis.charmstats.model.Variable;
import org.gesis.charmstats.model.WorkStepInstance;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class TabMapVariableAttribute extends Tab {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String INSTANCE_MATCH	= "instance_match";
	private static final String BLANK_STRING	= "                              ";
	
	/* ToolTip: */
	private static final String INST_CB_TOOLTIP		= "ma_inst_cb_tt";
	private static final String MAP_TAB_TOOLTIP		= "ma_map_tab_tt";
	private static final String NEXT_BTN_TOOLTIP	= "ma_next_btn_tt";
	
	/*
	 *	Fields
	 */
	JPanel 		formular;

	JSeparator	separator1;
	
	
	JScrollPane	table0ScrollPane;
	JScrollPane	table1ScrollPane;	
		
	AttributeMapTable map0Table;
	
	JLabel				instanceLabel;
	JComboBox			instanceComboBox;
	
	JPanel mixedCodeNamePane = new JPanel(false);
	
	CStatsModel	model;
	
	/*
	 *	Constructors
	 */
	/**
	 * @param locale
	 */
	public TabMapVariableAttribute(Locale locale) {
		super(locale);
		
		setName("TabMapVariableAttribute");
	}

	/**
	 * @param model
	 * @param al
	 * @param locale
	 */
	public TabMapVariableAttribute(CStatsModel model, ActionListener al,
			Locale locale) {
		this(locale);
		
		currentLocale	= locale;
		resourceBundle	= ResourceBundle.getBundle(BUNDLE, currentLocale);
		
		this.model = model;
		
		/* Create Form Components */
		JPanel recodeDLPanel = (JPanel)buildRecodeDLPanel(model.getProject(), null, resourceBundle, new ArrayList<OperaIndicator>(), new ArrayList<Variable>());
		
		/* Add Form Components to Form Panel */
		formPanel.add(recodeDLPanel);
		
		/* Setup Buttons */
		backButton.setActionCommand(ActionCommandText.BTN_DAT_STP_MAP_ATR_BACK);
		backButton.addActionListener(al);
//		resetButton.setActionCommand(ActionCommandText.BTN_DAT_STP_MAP_ATR_RESET);
//		resetButton.addActionListener(al);
		noteButton.setActionCommand(ActionCommandText.BTN_DAT_STP_MAP_ATR_NOTE);
		noteButton.addActionListener(al);
		nextButton.setActionCommand(ActionCommandText.BTN_DAT_STP_MAP_ATR_NEXT);
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
		
		ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, locale);
		instanceLabel.setText(bundle.getString(INSTANCE_MATCH));
		map0Table.changeLanguage(locale);
		
		/* ToolTip: */
		instanceComboBox.setToolTipText(bundle.getString(INST_CB_TOOLTIP));
		map0Table.setToolTipText(bundle.getString(MAP_TAB_TOOLTIP));
		nextButton.setToolTipText(bundle.getString(NEXT_BTN_TOOLTIP));
		
	}
	
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.Tab#changeFont(java.awt.Font)
	 */
	public void changeFont(Font f) {
		super.changeFont(f);
		
		instanceLabel.setFont(f);
		instanceComboBox.setFont(f);
		
		map0Table.changeFont(f);		
	}
	
	/**
	 * @param project
	 * @param layer
	 * @param bundle
	 * @param indicators
	 * @param variables
	 * @return
	 */
	private JComponent buildRecodeDLPanel(Project project, WorkStepInstance layer, ResourceBundle bundle, ArrayList<OperaIndicator> indicators, ArrayList<Variable> variables) {
		
		separator1 = new JSeparator(SwingConstants.HORIZONTAL);		
		
		/* Table 0 */
		/*
		 *	The attribute mapping is used to create the setup, as the connection between
		 *	different work step instances is not stored explicit. But attribute_link holds
		 *	the much needed information.
		 *
		 *	selectTable presents a list of source work step instances in its rows 
		 *	and a combo box filled with target work step instances.
		 * 
		 */
		final AttributeMapTable mapTable = new AttributeMapTable(model, new Variable(), new OperaIndicator(), resourceBundle);
		
		instanceLabel = new JLabel(resourceBundle.getString(INSTANCE_MATCH));
		instanceComboBox = new JComboBox();
		//AutoCompleteDecorator.decorate(instanceComboBox);
		//instanceComboBox.isEditable();
		instanceComboBox.addItem(BLANK_STRING);
		instanceComboBox.addActionListener (new ActionListener () {
		    /* (non-Javadoc)
		     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		     */
		    public void actionPerformed(ActionEvent e) {
	    		InstanceMap selectedData = null;
	    		
	    		WorkStepInstance source = null;
	    		WorkStepInstance target = null;
	    		
		    	if (e.getSource() != null) {
		    		if ( ((JComboBox)e.getSource()).getSelectedItem() != null) {
		    			if (((JComboBox)e.getSource()).getSelectedItem() instanceof InstanceMap) {
		    				InstanceMap map = (InstanceMap)((JComboBox)e.getSource()).getSelectedItem();
		    				
			    			selectedData = map;
			    			
			    			source = selectedData.getSourceInstance().getInstance();
				    		for (int i=0; i<mapTable.table.getModel().getRowCount(); i++)
				    			mapTable.table.getModel().setValueAt(source, i, 5);	
				    		
				    		if (selectedData.getTargetInstance() instanceof InstanceLink) { 
				    		target = selectedData.getTargetInstance().getInstance();
				    		for (int i=0; i<mapTable.table.getModel().getRowCount(); i++)
				    			mapTable.table.getModel().setValueAt(target, i, 4);	
				    		} 
		    	    		
		    	    	    RowFilter<Object, Object> map0Filter = new RowFilter<Object, Object>() {
		    	    	    	@SuppressWarnings("rawtypes")
								public boolean include(Entry entry) {
		    	    	    				    	    	    		
		    	    	    		if (((AttributeMap)entry.getValue(1)).getSourceAttribute().getInstance() != null)
		    	    	    			return ((AttributeMap)entry.getValue(1)).getSourceAttribute().getInstance().equals((entry.getValue(5))); // Old Order: 4
		    	    				else
		    	    					return false;
		    	    	    	}
		    	    	    };
		    	    	    
				    	    TableRowSorter<TableModel> map0Sorter = new TableRowSorter<TableModel>((TableModel) mapTable.table.getModel());
				    	    map0Sorter.setRowFilter(map0Filter);
				    	    mapTable.table.setRowSorter(map0Sorter);
			    	    };		    				
		    			}
		    		}
		    	}
		});
		instanceComboBox.addFocusListener(this);
		
		JLabel emptyLabel1 = new JLabel(" ");
		JLabel emptyLabel2 = new JLabel(" ");
		JLabel emptyLabel3 = new JLabel(" ");
		JLabel emptyLabel4 = new JLabel(" ");
		JLabel emptyLabel5 = new JLabel(" ");
		JLabel emptyLabel6 = new JLabel(" ");
		
		JPanel mixedLabelPanel = new JPanel();
		mixedLabelPanel.setLayout(new BoxLayout(mixedLabelPanel, BoxLayout.Y_AXIS));
		mixedLabelPanel.add(emptyLabel3);
		mixedLabelPanel.add(instanceLabel);
		mixedLabelPanel.add(Box.createVerticalStrut(5));
		mixedLabelPanel.add(emptyLabel5);
		mixedLabelPanel.add(emptyLabel1);
		
		JPanel mixedContentPanel = new JPanel();
		mixedContentPanel.setLayout(new BoxLayout(mixedContentPanel, BoxLayout.Y_AXIS));
		mixedContentPanel.add(emptyLabel4);
		mixedContentPanel.add(instanceComboBox);
		mixedContentPanel.add(Box.createVerticalStrut(5));
		mixedContentPanel.add(emptyLabel6);
		mixedContentPanel.add(emptyLabel2);
		
		mixedCodeNamePane.setLayout(new FlowLayout(FlowLayout.LEFT));
		mixedCodeNamePane.add(mixedLabelPanel);
		mixedCodeNamePane.add(mixedContentPanel);

		
		/*
		 *	Selecting a row in selectTable creates filter for the mapTable
		 */
		
		/* Table 1 */
		/*
		 *	mapTable presents a list of attributes from the selected source work step instance
		 *	and a combo box filled with attributes from the selected target work step instance.
		 *
		 *	There is a filter on this table, and the default filter value is not satisfiable.
		 *	Selecting a row in selectTable replaces this filter value with one which is satisfiable.
		 *
		 */
		map0Table = mapTable;
		mapTable.addRows(project, AttributeMapType.ASSIGNED_VARIABLE);
		
		table1ScrollPane = new JScrollPane(mapTable);
		table1ScrollPane.getVerticalScrollBar().setUnitIncrement(16);
		
	    RowFilter<Object, Object> map0Filter = new RowFilter<Object, Object>() {
	    	/* (non-Javadoc)
	    	 * @see javax.swing.RowFilter#include(javax.swing.RowFilter.Entry)
	    	 */
	    	@SuppressWarnings("rawtypes")
			public boolean include(Entry entry) {
	    		return false; 
	    	}
	    };
	    TableRowSorter<TableModel> map0Sorter = new TableRowSorter<TableModel>((TableModel) mapTable.table.getModel());
	    map0Sorter.setRowFilter(map0Filter);
	    mapTable.table.setRowSorter(map0Sorter);
		    
		/* Put all together */
		formular = new JPanel();
		formular.setLayout(new BorderLayout());
		formular.add(table1ScrollPane, BorderLayout.CENTER);
		JPanel selectPane = new JPanel(new BorderLayout());
		selectPane.add(separator1, BorderLayout.NORTH);
		selectPane.add(mixedCodeNamePane, BorderLayout.SOUTH);
		formular.add(selectPane, BorderLayout.SOUTH);		
		
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
			
			model.getProject().getContent().setMaps(new ArrayList<AttributeMap>()); 
		}
		
		fillModel(model);
	}
	
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.Tab#fillModel(org.gesis.charmstats.model.CStatsModel)
	 */
	public void fillModel(CStatsModel model) {
		DefaultComboBoxModel boxModel = (DefaultComboBoxModel)instanceComboBox.getModel();
		boxModel.removeAllElements();
		instanceComboBox.addItem(BLANK_STRING);
		ArrayList<InstanceMap> maps = model.getProject().getContent().getInstanceMapsByType(InstanceMapType.DATA_RECODING);		
		Iterator<InstanceMap> mapIterator = maps.iterator();
		while(mapIterator.hasNext()) {
			InstanceMap map = mapIterator.next();
			
			addInstanceMapEntry(map);
		}
		
		map0Table.tableModel.setRowCount(0);
		map0Table.addRows(model.getProject(), AttributeMapType.ASSIGNED_VARIABLE);	
		
		if ((model.getProject().getFinishedSince() != null) ||	
				(!model.getProject().isEditedByUser())) {		
			map0Table.setTableEnabled(false);
		} else {
			map0Table.setTableEnabled(true);
		}
		
		if (instanceComboBox.getItemCount() > 1)
			instanceComboBox.setSelectedIndex(1);
	}
	
	/**
	 * @param map
	 */
	public void addInstanceMapEntry(InstanceMap map) {
		instanceComboBox.addItem(map);
	}

	/**
	 * @param labels
	 * @param controls
	 * @param gridBagLyout
	 * @param container
	 */
	@SuppressWarnings("unused")
	private void addLabelCBoxControlAsRows(	JLabel[] labels,
			Component[] controls,
			GridBagLayout gridBagLyout,
			Container container) {
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.EAST;
		
		int numberOfLabels = labels.length;
		
		for (int i = 0; i < numberOfLabels; i++) {
			c.gridwidth = GridBagConstraints.RELATIVE;
			c.fill = GridBagConstraints.NONE;
			c.weightx = 0.0;
			c.insets = new Insets(0, 0, 6, 0);
			container.add(labels[i], c);
			
			c.gridwidth = GridBagConstraints.REMAINDER;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 1.0;
			c.insets = new Insets(0, 0, 6, 0);
			container.add(controls[i], c);
		}
	}
}

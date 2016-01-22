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
import java.util.Vector;

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
import org.gesis.charmstats.model.AttributeLink;
import org.gesis.charmstats.model.AttributeMap;
import org.gesis.charmstats.model.AttributeMapType;
import org.gesis.charmstats.model.Attributes;
import org.gesis.charmstats.model.CStatsModel;
import org.gesis.charmstats.model.CharacteristicMap;
import org.gesis.charmstats.model.CharacteristicMapType;
import org.gesis.charmstats.model.ConDimension;
import org.gesis.charmstats.model.ConSpecification;
import org.gesis.charmstats.model.InstanceMap;
import org.gesis.charmstats.model.InstanceMapType;
import org.gesis.charmstats.model.Value;
import org.gesis.charmstats.model.Variable;
import org.gesis.charmstats.model.WorkStepInstance;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1 / CharmStatsPro only
 *
 */
public class TabMapDimensionChar  extends Tab {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String INSTANCE_MATCH	= "instance_match";
	private static final String ATTRIBUTE_MATCH	= "attribute_match";
	private static final String BLANK_STRING	= "                              ";
	
	/*
	 *	Fields
	 */
	JPanel 		formular;

	JSeparator	separator1;
	JSeparator	separator2;	
	
	JScrollPane	table0ScrollPane;
	JScrollPane	table1ScrollPane;	
	JScrollPane table2ScrollPane;
	
	CharacteristicMapTable recode0Table;
	
	JLabel				instanceLabel;
	JComboBox			instanceComboBox;
	
	JLabel				attributeLabel;
	JComboBox			attributeComboBox;
	
	JPanel mixedCodeNamePane = new JPanel(false);
	
	ArrayList<AttributeMap>	attr_pairs;
	
	CStatsModel	model;

	/*
	 *	Constructor
	 */
	/**
	 * @param locale
	 */
	public TabMapDimensionChar(Locale locale) {
		super(locale);
		
		setName("TabMapDimensionChar");
	}
	
	/**
	 * @param model
	 * @param al
	 * @param locale
	 */
	public TabMapDimensionChar(CStatsModel model, ActionListener al, Locale locale) {
		this(locale);
		
		currentLocale	= locale;
		resourceBundle	= ResourceBundle.getBundle(BUNDLE, currentLocale);
		
		this.model = model;
		
		/* Create Form Components */
		JPanel recodeCLPanel = (JPanel)buildRecodeCLPanel(model, null, resourceBundle, new ArrayList<Variable>(), new ArrayList<ConDimension>());
		
		/* Add Form Components to Form Panel */
		formPanel.add(recodeCLPanel);
		
		/* Setup Buttons */
		backButton.setActionCommand(ActionCommandText.BTN_CON_STP_MAP_CHA_BACK);
		backButton.addActionListener(al);
//		resetButton.setActionCommand(ActionCommandText.BTN_CON_STP_MAP_CHA_RESET);
//		resetButton.addActionListener(al);
		noteButton.setActionCommand(ActionCommandText.BTN_CON_STP_MAP_CHA_NOTE);
		noteButton.addActionListener(al);
		confirmButton.setVisible(true);
		confirmButton.setEnabled(true);	
		confirmButton.setActionCommand(ActionCommandText.BTN_CON_STP_MAP_CHA_CONFIRM);
		confirmButton.addActionListener(al);
		nextButton.setEnabled(false);
		nextButton.setActionCommand(ActionCommandText.BTN_CON_STP_MAP_CHA_NEXT);
		nextButton.addActionListener(al);
		
		setPanelIdx(2); // 1
		
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
		attributeLabel.setText(bundle.getString(ATTRIBUTE_MATCH));

		recode0Table.changeLanguage(locale);
		
	}
	
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.Tab#changeFont(java.awt.Font)
	 */
	public void changeFont(Font f) {
		super.changeFont(f);
		
		instanceLabel.setFont(f);
		instanceComboBox.setFont(f);
		attributeLabel.setFont(f);
		attributeComboBox.setFont(f);
		
		recode0Table.changeFont(f);
	}

	/**
	 * @param model
	 * @param layer
	 * @param bundle
	 * @param measurements
	 * @param dimensions
	 * @return
	 */
	private JComponent buildRecodeCLPanel(final CStatsModel model, WorkStepInstance layer, ResourceBundle bundle, ArrayList<Variable> measurements, ArrayList<ConDimension> dimensions) {
			
		separator1 = new JSeparator(SwingConstants.HORIZONTAL);		
		separator2 = new JSeparator(SwingConstants.HORIZONTAL);
		
		attributeComboBox = new JComboBox();
		
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
		final CharacteristicMapTable recodeTable = new CharacteristicMapTable(model, new ConSpecification(), new Value(), resourceBundle);

		instanceLabel = new JLabel(resourceBundle.getString(INSTANCE_MATCH));
		instanceComboBox = new JComboBox();
		instanceComboBox.addItem(BLANK_STRING);
		instanceComboBox.addActionListener (new ActionListener () {
		    /* (non-Javadoc)
		     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		     */
		    public void actionPerformed(ActionEvent e) {
	    		InstanceMap selectedData = null;
	    		
	    		@SuppressWarnings("unused")
				WorkStepInstance source = null;
	    		@SuppressWarnings("unused")
				WorkStepInstance target = null;
	    		
		    	if (e.getSource() != null) {
		    		if ( ((JComboBox)e.getSource()).getSelectedItem() != null) {
		    			if (((JComboBox)e.getSource()).getSelectedItem() instanceof InstanceMap) {
		    				InstanceMap map = (InstanceMap)((JComboBox)e.getSource()).getSelectedItem();
		    				
			    			selectedData = map;
			    			source = selectedData.getSourceInstance().getInstance();
			    			target = selectedData.getTargetInstance().getInstance();
			    			
			    			/* 2. ComboBox mit attr mapping passend zur instance map füllen und setzen */
			    			/* Idee: Tabelle wie ursprünglich mal verwendet nutzen */
			    			Vector<AttributeMap> attr_maps = new Vector<AttributeMap>();
			    			
			    			Iterator<AttributeMap> attrMapIterator = attr_pairs.iterator();
			    			while(attrMapIterator.hasNext()) {
			    				AttributeMap attrMap = attrMapIterator.next();
			    				
			    				if ((attrMap.getBelongsTo() instanceof InstanceMap) &&
			    						(attrMap.getBelongsTo().equals(selectedData)))			    					
			    					attr_maps.add(attrMap);
			    			}
			    			
			    			DefaultComboBoxModel boxModel = new DefaultComboBoxModel(attr_maps);
			    			attributeComboBox.setModel(boxModel);
			    			
			    			if (attributeComboBox.getItemCount() > 0)
			    				attributeComboBox.setSelectedIndex(0);		    				
			    	    };		    				
		    			}
		    		}
		    	}
		});
		instanceComboBox.addFocusListener(this);
		
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
		attributeLabel = new JLabel(resourceBundle.getString(ATTRIBUTE_MATCH));
		attributeComboBox.addItem(BLANK_STRING);
		attributeComboBox.addActionListener (new ActionListener () {
		    /* (non-Javadoc)
		     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		     */
		    public void actionPerformed(ActionEvent e) {
	    		AttributeMap selectedData = null;
	    		
	    		Attributes source = null;
	    		Attributes target = null;
	    		
		    	if (e.getSource() != null) {
		    		if ( ((JComboBox)e.getSource()).getSelectedItem() != null) {
		    			if (((JComboBox)e.getSource()).getSelectedItem() instanceof AttributeMap) {
		    				AttributeMap map = (AttributeMap)((JComboBox)e.getSource()).getSelectedItem();
		    				
			    			selectedData = map;		    			
			    			
			    			source = selectedData.getSourceAttribute().getAttribute();
				    		for (int i=0; i<recodeTable.table.getModel().getRowCount(); i++)
				    			recodeTable.table.getModel().setValueAt(source, i, 5); 			    			
				    		
		    				if (selectedData.getTargetAttribute() instanceof AttributeLink) { 
					    		target = selectedData.getTargetAttribute().getAttribute();
					    		for (int i=0; i<recodeTable.table.getModel().getRowCount(); i++)
					    			recodeTable.table.getModel().setValueAt(target, i, 4); 
		    				} 
		    	    		
		    	    	    RowFilter<Object, Object> recode0Filter = new RowFilter<Object, Object>() {
		    	    	    	@SuppressWarnings("rawtypes")
								public boolean include(Entry entry) {
		    	    	    		
		    	    	    		if (((CharacteristicMap)entry.getValue(1)).getSourceCharacteristic().getAttribute() != null)
		    	    	    			return ((CharacteristicMap)entry.getValue(1)).getSourceCharacteristic().getAttribute().equals((entry.getValue(5))); // Old Order: 4
		    	    				else
		    	    					return false;
		    	    	    	}
		    	    	    };
		    	    	    
				    	    TableRowSorter<TableModel> recode0Sorter = new TableRowSorter<TableModel>((TableModel) recodeTable.table.getModel());
				    	    recode0Sorter.setRowFilter(recode0Filter);
				    	    recodeTable.table.setRowSorter(recode0Sorter);
			    	    };		    				
		    			}
		    		}
		    	}

		});
		attributeComboBox.addFocusListener(this);
		
		JLabel emptyLabel1 = new JLabel(" ");
		JLabel emptyLabel2 = new JLabel(" ");
		JLabel emptyLabel3 = new JLabel(" ");
		JLabel emptyLabel4 = new JLabel(" ");
		
		JPanel mixedLabelPanel = new JPanel();
		mixedLabelPanel.setLayout(new BoxLayout(mixedLabelPanel, BoxLayout.Y_AXIS));
		mixedLabelPanel.add(emptyLabel3);
		mixedLabelPanel.add(instanceLabel);
		mixedLabelPanel.add(Box.createVerticalStrut(5));
		mixedLabelPanel.add(attributeLabel);
		mixedLabelPanel.add(emptyLabel1);
		
		JPanel mixedContentPanel = new JPanel();
		mixedContentPanel.setLayout(new BoxLayout(mixedContentPanel, BoxLayout.Y_AXIS));
		mixedContentPanel.add(emptyLabel4);
		mixedContentPanel.add(instanceComboBox);
		mixedContentPanel.add(Box.createVerticalStrut(5));
		mixedContentPanel.add(attributeComboBox);
		mixedContentPanel.add(emptyLabel2);
		
		mixedCodeNamePane.setLayout(new FlowLayout(FlowLayout.LEFT));
		mixedCodeNamePane.add(mixedLabelPanel);
		mixedCodeNamePane.add(mixedContentPanel);

		/*
		 *	Selecting a row in mapTable creates filter for the recodeTable
		 */
		
		/* Table */
		/*
		 *	recodeTable presents a list of values from the selected source attribute
		 *	and a combo box filled with values from the selected target attribute.
		 *
		 *	There is a filter on this table, and the default filter value is not satisfiable.
		 *	Selecting a row in selectTable replaces this filter value with one which is satisfiable.
		 *
		 */
		recode0Table = recodeTable;
		recodeTable.addRows(model.getProject(), CharacteristicMapType.SPECIFICATION);
		table2ScrollPane = new JScrollPane(recodeTable);
		table2ScrollPane.getVerticalScrollBar().setUnitIncrement(16);
				
	    RowFilter<Object, Object> recode0Filter = new RowFilter<Object, Object>() {
	    	/* (non-Javadoc)
	    	 * @see javax.swing.RowFilter#include(javax.swing.RowFilter.Entry)
	    	 */
	    	@SuppressWarnings("rawtypes")
			public boolean include(Entry entry) {
	    		return false; 
	    	}
	    };
	    TableRowSorter<TableModel> recode0Sorter = new TableRowSorter<TableModel>((TableModel) recodeTable.table.getModel());
	    recode0Sorter.setRowFilter(recode0Filter);
	    recodeTable.table.setRowSorter(recode0Sorter);
	    
		/* Put all together */
		formular = new JPanel();
		formular.setLayout(new BorderLayout());
		formular.add(table2ScrollPane, BorderLayout.CENTER);
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
		/* Beide CB füllen und die Tabelle für recode*/	
		/* Master-vector der in der Klasse vereinbart ist mit allen Einträgen für CB 2 füllen, in Behandlung der Auswahl aus
		 * CB1, eine locale Version für CB2 ersezugen und setzen mit Vorbelegung über Objecte aus 1. Mapping
		 * attr_pairs als "globale" Version, die lokal ersetzt wird.
		 */
		CStatsModel model;
		
		if (defaults instanceof CStatsModel)
			model = (CStatsModel)defaults;
		else {
			model = new CStatsModel();
			
			model.getProject().getContent().setCharacteristicMaps(new ArrayList<CharacteristicMap>()); 
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
		ArrayList<InstanceMap> instMaps = model.getProject().getContent().getInstanceMapsByType(InstanceMapType.CONCEPTUAL);		
		Iterator<InstanceMap> instMapIterator = instMaps.iterator();
		while(instMapIterator.hasNext()) {
			InstanceMap instMap = instMapIterator.next();
			
			addInstanceMapEntry(instMap);
		}
		
		boxModel = (DefaultComboBoxModel)attributeComboBox.getModel();
		boxModel.removeAllElements();
		attributeComboBox.addItem(BLANK_STRING);
		
		attr_pairs = new ArrayList<AttributeMap>();
		ArrayList<AttributeMap> attrMaps = model.getProject().getContent().getMapsByType(AttributeMapType.SPECIFICATION);
		Iterator<AttributeMap> attrMapIterator = attrMaps.iterator();
		while(attrMapIterator.hasNext()) {
			AttributeMap attrMap = attrMapIterator.next();
			
			attr_pairs.add(attrMap);
		}
		
		recode0Table.tableModel.setRowCount(0);
		recode0Table.addRows(model.getProject(), CharacteristicMapType.SPECIFICATION);
		
		if ((model.getProject().getFinishedSince() != null) ||	
				(!model.getProject().isEditedByUser())) {		
			recode0Table.setTableEnabled(false);
			confirmButton.setEnabled(false);
		} else {
			recode0Table.setTableEnabled(true);
			confirmButton.setEnabled(true);
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

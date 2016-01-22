package org.gesis.charmstats.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.gesis.charmstats.ActionCommandText;
import org.gesis.charmstats.model.AttributeLink;
import org.gesis.charmstats.model.AttributeLinkType;
import org.gesis.charmstats.model.CStatsModel;
import org.gesis.charmstats.model.Definition;
import org.gesis.charmstats.model.InstanceType;
import org.gesis.charmstats.model.MeasurementLevel;
import org.gesis.charmstats.model.OperaIndicator;
import org.gesis.charmstats.model.TypeOfData;
import org.gesis.charmstats.model.WorkStepInstance;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1 / CharmStatsPro only
 *
 */
public class TabIndicator extends Tab implements TableModelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String OS_INSTANCE		= "os_instance_lbl";
	
	private static final String INDICATOR_ID =	"Indicator_ID";
	private static final String OBJECT = 		"Object";
	private static final String NAME = 			"name_col";
	private static final String DEFINITION = 	"definition_col";
	private static final String LEVEL = 		"level_col";
	private static final String MEASURE_TYPE =	"measure_col";
	private	static final String INSTANCE =		"instance_col";
	private static final String	FILTER			= "Filter";	
	
	private static final int INDICATOR_ID_COLUMN 	= 0;
	private static final int OBJECT_COLUMN 			= 1;
	private static final int NAME_COLUMN 			= 2;
	private static final int LEVEL_COLUMN 			= 3;
	public static final int DEFINITION_COLUMN 		= 4;
	private static final int MEASURE_TYPE_COLUMN 	= 5;
	private static final int INSTANCE_COLUMN 		= 6;
	private static final int FILTER_COLUMN			= 7;
	
	private static final int VIEWPORT_WIDTH =	600;
	private static final int VIEWPORT_HEIGHT =	94; 
	private static final int ROW_HEIGHT = 28; 
	
	private String 				ADD					= "add_btn";
	private String 				REMOVE				= "remove_btn";
	
	private static final String	BLANK_STRING		= new String(new char[32]).replace('\0', ' ');

	JPanel							osInstancePanel;
	JLabel							osInstanceLabel;
	JComboBox						osInstanceComboBox;
	
	JPanel							indicatorTablePanel;
	JTable							indicatorTable;
	DefaultTableModel				indicatorTableModel;
	JComboBox						levelComboBox;
	Vector<TypeOfData>		levelLabels;
	TypeOfData				level;

	JList							valueList;
	Vector<Object>					valueLabels;
	JComboBox						measureTypeComboBox;
	Vector<MeasurementLevel>	measureTypeLabels;
	MeasurementLevel			measureType;
	WorkStepInstance				instance;
	
	JPanel				indicatorButtonPnl;
	JButton				addIndicatorBtn;
	JButton				remIndicatorBtn;
	
	Font				currentFont;
	
	CStatsModel model;
	
	/*
	 *	Constructor
	 */
	/**
	 * @param locale
	 */
	public TabIndicator(Locale locale) {
		super(locale);
		
		setName("TabIndicator");
	}
	
	/**
	 * @param model
	 * @param al
	 * @param locale
	 */
	public TabIndicator(CStatsModel model, ActionListener al, Locale locale) {
		this(locale);
		
		this.model = model;
		
		currentLocale	= locale;
		resourceBundle	= ResourceBundle.getBundle(BUNDLE, currentLocale);
		currentFont		= this.getFont();
		
		/* Create Form Components */
		buildIndicatorTablePanel();
		
		/* Add Form Components to Form Panel */
		formPanel.add(indicatorTablePanel);
		
		/* Setup Buttons */
		backButton.setActionCommand(ActionCommandText.BTN_OPE_STP_IND_TAB_BACK);
		backButton.addActionListener(al);
		noteButton.setActionCommand(ActionCommandText.BTN_OPE_STP_IND_TAB_NOTE);
		noteButton.addActionListener(al);
		nextButton.setActionCommand(ActionCommandText.BTN_OPE_STP_IND_TAB_NEXT);
		nextButton.addActionListener(al);
		
		setPanelIdx(3); // 2
		
		changeLanguage(locale);
	}

	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.Tab#changeLanguage(java.util.Locale)
	 */
	public void changeLanguage(Locale locale) {
		super.changeLanguage(locale);
		
		ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, locale);	
		osInstanceLabel.setText(bundle.getString(OS_INSTANCE));
		
		for (int i=0; i<=3; i++)
			indicatorTable.getTableHeader().getColumnModel().getColumn(i).setHeaderValue(
				bundle.getString((String)indicatorTable.getTableHeader().getColumnModel().getColumn(i).getIdentifier()));
		
		addIndicatorBtn.setText(bundle.getString(ADD));
		remIndicatorBtn.setText(bundle.getString(REMOVE));
	}
	
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.Tab#changeFont(java.awt.Font)
	 */
	public void changeFont(Font f) {
		super.changeFont(f);
		
		osInstanceLabel.setFont(f);
		osInstanceComboBox.setFont(f);

		currentFont = f;
		
		indicatorTable.getTableHeader().setFont(f);
		indicatorTable.setFont(f);
		
		for (int column=0; column<indicatorTable.getModel().getColumnCount(); column++) 
			for (int row=0; row<indicatorTable.getModel().getRowCount(); row++) {
				Object o = indicatorTable.getModel().getValueAt(row, column);
				
				if (o instanceof JComboBox)
					((JComboBox)o).setFont(f);
			}
		
		addIndicatorBtn.setFont(f);
		remIndicatorBtn.setFont(f);
	}
	
	/**
	 * 
	 */
	void buildIndicatorTablePanel() {
		
		buildIndicatorRowModel();
		
        osInstancePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        osInstanceLabel = new JLabel(resourceBundle.getString(OS_INSTANCE));
        osInstanceComboBox = new JComboBox();
        osInstanceComboBox.addItem(BLANK_STRING);
		osInstanceComboBox.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	if (e.getSource() != null) {
		    		if ( ((JComboBox)e.getSource()).getSelectedItem() != null) {
		    			if (((JComboBox)e.getSource()).getSelectedItem() instanceof WorkStepInstance) {
		    				WorkStepInstance inst = (WorkStepInstance)((JComboBox)e.getSource()).getSelectedItem();
		    				
		    	    		for (int i=0; i<indicatorTable.getModel().getRowCount(); i++)
		    	    			indicatorTable.getModel().setValueAt(inst, i, FILTER_COLUMN);
		    	    		
		    	    	    RowFilter<Object, Object> ind0Filter = new RowFilter<Object, Object>() {
				    	    	/* (non-Javadoc)
				    	    	 * @see javax.swing.RowFilter#include(javax.swing.RowFilter.Entry)
				    	    	 */
				    	    	@SuppressWarnings("rawtypes")
								public boolean include(Entry entry) {
				    	    		if (((WorkStepInstance)entry.getValue(INSTANCE_COLUMN)) != null)
				    	    			return (((entry.getValue(INSTANCE_COLUMN).equals(entry.getValue(FILTER_COLUMN)))));
				    	    		else
				    	    			return false;
				    	    	}
				    	    };
				    	    TableRowSorter<TableModel> ind0Sorter = new TableRowSorter<TableModel>((TableModel) indicatorTable.getModel());
				    	    ind0Sorter.setRowFilter(ind0Filter);
				    	    indicatorTable.setRowSorter(ind0Sorter);		    				
			    		}
		    		}
		    	}
		    }
		});
		osInstanceComboBox.addFocusListener(this);
        
        osInstancePanel.add(osInstanceLabel);
        osInstancePanel.add(osInstanceComboBox);
		
		indicatorTableModel = buildIndicatorTableModel();
        indicatorTable = buildIndicatorTable(indicatorTableModel);
        indicatorTable.getColumnModel().getColumn(NAME_COLUMN).setIdentifier(NAME);
        indicatorTable.getColumnModel().getColumn(LEVEL_COLUMN).setIdentifier(LEVEL);
        indicatorTable.getColumnModel().getColumn(DEFINITION_COLUMN).setIdentifier(DEFINITION);
        indicatorTable.getColumnModel().getColumn(MEASURE_TYPE_COLUMN).setIdentifier(MEASURE_TYPE);
        indicatorTable.removeColumn(indicatorTable.getColumn(INDICATOR_ID));
        indicatorTable.removeColumn(indicatorTable.getColumn(OBJECT));
        indicatorTable.setName("indIndicatorTbl");
        
		OperaIndicator ind = new OperaIndicator();
		ind.setLevel(model.getProject().getContent().getMeasurement().getLevel());
		
		addIndicatorRow(ind, null, null); // dummy element		
	    RowFilter<Object, Object> ind0Filter = new RowFilter<Object, Object>() {
	    	/* (non-Javadoc)
	    	 * @see javax.swing.RowFilter#include(javax.swing.RowFilter.Entry)
	    	 */
	    	@SuppressWarnings("rawtypes")
			public boolean include(Entry entry) {
	    		return false; 
	    	}
	    };
	    TableRowSorter<TableModel> ind0Sorter = new TableRowSorter<TableModel>((TableModel) indicatorTable.getModel());
	    ind0Sorter.setRowFilter(ind0Filter);
	    indicatorTable.setRowSorter(ind0Sorter);
        
		addIndicatorBtn		= new JButton(resourceBundle.getString(ADD));
		addIndicatorBtn.addActionListener( 
				new ActionListener() {
					/* (non-Javadoc)
					 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
					 */
					public void actionPerformed(ActionEvent e) {
						addIndicatorBtnAction(e);
					}
				}
		);
		remIndicatorBtn		= new JButton(resourceBundle.getString(REMOVE));
		remIndicatorBtn.addActionListener(
				new ActionListener() {
					/* (non-Javadoc)
					 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
					 */
					public void actionPerformed(ActionEvent e) {
						remIndicatorBtnAction(e);
					}
				}
		);
		
		indicatorButtonPnl	= new JPanel(new FlowLayout(FlowLayout.TRAILING));
		indicatorButtonPnl.add(addIndicatorBtn);
		indicatorButtonPnl.add(remIndicatorBtn);
        
        indicatorTablePanel = new JPanel();
        indicatorTablePanel.setLayout(new BorderLayout());
        indicatorTablePanel.add(osInstancePanel, BorderLayout.NORTH);
        JScrollPane indScrollPane = new JScrollPane(indicatorTable);
        indScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        indScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        indicatorTablePanel.add(indScrollPane, BorderLayout.CENTER);        
        indicatorTablePanel.add(indicatorButtonPnl, BorderLayout.SOUTH);
	}
	
	/**
	 * @param e
	 */
	protected void addIndicatorBtnAction(ActionEvent e) {
		if (osInstanceComboBox.getSelectedItem() instanceof WorkStepInstance) {
			OperaIndicator ind = new OperaIndicator();
			ind.setLevel(model.getProject().getContent().getMeasurement().getLevel());
			
			this.addIndicatorRow(ind, (WorkStepInstance)osInstanceComboBox.getSelectedItem(), (WorkStepInstance)osInstanceComboBox.getSelectedItem());
		}
	}
	
	/**
	 * @param ind
	 * @param inst
	 * @param filter
	 */
	public void addIndicatorRow(OperaIndicator ind, WorkStepInstance inst, WorkStepInstance filter) {		
		JComboBox level = new GComboBox(this.levelLabels);
		level.setFont(currentFont);
		level.setBorder(BorderFactory.createEmptyBorder());
		if (ind.getLevel() != null) {
			level.setSelectedItem(ind.getLevel());
		} else {
			level.setSelectedItem(TypeOfData.NONE);
			ind.setLevel((TypeOfData)level.getSelectedItem());
		}
		
		JComboBox type = new GComboBox(this.measureTypeLabels);
		type.setFont(currentFont);
		type.setBorder(BorderFactory.createEmptyBorder());
		if (ind.getType() != null) {
			type.setSelectedItem(ind.getType());
		} else {
			type.setSelectedItem(MeasurementLevel.NONE);
			ind.setType((MeasurementLevel)type.getSelectedItem());
		}
		
		this.indicatorTableModel.addRow(
			new Object[]{
				ind.getEntityID(),
				ind,
				ind.getLabel(),				
				level,
				ind.getDefinition().getText(),
				type,
				inst,
				filter
			}
		);
		
		if (!indicatorTable.getAutoCreateRowSorter())
			indicatorTable.setAutoCreateRowSorter(true);
	}

	/**
	 * @param e
	 */
	protected void remIndicatorBtnAction(ActionEvent e) {
		int[] selectedRows = indicatorTable.getSelectedRows();
		
		for (int index=selectedRows.length-1; index >= 0; index--) {
			int selectedRow = indicatorTable.convertRowIndexToModel(selectedRows[index]);
			indicatorTableModel.removeRow(selectedRow);
		}
	}
	
	/**
	 * 
	 */
	void buildIndicatorRowModel() {
		
		levelLabels = new Vector<TypeOfData>();
		for (TypeOfData indicatorLevel : TypeOfData.values()) {
			indicatorLevel.setLocale(currentLocale);
			
			levelLabels.add(indicatorLevel);
		}
		
		valueLabels = new Vector<Object>();
		
		measureTypeLabels = new Vector<MeasurementLevel>();
		for (MeasurementLevel indicatorMeasureType : MeasurementLevel.values()) {
			indicatorMeasureType.setLocale(currentLocale);
			
			measureTypeLabels.add(indicatorMeasureType);
		}
	}
	
	/**
	 * @return
	 */
	DefaultTableModel buildIndicatorTableModel() {
		
		
		DefaultTableModel tableModel = new DefaultTableModel(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			/* (non-Javadoc)
			 * @see javax.swing.table.DefaultTableModel#isCellEditable(int, int)
			 */
			public boolean isCellEditable(int row,int column){  
//				if(column == LEVEL_COLUMN) return false;  
				return true;  
			}
			
            /* (non-Javadoc)
             * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
             */
            @SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int column) {
                Class returnValue;
                if (this.getRowCount() > 0)
	                if ((column >= 0) && (column < getColumnCount())) {
						if (getValueAt(0, column) != null)
							returnValue = getValueAt(0, column).getClass();
						else
							returnValue = Object.class;
	                } else {
	                	returnValue = Object.class;
	                }
                else
                	returnValue = Object.class;
                
                return returnValue;
            }
		};
            
		tableModel.addColumn(INDICATOR_ID);
		tableModel.addColumn(OBJECT);
		tableModel.addColumn(resourceBundle.getString(NAME));
		tableModel.addColumn(resourceBundle.getString(LEVEL));
        tableModel.addColumn(resourceBundle.getString(DEFINITION));
        tableModel.addColumn(resourceBundle.getString(MEASURE_TYPE));
        tableModel.addColumn(INSTANCE);
        tableModel.addColumn(FILTER);
        
        return tableModel;
	}

	/**
	 * @param tableModel
	 * @return
	 */
	JTable buildIndicatorTable(DefaultTableModel tableModel) {
		
		JTable table = new JTable(tableModel) {
           	/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			/* (non-Javadoc)
			 * @see javax.swing.JTable#getCellRenderer(int, int)
			 */
			@SuppressWarnings("rawtypes")
			public TableCellRenderer getCellRenderer(int row, int column) {
				TableColumn			tableColumn			= getColumnModel().getColumn(column);
				TableCellRenderer	tableCellRenderer	= tableColumn.getCellRenderer();
				if (tableCellRenderer == null) {
					Class c = getColumnClass(column);
					if (c.equals(Object.class)) {
						Object o = getValueAt(row,column);
						if (o != null)
							c = getValueAt(row,column).getClass();
					}
					tableCellRenderer = getDefaultRenderer(c);
				}
				return tableCellRenderer;
			}
			
			/* (non-Javadoc)
			 * @see javax.swing.JTable#getCellEditor(int, int)
			 */
			@SuppressWarnings("rawtypes")
			public TableCellEditor getCellEditor(int row, int column) {
				TableColumn 	tableColumn		= getColumnModel().getColumn(column);
				TableCellEditor tableCellEditor	= tableColumn.getCellEditor();
				if (tableCellEditor == null) {
					Class c = getColumnClass(column);
					if (c.equals(Object.class)) {
						Object o = getValueAt(row,column);
						if (o != null)
							c = getValueAt(row,column).getClass();
					}
					tableCellEditor = getDefaultEditor(c);
				}
				return tableCellEditor;
			}
			
			/* (non-Javadoc)
			 * @see javax.swing.JTable#prepareRenderer(javax.swing.table.TableCellRenderer, int, int)
			 */
			public Component prepareRenderer(TableCellRenderer renderer,int row, int col) {
				Component	component	= super.prepareRenderer(renderer, row, col);
		        JComponent	jComponent	= (JComponent)component;
		        if (component == jComponent) {
		        	if (this.getValueAt(row,col) != null)
		        		jComponent.setToolTipText((String)this.getValueAt(row, col).toString());
		        	
		        	if (isRowSelected(row))
		        		jComponent.setBackground(Color.yellow);		        	
		        	else
		        		jComponent.setBackground(Color.white);
		        	jComponent.setForeground(Color.black);
		        }
		        return component;
			}
        };

        table.setPreferredScrollableViewportSize(new Dimension(VIEWPORT_WIDTH, VIEWPORT_HEIGHT));
        table.setRowHeight(ROW_HEIGHT);
        
        table.setDefaultRenderer(JComponent.class, new JComponentCellRenderer());
		table.setDefaultEditor(JComponent.class, new JComponentCellEditor());
	    
		table.getModel().addTableModelListener(this);
		table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

		table.removeColumn(table.getColumn(INSTANCE));
		table.removeColumn(table.getColumn(FILTER));
		
        return table;
	}
	
	class JComponentCellRenderer implements TableCellRenderer
	{
	    public Component getTableCellRendererComponent(JTable table, Object value,
	    		boolean isSelected, boolean hasFocus, int row, int column) {
	        return (JComponent)value;
	    }
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.event.TableModelListener#tableChanged(javax.swing.event.TableModelEvent)
	 */
	public void tableChanged(TableModelEvent e) {
        TableModel	model	= (TableModel)e.getSource();
        int 		row		= e.getFirstRow();
        int 		column	= e.getColumn();

        if (row > -1 && column > -1) {
            Object  data = model.getValueAt(row, column);
        	
        	if (data != null ) {
        		OperaIndicator indicator = (OperaIndicator)model.getValueAt(row, OBJECT_COLUMN);
        		
	        	switch (column) {
	        		case INDICATOR_ID_COLUMN:
	        		case OBJECT_COLUMN:
	        			break;
	        		case LEVEL_COLUMN:
	        			if (((JComboBox) data).getSelectedItem() instanceof TypeOfData)
        					indicator.level = (TypeOfData) ((JComboBox) data).getSelectedItem();
	        			break;
	        		case NAME_COLUMN:
	        			break;
	        		case DEFINITION_COLUMN:
	        			indicator.setDefinition(new Definition((String)data));
	        			break;
	        		case MEASURE_TYPE_COLUMN:
	        			break;
	        		case INSTANCE_COLUMN:
	        			break;
	        	}
        	}   	
        }
		
	}
	
	/**
	 * @param inst
	 */
	public void addInstanceEntry(WorkStepInstance inst) {
		osInstanceComboBox.addItem(inst);
	}
	
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.Tab#fillModel(org.gesis.charmstats.model.CStatsModel)
	 */
	public void fillModel(CStatsModel model) {
		DefaultComboBoxModel boxModel = (DefaultComboBoxModel)osInstanceComboBox.getModel();
		boxModel.removeAllElements();
		osInstanceComboBox.addItem(BLANK_STRING);
		ArrayList<WorkStepInstance> instances = model.getProject().getContent().getLayers(InstanceType.OPERATIONAL);		
		Iterator<WorkStepInstance> instIterator = instances.iterator();
		while(instIterator.hasNext()) {
			WorkStepInstance inst = instIterator.next();
			
			addInstanceEntry(inst);
		}
		
		indicatorTableModel.setRowCount(0);
		ArrayList<OperaIndicator> indicators = model.getProject().getContent().getIndicators();
		Iterator<OperaIndicator> iterator = indicators.iterator();
		while(iterator.hasNext()) {
			OperaIndicator ind = iterator.next();			
			WorkStepInstance inst = null;
			
			Iterator<AttributeLink> attrIterator = model.getProject().getContent().getLinksByType(AttributeLinkType.INDICATOR).iterator();;
    		while (attrIterator.hasNext()) {
    			AttributeLink link = attrIterator.next();
    			
    			if (link.getAttribute().equals(ind))
    				inst = (WorkStepInstance)link.getInstance();
    		}
			
			addIndicatorRow(ind, inst, null);
		}
		
		if ((model.getProject().getFinishedSince() != null) ||	
				(!model.getProject().isEditedByUser())) {		
			indicatorTable.setEnabled(false);
			addIndicatorBtn.setEnabled(false);
			remIndicatorBtn.setEnabled(false);
		} else {
			indicatorTable.setEnabled(true);
			addIndicatorBtn.setEnabled(true);
			remIndicatorBtn.setEnabled(true);
		}
		
		if (osInstanceComboBox.getItemCount() > 1)
			osInstanceComboBox.setSelectedIndex(1);		
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
			
			model.getProject().getContent().setIndicators(new ArrayList<OperaIndicator>());
		}
		
		fillModel(model);			
	}
	
}

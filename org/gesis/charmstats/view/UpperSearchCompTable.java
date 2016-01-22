package org.gesis.charmstats.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import org.gesis.charmstats.model.CStatsModel;
import org.gesis.charmstats.model.CharacteristicLink;
import org.gesis.charmstats.model.MeasurementLevel;
import org.gesis.charmstats.model.OperaIndicator;
import org.gesis.charmstats.model.OperaPrescription;
import org.gesis.charmstats.model.TypeOfData;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1 / CharmStatsPro only
 *
 */
public class UpperSearchCompTable extends JPanel implements TableModelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String BUNDLE	= "org.gesis.charmstats.resources.DesktopBundle";
	
	private static final String INDICATOR_ID	= "Indicator_ID";
	private static final String OBJECT = 		"Object";
	private static final String LEVEL = 		"usct_level";
	private static final String NAME = 			"usct_name";
	private static final String VALUE =			"usct_categories";
	private static final String MEASURE_TYPE =	"usct_measurement_type";
	public  static final String	FILTER		= "Filter";
	public  static final String FILLER		= "Filler";
	
	private static final int INDICATOR_ID_COLUMN =	0;
	public static final int OBJECT_COLUMN =		1;
	private static final int LEVEL_COLUMN =			3;
	private static final int NAME_COLUMN =			2;
	private static final int VALUE_COLUMN =			4;
	private static final int MEASURE_TYPE_COLUMN =	5;
	public static final int FILTER_COLUMN			= 6;
	public static final int FILLER_COLUMN			= 7;
	
	private static final int VIEWPORT_WIDTH =	600;
	private static final int VIEWPORT_HEIGHT =	48; 
	private static final int ROW_HEIGHT = 28; 

	Locale 		  		currentLocale;
	ResourceBundle		resourceBundle;
	
	JPanel				tablePanel;
	JTable				table;
	DefaultTableModel	indicatorTableModel;
	Vector<Object>		levelLabels;
	TypeOfData			level;
	Vector<Object>		valueLabels;
	Vector<Object>		measureTypeLabels;
	MeasurementLevel	measureType;
	
	Font				currentFont;
	
	
	/*
	 *	Constructor
	 */
	/**
	 * @param locale
	 */
	UpperSearchCompTable (Locale locale) {
		this.setLayout(new BorderLayout());
		
		currentLocale	= locale;
		resourceBundle	= ResourceBundle.getBundle(BUNDLE, currentLocale);
		currentFont		= this.getFont();
		
		buildTablePanel();
		this.add(tablePanel, BorderLayout.NORTH);
	}
	
	/*
	 *	Methods
	 */
	/**
	 * @param locale
	 */
	public void changeLanguage(Locale locale) {
		ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, locale);
		
		table.getColumn(resourceBundle.getString(LEVEL)).setHeaderValue(bundle.getString(LEVEL));
		table.getColumn(resourceBundle.getString(NAME)).setHeaderValue(bundle.getString(NAME));
		table.getColumn(resourceBundle.getString(MEASURE_TYPE)).setHeaderValue(bundle.getString(MEASURE_TYPE));
		
		resourceBundle = bundle;
	}
	
	/**
	 * @param f
	 */
	public void changeFont(Font f) {
		table.getTableHeader().setFont(f);
		table.setFont(f);
		
		currentFont = f;
		
		for (int column=0; column<table.getModel().getColumnCount(); column++) 
			for (int row=0; row<table.getModel().getRowCount(); row++) {
				Object o = table.getModel().getValueAt(row, column);
				
				if (o instanceof JComboBox)
					((JComboBox)o).setFont(f);
				
				if (o instanceof JList)
					((JList)o).setFont(f);
			}
	}
	
	/**
	 * 
	 */
	void buildRowModel() {
				
		levelLabels = new Vector<Object>();
		for (TypeOfData indicatorLevel : TypeOfData.values()) {
			indicatorLevel.setLocale(currentLocale);
			
			levelLabels.add(indicatorLevel);
		}
		
		valueLabels = new Vector<Object>();
		
		measureTypeLabels = new Vector<Object>();
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
				if(column == LEVEL_COLUMN) return false;
				if(column == MEASURE_TYPE_COLUMN) return false;
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
		tableModel.addColumn(VALUE);
        tableModel.addColumn(resourceBundle.getString(MEASURE_TYPE));
		tableModel.addColumn(FILTER);
		tableModel.addColumn(FILLER);
        
        return tableModel;
	}
	
	/**
	 * @param tableModel
	 * @return
	 */
	JTable buildTable(DefaultTableModel tableModel) {
		
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
				TableColumn tableColumn = getColumnModel().getColumn(column);
				TableCellRenderer renderer = tableColumn.getCellRenderer();
				if (renderer == null) {
					Class c = getColumnClass(column);
					if( c.equals(Object.class) )
					{
						Object o = getValueAt(row,column);
						if( o != null )
							c = getValueAt(row,column).getClass();
					}
					renderer = getDefaultRenderer(c);
				}
				return renderer;
			}
			
			/* (non-Javadoc)
			 * @see javax.swing.JTable#getCellEditor(int, int)
			 */
			@SuppressWarnings("rawtypes")
			public TableCellEditor getCellEditor(int row, int column) {
				TableColumn tableColumn = getColumnModel().getColumn(column);
				TableCellEditor editor = tableColumn.getCellEditor();
				if (editor == null) {
					Class c = getColumnClass(column);
					if( c.equals(Object.class) )
					{
						Object o = getValueAt(row,column);
						if( o != null )
							c = getValueAt(row,column).getClass();
					}
					editor = getDefaultEditor(c);
				}
				return editor;
			}
			
			/* (non-Javadoc)
			 * @see javax.swing.JTable#prepareRenderer(javax.swing.table.TableCellRenderer, int, int)
			 */
			public Component prepareRenderer(TableCellRenderer renderer,int row, int col) {
				Component component = super.prepareRenderer(renderer, row, col);
		        JComponent jComponent = (JComponent)component;
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

        return table;
	}
	
	/**
	 * @param model
	 */
	public void addRows(CStatsModel model) {
		Iterator<OperaIndicator> iterator = model.getProject().getContent().getIndicators().iterator();	    
		while (iterator.hasNext()) {
			addRow(iterator.next(), model);						
		}		
	}
	
	/**
	 * @param ind
	 * @param model
	 */
	public void addRow(OperaIndicator ind, CStatsModel model) {

		JComboBox level = new GComboBox(this.levelLabels);
		level.setFont(currentFont);
		if (ind.getLevel() != null) {
			level.setSelectedItem(ind.getLevel());
		} else {
			level.setSelectedItem(TypeOfData.NONE);
			ind.setLevel((TypeOfData)level.getSelectedItem());
		}
		
		Vector<String> pres = new Vector<String>();
		
		ArrayList<CharacteristicLink> links = model.getProject().getContent().getCharacteristicLinksByAttribute(ind);
		Iterator<CharacteristicLink> linkIter = links.iterator();
		while(linkIter.hasNext()) {
			CharacteristicLink link = linkIter.next();
			OperaPrescription pre = (OperaPrescription)link.getCharacteristic();
			pres.add(pre.getValue() + "=" + pre.getLabel());
		}

		GList gList = new GList(pres); 
		gList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		gList.setFont(currentFont);

		JScrollPane value = new GScrollPane(gList);
		value.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		value.setBorder(BorderFactory.createEmptyBorder());
		
		JComboBox measureType = new GComboBox(this.measureTypeLabels);
		measureType.setFont(currentFont);
		if (ind.getType() != null) {
			measureType.setSelectedItem(ind.getType());
		} else {
			ind.setType((MeasurementLevel)measureType.getSelectedItem());
		}
		
		this.indicatorTableModel.addRow(
			new Object[]{
				ind.getEntityID(),
				ind,
				ind.getLabel(),
				level,
				value,
				measureType
			}
		);
		
		if (!table.getAutoCreateRowSorter())
			table.setAutoCreateRowSorter(true);
	}

	/**
	 * 
	 */
	public void clear() {
		this.indicatorTableModel.setRowCount(0);
	}
	
	/**
	 * @param enabled
	 */
	public void setTableEnabled(boolean enabled) {
		table.setEnabled(enabled);
	}
	
	/**
	 * 
	 */
	void buildTablePanel() {
		
		buildRowModel();
		
		indicatorTableModel = buildIndicatorTableModel();
        table = buildTable(indicatorTableModel);
        table.removeColumn(table.getColumn(INDICATOR_ID));
        table.removeColumn(table.getColumn(OBJECT));
        table.removeColumn(table.getColumn(VALUE));
		table.removeColumn(table.getColumn(FILTER));
		table.removeColumn(table.getColumn(FILLER));
        
        tablePanel = new JPanel();
        tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);
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
	@Override
	public void tableChanged(TableModelEvent e) {
		TableModel	model 	= (TableModel)e.getSource();
        int 		row 	= e.getFirstRow();
        int 		column	= e.getColumn();
        if (row > -1 && column > -1) {
        	Object data = model.getValueAt(row, column);
        	
        	if (data != null ) {
        		@SuppressWarnings("unused")
				OperaIndicator indicator = (OperaIndicator)model.getValueAt(row, OBJECT_COLUMN);
        		
	        	switch (column) {
	        		case INDICATOR_ID_COLUMN:
	        		case OBJECT_COLUMN:
	        		case NAME_COLUMN:
	        		case LEVEL_COLUMN:
	        		case VALUE_COLUMN:
	        		case MEASURE_TYPE_COLUMN:
	        	}
        	}   	
        }
		
	}
	
}

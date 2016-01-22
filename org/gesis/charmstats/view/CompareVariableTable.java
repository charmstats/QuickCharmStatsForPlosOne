package org.gesis.charmstats.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Iterator;
import java.util.Locale;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import org.gesis.charmstats.model.AttributeComp;
import org.gesis.charmstats.model.CStatsModel;
import org.gesis.charmstats.model.MeasurementLevel;
import org.gesis.charmstats.model.TypeOfData;
import org.gesis.charmstats.model.Variable;
import org.gesis.charmstats.model.VariableType;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1 / CharmStatsPro only
 *
 */
public class CompareVariableTable extends GTable implements TableModelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String BUNDLE			= "org.gesis.charmstats.resources.DesktopBundle";
		
	private static final String TABLE_ID		= "Table_ID";
	private static final String OBJECT			= "Object";
	private static final String VAR_TYPE		= "cvd_var_type";
	private static final String VAR_LEVEL		= "cvd_var_level";
	private static final String VAR_MEA			= "cvd_var_mea";
	public  static final String	FILTER			= "Filter";
	public  static final String FILLER			= "Filler";
	
	private static final int TABLE_ID_COLUMN	= 0;
	public static final int OBJECT_COLUMN		= 1;
	private static final int TYPE_COLUMN		= 2;
	private static final int LEVEL_COLUMN		= 3;
	private static final int MEA_COLUMN			= 4;
	public static final int FILTER_COLUMN		= 5;
	public static final int FILLER_COLUMN		= 6;

	private static final int VIEWPORT_WIDTH		= 600;
	private static final int VIEWPORT_HEIGHT	=  94; 
	private static final int ROW_HEIGHT			=  28; 
    
	/*
	 *	Fields
	 */
	Locale 		  		currentLocale;
	ResourceBundle		resourceBundle;
	Font				currentFont;
	
	JPanel				tablePanel;
	JTable				table;
	DefaultTableModel	variableTableModel;
	
	Vector<VariableType> 			typeLabels;
	VariableType					type;
	Vector<TypeOfData>		levelLabels;
	TypeOfData				level;

	Vector<MeasurementLevel>		measureTypeLabels;
	MeasurementLevel				measureType;
	
	/*
	 *	Constructor
	 */
	/**
	 * @param locale
	 */
	CompareVariableTable (Locale locale) {
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
		currentLocale = locale;
		
		table.getColumn(resourceBundle.getString(VAR_TYPE)).setHeaderValue(bundle.getString(VAR_TYPE));
		table.getColumn(resourceBundle.getString(VAR_LEVEL)).setHeaderValue(bundle.getString(VAR_LEVEL));
		table.getColumn(resourceBundle.getString(VAR_MEA)).setHeaderValue(bundle.getString(VAR_MEA));
		
		typeLabels.clear();
		for (VariableType variableType : VariableType.values()) {
			variableType.setLocale(currentLocale);
			
			typeLabels.add(variableType);
		}
		
		levelLabels.clear();
		for (TypeOfData variableLevel : TypeOfData.values()) {
			variableLevel.setLocale(currentLocale);
			
			levelLabels.add(variableLevel);
		}
		
		measureTypeLabels.clear();
		for (MeasurementLevel variableMeasureType : MeasurementLevel.values()) {
			variableMeasureType.setLocale(currentLocale);
			
			measureTypeLabels.add(variableMeasureType);
		}
		
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
			}
	}
	
	/**
	 * 
	 */
	void buildRowModel() {
		
		typeLabels = new Vector<VariableType>();
		for (VariableType variableType : VariableType.values()) {
			variableType.setLocale(currentLocale);
			
			typeLabels.add(variableType);
		}
		
		levelLabels = new Vector<TypeOfData>();
		for (TypeOfData variableLevel : TypeOfData.values()) {
			variableLevel.setLocale(currentLocale);
			
			levelLabels.add(variableLevel);
		}
		
		measureTypeLabels = new Vector<MeasurementLevel>();
		for (MeasurementLevel variableMeasureType : MeasurementLevel.values()) {
			variableMeasureType.setLocale(currentLocale);
			
			measureTypeLabels.add(variableMeasureType);
		}
		
	}
	
	/**
	 * @return
	 */
	DefaultTableModel buildVariableTableModel() {
		
		DefaultTableModel tableModel = new DefaultTableModel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
			public boolean isCellEditable(int row,int column){
				if(column == TYPE_COLUMN) return false;
				if(column == LEVEL_COLUMN) return false;
				if(column == MEA_COLUMN) return false;
				return true;  
			}

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
            
		tableModel.addColumn(TABLE_ID);
		tableModel.addColumn(OBJECT);
		tableModel.addColumn(resourceBundle.getString(VAR_TYPE));
		tableModel.addColumn(resourceBundle.getString(VAR_LEVEL));
		tableModel.addColumn(resourceBundle.getString(VAR_MEA));
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
		Iterator<AttributeComp> iterator = model.getProject().getContent().getComps().iterator();	    
		while (iterator.hasNext()) {
			addRow(iterator.next());						
		}		
	}
	
	/**
	 * @param comp
	 */
	public void addRow(AttributeComp comp) {
		JComboBox type = new JComboBox(this.typeLabels);
		
		Variable variable = (Variable)comp.getSourceAttribute().getAttribute();
		if (variable.getType() != null) {
			type.setSelectedItem(variable.getType());
		} else {
			variable.setType((VariableType)type.getSelectedItem());
		}

		JComboBox level = new GComboBox(this.levelLabels);
		level.setFont(currentFont);
		if (variable.getLevel() != null) {
			level.setSelectedItem(variable.getLevel());
		} else {
			level.setSelectedItem(TypeOfData.NONE);
			variable.setLevel((TypeOfData)level.getSelectedItem());
		}
		
		JComboBox measureType = new GComboBox(this.measureTypeLabels);
		measureType.setFont(currentFont);
		if (variable.getMeasureType() != null) {
			measureType.setSelectedItem(variable.getMeasureType());
		} else {
			variable.setMeasureType((MeasurementLevel)measureType.getSelectedItem());
		}
		
		this.variableTableModel.addRow(
			new Object[]{
				comp.getEntityID(), 
				comp, 				
				type,
				level,
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
		this.variableTableModel.setRowCount(0);
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
		
		variableTableModel = buildVariableTableModel();
        table = buildTable(variableTableModel);
		table.removeColumn(table.getColumn(TABLE_ID));
		table.removeColumn(table.getColumn(OBJECT));
		table.removeColumn(table.getColumn(FILTER));
		table.removeColumn(table.getColumn(FILLER));

        tablePanel = new JPanel();
        JScrollPane compareVariableScrollPane = new JScrollPane(table);
        compareVariableScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        tablePanel.add(compareVariableScrollPane, BorderLayout.CENTER);
	}
	
	class JComponentCellRenderer implements TableCellRenderer {
		
	    /* (non-Javadoc)
	     * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
	     */
	    public Component getTableCellRendererComponent(JTable table, Object value,
	    		boolean isSelected, boolean hasFocus, int row, int column) {
	        return (JComponent)value;
	    }
	}
	
	class TextCellRenderer extends JTextArea implements TableCellRenderer {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/* (non-Javadoc)
		 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
		 */
		public Component getTableCellRendererComponent(JTable table, Object obj, 
				boolean isSelected, boolean hasFocus, int row, int column) {
			setLineWrap(true);
			setWrapStyleWord(true);
			setText( (String) obj);

			return this;
		}
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.event.TableModelListener#tableChanged(javax.swing.event.TableModelEvent)
	 */
	public void tableChanged(TableModelEvent e) {
		TableModel	model	= (TableModel)e.getSource();
		
        int			row		= e.getFirstRow();
        int			column	= e.getColumn();
        
        if (row > -1 && column > -1) {            
            Object data = model.getValueAt(row, column);
        	
        	if (data != null ) {
        		@SuppressWarnings("unused")
				AttributeComp comp = (AttributeComp)model.getValueAt(row, OBJECT_COLUMN);
        		
	        	switch (column) {
	        		case TABLE_ID_COLUMN:
	        		case OBJECT_COLUMN:
	        		case TYPE_COLUMN:
	        		case LEVEL_COLUMN:
	        		case MEA_COLUMN:
	        			break;
	        	}
        	}   	
        }

    }

	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.GTable#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		
	}
	
}

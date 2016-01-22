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

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
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
import org.gesis.charmstats.model.Question;
import org.gesis.charmstats.model.Study;
import org.gesis.charmstats.model.Variable;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1 / CharmStatsPro only
 *
 */
public class CompareStudyTable extends GTable implements TableModelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		
	public static final String BUNDLE				= "org.gesis.charmstats.resources.DesktopBundle";
	
	private static final String TABLE_ID			= "Table_ID";
	private static final String OBJECT				= "Object";
	private static final String POPULATION			= "csd_population";
	private static final String SELECTION			= "csd_selection";
	private static final String COLLECTION_METHOD	= "csd_compile_method";
	private static final String	COLLECTOR			= "csd_collector";
	public  static final String	FILTER				= "Filter";
	public  static final String FILLER				= "Filler";
	
	private static final int TABLE_ID_COLUMN		= 0;
	public static final int OBJECT_COLUMN			= 1;
	private static final int POPULATION_COLUMN		= 2;
	private static final int SELECTION_COLUMN		= 3;
	private static final int COMPILE_METHOD_COLUMN	= 4;
	private static final int COLLECTOR_COLUMN		= 5;
	public static final int FILTER_COLUMN			= 6; 
	public static final int FILLER_COLUMN			= 7; 


	private static final int VIEWPORT_WIDTH			= 600;
	private static final int VIEWPORT_HEIGHT		=  94; 
	private static final int ROW_HEIGHT				=  28; 
    
	/*
	 *	Fields
	 */
	Locale 		  		currentLocale;
	ResourceBundle		resourceBundle;
	Font				currentFont;
	
	JPanel				tablePanel;
	JTable				table;
	DefaultTableModel	studyTableModel;
	
	/*
	 *	Constructor
	 */
	/**
	 * @param locale
	 */
	CompareStudyTable (Locale locale) {
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
		
		table.getColumn(resourceBundle.getString(POPULATION)).setHeaderValue(bundle.getString(POPULATION));
		table.getColumn(resourceBundle.getString(SELECTION)).setHeaderValue(bundle.getString(SELECTION));
		table.getColumn(resourceBundle.getString(COLLECTION_METHOD)).setHeaderValue(bundle.getString(COLLECTION_METHOD));
		table.getColumn(resourceBundle.getString(COLLECTOR)).setHeaderValue(bundle.getString(COLLECTOR));
		
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
					    
	}
	
	/**
	 * @return
	 */
	DefaultTableModel buildBiasTableModel() {
		
		DefaultTableModel tableModel = new DefaultTableModel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

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
		tableModel.addColumn(resourceBundle.getString(POPULATION));
		tableModel.addColumn(resourceBundle.getString(SELECTION));
		tableModel.addColumn(resourceBundle.getString(COLLECTION_METHOD));
		tableModel.addColumn(resourceBundle.getString(COLLECTOR));
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
		
		Study			study		= ((Question)((Variable)comp.getSourceAttribute().getAttribute()).getSource()).getSource();
		String 			population	= study.getPopulation();
		String			selection	= study.getSelection();
		String			coll_method	= study.getCollectionMethod();
		String			collector	= study.getCollectors();
		
		this.studyTableModel.addRow(
			new Object[]{
				comp.getEntityID(), 
				comp, 				
				new JLabel(population),
				new JLabel(selection),
				new JLabel(coll_method),
				new JLabel(collector)
			}
		);
		
		if (!table.getAutoCreateRowSorter())
			table.setAutoCreateRowSorter(true);
	}
	
	/**
	 * 
	 */
	public void clear() {
		this.studyTableModel.setRowCount(0);
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
		
		studyTableModel = buildBiasTableModel();
        table = buildTable(studyTableModel);
		table.removeColumn(table.getColumn(TABLE_ID));
		table.removeColumn(table.getColumn(OBJECT));
		table.removeColumn(table.getColumn(FILTER));
		table.removeColumn(table.getColumn(FILLER));

        tablePanel = new JPanel();
        JScrollPane studyScrollPane = new JScrollPane(table);
        studyScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        tablePanel.add(studyScrollPane, BorderLayout.CENTER);
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
		
        int 		row		= e.getFirstRow();
        int 		column	= e.getColumn();
        
       
        if (row > -1 && column > -1) {
            Object data = model.getValueAt(row, column);
        	
        	if (data != null ) {
        		@SuppressWarnings("unused")
				AttributeComp comp = (AttributeComp)model.getValueAt(row, OBJECT_COLUMN);
        		
	        	switch (column) {
	        		case TABLE_ID_COLUMN:
	        		case OBJECT_COLUMN:
	        		case POPULATION_COLUMN:
	        		case SELECTION_COLUMN:
	        		case COMPILE_METHOD_COLUMN:
	        		case COLLECTOR_COLUMN:
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

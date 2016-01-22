package org.gesis.charmstats.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
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
import javax.swing.ScrollPaneConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.gesis.charmstats.IdentifiedParameter;
import org.gesis.charmstats.model.CStatsModel;
import org.gesis.charmstats.model.InstanceMap;
import org.gesis.charmstats.model.InstanceMapType;
import org.gesis.charmstats.model.InstanceType;
import org.gesis.charmstats.model.Project;
import org.gesis.charmstats.model.WorkStepInstance;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class WSInstanceMapTable extends GTable implements TableModelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String MAPPING_ID		= "Mapping ID";
	private static final String MAPPING_OBJ		= "Mapping Object";
	private static final String SOURCE_INSTANCE	= "source_instance";
	private static final String	TARGET_INSTANCE	= "target_instance";
	
	private static final String EMPTY_LABEL		= "";
	
	private static final int	VIEWPORT_WIDTH 	= 750;
	private static final int	VIEWPORT_HEIGHT = 288;					
	private static final int	ROW_HEIGHT		= 28; 
	
	/*
	 *	Fields
	 */
	JPanel				tablePanel;
	JTable				table;
	DefaultTableModel	tableModel;
	
	@SuppressWarnings("rawtypes")
	Vector				targetList;
	
	ResourceBundle 		resourceBundle;
	
	CStatsModel			model;
	
	InstanceType		source_type;
	InstanceType		target_type;
	
	Font				currentFont;
	
	/*
	 *	Constructor
	 */
	/**
	 * @param model
	 * @param source_type
	 * @param target_type
	 * @param resourceBundle
	 */
	@SuppressWarnings("rawtypes")
	WSInstanceMapTable (CStatsModel model, InstanceType source_type, InstanceType target_type, ResourceBundle resourceBundle) {
		setLayout(new BorderLayout());
		
		this.resourceBundle = resourceBundle;
		
		currentFont = this.getFont();
		
		this.model = model;
		
		this.source_type = source_type;
		this.target_type = target_type;
		
		targetList = new Vector();
		
		buildRowModel(model.getProject(), target_type);
		buildTableModel();
		buildTable(tableModel);
		buildTablePanel(table);
		
		add(tablePanel, BorderLayout.NORTH);
	}
	
	/*
	 *	Methods
	 */
	/**
	 * @param project
	 * @param target_type
	 */
	void buildRowModel(Project project, InstanceType target_type) {
		targetList = getTargetList(project, target_type);
	}
	
	/**
	 * @param project
	 * @param target_type
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Vector getTargetList(Project project, InstanceType target_type) {
		Vector targetList = new Vector();
				
		ArrayList<WorkStepInstance> instances =
				project.getContent().getLayers(target_type);
		
		targetList.add(EMPTY_LABEL);
		Iterator<WorkStepInstance> iterator = instances.iterator();	    
		while (iterator.hasNext()) {
			targetList.add(iterator.next());
		}
		
		return targetList;
	}
	
	/**
	 * 
	 */
	void buildTableModel() {
		
		tableModel = new DefaultTableModel(){
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

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
            
		tableModel.addColumn(MAPPING_ID);
		tableModel.addColumn(MAPPING_OBJ);
//		Old Order:
//		tableModel.addColumn(resourceBundle.getString(SOURCE_INSTANCE));
//		tableModel.addColumn(resourceBundle.getString(TARGET_INSTANCE));
//		New Order:
		tableModel.addColumn(resourceBundle.getString(TARGET_INSTANCE));
		tableModel.addColumn(resourceBundle.getString(SOURCE_INSTANCE));
	}
	
	/**
	 * @param tableModel
	 */
	void buildTable(DefaultTableModel tableModel) {
		
		table = new JTable(tableModel) {
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
//		        	if (this.getValueAt(row,col) != null)
//		        		jComponent.setToolTipText((String)this.getValueAt(row, col).toString());
		        	
		        	if (this.getValueAt(row,col) != null) {
			        	String toolTipText = null;
			        	
			        	if (this.getValueAt(row, col) instanceof JComboBox) {
			        		Object item = ((JComboBox)this.getValueAt(row, col)).getSelectedItem();
			        		
			        		if (item instanceof WorkStepInstance)
			        			toolTipText = ((WorkStepInstance)item).getLabel();
			        	} else {
			        		toolTipText = (String)this.getValueAt(row, col).toString();
			        	}
			        	
			        	jComponent.setToolTipText(toolTipText);
		        	}
		        	
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
        
        table.setDefaultRenderer(JComponent.class, new JComponentCellRenderer());
		table.setDefaultEditor(JComponent.class, new JComponentCellEditor());
	    
		table.getModel().addTableModelListener(this);
		table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		
		table.removeColumn(table.getColumn(MAPPING_ID));
		table.removeColumn(table.getColumn(MAPPING_OBJ));
		table.setName("selectTable");
	}
	
	/**
	 * @param table
	 */
	void buildTablePanel(JTable table) {	
        tablePanel = new JPanel();
        JScrollPane tabScrollPane = new JScrollPane(table);
        tabScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        tabScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        tablePanel.add(tabScrollPane, BorderLayout.CENTER);   
	}
	
	
	/**
	 * @param project
	 * @param map_type
	 */
	public void addRows(Project project, InstanceMapType map_type) {
		ArrayList<InstanceMap> instance_maps =
				project.getContent().getInstanceMapsByType(map_type);		
		
		Iterator<InstanceMap> iterator = instance_maps.iterator();	    
		while (iterator.hasNext()) {
			InstanceMap instance_map = iterator.next();			
			
			addRow(instance_map);
		}
		
		table.setRowHeight(ROW_HEIGHT);
		table.setRowMargin(2);
	}
	
	/**
	 * @param instance_map
	 */
	public void addRow(InstanceMap instance_map) {
		targetList = getTargetList(model.getProject(), target_type);
		JComboBox targetCB = new JComboBox(targetList);
		targetCB.setFont(currentFont);
		//AutoCompleteDecorator.decorate(targetCB);
		//targetCB.isEditable();

		if (instance_map.getTargetInstance() != null) {
			targetCB.setSelectedItem(instance_map.getTargetInstance().getInstance());
		}			
				
		tableModel.addRow(
				new Object[]{
						instance_map.getEntityID(),
						instance_map, 
//						Old Order:
//						instance_map.getSourceInstance().getInstance(), 
//						targetCB});
//						New Order:
						targetCB,
						instance_map.getSourceInstance().getInstance()});
	}
		
	/**
	 * 
	 */
	public void clear() {
		this.tableModel.setRowCount(0);
	}
	
	class JComponentCellRenderer implements TableCellRenderer
	{
	    public Component getTableCellRendererComponent(JTable table, Object value,
	    		boolean isSelected, boolean hasFocus, int row, int column) {
	        return (JComponent)value;
	    }
	}
	
	/**
	 * @param locale
	 */
	public void changeLanguage(Locale locale) {	
		ResourceBundle bundle = ResourceBundle.getBundle("org.gesis.charmstats.resources.DesktopBundle", locale);
		
		table.getColumn(resourceBundle.getString(SOURCE_INSTANCE)).setHeaderValue(bundle.getString(SOURCE_INSTANCE));
		table.getColumn(resourceBundle.getString(TARGET_INSTANCE)).setHeaderValue(bundle.getString(TARGET_INSTANCE));
		
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
		
	/* (non-Javadoc)
	 * @see javax.swing.event.TableModelListener#tableChanged(javax.swing.event.TableModelEvent)
	 */
	@Override
	public void tableChanged(TableModelEvent e) {

		/* TODO */
	}

	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.GTable#update(java.util.Observable, java.lang.Object)
	 */
	@SuppressWarnings("unused")
	@Override
	public void update(Observable o, Object arg) {
		IdentifiedParameter parameter = (IdentifiedParameter)arg;
		
		/* TODO */				
	}

	/**
	 * @param enabled
	 */
	public void setTableEnabled(boolean enabled) {
		table.setEnabled(enabled);
	}
}

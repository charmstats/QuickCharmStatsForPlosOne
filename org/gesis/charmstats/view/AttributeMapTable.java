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
import org.gesis.charmstats.model.AttributeLink;
import org.gesis.charmstats.model.AttributeLinkType;
import org.gesis.charmstats.model.AttributeMap;
import org.gesis.charmstats.model.AttributeMapType;
import org.gesis.charmstats.model.Attributes;
import org.gesis.charmstats.model.CStatsModel;
import org.gesis.charmstats.model.ConDimension;
import org.gesis.charmstats.model.Measurement;
import org.gesis.charmstats.model.OperaIndicator;
import org.gesis.charmstats.model.Project;
import org.gesis.charmstats.model.Variable;
import org.gesis.charmstats.model.WorkStepInstance;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class AttributeMapTable  extends GTable implements TableModelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID 		= 1L;
	
	private static final String MAPPING_ID			= "Mapping ID";
	private static final String MAPPING_OBJ			= "Mapping Object";
	private static final String SOURCE_ATTRIBUTE	= "source_attribute";
	private static final String	TARGET_ATTRIBUTE	= "target_attribute";
	private static final String	FILTER				= "Filter";
	private static final String FILLER				= "Filler";
	
	private static final String EMPTY_LABEL			= "";
	
	private static final int	VIEWPORT_WIDTH 		= 750;
	private static final int	VIEWPORT_HEIGHT 	= 288;	// row plus header
	private static final int	ROW_HEIGHT			= 28; 
	
	/*
	 *	Fields
	 */
	JPanel				tablePanel;
	JTable				table;
	DefaultTableModel	tableModel;
	
	JComboBox			targetSelector;
	@SuppressWarnings("rawtypes")
	Vector				targetList;
	WorkStepInstance	targetObject;
	
	ResourceBundle 		resourceBundle;
	
	CStatsModel			model;
	
	AttributeMapType	source_type;
	AttributeMapType	target_type;
	
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
	AttributeMapTable (CStatsModel model, Attributes source_type, Attributes target_type, ResourceBundle resourceBundle) {
		setLayout(new BorderLayout());
		
		this.resourceBundle = resourceBundle;
		this.model = model;
		
		targetList = new Vector();
		
		currentFont		= this.getFont();
		
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
	void buildRowModel(Project project, Attributes target_type) {

	}
	
	/**
	 * @param project
	 * @param target_instance
	 * @param map_type
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Vector getTargetList(Project project, WorkStepInstance target_instance, AttributeMapType map_type) {
		Vector targetList = new Vector();

		Attributes attribute_type = null;
		AttributeLinkType link_type = null;
		if (map_type.equals(AttributeMapType.SPECIFICATION)) {
			attribute_type = new Measurement();
			link_type = AttributeLinkType.MEASUREMENT;
		}
		if (map_type.equals(AttributeMapType.ASSIGNED_INDICATOR)) {
			attribute_type = new ConDimension();
			link_type = AttributeLinkType.DIMENSION;
		}
		if (map_type.equals(AttributeMapType.ASSIGNED_VARIABLE)) {
			attribute_type = new OperaIndicator();
			link_type = AttributeLinkType.INDICATOR;
		}

		Iterator<AttributeLink> link_iter = project.getContent().getLinksByLayer(target_instance).iterator();
		ArrayList<Attributes> attributes = new ArrayList<Attributes>();
		while (link_iter.hasNext()) {
			AttributeLink link = link_iter.next();

			if (link.getAttribute().getClass().equals(attribute_type.getClass())) {
				if (link_type.equals(link.getAttributeLinkType()))
					attributes.add(link.getAttribute());
			}
		}

		Iterator<Attributes> iterator = attributes.iterator();
		targetList.add(EMPTY_LABEL);
		while (iterator.hasNext()) {
			Attributes attribute = iterator.next();

			targetList.add(attribute);
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
		tableModel.addColumn(resourceBundle.getString(TARGET_ATTRIBUTE));
		tableModel.addColumn(resourceBundle.getString(SOURCE_ATTRIBUTE));
		tableModel.addColumn(FILTER);
		tableModel.addColumn(FILLER);
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
		        	if (this.getValueAt(row,col) != null) {
			        	String toolTipText = null;
			        	
			        	if (this.getValueAt(row, col) instanceof JComboBox) {
			        		Object item = ((JComboBox)this.getValueAt(row, col)).getSelectedItem();
			        		
			        		if (item instanceof Measurement)
			        			toolTipText = ((Measurement)item).getName();
			        		if (item instanceof ConDimension)
			        			toolTipText = ((ConDimension)item).getLabel();
			        		if (item instanceof OperaIndicator)
			        			toolTipText = ((OperaIndicator)item).getLabel();
			        		if (item instanceof Variable)
			        			toolTipText = ((Variable)item).getName() +": "+ ((Variable)item).getLabel();   
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
		table.removeColumn(table.getColumn(FILTER));
		table.removeColumn(table.getColumn(FILLER));
		
		table.setName("mapTable");
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
	public void addRows(Project project, AttributeMapType map_type) {
		ArrayList<AttributeMap> attribute_maps =
				project.getContent().getMapsByType(map_type);
		
		Iterator<AttributeMap> iterator = attribute_maps.iterator();	    
		while (iterator.hasNext()) {
			AttributeMap attribute_map = iterator.next();
			
			if ((attribute_map.getBelongsTo() != null) && 
					(attribute_map.getBelongsTo().getTargetInstance() != null))
				addRow(attribute_map);
		}
		
		table.setRowHeight(ROW_HEIGHT);
		table.setRowMargin(2);
	}
	
	/**
	 * @param attribute_map
	 */
	public void addRow(AttributeMap attribute_map) {		
		targetList = getTargetList(model.getProject(), attribute_map.getBelongsTo().getTargetInstance().getInstance(), attribute_map.getAttributeMapType());
		JComboBox targetCB = new JComboBox(targetList);
		targetCB.setFont(currentFont);
		
		if ((attribute_map.getTargetAttribute() != null) &&
				(attribute_map.getTargetAttribute().getAttribute() != null)) {
			targetCB.setSelectedItem(attribute_map.getTargetAttribute().getAttribute());
		}			
		
		tableModel.addRow(
				new Object[]{
						attribute_map.getEntityID(),
						attribute_map,
						targetCB,
						attribute_map.getSourceAttribute().getAttribute()						
						}
				);
	}
		
	/**
	 * @param locale
	 */
	public void changeLanguage(Locale locale) {
		
		ResourceBundle bundle = ResourceBundle.getBundle("org.gesis.charmstats.resources.DesktopBundle", locale);	
		table.getColumn(resourceBundle.getString(SOURCE_ATTRIBUTE)).setHeaderValue(bundle.getString(SOURCE_ATTRIBUTE));
		table.getColumn(resourceBundle.getString(TARGET_ATTRIBUTE)).setHeaderValue(bundle.getString(TARGET_ATTRIBUTE));
		
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
	public void clear() {
		this.tableModel.setRowCount(0);
	}
	
	/**
	 * @param enabled
	 */
	public void setTableEnabled(boolean enabled) {
		table.setEnabled(enabled);
	}
	
	class JComponentCellRenderer implements TableCellRenderer
	{
	    /* (non-Javadoc)
	     * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
	     */
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

	}

	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.GTable#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		@SuppressWarnings("unused")
		IdentifiedParameter parameter = (IdentifiedParameter)arg;
		
	}

}

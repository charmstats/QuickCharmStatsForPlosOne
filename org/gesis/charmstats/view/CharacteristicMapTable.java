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
import org.gesis.charmstats.model.AttributeMap;
import org.gesis.charmstats.model.Attributes;
import org.gesis.charmstats.model.CStatsModel;
import org.gesis.charmstats.model.Category;
import org.gesis.charmstats.model.CharacteristicLink;
import org.gesis.charmstats.model.CharacteristicMap;
import org.gesis.charmstats.model.CharacteristicMapType;
import org.gesis.charmstats.model.Characteristics;
import org.gesis.charmstats.model.ConSpecification;
import org.gesis.charmstats.model.OperaPrescription;
import org.gesis.charmstats.model.Project;
import org.gesis.charmstats.model.Value;
import org.gesis.charmstats.model.WorkStepInstance;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class CharacteristicMapTable   extends GTable implements TableModelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String MAPPING_ID		= "Mapping ID";
	private static final String MAPPING_OBJ		= "Mapping Object";
	private static final String SOURCE_CHAR		= "source_char";
	private static final String	TARGET_CHAR		= "target_char";
	private static final String	FILTER			= "Filter";
	private static final String FILLER			= "Filler";
	
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
	
	JComboBox			targetSelector;
	@SuppressWarnings("rawtypes")
	Vector				targetList;
	WorkStepInstance	targetObject;
	
	ResourceBundle 		resourceBundle;
	
	CStatsModel			model;
	
	CharacteristicMapType	source_type;
	CharacteristicMapType	target_type;
	
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
	CharacteristicMapTable (CStatsModel model, Characteristics source_type, Characteristics target_type,  ResourceBundle resourceBundle) {
		setLayout(new BorderLayout());
		
		this.resourceBundle = resourceBundle;
		this.model = model;
		
		currentFont = this.getFont();
		
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
	@SuppressWarnings("unchecked")
	void buildRowModel(Project project, Characteristics target_type) {
		targetList.add(EMPTY_LABEL);
		
		ArrayList<Characteristics> characteristics = new ArrayList<Characteristics>();		
		if (target_type.getClass().equals(ConSpecification.class)) {
			Iterator<ConSpecification> iter_spe =  project.getContent().getSpecifications().iterator();
			while (iter_spe.hasNext())
				characteristics.add(iter_spe.next());
		}
		if (target_type.getClass().equals(OperaPrescription.class)) {
			Iterator<OperaPrescription> iter_pre =  project.getContent().getPrescriptions().iterator();
			while (iter_pre.hasNext())
				characteristics.add(iter_pre.next());
		}
		if (target_type.getClass().equals(Value.class)) {
			Iterator<Value> iter_val =  project.getContent().getValues().iterator();
			while (iter_val.hasNext())
				characteristics.add(iter_val.next());
		}
		
		Iterator<Characteristics> iterator = characteristics.iterator();	    
		while (iterator.hasNext()) {
			Characteristics characteristic = iterator.next();			
			
			targetList.add(characteristic);
		}
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
		tableModel.addColumn(resourceBundle.getString(TARGET_CHAR));
		tableModel.addColumn(resourceBundle.getString(SOURCE_CHAR));
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
			        		
			        		if (item instanceof Category)
			        			toolTipText = ((Category)item).getCode() +": "+ ((Category)item).getLabel();
			        		if (item instanceof ConSpecification)
			        			toolTipText = ((ConSpecification)item).getLabel();
			        		if (item instanceof OperaPrescription)
			        			toolTipText = ((OperaPrescription)item).getValue() +": "+ ((OperaPrescription)item).getLabel();
			        		if (item instanceof Value)
			        			toolTipText = ((Value)item).getValue() +": "+ ((Value)item).getLabel();	   
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
		
		table.setName("recodeTable");
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
	public void addRows(Project project, CharacteristicMapType map_type) {
		ArrayList<CharacteristicMap> characteristic_maps =
				project.getContent().getCharacteristicMapsByType(map_type);
		
		Iterator<CharacteristicMap> iterator = characteristic_maps.iterator();	    
		while (iterator.hasNext()) {
			CharacteristicMap characteristic_map = iterator.next();			
			
			if (characteristic_map.getBelongsTo() instanceof AttributeMap) {		
				if ((characteristic_map.getBelongsTo() != null) && 
						(characteristic_map.getBelongsTo().getTargetAttribute() != null))
					addRow(characteristic_map);
			}
		}
		
		table.setRowHeight(ROW_HEIGHT);
		table.setRowMargin(2);
	}
	
	/**
	 * @param characteristic_map
	 */
	public void addRow(CharacteristicMap characteristic_map) {
		targetList = getTargetList(model.getProject(), characteristic_map.getBelongsTo().getTargetAttribute().getAttribute(), characteristic_map.getType());
		JComboBox targetCB = new JComboBox(targetList);
		targetCB.setFont(currentFont);
				
		if ((characteristic_map.getTargetCharacteristic() != null) &&
				(characteristic_map.getTargetCharacteristic().getCharacteristic() != null)) {
			targetCB.setSelectedItem(characteristic_map.getTargetCharacteristic().getCharacteristic());
		}			
		
		tableModel.addRow(
				new Object[]{
						characteristic_map.getEntityID(),
						characteristic_map,
						targetCB,
						characteristic_map.getSourceCharacteristic().getCharacteristic()						
				});
	}
	
	/**
	 * @param project
	 * @param target_attribute
	 * @param map_type
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Vector getTargetList(Project project, Attributes target_attribute, CharacteristicMapType map_type) {
		Vector targetList = new Vector();
		
		ArrayList<Characteristics> characteristics = new ArrayList<Characteristics>();	
		Characteristics characteristic_type = null;
		if (map_type.equals(CharacteristicMapType.SPECIFICATION))
			characteristic_type = new Category();
		if (map_type.equals(CharacteristicMapType.PRESCRIPTION))
			characteristic_type = new ConSpecification();
		if (map_type.equals(CharacteristicMapType.VALUE))
			characteristic_type = new OperaPrescription();
		
		targetList.add(EMPTY_LABEL);

		Iterator<CharacteristicLink> link_iter = project.getContent().getCharacteristicLinksByAttribute(target_attribute).iterator();
		while(link_iter.hasNext()) {
			CharacteristicLink link = link_iter.next();
								
			if (link.getCharacteristic().getClass().equals(characteristic_type.getClass()))
				characteristics.add(link.getCharacteristic());
		}
		
		
		Iterator<Characteristics> iterator = characteristics.iterator();	    
		while (iterator.hasNext()) {
			Characteristics characteristic = iterator.next();			
			
			targetList.add(characteristic);
		}
		
		return targetList;
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
	
	/**
	 * @param locale
	 */
	public void changeLanguage(Locale locale) {
		
		ResourceBundle bundle = ResourceBundle.getBundle("org.gesis.charmstats.resources.DesktopBundle", locale);	
		table.getColumn(resourceBundle.getString(SOURCE_CHAR)).setHeaderValue(bundle.getString(SOURCE_CHAR));
		table.getColumn(resourceBundle.getString(TARGET_CHAR)).setHeaderValue(bundle.getString(TARGET_CHAR));
		
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

package org.gesis.charmstats.view;

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
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;
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
import javax.swing.table.TableModel;

import org.gesis.charmstats.ActionCommandText;
import org.gesis.charmstats.model.CStatsModel;
import org.gesis.charmstats.model.ConDimension;
import org.gesis.charmstats.model.TypeOfData;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1 / CharmStatsPro only
 *
 */
public class TabDimension extends Tab implements TableModelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String DIMENSION_ID	= "Dimension_ID";
	private static final String OBJECT			= "Object";
	private static final String NAME			= "name_col";
	private static final String LEVEL			= "level_col";
	private static final String DEFINITION		= "definition_col";
	
	private static final int DIMENSION_ID_COLUMN 	= 0;
	private static final int OBJECT_COLUMN			= 1;
	private static final int NAME_COLUMN			= 2;
	public static final int LEVEL_COLUMN			= 4;
	public static final int DEFINITION_COLUMN		= 3;
	
	private static final int VIEWPORT_WIDTH		= 600;
	private static final int VIEWPORT_HEIGHT	=  94; 
	private static final int ROW_HEIGHT			=  28; 
	
	private String 				ADD					= "add_btn";
	private String 				REMOVE				= "remove_btn";
    
	JPanel				dimensionTablePanel;
	JTable				dimensionTable;
	DefaultTableModel	dimensionTableModel;
	Vector<TypeOfData>	levelLabels;
	TypeOfData	level;
	JList				specList;
	Vector<Object> 				specLabels;
		
	JPanel				dimensionButtonPnl;
	JButton				addDimensionBtn;
	JButton				remDimensionBtn;
	
	Font				currentFont;
		
	/*
	 *	Constructor
	 */
	/**
	 * @param locale
	 */
	public TabDimension(Locale locale) {
		super(locale);
		
		setName("TabDimension");
	}
	
	/**
	 * @param al
	 * @param locale
	 */
	public TabDimension(ActionListener al, Locale locale) {
		this(locale);
		
		currentLocale	= locale;
		resourceBundle	= ResourceBundle.getBundle(BUNDLE, currentLocale);
		currentFont		= this.getFont();
		
		/* Create Form Components */
		buildDimensionTablePanel();
		
		/* Add Form Components to Form Panel */
		formPanel.add(dimensionTablePanel);
		
		/* Setup Buttons */
		backButton.setActionCommand(ActionCommandText.BTN_CON_STP_DIM_TAB_BACK);
		backButton.addActionListener(al);
		noteButton.setActionCommand(ActionCommandText.BTN_CON_STP_DIM_TAB_NOTE);
		noteButton.addActionListener(al);
		nextButton.setActionCommand(ActionCommandText.BTN_CON_STP_DIM_TAB_NEXT);
		nextButton.addActionListener(al);
		
		setPanelIdx(2); // 1
		
		changeLanguage(locale);
	}

	/*
	 * Method(s)
	 */
	/**
	 * @return
	 */
	public JButton getBackButton() {
		return backButton;
	}
	
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.Tab#changeLanguage(java.util.Locale)
	 */
	public void changeLanguage(Locale locale) {
		super.changeLanguage(locale);
		
		ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, locale);
		
		for (int i=0; i<3; i++)
			dimensionTable.getTableHeader().getColumnModel().getColumn(i).setHeaderValue(
				bundle.getString((String)dimensionTable.getTableHeader().getColumnModel().getColumn(i).getIdentifier()));
		addDimensionBtn.setText(bundle.getString(ADD));
		remDimensionBtn.setText(bundle.getString(REMOVE));
		
		repaint(); 
	}
	
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.Tab#changeFont(java.awt.Font)
	 */
	public void changeFont(Font f) {
		super.changeFont(f);
		
		addDimensionBtn.setFont(f);
		remDimensionBtn.setFont(f);

		dimensionTable.getTableHeader().setFont(f);
		dimensionTable.setFont(f);
	
		currentFont = f;
		
		for (int column=0; column<dimensionTable.getModel().getColumnCount(); column++) 
			for (int row=0; row<dimensionTable.getModel().getRowCount(); row++) {
				Object o = dimensionTable.getModel().getValueAt(row, column);
				
				if (o instanceof JComboBox)
					((JComboBox)o).setFont(f);
			}
	}

	/**
	 * 
	 */
	void buildDimensionTablePanel() {
		
		buildDimensionRowModel();
		
		dimensionTableModel	= buildDimensionTableModel();
		
        dimensionTable		= buildDimensionTable(dimensionTableModel);
        dimensionTable.getColumnModel().getColumn(NAME_COLUMN).setIdentifier(NAME);
        dimensionTable.getColumnModel().getColumn(DEFINITION_COLUMN).setIdentifier(DEFINITION);
        dimensionTable.getColumnModel().getColumn(LEVEL_COLUMN).setIdentifier(LEVEL);
		dimensionTable.removeColumn(dimensionTable.getColumn(DIMENSION_ID));
		dimensionTable.removeColumn(dimensionTable.getColumn(OBJECT));
		dimensionTable.setName("dimDimensionTbl");
		
		addDimensionBtn		= new JButton(resourceBundle.getString(ADD));
		addDimensionBtn.addActionListener( 
				new ActionListener() {
					/* (non-Javadoc)
					 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
					 */
					public void actionPerformed(ActionEvent e) {
						addDimensionBtnAction(e);
					}
				}
		);
		remDimensionBtn		= new JButton(resourceBundle.getString(REMOVE));
		remDimensionBtn.addActionListener(
				new ActionListener() {
					/* (non-Javadoc)
					 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
					 */
					public void actionPerformed(ActionEvent e) {
						remDimensionBtnAction(e);
					}
				}
		);
		
		dimensionButtonPnl	= new JPanel(new FlowLayout(FlowLayout.TRAILING));
		dimensionButtonPnl.add(addDimensionBtn);
		dimensionButtonPnl.add(remDimensionBtn);

        dimensionTablePanel = new JPanel();
        dimensionTablePanel.setLayout(new BoxLayout(dimensionTablePanel, BoxLayout.Y_AXIS));
        JScrollPane dimScrollPane = new JScrollPane(dimensionTable);
        dimScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        dimScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        dimensionTablePanel.add(dimScrollPane);
        dimensionTablePanel.add(dimensionButtonPnl);
	}
	
	/**
	 * @param e
	 */
	protected void addDimensionBtnAction(ActionEvent e) {
		this.addDimensionRow(new ConDimension());
	}
	
	/**
	 * @param dim
	 */
	public void addDimensionRow(ConDimension dim) {		
		JComboBox level = new GComboBox(this.levelLabels);
		//AutoCompleteDecorator.decorate(level);
		//level.isEditable();
		level.setBorder(BorderFactory.createEmptyBorder());
		if (dim.getLevel() != null) {
			level.setSelectedItem(dim.getLevel());
			level.setFont(currentFont);
		} else {
			level.setSelectedItem(TypeOfData.NONE);
			level.setFont(currentFont);
			dim.setLevel((TypeOfData)level.getSelectedItem());
		}
						
		this.dimensionTableModel.addRow(
			new Object[]{
				dim.getEntityID(),
				dim,
				dim.getLabel(),				
				dim.getDefinition().getText(),
				level,
			}
		);
		
		if (!dimensionTable.getAutoCreateRowSorter())
			dimensionTable.setAutoCreateRowSorter(true);
	}
	
	/**
	 * @param e
	 */
	protected void remDimensionBtnAction(ActionEvent e) {
		int[] selectedRows = dimensionTable.getSelectedRows();
		
		for (int index=selectedRows.length-1; index >= 0; index--) {
			int selectedRow = dimensionTable.convertRowIndexToModel(selectedRows[index]);
			dimensionTableModel.removeRow(selectedRow);
		}
	}
	
	/**
	 * 
	 */
	void buildDimensionRowModel() {
	    
	    levelLabels = new Vector<TypeOfData>();
		for (TypeOfData measurementLevel : TypeOfData.values()) {
			measurementLevel.setLocale(currentLocale);
			
			levelLabels.add(measurementLevel);
		}
	    
	    specLabels = new Vector<Object>();
	}
		
	/**
	 * @return
	 */
	DefaultTableModel buildDimensionTableModel() {
		
		DefaultTableModel tableModel = new DefaultTableModel() {
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
            
		tableModel.addColumn(DIMENSION_ID);
		tableModel.addColumn(OBJECT);
		tableModel.addColumn(resourceBundle.getString(NAME));
		tableModel.addColumn(resourceBundle.getString(DEFINITION));
        tableModel.addColumn(resourceBundle.getString(LEVEL));
        
        return tableModel;
	}
		
	/**
	 * @param tableModel
	 * @return
	 */
	JTable buildDimensionTable(DefaultTableModel tableModel) {
		
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
        		ConDimension dimension = (ConDimension)model.getValueAt(row, OBJECT_COLUMN);
        		
	        	switch (column) {
	        		case DIMENSION_ID_COLUMN:
	        		case OBJECT_COLUMN:
	        			break;
	        		case NAME_COLUMN:
	        			break;
	        		case LEVEL_COLUMN:
	        			if (((JComboBox) data).getSelectedItem() instanceof TypeOfData)
	        				dimension.setLevel((TypeOfData)((JComboBox) data).getSelectedItem());
	        			break;
	        		case DEFINITION_COLUMN:
	        			dimension.getDefinition().setText((String) data);
	        			break;
	        	}
        	}   	
        }

    }
	
	class JComponentCellRenderer implements TableCellRenderer {
		
	    public Component getTableCellRendererComponent(JTable table, Object value,
	    		boolean isSelected, boolean hasFocus, int row, int column) {
	        return (JComponent)value;
	    }
	}
	
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.Tab#fillModel(org.gesis.charmstats.model.CStatsModel)
	 */
	public void fillModel(CStatsModel model) {
		dimensionTableModel.setRowCount(0);
		
		ArrayList<ConDimension> dimensions = model.getProject().getContent().getDimensions();
		Iterator<ConDimension> iterator = dimensions.iterator();
		while(iterator.hasNext()) {
			ConDimension dim = iterator.next();
			
			addDimensionRow(dim);
		}
		
		if ((model.getProject().getFinishedSince() != null) ||	
				(!model.getProject().isEditedByUser())) {		
			dimensionTable.setEnabled(false);
			addDimensionBtn.setEnabled(false);
			remDimensionBtn.setEnabled(false);
		} else {
			dimensionTable.setEnabled(true);
			addDimensionBtn.setEnabled(true);
			remDimensionBtn.setEnabled(true);
		}
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
			
			model.getProject().getContent().setDimensions(new ArrayList<ConDimension>());
		}
		
		fillModel(model);		
	}

}

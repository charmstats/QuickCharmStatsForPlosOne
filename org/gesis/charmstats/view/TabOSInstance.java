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

import javax.swing.BoxLayout;
import javax.swing.JButton;
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
import javax.swing.table.TableModel;

import org.gesis.charmstats.ActionCommandText;
import org.gesis.charmstats.model.CStatsModel;
import org.gesis.charmstats.model.InstanceType;
import org.gesis.charmstats.model.WorkStepInstance;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1 / CharmStatsPro only
 *
 */
public class TabOSInstance extends Tab implements TableModelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String OS_INSTANCE_ID	= "OSInstance_ID";
	private static final String OBJECT			= "Object";
	private static final String NAME			= "name_col";
	
	private static final int OS_INSTANCE_ID_COLUMN 	= 0;
	private static final int OBJECT_COLUMN			= 1;
	private static final int NAME_COLUMN			= 2;
	
	private static final int VIEWPORT_WIDTH		= 600;
	private static final int VIEWPORT_HEIGHT	=  94; 
	private static final int ROW_HEIGHT			=  28; 
	
	private String 				ADD					= "add_btn";
	private String 				REMOVE				= "remove_btn";
    
	JPanel				osInstanceTablePanel;
	JTable				osInstanceTable;
	DefaultTableModel	osInstanceTableModel;
		
	JPanel				osInstanceButtonPnl;
	JButton				addOSInstanceBtn;
	JButton				remOSInstanceBtn;
	
	Font				currentFont;
	
	/*
	 *	Constructor
	 */
	/**
	 * @param locale
	 */
	public TabOSInstance(Locale locale) {
		super(locale);
		
		setName("TabOSInstance");
	}
	
	/**
	 * @param al
	 * @param locale
	 */
	public TabOSInstance(ActionListener al, Locale locale) {
		this(locale);
		
		currentLocale	= locale;
		resourceBundle	= ResourceBundle.getBundle(BUNDLE, currentLocale);
		currentFont		= this.getFont();
		
		/* Create Form Components */
		buildOSInstanceTablePanel();
		
		/* Add Form Components to Form Panel */
		formPanel.add(osInstanceTablePanel);
		
		/* Setup Buttons */
		backButton.setActionCommand(ActionCommandText.BTN_OPE_STP_INS_TAB_BACK);
		backButton.addActionListener(al);
		noteButton.setActionCommand(ActionCommandText.BTN_OPE_STP_INS_TAB_NOTE);
		noteButton.addActionListener(al);
		nextButton.setActionCommand(ActionCommandText.BTN_OPE_STP_INS_TAB_NEXT);
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
		osInstanceTable.getTableHeader().getColumnModel().getColumn(0).setHeaderValue(
				bundle.getString((String)osInstanceTable.getTableHeader().getColumnModel().getColumn(0).getIdentifier()));
		addOSInstanceBtn.setText(bundle.getString(ADD));
		remOSInstanceBtn.setText(bundle.getString(REMOVE));
	}
	
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.Tab#changeFont(java.awt.Font)
	 */
	public void changeFont(Font f) {
		super.changeFont(f);
		
		addOSInstanceBtn.setFont(f);
		remOSInstanceBtn.setFont(f);

		currentFont = f;
		
		osInstanceTable.getTableHeader().setFont(f);
		osInstanceTable.setFont(f);
		
		for (int column=0; column<osInstanceTable.getModel().getColumnCount(); column++) 
			for (int row=0; row<osInstanceTable.getModel().getRowCount(); row++) {
				Object o = osInstanceTable.getModel().getValueAt(row, column);
				
				if (o instanceof JComboBox)
					((JComboBox)o).setFont(f);
			}
	}
	
	/**
	 * 
	 */
	void buildOSInstanceTablePanel() {
		
		buildOSInstanceRowModel();
		
		osInstanceTableModel = buildOSInstanceTableModel();
        osInstanceTable = buildOSInstanceTable(osInstanceTableModel);
        osInstanceTable.getColumnModel().getColumn(NAME_COLUMN).setIdentifier(NAME);
		osInstanceTable.removeColumn(osInstanceTable.getColumn(OS_INSTANCE_ID));
		osInstanceTable.removeColumn(osInstanceTable.getColumn(OBJECT));
		osInstanceTable.setName("osiInstanceTbl");
		
		addOSInstanceBtn		= new JButton(resourceBundle.getString(ADD));
		addOSInstanceBtn.addActionListener( 
				new ActionListener() {
					/* (non-Javadoc)
					 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
					 */
					public void actionPerformed(ActionEvent e) {
						addOSInstanceBtnAction(e);
					}
				}
		);
		remOSInstanceBtn		= new JButton(resourceBundle.getString(REMOVE));
		remOSInstanceBtn.addActionListener(
				new ActionListener() {
					/* (non-Javadoc)
					 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
					 */
					public void actionPerformed(ActionEvent e) {
						remOSInstanceBtnAction(e);
					}
				}
		);
		
		osInstanceButtonPnl	= new JPanel(new FlowLayout(FlowLayout.TRAILING));
		osInstanceButtonPnl.add(addOSInstanceBtn);
		osInstanceButtonPnl.add(remOSInstanceBtn);

        osInstanceTablePanel = new JPanel();
        osInstanceTablePanel.setLayout(new BoxLayout(osInstanceTablePanel, BoxLayout.Y_AXIS));
        JScrollPane osiScrollPane = new JScrollPane(osInstanceTable);
        osiScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        osiScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        osInstanceTablePanel.add(osiScrollPane);
        osInstanceTablePanel.add(osInstanceButtonPnl);
	}
	
	/**
	 * @param e
	 */
	protected void addOSInstanceBtnAction(ActionEvent e) {
		this.addOSInstanceRow(new WorkStepInstance());
	}
	
	/**
	 * @param ins
	 */
	public void addOSInstanceRow(WorkStepInstance ins) {		
		
		ins.setType(InstanceType.OPERATIONAL);
		
		this.osInstanceTableModel.addRow(
			new Object[]{
				ins.getEntityID(),
				ins,
				ins.getLabel(),				
			}
		);
		
		if (!osInstanceTable.getAutoCreateRowSorter())
			osInstanceTable.setAutoCreateRowSorter(true);
	}
	
	/**
	 * @param e
	 */
	protected void remOSInstanceBtnAction(ActionEvent e) {
		int[] selectedRows = osInstanceTable.getSelectedRows();
		
		for (int index=selectedRows.length-1; index >= 0; index--) {
			int selectedRow = osInstanceTable.convertRowIndexToModel(selectedRows[index]);
			osInstanceTableModel.removeRow(selectedRow);
		}
	}
	
	/**
	 * 
	 */
	void buildOSInstanceRowModel() { }
		
	/**
	 * @return
	 */
	DefaultTableModel buildOSInstanceTableModel() {
		
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
            
		tableModel.addColumn(OS_INSTANCE_ID);
		tableModel.addColumn(OBJECT);
		tableModel.addColumn(resourceBundle.getString(NAME));
        
        return tableModel;
	}
		
	/**
	 * @param tableModel
	 * @return
	 */
	JTable buildOSInstanceTable(DefaultTableModel tableModel) {
		
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
        int 		row		= e.getFirstRow();
        int 		column	= e.getColumn();
        
        if (row > -1 && column > -1) {
            Object data = model.getValueAt(row, column);
        	
        	if (data != null ) {
        		@SuppressWarnings("unused")
				WorkStepInstance instance = (WorkStepInstance)model.getValueAt(row, OBJECT_COLUMN);
        		
	        	switch (column) {
	        		case OS_INSTANCE_ID_COLUMN:
	        		case OBJECT_COLUMN:
	        			break;
	        		case NAME_COLUMN:
	        			break;
	        		default:
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
		osInstanceTableModel.setRowCount(0);
		
		ArrayList<WorkStepInstance> instances = model.getProject().getContent().getLayers(InstanceType.OPERATIONAL);
		Iterator<WorkStepInstance> iterator = instances.iterator();
		while(iterator.hasNext()) {
			WorkStepInstance inst = iterator.next();
			
			addOSInstanceRow(inst);
		}
		
		if ((model.getProject().getFinishedSince() != null) ||	
				(!model.getProject().isEditedByUser())) {		
			osInstanceTable.setEnabled(false);
			addOSInstanceBtn.setEnabled(false);
			remOSInstanceBtn.setEnabled(false);
		} else {
			osInstanceTable.setEnabled(true); 
			addOSInstanceBtn.setEnabled(true); 
			remOSInstanceBtn.setEnabled(true); 
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
			
			model.getProject().getContent().setLayers(new ArrayList<WorkStepInstance>());
		}
		
		fillModel(model);		
	}
}

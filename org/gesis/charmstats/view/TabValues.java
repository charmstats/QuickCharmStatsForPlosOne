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

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
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
import org.gesis.charmstats.model.Attributes;
import org.gesis.charmstats.model.CStatsModel;
import org.gesis.charmstats.model.CharacteristicLink;
import org.gesis.charmstats.model.Value;
import org.gesis.charmstats.model.Variable;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class TabValues extends Tab implements TableModelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String VALUE_ID				= "Value_ID";
	private static final String OBJECT					= "Object";
	private static final String VALUE					= "value_col";
	private static final String LABEL					= "label_col";
	private static final String VARIABLE				= "variable_lbl";
	private static final String ATTRIBUTE				= "Attribute";
	private static final String	FILTER					= "Filter";

	private static final int VALUE_ID_COLUMN			= 0;
	private static final int OBJECT_COLUMN				= 1;
	private static final int VALUE_COLUMN				= 2;
	private static final int LABEL_COLUMN				= 3; 
	private static final int ATTRIBUTE_COLUMN			= 4; 
	private static final int FILTER_COLUMN				= 5; 
	

	private static final int VIEWPORT_WIDTH				= 600;
	private static final int VIEWPORT_HEIGHT			=  94; 
	private static final int ROW_HEIGHT					=  28; 
	
	private String 	ADD									= "add_btn";
	private String 	REMOVE								= "remove_btn";
	
	private static final String	BLANK_STRING			= new String(new char[32]).replace('\0', ' ');

	JPanel				variablePanel;
	JLabel				variableLabel;
	JComboBox			variableComboBox;
	
	JPanel				valueTablePanel;
	JTable				valueTable;
	DefaultTableModel	valueTableModel;
	
	JPanel				valueButtonPnl;
	JButton				addValueBtn;
	JButton				remValueBtn;
	
	Font				currentFont;
	
	/*
	 *	Constructor
	 */
	/**
	 * @param locale
	 */
	public TabValues(Locale locale) {
		super(locale);
		
		setName("TabValues");
	}
	
	/**
	 * @param al
	 * @param locale
	 */
	public TabValues(ActionListener al, Locale locale) {
		this(locale);
		
		currentLocale	= locale;
		resourceBundle	= ResourceBundle.getBundle(BUNDLE, currentLocale);
		currentFont		= this.getFont();
		
		/* Create Form Components */
		buildValueTablePanel();
		
		/* Add Form Components to Form Panel */
		formPanel.add(valueTablePanel);
		
		/* Setup Buttons */
		backButton.setActionCommand(ActionCommandText.BTN_DAT_STP_VAL_TAB_BACK);
		backButton.addActionListener(al);
		resetButton.setActionCommand(ActionCommandText.BTN_DAT_STP_VAL_TAB_RESET);
		resetButton.addActionListener(al);
		resetButton.setVisible(false);
		resetButton.setEnabled(false);
		noteButton.setActionCommand(ActionCommandText.BTN_DAT_STP_VAL_TAB_NOTE);
		noteButton.addActionListener(al);
		nextButton.setActionCommand(ActionCommandText.BTN_DAT_STP_VAL_TAB_NEXT);
		nextButton.addActionListener(al);
		
		setPanelIdx(5); // 4
		
		changeLanguage(locale);
	}
	
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.Tab#changeLanguage(java.util.Locale)
	 */
	public void changeLanguage(Locale locale) {
		super.changeLanguage(locale);
		
		ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, locale);		
		variableLabel.setText(bundle.getString(VARIABLE));
		valueTable.getTableHeader().getColumnModel().getColumn(0).setHeaderValue(
				bundle.getString((String)valueTable.getTableHeader().getColumnModel().getColumn(0).getIdentifier()));
		valueTable.getTableHeader().getColumnModel().getColumn(1).setHeaderValue(
				bundle.getString((String)valueTable.getTableHeader().getColumnModel().getColumn(1).getIdentifier()));
		addValueBtn.setText(bundle.getString(ADD));
		remValueBtn.setText(bundle.getString(REMOVE));
	}
	
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.Tab#changeFont(java.awt.Font)
	 */
	public void changeFont(Font f) {
		super.changeFont(f);
		
		variableLabel.setFont(f);
		variableComboBox.setFont(f);

		currentFont = f;
		
		valueTable.getTableHeader().setFont(f);
		valueTable.setFont(f);
		
		for (int column=0; column<valueTable.getModel().getColumnCount(); column++) 
			for (int row=0; row<valueTable.getModel().getRowCount(); row++) {
				Object o = valueTable.getModel().getValueAt(row, column);
				
				if (o instanceof JComboBox)
					((JComboBox)o).setFont(f);
			}
		
		addValueBtn.setFont(f);
		remValueBtn.setFont(f);
	}

	/**
	 * 
	 */
	void buildValueTablePanel() {
		
		buildValueRowModel();
		
        variablePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        variableLabel = new JLabel(resourceBundle.getString(VARIABLE));
        variableComboBox = new JComboBox();
        variableComboBox.addItem(BLANK_STRING);
		variableComboBox.addActionListener (new ActionListener () {
		    /* (non-Javadoc)
		     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		     */
		    public void actionPerformed(ActionEvent e) {
		    	if (e.getSource() != null) {
		    		if ( ((JComboBox)e.getSource()).getSelectedItem() != null) {
		    			if (((JComboBox)e.getSource()).getSelectedItem() instanceof Variable) {
		    				Variable var = (Variable)((JComboBox)e.getSource()).getSelectedItem();
		    				
		    	    		for (int i=0; i<valueTable.getModel().getRowCount(); i++)
		    	    			valueTable.getModel().setValueAt(var, i, FILTER_COLUMN);
		    	    		
		    	    	    RowFilter<Object, Object> val0Filter = new RowFilter<Object, Object>() {
				    	    	@SuppressWarnings("rawtypes")
								public boolean include(Entry entry) {
				    	    		if (((Variable)entry.getValue(ATTRIBUTE_COLUMN)) != null)
				    	    			return (((entry.getValue(ATTRIBUTE_COLUMN).equals(entry.getValue(FILTER_COLUMN)))));
				    	    		else
				    	    			return false;
				    	    	}
		    	    	    };
				    	    TableRowSorter<TableModel> val0Sorter = new TableRowSorter<TableModel>((TableModel) valueTable.getModel());
				    	    val0Sorter.setRowFilter(val0Filter);
				    	    valueTable.setRowSorter(val0Sorter);		    				
		    			}
		    		}
		    	}
		    }
		});
		variableComboBox.addFocusListener(this);
        
        variablePanel.add(variableLabel);
        variablePanel.add(variableComboBox);
        
		valueTableModel = buildValueTableModel();
        valueTable = buildValueTable(valueTableModel);
        valueTable.getColumnModel().getColumn(VALUE_COLUMN).setIdentifier(VALUE);
        valueTable.getColumnModel().getColumn(LABEL_COLUMN).setIdentifier(LABEL);
		valueTable.removeColumn(valueTable.getColumn(VALUE_ID));
		valueTable.removeColumn(valueTable.getColumn(OBJECT));
		valueTable.setName("valValueTbl");
		
		addValueRow(new Value(), null, null); // dummy element
	    RowFilter<Object, Object> val0Filter = new RowFilter<Object, Object>() {
	    	/* (non-Javadoc)
	    	 * @see javax.swing.RowFilter#include(javax.swing.RowFilter.Entry)
	    	 */
	    	@SuppressWarnings("rawtypes")
			public boolean include(Entry entry) {
	    		return false; 
	    	}
	    };
	    TableRowSorter<TableModel> val0Sorter = new TableRowSorter<TableModel>((TableModel) valueTable.getModel());
	    val0Sorter.setRowFilter(val0Filter);
	    valueTable.setRowSorter(val0Sorter);
		
		addValueBtn		= new JButton(resourceBundle.getString(ADD));
		addValueBtn.addActionListener( 
				new ActionListener() {
					/* (non-Javadoc)
					 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
					 */
					public void actionPerformed(ActionEvent e) {
						addValueBtnAction(e);
					}
				}
		);
		addValueBtn.setVisible(false);
		addValueBtn.setEnabled(false);
		remValueBtn		= new JButton(resourceBundle.getString(REMOVE));
		remValueBtn.addActionListener(
				new ActionListener() {
					/* (non-Javadoc)
					 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
					 */
					public void actionPerformed(ActionEvent e) {
						remValueBtnAction(e);
					}
				}
		);
		remValueBtn.setVisible(false);
		remValueBtn.setEnabled(false);
		
		valueButtonPnl	= new JPanel(new FlowLayout(FlowLayout.TRAILING));
		valueButtonPnl.add(addValueBtn);
		valueButtonPnl.add(remValueBtn);

        valueTablePanel = new JPanel();
        valueTablePanel.setLayout(new BorderLayout());
        valueTablePanel.add(variablePanel, BorderLayout.NORTH);
        JScrollPane valScrollPane = new JScrollPane(valueTable);
        valScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        valScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        valueTablePanel.add(valScrollPane, BorderLayout.CENTER);        
        valueTablePanel.add(valueButtonPnl, BorderLayout.SOUTH);
	}
	
	/**
	 * @param e
	 */
	protected void addValueBtnAction(ActionEvent e) {
		if (variableComboBox.getSelectedItem() instanceof Variable)
			this.addValueRow(new Value(), (Variable)variableComboBox.getSelectedItem(), (Variable)variableComboBox.getSelectedItem());
	}
	
	/**
	 * @param val
	 * @param var
	 * @param filter
	 */
	public void addValueRow(Value val, Variable var, Variable filter) {		
		
		this.valueTableModel.addRow(
			new Object[]{
				val.getEntityID(),
				val,
				val.getValue(),
				val.getLabel(),
				var,
				filter
			}
		);
		
		if (!valueTable.getAutoCreateRowSorter())
			valueTable.setAutoCreateRowSorter(true);
	}
	
	/**
	 * @param var
	 */
	public void addVariableEntry(Variable var) {
		variableComboBox.addItem(var);
	}
	
	/**
	 * @param e
	 */
	protected void remValueBtnAction(ActionEvent e) {
		int[] selectedRows = valueTable.getSelectedRows();
		
		for (int index=selectedRows.length-1; index >= 0; index--) {
			int selectedRow = valueTable.convertRowIndexToModel(selectedRows[index]);
			valueTableModel.removeRow(selectedRow);
		}
	}
	
	/**
	 * 
	 */
	void buildValueRowModel() {
		/* TODO */
	}

	/**
	 * @return
	 */
	DefaultTableModel buildValueTableModel() {
		
		DefaultTableModel tableModel = new DefaultTableModel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			/* (non-Javadoc)
			 * @see javax.swing.table.DefaultTableModel#isCellEditable(int, int)
			 */
			public boolean isCellEditable(int row,int column){    
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
            
		tableModel.addColumn(VALUE_ID);
		tableModel.addColumn(OBJECT);
		tableModel.addColumn(VALUE);
		tableModel.addColumn(LABEL);
		tableModel.addColumn(ATTRIBUTE);
		tableModel.addColumn(FILTER);
        
        return tableModel;
	}

	/**
	 * @param tableModel
	 * @return
	 */
	JTable buildValueTable(DefaultTableModel tableModel) {
		
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
		
		table.removeColumn(table.getColumn(ATTRIBUTE));
		table.removeColumn(table.getColumn(FILTER));
				
        return table;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.event.TableModelListener#tableChanged(javax.swing.event.TableModelEvent)
	 */
	public void tableChanged(TableModelEvent e) {
		TableModel	model 	= (TableModel)e.getSource();
        int 		row 	= e.getFirstRow();
        int 		column	= e.getColumn();
        
        if (row > -1 && column > -1) {
            Object data = model.getValueAt(row, column);
        	
        	if (data != null ) {
        		@SuppressWarnings("unused")
				Value value = (Value)model.getValueAt(row, OBJECT_COLUMN);
        		
	        	switch (column) {
	        		case VALUE_ID_COLUMN:
	        		case OBJECT_COLUMN:
	        			break;
	        		case VALUE_COLUMN:
	        		case LABEL_COLUMN:
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
	 * @see org.gesis.charmstats.view.Tab#setDefaults(java.lang.Object)
	 */
	public void setDefaults(Object defaults) {
		CStatsModel model;
		
		if (defaults instanceof CStatsModel)
			model = (CStatsModel)defaults;
		else {
			model = new CStatsModel();
			
			model.getProject().getContent().setValues(new ArrayList<Value>());
		}
		
		fillModel(model);		
	}
	
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.Tab#fillModel(org.gesis.charmstats.model.CStatsModel)
	 */
	public void fillModel(CStatsModel model) {
		DefaultComboBoxModel boxModel = (DefaultComboBoxModel)variableComboBox.getModel();
		boxModel.removeAllElements();
		variableComboBox.addItem(BLANK_STRING);
		ArrayList<Variable> variables = model.getProject().getContent().getImportedVariables();		
		Iterator<Variable> varIterator = variables.iterator();
		while(varIterator.hasNext()) {
			Variable var = varIterator.next();
			
			addVariableEntry(var);
		}
				
		valueTableModel.setRowCount(0);
		ArrayList<Value> values = model.getProject().getContent().getValues();
		Iterator<Value> valIterator = values.iterator();
		while(valIterator.hasNext()) {
			Value val = valIterator.next();
			Variable var = null;
			
			Iterator<CharacteristicLink> iterator = model.getProject().getContent().getCharacteristicLinks().iterator();
			while (iterator.hasNext()) {
				CharacteristicLink link = iterator.next();
				
				if (link.getCharacteristic().equals(val))
					var = (Variable)link.getAttribute();
			}
			
			addValueRow(val, var, null);
		}
				
	    RowFilter<Object, Object> value0Filter = new RowFilter<Object, Object>() {
	    	/* (non-Javadoc)
	    	 * @see javax.swing.RowFilter#include(javax.swing.RowFilter.Entry)
	    	 */
	    	@SuppressWarnings("rawtypes")
			public boolean include(Entry entry) {
	    		return false; 
	    	}
	    };
	    TableRowSorter<TableModel> value0Sorter = new TableRowSorter<TableModel>((TableModel) valueTable.getModel());
	    value0Sorter.setRowFilter(value0Filter);
	    valueTable.setRowSorter(value0Sorter);
		
		variableComboBox.addActionListener (new ActionListener () {
		    /* (non-Javadoc)
		     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		     */
		    public void actionPerformed(ActionEvent e) {
	    		Attributes selectedData = null;
	    		
		    	if (e.getSource() != null) {
		    		if ( ((JComboBox)e.getSource()).getSelectedItem() != null) {
		    			if (((JComboBox)e.getSource()).getSelectedItem() instanceof Attributes) {
		    				Attributes attr = (Attributes)((JComboBox)e.getSource()).getSelectedItem();
		    				
			    			selectedData = attr;
		    				
				    		for (int i=0; i<valueTable.getModel().getRowCount(); i++)
				    			valueTable.getModel().setValueAt(selectedData, i, FILTER_COLUMN);
		    	    		
		    	    	    RowFilter<Object, Object> value0Filter = new RowFilter<Object, Object>() {
		    	    	    	@SuppressWarnings("rawtypes")
								public boolean include(Entry entry) {
		    	    	    		
		    	    	    		if (entry.getValue(ATTRIBUTE_COLUMN) != null)
		    	    	    			return (entry.getValue(ATTRIBUTE_COLUMN).equals((entry.getValue(FILTER_COLUMN))));
		    	    				else
		    	    					return false;
		    	    	    	}
		    	    	    };
		    	    	    
				    	    TableRowSorter<TableModel> value0Sorter = new TableRowSorter<TableModel>((TableModel) valueTable.getModel());
				    	    value0Sorter.setRowFilter(value0Filter);
				    	    valueTable.setRowSorter(value0Sorter);
			    	    };		    				
		    			}
		    		}
		    	}

		});
		
		if ((model.getProject().getFinishedSince() != null) ||	
				(!model.getProject().isEditedByUser())) {		
			valueTable.setEnabled(false);
		} else {
			valueTable.setEnabled(false);
//			valueTable.setEnabled(true);
		}
		
		if (variableComboBox.getItemCount() > 1)
			variableComboBox.setSelectedIndex(1);
	}
}

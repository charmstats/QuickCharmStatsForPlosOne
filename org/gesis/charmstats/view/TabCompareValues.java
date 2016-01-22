package org.gesis.charmstats.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
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

import org.gesis.charmstats.ActionCommandID;
import org.gesis.charmstats.ActionCommandText;
import org.gesis.charmstats.IdentifiedParameter;
import org.gesis.charmstats.model.AttributeComp;
import org.gesis.charmstats.model.AttributeLink;
import org.gesis.charmstats.model.Attributes;
import org.gesis.charmstats.model.CStatsModel;
import org.gesis.charmstats.model.CharacteristicLink;
import org.gesis.charmstats.model.OperaIndicator;
import org.gesis.charmstats.model.OperaPrescription;
import org.gesis.charmstats.model.Value;
import org.gesis.charmstats.model.Variable;

/**
 * 
 * @author	Martin Friedrichs
 * @since	0.9.1 / CharmStatsPro only
 *
 */
public class TabCompareValues extends Tab implements FocusListener, TableModelListener, ActionListener, Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String PRESCRIPTION_ID			= "Prescription_ID";
	private static final String OBJECT					= "Object";
	private static final String VALUE					= "value_col";
	private static final String LABEL					= "label_col";
	private static final String INDICATOR				= "indicator_lbl";
	private static final String	ATTRIBUTE				= "Attribute";
	private static final String	FILTER					= "Filter";
	
	private static final String VALUE_ID				= "Value_ID";
	private static final String VARIABLE				= "variable_lbl";

	@SuppressWarnings("unused")
	private static final int PRESCRIPTION_ID_COLUMN		= 0;
	@SuppressWarnings("unused")
	private static final int OBJECT_COLUMN				= 1;
	private static final int VALUE_COLUMN				= 2;
	private static final int LABEL_COLUMN				= 3; 
	private static final int ATTRIBUTE_COLUMN			= 4; 
	private static final int FILTER_COLUMN				= 5;
	
	@SuppressWarnings("unused")
	private static final int VALUE_ID_COLUMN			= 0;
	
	private static final int VIEWPORT_WIDTH				= 225;
	private static final int VIEWPORT_HEIGHT			=  94; 
	private static final int ROW_HEIGHT					=  28; 
	
	private static final String	BLANK_STRING			= new String(new char[32]).replace('\0', ' ');
	
	/*
	 *	Fields
	 */
	JPanel				indicatorPanel;
	JPanel				indicatorRowPanel;
	JLabel				indicatorLabel;
	JComboBox			indicatorComboBox;
	
	JPanel				prescriptionTablePanel;
	JTable				prescriptionTable;
	DefaultTableModel	prescriptionTableModel;
	
	JPanel				variablePanel;
	JPanel				variableRowPanel;
	JLabel				variableLabel;
	JComboBox			variableComboBox;
	
	JPanel				valueTablePanel;
	JTable				valueTable;
	DefaultTableModel	valueTableModel;
	
	JButton				refillValuesBtn;
	
	Font				currentFont;
	
	CStatsModel			model;

	/*
	 *	Constructor
	 */
	/**
	 * @param locale
	 */
	public TabCompareValues(Locale locale) {
		super(locale);
		
		setName("TabCompareValues");
	}
	
	/**
	 * @param model
	 * @param al
	 * @param locale
	 */
	public TabCompareValues(CStatsModel model, ActionListener al, Locale locale) {
		this(locale);
		
		this.model = model;
		
		currentLocale	= locale;
		resourceBundle	= ResourceBundle.getBundle(BUNDLE, currentLocale);
		currentFont		= this.getFont();
		
		/* Create Form Components */
		JPanel comparePanel = (JPanel)buildSearchCompPanel(model, locale, al);
		
		/* Add Form Components to Form Panel */
		formPanel.add(comparePanel);
		
		/* Setup Buttons */
		backButton.setActionCommand(ActionCommandText.BTN_SEA_STP_VAL_TAB_BACK);
		backButton.addActionListener(al);
		noteButton.setActionCommand(ActionCommandText.BTN_SEA_STP_VAL_TAB_NOTE);
		noteButton.addActionListener(al);
		nextButton.setActionCommand(ActionCommandText.BTN_SEA_STP_VAL_TAB_NEXT);
		nextButton.addActionListener(al);
		
		setPanelIdx(4); // 3
		
		changeLanguage(locale);		
	}
	
	@SuppressWarnings("unused")
	private static final CStatsModel init(final CStatsModel model) {
		return model;
	}

	/*
	 *	Methods
	 */
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.Tab#changeLanguage(java.util.Locale)
	 */
	public void changeLanguage(Locale locale) {
		super.changeLanguage(locale);
		
		ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, locale);				
		indicatorLabel.setText(bundle.getString(INDICATOR));
		prescriptionTable.getTableHeader().getColumnModel().getColumn(0).setHeaderValue(
				bundle.getString((String)prescriptionTable.getTableHeader().getColumnModel().getColumn(0).getIdentifier()));
		prescriptionTable.getTableHeader().getColumnModel().getColumn(1).setHeaderValue(
				bundle.getString((String)prescriptionTable.getTableHeader().getColumnModel().getColumn(1).getIdentifier()));
		
		variableLabel.setText(bundle.getString(VARIABLE));
		valueTable.getTableHeader().getColumnModel().getColumn(0).setHeaderValue(
				bundle.getString((String)valueTable.getTableHeader().getColumnModel().getColumn(0).getIdentifier()));
		valueTable.getTableHeader().getColumnModel().getColumn(1).setHeaderValue(
				bundle.getString((String)valueTable.getTableHeader().getColumnModel().getColumn(1).getIdentifier()));
	}
	
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.Tab#changeFont(java.awt.Font)
	 */
	public void changeFont(Font f) {
		super.changeFont(f);

		indicatorLabel.setFont(f);
		indicatorComboBox.setFont(f);
		
		variableLabel.setFont(f);
		variableComboBox.setFont(f);

		currentFont = f;
		
		prescriptionTable.getTableHeader().setFont(f);
		prescriptionTable.setFont(f);
		
		for (int column=0; column<prescriptionTable.getModel().getColumnCount(); column++) 
			for (int row=0; row<prescriptionTable.getModel().getRowCount(); row++) {
				Object o = prescriptionTable.getModel().getValueAt(row, column);
				
				if (o instanceof JComboBox)
					((JComboBox)o).setFont(f);
			}
		
		valueTable.getTableHeader().setFont(f);
		valueTable.setFont(f);
		
		for (int column=0; column<valueTable.getModel().getColumnCount(); column++) 
			for (int row=0; row<valueTable.getModel().getRowCount(); row++) {
				Object o = valueTable.getModel().getValueAt(row, column);
				
				if (o instanceof JComboBox)
					((JComboBox)o).setFont(f);
			}
	}
	
	/**
	 * @param model
	 * @param locale
	 * @param al
	 * @return
	 */
	private JComponent buildSearchCompPanel(CStatsModel model, Locale locale, ActionListener al) {
		JPanel searchCompPanel = new JPanel();
		searchCompPanel.setLayout(new BoxLayout(searchCompPanel, BoxLayout.X_AXIS));
		
		indicatorRowPanel = buildIndicatorPanel(model, locale);
		variablePanel = buildVariablePanel(model, locale);
		refillValuesBtn = new JButton();
		refillValuesBtn.setActionCommand(ActionCommandText.BTN_TAB_COM_VAL_REFILL);
		refillValuesBtn.addActionListener(al);
		refillValuesBtn.setVisible(false);
		refillValuesBtn.setEnabled(true);
		
		searchCompPanel.add(indicatorRowPanel);
		searchCompPanel.add(variablePanel);
		
		return searchCompPanel;
	}
	
	/**
	 * @param model
	 * @param locale
	 * @return
	 */
	private JPanel buildIndicatorPanel(CStatsModel model, Locale locale) {
		buildPrescriptionRowModel();
		
	    indicatorRowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    indicatorLabel = new JLabel(resourceBundle.getString(INDICATOR));
	    indicatorComboBox = new JComboBox();
		
        indicatorComboBox.addItem(BLANK_STRING);
		indicatorComboBox.addActionListener (new ActionListener () {
		    /* (non-Javadoc)
		     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		     */
		    public void actionPerformed(ActionEvent e) {
		    	if (e.getSource() != null) {
		    		if ( ((JComboBox)e.getSource()).getSelectedItem() != null) {
		    			if (((JComboBox)e.getSource()).getSelectedItem() instanceof OperaIndicator) {
		    				OperaIndicator ind = (OperaIndicator)((JComboBox)e.getSource()).getSelectedItem();
		    				
		    	    		for (int i=0; i<prescriptionTable.getModel().getRowCount(); i++)
		    	    			prescriptionTable.getModel().setValueAt(ind, i, FILTER_COLUMN);
		    	    		
		    	    	    RowFilter<Object, Object> spec0Filter = new RowFilter<Object, Object>() {
				    	    	@SuppressWarnings("rawtypes")
								public boolean include(Entry entry) {
				    	    		if (((OperaIndicator)entry.getValue(ATTRIBUTE_COLUMN)) != null)
				    	    			return (((entry.getValue(ATTRIBUTE_COLUMN).equals(entry.getValue(FILTER_COLUMN)))));
				    	    		else
				    	    			return false;
				    	    	}
		    	    	    };
				    	    TableRowSorter<TableModel> pres0Sorter = new TableRowSorter<TableModel>((TableModel) prescriptionTable.getModel());
				    	    pres0Sorter.setRowFilter(spec0Filter);
				    	    prescriptionTable.setRowSorter(pres0Sorter);
				    	    
				    	    refillValuesBtn.doClick();
		    			}
		    		}
		    	}
		    }
		});
		indicatorComboBox.addFocusListener(this);

        indicatorRowPanel.add(indicatorLabel);
        indicatorRowPanel.add(indicatorComboBox);
		
		prescriptionTableModel = buildPrescriptionTableModel();
        prescriptionTable = buildPrescriptionTable(prescriptionTableModel);
        prescriptionTable.getColumnModel().getColumn(VALUE_COLUMN).setIdentifier(VALUE);
        prescriptionTable.getColumnModel().getColumn(LABEL_COLUMN).setIdentifier(LABEL);
		prescriptionTable.removeColumn(prescriptionTable.getColumn(PRESCRIPTION_ID));
		prescriptionTable.removeColumn(prescriptionTable.getColumn(OBJECT));
		prescriptionTable.setName("prePrescriptionTbl");
		
		addPrescriptionRow(new OperaPrescription(), null, null); // dummy element		
	    RowFilter<Object, Object> pres0Filter = new RowFilter<Object, Object>() {
	    	/* (non-Javadoc)
	    	 * @see javax.swing.RowFilter#include(javax.swing.RowFilter.Entry)
	    	 */
	    	@SuppressWarnings("rawtypes")
			public boolean include(Entry entry) {
	    		return false; 
	    	}
	    };
	    TableRowSorter<TableModel> pres0Sorter = new TableRowSorter<TableModel>((TableModel) prescriptionTable.getModel());
	    pres0Sorter.setRowFilter(pres0Filter);
	    prescriptionTable.setRowSorter(pres0Sorter);
		
        prescriptionTablePanel = new JPanel();
        prescriptionTablePanel.setLayout(new BorderLayout());
        prescriptionTablePanel.add(indicatorRowPanel, BorderLayout.NORTH);
        JScrollPane presScrollPane = new JScrollPane(prescriptionTable);
        presScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        presScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        prescriptionTablePanel.add(presScrollPane, BorderLayout.CENTER);       
		
		return prescriptionTablePanel;
	}
	
	/**
	 * 
	 */
	void buildPrescriptionRowModel() {
		
	}
	
	/**
	 * @return
	 */
	DefaultTableModel buildPrescriptionTableModel() {
		
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
            
		tableModel.addColumn(PRESCRIPTION_ID);
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
	JTable buildPrescriptionTable(DefaultTableModel tableModel) {
		
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
	
	class JComponentCellRenderer implements TableCellRenderer {
		
	    public Component getTableCellRendererComponent(JTable table, Object value,
	    		boolean isSelected, boolean hasFocus, int row, int column) {
	        return (JComponent)value;
	    }
	}
	
	/**
	 * @param pres
	 * @param ind
	 * @param filter
	 */
	public void addPrescriptionRow(OperaPrescription pres, OperaIndicator ind, OperaIndicator filter) {		
		
		this.prescriptionTableModel.addRow(
			new Object[]{
				pres.getEntityID(),
				pres,
				pres.getValue(),
				pres.getLabel(),
				ind,
				filter
			}
		);
		
		if (!prescriptionTable.getAutoCreateRowSorter())
			prescriptionTable.setAutoCreateRowSorter(true);
	}
	
	
	/**
	 * @param model
	 * @param locale
	 * @return
	 */
	private JPanel buildVariablePanel(CStatsModel model, Locale locale) {
		buildValueRowModel();
		
        variableRowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
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
        
        variableRowPanel.add(variableLabel);
        variableRowPanel.add(variableComboBox);

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
	    
        valueTablePanel = new JPanel();
        valueTablePanel.setLayout(new BorderLayout());
        valueTablePanel.add(variableRowPanel, BorderLayout.NORTH);
        JScrollPane valScrollPane = new JScrollPane(valueTable);
        valScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        valScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        valueTablePanel.add(valScrollPane, BorderLayout.CENTER);
        
        return valueTablePanel;
	}
	
	/**
	 * 
	 */
	void buildValueRowModel() {
		
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
	
	
	
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.Tab#setDefaults(java.lang.Object)
	 */
	public void setDefaults(Object defaults) {
		CStatsModel model;
		
		if (defaults instanceof CStatsModel)
			model = (CStatsModel)defaults;
		else {
			model = new CStatsModel();
			
			model.getProject().getContent().setPrescriptions(new ArrayList<OperaPrescription>());
			model.getProject().getContent().setValues(new ArrayList<Value>());
		}
		
		fillModel(model);		
	}
	
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.Tab#fillModel(org.gesis.charmstats.model.CStatsModel)
	 */
	public void fillModel(CStatsModel model) {
		DefaultComboBoxModel iBoxModel = (DefaultComboBoxModel)indicatorComboBox.getModel();
		iBoxModel.removeAllElements();
		indicatorComboBox.addItem(BLANK_STRING);
		ArrayList<AttributeComp>  attrComps = model.getProject().getContent().getComps();
		Iterator<AttributeComp> attrCompIterator = attrComps.iterator();
		while (attrCompIterator.hasNext()) {
			AttributeComp attrComp = attrCompIterator.next();
			
			AttributeLink attrLink = attrComp.getTargetAttribute();
			
			if (attrLink.getAttribute() instanceof OperaIndicator)
				addIndicatorEntry((OperaIndicator)attrLink.getAttribute());
		}		

		
		prescriptionTableModel.setRowCount(0);
		ArrayList<OperaPrescription> prescriptions = model.getProject().getContent().getPrescriptions(); 	
		Iterator<OperaPrescription> presIterator = prescriptions.iterator();
		while(presIterator.hasNext()) {
			OperaPrescription pres = presIterator.next();
			OperaIndicator ind = null;
			
			Iterator<CharacteristicLink> iterator = model.getProject().getContent().getCharacteristicLinks().iterator();
    		while (iterator.hasNext()) {
    			CharacteristicLink link = iterator.next();
    			
    			if (link.getCharacteristic().equals(pres))
    				ind = (OperaIndicator)link.getAttribute();
    		}
			
			addPrescriptionRow(pres, ind, null);
		}
		
		if ((model.getProject().getFinishedSince() != null) ||	
				(!model.getProject().isEditedByUser())) {		
			prescriptionTable.setEnabled(false);
			valueTable.setEnabled(false);
		} else {
			prescriptionTable.setEnabled(false);
			valueTable.setEnabled(false);
		}
		
		/* Variable / Value - Part: */
		DefaultComboBoxModel vboxModel = (DefaultComboBoxModel)variableComboBox.getModel();
		vboxModel.removeAllElements();
		variableComboBox.addItem(BLANK_STRING);
		
		attrComps = model.getProject().getContent().getComps();
		attrCompIterator = attrComps.iterator();
		while (attrCompIterator.hasNext()) {
			AttributeComp attrComp = attrCompIterator.next();
			
			AttributeLink attrLink = attrComp.getSourceAttribute();
			
			if (attrLink.getAttribute() instanceof Variable)
				addVariableEntry((Variable)attrLink.getAttribute());
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
			
			if (indicatorComboBox.getItemCount() > 1)
				indicatorComboBox.setSelectedIndex(1); 
		}
	}
	
	/**
	 * @param ind
	 */
	public void addIndicatorEntry(OperaIndicator ind) {
		for (int i=0; i<indicatorComboBox.getItemCount(); i++)
			if (ind.equals(indicatorComboBox.getItemAt(i)) )
				return;
		
		indicatorComboBox.addItem(ind);
	}
	
	/**
	 * @param var
	 */
	public void addVariableEntry(Variable var) {
		for (int i=0; i<variableComboBox.getItemCount(); i++)
			if (var.equals(variableComboBox.getItemAt(i)) )
				return;
		
		variableComboBox.addItem(var);
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.TableModelListener#tableChanged(javax.swing.event.TableModelEvent)
	 */
	@Override
	public void tableChanged(TableModelEvent e) {
		// Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.Tab#focusGained(java.awt.event.FocusEvent)
	 */
	@Override
	public void focusGained(FocusEvent e) {
		if(e.getSource() instanceof JTextField) {
			((JTextField)e.getSource()).setBackground(Color.yellow);
		}
		if(e.getSource() instanceof JTextArea) {
			((JTextArea)e.getSource()).setBackground(Color.yellow);
		}
		if(e.getSource() instanceof JComboBox) {
			((JComboBox)e.getSource()).setBackground(Color.yellow);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.Tab#focusLost(java.awt.event.FocusEvent)
	 */
	@Override
	public void focusLost(FocusEvent e) {
		if(e.getSource() instanceof JTextField) {
			((JTextField)e.getSource()).setBackground(Color.white);
		}
		if(e.getSource() instanceof JTextArea) {
			((JTextArea)e.getSource()).setBackground(Color.white);
		}
		if(e.getSource() instanceof JComboBox) {
			((JComboBox)e.getSource()).setBackground(Color.white);
		}
	}

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		IdentifiedParameter parameter = (IdentifiedParameter)arg;
		
		if (parameter != null) {
			Object[] addenda = parameter.getParameters();

			CStatsModel model = null;
			if (addenda[0] instanceof CStatsModel)
				model = (CStatsModel)addenda[0];
			
			@SuppressWarnings("unused")
			Object argument = addenda[0];
			
			@SuppressWarnings("unused")
			JTabbedPane tabPane = null;
			switch (parameter.getID()) {
				case ActionCommandID.BTN_TAB_COM_VAL_REFILL:
					if (indicatorComboBox.getSelectedItem() instanceof OperaIndicator) {
						OperaIndicator ind = (OperaIndicator)indicatorComboBox.getSelectedItem();
						
						DefaultComboBoxModel vboxModel = (DefaultComboBoxModel)variableComboBox.getModel();
						vboxModel.removeAllElements();
						variableComboBox.addItem(BLANK_STRING);
						
						ArrayList<AttributeComp>  attrComps = model.getProject().getContent().getComps();
						Iterator<AttributeComp> attrCompIterator = attrComps.iterator();
						while (attrCompIterator.hasNext()) {
							AttributeComp attrComp = attrCompIterator.next();
							
							AttributeLink sourceAttrLink = attrComp.getSourceAttribute();
							AttributeLink targetAttrLink = attrComp.getTargetAttribute();
							
							if ((sourceAttrLink.getAttribute() instanceof Variable) &&
									(targetAttrLink.getAttribute() instanceof OperaIndicator) &&
									(targetAttrLink.getAttribute().equals(ind)))
								addVariableEntry((Variable)sourceAttrLink.getAttribute());
						}
						if (variableComboBox.getItemCount() > 1)
							variableComboBox.setSelectedIndex(1);
						else {
							variableComboBox.setSelectedIndex(0);
							/* switch selection of value table to zero-element */
						}
							
					}
					break;				
				default:
			}
		}
	}	

}

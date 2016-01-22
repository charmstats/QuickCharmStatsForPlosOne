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
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
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
import org.gesis.charmstats.model.AttributeLink;
import org.gesis.charmstats.model.AttributeLinkType;
import org.gesis.charmstats.model.CStatsModel;
import org.gesis.charmstats.model.InstanceType;
import org.gesis.charmstats.model.MeasurementLevel;
import org.gesis.charmstats.model.TypeOfData;
import org.gesis.charmstats.model.Variable;
import org.gesis.charmstats.model.VariableType;
import org.gesis.charmstats.model.WorkStepInstance;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class TabVariable extends Tab implements TableModelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String DR_INSTANCE		= "dr_instance_lbl";
	
	private static final String VARIABLE_ID =	"Variable_ID";
	private static final String OBJECT = 		"Object";
	private static final String NAME = 			"name_col";
	private static final String LABEL =			"label_col";
	private static final String DEFINITION = 	"definition_col";
	private static final String LEVEL = 		"level_col";
	private static final String MEASURE_TYPE =	"measure_col";
	private	static final String INSTANCE =		"instance_col";
	private static final String TYPE = 			"type_col";
	private static final String	FILTER			= "Filter";
	
	/* Tooltip: */
	private static final String	DR_INST_CB_TOOLTIP	= "v_dr_inst_cb_tt";
	private static final String	IMPORT_BTN_TOOLTIP	= "v_import_btn_tt";
	private static final String	NEXT_BTN_TOOLTIP	= "v_next_btn_tt";
	
	private static final String VARIABLE				= "variable_lbl";
	
	private static final int VARIABLE_ID_COLUMN 	= 0;
	private static final int OBJECT_COLUMN 			= 1;
	private static final int NAME_COLUMN 			= 2;
	private static final int LABEL_COLUMN			= 3;
	private static final int TYPE_COLUMN 			= 4; 
	private static final int LEVEL_COLUMN 			= 5; 
	private static final int MEASURE_TYPE_COLUMN 	= 6; 
	public static final int DEFINITION_COLUMN 		= 7; 
	private static final int INSTANCE_COLUMN 		= 8; 
	private static final int FILTER_COLUMN			= 9; 

	private static final int VIEWPORT_WIDTH =	600;
	private static final int VIEWPORT_HEIGHT =	94; 
	private static final int ROW_HEIGHT = 28; 
	
	private String 				ADD					= "add_btn";
	private String 				REMOVE				= "remove_btn";
	
	private static final String	BLANK_STRING			= new String(new char[32]).replace('\0', ' ');

	JPanel						drInstancePanel;
	JLabel						drInstanceLabel;
	JComboBox					drInstanceComboBox;
	
	JPanel						variableTablePanel;
	JTable						variableTable;
	DefaultTableModel			variableTableModel;
	JComboBox 					typeComboBox;
	Vector<VariableType> 		typeLabels;
	VariableType				type;
	JComboBox					levelComboBox;
	Vector<TypeOfData>			levelLabels;
	TypeOfData					level;
	
	JList						valueList;
	Vector<Object>				valueLabels;
	JComboBox					measureTypeComboBox;
	Vector<MeasurementLevel>	measureTypeLabels;
	MeasurementLevel			measureType;
	WorkStepInstance			instance;
	
	JPanel						variableMetadataPanel;
	JScrollPane					definitionScrollPnl;
	
	JPanel						variableDefinitionPanel;
	
	JPanel						variablePanel;
	JLabel						variableLabel;
	JComboBox					variableComboBox;
	
	JPanel						definitionPanel;
	JLabel						definitionLabel;
	JTextArea					definitionTA;
	
	JPanel				variableButtonPnl;
	JButton				addVariableBtn;
	JButton				remVariableBtn;
	
	Font						currentFont;
	
	CStatsModel model; 
	
	/*
	 *	Constructor
	 */
	/**
	 * @param locale
	 */
	public TabVariable(Locale locale) {
		super(locale);
		
		setName("TabVariable");
	}
	
	/**
	 * @param model
	 * @param al
	 * @param locale
	 */
	public TabVariable(CStatsModel model, ActionListener al, Locale locale) {
		this(locale);
		
		this.model = model;
		
		currentLocale	= locale;
		resourceBundle	= ResourceBundle.getBundle(BUNDLE, currentLocale);
		currentFont		= this.getFont();
		
		/* Create Form Components */
		buildVariableTablePanel();
		
		/* Add Form Components to Form Panel */
		formPanel.add(variableTablePanel);
		
		/* Setup Buttons */
		backButton.setActionCommand(ActionCommandText.BTN_DAT_STP_VAR_TAB_BACK);
		backButton.addActionListener(al);
		noteButton.setActionCommand(ActionCommandText.BTN_DAT_STP_VAR_TAB_NOTE);
		noteButton.addActionListener(al);
		importButton.setActionCommand(ActionCommandText.BTN_DAT_STP_VAR_TAB_IMP);
		importButton.addActionListener(al);
		importButton.setVisible(true);
		importButton.setEnabled(true);
		nextButton.setActionCommand(ActionCommandText.BTN_DAT_STP_VAR_TAB_NEXT);
		nextButton.addActionListener(al);
		
		setPanelIdx(5); // 4
		
		changeLanguage(locale);
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
		drInstanceLabel.setText(bundle.getString(DR_INSTANCE));
		
		for (int i=0; i<=4; i++)
			variableTable.getTableHeader().getColumnModel().getColumn(i).setHeaderValue(
				bundle.getString((String)variableTable.getTableHeader().getColumnModel().getColumn(i).getIdentifier()));
		
		addVariableBtn.setText(bundle.getString(ADD));
		remVariableBtn.setText(bundle.getString(REMOVE));
		
		variableLabel.setText(bundle.getString(VARIABLE));
		definitionLabel.setText(bundle.getString(DEFINITION));		
		
		/* ToolTip: */
		drInstanceComboBox.setToolTipText(bundle.getString(DR_INST_CB_TOOLTIP));
		importButton.setToolTipText(bundle.getString(IMPORT_BTN_TOOLTIP));
		nextButton.setToolTipText(bundle.getString(NEXT_BTN_TOOLTIP));

	}
	
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.Tab#changeFont(java.awt.Font)
	 */
	public void changeFont(Font f) {
		super.changeFont(f);
		
		variableLabel.setFont(f);
		variableComboBox.setFont(f);

		definitionLabel.setFont(f);;
		definitionTA.setFont(f);
		
		drInstanceLabel.setFont(f);
		drInstanceComboBox.setFont(f);

		currentFont = f;
		
		variableTable.getTableHeader().setFont(f);
		variableTable.setFont(f);
		
		for (int column=0; column<variableTable.getModel().getColumnCount(); column++) 
			for (int row=0; row<variableTable.getModel().getRowCount(); row++) {
				Object o = variableTable.getModel().getValueAt(row, column);
				
				if (o instanceof JComboBox)
					((JComboBox)o).setFont(f);
			}
		
		addVariableBtn.setFont(f);
		remVariableBtn.setFont(f);
	}
	
	/**
	 * 
	 */
	void buildVariableTablePanel() {
		
		buildVariableRowModel();
		
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
		    				
		    				if (var instanceof Variable) {
		    					definitionTA.setText(var.getDefinition().getText());
		    					
		    				}
		    			}
		    		}
		    	}
		    }
		});
		variableComboBox.addFocusListener(this);
		
        variablePanel.add(variableLabel);
        variablePanel.add(variableComboBox);
    	
    	definitionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    	definitionLabel = new JLabel(resourceBundle.getString(DEFINITION));
    	
    	definitionTA	= new JTextArea(5, 90);
    	definitionTA.setLineWrap(true);
    	definitionTA.setWrapStyleWord(true);
    	
		definitionScrollPnl = new JScrollPane(definitionTA);
		definitionScrollPnl.getVerticalScrollBar().setUnitIncrement(16);
		
    	definitionPanel.add(definitionLabel);
    	definitionPanel.add(definitionScrollPnl);
    	
       	variableDefinitionPanel = new JPanel();
    	variableDefinitionPanel.setLayout(new BorderLayout());
    	variableDefinitionPanel.add(variablePanel, BorderLayout.NORTH);
    	variableDefinitionPanel.add(definitionPanel, BorderLayout.CENTER);
        
        drInstancePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        drInstanceLabel = new JLabel(resourceBundle.getString(DR_INSTANCE));
        drInstanceComboBox = new JComboBox();
        drInstanceComboBox.addItem(BLANK_STRING);
 		drInstanceComboBox.addActionListener (new ActionListener () {
 		    /* (non-Javadoc)
 		     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
 		     */
 		    public void actionPerformed(ActionEvent e) {
 		    	if (e.getSource() != null) {
 		    		if ( ((JComboBox)e.getSource()).getSelectedItem() != null) {
 		    			if (((JComboBox)e.getSource()).getSelectedItem() instanceof WorkStepInstance) {
 		    				WorkStepInstance inst = (WorkStepInstance)((JComboBox)e.getSource()).getSelectedItem();
 		    				
 		    	    		for (int i=0; i<variableTable.getModel().getRowCount(); i++)
 		    	    			variableTable.getModel().setValueAt(inst, i, FILTER_COLUMN);
 		    	    		
 		    	    	    RowFilter<Object, Object> var0Filter = new RowFilter<Object, Object>() {
	 			    	    	@SuppressWarnings("rawtypes")
								public boolean include(Entry entry) {
	 			    	    		if (((WorkStepInstance)entry.getValue(INSTANCE_COLUMN)) != null)
	 			    	    			return (((entry.getValue(INSTANCE_COLUMN).equals(entry.getValue(FILTER_COLUMN)))));
	 			    	    		else
	 			    	    			return false;
	 			    	    	}
	 			    	    };
	 			    	    TableRowSorter<TableModel> var0Sorter = new TableRowSorter<TableModel>((TableModel) variableTable.getModel());
	 			    	    var0Sorter.setRowFilter(var0Filter);
	 			    	    variableTable.setRowSorter(var0Sorter);		    				
 		    			}
 		    		}
 		    	}
 		    }
 		});
 		drInstanceComboBox.addFocusListener(this);
        
        drInstancePanel.add(drInstanceLabel);
        drInstancePanel.add(drInstanceComboBox);
		
		variableTableModel = buildVariableTableModel();
        variableTable = buildVariableTable(variableTableModel);
        variableTable.getColumnModel().getColumn(TYPE_COLUMN).setIdentifier(TYPE);
        variableTable.getColumnModel().getColumn(NAME_COLUMN).setIdentifier(NAME);
        variableTable.getColumnModel().getColumn(LABEL_COLUMN).setIdentifier(LABEL);
        variableTable.getColumnModel().getColumn(LEVEL_COLUMN).setIdentifier(LEVEL);
        variableTable.getColumnModel().getColumn(DEFINITION_COLUMN).setIdentifier(DEFINITION);
        variableTable.getColumnModel().getColumn(MEASURE_TYPE_COLUMN).setIdentifier(MEASURE_TYPE);
        variableTable.removeColumn(variableTable.getColumn(VARIABLE_ID));
        variableTable.removeColumn(variableTable.getColumn(OBJECT));
        variableTable.removeColumn(variableTable.getColumn(DEFINITION)); 
        variableTable.setName("varVariableTable");
        
        Variable var = new Variable();
        var.setLevel(model.getProject().getContent().getMeasurement().getLevel());
        
		addVariableRow(var, null, null); // dummy element		
	    RowFilter<Object, Object> var0Filter = new RowFilter<Object, Object>() {
	    	/* (non-Javadoc)
	    	 * @see javax.swing.RowFilter#include(javax.swing.RowFilter.Entry)
	    	 */
	    	@SuppressWarnings("rawtypes")
			public boolean include(Entry entry) {
	    		return false; 
	    	}
	    };
	    TableRowSorter<TableModel> var0Sorter = new TableRowSorter<TableModel>((TableModel) variableTable.getModel());
	    var0Sorter.setRowFilter(var0Filter);
	    variableTable.setRowSorter(var0Sorter);
        
		addVariableBtn		= new JButton(resourceBundle.getString(ADD));
		addVariableBtn.addActionListener( 
				new ActionListener() {
					/* (non-Javadoc)
					 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
					 */
					public void actionPerformed(ActionEvent e) {
						addVariableBtnAction(e);
					}
				}
		);
		addVariableBtn.setEnabled(false);
		addVariableBtn.setVisible(false);
		remVariableBtn		= new JButton(resourceBundle.getString(REMOVE));
		remVariableBtn.addActionListener(
				new ActionListener() {
					/* (non-Javadoc)
					 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
					 */
					public void actionPerformed(ActionEvent e) {
						remVariableBtnAction(e);
					}
				}
		);

		remVariableBtn.setEnabled(false);
		remVariableBtn.setVisible(false);
		
		variableButtonPnl	= new JPanel(new FlowLayout(FlowLayout.TRAILING));
		variableButtonPnl.add(addVariableBtn);
		variableButtonPnl.add(remVariableBtn);
		        
        variableTablePanel = new JPanel();
        variableTablePanel.setLayout(new BorderLayout());
        variableTablePanel.add(drInstancePanel, BorderLayout.NORTH);
        JScrollPane varScrollPane = new JScrollPane(variableTable);
        varScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        varScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        variableMetadataPanel = new JPanel();
        variableMetadataPanel.setLayout(new BorderLayout());
        variableMetadataPanel.add(varScrollPane, BorderLayout.CENTER);
        variableMetadataPanel.add(variableDefinitionPanel, BorderLayout.SOUTH);
        
        variableTablePanel.add(variableMetadataPanel, BorderLayout.CENTER);
                
        variableTablePanel.add(variableButtonPnl, BorderLayout.SOUTH);
	}
	
	public void addVariableEntry(Variable var) {
		variableComboBox.addItem(var);
	}
	
	/**
	 * @param e
	 */
	protected void addVariableBtnAction(ActionEvent e) {
		if (drInstanceComboBox.getSelectedItem() instanceof WorkStepInstance) {
			Variable var = new Variable();
			var.setLevel(model.getProject().getContent().getMeasurement().getLevel());
			
			this.addVariableRow(var, (WorkStepInstance)drInstanceComboBox.getSelectedItem(), (WorkStepInstance)drInstanceComboBox.getSelectedItem());
		}
	}
	
	/**
	 * @param var
	 * @param inst
	 * @param filter
	 */
	public void addVariableRow(Variable var, WorkStepInstance inst, WorkStepInstance filter) {		
		JComboBox level = new GComboBox(this.levelLabels);
		level.setFont(currentFont);
		level.setBorder(BorderFactory.createEmptyBorder());
		if (var.getLevel() != null) {
			level.setSelectedItem(var.getLevel());
		} else {
			level.setSelectedItem(TypeOfData.NONE);
			var.setLevel((TypeOfData)level.getSelectedItem());
		}
		
		this.variableTableModel.addRow(
			new Object[]{
				var.getEntityID(),
				var,
				var.getName(),
				var.getLabel(),
				var.getType(),
				level,
				var.getMeasureType(),
				var.getDefinition().getText(),
				inst,
				filter
			}
		);
		
		if (!variableTable.getAutoCreateRowSorter())
			variableTable.setAutoCreateRowSorter(true);
	}

	/**
	 * @param e
	 */
	protected void remVariableBtnAction(ActionEvent e) {
		int[] selectedRows = variableTable.getSelectedRows();
		
		for (int index=selectedRows.length-1; index >= 0; index--) {
			int selectedRow = variableTable.convertRowIndexToModel(selectedRows[index]);
			variableTableModel.removeRow(selectedRow);
		}
	}
	
	/**
	 * 
	 */
	void buildVariableRowModel() {
		
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
		
		valueLabels = new Vector<Object>();
		
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
				return true;  
			}
			
			/* (non-Javadoc)
			 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
			 */
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int column) {
                Class returnValue;
                if (this.getRowCount() > 0) // only check column content if there is a row -MF, 15.11.12
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
            
		tableModel.addColumn(VARIABLE_ID);
		tableModel.addColumn(OBJECT);
		tableModel.addColumn(resourceBundle.getString(NAME));
		tableModel.addColumn(resourceBundle.getString(LABEL));
		tableModel.addColumn(resourceBundle.getString(TYPE));
		tableModel.addColumn(resourceBundle.getString(LEVEL));
		tableModel.addColumn(resourceBundle.getString(MEASURE_TYPE));
        tableModel.addColumn(resourceBundle.getString(DEFINITION));
        tableModel.addColumn(INSTANCE);
        tableModel.addColumn(FILTER);
 
        return tableModel;
	}	

	/**
	 * @param tableModel
	 * @return
	 */
	JTable buildVariableTable(DefaultTableModel tableModel) {
		
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
				TableCellEditor tableCellEditor = tableColumn.getCellEditor();
				if (tableCellEditor == null) {
					Class c = getColumnClass(column);
					if( c.equals(Object.class) ) {
						Object o = getValueAt(row,column);
						if( o != null )
							c = getValueAt(row,column).getClass();
					}
					tableCellEditor = getDefaultEditor(c);
				}
				return tableCellEditor;
			}
			
			/* (non-Javadoc)
			 * @see javax.swing.JTable#prepareRenderer(javax.swing.table.TableCellRenderer, int, int)
			 */
			public Component prepareRenderer(TableCellRenderer renderer,int row, int col) {
				Component	component	= super.prepareRenderer(renderer, row, col);
		        JComponent	jComponent	= (JComponent)component;
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
		
		table.removeColumn(table.getColumn(INSTANCE));
		table.removeColumn(table.getColumn(FILTER));
		
        return table;
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
	public void tableChanged(TableModelEvent e) {
        TableModel	model	= (TableModel)e.getSource();
        int 		row 	= e.getFirstRow();
        int 		column 	= e.getColumn();
        
        if (row > -1 && column > -1) {
        	Object data = model.getValueAt(row, column);
        	
        	if (data != null ) {
        		Variable variable = (Variable)model.getValueAt(row, OBJECT_COLUMN);
        		
	        	switch (column) {
	        		case VARIABLE_ID_COLUMN:
	        		case OBJECT_COLUMN:
	        		case NAME_COLUMN:
	        			break;
	        		case LABEL_COLUMN:
	        			break;
	        		case TYPE_COLUMN:
	        			if (((JComboBox) data).getSelectedItem() instanceof VariableType)
	        				variable.type = (VariableType) ((JComboBox) data).getSelectedItem();
	        			break;
	        		case LEVEL_COLUMN:
	        			if (((JComboBox) data).getSelectedItem() instanceof TypeOfData)
	        				variable.level = (TypeOfData) ((JComboBox) data).getSelectedItem();
	        			break;
	        		case MEASURE_TYPE_COLUMN:
	        			if (((JComboBox) data).getSelectedItem() instanceof MeasurementLevel)
	        				variable.measureType = (MeasurementLevel) ((JComboBox) data).getSelectedItem();
	        			break;
	        		case DEFINITION_COLUMN:
	        			variable.getDefinition().setText((String) data);
	        			break;
	        		case INSTANCE_COLUMN:
	        			break;
	        	}
        	}   	
        }
		
	}
	
	/**
	 * @param inst
	 */
	public void addInstanceEntry(WorkStepInstance inst) {
		drInstanceComboBox.addItem(inst);
	}
	
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.Tab#fillModel(org.gesis.charmstats.model.CStatsModel)
	 */
	public void fillModel(CStatsModel model) {
		DefaultComboBoxModel varBoxModel = (DefaultComboBoxModel)variableComboBox.getModel();
		varBoxModel.removeAllElements();
		variableComboBox.addItem(BLANK_STRING);
		ArrayList<Variable> variableList = model.getProject().getContent().getImportedVariables();		
		Iterator<Variable> varIterator = variableList.iterator();
		while(varIterator.hasNext()) {
			Variable var = varIterator.next();
			
			addVariableEntry(var);
		}
		
		definitionTA.setEnabled(false);
		
		if (variableComboBox.getItemCount() > 1)
			variableComboBox.setSelectedIndex(1);
		
		DefaultComboBoxModel boxModel = (DefaultComboBoxModel)drInstanceComboBox.getModel();
		boxModel.removeAllElements();
		drInstanceComboBox.addItem(BLANK_STRING);
		ArrayList<WorkStepInstance> instances = model.getProject().getContent().getLayers(InstanceType.DATA_CODING);		
		Iterator<WorkStepInstance> instIterator = instances.iterator();
		while(instIterator.hasNext()) {
			WorkStepInstance inst = instIterator.next();
			
			addInstanceEntry(inst);
		}
		
		variableTableModel.setRowCount(0);
		ArrayList<Variable> variables = model.getProject().getContent().getVariables();
		Iterator<Variable> iterator = variables.iterator();
		while(iterator.hasNext()) {
			Variable var = iterator.next();			
			WorkStepInstance inst = null;
			
			Iterator<AttributeLink> attrIterator = model.getProject().getContent().getLinksByType(AttributeLinkType.VARIABLE).iterator();
    		while (attrIterator.hasNext()) {
    			AttributeLink link = attrIterator.next();
    			
    			if (link.getAttribute().equals(var))
    				inst = (WorkStepInstance)link.getInstance();
    		}
			
			addVariableRow(var, inst, null);
		}
		
		if ((model.getProject().getFinishedSince() != null) ||	
				(!model.getProject().isEditedByUser())) {		
			variableTable.setEnabled(false);
			addVariableBtn.setEnabled(false);
			remVariableBtn.setEnabled(false);
			importButton.setEnabled(false);
		} else {
			variableTable.setEnabled(false);
			addVariableBtn.setEnabled(false);
			remVariableBtn.setEnabled(false);
			importButton.setEnabled(true);
		}
		
		if (drInstanceComboBox.getItemCount() > 1)
			drInstanceComboBox.setSelectedIndex(1);
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
			
			model.getProject().getContent().setVariables(new ArrayList<Variable>());
		}
		
		fillModel(model);
	}

	/**
	 * @return
	 */
	public WorkStepInstance getInstance() {		
		return (WorkStepInstance)drInstanceComboBox.getSelectedItem();
	}
	
}

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
import org.gesis.charmstats.model.CStatsModel;
import org.gesis.charmstats.model.CharacteristicLink;
import org.gesis.charmstats.model.OperaIndicator;
import org.gesis.charmstats.model.OperaPrescription;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1 / CharmStatsPro only
 *
 */
public class TabPrescriptions extends Tab implements TableModelListener {

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

	private static final int PRESCRIPTION_ID_COLUMN		= 0;
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

	JPanel				indicatorPanel;
	JLabel				indicatorLabel;
	JComboBox			indicatorComboBox;
	
	JPanel				prescriptionTablePanel;
	JTable				prescriptionTable;
	DefaultTableModel	prescriptionTableModel;
	
	JPanel				prescriptionButtonPnl;
	JButton				addPrescriptionBtn;
	JButton				remPrescriptionBtn;
	
	Font				currentFont;
	
	/*
	 *	Constructor
	 */
	/**
	 * @param locale
	 */
	public TabPrescriptions(Locale locale) {
		super(locale);
		
		setName("TabPrescriptions");
	}
	
	/**
	 * @param al
	 * @param locale
	 */
	public TabPrescriptions(ActionListener al, Locale locale) {
		this(locale);
		
		currentLocale	= locale;
		resourceBundle	= ResourceBundle.getBundle(BUNDLE, currentLocale);
		currentFont		= this.getFont();
		
		/* Create Form Components */
		buildPrescriptionTablePanel();
		
		/* Add Form Components to Form Panel */
		formPanel.add(prescriptionTablePanel);
		
		/* Setup Buttons */
		backButton.setActionCommand(ActionCommandText.BTN_OPE_STP_PRE_TAB_BACK);
		backButton.addActionListener(al);
		noteButton.setActionCommand(ActionCommandText.BTN_OPE_STP_PRE_TAB_NOTE);
		noteButton.addActionListener(al);
		nextButton.setActionCommand(ActionCommandText.BTN_OPE_STP_PRE_TAB_NEXT);
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
		indicatorLabel.setText(bundle.getString(INDICATOR));
		prescriptionTable.getTableHeader().getColumnModel().getColumn(0).setHeaderValue(
				bundle.getString((String)prescriptionTable.getTableHeader().getColumnModel().getColumn(0).getIdentifier()));
		prescriptionTable.getTableHeader().getColumnModel().getColumn(1).setHeaderValue(
				bundle.getString((String)prescriptionTable.getTableHeader().getColumnModel().getColumn(1).getIdentifier()));
		addPrescriptionBtn.setText(bundle.getString(ADD));
		remPrescriptionBtn.setText(bundle.getString(REMOVE));
	}
	
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.Tab#changeFont(java.awt.Font)
	 */
	public void changeFont(Font f) {
		super.changeFont(f);
		
		indicatorLabel.setFont(f);
		indicatorComboBox.setFont(f);

		currentFont = f;
		
		prescriptionTable.getTableHeader().setFont(f);
		prescriptionTable.setFont(f);
		
		for (int column=0; column<prescriptionTable.getModel().getColumnCount(); column++) 
			for (int row=0; row<prescriptionTable.getModel().getRowCount(); row++) {
				Object o = prescriptionTable.getModel().getValueAt(row, column);
				
				if (o instanceof JComboBox)
					((JComboBox)o).setFont(f);
			}
		
		addPrescriptionBtn.setFont(f);
		remPrescriptionBtn.setFont(f);
	}
	
	/**
	 * 
	 */
	void buildPrescriptionTablePanel() {
		
		buildPrescriptionRowModel();
		
        indicatorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        indicatorLabel = new JLabel(resourceBundle.getString(INDICATOR));
        indicatorComboBox = new JComboBox();
        //AutoCompleteDecorator.decorate(indicatorComboBox);
		//indicatorComboBox.isEditable();
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
		    			}
		    		}
		    	}
		    }
		});
		indicatorComboBox.addFocusListener(this);
        
        indicatorPanel.add(indicatorLabel);
        indicatorPanel.add(indicatorComboBox);
        
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
		
		addPrescriptionBtn		= new JButton(ADD);
		addPrescriptionBtn.addActionListener( 
				new ActionListener() {
					/* (non-Javadoc)
					 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
					 */
					public void actionPerformed(ActionEvent e) {
						addPrescriptionBtnAction(e);
					}
				}
		);
		remPrescriptionBtn		= new JButton(REMOVE);
		remPrescriptionBtn.addActionListener(
				new ActionListener() {
					/* (non-Javadoc)
					 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
					 */
					public void actionPerformed(ActionEvent e) {
						remPrescriptionBtnAction(e);
					}
				}
		);
		
		prescriptionButtonPnl	= new JPanel(new FlowLayout(FlowLayout.TRAILING));
		prescriptionButtonPnl.add(addPrescriptionBtn);
		prescriptionButtonPnl.add(remPrescriptionBtn);

        prescriptionTablePanel = new JPanel();
        prescriptionTablePanel.setLayout(new BorderLayout());
        prescriptionTablePanel.add(indicatorPanel, BorderLayout.NORTH);
        JScrollPane presScrollPane = new JScrollPane(prescriptionTable);
        presScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        presScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        prescriptionTablePanel.add(presScrollPane, BorderLayout.CENTER);        
        prescriptionTablePanel.add(prescriptionButtonPnl, BorderLayout.SOUTH);
	}
	
	/**
	 * @param e
	 */
	protected void addPrescriptionBtnAction(ActionEvent e) {
		if (indicatorComboBox.getSelectedItem() instanceof OperaIndicator)
			this.addPrescriptionRow(new OperaPrescription(), (OperaIndicator)indicatorComboBox.getSelectedItem(), (OperaIndicator)indicatorComboBox.getSelectedItem()); 
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
	 * @param ind
	 */
	public void addIndicatorEntry(OperaIndicator ind) {
		indicatorComboBox.addItem(ind);
	}
	
	/**
	 * @param e
	 */
	protected void remPrescriptionBtnAction(ActionEvent e) {
		int[] selectedRows = prescriptionTable.getSelectedRows();
		
		for (int index=selectedRows.length-1; index >= 0; index--) {
			int selectedRow = prescriptionTable.convertRowIndexToModel(selectedRows[index]);
			prescriptionTableModel.removeRow(selectedRow);
		}
	}
	
	/**
	 * 
	 */
	void buildPrescriptionRowModel() {
		/* TODO */
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
	
	/* (non-Javadoc)
	 * @see javax.swing.event.TableModelListener#tableChanged(javax.swing.event.TableModelEvent)
	 */
	public void tableChanged(TableModelEvent e) {
        TableModel	model	= (TableModel)e.getSource();
        int			row 	= e.getFirstRow();
        int 		column 	= e.getColumn();
        
        if (row > -1 && column > -1) {
            Object data = model.getValueAt(row, column);
        	
        	if (data != null ) {
        		@SuppressWarnings("unused")
				OperaPrescription pres = (OperaPrescription)model.getValueAt(row, OBJECT_COLUMN);
        		
	        	switch (column) {
	        		case PRESCRIPTION_ID_COLUMN:
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
			
			model.getProject().getContent().setPrescriptions(new ArrayList<OperaPrescription>());
		}
		
		fillModel(model);		
	}
	
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.Tab#fillModel(org.gesis.charmstats.model.CStatsModel)
	 */
	public void fillModel(CStatsModel model) {
		DefaultComboBoxModel boxModel = (DefaultComboBoxModel)indicatorComboBox.getModel();
		boxModel.removeAllElements();
		indicatorComboBox.addItem(BLANK_STRING);
		ArrayList<OperaIndicator> indicators = model.getProject().getContent().getIndicators();		
		Iterator<OperaIndicator> indIterator = indicators.iterator();
		while(indIterator.hasNext()) {
			OperaIndicator ind = indIterator.next();
			
			addIndicatorEntry(ind);
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
			addPrescriptionBtn.setEnabled(false);
			remPrescriptionBtn.setEnabled(false);
		} else {
			prescriptionTable.setEnabled(true);
			addPrescriptionBtn.setEnabled(true);
			remPrescriptionBtn.setEnabled(true);
		}
		
		if (indicatorComboBox.getItemCount() > 1)
			indicatorComboBox.setSelectedIndex(1);
	}
	
}

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
import org.gesis.charmstats.model.ConDimension;
import org.gesis.charmstats.model.ConSpecification;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1 / CharmStatsPro only
 *
 */
public class TabSpecifications extends Tab implements TableModelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String SPECIFICATION_ID		= "Specification_ID";
	private static final String OBJECT					= "Object";
	private static final String LABEL					= "label_col";
	private static final String DIMENSION				= "dimension_lbl";
	private static final String	ATTRIBUTE				= "Attribute";
	private static final String	FILTER					= "Filter";

	private static final int SPECIFICATION_ID_COLUMN	= 0;
	private static final int OBJECT_COLUMN				= 1;
	private static final int LABEL_COLUMN				= 2;
	private static final int ATTRIBUTE_COLUMN			= 3;
	private static final int FILTER_COLUMN				= 4;

	private static final int VIEWPORT_WIDTH				= 600;
	private static final int VIEWPORT_HEIGHT			=  94; 
	private static final int ROW_HEIGHT					=  28; 
	
	private String 	ADD									= "add_btn";
	private String 	REMOVE								= "remove_btn";
	
	private static final String	BLANK_STRING			= new String(new char[32]).replace('\0', ' ');


	JPanel				dimensionPanel;
	JLabel				dimensionLabel;
	JComboBox			dimensionComboBox;
	
	JPanel				specificationTablePanel;
	JTable				specificationTable;
	DefaultTableModel	specificationTableModel;
	
	JPanel				specificationButtonPnl;
	JButton				addSpecificationBtn;
	JButton				remSpecificationBtn;
	
	Font				currentFont;
	
	/*
	 *	Constructor
	 */
	/**
	 * @param locale
	 */
	public TabSpecifications(Locale locale) {
		super(locale);
		
		setName("TabSpecifications");
	}
	
	/**
	 * @param al
	 * @param locale
	 */
	public TabSpecifications(ActionListener al, Locale locale) {
		this(locale);
		
		currentLocale	= locale;
		resourceBundle	= ResourceBundle.getBundle(BUNDLE, currentLocale);
		currentFont		= this.getFont();
		
		/* Create Form Components */
		buildSpecificationTablePanel();
		
		/* Add Form Components to Form Panel */
		formPanel.add(specificationTablePanel);
		
		/* Setup Buttons */
		backButton.setActionCommand(ActionCommandText.BTN_CON_STP_SPE_TAB_BACK);
		backButton.addActionListener(al);
		noteButton.setActionCommand(ActionCommandText.BTN_CON_STP_SPE_TAB_NOTE);
		noteButton.addActionListener(al);
		nextButton.setActionCommand(ActionCommandText.BTN_CON_STP_SPE_TAB_NEXT);
		nextButton.addActionListener(al);
		
		setPanelIdx(2); // 1
		
		changeLanguage(locale);
	}
	
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.Tab#changeLanguage(java.util.Locale)
	 */
	public void changeLanguage(Locale locale) {
		super.changeLanguage(locale);
		
		ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, locale);		
		dimensionLabel.setText(bundle.getString(DIMENSION));
		specificationTable.getTableHeader().getColumnModel().getColumn(0).setHeaderValue(
				bundle.getString((String)specificationTable.getTableHeader().getColumnModel().getColumn(0).getIdentifier()));
		addSpecificationBtn.setText(bundle.getString(ADD));
		remSpecificationBtn.setText(bundle.getString(REMOVE));
	}
	
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.Tab#changeFont(java.awt.Font)
	 */
	public void changeFont(Font f) {
		super.changeFont(f);
		
		dimensionLabel.setFont(f);
		dimensionComboBox.setFont(f);
		
		addSpecificationBtn.setFont(f);
		remSpecificationBtn.setFont(f);

		currentFont = f;
		
		specificationTable.getTableHeader().setFont(f);
		specificationTable.setFont(f);
		
		for (int column=0; column<specificationTable.getModel().getColumnCount(); column++) 
			for (int row=0; row<specificationTable.getModel().getRowCount(); row++) {
				Object o = specificationTable.getModel().getValueAt(row, column);
				
				if (o instanceof JComboBox)
					((JComboBox)o).setFont(f);
			}
	}
	
	/**
	 * 
	 */
	void buildSpecificationTablePanel() {
		
		buildSpecificationRowModel();
		
		dimensionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		dimensionLabel = new JLabel(resourceBundle.getString(DIMENSION));
		dimensionComboBox = new JComboBox();
		dimensionComboBox.addItem(BLANK_STRING);
		dimensionComboBox.addActionListener (new ActionListener () {
		    /* (non-Javadoc)
		     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		     */
		    public void actionPerformed(ActionEvent e) {
		    	if (e.getSource() != null) {
		    		if ( ((JComboBox)e.getSource()).getSelectedItem() != null) {
		    			if (((JComboBox)e.getSource()).getSelectedItem() instanceof ConDimension) {
		    				ConDimension dim = (ConDimension)((JComboBox)e.getSource()).getSelectedItem();
		    				
		    	    		for (int i=0; i<specificationTable.getModel().getRowCount(); i++)
		    	    			specificationTable.getModel().setValueAt(dim, i, FILTER_COLUMN);
		    	    		
		    	    	    RowFilter<Object, Object> spec0Filter = new RowFilter<Object, Object>() {
			    	    	@SuppressWarnings("rawtypes")
							public boolean include(Entry entry) {
			    	    		if (((ConDimension)entry.getValue(ATTRIBUTE_COLUMN)) != null)
			    	    			return (((entry.getValue(ATTRIBUTE_COLUMN).equals(entry.getValue(FILTER_COLUMN)))));
			    	    		else
			    	    			return false;
			    	    	}
			    	    };
			    	    TableRowSorter<TableModel> spec0Sorter = new TableRowSorter<TableModel>((TableModel) specificationTable.getModel());
			    	    spec0Sorter.setRowFilter(spec0Filter);
			    	    specificationTable.setRowSorter(spec0Sorter);		    				
		    			}
		    		}
		    	}
		    }
		});
		dimensionComboBox.addFocusListener(this);
		
		dimensionPanel.add(dimensionLabel);
		dimensionPanel.add(dimensionComboBox);
	        
		specificationTableModel = buildSpecificationTableModel();
        specificationTable = buildSpecificationTable(specificationTableModel);
        specificationTable.getColumnModel().getColumn(LABEL_COLUMN).setIdentifier(LABEL);
		specificationTable.removeColumn(specificationTable.getColumn(SPECIFICATION_ID));
		specificationTable.removeColumn(specificationTable.getColumn(OBJECT));
		specificationTable.setName("dimSpecificationTbl");
		
		addSpecificationRow(new ConSpecification(), null, null); // dummy element		
	    RowFilter<Object, Object> spec0Filter = new RowFilter<Object, Object>() {
	    	/* (non-Javadoc)
	    	 * @see javax.swing.RowFilter#include(javax.swing.RowFilter.Entry)
	    	 */
	    	@SuppressWarnings("rawtypes")
			public boolean include(Entry entry) {
	    		return false; 
	    	}
	    };
	    TableRowSorter<TableModel> spec0Sorter = new TableRowSorter<TableModel>((TableModel) specificationTable.getModel());
	    spec0Sorter.setRowFilter(spec0Filter);
	    specificationTable.setRowSorter(spec0Sorter);
		
		addSpecificationBtn		= new JButton(resourceBundle.getString(ADD));
		addSpecificationBtn.addActionListener( 
				new ActionListener() {
					/* (non-Javadoc)
					 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
					 */
					public void actionPerformed(ActionEvent e) {
						addSpecificationBtnAction(e);
					}
				}
		);
		remSpecificationBtn		= new JButton(resourceBundle.getString(REMOVE));
		remSpecificationBtn.addActionListener(
				new ActionListener() {
					/* (non-Javadoc)
					 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
					 */
					public void actionPerformed(ActionEvent e) {
						remSpecificationBtnAction(e);
					}
				}
		);
		
		specificationButtonPnl	= new JPanel(new FlowLayout(FlowLayout.TRAILING));
		specificationButtonPnl.add(addSpecificationBtn);
		specificationButtonPnl.add(remSpecificationBtn);

        specificationTablePanel = new JPanel();
        specificationTablePanel.setLayout(new BorderLayout());
        specificationTablePanel.add(dimensionPanel, BorderLayout.NORTH);
        JScrollPane specScrollPane = new JScrollPane(specificationTable);
        specScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        specScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        specificationTablePanel.add(specScrollPane, BorderLayout.CENTER);
        specificationTablePanel.add(specificationButtonPnl, BorderLayout.SOUTH);
	}
	
	/**
	 * @param e
	 */
	protected void addSpecificationBtnAction(ActionEvent e) {
		if (dimensionComboBox.getSelectedItem() instanceof ConDimension)
			this.addSpecificationRow(new ConSpecification(), (ConDimension)dimensionComboBox.getSelectedItem(), (ConDimension)dimensionComboBox.getSelectedItem()); 
	}
	
	/**
	 * @param spec
	 * @param dim
	 * @param filter
	 */
	public void addSpecificationRow(ConSpecification spec, ConDimension dim, ConDimension filter) {		
		
		this.specificationTableModel.addRow(
			new Object[]{
				spec.getEntityID(),
				spec,
				spec.getLabel(),
				dim,
				filter
			}
		);
		
		if (!specificationTable.getAutoCreateRowSorter())
			specificationTable.setAutoCreateRowSorter(true);
	}
	
	/**
	 * @param dim
	 */
	public void addDimensionEntry(ConDimension dim) {
		dimensionComboBox.addItem(dim);
	}
	
	/**
	 * @param e
	 */
	protected void remSpecificationBtnAction(ActionEvent e) {
		int[] selectedRows = specificationTable.getSelectedRows();
		
		for (int index=selectedRows.length-1; index >= 0; index--) {
			int selectedRow = specificationTable.convertRowIndexToModel(selectedRows[index]);
			specificationTableModel.removeRow(selectedRow);
		}
	}
	
	/**
	 * 
	 */
	void buildSpecificationRowModel() {
		/* TODO */
	}

	/**
	 * @return
	 */
	DefaultTableModel buildSpecificationTableModel() {
		
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
            
		tableModel.addColumn(SPECIFICATION_ID);
		tableModel.addColumn(OBJECT);
		tableModel.addColumn(LABEL);
		tableModel.addColumn(ATTRIBUTE);
		tableModel.addColumn(FILTER);
        
        return tableModel;
	}

	/**
	 * @param tableModel
	 * @return
	 */
	JTable buildSpecificationTable(DefaultTableModel tableModel) {
		
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
        int 		row 	= e.getFirstRow();
        int 		column	= e.getColumn();
        
        if (row > -1 && column > -1) {
            Object data = model.getValueAt(row, column);
        	
        	if (data != null ) {
        		@SuppressWarnings("unused")
				ConSpecification specification = (ConSpecification)model.getValueAt(row, OBJECT_COLUMN);
        		
	        	switch (column) {
	        		case SPECIFICATION_ID_COLUMN:
	        		case OBJECT_COLUMN:
	        			break;
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
	 * @see org.gesis.charmstats.view.Tab#fillModel(org.gesis.charmstats.model.CStatsModel)
	 */
	public void fillModel(CStatsModel model) {
		DefaultComboBoxModel boxModel = (DefaultComboBoxModel)dimensionComboBox.getModel();
		boxModel.removeAllElements();
		dimensionComboBox.addItem(BLANK_STRING);
		ArrayList<ConDimension> dimensions = model.getProject().getContent().getDimensions();		
		Iterator<ConDimension> dimIterator = dimensions.iterator();
		while(dimIterator.hasNext()) {
			ConDimension dim = dimIterator.next();
			
			addDimensionEntry(dim);
		}
		
		specificationTableModel.setRowCount(0);
		ArrayList<ConSpecification> specifications = model.getProject().getContent().getSpecifications();		
		Iterator<ConSpecification> specIterator = specifications.iterator();
		while(specIterator.hasNext()) {
			ConSpecification spec = specIterator.next();
			ConDimension dim = null;
			
			Iterator<CharacteristicLink> iterator = model.getProject().getContent().getCharacteristicLinks().iterator();
    		while (iterator.hasNext()) {
    			CharacteristicLink link = iterator.next();
    			
    			if (link.getCharacteristic().equals(spec))
    				dim = (ConDimension)link.getAttribute();
    		}
			
			addSpecificationRow(spec, dim, null);
		}
		
		if ((model.getProject().getFinishedSince() != null) ||	
				(!model.getProject().isEditedByUser())) {		
			specificationTable.setEnabled(false);
			addSpecificationBtn.setEnabled(false);
			remSpecificationBtn.setEnabled(false);
		} else {
			specificationTable.setEnabled(true);
			addSpecificationBtn.setEnabled(true);
			remSpecificationBtn.setEnabled(true);
		}
		
		if (dimensionComboBox.getItemCount() > 1)
			dimensionComboBox.setSelectedIndex(1);
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
			
			model.getProject().getContent().setSpecifications(new ArrayList<ConSpecification>());
		}
		
		fillModel(model);		
	}
	
}

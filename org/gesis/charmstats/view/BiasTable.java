package org.gesis.charmstats.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Iterator;
import java.util.Locale;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import org.gesis.charmstats.IdentifiedParameter;
import org.gesis.charmstats.model.AttributeComp;
import org.gesis.charmstats.model.BiasMetadata;
import org.gesis.charmstats.model.BiasPreferenceType;
import org.gesis.charmstats.model.BiasRatingType;
import org.gesis.charmstats.model.BiasStandCodingType;
import org.gesis.charmstats.model.CStatsModel;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1 / CharmStatsPro only
 *
 */
public class BiasTable extends GTable implements TableModelListener, FocusListener  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String BUNDLE			= "org.gesis.charmstats.resources.DesktopBundle";
	
	private static final String BIAS_ID			= "Bias_ID";
	private static final String OBJECT			= "Object";
	private static final String INVERSE			= "bd_inverse";
	private static final String DIFF			= "bd_diff";
	private static final String BIAS_RATING		= "bd_bias_rating";
	private static final String MISSINGS		= "bd_missings";
	private static final String C_PROC			= "bd_c_proc";
	private static final String PREFERENCE		= "bd_preference";
	public  static final String	FILTER			= "Filter";
	public  static final String FILLER			= "Filler";
	
	@SuppressWarnings("unused")
	private static final String EMPTY_LABEL		= "";
	
	private static final int BIAS_ID_COLUMN 	= 0;
	public static final int OBJECT_COLUMN		= 1;
	public static final int INVERSE_COLUMN		= 2;
	public static final int DIFF_COLUMN			= 3; 
	public static final int BIAS_RATING_COLUMN	= 4; 
	public static final int MISSINGS_COLUMN		= 5; 
	public static final int C_PROC_COLUMN		= 6;
	public static final int PREFERENCE_COLUMN	= 7;
	public static final int FILTER_COLUMN		= 8;
	public static final int FILLER_COLUMN		= 9;

	private static final int VIEWPORT_WIDTH		= 600;
	private static final int VIEWPORT_HEIGHT	=  94; // 4 rows plus header
	private static final int ROW_HEIGHT			=  28; 
    
	/*
	 *	Fields
	 */
	Locale 		  				currentLocale;
	ResourceBundle				resourceBundle;
	Font						currentFont;
	
	JPanel						tablePanel;
	JTable						table;
	DefaultTableModel			biasTableModel;
	
	JComboBox 					ratingComboBox;
	Vector<BiasRatingType> 		ratingLabels;
	BiasRatingType				rating;
	
	JComboBox 					codingComboBox;
	Vector<BiasStandCodingType> codingLabels;
	BiasStandCodingType			coding;
	
	JComboBox					preferenceComboBox;
	Vector<BiasPreferenceType>	preferenceLabels;
	BiasPreferenceType			preference;
	
	/*
	 *	Constructor
	 */
	/**
	 * @param locale
	 */
	BiasTable (Locale locale) {
		this.setLayout(new BorderLayout());
		
		currentLocale	= locale;
		resourceBundle	= ResourceBundle.getBundle(BUNDLE, currentLocale);
		currentFont		= this.getFont();

		buildTablePanel();
		this.add(tablePanel, BorderLayout.NORTH);
	}
	
	/*
	 *	Methods
	 */
	/**
	 * @param locale
	 */
	public void changeLanguage(Locale locale) {
		ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, locale);
		currentLocale = locale;
		
		table.getColumn(resourceBundle.getString(INVERSE)).setHeaderValue(bundle.getString(INVERSE));
		table.getColumn(resourceBundle.getString(DIFF)).setHeaderValue(bundle.getString(DIFF));
		table.getColumn(resourceBundle.getString(BIAS_RATING)).setHeaderValue(bundle.getString(BIAS_RATING));
		table.getColumn(resourceBundle.getString(MISSINGS)).setHeaderValue(bundle.getString(MISSINGS));
		table.getColumn(resourceBundle.getString(C_PROC)).setHeaderValue(bundle.getString(C_PROC));
		table.getColumn(resourceBundle.getString(PREFERENCE)).setHeaderValue(bundle.getString(PREFERENCE));
		
		ratingLabels.clear();
		for (BiasRatingType bias_ratingType : BiasRatingType.values()) {
			bias_ratingType.setLocale(currentLocale);
			
			ratingLabels.add(bias_ratingType);
		}
		
		codingLabels.clear();
		for (BiasStandCodingType stand_codingType : BiasStandCodingType.values()) {
			stand_codingType.setLocale(currentLocale);
			
			codingLabels.add(stand_codingType);
		}
		
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
	void buildRowModel() {
		ratingLabels = new Vector<BiasRatingType>();
		for (BiasRatingType bias_ratingType : BiasRatingType.values()) {
			bias_ratingType.setLocale(currentLocale);
			
			ratingLabels.add(bias_ratingType);
		}
		
		codingLabels = new Vector<BiasStandCodingType>();
		for (BiasStandCodingType stand_codingType : BiasStandCodingType.values()) {
			stand_codingType.setLocale(currentLocale);
			
			codingLabels.add(stand_codingType);
		}
		
		preferenceLabels = new Vector<BiasPreferenceType>();
		for (BiasPreferenceType preferenceType : BiasPreferenceType.values()) {
			
			preferenceLabels.add(preferenceType);
		}
		
	}
	
	/**
	 * @return
	 */
	DefaultTableModel buildBiasTableModel() {
		
		DefaultTableModel tableModel = new DefaultTableModel() {
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
            
		tableModel.addColumn(BIAS_ID);
		tableModel.addColumn(OBJECT);
		tableModel.addColumn(resourceBundle.getString(INVERSE));
		tableModel.addColumn(resourceBundle.getString(DIFF));
        tableModel.addColumn(resourceBundle.getString(BIAS_RATING));
        tableModel.addColumn(resourceBundle.getString(MISSINGS));
        tableModel.addColumn(resourceBundle.getString(C_PROC));
        tableModel.addColumn(resourceBundle.getString(PREFERENCE));
		tableModel.addColumn(FILTER);
		tableModel.addColumn(FILLER);
        
        return tableModel;
	}
	
	/**
	 * @param tableModel
	 * @return
	 */
	JTable buildTable(DefaultTableModel tableModel) {
		
		JTable table = new JTable(tableModel) {
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
       
        table.setName("BiasTableData");
        
        table.setPreferredScrollableViewportSize(new Dimension(VIEWPORT_WIDTH, VIEWPORT_HEIGHT));
        table.setRowHeight(ROW_HEIGHT);
//      table.setAutoCreateRowSorter(true);
        
        table.setDefaultRenderer(JComponent.class, new JComponentCellRenderer());
		table.setDefaultEditor(JComponent.class, new JComponentCellEditor());
	    
		table.getModel().addTableModelListener(this);
		table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		
        return table;
	}
	
	/**
	 * @param model
	 */
	public void addRows(CStatsModel model) {
		Iterator<AttributeComp> iterator = model.getProject().getContent().getComps().iterator();	    
		while (iterator.hasNext()) {
			addRow(iterator.next());						
		}		
	}
	
	/**
	 * @param comp
	 */
	public void addRow(AttributeComp comp) {
		
		BiasMetadata metadatum = comp.getBiasMetadata();
		
		JCheckBox inverse		= new JCheckBox();
		inverse.setFont(currentFont);
		inverse.addFocusListener(this);
		
		JCheckBox difference	= new JCheckBox();
		difference.setFont(currentFont);
		difference.addFocusListener(this);
		
		JComboBox rating		= new JComboBox(this.ratingLabels);
		rating.setFont(currentFont);
		//AutoCompleteDecorator.decorate(rating);
		//rating.isEditable();
		rating.addFocusListener(this);
		
		JTextArea missings		= new JTextArea();
		missings.setFont(currentFont);
		missings.addFocusListener(this);
		missings.setLineWrap(true);
		missings.setWrapStyleWord(true);
		
		JComboBox coding		= new JComboBox(this.codingLabels);
		coding.setFont(currentFont);
		//AutoCompleteDecorator.decorate(coding);
		//coding.isEditable();
		coding.addFocusListener(this);
		
		JComboBox preference	= new JComboBox(this.preferenceLabels);
		preference.setFont(currentFont);
		//AutoCompleteDecorator.decorate(preference);
		//preference.isEditable();
		preference.addFocusListener(this);
		
		if (metadatum != null) {
			if (metadatum.isInverse())
				inverse.setSelected(true);			
			
			if (metadatum.isDifference())
				difference.setSelected(true);
			
			if (metadatum.getRating() != null) {
				rating.setSelectedItem(metadatum.getRating());
			} else {
				rating.setSelectedItem(BiasRatingType.NONE);
				metadatum.setRating((BiasRatingType)rating.getSelectedItem());
			}
			
			missings.setText(metadatum.getMissings());
			
			if (metadatum.getStandCoding() != null) {
				coding.setSelectedItem(metadatum.getStandCoding());
			} else {
				coding.setSelectedItem(BiasStandCodingType.NONE);
				metadatum.setStandCoding((BiasStandCodingType)coding.getSelectedItem());
			}
			
			if (metadatum.getPreference() != null) {
				preference.setSelectedItem(metadatum.getPreference());
			} else {
				preference.setSelectedItem(BiasPreferenceType.NONE);
				metadatum.setPreference((BiasPreferenceType)preference.getSelectedItem());
			}
		}
		
		this.biasTableModel.addRow(
			new Object[]{
				comp.getEntityID(), 
				comp, 		      
				inverse,
				difference,
				rating,
				missings,
				coding,
				preference
			}
		);
		
		if (!table.getAutoCreateRowSorter())
			table.setAutoCreateRowSorter(true);
	}
	
	/**
	 * 
	 */
	public void clear() {
		this.biasTableModel.setRowCount(0);
	}
	
	/**
	 * @param enabled
	 */
	public void setTableEnabled(boolean enabled) {
		table.setEnabled(enabled);
	}
	
	/**
	 * 
	 */
	void buildTablePanel() {
		
		buildRowModel();
		
		biasTableModel = buildBiasTableModel();
        table = buildTable(biasTableModel);
		table.removeColumn(table.getColumn(BIAS_ID));
		table.removeColumn(table.getColumn(OBJECT));
		table.removeColumn(table.getColumn(FILTER));
		table.removeColumn(table.getColumn(FILLER));

        tablePanel = new JPanel();
        JScrollPane biasScrollPane = new JScrollPane(table);
        biasScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        tablePanel.add(biasScrollPane, BorderLayout.CENTER);
	}
	
	class JComponentCellRenderer implements TableCellRenderer {
		
	    /* (non-Javadoc)
	     * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
	     */
	    public Component getTableCellRendererComponent(JTable table, Object value,
	    		boolean isSelected, boolean hasFocus, int row, int column) {
	        return (JComponent)value;
	    }
	}
	
	class TextCellRenderer extends JTextArea implements TableCellRenderer {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/* (non-Javadoc)
		 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
		 */
		public Component getTableCellRendererComponent(JTable table, Object obj, 
				boolean isSelected, boolean hasFocus, int row, int column) {
			setLineWrap(true);
			setWrapStyleWord(true);
			setText( (String) obj);

			return this;
		}
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
        		@SuppressWarnings("unused")
				AttributeComp metadatum = (AttributeComp)model.getValueAt(row, OBJECT_COLUMN);
        		
	        	switch (column) {
	        		case BIAS_ID_COLUMN:
	        		case OBJECT_COLUMN:
	        		case INVERSE_COLUMN:
	        		case DIFF_COLUMN:
	        		case BIAS_RATING_COLUMN:
	        		case MISSINGS_COLUMN:
	        		case C_PROC_COLUMN:
	        		case PREFERENCE_COLUMN:		        			
	        			break;
	        	}
        	}   	
        }

    }

	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.GTable#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		IdentifiedParameter parameter = (IdentifiedParameter)arg;
		
		if (parameter != null) {
//			switch (parameter.getID()) {
//				case Constant.HANDLE_SRCH_N_COMP:
//					CCatsGUI viewer = (CCatsGUI)parameter.getParameter();
//					
//					if (biasTableModel.getRowCount() > 0) {
//						for (int i=0; i<biasTableModel.getRowCount(); i++) {
//							BiasMetadata metadata	= (BiasMetadata)biasTableModel.getValueAt(i, OBJECT_COLUMN);
//							JCheckBox inverse 		= (JCheckBox)biasTableModel.getValueAt(i, INVERSE_COLUMN);
//							JCheckBox difference 	= (JCheckBox)biasTableModel.getValueAt(i, DIFF_COLUMN);
//							JComboBox rating 		= (JComboBox)biasTableModel.getValueAt(i, BIAS_RATING_COLUMN);
//							JTextArea missings 		= (JTextArea)biasTableModel.getValueAt(i, MISSINGS_COLUMN);
//							JComboBox stand_coding 	= (JComboBox)biasTableModel.getValueAt(i, C_PROC_COLUMN);
//							JComboBox preference 	= (JComboBox)biasTableModel.getValueAt(i, PREFERENCE_COLUMN);
//							
//							metadata.setInverse(inverse.isSelected());
//							metadata.setDifference(difference.isSelected());
//							metadata.setRating((BiasRatingType)rating.getSelectedItem());
//							metadata.setMissings(missings.getText());
//							metadata.setStandCoding((StandCodingType)stand_coding.getSelectedItem());
//							metadata.setPreference((PreferenceType)preference.getSelectedItem());
//						}
//					}
//					
//					viewer.deleteObserver(this);
//					break;
//				default:
//					break;
//			}
		}
		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
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
		if(e.getSource() instanceof JCheckBox) {
			((JCheckBox)e.getSource()).setBackground(Color.yellow);
		}
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
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
		if(e.getSource() instanceof JCheckBox) {
			((JCheckBox)e.getSource()).setBackground(Color.white);
		}
	}

}

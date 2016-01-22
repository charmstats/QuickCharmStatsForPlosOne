package org.gesis.charmstats.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.gesis.charmstats.ActionCommandText;
import org.gesis.charmstats.controller.CStatsCtrl;
import org.gesis.charmstats.model.Category;
import org.gesis.charmstats.model.CharacteristicLink;
import org.gesis.charmstats.model.CharacteristicLinkType;
import org.gesis.charmstats.model.Comment;
import org.gesis.charmstats.model.Definition;
import org.gesis.charmstats.model.Measurement;
import org.gesis.charmstats.model.MeasurementLevel;
import org.gesis.charmstats.model.MeasurementSource;
import org.gesis.charmstats.model.TypeOfData;

/**
 * 
 * @author	Martin Friedrichs
 * @since	0.9.2
 *
 */
public class EditMeasureDialog extends JPanel implements FocusListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String BUNDLE			= "org.gesis.charmstats.resources.DesktopBundle";
	public static final String ERROR_BUNDLE 	= "org.gesis.charmstats.resources.ErrorMessagesBundle";
	
	public static final String ME_TITLE			= "me_title";
	public static final String ME_NAME			= "me_name";
	public static final String ME_ABBR			= "me_abbr";
	public static final String ME_TYPE			= "me_type";
	public static final String ME_LEVEL			= "me_level";
	public static final String ME_SOURCE		= "me_source";
	public static final String ME_KIND			= "me_kind";
	public static final String ME_IS_TEMP		= "me_is_template";
	public static final String ME_SAVE			= "me_save";
	public static final String ME_RESET			= "me_reset";
	public static final String VE_COMMENT		= "comment";
	public static final String VE_DATASET 		= "ve_dataset"; 
	public static final String VE_DEFINITION	= "ve_definition"; 
	
	public static final String ME_ACCEPT		= "me_accept";
	public static final String ME_CANCEL		= "me_cancel";
	public static final String ME_HELP			= "me_help";
	public static final String ME_NO_HELP		= "me_no_help";
	public static final String VE_QUESTION		= "ve_question";
	
	public static final String EMPTY			= "";
	public static final String PROTO_D_V		= "XXXXXXXXXXXXXXX";
	
	private static final int NAME_TF_LG			= 15;
	private static final int ABBR_TF_LG			= 15;
	
	private static final String CATEGORY_ID			= "Category_ID";
	@SuppressWarnings("unused")
	private static final int	CATEGORY_ID_COLUMN	= 0;
	private static final String OBJECT				= "Object";
	private static final int	OBJECT_COLUMN		= 1;
	private static final String CODE				= "code_col";
	private static final int	CODE_COLUMN			= 2;
	private static final String LABEL				= "label_col";
	private static final int 	LABEL_COLUMN		= 3;
	private static final String IS_MISSING			= "is_missing_col";
	private static final int	IS_MISSING_COLUMN	= 4;
	private static final String LINK				= "link";
	private static final int	LINK_COLUMN			= 5;
		
	private static final int 	VIEWPORT_WIDTH		= 600;
	private static final int 	VIEWPORT_HEIGHT		=  94;							
	private static final int 	ROW_HEIGHT			=  28;
	
	private String 				ADD					= "me_add_btn";
//	private String 				REMOVE				= "remove_btn";
	
	
	/*
	 *	Fields
	 */
	Locale					currentLocale;
	ResourceBundle			resourceBundle;
	Font					currentFont;
	
	JPanel 					formular;
	
	@SuppressWarnings("unused")
	private JPanel			namingPnl;
	private JLabel			nameLbl;
	private JTextField		nameTF;
	private JLabel			abbrLbl;
	private JTextField		abbrTF;
	
	private JPanel			metadataSep;
	private JPanel			metadataPnl;
//	private JLabel			typeLbl;
//	private JComboBox		typeCB;
	private JLabel			levelLbl;
	private JComboBox		levelCB;
	private JLabel			sourceLbl;
	private JComboBox		sourceCB;
	private JLabel			kindLbl;
	private JComboBox		kindCB;
	private JLabel			isTemplateLbl;
	private JCheckBox		isTemplateChk;
	
	JPanel					tablePnl;
	JTable					inputTbl;
	DefaultTableModel		inputTblModel;
	
	JPanel					buttonPnl;
	
	JPanel					tableButtonPnl;
	JButton					addBtn;
	private JButton			commentBtn;
	private JButton			definitionBtn; 
	private JButton			datasetBtn; 
	private JLabel			emptyLbl; 
	
	private JPanel			windowButtonSep;
	private JPanel			windowButtonPnl;
	@SuppressWarnings("unused")
	private JButton			saveBtn;
	private JButton			resetBtn;
	
	private Vector<TypeOfData>			levelLabels;
	private Vector<MeasurementSource>	sourceLabels;
	private Vector<MeasurementLevel>	kindLabels;
	
	JButton			callHelpBtn;
	
	Measurement 				measure;
	Measurement					mea_clone; 
	Comment 					comment;
	Definition					definition;
	List<CharacteristicLink> 	links;
	List<CharacteristicLink> 	lnk_clone; 
	
	final ActionListener fal;

	/*
	 *	Constructor
	 */
	/**
	 * @param measure
	 * @param links
	 * @param l
	 * @param f
	 */
	public EditMeasureDialog (Measurement mea, List<CharacteristicLink> links, Locale l, Font f, ActionListener al) {
		currentLocale	= l;
		resourceBundle	= ResourceBundle.getBundle(BUNDLE, currentLocale);
		currentFont		= f;
		
		this.measure	= mea;
		this.links		= links;
		
		fal = al;
		
		if (measure.getLevel().equals(TypeOfData.NONE))
			measure.setLevel(TypeOfData.INDIVIDUAL);
		if (measure.getSource().equals(MeasurementSource.NONE))
			measure.setSource(MeasurementSource.IMPORTED);
		if (measure.getKind().equals(MeasurementLevel.NONE))
			measure.setKind(MeasurementLevel.NOMINAL);

		mea_clone = new Measurement();
		mea_clone.setName(measure.getName());
		mea_clone.setLabel(measure.getLabel());
		mea_clone.setLevel(measure.getLevel());
		mea_clone.setSource(measure.getSource());
		mea_clone.setKind(measure.getKind());
		
		lnk_clone = new ArrayList<CharacteristicLink>();
		Iterator<CharacteristicLink> it =  links.iterator();
		while (it.hasNext()) {
			CharacteristicLink link = it.next();
			
			if (link.getAttribute().equals(measure)) {
				if (link.getCharacteristic() instanceof Category) {
					Category cat = (Category)link.getCharacteristic();
					
					Category ccat = new Category();
					ccat.setEntityID(cat.getEntityID());
					ccat.setCode(cat.getCode());
					ccat.setLabel(cat.getLabel());
					ccat.setMissing(cat.isMissing());
					
					CharacteristicLink clink = new CharacteristicLink();
					clink.setEntityID(link.getEntityID());
					clink.setAttribute(measure);
					clink.setCharacteristic(ccat);
					
					lnk_clone.add(clink);
				}
			}
		}
		
		buildLayout(fal);
	}
	
	/*
	 *	Methods
	 */
	/**
	 * 
	 */
	private void buildLayout(ActionListener al) {		
		GridLayout gridLayout = new GridLayout(0,6);
		
		/* Name */
		nameLbl = new JLabel(resourceBundle.getString(ME_NAME));
		nameLbl.setFont(currentFont);
		
		nameTF	= new JTextField(NAME_TF_LG);
		nameTF.setFont(currentFont);
		nameTF.addFocusListener(this);
		
		/* Abbr. */
		abbrLbl = new JLabel(resourceBundle.getString(ME_ABBR));
		abbrLbl.setFont(currentFont);
		
		abbrTF	= new JTextField(ABBR_TF_LG);
		abbrTF.setFont(currentFont);
		abbrTF.addFocusListener(this);
						
		/* Type */
//		typeLbl = new JLabel(resourceBundle.getString(ME_TYPE));
//		typeLbl.setFont(currentFont);
//		typeLabels = new Vector<MeasurementType>();
//		for( MeasurementType t: MeasurementType.values() ) {
//			t.setLocale(currentLocale);
//			
//			typeLabels.add(t);
//		}
		
//		typeCB = new JComboBox(typeLabels);
//		typeCB.setFont(currentFont);
//		typeCB.addFocusListener(this);
				
		/* Level */
		levelLbl = new JLabel(resourceBundle.getString(ME_LEVEL));
		levelLbl.setFont(currentFont);
		levelLabels = new Vector<TypeOfData>();
		for( TypeOfData l: TypeOfData.values() ) {
			l.setLocale(currentLocale);
			
			levelLabels.add(l);
		}
		
		levelCB = new JComboBox(levelLabels);
		levelCB.setFont(currentFont);
		levelCB.addFocusListener(this);
				
		/* Source */
		sourceLbl = new JLabel(resourceBundle.getString(ME_SOURCE));
		sourceLbl.setFont(currentFont);
		sourceLabels = new Vector<MeasurementSource>();
		for( MeasurementSource s: MeasurementSource.values() ) {
			s.setLocale(currentLocale);
			
			sourceLabels.add(s);
		}
		
		sourceCB = new JComboBox(sourceLabels);
		sourceCB.setFont(currentFont);
		sourceCB.addFocusListener(this);
				
		/* Kind */
		kindLbl = new JLabel(resourceBundle.getString(ME_KIND));
		kindLbl.setFont(currentFont);
		kindLabels = new Vector<MeasurementLevel>();
		for( MeasurementLevel l: MeasurementLevel.values() ) {
			l.setLocale(currentLocale);
			
			kindLabels.add(l);
		}
		
		kindCB = new JComboBox(kindLabels);
		kindCB.setFont(currentFont);
		kindCB.addFocusListener(this);
		
		/* isTemplate */
		isTemplateLbl = new JLabel(resourceBundle.getString(ME_IS_TEMP));
		isTemplateChk = new JCheckBox();
				
		/* Build Metadata Panel */
		JPanel metadataContentPnl = new JPanel();
		metadataContentPnl.setLayout(gridLayout);
		
		metadataContentPnl.add(nameLbl);
		metadataContentPnl.add(abbrLbl);
//		metadataContentPnl.add(typeLbl);
		metadataContentPnl.add(levelLbl);
		metadataContentPnl.add(sourceLbl);
		metadataContentPnl.add(kindLbl);
		metadataContentPnl.add(isTemplateLbl);
		metadataContentPnl.add(nameTF);
		metadataContentPnl.add(abbrTF);
//		metadataContentPnl.add(typeCB);
		metadataContentPnl.add(levelCB);
		metadataContentPnl.add(sourceCB);
		metadataContentPnl.add(kindCB);
		metadataContentPnl.add(isTemplateChk);
		
		metadataSep = new JPanel();
		metadataSep.setLayout(new BoxLayout(metadataSep, BoxLayout.Y_AXIS));
		
		metadataPnl = new JPanel();
		metadataPnl.setLayout(new FlowLayout(FlowLayout.LEFT));
		metadataPnl.add(metadataContentPnl);
		
		metadataSep.add(metadataPnl);
		metadataSep.add(Box.createVerticalStrut(5));
		metadataSep.add(new JSeparator(SwingConstants.HORIZONTAL));
		metadataSep.add(Box.createVerticalStrut(5));
		
		/* Buttons: */
//		saveBtn = new JButton(resourceBundle.getString(ME_SAVE));
//		saveBtn.setFont(currentFont);
//		saveBtn.addActionListener( 
//				new ActionListener() {
//					public void actionPerformed(ActionEvent e) {
//						saveBtnAction(e);
//					}
//				}
//		);
		
		resetBtn = new JButton(resourceBundle.getString(ME_RESET));
		resetBtn.setFont(currentFont);
		resetBtn.addActionListener( 
				new ActionListener() {
					/* (non-Javadoc)
					 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
					 */
					public void actionPerformed(ActionEvent e) {
						resetBtnAction(e);
					}
				}
		);
		
		commentBtn = new JButton(resourceBundle.getString(VE_COMMENT));
		commentBtn.setFont(currentFont);
		commentBtn.addActionListener( 
				new ActionListener() {
					/* (non-Javadoc)
					 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
					 */
					public void actionPerformed(ActionEvent e) {
						addEditorComment(measure);
					}
				}
		);
		
		definitionBtn = new JButton(resourceBundle.getString(VE_DEFINITION));
		definitionBtn.setFont(currentFont);
		definitionBtn.addActionListener( 
				new ActionListener() {
					/* (non-Javadoc)
					 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
					 */
					public void actionPerformed(ActionEvent e) {
						addEditorDefinition(measure);
					}
				}
		);
		
		datasetBtn = new JButton(resourceBundle.getString(VE_DATASET));
		datasetBtn.setFont(currentFont);
		datasetBtn.addActionListener( 
				new ActionListener() {
					/* (non-Javadoc)
					 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
					 */
					public void actionPerformed(ActionEvent e) {
						addEditorDataset(measure);
					}
				}
		);
		
		emptyLbl = new JLabel("        ");
		emptyLbl.setFont(currentFont);
		
		/* buttonPnl */
		windowButtonSep = new JPanel();
		windowButtonSep.setLayout(new BoxLayout(windowButtonSep, BoxLayout.Y_AXIS));
		windowButtonSep.add(Box.createVerticalStrut(5));
		windowButtonSep.add(new JSeparator(SwingConstants.HORIZONTAL));
		windowButtonSep.add(Box.createVerticalStrut(5));
		windowButtonPnl = new JPanel();
		windowButtonPnl.setLayout(new FlowLayout(FlowLayout.RIGHT));
//		windowButtonPnl.add(saveBtn);
//		windowButtonPnl.add(resetBtn);
		windowButtonPnl.add(commentBtn);
		windowButtonPnl.add(definitionBtn);
		windowButtonPnl.add(datasetBtn);
		windowButtonPnl.add(emptyLbl);
		windowButtonPnl.add(resetBtn);
		
		windowButtonSep.add(windowButtonPnl);
		
		tablePnl	= buildTablePanel();
		
		addBtn		= new JButton(resourceBundle.getString(ADD));
		addBtn.addActionListener( 
				new ActionListener() {
					/* (non-Javadoc)
					 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
					 */
					public void actionPerformed(ActionEvent e) {
						addBtnAction(e);
					}
				}
		);
//		remBtn		= new JButton(resourceBundle.getString(REMOVE));
//		remBtn.addActionListener(
//				new ActionListener() {
//					public void actionPerformed(ActionEvent e) {
//						remBtnAction(e);
//					}
//				}
//		);
		
		tableButtonPnl	= new JPanel(new FlowLayout(FlowLayout.TRAILING));
		tableButtonPnl.add(addBtn);
//		tableButtonPnl.add(remBtn);
		
		buttonPnl = new JPanel(new BorderLayout());
		buttonPnl.add(tableButtonPnl, BorderLayout.NORTH);
		buttonPnl.add(windowButtonSep, BorderLayout.SOUTH);
		
		/* this */
		formular = new JPanel();
		formular.setLayout(new BorderLayout());
		formular.add(metadataSep, BorderLayout.NORTH);
		formular.add(tablePnl, BorderLayout.CENTER);
		formular.add(buttonPnl, BorderLayout.SOUTH);
		
		callHelpBtn = new JButton();
		callHelpBtn.setActionCommand(ActionCommandText.CMD_HELP_HELP_EDIT_MEASUREMENT);
		callHelpBtn.addActionListener(al);
		callHelpBtn.setVisible(false);
		callHelpBtn.setEnabled(true);
		
		fillModel();
	}
	
	/**
	 * @return
	 */
	JPanel buildTablePanel() {
		JPanel panel;
				
		inputTblModel		= buildTableModel();
		inputTbl			= buildTable(inputTblModel);
		inputTbl.getColumnModel().getColumn(CODE_COLUMN).setIdentifier(CODE);
		inputTbl.getColumnModel().getColumn(LABEL_COLUMN).setIdentifier(LABEL);
		inputTbl.getColumnModel().getColumn(IS_MISSING_COLUMN).setIdentifier(IS_MISSING);
		inputTbl.removeColumn(inputTbl.getColumn(CATEGORY_ID));
		inputTbl.removeColumn(inputTbl.getColumn(OBJECT)); 
		inputTbl.removeColumn(inputTbl.getColumn(LINK)); 
		@SuppressWarnings("unused")
		ExcelAdapter myAd	= new ExcelAdapter(inputTbl);
		panel				= new JPanel(new BorderLayout());
		JScrollPane scrollPane = new JScrollPane(inputTbl);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
//		scrollPane.setMinimumSize(new Dimension(VIEWPORT_WIDTH, VIEWPORT_HEIGHT)); // xxx
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		panel.add(
			scrollPane, BorderLayout.CENTER
		);
		
		return panel;
	}
	
	/**
	 * @return
	 */
	DefaultTableModel buildTableModel() {
		
		DefaultTableModel tableModel = new DefaultTableModel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

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
            
		tableModel.addColumn(CATEGORY_ID);
		tableModel.addColumn(OBJECT); /* NEW */
		tableModel.addColumn(resourceBundle.getString(CODE));		
		tableModel.addColumn(resourceBundle.getString(LABEL));
		tableModel.addColumn(resourceBundle.getString(IS_MISSING));
		tableModel.addColumn(LINK); /* NEW */
        
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
		        	if (this.getValueAt(row, col) != null)
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
       
		table.setPreferredScrollableViewportSize(new Dimension(VIEWPORT_WIDTH, VIEWPORT_HEIGHT*4));
		table.setRowHeight(ROW_HEIGHT);
        
        table.setDefaultRenderer(JComponent.class, new JComponentCellRenderer());
		table.setDefaultEditor(JComponent.class, new JComponentCellEditor());
	    
//		table.getModel().addTableModelListener(this);
		table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		
        return table;
	}
	
	class JComponentCellRenderer implements TableCellRenderer {
		
	    public Component getTableCellRendererComponent(JTable table, Object value,
	    		boolean isSelected, boolean hasFocus, int row, int column) {
	        return (JComponent)value;
	    }
	}
		
	/**
	 * @param link
	 * @param cat
	 */
	public void addCategoryRow(CharacteristicLink link, Category cat) {
		this.inputTblModel.addRow(
		new Object[]{
			cat.getEntityID(),
			cat,
			cat.getCode(),
			cat.getLabel(),
			cat.isMissing(),
			link
		});
			
		if (!inputTbl.getAutoCreateRowSorter())
			inputTbl.setAutoCreateRowSorter(true);			
	}
	
	/**
	 * @param e
	 */
	protected void addBtnAction(ActionEvent e) {
		this.addCategoryRow(null, new Category()); 
	}
	
//	protected void remBtnAction(ActionEvent e) {
//		int[] selectedRows = inputTbl.getSelectedRows();
//		
//		for (int index=selectedRows.length-1; index >= 0; index--) {
//			int selectedRow = inputTbl.convertRowIndexToModel(selectedRows[index]);
//			inputTblModel.removeRow(selectedRow);
//		}
//	}

	/**
	 * 
	 */
	private void saveChanges() {
		/* Measurement */
		measure.setName(nameTF.getText());
		measure.setLabel(abbrTF.getText());
//		measure.setType((MeasurementType)typeCB.getSelectedItem());
		measure.setLevel((TypeOfData)levelCB.getSelectedItem());
		measure.setSource((MeasurementSource)sourceCB.getSelectedItem());
		measure.setKind((MeasurementLevel)kindCB.getSelectedItem());
		measure.setIsTemplate(isTemplateChk.isSelected());
		measure.setComment(comment);
		measure.setDefinition(definition);
		
		/* Categories */
		int rowCount = inputTblModel.getRowCount();
		for (int i=0; i<rowCount; i++) {
			if (inputTblModel.getValueAt(i, OBJECT_COLUMN) instanceof Category) {
				Category cat = (Category)inputTblModel.getValueAt(i, OBJECT_COLUMN);
				
				cat.setCode((String)inputTblModel.getValueAt(i, CODE_COLUMN));
				cat.setLabel((String)inputTblModel.getValueAt(i, LABEL_COLUMN));
				cat.setMissing((Boolean)inputTblModel.getValueAt(i, IS_MISSING_COLUMN));
				
				Object obj = inputTblModel.getValueAt(i, LINK_COLUMN);
//				if (cat.getEntityID() < 1) {
				if (!(obj instanceof CharacteristicLink)) {
					CharacteristicLink link = new CharacteristicLink();
					link.setAttribute(measure);
					link.setCharacteristic(cat);
					link.setType(CharacteristicLinkType.CATEGORY);
					
					links.add(link);
				}
				
			}
		}
				
	}
	
	/**
	 * 
	 */
	private void fillModel() {
		/* Measurement */
		nameTF.setText(measure.getName());
		abbrTF.setText(measure.getLabel());
//		typeCB.setSelectedItem(measure.getType());
		levelCB.setSelectedItem(measure.getLevel());
		sourceCB.setSelectedItem(measure.getSource());
		kindCB.setSelectedItem(measure.getKind());
		isTemplateChk.setSelected(measure.isTemplate());
		
		/* Categories */
		int rowCount = inputTblModel.getRowCount();
		for (int i=rowCount-1; i>=0; i--)
			inputTblModel.removeRow(i);
		
		Iterator<CharacteristicLink> it =  links.iterator();
		while (it.hasNext()) {
			CharacteristicLink link = it.next();
			
			if (link.getAttribute().equals(measure)) {
				if (link.getCharacteristic() instanceof Category) {
					Category cat = (Category)link.getCharacteristic();
					
					this.addCategoryRow(link, cat);
				}
			}
		}

	}
	
//	protected void saveBtnAction(ActionEvent e) {
//		saveChanges();
//	}
	
	/**
	 * @param e
	 */
	protected void resetBtnAction(ActionEvent e) {

		measure.setName(mea_clone.getName());
		measure.setLabel(mea_clone.getLabel());
		measure.setLevel(mea_clone.getLevel());
		measure.setSource(mea_clone.getSource());
		measure.setKind(mea_clone.getKind());
		
		List<CharacteristicLink> rem_links = new ArrayList<CharacteristicLink>();
		Iterator<CharacteristicLink> it =  links.iterator();
		while (it.hasNext()) {
			CharacteristicLink link = it.next();
			
			CharacteristicLink clink = getCharacteristicLinkByID(link.getEntityID());
			if (clink != null) {
				if (link.getAttribute().equals(measure)) {
					if (link.getCharacteristic() instanceof Category) {
						Category cat = (Category)link.getCharacteristic();
												
						cat.setCode(((Category)clink.getCharacteristic()).getCode());
						cat.setLabel(((Category)clink.getCharacteristic()).getLabel());
						cat.setMissing(((Category)clink.getCharacteristic()).isMissing());
					}
				}				
			} else {
				rem_links.add(link);
			}
		}
		it =  rem_links.iterator();
		while (it.hasNext()) {
			CharacteristicLink link = it.next();
			
			links.remove(link);
		}
		
		fillModel();
	}
	
	/**
	 * 
	 */
	public void goEdit() {
		final String[] DIALOG_BUTTONS_TITLES	= {resourceBundle.getString(ME_SAVE), resourceBundle.getString(ME_ACCEPT), resourceBundle.getString(ME_HELP)};
		
		int retValue = 2;
		
	  	while (retValue != 1)   
	   	{
	  		retValue = JOptionPane.showOptionDialog(
	  				((CStatsCtrl)fal).getView().getAppFrame(), 
		   			formular,
		   			resourceBundle.getString(ME_TITLE),
		            JOptionPane.OK_OPTION,
		            JOptionPane.PLAIN_MESSAGE,
		            null, 
		            DIALOG_BUTTONS_TITLES, 
		            DIALOG_BUTTONS_TITLES[0]
		       	);
		    
	  		if (retValue == 0) {
	  			saveChanges();
	  		}
	  		
	  		if (retValue == 1) {
				int n = saveMeasureDialog();
				
				switch (n) {
					case 0:			  			
			  			saveChanges();
					case 1:
						break;
				}
	  		}
	  		
	  		if (retValue == 2) {
	  			callHelpBtn.doClick();
	  		}
	   	}		
	}
	

	public int saveMeasureDialog() {
		Object[] options = {resourceBundle.getString("yes"),resourceBundle.getString("no")};
		
		return JOptionPane.showOptionDialog(((CStatsCtrl)fal).getView().getAppFrame(),
				resourceBundle.getString("save_mea_quest"),
				resourceBundle.getString(VE_QUESTION), 
			    JOptionPane.YES_NO_OPTION,
			    JOptionPane.QUESTION_MESSAGE,
			    null,
			    options,
			    options[1]);
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
	}
	
	/**
	 * @param mea
	 */
	public void addEditorComment(Measurement mea) {
		String commentText = null;
		comment = mea.getComment();
		
		String[] argValue = null;
		if (comment != null) {
			argValue = new String[2];
			argValue[0] = comment.getText();
			argValue[1] = comment.getSubject();
		}
		
		/*
		 *	 Open the pop-up window and wait for return value
		 */
		ResourceBundle resourceBundle	= ResourceBundle.getBundle(ERROR_BUNDLE, currentLocale);
		
		String dialogTitle = 
			(!(comment != null) ?  "add_comment" : "edit_comment");	
		Object o = new CommentDialog(((CStatsCtrl)fal).getView().getAppFrame(), resourceBundle.getString(dialogTitle), argValue, false, true, false, true, currentLocale, currentFont).getValue();
		
		/*
		 *	Handle Answer
		 */
		if (o != null) {
			commentText = ((String[])o)[0];
		}
		
		if (commentText != null) {
			if (comment != null) {
				comment.setText(commentText);
			} else {
				comment = new Comment();
				comment.setText(commentText);
				mea.setComment(comment);
			}
		}
	}
	
	/**
	 * @param var
	 */
	public void addEditorDataset(Measurement mea) {
		String dataset	= mea.getDataset();
		String pid		= mea.getPID();
		
		String[] argValue = new String[3];
		argValue[0] = dataset;
		argValue[1] = pid;
		argValue[2] = "";	
		
		/*
		 *	 Open the pop-up window and wait for return value
		 */
		ResourceBundle resourceBundle	= ResourceBundle.getBundle(BUNDLE, currentLocale);
		
		Object o = new DatasetDialog(((CStatsCtrl)fal).getView().getAppFrame(), resourceBundle.getString(VE_DATASET), argValue, false, true, false, true, currentLocale, currentFont).getValue();
		
		/*
		 *	Handle Answer
		 */
		if (o != null) {
			mea.setDataset(((String[])o)[0]);
			mea.setPID(((String[])o)[1]);
		}
	}
	
	public void addEditorDefinition(Measurement mea) {
		String definitionText = null;
		definition = mea.getDefinition();
		
		String argValue = "";
		if (definition != null) {			
			argValue = definition.getText();
			
		}
		
		/*
		 *	 Open the pop-up window and wait for return value
		 */
		ResourceBundle resourceBundle	= ResourceBundle.getBundle(ERROR_BUNDLE, currentLocale);
		
		String dialogTitle = 
			(!(definition != null) ?  "add_definition" : "edit_definition");	
		Object o = new DefinitionDialog(((CStatsCtrl)fal).getView().getAppFrame(), resourceBundle.getString(dialogTitle), argValue, true, false, true, currentLocale, currentFont).getValue();
		
		/*
		 *	Handle Answer
		 */
		if (o != null) {
			definitionText = (String)o;
		}
		
		if (definitionText != null) {
			if (definition != null) {
				definition.setText(definitionText);
			} else {
				definition = new Definition();
				definition.setText(definitionText);
				mea.setDefinition(definition);
			}
		}
	}
	
	public CharacteristicLink getCharacteristicLinkByID(int id) {
		if (lnk_clone != null) {
			Iterator<CharacteristicLink> iterator = lnk_clone.iterator();
							
			while(iterator.hasNext()) {
				CharacteristicLink link = iterator.next();
					
				if (link.getEntityID() == id)
					return link;
			}
		}
	
		return null;
	}

}

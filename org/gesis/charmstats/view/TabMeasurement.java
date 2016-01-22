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
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import org.gesis.charmstats.ActionCommandText;
import org.gesis.charmstats.controller.CStatsCtrl;
import org.gesis.charmstats.model.CStatsModel;
import org.gesis.charmstats.model.Category;
import org.gesis.charmstats.model.Measurement;
import org.gesis.charmstats.model.MeasurementLevel;
import org.gesis.charmstats.model.MeasurementType;
import org.gesis.charmstats.model.Project;
import org.gesis.charmstats.model.TypeOfData;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class TabMeasurement extends Tab implements FocusListener, TableModelListener, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String MISSING				= "missing_measurement";
	
	
	public static final String MSRMNT_NAME_LBL		= "name_of_measurement";
	public static final String MSRMNT_NAME			= "MeasurementName";
	public static final String MSRMNT_NAME_TOOLTIP	= "Measurement Name";
	public static final	String MEA_TARGET_VARIABLE	= "mea_target_variable";
	public static final	String MEA_TARGET_NAME		= "mea_target_name";
	public static final String MEA_TARGET_NAME_TOOLTIP	= "mea_target_name_tt"; 
	public static final	String MEA_TARGET_LABEL		= "mea_target_label";
	public static final String MEA_TARGET_LABEL_TOOLTIP	= "mea_target_label_tt"; 
	public static final String PPRJ_MEA_LVL			= "level_of_measurement";
	public static final String PPRJ_TYPE_OF_DATA	= "type_of_data";
	public static final String INDIVIDUAL			= "Individual";
	public static final String INDIVIDUAL_TOOLTIP	= "Individual";
	public static final String AGGREGATE			= "Aggregate";
	public static final String AGGREGATE_TOOLTIP	= "Aggregate";
	public static final String MESO			= "Meso";
	public static final String MESO_TOOLTIP	= "Meso";
	public static final String PPRJ_MEA_TYP			= "type_of_measurement";
	public static final String CLASS				= "Classification";
	public static final String CLASS_TOOLTIP		= "Classification";
	public static final String INDEX				= "Index";
	public static final String INDEX_TOOLTIP		= "Index";
	public static final String SCALE				= "Scale";
	public static final String SCALE_TOOLTIP		= "Scale";
	public static final String NOMINAL				= "Nominal";
	public static final String NOMINAL_TOOLTIP		= "Nominal";
	public static final String ORDINAL				= "Ordinal";
	public static final String ORDINAL_TOOLTIP		= "Ordinal";
	public static final String INTERVAL				= "Interval";
	public static final String INTERVAL_TOOLTIP		= "Interval";
	public static final String RATIO				= "Ratio";
	public static final String RATIO_TOOLTIP		= "Ratio";
	public static final String CONTINUOUS			= "Continuous";
	public static final String CONTINUOUS_TOOLTIP	= "Continuous";
	/* ToolTip: */
	public static final String IMPORT_TOOLTIP		= "import_tt";
	public static final String QUICKCHARM_TOOLTIP	= "autocomplete_tt";
	
	public static final String CATEGORY_EDITOR		= "CategoryEditor";
	
	public static final String MEA_CLASS			= "mea_class";
	public static final String MEA_INDEX			= "mea_index";
	public static final String MEA_SCALE			= "mea_scale";
	
	public static final String MEA_IND				= "mea_ind";
	public static final String MEA_AGG				= "mea_agg";
	public static final String MEA_MES				= "mea_mes";
	
	public static final String MEA_NOM				= "mea_nom";
	public static final String MEA_ORD				= "mea_ord";
	public static final String MEA_INT				= "mea_int";
	public static final String MEA_RAT				= "mea_rat";
	public static final String MEA_CON				= "mea_con";
	
	private static final String CATEGORY_ID			= "Category_ID";
	private static final int	CATEGORY_ID_COLUMN	= 0;
	private static final String OBJECT				= "Object";
	private static final int	OBJECT_COLUMN		= 1;
	private static final String CODE				= "code_col";
	private static final int	CODE_COLUMN			= 2;
	private static final String LABEL				= "label_col";
	private static final int 	LABEL_COLUMN		= 3;
	private static final String IS_MISSING			= "is_missing_col";
	private static final int	IS_MISSING_COLUMN	= 4;
	
	
	private static final int 	VIEWPORT_WIDTH		= 600;
	private static final int 	VIEWPORT_HEIGHT		=  94;							
	private static final int 	ROW_HEIGHT			=  28; 
	
	
	
	private String 				ADD					= "tm_add_btn";
	private String 				REMOVE				= "tm_remove_btn";
	
	/*
	 *	Fields
	 */
	JPanel			targetNameLabelPane;
	JLabel			targetNameLbl;
	JTextField		targetNameTF;
	JLabel			targetLabelLbl;
	JTextField		targetLabelTF;

	JPanel			msrmntNamePane;
	JLabel			msrmntNameLbl;
	JTextField		msrmntNameTF;
	
	JPanel			msrmntLevelPane;
	ButtonGroup		msrmntLevelBtnGrp;
	JRadioButton	individualRadioBtn;
	JRadioButton	aggregateRadioBtn;
//	JRadioButton	mesoRadioBtn;
	
	JPanel			msrmntTypePane;
	ButtonGroup		msrmntTypeBtnGrp;
	JRadioButton	classRadioBtn;
	JRadioButton	indexRadioBtn;
	JRadioButton	scaleRadioBtn;
	
	JPanel			msrmntKindPane;
	ButtonGroup		msrmntKindBtnGrp;
	JRadioButton	nominalRadioBtn;
	JRadioButton	ordinalRadioBtn;
	JRadioButton	intervalRadioBtn;
	JRadioButton	ratioRadioBtn;
	JRadioButton	continuousRadioBtn;
	
	JPanel				editorPnl;
	JPanel				tablePnl;
	JTable				inputTbl;
	DefaultTableModel	inputTblModel;
	JPanel				buttonPnl;
	JButton				addBtn;
	JButton				remBtn;
	
	int returnValue;
	
	Font				currentFont;
	
	CStatsGUI localGUI;
	
	/*
	 *	Constructor
	 */
	/**
	 * @param locale
	 */
	public TabMeasurement(Locale locale) {
		super(locale);
		
		setName("TabMeasurement");
	}
	
	/**
	 * @param al
	 * @param locale
	 * @param gui
	 * @param ctrl
	 * @param addenda
	 */
	public TabMeasurement(ActionListener al, Locale locale, CStatsGUI gui, CStatsCtrl ctrl, Object addenda) {
		this(locale);
		
		currentLocale	= locale;
		resourceBundle	= ResourceBundle.getBundle(BUNDLE, currentLocale);
		currentFont		= this.getFont();
		
		/* Create Form Components */
		localGUI = gui;
		
		targetNameLbl = new JLabel();
		targetNameLbl.setText(resourceBundle.getString(MEA_TARGET_NAME) + ":");
		
		targetNameTF = new JTextField(32);
		targetNameTF.setText("");
		targetNameTF.setName(MEA_TARGET_NAME);
		targetNameTF.addFocusListener(this);
		
		if (addenda instanceof Project)
			targetNameTF.setText(((Project)addenda).getTargetName());
		
		targetLabelLbl = new JLabel();
		targetLabelLbl.setText(" - " + resourceBundle.getString(MEA_TARGET_LABEL) + ":");
		
		targetLabelTF = new JTextField(32);
		targetLabelTF.setText("");
		targetLabelTF.setName(MEA_TARGET_LABEL);
		targetLabelTF.addFocusListener(this);
		
		if (addenda instanceof Project)
			targetLabelTF.setText(((Project)addenda).getTargetLabel());
		
		targetNameLabelPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
		targetNameLabelPane.add(targetNameLbl);
		targetNameLabelPane.add(targetNameTF);
		targetNameLabelPane.add(targetLabelLbl);
		targetNameLabelPane.add(targetLabelTF);
		
		targetNameLabelPane.setBorder(
				BorderFactory.createCompoundBorder(
					BorderFactory.createTitledBorder(resourceBundle.getString(MEA_TARGET_VARIABLE)),
					BorderFactory.createEmptyBorder(5, 5, 5, 5)
				)
			);
		
		msrmntNameTF = new JTextField(32);
		msrmntNameTF.setText("");
		msrmntNameTF.setName(MSRMNT_NAME);
		msrmntNameTF.setToolTipText(MSRMNT_NAME_TOOLTIP);
		msrmntNameTF.addFocusListener(this);
		
		if (addenda instanceof Project)
			msrmntNameTF.setText(((Project)addenda).getContent().getMeasurement().getName());
		
		msrmntNamePane = new JPanel(new FlowLayout(FlowLayout.LEFT));
		msrmntNamePane.add(msrmntNameTF);
		
		msrmntNamePane.setBorder(
			BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(resourceBundle.getString(MSRMNT_NAME_LBL)),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)
			)
		);
				
		individualRadioBtn = new JRadioButton(INDIVIDUAL);
		individualRadioBtn.setName(INDIVIDUAL);
		individualRadioBtn.setToolTipText(INDIVIDUAL_TOOLTIP);
		individualRadioBtn.setMnemonic(KeyEvent.VK_I);	
		individualRadioBtn.setActionCommand(INDIVIDUAL);
		individualRadioBtn.addActionListener(this);
		individualRadioBtn.setSelected(false);
		
		aggregateRadioBtn = new JRadioButton(AGGREGATE);
		aggregateRadioBtn.setName(AGGREGATE);
		aggregateRadioBtn.setToolTipText(AGGREGATE_TOOLTIP);
		aggregateRadioBtn.setMnemonic(KeyEvent.VK_A);	
		aggregateRadioBtn.setActionCommand(AGGREGATE);
		aggregateRadioBtn.addActionListener(this);
		aggregateRadioBtn.setSelected(true);
		
//		mesoRadioBtn = new JRadioButton(MESO);
//		mesoRadioBtn.setName(MESO);
//		mesoRadioBtn.setToolTipText(MESO_TOOLTIP);
//		mesoRadioBtn.setMnemonic(KeyEvent.VK_M);	
//		mesoRadioBtn.setActionCommand(MESO);
//		mesoRadioBtn.addActionListener(this);
//		mesoRadioBtn.setSelected(true);
		
		msrmntLevelBtnGrp = new ButtonGroup();		
		msrmntLevelBtnGrp.add(individualRadioBtn);
		msrmntLevelBtnGrp.add(aggregateRadioBtn);
//		msrmntLevelBtnGrp.add(mesoRadioBtn);

		msrmntLevelPane = new JPanel(new FlowLayout(FlowLayout.LEADING));		
		msrmntLevelPane.add(individualRadioBtn);
		msrmntLevelPane.add(aggregateRadioBtn);
//		msrmntLevelPane.add(mesoRadioBtn);
		
		msrmntLevelPane.setBorder(
			BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(resourceBundle.getString(PPRJ_TYPE_OF_DATA)), 
				BorderFactory.createEmptyBorder(5, 5, 5, 5)
			)
		);
		
		if ((addenda instanceof Project) &&
				((Project)addenda).getContent().getMeasurement().getLevel().equals(TypeOfData.INDIVIDUAL))
			individualRadioBtn.setSelected(true);
		
		classRadioBtn = new JRadioButton(CLASS);
		classRadioBtn.setName(CLASS);
		classRadioBtn.setToolTipText(CLASS_TOOLTIP);
		classRadioBtn.setMnemonic(KeyEvent.VK_C);	
		classRadioBtn.setActionCommand(CLASS);
		classRadioBtn.addActionListener(this);
		classRadioBtn.setSelected(true);
		
		if ((addenda instanceof Project) &&
				((Project)addenda).getContent().getMeasurement().getType().equals(MeasurementType.CLASSIFICATION))
			classRadioBtn.setSelected(true);
		
		indexRadioBtn = new JRadioButton(INDEX);
		indexRadioBtn.setName(INDEX);
		indexRadioBtn.setToolTipText(INDEX_TOOLTIP);
		indexRadioBtn.setMnemonic(KeyEvent.VK_I);
		indexRadioBtn.setActionCommand(INDEX);
		indexRadioBtn.addActionListener(this);
		
		if ((addenda instanceof Project) &&
				((Project)addenda).getContent().getMeasurement().getType().equals(MeasurementType.INDEX))
			indexRadioBtn.setSelected(true);
		
		scaleRadioBtn = new JRadioButton(SCALE);
		scaleRadioBtn.setName(SCALE);
		scaleRadioBtn.setToolTipText(SCALE_TOOLTIP);
		scaleRadioBtn.setMnemonic(KeyEvent.VK_S);
		scaleRadioBtn.setActionCommand(SCALE);
		scaleRadioBtn.addActionListener(this);
		
		if ((addenda instanceof Project) &&
				((Project)addenda).getContent().getMeasurement().getType().equals(MeasurementType.SCALE))
			scaleRadioBtn.setSelected(true);

		msrmntTypeBtnGrp = new ButtonGroup();		
		msrmntTypeBtnGrp.add(classRadioBtn);
		msrmntTypeBtnGrp.add(indexRadioBtn);
		msrmntTypeBtnGrp.add(scaleRadioBtn);

		msrmntTypePane = new JPanel(new FlowLayout(FlowLayout.LEADING));		
		msrmntTypePane.add(classRadioBtn);
		msrmntTypePane.add(indexRadioBtn);
		msrmntTypePane.add(scaleRadioBtn);
		msrmntTypePane.setVisible(false);
		
		msrmntTypePane.setBorder(
			BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(resourceBundle.getString(PPRJ_MEA_TYP)),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)
			)
		);
		
		nominalRadioBtn = new JRadioButton();
		nominalRadioBtn.setName(NOMINAL);
		nominalRadioBtn.setToolTipText(NOMINAL_TOOLTIP);
		nominalRadioBtn.setActionCommand(NOMINAL);
		nominalRadioBtn.addActionListener(this);
		
		if ((addenda instanceof Project) &&
				((Project)addenda).getContent().getMeasurement().getKind().equals(MeasurementLevel.NOMINAL))
			nominalRadioBtn.setSelected(true);
		
		ordinalRadioBtn = new JRadioButton();
		ordinalRadioBtn.setName(ORDINAL);
		ordinalRadioBtn.setToolTipText(ORDINAL_TOOLTIP);
		ordinalRadioBtn.setActionCommand(ORDINAL);
		ordinalRadioBtn.addActionListener(this);
		
		if ((addenda instanceof Project) &&
				((Project)addenda).getContent().getMeasurement().getKind().equals(MeasurementLevel.ORDINAL))
			ordinalRadioBtn.setSelected(true);
		
		intervalRadioBtn = new JRadioButton();
		intervalRadioBtn.setName(INTERVAL);
		intervalRadioBtn.setToolTipText(INTERVAL_TOOLTIP);
		intervalRadioBtn.setActionCommand(INTERVAL);
		intervalRadioBtn.addActionListener(this);
		
		if ((addenda instanceof Project) &&
				((Project)addenda).getContent().getMeasurement().getKind().equals(MeasurementLevel.INTERVAL))
			intervalRadioBtn.setSelected(true);
		
		ratioRadioBtn = new JRadioButton();
		ratioRadioBtn.setName(RATIO);
		ratioRadioBtn.setToolTipText(RATIO_TOOLTIP);
		ratioRadioBtn.setActionCommand(RATIO);
		ratioRadioBtn.addActionListener(this);
		
		if ((addenda instanceof Project) &&
				((Project)addenda).getContent().getMeasurement().getKind().equals(MeasurementLevel.RATIO))
			ratioRadioBtn.setSelected(true);
		
		continuousRadioBtn = new JRadioButton();
		continuousRadioBtn.setName(CONTINUOUS);
		continuousRadioBtn.setToolTipText(CONTINUOUS_TOOLTIP);
		continuousRadioBtn.setActionCommand(CONTINUOUS);
		continuousRadioBtn.addActionListener(this);
		
		if ((addenda instanceof Project) &&
				((Project)addenda).getContent().getMeasurement().getKind().equals(MeasurementLevel.CONTINUOUS))
			continuousRadioBtn.setSelected(true);
		
		msrmntKindBtnGrp = new ButtonGroup();		
		msrmntKindBtnGrp.add(nominalRadioBtn);
		msrmntKindBtnGrp.add(ordinalRadioBtn);
		msrmntKindBtnGrp.add(intervalRadioBtn);
		msrmntKindBtnGrp.add(ratioRadioBtn);
		msrmntKindBtnGrp.add(continuousRadioBtn);
		
		msrmntKindPane = new JPanel(new FlowLayout(FlowLayout.LEADING));		
		msrmntKindPane.add(nominalRadioBtn);
		msrmntKindPane.add(ordinalRadioBtn);
		msrmntKindPane.add(intervalRadioBtn);
		msrmntKindPane.add(ratioRadioBtn);
		msrmntKindPane.add(continuousRadioBtn);
		
		msrmntKindPane.setBorder(
				BorderFactory.createCompoundBorder(
					BorderFactory.createTitledBorder(resourceBundle.getString(PPRJ_MEA_TYP)),
					BorderFactory.createEmptyBorder(5, 5, 5, 5)
				)
			);
				
		JLabel blankLbl		= new JLabel("        ");
		JPanel blankPanel	= new JPanel();
		blankPanel.add(blankLbl);
		
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
		remBtn		= new JButton(resourceBundle.getString(REMOVE));
		remBtn.addActionListener(
				new ActionListener() {
					/* (non-Javadoc)
					 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
					 */
					public void actionPerformed(ActionEvent e) {
						remBtnAction(e);
					}
				}
		);
		
		buttonPnl	= new JPanel(new FlowLayout(FlowLayout.TRAILING));
		buttonPnl.add(addBtn);
		buttonPnl.add(remBtn);
				
		editorPnl 	= new JPanel(new BorderLayout());
		editorPnl.add(tablePnl, BorderLayout.CENTER);
		editorPnl.add(buttonPnl, BorderLayout.SOUTH);
		
		
		SwingUtilities.invokeLater(new Runnable() {
		    /* (non-Javadoc)
		     * @see java.lang.Runnable#run()
		     */
		    public void run() {
		    	msrmntNameTF.requestFocus();
		    }
		});
		
		/* Add Form Components to Form Panel */
		formPanel.add(targetNameLabelPane);
		formPanel.add(msrmntLevelPane);
		formPanel.add(msrmntTypePane);
		formPanel.add(msrmntKindPane);
		formPanel.add(blankPanel);
		formPanel.add(msrmntNamePane);
		formPanel.add(editorPnl);
		
		/* Setup Buttons */
		backButton.setActionCommand(ActionCommandText.BTN_TGT_STP_TGT_TAB_BACK);
		backButton.addActionListener(al);
		autoCompleteButton.setVisible(true);
		autoCompleteButton.setEnabled(false);
		autoCompleteButton.setActionCommand(ActionCommandText.BTN_TGT_STP_TGT_TAB_AUTO);
		autoCompleteButton.addActionListener(al);
		noteButton.setActionCommand(ActionCommandText.BTN_TGT_STP_TGT_TAB_NOTE);
		noteButton.addActionListener(al);
		importButton.setActionCommand(ActionCommandText.BTN_TGT_STP_TGT_TAB_IMP);
		importButton.addActionListener(al);
		importButton.setVisible(true);
		importButton.setEnabled(false);
		nextButton.setActionCommand(ActionCommandText.BTN_TGT_STP_TGT_TAB_NEXT);
		nextButton.addActionListener(al);
		// disabled in QuickCharmStats:
		nextButton.setEnabled(false);
				
		setPanelIdx(1); // 0
		
		changeLanguage(locale);
	}

	/*
	 *	Methods
	 */
	/**
	 * @return
	 */
	public JButton getNextButton() {
		return nextButton;
	}
	
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.Tab#changeLanguage(java.util.Locale)
	 */
	public void changeLanguage(Locale locale) {
		super.changeLanguage(locale);
		
		ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, locale);
		targetNameLabelPane.setBorder(
				BorderFactory.createCompoundBorder(
					BorderFactory.createTitledBorder(bundle.getString(MEA_TARGET_VARIABLE)),
					BorderFactory.createEmptyBorder(5, 5, 5, 5)
				)
			);
		targetNameLbl.setText(bundle.getString(MEA_TARGET_NAME) + ":");
		targetLabelLbl.setText(" - " + bundle.getString(MEA_TARGET_LABEL) + ":");
		msrmntNamePane.setBorder(
			BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(bundle.getString(MSRMNT_NAME_LBL)),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)
			)
		);
		msrmntLevelPane.setBorder(
			BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(bundle.getString(PPRJ_TYPE_OF_DATA)), //PPRJ_MEA_LVL)),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)
			)
		);
		msrmntTypePane.setBorder(
			BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(bundle.getString(PPRJ_MEA_TYP)),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)
			)
		);
		msrmntKindPane.setBorder(
				BorderFactory.createCompoundBorder(
					BorderFactory.createTitledBorder(bundle.getString(PPRJ_MEA_TYP)),
					BorderFactory.createEmptyBorder(5, 5, 5, 5)
				)
			);
		for (int i=0; i<=2; i++)
			inputTbl.getTableHeader().getColumnModel().getColumn(i).setHeaderValue(
				bundle.getString((String)inputTbl.getTableHeader().getColumnModel().getColumn(i).getIdentifier()));
		
		addBtn.setText(bundle.getString(ADD));
		remBtn.setText(bundle.getString(REMOVE));
		
		classRadioBtn.setText(bundle.getString(MEA_CLASS));
		indexRadioBtn.setText(bundle.getString(MEA_INDEX));
		scaleRadioBtn.setText(bundle.getString(MEA_SCALE));
		
		individualRadioBtn.setText(bundle.getString(MEA_IND));
		aggregateRadioBtn.setText(bundle.getString(MEA_AGG));
//		mesoRadioBtn.setText(bundle.getString(MEA_MES));
		
		nominalRadioBtn.setText(bundle.getString(MEA_NOM));
		ordinalRadioBtn.setText(bundle.getString(MEA_ORD));
		intervalRadioBtn.setText(bundle.getString(MEA_INT));
		ratioRadioBtn.setText(bundle.getString(MEA_RAT));
		continuousRadioBtn.setText(bundle.getString(MEA_CON));
		
		/* Tooltip: */
		targetNameTF.setToolTipText(bundle.getString(MEA_TARGET_NAME_TOOLTIP));
		targetLabelTF.setToolTipText(bundle.getString(MEA_TARGET_LABEL_TOOLTIP));
		importButton.setToolTipText(bundle.getString(IMPORT_TOOLTIP));
		autoCompleteButton.setToolTipText(bundle.getString(QUICKCHARM_TOOLTIP));

	}
	
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.Tab#changeFont(java.awt.Font)
	 */
	public void changeFont(Font f) {
		super.changeFont(f);
		
		targetNameLbl.setFont(f);
		targetLabelLbl.setFont(f);
		
		targetNameTF.setFont(f);
		targetLabelTF.setFont(f);
		msrmntNameTF.setFont(f);

		currentFont = f;
		
		inputTbl.getTableHeader().setFont(f);
		inputTbl.setFont(f);
		
		for (int column=0; column<inputTbl.getModel().getColumnCount(); column++) 
			for (int row=0; row<inputTbl.getModel().getRowCount(); row++) {
				Object o = inputTbl.getModel().getValueAt(row, column);
				
				if (o instanceof JComboBox)
					((JComboBox)o).setFont(f);
			}
		
		addBtn.setFont(f);
		remBtn.setFont(f);
		
		classRadioBtn.setFont(f);
		indexRadioBtn.setFont(f);
		scaleRadioBtn.setFont(f);
		
		individualRadioBtn.setFont(f);
		aggregateRadioBtn.setFont(f);
//		mesoRadioBtn.setFont(f);
		
		nominalRadioBtn.setFont(f);
		ordinalRadioBtn.setFont(f);
		intervalRadioBtn.setFont(f);
		ratioRadioBtn.setFont(f);
		continuousRadioBtn.setFont(f);
		
		UIManager.put("TitledBorder.font", f);
		javax.swing.SwingUtilities.updateComponentTreeUI(targetNameLabelPane);
		javax.swing.SwingUtilities.updateComponentTreeUI(msrmntNamePane);
		javax.swing.SwingUtilities.updateComponentTreeUI(msrmntLevelPane);
		javax.swing.SwingUtilities.updateComponentTreeUI(msrmntTypePane);
		javax.swing.SwingUtilities.updateComponentTreeUI(msrmntKindPane);
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
			
			Measurement measure = new Measurement();
			measure.setName("");
			measure.setType(MeasurementType.CLASSIFICATION);
			measure.setLevel(TypeOfData.INDIVIDUAL);
			measure.setKind(MeasurementLevel.NOMINAL);
			
			model.getProject().getContent().setMeasurement(measure);
		}
		
		fillModel(model);
	}
	
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.Tab#fillModel(org.gesis.charmstats.model.CStatsModel)
	 */
	public void fillModel(CStatsModel model) {
		Project project;
		
		if (model instanceof CStatsModel)
			project = ((CStatsModel)model).getProject();
		else
			project = new Project();
		
		targetNameTF.setText(project.getTargetName());
		targetLabelTF.setText(project.getTargetLabel());
		
		msrmntNameTF.setText(project.getContent().getMeasurement().getName());

		if (project.getContent().getMeasurement().getType().equals(MeasurementType.CLASSIFICATION))
			classRadioBtn.setSelected(true);			
		if (project.getContent().getMeasurement().getType().equals(MeasurementType.INDEX))
			indexRadioBtn.setSelected(true);
		if (project.getContent().getMeasurement().getType().equals(MeasurementType.SCALE))
			scaleRadioBtn.setSelected(true);

		if (project.getContent().getMeasurement().getLevel().equals(TypeOfData.INDIVIDUAL))
			individualRadioBtn.setSelected(true);
		if (project.getContent().getMeasurement().getLevel().equals(TypeOfData.AGGREGATE))
			aggregateRadioBtn.setSelected(true);
//		if (project.getContent().getMeasurement().getLevel().equals(TypeOfData.MESO))
//			mesoRadioBtn.setSelected(true);
		
		if (project.getContent().getMeasurement().getKind().equals(MeasurementLevel.NOMINAL))
			nominalRadioBtn.setSelected(true);
		if (project.getContent().getMeasurement().getKind().equals(MeasurementLevel.ORDINAL))
			ordinalRadioBtn.setSelected(true);
		if (project.getContent().getMeasurement().getKind().equals(MeasurementLevel.INTERVAL))
			intervalRadioBtn.setSelected(true);
		if (project.getContent().getMeasurement().getKind().equals(MeasurementLevel.RATIO))
			ratioRadioBtn.setSelected(true);
		if (project.getContent().getMeasurement().getKind().equals(MeasurementLevel.CONTINUOUS))
			continuousRadioBtn.setSelected(true);
		
		removeAllRows();
		
		if (!project.getContent().getCategories().isEmpty()) {
			ArrayList<Category> categories = project.getContent().getCategories();
			
			Iterator<Category> it_cat = categories.iterator();			
			while (it_cat.hasNext()) {
				Category cat = it_cat.next();
				
				addCategoryRow(cat); 
			}
		}
				
		if (inputTblModel.getRowCount() == 0)
			this.addCategoryRow(new Category()); /* NEW */ 
		
		if ((model.getProject().getFinishedSince() != null) ||	
				(!model.getProject().isEditedByUser())) {		
			targetNameTF.setEnabled(false);
			targetLabelTF.setEnabled(false);
			msrmntNameTF.setEnabled(false);
			classRadioBtn.setEnabled(false);			
			indexRadioBtn.setEnabled(false);
			scaleRadioBtn.setEnabled(false);
			individualRadioBtn.setEnabled(false);
			aggregateRadioBtn.setEnabled(false);
//			mesoRadioBtn.setEnabled(false);
			nominalRadioBtn.setEnabled(false);
			ordinalRadioBtn.setEnabled(false);
			intervalRadioBtn.setEnabled(false);
			ratioRadioBtn.setEnabled(false);
			continuousRadioBtn.setEnabled(false);
			inputTbl.setEnabled(false);
			addBtn.setEnabled(false);
			remBtn.setEnabled(false);
		} else {
			targetNameTF.setEnabled(true);
			targetLabelTF.setEnabled(true);
			msrmntNameTF.setEnabled(true);
			classRadioBtn.setEnabled(true);			
			indexRadioBtn.setEnabled(true);
			scaleRadioBtn.setEnabled(true);
			individualRadioBtn.setEnabled(true);
			aggregateRadioBtn.setEnabled(true);
//			mesoRadioBtn.setEnabled(true);
			nominalRadioBtn.setEnabled(true);
			ordinalRadioBtn.setEnabled(true);
			intervalRadioBtn.setEnabled(true);
			ratioRadioBtn.setEnabled(true);
			continuousRadioBtn.setEnabled(true);
			inputTbl.setEnabled(true);
			addBtn.setEnabled(true);
			remBtn.setEnabled(true);			
		}
		
		if (project.isMeasurementImported()) {
			msrmntNameTF.setEnabled(false);
			classRadioBtn.setEnabled(false);			
			indexRadioBtn.setEnabled(false);
			scaleRadioBtn.setEnabled(false);
			individualRadioBtn.setEnabled(false);
			aggregateRadioBtn.setEnabled(false);
//			mesoRadioBtn.setEnabled(false);
			nominalRadioBtn.setEnabled(false);
			ordinalRadioBtn.setEnabled(false);
			intervalRadioBtn.setEnabled(false);
			ratioRadioBtn.setEnabled(false);
			continuousRadioBtn.setEnabled(false);
			inputTbl.setEnabled(false);
			addBtn.setEnabled(false);
			remBtn.setEnabled(false);
		}
	}
	
	/**
	 * @param e
	 */
	protected void addBtnAction(ActionEvent e) {
		this.addCategoryRow(new Category()); 
	}
	
	/**
	 * @param e
	 */
	protected void remBtnAction(ActionEvent e) {
		int[] selectedRows = inputTbl.getSelectedRows();
		
		for (int index=selectedRows.length-1; index >= 0; index--) {
			int selectedRow = inputTbl.convertRowIndexToModel(selectedRows[index]);
			inputTblModel.removeRow(selectedRow);
		}
	}
	
	/**
	 * 
	 */
	protected void removeAllRows() {
		int rowCount = inputTblModel.getRowCount();
		for (int i=rowCount-1; i>-1; i--) {
			inputTblModel.removeRow(i); 
		}
	}
	
	/**
	 * @return
	 */
	JPanel buildTablePanel() {
		JPanel panel;
				
		inputTblModel		= buildTableModel();
		inputTbl			= buildTable(inputTblModel);
		inputTbl.setName(CATEGORY_EDITOR);
		inputTbl.getColumnModel().getColumn(CODE_COLUMN).setIdentifier(CODE);
		inputTbl.getColumnModel().getColumn(LABEL_COLUMN).setIdentifier(LABEL);
		inputTbl.getColumnModel().getColumn(IS_MISSING_COLUMN).setIdentifier(IS_MISSING);
		inputTbl.removeColumn(inputTbl.getColumn(CATEGORY_ID));
		inputTbl.removeColumn(inputTbl.getColumn(OBJECT)); /* NEW */
//		@SuppressWarnings("unused")
//		ExcelAdapter myAd	= new ExcelAdapter(inputTbl);
		panel				= new JPanel(new BorderLayout());
		JScrollPane scrollPane = new JScrollPane(inputTbl);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
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
       
		table.setPreferredScrollableViewportSize(new Dimension(VIEWPORT_WIDTH, VIEWPORT_HEIGHT));
		table.setRowHeight(ROW_HEIGHT);
        
        table.setDefaultRenderer(JComponent.class, new JComponentCellRenderer());
		table.setDefaultEditor(JComponent.class, new JComponentCellEditor());
	    
		table.getModel().addTableModelListener(this);
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
	 * @param cat
	 */
	public void addCategoryRow(Category cat) {
		this.inputTblModel.addRow(
		new Object[]{
			cat.getEntityID(),
			cat,
			cat.getCode(),
			cat.getLabel(),
			cat.isMissing()
		});
			
	if (!inputTbl.getAutoCreateRowSorter())
		inputTbl.setAutoCreateRowSorter(true);			
	}
	
	
	/* (non-Javadoc)
	 * @see javax.swing.event.TableModelListener#tableChanged(javax.swing.event.TableModelEvent)
	 */
	public void tableChanged(TableModelEvent e) {
        int row = e.getFirstRow();
        int column = e.getColumn();
        
        TableModel model = (TableModel)e.getSource();
        
        if ((row > -1) && (column > -1)) {                  	
        	Object data = model.getValueAt(row, column);
        	
        	if (data != null) {    		
	        	switch (column) {
	        		case CATEGORY_ID_COLUMN:
	        			break;
	        		case OBJECT_COLUMN:
	        			break;
	        		case CODE_COLUMN:
	        			break;
	        		case LABEL_COLUMN:
	        			break;
	        		case IS_MISSING_COLUMN:
	        			break;
	        		default:
	        	}
        	}   	
        }

    }
	
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.Tab#focusGained(java.awt.event.FocusEvent)
	 */
	@Override
	public void focusGained(FocusEvent e) {
		if(e.getSource() instanceof JTextField) {
			((JTextField)e.getSource()).setBackground(Color.yellow);
		}
	}

	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.Tab#focusLost(java.awt.event.FocusEvent)
	 */
	@Override
	public void focusLost(FocusEvent e) {
		
		if(e.getSource() instanceof JTextField) {
			((JTextField)e.getSource()).setBackground(Color.white);
						
			if ((msrmntNameTF.getText() == null) ||
					(msrmntNameTF.getText().isEmpty())) {
				
				if ((e.getSource() instanceof JTextField) &&
						(!((JTextField)e.getSource()).getName().equals("MeasurementName")))
					return;
				
				if (!(e.getOppositeComponent() != null))
					return;
				
				if ((e.getOppositeComponent() instanceof JButton) && 
						(((JButton)e.getOppositeComponent()).getName() != null) &&
						(((JButton)e.getOppositeComponent()).getName().equals("cancel")))
					return;
				
				resourceBundle	= ResourceBundle.getBundle(BUNDLE, currentLocale);
				JOptionPane.showMessageDialog(this, resourceBundle.getString(MISSING));
				javax.swing.SwingUtilities.invokeLater(new Runnable() {
					/* (non-Javadoc)
					 * @see java.lang.Runnable#run()
					 */
					public void run() {
						msrmntNameTF.requestFocusInWindow(); 
					}}
				);		
			}
				
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(CLASS))
			editorPnl.setVisible(true);
			
		if (e.getActionCommand().equals(INDEX)) {
			editorPnl.setVisible(false);
			for (int i=inputTblModel.getRowCount(); i>0; i--)
				inputTblModel.removeRow(i-1);
		}
			
		if (e.getActionCommand().equals(SCALE)) {
			editorPnl.setVisible(false);
			for (int i=inputTblModel.getRowCount(); i>0; i--)
				inputTblModel.removeRow(i-1);
		}
		
		this.revalidate();
		this.repaint();
	}

}

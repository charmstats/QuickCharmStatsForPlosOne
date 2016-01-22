package org.gesis.charmstats.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.gesis.charmstats.ActionCommandText;
import org.gesis.charmstats.controller.CStatsCtrl;
import org.gesis.charmstats.model.ConDimension;
import org.gesis.charmstats.model.Concept;
import org.gesis.charmstats.model.Measurement;
import org.gesis.charmstats.model.OperaIndicator;
import org.gesis.charmstats.model.Project;
import org.gesis.charmstats.model.Variable;
import org.gesis.charmstats.persistence.DBEntity;
import org.gesis.charmstats.persistence.EntityType;
import org.gesis.charmstats.persistence.SearchBooleanOperator;
import org.gesis.charmstats.persistence.SearchCBResource;
import org.gesis.charmstats.persistence.SearchCBResource.ConceptScope;
import org.gesis.charmstats.persistence.SearchCBResource.DimensionScope;
import org.gesis.charmstats.persistence.SearchCBResource.IndicatorScope;
import org.gesis.charmstats.persistence.SearchCBResource.MeasurementScope;
import org.gesis.charmstats.persistence.SearchCBResource.ProjectScope;
import org.gesis.charmstats.persistence.SearchCBResource.QuestionScope;
import org.gesis.charmstats.persistence.SearchCBResource.SearchObject;
import org.gesis.charmstats.persistence.SearchCBResource.UniverseScope;
import org.gesis.charmstats.persistence.SearchCBResource.VariableScope;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1 / CharmStatsPro only
 *
 */
public class TabSearchVariable extends Tab implements FocusListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String TSV_TITLE						= "tsv_search";
	
	public static final String TSV_SELECT_LBL					= "tsv_select";
	public static final String TSV_WHERE_LBL					= "tsv_where";
	
	public static final String ADD_SEARCH_TERM_BTN 				= "+"; // "Add";
	public static final String DEL_SEARCH_TERM_BTN 				= "X"; // "Delete";
	
	public static final String TSV_SEARCH_TERM_TOOLTIP			= "tsv_search_term";
	public static final String TSV_ADD_SEARCH_TERM_BTN_TOOLTIP	= "tsv_add_new_search_term";
	public static final String TSV_DEL_SEARCH_TERM_BTN_TOOLTIP	= "tsv_delete_this_line";
	
	public static final String TSV_SEARCH_BTN					= "tsv_search_btn";
	public static final String TSV_SAVE_QUERY_BTN				= "tsv_save_query_btn";
	public static final String TSV_SAVE_RESULT_BTN				= "tsv_save_result_btn";
	
	public static final String TSV_PRO_TYPE_LBL					= "tsv_project_type_lbl";
	public static final String TSV_HARMONIZATION				= "tsv_harmonization";
	public static final String TSV_HARMONIZATION_TOOLTIP		= "tsv_harmonization_tt";
	public static final String TSV_DOCUMENTATION				= "tsv_documentation";
	public static final String TSV_DOCUMENTATION_TOOLTIP		= "tsv_documentation_tt";
	public static final String TSV_CONCEPTUAL_BASIS				= "tsv_conceptual_basis";
	public static final String TSV_CONCEPTUAL_BASIS_TOOLTIP		= "tsv_conceptual_basis_tt";
	public static final String TSV_TYPE_OF_MSRMNT_LBL			= "tsv_type_of_measurement_lbl";
	public static final String TSV_NOMINAL						= "tsv_nominal";
	public static final String TSV_NOMINAL_TOOLTIP				= "tsv_nominal_tt";
	public static final String TSV_ORDINAL						= "tsv_ordinal";
	public static final String TSV_ORDINAL_TOOLTIP				= "tsv_ordinal_tt";
	public static final String TSV_INTERVAL						= "tsv_interval";
	public static final String TSV_INTERVAL_TOOLTIP				= "tsv_interval_tt";
	public static final String TSV_RATIO						= "tsv_ratio";
	public static final String TSV_RATIO_TOOLTIP				= "tsv_ratio_tt";
	public static final String TSV_CONTINUOUS					= "tsv_continuous";
	public static final String TSV_CONTINUOUS_TOOLTIP			= "tsv_continuous_tt";

	public static final String TSV_MSRMNT_LEVEL_LBL				= "tsv_msrmnt_level_lbl";
	public static final String TSV_INDIVIDUAL					= "tsv_individual";
	public static final String TSV_INDIVIDUAL_TOOLTIP			= "tsv_individual_tt";
	public static final String TSV_AGGREGATE					= "tsv_aggregate";
	public static final String TSV_AGGREGATE_TOOLTIP			= "tsv_aggregate_tt";
	
	public static final String TYPE_OF_MSRMNT				= "Type of Measurement: ";
	public static final String NOMINAL						= "Nominal";
	public static final String NOMINAL_TOOLTIP				= "Nominal";
	public static final String ORDINAL						= "Ordinal";
	public static final String ORDINAL_TOOLTIP				= "Ordinal";
	public static final String INTERVAL						= "Interval";
	public static final String INTERVAL_TOOLTIP				= "Interval";
	public static final String RATIO						= "Ratio";
	public static final String RATIO_TOOLTIP				= "Ratio";
	public static final String CONTINUOUS					= "Continuous";
	public static final String CONTINUOUS_TOOLTIP			= "Continuous";
	
	public static final String EMPTY_STRING 				= "";
	public static final String DONT_BOTHER					= "tsv_dont_bother";
	public static final String OR							= "tsv_or";

	/*
	 *	Fields
	 */
	JPanel 			formular;
	
	JLabel			selectLbl;
	GComboBox		searchCB;
	JPanel			upperSelectPanel;
	int				maxSearchTermCounter = 0;
	int				actSearchTermCounter = 0;
	JLabel			whereLbl;
	Vector<Object>	scopeVector;
	Vector<Object>	boolVector;
	JPanel			searchTermPanel;
	JScrollPane		upperScrollPane;
	JPanel 			searchTermBtnPane;
	JButton			addSearchTermBtn;
	JSeparator		separator0;
	JPanel			proTypePane;
	JLabel			proTypeLbl;
	ButtonGroup		proTypeBtnGrp;
	JRadioButton	harmRadioBtn;
	JRadioButton	docRadioBtn;
	JRadioButton	concRadioBtn;
	JRadioButton	noProTypeRadioBtn;
	JPanel			typeOfMsrmntPane;
	JLabel			typeOfMsrmntLbl;
	ButtonGroup		typeOfMsrmntBtnGrp;
	JRadioButton	nominalRadioBtn;
	JRadioButton	ordinalRadioBtn;
	JRadioButton	intervalRadioBtn;
	JRadioButton	ratioRadioBtn;
	JRadioButton	continuousRadioBtn;
	JRadioButton	noTypeOfMsrmntRadioBtn;
	JPanel			msrmntLvlPane;
	JLabel			msrmntLvlLbl;
	ButtonGroup		msrmntLvlBtnGrp;
	JRadioButton	indRadioBtn;
	JRadioButton	aggRadioBtn;
	JRadioButton	noMsrmntLvlRadioBtn;
	JPanel			lowerSelectPanel;
	JSeparator		separator1;
	JButton			searchBtn;
	JButton			saveSearchQueryBtn;
	JPanel			upperButtonPanel;
	JSeparator		separator2;
	JPanel			resultListPanel;
	JScrollPane 	lowerScrollPane;
	JSeparator		separator3;
	JButton			saveSearchResultBtn;
	JPanel			lowerButtonPanel;
	
	ArrayList<Component> scopeBoxChain = new ArrayList<Component>();
	ArrayList<Component> searchTermTFChain = new ArrayList<Component>();
	ArrayList<Component> conjunctionBoxChain = new ArrayList<Component>();
	
	ArrayList<Object> resultSetItemChain = new ArrayList<Object>();
	ArrayList<Object> resultList = new ArrayList<Object>();
	JButton	updateBasketTreeBtn;
	
	CStatsCtrl ctrl = null;
	Font		currentFont;
	
	double		maxLabelWidth	= -1;
	double		maxLabelHeight	= -1;
	Dimension	labelDimension = new Dimension();
	double		maxLevelWidth	= -1;
	double		maxLevelHeight	= -1;
	Dimension	levelDimension = new Dimension();
	double		maxFromWidth	= -1;
	double		maxFromHeight	= -1;
	Dimension	fromDimension = new Dimension();
	double		maxRadioWidth	= -1;
	double		maxRadioHeight	= -1;
	Dimension	radioDimension = new Dimension();
	
	/*
	 *	Constructor
	 */
	/**
	 * @param locale
	 */
	public TabSearchVariable(Locale locale) {
		super(locale);
		
		currentFont = this.getFont();
		
		setName("TabSearchVariable");
	}
	
	/**
	 * @param al
	 * @param locale
	 */
	public TabSearchVariable(ActionListener al, Locale locale) {
		this(locale);
		
		currentLocale	= locale;
		resourceBundle	= ResourceBundle.getBundle(BUNDLE, currentLocale);
		
		/* Create Form Components */
		JPanel searchPanel = buildSearchForm();
		
		/* Add Form Components to Form Panel */
		formPanel.add(searchPanel);
		
		/* Setup Buttons */
		backButton.setActionCommand(ActionCommandText.BTN_SEA_STP_SEA_TAB_BACK);
		backButton.addActionListener(al);
//		backButton.setEnabled(false);
		noteButton.setActionCommand(ActionCommandText.BTN_SEA_STP_SEA_TAB_NOTE);
		noteButton.addActionListener(al);
		nextButton.setActionCommand(ActionCommandText.BTN_SEA_STP_SEA_TAB_NEXT);
		nextButton.addActionListener(al);
		
		updateBasketTreeBtn = new JButton();
		updateBasketTreeBtn.setActionCommand(ActionCommandText.BTN_FRM_FRM_UPD_BAS_TREE);
		updateBasketTreeBtn.addActionListener(al);
		updateBasketTreeBtn.setVisible(false);
		updateBasketTreeBtn.setEnabled(true);
		
		setPanelIdx(4); // 3
		
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
		selectLbl.setText(bundle.getString(TSV_SELECT_LBL));
		whereLbl.setText(bundle.getString(TSV_WHERE_LBL));
		proTypeLbl.setText(bundle.getString(TSV_PRO_TYPE_LBL));
		harmRadioBtn.setText(bundle.getString(TSV_HARMONIZATION));
		harmRadioBtn.setToolTipText(bundle.getString(TSV_HARMONIZATION_TOOLTIP));
		docRadioBtn.setText(bundle.getString(TSV_DOCUMENTATION));
		docRadioBtn.setToolTipText(bundle.getString(TSV_DOCUMENTATION_TOOLTIP));
		concRadioBtn.setText(bundle.getString(TSV_CONCEPTUAL_BASIS));
		concRadioBtn.setToolTipText(bundle.getString(TSV_CONCEPTUAL_BASIS_TOOLTIP));
		noProTypeRadioBtn.setText("<HTML>" + bundle.getString(DONT_BOTHER) +"<B> "+ bundle.getString(OR) +"</B></HTML>");
		noProTypeRadioBtn.setToolTipText(bundle.getString(DONT_BOTHER));
		typeOfMsrmntLbl.setText(bundle.getString(TSV_TYPE_OF_MSRMNT_LBL));
		nominalRadioBtn.setText(bundle.getString(TSV_NOMINAL));
		nominalRadioBtn.setToolTipText(bundle.getString(TSV_NOMINAL_TOOLTIP));
		ordinalRadioBtn.setText(bundle.getString(TSV_ORDINAL));
		ordinalRadioBtn.setToolTipText(bundle.getString(TSV_ORDINAL_TOOLTIP));
		intervalRadioBtn.setText(bundle.getString(TSV_INTERVAL));
		intervalRadioBtn.setToolTipText(bundle.getString(TSV_INTERVAL_TOOLTIP));
		ratioRadioBtn.setText(bundle.getString(TSV_RATIO));
		ratioRadioBtn.setToolTipText(bundle.getString(TSV_RATIO_TOOLTIP));
		continuousRadioBtn.setText(bundle.getString(TSV_CONTINUOUS));
		continuousRadioBtn.setToolTipText(bundle.getString(TSV_CONTINUOUS_TOOLTIP));
		noTypeOfMsrmntRadioBtn.setText("<HTML>" + bundle.getString(DONT_BOTHER) +"<B> "+ bundle.getString(OR) +"</B></HTML>");
		noTypeOfMsrmntRadioBtn.setToolTipText(bundle.getString(DONT_BOTHER));
		msrmntLvlLbl.setText(bundle.getString(TSV_MSRMNT_LEVEL_LBL));
		indRadioBtn.setText(bundle.getString(TSV_INDIVIDUAL));
		indRadioBtn.setToolTipText(bundle.getString(TSV_INDIVIDUAL_TOOLTIP));
		aggRadioBtn.setText(bundle.getString(TSV_AGGREGATE));
		aggRadioBtn.setToolTipText(bundle.getString(TSV_AGGREGATE_TOOLTIP));
		noMsrmntLvlRadioBtn.setText("<HTML>" + bundle.getString(DONT_BOTHER) + "<B> "+ bundle.getString(OR) +"</B></HTML>");
		noMsrmntLvlRadioBtn.setToolTipText(bundle.getString(DONT_BOTHER));
		addSearchTermBtn.setToolTipText(bundle.getString(TSV_ADD_SEARCH_TERM_BTN_TOOLTIP));
		searchBtn.setText(bundle.getString(TSV_SEARCH_BTN));
		saveSearchQueryBtn.setText(bundle.getString(TSV_SAVE_QUERY_BTN));
		saveSearchResultBtn.setText(bundle.getString(TSV_SAVE_RESULT_BTN));
	}
	
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.Tab#changeFont(java.awt.Font)
	 */
	public void changeFont(Font f) {		
		currentFont = f;
		
		super.changeFont(f);
		
		searchCB.setFont(f);
		
		selectLbl.setFont(f);
		whereLbl.setFont(f);
		proTypeLbl.setFont(f);
		harmRadioBtn.setFont(f);
		docRadioBtn.setFont(f);
		concRadioBtn.setFont(f);
		noProTypeRadioBtn.setFont(f);
		typeOfMsrmntLbl.setFont(f);
		nominalRadioBtn.setFont(f);
		ordinalRadioBtn.setFont(f);
		intervalRadioBtn.setFont(f);
		ratioRadioBtn.setFont(f);
		continuousRadioBtn.setFont(f);
		noTypeOfMsrmntRadioBtn.setFont(f);
		msrmntLvlLbl.setFont(f);
		indRadioBtn.setFont(f);
		aggRadioBtn.setFont(f);
		noMsrmntLvlRadioBtn.setFont(f);	
		addSearchTermBtn.setFont(f);
		searchBtn.setFont(f);
		saveSearchQueryBtn.setFont(f);
		saveSearchResultBtn.setFont(f);
		
		
		for (int lvl0=0; lvl0<searchTermPanel.getComponentCount(); lvl0++) {
			if (searchTermPanel.getComponent(lvl0) instanceof JPanel) {
				JPanel lvl1Panel = (JPanel)searchTermPanel.getComponent(lvl0);
				
				for (int lvl1=0; lvl1<lvl1Panel.getComponentCount(); lvl1++)
					if (lvl1Panel.getComponent(lvl1) instanceof JPanel) {
						JPanel lvl2Panel = (JPanel)lvl1Panel.getComponent(lvl1);
						
						for (int lvl2=0; lvl2<lvl2Panel.getComponentCount(); lvl2++) {
							if (lvl2Panel.getComponent(lvl2) instanceof JTextField)
								((JTextField)lvl2Panel.getComponent(lvl2)).setFont(f);
							if (lvl2Panel.getComponent(lvl2) instanceof JLabel)
								((JLabel)lvl2Panel.getComponent(lvl2)).setFont(f);
							if (lvl2Panel.getComponent(lvl2) instanceof GComboBox)
								((GComboBox)lvl2Panel.getComponent(lvl2)).setFont(f);
							if (lvl2Panel.getComponent(lvl2) instanceof JComboBox)
								((JComboBox)lvl2Panel.getComponent(lvl2)).setFont(f);
							if (lvl2Panel.getComponent(lvl2) instanceof DelSearchTermButton)
								((DelSearchTermButton)lvl2Panel.getComponent(lvl2)).setFont(f);
						}
					}
			}
		}
		
		
		maxLabelWidth	= -1;
		maxLabelHeight	= -1;
		maxLevelWidth	= -1;
		maxLevelHeight	= -1;
		maxFromWidth	= -1;
		maxFromHeight	= -1;
		maxRadioWidth	= -1;
		maxRadioHeight	= -1;
				
		resultListPanel.removeAll();
		resultListPanel.revalidate();
		resultListPanel.repaint();
		
		if (!resultSetItemChain.isEmpty()) {
			Iterator<Object> rsic_it = resultSetItemChain.iterator();
			
			while (rsic_it.hasNext()) {
				Object o = rsic_it.next();
				
				if (o instanceof Project) {
					Project project = (Project)o;
					
					resultListPanel.add(buildResultSetItemPanel(project));
				}
				if (o instanceof Measurement) {
					Measurement measurement = (Measurement)o;
					
					resultListPanel.add(buildResultSetItemPanel(measurement));
				}
				if (o instanceof Variable) {
					Variable variable = (Variable)o;
					
					resultListPanel.add(buildResultSetItemPanel(variable));

				}
			}
			
			resultListPanel.revalidate();
			resultListPanel.repaint();
		}

	}
	
	/**
	 * @return
	 */
	JPanel buildSearchForm() {
		selectLbl = new JLabel(resourceBundle.getString(TSV_SELECT_LBL));
		
		Vector<Object> searchVector = fillVector(org.gesis.charmstats.persistence.SearchCBResource.SearchObject.class);
		
		searchCB = new GComboBox(searchVector);
		searchCB.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXX");
		searchCB.setThisEnum(org.gesis.charmstats.persistence.SearchCBResource.SearchObject.class);
		searchCB.addFocusListener(this);
		searchCB.addActionListener(new ActionListener() {
			/* (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			public void actionPerformed(ActionEvent e) {
				GComboBox cb = (GComboBox)e.getSource();
				
				Object obj = SearchCBResource.getEnum(((GComboBox)cb).getThisEnum(), cb.getSelectedIndex());
				scopeVector = fillVector(obj);
				
				if (obj != null)
					if (obj.equals(ProjectScope.class)) {
						noProTypeRadioBtn.setEnabled(true);
						harmRadioBtn.setEnabled(true);
						docRadioBtn.setEnabled(true);
						concRadioBtn.setEnabled(true);
						noTypeOfMsrmntRadioBtn.setEnabled(true);
						nominalRadioBtn.setEnabled(true);
						ordinalRadioBtn.setEnabled(true);
						intervalRadioBtn.setEnabled(true);
						ratioRadioBtn.setEnabled(true);
						continuousRadioBtn.setEnabled(true);
						noMsrmntLvlRadioBtn.setEnabled(true);
						indRadioBtn.setEnabled(true);
						aggRadioBtn.setEnabled(true);
					} else if (obj.equals(MeasurementScope.class)) {
						noProTypeRadioBtn.setEnabled(false);
						harmRadioBtn.setEnabled(false);
						docRadioBtn.setEnabled(false);
						concRadioBtn.setEnabled(false);
						noProTypeRadioBtn.setSelected(true);
						noTypeOfMsrmntRadioBtn.setEnabled(true);
						nominalRadioBtn.setEnabled(true);
						ordinalRadioBtn.setEnabled(true);
						intervalRadioBtn.setEnabled(true);
						ratioRadioBtn.setEnabled(true);
						continuousRadioBtn.setEnabled(true);
						noMsrmntLvlRadioBtn.setEnabled(true);
						indRadioBtn.setEnabled(true);
						aggRadioBtn.setEnabled(true);					
					} else if ((obj.equals(VariableScope.class)) ||
							(obj.equals(IndicatorScope.class)) ||
							(obj.equals(DimensionScope.class))) {
						noProTypeRadioBtn.setEnabled(false);
						harmRadioBtn.setEnabled(false);
						docRadioBtn.setEnabled(false);
						concRadioBtn.setEnabled(false);
						noProTypeRadioBtn.setSelected(true);
						noTypeOfMsrmntRadioBtn.setEnabled(false);
						nominalRadioBtn.setEnabled(false);
						ordinalRadioBtn.setEnabled(false);
						intervalRadioBtn.setEnabled(false);
						ratioRadioBtn.setEnabled(false);
						continuousRadioBtn.setEnabled(false);
						noTypeOfMsrmntRadioBtn.setSelected(true);
						noMsrmntLvlRadioBtn.setEnabled(true);
						indRadioBtn.setEnabled(true);
						aggRadioBtn.setEnabled(true);					
					} else if ((obj.equals(ConceptScope.class)) ||
							(obj.equals(UniverseScope.class)) ||
							(obj.equals(QuestionScope.class))) {
						noProTypeRadioBtn.setEnabled(false);
						harmRadioBtn.setEnabled(false);
						docRadioBtn.setEnabled(false);
						concRadioBtn.setEnabled(false);
						noProTypeRadioBtn.setSelected(true);
						noTypeOfMsrmntRadioBtn.setEnabled(false);
						nominalRadioBtn.setEnabled(false);
						ordinalRadioBtn.setEnabled(false);
						intervalRadioBtn.setEnabled(false);
						ratioRadioBtn.setEnabled(false);
						continuousRadioBtn.setEnabled(false);
						noTypeOfMsrmntRadioBtn.setSelected(true);
						noMsrmntLvlRadioBtn.setEnabled(false);
						indRadioBtn.setEnabled(false);
						aggRadioBtn.setEnabled(false);
						noMsrmntLvlRadioBtn.setSelected(true);
					}
				
				if (scopeBoxChain != null) {
		        	for (int i=0; i<scopeBoxChain.size(); i++) {
		        		Component scopeCB = scopeBoxChain.get(i);
		        		
		        		for (int ii=((GComboBox)scopeCB).getModel().getSize(); ii > 0; ii--) {
		        			((GComboBox)scopeCB).removeItemAt(ii-1);
		        		}
		        		
		        		if (scopeVector != null)
		        			for (int ii=0; ii < scopeVector.size(); ii++) {
		        				((GComboBox)scopeCB).addItem(scopeVector.elementAt(ii));
		        			}
		        		if (((GComboBox)scopeCB).getModel().getSize() > 0)
		        			((GComboBox)scopeCB).setSelectedIndex(0);
		        		((GComboBox)scopeCB).setThisEnum(obj);
		        		
		        	}
				}
			}
		});
		
		upperSelectPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		upperSelectPanel.add(selectLbl);
		upperSelectPanel.add(searchCB);
		
		whereLbl = new JLabel(resourceBundle.getString(TSV_WHERE_LBL));
		
		scopeVector = fillVector(org.gesis.charmstats.persistence.SearchCBResource.EmptyCB.class);
		boolVector = fillVector(org.gesis.charmstats.persistence.SearchBooleanOperator.class);

		searchTermPanel = new JPanel();
		BoxLayout boxLayout1 = new BoxLayout(searchTermPanel, BoxLayout.Y_AXIS);
		searchTermPanel.setLayout(boxLayout1);
		searchTermPanel.add(returnSearchTermPanel(true));
		
		upperScrollPane = new JScrollPane(searchTermPanel);
		upperScrollPane.setMinimumSize(new Dimension(550, 100)); 
		upperScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		upperScrollPane.getVerticalScrollBar().setUnitIncrement(16);
		
		separator0 = new JSeparator(SwingConstants.HORIZONTAL);
		
		proTypeLbl = new JLabel(resourceBundle.getString(TSV_PRO_TYPE_LBL));
		
		maxRadioWidth	= Math.max(proTypeLbl.getPreferredSize().getWidth(),	maxRadioWidth);
		maxRadioHeight	= Math.max(proTypeLbl.getPreferredSize().getHeight(),	maxRadioHeight);
		radioDimension.setSize(maxRadioWidth, maxRadioHeight);
		proTypeLbl.setPreferredSize(radioDimension);
		
		harmRadioBtn = new JRadioButton(resourceBundle.getString(TSV_HARMONIZATION));
		harmRadioBtn.setName(TSV_HARMONIZATION);
		harmRadioBtn.setToolTipText(resourceBundle.getString(TSV_HARMONIZATION_TOOLTIP));	
		harmRadioBtn.setActionCommand(TSV_HARMONIZATION);
		harmRadioBtn.setSelected(false);
		
		docRadioBtn = new JRadioButton(resourceBundle.getString(TSV_DOCUMENTATION));
		docRadioBtn.setName(TSV_DOCUMENTATION);
		docRadioBtn.setToolTipText(resourceBundle.getString(TSV_DOCUMENTATION_TOOLTIP));	
		docRadioBtn.setActionCommand(TSV_DOCUMENTATION);
		docRadioBtn.setSelected(false);
		
		concRadioBtn = new JRadioButton(resourceBundle.getString(TSV_CONCEPTUAL_BASIS));
		concRadioBtn.setName(TSV_CONCEPTUAL_BASIS);
		concRadioBtn.setToolTipText(resourceBundle.getString(TSV_CONCEPTUAL_BASIS_TOOLTIP));	
		concRadioBtn.setActionCommand(TSV_CONCEPTUAL_BASIS);
		concRadioBtn.setSelected(false);
		
		noProTypeRadioBtn = new JRadioButton("<HTML>" + resourceBundle.getString(DONT_BOTHER) + "<B> "+ resourceBundle.getString(OR) +"</B></HTML>");
		noProTypeRadioBtn.setName(DONT_BOTHER);
		noProTypeRadioBtn.setToolTipText(resourceBundle.getString(DONT_BOTHER));	
		noProTypeRadioBtn.setActionCommand(DONT_BOTHER);
		noProTypeRadioBtn.setSelected(true); 
		
		proTypeBtnGrp = new ButtonGroup();
		proTypeBtnGrp.add(noProTypeRadioBtn);
		proTypeBtnGrp.add(harmRadioBtn);
		proTypeBtnGrp.add(docRadioBtn);
		proTypeBtnGrp.add(concRadioBtn);
		
		proTypePane = new JPanel(new FlowLayout(FlowLayout.LEADING, 5, 2));
		proTypePane.add(proTypeLbl);
		proTypePane.add(noProTypeRadioBtn);
		proTypePane.add(harmRadioBtn);
		proTypePane.add(docRadioBtn);
		proTypePane.add(concRadioBtn);
		noProTypeRadioBtn.setEnabled(false);
		harmRadioBtn.setEnabled(false);
		docRadioBtn.setEnabled(false);
		concRadioBtn.setEnabled(false);
		
		typeOfMsrmntLbl	= new JLabel(TYPE_OF_MSRMNT);
		
		maxRadioWidth	= Math.max(typeOfMsrmntLbl.getPreferredSize().getWidth(),	maxRadioWidth);
		maxRadioHeight	= Math.max(typeOfMsrmntLbl.getPreferredSize().getHeight(),	maxRadioHeight);
		radioDimension.setSize(maxRadioWidth, maxRadioHeight);
		typeOfMsrmntLbl.setPreferredSize(radioDimension);
		
		nominalRadioBtn = new JRadioButton(NOMINAL);
		nominalRadioBtn.setName(NOMINAL);
		nominalRadioBtn.setToolTipText(NOMINAL_TOOLTIP);
		nominalRadioBtn.setActionCommand(NOMINAL);
		nominalRadioBtn.setSelected(false);
		
		ordinalRadioBtn = new JRadioButton(ORDINAL);
		ordinalRadioBtn.setName(ORDINAL);
		ordinalRadioBtn.setToolTipText(ORDINAL_TOOLTIP);
		ordinalRadioBtn.setActionCommand(ORDINAL);
		ordinalRadioBtn.setSelected(false);
		
		intervalRadioBtn = new JRadioButton(INTERVAL);
		intervalRadioBtn.setName(INTERVAL);
		intervalRadioBtn.setToolTipText(INTERVAL_TOOLTIP);
		intervalRadioBtn.setActionCommand(INTERVAL);
		intervalRadioBtn.setSelected(false);
		
		ratioRadioBtn = new JRadioButton(RATIO);
		ratioRadioBtn.setName(RATIO);
		ratioRadioBtn.setToolTipText(RATIO_TOOLTIP);
		ratioRadioBtn.setActionCommand(RATIO);
		ratioRadioBtn.setSelected(false);
		
		continuousRadioBtn = new JRadioButton(CONTINUOUS);
		continuousRadioBtn.setName(CONTINUOUS);
		continuousRadioBtn.setToolTipText(CONTINUOUS_TOOLTIP);
		continuousRadioBtn.setActionCommand(CONTINUOUS);
		continuousRadioBtn.setSelected(false);
		
		noTypeOfMsrmntRadioBtn = new JRadioButton("<HTML>" + resourceBundle.getString(DONT_BOTHER)  +"<B> "+ resourceBundle.getString(OR) +"</B></HTML>");
		noTypeOfMsrmntRadioBtn.setName(DONT_BOTHER);
		noTypeOfMsrmntRadioBtn.setToolTipText(DONT_BOTHER);
		noTypeOfMsrmntRadioBtn.setActionCommand(DONT_BOTHER);
		noTypeOfMsrmntRadioBtn.setSelected(true);
		
		typeOfMsrmntBtnGrp = new ButtonGroup();
		typeOfMsrmntBtnGrp.add(noTypeOfMsrmntRadioBtn);
		typeOfMsrmntBtnGrp.add(nominalRadioBtn);
		typeOfMsrmntBtnGrp.add(ordinalRadioBtn);
		typeOfMsrmntBtnGrp.add(intervalRadioBtn);
		typeOfMsrmntBtnGrp.add(ratioRadioBtn);
		typeOfMsrmntBtnGrp.add(continuousRadioBtn);
		
		typeOfMsrmntPane = new JPanel(new FlowLayout(FlowLayout.LEADING, 5, 2));
		typeOfMsrmntPane.add(typeOfMsrmntLbl);
		typeOfMsrmntPane.add(noTypeOfMsrmntRadioBtn);
		typeOfMsrmntPane.add(nominalRadioBtn);
		typeOfMsrmntPane.add(ordinalRadioBtn);
		typeOfMsrmntPane.add(intervalRadioBtn);
		typeOfMsrmntPane.add(ratioRadioBtn);
		typeOfMsrmntPane.add(continuousRadioBtn);
		noTypeOfMsrmntRadioBtn.setEnabled(false);
		nominalRadioBtn.setEnabled(false);
		ordinalRadioBtn.setEnabled(false);
		intervalRadioBtn.setEnabled(false);
		ratioRadioBtn.setEnabled(false);
		continuousRadioBtn.setEnabled(false);
		
		msrmntLvlLbl = new JLabel(resourceBundle.getString(TSV_MSRMNT_LEVEL_LBL));
		
		maxRadioWidth	= Math.max(msrmntLvlLbl.getPreferredSize().getWidth(),	maxRadioWidth);
		maxRadioHeight	= Math.max(msrmntLvlLbl.getPreferredSize().getHeight(),	maxRadioHeight);
		radioDimension.setSize(maxRadioWidth, maxRadioHeight);
		msrmntLvlLbl.setPreferredSize(radioDimension);
		
		indRadioBtn = new JRadioButton(resourceBundle.getString(TSV_INDIVIDUAL));
		indRadioBtn.setName(TSV_INDIVIDUAL);
		indRadioBtn.setToolTipText(resourceBundle.getString(TSV_INDIVIDUAL_TOOLTIP));	
		indRadioBtn.setActionCommand(TSV_INDIVIDUAL);
		indRadioBtn.setSelected(false);
		
		aggRadioBtn = new JRadioButton(resourceBundle.getString(TSV_AGGREGATE));
		aggRadioBtn.setName(TSV_AGGREGATE);
		aggRadioBtn.setToolTipText(resourceBundle.getString(TSV_AGGREGATE_TOOLTIP));	
		aggRadioBtn.setActionCommand(TSV_AGGREGATE);
		aggRadioBtn.setSelected(false);
		
		noMsrmntLvlRadioBtn = new JRadioButton("<HTML>" + resourceBundle.getString(DONT_BOTHER) +"<B> "+ resourceBundle.getString(OR) +"</B></HTML>");
		noMsrmntLvlRadioBtn.setName(DONT_BOTHER);
		noMsrmntLvlRadioBtn.setToolTipText(resourceBundle.getString(DONT_BOTHER));	
		noMsrmntLvlRadioBtn.setActionCommand(DONT_BOTHER);
		noMsrmntLvlRadioBtn.setSelected(true);
		
		msrmntLvlBtnGrp = new ButtonGroup();
		msrmntLvlBtnGrp.add(noMsrmntLvlRadioBtn);
		msrmntLvlBtnGrp.add(indRadioBtn);
		msrmntLvlBtnGrp.add(aggRadioBtn);	

		msrmntLvlPane = new JPanel(new FlowLayout(FlowLayout.LEADING, 5, 2));	
		
		msrmntLvlPane.add(msrmntLvlLbl);
		msrmntLvlPane.add(noMsrmntLvlRadioBtn);
		msrmntLvlPane.add(indRadioBtn);
		msrmntLvlPane.add(aggRadioBtn);
		noMsrmntLvlRadioBtn.setEnabled(false);
		indRadioBtn.setEnabled(false);
		aggRadioBtn.setEnabled(false);
		
		addSearchTermBtn = new JButton(ADD_SEARCH_TERM_BTN);
		addSearchTermBtn.addMouseListener(addSearchTermMouseListener);
		addSearchTermBtn.setToolTipText(resourceBundle.getString(TSV_ADD_SEARCH_TERM_BTN_TOOLTIP));
	
		searchTermBtnPane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		searchTermBtnPane.add(addSearchTermBtn);
		
		lowerSelectPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		lowerSelectPanel.add(whereLbl);
		lowerSelectPanel.add(upperScrollPane);
		
		separator1 = new JSeparator(SwingConstants.HORIZONTAL);
		
		
		searchBtn = new JButton(resourceBundle.getString(TSV_SEARCH_BTN));
		searchBtn.addMouseListener(searchMouseListener);
		saveSearchQueryBtn = new JButton(resourceBundle.getString(TSV_SAVE_QUERY_BTN));
		saveSearchQueryBtn.setEnabled(false);
		saveSearchQueryBtn.setVisible(false);
		upperButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		upperButtonPanel.add(saveSearchQueryBtn);
		
		separator2 = new JSeparator(SwingConstants.HORIZONTAL);
		
		resultListPanel = new JPanel();
		BoxLayout boxLayout2 = new BoxLayout(resultListPanel, BoxLayout.Y_AXIS);
		resultListPanel.setLayout(boxLayout2);
		
		lowerScrollPane = new JScrollPane(resultListPanel);
		lowerScrollPane.setPreferredSize(new Dimension(500, 100));
		lowerScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		lowerScrollPane.getVerticalScrollBar().setUnitIncrement(16);
		
		separator3 = new JSeparator(SwingConstants.HORIZONTAL);
		
		saveSearchResultBtn = new JButton(resourceBundle.getString(TSV_SAVE_RESULT_BTN));
		saveSearchResultBtn.addMouseListener(saveSearchResultMouseListener);
		lowerButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		lowerButtonPanel.add(searchBtn);
		lowerButtonPanel.add(saveSearchResultBtn);
		
		formular = new JPanel();
		formular.setLayout(new BoxLayout(formular, BoxLayout.Y_AXIS));
		formular.add(upperSelectPanel);
		formular.add(lowerSelectPanel);
		formular.add(searchTermBtnPane);
		formular.add(separator0);
		formular.add(proTypePane);
		formular.add(typeOfMsrmntPane);
		formular.add(msrmntLvlPane);
		formular.add(separator1);
		formular.add(upperButtonPanel);
		formular.add(separator2);
		formular.add(lowerScrollPane);
		formular.add(separator3);
		formular.add(lowerButtonPanel);
		formular.setBorder(
				BorderFactory.createCompoundBorder(
					BorderFactory.createTitledBorder(EMPTY_STRING),
					BorderFactory.createEmptyBorder(5, 5, 5, 5)
				)
		);
		
		return formular;
	}
	
	/**
	 * @param classObject
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private Vector<Object> fillVector(Object classObject) {
		Vector<Object> retVector = new Vector<Object>();
		
		if ((classObject != null) &&
				(((Class)classObject).isEnum())) {
			Object[] objectArray = new Object[((Class)classObject).getEnumConstants().length];
			objectArray = ((Class)classObject).getEnumConstants();
				
			for (int i=0; i < objectArray.length; i++) {
				if (objectArray[i] instanceof SearchObject)
					((SearchObject)objectArray[i]).setLocale(currentLocale);
				if (objectArray[i] instanceof ProjectScope)
					((ProjectScope)objectArray[i]).setLocale(currentLocale);
				if (objectArray[i] instanceof MeasurementScope)
					((MeasurementScope)objectArray[i]).setLocale(currentLocale);
				if (objectArray[i] instanceof VariableScope)
					((VariableScope)objectArray[i]).setLocale(currentLocale);
				if (objectArray[i] instanceof SearchBooleanOperator)
					((SearchBooleanOperator)objectArray[i]).setLocale(currentLocale);
				
				retVector.add(objectArray[i]);
			}
		}
		
		return retVector;
	}
		
	/**
	 * @param isVisible
	 * @return
	 */
	private Component returnSearchTermPanel(boolean isVisible) {
		JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 2));
		
		jPanel.add(addSearchTermInputBox(isVisible));
		jPanel.add(addSearchTermButtonPanel(isVisible));
		
		jPanel.setMinimumSize(new Dimension(550, 25)); 
				
		return jPanel;
	}
	
	MouseListener addSearchTermMouseListener = new MouseAdapter() {
		/* (non-Javadoc)
		 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
		 */
		public void mouseClicked(MouseEvent mouseEvent) {
						
			searchTermPanel.add(returnSearchTermPanel(true));
			changeFont(currentFont);
			
			searchTermPanel.revalidate();
			
       }
	};
	
	MouseListener searchMouseListener = new MouseAdapter() {
		/* (non-Javadoc)
		 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
		 */
		public void mouseClicked(MouseEvent mouseEvent) {
			
			maxLabelWidth	= -1;
			maxLabelHeight	= -1;
			maxLevelWidth	= -1;
			maxLevelHeight	= -1;
			maxFromWidth	= -1;
			maxFromHeight	= -1;
			maxRadioWidth	= -1;
			maxRadioHeight	= -1;
			
			resultListPanel.removeAll();
			resultSetItemChain.clear();
			resultListPanel.revalidate();
			resultListPanel.repaint();
			
			int classID = 0;
			switch (searchCB.getSelectedIndex()) {
				case  1:
					classID = EntityType.PROJECTS.getID();	
					break;
				case  2: 
					classID = EntityType.MEASUREMENTS.getID();
					break;
				case  3: 
					classID = EntityType.VARIABLES.getID();
					break;
				default:
					break;
			}
			
			String fullSql = EMPTY_STRING;
			String sqlPart = EMPTY_STRING;
			
			String searchTerm = EMPTY_STRING;
			GComboBox scope = null;
			if (scopeBoxChain != null)		
	        	for (int i=0; i<scopeBoxChain.size(); i++) {
	        		
	        		Component scopeCB = scopeBoxChain.get(i);
	        		scope = scopeCB instanceof GComboBox ?
	        			((GComboBox)scopeCB) : null;
	        		
	        		Component searchTermTF = searchTermTFChain.get(i);
	        		searchTerm = searchTermTF instanceof JTextField ? 
	        			((JTextField)searchTermTF).getText() : EMPTY_STRING;
	        			
	        		sqlPart = EMPTY_STRING;
	        		if ((scope != null) && 
	        				(searchTerm != null) &&
	        				(searchTerm.compareTo(EMPTY_STRING) != 0 )) {
	        			sqlPart = SearchCBResource.getDraftSQL(
	        					searchCB.getThisEnum(), searchCB.getSelectedIndex(), scope.getThisEnum(), scope.getSelectedIndex(), 
	        					getSelectedProType(), getSelectedTypeOfMea(), getSelectedMeaLevel());
	        			
	        			if (sqlPart != null)	        			
	        				sqlPart = sqlPart.replaceAll("<TEXT>", searchTerm);
	        			else
	        				sqlPart = EMPTY_STRING;
	        		}		
	        		
	        		if (i == 0) {
	        			fullSql = sqlPart;
	        		}
	        	
	        		if (i > 0) {
	        			JComboBox boolCB = (JComboBox)conjunctionBoxChain.get(i-1);
	        			
	        			switch (boolCB.getSelectedIndex()) {
	        				case 0:
	        					fullSql += sqlPart;
	        					break;
	        				case 1:
	    	        			String primaryKey = "";
	    	        			String tableAlias = "";
	        					
	           					switch (searchCB.getSelectedIndex()) {
		    						case  1:
		    							tableAlias = "p";
		    							primaryKey = "id";
		    							break;
		    						case  2: 
		    							tableAlias = "m";
		    							primaryKey = "id";
		    							break;
		    						case  6: 
		    							tableAlias = "v";
		    							primaryKey = "id";
		    							break;
		    						default:
		    							break;
		    					}
	           					
	           					fullSql = "select " + primaryKey + " from (" + fullSql + ") " +
	           						tableAlias + Integer.toString(i-1) + " where " + 
	           						tableAlias + Integer.toString(i-1) + "." + primaryKey + " > 0 " +
	           						" AND " + tableAlias + Integer.toString(i-1) + "." + primaryKey + " IN (" + 
	        						"select " + primaryKey + " from (" + sqlPart + ") " + 
	        						tableAlias + Integer.toString(i) + " where " + 
	        						tableAlias + Integer.toString(i) + "." + primaryKey + " > 0 " + ") ";
	           					
	        					break;
	        				case 2:
	        					fullSql += (" UNION " + sqlPart);
	        					break;
	        				default:
	        					fullSql += sqlPart;
	        					break;
	        			}
	        		}
	        		
	        	}	
			
			if (!fullSql.equals(EMPTY_STRING)) {
				
				ArrayList<Object> resultSet =
					CStatsCtrl.search(classID, fullSql);
				
				if ((resultSet != null) && 
						(resultSet.size() > 0)) {

					Iterator<Object> iterator_o = resultSet.iterator();
					while (iterator_o.hasNext()) {
						Object resultObject = iterator_o.next();
						
						if (classID == EntityType.PROJECTS.getID()) {
							Project project = (Project)resultObject;
							
							resultListPanel.add(buildResultSetItemPanel(project));
							resultSetItemChain.add(project);
						}
						if (classID == EntityType.CONCEPTS.getID()) {
							Concept concept = (Concept)resultObject;

							resultListPanel.add(buildResultSetItemPanel(concept));
							resultSetItemChain.add(concept);
						}
						if (classID == EntityType.MEASUREMENTS.getID()) {
							Measurement measurement = (Measurement)resultObject;
							
							resultListPanel.add(buildResultSetItemPanel(measurement));
							resultSetItemChain.add(measurement);
						}
						if (classID == EntityType.VARIABLES.getID()) {
							Variable variable = (Variable)resultObject;
							
							resultListPanel.add(buildResultSetItemPanel(variable));
							resultSetItemChain.add(variable);
						}
						if (classID == EntityType.OPERA_INDICATORS.getID()) {
							OperaIndicator indicator = (OperaIndicator)resultObject;

							resultListPanel.add(buildResultSetItemPanel(indicator));
							resultSetItemChain.add(indicator);
						}
						if (classID == EntityType.CON_DIMENSIONS.getID()) {
							ConDimension dimension = (ConDimension)resultObject;

							resultListPanel.add(buildResultSetItemPanel(dimension));
							resultSetItemChain.add(dimension);
						}
					}
				}
//				changeFont(currentFont);
				
				resultListPanel.revalidate();
				resultListPanel.repaint();
			}
		
       }
	};
	
	MouseListener saveSearchResultMouseListener = new MouseAdapter() {
		/* (non-Javadoc)
		 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
		 */
		public void mouseClicked(MouseEvent mouseEvent) {
			resultList.clear();
			
			for (int i=0; i<resultListPanel.getComponentCount(); i++) {
				Component panelComponent = resultListPanel.getComponent(i);
				
				if (panelComponent instanceof JPanel) {
					JPanel panel = (JPanel)panelComponent;
					
					for (int j=0; j<panel.getComponentCount(); j++) {
						Component subPanelComponent = panel.getComponent(j);
						
						if (subPanelComponent instanceof JPanel) {
							JPanel subPanel = (JPanel)subPanelComponent;
							
							for (int k=0; k<subPanel.getComponentCount(); k++) {
								Component otherComponent = subPanel.getComponent(k);
								
								if (otherComponent instanceof JCheckBox) {
									if ( ((JCheckBox)otherComponent).isSelected() ) {
										resultList.add(resultSetItemChain.get(i));
									}
								}
							}
						}
					}
				}
			}
			
			updateBasketTreeBtn.doClick();
       }
	};
	
	/**
	 * @param isVisible
	 * @return
	 */
	private Component addSearchTermInputBox(boolean isVisible) {
		
		JTextField searchTermTF = new JTextField(25);
		searchTermTF.setName("SearchTermTF_" + maxSearchTermCounter);
		searchTermTF.addFocusListener(this);

		JLabel inLbl = new JLabel(" in ");
		
		GComboBox scopeCB = new GComboBox(scopeVector);
		scopeCB.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXX");
		scopeCB.setName("ScopeCB_" + maxSearchTermCounter);
		scopeCB.addFocusListener(this);
		
		Object obj = SearchCBResource.getEnum((searchCB).getThisEnum(), searchCB.getSelectedIndex());
		scopeCB.setThisEnum(obj);
		
		JComboBox conjunctionCB = new JComboBox(boolVector);
		conjunctionCB.setName("ConjunctionCB_" + maxSearchTermCounter);
		conjunctionCB.addFocusListener(this);
		
		JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
		
		jPanel.add(searchTermTF);
		jPanel.add(inLbl);
		jPanel.add(scopeCB);
		jPanel.add(conjunctionCB);
				
		maxSearchTermCounter++;
		actSearchTermCounter++;
		
		jPanel.setVisible(isVisible);
		
		searchTermTFChain.add(searchTermTF);
		scopeBoxChain.add(scopeCB);
		conjunctionBoxChain.add(conjunctionCB);
		
		return jPanel;
	}
	
	/**
	 * @param isVisible
	 * @return
	 */
	private Component addSearchTermButtonPanel(boolean isVisible) {
		JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
	
		jPanel.add(new DelSearchTermButton());
		jPanel.setVisible(isVisible);
				
		return jPanel;
	}
	
	/**
	 * @return
	 */
	private int getSelectedProType() {
		int returnValue = 0;
		
		if (harmRadioBtn.isSelected())
			returnValue = 1;
		else if (docRadioBtn.isSelected())
			returnValue = 2;
		else if (concRadioBtn.isSelected())
			returnValue = 4;
		
		return returnValue;
	}
	
	/**
	 * @return
	 */
	private int getSelectedTypeOfMea() {
		int returnValue = 0;
		
		if (nominalRadioBtn.isSelected())
			returnValue = 1;
		else if (ordinalRadioBtn.isSelected())
			returnValue = 2;
		else if (intervalRadioBtn.isSelected())
			returnValue = 4;
		else if (ratioRadioBtn.isSelected())
			returnValue = 8;
		else if (continuousRadioBtn.isSelected())
			returnValue = 16;
		
		return returnValue;
	}
	
	/**
	 * @return
	 */
	private int getSelectedMeaLevel() {
		int returnValue = 0;
		
		if (indRadioBtn.isSelected())
			returnValue = 1;
		else if (aggRadioBtn.isSelected())
			returnValue = 2;
		
		return returnValue;
	}
	
	class DelSearchTermButton extends JButton {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		DelSearchTermButton() {
			super(DEL_SEARCH_TERM_BTN);
			
			this.addMouseListener(delSearchTermMouseListener);			
		}
	}
	
	MouseListener delSearchTermMouseListener = new MouseAdapter() {
		/* (non-Javadoc)
		 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
		 */
		public void mouseClicked(MouseEvent mouseEvent) {
			
			if (actSearchTermCounter > 1) {
				actSearchTermCounter--;
			
				deleteSearchTermPanel(mouseEvent.getComponent().getParent().getParent());
			} else 
			if (actSearchTermCounter == 1) {
				
				resetSearchTermInputBox(mouseEvent.getComponent().getParent().getParent());
			}
			
       }
	};
	
	/**
	 * @param container
	 */
	private void deleteSearchTermPanel(Container container) {
		
		if (container != null) {
			removeComponents(container);
			
			Container tabbedpane = container.getParent();
			tabbedpane.remove(container);
			
			((JPanel)tabbedpane).revalidate();
			((JPanel)tabbedpane).repaint();
		}
		
	}
	
	/**
	 * @param container
	 */
	private void resetSearchTermInputBox(Container container) {
		
		if (container != null) {
			
			for (int i = 0; i < container.getComponentCount(); i++) {	
				Component component = container.getComponent(i);
				
				if (component instanceof JPanel) {
					
					for (int j = 0; j < ((JPanel)component).getComponentCount(); j++) {	
						Component subComponent = ((JPanel)component).getComponent(j);
						
						if (subComponent instanceof JTextField) {
							((JTextField)subComponent).setText(TabSearchVariable.EMPTY_STRING);
							((JTextField)subComponent).setToolTipText(resourceBundle.getString(TSV_SEARCH_TERM_TOOLTIP));
						}
						
						if (subComponent instanceof JComboBox)
							((JComboBox)subComponent).setSelectedIndex(0);
					}
				}
			}
			
			Container parent = container.getParent(); 
			
			((JPanel)parent).revalidate();
			((JPanel)parent).repaint();
		}	
	}
	
	/**
	 * @param container
	 */
	private void removeComponents(Container container) {
		if (container != null) {
			for (int i=0; i<container.getComponentCount(); i++) {
				if (container.getComponent(i) instanceof Container)
					removeComponents((Container)container.getComponent(i));
		
				if ((container.getComponent(i).getName() != null) &&
						(container.getComponent(i).getName().startsWith("ScopeCB")))
					scopeBoxChain.remove(container.getComponent(i));
				
				if ((container.getComponent(i).getName() != null) &&
						(container.getComponent(i).getName().startsWith("SearchTermTF")))
					searchTermTFChain.remove(container.getComponent(i));
				
				if ((container.getComponent(i).getName() != null) &&
						(container.getComponent(i).getName().startsWith("ConjunctionCB")))
					conjunctionBoxChain.remove(container.getComponent(i));
				
				container.remove(container.getComponent(i));
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
	 * @see org.gesis.charmstats.view.Tab#setDefaults(java.lang.Object)
	 */
	public void setDefaults(Object defaults) {
		/* TODO */		
	}
	
	/**
	 * @param object
	 * @return
	 */
	private JPanel buildResultSetItemPanel(Object object) {
		JPanel returnPanel = new JPanel();
		returnPanel.setLayout(new BoxLayout(returnPanel, BoxLayout.Y_AXIS));
		
		JPanel panel = new JPanel();
		JPanel subPanel1 = new JPanel();
		JPanel subPanel2 = new JPanel();
		
		/* Add checkBox: */
		JCheckBox checkBox = new JCheckBox("", false);
		checkBox.setFont(currentFont);
		
		/* Add label with name of object: */
		JLabel label = new JLabel("<html><b>"+object.toString()+"</b></html>");
		label.setFont(currentFont);
		label.setName("LABEL");
		
		maxLabelWidth	= Math.max(label.getPreferredSize().getWidth(),		maxLabelWidth);
		maxLabelHeight	= Math.max(label.getPreferredSize().getHeight(),	maxLabelHeight);
		labelDimension.setSize(maxLabelWidth, maxLabelHeight);
		label.setPreferredSize(labelDimension);
		
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.add(checkBox);
		panel.add(label);
		
		subPanel1.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		subPanel2.setLayout(new FlowLayout(FlowLayout.LEFT));
		

		/* Add additional metadata fields to the output */
		if (object instanceof Project) {
			String measurementLevel = ((Project)object).getContent().getMeasurement().getLevel().toString();
			label = new JLabel("<html>Level: <b>" + ((measurementLevel.length() > 0) ? measurementLevel : "unknown") + ";</b></html>");
			label.setFont(currentFont);
			label.setName("LEVEL");
			
			maxLevelWidth	= Math.max(label.getPreferredSize().getWidth(),		maxLevelWidth);
			maxLevelHeight	= Math.max(label.getPreferredSize().getHeight(),	maxLevelHeight);
			levelDimension.setSize(maxLevelWidth, maxLevelHeight);
			label.setPreferredSize(levelDimension);
			
			panel.add(label);
			
			String sqlString	= Project.getDraftSQLForNote(((Project)object).getEntityID());
			String projectNote	= getProjectNote(sqlString);
						
			subPanel1.add(new JLabel(projectNote));
		}
		if (object instanceof Measurement) {
			String sqlString = Measurement.getDraftSQLForProject(((Measurement)object).getEntityID());
			
			String projectName = getProjectName(sqlString);
			label = new JLabel("<html>from <b>" + ((projectName.length() > 0) ? projectName : "unknown") + ";</b></html>");
			label.setFont(currentFont);
			label.setName("FROM");
			
			maxFromWidth	= Math.max(label.getPreferredSize().getWidth(),		maxFromWidth);
			maxFromHeight	= Math.max(label.getPreferredSize().getHeight(),	maxFromHeight);
			fromDimension.setSize(maxFromWidth, maxFromHeight);
			label.setPreferredSize(fromDimension);
			
			panel.add(label);
			
			String measurementLevel = ((Measurement)object).getLevel().toString();
			label = new JLabel("<html>Level: <b>" + ((measurementLevel.length() > 0) ? measurementLevel : "unknown") + ";</b></html>");
			label.setFont(currentFont);
			label.setName("LEVEL");
			
			maxLevelWidth	= Math.max(label.getPreferredSize().getWidth(),		maxLevelWidth);
			maxLevelHeight	= Math.max(label.getPreferredSize().getHeight(),	maxLevelHeight);
			levelDimension.setSize(maxLevelWidth, maxLevelHeight);
			label.setPreferredSize(levelDimension);
			
			panel.add(label);
			
			sqlString 			= Measurement.getDraftSQLForCategoryList(((Measurement)object).getEntityID());		
			String categoryList	= getMeasurementCategories(sqlString);
			
			subPanel1.add(new JLabel(categoryList));
	
			if ( (((Measurement)object).getDefinition() != null) &&
					(((Measurement)object).getDefinition().getText() != null) &&
					!((Measurement)object).getDefinition().getText().isEmpty()
					)
//			if (!((Measurement)object).getDefinition().getText().isEmpty())
				subPanel2.add(new JLabel( ( ((Measurement)object).getDefinition().getText()) ));
		}
		if (object instanceof Variable) {
			String sqlString = Variable.getDraftSQLForStudy(((Variable)object).getEntityID());
			
			String projectName = getProjectName(sqlString);
			label = new JLabel("<html>from <b>" + ((projectName.length() > 0) ? projectName : "unknown") + ";</b></html>");
			label.setFont(currentFont);
			label.setName("FROM");
			
			maxFromWidth	= Math.max(label.getPreferredSize().getWidth(),		maxFromWidth);
			maxFromHeight	= Math.max(label.getPreferredSize().getHeight(),	maxFromHeight);
			fromDimension.setSize(maxFromWidth, maxFromHeight);
			label.setPreferredSize(fromDimension);
			
			panel.add(label);
			
			String measurementLevel = ((Variable)object).getLevel().toString();
			label = new JLabel("<html>Level: <b>" + ((measurementLevel.length() > 0) ? measurementLevel : "unknown") + ";</b></html>");
			label.setFont(currentFont);
			label.setName("LEVEL");
			
			maxLevelWidth	= Math.max(label.getPreferredSize().getWidth(),		maxLevelWidth);
			maxLevelHeight	= Math.max(label.getPreferredSize().getHeight(),	maxLevelHeight);
			levelDimension.setSize(maxLevelWidth, maxLevelHeight);
			label.setPreferredSize(levelDimension);
			
			panel.add(label);
			
			sqlString 			= Variable.getDraftSQLForValueList(((Variable)object).getEntityID());		
			String valueList	= getVariableValues(sqlString);
			
			subPanel1.add(new JLabel(valueList));
	
			if ( (((Variable)object).getDefinition() != null) &&
					(((Variable)object).getDefinition().getText() != null) &&
					!((Variable)object).getDefinition().getText().isEmpty()
					)
//			if (!((Variable)object).getDefinition().getText().isEmpty())
				subPanel2.add(new JLabel( ( ((Variable)object).getDefinition().getText()) ));
		}
		
		panel.setVisible(true);
		panel.setName( Integer.toString(((DBEntity)object).getEntityID()) );
		
		returnPanel.add(panel);
		returnPanel.add(subPanel1);
		
		return returnPanel;
	}
	
	/**
	 * @param sql
	 * @return
	 */
	private String getProjectName(String sql) {
		
		return CStatsCtrl.searchForProjectName(sql);
	}
	
	/**
	 * @param sql
	 * @return
	 */
	private String getVariableValues(String sql) {
		
		return CStatsCtrl.searchForVariableValues(sql);
	}
	
	/**
	 * @param sql
	 * @return
	 */
	private String getMeasurementCategories(String sql) {
		
		return CStatsCtrl.searchForMeasurementCategories(sql);
	}
	
	/**
	 * @param sql
	 * @return
	 */
	private String getProjectNote(String sql) {
		
		return CStatsCtrl.searchForProjectNote(sql);
	}
	
	/**
	 * @return
	 */
	public ArrayList<Object> getResultSet() {
		return resultList;
	}
}

package org.gesis.charmstats.view;

import java.awt.BorderLayout;
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

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.RowFilter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.gesis.charmstats.ActionCommandText;
import org.gesis.charmstats.model.AttributeComp;
import org.gesis.charmstats.model.AttributeLink;
import org.gesis.charmstats.model.AttributeLinkType;
import org.gesis.charmstats.model.Attributes;
import org.gesis.charmstats.model.CStatsModel;
import org.gesis.charmstats.model.CharacteristicMap;
import org.gesis.charmstats.model.InstanceType;
import org.gesis.charmstats.model.OperaIndicator;
import org.gesis.charmstats.model.WorkStepInstance;

/**
 * 
 * @author	Martin Friedrichs
 * @since	0.1, known former as "TabCompareVariables" / CharmStatsPro only
 *
 */
public class TabCompareMetadata extends Tab {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String QUESTION		= "tcv_question";
	private static final String METADATA_TAB	= "tcv_metadata_tab";
	private static final String VARIABLE_TAB	= "tcv_variable_tab";
	private static final String QUESTION_TAB	= "tcv_question_tab";
	private static final String STUDY_TAB		= "tcv_study_tab";
	private static final String BIAS_TAB		= "tcv_bias_tab";
	
	private static final String BLANK_STRING	= "                              ";
	
	/*
	 *	Fields
	 */
	UpperSearchCompTable	upperTable;
	JLabel 					questionLabelLbl;
	JTabbedPane 			fixedPane;
	CompareFixedTable 		fixedDialog;
	JTabbedPane 			multiPane;
	CompareVariableTable 	variableDialog;
	CompareQuestionTable 	questionDialog;
	CompareStudyTable 		studyDialog;
	JTabbedPane 			biasPane;
	BiasTable 				biasDialog;
	
	JComboBox 				operaModelCB;
	JComboBox 				indicatorCB;
	
	ArrayList<AttributeLink>	attr_link;
	
	CStatsModel				model;
	
	/*
	 *	Constructor
	 */
	/**
	 * @param locale
	 */
	public TabCompareMetadata(Locale locale) {
		super(locale);
		
		setName("TabCompareVariables");
	}
	
	/**
	 * @param model
	 * @param al
	 * @param locale
	 */
	public TabCompareMetadata(CStatsModel model, ActionListener al, Locale locale) {
		this(locale);
		
		this.model = model;
		
		currentLocale	= locale;
		resourceBundle	= ResourceBundle.getBundle(BUNDLE, currentLocale);
		
		/* Create Form Components */
		JPanel comparePanel = (JPanel)buildSearchCompPanel(model, locale);
		
		/* Add Form Components to Form Panel */
		formPanel.add(comparePanel);
		
		/* Setup Buttons */
		backButton.setActionCommand(ActionCommandText.BTN_SEA_STP_COM_TAB_BACK);
		backButton.addActionListener(al);
		noteButton.setActionCommand(ActionCommandText.BTN_SEA_STP_COM_TAB_NOTE);
		noteButton.addActionListener(al);
		importButton.setActionCommand(ActionCommandText.BTN_SEA_STP_COM_TAB_IMP);
		importButton.addActionListener(al);
		importButton.setVisible(true);
		importButton.setEnabled(true);
		nextButton.setActionCommand(ActionCommandText.BTN_SEA_STP_COM_TAB_NEXT);
		nextButton.addActionListener(al);
		
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
		upperTable.changeLanguage(locale);
		questionLabelLbl.setText(bundle.getString(QUESTION));
		fixedPane.setTitleAt(0, bundle.getString(METADATA_TAB));
		fixedDialog.changeLanguage(locale);
		multiPane.setTitleAt(0, bundle.getString(VARIABLE_TAB));
		multiPane.setTitleAt(1, bundle.getString(QUESTION_TAB));
		multiPane.setTitleAt(2, bundle.getString(STUDY_TAB));
		variableDialog.changeLanguage(locale);
		questionDialog.changeLanguage(locale);
		studyDialog.changeLanguage(locale);
		biasPane.setTitleAt(0, bundle.getString(BIAS_TAB));
		biasDialog.changeLanguage(locale);
	}
	
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.Tab#changeFont(java.awt.Font)
	 */
	public void changeFont(Font f) {
		super.changeFont(f);
		
		operaModelCB.setFont(f);
		indicatorCB.setFont(f);
		
		upperTable.changeFont(f);
		questionLabelLbl.setFont(f);
		fixedPane.setFont(f);
		fixedDialog.changeFont(f);
		fixedPane.updateUI();		
		multiPane.setFont(f);
		variableDialog.changeFont(f);
		questionDialog.changeFont(f);
		studyDialog.changeFont(f);
		multiPane.updateUI();
		biasPane.setFont(f);
		biasDialog.changeFont(f);
		biasPane.updateUI();
	}
	
	/**
	 * @param model
	 * @param locale
	 * @return
	 */
	private JComponent buildSearchCompPanel(CStatsModel model, Locale locale) {
		JPanel searchCompPanel = new JPanel();
		searchCompPanel.setLayout(new BoxLayout(searchCompPanel, BoxLayout.Y_AXIS));
		
		JSplitPane horizontalSplitPaneInner	= new JSplitPane(); // Left: fixedPane / Right: MultiPane
		JSplitPane horizontalSplitPaneOuter	= new JSplitPane(); // Left: fixedPane/MultiPane / Right: BiasPane
		
		/* Upper Part */
		JPanel upperPart = new JPanel();
		upperPart.setName("upperPart");
		upperPart.setLayout(new BoxLayout(upperPart, BoxLayout.Y_AXIS));

		/* Select Opera Model and Indicator: */
		JPanel selectPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 2));
		selectPanel.setName("selectPanel");
		
		indicatorCB = new JComboBox();
		indicatorCB.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXX");
		indicatorCB.addFocusListener(null);
		
		operaModelCB = new JComboBox();
		operaModelCB.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXX");
		operaModelCB.addFocusListener(null);
		operaModelCB.addActionListener (new ActionListener () {
		    /* (non-Javadoc)
		     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		     */
		    public void actionPerformed(ActionEvent e) {
	    		WorkStepInstance selectedData = null;
	    		
		    	if (e.getSource() != null) {
		    		if ( ((JComboBox)e.getSource()).getSelectedItem() != null) {
		    			if (((JComboBox)e.getSource()).getSelectedItem() instanceof WorkStepInstance) {
		    				WorkStepInstance inst = (WorkStepInstance)((JComboBox)e.getSource()).getSelectedItem();
		    				
			    			selectedData = inst;
			    			
			    			/* 2. ComboBox mit attr mapping passend zur instance map füllen und setzen */
			    			/* Idee: Tabelle wie ursprünglich mal verwendet nutzen */
			    			Vector<Attributes> indicators = new Vector<Attributes>();
			    			
			    			Iterator<AttributeLink> attrLinkIterator = attr_link.iterator();
			    			while(attrLinkIterator.hasNext()) {
			    				AttributeLink attrLink = attrLinkIterator.next();
			    				
			    				if ((attrLink.getInstance() instanceof WorkStepInstance) &&
			    						(attrLink.getInstance().equals(selectedData)))			    					
			    					indicators.add(attrLink.getAttribute());
			    			}
			    			
			    			DefaultComboBoxModel boxModel = new DefaultComboBoxModel(indicators);
			    			indicatorCB.setModel(boxModel);
			    			
			    			if (indicatorCB.getItemCount() > 0)
			    				indicatorCB.setSelectedIndex(0);
		    				
			    	    };		    				
		    			}
		    		}
		    	}
		});
		operaModelCB.addFocusListener(this);
		
		indicatorCB.addActionListener (new ActionListener () {
		    /* (non-Javadoc)
		     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		     */
		    public void actionPerformed(ActionEvent e) {
	    		OperaIndicator selectedData = null;
	    		
	    		@SuppressWarnings("unused")
				AttributeComp comp = null;
	    		@SuppressWarnings("unused")
				AttributeLink source = null;
	    		@SuppressWarnings("unused")
				AttributeLink target = null;
	    		
		    	if (e.getSource() != null) {
		    		if ( ((JComboBox)e.getSource()).getSelectedItem() != null) {
		    			if (((JComboBox)e.getSource()).getSelectedItem() instanceof OperaIndicator) {
		    				OperaIndicator ind = (OperaIndicator)((JComboBox)e.getSource()).getSelectedItem();
		    				
			    			selectedData = ind;
			    			
			    			for (int i=0; i<upperTable.table.getModel().getRowCount(); i++)
				    			upperTable.table.getModel().setValueAt(selectedData, i, UpperSearchCompTable.FILTER_COLUMN);
			    			
		    	    	    RowFilter<Object, Object> upper0Filter = new RowFilter<Object, Object>() {
		    	    	    	@SuppressWarnings("rawtypes")
								public boolean include(Entry entry) {	    	    	    		
		    	    	    		if (entry.getValue(UpperSearchCompTable.OBJECT_COLUMN) != null)
		    	    	    			return (entry.getValue(UpperSearchCompTable.OBJECT_COLUMN).equals((entry.getValue(UpperSearchCompTable.FILTER_COLUMN))));
		    	    				else
		    	    					return false;
		    	    	    	}
		    	    	    };
				    	    TableRowSorter<TableModel> upper0Sorter = new TableRowSorter<TableModel>((TableModel) upperTable.table.getModel());
				    	    upper0Sorter.setRowFilter(upper0Filter);
				    	    upperTable.table.setRowSorter(upper0Sorter);
			    			
			    			
			    			for (int i=0; i<fixedDialog.table.getModel().getRowCount(); i++)
				    			fixedDialog.table.getModel().setValueAt(selectedData, i, CompareFixedTable.FILTER_COLUMN);
			    			
		    	    	    RowFilter<Object, Object> fixed0Filter = new RowFilter<Object, Object>() {
		    	    	    	@SuppressWarnings("rawtypes")
								public boolean include(Entry entry) {	    	    	    		
		    	    	    		if (((AttributeComp)entry.getValue(CompareFixedTable.OBJECT_COLUMN)).getTargetAttribute().getAttribute() != null)
		    	    	    			return (((AttributeComp)entry.getValue(CompareFixedTable.OBJECT_COLUMN)).getTargetAttribute().getAttribute().getEntityID() == 
    	    										( (((OperaIndicator)entry.getValue(CompareFixedTable.FILTER_COLUMN)) != null) ? ((OperaIndicator)entry.getValue(CompareFixedTable.FILTER_COLUMN)).getEntityID() : -111 )
		    	    	    						);
		    	    				else
		    	    					return false;
		    	    	    	}
		    	    	    };
				    	    TableRowSorter<TableModel> fixed0Sorter = new TableRowSorter<TableModel>((TableModel) fixedDialog.table.getModel());
				    	    fixed0Sorter.setRowFilter(fixed0Filter);
				    	    fixedDialog.table.setRowSorter(fixed0Sorter);
			    			
			    			for (int i=0; i<variableDialog.table.getModel().getRowCount(); i++)
				    			variableDialog.table.getModel().setValueAt(selectedData, i, CompareVariableTable.FILTER_COLUMN);
			    			
		    	    	    RowFilter<Object, Object> variable0Filter = new RowFilter<Object, Object>() {
		    	    	    	@SuppressWarnings("rawtypes")
								public boolean include(Entry entry) {	    	    	    		
		    	    	    		if (((AttributeComp)entry.getValue(CompareVariableTable.OBJECT_COLUMN)).getTargetAttribute().getAttribute() != null)
		    	    	    			return (((AttributeComp)entry.getValue(CompareVariableTable.OBJECT_COLUMN)).getTargetAttribute().getAttribute().getEntityID() == 
    	    										( (((OperaIndicator)entry.getValue(CompareVariableTable.FILTER_COLUMN)) != null) ? ((OperaIndicator)entry.getValue(CompareVariableTable.FILTER_COLUMN)).getEntityID() : -111 )
		    	    	    						);
		    	    				else
		    	    					return false;
		    	    	    	}
		    	    	    };
				    	    TableRowSorter<TableModel> variable0Sorter = new TableRowSorter<TableModel>((TableModel) variableDialog.table.getModel());
				    	    variable0Sorter.setRowFilter(variable0Filter);
				    	    variableDialog.table.setRowSorter(variable0Sorter);
			    			
			    			for (int i=0; i<questionDialog.table.getModel().getRowCount(); i++)
				    			questionDialog.table.getModel().setValueAt(selectedData, i, CompareQuestionTable.FILTER_COLUMN);
			    			
			   	    	    RowFilter<Object, Object> question0Filter = new RowFilter<Object, Object>() {
		    	    	    	@SuppressWarnings("rawtypes")
								public boolean include(Entry entry) {	    	    	    		
		    	    	    		if (((AttributeComp)entry.getValue(CompareQuestionTable.OBJECT_COLUMN)).getTargetAttribute().getAttribute() != null)
		    	    	    			return (((AttributeComp)entry.getValue(CompareQuestionTable.OBJECT_COLUMN)).getTargetAttribute().getAttribute().getEntityID() == 
    	    									( (((OperaIndicator)entry.getValue(CompareQuestionTable.FILTER_COLUMN)) != null) ? ((OperaIndicator)entry.getValue(CompareQuestionTable.FILTER_COLUMN)).getEntityID() : -111 )
		    	    	    					);
		    	    				else
		    	    					return false;
		    	    	    	}
		    	    	    };
				    	    TableRowSorter<TableModel> question0Sorter = new TableRowSorter<TableModel>((TableModel) questionDialog.table.getModel());
				    	    question0Sorter.setRowFilter(question0Filter);
				    	    questionDialog.table.setRowSorter(question0Sorter);
			    			
			    			for (int i=0; i<studyDialog.table.getModel().getRowCount(); i++)
				    			studyDialog.table.getModel().setValueAt(selectedData, i, CompareStudyTable.FILTER_COLUMN);
			    			
			   	    	    RowFilter<Object, Object> study0Filter = new RowFilter<Object, Object>() {
		    	    	    	@SuppressWarnings("rawtypes")
								public boolean include(Entry entry) {	    	    	    		
		    	    	    		if (((AttributeComp)entry.getValue(CompareStudyTable.OBJECT_COLUMN)).getTargetAttribute().getAttribute() != null)
		    	    	    			return (((AttributeComp)entry.getValue(CompareStudyTable.OBJECT_COLUMN)).getTargetAttribute().getAttribute().getEntityID() == 
    	    	    							( (((OperaIndicator)entry.getValue(CompareStudyTable.FILTER_COLUMN)) != null) ? ((OperaIndicator)entry.getValue(CompareStudyTable.FILTER_COLUMN)).getEntityID() : -111 )
		    	    	    					);
		    	    				else
		    	    					return false;
		    	    	    	}
		    	    	    };
				    	    TableRowSorter<TableModel> study0Sorter = new TableRowSorter<TableModel>((TableModel) studyDialog.table.getModel());
				    	    study0Sorter.setRowFilter(study0Filter);
				    	    studyDialog.table.setRowSorter(study0Sorter);
			    			
			    			for (int i=0; i<biasDialog.table.getModel().getRowCount(); i++)
				    			biasDialog.table.getModel().setValueAt(selectedData, i, BiasTable.FILTER_COLUMN);
			    			
			   	    	    RowFilter<Object, Object> bias0Filter = new RowFilter<Object, Object>() {
		    	    	    	@SuppressWarnings("rawtypes")
								public boolean include(Entry entry) {	    	    	    		
		    	    	    		if (((AttributeComp)entry.getValue(BiasTable.OBJECT_COLUMN)).getTargetAttribute().getAttribute() != null) {		    	    	    			
		    	    	    			return (((AttributeComp)entry.getValue(BiasTable.OBJECT_COLUMN)).getTargetAttribute().getAttribute().getEntityID() == 
		    	    	    					( (((OperaIndicator)entry.getValue(BiasTable.FILTER_COLUMN)) != null) ? ((OperaIndicator)entry.getValue(BiasTable.FILTER_COLUMN)).getEntityID() : -111 )
		    	    	    					);
		    	    	    		}
		    	    				else
		    	    					return false;
		    	    	    	}
		    	    	    };
				    	    TableRowSorter<TableModel> bias0Sorter = new TableRowSorter<TableModel>((TableModel) biasDialog.table.getModel());
				    	    bias0Sorter.setRowFilter(bias0Filter);
				    	    biasDialog.table.setRowSorter(bias0Sorter);
			    			
			    	    };		    				
		    			}
		    		}
		    	}

		});
		indicatorCB.addFocusListener(this);
		
		WorkStepInstance layer = new WorkStepInstance();
		
		Object[] operaModel = {layer};
		operaModelCB.setModel(new DefaultComboBoxModel(operaModel));
		
		selectPanel.add(operaModelCB);
		selectPanel.add(indicatorCB);
		selectPanel.setPreferredSize(new Dimension(500, 30));
		
		
		// Show Metadata from Indicator //
		JPanel tablePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		tablePanel.setName("tablePanel");
				
		upperTable = new UpperSearchCompTable(locale);
	    RowFilter<Object, Object> upper0Filter = new RowFilter<Object, Object>() {
	    	/* (non-Javadoc)
	    	 * @see javax.swing.RowFilter#include(javax.swing.RowFilter.Entry)
	    	 */
	    	@SuppressWarnings("rawtypes")
			public boolean include(Entry entry) {
	    		return false; 
	    	}
	    };
	    TableRowSorter<TableModel> upper0Sorter = new TableRowSorter<TableModel>((TableModel) upperTable.table.getModel());
	    upper0Sorter.setRowFilter(upper0Filter);
	    upperTable.table.setRowSorter(upper0Sorter);
		
		tablePanel.add(upperTable);
		tablePanel.setPreferredSize(new Dimension(500, 45));
	
		
		JPanel questionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 2));
		
		questionLabelLbl = new JLabel(resourceBundle.getString(QUESTION));
		JLabel questionTextLbl = new JLabel();
		
		questionPanel.add(questionLabelLbl);
		questionPanel.add(questionTextLbl);
		questionPanel.setPreferredSize(new Dimension(500, 24));
		
		
		// Bring Selection and Metadata together //
		upperPart.add(selectPanel);
		upperPart.add(tablePanel);
		upperPart.add(questionPanel);
		upperPart.setPreferredSize(new Dimension(500, 120));
		
		// Lower Part 
		JPanel lowerPart = new JPanel();
		lowerPart.setName("lowerPart");
		lowerPart.setLayout(new BorderLayout());
		lowerPart.setPreferredSize(new Dimension(500, 200));
		
		fixedDialog = new CompareFixedTable(locale);
		fixedDialog.setName("fixedDialog");
	    RowFilter<Object, Object> fixed0Filter = new RowFilter<Object, Object>() {
	    	/* (non-Javadoc)
	    	 * @see javax.swing.RowFilter#include(javax.swing.RowFilter.Entry)
	    	 */
	    	@SuppressWarnings("rawtypes")
			public boolean include(Entry entry) {
	    		return false; 
	    	}
	    };
	    TableRowSorter<TableModel> fixed0Sorter = new TableRowSorter<TableModel>((TableModel) fixedDialog.table.getModel());
	    fixed0Sorter.setRowFilter(fixed0Filter);
	    fixedDialog.table.setRowSorter(fixed0Sorter);
		
		fixedPane = new JTabbedPane();
		JPanel fixedPanel = new JPanel(new BorderLayout());
		
		JScrollPane scrollPaneFixed = new JScrollPane(fixedDialog);
		scrollPaneFixed.getVerticalScrollBar().setUnitIncrement(16);
		
		fixedPanel.add(scrollPaneFixed, BorderLayout.CENTER);
		
		fixedPane.insertTab(resourceBundle.getString(METADATA_TAB), null, fixedPanel, "", 0);
		
		multiPane = new JTabbedPane();
		variableDialog = new CompareVariableTable(locale);
		variableDialog.setName("variableDialog");
	    RowFilter<Object, Object> variable0Filter = new RowFilter<Object, Object>() {
	    	/* (non-Javadoc)
	    	 * @see javax.swing.RowFilter#include(javax.swing.RowFilter.Entry)
	    	 */
	    	@SuppressWarnings("rawtypes")
			public boolean include(Entry entry) {
	    		return false; 
	    	}
	    };
	    TableRowSorter<TableModel> variable0Sorter = new TableRowSorter<TableModel>((TableModel) variableDialog.table.getModel());
	    variable0Sorter.setRowFilter(variable0Filter);
	    variableDialog.table.setRowSorter(variable0Sorter);
		
		JScrollPane scrollPaneVariable = new JScrollPane(variableDialog);
		scrollPaneVariable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneVariable.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPaneVariable.getVerticalScrollBar().setUnitIncrement(16);
		multiPane.insertTab(resourceBundle.getString(VARIABLE_TAB), null, scrollPaneVariable, "", 0);
		questionDialog = new CompareQuestionTable(locale);
		questionDialog.setName("questionDialog");
	    RowFilter<Object, Object> question0Filter = new RowFilter<Object, Object>() {
	    	/* (non-Javadoc)
	    	 * @see javax.swing.RowFilter#include(javax.swing.RowFilter.Entry)
	    	 */
	    	@SuppressWarnings("rawtypes")
			public boolean include(Entry entry) {
	    		return false; 
	    	}
	    };
	    TableRowSorter<TableModel> question0Sorter = new TableRowSorter<TableModel>((TableModel) questionDialog.table.getModel());
	    question0Sorter.setRowFilter(question0Filter);
	    questionDialog.table.setRowSorter(question0Sorter);
		JScrollPane scrollPaneQuestion = new JScrollPane(questionDialog);
		scrollPaneQuestion.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneQuestion.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPaneQuestion.getVerticalScrollBar().setUnitIncrement(16);
		multiPane.insertTab(resourceBundle.getString(QUESTION_TAB), null, scrollPaneQuestion,
	        	"", 1);
		studyDialog = new CompareStudyTable(locale);
		studyDialog.setName("studyDialog");
	    RowFilter<Object, Object> study0Filter = new RowFilter<Object, Object>() {
	    	/* (non-Javadoc)
	    	 * @see javax.swing.RowFilter#include(javax.swing.RowFilter.Entry)
	    	 */
	    	@SuppressWarnings("rawtypes")
			public boolean include(Entry entry) {
	    		return false; 
	    	}
	    };
	    TableRowSorter<TableModel> study0Sorter = new TableRowSorter<TableModel>((TableModel) studyDialog.table.getModel());
	    study0Sorter.setRowFilter(study0Filter);
	    studyDialog.table.setRowSorter(study0Sorter);
		JScrollPane scrollPaneStudy = new JScrollPane(studyDialog);
		scrollPaneStudy.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneStudy.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPaneStudy.getVerticalScrollBar().setUnitIncrement(16);
		multiPane.insertTab(resourceBundle.getString(STUDY_TAB), null, scrollPaneStudy, "", 2);
		
		JPanel fixedPart = new JPanel();
		fixedPart.setLayout(new BoxLayout(fixedPart, BoxLayout.Y_AXIS));
		
		biasPane = new JTabbedPane();
		JScrollPane scrollPaneBias = new JScrollPane(fixedPart);
		scrollPaneBias.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneBias.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPaneBias.getVerticalScrollBar().setUnitIncrement(16);
		biasPane.insertTab(BIAS_TAB, null, scrollPaneBias, "", 0);		
		biasDialog = new BiasTable(locale);
		biasDialog.setName("biasDialog");
	    RowFilter<Object, Object> bias0Filter = new RowFilter<Object, Object>() {
	    	/* (non-Javadoc)
	    	 * @see javax.swing.RowFilter#include(javax.swing.RowFilter.Entry)
	    	 */
	    	@SuppressWarnings("rawtypes")
			public boolean include(Entry entry) {
	    		return false; 
	    	}
	    };
	    TableRowSorter<TableModel> bias0Sorter = new TableRowSorter<TableModel>((TableModel) biasDialog.table.getModel());
	    bias0Sorter.setRowFilter(bias0Filter);
	    biasDialog.table.setRowSorter(bias0Sorter); 
		
		fixedPart.add(biasDialog);
				
		horizontalSplitPaneInner = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				fixedPane, multiPane);
			horizontalSplitPaneInner.setOneTouchExpandable(true);
			horizontalSplitPaneInner.setDividerLocation(180);

			//Provide minimum sizes for the two components in the split pane
			Dimension minimumSize = new Dimension(200, 200);
			fixedPane.setMinimumSize(minimumSize);
			multiPane.setMinimumSize(minimumSize);
		horizontalSplitPaneOuter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
					horizontalSplitPaneInner, biasPane);
		horizontalSplitPaneOuter.setOneTouchExpandable(true);
		biasPane.setMinimumSize(minimumSize);
				
		lowerPart.add(horizontalSplitPaneOuter);
		
		/* Search'n'Comp (finished)*/
		searchCompPanel.add(upperPart);
		searchCompPanel.add(lowerPart);
		
		return searchCompPanel;
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
			
			model.getProject().getContent().setCharacteristicMaps(new ArrayList<CharacteristicMap>()); // ??
		}
		
		fillModel(model); 		
	}
	
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.Tab#fillModel(org.gesis.charmstats.model.CStatsModel)
	 */
	public void fillModel(CStatsModel model) {
		DefaultComboBoxModel boxModel = (DefaultComboBoxModel)operaModelCB.getModel();
		boxModel.removeAllElements();
		operaModelCB.addItem(BLANK_STRING);
		ArrayList<WorkStepInstance> instances = model.getProject().getContent().getLayers(InstanceType.OPERATIONAL);		
		Iterator<WorkStepInstance> instancesIterator = instances.iterator();
		while(instancesIterator.hasNext()) {
			WorkStepInstance instance = instancesIterator.next();
			
			addInstanceEntry(instance);
		}
		
		attr_link = model.getProject().getContent().getLinksByType(AttributeLinkType.INDICATOR);
		
		boxModel = (DefaultComboBoxModel)indicatorCB.getModel();
		boxModel.removeAllElements();
		indicatorCB.addItem(BLANK_STRING);
		
		/* Fill upperTable.table: */
		upperTable.indicatorTableModel.setRowCount(0);
		upperTable.addRows(model);
		
	    RowFilter<Object, Object> upper0Filter = new RowFilter<Object, Object>() {
	    	/* (non-Javadoc)
	    	 * @see javax.swing.RowFilter#include(javax.swing.RowFilter.Entry)
	    	 */
	    	@SuppressWarnings("rawtypes")
			public boolean include(Entry entry) {
	    		return false; 
	    	}
	    };
	    TableRowSorter<TableModel> upper0Sorter = new TableRowSorter<TableModel>((TableModel) upperTable.table.getModel());
	    upper0Sorter.setRowFilter(upper0Filter);
	    upperTable.table.setRowSorter(upper0Sorter);	
		
	    /* Fill fixedDialog.table: */
	    fixedDialog.fixedTableModel.setRowCount(0);
	    fixedDialog.addRows(model);
	    
	    RowFilter<Object, Object> fixed0Filter = new RowFilter<Object, Object>() {
	    	/* (non-Javadoc)
	    	 * @see javax.swing.RowFilter#include(javax.swing.RowFilter.Entry)
	    	 */
	    	@SuppressWarnings("rawtypes")
			public boolean include(Entry entry) {
	    		return false; 
	    	}
	    };
	    TableRowSorter<TableModel> fixed0Sorter = new TableRowSorter<TableModel>((TableModel) fixedDialog.table.getModel());
	    fixed0Sorter.setRowFilter(fixed0Filter);
	    fixedDialog.table.setRowSorter(fixed0Sorter);
	    
	    /* Fill variableDialog.table: */
	    variableDialog.variableTableModel.setRowCount(0);
	    variableDialog.addRows(model);
	    
	    RowFilter<Object, Object> variable0Filter = new RowFilter<Object, Object>() {
	    	/* (non-Javadoc)
	    	 * @see javax.swing.RowFilter#include(javax.swing.RowFilter.Entry)
	    	 */
	    	@SuppressWarnings("rawtypes")
			public boolean include(Entry entry) {
	    		return false; 
	    	}
	    };
	    TableRowSorter<TableModel> variable0Sorter = new TableRowSorter<TableModel>((TableModel) variableDialog.table.getModel());
	    variable0Sorter.setRowFilter(variable0Filter);
	    variableDialog.table.setRowSorter(variable0Sorter);
	    
	    /* Fill questionDialog.table: */
	    questionDialog.questionTableModel.setRowCount(0);
	    questionDialog.addRows(model);
	    
	    RowFilter<Object, Object> question0Filter = new RowFilter<Object, Object>() {
	    	/* (non-Javadoc)
	    	 * @see javax.swing.RowFilter#include(javax.swing.RowFilter.Entry)
	    	 */
	    	@SuppressWarnings("rawtypes")
			public boolean include(Entry entry) {
	    		return false; 
	    	}
	    };
	    TableRowSorter<TableModel> question0Sorter = new TableRowSorter<TableModel>((TableModel) questionDialog.table.getModel());
	    question0Sorter.setRowFilter(question0Filter);
	    questionDialog.table.setRowSorter(question0Sorter);
	    
	    /* Fill studyDialog.table: */
	    studyDialog.studyTableModel.setRowCount(0);
	    studyDialog.addRows(model);
	    
	    RowFilter<Object, Object> study0Filter = new RowFilter<Object, Object>() {
	    	/* (non-Javadoc)
	    	 * @see javax.swing.RowFilter#include(javax.swing.RowFilter.Entry)
	    	 */
	    	@SuppressWarnings("rawtypes")
			public boolean include(Entry entry) {
	    		return false; 
	    	}
	    };
	    TableRowSorter<TableModel> study0Sorter = new TableRowSorter<TableModel>((TableModel) studyDialog.table.getModel());
	    study0Sorter.setRowFilter(study0Filter);
	    studyDialog.table.setRowSorter(study0Sorter);
	    
	    /* Fill biasDialog.table: */
	    biasDialog.biasTableModel.setRowCount(0);
	    biasDialog.addRows(model);
	    
	    RowFilter<Object, Object> bias0Filter = new RowFilter<Object, Object>() {
	    	/* (non-Javadoc)
	    	 * @see javax.swing.RowFilter#include(javax.swing.RowFilter.Entry)
	    	 */
	    	@SuppressWarnings("rawtypes")
			public boolean include(Entry entry) {
	    		return false; 
	    	}
	    };
	    TableRowSorter<TableModel> bias0Sorter = new TableRowSorter<TableModel>((TableModel) biasDialog.table.getModel());
	    bias0Sorter.setRowFilter(bias0Filter);
	    biasDialog.table.setRowSorter(bias0Sorter);
		
		if ((model.getProject().getFinishedSince() != null) ||	
				(!model.getProject().isEditedByUser())) {		
			importButton.setEnabled(false);
			upperTable.setTableEnabled(false);
			variableDialog.setTableEnabled(false);
			questionDialog.setTableEnabled(false);
			studyDialog.setTableEnabled(false);
			biasDialog.setTableEnabled(false);
		} else {
			importButton.setEnabled(true);
			upperTable.setTableEnabled(false);
			variableDialog.setTableEnabled(false);
			questionDialog.setTableEnabled(false);
			studyDialog.setTableEnabled(false);
			biasDialog.setTableEnabled(true);
		}
		
		if (operaModelCB.getItemCount() > 1)
			operaModelCB.setSelectedIndex(1);
	}
	
	/**
	 * @param inst
	 */
	public void addInstanceEntry(WorkStepInstance inst) {
		operaModelCB.addItem(inst);
	}

	/**
	 * @return
	 */
	public Attributes getSourceAttribute() {		
		return (OperaIndicator)indicatorCB.getSelectedItem();
	}

	/**
	 * @return
	 */
	public WorkStepInstance getSourceInstance() {
		return (WorkStepInstance)operaModelCB.getSelectedItem();
	}
}

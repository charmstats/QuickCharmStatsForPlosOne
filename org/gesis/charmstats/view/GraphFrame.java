package org.gesis.charmstats.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Observable;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;

import org.gesis.charmstats.ActionCommandID;
import org.gesis.charmstats.ActionCommandText;
import org.gesis.charmstats.IdentifiedParameter;
import org.gesis.charmstats.model.AttributeLink;
import org.gesis.charmstats.model.AttributeMap;
import org.gesis.charmstats.model.AttributeMapType;
import org.gesis.charmstats.model.Attributes;
import org.gesis.charmstats.model.CStatsModel;
import org.gesis.charmstats.model.Category;
import org.gesis.charmstats.model.CharacteristicLink;
import org.gesis.charmstats.model.CharacteristicMap;
import org.gesis.charmstats.model.Characteristics;
import org.gesis.charmstats.model.ConDimension;
import org.gesis.charmstats.model.ConSpecification;
import org.gesis.charmstats.model.InstanceMap;
import org.gesis.charmstats.model.InstanceMapType;
import org.gesis.charmstats.model.Measurement;
import org.gesis.charmstats.model.OperaIndicator;
import org.gesis.charmstats.model.OperaPrescription;
import org.gesis.charmstats.model.ProContent;
import org.gesis.charmstats.model.Project;
import org.gesis.charmstats.model.Value;
import org.gesis.charmstats.model.Variable;
import org.gesis.charmstats.view.Graph.AssignmentEdge;
import org.gesis.charmstats.view.Graph.CategoryCell;
import org.gesis.charmstats.view.Graph.CategoryEdge;
import org.gesis.charmstats.view.Graph.CharacteristicEdge;
import org.gesis.charmstats.view.Graph.ConceptualJGraph;
import org.gesis.charmstats.view.Graph.DimensionCell;
import org.gesis.charmstats.view.Graph.GCellViewFactory;
import org.gesis.charmstats.view.Graph.GJGraph;
import org.gesis.charmstats.view.Graph.GJGraphScrollPane;
import org.gesis.charmstats.view.Graph.IndicatorCell;
import org.gesis.charmstats.view.Graph.LabelCell;
import org.gesis.charmstats.view.Graph.MeasurementCell;
import org.gesis.charmstats.view.Graph.PrescriptionCell;
import org.gesis.charmstats.view.Graph.PrescriptionEdge;
import org.gesis.charmstats.view.Graph.SpecificationCell;
import org.gesis.charmstats.view.Graph.SpecificationEdge;
import org.gesis.charmstats.view.Graph.ValueCell;
import org.gesis.charmstats.view.Graph.ValueEdge;
import org.gesis.charmstats.view.Graph.VariableCell;
import org.jgraph.JGraph;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.DefaultPort;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.GraphModel;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class GraphFrame extends InternalFrame {

//	/* RESOURCES */
//	public static final String CONTROL_BOX_ICON	= "org/gesis/charmstats/Resources/graph.GIF";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String BUNDLE	= "org.gesis.charmstats.resources.DesktopBundle";
	public static final String TITLE	= "graph_frame_title";
	public static final String PRINT	= "graph_print";
	public static final String SAVE		= "graph_save";
	public static final String OVERVIEW	= "graph_overview";
	public static final String MAPPING	= "graph_mapping";
	public static final String MEA		= "graph_mea";
	public static final String MAP_MEA	= "graph_map_mea";
	public static final String CON		= "graph_con";
	public static final String MAP_CON	= "graph_map_con";
	public static final String OPERA	= "graph_opera";
	public static final String MAP_OPERA	= "graph_map_opera";
	public static final String DATA		= "graph_data";
	public static final String SWAPED	= "swaped_direction";
	
	public static final String OVERWRITE_FILE_QUE	= "overwrite_file_que";
	public static final String CONFIRM_OVERWRITE	= "confirm_overwrite";
	
	public static final String NO_PRJ_NAME = "";
	
	public static final int	NO_GRAPH = 0;
	
	private static final String BUNDLE_F	= "org.gesis.charmstats.resources.FrameBundle";
	private static final String FILE_NAME	= "file_name";
	
	/*
	 *	Fields
	 */
	JPanel				form;
	JGraph				outputGraph				= new JGraph();
	GJGraphScrollPane	graphPane;
	JPanel				buttonPanel				= new JPanel();
	JPanel				sidePanel				= new JPanel();
	JButton				showOverviewBtn;
	JButton				showMappingBtn;
	JButton				showMeaBtn;
	JButton				showMapMeaBtn;
	JButton				showConBtn;
	JButton				showMapConBtn;
	JButton				showOperaBtn;
	JButton				showMapOperaBtn;
	JButton				showDataBtn;
	JButton				printBtn;
	JButton				saveBtn;
	
	JCheckBox			swapedChk;
	
	int					selectedGraph = NO_GRAPH;
	
	/*
	 *	Constructor
	 */
	/**
	 * @param dimension
	 * @param location
	 * @param visible
	 * @param resizable
	 * @param maximizable
	 * @param iconifiable
	 * @param closable
	 * @param locale
	 * @param al
	 */
	public GraphFrame(
			Dimension dimension,
			Point location,
			boolean visible, 
			boolean resizable,
			boolean maximizable,
			boolean iconifiable,
			boolean closable,
			Locale locale,
			ActionListener al) {
		super(dimension, location, visible, resizable, maximizable, iconifiable, closable);
		
		currentLocale	= locale;
		resourceBundle	= ResourceBundle.getBundle(BUNDLE, currentLocale);
		
		setTitle(resourceBundle.getString(TITLE));
		
		ImageIcon icon = createImageIcon(CStatsGUI.GRAPH_ICON,"");
		setFrameIcon(icon);
        		
	    outputGraph = new GJGraph(new DefaultGraphModel());
		
		graphPane = new GJGraphScrollPane();
		graphPane.setGraph(outputGraph);
		graphPane.setViewportView(outputGraph);
		graphPane.getVerticalScrollBar().setUnitIncrement(16);
		
		form		= new JPanel();
		form.setLayout(new BorderLayout());
		
		/* Build Button Panel */
		showOverviewBtn	= new JButton(resourceBundle.getString(OVERVIEW));
		showOverviewBtn.setActionCommand(ActionCommandText.BTN_FRM_GRP_SHW_OVER_GRAPH);
        showOverviewBtn.addActionListener(al);
        showOverviewBtn.setHorizontalAlignment(SwingConstants.LEFT);
		showMappingBtn	= new JButton(resourceBundle.getString(MAPPING));
		showMappingBtn.setActionCommand(ActionCommandText.BTN_FRM_GRP_SHW_MAP_GRAPH);
        showMappingBtn.addActionListener(al);
        showMappingBtn.setHorizontalAlignment(SwingConstants.LEFT);
		showMeaBtn	= new JButton(resourceBundle.getString(MEA));
		showMeaBtn.setActionCommand(ActionCommandText.BTN_FRM_GRP_SHW_MEA_GRAPH);
        showMeaBtn.addActionListener(al);
        showMeaBtn.setHorizontalAlignment(SwingConstants.LEFT);
		showMapMeaBtn	= new JButton(resourceBundle.getString(MAP_MEA));
		showMapMeaBtn.setActionCommand(ActionCommandText.BTN_FRM_GRP_SHW_MAP_MEA_GRAPH);
        showMapMeaBtn.addActionListener(al);
        showMapMeaBtn.setHorizontalAlignment(SwingConstants.LEFT);
		showConBtn	= new JButton(resourceBundle.getString(CON));
		showConBtn.setActionCommand(ActionCommandText.BTN_FRM_GRP_SHW_CON_GRAPH);
        showConBtn.addActionListener(al);
        showConBtn.setHorizontalAlignment(SwingConstants.LEFT);
		showMapConBtn	= new JButton(resourceBundle.getString(MAP_CON));
		showMapConBtn.setActionCommand(ActionCommandText.BTN_FRM_GRP_SHW_MAP_CON_GRAPH);
        showMapConBtn.addActionListener(al);
        showMapConBtn.setHorizontalAlignment(SwingConstants.LEFT);
		showOperaBtn= new JButton(resourceBundle.getString(OPERA));
		showOperaBtn.setActionCommand(ActionCommandText.BTN_FRM_GRP_SHW_OPE_GRAPH);
        showOperaBtn.addActionListener(al);
        showOperaBtn.setHorizontalAlignment(SwingConstants.LEFT);
		showMapOperaBtn= new JButton(resourceBundle.getString(MAP_OPERA));
		showMapOperaBtn.setActionCommand(ActionCommandText.BTN_FRM_GRP_SHW_MAP_OPE_GRAPH);
        showMapOperaBtn.addActionListener(al);
        showMapOperaBtn.setHorizontalAlignment(SwingConstants.LEFT);
		showDataBtn	= new JButton(resourceBundle.getString(DATA));
		showDataBtn.setActionCommand(ActionCommandText.BTN_FRM_GRP_SHW_DAT_GRAPH);
        showDataBtn.addActionListener(al);
        showDataBtn.setHorizontalAlignment(SwingConstants.LEFT);
        
		printBtn	= new JButton(resourceBundle.getString(PRINT));
		printBtn.setActionCommand(ActionCommandText.BTN_FRM_GRP_PRINT_GRAPH);
        printBtn.addActionListener(al);
		saveBtn		= new JButton(resourceBundle.getString(SAVE));
		saveBtn.setActionCommand(ActionCommandText.BTN_FRM_GRP_SAVE_GRAPH);
        saveBtn.addActionListener(al);
        swapedChk	= new JCheckBox(resourceBundle.getString(SWAPED));
        
        GridLayout experimentLayout = new GridLayout(0,1);
        sidePanel.setLayout(experimentLayout);
        sidePanel.add(showOverviewBtn);
        sidePanel.add(showMappingBtn);
        sidePanel.add(showMeaBtn);
        sidePanel.add(showMapMeaBtn);
        sidePanel.add(showConBtn);
        sidePanel.add(showMapConBtn);
        sidePanel.add(showOperaBtn);
        sidePanel.add(showMapOperaBtn);
        sidePanel.add(showDataBtn);
        
		buttonPanel.add(printBtn);
		buttonPanel.add(saveBtn);
		buttonPanel.add(swapedChk);
		
		form.add(sidePanel, BorderLayout.WEST);
	    form.add(graphPane, BorderLayout.CENTER);
	    form.add(buttonPanel, BorderLayout.SOUTH);
		
		setContentPane(form);
	}
	
	/**
	 * @return
	 */
	public JGraph clearGraph() {
		GraphModel			clearModel	= new DefaultGraphModel();
		GraphLayoutCache	clearCache 	= new GraphLayoutCache(clearModel, new GCellViewFactory(), true); // expanding and collapsing
		JGraph				clearGraph	= new GJGraph(clearModel, clearCache); // expanding and collapsing
		
		clearGraph.setSizeable(false);
		clearGraph.setEditable(false);
		
		return clearGraph;
	}
	
	/**
	 * @param model
	 * @return
	 */
	public JGraph createOverviewGraph(CStatsModel model, boolean swaped) {
		int columnWidth		= 195;
		int instanceWidth	= 180;
		int instanceHeight	= 48;
		int measureWidth	= 80;
		int measureHeight	= 80;
		int dimensionWidth	= 80;
		int dimensionHeight	= 48;
		int indicatorWidth	= 80;
		int indicatorHeight	= 48;
		int variableWidth	= 80;
		int variableHeight	= 48;
		
		GraphModel			overviewModel	= new DefaultGraphModel();
		GraphLayoutCache	overviewCache 	= new GraphLayoutCache(overviewModel, new GCellViewFactory(), true); // expanding and collapsing
		JGraph				overviewGraph	= new ConceptualJGraph(overviewModel, overviewCache); // expanding and collapsing
		
		overviewGraph.setSizeable(false);
		overviewGraph.setEditable(false);
		
		if (!NO_PRJ_NAME.equals(model.getProject().getName())) {
			
			Measurement					mea		= model.getProject().getContent().getMeasurement();
			ArrayList<ConDimension>		dims	= model.getProject().getContent().getDimensions();
			ArrayList<OperaIndicator>	inds	= model.getProject().getContent().getIndicators();
			ArrayList<Variable>			vars	= model.getProject().getContent().getVariables();
			
			int xBase = 25; int columnCounter; 
			int yBase = 25; int rowCounter = 0;
			
			/* Instance of Measurement */
			columnCounter = (swaped ? 5 : 0);
			
			AttributeLink attrLink = model.getProject().getContent().getLinkByAttribute(mea);
			if ((attrLink != null) &&
				(attrLink.getInstance() != null)) {
					String label = attrLink.getInstance().getLabel();
	
					DefaultGraphCell labelCell = generateLabelVertex(label,
							label,
							null,
							xBase + (columnCounter * columnWidth), yBase, instanceWidth, instanceHeight, 
							Color.ORANGE, 
							false);		
					// Insert the vertex into the graph view (including child port and attributes):
					overviewCache.insert(labelCell);
			}
			
			/* Measurement */
			columnCounter = (swaped ? 4 : 1);
			
			DefaultGraphCell meaCell = generateMeaVertex(mea.getName(),
					mea,
					null,
					xBase + (columnCounter * columnWidth), yBase, measureWidth, measureHeight, 
					Color.ORANGE, 
					false);		
			// Insert the vertex into the graph view (including child port and attributes):
			overviewCache.insert(meaCell);
								
			/* Dimensions */
			columnCounter = (swaped ? 3 : 2);
			
			Iterator<ConDimension> 		itDim = dims.iterator();		
			ArrayList<DimensionCell> dimCells = new ArrayList<DimensionCell>();

			rowCounter = 0;
			while (itDim.hasNext()) {
				ConDimension dim = itDim.next();
				
				DefaultGraphCell dimCell = generateDimVertex(dim.getLabel(),
						dim,
						null,
						xBase + (columnCounter * columnWidth), yBase + (rowCounter * 65), dimensionWidth, dimensionHeight, 
						Color.ORANGE, 
						false);
				dimCells.add((DimensionCell)dimCell);
				// Insert the vertex into the graph view (including child port and attributes):
				overviewCache.insert(dimCell);
				
				AttributeLink link = model.getProject().getContent().getLinkByAttribute(dim);
				AttributeMap attrMap = model.getProject().getContent().getAttributeMapByAttribute(link);
				
				AssignmentEdge assEdge = new AssignmentEdge(attrMap);			
				assEdge.setTarget(meaCell.getChildAt(0));
				assEdge.setSource(dimCell.getChildAt(0));	
				// Insert the edge into the graph view if unique:
				overviewGraph.getGraphLayoutCache().insert(assEdge);
				
				rowCounter++;
			}
			
			/* Indicators */
			columnCounter = (swaped ? 2 : 3);
			
			Iterator<OperaIndicator>	itInd = inds.iterator();
			ArrayList<IndicatorCell> indCells = new ArrayList<IndicatorCell>();

			rowCounter = 0;
			while (itInd.hasNext()) {
				OperaIndicator ind = itInd.next();
				
				DefaultGraphCell indCell = generateIndVertex(ind.getLabel(),
						ind,
						null,
						xBase + (columnCounter * columnWidth), yBase + (rowCounter * 65), indicatorWidth, indicatorHeight, 
						Color.ORANGE, 
						false);
				indCells.add((IndicatorCell)indCell);
				// Insert the vertex into the graph view (including child port and attributes):
				overviewCache.insert(indCell);
				
				AttributeLink link = model.getProject().getContent().getLinkByAttribute(ind);
				AttributeMap attrMap = model.getProject().getContent().getAttributeMapByAttribute(link);
				
				Attributes targetAttr = attrMap.getTargetAttribute().getAttribute();
				if (targetAttr instanceof Attributes) {
					DimensionCell dimCell = null;
					
					Iterator<DimensionCell> itDimCell = dimCells.iterator();
					while (itDimCell.hasNext()) {
						dimCell = itDimCell.next();
						
						if (dimCell.getDimension().equals(targetAttr))
							break;
					}
					
					AssignmentEdge assEdge = new AssignmentEdge(attrMap);			
					assEdge.setTarget(dimCell.getChildAt(0));
					assEdge.setSource(indCell.getChildAt(0));	
					// Insert the edge into the graph view if unique:
					overviewGraph.getGraphLayoutCache().insert(assEdge);
				}
				
				rowCounter++;
			}
			
			/* Variables */
			Iterator<Variable>	itVar = vars.iterator();
			ArrayList<DefaultGraphCell> varCells = new ArrayList<DefaultGraphCell>();
			
			rowCounter = 0;
			while (itVar.hasNext()) {
				columnCounter = (swaped ? 1 : 4);
				
				Variable var = itVar.next();
				
				DefaultGraphCell varCell = generateVarVertex(var.getLabel(),
						var,
						null,
						xBase + (columnCounter * columnWidth), yBase + (rowCounter * 65), variableWidth, variableHeight, 
						Color.ORANGE, 
						false);
				varCells.add(varCell);
				// Insert the vertex into the graph view (including child port and attributes):
				overviewCache.insert(varCell);
				
				/* Instance of Variable */
				columnCounter = (swaped ? 0 : 5);
				
				attrLink = model.getProject().getContent().getLinkByAttribute(var);
				if ((attrLink != null) &&
					(attrLink.getInstance() != null)) {
						String label = attrLink.getInstance().getLabel();
	
						DefaultGraphCell labelCell = generateLabelVertex(label,
								label,
								null,
								xBase + (columnCounter * columnWidth), /*xPos + 100,*/ yBase + (rowCounter * 65), instanceWidth, instanceHeight, 
								Color.ORANGE, 
								false);		
						// Insert the vertex into the graph view (including child port and attributes):
						overviewCache.insert(labelCell);
				}
				
				
				AttributeLink link = model.getProject().getContent().getLinkByAttribute(var);
				AttributeMap attrMap = model.getProject().getContent().getAttributeMapByAttribute(link);
				
				if ((attrMap instanceof AttributeMap) && 
						(attrMap.getTargetAttribute() instanceof AttributeLink)) {
					Attributes targetAttr = attrMap.getTargetAttribute().getAttribute();
					if (targetAttr instanceof Attributes) {
						IndicatorCell indCell = null;
						
						Iterator<IndicatorCell> itIndCell = indCells.iterator();
						while (itIndCell.hasNext()) {
							indCell = itIndCell.next();
							
							if (indCell.getIndicator().equals(targetAttr))
								break;
						}
						
						AssignmentEdge assEdge = new AssignmentEdge(attrMap);			
						assEdge.setTarget(indCell.getChildAt(0));
						assEdge.setSource(varCell.getChildAt(0));	
						// Insert the edge into the graph view if unique:
						overviewGraph.getGraphLayoutCache().insert(assEdge);
					}
				}
				
				rowCounter++;
			}
			
//			_setGraphUpdate(GraphUpdate.LAYER_REFRESH);
//			_fireGraphUpdateEvent();
		}
		
		return overviewGraph;
	}
	
	/**
	 * @param model
	 * @return
	 */
	public JGraph createMappingGraph(CStatsModel model, boolean swaped) {
		int columnWidth		= 195;
		int instanceWidth	= 180;
		int instanceHeight	= 48;
		int measureWidth	= 80;
		int measureHeight	= 80;
		int categoryWidth	= 60;
		int categoryHeight	= 60;
		int variableWidth	= 80;
		int variableHeight	= 48;
		int valueWidth		= 60;
		int valueHeight		= 60;
		
		GraphModel			mappingModel	= new DefaultGraphModel();
		GraphLayoutCache	mappingCache 	= new GraphLayoutCache(mappingModel, new GCellViewFactory(), true); // expanding and collapsing
		JGraph				mappingGraph	= new ConceptualJGraph(mappingModel, mappingCache); // expanding and collapsing
			
		mappingGraph.setSizeable(false);
		mappingGraph.setEditable(false);
		
		HashMap<Category, DefaultGraphCell>		usedCategories		= new HashMap<Category, DefaultGraphCell>();
		HashMap<Measurement, DefaultGraphCell>	usedMeasurements	= new HashMap<Measurement, DefaultGraphCell>();
		HashMap<Variable, DefaultGraphCell>		usedVariables		= new HashMap<Variable, DefaultGraphCell>();
		
		int xBase = 25; int columnCounter; 
		int yBase = 25; int rowCounter = 0;
		int xMod;
		
		Iterator<InstanceMap> itInstanceMap = model.getProject().getContent().getInstanceMaps().iterator();
		while (itInstanceMap.hasNext()) {
			InstanceMap instMap = itInstanceMap.next();
			
			Iterator<AttributeMap> itAttributeMap = model.getProject().getContent().getMapsByType(AttributeMapType.ASSIGNED_VARIABLE).iterator();
			while (itAttributeMap.hasNext()) {
				AttributeMap attrMap = itAttributeMap.next();
				
				if ((attrMap.getBelongsTo() instanceof InstanceMap) &&
						(attrMap.getBelongsTo().equals(instMap))) {
					ArrayList<Characteristics[]> mappings = getThreeStepMapperListList(model.getProject().getContent(), instMap, attrMap);
					
					Iterator<Characteristics[]> mappingIter = mappings.iterator();
					usedCategories.clear();
					usedMeasurements.clear();
					while (mappingIter.hasNext()) {
						Characteristics[] map = mappingIter.next();
						
						if ((map[0] instanceof Value) 
								&& (map[5] instanceof Category)) {
							columnCounter = (swaped ? 4 : 1);
							xMod = (swaped ? -190 : 0);
							int yPos = yBase + (rowCounter * 65);
							
							DefaultGraphCell	meaCell	= new MeasurementCell();							
							CharacteristicLink	catCharLink		= model.getProject().getContent().getCharacteristicLinkByCharacteristic((Category)map[5]);
							Measurement measurement = (Measurement)catCharLink.getAttribute();
							
							if (usedMeasurements.containsKey(measurement))
								meaCell = usedMeasurements.get(measurement);
							else {								 
								meaCell = generateMeaVertex(measurement.getName(),
										measurement,
										null,
										xBase + (columnCounter * columnWidth) + xMod, yPos, measureWidth, measureHeight, 
										Color.ORANGE, 
										false);
								usedMeasurements.put(measurement, meaCell);
								// Insert the vertex into the graph view (including child port and attributes):
								mappingCache.insert(meaCell);
								
								AttributeLink attrLink = model.getProject().getContent().getLinkByAttribute(measurement);
								if ((attrLink != null) &&
									(attrLink.getInstance() != null)) {
										String label = attrLink.getInstance().getLabel();
										columnCounter = (swaped ? 5 : 0);
										xMod = (swaped ? -95-190 : 0);

										DefaultGraphCell labelCell = generateLabelVertex(label,
												label,
												null,
												xBase + (columnCounter * columnWidth) + xMod, yPos, instanceWidth, instanceHeight, 
												Color.ORANGE, 
												false);		
										// Insert the vertex into the graph view (including child port and attributes):
										mappingCache.insert(labelCell);
								}
							}							
							
							DefaultGraphCell catCell = null;
							
							if (usedCategories.containsKey((Category)map[5]))
								catCell = usedCategories.get((Category)map[5]);
							else {
								columnCounter = (swaped ? 3 : 2);
								xMod = (swaped ? +95-190 : -95); 
								catCell = generateCatVertex(((Category)map[5]).getLabel(),
										(Category)map[5],
										xBase + (columnCounter * columnWidth) + xMod, yPos, categoryWidth, categoryHeight, 
										Color.ORANGE, 
										false);
								usedCategories.put((Category)map[5], catCell);
								// Insert the vertex into the graph view (including child port and attributes):
								mappingCache.insert(catCell);
							}
							
							CategoryEdge catEdge = new CategoryEdge(catCharLink);			
							catEdge.setSource(meaCell.getChildAt(0));
							catEdge.setTarget(catCell.getChildAt(0));	
							// Insert the edge into the graph view if unique:
							mappingGraph.getGraphLayoutCache().insert(catEdge);
							
							DefaultGraphCell	varCell	= new VariableCell();							
							CharacteristicLink	valCharLink		= model.getProject().getContent().getCharacteristicLinkByCharacteristic((Value)map[0]);
							Variable variable = (Variable)valCharLink.getAttribute();
							
							if (usedVariables.containsKey(variable))
								varCell = usedVariables.get(variable);
							else {
								columnCounter = (swaped ? 1 : 4);
								xMod = (swaped ? 0: -190);								 
								varCell = generateVarVertex(variable.getLabel(),
										variable,
										null,
										xBase + (columnCounter * columnWidth) +xMod, yPos, variableWidth, variableHeight, 
										Color.ORANGE, 
										false);
								usedVariables.put(variable, varCell);
								// Insert the vertex into the graph view (including child port and attributes):
								mappingCache.insert(varCell);
								
								AttributeLink attrLink = model.getProject().getContent().getLinkByAttribute(variable);
								if ((attrLink != null) &&
									(attrLink.getInstance() != null)) {
										String label = attrLink.getInstance().getLabel();
										columnCounter = (swaped ? 0 : 5);
										xMod = (swaped ? 0: -95-190);
										DefaultGraphCell labelCell = generateLabelVertex(label,
												label,
												null,
												xBase + (columnCounter * columnWidth) + xMod, yPos, instanceWidth, instanceHeight, 
												Color.ORANGE, 
												false);		
										// Insert the vertex into the graph view (including child port and attributes):
										mappingCache.insert(labelCell);
								}
							}
														
							columnCounter = (swaped ? 2 : 3);
							xMod = (swaped ? -95: +95-190);
							DefaultGraphCell valCell = generateValVertex(((Value)map[0]).getLabel(),
									(Value)map[0],
									xBase + (columnCounter * columnWidth) + xMod, yPos, valueWidth, valueHeight, 
									Color.ORANGE, 
									false);
							// Insert the vertex into the graph view (including child port and attributes):
							mappingCache.insert(valCell);
							
							CharacteristicEdge charEdge = new CharacteristicEdge((Category)map[5]);			
							charEdge.setSource(valCell.getChildAt(0));
							charEdge.setTarget(catCell.getChildAt(0));	
							// Insert the edge into the graph view if unique:
							mappingGraph.getGraphLayoutCache().insert(charEdge);
							
							ValueEdge valEdge = new ValueEdge(valCharLink);			
							valEdge.setSource(varCell.getChildAt(0));
							valEdge.setTarget(valCell.getChildAt(0));	
							// Insert the edge into the graph view if unique:
							mappingGraph.getGraphLayoutCache().insert(valEdge);
							
							rowCounter++;
						}
					}
					rowCounter++;
				}
			}
		}

		return mappingGraph;
	}
	
	/**
	 * @param content
	 * @param selectedMapping
	 * @param straitenedMapping
	 * @return
	 */
	private ArrayList<Characteristics[]> getThreeStepMapperListList(ProContent content, InstanceMap selectedMapping, AttributeMap straitenedMapping) {
		ArrayList<Characteristics[]> threeStepMapper = new ArrayList<Characteristics[]>();
		
		if (selectedMapping.getType().equals(InstanceMapType.DATA_RECODING)) {
			if ((straitenedMapping.getBelongsTo() instanceof InstanceMap) &&
					(straitenedMapping.getBelongsTo().getEntityID() == selectedMapping.getEntityID())) {
				Iterator<CharacteristicMap> charMapIterator = content.getCharacteristicMaps().iterator();								
				if (charMapIterator.hasNext()) {
					while (charMapIterator.hasNext()) {
						CharacteristicMap charMap = charMapIterator.next();
						
						if ((charMap.getBelongsTo() instanceof AttributeMap) &&
								(charMap.getBelongsTo().getEntityID() == straitenedMapping.getEntityID())) {
														
							CharacteristicMap osCharMap = content.getCharacteristicMapByCharacteristic(charMap.getTargetCharacteristic());
							
							if (!(osCharMap instanceof CharacteristicMap))
								osCharMap = new CharacteristicMap();
							
							CharacteristicMap csCharMap = 
									(osCharMap.getTargetCharacteristic() instanceof CharacteristicLink ? content.getCharacteristicMapByCharacteristic(osCharMap.getTargetCharacteristic()): new CharacteristicMap());
							
							Characteristics[] maps = new Characteristics[6];
							maps[0] = charMap.getSourceCharacteristic().getCharacteristic();
							maps[1] = charMap.getTargetCharacteristic() instanceof CharacteristicLink ? charMap.getTargetCharacteristic().getCharacteristic() : null;
							maps[2] = osCharMap.getSourceCharacteristic() instanceof CharacteristicLink ? osCharMap.getSourceCharacteristic().getCharacteristic(): null;
							maps[3] = osCharMap.getTargetCharacteristic() instanceof CharacteristicLink ? osCharMap.getTargetCharacteristic().getCharacteristic(): null;
							maps[4] = csCharMap.getSourceCharacteristic() instanceof CharacteristicLink ? csCharMap.getSourceCharacteristic().getCharacteristic(): null;
							maps[5] = csCharMap.getTargetCharacteristic() instanceof CharacteristicLink ? csCharMap.getTargetCharacteristic().getCharacteristic(): null;
							
							threeStepMapper.add(maps);
						}										 
					}
				}								
			}
		}
		
		return threeStepMapper;
	}
	
	/**
	 * @param model
	 * @return
	 */
	public JGraph createMeasurementGraph(CStatsModel model) {
		GraphModel			measureModel	= new DefaultGraphModel();
		GraphLayoutCache	measureCache = new GraphLayoutCache(measureModel, new GCellViewFactory(), true); // expanding and collapsing
		JGraph				measureGraph	= new ConceptualJGraph(measureModel, measureCache); // expanding and collapsing
				
		measureGraph.setSizeable(false);
		measureGraph.setEditable(false);
		
		if (!NO_PRJ_NAME.equals(model.getProject().getName())) {		
			int xBase = 25; int xCounter = 0; 
			int yBase = 25; int yCounter = 0;
			Measurement mea = model.getProject().getContent().getMeasurement();
		
			int xPos = xBase + (xCounter * 195);
			int yPos = yBase + 60; 
			DefaultGraphCell meaCell = generateMeaVertex(mea.getName(),
					mea,
					null,
					xPos, yPos, 80, 80, 
					Color.ORANGE, 
					false);		
			// Insert the vertex into the graph view (including child port and attributes):
			measureCache.insert(meaCell);
			
			Iterator<CharacteristicLink> itCharLink = model.getProject().getContent().getCharacteristicLinksByAttribute(mea).iterator();
			while (itCharLink.hasNext()) {
				CharacteristicLink	link = itCharLink.next();
				
				Category	cat = (Category)link.getCharacteristic();
				
				xPos = xBase + 100 + (xCounter * 195);
				yPos = yBase + 60 + (yCounter * 65);
				DefaultGraphCell catCell = generateCatVertex(cat.getLabel(),
						cat,
						xPos, yPos, 60, 60, 
						Color.ORANGE, 
						false);
				// Insert the vertex into the graph view (including child port and attributes):
				measureCache.insert(catCell);
				
				CategoryEdge catEdge = new CategoryEdge(link);			
				catEdge.setSource(meaCell.getChildAt(0));
				catEdge.setTarget(catCell.getChildAt(0));	
				// Insert the edge into the graph view if unique:
				measureGraph.getGraphLayoutCache().insert(catEdge);
				
				yCounter++;
			}
			
			AttributeLink attrLink = model.getProject().getContent().getLinkByAttribute(mea);
			if ((attrLink != null) &&
				(attrLink.getInstance() != null)) {
					String label = attrLink.getInstance().getLabel();

					DefaultGraphCell labelCell = generateLabelVertex(label,
							label,
							null,
							xBase + (xCounter * 195), yBase, 180, 48, 
							Color.ORANGE, 
							false);		
					// Insert the vertex into the graph view (including child port and attributes):
					measureCache.insert(labelCell);
			}
			
//		_setGraphUpdate(GraphUpdate.LAYER_REFRESH);
//		_fireGraphUpdateEvent();
		}
		
		return measureGraph;
	}
	
	/**
	 * @param name
	 * @param measure
	 * @param project
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param bg
	 * @param raised
	 * @return
	 */
	public /*static*/ DefaultGraphCell generateMeaVertex(String name, Measurement measure, Project project,
			double x, double y, double w, double h, Color bg, boolean raised) {
		
		DefaultGraphCell cell = new MeasurementCell(measure);
		
		// Set bounds
		GraphConstants.setBounds(cell.getAttributes(),
				new Rectangle2D.Double(x, y, w, h));

		// Set fill color
		if (bg != null) {
			GraphConstants.setGradientColor(cell.getAttributes(), bg);
			GraphConstants.setOpaque(cell.getAttributes(), true);
		}

		// Set raised border
		if (raised)
			GraphConstants.setBorder(cell.getAttributes(), 
					BorderFactory.createRaisedBevelBorder());
		else
			// Set black border
			GraphConstants.setBorderColor(cell.getAttributes(), Color.black);

		// Add a Port
		DefaultPort port = new DefaultPort();
		cell.add(port);
		port.setParent(cell);
						
		return cell;
	}
	
	/**
	 * @param name
	 * @param category
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param bg
	 * @param raised
	 * @return
	 */
	public DefaultGraphCell generateCatVertex(String name, Category category, 
			double x, double y, double w, double h, Color bg, boolean raised) {
				
		DefaultGraphCell cell = new CategoryCell(category);
		
		// Set bounds
		GraphConstants.setBounds(cell.getAttributes(),
			new Rectangle2D.Double(x, y, w, h));

		// Set fill color
		if (bg != null) {
			GraphConstants.setGradientColor(cell.getAttributes(), bg);
			GraphConstants.setOpaque(cell.getAttributes(), true);
		}

		// Set raised border
		if (raised)
			GraphConstants.setBorder(cell.getAttributes(), 
				BorderFactory.createRaisedBevelBorder());
		else
			// Set black border
			GraphConstants.setBorderColor(cell.getAttributes(), Color.black);
			// Add a Port
		DefaultPort port = new DefaultPort();
		cell.add(port);
		port.setParent(cell);
			
		return cell;
	}
	
	/**
	 * @param model
	 * @return
	 */
	public JGraph createConceptualGraph(CStatsModel model) {
		GraphModel			conceptualModel	= new DefaultGraphModel();
		GraphLayoutCache	conceptualCache = new GraphLayoutCache(conceptualModel, new GCellViewFactory(), true); // expanding and collapsing
		JGraph				conceptualGraph	= new ConceptualJGraph(conceptualModel, conceptualCache); // expanding and collapsing
				
		conceptualGraph.setSizeable(false);
		conceptualGraph.setEditable(false);
		
		Iterator<ConDimension> itDim = model.getProject().getContent().getDimensions().iterator();
		int xBase = 25; int xCounter = 0; 
		int yBase = 25; int yCounter = 0;
		while (itDim.hasNext()) {
			ConDimension dim = itDim.next();
		
			int xPos = xBase + (xCounter * 195);
			int yPos = yBase + 60; 
			DefaultGraphCell dimCell = generateDimVertex(dim.getLabel(),
					dim,
					null,
					xPos, yPos, 80, 48, 
					Color.ORANGE, 
					false);		
			// Insert the vertex into the graph view (including child port and attributes):
			conceptualCache.insert(dimCell);
			
			Iterator<CharacteristicLink> itCharLink = model.getProject().getContent().getCharacteristicLinksByAttribute(dim).iterator();
			while (itCharLink.hasNext()) {
				CharacteristicLink	link = itCharLink.next();
				
				ConSpecification	spec = (ConSpecification)link.getCharacteristic();
				
				xPos = xBase + 100 + (xCounter * 195);
				yPos = yBase + 60 + (yCounter * 65);
				DefaultGraphCell specCell = generateSpecVertex(spec.getLabel(),
						spec,
						xPos, yPos, 60, 60, 
						Color.ORANGE, 
						false);
				// Insert the vertex into the graph view (including child port and attributes):
				conceptualCache.insert(specCell);
				
				SpecificationEdge spEdge = new SpecificationEdge(link);			
				spEdge.setSource(dimCell.getChildAt(0));
				spEdge.setTarget(specCell.getChildAt(0));	
				// Insert the edge into the graph view if unique:
				conceptualGraph.getGraphLayoutCache().insert(spEdge);
				
				yCounter++;
			}
			
			AttributeLink attrLink = model.getProject().getContent().getLinkByAttribute(dim);
			if ((attrLink != null) &&
				(attrLink.getInstance() != null)) {
					String label = attrLink.getInstance().getLabel();

					DefaultGraphCell labelCell = generateLabelVertex(label,
							label,
							null,
							xBase + (xCounter * 195), yBase, 180, 48, 
							Color.ORANGE, 
							false);		
					// Insert the vertex into the graph view (including child port and attributes):
					conceptualCache.insert(labelCell);
			}
			
			xCounter++;
			yCounter = 0;
		}
//		_setGraphUpdate(GraphUpdate.LAYER_REFRESH);
//		_fireGraphUpdateEvent();
		
		return conceptualGraph;
	}
	
	/**
	 * @param name
	 * @param label
	 * @param project
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param bg
	 * @param raised
	 * @return
	 */
	public /*static*/ DefaultGraphCell generateLabelVertex(String name, String label, Project project,
			double x, double y, double w, double h, Color bg, boolean raised) {
		
		DefaultGraphCell cell = new LabelCell(label);
		
		// Set bounds
		GraphConstants.setBounds(cell.getAttributes(),
				new Rectangle2D.Double(x, y, w, h));

		// Set fill color
		if (bg != null) {
			GraphConstants.setGradientColor(cell.getAttributes(), bg);
			GraphConstants.setOpaque(cell.getAttributes(), true);
		}

		// Set raised border
		if (raised)
			GraphConstants.setBorder(cell.getAttributes(), 
					BorderFactory.createRaisedBevelBorder());
		else
			// Set black border
			GraphConstants.setBorderColor(cell.getAttributes(), Color.black);

		// Add a Port
		DefaultPort port = new DefaultPort();
		cell.add(port);
		port.setParent(cell);
						
		return cell;
	}
	
	/**
	 * @param name
	 * @param dimension
	 * @param project
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param bg
	 * @param raised
	 * @return
	 */
	public /*static*/ DefaultGraphCell generateDimVertex(String name, ConDimension dimension, Project project,
			double x, double y, double w, double h, Color bg, boolean raised) {
		
		DefaultGraphCell cell = new DimensionCell(dimension);
		
		// Set bounds
		GraphConstants.setBounds(cell.getAttributes(),
				new Rectangle2D.Double(x, y, w, h));

		// Set fill color
		if (bg != null) {
			GraphConstants.setGradientColor(cell.getAttributes(), bg);
			GraphConstants.setOpaque(cell.getAttributes(), true);
		}

		// Set raised border
		if (raised)
			GraphConstants.setBorder(cell.getAttributes(), 
					BorderFactory.createRaisedBevelBorder());
		else
			// Set black border
			GraphConstants.setBorderColor(cell.getAttributes(), Color.black);

		// Add a Port
		DefaultPort port = new DefaultPort();
		cell.add(port);
		port.setParent(cell);
						
		return cell;
	}
	
	/**
	 * @param name
	 * @param specification
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param bg
	 * @param raised
	 * @return
	 */
	public DefaultGraphCell generateSpecVertex(String name, ConSpecification specification, 
			double x, double y, double w, double h, Color bg, boolean raised) {
				
		DefaultGraphCell cell = new SpecificationCell(specification);
		
		// Set bounds
		GraphConstants.setBounds(cell.getAttributes(),
			new Rectangle2D.Double(x, y, w, h));

		// Set fill color
		if (bg != null) {
			GraphConstants.setGradientColor(cell.getAttributes(), bg);
			GraphConstants.setOpaque(cell.getAttributes(), true);
		}

		// Set raised border
		if (raised)
			GraphConstants.setBorder(cell.getAttributes(), 
				BorderFactory.createRaisedBevelBorder());
		else
			// Set black border
			GraphConstants.setBorderColor(cell.getAttributes(), Color.black);
			// Add a Port
		DefaultPort port = new DefaultPort();
		cell.add(port);
		port.setParent(cell);
			
		return cell;
	}
	
	/**
	 * @param model
	 * @return
	 */
	public JGraph createOperationalGraph(CStatsModel model) {
		GraphModel			operationalModel	= new DefaultGraphModel();
		GraphLayoutCache	operationalCache = new GraphLayoutCache(operationalModel, new GCellViewFactory(), true); // expanding and collapsing
		JGraph				operationalGraph	= new ConceptualJGraph(operationalModel, operationalCache); // expanding and collapsing
		
		operationalGraph.setSizeable(false);
		operationalGraph.setEditable(false);
		
		Iterator<OperaIndicator> itInd = model.getProject().getContent().getIndicators().iterator();
		int xBase = 25; int xCounter = 0; 
		int yBase = 25; int yCounter = 0;
		while (itInd.hasNext()) {
			OperaIndicator ind = itInd.next();
		
			int xPos = xBase + (xCounter * 195);
			int yPos = yBase + 60; 
			DefaultGraphCell indCell = generateIndVertex(ind.getLabel(),
					ind,
					null,
					xPos, yPos, 80, 48, 
					Color.ORANGE, 
					false);		
			// Insert the vertex into the graph view (including child port and attributes):
			operationalCache.insert(indCell);
			
			Iterator<CharacteristicLink> itCharLink = model.getProject().getContent().getCharacteristicLinksByAttribute(ind).iterator();
			while (itCharLink.hasNext()) {
				CharacteristicLink	link = itCharLink.next();
				
				OperaPrescription	pres = (OperaPrescription)link.getCharacteristic();
				
				xPos = xBase + 100 + (xCounter * 195);
				yPos = yBase + 60 + (yCounter * 65);
				DefaultGraphCell presCell = generatePresVertex(pres.getLabel(),
						pres,
						xPos, yPos, 60, 60, 
						Color.ORANGE, 
						false);
				// Insert the vertex into the graph view (including child port and attributes):
				operationalCache.insert(presCell);
				
				PrescriptionEdge prEdge = new PrescriptionEdge(link);			
				prEdge.setSource(indCell.getChildAt(0));
				prEdge.setTarget(presCell.getChildAt(0));	
				// Insert the edge into the graph view if unique:
				operationalGraph.getGraphLayoutCache().insert(prEdge);
				
				yCounter++;
			}
			
			AttributeLink attrLink = model.getProject().getContent().getLinkByAttribute(ind);
			if ((attrLink != null) &&
				(attrLink.getInstance() != null)) {
					String label = attrLink.getInstance().getLabel();

					DefaultGraphCell labelCell = generateLabelVertex(label,
							label,
							null,
							xBase + (xCounter * 195), yBase, 180, 48, 
							Color.ORANGE, 
							false);		
					// Insert the vertex into the graph view (including child port and attributes):
					operationalCache.insert(labelCell);
			}
			
			xCounter++;
			yCounter = 0;
		}
//		_setGraphUpdate(GraphUpdate.LAYER_REFRESH);
//		_fireGraphUpdateEvent();
		
		return operationalGraph;
	}
	
	/**
	 * @param name
	 * @param indicator
	 * @param project
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param bg
	 * @param raised
	 * @return
	 */
	public /*static*/ DefaultGraphCell generateIndVertex(String name, OperaIndicator indicator, Project project,
			double x, double y, double w, double h, Color bg, boolean raised) {
		
		DefaultGraphCell cell = new IndicatorCell(indicator);
		
		// Set bounds
		GraphConstants.setBounds(cell.getAttributes(),
				new Rectangle2D.Double(x, y, w, h));

		// Set fill color
		if (bg != null) {
			GraphConstants.setGradientColor(cell.getAttributes(), bg);
			GraphConstants.setOpaque(cell.getAttributes(), true);
		}

		// Set raised border
		if (raised)
			GraphConstants.setBorder(cell.getAttributes(), 
					BorderFactory.createRaisedBevelBorder());
		else
			// Set black border
			GraphConstants.setBorderColor(cell.getAttributes(), Color.black);

		// Add a Port
		DefaultPort port = new DefaultPort();
		cell.add(port);
		port.setParent(cell);
						
		return cell;
	}
	
	/**
	 * @param name
	 * @param prescription
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param bg
	 * @param raised
	 * @return
	 */
	public DefaultGraphCell generatePresVertex(String name, OperaPrescription prescription, 
			double x, double y, double w, double h, Color bg, boolean raised) {
				
		DefaultGraphCell cell = new PrescriptionCell(prescription);
		
		// Set bounds
		GraphConstants.setBounds(cell.getAttributes(),
			new Rectangle2D.Double(x, y, w, h));

		// Set fill color
		if (bg != null) {
			GraphConstants.setGradientColor(cell.getAttributes(), bg);
			GraphConstants.setOpaque(cell.getAttributes(), true);
		}

		// Set raised border
		if (raised)
			GraphConstants.setBorder(cell.getAttributes(), 
				BorderFactory.createRaisedBevelBorder());
		else
			// Set black border
			GraphConstants.setBorderColor(cell.getAttributes(), Color.black);
			// Add a Port
		DefaultPort port = new DefaultPort();
		cell.add(port);
		port.setParent(cell);
			
		return cell;
	}

	/**
	 * @param model
	 * @return
	 */
	public JGraph createDataRecodingGraph(CStatsModel model) {
		GraphModel			dataCodingModel	= new DefaultGraphModel();
		GraphLayoutCache	dataCodingCache = new GraphLayoutCache(dataCodingModel, new GCellViewFactory(), true); // expanding and collapsing
		JGraph				dataCodingGraph	= new ConceptualJGraph(dataCodingModel, dataCodingCache); // expanding and collapsing
				
		dataCodingGraph.setSizeable(false);
		dataCodingGraph.setEditable(false);
		
		Iterator<Variable> itVar = model.getProject().getContent().getVariables().iterator();
		int xBase = 25; int xCounter = 0; 
		int yBase = 25; int yCounter = 0;
		while (itVar.hasNext()) {
			Variable var = itVar.next();
		
			int xPos = xBase + (xCounter * 195);
			int yPos = yBase + 60; 
			DefaultGraphCell varCell = generateVarVertex(var.getLabel(),
					var,
					null,
					xPos, yPos, 80, 48, 
					Color.ORANGE, 
					false);		
			// Insert the vertex into the graph view (including child port and attributes):
			dataCodingCache.insert(varCell);
			
			Iterator<CharacteristicLink> itCharLink = model.getProject().getContent().getCharacteristicLinksByAttribute(var).iterator();
			while (itCharLink.hasNext()) {
				CharacteristicLink	link = itCharLink.next();
				
				Value	val = (Value)link.getCharacteristic();
				
				xPos = xBase + 100 + (xCounter * 195);
				yPos = yBase + 60 + (yCounter * 65);
				DefaultGraphCell valCell = generateValVertex(val.getLabel(),
						val,
						xPos, yPos, 60, 60, 
						Color.ORANGE, 
						false);
				// Insert the vertex into the graph view (including child port and attributes):
				dataCodingCache.insert(valCell);
				
				ValueEdge valEdge = new ValueEdge(link);			
				valEdge.setSource(varCell.getChildAt(0));
				valEdge.setTarget(valCell.getChildAt(0));	
				// Insert the edge into the graph view if unique:
				dataCodingGraph.getGraphLayoutCache().insert(valEdge);
				
				yCounter++;
			}
			
			AttributeLink attrLink = model.getProject().getContent().getLinkByAttribute(var);
			if ((attrLink != null) &&
				(attrLink.getInstance() != null)) {
					String label = attrLink.getInstance().getLabel();

					DefaultGraphCell labelCell = generateLabelVertex(label,
							label,
							null,
							xBase + (xCounter * 195), yBase, 180, 48, 
							Color.ORANGE, 
							false);		
					// Insert the vertex into the graph view (including child port and attributes):
					dataCodingCache.insert(labelCell);
			}
			
			xCounter++;
			yCounter = 0;
		}
//		_setGraphUpdate(GraphUpdate.LAYER_REFRESH);
//		_fireGraphUpdateEvent();
		
		return dataCodingGraph;
	}
	
	/**
	 * @param name
	 * @param variable
	 * @param project
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param bg
	 * @param raised
	 * @return
	 */
	public /*static*/ DefaultGraphCell generateVarVertex(String name, Variable variable, Project project,
			double x, double y, double w, double h, Color bg, boolean raised) {
		
		DefaultGraphCell cell = new VariableCell(variable);
		
		// Set bounds
		GraphConstants.setBounds(cell.getAttributes(),
				new Rectangle2D.Double(x, y, w, h));

		// Set fill color
		if (bg != null) {
			GraphConstants.setGradientColor(cell.getAttributes(), bg);
			GraphConstants.setOpaque(cell.getAttributes(), true);
		}

		// Set raised border
		if (raised)
			GraphConstants.setBorder(cell.getAttributes(), 
					BorderFactory.createRaisedBevelBorder());
		else
			// Set black border
			GraphConstants.setBorderColor(cell.getAttributes(), Color.black);

		// Add a Port
		DefaultPort port = new DefaultPort();
		cell.add(port);
		port.setParent(cell);
						
		return cell;
	}
	
	/**
	 * @param name
	 * @param value
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param bg
	 * @param raised
	 * @return
	 */
	public DefaultGraphCell generateValVertex(String name, Value value, 
			double x, double y, double w, double h, Color bg, boolean raised) {
				
		DefaultGraphCell cell = new ValueCell(value);
		
		// Set bounds
		GraphConstants.setBounds(cell.getAttributes(),
			new Rectangle2D.Double(x, y, w, h));

		// Set fill color
		if (bg != null) {
			GraphConstants.setGradientColor(cell.getAttributes(), bg);
			GraphConstants.setOpaque(cell.getAttributes(), true);
		}

		// Set raised border
		if (raised)
			GraphConstants.setBorder(cell.getAttributes(), 
				BorderFactory.createRaisedBevelBorder());
		else
			// Set black border
			GraphConstants.setBorderColor(cell.getAttributes(), Color.black);
			// Add a Port
		DefaultPort port = new DefaultPort();
		cell.add(port);
		port.setParent(cell);
			
		return cell;
	}
	
	/**
	 * @param pane
	 */
	private void printLayer(GJGraphScrollPane pane) {
		PrinterJob printJob = PrinterJob.getPrinterJob();
		
		if ((printJob != null) &&
				(pane != null)) {
			printJob.setPrintable(pane);
			
			if (printJob.printDialog()) {
				try {
					printJob.print();
				} catch (PrinterException e) {
					JOptionPane.showMessageDialog(
							null, e.getMessage(), "PrinterException:",  JOptionPane.ERROR_MESSAGE);
						  	
					/* DEMO ONLY */
					System.err.println("PrinterException: " + e.getMessage());
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * @param jGraph
	 * @param fileName
	 */
	private void exportImage(JGraph jGraph, String fileName, Project project) {
		JPGFilter jpgFilter = new JPGFilter();
		GIFFilter gifFilter = new GIFFilter();
		PNGFilter pngFilter = new PNGFilter();
		BMPFilter bmpFilter = new BMPFilter();
  
	    //File file = null;
		JFileChooser.setDefaultLocale(currentLocale);
	    JFileChooser fileChooser = new JFileChooser();

	    fileChooser.addChoosableFileFilter(jpgFilter);
	    fileChooser.addChoosableFileFilter(gifFilter);
	    fileChooser.addChoosableFileFilter(pngFilter);
	    fileChooser.addChoosableFileFilter(bmpFilter);
	    
	    // Start in current directory
//	    fileChooser.setCurrentDirectory (new File ("."));
	    
	    ResourceBundle	resourceBundle	= ResourceBundle.getBundle(BUNDLE_F, currentLocale);
		String			file_name		= resourceBundle.getString(FILE_NAME);
		
		resourceBundle	= ResourceBundle.getBundle(BUNDLE, currentLocale); 
	    
		String path_0 = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		path_0 = path_0.replace("/", "\\\\"); // use File.separator in CharmStatsPro
		path_0 = path_0.replace(file_name, ""); // filename stored in resource FrameBundle
		path_0 = path_0 + "reports\\\\projects\\\\";
		
		
		String path_1 = path_0 + project.getName() +"\\\\graphs\\\\";
		
		File dir = new File (path_1);
		if (!dir.exists()) {
			try{
			    if(dir.mkdirs()) {
			    	dir = new File (path_1);
			    } else {
			        dir = new File (path_0);
			    }
			} catch(Exception e){
			    e.printStackTrace();
			} 
		}

		
	    fileChooser.setCurrentDirectory (dir); //(new File (path_0));

	    // Set filter for Java source files.
	    fileChooser.setFileFilter (jpgFilter);
	    File file = new File (fileName);

	    // Set to a default name for save.
	    fileChooser.setSelectedFile (file);

	    // Open chooser dialog
	    
	    int result = fileChooser.showSaveDialog(this);
  
	    if (result == JFileChooser.CANCEL_OPTION) {
			// DoNothing
	    } else if (result == JFileChooser.APPROVE_OPTION) {
	    	FileFilter	ff		= fileChooser.getFileFilter();
	    	file = fileChooser.getSelectedFile ();
	    	
	    	int response = -1;
	        if (file.exists ()) {
	        	response = JOptionPane.showConfirmDialog (null,
	        			resourceBundle.getString(OVERWRITE_FILE_QUE),resourceBundle.getString(CONFIRM_OVERWRITE),
	        			JOptionPane.OK_CANCEL_OPTION,
	        			JOptionPane.QUESTION_MESSAGE);
	        }
	        if (response != JOptionPane.CANCEL_OPTION) {
				FileOutputStream out;
				try {
					out = new FileOutputStream(file);
					Color bg = jGraph.getBackground();
					BufferedImage img = jGraph.getImage(bg, 0);
					
					switch (((SaveImageFileFilter)ff).getFilterID()) {
						case SaveImageFileFilter.jpgFilterID:
							try {
								ImageIO.write(img, "jpg", out);
								out.flush ();
								out.close ();
							} catch (IOException ioe) {
								JOptionPane.showMessageDialog(
										null, ioe.getMessage(), "IOException:",  JOptionPane.ERROR_MESSAGE);
									  	
								/* DEMO ONLY */
								System.err.println("IOException: " + ioe.getMessage());
								ioe.printStackTrace();
							}
							break;
						case SaveImageFileFilter.gifFilterID:
							try {
								ImageIO.write(img, "gif", out);
								out.flush ();
								out.close ();
							} catch (IOException ioe) {
								JOptionPane.showMessageDialog(
										null, ioe.getMessage(), "IOException:",  JOptionPane.ERROR_MESSAGE);
									  	
								/* DEMO ONLY */
								System.err.println("IOException: " + ioe.getMessage());
								ioe.printStackTrace();
							}
							break;
						case SaveImageFileFilter.pngFilterID:
							try {
								ImageIO.write(img, "png", out);
								out.flush ();
								out.close ();
							} catch (IOException ioe) {
								JOptionPane.showMessageDialog(
										null, ioe.getMessage(), "IOException:",  JOptionPane.ERROR_MESSAGE);
									  	
								/* DEMO ONLY */
								System.err.println("IOException: " + ioe.getMessage());
								ioe.printStackTrace();
							}
							break;
						case SaveImageFileFilter.bmpFilterID:
							try {
								ImageIO.write(img, "bmp", out);
								out.flush ();
								out.close ();
							} catch (IOException ioe) {
								JOptionPane.showMessageDialog(
										null, ioe.getMessage(), "IOException:",  JOptionPane.ERROR_MESSAGE);
									  	
								/* DEMO ONLY */
								System.err.println("IOException: " + ioe.getMessage());
								ioe.printStackTrace();
							}
							break;
						default:
							break;
					}

				} catch (FileNotFoundException fnfe) {
					JOptionPane.showMessageDialog(
							null, fnfe.getMessage(), "FileNotFoundException:",  JOptionPane.ERROR_MESSAGE);
						  	
					/* DEMO ONLY */
					System.err.println("FileNotFoundException: " + fnfe.getMessage());
					fnfe.printStackTrace();
				}
	        }
	    } else {
	    	// DoNothing
	    }
	}
		
	/**
	 * @param model
	 * @return
	 */
	public JGraph createMapMeasurementGraph(CStatsModel model, boolean swaped) {
		int columnWidth		= 195;
		int instanceWidth	= 180;
		int instanceHeight	= 48;
		int measureWidth	= 80;
		int measureHeight	= 80;
		int categoryWidth	= 60;
		int categoryHeight	= 60;
		int dimensionWidth	= 80;
		int dimensionHeight	= 48;
		int specificationWidth = 60;
		int specificationHeight = 60;
		
		GraphModel			measureModel	= new DefaultGraphModel();
		GraphLayoutCache	measureCache 	= new GraphLayoutCache(measureModel, new GCellViewFactory(), true); // expanding and collapsing
		JGraph				measureGraph	= new ConceptualJGraph(measureModel, measureCache); // expanding and collapsing
			
		measureGraph.setSizeable(false);
		measureGraph.setEditable(false);
		
		int xBase = 25; int columnCounter; int xMod;
		int yBase = 25; int rowCounter = 0;
		Measurement mea = model.getProject().getContent().getMeasurement();
		
		int maxYCounterTarget = 0;
		int maxYCounterSource = 0;
			
		Iterator<ConDimension> itDim = model.getProject().getContent().getDimensions().iterator(); 
		rowCounter = 0;
		while (itDim.hasNext()) {
			ConDimension dim = itDim.next();
									
			AttributeLink attrLink = model.getProject().getContent().getLinkByAttribute(mea);
			if ((attrLink != null) &&
				(attrLink.getInstance() != null)) {
					String label = attrLink.getInstance().getLabel();
					columnCounter = (swaped ? 5 : 0);
					xMod = (swaped ? -290 : 0);

					DefaultGraphCell labelCell = generateLabelVertex(label,
							label,
							null,
							xBase + (columnCounter * columnWidth) + xMod, yBase + (maxYCounterTarget * 65), instanceWidth, instanceHeight, 
							Color.ORANGE, 
							false);		
					// Insert the vertex into the graph view (including child port and attributes):
					measureCache.insert(labelCell);
			}
			
			
			/* Handle Measurement */
			columnCounter = (swaped ? 4 : 1);
			xMod = (swaped ? -190 : 0);
			DefaultGraphCell meaCell = generateMeaVertex(mea.getName(),
					mea,
					null,
					xBase + (columnCounter * columnWidth) + xMod, yBase + (maxYCounterTarget * 65), measureWidth, measureHeight, 
					Color.ORANGE, 
					false);		
			// Insert the vertex into the graph view (including child port and attributes):
			measureCache.insert(meaCell);
			
			Iterator<CharacteristicLink> itCharLink = model.getProject().getContent().getCharacteristicLinksByAttribute(mea).iterator();
			ArrayList<CategoryCell> catCells = new ArrayList<CategoryCell>();
			columnCounter = 0;
			while (itCharLink.hasNext()) {
				CharacteristicLink	link = itCharLink.next();
				
				Category	cat = (Category)link.getCharacteristic();
				
				columnCounter = (swaped ? 3 : 2);
				xMod = (swaped ? +90 : +100);
				DefaultGraphCell catCell = generateCatVertex(cat.getLabel(),
						cat,
						xBase + (columnCounter * columnWidth) - xMod, yBase + (maxYCounterTarget * 65) + (rowCounter * 65), categoryWidth, categoryHeight, 
						Color.ORANGE, 
						false);
				// Insert the vertex into the graph view (including child port and attributes):
				catCells.add((CategoryCell)catCell);
				measureCache.insert(catCell);
				
				CategoryEdge catEdge = new CategoryEdge(link);			
				catEdge.setSource(meaCell.getChildAt(0));
				catEdge.setTarget(catCell.getChildAt(0));	
				// Insert the edge into the graph view if unique:
				measureGraph.getGraphLayoutCache().insert(catEdge);
				
				rowCounter++;
			}
			if (rowCounter >= maxYCounterTarget)
				maxYCounterTarget = rowCounter+1;
			/* Handle */
		
			columnCounter = (swaped ? 1 : 4);
			xMod = (swaped ? 0 : -190);
			rowCounter = 0;
			DefaultGraphCell dimCell = generateDimVertex(dim.getLabel(),
					dim,
					null,
					xBase + (columnCounter * columnWidth) + xMod, yBase + (maxYCounterSource * 65), dimensionWidth, dimensionHeight, 
					Color.ORANGE, 
					false);		
			// Insert the vertex into the graph view (including child port and attributes):
			measureCache.insert(dimCell);
			
			columnCounter = 2;
			itCharLink = model.getProject().getContent().getCharacteristicLinksByAttribute(dim).iterator();
			while (itCharLink.hasNext()) {
				CharacteristicLink	link = itCharLink.next();
				
				ConSpecification	spec = (ConSpecification)link.getCharacteristic();
				
				columnCounter = (swaped ? 2 : 3);
				xMod = (swaped ? +100 : +90);
				DefaultGraphCell specCell = generateSpecVertex(spec.getLabel(),
						spec,
						xBase + (columnCounter * columnWidth) - xMod, yBase + (maxYCounterSource * 65) + (rowCounter * 65), specificationWidth, specificationHeight, 
						Color.ORANGE, 
						false);
				// Insert the vertex into the graph view (including child port and attributes):
				measureCache.insert(specCell);
				
				SpecificationEdge spEdge = new SpecificationEdge(link);			
				spEdge.setSource(dimCell.getChildAt(0));
				spEdge.setTarget(specCell.getChildAt(0));	
				// Insert the edge into the graph view if unique:
				measureGraph.getGraphLayoutCache().insert(spEdge);
				
				CharacteristicLink charLink = model.getProject().getContent().getCharacteristicLinkByCharacteristic(spec);
				CharacteristicMap charMap = model.getProject().getContent().getCharacteristicMapByCharacteristic(charLink);
				
				Characteristics targetChar = charMap.getTargetCharacteristic().getCharacteristic();
				if (targetChar instanceof Characteristics) {
					CategoryCell catCell = null;
					
					Iterator<CategoryCell> itCatCell = catCells.iterator();
					while (itCatCell.hasNext()) {
						catCell = itCatCell.next();
						
						if (catCell.getCategory().equals(targetChar))
							break;
					}
					
					CharacteristicEdge charEdge = new CharacteristicEdge(charMap);			
					charEdge.setTarget(catCell.getChildAt(0));
					charEdge.setSource(specCell.getChildAt(0));	
					// Insert the edge into the graph view if unique:
					measureGraph.getGraphLayoutCache().insert(charEdge);
				}
				
				rowCounter++;
			}
			attrLink = model.getProject().getContent().getLinkByAttribute(dim);
			if ((attrLink != null) &&
				(attrLink.getInstance() != null)) {
					String label = attrLink.getInstance().getLabel();

					columnCounter = (swaped ? 0 : 5);
					xMod = (swaped ? 0 : -290);
					DefaultGraphCell labelCell = generateLabelVertex(label,
							label,
							null,
							xBase + (columnCounter * columnWidth) + xMod, yBase + (maxYCounterSource * 65), instanceWidth, instanceHeight, 
							Color.ORANGE, 
							false);		
					// Insert the vertex into the graph view (including child port and attributes):
					measureCache.insert(labelCell);
			}
			
			if (rowCounter >= maxYCounterTarget)
				maxYCounterTarget = rowCounter+1;
			maxYCounterSource = maxYCounterTarget;
						
			columnCounter++;
			rowCounter = 0;
		}
		
//		_setGraphUpdate(GraphUpdate.LAYER_REFRESH);
//		_fireGraphUpdateEvent();
		
		return measureGraph;
	}
	
	/**
	 * @param model
	 * @return
	 */
	public JGraph createMapConceptualGraph(CStatsModel model, boolean swaped) {
		int columnWidth		= 195;
		int instanceWidth	= 180;
		int instanceHeight	= 48;
		int dimensionWidth	= 80;
		int dimensionHeight	= 48;
		int specificationWidth = 60;
		int specificationHeight = 60;
		int indicatorWidth	= 80;
		int indicatorHeight	= 48;
		int prescriptionWidth = 60;
		int prescriptionHeight = 60;

		GraphModel			conceptModel	= new DefaultGraphModel();
		GraphLayoutCache	conceptCache 	= new GraphLayoutCache(conceptModel, new GCellViewFactory(), true); // expanding and collapsing
		JGraph				conceptGraph	= new ConceptualJGraph(conceptModel, conceptCache); // expanding and collapsing
				
		conceptGraph.setSizeable(false);
		conceptGraph.setEditable(false);
		
		int xBase = 25; int columnCounter; int xMod;
		int yBase = 25; int rowCounter = 0;
				
		int maxYCounterTarget = 0;
		int maxYCounterSource = 0;
		int baseY = 0;
			
		Iterator<OperaIndicator> itInd = model.getProject().getContent().getIndicators().iterator(); 
		rowCounter = 0;
		while (itInd.hasNext()) {
			OperaIndicator ind = itInd.next();
						
			/* Handle Dimension */
			AttributeLink link = model.getProject().getContent().getLinkByAttribute(ind);
			AttributeMap attrMap = model.getProject().getContent().getAttributeMapByAttribute(link);
			
			Attributes targetAttr = attrMap.getTargetAttribute().getAttribute();
			ConDimension dim = (ConDimension)targetAttr;
						
			AttributeLink attrLink = model.getProject().getContent().getLinkByAttribute(dim);
			if ((attrLink != null) &&
				(attrLink.getInstance() != null)) {
					String label = attrLink.getInstance().getLabel();

					columnCounter = (swaped ? 5 : 0);
					xMod = (swaped ? -290 : 0);
					DefaultGraphCell labelCell = generateLabelVertex(label,
							label,
							null,
							xBase + (columnCounter * columnWidth) + xMod, yBase + (baseY * 65), instanceWidth, instanceHeight, 
							Color.ORANGE, 
							false);		
					// Insert the vertex into the graph view (including child port and attributes):
					conceptCache.insert(labelCell);
			}
			
			
			columnCounter = (swaped ? 4 : 1);
			xMod = (swaped ? -190 : 0);
			DefaultGraphCell dimCell = generateDimVertex(dim.getLabel(),
					dim,
					null,
					xBase + (columnCounter * columnWidth) + xMod, yBase + (baseY * 65), dimensionWidth, dimensionHeight, 
					Color.ORANGE, 
					false);		
			// Insert the vertex into the graph view (including child port and attributes):
			conceptCache.insert(dimCell);
			
			Iterator<CharacteristicLink> itCharLink = model.getProject().getContent().getCharacteristicLinksByAttribute(dim).iterator();
			ArrayList<SpecificationCell> specCells = new ArrayList<SpecificationCell>();
			columnCounter = 0;
			while (itCharLink.hasNext()) {
				CharacteristicLink	charLink = itCharLink.next();
				
				ConSpecification spec = (ConSpecification)charLink.getCharacteristic();
				
				columnCounter = (swaped ? 3 : 2);
				xMod = (swaped ? +90 : +100);
				DefaultGraphCell specCell = generateSpecVertex(spec.getLabel(),
						spec,
						xBase + (columnCounter * columnWidth) - xMod, yBase + (baseY * 65) + (rowCounter * 65), specificationWidth, specificationHeight, 
						Color.ORANGE, 
						false);
				// Insert the vertex into the graph view (including child port and attributes):
				specCells.add((SpecificationCell)specCell);
				conceptCache.insert(specCell);
				
				SpecificationEdge specEdge = new SpecificationEdge(charLink);			
				specEdge.setSource(dimCell.getChildAt(0));
				specEdge.setTarget(specCell.getChildAt(0));	
				// Insert the edge into the graph view if unique:
				conceptGraph.getGraphLayoutCache().insert(specEdge);
				
				rowCounter++;
			}
			maxYCounterTarget = rowCounter+1;
			/* Handle */
		
			columnCounter = (swaped ? 1 : 4);
			xMod = (swaped ? 0 : -190);
			rowCounter = 0;
			DefaultGraphCell indCell = generateIndVertex(ind.getLabel(),
					ind,
					null,
					xBase + (columnCounter * columnWidth) + xMod, yBase + (baseY * 65), indicatorWidth, indicatorHeight, 
					Color.ORANGE, 
					false);		
			// Insert the vertex into the graph view (including child port and attributes):
			conceptCache.insert(indCell);
			
			columnCounter = 2;
			itCharLink = model.getProject().getContent().getCharacteristicLinksByAttribute(ind).iterator();
			while (itCharLink.hasNext()) {
				CharacteristicLink	charlink = itCharLink.next();
				
				OperaPrescription	pres = (OperaPrescription)charlink.getCharacteristic();
				
				columnCounter = (swaped ? 2 : 3);
				xMod = (swaped ? +100 : +90);
				DefaultGraphCell presCell = generatePresVertex(pres.getLabel(),
						pres,
						xBase + (columnCounter * columnWidth) - xMod, yBase + (baseY * 65) + (rowCounter * 65), prescriptionWidth, prescriptionHeight, 
						Color.ORANGE, 
						false);
				// Insert the vertex into the graph view (including child port and attributes):
				conceptCache.insert(presCell);
				
				PrescriptionEdge presEdge = new PrescriptionEdge(charlink);			
				presEdge.setSource(indCell.getChildAt(0));
				presEdge.setTarget(presCell.getChildAt(0));	
				// Insert the edge into the graph view if unique:
				conceptGraph.getGraphLayoutCache().insert(presEdge);
				
				CharacteristicLink charLink = model.getProject().getContent().getCharacteristicLinkByCharacteristic(pres);
				CharacteristicMap charMap = model.getProject().getContent().getCharacteristicMapByCharacteristic(charLink);
				
				if ((charMap != null) &&
						(charMap.getTargetCharacteristic() != null)) {
					Characteristics targetChar = charMap.getTargetCharacteristic().getCharacteristic();
					if (targetChar instanceof Characteristics) {
						SpecificationCell specCell = null;
						
						Iterator<SpecificationCell> itSpecCell = specCells.iterator();
						while (itSpecCell.hasNext()) {
							specCell = itSpecCell.next();
							
							if (specCell.getSpecification().equals(targetChar))
								break;
						}
						
						CharacteristicEdge charEdge = new CharacteristicEdge(charMap);			
						charEdge.setTarget(specCell.getChildAt(0));
						charEdge.setSource(presCell.getChildAt(0));	
						// Insert the edge into the graph view if unique:
						conceptGraph.getGraphLayoutCache().insert(charEdge);
					}
				}
				rowCounter++;
			}
			maxYCounterTarget = rowCounter+1;
			
			attrLink = model.getProject().getContent().getLinkByAttribute(ind);
			if ((attrLink != null) &&
				(attrLink.getInstance() != null)) {
					String label = attrLink.getInstance().getLabel();

					columnCounter = (swaped ? 0 : 5);
					xMod = (swaped ? 0 : -290);
					
					DefaultGraphCell labelCell = generateLabelVertex(label,
							label,
							null,
							xBase + (columnCounter * columnWidth) + xMod, yBase + (baseY * 65), instanceWidth, instanceHeight, 
							Color.ORANGE, 
							false);		
					// Insert the vertex into the graph view (including child port and attributes):
					conceptCache.insert(labelCell);
			}
			
			if (maxYCounterSource > maxYCounterTarget)
				baseY += maxYCounterSource;
			else
				baseY += maxYCounterTarget;
			
			columnCounter = 0;
			rowCounter = 0;
		}
		
//		_setGraphUpdate(GraphUpdate.LAYER_REFRESH);
//		_fireGraphUpdateEvent();
		
		return conceptGraph;
	}
	
	/**
	 * @param model
	 * @return
	 */
	public JGraph createMapOperationalGraph(CStatsModel model, boolean swaped) {
		int columnWidth		= 195;
		int instanceWidth	= 180;
		int instanceHeight	= 48;
		int indicatorWidth	= 80;
		int indicatorHeight	= 48;
		int prescriptionWidth = 60;
		int prescriptionHeight = 60;
		int variableWidth	= 80;
		int variableHeight	= 48;
		int valueWidth		= 60;
		int valueHeight		= 60;
		
		GraphModel			operaModel	= new DefaultGraphModel();
		GraphLayoutCache	operaCache 	= new GraphLayoutCache(operaModel, new GCellViewFactory(), true); // expanding and collapsing
		JGraph				operaGraph	= new ConceptualJGraph(operaModel, operaCache); // expanding and collapsing
			
		operaGraph.setSizeable(false);
		operaGraph.setEditable(false);
		
		int xBase = 25; int columnCounter = 0; int xMod;
		int yBase = 25; int rowCounter = 0;
		
		int maxYCounterTarget = 0;
		int maxYCounterSource = 0;
		int baseY = 0;
			
		Iterator<Variable> itVar = model.getProject().getContent().getVariables().iterator(); 
		rowCounter = 0;
		while (itVar.hasNext()) {
			Variable var = itVar.next();
						
			/* Handle Indicator */
			AttributeLink link = model.getProject().getContent().getLinkByAttribute(var);
			AttributeMap attrMap = model.getProject().getContent().getAttributeMapByAttribute(link);
			
			if ((attrMap instanceof AttributeMap) &&
					(attrMap.getTargetAttribute() instanceof AttributeLink)) {
				Attributes targetAttr = attrMap.getTargetAttribute().getAttribute();
				OperaIndicator ind = (OperaIndicator)targetAttr;
						
				AttributeLink attrLink = model.getProject().getContent().getLinkByAttribute(ind);
				if ((attrLink != null) &&
					(attrLink.getInstance() != null)) {
						String label = attrLink.getInstance().getLabel();
						columnCounter = (swaped ? 5 : 0);
						xMod = (swaped ? -290 : 0);
	
						DefaultGraphCell labelCell = generateLabelVertex(label,
								label,
								null,
								xBase + (columnCounter * columnWidth) + xMod, yBase + (baseY * 65), instanceWidth, instanceHeight, 
								Color.ORANGE, 
								false);		
						// Insert the vertex into the graph view (including child port and attributes):
						operaCache.insert(labelCell);
				}
			
			
				columnCounter = (swaped ? 4 : 1);
				xMod = (swaped ? -190 : 0);
				DefaultGraphCell indCell = generateIndVertex(ind.getLabel(),
						ind,
						null,
						xBase + (columnCounter * columnWidth) + xMod, yBase + (baseY * 65), indicatorWidth, indicatorHeight, 
						Color.ORANGE, 
						false);		
				// Insert the vertex into the graph view (including child port and attributes):
				operaCache.insert(indCell);
				
				Iterator<CharacteristicLink> itCharLink = model.getProject().getContent().getCharacteristicLinksByAttribute(ind).iterator();
				ArrayList<PrescriptionCell> presCells = new ArrayList<PrescriptionCell>();
				columnCounter = 0;
				while (itCharLink.hasNext()) {
					CharacteristicLink	charLink = itCharLink.next();
					
					OperaPrescription pres = (OperaPrescription)charLink.getCharacteristic();
					
					columnCounter = (swaped ? 3 : 2);
					xMod = (swaped ? +90 : +100);
					DefaultGraphCell presCell = generatePresVertex(pres.getLabel(),
							pres,
							xBase + (columnCounter * columnWidth) - xMod, yBase + (baseY * 65) + (rowCounter * 65), prescriptionWidth, prescriptionHeight, 
							Color.ORANGE, 
							false);
					// Insert the vertex into the graph view (including child port and attributes):
					presCells.add((PrescriptionCell)presCell);
					operaCache.insert(presCell);
					
					PrescriptionEdge presEdge = new PrescriptionEdge(charLink);			
					presEdge.setSource(indCell.getChildAt(0));
					presEdge.setTarget(presCell.getChildAt(0));	
					// Insert the edge into the graph view if unique:
					operaGraph.getGraphLayoutCache().insert(presEdge);
					
					rowCounter++;
				}
				maxYCounterTarget = rowCounter+1;
				/* Handle */
			
				columnCounter = (swaped ? 1 : 4);
				xMod = (swaped ? 0 : -190);
				rowCounter = 0;
				DefaultGraphCell varCell = generateVarVertex(var.getLabel(),
						var,
						null,
						xBase + (columnCounter * columnWidth) + xMod, yBase + (baseY * 65), variableWidth, variableHeight, 
						Color.ORANGE, 
						false);		
				// Insert the vertex into the graph view (including child port and attributes):
				operaCache.insert(varCell);
				
//				columnCounter = 2;
				itCharLink = model.getProject().getContent().getCharacteristicLinksByAttribute(var).iterator();
				while (itCharLink.hasNext()) {
					CharacteristicLink	charlink = itCharLink.next();
					
					Value	val = (Value)charlink.getCharacteristic();
					
					columnCounter = (swaped ? 2 : 3);
					xMod = (swaped ? +100 : +90);
					DefaultGraphCell valCell = generateValVertex(val.getLabel(),
							val,
							xBase + (columnCounter * columnWidth) - xMod, yBase + (baseY * 65) + (rowCounter * 65), valueWidth, valueHeight, 
							Color.ORANGE, 
							false);
					// Insert the vertex into the graph view (including child port and attributes):
					operaCache.insert(valCell);
					
					ValueEdge valEdge = new ValueEdge(charlink);			
					valEdge.setSource(varCell.getChildAt(0));
					valEdge.setTarget(valCell.getChildAt(0));	
					// Insert the edge into the graph view if unique:
					operaGraph.getGraphLayoutCache().insert(valEdge);
					
					CharacteristicLink charLink = model.getProject().getContent().getCharacteristicLinkByCharacteristic(val);
					CharacteristicMap charMap = model.getProject().getContent().getCharacteristicMapByCharacteristic(charLink);
					
					if ((charMap != null) &&
							(charMap.getTargetCharacteristic() != null)) {
						Characteristics targetChar = charMap.getTargetCharacteristic().getCharacteristic();
						if (targetChar instanceof Characteristics) {
							PrescriptionCell presCell = null;
							
							Iterator<PrescriptionCell> itPresCell = presCells.iterator();
							while (itPresCell.hasNext()) {
								presCell = itPresCell.next();
								
								if (presCell.getPrescription().equals(targetChar))
									break;
							}
							
							CharacteristicEdge charEdge = new CharacteristicEdge(charMap);			
							charEdge.setTarget(presCell.getChildAt(0));
							charEdge.setSource(valCell.getChildAt(0));	
							// Insert the edge into the graph view if unique:
							operaGraph.getGraphLayoutCache().insert(charEdge);
						}
					}
					rowCounter++;
				}
				maxYCounterTarget = rowCounter+1;
				
				attrLink = model.getProject().getContent().getLinkByAttribute(var);
				if ((attrLink != null) &&
					(attrLink.getInstance() != null)) {
						String label = attrLink.getInstance().getLabel();
	
						columnCounter = (swaped ? 0 : 5);
						xMod = (swaped ? 0 : -290);
						DefaultGraphCell labelCell = generateLabelVertex(label,
								label,
								null,
								xBase + (columnCounter * columnWidth) + xMod, yBase + (baseY * 65), instanceWidth, instanceHeight, 
								Color.ORANGE, 
								false);		
						// Insert the vertex into the graph view (including child port and attributes):
						operaCache.insert(labelCell);
				}
				
				if (maxYCounterSource > maxYCounterTarget)
					baseY += maxYCounterSource;
				else
					baseY += maxYCounterTarget;
				
				columnCounter = 0;
				rowCounter = 0;
			}
		}
		
//		_setGraphUpdate(GraphUpdate.LAYER_REFRESH);
//		_fireGraphUpdateEvent();
		
		return operaGraph;
	}
	
	/*
	 *	Methods
	 */
	/**
	 * @param locale
	 */
	protected void changeLanguage(Locale locale) {
		ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, locale);
		
		currentLocale = locale;
		resourceBundle = bundle;
		
		setTitle(bundle.getString(TITLE));
		printBtn.setText(resourceBundle.getString(PRINT));
 		saveBtn.setText(resourceBundle.getString(SAVE));
 		
		showOverviewBtn.setText(resourceBundle.getString(OVERVIEW));
		showMappingBtn.setText(resourceBundle.getString(MAPPING));
		showMeaBtn.setText(resourceBundle.getString(MEA));
		showMapMeaBtn.setText(resourceBundle.getString(MAP_MEA));
		showConBtn.setText(resourceBundle.getString(CON));
		showMapConBtn.setText(resourceBundle.getString(MAP_CON));
		showOperaBtn.setText(resourceBundle.getString(OPERA));
		showMapOperaBtn.setText(resourceBundle.getString(MAP_OPERA));
		showDataBtn.setText(resourceBundle.getString(DATA));
		
		swapedChk.setText(resourceBundle.getString(SWAPED)); 
	}
	
	/**
	 * @param f
	 */
	protected void changeFont(Font f) {
		UIManager.put("InternalFrame.titleFont", f);
		javax.swing.SwingUtilities.updateComponentTreeUI(this);
		
		printBtn.setFont(f);
 		saveBtn.setFont(f);
		
		showOverviewBtn.setFont(f);
		showMappingBtn.setFont(f);
		showMeaBtn.setFont(f);
		showMapMeaBtn.setFont(f);
		showConBtn.setFont(f);
		showMapConBtn.setFont(f);
		showOperaBtn.setFont(f);
		showMapOperaBtn.setFont(f);
		showDataBtn.setFont(f);
		
		swapedChk.setFont(f); 
	}
	
	/*
	 *	FrameGraph.update handles the update of the project graph!
	 */
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.InternalFrame#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		IdentifiedParameter parameter = null;
		
		if (arg instanceof IdentifiedParameter) {
			parameter = (IdentifiedParameter)arg;
		}
		
		if (parameter != null) {
			Object[]	addenda		= parameter.getParameters();
			
			CStatsModel model = null;
			if (addenda[0] instanceof CStatsModel)
				model = (CStatsModel)addenda[0];
			
			switch (parameter.getID()) {
				case ActionCommandID.BTN_TB_SHOW_FORM:
					try {
						this.setMaximum(false);
						this.updateUI();
					} catch (PropertyVetoException e) {
						System.err.println("BTN_TB_SHOW_FORM:FrameGraph");
					}
					break;
				case ActionCommandID.BTN_TB_SHOW_GRAPH:
					try {
						this.setSelected(true);
						this.setMaximum(true);
						this.updateUI();
					} catch (PropertyVetoException e) {
						System.err.println("BTN_TB_SHOW_GRAPH:FrameGraph");
					}
					break;
				case ActionCommandID.BTN_TB_SHOW_REPORT:
					try {
						this.setMaximum(false);
						this.updateUI();
					} catch (PropertyVetoException e) {
						System.err.println("BTN_TB_SHOW_REPORT:FrameGraph");
					}
					break;
				case ActionCommandID.MOD_MOD:
					switch (selectedGraph) {
						case NO_GRAPH:
							break;
						case ActionCommandID.BTN_FRM_GRP_SHW_OVER_GRAPH:
							ToolTipManager.sharedInstance().unregisterComponent(outputGraph);
							outputGraph = createOverviewGraph(model, swapedChk.isSelected());
							ToolTipManager.sharedInstance().registerComponent(outputGraph);
							
							graphPane.setGraph(outputGraph);
							graphPane.setViewportView(outputGraph);
							graphPane.update(getGraphics());
							this.update(getGraphics());
							break;
						case ActionCommandID.BTN_FRM_GRP_SHW_MAP_GRAPH:
							ToolTipManager.sharedInstance().unregisterComponent(outputGraph);
							outputGraph = createMappingGraph(model, swapedChk.isSelected());
							ToolTipManager.sharedInstance().registerComponent(outputGraph);
											
							graphPane.setGraph(outputGraph);
							graphPane.setViewportView(outputGraph);
							graphPane.update(getGraphics());
							this.update(getGraphics());
							break;
						case ActionCommandID.BTN_FRM_GRP_SHW_MEA_GRAPH:
							ToolTipManager.sharedInstance().unregisterComponent(outputGraph);
							outputGraph = createMeasurementGraph(model);
							ToolTipManager.sharedInstance().registerComponent(outputGraph);
											
							graphPane.setGraph(outputGraph);
							graphPane.setViewportView(outputGraph);
							graphPane.update(getGraphics());
							this.update(getGraphics());
							break;
						case ActionCommandID.BTN_FRM_GRP_SHW_CON_GRAPH:
							ToolTipManager.sharedInstance().unregisterComponent(outputGraph);
							outputGraph = createConceptualGraph(model);
							ToolTipManager.sharedInstance().registerComponent(outputGraph);
											
							graphPane.setGraph(outputGraph);
							graphPane.setViewportView(outputGraph);
							graphPane.update(getGraphics());
							this.update(getGraphics());
							break;
						case ActionCommandID.BTN_FRM_GRP_SHW_OPE_GRAPH:
							ToolTipManager.sharedInstance().unregisterComponent(outputGraph);
							outputGraph = createOperationalGraph(model);
							ToolTipManager.sharedInstance().registerComponent(outputGraph);
							
							graphPane.setGraph(outputGraph);
							graphPane.setViewportView(outputGraph);
							graphPane.update(getGraphics());
							this.update(getGraphics());
							break;
						case ActionCommandID.BTN_FRM_GRP_SHW_DAT_GRAPH:
							ToolTipManager.sharedInstance().unregisterComponent(outputGraph);
							outputGraph = createDataRecodingGraph(model);
							ToolTipManager.sharedInstance().registerComponent(outputGraph);
							
							graphPane.setGraph(outputGraph);
							graphPane.setViewportView(outputGraph);
							graphPane.update(getGraphics());
							this.update(getGraphics());
							break;
						case ActionCommandID.BTN_FRM_GRP_SHW_MAP_MEA_GRAPH:
							ToolTipManager.sharedInstance().unregisterComponent(outputGraph);
							outputGraph = createMapMeasurementGraph(model, swapedChk.isSelected());
							ToolTipManager.sharedInstance().registerComponent(outputGraph);
											
							graphPane.setGraph(outputGraph);
							graphPane.setViewportView(outputGraph);
							graphPane.update(getGraphics());
							this.update(getGraphics());
							break;
						case ActionCommandID.BTN_FRM_GRP_SHW_MAP_CON_GRAPH:
							ToolTipManager.sharedInstance().unregisterComponent(outputGraph);
							outputGraph = createMapConceptualGraph(model, swapedChk.isSelected());
							ToolTipManager.sharedInstance().registerComponent(outputGraph);
											
							graphPane.setGraph(outputGraph);
							graphPane.setViewportView(outputGraph);
							graphPane.update(getGraphics());
							this.update(getGraphics());
							break;
						case ActionCommandID.BTN_FRM_GRP_SHW_MAP_OPE_GRAPH:
							ToolTipManager.sharedInstance().unregisterComponent(outputGraph);
							outputGraph = createMapOperationalGraph(model, swapedChk.isSelected());
							ToolTipManager.sharedInstance().registerComponent(outputGraph);
											
							graphPane.setGraph(outputGraph);
							graphPane.setViewportView(outputGraph);
							graphPane.update(getGraphics());
							this.update(getGraphics());
							break;
						default:
							break;
					}
					break;
				case ActionCommandID.CMD_USER_LOGIN:
					/* TODO */
					break;
				case ActionCommandID.CMD_USER_LOGOFF:
					outputGraph = new GJGraph(new DefaultGraphModel());
					selectedGraph = NO_GRAPH;
					
					graphPane.setGraph(outputGraph);
					graphPane.setViewportView(outputGraph);
					graphPane.update(getGraphics());
					this.update(getGraphics());				
					break;
				case ActionCommandID.CMD_PRJ_OPEN_PROJECT:
					outputGraph = clearGraph();
					selectedGraph = NO_GRAPH;
					
					graphPane.setGraph(outputGraph);
					graphPane.setViewportView(outputGraph);
					graphPane.update(getGraphics());
					this.update(getGraphics());
					break;
				case ActionCommandID.CMD_PRJ_REMOVE:
				case ActionCommandID.CMD_PRJ_CLOSE:
					outputGraph = clearGraph();
					selectedGraph = NO_GRAPH;
					
					graphPane.setGraph(outputGraph);
					graphPane.setViewportView(outputGraph);
					graphPane.update(getGraphics());
					this.update(getGraphics());
					break;					
				case ActionCommandID.BTN_FRM_GRP_SHW_OVER_GRAPH:
					ToolTipManager.sharedInstance().unregisterComponent(outputGraph);
					outputGraph = createOverviewGraph(model, swapedChk.isSelected());
					selectedGraph = ActionCommandID.BTN_FRM_GRP_SHW_OVER_GRAPH;
					ToolTipManager.sharedInstance().registerComponent(outputGraph);
									
					graphPane.setGraph(outputGraph);
					graphPane.setViewportView(outputGraph);
					graphPane.update(getGraphics());
					this.update(getGraphics());
					break;
				case ActionCommandID.BTN_FRM_GRP_SHW_MAP_GRAPH:
					ToolTipManager.sharedInstance().unregisterComponent(outputGraph);
					outputGraph = createMappingGraph(model, swapedChk.isSelected());
					selectedGraph = ActionCommandID.BTN_FRM_GRP_SHW_MAP_GRAPH;
					ToolTipManager.sharedInstance().registerComponent(outputGraph);
									
					graphPane.setGraph(outputGraph);
					graphPane.setViewportView(outputGraph);
					graphPane.update(getGraphics());
					this.update(getGraphics());
					break;
				case ActionCommandID.BTN_FRM_GRP_SHW_MEA_GRAPH:
					ToolTipManager.sharedInstance().unregisterComponent(outputGraph);
					outputGraph = createMeasurementGraph(model);
					selectedGraph = ActionCommandID.BTN_FRM_GRP_SHW_MEA_GRAPH;
					ToolTipManager.sharedInstance().registerComponent(outputGraph);
									
					graphPane.setGraph(outputGraph);
					graphPane.setViewportView(outputGraph);
					graphPane.update(getGraphics());
					this.update(getGraphics());
					break;
				case ActionCommandID.BTN_FRM_GRP_SHW_CON_GRAPH:
					ToolTipManager.sharedInstance().unregisterComponent(outputGraph);
					outputGraph = createConceptualGraph(model);
					selectedGraph = ActionCommandID.BTN_FRM_GRP_SHW_CON_GRAPH;
					ToolTipManager.sharedInstance().registerComponent(outputGraph);
									
					graphPane.setGraph(outputGraph);
					graphPane.setViewportView(outputGraph);
					graphPane.update(getGraphics());
					this.update(getGraphics());
					break;
				case ActionCommandID.BTN_FRM_GRP_SHW_OPE_GRAPH:
					ToolTipManager.sharedInstance().unregisterComponent(outputGraph);
					outputGraph = createOperationalGraph(model);
					selectedGraph = ActionCommandID.BTN_FRM_GRP_SHW_OPE_GRAPH;
					ToolTipManager.sharedInstance().registerComponent(outputGraph);
					
					graphPane.setGraph(outputGraph);
					graphPane.setViewportView(outputGraph);
					graphPane.update(getGraphics());
					this.update(getGraphics());
					break;
				case ActionCommandID.BTN_FRM_GRP_SHW_DAT_GRAPH:
					ToolTipManager.sharedInstance().unregisterComponent(outputGraph);
					outputGraph = createDataRecodingGraph(model);
					selectedGraph = ActionCommandID.BTN_FRM_GRP_SHW_DAT_GRAPH;
					ToolTipManager.sharedInstance().registerComponent(outputGraph);
					
					graphPane.setGraph(outputGraph);
					graphPane.setViewportView(outputGraph);
					graphPane.update(getGraphics());
					this.update(getGraphics());
					break;
				case ActionCommandID.BTN_FRM_GRP_PRINT_GRAPH:
					printLayer(graphPane);
					break;
				case ActionCommandID.BTN_FRM_GRP_SAVE_GRAPH:
					exportImage(outputGraph, "Graph.jpg", model.getProject());
					break;
				case ActionCommandID.BTN_FRM_GRP_SHW_MAP_MEA_GRAPH:
					ToolTipManager.sharedInstance().unregisterComponent(outputGraph);
					outputGraph = createMapMeasurementGraph(model, swapedChk.isSelected());
					selectedGraph = ActionCommandID.BTN_FRM_GRP_SHW_MAP_MEA_GRAPH;
					ToolTipManager.sharedInstance().registerComponent(outputGraph);
									
					graphPane.setGraph(outputGraph);
					graphPane.setViewportView(outputGraph);
					graphPane.update(getGraphics());
					this.update(getGraphics());
					break;
				case ActionCommandID.BTN_FRM_GRP_SHW_MAP_CON_GRAPH:
					ToolTipManager.sharedInstance().unregisterComponent(outputGraph);
					outputGraph = createMapConceptualGraph(model, swapedChk.isSelected());
					selectedGraph = ActionCommandID.BTN_FRM_GRP_SHW_MAP_CON_GRAPH;
					ToolTipManager.sharedInstance().registerComponent(outputGraph);
									
					graphPane.setGraph(outputGraph);
					graphPane.setViewportView(outputGraph);
					graphPane.update(getGraphics());
					this.update(getGraphics());
					break;
				case ActionCommandID.BTN_FRM_GRP_SHW_MAP_OPE_GRAPH:
					ToolTipManager.sharedInstance().unregisterComponent(outputGraph);
					outputGraph = createMapOperationalGraph(model, swapedChk.isSelected());
					selectedGraph = ActionCommandID.BTN_FRM_GRP_SHW_MAP_OPE_GRAPH;
					ToolTipManager.sharedInstance().registerComponent(outputGraph);
									
					graphPane.setGraph(outputGraph);
					graphPane.setViewportView(outputGraph);
					graphPane.update(getGraphics());
					this.update(getGraphics());
					break;
				default:
					break;
			}			
		}	
		
	}
	
	private class JPGFilter extends SaveImageFileFilter
	{
		/* (non-Javadoc)
		 * @see org.gesis.charmstats.view.SaveImageFileFilter#accept(java.io.File)
		 */
		public boolean accept (File f) {
			return f.getName().toLowerCase().endsWith(".jpg") || f.isDirectory();
		}
	 
		/* (non-Javadoc)
		 * @see org.gesis.charmstats.view.SaveImageFileFilter#getDescription()
		 */
		public String getDescription () {
			return "JPG (*.jpg)";
		}
		
		/* (non-Javadoc)
		 * @see org.gesis.charmstats.view.SaveImageFileFilter#getFilterID()
		 */
		@Override
		public int getFilterID() {
			return jpgFilterID;
		}
	}
	
	private class GIFFilter extends SaveImageFileFilter
	{
		/* (non-Javadoc)
		 * @see org.gesis.charmstats.view.SaveImageFileFilter#accept(java.io.File)
		 */
		public boolean accept (File f) {
			return f.getName().toLowerCase().endsWith(".gif") || f.isDirectory();
		}
	 
		/* (non-Javadoc)
		 * @see org.gesis.charmstats.view.SaveImageFileFilter#getDescription()
		 */
		public String getDescription () {
			return "GIF (*.gif)";
		}
		
		/* (non-Javadoc)
		 * @see org.gesis.charmstats.view.SaveImageFileFilter#getFilterID()
		 */
		@Override
		public int getFilterID() {
			return gifFilterID;
		}
	}
	
	private class PNGFilter extends SaveImageFileFilter
	{
		/* (non-Javadoc)
		 * @see org.gesis.charmstats.view.SaveImageFileFilter#accept(java.io.File)
		 */
		public boolean accept (File f) {
			return f.getName().toLowerCase().endsWith(".png") || f.isDirectory();
		}
	 
		/* (non-Javadoc)
		 * @see org.gesis.charmstats.view.SaveImageFileFilter#getDescription()
		 */
		public String getDescription () {
			return "PNG (*.png)";
		}
		
		/* (non-Javadoc)
		 * @see org.gesis.charmstats.view.SaveImageFileFilter#getFilterID()
		 */
		@Override
		public int getFilterID() {
			return pngFilterID;
		}
	}
	
	private class BMPFilter extends SaveImageFileFilter
	{
		/* (non-Javadoc)
		 * @see org.gesis.charmstats.view.SaveImageFileFilter#accept(java.io.File)
		 */
		public boolean accept (File f) {
			return f.getName().toLowerCase().endsWith(".bmp") || f.isDirectory();
		}
	 
		/* (non-Javadoc)
		 * @see org.gesis.charmstats.view.SaveImageFileFilter#getDescription()
		 */
		public String getDescription () {
			return "BMP (*.bmp)";
		}
		
		/* (non-Javadoc)
		 * @see org.gesis.charmstats.view.SaveImageFileFilter#getFilterID()
		 */
		@Override
		public int getFilterID() {
			return bmpFilterID;
		}
	}
	
	@SuppressWarnings("unused")
	private class WBMPFilter extends SaveImageFileFilter
	{
		/* (non-Javadoc)
		 * @see org.gesis.charmstats.view.SaveImageFileFilter#accept(java.io.File)
		 */
		public boolean accept (File f) {
			return f.getName().toLowerCase().endsWith(".wbmp") || f.isDirectory();
		}
	 
		/* (non-Javadoc)
		 * @see org.gesis.charmstats.view.SaveImageFileFilter#getDescription()
		 */
		public String getDescription () {
			return "WBMP (*.wbmp)";
		}
		
		/* (non-Javadoc)
		 * @see org.gesis.charmstats.view.SaveImageFileFilter#getFilterID()
		 */
		@Override
		public int getFilterID() {
			return wbmpFilterID;
		}
	}
	
	/**
	 * @param resourceURL
	 * @param description
	 * @return
	 */
	protected static ImageIcon createImageIcon(String resourceURL,
			String description) {
		java.net.URL imgURL = ToolBar.class.getClassLoader().getResource(resourceURL);
		if (imgURL != null) {
	   		return new ImageIcon(imgURL, description);
	   	} else {
	   		System.err.println("Couldn't find file: " + resourceURL);
	   		return null;
	   	}
	}
}

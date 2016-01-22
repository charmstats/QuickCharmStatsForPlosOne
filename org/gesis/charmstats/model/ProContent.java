package org.gesis.charmstats.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class ProContent {

	/*
	 *	Fields
	 */
//	private Concept						concept; moved to Project

//	private ArrayList<Literature>		literatures; moved to Concept
	
		
	/* CStats - List of created Links - Intermediate between Project and Instance(s) */
	private ArrayList<InstanceLink> 		inst_links		= new ArrayList<InstanceLink>();

	/* CCats - List of created Maps - Mapping between Instances */
	private ArrayList<InstanceMap>			inst_maps		= new ArrayList<InstanceMap>();
	
	/* CStats - List of declared Instances */
	private ArrayList<WorkStepInstance>		instances		= new ArrayList<WorkStepInstance>();
	
	/* CStats - List of created Links - Intermediate between Instance and Attribute(s) */
	private ArrayList<AttributeLink> 		attr_links		= new ArrayList<AttributeLink>();
	
	/* CCats - List of created Maps - Mapping between Attributes */
	private ArrayList<AttributeMap> 		attr_maps		= new ArrayList<AttributeMap>();
	
	private ArrayList<AttributeComp>		attr_comps		= new ArrayList<AttributeComp>();
	
	/* List(s) of Attributes */
	/* Imported or declared Measurement */
	private Measurement						measurement;
	
	/* List of declared Measurements */
//	private ArrayList<Measurement> 			measurements 	= new ArrayList<Measurement>(); // unused yet
	
	/* List of declared Dimensions */
	private ArrayList<ConDimension> 		dimensions 		= new ArrayList<ConDimension>();
	
	/* List of declared Indicators */
	private ArrayList<OperaIndicator> 		indicators 		= new ArrayList<OperaIndicator>();
	
	/* List of imported / declared Variables */
	private ArrayList<Variable> 			variables 		= new ArrayList<Variable>();
	
	/* CStats - List of created Links - Intermediate between Attributes and Char. */
	private ArrayList<CharacteristicLink> 	char_links	= new ArrayList<CharacteristicLink>();
	
	/* CCats - List of created Maps - Mapping between Characteristics */
	private ArrayList<CharacteristicMap>	char_maps		= new ArrayList<CharacteristicMap>();
	
	/* List(s) of Characteristics */
	/* List of imported / declared Categories */
	private ArrayList<Category> 			categories 		= new ArrayList<Category>();
	
	/* List of declared Specifications */
	private ArrayList<ConSpecification>		specifications	= new ArrayList<ConSpecification>();
	
	/* List of declared Prescriptions */
	private ArrayList<OperaPrescription>	prescriptions	= new ArrayList<OperaPrescription>();
	
	/* List of imported / declared Values */
	private ArrayList<Value>				values 			= new ArrayList<Value>();
	
	
	/* Handle Tab Comments */
	private TabDummy					tabProject;
	private TabDummy					tabConcept;
	private TabDummy					tabLiterature;
	private TabDummy					tabMeasurement;
	private TabDummy					tabDimension;
	private TabDummy					tabSpecification;
	private TabDummy					tabMapDimensionInstance;
	private TabDummy					tabMapDimensionAttribute;
	private TabDummy					tabMapDimensionChar;
	private	TabDummy					tabOSInstance;
	private TabDummy					tabIndicator;
	private TabDummy					tabPrescription;
	private TabDummy					tabMapIndicatorInstance;
	private TabDummy					tabMapIndicatorAttribute;
	private TabDummy					tabMapIndicatorChar;
	private TabDummy					tabSearchVariable;
	private TabDummy					tabCompareVariables;
	private TabDummy					tabCompareValues; 
	private TabDummy					tabDRInstance;
	private TabDummy					tabVariable;
	private TabDummy					tabValue;
	private TabDummy					tabMapVariableInstance;
	private TabDummy					tabMapVariableAttribute;
	private TabDummy					tabMapVariableChar;

	
	/* List of declared Keywords */
	private ArrayList<Keyword>				keywords		= new ArrayList<Keyword>(); // unused yet, supports vocabulary info
	
		
	/* List of Removed Objects */
	private ArrayList<Object>				recycle_bin		= new ArrayList<Object>();
	
	/*
	 *	Constructor
	 */
	/**
	 * 
	 */
	public ProContent() {
		/* TODO */
	}
		
	/*
	 *	Methods
	 */
	/* Concept */
//	/**
//	 * @param concept
//	 */
//	public void setConcept(Concept concept) {
//		this.concept = concept;
//	}
//	
//	/**
//	 * @return
//	 */
//	public Concept getConcept() {
//		return concept;
//	}
	
	/* Measurement */
	/**
	 * @param measurement
	 */
	public void setMeasurement(Measurement measurement) {
		this.measurement = measurement;
	}
	
	/**
	 * @return
	 */
	public Measurement getMeasurement() {
		return measurement;
	}
		
//	/* Literature */
//	/**
//	 * @param literatures
//	 */
//	public void setLiteratures(ArrayList<Literature> literatures) {
//		this.literatures = literatures;
//	}
//	
//	/**
//	 * @param literature
//	 */
//	public void addLiterature(Literature literature) {
//		literatures.add(literature);
//	}
//	
//	/**
//	 * @return
//	 */
//	public ArrayList<Literature> getLiteratures() {
//		return literatures;
//	}
//	
//	/**
//	 * @param index
//	 * @return
//	 */
//	public Literature getLiteratureByIndex(int index) {
//		Literature result = null;
//		
//		try {
//			result = literatures.get(index);
//		} catch (IndexOutOfBoundsException e) {
//	    	/* DEMO ONLY */
//	    	System.err.println("SQLException: " + e.getMessage());
//	    	e.printStackTrace();
//		}
//		
//		return result;
//	}
//	
//	/**
//	 * @param literature_id
//	 * @return
//	 */
//	public Literature getLiteratureByID(int literature_id) {
//		Literature result = null;
//		
//		Iterator<Literature> it_lit = literatures.iterator();
//			
//		while (it_lit.hasNext()) {
//			Literature literature = it_lit.next();
//			
//			if (literature.getEntityID() == literature_id) {
//				result = literature; break;
//			}
//		}
//		
//		return result;
//	}
	
	/*
	 *	... ProjectLayer
	 */
	/**
	 * @param layers
	 */
	public void setLayers(ArrayList<WorkStepInstance> layers) {
		this.instances = layers;
	}
	
	/**
	 * @param layer
	 */
	public void addLayer(WorkStepInstance layer) {
		this.instances.add(layer);
	}

	/**
	 * @return
	 */
	public ArrayList<WorkStepInstance> getLayers() {
		return instances;
	}
	
	/**
	 * @param type
	 * @return
	 */
	public ArrayList<WorkStepInstance> getLayers(InstanceType type) {
		ArrayList<WorkStepInstance> results = new ArrayList<WorkStepInstance>();
		
		if (instances != null) {
			Iterator<WorkStepInstance> iterator = instances.iterator();
			
			while(iterator.hasNext()) {
				WorkStepInstance lay = iterator.next();
				
				if (lay.getType().equals(type))
					results.add(lay);
			}
		}
		
		return results;
	}
	
	/**
	 * @return
	 */
	public WorkStepInstance getSetupLayer() {
		ArrayList<WorkStepInstance> insts = getLayers(InstanceType.PROJECT_SETUP);
		
		return insts.get(0);
	}
	
	/**
	 * @return
	 */
	public WorkStepInstance getConceptualLayer() {
		ArrayList<WorkStepInstance> results = this.getLayers(InstanceType.CONCEPTUAL);
		
		return (results.size() > 0 ? results.get(0) : null);
	}
	
	/**
	 * @return
	 */
	public WorkStepInstance getVisualizeLayer() {
		ArrayList<WorkStepInstance> results = this.getLayers(InstanceType.VISUALIZE);
		
		return (results.size() > 0 ? results.get(0) : null);
	}
	
	/**
	 * @param index
	 * @return
	 */
	public WorkStepInstance getLayer(int index) {
		return instances.get(index);
	}
	
	/**
	 * @param id
	 * @return
	 */
	public WorkStepInstance getLayerByID(int id) {
		if (instances != null) {
			Iterator<WorkStepInstance> iterator = instances.iterator();
							
			while(iterator.hasNext()) {
				WorkStepInstance layer = iterator.next();
					
				if (layer.getEntityID() == id)
					return layer;
			}
		}
	
		return null;
	}
	
	/**
	 * @param label
	 * @return
	 */
	public WorkStepInstance getLayer(String label) {
		if (instances != null) {
			Iterator<WorkStepInstance> iterator = instances.iterator();
			
			while(iterator.hasNext()) {
				WorkStepInstance lay = iterator.next();
				
				if (lay.getLabel().equals(label))
					return lay;
			}
		}
		
		return null;
	}
		
	/**
	 * @param layer
	 * @return
	 */
	public int getIndexInLayers(WorkStepInstance layer) {
		if ((instances != null) &&
			(layer != null))
		{
			return instances.indexOf(layer);
		}
		
		return -1;
	}
	
	/**
	 * @param label
	 * @return
	 */
	public int getIndexInLayers(String label) {
		if ((instances != null) &&
			(label != null))
		{
			return getIndexInLayers(getLayer(label));
		}
		
		return -1;
	}

	/**
	 * @param layer
	 * @return
	 */
	public boolean removeLayer(WorkStepInstance layer) {
		return instances.remove(layer);
	}
	
	/**
	 * @param index
	 * @return
	 */
	public boolean removeLayer(int index) {
		return (instances.remove(index) != null) ? true : false;
	}
	
	/**
	 * 
	 */
	public void clearLayers() {
		instances.clear();
	}
			
	/*
	 * 
	 */
	/**
	 * @param refs
	 */
	public void setInstanceRefs(ArrayList<InstanceLink> refs) {
		this.inst_links = refs;
	}

	/**
	 * @param ref
	 */
	public void addInstanceRef(InstanceLink ref) {
		this.inst_links.add(ref);
	}
	
	/**
	 * @return
	 */
	public ArrayList<InstanceLink> getRefs() {
		return inst_links;
	}
	
	/**
	 * @param ref
	 * @return
	 */
	public boolean removeRef(InstanceLink ref) {
		return inst_links.remove(ref);
	}
			
	/**
	 * @param index
	 * @return
	 */
	public InstanceLink getRef(int index) {
		return inst_links.get(index);
	}
	
	/**
	 * @param id
	 * @return
	 */
	public InstanceLink getRefByID(int id) {
		if (inst_links != null) {
			Iterator<InstanceLink> iterator = inst_links.iterator();
							
			while(iterator.hasNext()) {
				InstanceLink ref = iterator.next();
					
				if (ref.getEntityID() == id)
					return ref;
			}
		}
	
		return null;
	}
	
	/**
	 * @param instance
	 * @return
	 */
	public InstanceLink getRefByInstance(WorkStepInstance instance) {
		if (inst_links != null) {
			Iterator<InstanceLink> iterator = inst_links.iterator();
							
			while(iterator.hasNext()) {
				InstanceLink ref = iterator.next();
				
				if (ref.getInstance().getEntityID() == instance.getEntityID())
					return ref;
			}
		}
	
		return null;
	}
	
	/*
	 *	CharacteristicLink
	 */
	/*
	 * 
	 */
	/**
	 * @param links
	 */
	public void setCharacteristicLink(ArrayList<CharacteristicLink> links) {
		this.char_links = links;
	}

	/**
	 * @param link
	 */
	public void addCharacteristicLink(CharacteristicLink link) {
		this.char_links.add(link);
	}
	
	/**
	 * @return
	 */
	public ArrayList<CharacteristicLink> getCharacteristicLinks() {
		return char_links;
	}
	
	/**
	 * @param attr
	 * @return
	 */
	public ArrayList<CharacteristicLink> getCharacteristicLinksByAttribute(Attributes attr) {
		ArrayList<CharacteristicLink> charLinksByAttr = new ArrayList<CharacteristicLink>();
		
		if (char_links != null) {
			Iterator<CharacteristicLink> iterator = char_links.iterator();
							
			while(iterator.hasNext()) {
				CharacteristicLink link = iterator.next();
					
				if ((link.getAttribute().getClass().equals(attr.getClass())) &&
						(link.getAttribute().getEntityID() == attr.getEntityID()))
					charLinksByAttr.add(link);
			}
		}
	
		return charLinksByAttr;
	}
	
	/**
	 * @param characteristic
	 * @return
	 */
	public CharacteristicLink getCharacteristicLinkByCharacteristic(Characteristics characteristic) {
		if (char_links != null) {
			Iterator<CharacteristicLink> iterator = char_links.iterator();
			
			while(iterator.hasNext()) {
				CharacteristicLink link = iterator.next();
					
				if (link.getCharacteristic().equals(characteristic))
					return link;
			}			
		}
		
		return null;
	}
	
	/**
	 * @param type
	 * @return
	 */
	public ArrayList<CharacteristicLink> getCharacteristicLinksByType(CharacteristicLinkType type) {
		ArrayList<CharacteristicLink> links = new ArrayList<CharacteristicLink>();
		
		if (char_links != null) {
			Iterator<CharacteristicLink> iterator = char_links.iterator();
			
			while(iterator.hasNext()) {
				CharacteristicLink link = iterator.next();
					
				if (link.getType().equals(type))
					links.add(link);
			}			
		}
		
		return links;
	}
		
	/**
	 * @param index
	 * @return
	 */
	public CharacteristicLink getCharacteristicLink(int index) {
		return char_links.get(index);
	}
	
	/**
	 * @param id
	 * @return
	 */
	public CharacteristicLink getCharacteristicLinkByID(int id) {
		if (char_links != null) {
			Iterator<CharacteristicLink> iterator = char_links.iterator();
							
			while(iterator.hasNext()) {
				CharacteristicLink link = iterator.next();
					
				if (link.getEntityID() == id)
					return link;
			}
		}
	
		return null;
	}
	
	/**
	 * @param link
	 * @return
	 */
	public boolean removeCharacteristicLink(CharacteristicLink link) {
		return char_links.remove(link);
	}
	
	/*
	 *	... Attributes
	 */
//	/**
//	 * @param id
//	 * @param type
//	 * @return
//	 */
//	public Attributes getAttribute(int id, int type) {
//		Attributes attribute = null;
//		
//		switch (type) {
//			case 610:
//				attribute = getDimensionByID(id);
//				break;
//			case 720:
//				attribute = getIndicatorByID(id);
//				break;
//			case 800:
//				attribute = getVariableByID(id);
//				break;
//			case 500:
//				attribute = getMeasurementByID(id);
//				break;
//		}
//		return attribute;
//	}
	
	/*
	 *	... Dimension
	 */
	/**
	 * @param dimensions
	 */
	public void setDimensions(ArrayList<ConDimension> dimensions) {
		this.dimensions = dimensions;
	}
	
	/**
	 * @param dimension
	 */
	public void addDimension(ConDimension dimension) {
		this.dimensions.add(dimension);
	}

	/**
	 * @return
	 */
	public ArrayList<ConDimension> getDimensions() {
		return dimensions;
	}
	
	/**
	 * @param index
	 * @return
	 */
	public ConDimension getDimension(int index) {
		return dimensions.get(index);
	}
	
	/**
	 * @param label
	 * @return
	 */
	public ConDimension getDimension(String label) {
		if (dimensions != null) {
			Iterator<ConDimension> iterator = dimensions.iterator();
			
			while(iterator.hasNext()) {
				ConDimension dim = iterator.next();
				
				if (dim.getLabel().equals(label))
					return dim;
			}
		}
		
		return null;
	}
	
	/**
	 * @param id
	 * @return
	 */
	public ConDimension getDimensionByID(int id) {
		if (dimensions != null) {
			Iterator<ConDimension> iterator = dimensions.iterator();
			
			while(iterator.hasNext()) {
				ConDimension dim = iterator.next();
				
				if (dim.getEntityID() == (id))
					return dim;
			}
		}
		
		return null;
	}
	
	/**
	 * @param label
	 * @return
	 */
	public boolean hasDimensionNamed(String label) {
		return (getDimension(label) != null);
	}
		
	/**
	 * @param dimension
	 * @return
	 */
	public int getIndexInDimensions(ConDimension dimension) {
		if ((dimensions != null) &&
			(dimension != null))
		{
			return dimensions.indexOf(dimension);
		}
		
		return -1;
	}
	
	/**
	 * @param label
	 * @return
	 */
	public int getIndexInDimensions(String label) {
		if ((dimensions != null) &&
			(label != null))
		{
			return getIndexInDimensions(getDimension(label));
		}
		
		return -1;
	}

	/**
	 * @param dimension
	 * @return
	 */
	public boolean removeDimension(ConDimension dimension) {
		return dimensions.remove(dimension);
	}
	
	/**
	 * @param index
	 * @return
	 */
	public boolean removeDimension(int index) {
		return (dimensions.remove(index) != null) ? true : false;
	}
	
	/**
	 * 
	 */
	public void clearDimensions() {
		dimensions.clear();
	}
	
	/*
	 *	... Indicator
	 */
	/**
	 * @param indicators
	 */
	public void setIndicators(ArrayList<OperaIndicator> indicators) {
		this.indicators = indicators;
	}
	
	/**
	 * @param indicator
	 */
	public void addIndicator(OperaIndicator indicator) {
		this.indicators.add(indicator);
	}

	/**
	 * @return
	 */
	public ArrayList<OperaIndicator> getIndicators() {
		return indicators;
	}
	
	/**
	 * @param defaultLabel
	 * @return
	 */
	public ArrayList<OperaIndicator> getIndicatorsByLayer(String defaultLabel) {
		ArrayList<OperaIndicator> layer_indicators = new ArrayList<OperaIndicator>();
		
		Iterator<AttributeLink> link_iterator = getLinksByLayer(defaultLabel).iterator();
		while (link_iterator.hasNext()) {
			AttributeLink link = link_iterator.next();
			
			if (link.getAttributeLinkType().equals(AttributeLinkType.INDICATOR))
				layer_indicators.add((OperaIndicator)link.getAttribute());
		}
		
		return layer_indicators;
	}

	/**
	 * @param index
	 * @return
	 */
	public OperaIndicator getIndicator(int index) {
		return indicators.get(index);
	}
	
	/**
	 * @param label
	 * @return
	 */
	public OperaIndicator getIndicator(String label) {
		if (indicators != null) {
			Iterator<OperaIndicator> iterator = indicators.iterator();
			
			while(iterator.hasNext()) {
				OperaIndicator ind = iterator.next();
				
				if (ind.getLabel().equals(label))
					return ind;
			}
		}
		
		return null;
	}
	
	/**
	 * @param id
	 * @return
	 */
	public OperaIndicator getIndicatorByID(int id) {
		if (indicators != null) {
			Iterator<OperaIndicator> iterator = indicators.iterator();
			
			while(iterator.hasNext()) {
				OperaIndicator ind = iterator.next();
				
				if (ind.getEntityID() == (id))
					return ind;
			}
		}
		
		return null;
	}
	
	/**
	 * @param label
	 * @return
	 */
	public boolean hasIndicatorNamed(String label) {
		return (getIndicator(label) != null);
	}
		
	/**
	 * @param indicator
	 * @return
	 */
	public int getIndexInIndicators(OperaIndicator indicator) {
		if ((indicators != null) &&
			(indicator != null))
		{
			return indicators.indexOf(indicator);
		}
		
		return -1;
	}
	
	/**
	 * @param label
	 * @return
	 */
	public int getIndexInIndicators(String label) {
		if ((indicators != null) &&
			(label != null))
		{
			return getIndexInIndicators(getIndicator(label));
		}
		
		return -1;
	}
	
	/**
	 * @param indicator
	 * @return
	 */
	public boolean removeIndicator(OperaIndicator indicator) {
		return indicators.remove(indicator);
	}
	
	/**
	 * @param index
	 * @return
	 */
	public boolean removeIndicator(int index) {
		return (indicators.remove(index) != null) ? true : false;
	}
	
	/**
	 * 
	 */
	public void clearIndicators() {
		indicators.clear();
	}
	
	/*
	 *	... Variable
	 */
	/**
	 * @param variables
	 */
	public void setVariables(ArrayList<Variable> variables) {
		this.variables = variables;
	}
	
	/**
	 * @param variable
	 */
	public void addVariable(Variable variable) {
		this.variables.add(variable);
	}

	/**
	 * @return
	 */
	public ArrayList<Variable> getVariables() {
		return variables;
	}
	
	/**
	 * @return
	 */
	public ArrayList<Variable> getImportedVariables() {
		ArrayList<Variable> variables = new ArrayList<Variable>();
		
		Iterator<AttributeLink> link_iterator = this.getLinksByType(AttributeLinkType.VARIABLE).iterator();
		while (link_iterator.hasNext()) {
			AttributeLink link = link_iterator.next();
		
			boolean duplicate = false;
			for (int i = 0; i < variables.size(); i++)
				if( ((Variable)link.getAttribute()).getEntityID() == (variables.get(i).getEntityID()) )
					duplicate = true;
			
			if (!duplicate)
				variables.add((Variable)link.getAttribute());
			
			duplicate = false;
		}
		
		return variables;
	}
	
	/**
	 * @param defaultLabel
	 * @return
	 */
	public ArrayList<Variable> getVariablesByLayer(String defaultLabel) {
		ArrayList<Variable> layer_variables = new ArrayList<Variable>();
		
		Iterator<AttributeLink> link_iterator = getLinksByLayer(defaultLabel).iterator();
		while (link_iterator.hasNext()) {
			AttributeLink link = link_iterator.next();
			
			if (link.getAttributeLinkType().equals(AttributeLinkType.VARIABLE))
				layer_variables.add((Variable)link.getAttribute());
		}
		
		return layer_variables;

	}
	
	/**
	 * @param index
	 * @return
	 */
	public Variable getVariable(int index) {
		return variables.get(index);
	}
	
	/**
	 * @param label
	 * @return
	 */
	public Variable getVariable(String label) {
		if (variables != null) {
			Iterator<Variable> iterator = variables.iterator();
			
			while(iterator.hasNext()) {
				Variable var = iterator.next();
				
				if (var.getLabel().equals(label))
					return var;
			}
		}
		
		return null;
	}
	
	/**
	 * @param id
	 * @return
	 */
	public Variable getVariableByID(int id) {
		if (variables != null) {
			Iterator<Variable> iterator = variables.iterator();
			
			while(iterator.hasNext()) {
				Variable var = iterator.next();
				
				if (var.getEntityID() == (id))
					return var;
			}
		}
		
		return null;
	}
	
	/**
	 * @param label
	 * @return
	 */
	public boolean hasVariableNamed(String label) {
		return (getVariable(label) != null);
	}
		
	/**
	 * @param variable
	 * @return
	 */
	public int getIndexInVariables(Variable variable) {
		if ((variables != null) &&
			(variable != null))
		{
			return variables.indexOf(variable);
		}
		
		return -1;
	}
	
	/**
	 * @param label
	 * @return
	 */
	public int getIndexInVariables(String label) {
		if ((variables != null) &&
			(label != null))
		{
			return getIndexInVariables(getVariable(label));
		}
		
		return -1;
	}
	
	/**
	 * @param variable
	 * @return
	 */
	public boolean removeVariable(Variable variable) {
		return variables.remove(variable);
	}
	
	/**
	 * @param index
	 * @return
	 */
	public boolean removeVariable(int index) {
		return (variables.remove(index) != null) ? true : false;
	}
	
	/**
	 * 
	 */
	public void clearVariables() {
		variables.clear();
	}
				
	/*
	 *	... AttributeLink
	 */
	/**
	 * @param links
	 */
	public void setLinks(ArrayList<AttributeLink> links) {
		this.attr_links = links;
	}

	/**
	 * @param link
	 */
	public void addLink(AttributeLink link) {
		this.attr_links.add(link);
	}
	
	/**
	 * @return
	 */
	public ArrayList<AttributeLink> getLinks() {
		return attr_links;
	}
	
	/**
	 * @return
	 */
	public ArrayList<AttributeLink> getLinksByLayer() {
		ArrayList<AttributeLink> linksByLayer = new ArrayList<AttributeLink>();
		
		if (attr_links != null) {
			Iterator<AttributeLink> iterator = attr_links.iterator();
							
			while(iterator.hasNext()) {
				AttributeLink link = iterator.next();
					
				if (link.getInstance().getType().equals(InstanceType.CONCEPTUAL))
					linksByLayer.add(link);
			}
		}
	
		return linksByLayer;
	}
	
	/**
	 * @param layerName
	 * @return
	 */
	public ArrayList<AttributeLink> getLinksByLayer(String layerName) {
		ArrayList<AttributeLink> linksByLayer = new ArrayList<AttributeLink>();
		
		if (attr_links != null) {
			Iterator<AttributeLink> iterator = attr_links.iterator();
							
			while(iterator.hasNext()) {
				AttributeLink link = iterator.next();
					
				if (link.getInstance().getLabel().equals(layerName))
					linksByLayer.add(link);
			}
		}
	
		return linksByLayer;
	}
	
	/**
	 * @param layer
	 * @return
	 */
	public ArrayList<AttributeLink> getLinksByLayer(WorkStepInstance layer) {
		ArrayList<AttributeLink> linksByLayer = new ArrayList<AttributeLink>();
		
		if (attr_links != null) {
			Iterator<AttributeLink> iterator = attr_links.iterator();
							
			while(iterator.hasNext()) {
				AttributeLink link = iterator.next();
					
				if (link.getInstance().equals(layer))
					linksByLayer.add(link);
			}
		}
	
		return linksByLayer;
	}
	
	/**
	 * @param attribute
	 * @return
	 */
	public AttributeLink getLinkByAttribute(Attributes attribute) {
		if (attr_links != null) {
			Iterator<AttributeLink> iterator = attr_links.iterator();
			
			while(iterator.hasNext()) {
				AttributeLink link = iterator.next();
					
				if (link.getAttribute().equals(attribute))
					return link;
			}			
		}
		
		return null;
	}
	
	/**
	 * @param attribute
	 * @param type
	 * @return
	 */
	public AttributeLink getLinkByAttributeAndInstanceType(Attributes attribute, AttributeLinkType type) {
		ArrayList<AttributeLink> attrLinks = this.getLinksByType(type);
		
		if (attrLinks != null) {
			Iterator<AttributeLink> iterator = attrLinks.iterator();
			
			while(iterator.hasNext()) {
				AttributeLink link = iterator.next();
					
				if (link.getAttribute().equals(attribute))
					return link;
			}			
		}
		
		return null;
	}
	
	/* DataRecoding only yet 12.08.13*/
	/**
	 * @param attribute
	 * @param type
	 * @param sourceinstance
	 * @return
	 */
	public AttributeLink getLinkByAttributeAndInstanceTypeAndInstance(Attributes attribute, AttributeLinkType type, WorkStepInstance sourceinstance) {
		ArrayList<AttributeLink> attrLinks = this.getLinksByType(type);
		
		if (attrLinks != null) {
			Iterator<AttributeLink> iterator = attrLinks.iterator();
			
			while(iterator.hasNext()) {
				AttributeLink link = iterator.next();
					
				if (link.getAttribute().equals(attribute) &&
						(link.getInstance().getEntityID() == sourceinstance.getEntityID())) 
					return link;
			}			
		}
		
		return null;
	}
	
	/**
	 * @param type
	 * @return
	 */
	public ArrayList<AttributeLink> getLinksByType(AttributeLinkType type) {
		ArrayList<AttributeLink> links = new ArrayList<AttributeLink>();
		
		if (attr_links != null) {
			Iterator<AttributeLink> iterator = attr_links.iterator();
			
			while(iterator.hasNext()) {
				AttributeLink link = iterator.next();
					
				if (link.getAttributeLinkType().equals(type))
					links.add(link);
			}			
		}
		
		return links;
	}
	
	/**
	 * @param index
	 * @return
	 */
	public AttributeLink getLink(int index) {
		return attr_links.get(index);
	}
	
	/**
	 * @param id
	 * @return
	 */
	public AttributeLink getLinkByID(int id) {
		if (attr_links != null) {
			Iterator<AttributeLink> iterator = attr_links.iterator();
							
			while(iterator.hasNext()) {
				AttributeLink link = iterator.next();
					
				if (link.getEntityID() == id)
					return link;
			}
		}
	
		return null;
	}
			
	/**
	 * @param link
	 * @return
	 */
	public int getIndexInLinks(AttributeLink link) {
		if ((attr_links != null) &&
			(link != null))
		{
			return attr_links.indexOf(link);
		}
		
		return -1;
	}
		
	/**
	 * @param link
	 * @return
	 */
	public boolean removeLink(AttributeLink link) {
		return attr_links.remove(link);
	}
	
	/**
	 * @param index
	 * @return
	 */
	public boolean removeLink(int index) {
		return (attr_links.remove(index) != null) ? true : false;
	}
	
	/**
	 * 
	 */
	public void clearLinks() {
		attr_links.clear();
	}
					
	/*
	 *	... Characteristics
	 */
	/* unused */
	/**
	 * @param id
	 * @param type
	 * @return
	 */
	public Characteristics getCharacteristic(int id, int type) {
		Characteristics characteristic = null;
		
		switch (type) {
			case 620:
				characteristic = getSpecificationByID(id);
				break;
			case 821:
				characteristic = getValueByID(id);
				break;
//			case 9860:
//				characteristic = getAlgorithmByID(id);
//				break;
			case 9888:
				characteristic = getCategoryByID(id);
				break;
		}
		return characteristic;
	}
	
	/*
	 *	... Specification
	 */
	/**
	 * @param specifications
	 */
	public void setSpecifications(ArrayList<ConSpecification> specifications) {
		this.specifications = specifications;
	}
	
	/**
	 * @param specification
	 */
	public void addSpecification(ConSpecification specification) {
		this.specifications.add(specification);
	}

	/**
	 * @return
	 */
	public ArrayList<ConSpecification> getSpecifications() {
		return specifications;
	}
	
	/**
	 * @param index
	 * @return
	 */
	public ConSpecification getSpecification(int index) {
		return specifications.get(index);
	}
	
	/**
	 * @param label
	 * @return
	 */
	public ConSpecification getSpecification(String label) {
		if (specifications != null) {
			Iterator<ConSpecification> iterator = specifications.iterator();
			
			while(iterator.hasNext()) {
				ConSpecification spec = iterator.next();
				
				if (spec.getLabel().equals(label))
					return spec;
			}
		}
		
		return null;
	}
	
	/**
	 * @param index
	 * @return
	 */
	public ConSpecification getSpecificationByID(int index) {
		if (specifications != null) {
			Iterator<ConSpecification> iterator = specifications.iterator();
			
			while(iterator.hasNext()) {
				ConSpecification spec = iterator.next();
				
				if (spec.getEntityID() == index)
					return spec;
			}
		}
		
		return null;
	}
		
	/**
	 * @param specification
	 * @return
	 */
	public int getIndexInSpecifications(ConSpecification specification) {
		if ((specifications != null) &&
			(specification != null))
		{
			return specifications.indexOf(specification);
		}
		
		return -1;
	}
	
	/**
	 * @param label
	 * @return
	 */
	public int getIndexInSpecifications(String label) {
		if ((specifications != null) &&
			(label != null))
		{
			return getIndexInSpecifications(getSpecification(label));
		}
		
		return -1;
	}

	/**
	 * @param specification
	 * @return
	 */
	public boolean removeSpecification(ConSpecification specification) {
		return specifications.remove(specification);
	}
	
	/**
	 * @param index
	 * @return
	 */
	public boolean removeSpecification(int index) {
		return (specifications.remove(index) != null) ? true : false;
	}
	
	/**
	 * 
	 */
	public void clearSpecifications() {
		specifications.clear();
	}
	
	/*
	 *	... Prescription
	 */
	/**
	 * @param prescriptions
	 */
	public void setPrescriptions(ArrayList<OperaPrescription> prescriptions) {
		this.prescriptions = prescriptions;
	}
	
	/**
	 * @param prescription
	 */
	public void addPrescription(OperaPrescription prescription) {
		this.prescriptions.add(prescription);
	}

	/**
	 * @return
	 */
	public ArrayList<OperaPrescription> getPrescriptions() {
		return prescriptions;
	}
	
	/**
	 * @param index
	 * @return
	 */
	public OperaPrescription getPrescription(int index) {
		return prescriptions.get(index);
	}
	
	/**
	 * @param label
	 * @return
	 */
	public OperaPrescription getPrescription(String label) {
		if (prescriptions != null) {
			Iterator<OperaPrescription> iterator = prescriptions.iterator();
			
			while(iterator.hasNext()) {
				OperaPrescription prescription = iterator.next();
				
				if (prescription.getLabel().equals(label))
					return prescription;
			}
		}
		
		return null;
	}
	
	/**
	 * @param index
	 * @return
	 */
	public OperaPrescription getPrescriptionByID(int index) {
		if (prescriptions != null) {
			Iterator<OperaPrescription> iterator = prescriptions.iterator();
			
			while(iterator.hasNext()) {
				OperaPrescription pre = iterator.next();
				
				if (pre.getEntityID() == index)
					return pre;
			}
		}
		
		return null;
	}
		
	/**
	 * @param prescription
	 * @return
	 */
	public int getIndexInPrescriptions(OperaPrescription prescription) {
		if ((prescriptions != null) &&
			(prescription != null))
		{
			return prescriptions.indexOf(prescription);
		}
		
		return -1;
	}
	
	/**
	 * @param label
	 * @return
	 */
	public int getIndexInPrescriptions(String label) {
		if ((prescriptions != null) &&
			(label != null))
		{
			return getIndexInPrescriptions(getPrescription(label));
		}
		
		return -1;
	}

	/**
	 * @param prescription
	 * @return
	 */
	public boolean removePrescription(OperaPrescription prescription) {
		return prescriptions.remove(prescription);
	}
	
	/**
	 * @param index
	 * @return
	 */
	public boolean removePrescription(int index) {
		return (prescriptions.remove(index) != null) ? true : false;
	}
	
	/**
	 * 
	 */
	public void clearPrescriptions() {
		prescriptions.clear();
	}
	
	/*
	 *	... Value
	 */
	/**
	 * @param values
	 */
	public void setValues(ArrayList<Value> values) {
		this.values = values;
	}
	
	/**
	 * @param value
	 */
	public void addValue(Value value) {
		this.values.add(value);
	}

	/**
	 * @return
	 */
	public ArrayList<Value> getValues() {
		return values;
	}
	
	/**
	 * @param index
	 * @return
	 */
	public Value getValue(int index) {
		return values.get(index);
	}
	
	/**
	 * @param label
	 * @return
	 */
	public Value getValue(String label) {
		if (values != null) {
			Iterator<Value> iterator = values.iterator();
			
			while(iterator.hasNext()) {
				Value value = iterator.next();
				
				if (value.getLabel().equals(label))
					return value;
			}
		}
		
		return null;
	}
	
	/**
	 * @param index
	 * @return
	 */
	public Value getValueByID(int index) {
		if (values != null) {
			Iterator<Value> iterator = values.iterator();
			
			while(iterator.hasNext()) {
				Value val = iterator.next();
				
				if (val.getEntityID() == index)
					return val;
			}
		}
		
		return null;
	}
		
	/**
	 * @param value
	 * @return
	 */
	public int getIndexInValues(Value value) {
		if ((values != null) &&
			(value != null))
		{
			return values.indexOf(value);
		}
		
		return -1;
	}
	
	/**
	 * @param label
	 * @return
	 */
	public int getIndexInValues(String label) {
		if ((values != null) &&
			(label != null))
		{
			return getIndexInValues(getValue(label));
		}
		
		return -1;
	}

	/**
	 * @param value
	 * @return
	 */
	public boolean removeValue(Value value) {
		return values.remove(value);
	}
	
	/**
	 * @param index
	 * @return
	 */
	public boolean removeValue(int index) {
		return (values.remove(index) != null) ? true : false;
	}
	
	/**
	 * 
	 */
	public void clearValues() {
		values.clear();
	}
		
	/*
	 *	... Category 
	 */
	/**
	 * @param categories
	 */
	public void setCategories(ArrayList<Category> categories) {
		this.categories = categories;
	}
	
	/**
	 * @param category
	 */
	public void addCategory(Category category) {
		this.categories.add(category);
	}

	/**
	 * @return
	 */
	public ArrayList<Category> getCategories() {
		return categories;
	}
	
	/**
	 * @param index
	 * @return
	 */
	public Category getCategory(int index) {
		return categories.get(index);
	}
	
	/**
	 * @param label
	 * @return
	 */
	public Category getCategory(String label) {
		if (categories != null) {
			Iterator<Category> iterator = categories.iterator();
			
			while(iterator.hasNext()) {
				Category cat = iterator.next();
				
				if (cat.getLabel().equals(label))
					return cat;
			}
		}
		
		return null;
	}
	
	/**
	 * @param id
	 * @return
	 */
	public Category getCategoryByID(int id) {
		if (categories != null) {
			Iterator<Category> iterator = categories.iterator();
			
			while(iterator.hasNext()) {
				Category cat = iterator.next();
				
				if (cat.getEntityID() == (id))
					return cat;
			}
		}
		
		return null;
	}
	
	/**
	 * @param label
	 * @return
	 */
	public boolean hasCategoryNamed(String label) {
		return (getCategory(label) != null);
	}
		
	/**
	 * @param category
	 * @return
	 */
	public int getIndexInCategories(Category category) {
		if ((categories != null) &&
			(category != null))
		{
			return categories.indexOf(category);
		}
		
		return -1;
	}
	
	/**
	 * @param label
	 * @return
	 */
	public int getIndexInCategories(String label) {
		if ((categories != null) &&
			(label != null))
		{
			return getIndexInCategories(getCategory(label));
		}
		
		return -1;
	}

	/**
	 * @param category
	 * @return
	 */
	public boolean removeCategory(Category category) {
		return categories.remove(category);
	}
	
	/**
	 * @param index
	 * @return
	 */
	public boolean removeCategory(int index) {
		return (categories.remove(index) != null) ? true : false;
	}
	
	/**
	 * 
	 */
	public void clearCategories() {
		categories.clear();
	}

	/*
	 *	... InstanceMap
	 */
	/**
	 * @param maps
	 */
	public void setInstanceMaps(ArrayList<InstanceMap> maps) {
		this.inst_maps = maps;
	}
	
	/**
	 * @param map
	 */
	public void addInstanceMap(InstanceMap map) {
		this.inst_maps.add(map);
	}
	
	/**
	 * @return
	 */
	public ArrayList<InstanceMap> getInstanceMaps() {
		return inst_maps;
	}
		
	/**
	 * @param instance_type
	 * @return
	 */
	public ArrayList<InstanceMap> getInstanceMapsByType(InstanceMapType instance_type) {
		ArrayList<InstanceMap> mapsByLayer = new ArrayList<InstanceMap>();
		
		if (inst_maps != null) {
			Iterator<InstanceMap> iterator = inst_maps.iterator();
							
			while(iterator.hasNext()) {
				InstanceMap map = iterator.next();
					
				if (map.getType().equals(instance_type))
					mapsByLayer.add(map);
			}
		}
	
		return mapsByLayer;
	}
	
	/**
	 * @param id
	 * @return
	 */
	public InstanceMap getInstanceMapByID(int id) {
		if (inst_maps != null) {
			Iterator<InstanceMap> iterator = inst_maps.iterator();
							
			while(iterator.hasNext()) {
				InstanceMap map = iterator.next();
					
				if (map.getEntityID() == id)
					return map;
			}
		}
	
		return null;
	}
	
	/**
	 * @param link
	 * @return
	 */
	public InstanceMap getInstanceMapByInstance(InstanceLink link) {
		if (inst_maps != null) {
			Iterator<InstanceMap> iterator = inst_maps.iterator();
							
			while(iterator.hasNext()) {
				InstanceMap map = iterator.next();				
				
				if (map.getSourceInstance().equals(link))
					return map;
			}
		}
	
		return null;
	}
	
	/**
	 * @param map
	 * @return
	 */
	public boolean removeInstanceMap(InstanceMap map) {
		return inst_maps.remove(map);
	}
	
	/*
	 *	... CharacteristicMap
	 */
	/**
	 * @param maps
	 */
	public void setCharacteristicMaps(ArrayList<CharacteristicMap> maps) {
		this.char_maps = maps;
	}
	
	/**
	 * @param map
	 */
	public void addCharacteristicMap(CharacteristicMap map) {
		this.char_maps.add(map);
	}
	
	/**
	 * @return
	 */
	public ArrayList<CharacteristicMap> getCharacteristicMaps() {
		return char_maps;
	}
	
	/**
	 * @param id
	 * @return
	 */
	public CharacteristicMap getCharacteristicMapByID(int id) {
		if (char_maps != null) {
			Iterator<CharacteristicMap> iterator = char_maps.iterator();
							
			while(iterator.hasNext()) {
				CharacteristicMap map = iterator.next();
					
				if (map.getEntityID() == id)
					return map;
			}
		}
	
		return null;
	}
	
	/**
	 * @param link
	 * @return
	 */
	public CharacteristicMap getCharacteristicMapByCharacteristic(CharacteristicLink link) {
		if (char_maps != null) {
			Iterator<CharacteristicMap> iterator = char_maps.iterator();
							
			while(iterator.hasNext()) {
				CharacteristicMap map = iterator.next();
					
				if (map.getSourceCharacteristic().equals(link))
					return map;
			}
		}
	
		return null;
	}
	
	/**
	 * @param iterCharLink
	 * @return
	 */
	public CharacteristicMap getCharMapBySourceAttribute(
			CharacteristicLink iterCharLink) {
		return getCharacteristicMapByCharacteristic(iterCharLink);		
	}
		
	/**
	 * @param characteristic_type
	 * @return
	 */
	public ArrayList<CharacteristicMap> getCharacteristicMapsByType(CharacteristicMapType characteristic_type) {
		ArrayList<CharacteristicMap> mapsByLayer = new ArrayList<CharacteristicMap>();
		
		if (char_maps != null) {
			Iterator<CharacteristicMap> iterator = char_maps.iterator();
							
			while(iterator.hasNext()) {
				CharacteristicMap map = iterator.next();
				
				if (map.getType().equals(characteristic_type))
					mapsByLayer.add(map);
			}
		}
	
		return mapsByLayer;
	}
	
	/**
	 * @param map
	 * @return
	 */
	public boolean removeCharacteristicMap(CharacteristicMap map) {
		return char_maps.remove(map);
	}
	
	/*
	 *	... AttributeMap
	 */
	/**
	 * @param maps
	 */
	public void setMaps(ArrayList<AttributeMap> maps) {
		this.attr_maps = maps;
	}

	/**
	 * @param map
	 */
	public void addMap(AttributeMap map) {
//		Integer.toHexString(System.identityHashCode(attr_maps))
		this.attr_maps.add(map);
	}
	
	/**
	 * @return
	 */
	public ArrayList<AttributeMap> getMaps() {
		return attr_maps;
	}
	
	/**
	 * @param iterAttrLink
	 * @return
	 */
	public AttributeMap getMapBySourceAttribute(AttributeLink iterAttrLink) {
		if (attr_maps != null) {
			Iterator<AttributeMap> iterator = attr_maps.iterator();
							
			while(iterator.hasNext()) {
				AttributeMap map = iterator.next();
				
				if (map.getSourceAttribute() instanceof AttributeLink)
					if (map.getSourceAttribute().getEntityID() == iterAttrLink.getEntityID())
						return map;
			}
		}
	
		return null;
	}
		
	/**
	 * @param mapType
	 * @return
	 */
	public ArrayList<AttributeMap> getMapsByType(AttributeMapType mapType) {
		ArrayList<AttributeMap> mapsByType = new ArrayList<AttributeMap>();
		
		if (attr_maps != null) {
			Iterator<AttributeMap> iterator = attr_maps.iterator();
							
			while(iterator.hasNext()) {
				AttributeMap map = iterator.next();
					
				if (map.getAttributeMapType().equals(mapType))
					mapsByType.add(map);
			}
		}
	
		return mapsByType;
	}
		
	/**
	 * @param index
	 * @return
	 */
	public AttributeMap getMap(int index) {
		return attr_maps.get(index);
	}
	
	/**
	 * @param id
	 * @return
	 */
	public AttributeMap getMapByID(int id) {
		if (attr_maps != null) {
			Iterator<AttributeMap> iterator = attr_maps.iterator();
							
			while(iterator.hasNext()) {
				AttributeMap map = iterator.next();
					
				if (map.getEntityID() == id)
					return map;
			}
		}
	
		return null;
	}
	
	/**
	 * @param link
	 * @return
	 */
	public AttributeMap getAttributeMapByAttribute(AttributeLink link) {
		if (attr_maps != null) {
			Iterator<AttributeMap> iterator = attr_maps.iterator();
							
			while(iterator.hasNext()) {
				AttributeMap map = iterator.next();
					
				if (map.getSourceAttribute().equals(link))
					return map;
			}
		}
	
		return null;
	}
								
	/**
	 * @param map
	 * @return
	 */
	public int getIndexInMaps(AttributeMap map) {
		if ((attr_maps != null) &&
			(map != null))
		{
			return attr_maps.indexOf(map);
		}
		
		return -1;
	}
		
	/**
	 * @param map
	 * @return
	 */
	public boolean removeMap(AttributeMap map) {
		return attr_maps.remove(map);
	}
	
	/**
	 * @param index
	 * @return
	 */
	public boolean removeMap(int index) {
		return (attr_maps.remove(index) != null) ? true : false;
	}
	
	/**
	 * 
	 */
	public void clearMaps() {
		attr_maps.clear();
	}
	
	/*
	 *	... AttributeComp
	 */
	/**
	 * @param comps
	 */
	public void setComps(ArrayList<AttributeComp> comps) {
		this.attr_comps = comps;
	}

	/**
	 * @param comp
	 */
	public void addComp(AttributeComp comp) {
		this.attr_comps.add(comp);
	}
	
	/**
	 * @return
	 */
	public ArrayList<AttributeComp> getComps() {
		return attr_comps;
	}
	
	/**
	 * @param comp
	 * @return
	 */
	public boolean removeComp(AttributeComp comp) {
		return attr_comps.remove(comp);
	}
					
	/*
	 *	... Keyword
	 */
	/**
	 * @param keywords
	 */
	public void setKeywords(ArrayList<Keyword> keywords) {
		this.keywords = keywords;
	}
	
	/**
	 * @param keyword
	 */
	public void addKeyword(Keyword keyword) {
		this.keywords.add(keyword);
	}

	/**
	 * @return
	 */
	public ArrayList<Keyword> getKeywords() {
		return keywords;
	}
	
	/**
	 * @param index
	 * @return
	 */
	public Keyword getKeyword(int index) {
		return keywords.get(index);
	}
	
	/**
	 * @param keyword
	 * @return
	 */
	public Keyword getKeyword(String keyword) {
		if (keywords != null) {
			Iterator<Keyword> iterator = keywords.iterator();
			
			while(iterator.hasNext()) {
				Keyword key = iterator.next();
				
				if (key.getKeyword().equals(keyword))
					return key;
			}
		}
		
		return null;
	}
	
	/**
	 * @param index
	 * @return
	 */
	public Keyword getKeywordByID(int index) {
		if (keywords != null) {
			Iterator<Keyword> iterator = keywords.iterator();
			
			while(iterator.hasNext()) {
				Keyword key = iterator.next();
				
				if (key.getEntityID() == index)
					return key;
			}
		}
		
		return null;
	}
		
	/**
	 * @param keyword
	 * @return
	 */
	public int getIndexInKeywords(Keyword keyword) {
		if ((keywords != null) &&
			(keyword != null))
		{
			return keywords.indexOf(keyword);
		}
		
		return -1;
	}
	
	/**
	 * @param label
	 * @return
	 */
	public int getIndexInKeywords(String label) {
		if ((keywords != null) &&
			(label != null))
		{
			return getIndexInKeywords(getKeyword(label));
		}
		
		return -1;
	}

	/**
	 * @param keyword
	 * @return
	 */
	public boolean removeKeyword(Keyword keyword) {
		return keywords.remove(keyword);
	}
	
	/**
	 * @param index
	 * @return
	 */
	public boolean removeKeyword(int index) {
		return (keywords.remove(index) != null) ? true : false;
	}
	
	/**
	 * 
	 */
	public void clearKeywords() {
		keywords.clear();
	}
	
	
	/*
	 *	... Measurement
	 */
//	/**
//	 * @param measurements
//	 */
//	public void setMeasurements(ArrayList<Measurement> measurements) {
//		this.measurements = measurements;
//	}
//	
//	/**
//	 * @param measurement
//	 */
//	public void addMeasurement(Measurement measurement) {
//		this.measurements.add(measurement);
//	}
//
//	/**
//	 * @return
//	 */
//	public ArrayList<Measurement> getMeasurements() {
//		return measurements;
//	}
//	
//	/**
//	 * @param index
//	 * @return
//	 */
//	public Measurement getMeasurement(int index) {
//		return measurements.get(index);
//	}
//	
//	/**
//	 * @param label
//	 * @return
//	 */
//	public Measurement getMeasurement(String label) {
//		if (measurements != null) {
//			Iterator<Measurement> iterator = measurements.iterator();
//			
//			while(iterator.hasNext()) {
//				Measurement mea = iterator.next();
//				
//				if (mea.getName().equals(label))
//					return mea;
//			}
//		}
//		
//		return null;
//	}
//	
//	/**
//	 * @param id
//	 * @return
//	 */
//	public Measurement getMeasurementByID(int id) {
//		if (measurements != null) {
//			Iterator<Measurement> iterator = measurements.iterator();
//			
//			while(iterator.hasNext()) {
//				Measurement mea = iterator.next();
//				
//				if (mea.getEntityID() == (id))
//					return mea;
//			}
//		}
//		
//		return null;
//	}
//		
//	/**
//	 * @param measurement
//	 * @return
//	 */
//	public int getIndexInMeasurements(Measurement measurement) {
//		if ((measurements != null) &&
//			(measurement != null))
//		{
//			return measurements.indexOf(measurement);
//		}
//		
//		return -1;
//	}
//	
//	/**
//	 * @param label
//	 * @return
//	 */
//	public int getIndexInMeasurements(String label) {
//		if ((measurements != null) &&
//			(label != null))
//		{
//			return getIndexInMeasurements(getMeasurement(label));
//		}
//		
//		return -1;
//	}
//
//	/**
//	 * @param measurement
//	 * @return
//	 */
//	public boolean removeMeasurement(Measurement measurement) {
//		return measurements.remove(measurement);
//	}
//	
//	/**
//	 * @param index
//	 * @return
//	 */
//	public boolean removeMeasurement(int index) {
//		return (measurements.remove(index) != null) ? true : false;
//	}
//	
//	/**
//	 * 
//	 */
//	public void clearMeasurements() {
//		measurements.clear();
//	}
		
	/*
	 *	... RecycleBin
	 */
	/**
	 * @param garbage
	 */
	public void fillRecycleBin(ArrayList<Object> garbage) {
		recycle_bin = garbage;
	}
	
	/**
	 * @param garbage
	 */
	public void addToRecycleBin(Object garbage) {
		recycle_bin.add(garbage);
	}

	/**
	 * @return
	 */
	public ArrayList<Object> getAllGarbage() {
		return recycle_bin;
	}
		
	/**
	 * @param index
	 * @return
	 */
	public Object getGarbage(int index) {
		return recycle_bin.get(index);
	}
			
	/**
	 * @param garbage
	 * @return
	 */
	public int getIndexInRecycleBin(Object garbage) {
		if ((recycle_bin != null) &&
				(garbage != null))
			return recycle_bin.indexOf(garbage);
		
		return -1;
	}
	
	/**
	 * @param garbage
	 * @return
	 */
	public boolean removeGarbage(Object garbage) {
		return recycle_bin.remove(garbage);
	}
	
	/**
	 * @param index
	 * @return
	 */
	public boolean removeGarbage(int index) {
		return (recycle_bin.remove(index) != null) ? true : false;
	}
	
	/**
	 * @param connection
	 */
	private void emptyRecycleBin(Connection connection) {
		if (recycle_bin != null) {
			Iterator<Object> iterator = recycle_bin.iterator();
			
			while(iterator.hasNext()) {
				Object obj = iterator.next();
				
				if (obj instanceof ConDimension)
					((ConDimension)obj).entityRemove(connection);
				
				if (obj instanceof Category)
					((Category)obj).entityRemove(connection);
				
				if (obj instanceof OperaIndicator)
					((OperaIndicator)obj).entityRemove(connection);
				
				if (obj instanceof Variable)
					((Variable)obj).entityRemove(connection);
				
//				if (obj instanceof LabelRef)
//					((LabelRef)obj).entityRemove(connection);
				
//				if (obj instanceof Label)
//					((Label)obj).entityRemove(connection);
				
//				if (obj instanceof MultLangText)
//					((MultLangText)obj).entityRemove(connection);
				
				if (obj instanceof ConSpecification)
					((ConSpecification)obj).entityRemove(connection);
				
				if (obj instanceof Keyword)
					((Keyword)obj).entityRemove(connection);
				
//				if (obj instanceof Author)
//					((Author)obj).entityRemove(connection);
				if (obj instanceof Person)
					((Person)obj).entityRemove(connection);
				
				if (obj instanceof AttributeMap)
					((AttributeMap)obj).entityRemove(connection);
				
//				if (obj instanceof BiasMetadata)
//					((BiasMetadata)obj).entityRemove(connection);  
				
				if (obj instanceof Value)
					((Value)obj).entityRemove(connection);
				
//				if (obj instanceof ValueRef)
//					((ValueRef)obj).entityRemove(connection);
				
				if (obj instanceof WorkStepInstance)
					((WorkStepInstance)obj).entityRemove(connection);
				
				if (obj instanceof InstanceLink)
					((InstanceLink)obj).entityRemove(connection);
				
				if (obj instanceof InstanceMap)
					((InstanceMap)obj).entityRemove(connection);
				
				if (obj instanceof CharacteristicMap)
					((CharacteristicMap)obj).entityRemove(connection);
				
				if (obj instanceof AttributeLink)
					((AttributeLink)obj).entityRemove(connection);
			}
			
			clearRecycleBin();
		}
	}
	
	/**
	 * 
	 */
	public void clearRecycleBin() {
		recycle_bin.clear();
	}
	
	/**
	 * 
	 */
	public void clearAllContent() {
//		clearMeasurements();
		clearDimensions();
		clearIndicators();
		clearVariables();
		clearCategories();
		clearSpecifications();
		clearPrescriptions();
		clearValues();
//		clearQuestions();
//		clearAlgorithms();
		clearKeywords();
		clearLayers();
//		clearBiasMetadata();
		
		clearRecycleBin();
		
		clearLinks();	
		clearMaps();	
	}
	
	/**
	 * @param project
	 * @param connection
	 * @return
	 */
	public boolean entityLoad(Project project, Connection connection) {

		boolean loadStatus = true;
		
//		java.sql.Statement stmt;
//		ResultSet rSet;
		
		if (connection.equals(null)) {
			loadStatus = false;
			JOptionPane.showMessageDialog(
					null, "No Connection to DataBase!", "Error:", JOptionPane.ERROR_MESSAGE);
							
			/* DEMO ONLY */
			System.err.println("Error: No Connection to DataBase!");
			
			return loadStatus;
		}
		
		try {
			if(!connection.isClosed()) {
//				stmt = connection.createStatement();
					
//				/*
//				 *	Concept
//				 */
//				String sqlQuery = "SELECT c.concept " +
//					"FROM concept_refs c " +
//					"WHERE c.entity=\"" + project.getEntityType().getID() +"\" " +
//						"AND c.entry=\"" + project.getEntityID() + "\" ";	
//				
//				rSet = stmt.executeQuery( sqlQuery );
//					
//				while (rSet.next()) {
//					setConcept(new Concept());
//					getConcept().setEntityID(rSet.getInt("concept"));					
//					if (loadStatus)
//						loadStatus = getConcept().entityLoad(connection);
//				}
				
				/* Handle Tab Comments */
				if (loadStatus) {
					getTabProject().setEntityID(project.getEntityID());
					loadStatus = getTabProject().entityLoad(connection);
				}
				
				if (loadStatus) {
					getTabConcept().setEntityID(project.getEntityID());
					loadStatus = getTabConcept().entityLoad(connection);
				}
				
				if (loadStatus) {
					getTabLiterature().setEntityID(project.getEntityID());
					loadStatus = getTabLiterature().entityLoad(connection);
				}
				
				if (loadStatus) {
					getTabMeasurement().setEntityID(project.getEntityID());
					loadStatus = getTabMeasurement().entityLoad(connection);
				}
				
				if (loadStatus) {
					getTabDimension().setEntityID(project.getEntityID());
					loadStatus = getTabDimension().entityLoad(connection);
				}
				
				if (loadStatus) {
					getTabSpecification().setEntityID(project.getEntityID());
					loadStatus = getTabSpecification().entityLoad(connection);
				}
				
				if (loadStatus) {
					getTabMapDimensionInstance().setEntityID(project.getEntityID());
					loadStatus = getTabMapDimensionInstance().entityLoad(connection);
				}
				
				if (loadStatus) {
					getTabMapDimensionAttribute().setEntityID(project.getEntityID());
					loadStatus = getTabMapDimensionAttribute().entityLoad(connection);
				}
				
				if (loadStatus) {
					getTabMapDimensionChar().setEntityID(project.getEntityID());
					loadStatus = getTabMapDimensionChar().entityLoad(connection);
				}
				
				if (loadStatus) {
					getTabOSInstance().setEntityID(project.getEntityID());
					loadStatus = getTabOSInstance().entityLoad(connection);
				}
				
				if (loadStatus) {
					getTabIndicator().setEntityID(project.getEntityID());
					loadStatus = getTabIndicator().entityLoad(connection);
				}
				
				if (loadStatus) {
					getTabPrescription().setEntityID(project.getEntityID());
					loadStatus = getTabPrescription().entityLoad(connection);
				}
				
				if (loadStatus) {
					getTabMapIndicatorInstance().setEntityID(project.getEntityID());
					loadStatus = getTabMapIndicatorInstance().entityLoad(connection);
				}
				
				if (loadStatus) {
					getTabMapIndicatorAttribute().setEntityID(project.getEntityID());
					loadStatus = getTabMapIndicatorAttribute().entityLoad(connection);
				}
				
				if (loadStatus) {
					getTabMapIndicatorChar().setEntityID(project.getEntityID());
					loadStatus = getTabMapIndicatorChar().entityLoad(connection);
				}
				
				if (loadStatus) {
					getTabSearchVariable().setEntityID(project.getEntityID());
					loadStatus = getTabSearchVariable().entityLoad(connection);
				}
				
				if (loadStatus) {
					getTabCompareVariables().setEntityID(project.getEntityID());
					loadStatus = getTabCompareVariables().entityLoad(connection);
				}
				
				if (loadStatus) {
					getTabCompareValues().setEntityID(project.getEntityID());
					loadStatus = getTabCompareValues().entityLoad(connection);
				}
				
				if (loadStatus) {
					getTabDRInstance().setEntityID(project.getEntityID());
					loadStatus = getTabDRInstance().entityLoad(connection);
				}
				
				if (loadStatus) {
					getTabVariable().setEntityID(project.getEntityID());
					loadStatus = getTabVariable().entityLoad(connection);
				}
				
				if (loadStatus) {
					getTabValue().setEntityID(project.getEntityID());
					loadStatus = getTabValue().entityLoad(connection);
				}
				
				if (loadStatus) {
					getTabMapVariableInstance().setEntityID(project.getEntityID());
					loadStatus = getTabMapVariableInstance().entityLoad(connection);
				}
				
				if (loadStatus) {
					getTabMapVariableAttribute().setEntityID(project.getEntityID());
					loadStatus = getTabMapVariableAttribute().entityLoad(connection);
				}
				
				if (loadStatus) {
					getTabMapVariableChar().setEntityID(project.getEntityID());
					loadStatus = getTabMapVariableChar().entityLoad(connection);
				}
				
								
//				if (loadStatus) {
//					LiteratureRef ref_lit = new LiteratureRef();
//					ref_lit.setReferenced(project);
//							
//					ArrayList<Integer> list = ref_lit.loadReferences(connection);
//					Iterator<Integer> it_lit = list.iterator();
//						
//					while (it_lit.hasNext()) {
//						int id = it_lit.next();
//							
//						Literature literature = new Literature();
//						literature.setEntityID(id);
//
//						if (loadStatus)
//							loadStatus = literature.entityLoad(connection);
//							
//						if (loadStatus) 
//							if (literature != null)
//								this.addLiterature(literature);					
//					}
//				}
				
				clearLayers();
				clearLinks();
				clearMaps();
				
				this.inst_links.clear();
				this.instances.clear();
				
				/*
				 *	InstanceLink, WorkStepInstance
				 */
				InstanceLink		instLink	= new InstanceLink(project);
				WorkStepInstance	instance;
				
				ArrayList<Integer>	instlist		= instLink.loadLinks(connection);
				
				if (!instlist.isEmpty()) {
					Iterator<Integer> iterator = instlist.iterator();
					
					while (iterator.hasNext()) {
						Integer id = iterator.next();
						
						instLink = new InstanceLink();
						instLink.setProject(project);
						instLink.setEntityID(id);
						if (loadStatus)
							loadStatus = instLink.entityLoad(connection);
						
						instance = new WorkStepInstance();
						instance.setEntityID(instLink.getDsInstanceID());						
						if (loadStatus)
							loadStatus = instance.entityLoad(connection);
						
						if (loadStatus) {
							addLayer(instance);
							
							instLink.setInstance(instance);
							addInstanceRef(instLink);
						}
					}
				}
				
				/*
				 *	AttributeLink, Attributes (Measurement, ConDimension, OperaIndicator, Variable)
				 */
				AttributeLink		attrLink;
				Attributes			attribute = null;
				
				Iterator<WorkStepInstance> instIter =	instances.iterator();
				while (instIter.hasNext()) {
					WorkStepInstance iterInst = instIter.next();
					
					attrLink = new AttributeLink(iterInst);
					ArrayList<Integer>	attrlist	= attrLink.loadLinks(connection);
					
					if (!attrlist.isEmpty()) {
						Iterator<Integer> iterator = attrlist.iterator();
						
						while (iterator.hasNext()) {
							Integer id = iterator.next();
							
							attrLink = new AttributeLink();
							attrLink.setInstance(iterInst);
							attrLink.setEntityID(id);
							if (loadStatus)
								loadStatus = attrLink.entityLoad(connection);
							
							switch (attrLink.getDsEntityTypeID()) {
								case 500:
//									attribute = getMeasurementByID(attrLink.getDsEntryID());
//									if (!(attribute != null)) {
										attribute = new Measurement();
										attribute.setEntityID(attrLink.getDsEntryID());
										
										if (loadStatus)
											loadStatus = ((Measurement)attribute).entityLoad(connection);
										
										if (loadStatus)
											setMeasurement((Measurement)attribute);
//									}
									break;
								case 610:
									attribute = getDimensionByID(attrLink.getDsEntryID()); 
									if (!(attribute != null)) {
										attribute = new ConDimension();
										attribute.setEntityID(attrLink.getDsEntryID());
										
										if (loadStatus)
											loadStatus = ((ConDimension)attribute).entityLoad(connection);
										
										if (loadStatus)
											addDimension((ConDimension)attribute);
									}
									break;
								case 720:
									attribute = getIndicatorByID(attrLink.getDsEntryID()); 
									if (!(attribute != null)) { 									
										attribute = new OperaIndicator();
										attribute.setEntityID(attrLink.getDsEntryID());
										
										if (loadStatus)
											loadStatus = ((OperaIndicator)attribute).entityLoad(connection);
										
										if (loadStatus)
											addIndicator((OperaIndicator)attribute);
									}
									break;
								case 800:
									attribute = getVariableByID(attrLink.getDsEntryID());
									if (!(attribute != null)) { 
										attribute = new Variable();
										attribute.setEntityID(attrLink.getDsEntryID());
										
										if (loadStatus)
											loadStatus = ((Variable)attribute).entityLoad(connection);
										
										if (loadStatus)
											addVariable((Variable)attribute);
									}
									break;
							}
							
							if (loadStatus) {
								attrLink.setAttribute(attribute);
								addLink(attrLink);
							}
						}
					}
				}
				
				/*
				 *	Char.Link, Characteristics (Category, Specification, Prescription, Value)
				 */
				CharacteristicLink		charLink;
				Characteristics			characteristic = null;
				
				charLink = new CharacteristicLink(getMeasurement());
				ArrayList<Integer>	charlist	= charLink.loadLinks(connection);
					
				if (!charlist.isEmpty()) {
					Iterator<Integer> iterator = charlist.iterator();
						
					while (iterator.hasNext()) {
						Integer id = iterator.next();
							
						charLink = new CharacteristicLink();
						charLink.setAttribute(getMeasurement());
						charLink.setEntityID(id);
						if (loadStatus)
							loadStatus = charLink.entityLoad(connection);
							
						characteristic = new Category();
						characteristic.setEntityID(charLink.getDsCharEntryID());
						
						if (loadStatus)
							loadStatus = ((Category)characteristic).entityLoad(connection);
							
						if (loadStatus) {
							addCategory((Category)characteristic);
							
							charLink.setCharacteristic(characteristic);
							addCharacteristicLink(charLink);
						}
					}
				}
				
				Iterator<ConDimension> dimIter =	dimensions.iterator();
				while (dimIter.hasNext()) {
					ConDimension iterDim = dimIter.next();
					
					charLink = new CharacteristicLink(iterDim);
					charlist = charLink.loadLinks(connection);
					
					if (!charlist.isEmpty()) {
						Iterator<Integer> iterator = charlist.iterator();
						
						while (iterator.hasNext()) {
							Integer id = iterator.next();
							
							charLink = new CharacteristicLink();
							charLink.setAttribute(iterDim);
							charLink.setEntityID(id);
							if (loadStatus)
								loadStatus = charLink.entityLoad(connection);
							
							characteristic = new ConSpecification();
							characteristic.setEntityID(charLink.getDsCharEntryID());
							
							if (loadStatus)
								loadStatus = ((ConSpecification)characteristic).entityLoad(connection);
								
							if (loadStatus) {
								addSpecification((ConSpecification)characteristic);
								
								charLink.setCharacteristic(characteristic);
								addCharacteristicLink(charLink);
							}							
						}
					}
				}
				
				Iterator<OperaIndicator> indIter =	indicators.iterator();
				while (indIter.hasNext()) {
					OperaIndicator iterInd = indIter.next();
					
					charLink = new CharacteristicLink(iterInd);
					charlist = charLink.loadLinks(connection);
					
					if (!charlist.isEmpty()) {
						Iterator<Integer> iterator = charlist.iterator();
						
						while (iterator.hasNext()) {
							Integer id = iterator.next();
							
							charLink = new CharacteristicLink();
							charLink.setAttribute(iterInd);
							charLink.setEntityID(id);
							if (loadStatus)
								loadStatus = charLink.entityLoad(connection);
							
							characteristic = new OperaPrescription();
							characteristic.setEntityID(charLink.getDsCharEntryID());
							
							if (loadStatus)
								loadStatus = ((OperaPrescription)characteristic).entityLoad(connection);
								
							if (loadStatus) {
								addPrescription((OperaPrescription)characteristic);
								
								charLink.setCharacteristic(characteristic);
								addCharacteristicLink(charLink);
							}							
						}
					}
				}
				
				Iterator<Variable> varIter =	variables.iterator();
				while (varIter.hasNext()) {
					Variable iterVar = varIter.next();
					
					charLink = new CharacteristicLink(iterVar);
					charlist = charLink.loadLinks(connection);
					
					if (!charlist.isEmpty()) {
						Iterator<Integer> iterator = charlist.iterator();
						
						while (iterator.hasNext()) {
							Integer id = iterator.next();
							
							charLink = new CharacteristicLink();
							charLink.setAttribute(iterVar);
							charLink.setEntityID(id);
							if (loadStatus)
								loadStatus = charLink.entityLoad(connection);
							
							characteristic = new Value();
							characteristic.setEntityID(charLink.getDsCharEntryID());
							
							if (loadStatus)
								loadStatus = ((Value)characteristic).entityLoad(connection);
								
							if (loadStatus) {
								addValue((Value)characteristic);
								
								charLink.setCharacteristic(characteristic);
								addCharacteristicLink(charLink);
							}							
						}
					}
				}
				
				/*
				 *	InstanceMap
				 */
				inst_maps.clear();
				
				InstanceMap		instMap = new InstanceMap();
				instMap.setBelongsTo(project);
				ArrayList<Integer>	instMaplist	= instMap.loadMaps(connection);
					
				if (!instMaplist.isEmpty()) {
					Iterator<Integer> iterator = instMaplist.iterator();
						
					while (iterator.hasNext()) {
						Integer id = iterator.next();
						
						instMap = new InstanceMap();
						instMap.setEntityID(id);
						if (loadStatus)
							loadStatus = instMap.entityLoad(connection);
						
						if (loadStatus) {
							instMap.setBelongsTo(project);
							instMap.setSourceInstance(getRefByID(instMap.getDsSourceInstanceEntry()));
							instMap.setTargetInstance(getRefByID(instMap.getDsTargetInstanceEntry()));
							
							addInstanceMap(instMap);
						}
					}
				}
				
				/*
				 *	AttributeMap
				 */
				Iterator<InstanceMap> instMapIter =	inst_maps.iterator();
				while (instMapIter.hasNext()) {
					InstanceMap iterInstMap = instMapIter.next();
					
					AttributeMap		attrMap = new AttributeMap();
					attrMap.setBelongsTo(iterInstMap);
					ArrayList<Integer>	attrMaplist	= attrMap.loadMaps(connection);
						
					if (!attrMaplist.isEmpty()) {
						Iterator<Integer> iterator = attrMaplist.iterator();
							
						while (iterator.hasNext()) {
							Integer id = iterator.next();
							
							attrMap = new AttributeMap();
							attrMap.setEntityID(id);
							if (loadStatus)
								loadStatus = attrMap.entityLoad(connection);
							
							if (loadStatus) {
								attrMap.setBelongsTo(iterInstMap);
								attrMap.setSourceAttribute(getLinkByID(attrMap.getDsSourceAttribute()));
								attrMap.setTargetAttribute(getLinkByID(attrMap.getDsTargetAttribute()));
								
								addMap(attrMap);
							}
						}
					}
				}
				
				/*
				 *	Char.Map
				 */
				Iterator<AttributeMap> attrMapIter =	attr_maps.iterator();
				while (attrMapIter.hasNext()) {
					AttributeMap iterAttrMap = attrMapIter.next();
					
					CharacteristicMap		charMap = new CharacteristicMap();
					charMap.setBelongsTo(iterAttrMap);
					ArrayList<Integer>	charMaplist	= charMap.loadMaps(connection);
						
					if (!charMaplist.isEmpty()) {
						Iterator<Integer> iterator = charMaplist.iterator();
							
						while (iterator.hasNext()) {
							Integer id = iterator.next();
							
							charMap = new CharacteristicMap();
							charMap.setEntityID(id);
							if (loadStatus)
								loadStatus = charMap.entityLoad(connection);
							
							if (loadStatus) {
								charMap.setBelongsTo(iterAttrMap);
								charMap.setSourceCharacteristic(getCharacteristicLinkByID(charMap.getDsSourceCharEntry()));
								charMap.setTargetCharacteristic(getCharacteristicLinkByID(charMap.getDsTargetCharEntry()));
								
								addCharacteristicMap(charMap);
							}
						}
					}
				}
				
				/*
				 *	Attr. Comps.
				 */
				attr_comps.clear();
				
				AttributeComp		attrComp		= new AttributeComp();
				attrComp.setBelongsTo(project);
				ArrayList<Integer>	attrComplist	= attrComp.loadComps(connection);
					
				if (!attrComplist.isEmpty()) {
					Iterator<Integer> iterator = attrComplist.iterator();
						
					while (iterator.hasNext()) {
						Integer id = iterator.next();
						
						attrComp = new AttributeComp();
						attrComp.setBelongsTo(project);
						attrComp.setEntityID(id);
						
						if (loadStatus)
							loadStatus = attrComp.entityLoad(connection);
						
						if (loadStatus) {
							attrComp.setSourceAttribute(getLinkByID(attrComp.getDsSourceAttributeEntry()));
							attrComp.setTargetAttribute(getLinkByID(attrComp.getDsTargetAttributeEntry()));
							
							addComp(attrComp);
						}
					}
				}
				
			}
		} catch (SQLException e) {
			loadStatus = false;
			JOptionPane.showMessageDialog(
					null, e.getMessage(), "SQLException:",  JOptionPane.ERROR_MESSAGE);
				  	
			/* DEMO ONLY */
			System.err.println("SQLException: " + e.getMessage());
			e.printStackTrace();
		}
		
		return loadStatus;
	}

	/**
	 * @param project
	 * @param connection
	 * @return
	 */
	public boolean entityStore(Project project, Connection connection) {

		boolean storeStatus = true;
		
		if (connection.equals(null)) {
			storeStatus = false;
			JOptionPane.showMessageDialog(null, "Error! No Connection to DataBase.");
			
			return storeStatus;
		}
				
		/* Handle Tab Comments */
		if (storeStatus) {
			getTabProject().setEntityID(project.getEntityID());
			storeStatus = getTabProject().entityStore(connection);
		}
		
		if (storeStatus) {
			getTabConcept().setEntityID(project.getEntityID());
			storeStatus = getTabConcept().entityStore(connection);
		}
		
		if (storeStatus) {
			getTabLiterature().setEntityID(project.getEntityID());
			storeStatus = getTabLiterature().entityStore(connection);
		}
		
		if (storeStatus) {
			getTabMeasurement().setEntityID(project.getEntityID());
			storeStatus = getTabMeasurement().entityStore(connection);
		}
		
		if (storeStatus) {
			getTabDimension().setEntityID(project.getEntityID());
			storeStatus = getTabDimension().entityStore(connection);
		}
		
		if (storeStatus) {
			getTabSpecification().setEntityID(project.getEntityID());
			storeStatus = getTabSpecification().entityStore(connection);
		}
		
		if (storeStatus) {
			getTabMapDimensionInstance().setEntityID(project.getEntityID());
			storeStatus = getTabMapDimensionInstance().entityStore(connection);
		}
		
		if (storeStatus) {
			getTabMapDimensionAttribute().setEntityID(project.getEntityID());
			storeStatus = getTabMapDimensionAttribute().entityStore(connection);
		}
		
		if (storeStatus) {
			getTabMapDimensionChar().setEntityID(project.getEntityID());
			storeStatus = getTabMapDimensionChar().entityStore(connection);
		}
		
		if (storeStatus) {
			getTabOSInstance().setEntityID(project.getEntityID());
			storeStatus = getTabOSInstance().entityStore(connection);
		}
		
		if (storeStatus) {
			getTabIndicator().setEntityID(project.getEntityID());
			storeStatus = getTabIndicator().entityStore(connection);
		}
		
		if (storeStatus) {
			getTabPrescription().setEntityID(project.getEntityID());
			storeStatus = getTabPrescription().entityStore(connection);
		}
		
		if (storeStatus) {
			getTabMapIndicatorInstance().setEntityID(project.getEntityID());
			storeStatus = getTabMapIndicatorInstance().entityStore(connection);
		}
		
		if (storeStatus) {
			getTabMapIndicatorAttribute().setEntityID(project.getEntityID());
			storeStatus = getTabMapIndicatorAttribute().entityStore(connection);
		}
		
		if (storeStatus) {
			getTabMapIndicatorChar().setEntityID(project.getEntityID());
			storeStatus = getTabMapIndicatorChar().entityStore(connection);
		}
		
		if (storeStatus) {
			getTabSearchVariable().setEntityID(project.getEntityID());
			storeStatus = getTabSearchVariable().entityStore(connection);
		}
		
		if (storeStatus) {
			getTabCompareVariables().setEntityID(project.getEntityID());
			storeStatus = getTabCompareVariables().entityStore(connection);
		}
		
		if (storeStatus) {
			getTabCompareValues().setEntityID(project.getEntityID());
			storeStatus = getTabCompareValues().entityStore(connection);
		}
		
		if (storeStatus) {
			getTabDRInstance().setEntityID(project.getEntityID());
			storeStatus = getTabDRInstance().entityStore(connection);
		}
		
		if (storeStatus) {
			getTabVariable().setEntityID(project.getEntityID());
			storeStatus = getTabVariable().entityStore(connection);
		}
		
		if (storeStatus) {
			getTabValue().setEntityID(project.getEntityID());
			storeStatus = getTabValue().entityStore(connection);
		}
		
		if (storeStatus) {
			getTabMapVariableInstance().setEntityID(project.getEntityID());
			storeStatus = getTabMapVariableInstance().entityStore(connection);
		}
		
		if (storeStatus) {
			getTabMapVariableAttribute().setEntityID(project.getEntityID());
			storeStatus = getTabMapVariableAttribute().entityStore(connection);
		}
		
		if (storeStatus) {
			getTabMapVariableChar().setEntityID(project.getEntityID());
			storeStatus = getTabMapVariableChar().entityStore(connection);
		}
				
		/* tab 2 */
//		if (storeStatus)
//			storeStatus = getConcept().entityStore(connection);
//		
//		if (storeStatus) {
//			ConceptRef conceptRef = new ConceptRef();
//			conceptRef.setConcept(getConcept());
//			conceptRef.setReferenced(project);
//			conceptRef.setRefRelationtype(RefRelationtype.REFERENCED); /* ONLY FOR DEMO */
//			
//			storeStatus = conceptRef.entityStore(connection);
//		}
		
		/* tab 3 */
//		if (storeStatus) {
//			Iterator<Literature> it_literature = getLiteratures().iterator();
//			
//			while (it_literature.hasNext()) {
//				Literature literature = it_literature.next();
//				
//				if (literature.notEmpty()) {
//					if (storeStatus)
//						storeStatus = literature.entityStore(connection);
//					
//					if (storeStatus) {
//						LiteratureRef literatureRef = new LiteratureRef();
//						literatureRef.setLiterature(literature);
//						literatureRef.setReferenced(project);
//						literatureRef.setRefRelationtype(RefRelationtype.REFERENCED);
//						
//						storeStatus = literatureRef.entityStore(connection);
//					}
//				}
//			}
//		}		
		
		/* tab 0, 10, 18 */
		if (instances != null) {
			Iterator<WorkStepInstance> it_instance = instances.iterator();
		
			while (it_instance.hasNext()) {
				WorkStepInstance instance = it_instance.next();
			
				if (storeStatus)
					storeStatus = instance.entityStore(connection);
			}
			
			if (inst_links != null) {
				Iterator<InstanceLink> it_link = inst_links.iterator();
				
				while (it_link.hasNext()) {
					InstanceLink link = it_link.next();
					
					if (storeStatus)
						storeStatus = link.entityStore(connection);
				}
			}				
		}
		
		/* tab 4 */
		if (storeStatus) {
			storeStatus = getMeasurement().entityStore(connection);
			
			if (attr_links != null) {
				Iterator<AttributeLink> it_link = this.getLinksByType(AttributeLinkType.MEASUREMENT).iterator();
				
				while (it_link.hasNext()) {
					AttributeLink link = it_link.next();
					
					if (storeStatus)
						storeStatus = link.entityStore(connection);
				}
			}
		}
		
		if (categories != null) {
			Iterator<Category> it_cat = categories.iterator();
			
			while (it_cat.hasNext()) {
				Category cat = it_cat.next();
				
				if (storeStatus)
					storeStatus = cat.entityStore(connection);
			}
			
			if (char_links != null) {
				Iterator<CharacteristicLink> it_link = this.getCharacteristicLinksByType(CharacteristicLinkType.CATEGORY).iterator();
				
				while (it_link.hasNext()) {
					CharacteristicLink link = it_link.next();
					
					if (storeStatus)
						storeStatus = link.entityStore(connection);
				}
			}
		}
		
		/* Attributes, AttributeLinks */
		/* tab 5 */
		if (dimensions != null) {
			Iterator<ConDimension> it_dimension = dimensions.iterator();
			
			while (it_dimension.hasNext()) {
				ConDimension dim = it_dimension.next();
				
				if (storeStatus)
					storeStatus = dim.entityStore(connection);
			}
			
			if (attr_links != null) {
				Iterator<AttributeLink> it_link = this.getLinksByType(AttributeLinkType.DIMENSION).iterator();
				
				while (it_link.hasNext()) {
					AttributeLink link = it_link.next();
					
					if (storeStatus)
						storeStatus = link.entityStore(connection);
				}
			}
		}
		
		/* tab 11 */
		if (indicators != null) {
			Iterator<OperaIndicator> it_indicator = indicators.iterator();
			
			while (it_indicator.hasNext()) {
				OperaIndicator ind = it_indicator.next();
				
				if (storeStatus)
					storeStatus = ind.entityStore(connection);
			}
			
			if (attr_links != null) {
				Iterator<AttributeLink> it_link = this.getLinksByType(AttributeLinkType.INDICATOR).iterator();
				
				while (it_link.hasNext()) {
					AttributeLink link = it_link.next();
					
					if (storeStatus)
						storeStatus = link.entityStore(connection);
				}
			}
		}
		
		/* tab 19 */
		if (variables != null) {
			/* Variables are imported and there is no need to store them */
//			Iterator<Variable> it_variable = variables.iterator();
//			
//			while (it_variable.hasNext()) {
//				Variable var = it_variable.next();
//				
//				if (storeStatus)
//					storeStatus = var.entityStore(connection);
//			}
			
			/* But the connection into the project are to be stored */ 
			if (attr_links != null) {
				Iterator<AttributeLink> it_link = this.getLinksByType(AttributeLinkType.VARIABLE).iterator();
				
				while (it_link.hasNext()) {
					AttributeLink link = it_link.next();
					
					if (storeStatus)
						storeStatus = link.entityStore(connection);
				}
			}
		}
		
		/* tab ?? */
		if (attr_links != null) {
			Iterator<AttributeLink> it_link = this.getLinksByType(AttributeLinkType.COMPARISON).iterator();
			
			while (it_link.hasNext()) {
				AttributeLink link = it_link.next();
				
				if (storeStatus)
					storeStatus = link.entityStore(connection);
			}
		}
		
		if (attr_comps != null) {
			Iterator<AttributeComp> it_comp = this.getComps().iterator();
			
			while (it_comp.hasNext()) {
				AttributeComp comp = it_comp.next();
				
				if (storeStatus)
					storeStatus = comp.entityStore(connection);
			}
		}
		
		/* Characteristics, Char. Links */
		/* tab 6 */
		if (specifications != null) {
			Iterator<ConSpecification> it_specification = specifications.iterator();
			
			while (it_specification.hasNext()) {
				ConSpecification spec = it_specification.next();
				
				if (storeStatus)
					storeStatus = spec.entityStore(connection);
			}
			
			if (char_links != null) {
				Iterator<CharacteristicLink> it_link = this.getCharacteristicLinksByType(CharacteristicLinkType.SPECIFICATION).iterator();
				
				while (it_link.hasNext()) {
					CharacteristicLink link = it_link.next();
					
					if (storeStatus)
						storeStatus = link.entityStore(connection);
				}
			}
		}
		
		/* tab 12 */
		if (prescriptions != null) {
			Iterator<OperaPrescription> it_prescription = prescriptions.iterator();
			
			while (it_prescription.hasNext()) {
				OperaPrescription pres = it_prescription.next();
				
				if (storeStatus)
					storeStatus = pres.entityStore(connection);
			}
			
			if (char_links != null) {
				Iterator<CharacteristicLink> it_link = this.getCharacteristicLinksByType(CharacteristicLinkType.PRESCRIPTION).iterator();
				
				while (it_link.hasNext()) {
					CharacteristicLink link = it_link.next();
					
					if (storeStatus)
						storeStatus = link.entityStore(connection);
				}
			}
		}
		
		/* tab 20 */
		/* Values are imported, not created and so also not stored! */
		
		/* InstanceMaps */
		/* tab 7, 13, 21 */
		if (inst_maps != null) {
			Iterator<InstanceMap> it_map = inst_maps.iterator();
			
			while (it_map.hasNext()) {
				InstanceMap map = it_map.next();
				
				if (storeStatus)
					storeStatus = map.entityStore(connection);
			}
		}
		
		/* AttributeMaps */
		/* tab 8, 14, 22 */
		if (attr_maps != null) {
			Iterator<AttributeMap> it_map = attr_maps.iterator();
			
			while (it_map.hasNext()) {
				AttributeMap map = it_map.next();
				
				if (map.getBelongsTo() != null) 
					if (storeStatus)
						storeStatus = map.entityStore(connection);
			}
		}
		
		/* Char. Maps */
		/* tab 9, 15, 23 */
		if (char_maps != null) {
			Iterator<CharacteristicMap> it_map = char_maps.iterator();
			
			while (it_map.hasNext()) {
				CharacteristicMap map = it_map.next();
				
				if ((map.getBelongsTo() != null) &&
						!(map.getType().equals(CharacteristicMapType.NONE)))
					if (storeStatus)
						storeStatus = map.entityStore(connection);
			}
		}
		
		emptyRecycleBin(connection); 
	
		return storeStatus;
	}

	/**
	 * @return
	 */
	public TabDummy getTabProject() {
		return tabProject;
	}

	/**
	 * @param tabProject
	 */
	public void setTabProject(TabDummy tabProject) {
		this.tabProject = tabProject;
	}

	/**
	 * @return
	 */
	public TabDummy getTabConcept() {
		return tabConcept;
	}

	/**
	 * @param tabConcept
	 */
	public void setTabConcept(TabDummy tabConcept) {
		this.tabConcept = tabConcept;
	}

	/**
	 * @return
	 */
	public TabDummy getTabLiterature() {
		return tabLiterature;
	}

	/**
	 * @param tabLiterature
	 */
	public void setTabLiterature(TabDummy tabLiterature) {
		this.tabLiterature = tabLiterature;
	}

	/**
	 * @return
	 */
	public TabDummy getTabMeasurement() {
		return tabMeasurement;
	}

	/**
	 * @param tabMeasurement
	 */
	public void setTabMeasurement(TabDummy tabMeasurement) {
		this.tabMeasurement = tabMeasurement;
	}

	/**
	 * @return
	 */
	public TabDummy getTabDimension() {
		return tabDimension;
	}

	/**
	 * @param tabDimension
	 */
	public void setTabDimension(TabDummy tabDimension) {
		this.tabDimension = tabDimension;
	}

	/**
	 * @return
	 */
	public TabDummy getTabSpecification() {
		return tabSpecification;
	}

	/**
	 * @param tabSpecification
	 */
	public void setTabSpecification(TabDummy tabSpecification) {
		this.tabSpecification = tabSpecification;
	}

	/**
	 * @return
	 */
	public TabDummy getTabMapDimensionInstance() {
		return tabMapDimensionInstance;
	}

	/**
	 * @param tabMapDimensionInstance
	 */
	public void setTabMapDimensionInstance(TabDummy tabMapDimensionInstance) {
		this.tabMapDimensionInstance = tabMapDimensionInstance;
	}

	/**
	 * @return
	 */
	public TabDummy getTabMapDimensionAttribute() {
		return tabMapDimensionAttribute;
	}

	/**
	 * @param tabMapDimensionAttribute
	 */
	public void setTabMapDimensionAttribute(TabDummy tabMapDimensionAttribute) {
		this.tabMapDimensionAttribute = tabMapDimensionAttribute;
	}

	/**
	 * @return
	 */
	public TabDummy getTabMapDimensionChar() {
		return tabMapDimensionChar;
	}

	/**
	 * @param tabMapDimensionChar
	 */
	public void setTabMapDimensionChar(TabDummy tabMapDimensionChar) {
		this.tabMapDimensionChar = tabMapDimensionChar;
	}

	/**
	 * @return
	 */
	public TabDummy getTabOSInstance() {
		return tabOSInstance;
	}

	/**
	 * @param tabOSInstance
	 */
	public void setTabOSInstance(TabDummy tabOSInstance) {
		this.tabOSInstance = tabOSInstance;
	}

	/**
	 * @return
	 */
	public TabDummy getTabIndicator() {
		return tabIndicator;
	}

	/**
	 * @param tabIndicator
	 */
	public void setTabIndicator(TabDummy tabIndicator) {
		this.tabIndicator = tabIndicator;
	}

	/**
	 * @return
	 */
	public TabDummy getTabPrescription() {
		return tabPrescription;
	}

	/**
	 * @param tabPrescription
	 */
	public void setTabPrescription(TabDummy tabPrescription) {
		this.tabPrescription = tabPrescription;
	}

	/**
	 * @return
	 */
	public TabDummy getTabMapIndicatorInstance() {
		return tabMapIndicatorInstance;
	}

	/**
	 * @param tabMapIndicatorInstance
	 */
	public void setTabMapIndicatorInstance(TabDummy tabMapIndicatorInstance) {
		this.tabMapIndicatorInstance = tabMapIndicatorInstance;
	}

	/**
	 * @return
	 */
	public TabDummy getTabMapIndicatorAttribute() {
		return tabMapIndicatorAttribute;
	}

	/**
	 * @param tabMapIndicatorAttribute
	 */
	public void setTabMapIndicatorAttribute(TabDummy tabMapIndicatorAttribute) {
		this.tabMapIndicatorAttribute = tabMapIndicatorAttribute;
	}

	/**
	 * @return
	 */
	public TabDummy getTabMapIndicatorChar() {
		return tabMapIndicatorChar;
	}

	/**
	 * @param tabMapIndicatorChar
	 */
	public void setTabMapIndicatorChar(TabDummy tabMapIndicatorChar) {
		this.tabMapIndicatorChar = tabMapIndicatorChar;
	}

	/**
	 * @return
	 */
	public TabDummy getTabSearchVariable() {
		return tabSearchVariable;
	}

	/**
	 * @param tabSearchVariable
	 */
	public void setTabSearchVariable(TabDummy tabSearchVariable) {
		this.tabSearchVariable = tabSearchVariable;
	}

	/**
	 * @return
	 */
	public TabDummy getTabCompareVariables() {
		return tabCompareVariables;
	}
	
	/**
	 * @param tabCompareVariables
	 */
	public void setTabCompareVariables(TabDummy tabCompareVariables) {
		this.tabCompareVariables = tabCompareVariables;
	}
	
	/**
	 * @return
	 */
	public TabDummy getTabCompareValues() {
		return tabCompareValues;
	}

	/**
	 * @param tabCompareValues
	 */
	public void setTabCompareValues(TabDummy tabCompareValues) {
		this.tabCompareValues = tabCompareValues;
	}

	/**
	 * @return
	 */
	public TabDummy getTabDRInstance() {
		return tabDRInstance;
	}

	/**
	 * @param tabDRInstance
	 */
	public void setTabDRInstance(TabDummy tabDRInstance) {
		this.tabDRInstance = tabDRInstance;
	}

	/**
	 * @return
	 */
	public TabDummy getTabVariable() {
		return tabVariable;
	}

	/**
	 * @param tabVariable
	 */
	public void setTabVariable(TabDummy tabVariable) {
		this.tabVariable = tabVariable;
	}

	/**
	 * @return
	 */
	public TabDummy getTabValue() {
		return tabValue;
	}

	/**
	 * @param tabValue
	 */
	public void setTabValue(TabDummy tabValue) {
		this.tabValue = tabValue;
	}

	/**
	 * @return
	 */
	public TabDummy getTabMapVariableInstance() {
		return tabMapVariableInstance;
	}

	/**
	 * @param tabMapVariableInstance
	 */
	public void setTabMapVariableInstance(TabDummy tabMapVariableInstance) {
		this.tabMapVariableInstance = tabMapVariableInstance;
	}

	/**
	 * @return
	 */
	public TabDummy getTabMapVariableAttribute() {
		return tabMapVariableAttribute;
	}

	/**
	 * @param tabMapVariableAttribute
	 */
	public void setTabMapVariableAttribute(TabDummy tabMapVariableAttribute) {
		this.tabMapVariableAttribute = tabMapVariableAttribute;
	}

	/**
	 * @return
	 */
	public TabDummy getTabMapVariableChar() {
		return tabMapVariableChar;
	}

	/**
	 * @param tabMapVariableChar
	 */
	public void setTabMapVariableChar(TabDummy tabMapVariableChar) {
		this.tabMapVariableChar = tabMapVariableChar;
	}

}

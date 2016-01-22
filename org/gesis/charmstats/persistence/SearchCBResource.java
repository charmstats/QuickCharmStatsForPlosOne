package org.gesis.charmstats.persistence;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JComboBox;

import org.gesis.charmstats.model.ConDimension;
import org.gesis.charmstats.model.Concept;
import org.gesis.charmstats.model.Measurement;
import org.gesis.charmstats.model.OperaIndicator;
import org.gesis.charmstats.model.Project;
import org.gesis.charmstats.model.Variable;
import org.gesis.charmstats.view.GComboBox;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class SearchCBResource {
	/*
	 *	Dummy
	 */
	public enum EmptyCB {
		NONE	(0, "          ");
		
		private final int id;
		private final String label;
		
		EmptyCB (int id, String label) {
			this.id		= id;
			this.label	= label;
		}
		
		public String toString() {
			return label;
		}
		
		public int getId() {
			return id;
		}
	}
	
	/*
	 *	Searchable Objects
	 */
	public enum SearchObject {
		NONE		( 0,  "sc_so_none"),
		PROJECT		(100, "sc_so_prj"),
		MEASUREMENT	(300, "sc_so_mea"),
		VARIABLE	(600, "sc_so_var");
		
		/*
		 *	Fields
		 */
		public static final String BUNDLE	= "org.gesis.charmstats.resources.ModelBundle";
		
		private final int id;
		private final String label;
		
		private Locale		locale;
		
		/** Resources for the default locale */
		private ResourceBundle res = 
			ResourceBundle.getBundle(BUNDLE);
		
		/*
		 *	Constructor
		 */
		SearchObject (int id, String label) {
			this.id		= id;
			this.label	= label;
		}
		
		/*
		 *	Methods
		 */
		public void setLocale(Locale loc) {
			this.locale = loc;
			this.res = ResourceBundle.getBundle(BUNDLE, locale);
		}
		
		/** @return the locale-dependent message */
		public String toString() {
		    return res.getString(getLabel());
		}
		
		public int getId() {
			return id;
		}
		
		public String getLabel() {
			return label;
		}
	}
	
	/*
	 *	Scopes
	 */
	public enum ProjectScope {
		PROJECT		(100, "sc_ps_prj"),
		CONCEPT		(200, "sc_ps_con"),
		MEASUREMENT	(300, "sc_ps_mea"),
		STUDY	 	(400, "sc_ps_sty"), //<html><span style=\"color:#C0C0C0\">Study</span></html>"),
		QUESTION	(500, "sc_ps_que"), //<html><span style=\"color:#C0C0C0\">Question</span></html>"),
		VARIABLE	(600, "sc_ps_var"),
		INDICATOR	(700, "sc_ps_ind"),
		DIMENSION	(800, "sc_ps_dim");
		
		/*
		 *	Fields
		 */
		public static final String BUNDLE	= "org.gesis.charmstats.resources.ModelBundle";
		
		private final int id;
		private final String label;
		
		private Locale		locale;
		
		/** Resources for the default locale */
		private ResourceBundle res = 
			ResourceBundle.getBundle(BUNDLE);
		
		/*
		 *	Constructor
		 */
		ProjectScope (int id, String label) {
			this.id			= id;
			this.label		= label;	
		}
		
		/*
		 *	Methods
		 */
		public void setLocale(Locale loc) {
			this.locale = loc;
			this.res = ResourceBundle.getBundle(BUNDLE, locale);
		}
		
		/** @return the locale-dependent message */
		public String toString() {
		    return res.getString(getLabel());
		}
		
		public int getId() {
			return id;
		}
		
		public String getLabel() {
			return label;
		}				
	}
		
	public enum ConceptScope {
	    LABEL		(201, "Label"),
	    DEFINITION	(202, "Definition");
		
		private final int id;
		private final String label;
		
		ConceptScope (int id, String label) {
			this.id			= id;
			this.label		= label;	
		}
		
		public String toString() {
			return label;
		}
		
		public int getId() {
			return id;
		}
	}
	
	public enum MeasurementScope {
		MEASUREMENT	(300, "sc_ms_mea");
		
		/*
		 *	Fields
		 */
		public static final String BUNDLE	= "org.gesis.charmstats.resources.ModelBundle";
		
		private final int id;
		private final String label;
		
		private Locale		locale;
		
		/** Resources for the default locale */
		private ResourceBundle res = 
			ResourceBundle.getBundle(BUNDLE);
		
		/*
		 *	Constructor
		 */
		MeasurementScope (int id, String label) {
			this.id			= id;
			this.label		= label;	
		}
		
		/*
		 *	Methods
		 */
		public void setLocale(Locale loc) {
			this.locale = loc;
			this.res = ResourceBundle.getBundle(BUNDLE, locale);
		}
		
		/** @return the locale-dependent message */
		public String toString() {
		    return res.getString(getLabel());
		}
		
		public int getId() {
			return id;
		}
		
		public String getLabel() {
			return label;
		}
	}
	
	public enum UniverseScope {
	    UNIVERSE 	(400, "Universe");
		
		private final int id;
		private final String label;
		
		UniverseScope (int id, String label) {
			this.id			= id;
			this.label		= label;	
		}
		
		public String toString() {
			return label;
		}
		
		public int getId() {
			return id;
		}
	}
	
	public enum QuestionScope {
	    QUESTION	(500, "Question");
		
		private final int id;
		private final String label;
		
		QuestionScope (int id, String label) {
			this.id			= id;
			this.label		= label;	
		}
		
		public String toString() {
			return label;
		}
		
		public int getId() {
			return id;
		}
	}
	
	public enum VariableScope {
		VARIABLE	(600, "sc_vs_var");
	    
		/*
		 *	Fields
		 */
		public static final String BUNDLE	= "org.gesis.charmstats.resources.ModelBundle";
		
		private final int id;
		private final String label;
		
		private Locale		locale;
		
		/** Resources for the default locale */
		private ResourceBundle res = 
			ResourceBundle.getBundle(BUNDLE);
		
		/*
		 *	Constructor
		 */
		VariableScope (int id, String label) {
			this.id			= id;
			this.label		= label;	
		}
		
		/*
		 *	Methods
		 */
		public void setLocale(Locale loc) {
			this.locale = loc;
			this.res = ResourceBundle.getBundle(BUNDLE, locale);
		}
		
		/** @return the locale-dependent message */
		public String toString() {
		    return res.getString(getLabel());
		}
		
		public int getId() {
			return id;
		}
		
		public String getLabel() {
			return label;
		}
	}
	
	public enum IndicatorScope {
	    LABEL		(701, "Label"),
	    DEFINITION	(702, "Definition"),
	    COMMENT		(703, "Comment"),
	    KEYWORD		(704, "Keyword");
		
		private final int id;
		private final String label;
		
		IndicatorScope (int id, String label) {
			this.id			= id;
			this.label		= label;	
		}
		
		public String toString() {
			return label;
		}
		
		public int getId() {
			return id;
		}
	}
	
	public enum DimensionScope {
	    LABEL		(801, "Label"),
	    DEFINITION	(802, "Definition"),
	    COMMENT		(803, "Comment"),
	    KEYWORD		(804, "Keyword");
		
		private final int id;
		private final String label;
		
		DimensionScope (int id, String label) {
			this.id			= id;
			this.label		= label;	
		}
		
		public String toString() {
			return label;
		}
		
		public int getId() {
			return id;
		}				
	}
	
	/*
	 *	Attributes
	 */
	public enum DimensionAttributes {
		NONE		(0, ""),
	    NAME 		(1, "Label"),
	    LEVEL 		(2, "Level"),
	    DEFINITION	(3, "Definition"),
	    COMMENT 	(4, "Comment");
		
		private final int id;
		private final String label;
		
		DimensionAttributes (int id, String label) {
			this.id			= id;
			this.label		= label;	
		}
		
		public String toString() {
			return label;
		}
		
		public int getId() {
			return id;
		}
				
		public static String getSelectLine() {
			return "select con_dimension_id ";
		}
	}
	
	public enum IndicatorAttributes {
		NONE		(0, ""),
	    NAME 		(1, "Label"),
	    LEVEL 		(2, "Level"),
	    MEASURE		(3, "Measure"),
	    DEFINITION	(4, "Definition"),
	    COMMENT 	(5, "Comment");
		
		private final int id;
		private final String label;
		
		IndicatorAttributes (int id, String label) {
			this.id			= id;
			this.label		= label;	
		}
		
		public String toString() {
			return label;
		}
		
		public int getId() {
			return id;
		}
				
		public static String getSelectLine() {
			return "select opera_indicator_id ";
		}
	}
	
	public enum VariableAttributes {
		NONE		(0, ""),
	    NAME 		(1, "Label"),
	    LEVEL 		(2, "Level"),
	    MEASURE		(3, "Measure"),
	    TYPE		(4, "Type"),
	    DEFINITION	(5, "Definition"),
	    COMMENT 	(6, "Comment");
		
		private final int id;
		private final String label;
		
		VariableAttributes (int id, String label) {
			this.id			= id;
			this.label		= label;	
		}
		
		public String toString() {
			return label;
		}
		
		public int getId() {
			return id;
		}
				
		public static String getSelectLine() {
			return "select variable_id ";
		}
	}
	
	/*
	 *	Values
	 */
	public enum DimensionNameValues {
		;
		
		public static String getFromLine() {
			return "from con_dimensions ";
		}
		
		public static String getWhereLine() {
			return "where con_dimensions.default_label ";
		}
		
		public static String getAndLine() {
			return "";
		}
	}

	public enum DimensionLevelValues {
		NONE		(0, ""),
	    INDIVIDUAL	(1, "Individual"),
	    AGGREGATE	(2, "Aggregate"),
	    BOTH		(3, "Both");
		
		private final int id;
		private final String label;
		
		DimensionLevelValues (int id, String label) {
			this.id			= id;
			this.label		= label;	
		}
		
		public String toString() {
			return label;
		}
		
		public int getId() {
			return id;
		}
		
		public static String getFromLine() {
			return "from con_dimensions ";
		}
		
		public static String getWhereLine() {
			return "where con_dimensions.level = ";
		}
		
		public static String getAndLine() {
			return "";
		}
	}
	
	public enum DimensionDefinitionValues {
		;
		
		public static String getFromLine() {
			return "from con_dimensions, definitions ";
		}
		
		public static String getWhereLine() {
			return "where definitions.definition_text ";
		}
		
		public static String getAndLine() {
			return "and con_dimensions.definition = definitions.definition_id";
		}
	}
	
	public enum DimensionCommentValues {
		;
		
		public static String getFromLine() {
			return "from con_dimensions, comments ";
		}
		
		public static String getWhereLine() {
			return "where comments.comment_text ";
		}
		
		public static String getAndLine() {
			return "and con_dimensions.comment = comments.comment_id";
		}
	}
	
	public enum IndicatorNameValues {
		;
		
		public static String getFromLine() {
			return "from opera_indicators ";
		}
		
		public static String getWhereLine() {
			return "where opera_indicators.default_label ";
		}
		
		public static String getAndLine() {
			return "";
		}
	}

	public enum IndicatorLevelValues {
		NONE		(0, ""),
	    INDIVIDUAL	(1, "Individual"),
	    AGGREGATE	(2, "Aggregate");
	    
		private final int id;
		private final String label;
		
		IndicatorLevelValues (int id, String label) {
			this.id			= id;
			this.label		= label;	
		}
		
		public String toString() {
			return label;
		}
		
		public int getId() {
			return id;
		}
		
		public static String getFromLine() {
			return "from opera_indicators ";
		}
		
		public static String getWhereLine() {
			return "where opera_indicators.level = ";
		}
		
		public static String getAndLine() {
			return "";
		}
	}
	
	public enum IndicatorMeasureValues {
		NONE		(0, ""),
	    NOMINAL		(1, "Individual"),
	    ORDINAL		(2, "Aggregate"),
	    INTERVAL	(3, "Interval"),
	    RATIO		(4, "Ration"),
	    CONTINUOUS	(5, "Continuous");
	    
		private final int id;
		private final String label;
		
		IndicatorMeasureValues (int id, String label) {
			this.id			= id;
			this.label		= label;	
		}
		
		public String toString() {
			return label;
		}
		
		public int getId() {
			return id;
		}
		
		public static String getFromLine() {
			return "from opera_indicators ";
		}
		
		public static String getWhereLine() {
			return "where opera_indicators.measure = ";
		}
		
		public static String getAndLine() {
			return "";
		}
	}
	
	public enum IndicatorDefinitionValues {
		;
		
		public static String getFromLine() {
			return "from opera_indicators, definitions ";
		}
		
		public static String getWhereLine() {
			return "where definitions.definition_text ";
		}
		
		public static String getAndLine() {
			return "and opera_indicators.definition = definitions.definition_id";
		}
	}
	
	public enum IndicatorCommentValues {
		;
		
		public static String getFromLine() {
			return "from opera_indicators, comments ";
		}
		
		public static String getWhereLine() {
			return "where comments.comment_text ";
		}
		
		public static String getAndLine() {
			return "and opera_indicators.comment = comments.comment_id";
		}
	}
	
	public enum VariableNameValues {
		;
		
		public static String getFromLine() {
			return "from variables, label_refs, labels, international_strings ";
		}
		
		public static String getWhereLine() {
			return "where international_strings.string_value ";
		}
		
		public static String getAndLine() {
			return "and international_strings.international_string_id = labels.international_string_type " +
				"and labels.label_id = label_refs.label " +
				"and label_refs.table_entry_id = variables.variable_id " +
				"and label_refs.table_id = 800";
		}
	}

	public enum VariableLevelValues {
		NONE		(0, ""),
	    INDIVIDUAL	(1, "Individual"),
	    AGGREGATE	(2, "Aggregate");
	    
		private final int id;
		private final String label;
		
		VariableLevelValues (int id, String label) {
			this.id			= id;
			this.label		= label;	
		}
		
		public String toString() {
			return label;
		}
		
		public int getId() {
			return id;
		}
		
		public static String getFromLine() {
			return "from variables ";
		}
		
		public static String getWhereLine() {
			return "where variables.level = ";
		}
		
		public static String getAndLine() {
			return "";
		}
	}
	
	public enum VariableMeasureValues {
		NONE		(0, ""),
	    NOMINAL		(1, "Individual"),
	    ORDINAL		(2, "Aggregate"),
	    INTERVAL	(3, "Interval"),
	    RATIO		(4, "Ration"),
	    CONTINUOUS	(5, "Continuous");
	    
		private final int id;
		private final String label;
		
		VariableMeasureValues (int id, String label) {
			this.id			= id;
			this.label		= label;	
		}
		
		public String toString() {
			return label;
		}
		
		public int getId() {
			return id;
		}
		
		public static String getFromLine() {
			return "from variables ";
		}
		
		public static String getWhereLine() {
			return "where variables.measure = ";
		}
		
		public static String getAndLine() {
			return "";
		}
	}
	
	public enum VariableTypeValues {
		NONE		(0, ""),
	    ORIGINAL	(1, "Original"),
	    HARMONIZED	(2, "Harmonized"),
	    TECHNICAL	(3, "Technical"),
	    WEIGHT		(4, "Weight"),
	    FILTER		(5, "Filter");
	    
		private final int id;
		private final String label;
		
		VariableTypeValues (int id, String label) {
			this.id			= id;
			this.label		= label;	
		}
		
		public String toString() {
			return label;
		}
		
		public int getId() {
			return id;
		}
		
		public static String getFromLine() {
			return "from variables ";
		}
		
		public static String getWhereLine() {
			return "where variables.type = ";
		}
		
		public static String getAndLine() {
			return "";
		}
	}
	
	public enum VariableDefinitionValues {
		;
		
		public static String getFromLine() {
			return "from variables, definitions ";
		}
		
		public static String getWhereLine() {
			return "where definitions.definition_text ";
		}
		
		public static String getAndLine() {
			return "and variables.definition = definitions.definition_id";
		}
	}
	
	public enum VariableCommentValues {
		;
		
		public static String getFromLine() {
			return "from variables, comments ";
		}
		
		public static String getWhereLine() {
			return "where comments.comment_text ";
		}
		
		public static String getAndLine() {
			return "and variables.comment = comments.comment_id";
		}
	}

	
	/*
	 *	Methods 
	 *
	 */
	/**
	 * @param cbList
	 * @param cbSelection
	 * @return
	 */
	public static Object getEnum(Object cbList, int cbSelection) {
		Object retObject = null;
		
		if (cbList != null) {
			if (cbList.equals(org.gesis.charmstats.persistence.SearchCBResource.SearchObject.class)) {
				switch (cbSelection) {
					case  0:
						retObject = null;
						break;
					case  1:
						retObject = org.gesis.charmstats.persistence.SearchCBResource.ProjectScope.class;
						break;
					case  2: 
						retObject = org.gesis.charmstats.persistence.SearchCBResource.MeasurementScope.class;
						break;
					case  3: 
						retObject = org.gesis.charmstats.persistence.SearchCBResource.VariableScope.class;
						break;
					default:
						retObject = null;
				}
			}
		}
		
		return retObject;
	}
	
	/**
	 * @param searchList
	 * @param searchSelection
	 * @param scopeList
	 * @param scopeSelection
	 * @param proType
	 * @param meaType
	 * @param meaLevel
	 * @return
	 */
	public static String getDraftSQL(Object searchList, 
			int searchSelection, Object scopeList, int scopeSelection, int proType, int meaType, int meaLevel) {
		String retString = null;
		
		if (searchList != null) {
			if (searchList.equals(org.gesis.charmstats.persistence.SearchCBResource.SearchObject.class)) {
				switch (searchSelection) {
					case  0:
						retString = null;
						break;
					case  1:
						retString = getDraftForProjectScope(scopeList, scopeSelection, proType, meaType, meaLevel);
						break;
					case  2: 
						retString = getDraftForMeasurementScope(scopeList, scopeSelection, meaType, meaLevel);
						break;
					case  3: 
						retString = getDraftForVariableScope(scopeList, scopeSelection, meaLevel);
						break;
					default:
						retString = null;
						break;	
				}
			}
		}
		
		return retString;
	}
	
	/**
	 * @param scopeList
	 * @param scopeSelection
	 * @param proType
	 * @param meaType
	 * @param meaLevel
	 * @return
	 */
	private static String getDraftForProjectScope(Object scopeList,
			int scopeSelection, int proType, int meaType, int meaLevel) {
		String retString = null;
		
		if (scopeList != null) {
			if (scopeList.equals(org.gesis.charmstats.persistence.SearchCBResource.ProjectScope.class)) {
				switch (scopeSelection) {
					case 0: 
						retString = Project.getDraftSQLForAllProjectsThroughLabel();
						break;
					case 1: 
						retString = Concept.getDraftSQLForAllProjectsThroughLabel() +" union distinct "+
								Concept.getDraftSQLForAllProjectsThroughDefinition();
						break;
					case 2:  
						retString = Measurement.getDraftSQLForAllProjectThroughLabel(meaType, meaLevel); 
						break;
					case 3:  
						retString = null;
						break;
					case 4:  
						retString = null;
						break;
					case 5:  
						retString = Variable.getDraftSQLForAllProjectsThroughLabel(proType, meaLevel) +" union distinct "+
								Variable.getDraftSQLForAllProjectsThroughName(proType, meaLevel) +" union distinct "+
								Variable.getDraftSQLForAllProjectsThroughDefinition(proType, meaLevel) +" union distinct "+
								Variable.getDraftSQLForAllProjectsThroughComment(proType, meaLevel) +" union distinct "+
								Variable.getDraftSQLForAllProjectsThroughKeyword(proType, meaLevel);
						break;
					case 6: 
						retString = OperaIndicator.getDraftSQLForAllProjectsThroughLabel(proType, meaLevel) +" union distinct "+
								OperaIndicator.getDraftSQLForAllProjectsThroughDefinition(proType, meaLevel) +" union distinct "+
								OperaIndicator.getDraftSQLForAllProjectsThroughComment(proType, meaLevel) +" union distinct "+
								OperaIndicator.getDraftSQLForAllProjectsThroughKeyword(proType, meaLevel);
						break;
					case 7:  
						retString = ConDimension.getDraftSQLForAllProjectsThroughLabel(proType, meaLevel) +" union distinct "+
								ConDimension.getDraftSQLForAllProjectsThroughDefinition(proType, meaLevel) +" union distinct "+
								ConDimension.getDraftSQLForAllProjectsThroughComment(proType, meaLevel) +" union distinct "+
								ConDimension.getDraftSQLForAllProjectsThroughKeyword(proType, meaLevel);
						break;
					default:
						retString = null;
						break;
				}
			}
		}
		
		return retString;
	}
	
	/**
	 * @param scopeList
	 * @param scopeSelection
	 * @return
	 */
	@SuppressWarnings("unused")
	private static String getDraftForConceptScope(Object scopeList,
			int scopeSelection) {
		String retString = null;
		
		if (scopeList != null) {
			if (scopeList.equals(org.gesis.charmstats.persistence.SearchCBResource.ConceptScope.class)) {
				switch (scopeSelection) {
					case 0: 						
						retString = Concept.getDraftSQLForConceptLabel();
						break;
					case 1:
						retString = Concept.getDraftSQLForConceptDefinition();
						break;	
					default:
						retString = null;
						break;
				}
			}
		}
		
		return retString;
	}
	
	/**
	 * @param scopeList
	 * @param scopeSelection
	 * @param meaType
	 * @param meaLevel
	 * @return
	 */
	private static String getDraftForMeasurementScope(Object scopeList,
			int scopeSelection, int meaType, int meaLevel) {
		String retString = null;
		
		if (scopeList != null) {
			if (scopeList.equals(org.gesis.charmstats.persistence.SearchCBResource.MeasurementScope.class)) {
				switch (scopeSelection) {
					case 0:						
						retString = Measurement.getDraftSQLForMeasurementName(meaType, meaLevel) +" union distinct "+
							Measurement.getDraftSQLForMeasurementLabel(meaType, meaLevel);
						break;
					default:
						retString = null;
						break;
				}
			}
		}
		
		return retString;
	}
	
	/**
	 * @param scopeList
	 * @param scopeSelection
	 * @param meaLevel
	 * @return
	 */
	private static String getDraftForVariableScope(Object scopeList,
			int scopeSelection, int meaLevel) {
		String retString = null;
		
		if (scopeList != null) {
			if (scopeList.equals(org.gesis.charmstats.persistence.SearchCBResource.VariableScope.class)) {
				switch (scopeSelection) {
					case 0: 
						retString = Variable.getDraftSQLForVariableLabel(meaLevel) +" union distinct "+
								Variable.getDraftSQLForVariableName(meaLevel) +" union distinct "+
								Variable.getDraftSQLForVariableDefinition(meaLevel) +" union distinct "+
								Variable.getDraftSQLForVariableComment(meaLevel) +" union distinct "+
								Variable.getDraftSQLForVariableKeyword(meaLevel);
						break;
					default:
						retString = null;
						break;
				}
			}
		}
		
		return retString;
	}
	
	/**
	 * @param scopeList
	 * @param scopeSelection
	 * @param meaLevel
	 * @return
	 */
	@SuppressWarnings("unused")
	private static String getDraftForIndicatorScope(Object scopeList,
			int scopeSelection, int meaLevel) {
		String retString = null;
		
		if (scopeList != null) {
			if (scopeList.equals(org.gesis.charmstats.persistence.SearchCBResource.IndicatorScope.class)) {
				switch (scopeSelection) {
					case 0:
						retString = OperaIndicator.getDraftSQLForIndicatorLabel(meaLevel);
						break;
					case 1:
						retString = OperaIndicator.getDraftSQLForIndicatorDefinition(meaLevel);
						break;
					case 2:
						retString = OperaIndicator.getDraftSQLForIndicatorComment(meaLevel);
						break;
					case 3:
						retString = OperaIndicator.getDraftSQLForIndicatorKeyword(meaLevel);
						break;
					default:
						retString = null;
						break;
				}
			}
		}
		
		return retString;
	}
	
	/**
	 * @param scopeList
	 * @param scopeSelection
	 * @param meaLevel
	 * @return
	 */
	@SuppressWarnings("unused")
	private static String getDraftForDimensionScope(Object scopeList,
			int scopeSelection, int meaLevel) {
		String retString = null;
		
		if (scopeList != null) {
			if (scopeList.equals(org.gesis.charmstats.persistence.SearchCBResource.DimensionScope.class)) {
				switch (scopeSelection) {
					case 0:
						retString = ConDimension.getDraftSQLForDimensionLabel(meaLevel);
						break;
					case 1:
						retString = ConDimension.getDraftSQLForDimensionDefinition(meaLevel);
						break;
					case 2:
						retString = ConDimension.getDraftSQLForDimensionComment(meaLevel);
						break;
					case 3:
						retString = ConDimension.getDraftSQLForDimensionKeyword(meaLevel);
						break;
						/* 26.04.2012 */
					default:
						retString = null;
						break;
				}
			}
		}
		
		return retString;
	}
		
	/**
	 * @param cbList
	 * @param cbSelection
	 * @return
	 */
	public static String getFromSQL(Object cbList, int cbSelection) {
		String retObject = null;
		
		if (cbList != null) {
			if (cbList.equals(DimensionAttributes.class)) {
				switch (cbSelection) {
					case 0:
						retObject = null;
						break;
					case 1:
						retObject = DimensionNameValues.getFromLine();
						break;
					case 2:
						retObject = DimensionLevelValues.getFromLine();
						break;
					case 3:
						retObject = DimensionDefinitionValues.getFromLine();
						break;
					case 4:
						retObject = DimensionCommentValues.getFromLine();
						break;
					default:
						retObject = null;
				}
			}
			if (cbList.equals(IndicatorAttributes.class)) {
				switch (cbSelection) {
					case 0:
						retObject = null;
						break;
					case 1:
						retObject = IndicatorNameValues.getFromLine();
						break;
					case 2:
						retObject = IndicatorLevelValues.getFromLine();
						break;
					case 3:
						retObject = IndicatorMeasureValues.getFromLine();
						break;
					case 4:
						retObject = IndicatorDefinitionValues.getFromLine();
						break;
					case 5:
						retObject = IndicatorCommentValues.getFromLine();
						break;	
					default:
						retObject = null;
				}
			}
			if (cbList.equals(VariableAttributes.class)) {
				switch (cbSelection) {
					case 0:
						retObject = null;
						break;
					case 1:
						retObject = VariableNameValues.getFromLine();
						break;
					case 2:
						retObject = VariableLevelValues.getFromLine();
						break;
					case 3:
						retObject = VariableMeasureValues.getFromLine();
						break;
					case 4:
						retObject = VariableTypeValues.getFromLine();
						break;
					case 5:
						retObject = VariableDefinitionValues.getFromLine();
						break;	
					case 6:
						retObject = VariableCommentValues.getFromLine();
						break;
					default:
						retObject = null;
				}
			}
		}
		
		return retObject;
	}
	
	/**
	 * @param cbList
	 * @param cbSelection
	 * @return
	 */
	public static String getWhereSQL(Object cbList, int cbSelection) {
		String retObject = null;
		
		if (cbList != null) {
			if (cbList.equals(DimensionAttributes.class)) {
				switch (cbSelection) {
					case 0:
						retObject = null;
						break;
					case 1:
						retObject = DimensionNameValues.getWhereLine();
						break;
					case 2:
						retObject = DimensionLevelValues.getWhereLine();
						break;
					case 3:
						retObject = DimensionDefinitionValues.getWhereLine();
						break;
					case 4:
						retObject = DimensionCommentValues.getWhereLine();
						break;
					default:
						retObject = null;
				}
			}
			if (cbList.equals(IndicatorAttributes.class)) {
				switch (cbSelection) {
					case 0:
						retObject = null;
						break;
					case 1:
						retObject = IndicatorNameValues.getWhereLine();
						break;
					case 2:
						retObject = IndicatorLevelValues.getWhereLine();
						break;
					case 3:
						retObject = IndicatorMeasureValues.getWhereLine();
						break;
					case 4:
						retObject = IndicatorDefinitionValues.getWhereLine();
						break;
					case 5:
						retObject = IndicatorCommentValues.getWhereLine();
						break;	
					default:
						retObject = null;
				}
			}
			if (cbList.equals(VariableAttributes.class)) {
				switch (cbSelection) {
					case 0:
						retObject = null;
						break;
					case 1:
						retObject = VariableNameValues.getWhereLine();
						break;
					case 2:
						retObject = VariableLevelValues.getWhereLine();
						break;
					case 3:
						retObject = VariableMeasureValues.getWhereLine();
						break;
					case 4:
						retObject = VariableTypeValues.getWhereLine();
						break;
					case 5:
						retObject = VariableDefinitionValues.getWhereLine();
						break;	
					case 6:
						retObject = VariableCommentValues.getWhereLine();
						break;
					default:
						retObject = null;
				}
			}
		}
		
		return retObject;
	}
	
	/**
	 * @param cb
	 * @param cbSelection
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String getValueSQL(JComboBox cb, int cbSelection) {
		String retString = "";
		
		Object[] enums = 
			((Class)((GComboBox)cb).getThisEnum()).getEnumConstants();
		
		if (enums != null) {
			if (enums.length > 0) {
				retString = String.valueOf(cbSelection);
			} else
				retString = "REGEXP '" + cb.getSelectedItem().toString() + "' ";
		}
		
		return retString;
	}
	
	/**
	 * @param cbList
	 * @param cbSelection
	 * @return
	 */
	public static String getAndSQL(Object cbList, int cbSelection) {
		String retObject = null;
		
		if (cbList != null) {
			if (cbList.equals(DimensionAttributes.class)) {
				switch (cbSelection) {
					case 0:
						retObject = null;
						break;
					case 1:
						retObject = DimensionNameValues.getAndLine();
						break;
					case 2:
						retObject = DimensionLevelValues.getAndLine();
						break;
					case 3:
						retObject = DimensionDefinitionValues.getAndLine();
						break;
					case 4:
						retObject = DimensionCommentValues.getAndLine();
						break;
					default:
						retObject = null;
				}
			}
			if (cbList.equals(IndicatorAttributes.class)) {
				switch (cbSelection) {
					case 0:
						retObject = null;
						break;
					case 1:
						retObject = IndicatorNameValues.getAndLine();
						break;
					case 2:
						retObject = IndicatorLevelValues.getAndLine();
						break;
					case 3:
						retObject = IndicatorMeasureValues.getAndLine();
						break;
					case 4:
						retObject = IndicatorDefinitionValues.getAndLine();
						break;
					case 5:
						retObject = IndicatorCommentValues.getAndLine();
						break;	
					default:
						retObject = null;
				}
			}
			if (cbList.equals(VariableAttributes.class)) {
				switch (cbSelection) {
					case 0:
						retObject = null;
						break;
					case 1:
						retObject = VariableNameValues.getAndLine();
						break;
					case 2:
						retObject = VariableLevelValues.getAndLine();
						break;
					case 3:
						retObject = VariableMeasureValues.getAndLine();
						break;
					case 4:
						retObject = VariableTypeValues.getAndLine();
						break;
					case 5:
						retObject = VariableDefinitionValues.getAndLine();
						break;	
					case 6:
						retObject = VariableCommentValues.getAndLine();
						break;
					default:
						retObject = null;
				}
			}
		}
		
		return retObject;
	}
	
	/**
	 * @param cbList
	 * @param cbSelection
	 * @return
	 */
	public static String getBoolOperatorSQL(Object cbList, int cbSelection) {
		String retObject = null;
		
		if (cbList != null) {
			if (cbList instanceof org.gesis.charmstats.view.GComboBox) {
				switch (cbSelection) {
					case 0:
						retObject = null;
						break;
					case 1:
						retObject = " AND ";
						break;
					case 2:
						retObject = " OR ";
						break;
					case 3:
						retObject = " NOT ";
						break;
					default:
						retObject = null;
				}
			}
		}
		
		return retObject;
	}
	
}

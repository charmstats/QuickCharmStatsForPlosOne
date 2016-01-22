package org.gesis.charmstats.controller;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Observable;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.xml.namespace.QName;

import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.ddialliance.ddi3.xml.xmlbeans.datacollection.CodeDomainType;
import org.ddialliance.ddi3.xml.xmlbeans.datacollection.DataCollectionType;
import org.ddialliance.ddi3.xml.xmlbeans.datacollection.DynamicTextType;
import org.ddialliance.ddi3.xml.xmlbeans.datacollection.LiteralTextType;
import org.ddialliance.ddi3.xml.xmlbeans.datacollection.QuestionItemType;
import org.ddialliance.ddi3.xml.xmlbeans.datacollection.QuestionSchemeType;
import org.ddialliance.ddi3.xml.xmlbeans.datacollection.TextType;
import org.ddialliance.ddi3.xml.xmlbeans.instance.DDIInstanceDocument;
import org.ddialliance.ddi3.xml.xmlbeans.instance.DDIInstanceType;
import org.ddialliance.ddi3.xml.xmlbeans.logicalproduct.BaseLogicalProductType;
import org.ddialliance.ddi3.xml.xmlbeans.logicalproduct.CategorySchemeType;
import org.ddialliance.ddi3.xml.xmlbeans.logicalproduct.CategoryType;
import org.ddialliance.ddi3.xml.xmlbeans.logicalproduct.CodeRepresentationType;
import org.ddialliance.ddi3.xml.xmlbeans.logicalproduct.CodeSchemeType;
import org.ddialliance.ddi3.xml.xmlbeans.logicalproduct.CodeType;
import org.ddialliance.ddi3.xml.xmlbeans.logicalproduct.LogicalProductType;
import org.ddialliance.ddi3.xml.xmlbeans.logicalproduct.RepresentationType;
import org.ddialliance.ddi3.xml.xmlbeans.logicalproduct.VariableSchemeType;
import org.ddialliance.ddi3.xml.xmlbeans.logicalproduct.VariableType;
import org.ddialliance.ddi3.xml.xmlbeans.reusable.CitationType;
import org.ddialliance.ddi3.xml.xmlbeans.reusable.IDType;
import org.ddialliance.ddi3.xml.xmlbeans.reusable.IdentifiedStructuredStringType;
import org.ddialliance.ddi3.xml.xmlbeans.reusable.InternationalStringType;
import org.ddialliance.ddi3.xml.xmlbeans.reusable.LabelType;
import org.ddialliance.ddi3.xml.xmlbeans.reusable.NameType;
import org.ddialliance.ddi3.xml.xmlbeans.reusable.NoteType;
import org.ddialliance.ddi3.xml.xmlbeans.reusable.NoteTypeCodeType;
import org.ddialliance.ddi3.xml.xmlbeans.reusable.ReferenceType;
import org.ddialliance.ddi3.xml.xmlbeans.reusable.RelationshipType;
import org.ddialliance.ddi3.xml.xmlbeans.reusable.StructuredStringType;
import org.ddialliance.ddi3.xml.xmlbeans.studyunit.StudyUnitType;
import org.gesis.charmstats.ActionCommandID;
import org.gesis.charmstats.ActionCommandText;
import org.gesis.charmstats.Basket;
import org.gesis.charmstats.BasketRef;
import org.gesis.charmstats.IdentifiedParameter;
import org.gesis.charmstats.ImportStatistics;
import org.gesis.charmstats.LoginPreference;
import org.gesis.charmstats.RemovalStatistics;
import org.gesis.charmstats.model.AttributeComp;
import org.gesis.charmstats.model.AttributeLink;
import org.gesis.charmstats.model.AttributeLinkType;
import org.gesis.charmstats.model.AttributeMap;
import org.gesis.charmstats.model.AttributeMapType;
import org.gesis.charmstats.model.Attributes;
import org.gesis.charmstats.model.BiasMetadata;
import org.gesis.charmstats.model.BiasPreferenceType;
import org.gesis.charmstats.model.BiasRatingType;
import org.gesis.charmstats.model.BiasStandCodingType;
import org.gesis.charmstats.model.CStatsModel;
import org.gesis.charmstats.model.Category;
import org.gesis.charmstats.model.CharacteristicLink;
import org.gesis.charmstats.model.CharacteristicLinkType;
import org.gesis.charmstats.model.CharacteristicMap;
import org.gesis.charmstats.model.CharacteristicMapType;
import org.gesis.charmstats.model.Characteristics;
import org.gesis.charmstats.model.Comment;
import org.gesis.charmstats.model.ConDimension;
import org.gesis.charmstats.model.ConSpecification;
import org.gesis.charmstats.model.Concept;
import org.gesis.charmstats.model.InstanceLink;
import org.gesis.charmstats.model.InstanceLinkType;
import org.gesis.charmstats.model.InstanceMap;
import org.gesis.charmstats.model.InstanceMapType;
import org.gesis.charmstats.model.InstanceType;
import org.gesis.charmstats.model.Literature;
import org.gesis.charmstats.model.Measurement;
import org.gesis.charmstats.model.MeasurementLevel;
import org.gesis.charmstats.model.MeasurementSource;
import org.gesis.charmstats.model.MeasurementType;
import org.gesis.charmstats.model.OperaIndicator;
import org.gesis.charmstats.model.OperaPrescription;
import org.gesis.charmstats.model.Participant;
import org.gesis.charmstats.model.ParticipantRole;
import org.gesis.charmstats.model.Person;
import org.gesis.charmstats.model.PersonRole;
import org.gesis.charmstats.model.ProContent;
import org.gesis.charmstats.model.Progress;
import org.gesis.charmstats.model.Project;
import org.gesis.charmstats.model.ProjectType;
import org.gesis.charmstats.model.Question;
import org.gesis.charmstats.model.Study;
import org.gesis.charmstats.model.TabDummy;
import org.gesis.charmstats.model.TypeOfData;
import org.gesis.charmstats.model.User;
import org.gesis.charmstats.model.Value;
import org.gesis.charmstats.model.Variable;
import org.gesis.charmstats.model.WorkStepInstance;
import org.gesis.charmstats.persistence.ActivityLog;
import org.gesis.charmstats.persistence.DBContent;
import org.gesis.charmstats.persistence.DBField;
import org.gesis.charmstats.persistence.DBMisc;
import org.gesis.charmstats.persistence.EntityType;
import org.gesis.charmstats.persistence.RefRelationtype;
import org.gesis.charmstats.persistence.StataMetadataImport;
import org.gesis.charmstats.view.AddParticipantDialog;
import org.gesis.charmstats.view.AddPersonDialog;
import org.gesis.charmstats.view.AddUserDialog;
import org.gesis.charmstats.view.BiasTable;
import org.gesis.charmstats.view.CStatsGUI;
import org.gesis.charmstats.view.DesktopPane;
import org.gesis.charmstats.view.CommentDialog;
import org.gesis.charmstats.view.ImportReportFrame;
import org.gesis.charmstats.view.RemovalReportFrame;
import org.gesis.charmstats.view.SearchDialog;
import org.gesis.charmstats.view.ImportMeasurementSelectionDialog;
import org.gesis.charmstats.view.ImportVariableSelectionDialog;
import org.gesis.charmstats.view.ExportSyntaxDialog;
import org.gesis.charmstats.view.FormFrame;
import org.gesis.charmstats.view.ProjectFrame;
import org.gesis.charmstats.view.ImportMeasurementDialog;
import org.gesis.charmstats.view.ImportVariableDialog;
import org.gesis.charmstats.view.InternalFrame;
import org.gesis.charmstats.view.LoginDialog;
import org.gesis.charmstats.view.ManageUserPasswordDialog;
import org.gesis.charmstats.view.EditMeasureDialog;
import org.gesis.charmstats.view.OpenDialogDDIFilter;
import org.gesis.charmstats.view.OpenDialogFileFilter;
import org.gesis.charmstats.view.OpenDialogSPSSFilter;
import org.gesis.charmstats.view.OpenDialogSTATAFilter;
import org.gesis.charmstats.view.OverwriteUserPasswordDialog;
import org.gesis.charmstats.view.ParticipantEditorPanel;
import org.gesis.charmstats.view.PersonEditorPanel;
import org.gesis.charmstats.view.Tab;
import org.gesis.charmstats.view.TabCompareMetadata;
import org.gesis.charmstats.view.TabCompareValues;
import org.gesis.charmstats.view.TabConcept;
import org.gesis.charmstats.view.TabDRInstance;
import org.gesis.charmstats.view.TabDimension;
import org.gesis.charmstats.view.TabIndicator;
import org.gesis.charmstats.view.TabLiterature;
import org.gesis.charmstats.view.TabMapDimensionAttribute;
import org.gesis.charmstats.view.TabMapDimensionChar;
import org.gesis.charmstats.view.TabMapDimensionInstance;
import org.gesis.charmstats.view.TabMapIndicatorAttribute;
import org.gesis.charmstats.view.TabMapIndicatorChar;
import org.gesis.charmstats.view.TabMapIndicatorInstance;
import org.gesis.charmstats.view.TabMapVariableAttribute;
import org.gesis.charmstats.view.TabMapVariableChar;
import org.gesis.charmstats.view.TabMapVariableInstance;
import org.gesis.charmstats.view.TabMeasurement;
import org.gesis.charmstats.view.TabOSInstance;
import org.gesis.charmstats.view.TabPrescriptions;
import org.gesis.charmstats.view.TabProject;
import org.gesis.charmstats.view.TabQuestion;
import org.gesis.charmstats.view.TabSearchVariable;
import org.gesis.charmstats.view.TabSpecifications;
import org.gesis.charmstats.view.TabStudy;
import org.gesis.charmstats.view.TabValues;
import org.gesis.charmstats.view.TabVariable;
import org.gesis.charmstats.view.TabbedPane;
import org.gesis.charmstats.view.TabbedPaneConceptualStep;
import org.gesis.charmstats.view.TabbedPaneDataReCodingStep;
import org.gesis.charmstats.view.TabbedPaneForm;
import org.gesis.charmstats.view.TabbedPaneProjectSetupStep;
import org.gesis.charmstats.view.TabbedPaneSearchNCompareStep;
import org.gesis.charmstats.view.TabbedPaneTargetVariableStep;
import org.gesis.charmstats.view.UserEditorPanel;
import org.gesis.charmstats.view.VariableEditorPanel;
import org.opendatafoundation.data.spss.SPSSFile;
import org.opendatafoundation.data.spss.SPSSFileException;
import org.opendatafoundation.data.spss.SPSSVariable;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



@SuppressWarnings("unused")
/**
 * 
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class CStatsCtrl extends Observable implements ActionListener, ChangeListener, TreeSelectionListener {
	
	public static final String BUNDLE			= "org.gesis.charmstats.resources.ErrorMessagesBundle";
	public static final String ERROR_BUNDLE 	= "org.gesis.charmstats.resources.ErrorMessagesBundle";
	
	public static final String WRONG_INPUT		= "wrong_input";
	public static final String LOGIN_FAILED		= "login_failed";
	public static final String DUPLICATE		= "duplicate";
	public static final String ALREADY_OPENED_1	= "already_opened_1";
	public static final String ALREADY_OPENED_2 = "already_opened_2";
	public static final String WARNING 			= "warning";
	public static final String INFORMATION		= "information"; 
	public static final String VAR_IMPORT_SKIPPED	= "var_import_skipped"; 
	public static final String VAR_IMPORTS_ABORTED	= "var_imports_aborted"; 
	public static final String MEA_IMPORT_SKIPPED	= "mea_import_skipped"; 
	public static final String MEA_IMPORTS_ABORTED	= "mea_imports_aborted"; 
	public static final String IMPORT_VARIABLE	= "import_variable";
	public static final String IMPORT_MEASURE	= "import_measure";
	public static final String IMPORT_WARNING_1 = "import_warning_1";
	public static final String IMPORT_WARNING_11 = "import_warning_11"; 
	public static final String IMPORT_WARNING_2 = "import_warning_2";
	public static final String IMPORT_WARNING_Q = "import_warning_q";
	public static final String IMPORT_WARNING_QQ = "import_warning_qq"; 
	public static final String QUESTION 		= "question"; 
	public static final String IMPORT_VAR_REALLY = "import_var_really"; 
	public static final String IMPORT_MEA_REALLY = "import_mea_really"; 
	public static final String YES				= "yes";
	public static final String NO				= "no";
	public static final String CANCEL			= "cancel";
	public static final String NO_EDITED_PROJECTS	= "no_edited_projects";
	public static final String MISSING_INPUT	= "missing_input";
	public static final String TARGET_VALUE		= "target_value";
	public static final String IN_ROW			= "in_row";
	public static final String SOURCE_VALUE 	= "source_value";
	public static final String READ_ERROR		= "read_error";
	public static final String READ_ERROR_NOT_VALID_SPSS	= "read_error_not_valid_spss";
	public static final String DONT_TRIFLE_MEA	= "dont_trifle_mea";
	public static final String DONT_TRIFLE_VAR	= "dont_trifle_var";
	public static final String PRO_SHARING_MEA	= "projects_sharing_mea";
	public static final String PRO_SHARING_VAR	= "projects_sharing_var";
	public static final String USED_NO_REMOVE	= "used_no_remove"; 
	public static final String CONFIRM_REMOVE	= "confirm_remove";
	public static final String YES_REMOVE		= "yes_remove";
	public static final String DONT_REMOVE		= "dont_remove";
	public static final String ABORT_REMOVE		= "abort_remove";
	public static final String IS_REMOVED		= "is_removed";
	public static final String MEA_S			= "mea_s"; 
	public static final String VAR_S			= "var_s";
	
	public static final String NEW_PROJECT		= "new_project";
	public static final String NEW_MEASUREMENT	= "new_measurement";
	
	public static final String OVERWRITE_FILE	= "overwrite_file";
	public static final String CONFIRM			= "confirm";
	
	public static final String OVERWRITE_VAR_QUE	= "overwrite_var_que";
	public static final String OVERWRITE_VAR_QUE_TEXT	= "overwrite_var_que_text";
	public static final String OVERWRITE		= "overwrite";
	
	public static final String RP_TITLE			= "remove_project";
	public static final String RP_NO_HELP		= "no_help";
	public static final String REMOVE			= "remove";
	public static final String HELP				= "help";
	public static final String REMOVE_QUESTION	= "remove_question";
	
	public static final String WRONG_APPLICATION = "wrong_application";
	public static final String WRONG_VERSION 	= "wrong_version";
	
	public static final String EMPTY		= "";
			
	private static final String BUNDLE_F	= "org.gesis.charmstats.resources.FrameBundle";
	private static final String FILE_NAME	= "file_name";
	
	private static final String FINAL_NAME		= "final_name";
	
	private static final String VARIABLE				= "variable";
	private static final String SUCCESSFULLY_IMPORTED	= "successfully_imported";
	private static final String IMPORT_OF_VARIABLE		= "import_of_variable";
	private static final String IMPORT_WARNING_3		= "import_warning_3";
	private static final String IMPORT_WARNING_4		= "import_warning_4";
	private static final String YES_CONT 				= "yes_cont";
	private static final String NO_SKIP_VAR				= "no_skip_var";
	private static final String ABORT_IMPORTS			= "abort_imports";
	
	private static final String MEASUREMENT				= "measurement";
	private static final String IMPORT_OF_MEASUREMENT	= "import_of_measurement";
	private static final String IMPORT_WARNING_33		= "import_warning_33";
	private static final String IMPORT_WARNING_44		= "import_warning_44";
	private static final String NO_SKIP_MEA				= "no_skip_mea";
	private static final String START_IMPORT_OF			= "start_import_of";
	private static final String END_IMPORT				= "end_import";
	private static final String YES_IMPORT 				= "yes_import";
	
	
	/*
	 *	Fields
	 */
	private CStatsModel		_model;
	private String			_supported_model_version;
	private CStatsGUI		_view;
	
	
	private User			_user;
	private LoginPreference _pref;
	private Connection		_connection;
	
	static Random generator = new Random();
	
	private boolean projectInWork	= false;
	private boolean isAutocomplete	= false;
	private boolean stepIsValid		= false;
	
	SearchDialog			search;
	EditMeasureDialog		editMeasure;
	VariableEditorPanel		editVariable;
	UserEditorPanel			editUser;
	PersonEditorPanel		editPerson;
	ParticipantEditorPanel	editParticipant;
	
	/**
	 *	Constructor 
	 */
	public CStatsCtrl() {
		super();
	}
	
	/**
	 * @param user
	 * @param pref
	 * @param con
	 * @param locale
	 * @param supported_model_version
	 */
	public CStatsCtrl(User user, LoginPreference pref, Connection con, Locale locale, String supported_model_version) {
		this();
		
		_user		= user;
		_pref		= pref;
		_connection	= con;
		
		_model		= new CStatsModel();
		this.setProjectInWork(false); /* DEMO ONLY!!!*/
		_model.setUser(_user);
		_model.setAgency("gesis.org"); /* DEMO ONLY: Should be set by Application content */
		
		_supported_model_version = supported_model_version; 
		
		_view		= new CStatsGUI(_model, _connection, locale, this, this);
		
		((JFrame)_view.getAppFrame()).setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		((JFrame)_view.getAppFrame()).addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                exit();
            }
        });
		addObserver(_view);			
	}
	
	/*
	 *	Methods
	 */
	/**
	 * @return
	 */
	private boolean reConnect() {
		_connection = DBMisc.connectToDB(DBMisc.getDBUser(), DBMisc.getDBPassword());
		
		return (_connection != null);
	}
		
	/* Handle ActionEvents */
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {		
		actionHandler(e);	
	}
	
	/**
	 * @param actionCommand
	 */
	private void actionHandler(Object actionCommand) {
		String 				cmd			= null;
		IdentifiedParameter parameter	= null;
		
		if (actionCommand != null) {
			if (actionCommand instanceof ActionEvent) {
				cmd = ((ActionEvent)actionCommand).getActionCommand();
			} else {
				cmd = actionCommand.toString();
			}
		}
		
		if (cmd == null) {
			/* DoNothing */
			
		} else if (cmd.equals(ActionCommandText.BTN_DAT_STP_ADD_INS)) {
			_model.setIsBrowsing(false);
			
		/* Handle some button events from the toolbar: */
		} else if (cmd.equals(ActionCommandText.BTN_TB_SHOW_FORM)) {
			parameter = new IdentifiedParameter(ActionCommandID.BTN_TB_SHOW_FORM);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_TB_SHOW_GRAPH)) {
			parameter = new IdentifiedParameter(ActionCommandID.BTN_TB_SHOW_GRAPH);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_TB_SHOW_REPORT)) {
			parameter = new IdentifiedParameter(ActionCommandID.BTN_TB_SHOW_REPORT);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);			
		/* Handle button events from graph window: */
		} else if (cmd.equals(ActionCommandText.BTN_FRM_GRP_SHW_MEA_GRAPH)) {
			parameter = new IdentifiedParameter(ActionCommandID.BTN_FRM_GRP_SHW_MEA_GRAPH);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_FRM_GRP_SHW_MAP_GRAPH)) {
			parameter = new IdentifiedParameter(ActionCommandID.BTN_FRM_GRP_SHW_MAP_GRAPH);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_FRM_GRP_SHW_CON_GRAPH)) {
			parameter = new IdentifiedParameter(ActionCommandID.BTN_FRM_GRP_SHW_CON_GRAPH);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_FRM_GRP_SHW_OPE_GRAPH)) {
			parameter = new IdentifiedParameter(ActionCommandID.BTN_FRM_GRP_SHW_OPE_GRAPH);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_FRM_GRP_SHW_DAT_GRAPH)) {
			parameter = new IdentifiedParameter(ActionCommandID.BTN_FRM_GRP_SHW_DAT_GRAPH);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_FRM_GRP_PRINT_GRAPH)) {
			parameter = new IdentifiedParameter(ActionCommandID.BTN_FRM_GRP_PRINT_GRAPH);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_FRM_GRP_SAVE_GRAPH)) {
			parameter = new IdentifiedParameter(ActionCommandID.BTN_FRM_GRP_SAVE_GRAPH);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_FRM_GRP_SHW_MAP_MEA_GRAPH)) {
			parameter = new IdentifiedParameter(ActionCommandID.BTN_FRM_GRP_SHW_MAP_MEA_GRAPH);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_FRM_GRP_SHW_MAP_CON_GRAPH)) {
			parameter = new IdentifiedParameter(ActionCommandID.BTN_FRM_GRP_SHW_MAP_CON_GRAPH);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_FRM_GRP_SHW_MAP_OPE_GRAPH)) {
			parameter = new IdentifiedParameter(ActionCommandID.BTN_FRM_GRP_SHW_MAP_OPE_GRAPH);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_FRM_GRP_SHW_OVER_GRAPH)) {
			parameter = new IdentifiedParameter(ActionCommandID.BTN_FRM_GRP_SHW_OVER_GRAPH);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
			
		} else if (cmd.equals(ActionCommandText.BTN_FRM_REP_SAVE_REPORT)) {
			parameter = new IdentifiedParameter(ActionCommandID.BTN_FRM_REP_SAVE_REPORT);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
			
		/* Handle button events from project window: */
		} else if (cmd.equals(ActionCommandText.BTN_FRM_PRJ_OPN_PRJ)) {
			DesktopPane	desktop		= _view.getDesktop();
			Project 	prj			= ((ProjectFrame)desktop.getProjectFrame()).getSelectedProject();
			if (prj instanceof Project) {
				int projectID = prj.getEntityID();
				
				/* QuickCharmStats only: */
				if (checkProProject(projectID)) {
					ResourceBundle resourceBundle	= ResourceBundle.getBundle(BUNDLE, _view.getLocale());
					JOptionPane.showMessageDialog(
							_view.getAppFrame(), resourceBundle.getString(WRONG_APPLICATION), "", JOptionPane.ERROR_MESSAGE);
					
					return;
				}
				/* QuickCharmStats only */
				
				if (!checkAppVersion(projectID)) {
					ResourceBundle resourceBundle	= ResourceBundle.getBundle(BUNDLE, _view.getLocale());
					JOptionPane.showMessageDialog(
							_view.getAppFrame(), resourceBundle.getString(WRONG_VERSION), "", JOptionPane.ERROR_MESSAGE);
					
					return;
				}
				
				if (projectID != -1) {
					openProject(projectID);
					
					/* Activity Log: */
					int		openProjectID	= _model.getProject().getEntityID();
					String	openProjectName	= _model.getProject().getName();
					
					ActivityLog log = new ActivityLog();
					log.setWho(_model.getUser());
					log.setWhat(ActionCommandText.CMD_PRJ_OPEN_PROJECT);
					log.setDetails(String.valueOf(openProjectID) +":"+ openProjectName);
					log.entityStore(_connection);
					/* Activity Log End*/
				
					parameter = new IdentifiedParameter(ActionCommandID.CMD_PRJ_OPEN_PROJECT);
					Object[] addenda = new Object[1];
					addenda[0] = _model;
					parameter.setParameters(addenda);
				} else
					return;
			} else
				return;
						
		} else if (cmd.equals(ActionCommandText.BTN_FRM_FRM_UPD_BAS_TREE))	 {
			DesktopPane			desktop		= _view.getDesktop();
			TabbedPaneForm		tabbedPane	= (TabbedPaneForm)((FormFrame)desktop.getFormFrame()).getTabbedPane();
			ArrayList<Object>	resultSet	= tabbedPane.getSearchTab().getResultSet();
			
			addToBasket(resultSet);
		} else if (cmd.equals(ActionCommandText.BTN_DIA_SEA_UPD_BAS_TREE))	 {
			_view.showWaitingCursor(); 
			ArrayList<Object>	resultSet	= search.getResultSet();
			
			addToBasket(resultSet);
			_view.showDefaultCursor(); 
		} else if (cmd.equals(ActionCommandText.BTN_TAB_COM_VAL_REFILL))	 {
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_TAB_COM_VAL_REFILL);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
			
		/* Handle actions from file menu: */
		} else if (cmd.equals(ActionCommandText.CMD_FILE_EXIT)) {
			exit();
			
			/* handle cancel option: */
			return;
						
		/* Handle actions from edit menu / tool bar: */
		} else if (cmd.equals(ActionCommandText.CMD_EDIT_UNDO)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_EDIT_UNDO);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_EDIT_REDO)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_EDIT_REDO);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_EDIT_CUT)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_EDIT_CUT);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_EDIT_COPY)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_EDIT_COPY);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_EDIT_PASTE)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_EDIT_PASTE);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_EDIT_REMOVE)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_EDIT_REMOVE);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
			
		/* Handle actions from search menu: */
		} else if (cmd.equals(ActionCommandText.CMD_SRH_SEARCH)) {
			search	= new SearchDialog(this, this, _view.getLocale(), _view.getFont());	
						
			search.goSearch();
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_SRH_SEARCH);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
			
		/* Handle actions from project menu / tool bar: */
		} else if (cmd.equals(ActionCommandText.CMD_PRJ_NEW_PROJECT)) {
			boolean actionSuccess = newProject();
			
			if (!actionSuccess)
				return;
			
			/* Activity Log: */
			ActivityLog log = new ActivityLog();
			log.setWho(_model.getUser());
			log.setWhat(ActionCommandText.CMD_PRJ_NEW_PROJECT);
			log.setDetails(EMPTY);
			log.entityStore(_connection);
			
			_model.setActLogNewProject(log);
			/* Activity Log End*/
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_PRJ_NEW_PROJECT);
			Object[] addenda = new Object[2];
			addenda[0] = _model;
			addenda[1] = _view.getLocale();
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_PRJ_OPEN_PROJECT)) {
			ArrayList<Project> newList = new ArrayList<Project>();
			newList.addAll(_user.getUnfinProjects());
			newList.addAll(Project.getAllFinProjects(_connection));
			int	projectID	= _view.showOpenProjectDialog(newList);
			
			/* QuickCharmStats only: */
			if (checkProProject(projectID)) {
				ResourceBundle resourceBundle	= ResourceBundle.getBundle(BUNDLE, _view.getLocale());
				JOptionPane.showMessageDialog(
						_view.getAppFrame(), resourceBundle.getString(WRONG_APPLICATION), "", JOptionPane.ERROR_MESSAGE);
				
				return;
			}
			/* QuickCharmStats only */
			
			if (!checkAppVersion(projectID)) {
				ResourceBundle resourceBundle	= ResourceBundle.getBundle(BUNDLE, _view.getLocale());
				JOptionPane.showMessageDialog(
						_view.getAppFrame(), resourceBundle.getString(WRONG_VERSION), "", JOptionPane.ERROR_MESSAGE);
				
				return;
			}
			
			if (projectID != -1) {
				openProject(projectID);
			
				/* Activity Log: */
				int		openProjectID	= _model.getProject().getEntityID();
				String	openProjectName	= _model.getProject().getName();
				
				ActivityLog log = new ActivityLog();
				log.setWho(_model.getUser());
				log.setWhat(ActionCommandText.CMD_PRJ_OPEN_PROJECT);
				log.setDetails(String.valueOf(openProjectID) +":"+ openProjectName);
				log.entityStore(_connection);
				/* Activity Log End*/
				
				parameter = new IdentifiedParameter(ActionCommandID.CMD_PRJ_OPEN_PROJECT);
				Object[] addenda = new Object[1];
				addenda[0] = _model;
				parameter.setParameters(addenda); 
			} else
				return;
		} else if (cmd.equals(ActionCommandText.CMD_PRJ_CLOSE)) {
			/* Activity Log (Part 1): */
			int		closeProjectID		= _model.getProject().getEntityID();
			String	closeProjectName	= _model.getProject().getName();
			/* Activity Log (Part 1) End*/
			
			if (!closeProject(false))
				return;
			
			_model.setIsBrowsing(true);
			
			/* Activity Log (Part 2): */
			ActivityLog log = new ActivityLog();
			log.setWho(_model.getUser());
			log.setWhat(ActionCommandText.CMD_PRJ_CLOSE);
			log.setDetails(String.valueOf(closeProjectID) +":"+ closeProjectName);
			log.entityStore(_connection);
			/* Activity Log (Part 2) End*/
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_PRJ_CLOSE);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_PRJ_CLOSE_ALL)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_PRJ_CLOSE_ALL);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_PRJ_SAVE)) {
			saveProject();
			
			/* Activity Log: */
			int		saveProjectID	= _model.getProject().getEntityID();
			String	saveProjectName	= _model.getProject().getName();
			
			ActivityLog log = new ActivityLog();
			log.setWho(_model.getUser());
			log.setWhat(ActionCommandText.CMD_PRJ_SAVE);
			log.setDetails(String.valueOf(saveProjectID) +":"+ saveProjectName);
			log.entityStore(_connection);
			
			if (_model.getActLogNewProject() != null) {
				_model.getActLogNewProject().setDetails(String.valueOf(saveProjectID) +":"+ saveProjectName);
				_model.getActLogNewProject().entityStore(_connection);
				
				_model.setActLogNewProject(null);
			}
			/* Activity Log End*/
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_PRJ_SAVE);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_PRJ_SAVE_AS)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_PRJ_SAVE_AS);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_PRJ_SAVE_ALL)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_PRJ_SAVE_ALL);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_PRJ_FINISH)) {
			finishProject();
			
			/* Activity Log: */
			int		finProjectID	= _model.getProject().getEntityID();
			String	finProjectName	= _model.getProject().getName();
			
			ActivityLog log = new ActivityLog();
			log.setWho(_model.getUser());
			log.setWhat(ActionCommandText.CMD_PRJ_FINISH);
			log.setDetails(String.valueOf(finProjectID) +":"+ finProjectName);
			log.entityStore(_connection);
			/* Activity Log End*/
			
    		_model.getUser().setFinProjects(this.getFinProjects(_connection, _model.getUser()));
    		_model.getUser().setUnfinProjects(this.getUnfinProjects(_connection, _model.getUser()));
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_PRJ_FINISH);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_PRJ_REMOVE)) {
			boolean removed = removeProject();
		   	
			if (!removed)
				return;
			
			ActivityLog log = new ActivityLog();
			log.setWho(_model.getUser());
			log.setWhat(ActionCommandText.CMD_PRJ_REMOVE);
			log.setDetails(String.valueOf(_model.getProject().getEntityID()) +":"+ _model.getProject().getName());
			log.entityStore(_connection);
			/* Activity Log End*/
			
			closeProject(true);
			
    		_model.getUser().setFinProjects(this.getFinProjects(_connection, _model.getUser()));
    		_model.getUser().setUnfinProjects(this.getUnfinProjects(_connection, _model.getUser()));
						
			parameter = new IdentifiedParameter(ActionCommandID.CMD_PRJ_REMOVE);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);	
			
		} else if (cmd.equals(ActionCommandText.CMD_PRJ_ADD_PARTICIPANT)) {
			Participant newParticipant = addParticipant(); 
			
			/* Activity Log: */
			int		partProjectID	= _model.getProject().getEntityID();
			String	partProjectName	= _model.getProject().getName();
			
			ActivityLog log = new ActivityLog();
			log.setWho(_model.getUser());
			log.setWhat(ActionCommandText.CMD_PRJ_ADD_PARTICIPANT);
			log.setDetails(String.valueOf(partProjectID) +":"+ partProjectName +" - "+ newParticipant.getUserID() +":"+ newParticipant.getUser().getName() +" / "+ newParticipant.getRoleID()); 
			log.entityStore(_connection);
			/* Activity Log End*/
						
			parameter = new IdentifiedParameter(ActionCommandID.CMD_PRJ_ADD_PARTICIPANT);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_PRJ_EDIT_PARTICIPANT)) {
			Participant part = new Participant(); part.setProject(_model.getProject());
			List<Participant> parts = new ArrayList<Participant>();
			
			ArrayList<Integer> par_list = part.listParticipants(_connection);
			Iterator<Integer> iter_par = par_list.iterator();
			while (iter_par.hasNext()) {
				Participant participant = new Participant();
				participant.setEntityID(iter_par.next());
				participant.entityLoad(_connection);
				
				participant.setProject(_model.getProject());
				
				parts.add(participant);
			}
			
			String participantsOutput = EMPTY;  
			int	partID = 0;
			while (partID != -1) {
				partID = _view.showOpenParticipantDialog(parts);
			
				if (partID > 0) {
					Participant par = new Participant(); 
					par.setEntityID(partID);
					par.entityLoad(_connection);
					
					editParticipant(par);
					
					boolean storeStatus = par.entityStore(_connection);
					
					if (storeStatus) {
						try {
							_connection.commit();
						} catch (SQLException e) {
							e.printStackTrace();
						}
						
						participantsOutput += par.getUserID() +":"+ par.getUser().getName() +" / "+ par.getRoleID() +"; "; 
					}
				}					
			} 
			
			/* Activity Log: */
			int		partProjectID	= _model.getProject().getEntityID();
			String	partProjectName	= _model.getProject().getName();
			
			ActivityLog log = new ActivityLog();
			log.setWho(_model.getUser());
			log.setWhat(ActionCommandText.CMD_PRJ_EDIT_PARTICIPANT);
			log.setDetails(String.valueOf(partProjectID) +":"+ partProjectName +" - "+ participantsOutput); 
			log.entityStore(_connection);
			/* Activity Log End*/
						
			parameter = new IdentifiedParameter(ActionCommandID.CMD_PRJ_EDIT_PARTICIPANT);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);	
			
			
		/* Handle actions from basket menu: */
		} else if (cmd.equals(ActionCommandText.CMD_BSKT_NEW_BASKET)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_BSKT_NEW_BASKET);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_BSKT_OPEN_BASKET)) {
			openBasket();
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_BSKT_OPEN_BASKET);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_BSKT_CLOSE)) {
			closeBasket();
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_BSKT_CLOSE);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_BSKT_CLOSE_ALL)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_BSKT_CLOSE_ALL);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_BSKT_SAVE)) {
			saveBasket(_connection, _user);
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_BSKT_SAVE);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_BSKT_SAVE_AS)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_BSKT_SAVE_AS);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_BSKT_SAVE_ALL)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_BSKT_SAVE_ALL);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_BSKT_EMPTY)) {
			int choice = _view.clearBasketDialog();
			
			switch (choice) {
				case 0:
					_view.showWaitingCursor();
					_model.getUser().getBasket().clearContents();
					clearBasket();
					_view.showDefaultCursor();
					break;
				case 1:
				default:
					return;
			}
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_BSKT_EMPTY);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_BSKT_EMPTY_TEMP_BASKET)) {
			_view.showWaitingCursor();
			_model.getUser().getBasket().clearContents();
			_view.showDefaultCursor();
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_BSKT_EMPTY_TEMP_BASKET);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
			
		/* Handle actions from user menu: */
		} else if (cmd.equals(ActionCommandText.CMD_USER_LOGIN)) {
			if (!login())
				return;
			
			/* Activity Log: */
			int		loginUserID		= _model.getUser().getEntityID();
			String	loginUserName	= _model.getUser().getName();
			
			ActivityLog log = new ActivityLog();
			log.setWho(_model.getUser());
			log.setWhat(ActionCommandText.CMD_USER_LOGIN);
			log.setDetails(String.valueOf(loginUserID) +":"+ loginUserName);
			log.entityStore(_connection);
			/* Activity Log End*/
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_USER_LOGIN);
			Object[] addenda = new Object[2];
			addenda[0] = _model;
			addenda[1] = _view.getLocale();
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_USER_LOGOFF)) {
			/* Activity Log: */
			int		logoffUserID	= _model.getUser().getEntityID();
			String	logoffUserName	= _model.getUser().getName();
			
			ActivityLog log = new ActivityLog();
			log.setWho(_model.getUser());
			log.setWhat(ActionCommandText.CMD_USER_LOGOFF);
			log.setDetails(String.valueOf(logoffUserID) +":"+ logoffUserName);
			log.entityStore(_connection);
			/* Activity Log End*/
			
			logoff();
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_USER_LOGOFF);
			Object[] addenda = new Object[2];
			addenda[0] = _model;
			addenda[1] = _view.getLocale();
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_USER_ADD_NEW_USER)) {
			User newUser = addUser(); 
			
			/* Activity Log: */
			ActivityLog log = new ActivityLog();
			
			int		newUserID	= newUser.getEntityID();
			String	newUserName	= newUser.getName();
			int		roleID		= newUser.getUserRole().getID();
			
			log.setWho(_model.getUser());
			log.setWhat(ActionCommandText.CMD_USER_ADD_NEW_USER);
			log.setDetails(String.valueOf(newUserID) +":"+ newUserName +" / "+ String.valueOf(roleID)); 
			log.entityStore(_connection);
			/* Activity Log End*/
						
			parameter = new IdentifiedParameter(ActionCommandID.CMD_USER_ADD_NEW_USER);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_USER_EDIT_USER)) {
			_view.showWaitingCursor(); 
			List<User> users = User.getAllUsers(_connection);
			_view.showDefaultCursor(); 
			
			String usersOutput = EMPTY; 
			int	userID = 0;
			while (userID != -1) {
				userID = _view.showOpenUserDialog(users);
			
				if (userID > 0) {
					User usr = new User(); 
					usr.setEntityID(userID);
					usr.entityLoad(_connection);
					
					editUser(usr);
					
					boolean storeStatus = usr.editStore(_connection);
					
					if (storeStatus) {
						try {
							_connection.commit();
						} catch (SQLException e) {
							e.printStackTrace();
						}
						
						usersOutput += String.valueOf(usr.getEntityID()) +":"+ usr.getName() +" / "+ String.valueOf(usr.getUserRole().getID()) +"; "; 
					}
				}					
			} 
			
			/* Activity Log: */
			ActivityLog log = new ActivityLog();
			log.setWho(_model.getUser());
			log.setWhat(ActionCommandText.CMD_USER_EDIT_USER);
			log.setDetails(usersOutput); 
			log.entityStore(_connection);
			/* Activity Log End*/
						
			parameter = new IdentifiedParameter(ActionCommandID.CMD_USER_EDIT_USER);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_USER_ADD_NEW_PERSON)) {
			Person newPerson = addPerson(); 
						
			/* Activity Log: */
			int		newPersonID		= newPerson.getEntityID();
			String	newPersonName	= newPerson.getName();
			int		roleID			= newPerson.getRole().getID();
			
			ActivityLog log = new ActivityLog();
			log.setWho(_model.getUser());
			log.setWhat(ActionCommandText.CMD_USER_ADD_NEW_PERSON);
			log.setDetails(String.valueOf(newPersonID) +":"+ newPersonName +" / "+ String.valueOf(roleID)); 
			log.entityStore(_connection);
			/* Activity Log End*/
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_USER_ADD_NEW_PERSON);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_USER_EDIT_PERSON)) {
			_view.showWaitingCursor(); 
			List<Person> prsns = Person.getAllPersons(_connection);
			_view.showDefaultCursor(); 
			
			String personsOutput = EMPTY; 
			int	prsnID = 0;
			while (prsnID != -1) {
				prsnID = _view.showOpenPersonDialog(prsns);
			
				if (prsnID > 0) {
					Person prsn = new Person(); 
					prsn.setEntityID(prsnID);
					prsn.entityLoad(_connection);
					
					editPerson(prsn);
					
					boolean storeStatus = prsn.entityStore(_connection);
					
					if (storeStatus) {
						try {
							_connection.commit();
						} catch (SQLException e) {
							e.printStackTrace();
						}
						
						personsOutput += String.valueOf(prsn.getEntityID()) +":"+ prsn.getName() +" / "+ String.valueOf(prsn.getRole().getID()) +"; "; 
					}
				}					
			} 
			
			/* Activity Log: */
			ActivityLog log = new ActivityLog();
			log.setWho(_model.getUser());
			log.setWhat(ActionCommandText.CMD_USER_EDIT_PERSON);
			log.setDetails(personsOutput); 
			log.entityStore(_connection);
			/* Activity Log End*/
						
			parameter = new IdentifiedParameter(ActionCommandID.CMD_USER_EDIT_PERSON);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_USER_CHANGE_PASSWORD)) {
			changePassword();
			
			/* Activity Log: */
			ActivityLog log = new ActivityLog();
			log.setWho(_model.getUser());
			log.setWhat(ActionCommandText.CMD_USER_CHANGE_PASSWORD);
			log.setDetails(EMPTY);
			log.entityStore(_connection);
			/* Activity Log End*/
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_USER_CHANGE_PASSWORD);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
			
		/* Handle actions from extra menu / tool bar: */
		} else if (cmd.equals(ActionCommandText.CMD_EXTRA_INC_FONT_SIZE)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_EXTRA_INC_FONT_SIZE);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_EXTRA_DEC_FONT_SIZE)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_EXTRA_DEC_FONT_SIZE);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_EXTRA_LANGUAGE_ENGLISH)) {
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_EXTRA_LANGUAGE_ENGLISH);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_EXTRA_LANGUAGE_GERMAN)) {
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_EXTRA_LANGUAGE_GERMAN);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_EXTRA_RELEASE_LOCK)) {
			List<Integer> projects	= new ArrayList<Integer>();
			List<Integer> users		= new ArrayList<Integer>();
			
			_model.getProject().getEditedProjects(_connection, projects, users);
			
			Project helpProject = null; 
			User	helpUser = null; 
			if(projects.size() > 0) {
				int	pairing_index	= _view.showReleaseLockDialog(projects, users);
				
				if (pairing_index > 0) {
					helpProject = new Project(); 
					helpProject.setEntityID(projects.get(pairing_index-1));
					helpProject.entityLoad(_connection); 

					helpUser	= new User(); 
					helpUser.setEntityID(users.get(pairing_index-1));
					helpUser.entityLoad(_connection); 
					
					helpProject.removeEditedByUser(_connection, helpUser, projects.get(pairing_index-1));					
				}
			} else {
				ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, _view.getLocale());
				
				JOptionPane.showMessageDialog(_view.getAppFrame(), bundle.getString(NO_EDITED_PROJECTS));
			}
			
			/* Activity Log: */
			if (helpProject != null) { 
				ActivityLog log = new ActivityLog();
				
				log.setWho(_model.getUser());
				log.setWhat(ActionCommandText.CMD_EXTRA_RELEASE_LOCK);
				log.setDetails(helpProject.getEntityID() +":"+ helpProject.getName() +" - "+ helpUser.getEntityID() +":"+ helpUser.getName()); 
				log.entityStore(_connection);
			} 
			/* Activity Log End*/
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_EXTRA_RELEASE_LOCK);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_EXTRA_UNFINISH)) {
			/* Get Projects from DataBase */
			ArrayList<Object> objectsList;
			 			
			String unfinishedProjectsOutput = EMPTY; 
			int	projectID = 0;
			while (projectID != -1) {
				objectsList = getBasketContentsByType(Project.class);
				 
				/* Prepare parameter for call */
				List<Project> projects = new ArrayList<Project>();
				if (objectsList != null) {
					Iterator<Object> iterator = objectsList.iterator();
					
					while (iterator.hasNext()) {
						Project project = (Project)iterator.next();
						
						projects.add(project);
					}
				}
				projectID	= _view.showUnfinishProjectDialog(projects);
							
				if (projectID > 0) {
					boolean ok = Project.unfinish(projectID, _connection);
					_model.getUser().getBasket().removeContentByClassAndID(Project.class, projectID);
					
					Project helpProject = new Project(projectID);
					helpProject.entityLoad(_connection);
					unfinishedProjectsOutput += helpProject.getEntityID() +":"+ helpProject.getName() +"; ";
					
					parameter = new IdentifiedParameter(ActionCommandID.CMD_EXTRA_UNFINISH);
					Object[] addenda = new Object[1];
					addenda[0] = _model;
					parameter.setParameters(addenda);
					
					setChanged();	
					notifyObservers(parameter);
				}					
			} 
			
			/* Activity Log: */
			ActivityLog log = new ActivityLog();
			
			log.setWho(_model.getUser());
			log.setWhat(ActionCommandText.CMD_EXTRA_UNFINISH);
			log.setDetails(unfinishedProjectsOutput); 
			log.entityStore(_connection);
			/* Activity Log End*/
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_EXTRA_UNFINISH);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_EXTRA_CHANGE_PASSWORD)) {
			User changePasswordUser = overwritePassword(); 
			
			/* Activity Log: */
			if (changePasswordUser != null) { 
				changePasswordUser.entityLoad(_connection); 
				
				ActivityLog log = new ActivityLog();
				log.setWho(_model.getUser());
				log.setWhat(ActionCommandText.CMD_EXTRA_CHANGE_PASSWORD);
				log.setDetails(changePasswordUser.getEntityID() +":"+ changePasswordUser.getName()); 
				log.entityStore(_connection);
			} 
			/* Activity Log End*/
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_EXTRA_CHANGE_PASSWORD);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_EXTRA_IMPORT_VARIABLE)) {
			ImportStatistics importStatistics = importVariable();  
			
			if (!(importStatistics != null)) 
				return;
			
			/* Activity Log: */
			ActivityLog log = new ActivityLog();
			
			log.setWho(_model.getUser());
			log.setWhat(ActionCommandText.CMD_EXTRA_IMPORT_VARIABLE);
			
			String logOutput = importStatistics.getImportedDatafile() +" - ";
			Iterator<Attributes> it_import_vars = importStatistics.getImported().iterator();
			while(it_import_vars.hasNext()) {
				Variable var = (Variable)it_import_vars.next();
				
				logOutput += var.getEntityID() +":"+ var.getName() +"/"+ var.getLabel() +"; ";
			}
			log.setDetails(logOutput);

			log.entityStore(_connection);
			/* Activity Log End*/
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_DATA_IMPORT_VARIABLE);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_EXTRA_EDIT_VARIABLE)) {
			boolean						useDefault		= false;
			boolean 					setDefault		= false;
			Study						defaultStudy	= new Study();
			
			/* Get Variable from Basket */
			ArrayList<Object> objectsList = getBasketContentsByType(Variable.class);
			 
			/* Prepare parameter for call */
			List<Variable> variables = new ArrayList<Variable>();
			if (objectsList != null) {
				Iterator<Object> iterator = objectsList.iterator();
				
				while (iterator.hasNext()) {
					Variable variable = (Variable)iterator.next();
					
					variables.add(variable);
				}
			}
			
			String logOutput = EMPTY; 
			int	variableID = 0;
			while (variableID != -1) {
				variableID = _view.showOpenVariableDialog(variables);
			
				if (variableID > 0) {
					Variable var = new Variable(); 
					var.setEntityID(variableID);
					var.entityLoad(_connection);
					
					CharacteristicLink		charLink;
					Characteristics			characteristic = null;
					
					charLink = new CharacteristicLink(var);
					ArrayList<Integer>				charlist	= charLink.loadLinks(_connection);
					ArrayList<CharacteristicLink>	charLinks	= new ArrayList<CharacteristicLink>();
						
					Boolean loadStatus = true;
					if (!charlist.isEmpty()) {
						Iterator<Integer> iterator = charlist.iterator();
							
						while (iterator.hasNext()) {
							Integer id = iterator.next();
								
							charLink = new CharacteristicLink();
							charLink.setAttribute(var);
							charLink.setEntityID(id);
							if (loadStatus)
								loadStatus = charLink.entityLoad(_connection);
								
							characteristic = new Value();
							characteristic.setEntityID(charLink.getDsCharEntryID());
							
							if (loadStatus)
								loadStatus = ((Value)characteristic).entityLoad(_connection);
								
							if (loadStatus) {								
								charLink.setCharacteristic(characteristic);
								charLinks.add(charLink);
							}
						}
					}
										
					if (useDefault) {
						Question	quest = var.getSource();
						Study		study = quest.getSource();
						
						study.setTitle(defaultStudy.getTitle());
						study.setDOI(defaultStudy.getDOI());
						study.setStudyArea(defaultStudy.getStudyArea());
						study.setDateOfCollection(defaultStudy.getDateOfCollection());
						study.setPopulation(defaultStudy.getPopulation());
						study.setSelection(defaultStudy.getSelection());
						study.setCollectionMethod(defaultStudy.getCollectionMethod());
						study.setCollectors(defaultStudy.getCollectors());
						study.setSourceFile(defaultStudy.getSourceFile());
						study.setDataset(defaultStudy.getDataset());
					}
					
					/* Check if Variable is already in use */
					_view.showWaitingCursor();
					String fullSql = Variable.getDraftSQLForAllProjects(var.getEntityID());
					int classID = EntityType.PROJECTS.getID();
					ArrayList<Object> resultSet =
						CStatsCtrl.search(classID, fullSql);
					_view.showDefaultCursor();
		
					ArrayList<Project> resultSetItemChain = new ArrayList<Project>();
					if ((resultSet != null) && 
							(resultSet.size() > 0)) {
						Iterator<Object> iterator_o = resultSet.iterator();
						while (iterator_o.hasNext()) {
							Object resultObject = iterator_o.next();
							
							Project project = (Project)resultObject;
							resultSetItemChain.add(project);
						}
						
						warnEditingVariable(resultSetItemChain);
					}

					
					setDefault = editVariable(var, charLinks);
					
					if (setDefault) {
						Question	quest = var.getSource();
						Study		study = quest.getSource();
						
						defaultStudy.setTitle(study.getTitle());
						defaultStudy.setDOI(study.getDOI());
						defaultStudy.setStudyArea(study.getStudyArea());
						defaultStudy.setDateOfCollection(study.getDateOfCollection());
						defaultStudy.setPopulation(study.getPopulation());
						defaultStudy.setSelection(study.getSelection());
						defaultStudy.setCollectionMethod(study.getCollectionMethod());
						defaultStudy.setCollectors(study.getCollectors());
						defaultStudy.setSourceFile(study.getSourceFile());
						defaultStudy.setDataset(study.getDataset());
						
						useDefault = true;
					}
					
					/* Save the Variable */
					boolean storeStatus = var.storeImport(_connection);
					logOutput += var.getEntityID() +":"+ var.getName() +"/"+ var.getLabel() +"; "; 
						    							    						
					if (storeStatus) {
						Iterator<CharacteristicLink> charLinksIter = charLinks.iterator();
						while (charLinksIter.hasNext()) {
							charLink = charLinksIter.next();
							
							if (charLink.getAttribute().equals(var)) {
								charLink.getCharacteristic().entityStore(_connection);
								charLink.entityStore(_connection);
							}
						}
						
						try {
							_connection.commit();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}					
			} 
			
			/* Activity Log: */
			ActivityLog log = new ActivityLog();
			
			log.setWho(_model.getUser());
			log.setWhat(ActionCommandText.CMD_EXTRA_EDIT_VARIABLE);
			log.setDetails(logOutput); 
			log.entityStore(_connection);
			/* Activity Log End*/
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_DATA_EDIT_VARIABLE);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_EXTRA_IMPORT_MEASURE)) {
			ImportStatistics importStatistics = importMeasure(); 
			
			if (!(importStatistics != null)) 
				return;
			
			/* Activity Log: */
			ActivityLog log = new ActivityLog();
			
			log.setWho(_model.getUser());
			log.setWhat(ActionCommandText.CMD_EXTRA_IMPORT_MEASURE);

			String logOutput = importStatistics.getImportedDatafile() +" - ";
			Iterator<Attributes> it_import_meas = importStatistics.getImported().iterator();
			while(it_import_meas.hasNext()) {
				Measurement mea = (Measurement)it_import_meas.next();
				
				logOutput += mea.getEntityID() +":"+ mea.getName() +"/"+ mea.getLabel() +"; ";
			}
			log.setDetails(logOutput);
			
			log.entityStore(_connection);
			/* Activity Log End*/
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_DATA_IMPORT_MEASURE);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_EXTRA_EDIT_MEASURE)) {
			/* Get Measurement from Basket */
			ArrayList<Object> objectsList = getBasketContentsByType(Measurement.class);
			 
			/* Prepare parameter for call */
			List<Measurement> measures = new ArrayList<Measurement>();
			if (objectsList != null) {
				Iterator<Object> iterator = objectsList.iterator();
				
				while (iterator.hasNext()) {
					Measurement measurement = (Measurement)iterator.next();
					
					measures.add(measurement);
				}
			}
			
			String logOutput = EMPTY; 
			int	measureID = 0;
			while (measureID != -1) {
				measureID = _view.showOpenMeasureDialog(measures);
			
				if (measureID > 0) {
					Measurement mea = new Measurement(); 
					mea.setEntityID(measureID);
					mea.entityLoad(_connection);
					
					CharacteristicLink		charLink;
					Characteristics			characteristic = null;
					
					charLink = new CharacteristicLink(mea);
					ArrayList<Integer>				charlist	= charLink.loadLinks(_connection);
					ArrayList<CharacteristicLink>	charLinks	= new ArrayList<CharacteristicLink>();
						
					Boolean loadStatus = true;
					if (!charlist.isEmpty()) {
						Iterator<Integer> iterator = charlist.iterator();
							
						while (iterator.hasNext()) {
							Integer id = iterator.next();
								
							charLink = new CharacteristicLink();
							charLink.setAttribute(mea);
							charLink.setEntityID(id);
							if (loadStatus)
								loadStatus = charLink.entityLoad(_connection);
								
							characteristic = new Category();
							characteristic.setEntityID(charLink.getDsCharEntryID());
							
							if (loadStatus)
								loadStatus = ((Category)characteristic).entityLoad(_connection);
								
							if (loadStatus) {								
								charLink.setCharacteristic(characteristic);
								charLinks.add(charLink);
							}
						}
					}
					
					/* Check if Measure is already in use */
					_view.showWaitingCursor();
					String fullSql = Measurement.getDraftSQLForAllProjects(mea.getEntityID());
					int classID = EntityType.PROJECTS.getID();
					ArrayList<Object> resultSet =
						CStatsCtrl.search(classID, fullSql);
					_view.showDefaultCursor();
		
					ArrayList<Project> resultSetItemChain = new ArrayList<Project>();
					if ((resultSet != null) && 
							(resultSet.size() > 0)) {
						Iterator<Object> iterator_o = resultSet.iterator();
						while (iterator_o.hasNext()) {
							Object resultObject = iterator_o.next();
							
							Project project = (Project)resultObject;
							resultSetItemChain.add(project);
						}
						
						warnEditingMeasurement(resultSetItemChain);
					}

					
					editMeasurement(mea, charLinks);
					
					boolean storeStatus = mea.storeImport(_connection);
					logOutput += mea.getEntityID() +":"+ mea.getName() +"/"+ mea.getLabel() +"; "; 
					
					if (storeStatus) {
						Iterator<CharacteristicLink> charLinksIter = charLinks.iterator();
						while (charLinksIter.hasNext()) {
							charLink = charLinksIter.next();
							
							if (charLink.getAttribute().equals(mea)) {
								charLink.getCharacteristic().entityStore(_connection);
								charLink.entityStore(_connection);
							}
						}
												
						try {
							_connection.commit();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}					
			} 
			
			/* Activity Log: */
			ActivityLog log = new ActivityLog();
			
			log.setWho(_model.getUser());
			log.setWhat(ActionCommandText.CMD_EXTRA_EDIT_MEASURE);
			log.setDetails(logOutput); 
			log.entityStore(_connection);
			/* Activity Log End*/
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_DATA_EDIT_MEASURE);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_EXTRA_EXPORT_VARIABLE)) {
			boolean exportSuccess = exportVariable();
			
			if (!exportSuccess)
				return;
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_DATA_EXPORT_VARIABLE);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_EXTRA_EXPORT_SYNTAX)) {
			ExportSyntaxDialog esd = new ExportSyntaxDialog(_model, _view.getLocale(), _view.getFont(), this);
			
//			parameter = new IdentifiedParameter(ActionCommandID.CMD_EXTRA_EXPORT_SYNTAX, null);
//			Object[] addenda = new Object[1];
//			addenda[0] = null;
//			parameter.setAddenda(addenda);
						
			return;
		} else if (cmd.equals(ActionCommandText.CMD_EXTRA_REMOVE_VARIABLE)) {
			RemovalStatistics statistics = new RemovalStatistics();
			statistics.setRemovingAdmin(_user);
			
			/* Get Variable from Basket */
			ArrayList<Object> objectsList = getBasketContentsByType(Variable.class);
			 
			/* Prepare parameter for call */
			List<Variable> variables = new ArrayList<Variable>();
			if (objectsList != null) {
				Iterator<Object> iterator = objectsList.iterator();
				
				while (iterator.hasNext()) {
					Variable variable = (Variable)iterator.next();
					
					variables.add(variable);
					statistics.addSelection(variable); 
				}
			}
			
			int	variableID = 0;
			while (variableID != -1) {
				variableID = _view.showRemoveVariableDialog(variables);
			
				if (variableID > 0) {
					Variable var = new Variable(); 
					var.setEntityID(variableID);
					var.entityLoad(_connection);
					
					/* Check if Variable is already in use */
					_view.showWaitingCursor();
					String fullSql = Variable.getDraftSQLForAllProjects(var.getEntityID());
					int classID = EntityType.PROJECTS.getID();
					ArrayList<Object> resultSet =
						CStatsCtrl.search(classID, fullSql);
					_view.showDefaultCursor();
		
					ArrayList<Project> resultSetItemChain = new ArrayList<Project>();
					if ((resultSet != null) && 
							(resultSet.size() > 0)) {
						/* Variable already used in projects */
						Iterator<Object> iterator_o = resultSet.iterator();
						while (iterator_o.hasNext()) {
							Object resultObject = iterator_o.next();
							
							Project project = (Project)resultObject;
							resultSetItemChain.add(project);
						}
						
						warnEditingVariable(resultSetItemChain);
						
						ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, _view.getLocale());
						JOptionPane.showMessageDialog(_view.getAppFrame(),
								bundle.getString(USED_NO_REMOVE), bundle.getString(WARNING), JOptionPane.ERROR_MESSAGE);
						
						statistics.addExcluded(var);
						
						continue;
					}
					
					ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, _view.getLocale());
					String message = bundle.getString(CONFIRM_REMOVE) + var.getName() +": "+ var.getLabel();
					String question = bundle.getString(QUESTION);
					final String[] DIALOG_BUTTONS_TITLES	= {bundle.getString(YES_REMOVE), bundle.getString(DONT_REMOVE), bundle.getString(ABORT_REMOVE)};
	    			int remove_it = JOptionPane.showOptionDialog(_view.getAppFrame(),
	    					message,
	    					question,
	    					JOptionPane.YES_NO_CANCEL_OPTION,
	    					JOptionPane.QUESTION_MESSAGE,
	    					null,
	    					DIALOG_BUTTONS_TITLES,
	    					DIALOG_BUTTONS_TITLES[2]);
	    			
    				switch (remove_it) {
    					case JOptionPane.OK_OPTION:
    						break;
    					case JOptionPane.NO_OPTION:
    						continue;
    					case JOptionPane.CANCEL_OPTION:
    						showRemovalReport(statistics);
    						return;
    				}
    				
					/* Load the connected Characteristics */
					CharacteristicLink		charLink;
					Characteristics			characteristic = null;
					
					charLink = new CharacteristicLink(var);
					ArrayList<Integer>				charlist	= charLink.loadLinks(_connection);
					ArrayList<CharacteristicLink>	charLinks	= new ArrayList<CharacteristicLink>();
						
					Boolean loadStatus = true;
					if (!charlist.isEmpty()) {
						Iterator<Integer> iterator = charlist.iterator();
							
						while (iterator.hasNext()) {
							Integer id = iterator.next();
								
							charLink = new CharacteristicLink();
							charLink.setAttribute(var);
							charLink.setEntityID(id);
							if (loadStatus)
								loadStatus = charLink.entityLoad(_connection);
								
							characteristic = new Value();
							characteristic.setEntityID(charLink.getDsCharEntryID());
							
							if (loadStatus)
								loadStatus = ((Value)characteristic).entityLoad(_connection);
								
							if (loadStatus) {								
								charLink.setCharacteristic(characteristic);
								charLinks.add(charLink);
							}
						}
					}
																
					/* Remove Variable from DB*/
					boolean removeStatus = removeVariable(var, charLinks);
					
					if (removeStatus) {
						JOptionPane.showMessageDialog(_view.getAppFrame(),
								bundle.getString(IS_REMOVED) + var.getName() +": "+ var.getLabel(), bundle.getString(INFORMATION), JOptionPane.INFORMATION_MESSAGE);
						
						statistics.addRemoved(var); 
						
						/* Removed from DB, so remove from list */
						ArrayList<Variable> remVarsFromList = new ArrayList<Variable>(); 
						Iterator<Variable> varIter = variables.iterator();
						while(varIter.hasNext()) {
							Variable varFromList = varIter.next();
							
							
							if (var.getEntityID() == varFromList.getEntityID())
								remVarsFromList.add(varFromList); 
						}
										
						Iterator<Variable> it_var = remVarsFromList.iterator();
						while (it_var.hasNext()) {
							Variable varFromList = it_var.next();
							
							variables.remove(varFromList);	
						}								
															
						while (_user.getBasket().removeContent(var));
					}
				}
			}

			showRemovalReport(statistics);
			
			/* Activity Log: */
			ActivityLog log = new ActivityLog();
			
			log.setWho(_model.getUser());
			log.setWhat(ActionCommandText.CMD_EXTRA_REMOVE_VARIABLE);
			
			String logOutput = EMPTY;
			Iterator<Attributes> it_removed_vars = statistics.getRemoved().iterator();
			while(it_removed_vars.hasNext()) {
				Variable var = (Variable)it_removed_vars.next();
				
				logOutput += var.getEntityID() +":"+ var.getName() +"/"+ var.getLabel() +"; ";
			}
			log.setDetails(logOutput);
			
			log.entityStore(_connection);
			/* Activity Log End*/
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_EXTRA_REMOVE_VARIABLE);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_EXTRA_REMOVE_MEASURE)) {
			RemovalStatistics statistics = new RemovalStatistics();
			statistics.setRemovingAdmin(_user);
			
			/* Get Measure from Basket */
			ArrayList<Object> objectsList = getBasketContentsByType(Measurement.class);
			 
			/* Prepare parameter for call */
			List<Measurement> measures = new ArrayList<Measurement>();
			if (objectsList != null) {
				Iterator<Object> iterator = objectsList.iterator();
				
				while (iterator.hasNext()) {
					Measurement measure = (Measurement)iterator.next();
					
					measures.add(measure);
					statistics.addSelection(measure); 
				}
			}
			
			int	measureID = 0;
			while (measureID != -1) {
				measureID = _view.showRemoveMeasureDialog(measures);
			
				if (measureID > 0) {
					Measurement mea = new Measurement(); 
					mea.setEntityID(measureID);
					mea.entityLoad(_connection);
					
					/* Check if Measure is already in use */
					_view.showWaitingCursor();
					String fullSql = Measurement.getDraftSQLForAllProjects(mea.getEntityID());
					int classID = EntityType.PROJECTS.getID();
					ArrayList<Object> resultSet =
						CStatsCtrl.search(classID, fullSql);
					_view.showDefaultCursor();
		
					ArrayList<Project> resultSetItemChain = new ArrayList<Project>();
					if ((resultSet != null) && 
							(resultSet.size() > 0)) {
						/* Variable already used in projects */
						Iterator<Object> iterator_o = resultSet.iterator();
						while (iterator_o.hasNext()) {
							Object resultObject = iterator_o.next();
							
							Project project = (Project)resultObject;
							resultSetItemChain.add(project);
						}
						
						warnEditingMeasurement(resultSetItemChain);
						
						ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, _view.getLocale());
						JOptionPane.showMessageDialog(_view.getAppFrame(),
								bundle.getString(USED_NO_REMOVE), bundle.getString(WARNING), JOptionPane.ERROR_MESSAGE);
						
						statistics.addExcluded(mea);
						
						continue;
					}
					
					ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, _view.getLocale());
					String message = bundle.getString(CONFIRM_REMOVE) + mea.getName() +": "+ mea.getLabel();
					String question = bundle.getString(QUESTION);
					final String[] DIALOG_BUTTONS_TITLES	= {bundle.getString(YES_REMOVE), bundle.getString(DONT_REMOVE), bundle.getString(ABORT_REMOVE)};
	    			int remove_it = JOptionPane.showOptionDialog(_view.getAppFrame(),
	    					message,
	    					question,
	    					JOptionPane.YES_NO_CANCEL_OPTION,
	    					JOptionPane.QUESTION_MESSAGE,
	    					null,
	    					DIALOG_BUTTONS_TITLES,
	    					DIALOG_BUTTONS_TITLES[2]);
	    			
    				switch (remove_it) {
    					case JOptionPane.OK_OPTION:
    						break;
    					case JOptionPane.NO_OPTION:
    						continue;
    					case JOptionPane.CANCEL_OPTION:
    						showRemovalReport(statistics);
    						return;
    				}
    				
					/* Load the connected Characteristics */
					CharacteristicLink		charLink;
					Characteristics			characteristic = null;
					
					charLink = new CharacteristicLink(mea);
					ArrayList<Integer>				charlist	= charLink.loadLinks(_connection);
					ArrayList<CharacteristicLink>	charLinks	= new ArrayList<CharacteristicLink>();
						
					Boolean loadStatus = true;
					if (!charlist.isEmpty()) {
						Iterator<Integer> iterator = charlist.iterator();
							
						while (iterator.hasNext()) {
							Integer id = iterator.next();
								
							charLink = new CharacteristicLink();
							charLink.setAttribute(mea);
							charLink.setEntityID(id);
							if (loadStatus)
								loadStatus = charLink.entityLoad(_connection);
								
							characteristic = new Category();
							characteristic.setEntityID(charLink.getDsCharEntryID());
							
							if (loadStatus)
								loadStatus = ((Category)characteristic).entityLoad(_connection);
								
							if (loadStatus) {								
								charLink.setCharacteristic(characteristic);
								charLinks.add(charLink);
							}
						}
					}
														
					/* Remove Measure from DB*/
					boolean removeStatus = removeMeasure(mea, charLinks);
																	
					if (removeStatus) {
						JOptionPane.showMessageDialog(_view.getAppFrame(),
								bundle.getString(IS_REMOVED) + mea.getName() +": "+ mea.getLabel(), bundle.getString(INFORMATION), JOptionPane.INFORMATION_MESSAGE);
						
						statistics.addRemoved(mea); 
						
						/* Removed from DB, so remove from list */
						ArrayList<Measurement> remMeasFromList = new ArrayList<Measurement>(); 
						Iterator<Measurement> meaIter = measures.iterator();
						while(meaIter.hasNext()) {
							Measurement meaFromList = meaIter.next();
																
							if (mea.getEntityID() == meaFromList.getEntityID())
								remMeasFromList.add(meaFromList); 
						}						
												
						Iterator<Measurement> it_mea = remMeasFromList.iterator();
						while (it_mea.hasNext()) {
							Measurement meaFromList = it_mea.next();
							
							measures.remove(meaFromList);	
						}								
															
						while (_user.getBasket().removeContent(mea));				
					}
				}
			}

			showRemovalReport(statistics);
			
			/* Activity Log: */
			ActivityLog log = new ActivityLog();
			log.setWho(_model.getUser());
			log.setWhat(ActionCommandText.CMD_EXTRA_REMOVE_MEASURE);

			String logOutput = EMPTY;
			Iterator<Attributes> it_removed_meas = statistics.getRemoved().iterator();
			while(it_removed_meas.hasNext()) {
				Measurement mea = (Measurement)it_removed_meas.next();
				
				logOutput += mea.getEntityID() +":"+ mea.getName() +"/"+ mea.getLabel() +"; ";
			}
			log.setDetails(logOutput);
					
			log.entityStore(_connection);
			/* Activity Log End*/
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_EXTRA_REMOVE_MEASURE);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
					
		/* Handle actions from help menu: */
		} else if (cmd.equals(ActionCommandText.CMD_HELP_HELP)) {
			/* DoNothing Yet */
			callBrowser(_view.getLocale(), "help.html");
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_HELP_HELP);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_HELP_HELP_ADD_PARTICIPANT)) {
			/* DoNothing Yet */
			callBrowser(_view.getLocale(), "help_menu_project_add_participant.html");
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_HELP_HELP_ADD_PARTICIPANT);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_HELP_HELP_ADD_PARTICIPANT_OPEN)) {
			/* DoNothing Yet */
			callBrowser(_view.getLocale(), "help_menu_project_add_participant_open.html");
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_HELP_HELP_ADD_PARTICIPANT_OPEN);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_HELP_HELP_EDIT_PARTICIPANT)) {
			/* DoNothing Yet */
			callBrowser(_view.getLocale(), "help_menu_project_edit_participant.html");
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_HELP_HELP_EDIT_PARTICIPANT);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_HELP_HELP_ADD_PERSON)) {
			/* DoNothing Yet */
			callBrowser(_view.getLocale(), "help_menu_user_add_person.html");
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_HELP_HELP_ADD_PERSON);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_HELP_HELP_ADD_PERSON_OPEN)) {
			/* DoNothing Yet */
			callBrowser(_view.getLocale(), "help_menu_user_add_person_open.html");
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_HELP_HELP_ADD_PERSON_OPEN);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_HELP_HELP_EDIT_PERSON)) {
			/* DoNothing Yet */
			callBrowser(_view.getLocale(), "help_menu_user_edit_person.html");
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_HELP_HELP_EDIT_PERSON);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_HELP_HELP_ADD_USER)) {
			/* DoNothing Yet */
			callBrowser(_view.getLocale(), "help_menu_user_add_user.html");
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_HELP_HELP_ADD_USER);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_HELP_HELP_ADD_USER_OPEN)) {
			/* DoNothing Yet */
			callBrowser(_view.getLocale(), "help_menu_user_add_user_open.html");
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_HELP_HELP_ADD_USER_OPEN);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_HELP_HELP_EDIT_USER)) {
			/* DoNothing Yet */
			callBrowser(_view.getLocale(), "help_menu_user_edit_user.html");
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_HELP_HELP_EDIT_USER);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_HELP_HELP_IMPORT_VARIABLE)) {
			/* DoNothing Yet */
			callBrowser(_view.getLocale(), "help_menu_data_import_variable.html");
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_HELP_HELP_IMPORT_VARIABLE);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_HELP_HELP_OPEN_VARIABLE)) {
			/* DoNothing Yet */
			callBrowser(_view.getLocale(), "help_menu_data_open_variable.html");
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_HELP_HELP_OPEN_VARIABLE);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_HELP_HELP_EDIT_VARIABLE)) {
			/* DoNothing Yet */
			callBrowser(_view.getLocale(), "help_menu_data_edit_variable.html");
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_HELP_HELP_EDIT_VARIABLE);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_HELP_HELP_EXPORT_VARIABLE)) {
			/* DoNothing Yet */
			callBrowser(_view.getLocale(), "help_menu_data_export_variable.html");
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_HELP_HELP_EXPORT_VARIABLE);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);			
		} else if (cmd.equals(ActionCommandText.CMD_HELP_HELP_EDIT_MEASUREMENT)) {
			/* DoNothing Yet */
			callBrowser(_view.getLocale(), "help_menu_data_edit_measurement.html");
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_HELP_HELP_EDIT_MEASUREMENT);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_HELP_HELP_OPEN_MEASUREMENT)) {
			/* DoNothing Yet */
			callBrowser(_view.getLocale(), "help_menu_data_open_measurement.html");
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_HELP_HELP_OPEN_MEASUREMENT);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_HELP_HELP_IMPORT_MEASUREMENT)) {
			/* DoNothing Yet */
			callBrowser(_view.getLocale(), "help_menu_data_import_measurement.html");
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_HELP_HELP_IMPORT_MEASUREMENT);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_HELP_HELP_EXPORT_SYNTAX)) {
			/* DoNothing Yet */
			callBrowser(_view.getLocale(), "help_menu_data_export_syntax.html");
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_HELP_HELP_EXPORT_SYNTAX);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_HELP_HELP_LOGIN)) {
			/* DoNothing Yet */
			callBrowser(_view.getLocale(), "help_menu_user_login.html");
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_HELP_HELP_LOGIN);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_HELP_HELP_CHANGE_PASSWORD)) {
			/* DoNothing Yet */
			callBrowser(_view.getLocale(), "help_menu_user_change_password.html");
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_HELP_HELP_CHANGE_PASSWORD);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_HELP_HELP_OVERWRITE_PASSWORD)) {
			/* DoNothing Yet */
			callBrowser(_view.getLocale(), "help_menu_extra_troubleshooting_overwrite_password.html");
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_HELP_HELP_OVERWRITE_PASSWORD);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_HELP_HELP_OPEN_PROJECT)) {
			/* DoNothing Yet */
			callBrowser(_view.getLocale(), "help_menu_project_open_project.html");
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_HELP_HELP_OPEN_PROJECT);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_HELP_HELP_TROUBLESHOOTING)) {
			/* DoNothing Yet */
			callBrowser(_view.getLocale(), "help_troubleshooting.html");
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_HELP_HELP_TROUBLESHOOTING);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_HELP_HELP_RELEASE_LOCK)) {
			/* DoNothing Yet */
			callBrowser(_view.getLocale(), "help_menu_extra_troubleshooting_release_lock.html");
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_HELP_HELP_RELEASE_LOCK);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_HELP_HELP_REVOKE_FINISHING)) {
			/* DoNothing Yet */
			callBrowser(_view.getLocale(), "help_menu_extra_troubleshooting_revoke_finishing.html");
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_HELP_HELP_REVOKE_FINISHING);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_HELP_HELP_SEARCH)) {
			/* DoNothing Yet */
			callBrowser(_view.getLocale(), "help_menu_search_search.html");
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_HELP_HELP_SEARCH);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.CMD_HELP_ABOUT)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.CMD_HELP_ABOUT);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
			
		/* Handle button events from form window: */
		/* Project Step Tab / Project Sub Tab: */
		} else if (cmd.equals(ActionCommandText.BTN_PRJ_STP_PRJ_TAB_BACK)) {
			/* DOES NOTHING */
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_PRJ_STP_PRJ_TAB_BACK);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_PRJ_STP_PRJ_TAB_RESET)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_PRJ_STP_PRJ_TAB_RESET);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);	
		} else if (cmd.equals(ActionCommandText.BTN_PRJ_STP_PRJ_TAB_NOTE)) {
			addTabComment(_model.getProject().getContent().getTabProject(), ActionCommandText.BTN_PRJ_STP_PRJ_TAB_NOTE);
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_PRJ_STP_PRJ_TAB_NOTE);
			Object[] addenda = new Object[1];
			addenda[0] = null;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_PRJ_STP_PRJ_TAB_NEXT)) {
			if (!validate(_view.getPreviousStep()))
				return;
			
			_model.getProject().getProgress().setProjectTabDone(true);
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_PRJ_STP_PRJ_TAB_NEXT);
			Object[] addenda = new Object[2];
			addenda[0] = _model;
			addenda[1] = _view.getLocale();
			parameter.setParameters(addenda);
			
		/* Project Step Tab / Concept Sub Tab: */
		} else if (cmd.equals(ActionCommandText.BTN_PRJ_STP_CON_TAB_BACK)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_PRJ_STP_CON_TAB_BACK);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_PRJ_STP_CON_TAB_RESET)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_PRJ_STP_CON_TAB_RESET);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_PRJ_STP_CON_TAB_NOTE)) {
			addTabComment(_model.getProject().getContent().getTabConcept(), ActionCommandText.BTN_PRJ_STP_CON_TAB_NOTE);
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_PRJ_STP_CON_TAB_NOTE);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_PRJ_STP_CON_TAB_NEXT)) {
			if (!validate(_view.getPreviousStep()))
				return;
			
			_model.getProject().getProgress().setConceptTabDone(true);
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_PRJ_STP_CON_TAB_NEXT);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
			
		/* Project Step Tab / Literature Sub Tab: */
		} else if (cmd.equals(ActionCommandText.BTN_PRJ_STP_LIT_TAB_BACK)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_PRJ_STP_LIT_TAB_BACK);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_PRJ_STP_LIT_TAB_RESET)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_PRJ_STP_LIT_TAB_RESET);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_PRJ_STP_LIT_TAB_NOTE)) {
			addTabComment(_model.getProject().getContent().getTabLiterature(), ActionCommandText.BTN_PRJ_STP_LIT_TAB_NOTE);
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_PRJ_STP_LIT_TAB_NOTE);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_PRJ_STP_LIT_TAB_NEXT)) {
			if (!validate(_view.getPreviousStep()))
				return;
			
			_model.getProject().getProgress().setReferenceTabDone(true);
			_model.getProject().getProgress().setTargetVariableStepTabbedPanelAvailable(true);
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_PRJ_STP_LIT_TAB_NEXT);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
			
		/* Target Variable Step Tab / Target Variable Sub Tab: */
		} else if (cmd.equals(ActionCommandText.BTN_TGT_STP_TGT_TAB_BACK)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_TGT_STP_TGT_TAB_BACK);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_TGT_STP_TGT_TAB_RESET)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_TGT_STP_TGT_TAB_RESET);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_TGT_STP_TGT_TAB_AUTO)) {
			boolean autoSuccess = autoCompletition(); 
			
			if (!autoSuccess)
				return;
			
			isAutocomplete = true;
			
			_model.setIsAutocomplete(isAutocomplete);
						
			parameter = new IdentifiedParameter(ActionCommandID.BTN_TGT_STP_TGT_TAB_AUTO);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_TGT_STP_TGT_TAB_NOTE)) {
			addTabComment(_model.getProject().getContent().getTabMeasurement(), ActionCommandText.BTN_TGT_STP_TGT_TAB_NOTE);
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_TGT_STP_TGT_TAB_NOTE);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_TGT_STP_TGT_TAB_IMP)) {
			boolean importSuccess = importMeasurement();
			
			if (!importSuccess)
				return;
			  			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_TGT_STP_TGT_TAB_IMP);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_TGT_STP_TGT_TAB_NEXT)) {
			if (!validate(_view.getPreviousStep()))
				return;
			
			_model.getProject().getProgress().setMeasurementTabDone(true);
			_model.getProject().getProgress().setConceptualStepTabbedPanelAvailable(true);
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_TGT_STP_TGT_TAB_NEXT);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
			
			
		/* Conceptual Step Tab / Dimension Sub Tab: */
		} else if (cmd.equals(ActionCommandText.BTN_CON_STP_DIM_TAB_BACK)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_CON_STP_DIM_TAB_BACK);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_CON_STP_DIM_TAB_RESET)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_CON_STP_DIM_TAB_RESET);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_CON_STP_DIM_TAB_NOTE)) {
			addTabComment(_model.getProject().getContent().getTabDimension(), ActionCommandText.BTN_CON_STP_DIM_TAB_NOTE);
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_CON_STP_DIM_TAB_NOTE);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_CON_STP_DIM_TAB_NEXT)) {
			if (!validate(_view.getPreviousStep()))
				return;
			
			_model.getProject().getProgress().setDimensionTabDone(true);
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_CON_STP_DIM_TAB_NEXT);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
			
		/* Conceptual Step Tab / Specification Sub Tab: */
		} else if (cmd.equals(ActionCommandText.BTN_CON_STP_SPE_TAB_BACK)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_CON_STP_SPE_TAB_BACK);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_CON_STP_SPE_TAB_RESET)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_CON_STP_SPE_TAB_RESET);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_CON_STP_SPE_TAB_NOTE)) {
			addTabComment(_model.getProject().getContent().getTabSpecification(), ActionCommandText.BTN_CON_STP_SPE_TAB_NOTE);
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_CON_STP_SPE_TAB_NOTE);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_CON_STP_SPE_TAB_NEXT)) {
			if (!validate(_view.getPreviousStep()))
				return;
			
			_model.getProject().getProgress().setSpecificationTabDone(true);
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_CON_STP_SPE_TAB_NEXT);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
			
		/* Conceptual Step Tab / Mapping Instance Sub Tab: */
		} else if (cmd.equals(ActionCommandText.BTN_CON_STP_MAP_INS_BACK)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_CON_STP_MAP_INS_BACK);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);	
		} else if (cmd.equals(ActionCommandText.BTN_CON_STP_MAP_INS_RESET)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_CON_STP_MAP_INS_RESET);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_CON_STP_MAP_INS_NOTE)) {
			addTabComment(_model.getProject().getContent().getTabMapDimensionInstance(), ActionCommandText.BTN_CON_STP_MAP_INS_NOTE);
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_CON_STP_MAP_INS_NOTE);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);	
		} else if (cmd.equals(ActionCommandText.BTN_CON_STP_MAP_INS_NEXT)) {
			if (!validate(_view.getPreviousStep()))
				return;
			
			_model.getProject().getProgress().setMapDimensionInstanceTabDone(true);
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_CON_STP_MAP_INS_NEXT);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
			
		/* Conceptual Step Tab / Mapping Attribute Sub Tab: */
		} else if (cmd.equals(ActionCommandText.BTN_CON_STP_MAP_ATR_BACK)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_CON_STP_MAP_ATR_BACK);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_CON_STP_MAP_ATR_RESET)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_CON_STP_MAP_ATR_RESET);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_CON_STP_MAP_ATR_NOTE)) {
			addTabComment(_model.getProject().getContent().getTabMapDimensionAttribute(), ActionCommandText.BTN_CON_STP_MAP_ATR_NOTE);
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_CON_STP_MAP_ATR_NOTE);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);	
		} else if (cmd.equals(ActionCommandText.BTN_CON_STP_MAP_ATR_NEXT)) {
			if (!validate(_view.getPreviousStep()))
				return;
			
			_model.getProject().getProgress().setMapDimensionAttributeTabDone(true);
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_CON_STP_MAP_ATR_NEXT);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
			
		/* Conceptual Step Tab / Mapping Characteristic Sub Tab: */
		} else if (cmd.equals(ActionCommandText.BTN_CON_STP_MAP_CHA_BACK)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_CON_STP_MAP_CHA_BACK);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_CON_STP_MAP_CHA_RESET)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_CON_STP_MAP_CHA_RESET);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_CON_STP_MAP_CHA_NOTE)) {
			addTabComment(_model.getProject().getContent().getTabMapDimensionChar(), ActionCommandText.BTN_CON_STP_MAP_CHA_NOTE);
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_CON_STP_MAP_CHA_NOTE);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_CON_STP_MAP_CHA_CONFIRM)) {
			_view.setPreviousStep(_view.getMapDimensionChar()); 
			
			if (!validate(_view.getPreviousStep()))
				return;
			
			saveProject();
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_CON_STP_MAP_CHA_CONFIRM);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_CON_STP_MAP_CHA_NEXT)) {
			_model.getProject().getProgress().setMapDimensionChaTabDone(true);
			_model.getProject().getProgress().setOperationalStepTabbedPanelAvailable(true);
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_CON_STP_MAP_CHA_NEXT);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
			
		/* Operational Step Tab / Instance Sub Tab: */
		} else if (cmd.equals(ActionCommandText.BTN_OPE_STP_INS_TAB_BACK)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_OPE_STP_INS_TAB_BACK);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_OPE_STP_INS_TAB_RESET)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_OPE_STP_INS_TAB_RESET);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_OPE_STP_INS_TAB_NOTE)) {
			addTabComment(_model.getProject().getContent().getTabOSInstance(), ActionCommandText.BTN_OPE_STP_INS_TAB_NOTE);
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_OPE_STP_INS_TAB_NOTE);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_OPE_STP_INS_TAB_NEXT)) {
			if (!validate(_view.getPreviousStep()))
				return;
			
			_model.getProject().getProgress().setOsInstanceTabDone(true);
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_OPE_STP_INS_TAB_NEXT);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
			
		/* Operational Step Tab / Indicator Sub Tab: */
		} else if (cmd.equals(ActionCommandText.BTN_OPE_STP_IND_TAB_BACK)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_OPE_STP_IND_TAB_BACK);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_OPE_STP_IND_TAB_RESET)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_OPE_STP_IND_TAB_RESET);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_OPE_STP_IND_TAB_NOTE)) {
			addTabComment(_model.getProject().getContent().getTabIndicator(), ActionCommandText.BTN_OPE_STP_IND_TAB_NOTE);
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_OPE_STP_IND_TAB_NOTE);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_OPE_STP_IND_TAB_NEXT)) {
			if (!validate(_view.getPreviousStep()))
				return;
			
			_model.getProject().getProgress().setIndicatorTabDone(true);
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_OPE_STP_IND_TAB_NEXT);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
			
		/* Operational Step Tab / Prescription Sub Tab: */
		} else if (cmd.equals(ActionCommandText.BTN_OPE_STP_PRE_TAB_BACK)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_OPE_STP_PRE_TAB_BACK);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_OPE_STP_PRE_TAB_RESET)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_OPE_STP_PRE_TAB_RESET);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_OPE_STP_PRE_TAB_NOTE)) {
			addTabComment(_model.getProject().getContent().getTabPrescription(), ActionCommandText.BTN_OPE_STP_PRE_TAB_NOTE);
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_OPE_STP_PRE_TAB_NOTE);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_OPE_STP_PRE_TAB_NEXT)) {
			if (!validate(_view.getPreviousStep()))
				return;
			
			_model.getProject().getProgress().setPrescriptionTabDone(true);
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_OPE_STP_PRE_TAB_NEXT);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
			
		/* Operational Step Tab / Mapping Instance Sub Tab: */
		} else if (cmd.equals(ActionCommandText.BTN_OPE_STP_MAP_INS_BACK)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_OPE_STP_MAP_INS_BACK);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_OPE_STP_MAP_INS_RESET)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_OPE_STP_MAP_INS_RESET);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_OPE_STP_MAP_INS_NOTE)) {
			addTabComment(_model.getProject().getContent().getTabMapIndicatorInstance(), ActionCommandText.BTN_OPE_STP_MAP_INS_NOTE);
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_OPE_STP_MAP_INS_NOTE);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_OPE_STP_MAP_INS_NEXT)) {
			if (!validate(_view.getPreviousStep()))
				return;
			
			_model.getProject().getProgress().setMapIndicatorInstanceTabDone(true);
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_OPE_STP_MAP_INS_NEXT);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
			
		/* Operational Step Tab / Mapping Attribute Sub Tab: */
		} else if (cmd.equals(ActionCommandText.BTN_OPE_STP_MAP_ATR_BACK)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_OPE_STP_MAP_ATR_BACK);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_OPE_STP_MAP_ATR_RESET)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_OPE_STP_MAP_ATR_RESET);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_OPE_STP_MAP_ATR_NOTE)) {
			addTabComment(_model.getProject().getContent().getTabMapIndicatorAttribute(), ActionCommandText.BTN_OPE_STP_MAP_ATR_NOTE);
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_OPE_STP_MAP_ATR_NOTE);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_OPE_STP_MAP_ATR_NEXT)) {
			if (!validate(_view.getPreviousStep()))
				return;
			
			_model.getProject().getProgress().setMapIndicatorAttributeTabDone(true);
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_OPE_STP_MAP_ATR_NEXT);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
			
		/* Operational Step Tab / Mapping Characteristic Sub Tab: */
		} else if (cmd.equals(ActionCommandText.BTN_OPE_STP_MAP_CHA_BACK)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_OPE_STP_MAP_CHA_BACK);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_OPE_STP_MAP_CHA_RESET)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_OPE_STP_MAP_CHA_RESET);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_OPE_STP_MAP_CHA_NOTE)) {
			addTabComment(_model.getProject().getContent().getTabMapIndicatorChar(), ActionCommandText.BTN_OPE_STP_MAP_CHA_NOTE);
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_OPE_STP_MAP_CHA_NOTE);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_OPE_STP_MAP_CHA_CONFIRM)) {
			_view.setPreviousStep(_view.getMapIndicatorChar()); 
			
			if (!validate(_view.getPreviousStep()))
				return;
			
			saveProject();
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_OPE_STP_MAP_CHA_CONFIRM);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_OPE_STP_MAP_CHA_NEXT)) {
			_model.getProject().getProgress().setMapIndicatorChaTabDone(true);
			_model.getProject().getProgress().setSearchNCompareStepTabbedPanelAvailable(true);
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_OPE_STP_MAP_CHA_NEXT);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
			
		/* Search and Compare Step Tab / Search Sub Tab: */
		} else if (cmd.equals(ActionCommandText.BTN_SEA_STP_SEA_TAB_BACK)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_SEA_STP_SEA_TAB_BACK);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_SEA_STP_SEA_TAB_RESET)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_SEA_STP_SEA_TAB_RESET);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_SEA_STP_SEA_TAB_NOTE)) {
			addTabComment(_model.getProject().getContent().getTabSearchVariable(), ActionCommandText.BTN_SEA_STP_SEA_TAB_NOTE);
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_SEA_STP_SEA_TAB_NOTE);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_SEA_STP_SEA_TAB_NEXT)) {
			if (!validate(_view.getPreviousStep()))
				return;
			
			_model.getProject().getProgress().setSearchTabDone(true);
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_SEA_STP_SEA_TAB_NEXT);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
			
		/* Search and Compare Step Tab / Comparison Sub Tab: */
		} else if (cmd.equals(ActionCommandText.BTN_SEA_STP_COM_TAB_BACK)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_SEA_STP_COM_TAB_BACK);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_SEA_STP_COM_TAB_RESET)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_SEA_STP_COM_TAB_RESET);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_SEA_STP_COM_TAB_NOTE)) {
			addTabComment(_model.getProject().getContent().getTabCompareVariables(), ActionCommandText.BTN_SEA_STP_COM_TAB_NOTE);
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_SEA_STP_COM_TAB_NOTE);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_SEA_STP_COM_TAB_IMP)) {
			/* Get Variables from Basket */
			ArrayList<Object> objectsList = getBasketContentsByType(Variable.class);
			 
			/* Prepare parameter for call */
			ArrayList<Variable> variablesList = new ArrayList<Variable>();
			if (objectsList != null) {
				Iterator<Object> iterator = objectsList.iterator();
				
				while (iterator.hasNext()) {
					Variable variable = (Variable)iterator.next();
					
					variablesList.add(variable);
				}
			}
			
			/* Call Dialogue "ImportVariable" */
			ImportVariableDialog iv = new ImportVariableDialog(variablesList, _view.getLocale(), this);
			
			/* Get selected Variables */
			ArrayList<Variable> selectedVariables = iv.getImportedVariables();
			
			Iterator<Variable> variable_iter = selectedVariables.iterator();
			WorkStepInstance	targetInstance = null;
			Attributes			targetAttribute = null;
			while (variable_iter.hasNext()) {
				Variable var = variable_iter.next();
				
				AttributeComp attr_comp = new AttributeComp();
				
				DesktopPane		desktop			= _view.getDesktop();
				InternalFrame	frame			= desktop.getFormFrame();
				JTabbedPane		tabPaneOuter	= ((FormFrame)frame).getTabbedPane();
				JTabbedPane		tabPaneInner	= (JTabbedPane)tabPaneOuter.getComponentAt(TabbedPaneForm.SEA_COM_STE_TAB_IDX);
				Tab 			tab  			= (Tab)tabPaneInner.getComponentAt(TabbedPaneSearchNCompareStep.COM_TAB_IDX);				

									targetAttribute	= ((TabCompareMetadata)tab).getSourceAttribute();
									targetInstance	= ((TabCompareMetadata)tab).getSourceInstance();

				AttributeLink targetLink = new AttributeLink();
				targetLink.setAttribute(targetAttribute);
				targetLink.setInstance(targetInstance);
				targetLink.setAttributeLinkType(AttributeLinkType.COMPARISON);
				
				AttributeLink sourceLink = new AttributeLink();
				sourceLink.setAttribute(var);
				sourceLink.setInstance(targetInstance);
				sourceLink.setAttributeLinkType(AttributeLinkType.COMPARISON);
				
				attr_comp.setBelongsTo(_model.getProject());
				attr_comp.setSourceAttribute(sourceLink);
				attr_comp.setTargetAttribute(targetLink);
				attr_comp.setBiasMetadata(new BiasMetadata());
								
				_model.getProject().getContent().addLink(sourceLink);
				_model.getProject().getContent().addLink(targetLink);
				_model.getProject().getContent().addComp(attr_comp);
				
				
				/* TESTWEISE: */
				boolean loadStatus = true;
				
				CharacteristicLink charLink = new CharacteristicLink(var);
				ArrayList<Integer> charlist = charLink.loadLinks(_connection);
				
				if (!charlist.isEmpty()) {
					Iterator<Integer> iterator = charlist.iterator();
					
					while (iterator.hasNext()) {
						Integer id = iterator.next();
						
						charLink = new CharacteristicLink();
						charLink.setAttribute(var);
						charLink.setEntityID(id);
						if (loadStatus)
							loadStatus = charLink.entityLoad(_connection);

						Characteristics char_ch		= (_model.getProject()).getContent().getValueByID(charLink.getDsCharEntryID());
						CharacteristicLink char_ln	= (_model.getProject()).getContent().getCharacteristicLinkByCharacteristic(char_ch);
						CharacteristicMap char_mp	= (_model.getProject()).getContent().getCharacteristicMapByCharacteristic(char_ln);
						
						Characteristics characteristic = new Value();
						if (!(char_ch != null)) {						
							characteristic.setEntityID(charLink.getDsCharEntryID());
						
							if (loadStatus)
								loadStatus = ((Value)characteristic).entityLoad(_connection);
						}								
						if (char_ch != null)
							characteristic = char_ch;
							
						if (char_ln != null)
							charLink = char_ln;
						charLink.setCharacteristic(characteristic);
						
						CharacteristicMap charMap = new CharacteristicMap();
						if (!(char_mp != null)) {
							charMap.setEntityID(generateNegID());
							charMap.setType(CharacteristicMapType.VALUE);
							
						}
						if (char_mp != null)
							charMap = char_mp;
						charMap.setSourceCharacteristic(charLink);
						
						if (loadStatus) {
							if (!(char_ch != null))
								(_model.getProject()).getContent().addValue((Value)characteristic);
							
							if (!(char_ln != null))	
								(_model.getProject()).getContent().addCharacteristicLink(charLink);
							
							if (!(char_mp != null))
								(_model.getProject()).getContent().addCharacteristicMap(charMap);

						}							
					}
				}
				
			}
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_SEA_STP_COM_TAB_IMP);
			Object[] addenda = new Object[3];
			addenda[0] = _model;
			addenda[1] = targetInstance;
			addenda[2] = targetAttribute;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_SEA_STP_COM_TAB_NEXT)) {
			if (!validate(_view.getPreviousStep()))
				return;
			
			_model.getProject().getProgress().setComparisonTabDone(true);
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_SEA_STP_COM_TAB_NEXT);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
			
		/* Search and Compare Step Tab / Comparison Value Sub Tab: new */
		} else if (cmd.equals(ActionCommandText.BTN_SEA_STP_VAL_TAB_BACK)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_SEA_STP_VAL_TAB_BACK);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_SEA_STP_VAL_TAB_RESET)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_SEA_STP_VAL_TAB_RESET);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_SEA_STP_VAL_TAB_NOTE)) {
			addTabComment(_model.getProject().getContent().getTabCompareValues(), ActionCommandText.BTN_SEA_STP_VAL_TAB_NOTE);
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_SEA_STP_VAL_TAB_NOTE);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_SEA_STP_VAL_TAB_NEXT)) {
			if (!validate(_view.getPreviousStep()))
				return;
			
			_model.getProject().getProgress().setCompareValuesTabDone(true);
			_model.getProject().getProgress().setDataRecodingStepTabbedPanelAvailable(true);
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_SEA_STP_VAL_TAB_NEXT);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
			
		/* Data Coding Step Tab / Instance Sub Tab: */
		} else if (cmd.equals(ActionCommandText.BTN_DAT_STP_INS_TAB_BACK)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_DAT_STP_INS_TAB_BACK);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_DAT_STP_INS_TAB_RESET)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_DAT_STP_INS_TAB_RESET);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_DAT_STP_INS_TAB_NOTE)) {
			addTabComment(_model.getProject().getContent().getTabDRInstance(), ActionCommandText.BTN_DAT_STP_INS_TAB_NOTE);
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_DAT_STP_INS_TAB_NOTE);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_DAT_STP_INS_TAB_NEXT)) {
			if (!validate(_view.getPreviousStep()))
				return;
			
			_model.getProject().getProgress().setDrInstanceTabDone(true);
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_DAT_STP_INS_TAB_NEXT);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
			
		/* Data Coding Step Tab / Variable Sub Tab: */
		} else if (cmd.equals(ActionCommandText.BTN_DAT_STP_VAR_TAB_BACK)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_DAT_STP_VAR_TAB_BACK);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_DAT_STP_VAR_TAB_RESET)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_DAT_STP_VAR_TAB_RESET);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_DAT_STP_VAR_TAB_NOTE)) {
			addTabComment(_model.getProject().getContent().getTabVariable(), ActionCommandText.BTN_DAT_STP_VAR_TAB_NOTE);
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_DAT_STP_VAR_TAB_NOTE);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_DAT_STP_VAR_TAB_IMP)) {
			DesktopPane			desktop			= _view.getDesktop();
			InternalFrame		frame			= desktop.getFormFrame();
			JTabbedPane			tabPaneOuter	= ((FormFrame)frame).getTabbedPane();
			JTabbedPane			tabPaneInner	= (JTabbedPane)tabPaneOuter.getComponentAt(TabbedPaneForm.DAT_REC_STE_TAB_IDX);
			Tab 				tab  			= (Tab)tabPaneInner.getComponentAt(TabbedPaneDataReCodingStep.VAR_TAB_IDX);	
			
			WorkStepInstance	instance	= ((TabVariable)tab).getInstance();
			
			ArrayList<AttributeLink>	links		= _model.getProject().getContent().getLinksByLayer(instance);
			Iterator<AttributeLink>		iter_links	= links.iterator();
			
			while (iter_links.hasNext()) {
				AttributeLink link	= iter_links.next();
				
				AttributeLink	obs_link = new AttributeLink();
				
				if (link.getAttribute() instanceof Variable) {
					
					/* Variable already assigned, ask user */
					ResourceBundle resourceBundle	= ResourceBundle.getBundle(BUNDLE, _view.getLocale());
					
					final String[] DIALOG_BUTTONS_TITLES	= {resourceBundle.getString(OVERWRITE), resourceBundle.getString(CANCEL), resourceBundle.getString(HELP)};
					
					int retValue = 2;
				  	while (retValue == 2)
				  	{ 
				  		retValue = JOptionPane.showOptionDialog(
			    			null, 
			    			resourceBundle.getString(OVERWRITE_VAR_QUE_TEXT),
			    			resourceBundle.getString(OVERWRITE_VAR_QUE),
			                JOptionPane.OK_CANCEL_OPTION,
			                JOptionPane.QUESTION_MESSAGE,
			                null, 
			                DIALOG_BUTTONS_TITLES, 
			                DIALOG_BUTTONS_TITLES[0]
				  		);
				  		
				  		if (retValue == 2) {
				  			callBrowser(_view.getLocale(), "help.html");
				  		}
				  		
				  		if (retValue == 1) {
				  			return;
				  		}
			    		
				  		if (retValue == 0) {
				  			/* Delete AttributeComp from DB */
				  			ArrayList<AttributeComp>	comps		= _model.getProject().getContent().getComps();
				  			Iterator<AttributeComp>		iter_comp	= comps.iterator();
				  			
				  			ArrayList<AttributeComp>	obs_comps	= new ArrayList<AttributeComp>();
				  			
				  			while (iter_comp.hasNext()) {
				  				AttributeComp comp = iter_comp.next();
				  				
				  				if (link.equals(comp.getSourceAttribute())) {
				  					comp.getBiasMetadata().entityRemove(_connection);				  					
				  					comp.entityRemove(_connection);
				  					
				  					obs_comps.add(comp);
				  				}
				  			}
				  			
				  			/* Delete AttributeMap from DB */
				  			ArrayList<AttributeMap>	attrMaps		= _model.getProject().getContent().getMaps();
				  			Iterator<AttributeMap>	iter_attrMap	= attrMaps.iterator();
				  			
				  			ArrayList<AttributeMap>			obs_attrMaps	= new ArrayList<AttributeMap>();
				  			ArrayList<CharacteristicMap>	obs_charMaps	= new ArrayList<CharacteristicMap>();
				  			
				  			while (iter_attrMap.hasNext()) {
				  				AttributeMap attrMap = iter_attrMap.next();
				  				
				  				if (link.equals(attrMap.getSourceAttribute())) {
				  					/* Delete CharMap from DB */
				  					ArrayList<CharacteristicMap>	charMaps		= _model.getProject().getContent().getCharacteristicMaps();
				  					Iterator<CharacteristicMap>		iter_charMap	= charMaps.iterator();
				  					
				  					while (iter_charMap.hasNext()) {
				  						CharacteristicMap charMap = iter_charMap.next();
				  						
				  						if (attrMap.equals(charMap.getBelongsTo())) {
				  							charMap.entityRemove(_connection);
				  							
				  							obs_charMaps.add(charMap);
				  						}
				  					}
				  					
				  					attrMap.entityRemove(_connection);
				  					
				  					obs_attrMaps.add(attrMap);
				  				}
				  			}
				  			
				  			/* Delete AttributeLink from DB */
				  			link.entityRemove(_connection);
				  			
				  			obs_link = link;
				  			
				  			/* remove AttributeComp from Memory */
				  			iter_comp	= obs_comps.iterator();
				  			
				  			while (iter_comp.hasNext()) {
				  				AttributeComp comp = iter_comp.next();
				  								  					
				  				_model.getProject().getContent().removeComp(comp);				  					
				  			}
				  			
		  					/* Remove CharMap from Memory */
		  					Iterator<CharacteristicMap>		iter_charMap	= obs_charMaps.iterator();
		  					
		  					while (iter_charMap.hasNext()) {
		  						CharacteristicMap charMap = iter_charMap.next();
		  								  						
		  						_model.getProject().getContent().removeCharacteristicMap(charMap);
		  					}
		  					
				  			/* remove AttributeMap from Memory */
				  			iter_attrMap	= obs_attrMaps.iterator();
				  			
				  			while (iter_attrMap.hasNext()) {
				  				AttributeMap attrMap = iter_attrMap.next();
				  								  					
				  				_model.getProject().getContent().removeMap(attrMap);
				  			}
				  			
				  			/* Remove AttributeLink from Memory */
				  			_model.getProject().getContent().removeLink(obs_link);				  			
				  		}
				  	}
				}
			}
			
			/* Get Variables from Basket */
			ArrayList<Object> objectsList = getBasketContentsByType(Variable.class);
			 
			/* Prepare parameter for call */
			ArrayList<Variable> variablesList = new ArrayList<Variable>();
			if (objectsList != null) {
				Iterator<Object> iterator = objectsList.iterator();
				
				while (iterator.hasNext()) {
					Variable variable = (Variable)iterator.next();
					
					variablesList.add(variable);
				}
			}
			
			/* Call Dialogue "ImportVariable" */
			ImportVariableDialog iv = new ImportVariableDialog(variablesList, _view.getLocale(), this);
			
			/* Get selected Variables */
			ArrayList<Variable> selectedVariables = iv.getImportedVariables();
			
			Iterator<Variable> variable_iter = selectedVariables.iterator();
			while (variable_iter.hasNext()) {
				Variable var = variable_iter.next();
				
				AttributeLink link = new AttributeLink();
	
				Variable attribute = (_model.getProject()).getContent().getVariableByID(var.getEntityID());
				AttributeLink attribute_link = null;
				if (attribute != null)
					attribute_link = (_model.getProject()).getContent().getLinkByAttributeAndInstanceType(attribute, AttributeLinkType.VARIABLE);
				
				if (attribute != null) {
					ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, _view.getLocale());
					JOptionPane.showMessageDialog(_view.getAppFrame(), bundle.getString(DUPLICATE) +" ("+ attribute +"@"+ attribute_link.getInstance() +")");					
					return;
				}
				
				link.setEntityID(generateNegID());			
				link.setAttribute(var);
				link.setAttributeLinkType(AttributeLinkType.VARIABLE);
				link.setInstance(instance);
				
				AttributeMap map = new AttributeMap();
				map.setEntityID(generateNegID());
				map.setAttributeMapType(AttributeMapType.ASSIGNED_VARIABLE);
				map.setSourceAttribute(link);
				
				if (!(attribute != null))
					(_model.getProject()).getContent().addVariable(var);
				
				(_model.getProject()).getContent().addLink(link);
				(_model.getProject()).getContent().addMap(map);
								
				boolean loadStatus = true;
				
				CharacteristicLink charLink = new CharacteristicLink(var);
				ArrayList<Integer> charlist = charLink.loadLinks(_connection);
				
				if (!charlist.isEmpty()) {
					Iterator<Integer> iterator = charlist.iterator();
					
					while (iterator.hasNext()) {
						Integer id = iterator.next();
						
						charLink = new CharacteristicLink();
						charLink.setAttribute(var);
						charLink.setEntityID(id);
						if (loadStatus)
							loadStatus = charLink.entityLoad(_connection);

						Characteristics char_ch		= (_model.getProject()).getContent().getValueByID(charLink.getDsCharEntryID());
						CharacteristicLink char_ln	= (_model.getProject()).getContent().getCharacteristicLinkByCharacteristic(char_ch);

						Characteristics characteristic = new Value();
						if (!(char_ch != null)) {						
							characteristic.setEntityID(charLink.getDsCharEntryID());
						
							if (loadStatus)
								loadStatus = ((Value)characteristic).entityLoad(_connection);
						}								
						if (char_ch != null)
							characteristic = char_ch;
							
						if (char_ln != null)
							charLink = char_ln;
						charLink.setCharacteristic(characteristic);
						
						CharacteristicMap charMap = new CharacteristicMap();
						charMap.setEntityID(generateNegID());
						charMap.setType(CharacteristicMapType.VALUE);
						charMap.setBelongsTo(map); 						
						charMap.setSourceCharacteristic(charLink);
						
						if (loadStatus) {
							if (!(char_ch != null))
								(_model.getProject()).getContent().addValue((Value)characteristic);
							
							if (!(char_ln != null))	
								(_model.getProject()).getContent().addCharacteristicLink(charLink);
							
							(_model.getProject()).getContent().addCharacteristicMap(charMap);
						}							
					}
				}				
				
			}
	
			parameter = new IdentifiedParameter(ActionCommandID.BTN_DAT_STP_VAR_TAB_IMP);
			Object[] addenda = new Object[2];
			addenda[0] = _model;
			addenda[1] = instance;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_DAT_STP_VAR_TAB_NEXT)) {
			if (!validate(_view.getPreviousStep()))
				return;
			
			_model.getProject().getProgress().setVariableTabDone(true);
						
			parameter = new IdentifiedParameter(ActionCommandID.BTN_DAT_STP_VAR_TAB_NEXT);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
			
		/* Data Coding Step Tab / Value Sub Tab: */
		} else if (cmd.equals(ActionCommandText.BTN_DAT_STP_VAL_TAB_BACK)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_DAT_STP_VAL_TAB_BACK);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_DAT_STP_VAL_TAB_RESET)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_DAT_STP_VAL_TAB_RESET);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_DAT_STP_VAL_TAB_NOTE)) {
			addTabComment(_model.getProject().getContent().getTabValue(), ActionCommandText.BTN_DAT_STP_VAL_TAB_NOTE);
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_DAT_STP_VAL_TAB_NOTE);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_DAT_STP_VAL_TAB_NEXT)) {
			if (!validate(_view.getPreviousStep()))
				return;
			
			_model.getProject().getProgress().setValueTabDone(true);
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_DAT_STP_VAL_TAB_NEXT);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
			
		} else if (cmd.equals(ActionCommandText.BTN_DAT_STP_QUE_TAB_BACK)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_DAT_STP_QUE_TAB_BACK);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_DAT_STP_QUE_TAB_RESET)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_DAT_STP_QUE_TAB_RESET);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_DAT_STP_QUE_TAB_NOTE)) {
			addTabComment(_model.getProject().getContent().getTabValue(), ActionCommandText.BTN_DAT_STP_QUE_TAB_NOTE);
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_DAT_STP_QUE_TAB_NOTE);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_DAT_STP_QUE_TAB_NEXT)) {
			if (!validate(_view.getPreviousStep()))
				return;
			
			_model.getProject().getProgress().setQuestionTabDone(true);
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_DAT_STP_QUE_TAB_NEXT);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
	
		} else if (cmd.equals(ActionCommandText.BTN_DAT_STP_STU_TAB_BACK)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_DAT_STP_STU_TAB_BACK);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_DAT_STP_STU_TAB_RESET)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_DAT_STP_STU_TAB_RESET);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_DAT_STP_STU_TAB_NOTE)) {
			addTabComment(_model.getProject().getContent().getTabValue(), ActionCommandText.BTN_DAT_STP_STU_TAB_NOTE);
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_DAT_STP_STU_TAB_NOTE);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_DAT_STP_STU_TAB_NEXT)) {
			if (!validate(_view.getPreviousStep()))
				return;
			
			_model.getProject().getProgress().setStudyTabDone(true);
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_DAT_STP_STU_TAB_NEXT);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);			
			
		/* Data Coding Step Tab / Mapping Instance Sub Tab: */
		} else if (cmd.equals(ActionCommandText.BTN_DAT_STP_MAP_INS_BACK)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_DAT_STP_MAP_INS_BACK);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_DAT_STP_MAP_INS_RESET)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_DAT_STP_MAP_INS_RESET);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_DAT_STP_MAP_INS_NOTE)) {
			addTabComment(_model.getProject().getContent().getTabMapVariableInstance(), ActionCommandText.BTN_DAT_STP_MAP_INS_NOTE);
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_DAT_STP_MAP_INS_NOTE);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_DAT_STP_MAP_INS_NEXT)) {
			if (!validate(_view.getPreviousStep()))
				return;
			
			_model.getProject().getProgress().setMapVariableInstanceTabDone(true);
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_DAT_STP_MAP_INS_NEXT);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
			
		/* Data Coding Step Tab / Mapping Attribute Sub Tab: */
		} else if (cmd.equals(ActionCommandText.BTN_DAT_STP_MAP_ATR_BACK)) {
			/* DoNothing Yet*/
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_DAT_STP_MAP_ATR_BACK);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_DAT_STP_MAP_ATR_RESET)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_DAT_STP_MAP_ATR_RESET);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_DAT_STP_MAP_ATR_NOTE)) {
			addTabComment(_model.getProject().getContent().getTabMapVariableAttribute(), ActionCommandText.BTN_DAT_STP_MAP_ATR_NOTE);
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_DAT_STP_MAP_ATR_NOTE);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_DAT_STP_MAP_ATR_NEXT)) {
			if (!validate(_view.getPreviousStep()))
				return;
				
			_model.getProject().getProgress().setMapVariableAttributeTabDone(true);
		
			parameter = new IdentifiedParameter(ActionCommandID.BTN_DAT_STP_MAP_ATR_NEXT);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
			
		/* Data Coding Step Tab / Mapping Characteristic Sub Tab: */
		} else if (cmd.equals(ActionCommandText.BTN_DAT_STP_MAP_CHA_BACK)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_DAT_STP_MAP_CHA_BACK);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_DAT_STP_MAP_CHA_RESET)) {
			/* DoNothing Yet */
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_DAT_STP_MAP_CHA_RESET);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_DAT_STP_MAP_CHA_NOTE)) {
			addTabComment(_model.getProject().getContent().getTabMapVariableChar(), ActionCommandText.BTN_DAT_STP_MAP_CHA_NOTE);
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_DAT_STP_MAP_CHA_NOTE);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_DAT_STP_MAP_CHA_CONFIRM)) {
			_view.setPreviousStep(_view.getMapVariableChar()); 
			
			if (!validate(_view.getPreviousStep()))
				return;
			
			_model.getProject().getProgress().setMapVariableChaTabDone(true);
			
			saveProject();
			int project_id = _model.getProject().getEntityID();
			closeProject(true);
			openProject(project_id);
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_DAT_STP_MAP_CHA_CONFIRM);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		} else if (cmd.equals(ActionCommandText.BTN_DAT_STP_MAP_CHA_NEXT)) {
			_model.getProject().getProgress().setMapVariableChaTabDone(true);
			
			parameter = new IdentifiedParameter(ActionCommandID.BTN_DAT_STP_MAP_CHA_NEXT);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
		}
			
		/* Refresh the View: */
		setChanged();	
		notifyObservers(parameter);
	}
	
	private boolean removeVariable(Variable var, ArrayList<CharacteristicLink> charLinks) {
		/* Remove Variable from DB */
		boolean removeStatus = var.entityRemove(_connection);
		
		if (removeStatus) {
			if ((charLinks != null) &&
					(!charLinks.isEmpty())) {
				Iterator<CharacteristicLink> linkIter = charLinks.iterator();
				
				while(linkIter.hasNext()) {
					CharacteristicLink link = linkIter.next();
					
					/* Remove Characteristics of Variable from DB */ 
					Characteristics chr = link.getCharacteristic();
					chr.entityRemove(_connection);
									
					/* Remove CharacteristicLink from DB */
					link.entityRemove(_connection);
				}
			}
			
			try {
				_connection.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return removeStatus;
	}
	
	private boolean removeMeasure(Measurement mea, ArrayList<CharacteristicLink> charLinks) {
		/* Remove Variable from DB */
		boolean removeStatus = mea.entityRemove(_connection);
		
		if (removeStatus) {
			if ((charLinks != null) &&
					(!charLinks.isEmpty())) {
				Iterator<CharacteristicLink> linkIter = charLinks.iterator();
				
				while(linkIter.hasNext()) {
					CharacteristicLink link = linkIter.next();
					
					/* Remove Characteristics of Variable from DB */ 
					Characteristics chr = link.getCharacteristic();
					chr.entityRemove(_connection);
									
					/* Remove CharacteristicLink from DB */
					link.entityRemove(_connection);
				}
			}
			
			try {
				_connection.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return removeStatus;
	}

	private void warnEditingMeasurement(ArrayList<Project> projects) {
		ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, _view.getLocale());
		
		if ((projects != null)
				&& (projects.size() > 0)) {
			String specificWarning = bundle.getString(DONT_TRIFLE_MEA) +"\n\n"+ bundle.getString(PRO_SHARING_MEA) +"\n";
			
			Iterator<Project> iterator_p = projects.iterator();
			while (iterator_p.hasNext()) {
				Project project = iterator_p.next();
				
				specificWarning += project.getName() + (project.isFinished() ? "  ["+project.getFinishedSince()+"]":"") +"\n";
			}
			
			JOptionPane.showMessageDialog(_view.getAppFrame(), specificWarning, bundle.getString(WARNING), JOptionPane.WARNING_MESSAGE);
		}	
	}
	
	private void warnEditingVariable(ArrayList<Project> projects) {
		ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, _view.getLocale());
		
		if ((projects != null)
				&& (projects.size() > 0)) {
			String specificWarning = bundle.getString(DONT_TRIFLE_VAR) +"\n\n"+ bundle.getString(PRO_SHARING_VAR) +"\n";
			
			Iterator<Project> iterator_p = projects.iterator();
			while (iterator_p.hasNext()) {
				Project project = iterator_p.next();
				
				specificWarning += project.getName() + (project.isFinished() ? "  ["+project.getFinishedSince()+"]":"") +"\n";
			}
			
			JOptionPane.showMessageDialog(_view.getAppFrame(), specificWarning, bundle.getString(WARNING), JOptionPane.WARNING_MESSAGE);
		}
	}

	/**
	 * @return
	 */
	private boolean removeProject() {
		ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, _view.getLocale());
		final String[] DIALOG_BUTTONS_TITLES	= {bundle.getString(REMOVE), bundle.getString(CANCEL), bundle.getString(HELP)};
		
		int retValue = 2;
	  	while (retValue == 2) 
	   	{
	  		retValue = JOptionPane.showOptionDialog(		
    			_view.getAppFrame(), 
    			bundle.getString(REMOVE_QUESTION),
    			bundle.getString(RP_TITLE),
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, 
                DIALOG_BUTTONS_TITLES, 
                DIALOG_BUTTONS_TITLES[0]
	  		);
	  		
	  		if (retValue == 1) 
	  			return false;
	  		
	  		if (retValue == 2) {
	  			callBrowser(_view.getLocale(), "help.html");
	  		}
	  		
	  		if (retValue == -1) 
	  			return false;
	   	}
	  	
	  	/* Remove project and components from the database: */
	  	_view.showWaitingCursor();
	  	Project		project	= _model.getProject();
	  	ProContent	content	= project.getContent();
	  	
	  	ArrayList<InstanceLink>	instance_links		= content.getRefs();
	  	Iterator<InstanceLink>	iter_instance_links	= instance_links.iterator();  	
	  	while (iter_instance_links.hasNext()) {
	  		InstanceLink instance_link = iter_instance_links.next();
	  		
	  		WorkStepInstance	instance		= instance_link.getInstance();
	  		InstanceMap			instance_map	= content.getInstanceMapByInstance(instance_link);
	  		
	  		ArrayList<AttributeLink> attribute_links = content.getLinksByLayer(instance);
	  		Iterator<AttributeLink> iter_attribute_links = attribute_links.iterator();
	  		while (iter_attribute_links.hasNext()) {
	  			AttributeLink attribute_link = iter_attribute_links.next();
	  			
	  			Attributes		attribute		= attribute_link.getAttribute();
	  			AttributeMap	attribute_map	= content.getAttributeMapByAttribute(attribute_link);
	  			
	  			if (attribute instanceof Variable) {
	  	  			ArrayList<CharacteristicLink> char_links = content.getCharacteristicLinksByAttribute(attribute);
	  	  			Iterator<CharacteristicLink> iter_char_links = char_links.iterator();
	  	  			while (iter_char_links.hasNext()) {
	  	  				CharacteristicLink char_link = iter_char_links.next();
	  	  				
	  	  				CharacteristicMap	char_map	= content.getCharacteristicMapByCharacteristic(char_link);
	  	  				
	  	  				if (char_map != null)	char_map.entityRemove(_connection);
	  	  			}
	  			}
	  			
	  	  		if ((attribute instanceof OperaIndicator) ||
	  	  				(attribute instanceof ConDimension)) {
	  	  			
	  	  			ArrayList<CharacteristicLink> char_links = content.getCharacteristicLinksByAttribute(attribute);
	  	  			Iterator<CharacteristicLink> iter_char_links = char_links.iterator();
	  	  			while (iter_char_links.hasNext()) {
	  	  				CharacteristicLink char_link = iter_char_links.next();
	  	  				
	  	  				Characteristics		charac		= char_link.getCharacteristic();
	  	  				CharacteristicMap	char_map	= content.getCharacteristicMapByCharacteristic(char_link);
	  	  				
	  	  				if (char_map != null)	char_map.entityRemove(_connection);
	  	  				if (charac != null)		charac.entityRemove(_connection);
	  	  				
	  	  				if (char_link != null)	char_link.entityRemove(_connection);
	  	  			}
	  	  				  	  			
	  				if (attribute != null)		attribute.entityRemove(_connection);	
	  			}
	  	  		
	  	  		if (attribute_map != null)	attribute_map.entityRemove(_connection);
	  	  		if (attribute_link != null)	attribute_link.entityRemove(_connection);
	  		}
	  		
	  		if (instance_map != null)	instance_map.entityRemove(_connection);
	  		if (instance != null)		instance.entityRemove(_connection);
  		
	  		if (instance_link != null)	instance_link.entityRemove(_connection);
	  	}
	  	
	  	ArrayList<AttributeComp> comparisons = content.getComps();
	  	Iterator<AttributeComp>	iter_comparisons = comparisons.iterator();
	  	while (iter_comparisons.hasNext()) {
	  		AttributeComp comp = iter_comparisons.next();
	  		
	  		BiasMetadata bias = comp.getBiasMetadata();
	  		
	  		if (bias != null)	bias.entityRemove(_connection);
	  		if (comp != null)	comp.entityRemove(_connection);
	  	}
	  	
	  	project.entityRemove(_connection);
	  	_view.showDefaultCursor();

	  	return true;
	}

	/* Handling Menu Commands */
	/* Menu: File */
	/**
	 * 
	 */
	private void exit() {
		if (isProjectInWork() && /* and therefore an active project exists */
				(!_model.getProject().isFinished() &&
				(_model.getProject().isEditedByUser()))) {
			int n = _view.exitDialog();
			
			switch (n) {
				case 0:
					saveProject();
				case 1:
					closeProject(true);
					break;
				/* handle cancel option: */
				case 2:
				default:
					return;
			}
		}
		
		/* handle exit option: */
		System.exit(0);
	}
	
	/* Menu: Search */
	
	/* Execute Search from Dialogue Search or from Tab SearchVariable */
	public static ArrayList<Object> search(int classID, String fullSql) {
		ArrayList<Object> resultSet = DBContent.search(classID, fullSql);
			
		return resultSet;
	}
	
	/* Fetch Project Name from Dialogue Search or from Tab SearchVariable */
	public static String searchForProjectName(String sql) {
		return DBContent.searchForProjectName(sql);
	}
	
	public static String searchForVariableValues(String sql) {
		
		return DBContent.searchForVariableValues(sql);
	}
	
	public static String searchForMeasurementCategories(String sql) {
		
		return DBContent.searchForMeasurementCategories(sql); 
	}
	
	public static String searchForProjectNote(String sql) {
		
		return DBContent.searchForProjectNote(sql); 
	}

	/* Menu: Project */
	/**
	 * @return
	 */
	private boolean newProject() {
		if (isProjectInWork()) { /* and therefore an active project exists */
			int n = _view.saveProjectDialog();
			
			switch (n) {
				case 0:
					saveProject();
				case 1:
					closeProject(true);
					break;
				/* handle cancel option: */
				case 2:
				default:
					return false;
			}
		}
		
		_model.setProject(new Project());
		ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, _view.getLocale());
		_model.getProject().setName(bundle.getString(NEW_PROJECT)+generateNegID());
		_model.getProject().getContent().getMeasurement().setName(bundle.getString(NEW_MEASUREMENT)+generateNegID());
		_model.getUser().addUnfinProject(_model.getProject());
		_model.getProject().setModelVersion(_supported_model_version); 
		
		Participant par = new Participant();
		par.setUser(_model.getUser());
		par.setProject(_model.getProject());
		par.setRole(ParticipantRole.PROJECT_OWNER);
		_model.getProject().addParticipant(par);
		
		setProjectInWork(true);
		_model.setIsBrowsing(false);
		
		return true;
	}

	/**
	 * @param projectID
	 */
	private void openProject(int projectID) {
		
		if (isProjectInWork()) { /* and therefore an active project exists */			
			if ((!_model.getProject().isFinished()) &&
					(_model.getProject().isEditedByUser())){ 
				int n = _view.openProjectDialog();
				
				switch (n) {
					case 0:
						saveProject();
					case 1:
						closeProject(true);
						break;
					default:
						return;
				}
			} else {
				this.closeProject(true);
			}
		}
		
		_view.showWaitingCursor();
		_model.setProject(new Project(_connection, projectID));
		
		boolean editedByUser = 
				_model.getProject().getEditedByUser(_connection, _user, projectID);
		
		if (!editedByUser) {
			int user_id = _model.getProject().getEditingUser(_connection, _user, projectID);
			
			ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, _view.getLocale());
			
			User anotherUser = new User();
			anotherUser.setEntityID(user_id);
			anotherUser.entityLoad(_connection);
			
			_view.showDefaultCursor();
			JOptionPane.showMessageDialog(_view.getAppFrame(), 
					bundle.getString(ALREADY_OPENED_1) +anotherUser.getName()+ bundle.getString(ALREADY_OPENED_2), bundle.getString(WARNING), 
					JOptionPane.OK_CANCEL_OPTION);
			_view.showWaitingCursor();
		}
		
		_model.getProject().setEditedByUser(editedByUser);		
		_model.setIsBrowsing(true);
		
		/* initialize(active_project): */
		ProContent content = _model.getProject().getContent();
		if (content != null) {
			content.entityLoad(_model.getProject(), _connection);
			
			/* Add missing maps: */
			Iterator<AttributeLink> attrLinkIter =  _model.getProject().getContent().getLinks().iterator();
			while (attrLinkIter.hasNext()) {
				AttributeLink iterAttrLink = attrLinkIter.next();
				
				AttributeMap map = _model.getProject().getContent().getMapBySourceAttribute(iterAttrLink);
				
				if (!(map != null)) {
					map = new AttributeMap();
					map.setEntityID(generateNegID());
					if (iterAttrLink.getAttribute().getEntityType().equals(EntityType.MEASUREMENTS))
						map.setAttributeMapType(AttributeMapType.NONE);
					else if (iterAttrLink.getAttribute().getEntityType().equals(EntityType.CON_DIMENSIONS))
						map.setAttributeMapType(AttributeMapType.SPECIFICATION);
					else if (iterAttrLink.getAttribute().getEntityType().equals(EntityType.OPERA_INDICATORS))
						map.setAttributeMapType(AttributeMapType.ASSIGNED_INDICATOR);
					else if (iterAttrLink.getAttribute().getEntityType().equals(EntityType.VARIABLES))
						map.setAttributeMapType(AttributeMapType.ASSIGNED_VARIABLE);
					map.setSourceAttribute(iterAttrLink);
					
					map.setBelongsTo( 
							(_model.getProject()).getContent().getInstanceMapByInstance(
									(_model.getProject()).getContent().getRefByInstance(
											iterAttrLink.getInstance()
									) 
							) 
					);
					
					(_model.getProject()).getContent().addMap(map);
				}
			}
			
			Iterator<CharacteristicLink> charLinkIter =  _model.getProject().getContent().getCharacteristicLinks().iterator();
			while (charLinkIter.hasNext()) {
				CharacteristicLink	iterCharLink	= charLinkIter.next();				
				CharacteristicMap	map				= _model.getProject().getContent().getCharMapBySourceAttribute(iterCharLink);
				
				if (!(map != null)) {
					map = new CharacteristicMap();
					map.setEntityID(generateNegID());
					if (iterCharLink.getCharacteristic().getEntityType().equals(EntityType.CATEGORIES))
						map.setType(CharacteristicMapType.NONE);
					else if (iterCharLink.getCharacteristic().getEntityType().equals(EntityType.CON_SPECIFICATIONS))
						map.setType(CharacteristicMapType.SPECIFICATION);
					else if (iterCharLink.getCharacteristic().getEntityType().equals(EntityType.OPERA_PRESCRIPTIONS))
						map.setType(CharacteristicMapType.PRESCRIPTION);
					else if (iterCharLink.getCharacteristic().getEntityType().equals(EntityType.DATA_VALUES))
						map.setType(CharacteristicMapType.VALUE);
					map.setSourceCharacteristic(iterCharLink);
					
					map.setBelongsTo(  
							(_model.getProject()).getContent().getAttributeMapByAttribute(
									(_model.getProject()).getContent().getLinkByAttribute(
											iterCharLink.getAttribute()
									) 
							) 
					);
					
					(_model.getProject()).getContent().addCharacteristicMap(map);
				}
			}
			_view.showDefaultCursor();
		
			setProjectInWork(true);
		}
	}
	
	/**
	 * @param projectID
	 * @return
	 */
	private boolean checkProProject(int projectID) {
		Boolean isProProject = false;
		
		Project	prj			= new Project(projectID);
		Boolean	loadStatus	= prj.loadProjectHeader(_connection);
	
		if (loadStatus) {
			Progress progress = prj.getProgress();
			
			if ((progress.isReferenceTabDone()) ||
					(progress.isConceptualStepTabbedPanelAvailable()) ||
					(progress.isOperationalStepTabbedPanelAvailable()) ||
					(progress.isSearchNCompareStepTabbedPanelAvailable()))
				isProProject = true;
		}
		
		return isProProject;
	}
	
	/**
	 * @param projectID
	 * @return
	 */
	private boolean checkAppVersion(int projectID) {
		boolean isAppVersion = true;
		
		Project	prj			= new Project(projectID);
		Boolean	loadStatus	= prj.loadProjectHeader(_connection);
		
		if (loadStatus) {
			Float prjVersion = Float.parseFloat(prj.getModelVersion());
			Float appVersion = Float.parseFloat(_supported_model_version);
			
			if (prjVersion > appVersion)
				isAppVersion = false;
		}
		
		return isAppVersion;
	}
	
	/**
	 * @param alreadySaved
	 * @return
	 */
	private boolean closeProject(boolean alreadySaved) {
		if (!isProjectInWork()) { /* and therefore no active project exists */
			return false;
		}
		if (isProjectInWork() && /* and therefore an active project exists */
				!alreadySaved  &&
				(!_model.getProject().isFinished() &&
				(_model.getProject().isEditedByUser()))) { 
			
			int n = _view.saveProjectDialog();
			
			switch (n) {
				case 0:
					saveProject();
				case 1:
					break;
				default:
					return false;
			}
		}
		
		if (_model.getProject().isEditedByUser())
			_model.getProject().removeEditedByUser(_connection, _user, _model.getProject().getEntityID());
		
		_model.setProject(new Project());
		this.setProjectInWork(false);
		
		
		
		return true;
	}
	
	/**
	 * 
	 */
	private void saveProject() {
		_view.showWaitingCursor(); 
		
		Tab tab = _view.getPreviousStep();
		
		if (tab instanceof TabProject) handleProjectTab(tab);
		if (tab instanceof TabConcept) handleConceptTab(tab);
		if (tab instanceof TabLiterature) handleLiteratureTab(tab);
		if (tab instanceof TabMeasurement) handleMeasurementTab(tab);
		
		if (tab instanceof TabDimension) handleDimensionTab(tab);
		if (tab instanceof TabSpecifications) handleSpecificationsTab(tab);
		if (tab instanceof TabMapDimensionInstance)	handleMapDimensionInstanceTab(tab);
		if (tab instanceof TabMapDimensionAttribute)	handleMapDimensionAttributeTab(tab);
		if (tab instanceof TabMapDimensionChar)	handleMapDimensionCharTab(tab);
		
		if (tab instanceof TabOSInstance) handleOSInstanceTab(tab);
		if (tab instanceof TabIndicator) handleIndicatorTab(tab);
		if (tab instanceof TabPrescriptions) handlePrescriptionsTab(tab);
		if (tab instanceof TabMapIndicatorInstance) handleMapIndicatorInstanceTab(tab);
		if (tab instanceof TabMapIndicatorAttribute) handleMapIndicatorAttributeTab(tab);
		if (tab instanceof TabMapIndicatorChar) handleMapIndicatorCharTab(tab);
		
		if (tab instanceof TabSearchVariable) handleSearchVariableTab(tab);
		if (tab instanceof TabCompareMetadata) handleCompareVariablesTab(tab);
		if (tab instanceof TabCompareValues) handleCompareValuesTab(tab);
		
		if (tab instanceof TabDRInstance) handleDRInstanceTab(tab);
		if (tab instanceof TabVariable) handleVariableTab(tab);
		if (tab instanceof TabValues) handleValuesTab(tab);
		if (tab instanceof TabMapVariableInstance) handleMapVariableInstanceTab(tab);
		if (tab instanceof TabMapVariableAttribute) handleMapVariableAttributeTab(tab);
		if (tab instanceof TabMapVariableChar) handleMapVariableCharTab(tab);
		
		
		if (_model.getProject().entityStore(_connection) &&
				(_model.getProject().getContent().entityStore(_model.getProject(), _connection))) {
			try {
				_connection.commit();
			} catch (SQLException e) {
				_view.showDefaultCursor();
				
				JOptionPane.showMessageDialog(
						null, e.getMessage(), "SQLException:",  JOptionPane.ERROR_MESSAGE);
					  	
				/* DEMO ONLY */
				System.err.println("SQLException: " + e.getMessage());
				e.printStackTrace();
			}
		}
		_view.showDefaultCursor();
	}

	/**
	 * 
	 *	former publishProject
	 */
	private void finishProject() {		
		/* Get final name: */
		String	finalName	= "";

		ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, _view.getLocale());
		
		JOptionPane.setDefaultLocale(_view.getLocale());
		finalName = (String)JOptionPane.showInputDialog(_view.getAppFrame(), bundle.getString(FINAL_NAME), "", JOptionPane.PLAIN_MESSAGE, null, null, _model.getProject().getName());
		
		if ( (finalName != null) && 
				!finalName.equals("")) {
			/* Set final name: */
			_model.getProject().setName(finalName);
			
			/* Get publication date: */
		    java.util.Date	utilDate	= new java.util.Date();
		    java.sql.Date	sqlDate		= new java.sql.Date(utilDate.getTime());
		    
		    /* Set publication date: */
			_model.getProject().setFinishedSince(sqlDate);
			
			/* Destine project type */
			ProjectType proType = ProjectType.CONCEPTUAL_BASIS_CSI;

			if ((_model.getProject().getContent().getIndicators() != null) && 
					(_model.getProject().getContent().getIndicators().size() > 0))
				proType = ProjectType.DOCUMENTATION_CSI;
			if ((_model.getProject().getContent().getVariables() != null) &&
					(_model.getProject().getContent().getVariables().size() > 0))
				proType = ProjectType.HARMONIZATION_PROJECT;
			
			/* Set project type */
			_model.getProject().setType(proType);
			
			/* Set Version */
			_model.getProject().setVersion("1.0");
			
			/* Finish the project: */
			saveProject();
			
			/* Re-open the finished project: */
			int projectID = _model.getProject().getEntityID();	
			closeProject(true);
			openProject(projectID);
		}		
	}
	
	/**
	 * 
	 */
	private Person addPerson() {	   
		_view.showWaitingCursor();
		ArrayList<Person>	personList	= Person.getAllPersons(_connection);
		_view.showDefaultCursor();
		
		AddPersonDialog im = new AddPersonDialog(personList, _view.getLocale(), _view.getFont(), this);
		
		Person newPerson = null; 
	   
		_view.showWaitingCursor(); 
		if (im.isAccepted()) {	   
			newPerson = im.getAddedPerson(); 
			
			Boolean storeStatus = newPerson.entityStore(_connection);	
			if (storeStatus) {
				try {
					_connection.commit();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(
							null, e.getMessage(), "SQLException:",  JOptionPane.ERROR_MESSAGE);
					  	
					/* DEMO ONLY */
					System.err.println("SQLException: " + e.getMessage());
					e.printStackTrace();
				}
			}
	   	}
		_view.showDefaultCursor(); 
		
		return newPerson; 
	}

	/**
	 * 
	 */
	private Participant addParticipant() {
		/* Call Dialogue "ManageParticipantContainer" */
		_view.showWaitingCursor();
		ArrayList<User>	userList	= User.getAllUsers(_connection);
		_view.showDefaultCursor();
		
		AddParticipantDialog im = new AddParticipantDialog(userList, _model.getProject(), _view.getLocale(), _view.getFont(), this);

		Participant	newParticipant = null; 
		
		_view.showWaitingCursor(); 
		if (im.isAccepted()) {
			newParticipant = im.getAddedParticipant(); 
			
			Boolean	storeStatus	= newParticipant.entityStore(_connection);			
			if (storeStatus) {
				try {
					_connection.commit();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(
							null, e.getMessage(), "SQLException:",  JOptionPane.ERROR_MESSAGE);
						  	
					/* DEMO ONLY */
					System.err.println("SQLException: " + e.getMessage());
					e.printStackTrace();
				}
			}
		}
		_view.showDefaultCursor(); 
		
		return newParticipant;  
	}
	
	/* Menu: Basket */
	/**
	 * 
	 */
	private void openBasket() {
		_view.showWaitingCursor();
		_model.getUser().getBasket().clearContents();
		
		BasketRef basketRef = new BasketRef();
		basketRef.setReferenced(_model.getUser());
		ArrayList<Integer> basketIDs = basketRef.loadReferences(_connection);
		
		if (basketIDs.size() > 0) { 
			Basket basket = new Basket();
			basket.setEntityID(basketIDs.get(0)); /* Only one basket supported for now */
			basket.entityLoad(_connection);
			
			setBasketLoadStatus(true);
			_model.getUser().setBasket(basket);
			_model.getUser().getBasket().setTempBasket(false); 
		}
		_view.showDefaultCursor();
	}
	
	/**
	 * @param loadStatus
	 */
	private void setBasketLoadStatus(boolean loadStatus) {
		_model.getUser().getBasket().setLoadstatus(loadStatus);
		
	}
	
	/**
	 * 
	 */
	private void closeBasket() {
		_model.getUser().getBasket().clearContents();
		_model.getUser().getBasket().setTempBasket(true); 
	}
	
	/**
	 * @param conn
	 * @param user
	 */
	public void saveBasket(Connection conn, User user) {
		boolean storeStatus;
		
		_view.showWaitingCursor();
		_model.getUser().getBasket().setOwner(user);
		_model.getUser().getBasket().entityRemove(conn);
		
		storeStatus = 
			_model.getUser().getBasket().entityStore(conn);
		
		if (storeStatus) {
			BasketRef ref = new BasketRef();
			ref.setReferenced(user);
			ref.setBasket(_model.getUser().getBasket());
			ref.setRefRelationtype(RefRelationtype.ASSIGNED);
			
			storeStatus = 
				ref.entityStore(conn);
		}
		
		if (storeStatus) {
			try {
				conn.commit();
			} catch (SQLException e) {
				_view.showDefaultCursor();
				
				JOptionPane.showMessageDialog(
						null, e.getMessage(), "SQLException:",  JOptionPane.ERROR_MESSAGE);
					  	
				/* DEMO ONLY */
				System.err.println("SQLException: " + e.getMessage());
				e.printStackTrace();
			}
		}
		_view.showDefaultCursor();
	}
	
	/**
	 * 
	 */
	private void clearBasket() {
		_model.getUser().getBasket().entityRemove(_connection);
		closeBasket();		
	}
	
	/* fill Basket from Search */
	/**
	 * @param searchResults
	 */
	public void addToBasket(ArrayList<Object> searchResults) {
		
		if (searchResults != null) {
			Iterator<Object> iterator = searchResults.iterator();
							
			while(iterator.hasNext()) {
				Object object = iterator.next();
					
				_model.getUser().getBasket().addContent(object);
			}
			
			IdentifiedParameter parameter = new IdentifiedParameter(ActionCommandID.ADD_TO_BASKET);
			Object[] addenda = new Object[1];
			addenda[0] = _model;
			parameter.setParameters(addenda);
			
			setChanged();
			notifyObservers(parameter);
		}
	}
	
	/* Menu: User */
	/**
	 * @return
	 */
	private boolean login() {		
		User			user					= User.getAnonymousUser();
		
		ResourceBundle	resourceBundle			= ResourceBundle.getBundle(BUNDLE, _view.getLocale());
		
       	boolean			registration			= false;
        int				countOfLoginAttempts	= 0;        
                
        while (!registration && countOfLoginAttempts < 3) {
        	LoginDialog dialog = new LoginDialog(_pref, _view.getLocale(), _view.getFont(), this);
        	
        	user = dialog.getLoginUser();
        	
        	if (!user.equals(User.getAnonymousUser())) {
        	
	        	registration = user.checkUserAccount(_connection);
	        	
	        	if (!registration)
	        		JOptionPane.showMessageDialog(_view.getAppFrame(), resourceBundle.getString(WRONG_INPUT));
	        	
	            dialog.setVisible(false);
	            dialog.dispose();
	            
	            countOfLoginAttempts++;
        	} else
        		countOfLoginAttempts = 3;
        };
        if (!registration) {
        	JOptionPane.showMessageDialog(_view.getAppFrame(), resourceBundle.getString(LOGIN_FAILED));
        }
        else {
    		closeProject(false);
    		
    		user.setFinProjects(this.getFinProjects(_connection, user));
    		user.setUnfinProjects(this.getUnfinProjects(_connection, user));
    		user.entityLoad(_connection);
    		
    		_user = user;
        	_model.setUser(user);
        	
        	System.err.println("Successfully logged in by user " + _model.getUser().getName());
        }
        return registration;
	}
	
	/**
	 * 
	 */
	private void logoff() {
		if ((!isProjectInWork()) || 
				(isProjectInWork() && closeProject(false))) {		        
			_model.setUser(User.getAnonymousUser());
			_model.setProject(new Project());
			this.setProjectInWork(false);
			
    		_model.getUser().setFinProjects(this.getFinProjects(_connection, _model.getUser()));
    		_model.getUser().setUnfinProjects(this.getUnfinProjects(_connection, _model.getUser()));
		}
	}
	
	/**
	 * former getUnpublProjects
	 * 
	 * @param con
	 * @param user
	 * @return
	 */
	public ArrayList<Project> getUnfinProjects(Connection con, User user) {
		ArrayList<Project> unfinProjects = Project.getUnfinProjects(con, user);
		
		return unfinProjects;
	}
	
	/**
	 * former getPublProjects
	 * 
	 * @param con
	 * @param user
	 * @return
	 */
	public ArrayList<Project> getFinProjects(Connection con, User user) {
		ArrayList<Project> finProjects = Project.getFinProjects(con, user);
		
		return finProjects;
	}
	
	/**
	 * 
	 */
	private User addUser() {
		/* Call Dialogue "ManageUserContainer" */
		_view.showWaitingCursor();
		ArrayList<User>		userList	= User.getAllUsers(_connection);
		ArrayList<Person>	personList	= Person.getAllPersonsWithRole(_connection, PersonRole.CERTIFIED_USER);
		_view.showDefaultCursor();
		
		AddUserDialog im = new AddUserDialog(userList, personList, _connection, _view.getLocale(), _view.getFont(), this);
		
		_view.showWaitingCursor(); 
		User newUser = null; 
		if (im.isAccepted()) {		
			newUser = im.getAddedUser(); 
			
			Boolean storeStatus = newUser.entityStore(_connection);			
			if (storeStatus) {
				try {
					_connection.commit();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(
							null, e.getMessage(), "SQLException:",  JOptionPane.ERROR_MESSAGE);
						  	
					/* DEMO ONLY */
					System.err.println("SQLException: " + e.getMessage());
					e.printStackTrace();
				}
			}
		}
		_view.showDefaultCursor(); 
		
		return newUser; 
	}
	
	/**
	 * 
	 */
	private void changePassword() {
		/* Call Dialogue "ManageUserPassword" */
		ManageUserPasswordDialog im = new ManageUserPasswordDialog(_view.getLocale(), _user, _connection, _view.getFont(), this);
		
		if (im.isAccepted()) {
			String pwd = im.getPassword();
			
			_user.setPassword(pwd);
			Boolean storeStatus =_user.entityStore(_connection);
			
			if (storeStatus) {
				try {
					_connection.commit();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(
							null, e.getMessage(), "SQLException:",  JOptionPane.ERROR_MESSAGE);
						  	
					/* DEMO ONLY */
					System.err.println("SQLException: " + e.getMessage());
					e.printStackTrace();
				}
			}
		}		
	}
	
	/**
	 * 
	 */
	private User overwritePassword() {
		User user = new User();
		
		/* Call Dialogue "OverwriteUserPassword" */
		OverwriteUserPasswordDialog im = new OverwriteUserPasswordDialog(_view.getLocale(), user, User.getAllUsers(_connection), _connection, _view.getFont(), this);
		
		if (im.isAccepted()) {
			String pwd = im.getPassword();
			
			user.setPassword(pwd);
			Boolean storeStatus = user.storePassword(_connection);
			
			if (storeStatus) {
				try {
					_connection.commit();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(
							null, e.getMessage(), "SQLException:",  JOptionPane.ERROR_MESSAGE);
						  	
					/* DEMO ONLY */
					System.err.println("SQLException: " + e.getMessage());
					e.printStackTrace();
				}
			}
		} else
			user = null;
		
		return user; 
	}

	/* Menu: Extra */
	/**
	 * @return
	 */
	private ImportStatistics importVariable() {
		ImportStatistics statistics = new ImportStatistics();
		statistics.setImportingAdmin(_user);
		
		List<Variable>				retVarList		= new ArrayList<Variable>();			
		List<CharacteristicLink>	retCharLinks	= new ArrayList<CharacteristicLink>();
		
		boolean						useDefault		= false;
		boolean 					setDefault		= false;
		Study						defaultStudy	= new Study();
		
		/* Set up the validation error listener. */
		@SuppressWarnings("rawtypes")
		List		validationErrors	= new ArrayList();
		XmlOptions	validationOptions	= new XmlOptions();
		validationOptions.setErrorListener(validationErrors);
		
		JFileChooser.setDefaultLocale(_view.getLocale());
	    JFileChooser fc = new JFileChooser ();
	    
		ResourceBundle	resourceBundle	= ResourceBundle.getBundle(BUNDLE_F, _view.getLocale());
		String			file_name		= resourceBundle.getString(FILE_NAME);
		
		String path_0 = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		path_0 = path_0.replace("/", "\\\\"); // use File.separator in CharmStatsPro
		path_0 = path_0.replace(file_name, ""); // filename stored in resource FrameBundle
		path_0 = path_0 + "imports\\\\";
		fc.setCurrentDirectory (new File (path_0));
	    		    
	    FileFilter spssFilter	= new OpenDialogSPSSFilter();
	    /* Disabled for now:
	    FileFilter ddiFilter	= new OpenDialogDDIFilter();
	    FileFilter stataFilter	= new OpenDialogSTATAFilter();
	    FileFilter sasFilter	= new OpenDialogSASFilter();
	    FileFilter mplusFilter	= new OpenDialogMplusFilter();
	    */
	    
	    fc.addChoosableFileFilter(spssFilter);
	    /* Disabled for now: 
	    fc.addChoosableFileFilter(stataFilter);
	    fc.addChoosableFileFilter(ddiFilter);
	    fc.addChoosableFileFilter(sasFilter);
	    fc.addChoosableFileFilter(mplusFilter);
	    */
	    
	    fc.setFileFilter(spssFilter);
	    
		ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, _view.getLocale());	
	    fc.setDialogTitle(bundle.getString(IMPORT_VARIABLE));
	    
	    int returnVal = fc.showOpenDialog(_view.getAppFrame());
	    if (returnVal == JFileChooser.APPROVE_OPTION) {
	    	FileFilter	ff		= fc.getFileFilter();
	    	File 		file	= fc.getSelectedFile();
	    	
	    	statistics.setImportedDatafile(file.getName()); 
	    	_view.showWaitingCursor();
	    	
	    	switch (((OpenDialogFileFilter)ff).getFilterID()) {
	    		case OpenDialogFileFilter.ddiFilterID:
	    			DDIInstanceDocument ddiiDoc = null;
	    		    try {
	    		    	ddiiDoc = DDIInstanceDocument.Factory.parse(file);
	    		    } catch (XmlException e) {
	    		        JOptionPane.showMessageDialog(_view.getAppFrame(), bundle.getString(READ_ERROR)); //"Read Error");		        
						_view.showDefaultCursor();
						
						return null; 
	    		    } catch (IOException e) {
	    		    	JOptionPane.showMessageDialog(_view.getAppFrame(), bundle.getString(READ_ERROR)); //"Read Error");
						_view.showDefaultCursor();

						return null; 
	    		    }
	    		    
	    		    	    		    
//	    		    /* DEVELOPMENT ONLY - TO BE REMOVED: */
//	    			if (ddiiDoc != null) {
//	    				boolean isValid = ddiiDoc.validate(validationOptions);
//	    						    				
//	    				System.out.println("This Document is "+ (isValid ? "" : "not ") +"valid!");		    				
//	    				if (!isValid)
//	    				{
//	    				    @SuppressWarnings("rawtypes")
//							Iterator iterErrors = validationErrors.iterator();
//	    				    while (iterErrors.hasNext())
//	    				    {
//	    				        System.out.println(">> " + iterErrors.next() + "\n");
//	    				    }
//	    				}
//	    			}
//	    			/* DEVELOPMENT ONLY */
	    				    			

    				String queryVarExpression =
	    				"declare namespace l='ddi:logicalproduct:3_1';" +
	    				"$this//l:Variable";
    				
    				if (ddiiDoc.selectPath(queryVarExpression).length > 0) {
	    				VariableType[] imp_variables = (VariableType[])ddiiDoc.selectPath(queryVarExpression);
	    				
	    				for (int i=0; i<imp_variables.length; i++) {
	    					VariableType	ddiVar	= imp_variables[i];
	    					Variable		csVar	= new Variable();
	    					
	    					/* Fill Variable csVar with content from ddiVar */
	    					
	    					/* Set ID */
	    					String varID = ddiVar.getId();
	    					csVar.setUUID(varID);
	    					
	    					/* Set Variable Name */
	    					List<NameType>		varNameList		= ddiVar.getVariableNameList();
	    					String				preferredName	= "";
	    					
	    					if (!varNameList.isEmpty())
	    						preferredName = varNameList.get(0).getStringValue();
	    					
	    					Iterator<NameType>	varNameListIter	= varNameList.iterator();
	    					while (varNameListIter.hasNext()) {
	    						NameType	varName			= varNameListIter.next();			    						
	    									
	    						if (varName.getIsPreferred())
	    							preferredName	= varName.getStringValue();
	    					}
	    					csVar.setName("p"+preferredName); 
	    					
	    					/* Set Variable Label */
	    					List<LabelType>		varLabelList	= ddiVar.getLabelList();
	    					String 				preferredLabel	= "";
	    					
	    					if (!varLabelList.isEmpty()) {
	    						LabelType	varLabel		= varLabelList.get(0);

	    						XmlCursor	labelCursor		= varLabel.newCursor();
	    									preferredLabel	= labelCursor.getTextValue();
	    					}
	    					csVar.setLabel(preferredLabel);
	    					
	    					/* Representation */
	    					RepresentationType		varRepresentation	= ddiVar.getRepresentation();
	    					CodeRepresentationType	codeRepresentation	= (CodeRepresentationType)varRepresentation.getValueRepresentation();
	    					ReferenceType			codeSchemeRef		= codeRepresentation.getCodeSchemeReference();
	    					String					codeSchemeID		= codeSchemeRef.getIDArray(0).getStringValue();
	    					
	    					
	    					String queryCodeSchemeExpression =
				    			"declare namespace l='ddi:logicalproduct:3_1';" +
				    			"$this//l:CodeScheme[@id='"+ codeSchemeID +"']";
	    					
	    					if (ddiiDoc.selectPath(queryCodeSchemeExpression).length > 0) {
	    						CodeSchemeType[] imp_codeSchemes = (CodeSchemeType[])ddiiDoc.selectPath(queryCodeSchemeExpression);
	    						
	    						List<CodeType> code_list = imp_codeSchemes[0].getCodeList();
	    						for (int j=0; j<code_list.size(); j++) {
	    							String			code_value	= code_list.get(j).getValue();
	    							String			cat_label	= "";
	    							ReferenceType	cat_ref		= code_list.get(j).getCategoryReference();
	    							String			catID		= cat_ref.getIDArray(0).getStringValue();
	    							
			    					String queryCategoryExpression =
							    		"declare namespace l='ddi:logicalproduct:3_1';" +
							    		"$this//l:Category[@id='"+ catID +"']";
			    					
			    					if (ddiiDoc.selectPath(queryCategoryExpression).length > 0) {
			    						CategoryType[]	imp_categories	= (CategoryType[])ddiiDoc.selectPath(queryCategoryExpression);
			    						
			    						LabelType		imp_label		= ((CategoryType)imp_categories[0]).getLabelArray(0);
			    						XmlCursor		labelCursor		= imp_label.newCursor();
			    										cat_label		= labelCursor.getTextValue();
			    					}
			    					
			    					Value cs_val = new Value();
			    					cs_val.setLabel(cat_label);
			    					cs_val.setValue(code_value);
			    					
			    					CharacteristicLink cat_link = new CharacteristicLink();
			    					cat_link.setEntityID(generateNegID());
			    					cat_link.setAttribute(csVar);
			    					cat_link.setCharacteristic(cs_val);
			    					cat_link.setType(CharacteristicLinkType.VALUE);
			    					
			    					retCharLinks.add(cat_link);
	    						}
	    					}
	    					
	    					retVarList.add(csVar);
	    							    					
	    					/* Handle Questions: */
	    				    List<ReferenceType> question_list = ddiVar.getQuestionReferenceList();
	    				    if (!question_list.isEmpty()) {
	    				    	ReferenceType	question_ref	= question_list.get(0);			    				    	
	    				    	IDType			questionID		= question_ref.getIDArray(0);
			    				
			    				String queryQuestExpression =
			    					"declare namespace d='ddi:datacollection:3_1';" +
			    					"$this//d:QuestionItem[@id='"+ questionID.getStringValue() +"']";
			    				
			    				if (ddiiDoc.selectPath(queryQuestExpression).length > 0) {
			    					QuestionItemType[] imp_questions = (QuestionItemType[])ddiiDoc.selectPath(queryQuestExpression);

			    					QuestionItemType ddiQuest = imp_questions[0];
			    					
			    					Question csQuest = new Question();
			    					/* Fill Question with content from ddiQuest */
			    					if (!ddiQuest.getQuestionTextList().isEmpty()) {
			    						DynamicTextType queTextCon = ddiQuest.getQuestionTextArray(0);
			    						TextType queText = null;
			    						if (!queTextCon.getTextList().isEmpty()) {
			    							queText = queTextCon.getTextArray(0);
			    						}
			    						XmlCursor		questionCursor		= queText.newCursor();
										String			questionText		= questionCursor.getTextValue();
			    						csQuest.setText(questionText);
			    					}
			    						
			    					
			    					/* Now handle the Study Metadata : */
			    					Study	csStudy = new Study();
			    					/* Fill Study with content from ddiDocument */
			    					
			    					String queryStudyExpression =
					    					"declare namespace s='ddi:studyunit:3_1';" +
					    					"$this//s:StudyUnit";
			    					
			    					if (ddiiDoc.selectPath(queryQuestExpression).length > 0) {
			    						StudyUnitType[] imp_studies = (StudyUnitType[])ddiiDoc.selectPath(queryStudyExpression);
			    						
			    						/* ID of Study */
			    						csStudy.setUUID(imp_studies[0].getId());
			    						
			    						/* Title of Study */
			    						CitationType	study_cit	= imp_studies[0].getCitation();
			    						String			study_title = "";
			    						
			    						List<InternationalStringType> titleList = study_cit.getTitleList();
			    						if (!titleList.isEmpty()) {
			    							XmlCursor		titleCursor		= titleList.get(0).newCursor();
    														study_title		= titleCursor.getTextValue();
			    						}
			    						
			    						csStudy.setTitle(study_title);
			    					}
			    					
			    					csQuest.setSource(csStudy);
			    					
			    					/* Finish it with assigning Question to Variable: */
			    					csVar.setSource(csQuest);
			    				}
	    				    }
	    				}		    						    				
    				}	    				
	    			
	    			break;
	    		case OpenDialogFileFilter.spssFilterID:
					try {
						SPSSFile spss = new SPSSFile(file);
						spss.loadMetadata();
						spss.loadData();

						Document spssDoc = spss.getDDI3LogicalProduct();
						
						List<Node> codeSchNodeList = getNodeList(spssDoc,"CodeScheme","",""); 
						
						List<Node> questionNodeList = getNodeList(spssDoc,"QuestionItem","","");
						
						List<Node> varNodeList = getNodeList(spssDoc,"VariableScheme","","");
						
						Iterator<Node> nodeIt = varNodeList.iterator();
						while (nodeIt.hasNext()) {
							Node varNode = nodeIt.next();
							
							NodeList childs = varNode.getChildNodes();
							
							for (int i=0; i<childs.getLength(); i++) {
								retVarList.add(printVar(spssDoc, childs.item(i), retCharLinks));								
							}
						}
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (SPSSFileException es) {
						JOptionPane.showMessageDialog(_view.getAppFrame(), bundle.getString(READ_ERROR_NOT_VALID_SPSS));
						
						_view.showDefaultCursor();
						return null; 
					}
	    			
	    			break;
	    		case OpenDialogFileFilter.stataFilterID:
	    			 File stataFile = file;
	    			 
	    			 StataMetadataImport smi = new StataMetadataImport();
	    			 List<Variable> vars = smi.getVariables(stataFile);
	    			 
	    			 Iterator<Variable> vars_it = vars.iterator();
	    			 while (vars_it.hasNext()) {
	    				 Variable var = vars_it.next();
	    				 
	    				 /* Set ID */
	    				 var.setUUID((String.valueOf(UUID.randomUUID())));
	    				 
	    				 /* Representation */
	    				 List<Value> vals = smi.getValues(stataFile, var);
	    				 
	    				 Iterator<Value> vals_it = vals.iterator();
	    				 while (vals_it.hasNext()) {
	    					 Value val = vals_it.next();
	    					 
	    					 CharacteristicLink cat_link = new CharacteristicLink();
	    					 cat_link.setEntityID(generateNegID());
	    					 cat_link.setAttribute(var);
	    					 cat_link.setCharacteristic(val);
	    					 cat_link.setType(CharacteristicLinkType.VALUE);
	    					 
	    					 retCharLinks.add(cat_link);	    					 
	    				 }
	    				 
	    				 retVarList.add(var);
	    			 }
	    			 
	    			break;
	    		case OpenDialogFileFilter.sasFilterID:
	    			/* Pro only */
	    			break;
	    		case OpenDialogFileFilter.mplusFilterID:
	    			/* Pro only */
	    			break;
	    		default:
	    			/* DoNothing */
	    			break;
	    	}		    	
	    	
	    	_view.showDefaultCursor();
	    	
	    	/* Call DialogImportVariable, Parameter: resultList */	    	
	    	ArrayList<Variable> selection = 
	    			new ImportVariableSelectionDialog(_view.getAppFrame(), true, retVarList, _model, _view.getLocale(), _view.getFont()).getSelections();
	    	ArrayList<Variable> imported = new ArrayList<Variable>();
	    	
	    	/* Handle modified resultList */
	    	
	    	/* Check if Variable / Question / Study is already imported into the database */
	    	if (!selection.isEmpty()) {
	
				JOptionPane.showMessageDialog(_view.getAppFrame(),
					"<html>"+ bundle.getString(START_IMPORT_OF) +" <b>"+ selection.size() +"</b> "+ bundle.getString(VARIABLE)+"("+bundle.getString(VAR_S)+")</html>", 
					bundle.getString(INFORMATION), //"Information",					      
                    JOptionPane.INFORMATION_MESSAGE);
				
				int counter = 0;
	    		
	    		Iterator<Variable> iter = selection.iterator();
	    		while (iter.hasNext()) {
	    			int import_it = 0;
	    			int import_itt = 0;
	    			
	    			counter++; 
	    			
	    			Variable	var			= iter.next();
	    			statistics.addSelection(var); 
	    			String originalVarName = var.getName(); 
	    			String originalVarLabel = var.getLabel();
	    			
	    			Variable	db_var		= new Variable();
    					db_var.setName(var.getName());
    					db_var.setLabel(DBField.VAR_LAB.truncate(var.getLabel()));
    					boolean loadStatus = db_var.entityLoadByDesignator(_connection);
    						
    				JOptionPane.showMessageDialog(_view.getAppFrame(),
    					"<html>"+ bundle.getString(IMPORT_OF_VARIABLE) +" <b>"+ var.getName() +"</b>; <b>"+ var.getLabel() +"</b> (#"+ counter +"/"+ selection.size() +")</html>",
    					bundle.getString(INFORMATION), //"Information",					      
                        JOptionPane.INFORMATION_MESSAGE);	
    					
    				if (loadStatus) {
    					/* Variable may be already imported into the system */
    					statistics.addDuplicated(var); 
    					
    					/* Try to fetch Question: */
		    			Question	quest		= null;
		    			if (var != null)
		    				quest = var.getSource();
    					
						Question	db_quest	= db_var.getQuestion(_connection);
		    			
		    			/* Try to fetch Study: */
						Study		stu			= null;  			
		    			if (quest != null)
		    				stu = quest.getSource();
		    			
						Study		db_stu		= db_var.getStudy(_connection);
		    					    			
						String message = "<html>"+ bundle.getString(IMPORT_WARNING_3) +" <br> "+ bundle.getString(IMPORT_WARNING_4) +" <b>"+ var.getName() +"</b>; <b>"+ var.getLabel() +"</b>?</html>";		    			
						final String[] DIALOG_BUTTONS_TITLES	= {bundle.getString(YES_CONT), bundle.getString(NO_SKIP_VAR), bundle.getString(ABORT_IMPORTS)};
		    			
		    			import_it = JOptionPane.showOptionDialog(_view.getAppFrame(),
		    					message,
		    					bundle.getString(WARNING),
		    					JOptionPane.YES_NO_CANCEL_OPTION,
		    					JOptionPane.QUESTION_MESSAGE,
		    					null,
		    					DIALOG_BUTTONS_TITLES,
		    					DIALOG_BUTTONS_TITLES[2]);
    				}

    				else {
		    			import_it = JOptionPane.OK_OPTION;    					
    				}
    				
    				switch (import_it) {
    					case JOptionPane.OK_OPTION:
    						if (useDefault) {
    							Question	quest = var.getSource();
    							Study		study = quest.getSource();
    							
    							study.setTitle(defaultStudy.getTitle());
    							study.setDOI(defaultStudy.getDOI());
    							study.setStudyArea(defaultStudy.getStudyArea());
    							study.setDateOfCollection(defaultStudy.getDateOfCollection());
    							study.setPopulation(defaultStudy.getPopulation());
    							study.setSelection(defaultStudy.getSelection());
    							study.setCollectionMethod(defaultStudy.getCollectionMethod());
    							study.setCollectors(defaultStudy.getCollectors());
    							study.setSourceFile(defaultStudy.getSourceFile());
    							study.setDataset(defaultStudy.getDataset());
    						}
    						
    						setDefault = editVariable(var, retCharLinks);
    						
    						if (setDefault) {
       							Question	quest = var.getSource();
    							Study		study = quest.getSource();
    							
    							defaultStudy.setTitle(study.getTitle());
    							defaultStudy.setDOI(study.getDOI());
    							defaultStudy.setStudyArea(study.getStudyArea());
    							defaultStudy.setDateOfCollection(study.getDateOfCollection());
    							defaultStudy.setPopulation(study.getPopulation());
    							defaultStudy.setSelection(study.getSelection());
    							defaultStudy.setCollectionMethod(study.getCollectionMethod());
    							defaultStudy.setCollectors(study.getCollectors());
    							defaultStudy.setSourceFile(study.getSourceFile());
    							defaultStudy.setDataset(study.getDataset());
    							
    							useDefault = true;
    						}
    						
    						
    						/* Check if renaming/relabeling creates duplicate: */
    						if ((!var.getName().equals(originalVarName)) &&
    								(!var.getLabel().equals(originalVarLabel))) {
	    		    			db_var		= new Variable();
	        					db_var.setName(var.getName());
	        					db_var.setLabel(DBField.VAR_LAB.truncate(var.getLabel()));
	        					loadStatus = db_var.entityLoadByDesignator(_connection);
        					
	        					if (loadStatus) {
		    						String message = "<html>"+ bundle.getString(IMPORT_WARNING_3) +" <br> "+ bundle.getString(IMPORT_WARNING_4) +" <b>"+ var.getName() +"</b>; <b>"+ var.getLabel() +"</b>?</html>";
	    		    			
		    		    			JOptionPane.showMessageDialog(_view.getAppFrame(),
		    		    				message,
		    		    				bundle.getString(WARNING),
		    		    				JOptionPane.WARNING_MESSAGE);
	        					}
    						}
    						       						
    		    			
    						String message = "<html>"+ bundle.getString(IMPORT_VAR_REALLY) +" <b>"+ var.getName() +"</b>; <b>"+ var.getLabel() +"</b>?</html>";    		    			
    						final String[] DIALOG_BUTTONS_TITLES	= {bundle.getString(YES_IMPORT), bundle.getString(NO_SKIP_VAR), bundle.getString(ABORT_IMPORTS)};
    		    			
    		    			import_itt = JOptionPane.showOptionDialog(_view.getAppFrame(),
    		    					message,
    		    					bundle.getString(QUESTION),
    		    					JOptionPane.YES_NO_CANCEL_OPTION,
    		    					JOptionPane.QUESTION_MESSAGE,
    		    					null,
    		    					DIALOG_BUTTONS_TITLES,
    		    					DIALOG_BUTTONS_TITLES[2]);
    		    			
    	    				switch (import_itt) {
	        					case JOptionPane.OK_OPTION:
	        						statistics.addImported(var); 
	        						statistics.addOriginalName(originalVarName);
	        						statistics.addOriginalLabel(originalVarLabel);

	        						
	        						/* Save the Variable */
	        						boolean storeStatus = var.storeImport(_connection);
	        							    							    						
	        						if (storeStatus) {
	    	    						Iterator<CharacteristicLink> charLinksIter = retCharLinks.iterator();
	    	    						while (charLinksIter.hasNext()) {
	    	    							CharacteristicLink charLink = charLinksIter.next();
	    	    							
	    	    							if (charLink.getAttribute().equals(var)) {
	    	    								charLink.getCharacteristic().entityStore(_connection);
	    	    								charLink.entityStore(_connection);
	    	    							}
	    	    						}
	        							
	    								try {
	    									_connection.commit();
	    								} catch (SQLException e) {
	    									e.printStackTrace();
	    								}
	    								
	    								imported.add(var); 
	    								
		           						JOptionPane.showMessageDialog(_view.getAppFrame(),
		           							"<html>"+ bundle.getString(VARIABLE) + " <b>"+ var.getName() +"</b>; <b>"+ var.getLabel() +"</b> "+ bundle.getString(SUCCESSFULLY_IMPORTED) +"</html>",
		           							bundle.getString(INFORMATION), //"Information",					      
		           							JOptionPane.INFORMATION_MESSAGE);
	        						}
	           						break;
	        					case JOptionPane.NO_OPTION:
	        						var.setName(originalVarName);
	        						var.setLabel(originalVarLabel);

	        						statistics.addSkipped(var); 

	        						JOptionPane.showMessageDialog(_view.getAppFrame(),
	        								bundle.getString(VAR_IMPORT_SKIPPED), //"Import of Variable skipped",
	        								bundle.getString(INFORMATION), //"Information",					      
                                            JOptionPane.INFORMATION_MESSAGE);

	        						break;
	        					case JOptionPane.CANCEL_OPTION:
	        						var.setName(originalVarName);
	        						var.setLabel(originalVarLabel);
	        						
	        						statistics.addUnhandled(var); 
	        			    		while (iter.hasNext()) {
	        			    			var	= iter.next();
	        			    			statistics.addSelection(var);
	        			    			statistics.addUnhandled(var);
	        			    		}

	        			    		JOptionPane.showMessageDialog(_view.getAppFrame(),
	        								bundle.getString(VAR_IMPORTS_ABORTED), //"Import of Variables aborted",
	        								bundle.getString(INFORMATION), //"Information",					      
                                            JOptionPane.INFORMATION_MESSAGE);

	        			    		JOptionPane.showMessageDialog(_view.getAppFrame(),
	        							"<html>"+ bundle.getString(END_IMPORT) +" <b>"+ imported.size() +" </b>"+ bundle.getString(VARIABLE)+"("+ bundle.getString(VAR_S) +")</html>", 
	        							bundle.getString(INFORMATION), //"Information",					      
	        		                    JOptionPane.INFORMATION_MESSAGE);
	        						
	        						showImportReport(statistics); 
	        						
	        						return statistics; 
    	    				}
    						break;
    						
    					case JOptionPane.NO_OPTION:
    						statistics.addSkipped(var); 

    						JOptionPane.showMessageDialog(_view.getAppFrame(),
    								bundle.getString(VAR_IMPORT_SKIPPED), //"Import of Variable skipped",
    								bundle.getString(INFORMATION), //"Information",					      
                                    JOptionPane.INFORMATION_MESSAGE);
    						break;
    					case JOptionPane.CANCEL_OPTION:
    						statistics.addUnhandled(var); 
    			    		while (iter.hasNext()) {
    			    			var	= iter.next();
    			    			statistics.addSelection(var);
    			    			statistics.addUnhandled(var);
    			    		}
    						
    						JOptionPane.showMessageDialog(_view.getAppFrame(),
    								bundle.getString(VAR_IMPORTS_ABORTED), //"Import of Variables aborted",
    								bundle.getString(INFORMATION), //"Information",					      
                                    JOptionPane.INFORMATION_MESSAGE);

    						JOptionPane.showMessageDialog(_view.getAppFrame(),
    							"<html>"+ bundle.getString(END_IMPORT) +" <b>"+ imported.size() +" </b>"+ bundle.getString(VARIABLE)+"("+ bundle.getString(VAR_S) +")</html>", 
    							bundle.getString(INFORMATION), //"Information",					      
    		                    JOptionPane.INFORMATION_MESSAGE);

    						showImportReport(statistics); 
    						
    						return statistics; 
    				}
	    		}
	    			
				JOptionPane.showMessageDialog(_view.getAppFrame(),

					"<html>"+ bundle.getString(END_IMPORT) +" <b>"+ imported.size() +" </b>"+ bundle.getString(VARIABLE)+"("+ bundle.getString(VAR_S) +")</html>", 
					bundle.getString(INFORMATION), //"Information",					      
                    JOptionPane.INFORMATION_MESSAGE);
	    	}		    	
	    }else {

	    	return null; 
	    }
	    showImportReport(statistics); 
		
	    return statistics; 
	}
	
	private void showImportReport(ImportStatistics statistics) {
		if (statistics.getImportedDatafile() != null) {
			
			ImportReportFrame newFrame = new ImportReportFrame(new Dimension(750, 300), new Point(208, 8), true, true, true, false, true, _view.getLocale(), this, _connection, statistics);
			_view.getDesktop().add(newFrame);
			newFrame.toFront();
		}
		
	}
	
	private void showRemovalReport(RemovalStatistics statistics) {
		RemovalReportFrame newFrame = new RemovalReportFrame(new Dimension(750, 300), new Point(208, 8), true, true, true, false, true, _view.getLocale(), this, _connection, statistics);
		_view.getDesktop().add(newFrame);
		newFrame.toFront();
		
	}
	
	/**
	 * @return
	 */
	private ImportStatistics importMeasure() {
		ImportStatistics statistics = new ImportStatistics();
		statistics.setImportingAdmin(_user);
		
		List<Measurement>			retMeaList		= new ArrayList<Measurement>();			
		List<CharacteristicLink>	retCharLinks	= new ArrayList<CharacteristicLink>();
		
		/* Set up the validation error listener. */
		@SuppressWarnings("rawtypes")
		List		validationErrors	= new ArrayList();
		XmlOptions	validationOptions	= new XmlOptions();
		validationOptions.setErrorListener(validationErrors);
		
		JFileChooser.setDefaultLocale(_view.getLocale());
	    JFileChooser fc = new JFileChooser ();
	    		    
		ResourceBundle	resourceBundle	= ResourceBundle.getBundle(BUNDLE_F, _view.getLocale());
		String			file_name		= resourceBundle.getString(FILE_NAME);
		
		String path_0 = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		path_0 = path_0.replace("/", "\\\\"); // use File.separator in CharmStatsPro
		path_0 = path_0.replace(file_name, ""); // filename stored in resource FrameBundle
		path_0 = path_0 + "imports\\\\";
		fc.setCurrentDirectory (new File (path_0));
	    
	    FileFilter spssFilter	= new OpenDialogSPSSFilter();
	    /* disabled for now:  
	    FileFilter ddiFilter	= new OpenDialogDDIFilter();
	    FileFilter stataFilter	= new OpenDialogSTATAFilter();
	    FileFilter sasFilter	= new OpenDialogSASFilter();
	    FileFilter mplusFilter	= new OpenDialogMplusFilter();
	    */
	    
	    fc.addChoosableFileFilter(spssFilter);
	    /* disabled for now: 
	    fc.addChoosableFileFilter(stataFilter);
	    fc.addChoosableFileFilter(ddiFilter);
	    fc.addChoosableFileFilter(sasFilter);
	    fc.addChoosableFileFilter(mplusFilter);
	    */
   
	    fc.setFileFilter(spssFilter);
	    
		ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, _view.getLocale());	
	    fc.setDialogTitle(bundle.getString(IMPORT_MEASURE));
	    
	    int returnVal = fc.showOpenDialog(_view.getAppFrame());
	    if (returnVal == JFileChooser.APPROVE_OPTION) {
	    	FileFilter	ff		= fc.getFileFilter();
	    	File 		file	= fc.getSelectedFile();
	    	
	    	statistics.setImportedDatafile(file.getName()); 
	    	_view.showWaitingCursor();
	    	
	    	switch (((OpenDialogFileFilter)ff).getFilterID()) {
	    		case OpenDialogFileFilter.ddiFilterID:
	    			DDIInstanceDocument ddiiDoc = null;
	    		    try {
	    		    	ddiiDoc = DDIInstanceDocument.Factory.parse(file);
	    		    } catch (XmlException e) {
//	    		        e.printStackTrace();
	    		    	JOptionPane.showMessageDialog(_view.getAppFrame(), bundle.getString(READ_ERROR)); //"Read Error");
	    		    	
						_view.showDefaultCursor();
						return null;
	    		    } catch (IOException e) {
//	    		        e.printStackTrace(); 
	    		    	JOptionPane.showMessageDialog(_view.getAppFrame(), bundle.getString(READ_ERROR)); //"Read Error");
	    		    	
						_view.showDefaultCursor();
						return null;						
	    		    }
	    		    	    
	    		    
//	    		    /* DEVELOPMENT ONLY - TO BE REMOVED: */
//	    			if (ddiiDoc != null) {
//	    				boolean isValid = ddiiDoc.validate(validationOptions);
//	    						    				
//	    				System.out.println("This Document is "+ (isValid ? "" : "not ") +"valid!");		    				
//	    				if (!isValid)
//	    				{
//	    				    @SuppressWarnings("rawtypes")
//							Iterator iterErrors = validationErrors.iterator();
//	    				    while (iterErrors.hasNext())
//	    				    {
//	    				        System.out.println(">> " + iterErrors.next() + "\n");
//	    				    }
//	    				}
//	    			}
//	    			/* DEVELOPMENT ONLY */
	    				   
	    		    
    				String queryVarExpression =
	    				"declare namespace l='ddi:logicalproduct:3_1';" +
	    				"$this//l:Variable";
    				
    				if (ddiiDoc.selectPath(queryVarExpression).length > 0) {
	    				VariableType[] imp_variables = (VariableType[])ddiiDoc.selectPath(queryVarExpression);
	    				
	    				for (int i=0; i<imp_variables.length; i++) {
	    					VariableType	ddiVar	= imp_variables[i];
	    					Measurement		csMea	= new Measurement();
	    					
	    					/* Fill Measurement csMea with content from ddiVar */
	    					
	    					/* Set ID */
	    					String varID = ddiVar.getId();
	    					csMea.setUUID(varID);
	    					
	    					/* Set Measurement Name */
	    					List<NameType>		varNameList		= ddiVar.getVariableNameList();
	    					String				preferredName	= "";
	    					
	    					if (!varNameList.isEmpty())
	    						preferredName = varNameList.get(0).getStringValue();
	    					
	    					Iterator<NameType>	varNameListIter	= varNameList.iterator();
	    					while (varNameListIter.hasNext()) {
	    						NameType	varName			= varNameListIter.next();			    						
	    									
	    						if (varName.getIsPreferred())
	    							preferredName	= varName.getStringValue();
	    					}
	    					csMea.setName("p"+preferredName); 
	    					
	    					
	    					/* Set Variable Label */
	    					List<LabelType>		varLabelList	= ddiVar.getLabelList();
	    					String 				preferredLabel	= "";
	    					
	    					if (!varLabelList.isEmpty()) {
	    						LabelType	varLabel		= varLabelList.get(0);

	    						XmlCursor	labelCursor		= varLabel.newCursor();
	    									preferredLabel	= labelCursor.getTextValue();
	    					}
	    					csMea.setLabel(preferredLabel);
	    					
	    					
	    					/* Representation */
	    					RepresentationType		varRepresentation	= ddiVar.getRepresentation();
	    					CodeRepresentationType	codeRepresentation	= (CodeRepresentationType)varRepresentation.getValueRepresentation();
	    					ReferenceType			codeSchemeRef		= codeRepresentation.getCodeSchemeReference();
	    					String					codeSchemeID		= codeSchemeRef.getIDArray(0).getStringValue();
	    					
	    					
	    					String queryCodeSchemeExpression =
				    			"declare namespace l='ddi:logicalproduct:3_1';" +
				    			"$this//l:CodeScheme[@id='"+ codeSchemeID +"']";
	    					
	    					if (ddiiDoc.selectPath(queryCodeSchemeExpression).length > 0) {
	    						CodeSchemeType[] imp_codeSchemes = (CodeSchemeType[])ddiiDoc.selectPath(queryCodeSchemeExpression);
	    						
	    						List<CodeType> code_list = imp_codeSchemes[0].getCodeList();
	    						for (int j=0; j<code_list.size(); j++) {
	    							String			code_value	= code_list.get(j).getValue();
	    							String			cat_label	= "";
	    							ReferenceType	cat_ref		= code_list.get(j).getCategoryReference();
	    							String			catID		= cat_ref.getIDArray(0).getStringValue();
	    							
			    					String queryCategoryExpression =
							    		"declare namespace l='ddi:logicalproduct:3_1';" +
							    		"$this//l:Category[@id='"+ catID +"']";
			    					
			    					if (ddiiDoc.selectPath(queryCategoryExpression).length > 0) {
			    						CategoryType[]	imp_categories	= (CategoryType[])ddiiDoc.selectPath(queryCategoryExpression);
			    						
			    						LabelType		imp_label		= ((CategoryType)imp_categories[0]).getLabelArray(0);
			    						XmlCursor		labelCursor		= imp_label.newCursor();
			    										cat_label		= labelCursor.getTextValue();
			    					}
			    					
			    					Category cs_cat = new Category();
			    					cs_cat.setLabel(cat_label);
			    					cs_cat.setCode(code_value);
			    					
			    					CharacteristicLink cat_link = new CharacteristicLink();
			    					cat_link.setEntityID(generateNegID());
			    					cat_link.setAttribute(csMea);
			    					cat_link.setCharacteristic(cs_cat);
			    					cat_link.setType(CharacteristicLinkType.CATEGORY);
			    					
			    					retCharLinks.add(cat_link);
	    						}
	    					}
	    					
	    					retMeaList.add(csMea);		    							    					
	    				}		    						    				
    				}	    				
	    			
	    			break;
	    		case OpenDialogFileFilter.spssFilterID:
					try {
						SPSSFile spss = new SPSSFile(file);
						spss.loadMetadata();
						spss.loadData();

						Document spssDoc = spss.getDDI3LogicalProduct();
						
						List<Node> codeSchNodeList = getNodeList(spssDoc,"CodeScheme","",""); 
						
						List<Node> questionNodeList = getNodeList(spssDoc,"QuestionItem","","");
						
						List<Node> varNodeList = getNodeList(spssDoc,"VariableScheme","","");
						
						Iterator<Node> nodeIt = varNodeList.iterator();
						while (nodeIt.hasNext()) {
							Node varNode = nodeIt.next();
							
							NodeList childs = varNode.getChildNodes();
							
							for (int i=0; i<childs.getLength(); i++) {
								retMeaList.add(printMea(spssDoc, childs.item(i), retCharLinks));								
							}
						}
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (SPSSFileException es) {
						JOptionPane.showMessageDialog(_view.getAppFrame(), bundle.getString(READ_ERROR_NOT_VALID_SPSS));
						
						_view.showDefaultCursor();
						return null;						
					}
	    			
	    			break;
	    		case OpenDialogFileFilter.stataFilterID:
	    			 File stataFile = file;
	    			 
	    			 StataMetadataImport smi = new StataMetadataImport();
	    			 List<Variable> vars = smi.getVariables(stataFile);
	    			 
	    			 Iterator<Variable> vars_it = vars.iterator();
	    			 while (vars_it.hasNext()) {
	    				 Variable var = vars_it.next();
	    				 
	    				 Measurement mea = new Measurement();
	    				 
	    				 mea.setName(var.getName());
	    				 mea.setLabel(var.getLabel());
	    				 
	    				 /* Set ID */
	    				 mea.setUUID((String.valueOf(UUID.randomUUID())));
	    				 
	    				 /* Representation */
	    				 List<Value> vals = smi.getValues(stataFile, var);
	    				 
	    				 Iterator<Value> vals_it = vals.iterator();
	    				 while (vals_it.hasNext()) {
	    					 Value val = vals_it.next();
	    					 	
	    					 Category cat = new Category();
	    					 cat.setLabel(val.getLabel());
	    					 cat.setCode(val.getValue());
	    					 
	    					 CharacteristicLink cat_link = new CharacteristicLink();
	    					 cat_link.setEntityID(generateNegID());
	    					 cat_link.setAttribute(mea);
	    					 cat_link.setCharacteristic(cat);
	    					 cat_link.setType(CharacteristicLinkType.CATEGORY);
	    					 
	    					 retCharLinks.add(cat_link);	    					 
	    				 }
	    				 
	    				 retMeaList.add(mea);
	    			 }

	    			break;
	    		case OpenDialogFileFilter.sasFilterID:
	    			/* Pro only */
	    			break;
	    		case OpenDialogFileFilter.mplusFilterID:
	    			/* Pro only */
	    			break;
	    		default:
	    			/* DoNothing */
	    			break;
	    	}	
	    	
	    	_view.showDefaultCursor();
	    	
	    	/* Call DialogImportVariable, Parameter: resultList */	    	
	    	ArrayList<Measurement> selection = 
	    			new ImportMeasurementSelectionDialog(_view.getAppFrame(), true, retMeaList, _model, _view.getLocale(), _view.getFont()).getSelections();
	    	ArrayList<Measurement> imported = new ArrayList<Measurement>(); 
	    	
	    	/* Handle modified resultList */
	    	
	    	/* Check if Measurement is already imported into the database */
	    	if (!selection.isEmpty()) {
				JOptionPane.showMessageDialog(_view.getAppFrame(),
					"<html>"+ bundle.getString(START_IMPORT_OF) +" <b>"+ selection.size() +"</b> "+ bundle.getString(MEASUREMENT)+"("+bundle.getString(MEA_S)+")</html>", 
					bundle.getString(INFORMATION), //"Information",					      
                    JOptionPane.INFORMATION_MESSAGE);
				
				int counter = 0;
				
	    		Iterator<Measurement> iter = selection.iterator();
	    		while (iter.hasNext()) {
	    			int import_it = 0;
	    			int import_itt = 0;
	    			
	    			counter++; 
	    			
	    			Measurement	mea			= iter.next();
	    			statistics.addSelection(mea); 

	    			String originalMeaName	= mea.getName(); 
	    			String originalMeaLabel	= mea.getLabel();
	    			
	    			Measurement	db_mea		= new Measurement();
    					db_mea.setName(mea.getName());
    					boolean loadStatus = db_mea.entityLoadByDesignator(_connection);
    					
    				JOptionPane.showMessageDialog(_view.getAppFrame(),
    					"<html>"+ bundle.getString(IMPORT_OF_MEASUREMENT) +" <b>"+ mea.getName() +"</b>; <b>"+ mea.getLabel() +"</b> (#"+ counter +"/"+ selection.size() +")</html>",
    					bundle.getString(INFORMATION), //"Information",					      
                        JOptionPane.INFORMATION_MESSAGE);
    				
    				if (loadStatus) {
    					/* Measurement may be already imported into the system */
    					statistics.addDuplicated(mea); 
    					

    					String message = "<html>"+ bundle.getString(IMPORT_WARNING_33) +" <br> "+ bundle.getString(IMPORT_WARNING_44) +" <b>"+ mea.getName() +"</b>; <b>"+ mea.getLabel() +"</b>?</html>";

    					final String[] DIALOG_BUTTONS_TITLES	= {bundle.getString(YES_CONT), bundle.getString(NO_SKIP_MEA), bundle.getString(ABORT_IMPORTS)};
    					
		    			import_it = JOptionPane.showOptionDialog(_view.getAppFrame(),
		    					message,
		    					bundle.getString(WARNING),
		    					JOptionPane.YES_NO_CANCEL_OPTION,
		    					JOptionPane.QUESTION_MESSAGE,
		    					null,
		    					DIALOG_BUTTONS_TITLES,
		    					DIALOG_BUTTONS_TITLES[2]);
    				}
    				else {
		    			import_it = JOptionPane.OK_OPTION;    					
    				}
    				
    				switch (import_it) {
    					case JOptionPane.OK_OPTION:
    						/* Set source to "Imported": */
    						mea.setSource(MeasurementSource.IMPORTED);
    						
    						editMeasurement(mea, retCharLinks);
    						    						
    						/* Check if renaming/relabeling creates duplicate */
    						if ((!mea.getName().equals(originalMeaName)) &&
    								(!mea.getLabel().equals(originalMeaLabel))) {
	    		    			db_mea		= new Measurement();
	        					db_mea.setName(mea.getName());
	        					db_mea.setLabel(DBField.MEA_LAB.truncate(mea.getLabel()));
	        					loadStatus = db_mea.entityLoadByDesignator(_connection);
        					
	        					if (loadStatus) {
		    						String message = "<html>"+ bundle.getString(IMPORT_WARNING_33) +" <br> "+ bundle.getString(IMPORT_WARNING_44) +" <b>"+ mea.getName() +"</b>; <b>"+ mea.getLabel() +"</b>?</html>";
	    		    			
		    		    			JOptionPane.showMessageDialog(_view.getAppFrame(),
		    		    				message,
		    		    				bundle.getString(WARNING),
		    		    				JOptionPane.WARNING_MESSAGE);
	        					}
    						}

    						String message = "<html>"+ bundle.getString(IMPORT_MEA_REALLY) +" <b>"+ mea.getName() +"</b>; <b>"+ mea.getLabel() +"</b>?</html>";

    						final String[] DIALOG_BUTTONS_TITLES	= {bundle.getString(YES_IMPORT), bundle.getString(NO_SKIP_MEA), bundle.getString(ABORT_IMPORTS)};
    						
			    			import_itt = JOptionPane.showOptionDialog(_view.getAppFrame(),
			    					message,
			    					bundle.getString(QUESTION),
			    					JOptionPane.YES_NO_CANCEL_OPTION,
			    					JOptionPane.QUESTION_MESSAGE,
			    					null,
			    					DIALOG_BUTTONS_TITLES,
			    					DIALOG_BUTTONS_TITLES[2]);
			    			
	    	    				switch (import_itt) {
		        					case JOptionPane.OK_OPTION:
		        						statistics.addImported(mea); 

		        						statistics.addOriginalName(originalMeaName);
		        						statistics.addOriginalLabel(originalMeaLabel);
		        						
		        						/* Save the Measurement */
		        						boolean storeStatus = mea.storeImport(_connection);
		        							    							    						
		        						if (storeStatus) {
		    	    						Iterator<CharacteristicLink> charLinksIter = retCharLinks.iterator();
		    	    						while (charLinksIter.hasNext()) {
		    	    							CharacteristicLink charLink = charLinksIter.next();
		    	    							
		    	    							if (charLink.getAttribute().equals(mea)) {
		    	    								charLink.getCharacteristic().entityStore(_connection);
		    	    								charLink.entityStore(_connection);
		    	    							}
		    	    						}
		        							
		    								try {
		    									_connection.commit();
		    								} catch (SQLException e) {
		    									e.printStackTrace();
		    								}
		    								
		    								imported.add(mea); 
		    								
			           						JOptionPane.showMessageDialog(_view.getAppFrame(),
			           							"<html>"+ bundle.getString(MEASUREMENT) + " <b>"+ mea.getName() +"</b>; <b>"+ mea.getLabel() +"</b> "+ bundle.getString(SUCCESSFULLY_IMPORTED) +"</html>",
			           							bundle.getString(INFORMATION), //"Information",					      
			           							JOptionPane.INFORMATION_MESSAGE);
		        						}
	           						break;
	        					case JOptionPane.NO_OPTION:
	        						mea.setName(originalMeaName);
	        						mea.setLabel(originalMeaLabel);

	        						statistics.addSkipped(mea); 

	        						JOptionPane.showMessageDialog(_view.getAppFrame(),
	        								bundle.getString(MEA_IMPORT_SKIPPED), //"Import of Variable skipped",
	        								bundle.getString(INFORMATION), //"Information",					      
                                            JOptionPane.INFORMATION_MESSAGE);

	        						break;
	        					case JOptionPane.CANCEL_OPTION:	        						
	        						mea.setName(originalMeaName);
	        						mea.setLabel(originalMeaLabel);
	        						
	        						statistics.addUnhandled(mea); 
	        			    		while (iter.hasNext()) {
	        			    			mea	= iter.next();
	        			    			statistics.addSelection(mea);
	        			    			statistics.addUnhandled(mea);
	        			    		}
	        			    			        						
	        						JOptionPane.showMessageDialog(_view.getAppFrame(),
	        								bundle.getString(MEA_IMPORTS_ABORTED), //"Import of Variables aborted",
	        								bundle.getString(INFORMATION), //"Information",					      
                                            JOptionPane.INFORMATION_MESSAGE);
	
	        						JOptionPane.showMessageDialog(_view.getAppFrame(),
	        							"<html>"+ bundle.getString(END_IMPORT) +" <b>"+ imported.size() +" </b>"+ bundle.getString(MEASUREMENT)+"("+ bundle.getString(MEA_S) +")</html>", 
	        							bundle.getString(INFORMATION), //"Information",					      
	        		                    JOptionPane.INFORMATION_MESSAGE);
	        						
	        						showImportReport(statistics); 
	        						
	        						return statistics; 
		    				}
    						break;
    					case JOptionPane.NO_OPTION:
    						statistics.addSkipped(mea); 
    						
    						JOptionPane.showMessageDialog(_view.getAppFrame(),
    								bundle.getString(MEA_IMPORT_SKIPPED), //"Import of Variable skipped",
    								bundle.getString(INFORMATION), //"Information",					      
                                    JOptionPane.INFORMATION_MESSAGE);
    						
    						break;
    					case JOptionPane.CANCEL_OPTION:       						
    						statistics.addUnhandled(mea); 
    			    		while (iter.hasNext()) {
    			    			mea	= iter.next();
    			    			statistics.addSelection(mea);
    			    			statistics.addUnhandled(mea);
    			    		}

    						JOptionPane.showMessageDialog(_view.getAppFrame(),
    								bundle.getString(MEA_IMPORTS_ABORTED), //"Import of Variables aborted",
    								bundle.getString(INFORMATION), //"Information",					      
                                    JOptionPane.INFORMATION_MESSAGE);

    						JOptionPane.showMessageDialog(_view.getAppFrame(),
        						"<html>"+ bundle.getString(END_IMPORT) +" <b>"+ imported.size() +" </b>"+ bundle.getString(MEASUREMENT)+"("+ bundle.getString(MEA_S) +")</html>", 
    							bundle.getString(INFORMATION), //"Information",					      
    		                    JOptionPane.INFORMATION_MESSAGE);

    						showImportReport(statistics);  
    						
    						return statistics; 
    				}
	    		}
				
				JOptionPane.showMessageDialog(_view.getAppFrame(),

					"<html>"+ bundle.getString(END_IMPORT) +" <b>"+ imported.size() +" </b>"+ bundle.getString(MEASUREMENT)+"("+ bundle.getString(MEA_S) +")</html>", 
					bundle.getString(INFORMATION), //"Information",					      
                    JOptionPane.INFORMATION_MESSAGE);
				
	    	}		    	
	    } else{

	    	return null; 
	    }
	    showImportReport(statistics); 
		
	    return statistics; 
	}
		
	/* Methods called from importVariable and from editVariable */
	/**
	 * @param var
	 * @param links
	 * @return
	 */
	private boolean editVariable(Variable var, List<CharacteristicLink> links) {
		boolean isDefault = false;
		
        /* Create and set up the content pane. */
        editVariable	= new VariableEditorPanel(var, links, _view.getLocale(), _view.getFont(), this);        
        isDefault = editVariable.goEdit();
        
        
   		return isDefault;
	}
	
	/* Methods called from importMeasurement and from editMeasurement */
	/**
	 * @param mea
	 * @param links
	 */
	private void editMeasurement(Measurement mea,  List<CharacteristicLink> links) {
        /* Create and set up the content pane. */
        editMeasure	= new EditMeasureDialog(mea, links, _view.getLocale(), _view.getFont(), this);	
        
		Dimension frameSize		= _view.getAppFrame().getSize();
		Dimension dialogSize	= editMeasure.getSize();
		
		editMeasure.setLocation(
	    		_view.getAppFrame().getLocation().x + ((frameSize.width-dialogSize.width)/2), _view.getAppFrame().getLocation().y + ((frameSize.height-dialogSize.height)/2) );

        
        editMeasure.goEdit();
        
	}
	
	/* Method called through CMD_USER_EDIT_USER */
	/**
	 * @param usr
	 */
	private void editUser(User usr) {
		_view.showWaitingCursor();
		ArrayList<Person>	personList	= Person.getAllPersonsWithRole(_connection, PersonRole.CERTIFIED_USER);
		_view.showDefaultCursor();
		
		editUser = new UserEditorPanel(usr, personList, _connection, _view.getLocale(), _view.getFont(), this);		
		editUser.goEdit();		
	}
	
	/* Method called through CMD_USER_EDIT_PERSON */
	/**
	 * @param prsn
	 */
	private void editPerson(Person prsn) {
        /* Create and set up the content pane. */
        editPerson	= new PersonEditorPanel(prsn, _connection, _view.getLocale(), _view.getFont(), this);		
        editPerson.goEdit();        			
	}
	
	/* Method called through CMD_PRJ_EDIT_PARTICIPANT */
	/**
	 * @param prt
	 */
	private void editParticipant(Participant prt) {
        /* Create and set up the content pane. */
        editParticipant	= new ParticipantEditorPanel(prt, _model.getProject(), _connection, _view.getLocale(), _view.getFont(), this);		
        editParticipant.goEdit();      			
	}

	/**
	 * @return
	 */
	private boolean exportVariable() {
		_view.showWaitingCursor(); 
		DDIInstanceDocument		ddiDocument				= DDIInstanceDocument.Factory.newInstance();
				
		DDIInstanceType			ddiInstance				= ddiDocument.addNewDDIInstance();
		ddiInstance.setId(_model.getProject().getUUID());
//		ddiInstance.setVersionResponsibility(_model.getProject().isPublished() ? _model.getProject().getProjectOwner().getPerson().getName() : _user.getPerson().getName());
		ddiInstance.setVersion(_model.getProject().isFinished() ? "1.0.0" : "0.0.0");
		ddiInstance.setVersionDate(_model.getProject().isFinished() ? _model.getProject().getFinishedSince() : new Date());
		ddiInstance.setAgency(_model.getAgency());	
//		ddiInstance.setIsPublished(_model.getProject().isPublished());
//		ddiInstance.setLang(_view.getLocale().toString());
//		ddiInstance.setIsMaintainable(true);
		
		NoteType 				ddiInstanceNote			= ddiInstance.addNewNote();
		ddiInstanceNote.setId("TODO");
		ddiInstanceNote.setType(NoteTypeCodeType.PROCESSING);
		RelationshipType		ddiInstanceNoteRel		= ddiInstanceNote.addNewRelationship();
		ReferenceType			ddiInstanceNoteRelRef	= ddiInstanceNoteRel.addNewRelatedToReference();
		ddiInstanceNoteRelRef.addNewURN();
		
		
		StructuredStringType	ddiInstanceNoteContent	= ddiInstanceNote.addNewContent();
        XmlObject 				xmlObject				= XmlObject.Factory.newInstance();
        xmlObject.newCursor().setTextValue("This Document was produced using CharmStats");
        ddiInstanceNoteContent.set(xmlObject);
								
		StudyUnitType			studyUnit				= ddiInstance.addNewStudyUnit();
		studyUnit.setId("TODO");
	
		studyUnit.setVersionResponsibility(_model.getProject().isFinished() ? _model.getProject().getProjectOwner().getPerson().getName() : _user.getPerson().getName());
		studyUnit.setVersion(_model.getProject().isFinished() ? "1.0.0" : "0.0.0");
		studyUnit.setVersionDate(_model.getProject().isFinished() ? _model.getProject().getFinishedSince() : new Date());
			
		studyUnit.setAgency(_model.getAgency());
		studyUnit.setIsPublished(_model.getProject().isFinished());
		studyUnit.setLang(_view.getLocale().getLanguage());
		studyUnit.setIsMaintainable(true);
		
		CitationType			studyUnitCitation		= studyUnit.addNewCitation();
		InternationalStringType	StudyUnitCitationTitle	= studyUnitCitation.addNewTitle();
		
		IdentifiedStructuredStringType	studyUnitAbstract = studyUnit.addNewAbstract();
		studyUnitAbstract.setId("TODO");
		studyUnitAbstract.addNewContent();
		
		ReferenceType 			studyUnitUniverseRef	= studyUnit.addNewUniverseReference();
		studyUnitUniverseRef.addNewURN();
	
					
		IdentifiedStructuredStringType studyPurpose		= studyUnit.addNewPurpose();
		studyPurpose.setId("TODO");
		studyPurpose.addNewContent();
		
		NoteType 				studyUnitNote			= studyUnit.addNewNote();
		studyUnitNote.setId("TODO");
		studyUnitNote.setType(NoteTypeCodeType.PROCESSING);
		RelationshipType		studyUnitNoteRel		= studyUnitNote.addNewRelationship();
		ReferenceType			studyUnitNoteRelRef		= studyUnitNoteRel.addNewRelatedToReference();
		studyUnitNoteRelRef.addNewURN();
		
		StructuredStringType	studyUnitNoteContent	= studyUnitNote.addNewContent();
        						xmlObject				= XmlObject.Factory.newInstance();
        xmlObject.newCursor().setTextValue("This Document is based on Harmonization Project " + _model.getProject().getName());
        studyUnitNoteContent.set(xmlObject);
        
					
		/* Questions (connected to Source Variable(s)) */
		DataCollectionType 		dataCollection			= studyUnit.addNewDataCollection();
		dataCollection.setId("TODO");
		/* TODO add question(s) */
		
		/* Variables */
		BaseLogicalProductType	baseLogicalProduct	=  studyUnit.addNewBaseLogicalProduct();
		
		LogicalProductType		logicalProduct		= (LogicalProductType)baseLogicalProduct.substitute(new QName("ddi:logicalproduct:3_1","LogicalProduct"),LogicalProductType.type);
		logicalProduct.setId("TODO");
		
		QuestionSchemeType		target_questSch		= dataCollection.addNewQuestionScheme();			
		target_questSch.setId(_model.getProject().getDDISupExpQueSchemeUUId());
		StructuredStringType	target_questSchDesc	= target_questSch.addNewDescription();
								xmlObject			= XmlObject.Factory.newInstance();
		xmlObject.newCursor().setTextValue("Scheme contains Question for Target Variable");
		target_questSchDesc.set(xmlObject);
		
		QuestionItemType 		target_questSchItem	= target_questSch.addNewQuestionItem();
		target_questSchItem.setId(_model.getProject().getDDISupExpQueUUId());
		org.ddialliance.ddi3.xml.xmlbeans.reusable.RepresentationType targetQuestItemRepr = target_questSchItem.addNewResponseDomain();
		CodeDomainType	
					target_queReprCode	= (CodeDomainType)targetQuestItemRepr.substitute(new QName("ddi:datacollection:3_1","CodeDomain"),CodeDomainType.type);
		ReferenceType			target_queReprRef	= target_queReprCode.addNewCodeSchemeReference();
		target_queReprRef.addNewURN();
		
		VariableSchemeType 		target_varSch			= logicalProduct.addNewVariableScheme();
		target_varSch.setId(_model.getProject().getDDISupExpVarSchemeUUId());
		StructuredStringType	target_varSchDesc		= target_varSch.addNewDescription();
								xmlObject				= XmlObject.Factory.newInstance();
		xmlObject.newCursor().setTextValue("Scheme contains Target Variable");
		target_varSchDesc.set(xmlObject);
		
		Measurement				var_target				= _model.getProject().getContent().getMeasurement(); 
		
		VariableType 			target_var				= target_varSch.addNewVariable();
		target_var.setId(_model.getProject().getDDISupExpVarUUId());
		
		NameType 				target_varName 			= target_var.addNewVariableName();
		target_varName.setStringValue(_model.getProject().getTargetName());
		
		LabelType 				target_varLabel			= target_var.addNewLabel();
        						xmlObject				= XmlObject.Factory.newInstance();
        xmlObject.newCursor().setTextValue(_model.getProject().getTargetLabel());
        target_varLabel.set(xmlObject);
        target_varLabel.setLang(_view.getLocale().getLanguage());
		
        ReferenceType target_varQueRef					= target_var.addNewQuestionReference();
		IDType[] 				target_queIDs		= new IDType[1];
		IDType 					target_queID			= IDType.Factory.newInstance();
		target_queID.setStringValue(_model.getProject().getDDISupExpQueUUId());
								target_queIDs[0]		= target_queID;
								
		target_varQueRef.setIDArray(target_queIDs);
        
        
		/* Fill target_var with content from _model */
		RepresentationType		target_varRepr			= target_var.addNewRepresentation();
		org.ddialliance.ddi3.xml.xmlbeans.reusable.RepresentationType		
								target_varReprVal		= target_varRepr.addNewValueRepresentation();
		
		CodeRepresentationType	target_varReprCod		= (CodeRepresentationType)target_varReprVal.substitute(new QName("ddi:logicalproduct:3_1","CodeRepresentation"),CodeRepresentationType.type);
		
		ReferenceType			target_varReprRef		= target_varReprCod.addNewCodeSchemeReference();
		
		CodeSchemeType			target_codSch			= logicalProduct.addNewCodeScheme();
		target_codSch.setId(_model.getProject().getContent().getMeasurement().getDDISupCodSchemeUUId());
		target_codSch.setVersion(_model.getProject().isFinished() ? "1.0.0" : "0.0.0");
		target_codSch.setVersionDate(_model.getProject().isFinished() ? _model.getProject().getFinishedSince() : new Date());
		target_codSch.setAgency(_model.getAgency());
					
		StructuredStringType	target_codSchDesc		= target_codSch.addNewDescription();
								xmlObject				= XmlObject.Factory.newInstance();
		xmlObject.newCursor().setTextValue("Scheme contains Code Scheme for Target Variable");
		target_codSchDesc.set(xmlObject);
		
		CategorySchemeType		target_catSch			= logicalProduct.addNewCategoryScheme();
		target_catSch.setId(_model.getProject().getContent().getMeasurement().getDDISupCatSchemeUUId());
		target_catSch.setVersion(_model.getProject().isFinished() ? "1.0.0" : "0.0.0");
		target_catSch.setVersionDate(_model.getProject().isFinished() ? _model.getProject().getFinishedSince() : new Date());
		target_catSch.setAgency(_model.getAgency());
		target_catSch.setIsPublished(_model.getProject().isFinished());
		
		StructuredStringType	target_catSchDesc		= target_catSch.addNewDescription();
								xmlObject				= XmlObject.Factory.newInstance();
		xmlObject.newCursor().setTextValue("Scheme contains Category Scheme for Target Variable");
		target_catSchDesc.set(xmlObject);
		
		IDType[] 				target_codSchIDs		= new IDType[1];
		IDType 					target_codSchID			= IDType.Factory.newInstance();
		target_codSchID.setStringValue(target_codSch.getId());
								target_codSchIDs[0]		= target_codSchID;
		
		target_varReprRef.setIDArray(target_codSchIDs);
//		target_varReprVal.set(target_varReprCod);
		
		Iterator<CharacteristicLink>	cl_iter			= _model.getProject().getContent().getCharacteristicLinksByAttribute(var_target).iterator();
		
		while (cl_iter.hasNext()) {
			CharacteristicLink	link					= (CharacteristicLink)cl_iter.next();
							
			CodeType			target_cod				= target_codSch.addNewCode();
			target_cod.setValue(((Category)link.getCharacteristic()).getCode());
			ReferenceType		target_codCatRef		= target_cod.addNewCategoryReference();
			
			CategoryType		target_cat				= target_catSch.addNewCategory();
			target_cat.setId(((Category)link.getCharacteristic()).getUUID());
			target_cat.setVersion( _model.getProject().isFinished() ? "1.0.0" : "0.0.0");
			target_cat.setVersionDate( _model.getProject().isFinished() ? _model.getProject().getFinishedSince() : new Date());
			target_cat.setMissing(((Category)link.getCharacteristic()).isMissing());
			
			LabelType			target_catLabel			= target_cat.addNewLabel();
            					xmlObject				= XmlObject.Factory.newInstance();
            xmlObject.newCursor().setTextValue(((Category)link.getCharacteristic()).getLabel());
            target_catLabel.set(xmlObject);
            target_catLabel.setLang(_view.getLocale().getLanguage());
			
			IDType[] 			target_catIDs			= new IDType[1];
			IDType 				target_catID			= IDType.Factory.newInstance();
			target_catID.setStringValue(target_cat.getId()); 
			target_catIDs[0] = target_catID;
			
			target_codCatRef.setIDArray(target_catIDs);
		}

		QuestionSchemeType		source_questSch		= dataCollection.addNewQuestionScheme();
		source_questSch.setId(_model.getProject().getDDISupImpQueSchemeUUId());
		StructuredStringType	source_questSchDesc	= source_questSch.addNewDescription();
								xmlObject			= XmlObject.Factory.newInstance();
		xmlObject.newCursor().setTextValue("Scheme contains Question(s) for Source Variable(s)");
		source_questSchDesc.set(xmlObject);
		
		VariableSchemeType 		source_varSch			= logicalProduct.addNewVariableScheme();
		source_varSch.setId(_model.getProject().getDDISupImpVarSchemeUUId());
		StructuredStringType	source_varSchDesc		= source_varSch.addNewDescription();
								xmlObject				= XmlObject.Factory.newInstance();
		xmlObject.newCursor().setTextValue("Scheme contains List of Source Variable(s)");
		source_varSchDesc.set(xmlObject);
		
		ArrayList<AttributeMap> source_list				= _model.getProject().getContent().getMapsByType(AttributeMapType.ASSIGNED_VARIABLE);
		Iterator<AttributeMap> 	iterator	 			= source_list.iterator();

		while (iterator.hasNext()) {
			AttributeMap 		map						= (AttributeMap)iterator.next();
			Variable			var_source				= (Variable)((AttributeLink)map.getSourceAttribute()).getAttribute();
			
			VariableType		source_var				= source_varSch.addNewVariable();
			source_var.setId(var_source.getUUID());
			
			NameType 			source_varName			= source_var.addNewVariableName();
			source_varName.setStringValue(var_source.getName());
			
			LabelType 			source_varLabel			= source_var.addNewLabel();
								xmlObject				= XmlObject.Factory.newInstance();
			xmlObject.newCursor().setTextValue(var_source.getLabel());
			source_varLabel.set(xmlObject);
			source_varLabel.setLang(_view.getLocale().getLanguage());
			
            ReferenceType source_varQueRef					= source_var.addNewQuestionReference();
			IDType[] 				source_queIDs			= new IDType[1];
			IDType 					source_queID			= IDType.Factory.newInstance();
			source_queID.setStringValue(
					var_source.getQuestion(_connection) != null ? var_source.getQuestion(_connection).getUUID() : "TODO");
									source_queIDs[0]		= source_queID;
									
			source_varQueRef.setIDArray(source_queIDs);
			
			/* Fill source_var with content from var_source */
			RepresentationType		source_varRepr		= source_var.addNewRepresentation();
			org.ddialliance.ddi3.xml.xmlbeans.reusable.RepresentationType		
									source_varReprVal	= source_varRepr.addNewValueRepresentation();
			
			CodeRepresentationType	source_varReprCode		= (CodeRepresentationType)source_varReprVal.substitute(new QName("ddi:logicalproduct:3_1","CodeRepresentation"),CodeRepresentationType.type);
			
			ReferenceType			source_varReprRef	= source_varReprCode.addNewCodeSchemeReference();
			
			CodeSchemeType			source_codSch		= logicalProduct.addNewCodeScheme();
			source_codSch.setId(var_source.getDDISupCodSchemeUUId());
			source_codSch.setVersion(_model.getProject().isFinished() ? "1.0.0" : "0.0.0");
			source_codSch.setVersionDate(_model.getProject().isFinished() ? _model.getProject().getFinishedSince() : new Date());
			source_codSch.setAgency(_model.getAgency());
			
			StructuredStringType	source_codSchDesc		= source_codSch.addNewDescription();
									xmlObject				= XmlObject.Factory.newInstance();
			xmlObject.newCursor().setTextValue("Scheme contains Code Scheme of a Source Variable");
			source_codSchDesc.set(xmlObject);
			
			CategorySchemeType		source_catSch		= logicalProduct.addNewCategoryScheme();
			source_catSch.setId(var_source.getDDISupCatSchemeUUId());
			source_catSch.setVersion(_model.getProject().isFinished() ? "1.0.0" : "0.0.0");
			source_catSch.setVersionDate(_model.getProject().isFinished() ? _model.getProject().getFinishedSince() : new Date());
			source_catSch.setAgency(_model.getAgency());
			source_catSch.setIsPublished(_model.getProject().isFinished());
			
			StructuredStringType	source_catSchDesc		= source_catSch.addNewDescription();
									xmlObject				= XmlObject.Factory.newInstance();
			xmlObject.newCursor().setTextValue("Scheme contains Category Scheme of a Source Variable");
			source_catSchDesc.set(xmlObject);
			
			IDType[] 				source_codSchIDs	= new IDType[1];
			IDType 					source_codSchID		= IDType.Factory.newInstance();
			source_codSchID.setStringValue(source_codSch.getId());
									source_codSchIDs[0] = source_codSchID;
			
			source_varReprRef.setIDArray(source_codSchIDs);
//			source_varReprVal.set(source_varReprCode);
							
			cl_iter	= _model.getProject().getContent().getCharacteristicLinksByAttribute(var_source).iterator();
			
			while (cl_iter.hasNext()) {
				CharacteristicLink	char_link			= (CharacteristicLink)cl_iter.next();
								
				CodeType			source_cod			= source_codSch.addNewCode();
				source_cod.setValue(((Value)char_link.getCharacteristic()).getValue());
				ReferenceType		source_codCatRef	= source_cod.addNewCategoryReference();
				
				CategoryType		source_cat			= source_catSch.addNewCategory();
				source_cat.setId(((Value)char_link.getCharacteristic()).getUUID());
				source_cat.setVersion( _model.getProject().isFinished() ? "1.0.0" : "0.0.0");
				source_cat.setVersionDate( _model.getProject().isFinished() ? _model.getProject().getFinishedSince() : new Date());								
				source_cat.setMissing(((Value)char_link.getCharacteristic()).isMissing());
				
				LabelType			source_catLabel		= source_cat.addNewLabel();
	            					xmlObject 			= XmlObject.Factory.newInstance();
	            xmlObject.newCursor().setTextValue(((Value)char_link.getCharacteristic()).getLabel());
	            source_catLabel.set(xmlObject);
	            source_catLabel.setLang(_view.getLocale().getLanguage());										
				
				IDType[] 			source_catIDs		= new IDType[1];
				IDType 				source_catID		= IDType.Factory.newInstance();
				source_catID.setStringValue(source_cat.getId()); 
									source_catIDs[0] 	= source_catID;
				
				source_codCatRef.setIDArray(source_catIDs);
			}
			
			/* HOW TO ACCESS QUESTION? */
			Question var_sourceQuest = var_source.getQuestion(_connection);
			
			QuestionItemType source_varQuest = source_questSch.addNewQuestionItem();
			source_varQuest.setId("TODO"); //var_sourceQuest.getUUID()
			source_varQuest.setVersion(_model.getProject().isFinished() ? "1.0.0" : "0.0.0");
			source_varQuest.setVersionDate(_model.getProject().isFinished() ? _model.getProject().getFinishedSince() : new Date());
			
			org.ddialliance.ddi3.xml.xmlbeans.reusable.RepresentationType 		
				source_varQuestRepr	= source_varQuest.addNewResponseDomain();
			CodeDomainType	source_varQueReprCode	= (CodeDomainType)source_varQuestRepr.substitute(new QName("ddi:datacollection:3_1","CodeDomain"),CodeDomainType.type);			
			ReferenceType			source_varQueReprRef	= source_varQueReprCode.addNewCodeSchemeReference();
			source_varQueReprRef.addNewURN();
			
			DynamicTextType source_varQuestText = source_varQuest.addNewQuestionText();
			source_varQuestText.setLang(_view.getLocale().getLanguage());
			TextType source_varQuestTextText = source_varQuestText.addNewText();
			
			XmlObject textText = source_varQuestTextText.substitute(new QName("ddi:datacollection:3_1","LiteralText"), LiteralTextType.type);
			StructuredStringType content = ((LiteralTextType)textText).addNewText();
			xmlObject 			= XmlObject.Factory.newInstance();
			xmlObject.newCursor().setTextValue("It's done!");
			content.set(xmlObject);					

		}
					
		/* Export into File: */
		XmlOptions				xmlOptionList	= new XmlOptions();
		HashMap<String, String>	nameSpaceMap	= new HashMap<String, String>();
		
		nameSpaceMap.put("ddi:studyunit:3_1",		"s");
		nameSpaceMap.put("ddi:logicalproduct:3_1",	"l");
		nameSpaceMap.put("ddi:reusable:3_1",		"r");
		nameSpaceMap.put("ddi:datacollection:3_1",	"d");
		xmlOptionList.setSaveAggressiveNamespaces();
		xmlOptionList.setSaveSuggestedPrefixes(nameSpaceMap);

		DDIFilter fDDIFilter = new DDIFilter ();
		
		File fFile = new File ("ddi_xprt.xml");
		
		JComponent.setDefaultLocale(_view.getLocale());
		JFileChooser fc = new JFileChooser();
		
		ResourceBundle	resourceBundle	= ResourceBundle.getBundle(BUNDLE_F, _view.getLocale());
		String			file_name		= resourceBundle.getString(FILE_NAME);
		
		String path_0 = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		path_0 = path_0.replace("/", "\\\\"); // use File.separator in CharmStatsPro
		path_0 = path_0.replace(file_name, ""); // filename stored in resource FrameBundle
		path_0 = path_0 + "exports\\\\";
		
		fc.setCurrentDirectory (new File (path_0));
		
	    /* Start in current directory */
//	    fc.setCurrentDirectory (new File ("."));

	    /* Set filter for Java source files. */
	    fc.setFileFilter (fDDIFilter); 

	    /* Set to a default name for save. */
	    fc.setSelectedFile (fFile);
	    
	    _view.showDefaultCursor(); 
	    /* Open chooser dialog */
	    int result = fc.showSaveDialog(_view.getAppFrame());
		
	    ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, _view.getLocale());
	    if (result == JFileChooser.CANCEL_OPTION) {
	    	return false;
	    } else if (result == JFileChooser.APPROVE_OPTION) {
	    	fFile = fc.getSelectedFile ();
	        if (fFile.exists ()) {
	        	int response = JOptionPane.showConfirmDialog (null,
	        			bundle.getString(OVERWRITE_FILE),bundle.getString(CONFIRM),
	        			JOptionPane.OK_CANCEL_OPTION,
	        			JOptionPane.QUESTION_MESSAGE);
	        	if (response == JOptionPane.CANCEL_OPTION) return false;
	        }
			try {
				_view.showWaitingCursor(); 
				ddiDocument.save(new FileOutputStream(fFile), xmlOptionList);
				 _view.showDefaultCursor(); 
			} catch (IOException e) {
				e.printStackTrace();
			}
	    } else {
	    	return false;
	    }
	    
	    return true;
	}
	
	/* Menu: Help */

		
	/* Tab Handling: */
	/* (non-Javadoc)
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	public void stateChanged(ChangeEvent e) {
		changeHandler(e);
	}
	
	/**
	 * @param e
	 */
	private void changeHandler(ChangeEvent e) {
		if (e.getSource() instanceof TabbedPane) {
			if (_view != null) {
				
				Tab tab = _view.getPreviousStep();
									
				if (tab instanceof TabProject) handleProjectTab(tab);
				if (tab instanceof TabConcept) handleConceptTab(tab);
				if (tab instanceof TabLiterature) handleLiteratureTab(tab);
				
				if (tab instanceof TabMeasurement) handleMeasurementTab(tab);
				
				if (tab instanceof TabDimension) handleDimensionTab(tab);
				if (tab instanceof TabSpecifications) handleSpecificationsTab(tab);
				if (tab instanceof TabMapDimensionInstance)	handleMapDimensionInstanceTab(tab);
				if (tab instanceof TabMapDimensionAttribute)	handleMapDimensionAttributeTab(tab);
				if (tab instanceof TabMapDimensionChar)	handleMapDimensionCharTab(tab);
				
				if (tab instanceof TabOSInstance) handleOSInstanceTab(tab);
				if (tab instanceof TabIndicator) handleIndicatorTab(tab);
				if (tab instanceof TabPrescriptions) handlePrescriptionsTab(tab);
				if (tab instanceof TabMapIndicatorInstance) handleMapIndicatorInstanceTab(tab);
				if (tab instanceof TabMapIndicatorAttribute) handleMapIndicatorAttributeTab(tab);
				if (tab instanceof TabMapIndicatorChar) { handleMapIndicatorCharTab(tab);
					if (isAutocomplete) {
						callExportSyntax();
						
						isAutocomplete = false;
					}
				}
				
				if (tab instanceof TabSearchVariable) handleSearchVariableTab(tab);
				if (tab instanceof TabCompareMetadata) handleCompareVariablesTab(tab);
				if (tab instanceof TabCompareValues) handleCompareValuesTab(tab);
				
				if (tab instanceof TabDRInstance) handleDRInstanceTab(tab);
				if (tab instanceof TabVariable) handleVariableTab(tab);
				if (tab instanceof TabValues) handleValuesTab(tab);
				if (tab instanceof TabMapVariableInstance) handleMapVariableInstanceTab(tab);
				if (tab instanceof TabMapVariableAttribute) handleMapVariableAttributeTab(tab);
				if (tab instanceof TabMapVariableChar) { 
					handleMapVariableCharTab(tab);
				}
				
				/* Tab to Tab Movement (Inner Tab Pane) */
				if (((JTabbedPane)e.getSource()).getSelectedComponent() instanceof Tab) {
					_view.setPreviousStep((Tab)((JTabbedPane)e.getSource()).getSelectedComponent());
					
					Tab nextTab = (Tab)((JTabbedPane)e.getSource()).getSelectedComponent();				
					nextTab.setDefaults(_model); /* Tab update */
					TabbedPane tabbedPane = 
						((TabbedPane)((FormFrame)_view.getDesktop().getFormFrame()).getTabbedPane().getComponentAt(nextTab.getPanelIdx()));
					tabbedPane.setSelectedTab(nextTab);
					
				}
		
				/* Code Deprecated */
				if (((JTabbedPane)e.getSource()).getSelectedComponent() instanceof JScrollPane) {
					_view.setPreviousStep((Tab)((JViewport)((JScrollPane)((JTabbedPane)e.getSource()).getSelectedComponent()).getComponent(0)).getComponent(0));
					
					Tab nextTab = (Tab)((JViewport)((JScrollPane)((JTabbedPane)e.getSource()).getSelectedComponent()).getComponent(0)).getComponent(0);
					TabbedPane tabbedPane = 
						((TabbedPane)((FormFrame)_view.getDesktop().getFormFrame()).getTabbedPane().getComponentAt(nextTab.getPanelIdx()));
					tabbedPane.setSelectedTab(nextTab);
				}
				
				/* Tab to Tab Movement (Outer Tab Pane) */
				if (((JTabbedPane)e.getSource()).getSelectedComponent() instanceof TabbedPane) {
					_view.setPreviousStep(((TabbedPane)((JTabbedPane)e.getSource()).getSelectedComponent()).getSelectedTab());
				}
								
				IdentifiedParameter parameter = new IdentifiedParameter(ActionCommandID.MOD_MOD);
				Object[] addenda = new Object[1];
				addenda[0] = _model;
				parameter.setParameters(addenda);
				
				setChanged();	
				notifyObservers(parameter);
			
			}
			
		}
	}
	
	/**
	 * @param tab
	 * @return
	 */
	private boolean validate(Tab tab) {
		boolean isValid = false;
			
		if (tab instanceof TabProject)					isValid = validateProjectTab(tab);
		if (tab instanceof TabConcept)					isValid = validateConceptTab(tab);
		if (tab instanceof TabLiterature)				isValid = validateLiteratureTab(tab);
		
		if (tab instanceof TabMeasurement)				isValid = validateMeasurementTab(tab);
		
		if (tab instanceof TabDimension) 				isValid = validateDimensionTab(tab);
		if (tab instanceof TabSpecifications)			isValid = validateSpecificationsTab(tab);
		if (tab instanceof TabMapDimensionInstance)		isValid = validateMapDimensionInstanceTab(tab);
		if (tab instanceof TabMapDimensionAttribute)	isValid = validateMapDimensionAttributeTab(tab);
		if (tab instanceof TabMapDimensionChar)			isValid = validateMapDimensionCharTab(tab);
		
		if (tab instanceof TabOSInstance)				isValid = validateOSInstanceTab(tab);
		if (tab instanceof TabIndicator)				isValid = validateIndicatorTab(tab);
		if (tab instanceof TabPrescriptions)			isValid = validatePrescriptionsTab(tab);
		if (tab instanceof TabMapIndicatorInstance) 	isValid = validateMapIndicatorInstanceTab(tab);
		if (tab instanceof TabMapIndicatorAttribute) 	isValid = validateMapIndicatorAttributeTab(tab);
		if (tab instanceof TabMapIndicatorChar) 		isValid = validateMapIndicatorCharTab(tab);
		
		if (tab instanceof TabSearchVariable) 			isValid = validateSearchVariableTab(tab);
		if (tab instanceof TabCompareMetadata) 			isValid = validateCompareVariablesTab(tab);
		if (tab instanceof TabCompareValues) 			isValid = validateCompareValuesTab(tab);
		
		if (tab instanceof TabDRInstance) 				isValid = validateDRInstanceTab(tab);
		if (tab instanceof TabVariable) 				isValid = validateVariableTab(tab);
		if (tab instanceof TabValues) 					isValid = validateValuesTab(tab);
		if (tab instanceof TabQuestion)					isValid = validateQuestionTab(tab); 
		if (tab instanceof TabStudy)					isValid = validateStudyTab(tab); 
		if (tab instanceof TabMapVariableInstance) 		isValid = validateMapVariableInstanceTab(tab);
		if (tab instanceof TabMapVariableAttribute) 	isValid = validateMapVariableAttributeTab(tab);
		if (tab instanceof TabMapVariableChar) 			isValid = validateMapVariableCharTab(tab);
			
		return isValid;
	}
	
	/**
	 * 
	 */
	private void callExportSyntax() {
		ExportSyntaxDialog esd = new ExportSyntaxDialog(_model, _view.getLocale(), _view.getFont(), this);
	}
	   
	/**
	 * @param tab
	 * @return
	 */
	public boolean validateProjectTab(Tab tab) {
		return true;
	}
	
	/**
	 * @param tab
	 */
	public void handleProjectTab(Tab tab) {
		HashMap<String, Component> tabHashMap = tab.buildHashMap(); 

		String projectName = "LBL_NEW_PROJECT";
    	if (tabHashMap.containsKey("prjNameTF")) {
    		projectName = ((JTextField)tabHashMap.get("prjNameTF")).getText();
    	}
    	(_model.getProject()).setName(projectName);
    	
		String projectSummary = "";
    	if (tabHashMap.containsKey("prjSummaryTA")) {
    		projectSummary = ((JTextArea)tabHashMap.get("prjSummaryTA")).getText();
    	}
    	if (!(((_model.getProject()).getSummary() instanceof Comment))) {
    		Comment proSummary = new Comment();
    		proSummary.setReference(this);
    		(_model.getProject()).setSummary(proSummary);
    	}
    	(_model.getProject()).getSummary().setText(projectSummary);
    	(_model.getProject()).getSummary().setReference(_model.getProject());
	}
	
	/**
	 * @param tab
	 * @return
	 */
	private boolean validateConceptTab(Tab tab) {
		return true;
	}
	
	/**
	 * @param tab
	 */
	private void handleConceptTab(Tab tab) {
		HashMap<String, Component> tabHashMap = tab.buildHashMap();
		
    	String concept = "";
    	if (tabHashMap.containsKey("conConceptTF")) {
    		concept = ((JTextField)tabHashMap.get("conConceptTF")).getText();
    		
	    	String definition = "";
	    	if (tabHashMap.containsKey("conDefinitionTA")) {
	    		definition = ((JTextArea)tabHashMap.get("conDefinitionTA")).getText();
	    	}
	    	
	    	if (!(_model.getProject().getConcept() instanceof Concept))
	    		(_model.getProject()).setConcept(new Concept());
	    	(_model.getProject()).getConcept().setDefaultLabel(concept);
	    	(_model.getProject()).getConcept().setDefaultDescription(definition);
    	}
	}
	
	/**
	 * @param tab
	 * @return
	 */
	private boolean validateLiteratureTab(Tab tab) {
		return true;
	}
	
	/**
	 * @param tab
	 */
	private void handleLiteratureTab(Tab tab) {
		HashMap<String, Component> tabHashMap = tab.buildHashMap(); 
	   
	   	ArrayList<Literature>	newLiteratures = new ArrayList<Literature>();
    	for (int litCounter = 0; litCounter < 9; litCounter++) {
    		if (!tabHashMap.containsKey("LiteratureID_" + litCounter))
    			continue;
    		
    		int literature_id = -1;
			try {
				literature_id = Integer.parseInt(((JTextField)tabHashMap.get("LiteratureID_" + litCounter)).getText());
			} catch (NumberFormatException e) {
				literature_id = -1;
			}
    		
    		Literature literature = null;
       		if (literature_id == -1) {
       			literature = new Literature(generateNegID());
       		} else {
       			literature = (_model.getProject()).getConcept().getLiteratureByID(literature_id);
       		}
       		
       		ArrayList<Person>	newAuthors = new ArrayList<Person>();
    		for (int authorCounter = 0; authorCounter < 3; authorCounter++) {
    			if (!tabHashMap.containsKey("AuthorName_" + authorCounter + "_" + litCounter))
        			continue;
      			
    			String authorName = "";
	    		authorName = ((JTextField)tabHashMap.get("AuthorName_" + authorCounter + "_" + litCounter)).getText();    		

	    		int author_id = -1;
	    		if (tabHashMap.containsKey("AuthorID_" + authorCounter + "_" + litCounter)) {
	    			author_id = Integer.parseInt(((JTextField)tabHashMap.get("AuthorID_" + authorCounter + "_" + litCounter)).getText());
	    		}
	    		
	    		Person author = null;
	       		if (author_id < 1) {
		    		author = new Person(generateNegID());
	    		} else {
	    			author = literature.getAuthorByID(author_id);
	    		}
	       		
    			if (author != null) {
	    			author.setName(authorName);
	    			
	    			newAuthors.add(author);
    			}
    		}
    		
    		if (tabHashMap.containsKey("AuthorEtAl_" + litCounter)) {   			
    			literature.setAuthorEtAl(((JCheckBox)(tabHashMap.get("AuthorEtAl_" + litCounter))).isSelected());
    		}

       		String dateString = null;
    		java.sql.Date date = null;
    		if (tabHashMap.containsKey("Date_" + litCounter)) {
    			if (tabHashMap.get("Date_" + litCounter) != null) {
    				dateString = ((JFormattedTextField)tabHashMap.get("Date_" + litCounter)).getText();
	    			
	    			if (dateString.length() == 4) {     			
		    			DateFormat formatter;
		    			formatter = new SimpleDateFormat("yyyy");
						try {
							date = new java.sql.Date(formatter.parse(dateString.substring(dateString.length()-4, dateString.length())).getTime());
						} catch (ParseException e) {
							JOptionPane.showMessageDialog(
									null, e.getMessage(), "ParseException:",  JOptionPane.ERROR_MESSAGE);
						}
	    			}
    			}
    		}
    		
    		String title = null;
    		if (tabHashMap.containsKey("Title_" + litCounter))
    			title = ((JTextArea)tabHashMap.get("Title_" + litCounter)).getText();
    		
    		String source = null;
    		if (tabHashMap.containsKey("Source_" + litCounter))
    			source = ((JTextArea)tabHashMap.get("Source_" + litCounter)).getText();
    		
    		/* EDITOR LIKE AUTHOR */
    		ArrayList<Person> newEditors = new ArrayList<Person>();
       		for (int editorCounter = 0; editorCounter < 2; editorCounter++) {
    			if (!tabHashMap.containsKey("EditorName_" + editorCounter + "_" + litCounter))
        			continue;
    			
	    		String editorName = ((JTextField)tabHashMap.get("EditorName_" + editorCounter + "_" + litCounter)).getText();

	    		int editor_id = -1;
	    		if (tabHashMap.containsKey("EditorID_" + editorCounter + "_" + litCounter)) {
	    			editor_id = Integer.parseInt(((JTextField)tabHashMap.get("EditorID_" + editorCounter + "_" + litCounter)).getText());
	    		}
	    		
	    		Person editor = null;
	       		if (editor_id < 1) {
		    		editor = new Person();
	    		} else {
	    			editor = literature.getEditorByID(editor_id);
	    		}
	       		
    			if (editor != null) {
	    			editor.setName(editorName);
	    			
	    			newEditors.add(editor);		    			
    			}
    		}
       		
    		if (tabHashMap.containsKey("EditorEtAl_" + litCounter))
    			literature.setEditorEtAl(((JCheckBox)(tabHashMap.get("EditorEtAl_" + litCounter))).isSelected());
    		
    		String publisher = null;
    		if (tabHashMap.containsKey("Publisher_" + litCounter))
    			publisher = ((JTextField)tabHashMap.get("Publisher_" + litCounter)).getText();
    		
    		String place = null;
       		if (tabHashMap.containsKey("Place_" + litCounter))
    			place = ((JTextField)tabHashMap.get("Place_" + litCounter)).getText();

    		String issue = null;
    		if (tabHashMap.containsKey("Issue_" + litCounter))
    			issue = ((JTextField)tabHashMap.get("Issue_" + litCounter)).getText();
    			
       		String pages = null;
    		if (tabHashMap.containsKey("Pages_" + litCounter))
    			pages = ((JTextField)tabHashMap.get("Pages_" + litCounter)).getText();
    		
       		String webaddress = null;
    		if (tabHashMap.containsKey("Webaddress_" + litCounter))
    			webaddress = ((JTextField)tabHashMap.get("Webaddress_" + litCounter)).getText();
    		    		
    		literature.setAuthors(newAuthors);
    		literature.setDate(date);
    		literature.setTitle(title);
    		literature.setSource(source);
    		literature.setEditors(newEditors);
    		literature.setPublisher(publisher);
    		literature.setPlace(place); 
    		literature.setIssue(issue);
    		literature.setPages(pages);
    		literature.setWebaddress(webaddress);
    		
    		newLiteratures.add(literature);
    	}
    	
    	(_model.getProject()).getConcept().setLiteratures(newLiteratures);
	}
	
	/**
	 * @param tab
	 * @return
	 */
	private boolean validateMeasurementTab(Tab tab) {
		HashMap<String, Component> tabHashMap = tab.buildHashMap();
		boolean isValid = true;
		
    	if (tabHashMap.containsKey(TabMeasurement.CATEGORY_EDITOR)) {
    		JTable resultTable = ((JTable)(tabHashMap.get(TabMeasurement.CATEGORY_EDITOR)));
    		
    		TableModel resultModel = resultTable.getModel();
			String code			= (String)resultModel.getValueAt(0, 2);
			String label		= (String)resultModel.getValueAt(0, 3);
			
			if ((resultTable.getModel().getRowCount() == 1) && 
					(code.isEmpty()) &&
					(label.isEmpty()) ) {
				ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, _view.getLocale());
				JOptionPane.showMessageDialog(_view.getAppFrame(), bundle.getString(MISSING_INPUT)); 
					
				isValid = false;
			}
		}
		
		return isValid;	
	}
	
	/**
	 * @param tab
	 */
	private void handleMeasurementTab(Tab tab) {
		HashMap<String, Component> tabHashMap = tab.buildHashMap(); 
		
   		String targetName = EMPTY;
		if (tabHashMap.containsKey(TabMeasurement.MEA_TARGET_NAME)) {
			targetName = ((JTextField) tabHashMap.get(TabMeasurement.MEA_TARGET_NAME)).getText();
		}
		
   		String targetLabel = EMPTY;
		if (tabHashMap.containsKey(TabMeasurement.MEA_TARGET_LABEL)) {
			targetLabel = ((JTextField) tabHashMap.get(TabMeasurement.MEA_TARGET_LABEL)).getText();
		}
		
    	TypeOfData measurementLevel = null;   	
    	if (tabHashMap.containsKey(TabMeasurement.INDIVIDUAL)) {
    		if (((JRadioButton) tabHashMap.get(TabMeasurement.INDIVIDUAL)).isSelected())
    			measurementLevel = TypeOfData.INDIVIDUAL;
    	}	
    	if (tabHashMap.containsKey(TabMeasurement.AGGREGATE)) {
    		if (((JRadioButton) tabHashMap.get(TabMeasurement.AGGREGATE)).isSelected())
    			measurementLevel = TypeOfData.AGGREGATE;
    	}
//    	if (tabHashMap.containsKey(TabMeasurement.MESO)) {
//    		if (((JRadioButton) tabHashMap.get(TabMeasurement.MESO)).isSelected())
//    			measurementLevel = TypeOfData.MESO;
//    	}
    	
      	MeasurementLevel kind = null;   	
    	if (tabHashMap.containsKey(TabMeasurement.NOMINAL)) {
    		if (((JRadioButton) tabHashMap.get(TabMeasurement.NOMINAL)).isSelected())
    			kind = MeasurementLevel.NOMINAL;
    	}	
    	if (tabHashMap.containsKey(TabMeasurement.ORDINAL)) {
    		if (((JRadioButton) tabHashMap.get(TabMeasurement.ORDINAL)).isSelected())
    			kind = MeasurementLevel.ORDINAL;
    	}
       	if (tabHashMap.containsKey(TabMeasurement.INTERVAL)) {
    		if (((JRadioButton) tabHashMap.get(TabMeasurement.INTERVAL)).isSelected())
    			kind = MeasurementLevel.INTERVAL;
    	}
       	if (tabHashMap.containsKey(TabMeasurement.RATIO)) {
    		if (((JRadioButton) tabHashMap.get(TabMeasurement.RATIO)).isSelected())
    			kind = MeasurementLevel.RATIO;
    	}
       	if (tabHashMap.containsKey(TabMeasurement.CONTINUOUS)) {
    		if (((JRadioButton) tabHashMap.get(TabMeasurement.CONTINUOUS)).isSelected())
    			kind = MeasurementLevel.CONTINUOUS;
    	}
    	
    	MeasurementType measurementType = null;   	
    	if (tabHashMap.containsKey(TabMeasurement.CLASS)) {
    		if (((JRadioButton) tabHashMap.get(TabMeasurement.CLASS)).isSelected())
    			measurementType = MeasurementType.CLASSIFICATION;
    	}	
    	if (tabHashMap.containsKey(TabMeasurement.SCALE)) {
    		if (((JRadioButton) tabHashMap.get(TabMeasurement.SCALE)).isSelected())
    			measurementType = MeasurementType.SCALE;
    	}
    	if (tabHashMap.containsKey(TabMeasurement.INDEX)) {
    		if (((JRadioButton) tabHashMap.get(TabMeasurement.INDEX)).isSelected())
    			measurementType = MeasurementType.INDEX;
    	}   	
    	if (measurementType != null) {
    		/* TODO EINE ÄNDERUNG DES TYPS SOLLTE ZU EINEM NEUEN MAß FÜHREN */
    		
    		String measurementLabel = EMPTY;
    		if (tabHashMap.containsKey(TabMeasurement.MSRMNT_NAME)) {
    			measurementLabel = ((JTextField) tabHashMap.get(TabMeasurement.MSRMNT_NAME)).getText();
    		}
    		
    		if (!((_model.getProject()).getContent().getMeasurement() instanceof Measurement))
    			(_model.getProject()).getContent().setMeasurement(new Measurement());
    		
    		(_model.getProject()).setTargetName(targetName);
    		(_model.getProject()).setTargetLabel(targetLabel);
    		(_model.getProject()).getContent().getMeasurement().setLevel(measurementLevel);
    		(_model.getProject()).getContent().getMeasurement().setType(measurementType);
    		(_model.getProject()).getContent().getMeasurement().setName(measurementLabel);
    		(_model.getProject()).getContent().getMeasurement().setKind(kind);
    		
    		WorkStepInstance inst = (_model.getProject()).getContent().getSetupLayer(); 
    		
    		AttributeLink link = (_model.getProject()).getContent().getLinkByAttribute((_model.getProject()).getContent().getMeasurement());
    		boolean no_link_yet = false;
			if (!(link instanceof AttributeLink)) {
				no_link_yet = true;
				
				link = new AttributeLink();			
				link.setEntityID(generateNegID());			
			}			
			link.setAttribute((_model.getProject()).getContent().getMeasurement());
			link.setAttributeLinkType(AttributeLinkType.MEASUREMENT);
			link.setInstance(inst);
			
			if (no_link_yet)
				(_model.getProject()).getContent().addLink(link);
	    	
    	}
    	
    	if (tabHashMap.containsKey(TabMeasurement.CATEGORY_EDITOR)) {
    		JTable resultTable = ((JTable)(tabHashMap.get(TabMeasurement.CATEGORY_EDITOR)));
    		
    		ArrayList<Category> uptodateCategories = new ArrayList<Category>();
    		TableModel resultModel = resultTable.getModel();
    		for (int i=0; i<resultModel.getRowCount(); i++) {
    			Category cat = null; 
    			int id 				= (Integer)resultModel.getValueAt(i, 0);
    			cat	= (Category)resultModel.getValueAt(i, 1); 
    			if (cat != null) 
    				id = cat.getEntityID(); 
    			String code			= (String)resultModel.getValueAt(i, 2);
    			String label		= (String)resultModel.getValueAt(i, 3);
    			boolean is_missing	= (Boolean)resultModel.getValueAt(i, 4);
    			
    			if (id == -1) {
    				cat.setEntityID(generateNegID()); 
    				
    				resultModel.setValueAt(cat.getEntityID(), i, 0);
    				
    				CharacteristicLink link = new CharacteristicLink();
    				link.setEntityID(generateNegID());
    				link.setAttribute((_model.getProject()).getContent().getMeasurement());
    				link.setType(CharacteristicLinkType.CATEGORY);
    				link.setCharacteristic(cat);
    				
    				(_model.getProject()).getContent().addCategory(cat);
    				(_model.getProject()).getContent().addCharacteristicLink(link);
    			} else {
    				cat = (_model.getProject()).getContent().getCategoryByID(id);
    			}
    			if (cat != null) {
    				cat.setCode(code);
    				cat.setLabel(label);
//    				cat.getDefinition().setText(definition);
//    				cat.setLevel(level);
    				cat.setMissing(is_missing);
    				
    				uptodateCategories.add(cat);
    			}
    		}
    		
       		/* Collect list of deleted Categories */
    		ArrayList<Category> deletedCategories = new ArrayList<Category>();    		
    		Iterator<Category> catIterator = (_model.getProject()).getContent().getCategories().iterator();
    		while (catIterator.hasNext()) {
    			Category cat = catIterator.next();
    			
    			if (!uptodateCategories.contains(cat))
    				deletedCategories.add(cat);
    		}
    		/* Remove deleted Categories from the Model */
    		catIterator = deletedCategories.iterator();
    		while (catIterator.hasNext()) {
    			Category cat = catIterator.next();
    			
    			(_model.getProject()).getContent().removeCategory(cat);
    		}
    		
       		/* Collect list of obsolete char. Links */
    		ArrayList<CharacteristicLink> obsoleteCharLinks = new ArrayList<CharacteristicLink>();
    		Iterator<CharacteristicLink> charLinkIterator = (_model.getProject()).getContent().getCharacteristicLinks().iterator();
    		while (charLinkIterator.hasNext()) {
    			CharacteristicLink link = charLinkIterator.next();
    			
    			if (deletedCategories.contains(link.getCharacteristic()))
    				obsoleteCharLinks.add(link);
    		}
    		/* Remove obsolete char. Links from the Model */
    		charLinkIterator = obsoleteCharLinks.iterator();
    		while (charLinkIterator.hasNext()) {
    			CharacteristicLink link = charLinkIterator.next();
    			
    			(_model.getProject()).getContent().removeCharacteristicLink(link);
    		}
    		
       		/* Collect obsolete char. Mappings */
    		ArrayList<CharacteristicMap> obsoleteCharMap = new ArrayList<CharacteristicMap>();
    		Iterator<CharacteristicMap> charMapIterator = (_model.getProject()).getContent().getCharacteristicMaps().iterator();
    		while (charMapIterator.hasNext()) {
    			CharacteristicMap map = charMapIterator.next();
    			
    			if (obsoleteCharLinks.contains(map.getSourceCharacteristic())) 
    				obsoleteCharMap.add(map);
    			
       			if (obsoleteCharLinks.contains(map.getTargetCharacteristic()))
    				map.setTargetCharacteristic(null);
    		}
       		/* remove obsolete char. Mappings from the Model */
    		charMapIterator = obsoleteCharMap.iterator();
    		while (charMapIterator.hasNext()) {
    			CharacteristicMap map = charMapIterator.next();
    			
    			(_model.getProject()).getContent().removeCharacteristicMap(map);
    		}
    	}
    	
	}
	
	/**
	 * @param tab
	 * @return
	 */
	private boolean validateDimensionTab(Tab tab) {
		HashMap<String, Component> tabHashMap = tab.buildHashMap();
		boolean isValid = true;
		
		if (tabHashMap.containsKey("dimDimensionTbl")) {
			JTable resultTable = ((JTable)(tabHashMap.get("dimDimensionTbl")));
			
			if (resultTable.getModel().getRowCount() < 1) {
				ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, _view.getLocale());
				JOptionPane.showMessageDialog(_view.getAppFrame(), bundle.getString(MISSING_INPUT)); 
					
				isValid = false;
			}
		}
		
		return isValid;
	}
	
	/**
	 * @param tab
	 */
	private void handleDimensionTab(Tab tab) {
		HashMap<String, Component> tabHashMap = tab.buildHashMap(); 
		
    	if (tabHashMap.containsKey("dimDimensionTbl")) {
    		JTable resultTable = ((JTable)(tabHashMap.get("dimDimensionTbl")));
    		
    		ArrayList<ConDimension> uptodateDimensions = new ArrayList<ConDimension>();
    		TableModel resultModel = resultTable.getModel();
    		for (int i=0; i<resultModel.getRowCount(); i++) {
    			int 		id			= (Integer)resultModel.getValueAt(i, 0);
    			String		label		= (String)resultModel.getValueAt(i, 2);
    			String		definition	= (String)resultModel.getValueAt(i, 3);
    			JComboBox	level		= (JComboBox)resultModel.getValueAt(i, 4);
    			
    			ConDimension dim = null;
    			if (id == -1) {
    				dim = new ConDimension(generateNegID());
    				
    				AttributeLink link = new AttributeLink();
    				link.setEntityID(generateNegID());			
    				link.setAttribute(dim);
    				link.setAttributeLinkType(AttributeLinkType.DIMENSION);
    				link.setInstance((_model.getProject()).getContent().getConceptualLayer());
    				
    				AttributeMap map = new AttributeMap();
    				map.setEntityID(generateNegID());
    				map.setAttributeMapType(AttributeMapType.SPECIFICATION);
    				map.setSourceAttribute(link);
    				
    				(_model.getProject()).getContent().addDimension(dim);
    				(_model.getProject()).getContent().addLink(link);
    				(_model.getProject()).getContent().addMap(map);
    			} else {
    				dim = (_model.getProject()).getContent().getDimensionByID(id);    				
    			}
    			if (dim != null) {
	    			dim.setLabel(label);
	    			dim.getDefinition().setText(definition);
	    			dim.setLevel((TypeOfData)level.getSelectedItem());
	    			
	    			uptodateDimensions.add(dim);
    			}
    		}
    		
    		/* Collect list of Dimensions to be removed */
    		ArrayList<ConDimension> deletedDimensions = new ArrayList<ConDimension>();    		
    		Iterator<ConDimension> dimIterator = (_model.getProject()).getContent().getDimensions().iterator();
    		while (dimIterator.hasNext()) {
    			ConDimension dim = dimIterator.next();
    			
    			if (!uptodateDimensions.contains(dim))
    				deletedDimensions.add(dim);
    		}
    		/* Remove deleted Dimensions from the Model */
    		dimIterator = deletedDimensions.iterator();
    		while (dimIterator.hasNext()) {
    			ConDimension dim = dimIterator.next();
    			
    			(_model.getProject()).getContent().removeDimension(dim);
    		}
    		
    		/* Collect list of obsolete attr. Links */
    		ArrayList<AttributeLink> obsoleteAttrLinks = new ArrayList<AttributeLink>();
    		Iterator<AttributeLink> attrLinkIterator = (_model.getProject()).getContent().getLinks().iterator();
    		while (attrLinkIterator.hasNext()) {
    			AttributeLink link = attrLinkIterator.next();
    			
    			if (deletedDimensions.contains(link.getAttribute()))
    				obsoleteAttrLinks.add(link);
    		}
    		/* Remove obsolete attr. Links from the Model */
    		attrLinkIterator = obsoleteAttrLinks.iterator();
    		while (attrLinkIterator.hasNext()) {
    			AttributeLink link = attrLinkIterator.next();
    			
    			(_model.getProject()).getContent().removeLink(link);
    		}
    		
    		/* Collect obsolete attr. Mappings */
    		ArrayList<AttributeMap> obsoleteAttrMap = new ArrayList<AttributeMap>();
    		Iterator<AttributeMap> attrMapIterator = (_model.getProject()).getContent().getMaps().iterator();
    		while (attrMapIterator.hasNext()) {
    			AttributeMap map = attrMapIterator.next();
    			
    			if (obsoleteAttrLinks.contains(map.getSourceAttribute())) 
    				obsoleteAttrMap.add(map);
    			
    			if (obsoleteAttrLinks.contains(map.getTargetAttribute()))
    				map.setTargetAttribute(null);    			
    		}
    		/* remove obsolete attr. Mappings from the Model */
    		attrMapIterator = obsoleteAttrMap.iterator();
    		while (attrMapIterator.hasNext()) {
    			AttributeMap map = attrMapIterator.next();
    			
    			(_model.getProject()).getContent().removeMap(map) ;
    		}
    		
    		/* Collect obsolete attr. Comparisons */
    		ArrayList<AttributeComp> obsoleteAttrComp = new ArrayList<AttributeComp>();
    		Iterator<AttributeComp> attrCompIterator = (_model.getProject()).getContent().getComps().iterator();
    		while (attrCompIterator.hasNext()) {
    			AttributeComp comp = attrCompIterator.next();
    			
    			if ((obsoleteAttrLinks.contains(comp.getSourceAttribute())) ||
    					(obsoleteAttrLinks.contains(comp.getTargetAttribute())))
    				obsoleteAttrComp.add(comp);
    		}
    		/* remove obsolete attr. Comparisons from the Model */
    		attrCompIterator = obsoleteAttrComp.iterator();
    		while (attrCompIterator.hasNext()) {
    			AttributeComp comp = attrCompIterator.next();
    			
    			(_model.getProject()).getContent().removeComp(comp);
    		}
    		
    		/* Collect list of obsolete char. Links */
    		ArrayList<CharacteristicLink> obsoleteCharLinks = new ArrayList<CharacteristicLink>();
    		Iterator<CharacteristicLink> charLinkIterator = (_model.getProject()).getContent().getCharacteristicLinks().iterator();
    		while (charLinkIterator.hasNext()) {
    			CharacteristicLink link = charLinkIterator.next();
    			
    			if (deletedDimensions.contains(link.getAttribute()))
    				obsoleteCharLinks.add(link);
    		}
    		/* Remove obsolete char. Links from the Model */
    		charLinkIterator = obsoleteCharLinks.iterator();
    		while (charLinkIterator.hasNext()) {
    			CharacteristicLink link = charLinkIterator.next();
    			
    			(_model.getProject()).getContent().removeCharacteristicLink(link);
    		}
    		
    		/* Collect obsolete char. Mappings */
    		ArrayList<CharacteristicMap> obsoleteCharMap = new ArrayList<CharacteristicMap>();
    		Iterator<CharacteristicMap> charMapIterator = (_model.getProject()).getContent().getCharacteristicMaps().iterator();
    		while (charMapIterator.hasNext()) {
    			CharacteristicMap map = charMapIterator.next();
    			
    			if (obsoleteCharLinks.contains(map.getSourceCharacteristic()))
    				obsoleteCharMap.add(map);
    			
    			if (obsoleteCharLinks.contains(map.getTargetCharacteristic()))
    				map.setTargetCharacteristic(null);
    		}
       		/* remove obsolete char. Mappings from the Model */
    		charMapIterator = obsoleteCharMap.iterator();
    		while (charMapIterator.hasNext()) {
    			CharacteristicMap map = charMapIterator.next();
    			
    			(_model.getProject()).getContent().removeCharacteristicMap(map);
    		}   		
    	}
	}
	
	/**
	 * @param tab
	 * @return
	 */
	private boolean validateSpecificationsTab(Tab tab) {
		HashMap<String, Component> tabHashMap = tab.buildHashMap();
		boolean isValid = true;
		
		if (tabHashMap.containsKey("dimSpecificationTbl")) {
			JTable resultTable = ((JTable)(tabHashMap.get("dimSpecificationTbl")));
			
			ArrayList<ConDimension> usedDimensions = new ArrayList<ConDimension>();
			TableModel resultModel = resultTable.getModel();
			for (int i=0; i<resultModel.getRowCount(); i++) {
				if (resultModel.getValueAt(i, 3) instanceof ConDimension) {
					ConDimension	dimension	= (ConDimension)resultModel.getValueAt(i, 3);
					
					usedDimensions.add(dimension);
				}	
			}
			
			Iterator<ConDimension> dimension_it = _model.getProject().getContent().getDimensions().iterator();
			while (dimension_it.hasNext()) {
				ConDimension dimension = dimension_it.next();
				
				if (!usedDimensions.contains(dimension)) {
					ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, _view.getLocale());
					JOptionPane.showMessageDialog(_view.getAppFrame(), bundle.getString(MISSING_INPUT)); 
					
					isValid = false;
				}
			}
		}
		
		return isValid;
	}
	
	/**
	 * @param tab
	 */
	private void handleSpecificationsTab(Tab tab) {
		HashMap<String, Component> tabHashMap = tab.buildHashMap(); 
		
    	if (tabHashMap.containsKey("dimSpecificationTbl")) {
    		JTable resultTable = ((JTable)(tabHashMap.get("dimSpecificationTbl")));
    		
    		ArrayList<ConSpecification> uptodateSpecifications = new ArrayList<ConSpecification>();
    		TableModel resultModel = resultTable.getModel();
    		for (int i=0; i<resultModel.getRowCount(); i++) {
       			int 			id		= (Integer)resultModel.getValueAt(i, 0);
    			String			label	= (String)resultModel.getValueAt(i, 2);
    			ConDimension	dim		= (ConDimension)resultModel.getValueAt(i, 3);
    			
    			ConSpecification spec = null;
    			if (id == -1) {
    				spec = new ConSpecification(generateNegID());
    				
    				CharacteristicLink link = new CharacteristicLink();
    				link.setEntityID(generateNegID());
    				link.setAttribute(dim);
    				link.setType(CharacteristicLinkType.SPECIFICATION);
    				link.setCharacteristic(spec);
    				
    				CharacteristicMap map = new CharacteristicMap();
    				map.setEntityID(generateNegID());
    				map.setType(CharacteristicMapType.SPECIFICATION);
    				map.setSourceCharacteristic(link);
    				
    				(_model.getProject()).getContent().addSpecification(spec);
    				(_model.getProject()).getContent().addCharacteristicLink(link);
    				(_model.getProject()).getContent().addCharacteristicMap(map);
    			} else {
    				spec = (_model.getProject()).getContent().getSpecificationByID(id);    				
    			}
    			if (spec != null) {
	    			spec.setLabel(label);
	    			
	    			uptodateSpecifications.add(spec);
    			}
    		}
    		
       		/* Collect list of deleted Specifications */
    		ArrayList<ConSpecification> deletedSpecifications = new ArrayList<ConSpecification>();    		
    		Iterator<ConSpecification> specIterator = (_model.getProject()).getContent().getSpecifications().iterator();
    		while (specIterator.hasNext()) {
    			ConSpecification spec = specIterator.next();
    			
    			if (!uptodateSpecifications.contains(spec))
    				deletedSpecifications.add(spec);
    		}
    		/* Remove deleted Specifications from the Model */
    		specIterator = deletedSpecifications.iterator();
    		while (specIterator.hasNext()) {
    			ConSpecification spec = specIterator.next();
    			
    			(_model.getProject()).getContent().removeSpecification(spec);
    		}
    		
       		/* Collect list of obsolete char. Links */
    		ArrayList<CharacteristicLink> obsoleteCharLinks = new ArrayList<CharacteristicLink>();
    		Iterator<CharacteristicLink> charLinkIterator = (_model.getProject()).getContent().getCharacteristicLinks().iterator();
    		while (charLinkIterator.hasNext()) {
    			CharacteristicLink link = charLinkIterator.next();
    			
    			if (deletedSpecifications.contains(link.getCharacteristic()))
    				obsoleteCharLinks.add(link);
    		}
    		/* Remove obsolete char. Links from the Model */
    		charLinkIterator = obsoleteCharLinks.iterator();
    		while (charLinkIterator.hasNext()) {
    			CharacteristicLink link = charLinkIterator.next();
    			
    			(_model.getProject()).getContent().getCharacteristicLinks().remove(link);
    		}
    		
       		/* Collect obsolete char. Mappings */
    		ArrayList<CharacteristicMap> obsoleteCharMap = new ArrayList<CharacteristicMap>();
    		Iterator<CharacteristicMap> charMapIterator = (_model.getProject()).getContent().getCharacteristicMaps().iterator();
    		while (charMapIterator.hasNext()) {
    			CharacteristicMap map = charMapIterator.next();
    			
    			if (obsoleteCharLinks.contains(map.getSourceCharacteristic()))			
    				obsoleteCharMap.add(map);
    			
       			if (obsoleteCharLinks.contains(map.getTargetCharacteristic()))
       				map.setTargetCharacteristic(null);       			
    		}
       		/* remove obsolete char. Mappings from the Model */
    		charMapIterator = obsoleteCharMap.iterator();
    		while (charMapIterator.hasNext()) {
    			CharacteristicMap map = charMapIterator.next();
    			
    			(_model.getProject()).getContent().removeCharacteristicMap(map);
    		}
    	}		
	}
	
	/**
	 * @param tab
	 * @return
	 */
	private boolean validateMapDimensionAttributeTab(Tab tab) {
		HashMap<String, Component> tabHashMap = tab.buildHashMap();
		boolean isValid = true;
		
		if (tabHashMap.containsKey("mapTable")) {
			JTable resultTable = ((JTable)(tabHashMap.get("mapTable")));
			
			TableModel resultModel = resultTable.getModel();
			for (int i=0; i<resultModel.getRowCount(); i++) {
				JComboBox			target	= (JComboBox)resultModel.getValueAt(i, 2);	
				
				if (!(target.getSelectedItem() instanceof Attributes)) {
					ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, _view.getLocale());
					JOptionPane.showMessageDialog(_view.getAppFrame(), bundle.getString(MISSING_INPUT)); 
					
					isValid = false;
				}
			}
		}
		
		return isValid;
	}
	
	/**
	 * @param tab
	 */
	private void handleMapDimensionAttributeTab(Tab tab) {
		HashMap<String, Component> tabHashMap = tab.buildHashMap();
		
		if (tabHashMap.containsKey("mapTable")) {
			JTable resultTable = ((JTable)(tabHashMap.get("mapTable")));
			
	   		ArrayList<AttributeMap> uptodateAttributeMaps = new ArrayList<AttributeMap>();
			TableModel resultModel = resultTable.getModel();
			for (int i=0; i<resultModel.getRowCount(); i++) {
	   			int 				id		= (Integer)resultModel.getValueAt(i, 0);
				ConDimension		source	= (ConDimension)resultModel.getValueAt(i, 3);	
				JComboBox			target	= (JComboBox)resultModel.getValueAt(i, 2);	
				
				AttributeMap map = null;
				if (id == -1) {
					map = new AttributeMap(generateNegID());
					
					(_model.getProject()).getContent().addMap(map);
				} else {
					map = (_model.getProject()).getContent().getMapByID(id);    				
				}
				if (map != null) {
					map.setSourceAttribute((_model.getProject()).getContent().getLinkByAttribute(source));
					if (target.getSelectedItem() instanceof Attributes) 
					map.setTargetAttribute((_model.getProject()).getContent().getLinkByAttribute((Attributes)target.getSelectedItem()));
					
					map.setAttributeMapType(AttributeMapType.SPECIFICATION);
					
					map.setBelongsTo( 
							(_model.getProject()).getContent().getInstanceMapByInstance(
									(_model.getProject()).getContent().getRefByInstance( 
											((AttributeMap)resultModel.getValueAt(i, 1)).getSourceAttribute().getInstance()
									) 
							) 
					);
					
	    			uptodateAttributeMaps.add(map);
				}
			}
			
    		Iterator<CharacteristicMap> charMapsIter = (_model.getProject()).getContent().getCharacteristicMaps().iterator();
    		while (charMapsIter.hasNext()) {
    			CharacteristicMap charMap = charMapsIter.next();
    			
    			CharacteristicLink charLink = charMap.getSourceCharacteristic();
    			Attributes attr = charLink.getAttribute();
    			AttributeLink attrLink = (_model.getProject()).getContent().getLinkByAttribute(attr);
    			AttributeMap attrMap = (_model.getProject()).getContent().getAttributeMapByAttribute(attrLink);
    			
    			charMap.setBelongsTo(attrMap);
    		}
    		
       		/* Collect list of deleted Mappings */
    		ArrayList<AttributeMap> deletedAttributeMaps = new ArrayList<AttributeMap>();    		
    		Iterator<AttributeMap> attrMapIterator = (_model.getProject()).getContent().getMapsByType(AttributeMapType.SPECIFICATION).iterator();
    		while (attrMapIterator.hasNext()) {
    			AttributeMap map = attrMapIterator.next();
    			
    			if (!uptodateAttributeMaps.contains(map))
    				deletedAttributeMaps.add(map);
    		}
    		/* Remove deleted Mappings from the Model */
    		attrMapIterator = deletedAttributeMaps.iterator();
    		while (attrMapIterator.hasNext()) {
    			AttributeMap map = attrMapIterator.next();
    			
    			(_model.getProject()).getContent().removeMap(map); 
    		}
    		
    		/* Collect list of obsolete Char Mappings */
    		ArrayList<CharacteristicMap> obsoleteCharMaps = new ArrayList<CharacteristicMap>();    		
    		Iterator<CharacteristicMap> charMapIterator = (_model.getProject()).getContent().getCharacteristicMaps().iterator();
    		while (charMapIterator.hasNext()) {
    			CharacteristicMap map = charMapIterator.next();
    			
    			if (deletedAttributeMaps.contains(map.getBelongsTo()))
    				obsoleteCharMaps.add(map);
    		}
    		/* Remove obsolete Mappings from the Model */
    		charMapIterator = obsoleteCharMaps.iterator();
    		while (charMapIterator.hasNext()) {
    			CharacteristicMap map = charMapIterator.next();
    			
       			(_model.getProject()).getContent().removeCharacteristicMap(map);
        	}
 		}
	}
	
	/**
	 * @param tab
	 * @return
	 */
	private boolean validateMapDimensionInstanceTab(Tab tab) {
		HashMap<String, Component> tabHashMap = tab.buildHashMap();
		boolean isValid = true;
		
		if (tabHashMap.containsKey("selectTable")) {
			JTable resultTable = ((JTable)(tabHashMap.get("selectTable")));
			
			TableModel resultModel = resultTable.getModel();
			for (int i=0; i<resultModel.getRowCount(); i++) {
				JComboBox			target	= (JComboBox)resultModel.getValueAt(i, 2); 
				
				if (!(target.getSelectedItem() instanceof WorkStepInstance)) {
					ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, _view.getLocale());
					JOptionPane.showMessageDialog(_view.getAppFrame(), bundle.getString(MISSING_INPUT)); 
					
					isValid = false;
				}
			}
		}
		
		return isValid;
	}
	
	/**
	 * @param tab
	 */
	private void handleMapDimensionInstanceTab(Tab tab) {
		HashMap<String, Component> tabHashMap = tab.buildHashMap(); 
		
		if (tabHashMap.containsKey("selectTable")) {
    		JTable resultTable = ((JTable)(tabHashMap.get("selectTable")));
    		
    		ArrayList<InstanceMap> uptodateInstanceMaps = new ArrayList<InstanceMap>();
    		TableModel resultModel = resultTable.getModel();
    		for (int i=0; i<resultModel.getRowCount(); i++) {
       			int 				id		= (Integer)resultModel.getValueAt(i, 0);
    			WorkStepInstance	source	= (WorkStepInstance)resultModel.getValueAt(i, 3); 
    			JComboBox			target	= (JComboBox)resultModel.getValueAt(i, 2);	
    			
    			InstanceMap map = null;
    			if (id == -1) {
    				map = new InstanceMap(generateNegID());
    				map.setBelongsTo(_model.getProject());
    				
    				(_model.getProject()).getContent().addInstanceMap(map);
    			} else {
    				map = (_model.getProject()).getContent().getInstanceMapByID(id);    				
    			}
    			if ((map != null) &&
    					(target.getSelectedItem() instanceof WorkStepInstance)) { 
    				map.setSourceInstance((_model.getProject()).getContent().getRefByInstance(source));
    				map.setTargetInstance((_model.getProject()).getContent().getRefByInstance((WorkStepInstance)target.getSelectedItem()));
    				map.setType(InstanceMapType.CONCEPTUAL);
    				map.setBelongsTo(_model.getProject());
    				
	    			uptodateInstanceMaps.add(map);
    			}
    		}
    		
    		Iterator<AttributeMap> attrMapsIter = (_model.getProject()).getContent().getMaps().iterator();
    		while (attrMapsIter.hasNext()) {
    			AttributeMap attrMap = attrMapsIter.next();
    			
    			AttributeLink attrLink = attrMap.getSourceAttribute();
    			WorkStepInstance inst = attrLink.getInstance();
    			InstanceLink instLink = (_model.getProject()).getContent().getRefByInstance(inst);
    			InstanceMap instMap = (_model.getProject()).getContent().getInstanceMapByInstance(instLink);
    			
    			attrMap.setBelongsTo(instMap);
    		}
    		
    		/* Collect list of deleted Mappings */
    		ArrayList<InstanceMap> deletedInstanceMaps = new ArrayList<InstanceMap>();    		
    		Iterator<InstanceMap> instMapIterator = (_model.getProject()).getContent().getInstanceMapsByType(InstanceMapType.CONCEPTUAL).iterator();
    		while (instMapIterator.hasNext()) {
    			InstanceMap map = instMapIterator.next();
    			
    			if (!uptodateInstanceMaps.contains(map))
    				deletedInstanceMaps.add(map);
    		}
    		/* Remove deleted Mappings from the Model */
    		instMapIterator = deletedInstanceMaps.iterator();
    		while (instMapIterator.hasNext()) {
    			InstanceMap map = instMapIterator.next();
    			
    			(_model.getProject()).getContent().removeInstanceMap(map);
    		}
    		
      		/* Collect list of obsolete Attribute Mappings */
    		ArrayList<AttributeMap> obsoleteAttributeMaps = new ArrayList<AttributeMap>();    		
    		Iterator<AttributeMap> attrMapIterator = (_model.getProject()).getContent().getMaps().iterator();
    		while (attrMapIterator.hasNext()) {
    			AttributeMap map = attrMapIterator.next();
    			
    			if (deletedInstanceMaps.contains(map.getBelongsTo()))
    				obsoleteAttributeMaps.add(map);
    		}
    		/* Remove obsolete Mappings from the Model */
    		attrMapIterator = obsoleteAttributeMaps.iterator();
    		while (attrMapIterator.hasNext()) {
    			AttributeMap map = attrMapIterator.next();
    			
    			(_model.getProject()).getContent().removeMap(map);
    		}
    		
      		/* Collect list of obsolete Char Mappings */
    		ArrayList<CharacteristicMap> obsoleteCharMaps = new ArrayList<CharacteristicMap>();    		
    		Iterator<CharacteristicMap> charMapIterator = (_model.getProject()).getContent().getCharacteristicMaps().iterator();
    		while (charMapIterator.hasNext()) {
    			CharacteristicMap map = charMapIterator.next();
    			
    			if (obsoleteAttributeMaps.contains(map.getBelongsTo()))
    				obsoleteCharMaps.add(map);
    		}
    		/* Remove obsolete Mappings from the Model */
    		charMapIterator = obsoleteCharMaps.iterator();
    		while (charMapIterator.hasNext()) {
    			CharacteristicMap map = charMapIterator.next();
    			
       			(_model.getProject()).getContent().removeCharacteristicMap(map);
        	}   
		}
	}
	
	/**
	 * @param tab
	 * @return
	 */
	private boolean validateMapDimensionCharTab(Tab tab) {
		HashMap<String, Component> tabHashMap = tab.buildHashMap();
		boolean isValid = true;
		
		if (tabHashMap.containsKey("recodeTable")) {
			JTable resultTable = ((JTable)(tabHashMap.get("recodeTable")));
			
			TableModel resultModel = resultTable.getModel();
			for (int i=0; i<resultModel.getRowCount(); i++) {
				JComboBox			target	= (JComboBox)resultModel.getValueAt(i, 2); 
				
				if (!(target.getSelectedItem() instanceof Characteristics)) {
					ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, _view.getLocale());
					JOptionPane.showMessageDialog(_view.getAppFrame(), bundle.getString(MISSING_INPUT)); 
					
					isValid = false;
				}
			}
		}
		
		return isValid;
	}
	
	/**
	 * @param tab
	 */
	private void handleMapDimensionCharTab(Tab tab) {
		HashMap<String, Component> tabHashMap = tab.buildHashMap(); 
		
		if (tabHashMap.containsKey("recodeTable")) {
			JTable resultTable = ((JTable)(tabHashMap.get("recodeTable")));
			
	   		ArrayList<CharacteristicMap> uptodateCharMaps = new ArrayList<CharacteristicMap>();
			TableModel resultModel = resultTable.getModel();
			for (int i=0; i<resultModel.getRowCount(); i++) {
	   			int 				id		= (Integer)resultModel.getValueAt(i, 0);
				ConSpecification	source	= (ConSpecification)resultModel.getValueAt(i, 3); 
				JComboBox			target	= (JComboBox)resultModel.getValueAt(i, 2);	
				
				CharacteristicMap map = null;
				if (id == -1) {
					map = new CharacteristicMap(generateNegID());
					
					(_model.getProject()).getContent().addCharacteristicMap(map);
				} else {
					map = (_model.getProject()).getContent().getCharacteristicMapByID(id);    				
				}
				if (map != null) {
					map.setSourceCharacteristic((_model.getProject()).getContent().getCharacteristicLinkByCharacteristic(source));
					if (target.getSelectedItem() instanceof Characteristics) 
					map.setTargetCharacteristic((_model.getProject()).getContent().getCharacteristicLinkByCharacteristic((Characteristics)target.getSelectedItem()));
					
					map.setType(CharacteristicMapType.SPECIFICATION); 
										
					map.setBelongsTo( 
							(_model.getProject()).getContent().getAttributeMapByAttribute(
									(_model.getProject()).getContent().getLinkByAttribute(
											((CharacteristicMap)resultModel.getValueAt(i, 1)).getSourceCharacteristic().getAttribute()
									) 
							) 
					);
					
	    			uptodateCharMaps.add(map);
				}
			}
			
       		/* Collect list of deleted Mappings */
    		ArrayList<CharacteristicMap> deletedCharacteristicMaps = new ArrayList<CharacteristicMap>();    		
    		Iterator<CharacteristicMap> charMapIterator = (_model.getProject()).getContent().getCharacteristicMapsByType(CharacteristicMapType.SPECIFICATION).iterator();
    		while (charMapIterator.hasNext()) {
    			CharacteristicMap map = charMapIterator.next();
    			
    			if (!uptodateCharMaps.contains(map))
    				deletedCharacteristicMaps.add(map);
    		}
    		/* Remove deleted Mappings from the Model */
    		charMapIterator = deletedCharacteristicMaps.iterator();
    		while (charMapIterator.hasNext()) {
    			CharacteristicMap map = charMapIterator.next();
    			
    			(_model.getProject()).getContent().removeCharacteristicMap(map);
    		}
		}
	}
		
	/**
	 * @param tab
	 * @return
	 */
	private boolean validateOSInstanceTab(Tab tab) {
		HashMap<String, Component> tabHashMap = tab.buildHashMap();
		boolean isValid = true;
		
		if (tabHashMap.containsKey("osiInstanceTbl")) {
			JTable resultTable = ((JTable)(tabHashMap.get("osiInstanceTbl")));
			
			if (resultTable.getModel().getRowCount() < 1) {
				ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, _view.getLocale());
				JOptionPane.showMessageDialog(_view.getAppFrame(), bundle.getString(MISSING_INPUT)); 
					
				isValid = false;
			}
			
			if (resultTable.getModel().getRowCount() > 0) {
				for (int i=0; i<resultTable.getModel().getRowCount(); i++) {
	    			String	label	= (String)resultTable.getModel().getValueAt(i, 2);
	    			
	    			if (label.isEmpty()) {
	    				ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, _view.getLocale());
	    				JOptionPane.showMessageDialog(_view.getAppFrame(), bundle.getString(MISSING_INPUT)); 
	    					
	    				isValid = false;	    				
	    			}    			
				}
			}

		}
		
		return isValid;
	}
	
	/**
	 * @param tab
	 */
	private void handleOSInstanceTab(Tab tab) {
		HashMap<String, Component> tabHashMap = tab.buildHashMap(); 
		
	   	if (tabHashMap.containsKey("osiInstanceTbl")) {
	   		JTable resultTable = ((JTable)(tabHashMap.get("osiInstanceTbl")));
	   		
    		ArrayList<WorkStepInstance> uptodateInstances = new ArrayList<WorkStepInstance>();
    		TableModel resultModel = resultTable.getModel();
    		for (int i=0; i<resultModel.getRowCount(); i++) {
      			int 			id		= (Integer)resultModel.getValueAt(i, 0);
    			String			label	= (String)resultModel.getValueAt(i, 2);
    			
       			WorkStepInstance instance = null;
    			if (id == -1) {
    				instance = new WorkStepInstance(generateNegID());
    				
    				InstanceLink link = new InstanceLink();
    				link.setEntityID(generateNegID());
    				link.setProject(_model.getProject());
    				link.setInstance(instance);
    				link.setWorkStepRefType(InstanceLinkType.OPERATIONAL);
    				
    				InstanceMap map = new InstanceMap();
    				map.setEntityID(generateNegID());
    				map.setSourceInstance(link);
    				map.setType(InstanceMapType.OPERATIONAL);
    				map.setBelongsTo(_model.getProject());
    				
    				(_model.getProject()).getContent().addLayer(instance);
    				(_model.getProject()).getContent().addInstanceRef(link);
    				(_model.getProject()).getContent().addInstanceMap(map);
    			} else {
    				instance = (_model.getProject()).getContent().getLayerByID(id);
    			}
    			if (instance != null) {
	    			instance.setLabel(label);
	    			instance.setType(InstanceType.OPERATIONAL);
	    			
	    			uptodateInstances.add(instance);
    			}
    		}
    		
     		/* Collect list of deleted Instances */
    		ArrayList<WorkStepInstance> deletedInstances = new ArrayList<WorkStepInstance>();    		
    		Iterator<WorkStepInstance> instanceIterator = (_model.getProject()).getContent().getLayers(InstanceType.OPERATIONAL).iterator();
    		while (instanceIterator.hasNext()) {
    			WorkStepInstance instance = instanceIterator.next();
    			
    			if (!uptodateInstances.contains(instance))
    				deletedInstances.add(instance);
    		}
    		/* Remove deleted Instances from the Model */
    		instanceIterator = deletedInstances.iterator();
    		while (instanceIterator.hasNext()) {
    			WorkStepInstance instance = instanceIterator.next();
    			
    			(_model.getProject()).getContent().getLayers().remove(instance);
    		}
    		
       		/* Collect list of obsolete inst. Links */
    		ArrayList<InstanceLink> obsoleteInstanceLinks = new ArrayList<InstanceLink>();
    		Iterator<InstanceLink> instanceLinkIterator = (_model.getProject()).getContent().getRefs().iterator();
    		while (instanceLinkIterator.hasNext()) {
    			InstanceLink link = instanceLinkIterator.next();
    			
    			if (deletedInstances.contains(link.getInstance()))
    				obsoleteInstanceLinks.add(link);
    		}
    		/* Remove obsolete inst. Links from the Model */
    		instanceLinkIterator = obsoleteInstanceLinks.iterator();
    		while (instanceLinkIterator.hasNext()) {
    			InstanceLink link = instanceLinkIterator.next();
    			
    			(_model.getProject()).getContent().getRefs().remove(link);
    		}
    		
       		/* Collect obsolete inst. Mappings */
    		ArrayList<InstanceMap> obsoleteInstanceMap = new ArrayList<InstanceMap>();
    		Iterator<InstanceMap> instanceMapIterator = (_model.getProject()).getContent().getInstanceMaps().iterator();
    		while (instanceMapIterator.hasNext()) {
    			InstanceMap map = instanceMapIterator.next();
    			
    			if (obsoleteInstanceLinks.contains(map.getSourceInstance()))
    				obsoleteInstanceMap.add(map);
    			
    			if (obsoleteInstanceLinks.contains(map.getTargetInstance()))
    				map.setTargetInstance(null);
    		}
    		/* remove obsolete inst. Mappings from the Model */
    		instanceMapIterator = obsoleteInstanceMap.iterator();
    		while (instanceMapIterator.hasNext()) {
    			InstanceMap map = instanceMapIterator.next();
    			
    			(_model.getProject()).getContent().removeInstanceMap(map);
    		}
    		
       		/* Collect obsolete attr. Mappings */
    		ArrayList<AttributeMap> obsoleteAttrMap = new ArrayList<AttributeMap>();
    		Iterator<AttributeMap> attrMapIterator = (_model.getProject()).getContent().getMaps().iterator();
    		while (attrMapIterator.hasNext()) {
    			AttributeMap map = attrMapIterator.next();
    			
    			if (obsoleteInstanceMap.contains(map.getBelongsTo())) 
    				obsoleteAttrMap.add(map);
    		}
    		/* remove obsolete attr. Mappings from the Model */
    		attrMapIterator = obsoleteAttrMap.iterator();
    		while (attrMapIterator.hasNext()) {
    			AttributeMap map = attrMapIterator.next();
    			
    			(_model.getProject()).getContent().removeMap(map);
    		}
    		
      		/* Collect obsolete char. Mappings */
    		ArrayList<CharacteristicMap> obsoleteCharMap = new ArrayList<CharacteristicMap>();
    		Iterator<CharacteristicMap> charMapIterator = (_model.getProject()).getContent().getCharacteristicMaps().iterator();
    		while (charMapIterator.hasNext()) {
    			CharacteristicMap map = charMapIterator.next();
    			
    			if (obsoleteAttrMap.contains(map.getBelongsTo()))
    				obsoleteCharMap.add(map);
    		}
       		/* remove obsolete char. Mappings from the Model */
    		charMapIterator = obsoleteCharMap.iterator();
    		while (charMapIterator.hasNext()) {
    			CharacteristicMap map = charMapIterator.next();
    			
    			(_model.getProject()).getContent().removeCharacteristicMap(map);
    		}
    		
    		/* Collect list of obsolete attr. Links */
    		ArrayList<AttributeLink> obsoleteAttrLinks = new ArrayList<AttributeLink>();
    		Iterator<AttributeLink> attrLinkIterator = (_model.getProject()).getContent().getLinks().iterator();
    		while (attrLinkIterator.hasNext()) {
    			AttributeLink link = attrLinkIterator.next();
    			
    			if (deletedInstances.contains(link.getInstance()))
    				obsoleteAttrLinks.add(link);
    		}
    		/* Remove obsolete attr. Links from the Model */
    		attrLinkIterator = obsoleteAttrLinks.iterator();
    		while (attrLinkIterator.hasNext()) {
    			AttributeLink link = attrLinkIterator.next();
    			
    			(_model.getProject()).getContent().getLinks().remove(link);
    		}
    	}
	}
	
	/**
	 * @param tab
	 * @return
	 */
	private boolean validateIndicatorTab(Tab tab) {
		HashMap<String, Component> tabHashMap = tab.buildHashMap();
		boolean isValid = true;
		
		if (tabHashMap.containsKey("indIndicatorTbl")) {
			JTable resultTable = ((JTable)(tabHashMap.get("indIndicatorTbl")));
			
			ArrayList<WorkStepInstance> usedInstances = new ArrayList<WorkStepInstance>();
			TableModel resultModel = resultTable.getModel();
			for (int i=0; i<resultModel.getRowCount(); i++) {
				if (resultModel.getValueAt(i, 6) instanceof WorkStepInstance) {
					WorkStepInstance	instance	= (WorkStepInstance)resultModel.getValueAt(i, 6);
					
					usedInstances.add(instance);
				}	
			}
			
			Iterator<WorkStepInstance> instance_it = _model.getProject().getContent().getLayers(InstanceType.OPERATIONAL).iterator();
			while (instance_it.hasNext()) {
				WorkStepInstance instance = instance_it.next();
				
				if (!usedInstances.contains(instance)) {
					ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, _view.getLocale());
					JOptionPane.showMessageDialog(_view.getAppFrame(), bundle.getString(MISSING_INPUT)); 
					
					isValid = false;
				}
			}
		}
		
		return isValid;
	}
	
	/**
	 * @param tab
	 */
	private void handleIndicatorTab(Tab tab) {
		HashMap<String, Component> tabHashMap = tab.buildHashMap(); 
		
	   	if (tabHashMap.containsKey("indIndicatorTbl")) {
	   		JTable resultTable = ((JTable)(tabHashMap.get("indIndicatorTbl")));
	   		
    		ArrayList<OperaIndicator> uptodateIndicators = new ArrayList<OperaIndicator>();
    		TableModel resultModel = resultTable.getModel();
    		
    		for (int i=0; i<resultModel.getRowCount(); i++) {    			
    			int 				id			= (Integer)resultModel.getValueAt(i, 0);
    			String 				label		= (String)resultModel.getValueAt(i, 2);
    			JComboBox 			level		= (JComboBox)resultModel.getValueAt(i, 3);
    			String 				definition	= (String)resultModel.getValueAt(i, 4);    			
    			JComboBox 			type		= (JComboBox)resultModel.getValueAt(i, 5);
    			WorkStepInstance	instance	= (WorkStepInstance)resultModel.getValueAt(i, 6); 
    			
    			OperaIndicator ind = null;
    			if (id == -1) {
    				ind = new OperaIndicator(generateNegID());
    				
      				AttributeLink link = new AttributeLink();
      				link.setEntityID(generateNegID());	
    				link.setAttribute(ind);
    				link.setAttributeLinkType(AttributeLinkType.INDICATOR);
    				link.setInstance(instance);
    				
    				AttributeMap map = new AttributeMap();
    				map.setEntityID(generateNegID());
    				map.setAttributeMapType(AttributeMapType.ASSIGNED_INDICATOR);
    				map.setSourceAttribute(link);
    				
    				(_model.getProject()).getContent().addIndicator(ind);
    				(_model.getProject()).getContent().addLink(link);
    				(_model.getProject()).getContent().addMap(map);
      				
    			}else {
    				ind = (_model.getProject()).getContent().getIndicatorByID(id);   
    			}
    			
    			if (ind != null) {
        			ind.setLabel(label);
        			ind.getDefinition().setText(definition);
        			ind.setLevel((TypeOfData)level.getSelectedItem());
        			ind.setType((MeasurementLevel)type.getSelectedItem());
        			
        			uptodateIndicators.add(ind);   				
    			}
    		}
    		
      		/* Collect list of deleted Indicators */
    		ArrayList<OperaIndicator> deletedIndicators = new ArrayList<OperaIndicator>();    		
    		Iterator<OperaIndicator> indIterator = (_model.getProject()).getContent().getIndicators().iterator();
    		while (indIterator.hasNext()) {
    			OperaIndicator ind = indIterator.next();
    			
    			if (!uptodateIndicators.contains(ind))
    				deletedIndicators.add(ind);
    		}
    		/* Remove deleted Indicators from the Model */
    		indIterator = deletedIndicators.iterator();
    		while (indIterator.hasNext()) {
    			OperaIndicator ind = indIterator.next();
    			
    			(_model.getProject()).getContent().getIndicators().remove(ind);
    		}
    		
      		/* Collect list of obsolete attr. Links */
    		ArrayList<AttributeLink> obsoleteAttrLinks = new ArrayList<AttributeLink>();
    		Iterator<AttributeLink> attrLinkIterator = (_model.getProject()).getContent().getLinks().iterator();
    		while (attrLinkIterator.hasNext()) {
    			AttributeLink link = attrLinkIterator.next();
    			
    			if (deletedIndicators.contains(link.getAttribute()))
    				obsoleteAttrLinks.add(link);
    		}
    		/* Remove obsolete attr. Links from the Model */
    		attrLinkIterator = obsoleteAttrLinks.iterator();
    		while (attrLinkIterator.hasNext()) {
    			AttributeLink link = attrLinkIterator.next();
    			
    			(_model.getProject()).getContent().getLinks().remove(link);
    		}
    		
    		/* Collect obsolete attr. Mappings */
    		ArrayList<AttributeMap> obsoleteAttrMap = new ArrayList<AttributeMap>();
    		Iterator<AttributeMap> attrMapIterator = (_model.getProject()).getContent().getMaps().iterator();
    		while (attrMapIterator.hasNext()) {
    			AttributeMap map = attrMapIterator.next();
    			
    			if (obsoleteAttrLinks.contains(map.getSourceAttribute()))
    				obsoleteAttrMap.add(map);
    			
    			if (obsoleteAttrLinks.contains(map.getTargetAttribute()))
    				map.setTargetAttribute(null);
    		}
    		/* remove obsolete attr. Mappings from the Model */
    		attrMapIterator = obsoleteAttrMap.iterator();
    		while (attrMapIterator.hasNext()) {
    			AttributeMap map = attrMapIterator.next();

    			(_model.getProject()).getContent().removeMap(map);
    		}
    		
       		/* Collect obsolete attr. Comparisons */
    		ArrayList<AttributeComp> obsoleteAttrComp = new ArrayList<AttributeComp>();
    		Iterator<AttributeComp> attrCompIterator = (_model.getProject()).getContent().getComps().iterator();
    		while (attrCompIterator.hasNext()) {
    			AttributeComp comp = attrCompIterator.next();
    			
    			if ((obsoleteAttrLinks.contains(comp.getSourceAttribute())) ||
    					(obsoleteAttrLinks.contains(comp.getTargetAttribute())))
    				obsoleteAttrComp.add(comp);
    		}
    		/* remove obsolete attr. Comparisons from the Model */
    		attrCompIterator = obsoleteAttrComp.iterator();
    		while (attrCompIterator.hasNext()) {
    			AttributeComp comp = attrCompIterator.next();
    			
    			(_model.getProject()).getContent().removeComp(comp);
    		}
    		
       		/* Collect list of obsolete char. Links */
    		ArrayList<CharacteristicLink> obsoleteCharLinks = new ArrayList<CharacteristicLink>();
    		Iterator<CharacteristicLink> charLinkIterator = (_model.getProject()).getContent().getCharacteristicLinks().iterator();
    		while (charLinkIterator.hasNext()) {
    			CharacteristicLink link = charLinkIterator.next();
    			
    			if (deletedIndicators.contains(link.getAttribute()))
    				obsoleteCharLinks.add(link);
    		}
    		/* Remove obsolete char. Links from the Model */
    		charLinkIterator = obsoleteCharLinks.iterator();
    		while (charLinkIterator.hasNext()) {
    			CharacteristicLink link = charLinkIterator.next();
    			
    			(_model.getProject()).getContent().getCharacteristicLinks().remove(link);
    		}
    		
    		/* Collect obsolete char. Mappings */
    		ArrayList<CharacteristicMap> obsoleteCharMap = new ArrayList<CharacteristicMap>();
    		Iterator<CharacteristicMap> charMapIterator = (_model.getProject()).getContent().getCharacteristicMaps().iterator();
    		while (charMapIterator.hasNext()) {
    			CharacteristicMap map = charMapIterator.next();
    			
    			if (obsoleteCharLinks.contains(map.getSourceCharacteristic()))
    				obsoleteCharMap.add(map);
    			
    			if (obsoleteAttrLinks.contains(map.getTargetCharacteristic()))
    				map.setTargetCharacteristic(null);
    		}
       		/* remove obsolete char. Mappings from the Model */
    		charMapIterator = obsoleteCharMap.iterator();
    		while (charMapIterator.hasNext()) {
    			CharacteristicMap map = charMapIterator.next();
    			
    			(_model.getProject()).getContent().removeCharacteristicMap(map);
    		}   
    	}
	}
	
	/**
	 * @param tab
	 * @return
	 */
	private boolean validatePrescriptionsTab(Tab tab) {
		HashMap<String, Component> tabHashMap = tab.buildHashMap();
		boolean isValid = true;
		
		if (tabHashMap.containsKey("prePrescriptionTbl")) {
			JTable resultTable = ((JTable)(tabHashMap.get("prePrescriptionTbl")));
			
			ArrayList<OperaIndicator> usedIndicators = new ArrayList<OperaIndicator>();
			TableModel resultModel = resultTable.getModel();
			for (int i=0; i<resultModel.getRowCount(); i++) {
				if (resultModel.getValueAt(i, 4) instanceof OperaIndicator) {
					OperaIndicator	indicator	= (OperaIndicator)resultModel.getValueAt(i, 4);
					
					usedIndicators.add(indicator);
				}	
			}
			
			Iterator<OperaIndicator> indicator_it = _model.getProject().getContent().getIndicators().iterator();
			while (indicator_it.hasNext()) {
				OperaIndicator indicator = indicator_it.next();
				
				if (!usedIndicators.contains(indicator)) {
					ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, _view.getLocale());
					JOptionPane.showMessageDialog(_view.getAppFrame(), bundle.getString(MISSING_INPUT)); 
					
					isValid = false;
				}
			}
		}
		
		return isValid;
	}
	
	/**
	 * @param tab
	 */
	private void handlePrescriptionsTab(Tab tab) {
		HashMap<String, Component> tabHashMap = tab.buildHashMap(); 

    	if (tabHashMap.containsKey("prePrescriptionTbl")) {
    		JTable resultTable = ((JTable)(tabHashMap.get("prePrescriptionTbl")));
    		
    		ArrayList<OperaPrescription> uptodatePrescriptions = new ArrayList<OperaPrescription>();
    		TableModel resultModel = resultTable.getModel();
    		for (int i=0; i<resultModel.getRowCount(); i++) {
       			int 			id		= (Integer)resultModel.getValueAt(i, 0);
       			String			value	= (String)resultModel.getValueAt(i, 2);
    			String			label	= (String)resultModel.getValueAt(i, 3); 
    			OperaIndicator	ind		= (OperaIndicator)resultModel.getValueAt(i, 4); 
    			
    			OperaPrescription pres = null;
    			if (id == -1) {
    				pres = new OperaPrescription(generateNegID());
    				
    				CharacteristicLink link = new CharacteristicLink();
       				link.setEntityID(generateNegID());
    				link.setAttribute(ind);
    				link.setType(CharacteristicLinkType.PRESCRIPTION);
    				link.setCharacteristic(pres);
    				
    				CharacteristicMap map = new CharacteristicMap();
       				map.setEntityID(generateNegID());
    				map.setType(CharacteristicMapType.PRESCRIPTION);
    				map.setSourceCharacteristic(link);
    				
    				(_model.getProject()).getContent().addPrescription(pres);
    				(_model.getProject()).getContent().addCharacteristicLink(link);
    				(_model.getProject()).getContent().addCharacteristicMap(map);
    			} else {
    				pres = (_model.getProject()).getContent().getPrescriptionByID(id);    				
    			}
    			if (pres != null) {
    				pres.setValue(value);
	    			pres.setLabel(label);
	    			
	    			uptodatePrescriptions.add(pres);
    			}
    		}
    		
       		/* Collect list of deleted Prescriptions */
    		ArrayList<OperaPrescription> deletedPrescriptions = new ArrayList<OperaPrescription>();    		
    		Iterator<OperaPrescription> presIterator = (_model.getProject()).getContent().getPrescriptions().iterator();
    		while (presIterator.hasNext()) {
    			OperaPrescription pres = presIterator.next();
    			
    			if (!uptodatePrescriptions.contains(pres))
    				deletedPrescriptions.add(pres);
    		}
    		/* Remove deleted Prescriptions from the Model */
    		presIterator = deletedPrescriptions.iterator();
    		while (presIterator.hasNext()) {
    			OperaPrescription pres = presIterator.next();
    			
    			(_model.getProject()).getContent().getPrescriptions().remove(pres);
    		}
    		
       		/* Collect list of obsolete char. Links */
    		ArrayList<CharacteristicLink> obsoleteCharLinks = new ArrayList<CharacteristicLink>();
    		Iterator<CharacteristicLink> charLinkIterator = (_model.getProject()).getContent().getCharacteristicLinks().iterator();
    		while (charLinkIterator.hasNext()) {
    			CharacteristicLink link = charLinkIterator.next();
    			
    			if (deletedPrescriptions.contains(link.getCharacteristic()))
    				obsoleteCharLinks.add(link);
    		}
    		/* Remove obsolete char. Links from the Model */
    		charLinkIterator = obsoleteCharLinks.iterator();
    		while (charLinkIterator.hasNext()) {
    			CharacteristicLink link = charLinkIterator.next();
    			
    			(_model.getProject()).getContent().getCharacteristicLinks().remove(link);
    		}
    		
       		/* Collect obsolete char. Mappings */
    		ArrayList<CharacteristicMap> obsoleteCharMap = new ArrayList<CharacteristicMap>();
    		Iterator<CharacteristicMap> charMapIterator = (_model.getProject()).getContent().getCharacteristicMaps().iterator();
    		while (charMapIterator.hasNext()) {
    			CharacteristicMap map = charMapIterator.next();
    			
    			if (obsoleteCharLinks.contains(map.getSourceCharacteristic()))
    				obsoleteCharMap.add(map);
    			
    			if (obsoleteCharLinks.contains(map.getTargetCharacteristic()))
    				map.setTargetCharacteristic(null);
    		}
       		/* remove obsolete char. Mappings from the Model */
    		charMapIterator = obsoleteCharMap.iterator();
    		while (charMapIterator.hasNext()) {
    			CharacteristicMap map = charMapIterator.next();
    			
    			(_model.getProject()).getContent().removeCharacteristicMap(map);
    		}
    	}
	}
	
	/**
	 * @param tab
	 * @return
	 */
	private boolean validateMapIndicatorAttributeTab(Tab tab) {
		HashMap<String, Component> tabHashMap = tab.buildHashMap();
		boolean isValid = true;
		
		if (tabHashMap.containsKey("mapTable")) {
			JTable resultTable = ((JTable)(tabHashMap.get("mapTable")));
			
			TableModel resultModel = resultTable.getModel();
			for (int i=0; i<resultModel.getRowCount(); i++) {
				JComboBox			target	= (JComboBox)resultModel.getValueAt(i, 2);	
				
				if (!(target.getSelectedItem() instanceof Attributes)) {
					ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, _view.getLocale());
					JOptionPane.showMessageDialog(_view.getAppFrame(), bundle.getString(MISSING_INPUT)); 
					
					isValid = false;
				}
			}
		}
		
		return isValid;
	}
	
	/**
	 * @param tab
	 */
	private void handleMapIndicatorAttributeTab(Tab tab) {
		HashMap<String, Component> tabHashMap = tab.buildHashMap(); 
		
		if (tabHashMap.containsKey("mapTable")) {
			JTable resultTable = ((JTable)(tabHashMap.get("mapTable")));
			
	   		ArrayList<AttributeMap> uptodateAttributeMaps = new ArrayList<AttributeMap>();
			TableModel resultModel = resultTable.getModel();
			for (int i=0; i<resultModel.getRowCount(); i++) {
	   			int 				id		= (Integer)resultModel.getValueAt(i, 0);
				OperaIndicator		source	= (OperaIndicator)resultModel.getValueAt(i, 3);	
				JComboBox			target	= (JComboBox)resultModel.getValueAt(i, 2);	
				
				if (target.getSelectedItem() instanceof Attributes) {					
					AttributeMap map = null;
					if (id == -1) {
						map = new AttributeMap(generateNegID());
						
						(_model.getProject()).getContent().addMap(map);
					} else {
						map = (_model.getProject()).getContent().getMapByID(id);    				
					}
					if (map != null) {
						map.setSourceAttribute((_model.getProject()).getContent().getLinkByAttribute(source));
						map.setTargetAttribute((_model.getProject()).getContent().getLinkByAttribute((Attributes)target.getSelectedItem()));
						map.setAttributeMapType(AttributeMapType.ASSIGNED_INDICATOR);
											
						map.setBelongsTo( 
								(_model.getProject()).getContent().getInstanceMapByInstance(
										(_model.getProject()).getContent().getRefByInstance(
												((AttributeMap)resultModel.getValueAt(i, 1)).getSourceAttribute().getInstance()
										) 
								) 
						);
						
		    			uptodateAttributeMaps.add(map);
					}
				}
			}
			
    		Iterator<CharacteristicMap> charMapsIter = (_model.getProject()).getContent().getCharacteristicMaps().iterator();
    		while (charMapsIter.hasNext()) {
    			CharacteristicMap charMap = charMapsIter.next();
    			
    			CharacteristicLink charLink = charMap.getSourceCharacteristic();
    			Attributes attr = charLink.getAttribute();
    			AttributeLink attrLink = (_model.getProject()).getContent().getLinkByAttribute(attr);
    			AttributeMap attrMap = (_model.getProject()).getContent().getAttributeMapByAttribute(attrLink);
    			
    			charMap.setBelongsTo(attrMap);
    		}
    		
       		/* Collect list of deleted Mappings */
    		ArrayList<AttributeMap> deletedAttributeMaps = new ArrayList<AttributeMap>();    		
    		Iterator<AttributeMap> attrMapIterator = (_model.getProject()).getContent().getMapsByType(AttributeMapType.ASSIGNED_INDICATOR).iterator();
    		while (attrMapIterator.hasNext()) {
    			AttributeMap map = attrMapIterator.next();
    			
    			if (!uptodateAttributeMaps.contains(map))
    				deletedAttributeMaps.add(map);
    		}
    		/* Remove deleted Mappings from the Model */
    		attrMapIterator = deletedAttributeMaps.iterator();
    		while (attrMapIterator.hasNext()) {
    			AttributeMap map = attrMapIterator.next();
    			
    			(_model.getProject()).getContent().removeMap(map);
    		}
    		
    		/* Collect list of obsolete Char Mappings */
    		ArrayList<CharacteristicMap> obsoleteCharMaps = new ArrayList<CharacteristicMap>();    		
    		Iterator<CharacteristicMap> charMapIterator = (_model.getProject()).getContent().getCharacteristicMaps().iterator();
    		while (charMapIterator.hasNext()) {
    			CharacteristicMap map = charMapIterator.next();
    			
    			if (deletedAttributeMaps.contains(map.getBelongsTo()))
    				obsoleteCharMaps.add(map);
    		}
    		/* Remove obsolete Mappings from the Model */
    		charMapIterator = obsoleteCharMaps.iterator();
    		while (charMapIterator.hasNext()) {
    			CharacteristicMap map = charMapIterator.next();
    			
       			(_model.getProject()).getContent().removeCharacteristicMap(map);
        	}
			
		}
	}

	/**
	 * @param tab
	 * @return
	 */
	private boolean validateMapIndicatorCharTab(Tab tab) {
		HashMap<String, Component> tabHashMap = tab.buildHashMap();
		boolean isValid = true;
		
		if (tabHashMap.containsKey("recodeTable")) {
			JTable resultTable = ((JTable)(tabHashMap.get("recodeTable")));
			
			TableModel resultModel = resultTable.getModel();
			for (int i=0; i<resultModel.getRowCount(); i++) {
				JComboBox			target	= (JComboBox)resultModel.getValueAt(i, 2); 
				
				if (!(target.getSelectedItem() instanceof Characteristics)) {
					ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, _view.getLocale());
					JOptionPane.showMessageDialog(_view.getAppFrame(), bundle.getString(MISSING_INPUT)); 
					
					isValid = false;
				}
			}
		}
		
		return isValid;
	}
	
	/**
	 * @param tab
	 */
	private void handleMapIndicatorCharTab(Tab tab) {
		HashMap<String, Component> tabHashMap = tab.buildHashMap(); 
		
		if (tabHashMap.containsKey("recodeTable")) {
			JTable resultTable = ((JTable)(tabHashMap.get("recodeTable")));
			
	   		ArrayList<CharacteristicMap> uptodateCharMaps = new ArrayList<CharacteristicMap>();
			TableModel resultModel = resultTable.getModel();
			for (int i=0; i<resultModel.getRowCount(); i++) {
	   			int 				id		= (Integer)resultModel.getValueAt(i, 0);
				OperaPrescription	source	= (OperaPrescription)resultModel.getValueAt(i, 3); 
				JComboBox			target	= (JComboBox)resultModel.getValueAt(i, 2);	
				
				
				if (target.getSelectedItem() instanceof Characteristics) {
					CharacteristicMap map = null;
					if (id == -1) {
						map = new CharacteristicMap(generateNegID());
						
						(_model.getProject()).getContent().addCharacteristicMap(map);
					} else {
						map = (_model.getProject()).getContent().getCharacteristicMapByID(id);    				
					}
					if (map != null) {
						map.setSourceCharacteristic((_model.getProject()).getContent().getCharacteristicLinkByCharacteristic(source));
						map.setTargetCharacteristic((_model.getProject()).getContent().getCharacteristicLinkByCharacteristic((Characteristics)target.getSelectedItem()));
						map.setType(CharacteristicMapType.PRESCRIPTION); 
											
						map.setBelongsTo( 
								(_model.getProject()).getContent().getAttributeMapByAttribute(
										(_model.getProject()).getContent().getLinkByAttribute(
												((CharacteristicMap)resultModel.getValueAt(i, 1)).getSourceCharacteristic().getAttribute()
										) 
								) 
						);
						
		    			uptodateCharMaps.add(map);
					}
				}
			}
			
       		/* Collect list of deleted Mappings */
    		ArrayList<CharacteristicMap> deletedCharacteristicMaps = new ArrayList<CharacteristicMap>();    		
    		Iterator<CharacteristicMap> charMapIterator = (_model.getProject()).getContent().getCharacteristicMapsByType(CharacteristicMapType.PRESCRIPTION).iterator();
    		while (charMapIterator.hasNext()) {
    			CharacteristicMap map = charMapIterator.next();
    			
    			if (!uptodateCharMaps.contains(map))
    				deletedCharacteristicMaps.add(map);
    		}
    		/* Remove deleted Mappings from the Model */
    		charMapIterator = deletedCharacteristicMaps.iterator();
    		while (charMapIterator.hasNext()) {
    			CharacteristicMap map = charMapIterator.next();
    			
    			(_model.getProject()).getContent().removeCharacteristicMap(map);
    		}
		}
	}
	
	/**
	 * @param tab
	 * @return
	 */
	private boolean validateMapIndicatorInstanceTab(Tab tab) {
		HashMap<String, Component> tabHashMap = tab.buildHashMap();
		boolean isValid = true;
		
		if (tabHashMap.containsKey("selectTable")) {
			JTable resultTable = ((JTable)(tabHashMap.get("selectTable")));
			
			TableModel resultModel = resultTable.getModel();
			for (int i=0; i<resultModel.getRowCount(); i++) {
				JComboBox			target	= (JComboBox)resultModel.getValueAt(i, 2); 
				
				if (!(target.getSelectedItem() instanceof WorkStepInstance)) {
					ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, _view.getLocale());
					JOptionPane.showMessageDialog(_view.getAppFrame(), bundle.getString(MISSING_INPUT)); 
					
					isValid = false;
				}
			}
		}
		
		return isValid;
	}
	
	/**
	 * @param tab
	 */
	private void handleMapIndicatorInstanceTab(Tab tab) {
		HashMap<String, Component> tabHashMap = tab.buildHashMap(); 
		
		if (tabHashMap.containsKey("selectTable")) {
    		JTable resultTable = ((JTable)(tabHashMap.get("selectTable")));
    		
    		ArrayList<InstanceMap> uptodateInstanceMaps = new ArrayList<InstanceMap>();
    		TableModel resultModel = resultTable.getModel();
    		for (int i=0; i<resultModel.getRowCount(); i++) {
       			int 				id		= (Integer)resultModel.getValueAt(i, 0);
    			WorkStepInstance	source	= (WorkStepInstance)resultModel.getValueAt(i, 3);	
    			JComboBox			target	= (JComboBox)resultModel.getValueAt(i, 2);	
    			
    			InstanceMap map = null;
    			if (id == -1) {
    				map = new InstanceMap(generateNegID());
    				map.setBelongsTo(_model.getProject());
    				
    				(_model.getProject()).getContent().addInstanceMap(map);
    			} else {
    				map = (_model.getProject()).getContent().getInstanceMapByID(id);
    			}
    			if ((map != null) &&
    					(target.getSelectedItem() instanceof WorkStepInstance)) { 
    				map.setSourceInstance((_model.getProject()).getContent().getRefByInstance(source));
    				map.setTargetInstance((_model.getProject()).getContent().getRefByInstance((WorkStepInstance)target.getSelectedItem()));
    				map.setType(InstanceMapType.OPERATIONAL);
    				map.setBelongsTo(_model.getProject());
    				
	    			uptodateInstanceMaps.add(map);
    			}
    		}
    		
    		Iterator<AttributeMap> attrMapsIter = (_model.getProject()).getContent().getMaps().iterator();
    		while (attrMapsIter.hasNext()) {
    			AttributeMap attrMap = attrMapsIter.next();
    			
    			AttributeLink attrLink = attrMap.getSourceAttribute();
    			WorkStepInstance inst = attrLink.getInstance();
    			InstanceLink instLink = (_model.getProject()).getContent().getRefByInstance(inst);
    			InstanceMap instMap = (_model.getProject()).getContent().getInstanceMapByInstance(instLink);
    			
    			attrMap.setBelongsTo(instMap);
    		}
    		
    		/* Collect list of deleted Mappings */
    		ArrayList<InstanceMap> deletedInstanceMaps = new ArrayList<InstanceMap>();    		
    		Iterator<InstanceMap> instMapIterator = (_model.getProject()).getContent().getInstanceMapsByType(InstanceMapType.OPERATIONAL).iterator();
    		while (instMapIterator.hasNext()) {
    			InstanceMap map = instMapIterator.next();
    			
    			if (!uptodateInstanceMaps.contains(map))
    				deletedInstanceMaps.add(map);
    		}
    		/* Remove deleted Mappings from the Model */
    		instMapIterator = deletedInstanceMaps.iterator();
    		while (instMapIterator.hasNext()) {
    			InstanceMap map = instMapIterator.next();
    			
    			(_model.getProject()).getContent().removeInstanceMap(map);
    		}
    		
      		/* Collect list of obsolete Attribute Mappings */
    		ArrayList<AttributeMap> obsoleteAttributeMaps = new ArrayList<AttributeMap>();    		
    		Iterator<AttributeMap> attrMapIterator = (_model.getProject()).getContent().getMaps().iterator();
    		while (attrMapIterator.hasNext()) {
    			AttributeMap map = attrMapIterator.next();
    			
    			if (deletedInstanceMaps.contains(map.getBelongsTo()))
    				obsoleteAttributeMaps.add(map);
    		}
    		/* Remove obsolete Mappings from the Model */
    		attrMapIterator = obsoleteAttributeMaps.iterator();
    		while (attrMapIterator.hasNext()) {
    			AttributeMap map = attrMapIterator.next();
    			
    			(_model.getProject()).getContent().removeMap(map);
    		}
    		
      		/* Collect list of obsolete Char Mappings */
    		ArrayList<CharacteristicMap> obsoleteCharMaps = new ArrayList<CharacteristicMap>();    		
    		Iterator<CharacteristicMap> charMapIterator = (_model.getProject()).getContent().getCharacteristicMaps().iterator();
    		while (charMapIterator.hasNext()) {
    			CharacteristicMap map = charMapIterator.next();
    			
    			if (obsoleteAttributeMaps.contains(map.getBelongsTo()))
    				obsoleteCharMaps.add(map);
    		}
    		/* Remove obsolete Mappings from the Model */
    		charMapIterator = obsoleteCharMaps.iterator();
    		while (charMapIterator.hasNext()) {
    			CharacteristicMap map = charMapIterator.next();
    			
       			(_model.getProject()).getContent().removeCharacteristicMap(map);
        	}   
		}
	}
	
	/**
	 * @param tab
	 * @return
	 */
	private boolean validateSearchVariableTab(Tab tab) {
		return true;
	}
	
	/**
	 * @param tab
	 */
	private void handleSearchVariableTab(Tab tab) { 
		/* Pro only */
	}
	
	/**
	 * @param tab
	 * @return
	 */
	private boolean validateCompareVariablesTab(Tab tab) {
		return true;
	}
	
	/**
	 * @param tab
	 */
	private void handleCompareVariablesTab(Tab tab) {
		HashMap<String, Component> tabHashMap = tab.buildHashMap(); 
		
		if (tabHashMap.containsKey("BiasTableData")) {
			JTable table = (JTable)tabHashMap.get("BiasTableData");
			
			DefaultTableModel biasTableModel = (DefaultTableModel)table.getModel();			
			if (biasTableModel.getRowCount() > 0) {
				for (int i=0; i<biasTableModel.getRowCount(); i++) {
					BiasMetadata metadata	= ((AttributeComp)biasTableModel.getValueAt(i, BiasTable.OBJECT_COLUMN)).getBiasMetadata();
					JCheckBox inverse 		= (JCheckBox)biasTableModel.getValueAt(i, BiasTable.INVERSE_COLUMN);
					JCheckBox difference 	= (JCheckBox)biasTableModel.getValueAt(i, BiasTable.DIFF_COLUMN);
					JComboBox rating 		= (JComboBox)biasTableModel.getValueAt(i, BiasTable.BIAS_RATING_COLUMN);
					JTextArea missings 		= (JTextArea)biasTableModel.getValueAt(i, BiasTable.MISSINGS_COLUMN);
					JComboBox stand_coding 	= (JComboBox)biasTableModel.getValueAt(i, BiasTable.C_PROC_COLUMN);
					JComboBox preference 	= (JComboBox)biasTableModel.getValueAt(i, BiasTable.PREFERENCE_COLUMN);
					
					metadata.setInverse(inverse.isSelected());
					metadata.setDifference(difference.isSelected());
					metadata.setRating((BiasRatingType)rating.getSelectedItem());
					metadata.setMissings(missings.getText());
					metadata.setStandCoding((BiasStandCodingType)stand_coding.getSelectedItem());
					metadata.setPreference((BiasPreferenceType)preference.getSelectedItem());
				}
			}
		}
	}
	
	/**
	 * @param tab
	 * @return
	 */
	private boolean validateCompareValuesTab(Tab tab) {
		return true;
	}
	
	/**
	 * @param tab
	 */
	private void handleCompareValuesTab(Tab tab) {
		/* DoNothing, CompareValuesTab is only a view */
	}
	
	/**
	 * @param tab
	 * @return
	 */
	private boolean validateDRInstanceTab(Tab tab) {
		HashMap<String, Component> tabHashMap = tab.buildHashMap();
		boolean isValid = true;
		
		if (tabHashMap.containsKey("driInstanceTbl")) {
			JTable resultTable = ((JTable)(tabHashMap.get("driInstanceTbl")));
			
			if (resultTable.getModel().getRowCount() < 1) {
				ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, _view.getLocale());
				JOptionPane.showMessageDialog(_view.getAppFrame(), bundle.getString(MISSING_INPUT)); 
					
				isValid = false;
			}
			
			if (resultTable.getModel().getRowCount() > 0) {
				for (int i=0; i<resultTable.getModel().getRowCount(); i++) {
	    			String	label	= (String)resultTable.getModel().getValueAt(i, 2);
	    			
	    			if (label.isEmpty()) {
	    				ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, _view.getLocale());
	    				JOptionPane.showMessageDialog(_view.getAppFrame(), bundle.getString(MISSING_INPUT)); 
	    					
	    				isValid = false;	    				
	    			}    			
				}
			}
		}
		
		return isValid;
	}
	
	/**
	 * @param tab
	 */
	private void handleDRInstanceTab(Tab tab) {
		HashMap<String, Component> tabHashMap = tab.buildHashMap(); 

	   	if (tabHashMap.containsKey("driInstanceTbl")) {
	   		JTable resultTable = ((JTable)(tabHashMap.get("driInstanceTbl")));
	   		
    		ArrayList<WorkStepInstance> uptodateInstances = new ArrayList<WorkStepInstance>();
    		TableModel resultModel = resultTable.getModel();
    		for (int i=0; i<resultModel.getRowCount(); i++) {
       			int 			id		= (Integer)resultModel.getValueAt(i, 0);
    			String			label	= (String)resultModel.getValueAt(i, 2);
    			
       			WorkStepInstance instance = null;
    			if (id == -1) {
    				instance = new WorkStepInstance(generateNegID());
    				
    				InstanceLink link = new InstanceLink();
    				link.setEntityID(generateNegID());
    				link.setProject(_model.getProject());
    				link.setInstance(instance);
    				link.setWorkStepRefType(InstanceLinkType.DATA_CODING);
    				
    				InstanceMap map = new InstanceMap();
    				map.setEntityID(generateNegID());
    				map.setSourceInstance(link);
    				map.setType(InstanceMapType.DATA_RECODING);
    				map.setBelongsTo(_model.getProject());
    				
    				(_model.getProject()).getContent().addLayer(instance);
    				(_model.getProject()).getContent().addInstanceRef(link);
    				(_model.getProject()).getContent().addInstanceMap(map);
    			} else {
    				instance = (_model.getProject()).getContent().getLayerByID(id);    				
    			}
    			if (instance != null) {
	    			instance.setLabel(label);
	    			instance.setType(InstanceType.DATA_CODING);
	    			
	    			uptodateInstances.add(instance);
    			}

    		}
    		
     		/* Collect list of deleted Instances */
    		ArrayList<WorkStepInstance> deletedInstances = new ArrayList<WorkStepInstance>();    		
    		Iterator<WorkStepInstance> instanceIterator = (_model.getProject()).getContent().getLayers(InstanceType.DATA_CODING).iterator();
    		while (instanceIterator.hasNext()) {
    			WorkStepInstance instance = instanceIterator.next();
    			
    			if (!uptodateInstances.contains(instance))
    				deletedInstances.add(instance);
    		}
    		/* Remove deleted Instances from the Model */
    		instanceIterator = deletedInstances.iterator();
    		while (instanceIterator.hasNext()) {
    			WorkStepInstance instance = instanceIterator.next();
    			
    			(_model.getProject()).getContent().removeLayer(instance);
    			_model.getProject().getContent().addToRecycleBin(instance); 
    		}

       		/* Collect list of obsolete inst. Links */
    		ArrayList<InstanceLink> obsoleteInstanceLinks = new ArrayList<InstanceLink>();
    		Iterator<InstanceLink> instanceLinkIterator = (_model.getProject()).getContent().getRefs().iterator();
    		while (instanceLinkIterator.hasNext()) {
    			InstanceLink link = instanceLinkIterator.next();
    			
    			if (deletedInstances.contains(link.getInstance()))
    				obsoleteInstanceLinks.add(link);
    		}
    		/* Remove obsolete inst. Links from the Model */
    		instanceLinkIterator = obsoleteInstanceLinks.iterator();
    		while (instanceLinkIterator.hasNext()) {
    			InstanceLink link = instanceLinkIterator.next();
    			
    			(_model.getProject()).getContent().removeRef(link);
    			_model.getProject().getContent().addToRecycleBin(link); 
    		}

       		/* Collect obsolete inst. Mappings */
    		ArrayList<InstanceMap> obsoleteInstanceMap = new ArrayList<InstanceMap>();
    		Iterator<InstanceMap> instanceMapIterator = (_model.getProject()).getContent().getInstanceMaps().iterator();
    		while (instanceMapIterator.hasNext()) {
    			InstanceMap map = instanceMapIterator.next();
    			
    			if (obsoleteInstanceLinks.contains(map.getSourceInstance()))
    				obsoleteInstanceMap.add(map);
    			
    			if (obsoleteInstanceLinks.contains(map.getTargetInstance()))
    				map.setTargetInstance(null);
    		}
    		/* remove obsolete inst. Mappings from the Model */
    		instanceMapIterator = obsoleteInstanceMap.iterator();
    		while (instanceMapIterator.hasNext()) {
    			InstanceMap map = instanceMapIterator.next();
    			
    			(_model.getProject()).getContent().removeInstanceMap(map);
    			_model.getProject().getContent().addToRecycleBin(map); 
    		}

       		/* Collect obsolete attr. Mappings */
    		ArrayList<AttributeMap> obsoleteAttrMap = new ArrayList<AttributeMap>();
    		Iterator<AttributeMap> attrMapIterator = (_model.getProject()).getContent().getMaps().iterator();
    		while (attrMapIterator.hasNext()) {
    			AttributeMap map = attrMapIterator.next();
    			
    			if (obsoleteInstanceMap.contains(map.getBelongsTo())) 
    				obsoleteAttrMap.add(map);
    		}
    		/* remove obsolete attr. Mappings from the Model */
    		attrMapIterator = obsoleteAttrMap.iterator();
    		while (attrMapIterator.hasNext()) {
    			AttributeMap map = attrMapIterator.next();
    			
    			(_model.getProject()).getContent().removeMap(map);
    			_model.getProject().getContent().addToRecycleBin(map); 
    		}
    		
      		/* Collect obsolete char. Mappings */
    		ArrayList<CharacteristicMap> obsoleteCharMap = new ArrayList<CharacteristicMap>();
    		Iterator<CharacteristicMap> charMapIterator = (_model.getProject()).getContent().getCharacteristicMaps().iterator();
    		while (charMapIterator.hasNext()) {
    			CharacteristicMap map = charMapIterator.next();
    			
    			if (obsoleteAttrMap.contains(map.getBelongsTo()))
    				obsoleteCharMap.add(map);
    		}
       		/* remove obsolete char. Mappings from the Model */
    		charMapIterator = obsoleteCharMap.iterator();
    		while (charMapIterator.hasNext()) {
    			CharacteristicMap map = charMapIterator.next();
    			
    			(_model.getProject()).getContent().removeCharacteristicMap(map);
    			_model.getProject().getContent().addToRecycleBin(map); 
    		}

    		/* Collect list of obsolete attr. Links */
    		ArrayList<AttributeLink> obsoleteAttrLinks = new ArrayList<AttributeLink>();
    		Iterator<AttributeLink> attrLinkIterator = (_model.getProject()).getContent().getLinks().iterator();
    		while (attrLinkIterator.hasNext()) {
    			AttributeLink link = attrLinkIterator.next();
    			
    			if (deletedInstances.contains(link.getInstance()))
    				obsoleteAttrLinks.add(link);
    		}
    		/* Remove obsolete attr. Links from the Model */
    		attrLinkIterator = obsoleteAttrLinks.iterator();
    		while (attrLinkIterator.hasNext()) {
    			AttributeLink link = attrLinkIterator.next();
    			
    			(_model.getProject()).getContent().removeLink(link);
    			_model.getProject().getContent().addToRecycleBin(link); 
    		}    		
	   	}
	}
	
	/**
	 * @param tab
	 * @return
	 */
	private boolean validateVariableTab(Tab tab) {
		HashMap<String, Component> tabHashMap = tab.buildHashMap();
		boolean isValid = true;
		
		if (tabHashMap.containsKey("varVariableTable")) {
			JTable resultTable = ((JTable)(tabHashMap.get("varVariableTable")));
			
			ArrayList<WorkStepInstance> usedInstances = new ArrayList<WorkStepInstance>();
			TableModel resultModel = resultTable.getModel();
			for (int i=0; i<resultModel.getRowCount(); i++) {
				if (resultModel.getValueAt(i, 8) instanceof WorkStepInstance) {
					WorkStepInstance	instance	= (WorkStepInstance)resultModel.getValueAt(i, 8);
					
					usedInstances.add(instance);
				}	
			}
			
			Iterator<WorkStepInstance> instance_it = _model.getProject().getContent().getLayers(InstanceType.DATA_CODING).iterator();
			while (instance_it.hasNext()) {
				WorkStepInstance instance = instance_it.next();
				
				if (!usedInstances.contains(instance)) {
					ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, _view.getLocale());
					JOptionPane.showMessageDialog(_view.getAppFrame(), bundle.getString(MISSING_INPUT)); 
					
					isValid = false;
				}
			}
		}
		
		return isValid;
	}
	
	/**
	 * @param tab
	 */
	private void handleVariableTab(Tab tab) {
		HashMap<String, Component> tabHashMap = tab.buildHashMap(); 

	   	if (tabHashMap.containsKey("varVariableTbl")) {
	   		JTable resultTable = ((JTable)(tabHashMap.get("varVariableTbl")));
	   		
    		ArrayList<Variable> uptodateVariables = new ArrayList<Variable>();
    		TableModel resultModel = resultTable.getModel();
    		for (int i=0; i<resultModel.getRowCount(); i++) {
    			int 				id			= (Integer)resultModel.getValueAt(i, 0);
    			String 				label		= (String)resultModel.getValueAt(i, 2);
    			String 				definition	= (String)resultModel.getValueAt(i, 5);
    			JComboBox			v_type		= (JComboBox)resultModel.getValueAt(i, 3);
    			JComboBox 			level		= (JComboBox)resultModel.getValueAt(i, 4);
    			JComboBox 			m_type		= (JComboBox)resultModel.getValueAt(i, 6);
    			WorkStepInstance	instance	= (WorkStepInstance)resultModel.getValueAt(i, 7);

    			Variable var = null;
    			if (id == -1) {
    				var = new Variable(generateNegID());
    				
      				AttributeLink link = new AttributeLink();
      				link.setEntityID(generateNegID());	
    				link.setAttribute(var);
    				link.setAttributeLinkType(AttributeLinkType.VARIABLE);
    				link.setInstance(instance);
    				
    				AttributeMap map = new AttributeMap();
    				map.setEntityID(generateNegID());
    				map.setAttributeMapType(AttributeMapType.ASSIGNED_VARIABLE);
    				map.setSourceAttribute(link);
    				
    				(_model.getProject()).getContent().addVariable(var);
    				(_model.getProject()).getContent().addLink(link);
    				(_model.getProject()).getContent().addMap(map);
    			} else {
    				var = (_model.getProject()).getContent().getVariableByID(id); 
    			}
    			
    			if (var != null) {
    				var.setLabel(label);
        			var.setType((org.gesis.charmstats.model.VariableType)(v_type.getSelectedItem()));
        			var.setLevel((TypeOfData)level.getSelectedItem());
        			var.setMeasureType((MeasurementLevel)m_type.getSelectedItem());
        			var.getDefinition().setText(definition);
        			
        			uptodateVariables.add(var);
    			}
    		}
    		
      		/* Collect list of deleted Indicators */
    		ArrayList<Variable> deletedVariables = new ArrayList<Variable>();    		
    		Iterator<Variable> varIterator = (_model.getProject()).getContent().getVariables().iterator();
    		while (varIterator.hasNext()) {
    			Variable var = varIterator.next();
    			
    			if (!uptodateVariables.contains(var))
    				deletedVariables.add(var);
    		}
    		/* Remove deleted Variables from the Model */
    		varIterator = deletedVariables.iterator();
    		while (varIterator.hasNext()) {
    			Variable var = varIterator.next();
    			
    			(_model.getProject()).getContent().removeVariable(var);
    		}

      		/* Collect list of obsolete attr. Links */
    		ArrayList<AttributeLink> obsoleteAttrLinks = new ArrayList<AttributeLink>();
    		Iterator<AttributeLink> attrLinkIterator = (_model.getProject()).getContent().getLinks().iterator();
    		while (attrLinkIterator.hasNext()) {
    			AttributeLink link = attrLinkIterator.next();
    			
    			if (deletedVariables.contains(link.getAttribute()))
    				obsoleteAttrLinks.add(link);
    		}
    		/* Remove obsolete attr. Links from the Model */
    		attrLinkIterator = obsoleteAttrLinks.iterator();
    		while (attrLinkIterator.hasNext()) {
    			AttributeLink link = attrLinkIterator.next();
    			
    			(_model.getProject()).getContent().removeLink(link);
    		}
    		
    		/* Collect obsolete attr. Mappings */
    		ArrayList<AttributeMap> obsoleteAttrMap = new ArrayList<AttributeMap>();
    		Iterator<AttributeMap> attrMapIterator = (_model.getProject()).getContent().getMaps().iterator();
    		while (attrMapIterator.hasNext()) {
    			AttributeMap map = attrMapIterator.next();
    			
    			if (obsoleteAttrLinks.contains(map.getSourceAttribute()))
    				obsoleteAttrMap.add(map);
    			
    			if (obsoleteAttrLinks.contains(map.getTargetAttribute()))
    				map.setTargetAttribute(null);
    		}
    		/* remove obsolete attr. Mappings from the Model */
    		attrMapIterator = obsoleteAttrMap.iterator();
    		while (attrMapIterator.hasNext()) {
    			AttributeMap map = attrMapIterator.next();
    			
    			(_model.getProject()).getContent().removeMap(map);
    		}
    		
       		/* Collect obsolete attr. Comparisons */
    		ArrayList<AttributeComp> obsoleteAttrComp = new ArrayList<AttributeComp>();
    		Iterator<AttributeComp> attrCompIterator = (_model.getProject()).getContent().getComps().iterator();
    		while (attrCompIterator.hasNext()) {
    			AttributeComp comp = attrCompIterator.next();
    			
    			if ((obsoleteAttrLinks.contains(comp.getSourceAttribute())) ||
    					(obsoleteAttrLinks.contains(comp.getTargetAttribute())))
    				obsoleteAttrComp.add(comp);
    		}
    		/* remove obsolete attr. Comparisons from the Model */
    		attrCompIterator = obsoleteAttrComp.iterator();
    		while (attrCompIterator.hasNext()) {
    			AttributeComp comp = attrCompIterator.next();
    			
    			(_model.getProject()).getContent().removeComp(comp);
    		}
    		
       		/* Collect list of obsolete char. Links */
    		ArrayList<CharacteristicLink> obsoleteCharLinks = new ArrayList<CharacteristicLink>();
    		Iterator<CharacteristicLink> charLinkIterator = (_model.getProject()).getContent().getCharacteristicLinks().iterator();
    		while (charLinkIterator.hasNext()) {
    			CharacteristicLink link = charLinkIterator.next();
    			
    			if (deletedVariables.contains(link.getAttribute()))
    				obsoleteCharLinks.add(link);
    		}
    		/* Remove obsolete char. Links from the Model */
    		charLinkIterator = obsoleteCharLinks.iterator();
    		while (charLinkIterator.hasNext()) {
    			CharacteristicLink link = charLinkIterator.next();
    			
    			(_model.getProject()).getContent().removeCharacteristicLink(link);
    		}
    		
      		/* Collect obsolete char. Mappings */
    		ArrayList<CharacteristicMap> obsoleteCharMap = new ArrayList<CharacteristicMap>();
    		Iterator<CharacteristicMap> charMapIterator = (_model.getProject()).getContent().getCharacteristicMaps().iterator();
    		while (charMapIterator.hasNext()) {
    			CharacteristicMap map = charMapIterator.next();
    			
    			if (obsoleteCharLinks.contains(map.getSourceCharacteristic()))    					
    				obsoleteCharMap.add(map);
    			
    			if (obsoleteAttrLinks.contains(map.getTargetCharacteristic()))
    				map.setTargetCharacteristic(null);
    		}
       		/* remove obsolete char. Mappings from the Model */
    		charMapIterator = obsoleteCharMap.iterator();
    		while (charMapIterator.hasNext()) {
    			CharacteristicMap map = charMapIterator.next();
    			
    			(_model.getProject()).getContent().removeCharacteristicMap(map);
    		}   
    	}
	}
	
	/**
	 * @param tab
	 * @return
	 */
	private boolean validateValuesTab(Tab tab) {
		return true;
	}
	
	private boolean validateQuestionTab(Tab tab) {
		return true;
	}
	
	private boolean validateStudyTab(Tab tab) {
		return true;
	}
	
	/**
	 * @param tab
	 */
	private void handleValuesTab(Tab tab) {
		HashMap<String, Component> tabHashMap = tab.buildHashMap(); 

	   	if (tabHashMap.containsKey("valValueTbl")) {
	   		JTable resultTable = ((JTable)(tabHashMap.get("valValueTbl")));
	   		
    		ArrayList<Value> uptodateValues = new ArrayList<Value>();
    		TableModel resultModel = resultTable.getModel();
    		for (int i=0; i<resultModel.getRowCount(); i++) {
       			int 		id		= (Integer)resultModel.getValueAt(i, 0);
       			String		value	= (String)resultModel.getValueAt(i, 2);
    			String		label	= (String)resultModel.getValueAt(i, 3); 
    			Variable	var		= (Variable)resultModel.getValueAt(i, 4); 
    			
    			Value val = null;
    			if (id == -1) {
    				val = new Value(generateNegID());
    				
    				CharacteristicLink link = new CharacteristicLink();
       				link.setEntityID(generateNegID());
    				link.setAttribute(var);
    				link.setType(CharacteristicLinkType.VALUE);
    				link.setCharacteristic(val);
    				
    				CharacteristicMap map = new CharacteristicMap();
       				map.setEntityID(generateNegID());
    				map.setType(CharacteristicMapType.VALUE);
    				map.setSourceCharacteristic(link);
    				
    				(_model.getProject()).getContent().addValue(val);
    				(_model.getProject()).getContent().addCharacteristicLink(link);
    				(_model.getProject()).getContent().addCharacteristicMap(map);
    			} else {
    				val = (_model.getProject()).getContent().getValueByID(id);    				
    			}
    			if (val != null) {
    				val.setValue(value);
	    			val.setLabel(label);
	    			
	    			uptodateValues.add(val);
    			}

    		}
    		
       		/* Collect list of deleted Values */
    		ArrayList<Value> deletedValues = new ArrayList<Value>();    		
    		Iterator<Value> valIterator = (_model.getProject()).getContent().getValues().iterator();
    		while (valIterator.hasNext()) {
    			Value val = valIterator.next();
    			
    			if (!uptodateValues.contains(val))
    				deletedValues.add(val);
    		}
    		/* Remove deleted Values from the Model */
    		valIterator = deletedValues.iterator();
    		while (valIterator.hasNext()) {
    			Value val = valIterator.next();
    			
    			(_model.getProject()).getContent().removeValue(val);
    		}

     		/* Collect list of obsolete char. Links */
    		ArrayList<CharacteristicLink> obsoleteCharLinks = new ArrayList<CharacteristicLink>();
    		Iterator<CharacteristicLink> charLinkIterator = (_model.getProject()).getContent().getCharacteristicLinks().iterator();
    		while (charLinkIterator.hasNext()) {
    			CharacteristicLink link = charLinkIterator.next();
    			
    			if (deletedValues.contains(link.getCharacteristic()))
    				obsoleteCharLinks.add(link);
    		}
    		/* Remove obsolete char. Links from the Model */
    		charLinkIterator = obsoleteCharLinks.iterator();
    		while (charLinkIterator.hasNext()) {
    			CharacteristicLink link = charLinkIterator.next();
    			
    			(_model.getProject()).getContent().removeCharacteristicLink(link);
    		}
    		
       		/* Collect obsolete char. Mappings */
    		ArrayList<CharacteristicMap> obsoleteCharMap = new ArrayList<CharacteristicMap>();
    		Iterator<CharacteristicMap> charMapIterator = (_model.getProject()).getContent().getCharacteristicMaps().iterator();
    		while (charMapIterator.hasNext()) {
    			CharacteristicMap map = charMapIterator.next();
    			
    			if (obsoleteCharLinks.contains(map.getSourceCharacteristic()))
    				obsoleteCharMap.add(map);
    			
    			if (obsoleteCharLinks.contains(map.getTargetCharacteristic()))
    				map.setTargetCharacteristic(null);
    		}
       		/* remove obsolete char. Mappings from the Model */
    		charMapIterator = obsoleteCharMap.iterator();
    		while (charMapIterator.hasNext()) {
    			CharacteristicMap map = charMapIterator.next();
    			
    			(_model.getProject()).getContent().removeCharacteristicMap(map);
    		}
    	}
	}
	
	
	/**
	 * @param tab
	 * @return
	 */
	private boolean validateMapVariableAttributeTab(Tab tab) {
		HashMap<String, Component> tabHashMap = tab.buildHashMap();
		boolean isValid = true;
		
		if (tabHashMap.containsKey("mapTable")) {
			JTable resultTable = ((JTable)(tabHashMap.get("mapTable")));
			
			TableModel resultModel = resultTable.getModel();
			for (int i=0; i<resultModel.getRowCount(); i++) {
				JComboBox			target	= (JComboBox)resultModel.getValueAt(i, 2);	
				
				if (!(target.getSelectedItem() instanceof Attributes)) {
					ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, _view.getLocale());
					JOptionPane.showMessageDialog(_view.getAppFrame(), bundle.getString(MISSING_INPUT)); 
					
					isValid = false;
				}
			}
		}
		
		return isValid;
	}
	
	/**
	 * @param tab
	 */
	private void handleMapVariableAttributeTab(Tab tab) {
		HashMap<String, Component> tabHashMap = tab.buildHashMap(); 
		
		if (tabHashMap.containsKey("mapTable")) {
			JTable resultTable = ((JTable)(tabHashMap.get("mapTable")));
			
	   		ArrayList<AttributeMap> uptodateAttributeMaps = new ArrayList<AttributeMap>();
			TableModel resultModel = resultTable.getModel();
			for (int i=0; i<resultModel.getRowCount(); i++) {
	   			int 				id		= (Integer)resultModel.getValueAt(i, 0);
				Variable			source	= (Variable)resultModel.getValueAt(i, 3);	
				JComboBox			target	= (JComboBox)resultModel.getValueAt(i, 2);	
				
				if (target.getSelectedItem() instanceof Attributes) {
					AttributeMap map = null;
					if (id == -1) {
						map = new AttributeMap(generateNegID());
						
						(_model.getProject()).getContent().addMap(map);
					} else {
						map = (_model.getProject()).getContent().getMapByID(id);    				
					}
					if (map != null) {
						map.setSourceAttribute((_model.getProject()).getContent().getLinkByAttributeAndInstanceTypeAndInstance(source, AttributeLinkType.VARIABLE, ((AttributeMap)resultModel.getValueAt(i, 1)).getSourceAttribute().getInstance()));
						map.setTargetAttribute((_model.getProject()).getContent().getLinkByAttribute((Attributes)target.getSelectedItem()));
						map.setAttributeMapType(AttributeMapType.ASSIGNED_VARIABLE);
						
						map.setBelongsTo( 
								(_model.getProject()).getContent().getInstanceMapByInstance(
										(_model.getProject()).getContent().getRefByInstance(
												((AttributeMap)resultModel.getValueAt(i, 1)).getSourceAttribute().getInstance()
										) 
								) 
						);
						
		    			uptodateAttributeMaps.add(map);
					}
				}
			}
			
    		Iterator<CharacteristicMap> charMapsIter = (_model.getProject()).getContent().getCharacteristicMaps().iterator();
    		while (charMapsIter.hasNext()) {
    			CharacteristicMap charMap = charMapsIter.next();
    			
    			CharacteristicLink charLink = charMap.getSourceCharacteristic();
    			Attributes attr = charLink.getAttribute();
    			AttributeLink attrLink = (_model.getProject()).getContent().getLinkByAttributeAndInstanceType(attr, AttributeLinkType.VARIABLE);
    			AttributeMap attrMap = (_model.getProject()).getContent().getAttributeMapByAttribute(attrLink);
    			
    			charMap.setBelongsTo(attrMap); 
    		}
    		
       		/* Collect list of deleted Mappings */
    		ArrayList<AttributeMap> deletedAttributeMaps = new ArrayList<AttributeMap>();    		
    		Iterator<AttributeMap> attrMapIterator = (_model.getProject()).getContent().getMapsByType(AttributeMapType.ASSIGNED_VARIABLE).iterator();
    		while (attrMapIterator.hasNext()) {
    			AttributeMap map = attrMapIterator.next();
    			
    			if (!uptodateAttributeMaps.contains(map))
    				deletedAttributeMaps.add(map);
    		}
    		/* Remove deleted Mappings from the Model */
    		attrMapIterator = deletedAttributeMaps.iterator();
    		while (attrMapIterator.hasNext()) {
    			AttributeMap map = attrMapIterator.next();
    			
    			(_model.getProject()).getContent().removeMap(map);
    		}
    		
    		/* Collect list of obsolete Char Mappings */
    		ArrayList<CharacteristicMap> obsoleteCharMaps = new ArrayList<CharacteristicMap>();    		
    		Iterator<CharacteristicMap> charMapIterator = (_model.getProject()).getContent().getCharacteristicMaps().iterator();
    		while (charMapIterator.hasNext()) {
    			CharacteristicMap map = charMapIterator.next();
    			
    			if (deletedAttributeMaps.contains(map.getBelongsTo()))
    				obsoleteCharMaps.add(map);
    		}
    		/* Remove obsolete Mappings from the Model */
    		charMapIterator = obsoleteCharMaps.iterator();
    		while (charMapIterator.hasNext()) {
    			CharacteristicMap map = charMapIterator.next();
    			
       			(_model.getProject()).getContent().removeCharacteristicMap(map);
        	}
			
		}
	}
	
	/**
	 * @param tab
	 * @return
	 */
	private boolean validateMapVariableCharTab(Tab tab) {
		HashMap<String, Component> tabHashMap = tab.buildHashMap();
		boolean isValid = true;
		
		if (tabHashMap.containsKey("recodeTable")) {
			JTable resultTable = ((JTable)(tabHashMap.get("recodeTable")));
			
			TableModel resultModel = resultTable.getModel();
			for (int i=0; i<resultModel.getRowCount(); i++) {
				JComboBox			target	= (JComboBox)resultModel.getValueAt(i, 2); 
				
				if (!(target.getSelectedItem() instanceof Characteristics)) {
					ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, _view.getLocale());
					JOptionPane.showMessageDialog(_view.getAppFrame(), bundle.getString(MISSING_INPUT) +" "+ bundle.getString(TARGET_VALUE) +" "+ bundle.getString(IN_ROW) +" "+ (i+1) +" ('"+ bundle.getString(SOURCE_VALUE) +" "+ resultModel.getValueAt(i, 3) +"')"); 
					
					isValid = false;
				}
			}
		}
		
		return isValid;
	}
	
	/**
	 * @param tab
	 */
	private void handleMapVariableCharTab(Tab tab) {
		HashMap<String, Component> tabHashMap = tab.buildHashMap(); 
		
		if (tabHashMap.containsKey("recodeTable")) {
			JTable resultTable = ((JTable)(tabHashMap.get("recodeTable")));
			
	   		ArrayList<CharacteristicMap> uptodateCharMaps = new ArrayList<CharacteristicMap>();
			TableModel resultModel = resultTable.getModel();
			for (int i=0; i<resultModel.getRowCount(); i++) {
	   			int 				id		= (Integer)resultModel.getValueAt(i, 0);
				Value				source	= (Value)resultModel.getValueAt(i, 3);	
				JComboBox			target	= (JComboBox)resultModel.getValueAt(i, 2);	
				
				CharacteristicMap map = null;
				if (id == -1) {
					map = new CharacteristicMap(generateNegID());
					
					(_model.getProject()).getContent().addCharacteristicMap(map);
				} else {
					map = (_model.getProject()).getContent().getCharacteristicMapByID(id);    				
				}
				if (map != null) {
					map.setSourceCharacteristic((_model.getProject()).getContent().getCharacteristicLinkByCharacteristic(source));
					if (target.getSelectedItem() instanceof Characteristics) 
					map.setTargetCharacteristic((_model.getProject()).getContent().getCharacteristicLinkByCharacteristic((Characteristics)target.getSelectedItem()));
						
					map.setType(CharacteristicMapType.VALUE); 
										
					map.setBelongsTo( 
							(_model.getProject()).getContent().getAttributeMapByAttribute(
									(_model.getProject()).getContent().getLinkByAttribute(
											((CharacteristicMap)resultModel.getValueAt(i, 1)).getSourceCharacteristic().getAttribute()
									) 
							) 
					);
					
	    			uptodateCharMaps.add(map);
				}
			}
			
       		/* Collect list of deleted Mappings */
    		ArrayList<CharacteristicMap> deletedCharacteristicMaps = new ArrayList<CharacteristicMap>();    		
    		Iterator<CharacteristicMap> charMapIterator = (_model.getProject()).getContent().getCharacteristicMapsByType(CharacteristicMapType.VALUE).iterator();
    		while (charMapIterator.hasNext()) {
    			CharacteristicMap map = charMapIterator.next();
    			
    			if (!uptodateCharMaps.contains(map))
    				deletedCharacteristicMaps.add(map);
    		}
    		/* Remove deleted Mappings from the Model */
    		charMapIterator = deletedCharacteristicMaps.iterator();
    		while (charMapIterator.hasNext()) {
    			CharacteristicMap map = charMapIterator.next();
    			
    			(_model.getProject()).getContent().removeCharacteristicMap(map);
    		}
		}
	}
	
	/**
	 * @param tab
	 * @return
	 */
	private boolean validateMapVariableInstanceTab(Tab tab) {
		HashMap<String, Component> tabHashMap = tab.buildHashMap();
		boolean isValid = true;
		
		if (tabHashMap.containsKey("selectTable")) {
			JTable resultTable = ((JTable)(tabHashMap.get("selectTable")));
			
			TableModel resultModel = resultTable.getModel();
			for (int i=0; i<resultModel.getRowCount(); i++) {
				JComboBox			target	= (JComboBox)resultModel.getValueAt(i, 2);	
				
				if (!(target.getSelectedItem() instanceof WorkStepInstance)) {
					ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, _view.getLocale());
					JOptionPane.showMessageDialog(_view.getAppFrame(), bundle.getString(MISSING_INPUT)); 
					
					isValid = false;
				}
			}
		}
		
		return isValid;
	}
	
	/**
	 * @param tab
	 */
	private void handleMapVariableInstanceTab(Tab tab) {
		HashMap<String, Component> tabHashMap = tab.buildHashMap(); 
		
		if (tabHashMap.containsKey("selectTable")) {
    		JTable resultTable = ((JTable)(tabHashMap.get("selectTable")));
    		
    		ArrayList<InstanceMap> uptodateInstanceMaps = new ArrayList<InstanceMap>();
    		TableModel resultModel = resultTable.getModel();
    		for (int i=0; i<resultModel.getRowCount(); i++) {
       			int 				id		= (Integer)resultModel.getValueAt(i, 0);
    			WorkStepInstance	source	= (WorkStepInstance)resultModel.getValueAt(i, 3);	
    			JComboBox			target	= (JComboBox)resultModel.getValueAt(i, 2);	
    			
    			InstanceMap map = null;
    			if (id == -1) {
    				map = new InstanceMap(generateNegID());
    				map.setBelongsTo(_model.getProject());
    				
    				(_model.getProject()).getContent().addInstanceMap(map);
    			} else {
    				map = (_model.getProject()).getContent().getInstanceMapByID(id);    				
    			}
    			if ((map != null) &&
    					(target.getSelectedItem() instanceof WorkStepInstance)) { 
    				map.setSourceInstance((_model.getProject()).getContent().getRefByInstance(source));    				
    				map.setTargetInstance((_model.getProject()).getContent().getRefByInstance((WorkStepInstance)target.getSelectedItem()));
    				map.setType(InstanceMapType.DATA_RECODING);
    				map.setBelongsTo(_model.getProject());
    				
	    			uptodateInstanceMaps.add(map);
    			}
    		}
    		
    		Iterator<AttributeMap> attrMapsIter = (_model.getProject()).getContent().getMaps().iterator();
    		while (attrMapsIter.hasNext()) {
    			AttributeMap attrMap = attrMapsIter.next();
    			
    			AttributeLink attrLink = attrMap.getSourceAttribute();
    			WorkStepInstance inst = attrLink.getInstance();
    			InstanceLink instLink = (_model.getProject()).getContent().getRefByInstance(inst);
    			InstanceMap instMap = (_model.getProject()).getContent().getInstanceMapByInstance(instLink);
    			
    			attrMap.setBelongsTo(instMap);
    		}
    		
    		/* Collect list of deleted Mappings */
    		ArrayList<InstanceMap> deletedInstanceMaps = new ArrayList<InstanceMap>();    		
    		Iterator<InstanceMap> instMapIterator = (_model.getProject()).getContent().getInstanceMapsByType(InstanceMapType.DATA_RECODING).iterator();
    		while (instMapIterator.hasNext()) {
    			InstanceMap map = instMapIterator.next();
    			
    			if (!uptodateInstanceMaps.contains(map))
    				deletedInstanceMaps.add(map);
    		}
    		/* Remove deleted Mappings from the Model */
    		instMapIterator = deletedInstanceMaps.iterator();
    		while (instMapIterator.hasNext()) {
    			InstanceMap map = instMapIterator.next();
    			
    			(_model.getProject()).getContent().removeInstanceMap(map);
    		}
    		
      		/* Collect list of obsolete Attribute Mappings */
    		ArrayList<AttributeMap> obsoleteAttributeMaps = new ArrayList<AttributeMap>();    		
    		Iterator<AttributeMap> attrMapIterator = (_model.getProject()).getContent().getMaps().iterator();
    		while (attrMapIterator.hasNext()) {
    			AttributeMap map = attrMapIterator.next();
    			
    			if (deletedInstanceMaps.contains(map.getBelongsTo()))
    				obsoleteAttributeMaps.add(map);
    		}
    		/* Remove obsolete Mappings from the Model */
    		attrMapIterator = obsoleteAttributeMaps.iterator();
    		while (attrMapIterator.hasNext()) {
    			AttributeMap map = attrMapIterator.next();
    			
    			(_model.getProject()).getContent().removeMap(map);
    		}
    		
      		/* Collect list of obsolete Char Mappings */
    		ArrayList<CharacteristicMap> obsoleteCharMaps = new ArrayList<CharacteristicMap>();    		
    		Iterator<CharacteristicMap> charMapIterator = (_model.getProject()).getContent().getCharacteristicMaps().iterator();
    		while (charMapIterator.hasNext()) {
    			CharacteristicMap map = charMapIterator.next();
    			
    			if (obsoleteAttributeMaps.contains(map.getBelongsTo()))
    				obsoleteCharMaps.add(map);
    		}
    		/* Remove obsolete Mappings from the Model */
    		charMapIterator = obsoleteCharMaps.iterator();
    		while (charMapIterator.hasNext()) {
    			CharacteristicMap map = charMapIterator.next();
    			
       			(_model.getProject()).getContent().removeCharacteristicMap(map);
        	}   
		}
	}
	
	/**
	 * @return
	 */
	private boolean autoCompletition() {
		_view.showWaitingCursor(); 
		
		DesktopPane			desktop		= _view.getDesktop();
		TabbedPaneForm		tabbedPane	= (TabbedPaneForm)((FormFrame)desktop.getFormFrame()).getTabbedPane();
					
		JTabbedPane tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.TAR_VAR_TAB_IDX);
		TabMeasurement tabMea = (TabMeasurement) tabPane.getComponentAt(TabbedPaneTargetVariableStep.MEA_TAB_IDX);
		
		/* disabled in QuickCharmStats, therefore enabled for one programmed action: */
		tabMea.getNextButton().setEnabled(true);
		
		tabMea.getNextButton().doClick(); /* original call for all versions valid */
		
		/* disabled in QuickCharmStats, therefore again disabled after executing the programmed action: */
		tabMea.getNextButton().setEnabled(false);
		
		tabPane = (JTabbedPane) tabbedPane.getComponentAt(TabbedPaneForm.CON_STE_TAB_IDX);
		TabDimension tabDim = (TabDimension) tabPane.getComponentAt(TabbedPaneConceptualStep.DIM_TAB_IDX);
		tabDim.getBackButton().doClick();
		
		/* disabled in QuickCharmStats */
		tabbedPane.setEnabledAt(TabbedPaneForm.CON_STE_TAB_IDX, false);
		tabPane.setEnabledAt(TabbedPaneConceptualStep.DIM_TAB_IDX, false);
		/* disabled in QuickCharmStat END */
				
		if (tabPane.getSelectedComponent() instanceof Tab) {
			((Tab)tabPane.getSelectedComponent()).enableElements(true);
		}
		
		if (!_model.getProject().getProgress().isMeasurementTabDone())
			return false;
		
		Variable source_variable = null;
		
		/* 1. Import Variable */
		/* Get Variable from Basket */
		ArrayList<Object> objectsList = getBasketContentsByType(Variable.class);
		
		ArrayList<Variable> variablesList = new ArrayList<Variable>();
		if (objectsList != null) {
			Iterator<Object> iterator = objectsList.iterator();
			
			while (iterator.hasNext()) {
				Variable var = (Variable)iterator.next();
				
				variablesList.add(var);
			}
		}
		
		_view.showDefaultCursor(); 
		
		/* Call Dialogue "ImportVariable" */
		ImportVariableDialog iv = new ImportVariableDialog(variablesList, _view.getLocale(), this);			
		/* Get selected Variables & Create SourceVariable*/
		ArrayList<Variable> selectedVariables = iv.getImportedVariables();
		
		if (selectedVariables.size() < 1) {
			return false;
		}
		
		_view.showWaitingCursor(); 
		
		Iterator<Variable> variable_iter = selectedVariables.iterator();
		while (variable_iter.hasNext()) {
			source_variable = variable_iter.next();
		}
		_model.getProject().getContent().addVariable(source_variable);			
		
		/* 2. Create DataRecoding Step Instance */
		WorkStepInstance di = new WorkStepInstance();
		di.setLabel("di-default");
		di.setType(InstanceType.DATA_CODING);
		_model.getProject().getContent().addLayer(di);
		
		InstanceLink dil = new InstanceLink();
		dil.setWorkStepRefType(InstanceLinkType.DATA_CODING);
		dil.setInstance(di);
		dil.setProject(_model.getProject());
		_model.getProject().getContent().addInstanceRef(dil);
		
		boolean loadStatus = true;
		
		CharacteristicLink charLink = new CharacteristicLink(source_variable);
		ArrayList<Integer> charlist = charLink.loadLinks(_connection);
		
		if (!charlist.isEmpty()) {
			Iterator<Integer> iterator = charlist.iterator();
			
			while (iterator.hasNext()) {
				Integer id = iterator.next();
				
				charLink = new CharacteristicLink();
				charLink.setAttribute(source_variable);
				charLink.setEntityID(id);
				if (loadStatus)
					loadStatus = charLink.entityLoad(_connection);

				Characteristics char_ch		= (_model.getProject()).getContent().getValueByID(charLink.getDsCharEntryID());
				CharacteristicLink char_ln	= (_model.getProject()).getContent().getCharacteristicLinkByCharacteristic(char_ch);
				CharacteristicMap char_mp	= (_model.getProject()).getContent().getCharacteristicMapByCharacteristic(char_ln);

				Characteristics characteristic = new Value();
				if (!(char_ch != null)) {						
					characteristic.setEntityID(charLink.getDsCharEntryID());
				
					if (loadStatus)
						loadStatus = ((Value)characteristic).entityLoad(_connection);
				}								
				if (char_ch != null)
					characteristic = char_ch;
					
				if (char_ln != null)
					charLink = char_ln;
				charLink.setCharacteristic(characteristic);
				
				CharacteristicMap charMap = new CharacteristicMap();
				if (!(char_mp != null)) {
					charMap.setEntityID(generateNegID());
					charMap.setType(CharacteristicMapType.VALUE);
					
				}
				if (char_mp != null)
					charMap = char_mp;
				charMap.setSourceCharacteristic(charLink);
				
				if (loadStatus) {
					if (!(char_ch != null))
						(_model.getProject()).getContent().addValue((Value)characteristic);
					
					if (!(char_ln != null))	
						(_model.getProject()).getContent().addCharacteristicLink(charLink);
					
					if (!(char_mp != null))
						(_model.getProject()).getContent().addCharacteristicMap(charMap);

				}							
			}
		}
		
		/* 3. Create AttributeLink for variable */
		AttributeLink varLink = new AttributeLink();
		varLink.setAttributeLinkType(AttributeLinkType.VARIABLE);
		varLink.setInstance(di);
		varLink.setAttribute(source_variable);
		_model.getProject().getContent().addLink(varLink);
		
		/* 4. Create Dimension identical to Measurement */
		ConDimension dimension = new ConDimension(generateNegID());
		dimension.setLabel(_model.getProject().getContent().getMeasurement().getName() + "_d");
		dimension.setLevel(_model.getProject().getContent().getMeasurement().getLevel());
		_model.getProject().getContent().addDimension(dimension);
		
		AttributeLink dimLink = new AttributeLink();
		dimLink.setAttributeLinkType(AttributeLinkType.DIMENSION);
		dimLink.setInstance(_model.getProject().getContent().getConceptualLayer());
		dimLink.setAttribute(dimension);
		_model.getProject().getContent().addLink(dimLink);
		
		/* 6. Create Indicator identical to Measurement* more or less */
		OperaIndicator indicator = new OperaIndicator(generateNegID());
		indicator.setLabel(_model.getProject().getContent().getMeasurement().getName() + "_i");
		indicator.setLevel(dimension.getLevel());
		indicator.setType(source_variable.getMeasureType());
		_model.getProject().getContent().addIndicator(indicator);
		
		/* 7. Create Operational Step Instance */
		WorkStepInstance oi = new WorkStepInstance();
		oi.setLabel("oi-default");
		oi.setType(InstanceType.OPERATIONAL);
		_model.getProject().getContent().addLayer(oi);
		
		InstanceLink oil = new InstanceLink();
		oil.setWorkStepRefType(InstanceLinkType.OPERATIONAL);
		oil.setInstance(oi);
		oil.setProject(_model.getProject());
		_model.getProject().getContent().addInstanceRef(oil);
		
		/* 8. Create AttributeLink for indicator */
		AttributeLink indLink = new AttributeLink();
		indLink.setAttributeLinkType(AttributeLinkType.INDICATOR);
		indLink.setInstance(oi);
		indLink.setAttribute(indicator);
		_model.getProject().getContent().addLink(indLink);
		
		/* Mapping */
		ArrayList<WorkStepInstance> sources = 
				_model.getProject().getContent().getLayers(InstanceType.CONCEPTUAL);
		InstanceLink cil = 
				(_model.getProject()).getContent().getRefByInstance(sources.get(0));			
		
//		ArrayList<WorkStepInstance> targets = 
//				_model.getProject().getContent().getLayers(InstanceType.PROJECT_SETUP);
//		InstanceLink sil =
//				(_model.getProject()).getContent().getRefByInstance(targets.get(0));
		
//		InstanceMap conInstMap = new InstanceMap(generateNegID());	
//		(_model.getProject()).getContent().addInstanceMap(conInstMap);
//		
//		conInstMap.setType(InstanceMapType.CONCEPTUAL);
//		conInstMap.setSourceInstance(cil);
//		conInstMap.setTargetInstance(sil);					
//		conInstMap.setBelongsTo(_model.getProject());
					
		InstanceMap operaInstMap = new InstanceMap(generateNegID());
		(_model.getProject()).getContent().addInstanceMap(operaInstMap);
		
		operaInstMap.setType(InstanceMapType.OPERATIONAL);			
		operaInstMap.setSourceInstance(oil);			
		operaInstMap.setTargetInstance(cil);			
		operaInstMap.setBelongsTo(_model.getProject());
					
		InstanceMap dataInstMap = new InstanceMap(generateNegID());
		(_model.getProject()).getContent().addInstanceMap(dataInstMap);
		
		dataInstMap.setType(InstanceMapType.DATA_RECODING);			
		dataInstMap.setSourceInstance(dil);			
		dataInstMap.setTargetInstance(oil);			
		dataInstMap.setBelongsTo(_model.getProject());
		
		
		AttributeLink meaLink = 
				_model.getProject().getContent().getLinkByAttribute(
						_model.getProject().getContent().getMeasurement());
		
		AttributeMap conAttrMap = new AttributeMap(generateNegID());
		(_model.getProject()).getContent().addMap(conAttrMap);
		
		conAttrMap.setAttributeMapType(AttributeMapType.SPECIFICATION);
		conAttrMap.setSourceAttribute(dimLink);
		conAttrMap.setTargetAttribute(meaLink);
		conAttrMap.setBelongsTo(_model.getProject().getContent().getInstanceMapsByType(InstanceMapType.CONCEPTUAL).get(0));
		
		AttributeMap operaAttrMap = new AttributeMap(generateNegID());
		(_model.getProject()).getContent().addMap(operaAttrMap);
		
		operaAttrMap.setAttributeMapType(AttributeMapType.ASSIGNED_INDICATOR);
		operaAttrMap.setSourceAttribute(indLink);
		operaAttrMap.setTargetAttribute(dimLink);
		operaAttrMap.setBelongsTo(operaInstMap);
		
		AttributeMap dataAttrMap = new AttributeMap(generateNegID());
		(_model.getProject()).getContent().addMap(dataAttrMap);
		
		dataAttrMap.setAttributeMapType(AttributeMapType.ASSIGNED_VARIABLE);
		dataAttrMap.setSourceAttribute(varLink);
		dataAttrMap.setTargetAttribute(indLink);
		dataAttrMap.setBelongsTo(dataInstMap);
		
		/* CharMap for val, spec, pres & val */
		/* 5. Create Specifications identical to Categories */
		ArrayList<CharacteristicLink> catLinks = _model.getProject().getContent().getCharacteristicLinksByAttribute(
				_model.getProject().getContent().getMeasurement());
		
		Iterator<CharacteristicLink> catLinkIter = catLinks.iterator();
		while (catLinkIter.hasNext()) {
			CharacteristicLink catLink = catLinkIter.next();
			
			ConSpecification spec = new ConSpecification();
			spec.setLabel(((Category)catLink.getCharacteristic()).getLabel() + "_s");
			_model.getProject().getContent().addSpecification(spec);
			
			CharacteristicLink specLink = new CharacteristicLink();
			specLink.setType(CharacteristicLinkType.SPECIFICATION);
			specLink.setAttribute(dimension);
			specLink.setCharacteristic(spec);
			_model.getProject().getContent().addCharacteristicLink(specLink);
			
			CharacteristicMap charMap = new CharacteristicMap(generateNegID());
			(_model.getProject()).getContent().addCharacteristicMap(charMap);
			
			charMap.setType(CharacteristicMapType.SPECIFICATION);
			charMap.setSourceCharacteristic(specLink);
			charMap.setTargetCharacteristic(catLink);
			charMap.setBelongsTo(conAttrMap);
			
			/* 9. Create Prescriptions identical to Dimensions */
			OperaPrescription pres = new OperaPrescription();
			pres.setLabel(((Category)catLink.getCharacteristic()).getLabel() + "_p");
			pres.setValue(((Category)catLink.getCharacteristic()).getCode());
			_model.getProject().getContent().addPrescription(pres);
			
			CharacteristicLink presLink = new CharacteristicLink();
			presLink.setType(CharacteristicLinkType.PRESCRIPTION);
			presLink.setAttribute(indicator);
			presLink.setCharacteristic(pres);
			_model.getProject().getContent().addCharacteristicLink(presLink);
			
			charMap = new CharacteristicMap(generateNegID());
			(_model.getProject()).getContent().addCharacteristicMap(charMap);
			
			charMap.setType(CharacteristicMapType.PRESCRIPTION);
			charMap.setSourceCharacteristic(presLink);
			charMap.setTargetCharacteristic(specLink);
			charMap.setBelongsTo(operaAttrMap);
		}
		
				
		ArrayList<CharacteristicLink> valLinks = _model.getProject().getContent().getCharacteristicLinksByAttribute(
				source_variable);
		
		Iterator<CharacteristicLink> valLinkIter = valLinks.iterator();
		while (valLinkIter.hasNext()) {
			CharacteristicLink valLink = valLinkIter.next();
			
			CharacteristicMap charMap = new CharacteristicMap(generateNegID());
			(_model.getProject()).getContent().addCharacteristicMap(charMap);
			
			charMap.setType(CharacteristicMapType.VALUE);
			charMap.setSourceCharacteristic(valLink);
//			charMap.setTargetCharacteristic(presLink); by the user
			charMap.setBelongsTo(dataAttrMap);
		}
					
		_model.getProject().getProgress().setProjectSetupStepTabbedPanelAvailable(true);
		_model.getProject().getProgress().setProjectTabDone(true);
		_model.getProject().getProgress().setConceptTabDone(true);
		/* disabled in QuickCharmStats: */
		_model.getProject().getProgress().setReferenceTabDone(false);
		/* disabled in QuickCharmStats, therefore not enabled: */
		//_model.getProject().getProgress().setReferenceTabDone(true);
		_model.getProject().getProgress().setTargetVariableStepTabbedPanelAvailable(true);
		_model.getProject().getProgress().setMeasurementTabDone(true);
		/* disabled in QuickCharmStats: */
		_model.getProject().getProgress().setConceptualStepTabbedPanelAvailable(false);
		/* disabled in QuickCharmStats: */
		/*
		_model.getProject().getProgress().setConceptualStepTabbedPanelAvailable(true);			
		_model.getProject().getProgress().setDimensionTabDone(true);
		_model.getProject().getProgress().setSpecificationTabDone(true);
		_model.getProject().getProgress().setMapDimensionAttributeTabDone(true);
		_model.getProject().getProgress().setMapDimensionChaTabDone(true);
		_model.getProject().getProgress().setMapDimensionInstanceTabDone(true);
		*/
		/* disabled in QuickCharmStats: */
		_model.getProject().getProgress().setOperationalStepTabbedPanelAvailable(false);
		/* disabled in QuickCharmStats: */
		/*
		_model.getProject().getProgress().setOsInstanceTabDone(true);
		_model.getProject().getProgress().setIndicatorTabDone(true);
		_model.getProject().getProgress().setPrescriptionTabDone(true);
		_model.getProject().getProgress().setMapIndicatorAttributeTabDone(true);
		_model.getProject().getProgress().setMapIndicatorInstanceTabDone(true);
		_model.getProject().getProgress().setMapIndicatorChaTabDone(true);
		*/
		/* disabled in QuickCharmStat: */
		_model.getProject().getProgress().setSearchNCompareStepTabbedPanelAvailable(false);
		/* disabled in QuickCharmStats: */
		/*
		_model.getProject().getProgress().setSearchNCompareStepTabbedPanelAvailable(true);
		_model.getProject().getProgress().setSearchTabDone(true);
		_model.getProject().getProgress().setComparisonTabDone(true);
		_model.getProject().getProgress().setCompareValuesTabDone(true);
		*/
		_model.getProject().getProgress().setDataRecodingStepTabbedPanelAvailable(true);
		_model.getProject().getProgress().setDrInstanceTabDone(true);
		_model.getProject().getProgress().setVariableTabDone(true);
		_model.getProject().getProgress().setValueTabDone(true);
		_model.getProject().getProgress().setQuestionTabDone(true);
		_model.getProject().getProgress().setStudyTabDone(true);
		_model.getProject().getProgress().setMapVariableAttributeTabDone(true);
		_model.getProject().getProgress().setMapVariableChaTabDone(true);
		_model.getProject().getProgress().setMapVariableInstanceTabDone(true);
		
		saveProject();
		int project_id = _model.getProject().getEntityID();
		closeProject(true);
		openProject(project_id);
		
		_view.showDefaultCursor(); 
		
		return true;
	}
	
	/**
	 * @return
	 */
	private boolean importMeasurement() {
		_view.showWaitingCursor(); 
		
		Tab tab = _view.getPreviousStep();			
		if (tab instanceof TabMeasurement) {
			HashMap<String, Component> tabHashMap = tab.buildHashMap(); 
			
	   		String targetName = "";
			if (tabHashMap.containsKey("mea_target_name")) {
				targetName = ((JTextField) tabHashMap.get("mea_target_name")).getText();
			}
			
	   		String targetLabel = "";
			if (tabHashMap.containsKey("mea_target_label")) {
				targetLabel = ((JTextField) tabHashMap.get("mea_target_label")).getText();
			}
			
			_model.getProject().setTargetName(targetName);
			_model.getProject().setTargetLabel(targetLabel);
		}
		
		
		/* Get Measurement from Basket */
		ArrayList<Object> objectsList = getBasketContentsByType(Measurement.class);
		 
		/* Prepare parameter for call */
		ArrayList<Measurement> measurementsList = new ArrayList<Measurement>();
		if (objectsList != null) {
			Iterator<Object> iterator = objectsList.iterator();
			
			while (iterator.hasNext()) {
				Measurement measurement = (Measurement)iterator.next();
				
				measurementsList.add(measurement);
			}
		}
		
		_view.showDefaultCursor(); 
		
		/* Call Dialogue "ImportMeasurement" */
		ImportMeasurementDialog im = new ImportMeasurementDialog(measurementsList, _view.getLocale(), this);
		
		/* Get selected Measurements */
		ArrayList<Measurement> selectedMeasurements = im.getImportedMeasurements();
		
		if (selectedMeasurements.size() < 1)
			return false;
		
		_view.showWaitingCursor(); 
			
		Iterator<Measurement> measurement_iter = selectedMeasurements.iterator();
		while (measurement_iter.hasNext()) {
			Measurement mea = measurement_iter.next();
			
			_model.getProject().getContent().setMeasurement(mea);
		}
		
		
		/* Remove old Categories */
  		/* Collect list of deleted Categories */
		ArrayList<Category> deletedCategories = new ArrayList<Category>();    		
		Iterator<Category> catIterator = (_model.getProject()).getContent().getCategories().iterator();
		while (catIterator.hasNext()) {
			Category cat = catIterator.next();
			deletedCategories.add(cat);
		}
		/* Remove deleted Categories from the Model */
		catIterator = deletedCategories.iterator();
		while (catIterator.hasNext()) {
			Category cat = catIterator.next();
			
			(_model.getProject()).getContent().removeCategory(cat);
		}
		
   		/* Collect list of obsolete char. Links */
		ArrayList<CharacteristicLink> obsoleteCharLinks = new ArrayList<CharacteristicLink>();
		Iterator<CharacteristicLink> charLinkIterator = (_model.getProject()).getContent().getCharacteristicLinks().iterator();
		while (charLinkIterator.hasNext()) {
			CharacteristicLink link = charLinkIterator.next();
			
			if (deletedCategories.contains(link.getCharacteristic()))
				obsoleteCharLinks.add(link);
		}
		/* Remove obsolete char. Links from the Model */
		charLinkIterator = obsoleteCharLinks.iterator();
		while (charLinkIterator.hasNext()) {
			CharacteristicLink link = charLinkIterator.next();
			
			(_model.getProject()).getContent().removeCharacteristicLink(link);
		}
		
   		/* Collect obsolete char. Mappings */
		ArrayList<CharacteristicMap> obsoleteCharMap = new ArrayList<CharacteristicMap>();
		Iterator<CharacteristicMap> charMapIterator = (_model.getProject()).getContent().getCharacteristicMaps().iterator();
		while (charMapIterator.hasNext()) {
			CharacteristicMap map = charMapIterator.next();
			
			if (obsoleteCharLinks.contains(map.getSourceCharacteristic())) 
				obsoleteCharMap.add(map);
			
   			if (obsoleteCharLinks.contains(map.getTargetCharacteristic()))
				map.setTargetCharacteristic(null);
		}
   		/* remove obsolete char. Mappings from the Model */
		charMapIterator = obsoleteCharMap.iterator();
		while (charMapIterator.hasNext()) {
			CharacteristicMap map = charMapIterator.next();
			
			(_model.getProject()).getContent().removeCharacteristicMap(map);
		}
		
		/* Reset Mappings from Instances, Attributes and Characteristics */
		
		boolean loadStatus = true;
		
		CharacteristicLink		charLink;
		Characteristics			characteristic = null;
		
		charLink = new CharacteristicLink(_model.getProject().getContent().getMeasurement());
		ArrayList<Integer>	charlist	= charLink.loadLinks(_connection);
			
		if (!charlist.isEmpty()) {
			Iterator<Integer> iterator = charlist.iterator();
				
			while (iterator.hasNext()) {
				Integer id = iterator.next();
					
				charLink = new CharacteristicLink();
				charLink.setAttribute(_model.getProject().getContent().getMeasurement());
				charLink.setEntityID(id);
				if (loadStatus)
					loadStatus = charLink.entityLoad(_connection);
					
				characteristic = new Category();
				characteristic.setEntityID(charLink.getDsCharEntryID());
				
				if (loadStatus)
					loadStatus = ((Category)characteristic).entityLoad(_connection);
					
				if (loadStatus) {
					if(_model.getProject().getContent().getMeasurement().isTemplate()) {
						characteristic.setEntityID(generateNegID());
						charLink.setEntityID(generateNegID());
					}							
					
					_model.getProject().getContent().addCategory((Category)characteristic);
					
					charLink.setCharacteristic(characteristic);
					_model.getProject().getContent().addCharacteristicLink(charLink);
				}
			}
		}
				
//		_model.getProject().getProgress().setMeasurementTabDone(true);
//		saveProject();
//		int project_id = _model.getProject().getEntityID();
//		closeProject(true);
//		openProject(project_id);
		
		if (!_model.getProject().getContent().getMeasurement().isTemplate())
			_model.getProject().setMeasurementImported(true);
		else {
			_model.getProject().getContent().getMeasurement().setEntityID(-1);
			_model.getProject().setMeasurementImported(false);
			_model.getProject().getContent().getMeasurement().setIsTemplate(false);
		}
		
		_view.showDefaultCursor(); 
		
		return true;
	}

	
	/*
	 *	Miscellaneous methods
	 */
	public static int generateNegID() {
		int id = generator.nextInt();
		while (id >= -1) {
			id = generator.nextInt();
		}
		return id;
	}
	
	/**
	 * @param type
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public ArrayList<Object> getBasketContentsByType(Class type) {		
		return _model.getUser().getBasket().getContentsByType(type);
	}
		
	/**
	 * @param tab
	 * @param subjectTemplate
	 */
	public void addTabComment(TabDummy tab, String subjectTemplate) {
		String commentText = null;
		Comment comment = tab.getComment();
		
		String[] argValue = null;
		if (comment != null) {
			argValue = new String[2];
			argValue[0] = comment.getText();
			argValue[1] = comment.getSubject();
		}
		
		/*
		 *	 Open the pop-up window and wait for return value
		 */
		ResourceBundle resourceBundle	= ResourceBundle.getBundle(BUNDLE, _view.getLocale());
		
		if (!subjectTemplate.isEmpty()) {
			argValue[1] = resourceBundle.getString(subjectTemplate);
		}
		
		String dialogTitle = 
			(!(comment != null) ?  "add_comment" : "edit_comment");	
		Object o = new CommentDialog(_view.getAppFrame(), resourceBundle.getString(dialogTitle), argValue, false, true, _model.getProject().isFinished(), _model.getProject().isEditedByUser(), _view.getLocale(), _view.getFont()).getValue();	
		
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
				tab.setComment(comment);
			}
		}
	}
		
	/**
	 * @param isProjectInWork
	 */
	public void setProjectInWork(boolean isProjectInWork) {
		this.projectInWork = isProjectInWork;
	}

	/**
	 * @return
	 */
	public boolean isProjectInWork() {
		return projectInWork;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.event.TreeSelectionListener#valueChanged(javax.swing.event.TreeSelectionEvent)
	 */
	@Override
	public void valueChanged(TreeSelectionEvent e) {
		/* Auto-generated method stub */
		
	}
		
	private class DDIFilter extends javax.swing.filechooser.FileFilter
	{
		public boolean accept (File f) {
			String extension = ".xml";
			
			return f.getName().toLowerCase().endsWith(extension) || f.isDirectory();
		}
	 
		public String getDescription () {
			String description = "DDI files (*.xml)";
									
			return description;
		}
	}
	
	/**
	 * @param element
	 * @param nodeName
	 * @param attributeName
	 * @param attributeValue
	 * @return
	 */
	private List<Node> getNodeList(Node element, String nodeName, String attributeName, String attributeValue) {
		List<Node> resultList = new ArrayList<Node>();
		
		NodeList childs = element.getChildNodes();
		for (int i=0; i<childs.getLength(); i++) {
			
			if (childs.item(i).getNodeName().equals(nodeName)) {
				boolean resolvedAttribute = true;
				
				if (!attributeName.equals("")) {
					NamedNodeMap attributes = childs.item(i).getAttributes();
					
					Node attribute = attributes.getNamedItem(attributeName);
					if (attribute != null) {
						if (!attribute.getNodeValue().equals(attributeValue))
							resolvedAttribute = false;
					}
				}
				
				if (resolvedAttribute)
					resultList.add(childs.item(i));
			} else {
				List<Node> returnList = getNodeList(childs.item(i), nodeName, attributeName, attributeValue); 
				
				if (!returnList.isEmpty())
					resultList.addAll(returnList);
			}
		}
		
		return resultList;
	}
	
	/**
	 * @param element
	 * @param attributeValue
	 * @return
	 */
	private List<Node> getNodeValue(Node element, String attributeValue) {
		List<Node> resultList = new ArrayList<Node>();
		
		NodeList childs = element.getChildNodes();
		for (int i=0; i<childs.getLength(); i++) {
			resultList.add(childs.item(i));
		}
		
		return resultList;
	}
	
	/**
	 * @param doc
	 * @param element
	 * @param charLinks
	 * @return
	 */
	private Variable printVar(Node doc, Node element, List<CharacteristicLink> charLinks) {
		Variable returnValue = new Variable();
		
		/* Set UUID */
		returnValue.setUUID(String.valueOf(UUID.randomUUID()));
		
		/* Set Name */
		List<Node> nodeList = getNodeList(element,"Name","","");
		for (int i=0; i<nodeList.size(); i++) {
			List<Node> nodeValue = getNodeValue(nodeList.get(i), "");			
			for (int j=0; j<nodeValue.size(); j++) {
								
				returnValue.setName(nodeValue.get(j).getNodeValue());
			}
		}
		
		/* Set Label */
		returnValue.setLabel("");
		nodeList = getNodeList(element,"Label","","");
		for (int i=0; i<nodeList.size(); i++) {
			List<Node> modeValue = getNodeValue(nodeList.get(i), "");			
			for (int j=0; j<modeValue.size(); j++) {
				if (!modeValue.get(j).getNodeValue().equals(returnValue.getName()))
					returnValue.setLabel(modeValue.get(j).getNodeValue());
			}
		}
				
		/* Set Representation */
		List<Node> abstrRepresentations = getNodeList(element, "Representation", "","");
		String codeSchemeID = "";
		for (int i=0; i<abstrRepresentations.size(); i++) {
			List<Node> implRepresentations = getNodeValue(abstrRepresentations.get(i), "");			
			for (int j=0; j<implRepresentations.size(); j++) {
				List<Node> codeSchemeRefs = getNodeValue(implRepresentations.get(j), "");
				for (int k=0; k<codeSchemeRefs.size(); k++) {
					List<Node> ids = getNodeValue(codeSchemeRefs.get(k), "");
					for (int l=0; l<ids.size(); l++) {
						List<Node> nodeValue = getNodeValue(ids.get(l), "");
						for (int m=0; m<nodeValue.size(); m++) {
							codeSchemeID = nodeValue.get(m).getNodeValue();
						}
					}
				}	
			}
			
			nodeList = getNodeList(doc,"CodeScheme","id",codeSchemeID); 
			for (int n=0; n<nodeList.size(); n++) {
				Node codeScheme = nodeList.get(n);
				
				String catSchemeID = "";
				List<Node> catSchemes = null;
				List<Node> catSchRefs = getNodeList(codeScheme, "CategorySchemeReference", "", "");
				if (catSchRefs != null) {
					List<Node> ids = getNodeList(catSchRefs.get(0), "ID", "", "");
					
					if (ids != null) {
						catSchemeID = ids.get(0).getTextContent();
					}
				}
				
				catSchemes = getNodeList(doc, "CategoryScheme", "id", catSchemeID);
				
				List<Node> codes = getNodeList(codeScheme, "Code", "", "");
				if (codes != null) {
					for (int o=0; o<codes.size(); o++) {
						Value cs_val = new Value();
						List<Node> values = getNodeList(codes.get(o), "Value", "", "");
						if (values != null) {
							cs_val.setValue(values.get(0).getTextContent());
						}
						
						List<Node> catRefs = getNodeList(codes.get(o), "CategoryReference", "", "");
						if (catRefs != null) {
							List<Node> ids = getNodeList(catRefs.get(0), "ID", "", "");
							
							if (ids != null) {
								List<Node> categories = getNodeList(catSchemes.get(0), "Category", "id", ids.get(0).getTextContent());
								if (categories != null) {
									cs_val.setLabel(categories.get(0).getTextContent());
								}
								
							}
						}
						
    					CharacteristicLink cat_link = new CharacteristicLink();
    					cat_link.setEntityID(generateNegID());
    					cat_link.setAttribute(returnValue);
    					cat_link.setCharacteristic(cs_val);
    					cat_link.setType(CharacteristicLinkType.VALUE);
    					
    					charLinks.add(cat_link); 
					}
				}
			}
			
			
		}
		Question csQuest = new Question();
		csQuest.setText("");
		Study	csStudy = new Study();
		csStudy.setUUID(String.valueOf(UUID.randomUUID()));
		csStudy.setTitle("");
		csQuest.setSource(csStudy);
		returnValue.setSource(csQuest);
		
		return returnValue;
	}
	
	/**
	 * @param doc
	 * @param element
	 * @param charLinks
	 * @return
	 */
	private Measurement printMea(Node doc, Node element, List<CharacteristicLink> charLinks) {
		Measurement returnValue = new Measurement();
		
		/* Set UUID */
		returnValue.setUUID(String.valueOf(UUID.randomUUID()));
		
		/* Set Name */
		List<Node> nodeList = getNodeList(element,"Name","","");
		for (int i=0; i<nodeList.size(); i++) {
			List<Node> nodeValue = getNodeValue(nodeList.get(i), "");			
			for (int j=0; j<nodeValue.size(); j++) {
				returnValue.setName(nodeValue.get(j).getNodeValue());
			}
		}
		
		/* Set Label */
		returnValue.setLabel("");
		nodeList = getNodeList(element,"Label","","");
		for (int i=0; i<nodeList.size(); i++) {
			List<Node> modeValue = getNodeValue(nodeList.get(i), "");			
			for (int j=0; j<modeValue.size(); j++) {
				if (!modeValue.get(j).getNodeValue().equals(returnValue.getName()))
					returnValue.setLabel(modeValue.get(j).getNodeValue());
			}
		}
				
		/* Set Representation */
		List<Node> abstrRepresentations = getNodeList(element, "Representation", "","");
		String codeSchemeID = "";
		for (int i=0; i<abstrRepresentations.size(); i++) {
			List<Node> implRepresentations = getNodeValue(abstrRepresentations.get(i), "");			
			for (int j=0; j<implRepresentations.size(); j++) {
				List<Node> codeSchemeRefs = getNodeValue(implRepresentations.get(j), "");
				for (int k=0; k<codeSchemeRefs.size(); k++) {
					List<Node> ids = getNodeValue(codeSchemeRefs.get(k), "");
					for (int l=0; l<ids.size(); l++) {
						List<Node> nodeValue = getNodeValue(ids.get(l), "");
						for (int m=0; m<nodeValue.size(); m++) {
							codeSchemeID = nodeValue.get(m).getNodeValue();
						}
					}
				}	
			}
			
			nodeList = getNodeList(doc,"CodeScheme","id",codeSchemeID); 
			for (int n=0; n<nodeList.size(); n++) {
				Node codeScheme = nodeList.get(n);
				
				String catSchemeID = "";
				List<Node> catSchemes = null;
				List<Node> catSchRefs = getNodeList(codeScheme, "CategorySchemeReference", "", "");
				if (catSchRefs != null) {
					List<Node> ids = getNodeList(catSchRefs.get(0), "ID", "", "");
					
					if (ids != null) {
						catSchemeID = ids.get(0).getTextContent();
					}
				}
				
				catSchemes = getNodeList(doc, "CategoryScheme", "id", catSchemeID);
				
				List<Node> codes = getNodeList(codeScheme, "Code", "", "");
				if (codes != null) {
					for (int o=0; o<codes.size(); o++) {
						Category cs_cat = new Category();
						List<Node> values = getNodeList(codes.get(o), "Value", "", "");
						if (values != null) {
							cs_cat.setCode(values.get(0).getTextContent());
						}
						
						List<Node> catRefs = getNodeList(codes.get(o), "CategoryReference", "", "");
						if (catRefs != null) {
							List<Node> ids = getNodeList(catRefs.get(0), "ID", "", "");
							
							if (ids != null) {
								List<Node> categories = getNodeList(catSchemes.get(0), "Category", "id", ids.get(0).getTextContent());
								if (categories != null) {
									cs_cat.setLabel(categories.get(0).getTextContent());
								}
								
							}
						}
						
    					CharacteristicLink cat_link = new CharacteristicLink();
    					cat_link.setEntityID(generateNegID());
    					cat_link.setAttribute(returnValue);
    					cat_link.setCharacteristic(cs_cat);
    					cat_link.setType(CharacteristicLinkType.CATEGORY);
    					
    					charLinks.add(cat_link);
					}
				}
			}
				
		}
//		Question csQuest = new Question();
//		csQuest.setText("");
//		Study	csStudy = new Study();
//		csStudy.setUUID(String.valueOf(UUID.randomUUID()));
//		csStudy.setTitle("");
//		csQuest.setSource(csStudy);
//		returnValue.setSource(csQuest);
		
		return returnValue;
	}
	
	public void callBrowser(Locale locale, String file_path) {
		ResourceBundle	resourceBundle	= ResourceBundle.getBundle(BUNDLE_F, locale);
		String			file_name		= resourceBundle.getString(FILE_NAME);
		
		String path_0 = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		path_0 = path_0.replace("/", "\\\\"); // use File.separator in CharmStatsPro
		path_0 = path_0.replace(file_name, ""); // filename stored in resource FrameBundle
		path_0 = path_0 + "resources\\\\help documents\\\\"+ locale.toString() +"\\\\"+ file_path;
		path_0 = path_0.replace("%20", " "); // prevent double encoding
		
		File htmlFile = new File(path_0);
		
		
		
		try {
//			JOptionPane.showMessageDialog(_view.getAppFrame(),path_0,"Pfad", JOptionPane.PLAIN_MESSAGE);
			
			Desktop.getDesktop().browse(htmlFile.toURI());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(_view.getAppFrame(),e.getMessage(),"Fehler", JOptionPane.PLAIN_MESSAGE);
			e.printStackTrace();
		}
	}
	
	public CStatsGUI getView() {
		return _view;
	}
			
}

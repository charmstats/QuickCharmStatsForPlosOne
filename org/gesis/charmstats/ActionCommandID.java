package org.gesis.charmstats;

/**
 * 
 * @author	Martin Friedrichs
 * @since	0.1
 *
 */
public class ActionCommandID {
	
	/* MenuBar */
	/* File Menu 1### */
	public static final int CMD_FILE_EXIT					= 1001;
	/* Edit Menu 2### */
	public static final int CMD_EDIT_UNDO					= 2001; /* unused */
	public static final int CMD_EDIT_REDO					= 2002; /* unused */
	public static final int CMD_EDIT_CUT					= 2003; /* unused */
	public static final int CMD_EDIT_COPY					= 2004; /* unused */
	public static final int CMD_EDIT_PASTE					= 2005; /* unused */
	public static final int CMD_EDIT_REMOVE					= 2006; /* unused */
	/* Search Menu 3### */
	public static final int CMD_SRH_SEARCH					= 3001;
	/* Project Menu 4### */
	public static final int CMD_PRJ_NEW_PROJECT				= 4001;
	public static final int CMD_PRJ_OPEN_PROJECT			= 4002;
	public static final int CMD_PRJ_CLOSE					= 4003;
	public static final int CMD_PRJ_CLOSE_ALL				= 4004; /* unused */
	public static final int CMD_PRJ_SAVE					= 4005;
	public static final int CMD_PRJ_SAVE_AS					= 4006; /* unused */
	public static final int CMD_PRJ_SAVE_ALL				= 4007; /* unused */
	public static final int CMD_PRJ_FINISH					= 4008; /* former CMD_PRJ_PUBLISH */
	public static final int CMD_PRJ_REMOVE					= 4011;
	public static final int CMD_PRJ_ADD_PARTICIPANT			= 4009; /* CharmStatsPro only */
	public static final int CMD_PRJ_EDIT_PARTICIPANT		= 4010; /* CharmStatsPro only */
	/* Basket Menu 5### */
	public static final int CMD_BSKT_NEW_BASKET				= 5001; /* unused */
	public static final int CMD_BSKT_OPEN_BASKET			= 5002;
	public static final int CMD_BSKT_CLOSE					= 5003;
	public static final int CMD_BSKT_CLOSE_ALL				= 5004; /* unused */
	public static final int CMD_BSKT_SAVE					= 5005;
	public static final int CMD_BSKT_SAVE_AS				= 5006; /* unused */
	public static final int CMD_BSKT_SAVE_ALL				= 5007; /* unused */
	public static final int CMD_BSKT_EMPTY					= 5008;
	public static final int CMD_BSKT_EMPTY_TEMP_BASKET 		= 5009;
	/* User Menu 6### */
	public static final int CMD_USER_LOGIN					= 6001;
	public static final int CMD_USER_LOGOFF					= 6002;
	public static final int CMD_USER_ADD_NEW_USER			= 6003; /* CharmStatsPro only */
	public static final int CMD_USER_EDIT_USER				= 6005; /* CharmStatsPro only */
	public static final int CMD_USER_ADD_NEW_PERSON			= 6006; /* CharmStatsPro only */
	public static final int CMD_USER_EDIT_PERSON			= 6007; /* CharmStatsPro only */
	public static final int CMD_USER_CHANGE_PASSWORD		= 6004;
	/* Extra Menu 7### */
	/* Settings Sub-Menu 7### */
	/* Font size Sub-Menu 70## */
	public static final int CMD_EXTRA_INC_FONT_SIZE			= 7001;
	public static final int CMD_EXTRA_DEC_FONT_SIZE			= 7002;
	/* Language Sub-Menu 71## */
	public static final int CMD_EXTRA_LANGUAGE_ENGLISH		= 7101;
	public static final int CMD_EXTRA_LANGUAGE_GERMAN		= 7102;
	/* Troubleshooting Sub-Menu 73## */
	public static final int CMD_EXTRA_RELEASE_LOCK			= 7301;
	public static final int CMD_EXTRA_UNFINISH				= 7303;	/* former CMD_EXTRA_UNPUBLISH */
	public static final int CMD_EXTRA_CHANGE_PASSWORD		= 7302;	
	/* Help Menu 8### */
	public static final int CMD_HELP_HELP					= 8001;
	public static final int CMD_HELP_ABOUT					= 8002;
	/* Help Buttons */
	public static final int CMD_HELP_HELP_ADD_PARTICIPANT	= 8101;
	public static final int CMD_HELP_HELP_ADD_PARTICIPANT_OPEN	= 81011;
	public static final int CMD_HELP_HELP_EDIT_PARTICIPANT	= 81012;
	public static final int CMD_HELP_HELP_ADD_PERSON		= 8102;
	public static final int CMD_HELP_HELP_ADD_PERSON_OPEN	= 81021;
	public static final int CMD_HELP_HELP_EDIT_PERSON		= 81022;
	public static final int CMD_HELP_HELP_ADD_USER			= 8103;
	public static final int CMD_HELP_HELP_ADD_USER_OPEN		= 81031;
	public static final int CMD_HELP_HELP_EDIT_USER			= 81032;
	public static final int CMD_HELP_HELP_EDIT_MEASUREMENT	= 8105;
	public static final int CMD_HELP_HELP_OPEN_MEASUREMENT	= 81051;
	public static final int CMD_HELP_HELP_IMPORT_MEASUREMENT	= 81052;
	public static final int CMD_HELP_HELP_EXPORT_SYNTAX		= 8106;	
	public static final int CMD_HELP_HELP_IMPORT_VARIABLE	= 8104;
	public static final int CMD_HELP_HELP_OPEN_VARIABLE		= 81041;
	public static final int CMD_HELP_HELP_EDIT_VARIABLE		= 81042;
	public static final int CMD_HELP_HELP_EXPORT_VARIABLE	= 81043;
	public static final int CMD_HELP_HELP_LOGIN				= 8107;	
	public static final int CMD_HELP_HELP_CHANGE_PASSWORD	= 8108;	
	public static final int CMD_HELP_HELP_OVERWRITE_PASSWORD= 8109;
	public static final int CMD_HELP_HELP_RELEASE_LOCK		= 8113;
	public static final int CMD_HELP_HELP_REVOKE_FINISHING	= 8114;	
	public static final int CMD_HELP_HELP_OPEN_PROJECT		= 8110;	
	public static final int CMD_HELP_HELP_TROUBLESHOOTING	= 8111;
	public static final int CMD_HELP_HELP_SEARCH			= 8112;	
	/* Data Menu 9### */
	/* Import Sub-Menu 90## */
	public static final int CMD_DATA_IMPORT_VARIABLE		= 9001;
	public static final int CMD_DATA_IMPORT_MEASURE			= 9002;
	/* Edit Sub-Menu 91## */
	public static final int CMD_DATA_EDIT_VARIABLE			= 9101;
	public static final int CMD_DATA_EDIT_MEASURE			= 9102;
	/* Export Sub-Menu 92## */
	public static final int CMD_DATA_EXPORT_VARIABLE		= 9201;
	public static final int CMD_DATA_EXPORT_SYNTAX			= 9202;
	/* Remove Sub-Menu 93## */
	public static final int CMD_EXTRA_REMOVE_VARIABLE 		= 9301;
	public static final int CMD_EXTRA_REMOVE_MEASURE 		= 9302;
	
	/* Main TabPanel */
	/* Project Setup Step TabPanel 10### */
	/* Project Tab 101## */
	public static final int BTN_PRJ_STP_PRJ_TAB_BACK		= 10101;
	public static final int BTN_PRJ_STP_PRJ_TAB_RESET		= 10100;
	public static final int BTN_PRJ_STP_PRJ_TAB_NEXT		= 10102;
	public static final int BTN_PRJ_STP_PRJ_TAB_NOTE		= 10103;
	/* Concept Tab 102## */
	public static final int BTN_PRJ_STP_CON_TAB_BACK		= 10201;
	public static final int BTN_PRJ_STP_CON_TAB_RESET		= 10200;
	public static final int BTN_PRJ_STP_CON_TAB_NEXT		= 10202;
	public static final int BTN_PRJ_STP_CON_TAB_NOTE		= 10203;
	/* Literature Tab 103## */										 /* CharmStatsPro only */
	public static final int BTN_PRJ_STP_LIT_TAB_BACK		= 10301; /* CharmStatsPro only */
	public static final int BTN_PRJ_STP_LIT_TAB_RESET		= 10300; /* CharmStatsPro only */
	public static final int BTN_PRJ_STP_LIT_TAB_NEXT		= 10302; /* CharmStatsPro only */
	public static final int BTN_PRJ_STP_LIT_TAB_NOTE		= 10303; /* CharmStatsPro only */
	/* Target Variable TabPanel 11### */
	/* Target Variable Tab 111## */
	public static final int BTN_TGT_STP_TGT_TAB_BACK		= 11101;
	public static final int BTN_TGT_STP_TGT_TAB_RESET		= 11100;
	public static final int BTN_TGT_STP_TGT_TAB_NEXT		= 11102;
	public static final int BTN_TGT_STP_TGT_TAB_NOTE		= 11103;
	public static final int BTN_TGT_STP_TGT_TAB_IMP			= 11104;
	public static final int BTN_TGT_STP_TGT_TAB_AUTO		= 11105;
	/* Conceptual Step TabPanel 12### */							 /* CharmStatsPro only */
	/* Dimension Tab 121## */										 /* CharmStatsPro only */
	public static final int BTN_CON_STP_DIM_TAB_BACK		= 12101; /* CharmStatsPro only */
	public static final int BTN_CON_STP_DIM_TAB_RESET		= 12100; /* CharmStatsPro only */
	public static final int BTN_CON_STP_DIM_TAB_NEXT		= 12102; /* CharmStatsPro only */
	public static final int BTN_CON_STP_DIM_TAB_NOTE		= 12103; /* CharmStatsPro only */
	/* Specification Tab 122## */									 /* CharmStatsPro only */
	public static final int BTN_CON_STP_SPE_TAB_BACK		= 12201; /* CharmStatsPro only */
	public static final int BTN_CON_STP_SPE_TAB_RESET		= 12200; /* CharmStatsPro only */
	public static final int BTN_CON_STP_SPE_TAB_NEXT		= 12202; /* CharmStatsPro only */
	public static final int BTN_CON_STP_SPE_TAB_NOTE		= 12203; /* CharmStatsPro only */
	/* Map Dimension Instances Tab 123## */							 /* CharmStatsPro only */
	public static final int BTN_CON_STP_MAP_INS_BACK		= 12301; /* CharmStatsPro only */
	public static final int BTN_CON_STP_MAP_INS_RESET		= 12300; /* CharmStatsPro only */
	public static final int BTN_CON_STP_MAP_INS_NEXT		= 12302; /* CharmStatsPro only */
	public static final int BTN_CON_STP_MAP_INS_NOTE		= 12303; /* CharmStatsPro only */
	/* Map Dimension Attributes Tab 124## */						 /* CharmStatsPro only */
	public static final int BTN_CON_STP_MAP_ATR_BACK		= 12401; /* CharmStatsPro only */
	public static final int BTN_CON_STP_MAP_ATR_RESET		= 12400; /* CharmStatsPro only */
	public static final int BTN_CON_STP_MAP_ATR_NEXT		= 12402; /* CharmStatsPro only */
	public static final int BTN_CON_STP_MAP_ATR_NOTE		= 12403; /* CharmStatsPro only */
	/* Map Dimension Characteristics Tab 125## */					 /* CharmStatsPro only */
	public static final int BTN_CON_STP_MAP_CHA_BACK		= 12501; /* CharmStatsPro only */
	public static final int BTN_CON_STP_MAP_CHA_RESET		= 12500; /* CharmStatsPro only */
	public static final int BTN_CON_STP_MAP_CHA_CONFIRM		= 12504; /* CharmStatsPro only */
	public static final int BTN_CON_STP_MAP_CHA_NEXT		= 12502; /* CharmStatsPro only */
	public static final int BTN_CON_STP_MAP_CHA_NOTE		= 12503; /* CharmStatsPro only */	
	/* Operational Step TabPanel 13### */							 /* CharmStatsPro only */
	/* O.S. Instance Tab 131## */									 /* CharmStatsPro only */
	public static final int BTN_OPE_STP_INS_TAB_BACK		= 13101; /* CharmStatsPro only */ 
	public static final int BTN_OPE_STP_INS_TAB_RESET		= 13100; /* CharmStatsPro only */ 
	public static final int BTN_OPE_STP_INS_TAB_NEXT		= 13102; /* CharmStatsPro only */ 
	public static final int BTN_OPE_STP_INS_TAB_NOTE		= 13103; /* CharmStatsPro only */
	/* Indicator Tab 132## */										 /* CharmStatsPro only */
	public static final int BTN_OPE_STP_IND_TAB_BACK		= 13201; /* CharmStatsPro only */
	public static final int BTN_OPE_STP_IND_TAB_RESET		= 13200; /* CharmStatsPro only */
	public static final int BTN_OPE_STP_IND_TAB_NEXT		= 13202; /* CharmStatsPro only */
	public static final int BTN_OPE_STP_IND_TAB_NOTE		= 13203; /* CharmStatsPro only */
	/* Prescription Tab 133## */									 /* CharmStatsPro only */
	public static final int BTN_OPE_STP_PRE_TAB_BACK		= 13301; /* CharmStatsPro only */
	public static final int BTN_OPE_STP_PRE_TAB_RESET		= 13300; /* CharmStatsPro only */
	public static final int BTN_OPE_STP_PRE_TAB_NEXT		= 13302; /* CharmStatsPro only */
	public static final int BTN_OPE_STP_PRE_TAB_NOTE		= 13303; /* CharmStatsPro only */
	/* Map Indicator Instances Tab 134## */							 /* CharmStatsPro only */
	public static final int BTN_OPE_STP_MAP_INS_BACK		= 13401; /* CharmStatsPro only */
	public static final int BTN_OPE_STP_MAP_INS_RESET		= 13400; /* CharmStatsPro only */
	public static final int BTN_OPE_STP_MAP_INS_NEXT		= 13402; /* CharmStatsPro only */
	public static final int BTN_OPE_STP_MAP_INS_NOTE		= 13403; /* CharmStatsPro only */
	/* Map Indicator Attributes Tab 135## */						 /* CharmStatsPro only */
	public static final int BTN_OPE_STP_MAP_ATR_BACK		= 13501; /* CharmStatsPro only */
	public static final int BTN_OPE_STP_MAP_ATR_RESET		= 13500; /* CharmStatsPro only */
	public static final int BTN_OPE_STP_MAP_ATR_NEXT		= 13502; /* CharmStatsPro only */
	public static final int BTN_OPE_STP_MAP_ATR_NOTE		= 13503; /* CharmStatsPro only */
	/* Map Indicator Characteristics Tab 136## */					 /* CharmStatsPro only */
	public static final int BTN_OPE_STP_MAP_CHA_BACK		= 13601; /* CharmStatsPro only */
	public static final int BTN_OPE_STP_MAP_CHA_RESET		= 13600; /* CharmStatsPro only */
	public static final int BTN_OPE_STP_MAP_CHA_CONFIRM		= 13604; /* CharmStatsPro only */
	public static final int BTN_OPE_STP_MAP_CHA_NEXT		= 13602; /* CharmStatsPro only */
	public static final int BTN_OPE_STP_MAP_CHA_NOTE		= 13603; /* CharmStatsPro only */	
	/* Search'n'Compare Step TabPanel 14### */						 /* CharmStatsPro only */
	/* Search Tab 141## */											 /* CharmStatsPro only */
	public static final int BTN_SEA_STP_SEA_TAB_BACK		= 14101; /* CharmStatsPro only */
	public static final int BTN_SEA_STP_SEA_TAB_RESET		= 14100; /* CharmStatsPro only */
	public static final int BTN_SEA_STP_SEA_TAB_NEXT		= 14102; /* CharmStatsPro only */
	public static final int BTN_SEA_STP_SEA_TAB_NOTE		= 14103; /* CharmStatsPro only */
	/* Comparison Tab 142## */										 /* CharmStatsPro only */
	public static final int BTN_SEA_STP_COM_TAB_BACK		= 14201; /* CharmStatsPro only */
	public static final int BTN_SEA_STP_COM_TAB_RESET		= 14200; /* CharmStatsPro only */
	public static final int BTN_SEA_STP_COM_TAB_NEXT		= 14202; /* CharmStatsPro only */
	public static final int BTN_SEA_STP_COM_TAB_NOTE		= 14203; /* CharmStatsPro only */
	public static final int BTN_SEA_STP_COM_TAB_IMP			= 14204; /* CharmStatsPro only */
	/* Compare Values Tab */										 /* CharmStatsPro only */
	public static final int BTN_SEA_STP_VAL_TAB_BACK		= 14301; /* CharmStatsPro only */
	public static final int BTN_SEA_STP_VAL_TAB_RESET		= 14300; /* CharmStatsPro only */
	public static final int BTN_SEA_STP_VAL_TAB_NEXT		= 14302; /* CharmStatsPro only */
	public static final int BTN_SEA_STP_VAL_TAB_NOTE		= 14303; /* CharmStatsPro only */
	/* Data ReCoding Step TabPanel 15### */
	/* D.R. Instance Tab 151## */
	public static final int BTN_DAT_STP_INS_TAB_BACK		= 15101; 
	public static final int BTN_DAT_STP_INS_TAB_RESET		= 15100; 
	public static final int BTN_DAT_STP_INS_TAB_NEXT		= 15102; 
	public static final int BTN_DAT_STP_INS_TAB_NOTE		= 15103; 
	/* Variable Tab 152## */
	public static final int BTN_DAT_STP_VAR_TAB_BACK		= 15201; 
	public static final int BTN_DAT_STP_VAR_TAB_RESET		= 15200; 
	public static final int BTN_DAT_STP_VAR_TAB_NEXT		= 15202; 
	public static final int BTN_DAT_STP_VAR_TAB_NOTE		= 15203; 
	public static final int BTN_DAT_STP_VAR_TAB_IMP			= 15204; 
	/* Value Tab 153## */
	public static final int BTN_DAT_STP_VAL_TAB_BACK		= 15301; 
	public static final int BTN_DAT_STP_VAL_TAB_RESET		= 15300; 
	public static final int BTN_DAT_STP_VAL_TAB_NEXT		= 15302; 
	public static final int BTN_DAT_STP_VAL_TAB_NOTE		= 15303;
	/* Value Tab 157## */
	public static final int BTN_DAT_STP_QUE_TAB_BACK		= 15701; 
	public static final int BTN_DAT_STP_QUE_TAB_RESET		= 15700; 
	public static final int BTN_DAT_STP_QUE_TAB_NEXT		= 15702; 
	public static final int BTN_DAT_STP_QUE_TAB_NOTE		= 15703;
	/* Value Tab 158## */
	public static final int BTN_DAT_STP_STU_TAB_BACK		= 15801; 
	public static final int BTN_DAT_STP_STU_TAB_RESET		= 15800; 
	public static final int BTN_DAT_STP_STU_TAB_NEXT		= 15802; 
	public static final int BTN_DAT_STP_STU_TAB_NOTE		= 15803;
	/* Map Variable Instances Tab 154## */
	public static final int BTN_DAT_STP_MAP_INS_BACK		= 15401; 
	public static final int BTN_DAT_STP_MAP_INS_RESET		= 15400; 
	public static final int BTN_DAT_STP_MAP_INS_NEXT		= 15402; 
	public static final int BTN_DAT_STP_MAP_INS_NOTE		= 15403; 
	/* Map Variable Attributes Tab 155## */
	public static final int BTN_DAT_STP_MAP_ATR_BACK		= 15501; 
	public static final int BTN_DAT_STP_MAP_ATR_RESET		= 15500; 
	public static final int BTN_DAT_STP_MAP_ATR_NEXT		= 15502; 
	public static final int BTN_DAT_STP_MAP_ATR_NOTE		= 15503; 
	/* Map Variable Characteristics Tab 156## */
	public static final int BTN_DAT_STP_MAP_CHA_BACK		= 15601; 
	public static final int BTN_DAT_STP_MAP_CHA_RESET		= 15600;
	public static final int BTN_DAT_STP_MAP_CHA_CONFIRM		= 15604;
	public static final int BTN_DAT_STP_MAP_CHA_NEXT		= 15602; 
	public static final int BTN_DAT_STP_MAP_CHA_NOTE		= 15603; 
	
	/* Miscellaneous 9### */
	/* Model Modification 991## */
	public static final int MOD_MOD 						= 99101;
	/* Hidden Buttons 992## */
	public static final int BTN_FRM_PRJ_OPN_PRJ 			= 99201;
	public static final int BTN_FRM_FRM_UPD_BAS_TREE		= 99202;
	public static final int BTN_DIA_SEA_UPD_BAS_TREE		= 99202;
	public static final int BTN_TAB_COM_VAL_REFILL 			= 99203;
	/* Basket Handling 993## */
	public static final int ADD_TO_BASKET 					= 99303;	
	/* Graph View 994## */
	public static final int BTN_FRM_GRP_SHW_MEA_GRAPH		= 99401;
	public static final int BTN_FRM_GRP_SHW_CON_GRAPH		= 99402;
	public static final int BTN_FRM_GRP_SHW_OPE_GRAPH		= 99403;
	public static final int BTN_FRM_GRP_SHW_DAT_GRAPH		= 99404;
	public static final int BTN_FRM_GRP_PRINT_GRAPH			= 99405;
	public static final int BTN_FRM_GRP_SAVE_GRAPH			= 99406;
	public static final int BTN_FRM_GRP_SHW_MAP_MEA_GRAPH	= 99407;
	public static final int BTN_FRM_GRP_SHW_OVER_GRAPH		= 99408;
	public static final int BTN_FRM_GRP_SHW_MAP_CON_GRAPH	= 99409;
	public static final int BTN_FRM_GRP_SHW_MAP_OPE_GRAPH	= 99410;
	public static final int BTN_FRM_GRP_SHW_MAP_GRAPH		= 99411;
	/* Tool Bar 995## */
	public static final int BTN_TB_SHOW_FORM 				= 99501;
	public static final int BTN_TB_SHOW_GRAPH 				= 99502;
	public static final int BTN_TB_SHOW_REPORT 				= 99503;
	/* report View 996##*/
	public static final int BTN_FRM_REP_SAVE_REPORT 		= 99601;
	
}

package org.gesis.charmstats;

/**
 * 
 * @author	Martin Friedrichs
 * @since	0.1
 *
 */
public class ActionCommandText {
	
	/* MenuBar */
	/* File Menu */
	public static final String CMD_FILE_EXIT					= "cmd.file.exit";
	/* Edit Menu */
	public static final String CMD_EDIT_UNDO					= "cmd.edit.undo"; 				/* unused */
	public static final String CMD_EDIT_REDO					= "cmd.edit.redo"; 				/* unused */
	public static final String CMD_EDIT_CUT						= "cmd.edit.cut"; 				/* unused */
	public static final String CMD_EDIT_COPY					= "cmd.edit.copy"; 				/* unused */
	public static final String CMD_EDIT_PASTE					= "cmd.edit.paste"; 			/* unused */
	public static final String CMD_EDIT_REMOVE					= "cmd.edit.remove"; 			/* unused */
	/* Search Menu */
	public static final String CMD_SRH_SEARCH					= "cmd.srh.search";
	/* Project Menu */
	public static final String CMD_PRJ_NEW_PROJECT				= "cmd.prj.new_project";
	public static final String CMD_PRJ_OPEN_PROJECT				= "cmd.prj.open_project";
	public static final String CMD_PRJ_CLOSE					= "cmd.prj.close";
	public static final String CMD_PRJ_CLOSE_ALL				= "cmd.prj.close_all";			/* unused */
	public static final String CMD_PRJ_SAVE						= "cmd.prj.save";
	public static final String CMD_PRJ_SAVE_AS					= "cmd.prj.save_as";			/* unused */
	public static final String CMD_PRJ_SAVE_ALL					= "cmd.prj.save_all";			/* unused */
	public static final String CMD_PRJ_FINISH					= "cmd.prj.finish";				/* former CMD_PRJ_PUBLISH, cmd.prj.publish */
	public static final String CMD_PRJ_REMOVE					= "cmd.prj.remove";
	public static final String CMD_PRJ_ADD_PARTICIPANT			= "cmd.prj.add_participant";	/* CharmStatsPro only */
	public static final String CMD_PRJ_EDIT_PARTICIPANT			= "cmd.prj.edit_participant";	/* CharmStatsPro only */
	/* Basket Menu */
	public static final String CMD_BSKT_NEW_BASKET				= "cmd.bskt.new_basket";		/* unused */
	public static final String CMD_BSKT_OPEN_BASKET				= "cmd.bskt.open_basket";
	public static final String CMD_BSKT_CLOSE					= "cmd.bskt.close";
	public static final String CMD_BSKT_CLOSE_ALL				= "cmd.bskt.close_all"; 		/* unused */
	public static final String CMD_BSKT_SAVE					= "cmd.bskt.save";
	public static final String CMD_BSKT_SAVE_AS					= "cmd.bskt.save_as"; 			/* unused */
	public static final String CMD_BSKT_SAVE_ALL				= "cmd.bskt.save_all"; 			/* unused */
	public static final String CMD_BSKT_EMPTY					= "cmd.bskt.empty";
	public static final String CMD_BSKT_EMPTY_TEMP_BASKET 		= "cmd.bskt_empty_temp_basket";
	/* User Menu */
	public static final String CMD_USER_LOGIN					= "cmd.user.login";
	public static final String CMD_USER_LOGOFF					= "cmd.user.logoff";
	public static final String CMD_USER_ADD_NEW_USER			= "cmd.user.add_new_user";		/* CharmStatsPro only */
	public static final String CMD_USER_EDIT_USER				= "cmd.user.edit_user";			/* CharmStatsPro only */
	public static final String CMD_USER_ADD_NEW_PERSON			= "cmd.user.add_new_person"; 	/* CharmStatsPro only */
	public static final String CMD_USER_EDIT_PERSON				= "cmd.user.edit_person";		/* CharmStatsPro only */
	public static final String CMD_USER_CHANGE_PASSWORD			= "cmd.user.change_password";
	/* Extra Menu */
	/* Setting Sub-Menu */
	public static final String CMD_EXTRA_INC_FONT_SIZE 			= "cmd.extra.inc_font_size";
	public static final String CMD_EXTRA_DEC_FONT_SIZE			= "cmd.extra.dec_font_size";
	/* Language Sub-Menu */
	public static final String CMD_EXTRA_LANGUAGE_ENGLISH		= "cmd.extra.language_english";
	public static final String CMD_EXTRA_LANGUAGE_GERMAN		= "cmd.extra.language_german";
	/* Troubleshooting Sub-Menu */
	public static final String CMD_EXTRA_RELEASE_LOCK			= "cmd.extra.release_lock";
	public static final String CMD_EXTRA_CHANGE_PASSWORD		= "cmd.extra.change_password";
	public static final String CMD_EXTRA_UNFINISH				= "cmd.extra.unfinish";		/* former CMD_EXTRA_UNPUBLISH, cmd.extra.unpublish */
	/* Data Menu */
	/* Import Sub-Menu */
	public static final String CMD_EXTRA_IMPORT_VARIABLE		= "cmd.extra.import_variable";
	public static final String CMD_EXTRA_EDIT_VARIABLE			= "cmd.extra.edit_variable";
	/* Exit Sub-Menu */
	public static final String CMD_EXTRA_IMPORT_MEASURE			= "cmd.extra.import_measure";
	public static final String CMD_EXTRA_EDIT_MEASURE			= "cmd.extra.edit_measure";
	/* Export Sub-Menu */
	public static final String CMD_EXTRA_EXPORT_SYNTAX			= "cmd.extra.export_syntax";
	public static final String CMD_EXTRA_EXPORT_VARIABLE		= "cmd.extra.export_variable";
	/* Remove Sub-Menu */
	public static final String CMD_EXTRA_REMOVE_VARIABLE		= "cmd.extra.remove_variable";
	public static final String CMD_EXTRA_REMOVE_MEASURE			= "cmd.extra.remove_measure";
	/* Help Menu */
	public static final String CMD_HELP_HELP					= "cmd.help.help";
	public static final String CMD_HELP_HELP_ADD_PARTICIPANT	= "cmd.help.help.add_participant";
	public static final String CMD_HELP_HELP_ADD_PARTICIPANT_OPEN	= "cmd.help.help.add_participant_open";
	public static final String CMD_HELP_HELP_EDIT_PARTICIPANT	= "cmd.help.help.edit_participant";
	public static final String CMD_HELP_HELP_ADD_PERSON			= "cmd.help.help.add_person";
	public static final String CMD_HELP_HELP_ADD_PERSON_OPEN		= "cmd.help.help.add_person_open";
	public static final String CMD_HELP_HELP_EDIT_PERSON		= "cmd.help.help.edit_person";
	public static final String CMD_HELP_HELP_ADD_USER			= "cmd.help.help.add_user";
	public static final String CMD_HELP_HELP_ADD_USER_OPEN		= "cmd.help.help.add_user_open";
	public static final String CMD_HELP_HELP_EDIT_USER			= "cmd.help.help.edit_user";
	public static final String CMD_HELP_HELP_EDIT_MEASUREMENT	= "cmd.help.help.edit_measurement";
	public static final String CMD_HELP_HELP_OPEN_MEASUREMENT	= "cmd.help.help.edit_measurement_open";
	public static final String CMD_HELP_HELP_IMPORT_MEASUREMENT	= "cmd.help.help.import_measurement";
	public static final String CMD_HELP_HELP_EXPORT_SYNTAX		= "cmd.help.help.export_syntax";	
	public static final String CMD_HELP_HELP_IMPORT_VARIABLE	= "cmd.help.help.import_variable";
	public static final String CMD_HELP_HELP_OPEN_VARIABLE	= "cmd.help.help.import_variable_open";
	public static final String CMD_HELP_HELP_EDIT_VARIABLE		= "cmd.help.help.edit_variable";
	public static final String CMD_HELP_HELP_EXPORT_VARIABLE	= "cmd.help.help.export_variable";	
	public static final String CMD_HELP_HELP_LOGIN				= "cmd.help.help.login";
	public static final String CMD_HELP_HELP_CHANGE_PASSWORD	= "cmd.help.help.change_password";	
	public static final String CMD_HELP_HELP_OVERWRITE_PASSWORD	= "cmd.help.help.overwrite_password";
	public static final String CMD_HELP_HELP_RELEASE_LOCK		= "cmd.help.help.release_lock";
	public static final String CMD_HELP_HELP_REVOKE_FINISHING	= "cmd.help.help.revoke_finishing";	
	public static final String CMD_HELP_HELP_OPEN_PROJECT		= "cmd.help.help.open_project";	
	public static final String CMD_HELP_HELP_TROUBLESHOOTING	= "cmd.help.help.troubleshooting";
	public static final String CMD_HELP_HELP_SEARCH				= "cmd.help.help.search";

	public static final String CMD_HELP_ABOUT					= "cmd.help.about";
	
	/* Main TabPanel */
	/* Project Setup Step TabPanel*/
	/* Project Tab */
	public static final String BTN_PRJ_STP_PRJ_TAB_BACK			= "btn.pro_stp.pro_tab.back";
	public static final String BTN_PRJ_STP_PRJ_TAB_RESET		= "btn.pro_stp.pro_tab.reset";
	public static final String BTN_PRJ_STP_PRJ_TAB_NEXT			= "btn.pro_stp.pro_tab.next";
	public static final String BTN_PRJ_STP_PRJ_TAB_NOTE			= "btn.pro_stp.pro_tab.note";
	/* Concept Tab */
	public static final String BTN_PRJ_STP_CON_TAB_BACK			= "btn.pro_stp.con_tab.back";
	public static final String BTN_PRJ_STP_CON_TAB_RESET		= "btn.pro_stp.con_tab.reset";
	public static final String BTN_PRJ_STP_CON_TAB_NEXT			= "btn.pro_stp.con_tab.next";
	public static final String BTN_PRJ_STP_CON_TAB_NOTE			= "btn.pro_stp.con_tab.note";
	/* Literature Tab */																		/* CharmStatsPro only */				
	public static final String BTN_PRJ_STP_LIT_TAB_BACK			= "btn.pro_stp.lit_tab.back";	/* CharmStatsPro only */
	public static final String BTN_PRJ_STP_LIT_TAB_RESET		= "btn.pro_stp.lit_tab.reset";	/* CharmStatsPro only */
	public static final String BTN_PRJ_STP_LIT_TAB_NEXT			= "btn.pro_stp.lit_tab.next";	/* CharmStatsPro only */
	public static final String BTN_PRJ_STP_LIT_TAB_NOTE			= "btn.pro_stp.lit_tab.note";	/* CharmStatsPro only */
	/* Target Variable TabPanel */
	/* Target Variable Tab */
	public static final String BTN_TGT_STP_TGT_TAB_BACK			= "btn.tgt_stp.tgt_tab.back";
	public static final String BTN_TGT_STP_TGT_TAB_RESET		= "btn.tgt_stp.tgt_tab.reset";
	public static final String BTN_TGT_STP_TGT_TAB_NEXT			= "btn.tgt_stp.tgt_tab.next";
	public static final String BTN_TGT_STP_TGT_TAB_NOTE			= "btn.tgt_stp.tgt_tab.note";
	public static final String BTN_TGT_STP_TGT_TAB_IMP			= "btn.tgt_stp.tgt_tab.imp";
	public static final String BTN_TGT_STP_TGT_TAB_AUTO			= "btn_tgt_stp_tgt_tab_auto";
	/* Conceptual Step TabPanel*/																/* CharmStatsPro only */
	/* Dimension Tab */																			/* CharmStatsPro only */	
	public static final String BTN_CON_STP_DIM_TAB_BACK			= "btn.con_stp.dim_tab.back";	/* CharmStatsPro only */
	public static final String BTN_CON_STP_DIM_TAB_RESET		= "btn.con_stp.dim_tab.reset";	/* CharmStatsPro only */
	public static final String BTN_CON_STP_DIM_TAB_NEXT			= "btn.con_stp.dim_tab.next";	/* CharmStatsPro only */
	public static final String BTN_CON_STP_DIM_TAB_NOTE			= "btn.con_stp.dim_tab.note";	/* CharmStatsPro only */
	/* Specification Tab */																		/* CharmStatsPro only */			
	public static final String BTN_CON_STP_SPE_TAB_BACK			= "btn.con_stp.spe_tab.back";	/* CharmStatsPro only */
	public static final String BTN_CON_STP_SPE_TAB_RESET		= "btn.con_stp.spe_tab.reset";	/* CharmStatsPro only */
	public static final String BTN_CON_STP_SPE_TAB_NEXT			= "btn.con_stp.spe_tab.next";	/* CharmStatsPro only */
	public static final String BTN_CON_STP_SPE_TAB_NOTE			= "btn.con_stp.spe_tab.note";	/* CharmStatsPro only */
	/* Map Dimension Instances Tab*/															/* CharmStatsPro only */
	public static final String BTN_CON_STP_MAP_INS_BACK			= "btn.con_stp.map_ins.back";	/* CharmStatsPro only */
	public static final String BTN_CON_STP_MAP_INS_RESET		= "btn.con_stp.map_ins.reset";	/* CharmStatsPro only */
	public static final String BTN_CON_STP_MAP_INS_NEXT			= "btn.con_stp.map_ins.next";	/* CharmStatsPro only */
	public static final String BTN_CON_STP_MAP_INS_NOTE			= "btn.con_stp.map_ins.note";	/* CharmStatsPro only */
	/* Map Dimension Attributes Tab*/															/* CharmStatsPro only */
	public static final String BTN_CON_STP_MAP_ATR_BACK			= "btn.con_stp.map_atr.back";	/* CharmStatsPro only */
	public static final String BTN_CON_STP_MAP_ATR_RESET		= "btn.con_stp.map_atr.reset";	/* CharmStatsPro only */
	public static final String BTN_CON_STP_MAP_ATR_NEXT			= "btn.con_stp.map_atr.next";	/* CharmStatsPro only */
	public static final String BTN_CON_STP_MAP_ATR_NOTE			= "btn.con_stp.map_atr.note";	/* CharmStatsPro only */
	/* Map Dimension Characteristics Tab*/														/* CharmStatsPro only */	
	public static final String BTN_CON_STP_MAP_CHA_BACK			= "btn.con_stp.map_cha.back";	/* CharmStatsPro only */
	public static final String BTN_CON_STP_MAP_CHA_RESET		= "btn.con_stp.map_cha.reset";	/* CharmStatsPro only */
	public static final String BTN_CON_STP_MAP_CHA_CONFIRM		= "btn.con_stp.map_cha.confirm";/* CharmStatsPro only */
	public static final String BTN_CON_STP_MAP_CHA_NEXT			= "btn.con_stp.map_cha.next";	/* CharmStatsPro only */
	public static final String BTN_CON_STP_MAP_CHA_NOTE			= "btn.con_stp.map_cha.note";	/* CharmStatsPro only */
	/* Operational Step TabPanel*/																/* CharmStatsPro only */
	/* O.S. Instance Tab */																		/* CharmStatsPro only */
	public static final String BTN_OPE_STP_INS_TAB_BACK			= "btn.ope_stp.ins_tab.back";	/* CharmStatsPro only */
	public static final String BTN_OPE_STP_INS_TAB_RESET		= "btn.ope_stp.ins_tab.reset";	/* CharmStatsPro only */
	public static final String BTN_OPE_STP_INS_TAB_NEXT			= "btn.ope_stp.ins_tab.next";	/* CharmStatsPro only */
	public static final String BTN_OPE_STP_INS_TAB_NOTE			= "btn.ope_stp.ins_tab.note";	/* CharmStatsPro only */
	/* Indicator Tab */																			/* CharmStatsPro only */
	public static final String BTN_OPE_STP_IND_TAB_BACK			= "btn.ope_stp.ind_tab.back";	/* CharmStatsPro only */
	public static final String BTN_OPE_STP_IND_TAB_RESET		= "btn.ope_stp.ind_tab.reset";	/* CharmStatsPro only */
	public static final String BTN_OPE_STP_IND_TAB_NEXT			= "btn.ope_stp.ind_tab.next";	/* CharmStatsPro only */
	public static final String BTN_OPE_STP_IND_TAB_NOTE			= "btn.ope_stp.ind_tab.note";	/* CharmStatsPro only */
	/* Prescription Tab */																		/* CharmStatsPro only */
	public static final String BTN_OPE_STP_PRE_TAB_BACK			= "btn.ope_stp.pre_tab.back";	/* CharmStatsPro only */
	public static final String BTN_OPE_STP_PRE_TAB_RESET		= "btn.ope_stp.pre_tab.reset";	/* CharmStatsPro only */
	public static final String BTN_OPE_STP_PRE_TAB_NEXT			= "btn.ope_stp.pre_tab.next";	/* CharmStatsPro only */
	public static final String BTN_OPE_STP_PRE_TAB_NOTE			= "btn.ope_stp.pre_tab.note";	/* CharmStatsPro only */
	/* Map Indicator Instances Tab*/															/* CharmStatsPro only */
	public static final String BTN_OPE_STP_MAP_INS_BACK			= "btn.ope_stp.map_ins.back";	/* CharmStatsPro only */
	public static final String BTN_OPE_STP_MAP_INS_RESET		= "btn.ope_stp.map_ins.reset";	/* CharmStatsPro only */
	public static final String BTN_OPE_STP_MAP_INS_NEXT			= "btn.ope_stp.map_ins.next";	/* CharmStatsPro only */
	public static final String BTN_OPE_STP_MAP_INS_NOTE			= "btn.ope_stp.map_ins.note";	/* CharmStatsPro only */
	/* Map Indicator Attributes Tab*/															/* CharmStatsPro only */
	public static final String BTN_OPE_STP_MAP_ATR_BACK			= "btn.ope_stp.map_atr.back";	/* CharmStatsPro only */
	public static final String BTN_OPE_STP_MAP_ATR_RESET		= "btn.ope_stp.map_atr.reset";	/* CharmStatsPro only */
	public static final String BTN_OPE_STP_MAP_ATR_NEXT			= "btn.ope_stp.map_atr.next";	/* CharmStatsPro only */
	public static final String BTN_OPE_STP_MAP_ATR_NOTE			= "btn.ope_stp.map_atr.note";	/* CharmStatsPro only */
	/* Map Indicator Characteristics Tab*/														/* CharmStatsPro only */
	public static final String BTN_OPE_STP_MAP_CHA_BACK			= "btn.ope_stp.map_cha.back";	/* CharmStatsPro only */
	public static final String BTN_OPE_STP_MAP_CHA_RESET		= "btn.ope_stp.map_cha.reset";	/* CharmStatsPro only */
	public static final String BTN_OPE_STP_MAP_CHA_CONFIRM		= "btn.ope_stp.map_cha.confirm";/* CharmStatsPro only */
	public static final String BTN_OPE_STP_MAP_CHA_NEXT			= "btn.ope_stp.map_cha.next";	/* CharmStatsPro only */
	public static final String BTN_OPE_STP_MAP_CHA_NOTE			= "btn.ope_stp.map_cha.note";	/* CharmStatsPro only */
	/* Search'n'Compare Step TabPanel*/															/* CharmStatsPro only */				
	/* Search Tab */																			/* CharmStatsPro only */
	public static final String BTN_SEA_STP_SEA_TAB_BACK			= "btn.sea_stp.sea_tab.back";	/* CharmStatsPro only */
	public static final String BTN_SEA_STP_SEA_TAB_RESET		= "btn.sea_stp.sea_tab.reset";	/* CharmStatsPro only */
	public static final String BTN_SEA_STP_SEA_TAB_NEXT			= "btn.sea_stp.sea_tab.next";	/* CharmStatsPro only */
	public static final String BTN_SEA_STP_SEA_TAB_NOTE			= "btn.sea_stp.sea_tab.note";	/* CharmStatsPro only */
	/* Compare Metadata Tab */																	/* CharmStatsPro only */
	public static final String BTN_SEA_STP_COM_TAB_BACK			= "btn.sea_stp.com_tab.back";	/* CharmStatsPro only */
	public static final String BTN_SEA_STP_COM_TAB_RESET		= "btn.sea_stp.com_tab.reset";	/* CharmStatsPro only */
	public static final String BTN_SEA_STP_COM_TAB_NEXT			= "btn.sea_stp.com_tab.next";	/* CharmStatsPro only */
	public static final String BTN_SEA_STP_COM_TAB_NOTE			= "btn.sea_stp.com_tab.note";	/* CharmStatsPro only */
	public static final String BTN_SEA_STP_COM_TAB_IMP			= "btn.sea_stp.com_tab.imp";	/* CharmStatsPro only */
	/* Compare Values Tab */																	/* CharmStatsPro only */	
	public static final String BTN_SEA_STP_VAL_TAB_BACK			= "btn.sea_stp.val_tab.back";	/* CharmStatsPro only */
	public static final String BTN_SEA_STP_VAL_TAB_RESET		= "btn.sea_stp.val_tab.reset";	/* CharmStatsPro only */
	public static final String BTN_SEA_STP_VAL_TAB_NEXT			= "btn.sea_stp.val_tab.next";	/* CharmStatsPro only */
	public static final String BTN_SEA_STP_VAL_TAB_NOTE			= "btn.sea_stp.val_tab.note";	/* CharmStatsPro only */
	/* Data ReCoding Step TabPanel*/
	/* D.R. Instance Tab */
	public static final String BTN_DAT_STP_INS_TAB_BACK			= "btn.dat_stp.ins_tab.back";
	public static final String BTN_DAT_STP_INS_TAB_RESET		= "btn.dat_stp.ins_tab.reset";
	public static final String BTN_DAT_STP_INS_TAB_NEXT			= "btn.dat_stp.ins_tab.next";
	public static final String BTN_DAT_STP_INS_TAB_NOTE			= "btn.dat_stp.ins_tab.note";
	public static final String BTN_DAT_STP_ADD_INS 				= "btn.dat_stp.add_ins";
	/* Variable Tab */
	public static final String BTN_DAT_STP_VAR_TAB_BACK			= "btn.dat_stp.var_tab.back";
	public static final String BTN_DAT_STP_VAR_TAB_RESET		= "btn.dat_stp.var_tab.reset";
	public static final String BTN_DAT_STP_VAR_TAB_NEXT			= "btn.dat_stp.var_tab.next";
	public static final String BTN_DAT_STP_VAR_TAB_NOTE			= "btn.dat_stp.var_tab.note";
	public static final String BTN_DAT_STP_VAR_TAB_IMP			= "btn.dat_stp.var_tab.imp";
	/* Value Tab */
	public static final String BTN_DAT_STP_VAL_TAB_BACK			= "btn.dat_stp.val_tab.back";
	public static final String BTN_DAT_STP_VAL_TAB_RESET		= "btn.dat_stp.val_tab.reset";
	public static final String BTN_DAT_STP_VAL_TAB_NEXT			= "btn.dat_stp.val_tab.next";
	public static final String BTN_DAT_STP_VAL_TAB_NOTE			= "btn.dat_stp.val_tab.note";
	/* Question Tab */
	public static final String BTN_DAT_STP_QUE_TAB_BACK			= "btn.dat_stp.que_tab.back";
	public static final String BTN_DAT_STP_QUE_TAB_RESET		= "btn.dat_stp.que_tab.reset";
	public static final String BTN_DAT_STP_QUE_TAB_NEXT			= "btn.dat_stp.que_tab.next";
	public static final String BTN_DAT_STP_QUE_TAB_NOTE			= "btn.dat_stp.que_tab.note";
	/* Study Tab */
	public static final String BTN_DAT_STP_STU_TAB_BACK			= "btn.dat_stp.stu_tab.back";
	public static final String BTN_DAT_STP_STU_TAB_RESET		= "btn.dat_stp.stu_tab.reset";
	public static final String BTN_DAT_STP_STU_TAB_NEXT			= "btn.dat_stp.stu_tab.next";
	public static final String BTN_DAT_STP_STU_TAB_NOTE			= "btn.dat_stp.stu_tab.note";
	/* Map Variable Instances Tab*/
	public static final String BTN_DAT_STP_MAP_INS_BACK			= "btn.dat_stp.map_ins.back";
	public static final String BTN_DAT_STP_MAP_INS_RESET		= "btn.dat_stp.map_ins.reset";
	public static final String BTN_DAT_STP_MAP_INS_NEXT			= "btn.dat_stp.map_ins.next";
	public static final String BTN_DAT_STP_MAP_INS_NOTE			= "btn.dat_stp.map_ins.note";
	/* Map Variable Attributes Tab*/
	public static final String BTN_DAT_STP_MAP_ATR_BACK			= "btn.dat_stp.map_atr.back";
	public static final String BTN_DAT_STP_MAP_ATR_RESET		= "btn.dat_stp.map_atr.reset";
	public static final String BTN_DAT_STP_MAP_ATR_NEXT			= "btn.dat_stp.map_atr.next";
	public static final String BTN_DAT_STP_MAP_ATR_NOTE			= "btn.dat_stp.map_atr.note";
	/* Map Variable Characteristics Tab*/
	public static final String BTN_DAT_STP_MAP_CHA_BACK			= "btn.dat_stp.map_cha.back";
	public static final String BTN_DAT_STP_MAP_CHA_RESET		= "btn.dat_stp.map_cha.reset";
	public static final String BTN_DAT_STP_MAP_CHA_CONFIRM		= "btn.dat_stp.map_cha.confirm";
	public static final String BTN_DAT_STP_MAP_CHA_NEXT			= "btn.dat_stp.map_cha.next";
	public static final String BTN_DAT_STP_MAP_CHA_NOTE			= "btn.dat_stp.map_cha.note";
	
	/* Miscellaneous */
	/* Model Modification */
	public static final String MOD_MOD							= "mod_mod";
	/* Hidden Buttons */
	public static final String BTN_FRM_PRJ_OPN_PRJ 				= "btn.frm.prj.opn.prj";
	public static final String BTN_FRM_FRM_UPD_BAS_TREE 		= "btn.frm.frm.upd.bas.tree";
	public static final String BTN_DIA_SEA_UPD_BAS_TREE			= "btn.dia.sea.upd.bas.tree";
	public static final String BTN_TAB_COM_VAL_REFILL			= "btn.tab_com_val_refill";
	/* Tool Bar */
	public static final String BTN_TB_SHOW_FORM 				= "btn.tb.show_form";
	public static final String BTN_TB_SHOW_GRAPH 				= "btn.tb.show_graph";
	public static final String BTN_TB_SHOW_REPORT 				= "btn.tb.show_report";
	/* Graph View */
	public static final String BTN_FRM_GRP_SHW_MEA_GRAPH 		= "btn.frm.grp.shw_mea_graph";
	public static final String BTN_FRM_GRP_SHW_CON_GRAPH 		= "btn.frm.grp.shw_con_graph";
	public static final String BTN_FRM_GRP_SHW_OPE_GRAPH 		= "btn.frm.grp.shw_ope_graph";
	public static final String BTN_FRM_GRP_SHW_DAT_GRAPH 		= "btn.frm.grp.shw_dat_graph";
	public static final String BTN_FRM_GRP_PRINT_GRAPH 			= "btn.frm.grp.print_graph";
	public static final String BTN_FRM_GRP_SAVE_GRAPH 			= "btn.frm.grp.save_graph";
	public static final String BTN_FRM_GRP_SHW_MAP_MEA_GRAPH	= "btn.frm.grp.shw_map_mea_graph";
	public static final	String BTN_FRM_GRP_SHW_OVER_GRAPH		= "btn.frm.grp.shw_over_graph";
	public static final	String BTN_FRM_GRP_SHW_MAP_CON_GRAPH	= "btn.frm.grp.shw_map_con_graph";
	public static final	String BTN_FRM_GRP_SHW_MAP_OPE_GRAPH	= "btn.frm.grp.shw_map_ope_graph";
	public static final	String BTN_FRM_GRP_SHW_MAP_GRAPH		= "btn.frm.grp.shw_map_graph";
	/* Report View */
	public static final String BTN_FRM_REP_SAVE_REPORT 			= "btn.frm.rep.save_report";
	
	
}

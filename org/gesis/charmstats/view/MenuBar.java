package org.gesis.charmstats.view;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.MenuElement;
import javax.swing.UIManager;

import org.gesis.charmstats.ActionCommandText;
import org.gesis.charmstats.model.CStatsModel;
import org.gesis.charmstats.model.ParticipantRole;
import org.gesis.charmstats.model.UserRole;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class MenuBar extends JMenuBar {		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/* RESOURCES */
	public static final String US_FLAG_ICON				= "org/gesis/charmstats/resources/us_uk_flag.gif";
	public static final String DE_FLAG_ICON				= "org/gesis/charmstats/resources/de_flag.gif";
	
	public static final String BUNDLE	= "org.gesis.charmstats.resources.MenuBundle";
	
	public static final String MNU_FILE_FILE			= "mnu_file_file";
	public static final String MNU_FILE_EXIT			= "mnu_file_exit";
	public static final String MNU_EDIT_EDIT			= "mnu_edit_edit";
	public static final String MNU_EDIT_UNDO			= "mnu_edit_undo";
	public static final String MNU_EDIT_REDO			= "mnu_edit_redo";
	public static final String MNU_EDIT_CUT				= "mnu_edit_cut";
	public static final String MNU_EDIT_COPY			= "mnu_edit_copy";
	public static final String MNU_EDIT_PASTE			= "mnu_edit_paste";
	public static final String MNU_EDIT_REMOVE			= "mnu_edit_remove";
	public static final String MNU_SRH_SRH				= "mnu_srh_srh";
	public static final String MNU_SRH_SEARCH			= "mnu_srh_search";
	public static final String MNU_PRJ_PRJ				= "mnu_prj_prj";
	public static final String MNU_PRJ_NEW_PROJECT		= "mnu_prj_new_project";
	public static final String MNU_PRJ_OPEN_PROJECT		= "mnu_prj_open_project";
	public static final String MNU_PRJ_CLOSE			= "mnu_prj_close";
	public static final String MNU_PRJ_CLOSE_ALL		= "mnu_prj_close_all";
	public static final String MNU_PRJ_SAVE				= "mnu_prj_save";
	public static final String MNU_PRJ_SAVE_AS			= "mnu_prj_save_as";
	public static final String MNU_PRJ_SAVE_ALL			= "mnu_prj_save_all";
	public static final String MNU_PRJ_PUBLISH			= "mnu_prj_publish";
	public static final String MNU_PRJ_REMOVE			= "mnu_prj_remove";
	public static final String MNU_PRJ_ADD_PARTICIPANT	= "mnu_prj_add_participant";
	public static final String MNU_PRJ_EDIT_PARTICIPANT	= "mnu_prj_edit_participant";
	public static final String MNU_BSKT_BSKT			= "mnu_bskt_bskt";
	public static final String MNU_BSKT_NEW_BASKET		= "mnu_bskt_new_basket";
	public static final String MNU_BSKT_OPEN_BASKET		= "mnu_bskt_open_basket";
	public static final String MNU_BSKT_CLOSE			= "mnu_bskt_close";
	public static final String MNU_BSKT_CLOSE_ALL		= "mnu_bskt_close_all";
	public static final String MNU_BSKT_SAVE			= "mnu_bskt_save";
	public static final String MNU_BSKT_SAVE_AS			= "mnu_bskt_save_as";
	public static final String MNU_BSKT_SAVE_ALL		= "mnu_bskt_save_all";
	public static final String MNU_BSKT_EMPTY			= "mnu_bskt_empty";
	public static final String MNU_BSKT_EMPTY_TEMP		= "mnu_bskt_empty_temp_basket";
	public static final String MNU_USER_USER			= "mnu_user_user";
	public static final String MNU_USER_LOGIN			= "mnu_user_login";
	public static final String MNU_USER_LOGOFF			= "mnu_user_logoff";
	public static final String MNU_USER_NEW_USER		= "mnu_user_new_user";
	public static final String MNU_USER_EDIT_USER		= "mnu_user_edit_user";
	public static final String MNU_USER_NEW_PERSON		= "mnu_user_new_person";
	public static final String MNU_USER_EDIT_PERSON		= "mnu_user_edit_person";
	public static final String MNU_USER_NEW_PASSWORD	= "mnu_user_new_password";
	public static final String MNU_DATA_DATA			= "mnu_data_data";
	public static final String MNU_DATA_IMPORT			= "mnu_data_import";
	public static final String MNU_DATA_EDIT			= "mnu_data_edit";
	public static final String MNU_DATA_EXPORT			= "mnu_data_export";
	public static final String MNU_DATA_REMOVE			= "mnu_data_remove"; 
	public static final String MNU_DATA_IMPORT_VARIABLE	= "mnu_data_import_variable";
	public static final String MNU_DATA_IMPORT_MEASURE	= "mnu_data_import_measure";
	public static final String MNU_DATA_EDIT_VARIABLE	= "mnu_data_edit_variable";
	public static final String MNU_DATA_EDIT_MEASURE	= "mnu_data_edit_measure";
	public static final String MNU_DATA_EXPORT_VARIABLE	= "mnu_data_export_variable";
	public static final String MNU_DATA_EXPORT_SYNTAX	= "mnu_data_export_syntax";
	public static final String MNU_DATA_REMOVE_VARIABLE	= "mnu_data_remove_variable";
	public static final String MNU_DATA_REMOVE_MEASURE	= "mnu_data_remove_measure";
	public static final String MNU_EXTRA_EXTRA			= "mnu_extra_extra";
	public static final String MNU_EXTRA_SETTINGS		= "mnu_extra_settings";
	public static final String MNU_EXTRA_FONT_SIZE		= "mnu_extra_font_size";
	public static final String MNU_EXTRA_INC_FONT_SIZE	= "mnu_extra_inc_font_size";
	public static final String MNU_EXTRA_DEC_FONT_SIZE	= "mnu_extra_dec_font_size";
	public static final String MNU_EXTRA_LANGUAGE		= "mnu_extra_language";
	public static final String MNU_EXTRA_LANGUAGE_ENG	= "mnu_extra_language_english";
	public static final String MNU_EXTRA_LANGUAGE_GER	= "mnu_extra_language_german";
	public static final String MNU_EXTRA_TROUBLESHOOTING	= "mnu_extra_troubleshooting";
	public static final String MNU_EXTRA_RELEASE_LOCK	= "mnu_extra_release_lock";
	public static final String MNU_EXTRA_CHANGE_PASSWORD	= "mnu_extra_change_password";
	public static final String MNU_EXTRA_UNPUBLISH		= "mnu_extra_unpublish";
	public static final String MNU_HELP_HELP			= "mnu_help_help";
	public static final String MNU_HELP_ABOUT			= "mnu_help_about";
	
	/*
	 *	Fields
	 */
	Locale 		  	currentLocale;
	ResourceBundle	resourceBundle;
	
	ImageIcon		flag;
	JLabel			flagLabel;
	
	JMenu			fileMenu;
	JMenu			editMenu;
	JMenu			searchMenu;
	JMenu			projectMenu;
	JMenu			basketMenu;
	JMenu			userMenu;
	JMenu			dataMenu;
	JMenu			dataImportMenu;
	JMenu			dataEditMenu;
	JMenu			dataExportMenu;
	JMenu			dataRemoveMenu; 
	JMenu			extraMenu;
	JMenu			extraSettingMenu;
	JMenu			extraFontSizeMenu;
	JMenu			extraLanguageMenu;
	JMenu			extraTroubleMenu;
	JMenu			helpMenu;
	
	JMenuItem		fileExit;
	JMenuItem		editUndo;
	JMenuItem		editRedo;
	JMenuItem		editCut;
	JMenuItem		editCopy;
	JMenuItem		editPaste;
	JMenuItem		editRemove;
	JMenuItem		srhSearch;
	JMenuItem		prjNewProject;
	JMenuItem		prjOpenProject;
	JMenuItem		prjClose;
	JMenuItem		prjCloseAll;
	JMenuItem		prjSave;
	JMenuItem		prjSaveAs;
	JMenuItem		prjSaveAll;
	JMenuItem		prjPublish;
	JMenuItem		prjRemove;
	JMenuItem		prjAddParticipant;
	JMenuItem		prjEditParticipant;
	JMenuItem		bsktNewBasket;
	JMenuItem		bsktOpenBasket;
	JMenuItem		bsktClose;
	JMenuItem		bsktCloseAll;
	JMenuItem		bsktSave;
	JMenuItem		bsktSaveAs;
	JMenuItem		bsktSaveAll;
	JMenuItem		bsktEmpty;
	JMenuItem		bsktEmptyTemp;
	JMenuItem		userLogin;
	JMenuItem		userLogoff;
	JMenuItem		userNewUser;
	JMenuItem		userEditUser;
	JMenuItem		userNewPerson;
	JMenuItem		userEditPerson;
	JMenuItem		userNewPassword;
	JMenuItem		dataImportVariable;
	JMenuItem		dataEditVariable;
	JMenuItem		dataImportMeasure;
	JMenuItem		dataEditMeasure;
	JMenuItem		dataExportSyntax;
	JMenuItem		dataExportVariable;
	JMenuItem		dataRemoveVariable;
	JMenuItem		dataRemoveMeasure;
	JMenuItem		extraIncreaseFontSize;
	JMenuItem		extraDecreaseFontSize;
	JMenuItem		extraLanguageEnglish;
	JMenuItem		extraLanguageGerman;
	JMenuItem		extraReleaseLock;
	JMenuItem		extraChangePassword;
	JMenuItem		extraUnpublish;
	JMenuItem		helpHelp;
	JMenuItem		helpAbout;
	
	/*
	 *	Constructor
	 */
	/**
	 * @param al
	 * @param locale
	 */
	public MenuBar(ActionListener al, Locale locale) {
		super();
		
		currentLocale = locale;
		resourceBundle = ResourceBundle.getBundle(BUNDLE, currentLocale);
		
		fileMenu	= fileMenu(al);
		editMenu	= editMenu(al); editMenu.setEnabled(false); editMenu.setVisible(false);
		searchMenu	= searchMenu(al);
		projectMenu	= projectMenu(al);
		basketMenu	= basketMenu(al);
		userMenu	= userMenu(al);
		dataMenu	= dataMenu(al);
		extraMenu	= extraMenu(al);
		helpMenu	= helpMenu(al);
		flagLabel	= flagLabel(currentLocale);
		
	   	add(fileMenu);
	   	add(userMenu);
	   	add(dataMenu);
	   	add(projectMenu);
	   	add(basketMenu);
	   	add(searchMenu);
    	add(editMenu);
    	add(extraMenu);
    	add(helpMenu);    	
		add(Box.createGlue());
		add(flagLabel);
		
	}
	
	/*
	 *	Methods
	 */
    /**
     * @param al
     * @return
     */
    private JMenu fileMenu(ActionListener al) {    	
    	JMenu menu = menu(MNU_FILE_FILE, KeyEvent.VK_F);	

    	fileExit	= menuItem(resourceBundle.getString(MNU_FILE_EXIT),		ActionCommandText.CMD_FILE_EXIT,	null,	-1,	-1,	true,	al); 
        menu.add(fileExit);
        
    	return menu;
    }
    
    /**
     * @param al
     * @return
     */
    private JMenu editMenu(ActionListener al) {
    	JMenu menu = menu(MNU_EDIT_EDIT, KeyEvent.VK_E);	
 
    	editUndo	= menuItem(resourceBundle.getString(MNU_EDIT_UNDO),		ActionCommandText.CMD_EDIT_UNDO,	CStatsGUI.UNDO_ICON,	KeyEvent.VK_Z,		InputEvent.CTRL_MASK,	false,	al);
    	editRedo	= menuItem(resourceBundle.getString(MNU_EDIT_REDO),		ActionCommandText.CMD_EDIT_REDO,	CStatsGUI.REDO_ICON,	KeyEvent.VK_Y,		InputEvent.CTRL_MASK,	false,	al);
    	editCut		= menuItem(resourceBundle.getString(MNU_EDIT_CUT),		ActionCommandText.CMD_EDIT_CUT, 	CStatsGUI.CUT_ICON,		KeyEvent.VK_X,		InputEvent.CTRL_MASK,	false,	al);
    	editCopy	= menuItem(resourceBundle.getString(MNU_EDIT_COPY),		ActionCommandText.CMD_EDIT_COPY,	CStatsGUI.COPY_ICON,	KeyEvent.VK_C,		InputEvent.CTRL_MASK,	false,	al);
    	editPaste	= menuItem(resourceBundle.getString(MNU_EDIT_PASTE),	ActionCommandText.CMD_EDIT_PASTE,	CStatsGUI.PASTE_ICON,	KeyEvent.VK_V,		InputEvent.CTRL_MASK,	false,	al);
    	editRemove	= menuItem(resourceBundle.getString(MNU_EDIT_REMOVE),	ActionCommandText.CMD_EDIT_REMOVE,	CStatsGUI.REMOVE_ICON,	KeyEvent.VK_DELETE,	InputEvent.CTRL_MASK,	false,	al);
        menu.add(editUndo); editUndo.setVisible(false);
        menu.add(editRedo); editRedo.setVisible(false);    	
        menu.add(editCut); editCut.setVisible(false);
        menu.add(editCopy); editCopy.setVisible(false);
        menu.add(editPaste); editPaste.setVisible(false);
        menu.add(editRemove); editRemove.setVisible(false);
        
    	return menu;
    }
    
    /**
     * @param al
     * @return
     */
    private JMenu searchMenu(ActionListener al) {	
    	JMenu menu = menu(MNU_SRH_SRH, KeyEvent.VK_S);
	
    	srhSearch	= menuItem(resourceBundle.getString(MNU_SRH_SEARCH),	ActionCommandText.CMD_SRH_SEARCH,	CStatsGUI.SEARCH_ICON,	KeyEvent.VK_F,	InputEvent.CTRL_MASK,	true,	al);
        menu.add(srhSearch);
                
    	return menu;
    }
    
    /**
     * @param al
     * @return
     */
    private JMenu projectMenu(ActionListener al) {    	
    	JMenu menu = menu(MNU_PRJ_PRJ, KeyEvent.VK_P);	

    	prjNewProject		= menuItem(resourceBundle.getString(MNU_PRJ_NEW_PROJECT),		ActionCommandText.CMD_PRJ_NEW_PROJECT,		CStatsGUI.NEW_ICON,		KeyEvent.VK_N,	InputEvent.CTRL_MASK,							false, al);
    	prjOpenProject		= menuItem(resourceBundle.getString(MNU_PRJ_OPEN_PROJECT),		ActionCommandText.CMD_PRJ_OPEN_PROJECT,		CStatsGUI.OPEN_ICON,	KeyEvent.VK_O,	InputEvent.CTRL_MASK,							false, al);
    	prjClose			= menuItem(resourceBundle.getString(MNU_PRJ_CLOSE),				ActionCommandText.CMD_PRJ_CLOSE,			null, 					KeyEvent.VK_W,	InputEvent.CTRL_MASK,							false, al);
    	prjCloseAll			= menuItem(resourceBundle.getString(MNU_PRJ_CLOSE_ALL),			ActionCommandText.CMD_PRJ_CLOSE_ALL,		null, 					KeyEvent.VK_W,	InputEvent.SHIFT_MASK,							false, al);
    	prjSave				= menuItem(resourceBundle.getString(MNU_PRJ_SAVE),				ActionCommandText.CMD_PRJ_SAVE,				CStatsGUI.SAVE_ICON,	KeyEvent.VK_S,	InputEvent.CTRL_MASK,							false, al);
    	prjSaveAs			= menuItem(resourceBundle.getString(MNU_PRJ_SAVE_AS),			ActionCommandText.CMD_PRJ_SAVE_AS,			null,					-1,				-1,												false, al);
    	prjSaveAll			= menuItem(resourceBundle.getString(MNU_PRJ_SAVE_ALL),			ActionCommandText.CMD_PRJ_SAVE_ALL,			null,					KeyEvent.VK_S,	InputEvent.CTRL_MASK + InputEvent.SHIFT_MASK,	false, al);
    	prjPublish			= menuItem(resourceBundle.getString(MNU_PRJ_PUBLISH),			ActionCommandText.CMD_PRJ_FINISH,			null,					-1,				-1,												false, al);
    	prjRemove			= menuItem(resourceBundle.getString(MNU_PRJ_REMOVE),			ActionCommandText.CMD_PRJ_REMOVE,			null,					-1,				-1,												false, al);
    	prjAddParticipant	= menuItem(resourceBundle.getString(MNU_PRJ_ADD_PARTICIPANT),	ActionCommandText.CMD_PRJ_ADD_PARTICIPANT,	null,					-1,				-1,												false, al);
    	prjEditParticipant	= menuItem(resourceBundle.getString(MNU_PRJ_EDIT_PARTICIPANT),	ActionCommandText.CMD_PRJ_EDIT_PARTICIPANT,	null,					-1,				-1,												false, al);
    	menu.add(prjNewProject);
    	menu.add(prjOpenProject);
        menu.addSeparator();    
        menu.add(prjClose);
        menu.add(prjCloseAll); prjCloseAll.setVisible(false);
        menu.addSeparator();
        menu.add(prjSave);
        menu.add(prjSaveAs); prjSaveAs.setVisible(false);
        menu.add(prjSaveAll); prjSaveAll.setVisible(false);
        menu.addSeparator();
        menu.add(prjPublish); prjPublish.setVisible(false);
        menu.add(prjRemove); prjRemove.setVisible(false);
        menu.addSeparator();
        menu.add(prjAddParticipant); prjAddParticipant.setVisible(false);
        menu.add(prjEditParticipant); prjEditParticipant.setVisible(false);
               
    	return menu;
    }
    
    /**
     * @param al
     * @return
     */
    private JMenu basketMenu(ActionListener al) {	
    	JMenu menu = menu(MNU_BSKT_BSKT, KeyEvent.VK_B);	

    	bsktNewBasket	= menuItem(resourceBundle.getString(MNU_BSKT_NEW_BASKET),	ActionCommandText.CMD_BSKT_NEW_BASKET,			null,	-1,	-1,	false,	al);
    	bsktOpenBasket	= menuItem(resourceBundle.getString(MNU_BSKT_OPEN_BASKET),	ActionCommandText.CMD_BSKT_OPEN_BASKET,			null,	-1,	-1,	false,	al);
    	bsktClose		= menuItem(resourceBundle.getString(MNU_BSKT_CLOSE),		ActionCommandText.CMD_BSKT_CLOSE,				null,	-1, -1, false,	al);
    	bsktCloseAll	= menuItem(resourceBundle.getString(MNU_BSKT_CLOSE_ALL),	ActionCommandText.CMD_BSKT_CLOSE_ALL,			null,	-1, -1, false,	al);
    	bsktSave		= menuItem(resourceBundle.getString(MNU_BSKT_SAVE),			ActionCommandText.CMD_BSKT_SAVE,				null,	-1, -1, false,	al);
    	bsktSaveAs		= menuItem(resourceBundle.getString(MNU_BSKT_SAVE_AS),		ActionCommandText.CMD_BSKT_SAVE_AS,				null,	-1, -1, false,	al);
    	bsktSaveAll		= menuItem(resourceBundle.getString(MNU_BSKT_SAVE_ALL),		ActionCommandText.CMD_BSKT_SAVE_ALL,			null,	-1, -1, false,	al);
    	bsktEmpty		= menuItem(resourceBundle.getString(MNU_BSKT_EMPTY),		ActionCommandText.CMD_BSKT_EMPTY,				null,	-1, -1, false,	al);
    	bsktEmptyTemp	= menuItem(resourceBundle.getString(MNU_BSKT_EMPTY_TEMP),	ActionCommandText.CMD_BSKT_EMPTY_TEMP_BASKET,	null,	-1, -1, true,	al);
    	menu.add(bsktNewBasket); bsktNewBasket.setVisible(false);
    	menu.add(bsktOpenBasket);
        menu.addSeparator();    
        menu.add(bsktClose);
        menu.add(bsktCloseAll); bsktCloseAll.setVisible(false);        
        menu.addSeparator();
        menu.add(bsktSave);
        menu.add(bsktSaveAs); bsktSaveAs.setVisible(false);
        menu.add(bsktSaveAll); bsktSaveAll.setVisible(false);
        menu.addSeparator();
        menu.add(bsktEmpty);
        menu.addSeparator();
        menu.add(bsktEmptyTemp);
               
    	return menu;
    }
    
    /**
     * @param al
     * @return
     */
    private JMenu userMenu(ActionListener al) {
    	JMenu menu = menu(MNU_USER_USER, KeyEvent.VK_U);
    	
    	userLogin		= menuItem(resourceBundle.getString(MNU_USER_LOGIN),		ActionCommandText.CMD_USER_LOGIN,			null,	KeyEvent.VK_L,	InputEvent.CTRL_MASK,	true,	al);
    	userLogoff		= menuItem(resourceBundle.getString(MNU_USER_LOGOFF),		ActionCommandText.CMD_USER_LOGOFF,			null,	-1,				-1,						false,	al);
    	userNewUser		= menuItem(resourceBundle.getString(MNU_USER_NEW_USER),		ActionCommandText.CMD_USER_ADD_NEW_USER,	null,	-1,				-1,						false,	al);
    	userEditUser	= menuItem(resourceBundle.getString(MNU_USER_EDIT_USER),	ActionCommandText.CMD_USER_EDIT_USER,		null,	-1,				-1,						false,	al);
    	userNewPerson	= menuItem(resourceBundle.getString(MNU_USER_NEW_PERSON),	ActionCommandText.CMD_USER_ADD_NEW_PERSON,	null,	-1,				-1,						false,	al);
    	userEditPerson	= menuItem(resourceBundle.getString(MNU_USER_EDIT_PERSON),	ActionCommandText.CMD_USER_EDIT_PERSON,		null,	-1,				-1,						false,	al);
    	userNewPassword	= menuItem(resourceBundle.getString(MNU_USER_NEW_PASSWORD),	ActionCommandText.CMD_USER_CHANGE_PASSWORD,	null,	-1,				-1,						false,	al);
        menu.add(userLogin);        
        menu.addSeparator();
        menu.add(userLogoff);
        menu.addSeparator();        
        menu.add(userNewUser); userNewUser.setVisible(false);
        menu.add(userEditUser); userEditUser.setVisible(false);
        menu.add(userNewPerson); userNewPerson.setVisible(false);
        menu.add(userEditPerson); userEditPerson.setVisible(false);
        menu.addSeparator(); 
        menu.add(userNewPassword);
        
    	return menu;    	
    }
    
    /**
     * @param al
     * @return
     */
    private JMenu dataMenu(ActionListener al) {
    	JMenu menu = menu(MNU_DATA_DATA, KeyEvent.VK_D);
    	
    	dataImportMenu	= new JMenu(resourceBundle.getString(MNU_DATA_IMPORT));	dataImportMenu.setVisible(false);    	
    	dataEditMenu	= new JMenu(resourceBundle.getString(MNU_DATA_EDIT));	dataEditMenu.setVisible(false);    	
    	dataExportMenu	= new JMenu(resourceBundle.getString(MNU_DATA_EXPORT));
    	dataRemoveMenu	= new JMenu(resourceBundle.getString(MNU_DATA_REMOVE));	dataRemoveMenu.setVisible(false);	
    	
    	dataImportMenu.setName(MNU_DATA_IMPORT);
    	dataEditMenu.setName(MNU_DATA_EDIT);
    	dataRemoveMenu.setName(MNU_DATA_REMOVE);	
    	
    	dataImportVariable	= menuItem(resourceBundle.getString(MNU_DATA_IMPORT_VARIABLE),	ActionCommandText.CMD_EXTRA_IMPORT_VARIABLE,	null,					-1,	-1,	false,	al);
    	dataImportMeasure	= menuItem(resourceBundle.getString(MNU_DATA_IMPORT_MEASURE),	ActionCommandText.CMD_EXTRA_IMPORT_MEASURE,		null,					-1,	-1,	false,	al);
    	dataEditVariable	= menuItem(resourceBundle.getString(MNU_DATA_EDIT_VARIABLE),	ActionCommandText.CMD_EXTRA_EDIT_VARIABLE,		null,					-1,	-1,	false,	al); 	
    	dataEditMeasure		= menuItem(resourceBundle.getString(MNU_DATA_EDIT_MEASURE),		ActionCommandText.CMD_EXTRA_EDIT_MEASURE,		null,					-1,	-1,	false,	al);
    	dataExportVariable	= menuItem(resourceBundle.getString(MNU_DATA_EXPORT_VARIABLE),	ActionCommandText.CMD_EXTRA_EXPORT_VARIABLE,	null,					-1,	-1,	false,	al);
    	dataExportSyntax	= menuItem(resourceBundle.getString(MNU_DATA_EXPORT_SYNTAX),	ActionCommandText.CMD_EXTRA_EXPORT_SYNTAX,		CStatsGUI.SYNTAX_ICON,	-1,	-1,	false,	al);
    	dataRemoveVariable	= menuItem(resourceBundle.getString(MNU_DATA_REMOVE_VARIABLE),	ActionCommandText.CMD_EXTRA_REMOVE_VARIABLE,	null,					-1,	-1,	false,	al);	
    	dataRemoveMeasure	= menuItem(resourceBundle.getString(MNU_DATA_REMOVE_MEASURE),	ActionCommandText.CMD_EXTRA_REMOVE_MEASURE,		null,					-1,	-1,	false,	al);	
  
    	dataImportMenu.add(dataImportVariable);	dataImportVariable.setVisible(false);
    	dataImportMenu.add(dataImportMeasure);	dataImportMeasure.setVisible(false);
    	dataEditMenu.add(dataEditVariable);		dataEditVariable.setVisible(false);
    	dataEditMenu.add(dataEditMeasure);		dataEditMeasure.setVisible(false);
    	dataExportMenu.add(dataExportVariable);
    	dataExportMenu.add(dataExportSyntax);
    	dataRemoveMenu.add(dataRemoveVariable);	dataRemoveVariable.setVisible(false);	
    	dataRemoveMenu.add(dataRemoveMeasure);	dataRemoveMeasure.setVisible(false);	
    	
        menu.add(dataImportMenu);
        menu.add(dataEditMenu);
        menu.add(dataExportMenu);
        menu.add(dataRemoveMenu);	
        
    	return menu;    	
    }
    
    /**
     * @param al
     * @return
     */
    private JMenu extraMenu(ActionListener al) {
    	JMenu menu = menu(MNU_EXTRA_EXTRA, KeyEvent.VK_X);
    	
    	extraSettingMenu	= new JMenu(resourceBundle.getString(MNU_EXTRA_SETTINGS));
    	extraFontSizeMenu	= new JMenu(resourceBundle.getString(MNU_EXTRA_FONT_SIZE));
    	extraLanguageMenu	= new JMenu(resourceBundle.getString(MNU_EXTRA_LANGUAGE));
    	extraTroubleMenu	= new JMenu(resourceBundle.getString(MNU_EXTRA_TROUBLESHOOTING));
    	
    	extraTroubleMenu.setName(MNU_EXTRA_TROUBLESHOOTING);
    	
    	extraIncreaseFontSize	= menuItem(resourceBundle.getString(MNU_EXTRA_INC_FONT_SIZE),	ActionCommandText.CMD_EXTRA_INC_FONT_SIZE,		null,			KeyEvent.VK_ADD,		InputEvent.CTRL_MASK,	true,	al);
    	extraDecreaseFontSize	= menuItem(resourceBundle.getString(MNU_EXTRA_DEC_FONT_SIZE),	ActionCommandText.CMD_EXTRA_DEC_FONT_SIZE,		null,			KeyEvent.VK_SUBTRACT,	InputEvent.CTRL_MASK,	true,	al);
    	extraLanguageEnglish	= menuItem(resourceBundle.getString(MNU_EXTRA_LANGUAGE_ENG),	ActionCommandText.CMD_EXTRA_LANGUAGE_ENGLISH,	US_FLAG_ICON,	-1,						-1,						true,	al);
    	extraLanguageGerman		= menuItem(resourceBundle.getString(MNU_EXTRA_LANGUAGE_GER),	ActionCommandText.CMD_EXTRA_LANGUAGE_GERMAN,	DE_FLAG_ICON,	-1,						-1,						true,	al);
    	extraReleaseLock		= menuItem(resourceBundle.getString(MNU_EXTRA_RELEASE_LOCK),	ActionCommandText.CMD_EXTRA_RELEASE_LOCK,		null,			-1,						-1,						false,	al);
    	extraChangePassword		= menuItem(resourceBundle.getString(MNU_EXTRA_CHANGE_PASSWORD),	ActionCommandText.CMD_EXTRA_CHANGE_PASSWORD,	null,			-1,						-1,						false,	al);
    	extraUnpublish			= menuItem(resourceBundle.getString(MNU_EXTRA_UNPUBLISH),		ActionCommandText.CMD_EXTRA_UNFINISH,			null,			-1,						-1,						false,	al);
    	    	
    	extraFontSizeMenu.add(extraIncreaseFontSize);
    	extraFontSizeMenu.add(extraDecreaseFontSize);   	
    	extraLanguageMenu.add(extraLanguageEnglish);
    	extraLanguageMenu.add(extraLanguageGerman);
    	extraSettingMenu.add(extraFontSizeMenu);
    	extraSettingMenu.add(extraLanguageMenu);    	
    	extraTroubleMenu.add(extraReleaseLock);			extraReleaseLock.setVisible(false);
    	extraTroubleMenu.add(extraUnpublish);			extraUnpublish.setVisible(false);
    	extraTroubleMenu.addSeparator();
    	extraTroubleMenu.add(extraChangePassword);		extraChangePassword.setVisible(false);
    	
        menu.add(extraSettingMenu); 
        menu.add(extraTroubleMenu);	extraTroubleMenu.setVisible(false);
        
    	return menu;    	
    }
    
    /**
     * @param al
     * @return
     */
    private JMenu helpMenu(ActionListener al) {
    	JMenu menu = menu(MNU_HELP_HELP, KeyEvent.VK_H);    	
    	
    	helpHelp	= menuItem(resourceBundle.getString(MNU_HELP_HELP),		ActionCommandText.CMD_HELP_HELP,	null,	KeyEvent.VK_F1,	InputEvent.CTRL_MASK,	true,	al);
    	helpAbout	= menuItem(resourceBundle.getString(MNU_HELP_ABOUT),	ActionCommandText.CMD_HELP_ABOUT,	null,	-1, 			-1,						true,	al);
    	
        menu.add(helpHelp);        
        menu.addSeparator();
        menu.add(helpAbout);
        
    	return menu;
    }
    
    /**
     * @param text
     * @param key
     * @return
     */
    private JMenu menu(String text, int key) {
    	JMenu menu = new JMenu();
    	
    	menu.setText(resourceBundle.getString(text));
    	menu.setName(text);
    	menu.setMnemonic(key);
    	
    	return menu;
    }
    
    /**
     * @param text
     * @param command
     * @param icon
     * @param stroke
     * @param mask
     * @param enabled
     * @param al
     * @return
     */
    private JMenuItem menuItem(String text, String command, String icon, int stroke, int mask, boolean enabled, ActionListener al) {
    	JMenuItem item = new JMenuItem();
    	
    	item.setText(text);
    	item.setName(command);
    	item.setActionCommand(command);
    	item.addActionListener(al);
    	ImageIcon image = null;
    	if (icon instanceof String) {
            image = createImageIcon(icon, "");
            if (image != null)
            	item.setIcon(image);    		
    	}
    	if (stroke > -1) {
	        KeyStroke keyStroke = KeyStroke.getKeyStroke(stroke, mask);
	    	if (keyStroke instanceof KeyStroke)
	    		item.setAccelerator(keyStroke);
    	}
        item.setEnabled(enabled);
    	
    	return item;
    }
    
    /**
     * @param locale
     * @return
     */
    private JLabel flagLabel(Locale locale) {
    	JLabel label = new JLabel();

		try {
			String iso3Locale = locale.getISO3Language();
			
			String iso3Ger = Locale.GERMAN.getISO3Language();
			String iso3Eng = Locale.US.getISO3Language();
			
			if (iso3Locale.equals(iso3Ger))
				flag = createImageIcon(DE_FLAG_ICON, "");
			if (iso3Locale.equals(iso3Eng))
				flag = createImageIcon(US_FLAG_ICON, "");
						
			label = new JLabel(flag);
		} catch (MissingResourceException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
			
    	return label;
    }
    
	protected static ImageIcon createImageIcon(String resourceURL,
			String description) {
		java.net.URL imgURL = MenuBar.class.getClassLoader().getResource(resourceURL);
		if (imgURL != null) {
	   		return new ImageIcon(imgURL, description);
	   	} else {
	   		System.err.println("Couldn't find file: " + resourceURL);
	   		return null;
	   	}
	}
	
	/**
	 * @param locale
	 */
	protected void setLanguageIcon(Locale locale) {
		try {
			String iso3Locale = locale.getISO3Language();
			
			String iso3Ger = Locale.GERMAN.getISO3Language();
			String iso3Eng = Locale.US.getISO3Language();
			
			if (iso3Locale.equals(iso3Ger))
				flag = createImageIcon(DE_FLAG_ICON, "");
			if (iso3Locale.equals(iso3Eng))
				flag = createImageIcon(US_FLAG_ICON, "");
						
			remove(flagLabel);
			flagLabel = new JLabel(flag);
			add(flagLabel);
		} catch (MissingResourceException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}		
	}
			
	/**
	 * @param f
	 */
	protected void changeFont(Font f) {
    	fileMenu.setFont(f);
    	editMenu.setFont(f);
       	searchMenu.setFont(f);
       	projectMenu.setFont(f);
    	basketMenu.setFont(f);
    	dataMenu.setFont(f);
    	userMenu.setFont(f);
    	extraMenu.setFont(f);
    	helpMenu.setFont(f);    	    	
    	
     	fileExit.setFont(f);
    	editUndo.setFont(f);
    	editRedo.setFont(f);
    	editCut.setFont(f);
    	editCopy.setFont(f);
    	editPaste.setFont(f);
    	editRemove.setFont(f);
    	srhSearch.setFont(f);
    	prjNewProject.setFont(f);
    	prjOpenProject.setFont(f);
    	prjClose.setFont(f);
    	prjCloseAll.setFont(f);
    	prjSave.setFont(f);
    	prjSaveAs.setFont(f);
    	prjSaveAll.setFont(f);
    	prjPublish.setFont(f);
    	prjRemove.setFont(f);
    	prjAddParticipant.setFont(f);
    	prjEditParticipant.setFont(f);
    	bsktNewBasket.setFont(f);
    	bsktOpenBasket.setFont(f);
    	bsktClose.setFont(f);
    	bsktCloseAll.setFont(f);
    	bsktSave.setFont(f);
    	bsktSaveAs.setFont(f);
    	bsktSaveAll.setFont(f);
    	bsktEmpty.setFont(f);
    	bsktEmptyTemp.setFont(f);
       	dataImportMenu.setFont(f);
    	dataImportVariable.setFont(f);
    	dataImportMeasure.setFont(f);
    	dataEditMenu.setFont(f);
    	dataEditVariable.setFont(f); 
    	dataEditMeasure.setFont(f);
    	dataExportMenu.setFont(f);
    	dataExportVariable.setFont(f);
    	dataExportSyntax.setFont(f);
    	dataRemoveMenu.setFont(f);
    	dataRemoveVariable.setFont(f);
    	dataRemoveMeasure.setFont(f);
    	userLogin.setFont(f);
    	userLogoff.setFont(f);
    	userNewUser.setFont(f);
    	userEditUser.setFont(f);
    	userNewPerson.setFont(f);
    	userEditPerson.setFont(f);
    	userNewPassword.setFont(f);
       	extraSettingMenu.setFont(f);
       	extraFontSizeMenu.setFont(f);
       	extraIncreaseFontSize.setFont(f);
       	extraDecreaseFontSize.setFont(f);
    	extraLanguageMenu.setFont(f);
    	extraLanguageEnglish.setFont(f);
    	extraLanguageGerman.setFont(f);
    	extraTroubleMenu.setFont(f);
    	extraReleaseLock.setFont(f);
    	extraChangePassword.setFont(f);
    	extraUnpublish.setFont(f);
    	helpHelp.setFont(f);
    	helpAbout.setFont(f);
	    UIManager.put("Menu.acceleratorFont", f);
        UIManager.put("MenuItem.acceleratorFont", f);
        javax.swing.SwingUtilities.updateComponentTreeUI(this);
	}
	
	/**
	 * @param locale
	 */
	protected void changeLanguage(Locale locale) {
		ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, locale);
		
		fileMenu.setText(bundle.getString(MNU_FILE_FILE));
		editMenu.setText(bundle.getString(MNU_EDIT_EDIT));
		searchMenu.setText(bundle.getString(MNU_SRH_SRH));
		projectMenu.setText(bundle.getString(MNU_PRJ_PRJ));
		basketMenu.setText(bundle.getString(MNU_BSKT_BSKT));
		dataMenu.setText(bundle.getString(MNU_DATA_DATA));
		userMenu.setText(bundle.getString(MNU_USER_USER));
		extraMenu.setText(bundle.getString(MNU_EXTRA_EXTRA));
		helpMenu.setText(bundle.getString(MNU_HELP_HELP));
		
		fileExit.setText(bundle.getString(MNU_FILE_EXIT));
		editUndo.setText(bundle.getString(MNU_EDIT_UNDO));
		editRedo.setText(bundle.getString(MNU_EDIT_REDO));
		editCut.setText(bundle.getString(MNU_EDIT_CUT));
		editCopy.setText(bundle.getString(MNU_EDIT_COPY));
		editPaste.setText(bundle.getString(MNU_EDIT_PASTE));
		editRemove.setText(bundle.getString(MNU_EDIT_REMOVE));
		srhSearch.setText(bundle.getString(MNU_SRH_SEARCH));
		prjNewProject.setText(bundle.getString(MNU_PRJ_NEW_PROJECT));
		prjOpenProject.setText(bundle.getString(MNU_PRJ_OPEN_PROJECT));
		prjClose.setText(bundle.getString(MNU_PRJ_CLOSE));
		prjCloseAll.setText(bundle.getString(MNU_PRJ_CLOSE_ALL));
		prjSave.setText(bundle.getString(MNU_PRJ_SAVE));
		prjSaveAs.setText(bundle.getString(MNU_PRJ_SAVE_AS));
		prjSaveAll.setText(bundle.getString(MNU_PRJ_SAVE_ALL));
		prjPublish.setText(bundle.getString(MNU_PRJ_PUBLISH));
		prjRemove.setText(bundle.getString(MNU_PRJ_REMOVE));
		prjAddParticipant.setText(bundle.getString(MNU_PRJ_ADD_PARTICIPANT));
		prjEditParticipant.setText(bundle.getString(MNU_PRJ_EDIT_PARTICIPANT));
		bsktNewBasket.setText(bundle.getString(MNU_BSKT_NEW_BASKET));
		bsktOpenBasket.setText(bundle.getString(MNU_BSKT_OPEN_BASKET));
		bsktClose.setText(bundle.getString(MNU_BSKT_CLOSE));
		bsktCloseAll.setText(bundle.getString(MNU_BSKT_CLOSE_ALL));
		bsktSave.setText(bundle.getString(MNU_BSKT_SAVE));
		bsktSaveAs.setText(bundle.getString(MNU_BSKT_SAVE_AS));
		bsktSaveAll.setText(bundle.getString(MNU_BSKT_SAVE_ALL));
		bsktEmpty.setText(bundle.getString(MNU_BSKT_EMPTY));
		bsktEmptyTemp.setText(bundle.getString(MNU_BSKT_EMPTY_TEMP));
		dataImportMenu.setText(bundle.getString(MNU_DATA_IMPORT));
		dataImportVariable.setText(bundle.getString(MNU_DATA_IMPORT_VARIABLE));
		dataImportMeasure.setText(bundle.getString(MNU_DATA_IMPORT_MEASURE));
		dataEditMenu.setText(bundle.getString(MNU_DATA_EDIT));
		dataEditVariable.setText(bundle.getString(MNU_DATA_EDIT_VARIABLE));		
		dataEditMeasure.setText(bundle.getString(MNU_DATA_EDIT_MEASURE));
		dataExportMenu.setText(bundle.getString(MNU_DATA_EXPORT));
		dataExportVariable.setText(bundle.getString(MNU_DATA_EXPORT_VARIABLE));
		dataExportSyntax.setText(bundle.getString(MNU_DATA_EXPORT_SYNTAX));
    	dataRemoveMenu.setText(bundle.getString(MNU_DATA_REMOVE));
    	dataRemoveVariable.setText(bundle.getString(MNU_DATA_REMOVE_VARIABLE));
    	dataRemoveMeasure.setText(bundle.getString(MNU_DATA_REMOVE_MEASURE));
		userLogin.setText(bundle.getString(MNU_USER_LOGIN));
		userLogoff.setText(bundle.getString(MNU_USER_LOGOFF));
		userNewUser.setText(bundle.getString(MNU_USER_NEW_USER));
		userEditUser.setText(bundle.getString(MNU_USER_EDIT_USER));
		userNewPerson.setText(bundle.getString(MNU_USER_NEW_PERSON));
		userEditPerson.setText(bundle.getString(MNU_USER_EDIT_PERSON));
		userNewPassword.setText(bundle.getString(MNU_USER_NEW_PASSWORD));
		extraSettingMenu.setText(bundle.getString(MNU_EXTRA_SETTINGS));
		extraFontSizeMenu.setText(bundle.getString(MNU_EXTRA_FONT_SIZE));
		extraIncreaseFontSize.setText(bundle.getString(MNU_EXTRA_INC_FONT_SIZE));
		extraDecreaseFontSize.setText(bundle.getString(MNU_EXTRA_DEC_FONT_SIZE));
		extraLanguageMenu.setText(bundle.getString(MNU_EXTRA_LANGUAGE));
		extraLanguageEnglish.setText(bundle.getString(MNU_EXTRA_LANGUAGE_ENG));
		extraLanguageGerman.setText(bundle.getString(MNU_EXTRA_LANGUAGE_GER));
	   	extraTroubleMenu.setText(bundle.getString(MNU_EXTRA_TROUBLESHOOTING));
    	extraReleaseLock.setText(bundle.getString(MNU_EXTRA_RELEASE_LOCK));
    	extraChangePassword.setText(bundle.getString(MNU_EXTRA_CHANGE_PASSWORD));
    	extraUnpublish.setText(bundle.getString(MNU_EXTRA_UNPUBLISH));
		helpHelp.setText(bundle.getString(MNU_HELP_HELP));
		helpAbout.setText(bundle.getString(MNU_HELP_ABOUT));
		
		try {
			String iso3Locale = locale.getISO3Language();
			
			String iso3Ger = Locale.GERMAN.getISO3Language();
			String iso3Eng = Locale.US.getISO3Language();
			
			if (iso3Locale.equals(iso3Ger))
				flag = createImageIcon(DE_FLAG_ICON, "");
			if (iso3Locale.equals(iso3Eng))
				flag = createImageIcon(US_FLAG_ICON, "");
			
			flagLabel.setIcon(flag);
		} catch (MissingResourceException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());			
		}
	}
	
	/**
	 * @param item
	 * @param model
	 */
	void userLogin(JMenuBar item, CStatsModel model) {
		MenuElement[] elements = item.getSubElements();
		
	    for (int i=0; i<elements.length; i++) {
	    	if (elements[i] instanceof JMenu) {	    		
	    		if (!((JMenu)elements[i]).getName().equals(MNU_FILE_FILE) &&
	    				!((JMenu)elements[i]).getName().equals(MNU_SRH_SRH) &&
	    				!((JMenu)elements[i]).getName().equals(MNU_HELP_HELP)) {
	    			Component[] components = ((JMenu)elements[i]).getMenuComponents();
	    			
	    			for (int j=0; j<components.length; j++) {
	    				if (components[j] instanceof JMenu) {
	    					
	    					if (((JMenu)components[j]).getName() instanceof String) {
		    					if ( ((JMenu)components[j]).getName().equals(MNU_DATA_IMPORT) &&
		    							(UserRole.ADMIN.equals(model.getUser().getUserRole()))) {
		    						((JMenu)components[j]).setVisible(true);
		    					}
		    					if ( ((JMenu)components[j]).getName().equals(MNU_DATA_EDIT) &&
		    							(UserRole.ADMIN.equals(model.getUser().getUserRole()))) {
		    						((JMenu)components[j]).setVisible(true);
		    					}
		    					if ( ((JMenu)components[j]).getName().equals(MNU_DATA_REMOVE) &&
		    							(UserRole.ADMIN.equals(model.getUser().getUserRole()))) {
		    						((JMenu)components[j]).setVisible(true);
		    					}
		    					if ( ((JMenu)components[j]).getName().equals(MNU_EXTRA_TROUBLESHOOTING) &&
		    							(UserRole.TROUBLE.equals(model.getUser().getUserRole()))) {
		    						((JMenu)components[j]).setVisible(true);
		    					}
	    					}
	    					
	    					Component[] subComponents = ((JMenu)components[j]).getMenuComponents();
	    					
	    					for (int k=0; k<subComponents.length; k++) {
	    						if (((Component)subComponents[k]).getName() != null) {
	    		    				if (((Component)subComponents[k]).getName().equals(ActionCommandText.CMD_EXTRA_IMPORT_VARIABLE) &&
	    		    						UserRole.ADMIN.equals(model.getUser().getUserRole())) {
	    		    					((Component)subComponents[k]).setVisible(true);
	    		    					((Component)subComponents[k]).setEnabled(true);
	    		    				}
	    		    				if (((Component)subComponents[k]).getName().equals(ActionCommandText.CMD_EXTRA_EDIT_VARIABLE) &&
	    		    						UserRole.ADMIN.equals(model.getUser().getUserRole())) {
	    		    					((Component)subComponents[k]).setVisible(true);
	    		    					((Component)subComponents[k]).setEnabled(true);
	    		    				}
	    		    				if (((Component)subComponents[k]).getName().equals(ActionCommandText.CMD_EXTRA_IMPORT_MEASURE) &&
	    		    						UserRole.ADMIN.equals(model.getUser().getUserRole())) {
	    		    					((Component)subComponents[k]).setVisible(true);
	    		    					((Component)subComponents[k]).setEnabled(true);
	    		    				}
	    		    				if (((Component)subComponents[k]).getName().equals(ActionCommandText.CMD_EXTRA_EDIT_MEASURE) &&
	    		    						UserRole.ADMIN.equals(model.getUser().getUserRole())) {
	    		    					((Component)subComponents[k]).setVisible(true);
	    		    					((Component)subComponents[k]).setEnabled(true);
	    		    				}
	    		    				if (((Component)subComponents[k]).getName().equals(ActionCommandText.CMD_EXTRA_REMOVE_VARIABLE) &&
	    		    						UserRole.ADMIN.equals(model.getUser().getUserRole())) {
	    		    					((Component)subComponents[k]).setVisible(true);
	    		    					((Component)subComponents[k]).setEnabled(true);
	    		    				}
	    		    				if (((Component)subComponents[k]).getName().equals(ActionCommandText.CMD_EXTRA_REMOVE_MEASURE) &&
	    		    						UserRole.ADMIN.equals(model.getUser().getUserRole())) {
	    		    					((Component)subComponents[k]).setVisible(true);
	    		    					((Component)subComponents[k]).setEnabled(true);
	    		    				}
	    		    				if (((Component)subComponents[k]).getName().equals(ActionCommandText.CMD_EXTRA_RELEASE_LOCK) &&
	    		    						UserRole.TROUBLE.equals(model.getUser().getUserRole())) {
	    		    					((Component)subComponents[k]).setVisible(true);
	    		    					((Component)subComponents[k]).setEnabled(true);
	    		    				}
	    		    				if (((Component)subComponents[k]).getName().equals(ActionCommandText.CMD_EXTRA_CHANGE_PASSWORD) &&
	    		    						UserRole.TROUBLE.equals(model.getUser().getUserRole())) {
	    		    					((Component)subComponents[k]).setVisible(true);
	    		    					((Component)subComponents[k]).setEnabled(true);
	    		    				}
	    		    				if (((Component)subComponents[k]).getName().equals(ActionCommandText.CMD_EXTRA_UNFINISH) &&
	    		    						UserRole.TROUBLE.equals(model.getUser().getUserRole())) {
	    		    					((Component)subComponents[k]).setVisible(true);
	    		    					((Component)subComponents[k]).setEnabled(true);
	    		    				}
	    						}
	    					}
	    				}
	    				
	    				if (((Component)components[j]).getName() != null) {
		    				if (((Component)components[j]).getName().equals(ActionCommandText.CMD_PRJ_NEW_PROJECT) &&
		    						!UserRole.TROUBLE.equals(model.getUser().getUserRole())) 
		    					((Component)components[j]).setEnabled(true);
		    				if (((Component)components[j]).getName().equals(ActionCommandText.CMD_PRJ_OPEN_PROJECT) &&
    								!UserRole.TROUBLE.equals(model.getUser().getUserRole())) 
		    					((Component)components[j]).setEnabled(true);
		    				if (((Component)components[j]).getName().equals(ActionCommandText.CMD_BSKT_OPEN_BASKET) &&
    								!UserRole.TROUBLE.equals(model.getUser().getUserRole()))
		    					((Component)components[j]).setEnabled(true);			
		    				if (((Component)components[j]).getName().equals(ActionCommandText.CMD_USER_LOGIN)) 
		    					((Component)components[j]).setEnabled(false);
		    				if (((Component)components[j]).getName().equals(ActionCommandText.CMD_USER_LOGOFF)) 
		    					((Component)components[j]).setEnabled(true);
		    				// not used in QuickCharmStats:
//		    				if (((Component)components[j]).getName().equals(ActionCommandText.CMD_USER_ADD_NEW_USER) &&
//		    						UserRole.ADMIN.equals(model.getUser().getUserRole())) {
//		    					((Component)components[j]).setEnabled(true);
//		    					((Component)components[j]).setVisible(true);
//		    				}
//		    				if (((Component)components[j]).getName().equals(ActionCommandText.CMD_USER_EDIT_USER) &&
//		    						UserRole.ADMIN.equals(model.getUser().getUserRole())) {
//		    					((Component)components[j]).setEnabled(true);
//		    					((Component)components[j]).setVisible(true);
//		    				}		    				
//		    				if (((Component)components[j]).getName().equals(ActionCommandText.CMD_USER_ADD_NEW_PERSON) &&
//		    						UserRole.ADMIN.equals(model.getUser().getUserRole())) {
//		    					((Component)components[j]).setEnabled(true);
//		    					((Component)components[j]).setVisible(true);
//		    				}
//		    				if (((Component)components[j]).getName().equals(ActionCommandText.CMD_USER_EDIT_PERSON) &&
//		    						UserRole.ADMIN.equals(model.getUser().getUserRole())) {
//		    					((Component)components[j]).setEnabled(true);
//		    					((Component)components[j]).setVisible(true);
//		    				}
		    				if (((Component)components[j]).getName().equals(ActionCommandText.CMD_USER_CHANGE_PASSWORD))
		    					((Component)components[j]).setEnabled(true);
	    				}	    				
	    				
	    			}
	    		}
	    	}      
        }			
	}
	
	/**
	 * @param item
	 */
	void userLogoff(JMenuBar item) {
		MenuElement[] elements = item.getSubElements();
		
	    projectCloseProject(item);
	    basketCloseBasket(item);
	    
	    for (int i=0; i<elements.length; i++) {
	    	if (elements[i] instanceof JMenu) {
	    		
	    		if (!((JMenu)elements[i]).getName().equals(MNU_FILE_FILE) &&
	    				!((JMenu)elements[i]).getName().equals(MNU_SRH_SRH) &&
	    				!((JMenu)elements[i]).getName().equals(MNU_HELP_HELP)) {
	    			Component[] components = ((JMenu)elements[i]).getMenuComponents();
       
	    			for (int j=0; j<components.length; j++) {
	    				if (components[j] instanceof JMenu) {
	    					if (((JMenu)components[j]).getName() instanceof String) {
		    					if ( ((JMenu)components[j]).getName().equals(MNU_DATA_IMPORT)) {
		    						((JMenu)components[j]).setVisible(false);
		    					}
		    					if ( ((JMenu)components[j]).getName().equals(MNU_DATA_EDIT)) {
		    						((JMenu)components[j]).setVisible(false);
		    					}
		    					if ( ((JMenu)components[j]).getName().equals(MNU_DATA_REMOVE)) {
		    						((JMenu)components[j]).setVisible(false);
		    					}
		    					if ( ((JMenu)components[j]).getName().equals(MNU_EXTRA_TROUBLESHOOTING)) {
		    						((JMenu)components[j]).setVisible(false);
		    					}
	    					}
	    					
	    					Component[] subComponents = ((JMenu)components[j]).getMenuComponents();
	    					
	    					for (int k=0; k<subComponents.length; k++) {
	    						if (((Component)subComponents[k]).getName() != null) {
	    		    				if (((Component)subComponents[k]).getName().equals(ActionCommandText.CMD_EXTRA_IMPORT_VARIABLE)) {
	    		    					((Component)subComponents[k]).setVisible(false);
	    		    					((Component)subComponents[k]).setEnabled(false);
	    		    				}
	    		    				if (((Component)subComponents[k]).getName().equals(ActionCommandText.CMD_EXTRA_EDIT_VARIABLE)) {
	    		    					((Component)subComponents[k]).setVisible(false);
	    		    					((Component)subComponents[k]).setEnabled(false);
	    		    				}
	    		    				if (((Component)subComponents[k]).getName().equals(ActionCommandText.CMD_EXTRA_IMPORT_MEASURE)) {
	    		    					((Component)subComponents[k]).setVisible(false);
	    		    					((Component)subComponents[k]).setEnabled(false);
	    		    				}
	    		    				if (((Component)subComponents[k]).getName().equals(ActionCommandText.CMD_EXTRA_EDIT_MEASURE)) {
	    		    					((Component)subComponents[k]).setVisible(false);
	    		    					((Component)subComponents[k]).setEnabled(false);
	    		    				}
	    		    				if (((Component)subComponents[k]).getName().equals(ActionCommandText.CMD_EXTRA_REMOVE_VARIABLE)) {
	    		    					((Component)subComponents[k]).setVisible(false);
	    		    					((Component)subComponents[k]).setEnabled(false);
	    		    				}
	    		    				if (((Component)subComponents[k]).getName().equals(ActionCommandText.CMD_EXTRA_REMOVE_MEASURE)) {
	    		    					((Component)subComponents[k]).setVisible(false);
	    		    					((Component)subComponents[k]).setEnabled(false);
	    		    				}
	    		    				if (((Component)subComponents[k]).getName().equals(ActionCommandText.CMD_EXTRA_EXPORT_SYNTAX))	 
	    		    					((Component)subComponents[k]).setEnabled(false);
	    		    				if (((Component)subComponents[k]).getName().equals(ActionCommandText.CMD_EXTRA_EXPORT_VARIABLE))	 
	    		    					((Component)subComponents[k]).setEnabled(false);
	    		    				if (((Component)subComponents[k]).getName().equals(ActionCommandText.CMD_EXTRA_RELEASE_LOCK)) {
	    		    					((Component)subComponents[k]).setVisible(false);
	    		    					((Component)subComponents[k]).setEnabled(false);
	    		    				}
	    		    				if (((Component)subComponents[k]).getName().equals(ActionCommandText.CMD_EXTRA_CHANGE_PASSWORD)) {
	    		    					((Component)subComponents[k]).setVisible(false);
	    		    					((Component)subComponents[k]).setEnabled(false);
	    		    				}
	    		    				if (((Component)subComponents[k]).getName().equals(ActionCommandText.CMD_EXTRA_UNFINISH)) {
	    		    					((Component)subComponents[k]).setVisible(false);
	    		    					((Component)subComponents[k]).setEnabled(false);
	    		    				}
	    						}
	    					}
	    				}
	    				
	    				if (((Component)components[j]).getName() != null) {
		    				if (((Component)components[j]).getName().equals(ActionCommandText.CMD_PRJ_NEW_PROJECT))
		    					((Component)components[j]).setEnabled(false);
		    				if (((Component)components[j]).getName().equals(ActionCommandText.CMD_PRJ_OPEN_PROJECT))
		    					((Component)components[j]).setEnabled(false);
		    				if (((Component)components[j]).getName().equals(ActionCommandText.CMD_BSKT_OPEN_BASKET))
		    					((Component)components[j]).setEnabled(false);			
		    				if (((Component)components[j]).getName().equals(ActionCommandText.CMD_USER_LOGIN)) 
		    					((Component)components[j]).setEnabled(true);
		    				if (((Component)components[j]).getName().equals(ActionCommandText.CMD_USER_LOGOFF)) 
		    					((Component)components[j]).setEnabled(false);
		    				if (((Component)components[j]).getName().equals(ActionCommandText.CMD_USER_ADD_NEW_USER)) { 
		    					((Component)components[j]).setEnabled(false);
		    					((Component)components[j]).setVisible(false);
		    				}
		    				if (((Component)components[j]).getName().equals(ActionCommandText.CMD_USER_EDIT_USER)) { 
		    					((Component)components[j]).setEnabled(false);
		    					((Component)components[j]).setVisible(false);
		    				}
		    				if (((Component)components[j]).getName().equals(ActionCommandText.CMD_USER_ADD_NEW_PERSON)) { 
		    					((Component)components[j]).setEnabled(false);
		    					((Component)components[j]).setVisible(false);
		    				}
		    				if (((Component)components[j]).getName().equals(ActionCommandText.CMD_USER_EDIT_PERSON)) { 
		    					((Component)components[j]).setEnabled(false);
		    					((Component)components[j]).setVisible(false);
		    				}
		    				if (((Component)components[j]).getName().equals(ActionCommandText.CMD_USER_CHANGE_PASSWORD))
		    					((Component)components[j]).setEnabled(false);
	    				}
	    			}
	    		}
	    	}      
        }
	}
	

	/**
	 * @param item
	 * @param isPublished
	 * @param isEditedByUser
	 * @param model
	 */
	void projectOpenProject(JMenuBar item, boolean isPublished, boolean isEditedByUser, CStatsModel model) {	
		MenuElement[] elements = item.getSubElements();
		
	    for (int i=0; i<elements.length; i++) {
	    	if (elements[i] instanceof JMenu) {
	    		
	    		if (!((JMenu)elements[i]).getName().equals(MNU_FILE_FILE) &&
	    				!((JMenu)elements[i]).getName().equals(MNU_SRH_SRH) &&
	    				!((JMenu)elements[i]).getName().equals(MNU_HELP_HELP)) {
	    			Component[] components = ((JMenu)elements[i]).getMenuComponents();
       	    			
	    			for (int j=0; j<components.length; j++) {
	    				if (components[j] instanceof JMenu) {
	    					Component[] subComponents = ((JMenu)components[j]).getMenuComponents();
	    					
	    					for (int k=0; k<subComponents.length; k++) {
	    						if (((Component)subComponents[k]).getName() != null) {	    							
	    		    				if (((Component)subComponents[k]).getName().equals(ActionCommandText.CMD_EXTRA_EXPORT_SYNTAX) &&
	    		    						(model.getProject().getProgress().isMapVariableChaTabDone()))
	    		    					((Component)subComponents[k]).setEnabled(true);
	    		    				if (((Component)subComponents[k]).getName().equals(ActionCommandText.CMD_EXTRA_EXPORT_VARIABLE) &&
	    		    						(model.getProject().getProgress().isMapVariableChaTabDone())) 
	    		    					((Component)subComponents[k]).setEnabled(true);	
	    						}
	    					}
	    				}
	    				
	    				if (((Component)components[j]).getName() != null) {
		    				if (((Component)components[j]).getName().equals(ActionCommandText.CMD_PRJ_CLOSE))
		    					((Component)components[j]).setEnabled(true);
		    				if (((Component)components[j]).getName().equals(ActionCommandText.CMD_PRJ_SAVE))
		    					((Component)components[j]).setEnabled(!isPublished && isEditedByUser); 
		    				if (((Component)components[j]).getName().equals(ActionCommandText.CMD_PRJ_FINISH) &&
		    						(model.getProject().getParticipantByUser(model.getUser()) != null) &&
		    						(model.getProject().getParticipantByUser(model.getUser())).getRole().equals(ParticipantRole.PROJECT_OWNER)) {
		    					((Component)components[j]).setEnabled(!isPublished && isEditedByUser);
		    					((Component)components[j]).setVisible(!isPublished && isEditedByUser);
		    				}
		    				if (((Component)components[j]).getName().equals(ActionCommandText.CMD_PRJ_REMOVE) &&
		    						(model.getProject().getParticipantByUser(model.getUser()) != null) &&
		    						(model.getProject().getParticipantByUser(model.getUser())).getRole().equals(ParticipantRole.PROJECT_OWNER)) {
		    					((Component)components[j]).setEnabled(!isPublished && isEditedByUser);
		    					((Component)components[j]).setVisible(!isPublished && isEditedByUser);
		    				}
		    				// not used in QuickCharmStats
//		    				if (((Component)components[j]).getName().equals(ActionCommandText.CMD_PRJ_ADD_PARTICIPANT) &&
//		    						(!model.getProject().isFinished()) && 
//		    						(model.getProject().getParticipantByUser(model.getUser()) != null) &&
//		    						(model.getProject().getParticipantByUser(model.getUser())).getRole().equals(ParticipantRole.PROJECT_OWNER)) {
//		    					((Component)components[j]).setEnabled(true);
//		    					((Component)components[j]).setVisible(true);
//		    				}
//		    				if (((Component)components[j]).getName().equals(ActionCommandText.CMD_PRJ_EDIT_PARTICIPANT) &&
//    								(!model.getProject().isFinished()) && 		    				
//		    						(model.getProject().getParticipantByUser(model.getUser()) != null) &&
//		    						(model.getProject().getParticipantByUser(model.getUser())).getRole().equals(ParticipantRole.PROJECT_OWNER)) {
//		    					((Component)components[j]).setEnabled(true);
//		    					((Component)components[j]).setVisible(true);
//		    				}
	    				}
	    			}
	    		}
	    	}     
        }			
	}
	
	/**
	 * @param item
	 * @param model
	 */
	void projectNewProject(JMenuBar item, CStatsModel model) {
		MenuElement[] elements = item.getSubElements();
		
	    for (int i=0; i<elements.length; i++) {
	    	if (elements[i] instanceof JMenu) {
	    		
	    		if (!((JMenu)elements[i]).getName().equals(MNU_FILE_FILE) &&
	    				!((JMenu)elements[i]).getName().equals(MNU_SRH_SRH) &&
	    				!((JMenu)elements[i]).getName().equals(MNU_EXTRA_EXTRA) &&
	    				!((JMenu)elements[i]).getName().equals(MNU_HELP_HELP)) {
	    			Component[] components = ((JMenu)elements[i]).getMenuComponents();
       
	    			for (int j=0; j<components.length; j++) {
	    				if (((Component)components[j]).getName() != null) {
		    				if (((Component)components[j]).getName().equals(ActionCommandText.CMD_PRJ_CLOSE))
		    					((Component)components[j]).setEnabled(true);
		    				if (((Component)components[j]).getName().equals(ActionCommandText.CMD_PRJ_SAVE))
		    					((Component)components[j]).setEnabled(true);
		    				if (((Component)components[j]).getName().equals(ActionCommandText.CMD_PRJ_FINISH)) {
		    					((Component)components[j]).setEnabled(true);
		    					((Component)components[j]).setVisible(true);
		    				}
		    				if (((Component)components[j]).getName().equals(ActionCommandText.CMD_PRJ_REMOVE)) {
		    					((Component)components[j]).setEnabled(true);
		    					((Component)components[j]).setVisible(true);
		    				}
		    				// not used in QuickCharmStats
//		    				if (((Component)components[j]).getName().equals(ActionCommandText.CMD_PRJ_ADD_PARTICIPANT) &&
//		    						(model.getProject().getParticipantByUser(model.getUser()) != null) &&
//		    						(model.getProject().getParticipantByUser(model.getUser())).getRole().equals(ParticipantRole.PROJECT_OWNER)) {
//		    					((Component)components[j]).setEnabled(true);
//		    					((Component)components[j]).setVisible(true);
//		    				}
//		    				if (((Component)components[j]).getName().equals(ActionCommandText.CMD_PRJ_EDIT_PARTICIPANT) &&
//		    						(model.getProject().getParticipantByUser(model.getUser()) != null) &&
//		    						(model.getProject().getParticipantByUser(model.getUser())).getRole().equals(ParticipantRole.PROJECT_OWNER)) {
//		    					((Component)components[j]).setEnabled(true);
//		    					((Component)components[j]).setVisible(true);
//		    				}
	    				}
	    			}
	    		}
	    	}      
        }			
	}
	
	/**
	 * @param item
	 */
	void projectCloseProject(JMenuBar item) {
		MenuElement[] elements = item.getSubElements();
		
	    for (int i=0; i<elements.length; i++) {
	    	if (elements[i] instanceof JMenu) {
	    		
	    		if (!((JMenu)elements[i]).getName().equals(MNU_FILE_FILE) &&
	    				!((JMenu)elements[i]).getName().equals(MNU_SRH_SRH) &&
	    				!((JMenu)elements[i]).getName().equals(MNU_HELP_HELP)) {
	    			Component[] components = ((JMenu)elements[i]).getMenuComponents();
       
	    			for (int j=0; j<components.length; j++) {
	    				if (components[j] instanceof JMenu) {
	    					Component[] subComponents = ((JMenu)components[j]).getMenuComponents();
	    					
	    					for (int k=0; k<subComponents.length; k++) {
	    						if (((Component)subComponents[k]).getName() != null) {
	    		    				if (((Component)subComponents[k]).getName().equals(ActionCommandText.CMD_EXTRA_EXPORT_SYNTAX))
	    		    					((Component)subComponents[k]).setEnabled(false);
	    		    				if (((Component)subComponents[k]).getName().equals(ActionCommandText.CMD_EXTRA_EXPORT_VARIABLE))
	    		    					((Component)subComponents[k]).setEnabled(false);
	    						}
	    					}
	    				}
	    				
	    				if (((Component)components[j]).getName() != null) {
		    				if (((Component)components[j]).getName().equals(ActionCommandText.CMD_PRJ_CLOSE))
		    					((Component)components[j]).setEnabled(false);
		    				if (((Component)components[j]).getName().equals(ActionCommandText.CMD_PRJ_SAVE))
		    					((Component)components[j]).setEnabled(false);
		    				if (((Component)components[j]).getName().equals(ActionCommandText.CMD_PRJ_FINISH)) {
		    					((Component)components[j]).setEnabled(false);
		    					((Component)components[j]).setVisible(false);
		    				}
		    				if (((Component)components[j]).getName().equals(ActionCommandText.CMD_PRJ_REMOVE)) {
		    					((Component)components[j]).setEnabled(false);
		    					((Component)components[j]).setVisible(false);
		    				}
		    				if (((Component)components[j]).getName().equals(ActionCommandText.CMD_PRJ_ADD_PARTICIPANT)) {
		    					((Component)components[j]).setEnabled(false);
		    					((Component)components[j]).setVisible(false);
		    				}
		    				if (((Component)components[j]).getName().equals(ActionCommandText.CMD_PRJ_EDIT_PARTICIPANT)) {
		    					((Component)components[j]).setEnabled(false);
		    					((Component)components[j]).setVisible(false);
		    				}
	    				}
	    			}
	    		}
	    	}      
        }			
	}
	
	/**
	 * @param item
	 */
	void basketOpenBasket(JMenuBar item) {
		MenuElement[] elements = item.getSubElements();
		
	    for (int i=0; i<elements.length; i++) {
	    	if (elements[i] instanceof JMenu) {
	    		
	    		if (!((JMenu)elements[i]).getName().equals(MNU_FILE_FILE) &&
	    				!((JMenu)elements[i]).getName().equals(MNU_SRH_SRH) &&
	    				!((JMenu)elements[i]).getName().equals(MNU_EXTRA_EXTRA) &&
	    				!((JMenu)elements[i]).getName().equals(MNU_HELP_HELP)) {
	    			Component[] components = ((JMenu)elements[i]).getMenuComponents();
       
	    			for (int j=0; j<components.length; j++) {
	    				if (((Component)components[j]).getName() != null) {
		    				if (((Component)components[j]).getName().equals(ActionCommandText.CMD_BSKT_OPEN_BASKET))
		    					((Component)components[j]).setEnabled(false);
		    				if (((Component)components[j]).getName().equals(ActionCommandText.CMD_BSKT_CLOSE))
		    					((Component)components[j]).setEnabled(true);
		    				if (((Component)components[j]).getName().equals(ActionCommandText.CMD_BSKT_SAVE))
		    					((Component)components[j]).setEnabled(true);
		    				if (((Component)components[j]).getName().equals(ActionCommandText.CMD_BSKT_EMPTY))
		    					((Component)components[j]).setEnabled(true);
		    				if (((Component)components[j]).getName().equals(ActionCommandText.CMD_BSKT_EMPTY_TEMP_BASKET))
		    					((Component)components[j]).setEnabled(false);
	    				}
	    			}
	    		}
	    	}      
        }			
	}
	
	/**
	 * @param item
	 */
	void basketCloseBasket(JMenuBar item) {
		MenuElement[] elements = item.getSubElements();
		
	    for (int i=0; i<elements.length; i++) {
	    	if (elements[i] instanceof JMenu) {
	    		
	    		if (!((JMenu)elements[i]).getName().equals(MNU_FILE_FILE) &&
	    				!((JMenu)elements[i]).getName().equals(MNU_SRH_SRH) &&
	    				!((JMenu)elements[i]).getName().equals(MNU_EXTRA_EXTRA) &&
	    				!((JMenu)elements[i]).getName().equals(MNU_HELP_HELP)) {
	    			Component[] components = ((JMenu)elements[i]).getMenuComponents();
       
	    			for (int j=0; j<components.length; j++) {
	    				if (((Component)components[j]).getName() != null) {
		    				if (((Component)components[j]).getName().equals(ActionCommandText.CMD_BSKT_OPEN_BASKET))
		    					((Component)components[j]).setEnabled(true);
		    				if (((Component)components[j]).getName().equals(ActionCommandText.CMD_BSKT_CLOSE))
		    					((Component)components[j]).setEnabled(false);
		    				if (((Component)components[j]).getName().equals(ActionCommandText.CMD_BSKT_SAVE))
		    					((Component)components[j]).setEnabled(false);
		    				if (((Component)components[j]).getName().equals(ActionCommandText.CMD_BSKT_EMPTY))
		    					((Component)components[j]).setEnabled(false);
		    				if (((Component)components[j]).getName().equals(ActionCommandText.CMD_BSKT_EMPTY_TEMP_BASKET))
		    					((Component)components[j]).setEnabled(true);
	    				}
	    			}
	    		}
	    	}      
        }			
	}
		
}

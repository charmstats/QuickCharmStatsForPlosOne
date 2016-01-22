package org.gesis.charmstats.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeListener;
import javax.swing.undo.UndoManager;

import org.gesis.charmstats.ActionCommandID;
import org.gesis.charmstats.ActionCommandText;
import org.gesis.charmstats.IdentifiedParameter;
import org.gesis.charmstats.LoginPreference;
import org.gesis.charmstats.model.CStatsModel;
import org.gesis.charmstats.model.Measurement;
import org.gesis.charmstats.model.Participant;
import org.gesis.charmstats.model.Person;
import org.gesis.charmstats.model.Project;
import org.gesis.charmstats.model.User;
import org.gesis.charmstats.model.Variable;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class CStatsGUI extends Observable implements Observer {

	public static final String BUNDLE_F		= "org.gesis.charmstats.resources.FrameBundle";
	public static final String BUNDLE_D		= "org.gesis.charmstats.resources.DesktopBundle";
	
	/* SHARED RESOURCES */
	public static final String NEW_ICON		= "org/gesis/charmstats/resources/New24.gif"; 
	public static final String SAVE_ICON	= "org/gesis/charmstats/resources/Save24.gif"; 
	public static final String OPEN_ICON	= "org/gesis/charmstats/resources/Open24.gif"; 
	public static final String SYNTAX_ICON	= "org/gesis/charmstats/resources/Preferences24.gif"; 
	public static final String FORM_ICON	= "org/gesis/charmstats/resources/quick-charmstats-16x16.png"; 
	public static final String GRAPH_ICON	= "org/gesis/charmstats/resources/Paste24.gif"; 
	public static final String REPORT_ICON	= "org/gesis/charmstats/resources/Properties24.gif"; 
	public static final String SEARCH_ICON	= "org/gesis/charmstats/resources/Find24.gif";
	public static final String PROJECT_ICON	= "org/gesis/charmstats/resources/Home24.gif";
	public static final String BASKET_ICON	= "org/gesis/charmstats/resources/Bookmarks24.gif";
	public static final String UNDO_ICON	= "org/gesis/charmstats/resources/undo.gif";
	public static final String REDO_ICON	= "org/gesis/charmstats/resources/redo.gif";
	public static final String CUT_ICON		= "org/gesis/charmstats/resources/cut.gif";
	public static final String COPY_ICON	= "org/gesis/charmstats/resources/copy.gif";
	public static final String PASTE_ICON	= "org/gesis/charmstats/resources/paste.gif";
	public static final String REMOVE_ICON	= "org/gesis/charmstats/resources/delete.gif";
	
	public static final String NO_PROJECT	= "no_project";
	public static final String NO_PROJECTS	= "no_project";
	public static final String NO_MEASURES	= "no_measures";
	public static final String NO_VARIABLES	= "no_variables";
	public static final String NO_USERS		= "no_users";
	public static final String NO_PERSONS	= "no_persons";
	public static final String NO_PARTICIPANTS	= "no_participants";
	public static final String NO_HELP		= "no_help";
	public static final String NO_PAIRING	= "no_pairing";

	
	public static User ccats_user			= null;
	Connection ccats_con					= null;
	Font menuFont							= null;
	
	
	/*
	 *	Fields
	 */
	private CStatsModel		_model;	
	private Locale			currentLocale;
	private ActionListener	actionListener;
	private ChangeListener	changeListener;
		
    /* Application Frame */
	private ApplicationFrame 			appFrame;
	private MenuBar 		menuBar;
	private ToolBar			toolBar;
	private DesktopPane		desktop;
	private StatusBar		statusBar;
	
	private Dimension		screenSize;
	
	private Tab				previousStep;
	private Tab				projectStep;
	private Tab				defaultTab;
	private Tab				mapDimensionCharStep;
	private Tab				mapIndicatorCharStep;
	private Tab				mapVariableCharStep;

	/*
	 *	Constructor
	 */
	/**
	 * @param model
	 * @param conn
	 * @param locale
	 * @param alistener
	 * @param cListener
	 */
	public CStatsGUI(CStatsModel model, Connection conn, Locale locale, ActionListener alistener, ChangeListener cListener) {
		super();
		
		_model			= model;
		ccats_con		= conn;
		currentLocale	= locale;
		actionListener	= alistener;
		changeListener	= cListener;
		
		setScreenSize(Toolkit.getDefaultToolkit().getScreenSize());
		
		initComponents();
	}

	/*
	 *	Methods
	 */
	/**
	 * 
	 */
	private void initComponents() {
		/* FRAME / TITLE BAR */
		setAppFrame(buildFrame(currentLocale));
		getAppFrame().getContentPane().setLayout(new BorderLayout());
    	
    	/* MENU BAR */
        menuBar = buildMenuBar(actionListener, currentLocale);
        menuFont = menuBar.getFont();
        getAppFrame().setJMenuBar(menuBar);     

        /* CONTENT PANE */        
        /* Tool Bar */
        toolBar = buildToolBar(actionListener, currentLocale);
        toolBar.setEnabled(false);
		getAppFrame().getContentPane().add(toolBar, BorderLayout.NORTH);
		/* Desktop */
        desktop = buildDesktop(actionListener, changeListener, currentLocale, getModel());
		getAppFrame().getContentPane().add(desktop, BorderLayout.CENTER);
		/* Status Bar */
		statusBar = buildStatusBar(currentLocale);
		getAppFrame().getContentPane().add(statusBar, BorderLayout.SOUTH);
		
		previousStep = ((FormFrame)desktop.getFormFrame()).getHashMap().get("TabProject");
		projectStep = ((FormFrame)desktop.getFormFrame()).getHashMap().get("TabProject");
		mapDimensionCharStep = ((FormFrame)desktop.getFormFrame()).getHashMap().get("TabMapDimensionChar");
		mapIndicatorCharStep = ((FormFrame)desktop.getFormFrame()).getHashMap().get("TabMapIndicatorChar");
		mapVariableCharStep = ((FormFrame)desktop.getFormFrame()).getHashMap().get("TabMapVariableChar");
		
		/* Set best Size */
		getAppFrame().pack();
		getAppFrame().getContentPane().setPreferredSize(
				new Dimension(1095, toolBar.getSize().height + 625 + statusBar.getSize().height) );
		getAppFrame().pack();
		
		/* Resize */
	    Dimension screenSize	= Toolkit.getDefaultToolkit().getScreenSize();
	    Dimension frameSize		= getAppFrame().getSize();
	    
	    if (frameSize.height > screenSize.height)
	    	frameSize.height = screenSize.height;
	    if (frameSize.width > screenSize.width)
	    	frameSize.width = screenSize.width;
	   
	    /* Center the Application window */
	    getAppFrame().setLocation(
	    		(screenSize.width-frameSize.width)/2, (screenSize.height-frameSize.height)/2 );
	 
        getAppFrame().setVisible(true);
	}
	
	/**
	 * @param locale
	 * @return
	 */
	private ApplicationFrame buildFrame(Locale locale) {
		return new ApplicationFrame(_model, locale);
	}
	
	/**
	 * @param al
	 * @param locale
	 * @return
	 */
	private MenuBar buildMenuBar(ActionListener al, Locale locale){
		return new MenuBar(al, locale);
    }
	
	/**
	 * @param al
	 * @param locale
	 * @return
	 */
	private ToolBar buildToolBar(ActionListener al, Locale locale) {		
		return new ToolBar(al, locale);
	}
	
	/**
	 * @param al
	 * @param cl
	 * @param locale
	 * @param addenda
	 * @return
	 */
	private DesktopPane buildDesktop(ActionListener al, ChangeListener cl,  Locale locale, Object addenda) {
		return new DesktopPane(al, cl, locale, this);
	}
	
	/**
	 * @param locale
	 * @return
	 */
	private StatusBar buildStatusBar(Locale locale) {
		return new StatusBar(locale);
	}
	
	/**
	 * @return
	 */
	public DesktopPane getDesktop() {
		return desktop;
	}
	
	/**
	 * @param locale
	 */
	protected void changeLanguage(Locale locale) {
		getAppFrame().changeLanguage(locale);
		menuBar.changeLanguage(locale);
		toolBar.changeLanguage(locale);
		desktop.changeLanguage(locale);
		statusBar.changeLanguage(locale);
	}
	
	/* SHARED METHOD */
	protected static ImageIcon createImageIcon(String resourceURL,
			String description) {
		java.net.URL imgURL = CStatsGUI.class.getClassLoader().getResource(resourceURL);
		if (imgURL != null) {
	   		return new ImageIcon(imgURL, description);
	   	} else {
	   		System.err.println("Couldn't find file: " + resourceURL);
	   		return null;
	   	}
	}
			
	/**
	 * @return
	 */
	public Dimension getScreenSize() {
		return screenSize;
	}

	/**
	 * @param screenSize
	 */
	private void setScreenSize(Dimension screenSize) {
		this.screenSize = screenSize;
	}
	
	/**
	 * @return
	 */
	public Tab getProjectStep() {
		return projectStep;
	}

	/**
	 * @param projectStep
	 */
	public void setProjectStep(Tab projectStep) {
		this.projectStep = projectStep;
	}
	
	/**
	 * @return
	 */
	public Tab getPreviousStep() {
		return previousStep;
	}
	
	/**
	 * @return
	 */
	public Tab getMapDimensionChar() {
		return mapDimensionCharStep;
	}
	
	/**
	 * @return
	 */
	public Tab getMapIndicatorChar() {
		return mapIndicatorCharStep;
	}
	
	/**
	 * @return
	 */
	public Tab getMapVariableChar() {
		return mapVariableCharStep;
	}

	/**
	 * @param previousStep
	 */
	public void setPreviousStep(Tab previousStep) {
		this.previousStep = previousStep;
	}

	/**
	 * @return
	 */
	protected CStatsModel getModel() {
		return _model;
	}
			
	/**
	 * @return
	 */
	public HashMap<String, Tab> getFormulars() {
		return desktop.getFormulars();
	}

	/**
	 * @param pref
	 */
	@SuppressWarnings("unused")
	private void login(LoginPreference pref) {
		closeProject(false);
		
       	boolean registration = false;
        int countOfLoginAttempts = 0;
                
        while (!registration && countOfLoginAttempts < 3) {
        	LoginDialog dialog = new LoginDialog(pref, currentLocale, getFont(), actionListener);        	        	
        	_model.setUser(dialog.getLoginUser());
        	
        	if (!_model.getUser().equals(User.getAnonymousUser())) {
        	
	        	registration = _model.getUser().checkUserAccount(ccats_con);
	        	
	        	if (!registration)
	        		JOptionPane.showMessageDialog(getAppFrame(), "Wrong Username or Password");
	        	
	            dialog.setVisible(false);
	            dialog.dispose();
	            
	            countOfLoginAttempts++;
        	} else
        		countOfLoginAttempts = 3;
        };
        if (!registration) {
        	JOptionPane.showMessageDialog(getAppFrame(), "Login failed!");
        }
        else { 
        	System.err.println("Successfully logged in by user " + _model.getUser().getName());
        
        	ccats_user = _model.getUser();
        	getAppFrame().changeLanguage(currentLocale);
        } 
	}

	/**
	 * @param b
	 */
	private void closeProject(boolean b) {
		// Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		IdentifiedParameter parameter = null;
		
		if (arg instanceof IdentifiedParameter) {
			parameter = (IdentifiedParameter)arg;
		}
				
		if (parameter != null) {
			Object[]	addenda		= parameter.getParameters();
			
			switch (parameter.getID()) {
				case ActionCommandID.CMD_FILE_EXIT:
					
					statusBar.setLeftSideMessage(ActionCommandText.CMD_FILE_EXIT);
					break;
				case ActionCommandID.CMD_EDIT_UNDO:
					
					statusBar.setLeftSideMessage(ActionCommandText.CMD_EDIT_UNDO);
					break;
				case ActionCommandID.CMD_EDIT_REDO:
					
					statusBar.setLeftSideMessage(ActionCommandText.CMD_EDIT_REDO);
					break;
				case ActionCommandID.CMD_EDIT_CUT:
					
					statusBar.setLeftSideMessage(ActionCommandText.CMD_EDIT_CUT);
					break;
				case ActionCommandID.CMD_EDIT_COPY:
					
					statusBar.setLeftSideMessage(ActionCommandText.CMD_EDIT_COPY);
					break;
				case ActionCommandID.CMD_EDIT_PASTE:
					
					statusBar.setLeftSideMessage(ActionCommandText.CMD_EDIT_PASTE);
					break;
				case ActionCommandID.CMD_EDIT_REMOVE:
					
					statusBar.setLeftSideMessage(ActionCommandText.CMD_EDIT_REMOVE);
					break;
				case ActionCommandID.CMD_SRH_SEARCH:
					
					statusBar.setLeftSideMessage(ActionCommandText.CMD_SRH_SEARCH);
					break;
				case ActionCommandID.CMD_PRJ_NEW_PROJECT:
					menuBar.projectNewProject(getAppFrame().getJMenuBar(), (CStatsModel)addenda[0]);
					toolBar.enableSave();
					
					defaultTab = ((FormFrame)desktop.getFormFrame()).getHashMap().get("TabMeasurement");
					defaultTab.autoCompleteButton.setEnabled(true);
					defaultTab.importButton.setEnabled(true);
					
					defaultTab = ((FormFrame)desktop.getFormFrame()).getHashMap().get("TabProject");
					defaultTab.noteButton.setEnabled(true); 
					defaultTab.nextButton.setEnabled(true); 
					defaultTab.resetButton.setEnabled(true);
					
					previousStep = defaultTab; 
					projectStep = defaultTab; 
					
					defaultTab = ((FormFrame)desktop.getFormFrame()).getHashMap().get("TabConcept");
					defaultTab.resetButton.setEnabled(true);
					
					defaultTab = ((FormFrame)desktop.getFormFrame()).getHashMap().get("TabMapDimensionChar");
					defaultTab.confirmButton.setEnabled(true);
					defaultTab.nextButton.setEnabled(false);
					defaultTab = ((FormFrame)desktop.getFormFrame()).getHashMap().get("TabMapIndicatorChar");
					defaultTab.confirmButton.setEnabled(true);
					defaultTab.nextButton.setEnabled(false);
					defaultTab = ((FormFrame)desktop.getFormFrame()).getHashMap().get("TabMapVariableChar");
					defaultTab.confirmButton.setEnabled(true);
					defaultTab.nextButton.setEnabled(false);
					
					statusBar.setLeftSideMessage(ActionCommandText.CMD_PRJ_NEW_PROJECT);
					break;
				case ActionCommandID.BTN_TGT_STP_TGT_TAB_AUTO:
				case ActionCommandID.CMD_PRJ_OPEN_PROJECT:
					menuBar.projectOpenProject(getAppFrame().getJMenuBar(), _model.getProject().isFinished(), _model.getProject().isEditedByUser(), (CStatsModel)addenda[0]);
					if (!((CStatsModel)addenda[0]).getProject().isFinished())
						toolBar.enableSave();
					if (((CStatsModel)addenda[0]).getProject().getProgress().isMapVariableChaTabDone())
						toolBar.enableSyntax();
					
					defaultTab = ((FormFrame)desktop.getFormFrame()).getHashMap().get("TabProject");
					defaultTab.noteButton.setEnabled(true); 
					defaultTab.nextButton.setEnabled(true); 
					defaultTab.resetButton.setEnabled(true);
					
					previousStep = defaultTab; 
					projectStep = defaultTab; 
					
					defaultTab = ((FormFrame)desktop.getFormFrame()).getHashMap().get("TabConcept");
					defaultTab.resetButton.setEnabled(true);
					
					defaultTab = ((FormFrame)desktop.getFormFrame()).getHashMap().get("TabMeasurement");

					ArrayList<Variable>	_vars	= _model.getProject().getContent().getVariables();
					Measurement			_mea	= _model.getProject().getContent().getMeasurement();
					defaultTab.autoCompleteButton.setEnabled(_vars.size() < 1);
					defaultTab.importButton.setEnabled(_mea.getEntityID() < 0);
					
					/* disabled in QuickCharmStats: */
					defaultTab.nextButton.setEnabled(false);
					
					defaultTab = ((FormFrame)desktop.getFormFrame()).getHashMap().get("TabMapDimensionChar");
					defaultTab.confirmButton.setEnabled(true);
					defaultTab.nextButton.setEnabled(false);
					defaultTab = ((FormFrame)desktop.getFormFrame()).getHashMap().get("TabMapIndicatorChar");
					defaultTab.confirmButton.setEnabled(true);
					defaultTab.nextButton.setEnabled(false);
					defaultTab = ((FormFrame)desktop.getFormFrame()).getHashMap().get("TabMapVariableChar");
					defaultTab.confirmButton.setEnabled(true);
					defaultTab.nextButton.setEnabled(false);
					
					appFrame.changeLanguage(currentLocale);
					
					statusBar.setLeftSideMessage(ActionCommandText.CMD_PRJ_OPEN_PROJECT);
					break;
				case ActionCommandID.CMD_PRJ_REMOVE:
				case ActionCommandID.CMD_PRJ_CLOSE:
					menuBar.projectCloseProject(getAppFrame().getJMenuBar());
					toolBar.disableSave();
					toolBar.disableSyntax();
					projectStep.resetButton.setEnabled(false);
					projectStep.noteButton.setEnabled(false);
					projectStep.nextButton.setEnabled(false);
					
					defaultTab = ((FormFrame)desktop.getFormFrame()).getHashMap().get("TabProject");
					defaultTab.resetButton.setEnabled(false);
					defaultTab = ((FormFrame)desktop.getFormFrame()).getHashMap().get("TabConcept");
					defaultTab.resetButton.setEnabled(false);
					
					defaultTab = ((FormFrame)desktop.getFormFrame()).getHashMap().get("TabMeasurement");
					defaultTab.autoCompleteButton.setEnabled(false);
					defaultTab.importButton.setEnabled(false);
					
					defaultTab = ((FormFrame)desktop.getFormFrame()).getHashMap().get("TabMapDimensionChar");
					defaultTab.confirmButton.setEnabled(true);
					defaultTab.nextButton.setEnabled(false);
					defaultTab = ((FormFrame)desktop.getFormFrame()).getHashMap().get("TabMapIndicatorChar");
					defaultTab.confirmButton.setEnabled(true);
					defaultTab.nextButton.setEnabled(false);
					defaultTab = ((FormFrame)desktop.getFormFrame()).getHashMap().get("TabMapVariableChar");
					defaultTab.confirmButton.setEnabled(true);
					defaultTab.nextButton.setEnabled(false);
					
					appFrame.changeLanguage(currentLocale);
					
					statusBar.setLeftSideMessage(ActionCommandText.CMD_PRJ_CLOSE);
					break;
				case ActionCommandID.CMD_PRJ_CLOSE_ALL:
					
					statusBar.setLeftSideMessage(ActionCommandText.CMD_PRJ_CLOSE_ALL);
					break;
				case ActionCommandID.CMD_PRJ_SAVE:
					
					statusBar.setLeftSideMessage(ActionCommandText.CMD_PRJ_SAVE);
					break;
				case ActionCommandID.CMD_PRJ_SAVE_AS:
					
					statusBar.setLeftSideMessage(ActionCommandText.CMD_PRJ_SAVE_AS);
					break;
				case ActionCommandID.CMD_PRJ_SAVE_ALL:
					
					statusBar.setLeftSideMessage(ActionCommandText.CMD_PRJ_SAVE_ALL);
					break;
				case ActionCommandID.CMD_PRJ_FINISH:
					
					statusBar.setLeftSideMessage(ActionCommandText.CMD_PRJ_FINISH);
					break;
				case ActionCommandID.CMD_BSKT_NEW_BASKET:
					
					statusBar.setLeftSideMessage(ActionCommandText.CMD_BSKT_NEW_BASKET);
					break;
				case ActionCommandID.CMD_BSKT_OPEN_BASKET:
					menuBar.basketOpenBasket(getAppFrame().getJMenuBar());
					
					statusBar.setLeftSideMessage(ActionCommandText.CMD_BSKT_OPEN_BASKET);
					break;
				case ActionCommandID.CMD_BSKT_CLOSE:
					menuBar.basketCloseBasket(getAppFrame().getJMenuBar());
					
					statusBar.setLeftSideMessage(ActionCommandText.CMD_BSKT_CLOSE);
					break;
				case ActionCommandID.CMD_BSKT_CLOSE_ALL:
					
					statusBar.setLeftSideMessage(ActionCommandText.CMD_BSKT_CLOSE_ALL);
					break;
				case ActionCommandID.CMD_BSKT_SAVE:
					
					statusBar.setLeftSideMessage(ActionCommandText.CMD_BSKT_SAVE);
					break;
				case ActionCommandID.CMD_BSKT_SAVE_AS:
					
					statusBar.setLeftSideMessage(ActionCommandText.CMD_BSKT_SAVE_AS);
					break;
				case ActionCommandID.CMD_BSKT_SAVE_ALL:
					
					statusBar.setLeftSideMessage(ActionCommandText.CMD_BSKT_SAVE_ALL);
					break;
				case ActionCommandID.CMD_BSKT_EMPTY:
					
					statusBar.setLeftSideMessage(ActionCommandText.CMD_BSKT_EMPTY);
					break;
				case ActionCommandID.CMD_BSKT_EMPTY_TEMP_BASKET:
					
					statusBar.setLeftSideMessage(ActionCommandText.CMD_BSKT_EMPTY_TEMP_BASKET);
					break;
				case ActionCommandID.CMD_USER_LOGIN:
					getAppFrame().changeLanguage(currentLocale); 
					menuBar.userLogin(getAppFrame().getJMenuBar(), (CStatsModel)addenda[0]);
					toolBar.setEnabled(true);
					
					statusBar.setLeftSideMessage(ActionCommandText.CMD_USER_LOGIN);
					break;
				case ActionCommandID.CMD_USER_LOGOFF:
					getAppFrame().changeLanguage(currentLocale); 
					menuBar.userLogoff(getAppFrame().getJMenuBar());
					toolBar.setEnabled(false);
					
					statusBar.setLeftSideMessage(ActionCommandText.CMD_USER_LOGOFF);
					break;
				case ActionCommandID.CMD_EXTRA_INC_FONT_SIZE:
					if (menuFont.getSize() < 24) {
						int largerSize = menuFont.getSize()+1;
						menuFont = menuFont.deriveFont((float)largerSize);
						
						javax.swing.UIManager.put("OptionPane.messageFont", menuFont);
						javax.swing.UIManager.put("OptionPane.buttonFont", menuFont);
						javax.swing.UIManager.put("OptionPane.font", menuFont);
						javax.swing.UIManager.put("Table.font", menuFont);
						
						appFrame.changeFont(menuFont);
						menuBar.changeFont(menuFont);
						desktop.changeFont(menuFont);
						statusBar.changeFont(menuFont);
						
						statusBar.setLeftSideMessage(ActionCommandText.CMD_EXTRA_INC_FONT_SIZE);
					}
					break;
				case ActionCommandID.CMD_EXTRA_DEC_FONT_SIZE:
					if (menuFont.getSize() > 5) {
						int smallerSize = menuFont.getSize()-1;
						menuFont = menuFont.deriveFont((float)smallerSize);
						
						javax.swing.UIManager.put("OptionPane.messageFont", menuFont);
						javax.swing.UIManager.put("OptionPane.buttonFont", menuFont);
						javax.swing.UIManager.put("OptionPane.font", menuFont);
						javax.swing.UIManager.put("Table.font", menuFont);
						
						appFrame.changeFont(menuFont);
						menuBar.changeFont(menuFont);
						desktop.changeFont(menuFont);
						statusBar.changeFont(menuFont);
						
						statusBar.setLeftSideMessage(ActionCommandText.CMD_EXTRA_DEC_FONT_SIZE);
					}
					break;
				case ActionCommandID.CMD_EXTRA_LANGUAGE_ENGLISH:
					currentLocale = new Locale("en", "US"); 
					changeLanguage(currentLocale);
					
					statusBar.setLeftSideMessage(ActionCommandText.CMD_EXTRA_LANGUAGE_ENGLISH + "("+ currentLocale.getISO3Country() +","+ currentLocale.getISO3Language() +")");
					break;
				case ActionCommandID.CMD_EXTRA_LANGUAGE_GERMAN:
					currentLocale = new Locale("de", "DE"); 
					changeLanguage(currentLocale);
					
					statusBar.setLeftSideMessage(ActionCommandText.CMD_EXTRA_LANGUAGE_GERMAN + "("+ currentLocale.getISO3Country() +","+ currentLocale.getISO3Language() +")");
					break;
				case ActionCommandID.CMD_HELP_HELP:
					
					statusBar.setLeftSideMessage(ActionCommandText.CMD_HELP_HELP);
					break;
				case ActionCommandID.CMD_HELP_ABOUT:
					AboutDialog aboutDialog = new AboutDialog(getAppFrame(), currentLocale, menuFont);
										
					Dimension frameSize		= getAppFrame().getSize();
					Dimension dialogSize	= aboutDialog.getSize();
					
					aboutDialog.setLocation(
				    		getAppFrame().getLocation().x + ((frameSize.width-dialogSize.width)/2), getAppFrame().getLocation().y + ((frameSize.height-dialogSize.height)/2) );
					
					aboutDialog.setVisible(true);
					
					statusBar.setLeftSideMessage(ActionCommandText.CMD_HELP_ABOUT);
					break;
				case ActionCommandID.BTN_PRJ_STP_PRJ_TAB_BACK:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_PRJ_STP_PRJ_TAB_BACK);
					break;
				case ActionCommandID.BTN_PRJ_STP_PRJ_TAB_RESET:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_PRJ_STP_PRJ_TAB_RESET);
					break;
				case ActionCommandID.BTN_PRJ_STP_PRJ_TAB_NOTE:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_PRJ_STP_PRJ_TAB_NOTE);
					break;
				case ActionCommandID.BTN_PRJ_STP_PRJ_TAB_NEXT:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_PRJ_STP_PRJ_TAB_NEXT);
					break;
				case ActionCommandID.BTN_PRJ_STP_CON_TAB_BACK:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_PRJ_STP_CON_TAB_BACK);
					break;
				case ActionCommandID.BTN_PRJ_STP_CON_TAB_RESET:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_PRJ_STP_CON_TAB_RESET);
					break;
				case ActionCommandID.BTN_PRJ_STP_CON_TAB_NOTE:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_PRJ_STP_CON_TAB_NOTE);
					break;
				case ActionCommandID.BTN_PRJ_STP_CON_TAB_NEXT:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_PRJ_STP_CON_TAB_NEXT);
					break;
				case ActionCommandID.BTN_PRJ_STP_LIT_TAB_BACK:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_PRJ_STP_LIT_TAB_BACK);
					break;
				case ActionCommandID.BTN_PRJ_STP_LIT_TAB_RESET:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_PRJ_STP_LIT_TAB_RESET);
					break;
				case ActionCommandID.BTN_PRJ_STP_LIT_TAB_NOTE:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_PRJ_STP_LIT_TAB_NOTE);
					break;
				case ActionCommandID.BTN_PRJ_STP_LIT_TAB_NEXT:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_PRJ_STP_LIT_TAB_NEXT);
					break;
				case ActionCommandID.BTN_TGT_STP_TGT_TAB_BACK:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_TGT_STP_TGT_TAB_BACK);
					break;
				case ActionCommandID.BTN_TGT_STP_TGT_TAB_RESET:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_TGT_STP_TGT_TAB_RESET);
					break;
				case ActionCommandID.BTN_TGT_STP_TGT_TAB_NOTE:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_TGT_STP_TGT_TAB_NOTE);
					break;
				case ActionCommandID.BTN_TGT_STP_TGT_TAB_IMP:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_TGT_STP_TGT_TAB_IMP);
					break;
				case ActionCommandID.BTN_TGT_STP_TGT_TAB_NEXT:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_TGT_STP_TGT_TAB_NEXT);
					break;
				case ActionCommandID.BTN_CON_STP_DIM_TAB_BACK:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_CON_STP_DIM_TAB_BACK);
					break;
				case ActionCommandID.BTN_CON_STP_DIM_TAB_RESET:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_CON_STP_DIM_TAB_RESET);
					break;
				case ActionCommandID.BTN_CON_STP_DIM_TAB_NOTE:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_CON_STP_DIM_TAB_NOTE);
					break;
				case ActionCommandID.BTN_CON_STP_DIM_TAB_NEXT:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_CON_STP_DIM_TAB_NEXT);
					break;
				case ActionCommandID.BTN_CON_STP_SPE_TAB_BACK:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_CON_STP_SPE_TAB_BACK);
					break;
				case ActionCommandID.BTN_CON_STP_SPE_TAB_RESET:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_CON_STP_SPE_TAB_RESET);
					break;	
				case ActionCommandID.BTN_CON_STP_SPE_TAB_NOTE:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_CON_STP_SPE_TAB_NOTE);
					break;
				case ActionCommandID.BTN_CON_STP_SPE_TAB_NEXT:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_CON_STP_SPE_TAB_NEXT);
					break;
				case ActionCommandID.BTN_CON_STP_MAP_CHA_CONFIRM:
					defaultTab = ((FormFrame)desktop.getFormFrame()).getHashMap().get("TabMapDimensionChar");
					defaultTab.confirmButton.setEnabled(false);
					defaultTab.nextButton.setEnabled(true);
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_CON_STP_MAP_CHA_CONFIRM);
					break;
				case ActionCommandID.BTN_OPE_STP_INS_TAB_BACK:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_OPE_STP_INS_TAB_BACK);
					break;
				case ActionCommandID.BTN_OPE_STP_INS_TAB_RESET:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_OPE_STP_INS_TAB_RESET);
					break;
				case ActionCommandID.BTN_OPE_STP_INS_TAB_NOTE:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_OPE_STP_INS_TAB_NOTE);
					break;
				case ActionCommandID.BTN_OPE_STP_INS_TAB_NEXT:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_OPE_STP_INS_TAB_NEXT);
					break;	
				case ActionCommandID.BTN_OPE_STP_IND_TAB_BACK:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_OPE_STP_IND_TAB_BACK);
					break;
				case ActionCommandID.BTN_OPE_STP_IND_TAB_RESET:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_OPE_STP_IND_TAB_RESET);
					break;
				case ActionCommandID.BTN_OPE_STP_IND_TAB_NOTE:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_OPE_STP_IND_TAB_NOTE);
					break;
				case ActionCommandID.BTN_OPE_STP_IND_TAB_NEXT:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_OPE_STP_IND_TAB_NEXT);
					break;
				case ActionCommandID.BTN_OPE_STP_PRE_TAB_BACK:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_OPE_STP_PRE_TAB_BACK);
					break;
				case ActionCommandID.BTN_OPE_STP_PRE_TAB_RESET:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_OPE_STP_PRE_TAB_RESET);
					break;
				case ActionCommandID.BTN_OPE_STP_PRE_TAB_NOTE:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_OPE_STP_PRE_TAB_NOTE);
					break;
				case ActionCommandID.BTN_OPE_STP_PRE_TAB_NEXT:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_OPE_STP_PRE_TAB_NEXT);
					break;
				case ActionCommandID.BTN_OPE_STP_MAP_INS_BACK:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_OPE_STP_MAP_INS_BACK);
					break;
				case ActionCommandID.BTN_OPE_STP_MAP_INS_RESET:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_OPE_STP_MAP_INS_RESET);
					break;
				case ActionCommandID.BTN_OPE_STP_MAP_INS_NOTE:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_OPE_STP_MAP_INS_NOTE);
					break;
				case ActionCommandID.BTN_OPE_STP_MAP_INS_NEXT:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_OPE_STP_MAP_INS_NEXT);
					break;
				case ActionCommandID.BTN_OPE_STP_MAP_ATR_BACK:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_OPE_STP_MAP_ATR_BACK);
					break;
				case ActionCommandID.BTN_OPE_STP_MAP_ATR_RESET:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_OPE_STP_MAP_ATR_RESET);
					break;
				case ActionCommandID.BTN_OPE_STP_MAP_ATR_NOTE:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_OPE_STP_MAP_ATR_NOTE);
					break;
				case ActionCommandID.BTN_OPE_STP_MAP_ATR_NEXT:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_OPE_STP_MAP_ATR_NEXT);
					break;
				case ActionCommandID.BTN_OPE_STP_MAP_CHA_BACK:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_OPE_STP_MAP_CHA_BACK);
					break;
				case ActionCommandID.BTN_OPE_STP_MAP_CHA_RESET:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_OPE_STP_MAP_CHA_RESET);
					break;
				case ActionCommandID.BTN_OPE_STP_MAP_CHA_NOTE:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_OPE_STP_MAP_CHA_NOTE);
					break;
				case ActionCommandID.BTN_OPE_STP_MAP_CHA_CONFIRM:
					defaultTab = ((FormFrame)desktop.getFormFrame()).getHashMap().get("TabMapIndicatorChar");
					defaultTab.confirmButton.setEnabled(false);
					defaultTab.nextButton.setEnabled(true);
					
					defaultTab = ((FormFrame)desktop.getFormFrame()).getHashMap().get("TabMeasurement");
					defaultTab.autoCompleteButton.setEnabled(false);
					defaultTab.importButton.setEnabled(false);
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_OPE_STP_MAP_CHA_CONFIRM);
					break;
				case ActionCommandID.BTN_OPE_STP_MAP_CHA_NEXT:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_OPE_STP_MAP_CHA_NEXT);
					break;
				case ActionCommandID.BTN_SEA_STP_SEA_TAB_BACK:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_SEA_STP_SEA_TAB_BACK);
					break;
				case ActionCommandID.BTN_SEA_STP_SEA_TAB_RESET:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_SEA_STP_SEA_TAB_RESET);
					break;
				case ActionCommandID.BTN_SEA_STP_SEA_TAB_NOTE:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_SEA_STP_SEA_TAB_NOTE);
					break;
				case ActionCommandID.BTN_SEA_STP_SEA_TAB_NEXT:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_SEA_STP_SEA_TAB_NEXT);
					break;
				case ActionCommandID.BTN_SEA_STP_COM_TAB_BACK:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_SEA_STP_COM_TAB_BACK);
					break;
				case ActionCommandID.BTN_SEA_STP_COM_TAB_RESET:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_SEA_STP_COM_TAB_RESET);
					break;
				case ActionCommandID.BTN_SEA_STP_COM_TAB_NOTE:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_SEA_STP_COM_TAB_NOTE);
					break;
				case ActionCommandID.BTN_SEA_STP_COM_TAB_NEXT:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_SEA_STP_COM_TAB_NEXT);
					break;
				case ActionCommandID.BTN_SEA_STP_VAL_TAB_BACK:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_SEA_STP_VAL_TAB_BACK);
					break;
				case ActionCommandID.BTN_SEA_STP_VAL_TAB_RESET:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_SEA_STP_VAL_TAB_RESET);
					break;
				case ActionCommandID.BTN_SEA_STP_VAL_TAB_NOTE:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_SEA_STP_VAL_TAB_NOTE);
					break;
				case ActionCommandID.BTN_SEA_STP_VAL_TAB_NEXT:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_SEA_STP_VAL_TAB_NEXT);
					break;
				case ActionCommandID.BTN_DAT_STP_INS_TAB_BACK:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_DAT_STP_INS_TAB_BACK);
					break;
				case ActionCommandID.BTN_DAT_STP_INS_TAB_RESET:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_DAT_STP_INS_TAB_RESET);
					break;
				case ActionCommandID.BTN_DAT_STP_INS_TAB_NOTE:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_DAT_STP_INS_TAB_NOTE);
					break;
				case ActionCommandID.BTN_DAT_STP_INS_TAB_NEXT:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_DAT_STP_INS_TAB_NEXT);
					break;
				case ActionCommandID.BTN_DAT_STP_VAR_TAB_BACK:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_DAT_STP_VAR_TAB_BACK);
					break;
				case ActionCommandID.BTN_DAT_STP_VAR_TAB_RESET:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_DAT_STP_VAR_TAB_RESET);
					break;
				case ActionCommandID.BTN_DAT_STP_VAR_TAB_NOTE:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_DAT_STP_VAR_TAB_NOTE);
					break;
				case ActionCommandID.BTN_DAT_STP_VAR_TAB_IMP:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_DAT_STP_VAR_TAB_IMP);
					break;
				case ActionCommandID.BTN_DAT_STP_VAR_TAB_NEXT:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_DAT_STP_VAR_TAB_NEXT);
					break;
				case ActionCommandID.BTN_DAT_STP_VAL_TAB_BACK:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_DAT_STP_VAL_TAB_BACK);
					break;
				case ActionCommandID.BTN_DAT_STP_VAL_TAB_RESET:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_DAT_STP_VAL_TAB_RESET);
					break;
				case ActionCommandID.BTN_DAT_STP_VAL_TAB_NOTE:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_DAT_STP_VAL_TAB_NOTE);
					break;
				case ActionCommandID.BTN_DAT_STP_VAL_TAB_NEXT:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_DAT_STP_VAL_TAB_NEXT);
					break;
				case ActionCommandID.BTN_DAT_STP_MAP_INS_BACK:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_DAT_STP_MAP_INS_BACK);
					break;
				case ActionCommandID.BTN_DAT_STP_MAP_INS_RESET:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_DAT_STP_MAP_INS_RESET);
					break;
				case ActionCommandID.BTN_DAT_STP_MAP_INS_NOTE:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_DAT_STP_MAP_INS_NOTE);
					break;
				case ActionCommandID.BTN_DAT_STP_MAP_INS_NEXT:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_DAT_STP_MAP_INS_NEXT);
					break;
				case ActionCommandID.BTN_DAT_STP_MAP_ATR_BACK:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_DAT_STP_MAP_ATR_BACK);
					break;
				case ActionCommandID.BTN_DAT_STP_MAP_ATR_RESET:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_DAT_STP_MAP_ATR_RESET);
					break;
				case ActionCommandID.BTN_DAT_STP_MAP_ATR_NOTE:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_DAT_STP_MAP_ATR_NOTE);
					break;
				case ActionCommandID.BTN_DAT_STP_MAP_CHA_CONFIRM:
					defaultTab = ((FormFrame)desktop.getFormFrame()).getHashMap().get("TabMapVariableChar");
					defaultTab.confirmButton.setEnabled(false);
					defaultTab.nextButton.setEnabled(true);
					
					menuBar.projectOpenProject(getAppFrame().getJMenuBar(), _model.getProject().isFinished(), _model.getProject().isEditedByUser(), (CStatsModel)addenda[0]);
					if (!((CStatsModel)addenda[0]).getProject().isFinished())
						toolBar.enableSave();
					if (((CStatsModel)addenda[0]).getProject().getProgress().isMapVariableChaTabDone())
						toolBar.enableSyntax();
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_DAT_STP_MAP_CHA_CONFIRM);
					break;
				case ActionCommandID.BTN_DAT_STP_MAP_ATR_NEXT:
					
					statusBar.setLeftSideMessage(ActionCommandText.BTN_DAT_STP_MAP_ATR_NEXT);
					break;
				default:
			}
			
			setChanged();	
			notifyObservers(parameter);
		}
		
	}

	/**
	 * @return
	 */
	public ApplicationFrame getAppFrame() {
		return appFrame;
	}

	/**
	 * @param appFrame
	 */
	public void setAppFrame(ApplicationFrame appFrame) {
		this.appFrame = appFrame;
	}
	
	/**
	 * @return
	 */
	public int saveProjectDialog() {
		ResourceBundle resourceBundle	= ResourceBundle.getBundle(BUNDLE_D, currentLocale);
		
		Object[] options = {resourceBundle.getString("yes"),resourceBundle.getString("no"),resourceBundle.getString("cancel_save")};
		
		return JOptionPane.showOptionDialog(getAppFrame(),
				resourceBundle.getString("save_quest"),
				"",
			    JOptionPane.YES_NO_CANCEL_OPTION,
			    JOptionPane.QUESTION_MESSAGE,
			    null,
			    options,
			    options[2]);
	}
	
	/**
	 * @return
	 */
	public int openProjectDialog() {
		ResourceBundle resourceBundle	= ResourceBundle.getBundle(BUNDLE_D, currentLocale);
		
		Object[] options = {resourceBundle.getString("yes"),resourceBundle.getString("no"),resourceBundle.getString("cancel_open")};
		return JOptionPane.showOptionDialog(getAppFrame(),
				resourceBundle.getString("open_quest"),			    
			    "",
			    JOptionPane.YES_NO_CANCEL_OPTION,
			    JOptionPane.QUESTION_MESSAGE,
			    null,
			    options,
			    options[2]);	
	}
	
	/**
	 * @return
	 */
	public int exitDialog() {
		ResourceBundle resourceBundle	= ResourceBundle.getBundle(BUNDLE_D, currentLocale);
		
		Object[] options = {resourceBundle.getString("yes"),resourceBundle.getString("no"),resourceBundle.getString("cancel_exit")};
		return JOptionPane.showOptionDialog(getAppFrame(),
				resourceBundle.getString("exit_quest"),
			    "",
			    JOptionPane.YES_NO_CANCEL_OPTION,
			    JOptionPane.QUESTION_MESSAGE,
			    null,
			    options,
			    options[2]);
	}
		
	/**
	 * @return
	 */
	public int clearBasketDialog() {
		ResourceBundle resourceBundle	= ResourceBundle.getBundle(BUNDLE_D, currentLocale);
		
		Object[] options = {resourceBundle.getString("yes"),resourceBundle.getString("no")};
		
		return JOptionPane.showOptionDialog(getAppFrame(),
			    "<html>"+
			    "<b>Warning: The emptied content will unrecoverable be lost.</b>"+
			    "<br>"+
			    "<div align=\"center\">Do you really want to empty the basket?</div>"+
			    "</html>",
			    "",
			    JOptionPane.YES_NO_OPTION,
			    JOptionPane.WARNING_MESSAGE,
			    null,
			    options,
			    options[1]);
		
	}

	/**
	 * @param projectList
	 * @return
	 */
	public int showOpenProjectDialog(ArrayList<Project> projectList) {
		ResourceBundle	resourceBundle;
		
		resourceBundle	= ResourceBundle.getBundle(BUNDLE_D, this.getLocale());
		
		if ((projectList != null) &&
				(projectList.size() > 0)) {
			OpenProjectDialog op = new OpenProjectDialog(projectList, this.getLocale(), menuFont, actionListener);
			
			Project project = op.getChosenProject();			
			if ((project != null) &&
					(project.getEntityID() > 0)){
				return project.getEntityID();
				
			} else
				JOptionPane.showMessageDialog(appFrame, resourceBundle.getString(NO_PROJECT));
				
		} else 
			JOptionPane.showMessageDialog(appFrame, resourceBundle.getString(NO_PROJECTS));
		
		return -1;
	}
	
	/**
	 * @param projectList
	 * @param userList
	 * @return
	 */
	public int showReleaseLockDialog(List<Integer> projectList, List<Integer> userList) {
		ResourceBundle	resourceBundle;
		
		resourceBundle	= ResourceBundle.getBundle(BUNDLE_D, this.getLocale());
		
		if ((projectList != null) &&
				(projectList.size() > 0)) {
			ReleaseLockDialog rl = new ReleaseLockDialog(projectList, userList, this.getLocale(), menuFont, ccats_con, actionListener);
			
			Integer pairing_index = rl.getChosenPairing();			
			if (pairing_index > 0)
				return pairing_index;
			else
				JOptionPane.showMessageDialog(appFrame, resourceBundle.getString(NO_PAIRING));
				
		} else 
			JOptionPane.showMessageDialog(appFrame, resourceBundle.getString(NO_PAIRING));
		
		return -1;
	}
	
	/**
	 * former showUnpublishProjectDialog
	 * 
	 * @param projectList
	 * @return
	 */
	public int showUnfinishProjectDialog(List<Project> projectList) {
		ResourceBundle	resourceBundle;
		
		resourceBundle	= ResourceBundle.getBundle(BUNDLE_D, this.getLocale());
		
		if ((projectList != null) &&
				(projectList.size() > 0)) {
			UnfinishProjectDialog up = new UnfinishProjectDialog(projectList, this.getLocale(), menuFont, actionListener);
			
			Project project = up.getChosenProject();			
			if ((project != null) &&
					(project.getEntityID() > 0)){
				return project.getEntityID();
				
			} else
				JOptionPane.showMessageDialog(appFrame, resourceBundle.getString(NO_PROJECT));
				
		} else 
			JOptionPane.showMessageDialog(appFrame, resourceBundle.getString(NO_PROJECTS));
		
		return -1;
	}
	
	/**
	 * @return
	 */
	public Locale getLocale() {
		return currentLocale;
	}

	/**
	 * @return
	 */
	public Font getFont() {
		return menuFont;
	}

	/**
	 * @param users
	 * @return
	 */
	public int showOpenUserDialog(List<User> users) {
		ResourceBundle	resourceBundle;
		
		resourceBundle	= ResourceBundle.getBundle(BUNDLE_D, this.getLocale());
		
		if ((users != null) &&
				(users.size() > 0)) {
			OpenUserDialog ou = new OpenUserDialog(users, this.getLocale(), menuFont, actionListener);
			
			User user = ou.getChosenUser();		
			if ((user != null) &&
					(user.getEntityID() > 0)){
				return user.getEntityID();				
			} 
				
		} else 
			JOptionPane.showMessageDialog(appFrame, resourceBundle.getString(NO_USERS));
		
		return -1;		
	}
	
	/**
	 * @param persons
	 * @return
	 */
	public int showOpenPersonDialog(List<Person> persons) {
		ResourceBundle	resourceBundle;
		
		resourceBundle	= ResourceBundle.getBundle(BUNDLE_D, this.getLocale());
		
		if ((persons != null) &&
				(persons.size() > 0)) {
			OpenPersonDialog op = new OpenPersonDialog(persons, this.getLocale(), menuFont, actionListener);
			
			Person prsn = op.getChosenPerson();		
			if ((prsn != null) &&
					(prsn.getEntityID() > 0)){
				return prsn.getEntityID();				
			} 
			
		} else 
			JOptionPane.showMessageDialog(appFrame, resourceBundle.getString(NO_PERSONS));
		
		return -1;		
	}
	
	/**
	 * @param parts
	 * @return
	 */
	public int showOpenParticipantDialog(List<Participant> parts) {
		ResourceBundle	resourceBundle;
		
		resourceBundle	= ResourceBundle.getBundle(BUNDLE_D, this.getLocale());
		
		if ((parts != null) &&
				(parts.size() > 0)) {
			OpenParticipantDialog op = new OpenParticipantDialog(parts, this.getLocale(), menuFont, actionListener);
			
			Participant part = op.getChosenParticipant();		
			if ((part != null) &&
					(part.getEntityID() > 0)){
				return part.getEntityID();				
			} 
				
		} else 
			JOptionPane.showMessageDialog(appFrame, resourceBundle.getString(NO_PARTICIPANTS));
		
		return -1;		
	}
	
	/**
	 * @param measures
	 * @return
	 */
	public int showOpenMeasureDialog(List<Measurement> measures) {
		ResourceBundle	resourceBundle;
		
		resourceBundle	= ResourceBundle.getBundle(BUNDLE_D, this.getLocale());
		
		if ((measures != null) &&
				(measures.size() > 0)) {
			OpenMeasureDialog om = new OpenMeasureDialog(measures, this.getLocale(), menuFont, actionListener);
			
			Measurement mea = om.getChosenMeasure();			
			if ((mea != null) &&
					(mea.getEntityID() > 0)){
				return mea.getEntityID();				
			} 
				
		} else 
			JOptionPane.showMessageDialog(appFrame, resourceBundle.getString(NO_MEASURES));
		
		return -1;
	}
	
	/**
	 * @param variables
	 * @return
	 */
	public int showOpenVariableDialog(List<Variable> variables) {
		ResourceBundle	resourceBundle;
		
		resourceBundle	= ResourceBundle.getBundle(BUNDLE_D, this.getLocale());
		
		if ((variables != null) &&
				(variables.size() > 0)) {
			OpenVariableDialog ov = new OpenVariableDialog(variables, this.getLocale(), menuFont, actionListener);
			
			Variable var = ov.getChosenVariable();			
			if ((var != null) &&
					(var.getEntityID() > 0)){
				return var.getEntityID();				
			} 
				
		} else 
			JOptionPane.showMessageDialog(appFrame, resourceBundle.getString(NO_VARIABLES));
		
		return -1;
	}
	
	public void showWaitingCursor() {
    	Cursor		waitCursor	= new Cursor(Cursor.WAIT_CURSOR); 
    	Component	glassPane	= getAppFrame().getRootPane().getGlassPane();
    	
    	glassPane.setCursor(waitCursor);
    	glassPane.setVisible(true);
	}
	
	public void showDefaultCursor() {
    	Cursor		defaultCursor	= new Cursor(Cursor.DEFAULT_CURSOR); 
       	Component	glassPane		= getAppFrame().getRootPane().getGlassPane();
       	
    	glassPane.setCursor(defaultCursor);
    	glassPane.setVisible(false);
	}
	
	public int showRemoveVariableDialog(List<Variable> variables) {
		ResourceBundle	resourceBundle;
		
		resourceBundle	= ResourceBundle.getBundle(BUNDLE_D, this.getLocale());
		
		if ((variables != null) &&
				(variables.size() > 0)) {
			RemoveVariableDialog ov = new RemoveVariableDialog(variables, this.getLocale(), menuFont, actionListener);
			
			Variable var = ov.getChosenVariable();			
			if ((var != null) &&
					(var.getEntityID() > 0)){
				return var.getEntityID();				
			} 
				
		} else 
			JOptionPane.showMessageDialog(appFrame, resourceBundle.getString(NO_VARIABLES));
		
		return -1;
	}

	public int showRemoveMeasureDialog(List<Measurement> measures) {
		ResourceBundle	resourceBundle;
		
		resourceBundle	= ResourceBundle.getBundle(BUNDLE_D, this.getLocale());
		
		if ((measures != null) &&
				(measures.size() > 0)) {
			RemoveMeasureDialog ov = new RemoveMeasureDialog(measures, this.getLocale(), menuFont, actionListener);
			
			Measurement mea = ov.getChosenMeasurement();			
			if ((mea != null) &&
					(mea.getEntityID() > 0)){
				return mea.getEntityID();				
			} 
				
		} else 
			JOptionPane.showMessageDialog(appFrame, resourceBundle.getString(NO_MEASURES));
		
		return -1;
	}
}

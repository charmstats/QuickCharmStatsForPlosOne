package org.gesis.charmstats.view;

import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

import org.gesis.charmstats.ActionCommandText;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class ToolBar extends JToolBar {
	
	public static final String BUNDLE		= "org.gesis.charmstats.resources.DesktopBundle";
	
	public static final String TB_NEW		= "tb_new";
	public static final String TB_SAVE		= "tb_save";
	public static final String TB_OPEN		= "tb_open";
	public static final String TB_SYNTAX	= "tb_syntax";
	public static final String TB_FORM		= "tb_form";
	public static final String TB_GRAPH		= "tb_graph";
	public static final String TB_REPORT	= "tb_report";
	
	/*
	 *	Fields
	 */
	Locale 		  	currentLocale;
	ResourceBundle	resourceBundle;
	
	private JButton	newButton;
	private JButton saveButton;
	private JButton openButton;
	private JButton	syntaxButton;
	private JButton	formButton;
	private JButton graphButton;
	private JButton	reportButton;
	private JButton undoButton;
	private JButton redoButton;
	private JButton cutButton;
	private JButton copyButton;
	private JButton pasteButton;
	private JButton removeButton;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 *	Constructor
	 */
	/**
	 * @param al
	 * @param locale
	 */
	public ToolBar(ActionListener al, Locale locale) {
		super();
		
		currentLocale = locale;
		resourceBundle = ResourceBundle.getBundle(BUNDLE, currentLocale);
				        
		setFloatable(false);
		
		newButton		= newButton(al);
		saveButton		= saveButton(al);
		openButton		= openButton(al);
		syntaxButton	= syntaxButton(al);
		formButton		= formButton(al);
		graphButton		= graphButton(al);
		reportButton	= reportButton(al);
		undoButton		= undoButton(al);
		redoButton		= redoButton(al);
		cutButton		= cutButton(al);
		copyButton		= copyButton(al);
		pasteButton		= pasteButton(al);
		removeButton	= removeButton(al);
		
		add(newButton);
		add(saveButton);
		add(openButton);
		addSeparator();
		add(syntaxButton);
		addSeparator();
		add(formButton);
		add(graphButton);
		add(reportButton);
	}
	
	/*
	 *	Methods
	 */
    /**
     * @param al
     * @return
     */
    private JButton newButton(ActionListener al) {    	
        JButton button = new JButton(createImageIcon(CStatsGUI.NEW_ICON,""));
        
        button.setActionCommand(ActionCommandText.CMD_PRJ_NEW_PROJECT);
        button.addActionListener(al);
        
        button.setToolTipText(resourceBundle.getString(TB_NEW));
        
    	return button;
    }
    
    /**
     * @param al
     * @return
     */
    private JButton saveButton(ActionListener al) {    	
        JButton button = new JButton(createImageIcon(CStatsGUI.SAVE_ICON,""));
        
        button.setActionCommand(ActionCommandText.CMD_PRJ_SAVE);
        button.addActionListener(al);
        
        button.setToolTipText(resourceBundle.getString(TB_SAVE));
        
    	return button;
    }
    
    /**
     * @param al
     * @return
     */
    private JButton openButton(ActionListener al) {    	
        JButton button = new JButton(createImageIcon(CStatsGUI.OPEN_ICON,""));
        
        button.setActionCommand(ActionCommandText.CMD_PRJ_OPEN_PROJECT);
        button.addActionListener(al);
        
        button.setToolTipText(resourceBundle.getString(TB_OPEN));
        
    	return button;
    }
    
    /**
     * @param al
     * @return
     */
    private JButton syntaxButton(ActionListener al) {    	
        JButton button = new JButton(createImageIcon(CStatsGUI.SYNTAX_ICON,""));
        
        button.setActionCommand(ActionCommandText.CMD_EXTRA_EXPORT_SYNTAX);
        button.addActionListener(al);
        
        button.setToolTipText(resourceBundle.getString(TB_SYNTAX));
        
    	return button;
    }
    
    /**
     * @param al
     * @return
     */
    private JButton formButton(ActionListener al) {    	
        JButton button = new JButton(createImageIcon(CStatsGUI.FORM_ICON,""));
        
        button.setActionCommand(ActionCommandText.BTN_TB_SHOW_FORM);
        button.addActionListener(al);
        
        button.setToolTipText(resourceBundle.getString(TB_FORM));
        
    	return button;
    }
    
    /**
     * @param al
     * @return
     */
    private JButton graphButton(ActionListener al) {    	
        JButton button = new JButton(createImageIcon(CStatsGUI.GRAPH_ICON,""));
        
        button.setActionCommand(ActionCommandText.BTN_TB_SHOW_GRAPH);
        button.addActionListener(al);
        
        button.setToolTipText(resourceBundle.getString(TB_GRAPH));
        
    	return button;
    }
    
    /**
     * @param al
     * @return
     */
    private JButton reportButton(ActionListener al) {    	
        JButton button = new JButton(createImageIcon(CStatsGUI.REPORT_ICON,""));
        
        button.setActionCommand(ActionCommandText.BTN_TB_SHOW_REPORT);
        button.addActionListener(al);
        
        button.setToolTipText(resourceBundle.getString(TB_REPORT));
        
    	return button;
    }
    
    /**
     * @param al
     * @return
     */
    private JButton undoButton(ActionListener al) {    	
        JButton button = new JButton(createImageIcon(CStatsGUI.UNDO_ICON,""));
        
        button.setActionCommand(ActionCommandText.CMD_EDIT_UNDO);
        button.addActionListener(al);
        
    	return button;
    }
    
    /**
     * @param al
     * @return
     */
    private JButton redoButton(ActionListener al) {    	
        JButton button = new JButton(createImageIcon(CStatsGUI.REDO_ICON,""));
        
        button.setActionCommand(ActionCommandText.CMD_EDIT_REDO);
        button.addActionListener(al);
        
    	return button;
    }
    
    /**
     * @param al
     * @return
     */
    private JButton cutButton(ActionListener al) {    	
        JButton button = new JButton(createImageIcon(CStatsGUI.CUT_ICON,""));
        
        button.setActionCommand(ActionCommandText.CMD_EDIT_CUT);
        button.addActionListener(al);
        
    	return button;
    }
    
    /**
     * @param al
     * @return
     */
    private JButton copyButton(ActionListener al) {    	
        JButton button = new JButton(createImageIcon(CStatsGUI.COPY_ICON,""));
        
        button.setActionCommand(ActionCommandText.CMD_EDIT_COPY);
        button.addActionListener(al);
        
    	return button;
    }
    
    /**
     * @param al
     * @return
     */
    private JButton pasteButton(ActionListener al) {    	
        JButton button = new JButton(createImageIcon(CStatsGUI.PASTE_ICON,""));
        
        button.setActionCommand(ActionCommandText.CMD_EDIT_PASTE);
        button.addActionListener(al);
        
    	return button;
    }
    
    /**
     * @param al
     * @return
     */
    private JButton removeButton(ActionListener al) {    	
        JButton button = new JButton(createImageIcon(CStatsGUI.REMOVE_ICON,""));
        
        button.setActionCommand(ActionCommandText.CMD_EDIT_REMOVE);
        button.addActionListener(al);
        
    	return button;
    }
	
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

	/**
	 * @param locale
	 */
	public void changeLanguage(Locale locale) {
		ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, locale);
		
		newButton.setToolTipText(bundle.getString(TB_NEW));
		saveButton.setToolTipText(bundle.getString(TB_SAVE));
		openButton.setToolTipText(bundle.getString(TB_OPEN));
		syntaxButton.setToolTipText(bundle.getString(TB_SYNTAX));
		formButton.setToolTipText(bundle.getString(TB_FORM));
		graphButton.setToolTipText(bundle.getString(TB_GRAPH));
		reportButton.setToolTipText(bundle.getString(TB_REPORT));
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#setEnabled(boolean)
	 */
	public void setEnabled(boolean b) {
		newButton.setEnabled(b);
		saveButton.setEnabled(b);
		openButton.setEnabled(b);
		syntaxButton.setEnabled(b);
		formButton.setEnabled(b);
		graphButton.setEnabled(b);
		reportButton.setEnabled(b);
		undoButton.setEnabled(b);
		redoButton.setEnabled(b);
		cutButton.setEnabled(b);
		copyButton.setEnabled(b);
		pasteButton.setEnabled(b);
		removeButton.setEnabled(b);
		
		disableSave();
		disableSyntax();
		disableUnsupported();
	}
	
	/**
	 * 
	 */
	public void enableSave() {
		saveButton.setEnabled(true);
	}
	
	/**
	 * 
	 */
	public void disableSave() {
		saveButton.setEnabled(false);
	}
	
	/**
	 * 
	 */
	public void enableSyntax() {
		syntaxButton.setEnabled(true);
	}
	
	/**
	 * 
	 */
	public void disableSyntax() {
		syntaxButton.setEnabled(false);
	}
	
	/**
	 * 
	 */
	public void disableUnsupported() {
		undoButton.setEnabled(false);
		redoButton.setEnabled(false);
		cutButton.setEnabled(false);
		copyButton.setEnabled(false);
		pasteButton.setEnabled(false);
		removeButton.setEnabled(false);		
	}
	
}

package org.gesis.charmstats.view;

import java.awt.Font;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import org.gesis.charmstats.model.CStatsModel;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class ApplicationFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* RESOURCE */
	private static final String CONTROL_BOX_ICON	= "org/gesis/charmstats/resources/quick-charmstats-16x16.png";	
	private static final String BUNDLE				= "org.gesis.charmstats.resources.FrameBundle";
	
	private static final String TITLE				= "title";
	private static final String AUTHORITY			= "authority";
	
	/*
	 *	Fields
	 */
	private CStatsModel	_model;	
	private Locale		currentLocale;
	
	/*
	 *	Constructor
	 */
	/**
	 * @param model
	 * @param locale
	 */
	public ApplicationFrame(CStatsModel model, Locale locale) {
		super();
		
		_model			= model;
		currentLocale	= locale;
		
		buildTitleBar();
	}
	
	
	/*
	 *	Methods
	 */
	/**
	 * 
	 */
	private void buildTitleBar() {
		ImageIcon controlBoxIcon = CStatsGUI.createImageIcon(CONTROL_BOX_ICON, "Nekomimi");
        if (controlBoxIcon != null)
        	setIconImage(controlBoxIcon.getImage());
        changeLanguage(currentLocale);
    	setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);		
	}
	
	/**
	 * @param locale
	 */
	protected void changeLanguage(Locale locale) {
		ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, locale);
		
		setTitle(bundle.getString(TITLE) + " - " + bundle.getString(AUTHORITY) + ": " + _model.getUser().getName() + 
				((_model.getProject().getName().length() > 0) ? " - "+ _model.getProject().getName() : "") 
				+ (((_model.getProject().getDOI() != null) &&
					(_model.getProject().getDOI().length() > 0)) ? " - DOI: "+ _model.getProject().getDOI() : "")
			);
	}
	
	/* doesn't work yet... TODO */
	/**
	 * @param f
	 */
	protected void changeFont(Font f) {
		this.setFont(f);
	}
	
}

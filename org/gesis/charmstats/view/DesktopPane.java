package org.gesis.charmstats.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Locale;

import javax.swing.JDesktopPane;
import javax.swing.event.ChangeListener;
import javax.swing.undo.UndoManager;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class DesktopPane extends JDesktopPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Dimension 	prjDim 	= new Dimension(200,325);	
	private static final Dimension 	frmDim 	= new Dimension(895,573); 
	private static final Dimension 	bsktDim	= new Dimension(200,300);
	private static final Dimension 	grphDim	= new Dimension(895,26); 
	private static final Dimension 	rprtDim	= new Dimension(895,26); 
	private static final Point		prjPos	= new Point(0,0);	
	private static final Point 		frmPos 	= new Point(200,0);
	private static final Point 		bsktPos	= new Point(0,325);
	private static final Point 		grphPos	= new Point(200,573); 
	private static final Point 		rprtPos	= new Point(200,599); 
	
	/*
	 *	Fields
	 */
	InternalFrame projectFrame;
	private InternalFrame formFrame;
	InternalFrame basketFrame;
	InternalFrame graphFrame;
	InternalFrame reportFrame;
	
	Connection _connection;

	/*
	 *	Constructor
	 */
	/**
	 * @param al
	 * @param cl
	 * @param locale
	 * @param gui
	 */
	public DesktopPane(ActionListener al, ChangeListener cl, Locale locale, CStatsGUI gui) {
		super();
		
		_connection = gui.ccats_con;
		
		projectFrame	= projectFrame(locale, al, gui.getModel());
		setFormFrame(formFrame(locale, al, cl, gui.getModel()));
		basketFrame		= basketFrame(locale, al, gui.getModel());
		graphFrame		= graphFrame(locale, al);
		reportFrame		= reportFrame(locale, al, gui.getModel());
		
	   	add(projectFrame);
	   	add(getFormFrame());
	   	add(basketFrame);
	   	add(graphFrame);
	   	add(reportFrame);
	   	
	   	gui.addObserver(projectFrame);
	   	gui.addObserver(getFormFrame());
	   	gui.addObserver(((FormFrame)getFormFrame()).getCompareValuesTab());
	   	gui.addObserver(basketFrame);
	   	gui.addObserver(graphFrame);
	   	gui.addObserver(reportFrame);
	}
	
	/*
	 *	Methods
	 */
	/**
	 * @param locale
	 * @param al
	 * @param addenda
	 * @return
	 */
	private InternalFrame projectFrame(Locale locale, ActionListener al, Object addenda) {    	
    	InternalFrame frame = new ProjectFrame(
    			prjDim, prjPos, true, true, true, true, false, locale, al, addenda);
    	    	
        return frame;
	}
	
	/**
	 * @param locale
	 * @param al
	 * @param cl
	 * @param addenda
	 * @return
	 */
	private InternalFrame formFrame(Locale locale, ActionListener al, ChangeListener cl, Object addenda) {
		InternalFrame frame = new FormFrame(
				frmDim, frmPos, true, true, true, true, false, locale, al, cl, addenda);
		
		return frame;
	}
    
	/**
	 * @param locale
	 * @param al
	 * @param addenda
	 * @return
	 */
	private InternalFrame basketFrame(Locale locale, ActionListener al, Object addenda) {
		InternalFrame frame = new BasketFrame(
				bsktDim,  bsktPos, true, true, true, true, false, locale, al, addenda);
	    
    	return frame;
	}
	
	/**
	 * @param locale
	 * @param al
	 * @return
	 */
	private InternalFrame graphFrame(Locale locale, ActionListener al) {
	    InternalFrame frame = new GraphFrame(
	    		grphDim, grphPos, true, true, true, true, false, locale, al);
	    
	    return frame;
	}
    
	/**
	 * @param locale
	 * @param al
	 * @param addenda
	 * @return
	 */
	private InternalFrame reportFrame(Locale locale, ActionListener al, Object addenda) {
	    InternalFrame frame = new ReportFrame(
	    		rprtDim, rprtPos, true, true, true, true, false, locale, al, _connection, addenda);
	    
	    return frame;
	}
	   
	/**
	 * @param locale
	 */
	protected void changeLanguage(Locale locale) {
		((ProjectFrame)projectFrame).changeLanguage(locale);
		((FormFrame)getFormFrame()).changeLanguage(locale);
		((BasketFrame)basketFrame).changeLanguage(locale);
		((GraphFrame)graphFrame).changeLanguage(locale);
		((ReportFrame)reportFrame).changeLanguage(locale);		
	}
	
	/**
	 * @param f
	 */
	protected void changeFont(Font f) {
		((ProjectFrame)projectFrame).changeFont(f);
		((FormFrame)getFormFrame()).changeFont(f);
		((BasketFrame)basketFrame).changeFont(f);
		((GraphFrame)graphFrame).changeFont(f);
		((ReportFrame)reportFrame).changeFont(f);
	}
	
	/**
	 * @return
	 */
	public HashMap<String, Tab> getFormulars() {
		return ((FormFrame)getFormFrame()).getHashMap();
	}

	/**
	 * @return
	 */
	public InternalFrame getFormFrame() {
		return formFrame;
	}

	/**
	 * @param formFrame
	 */
	public void setFormFrame(InternalFrame formFrame) {
		this.formFrame = formFrame;
	}
	
	/**
	 * @return
	 */
	public InternalFrame getProjectFrame() {
		return projectFrame;
	}
	
}

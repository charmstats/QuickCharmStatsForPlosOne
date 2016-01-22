package org.gesis.charmstats.view;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import javax.swing.JInternalFrame;

import org.gesis.charmstats.IdentifiedParameter;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class InternalFrame extends JInternalFrame implements Observer {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/*
	 *	Fields
	 */
	Locale 		  		currentLocale;
	ResourceBundle		resourceBundle;
	
	/*
	 *	Constructor
	 */
	/**
	 * @param dimension
	 * @param location
	 * @param visible
	 * @param resizable
	 * @param maximizable
	 * @param iconifiable
	 * @param closable
	 */
	public InternalFrame(
			Dimension dimension,
			Point location,
			boolean visible, 
			boolean resizable,
			boolean maximizable,
			boolean iconifiable,
			boolean closable) {
		super();
		
	    setSize(dimension);
	    setLocation(location);
	    setVisible(visible);
	    setResizable(resizable);
	    setMaximizable(maximizable);
	    setIconifiable(iconifiable);
	    setClosable(closable);
	}

	/*
	 *	Methods
	 */
	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		IdentifiedParameter parameter = null;
		
		if (arg1 instanceof IdentifiedParameter) {
			parameter = (IdentifiedParameter)arg1;
		}
		
		if (parameter != null) {
			@SuppressWarnings("unused")
			Object[]	addenda		= parameter.getParameters();

			/* TODO */
		}
	}
}

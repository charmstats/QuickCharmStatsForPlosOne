package org.gesis.charmstats.view;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class GScrollPane extends JScrollPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/*
	 *	Constants
	 */
	private static final String EMPTY_STRING = "";
	
	/*
	 *	Constructor(s)
	 */
	/**
	 * 
	 */
	public GScrollPane() {
		super();
		
		getVerticalScrollBar().setUnitIncrement(16);
	}
	
	/**
	 * @param l
	 */
	public GScrollPane(JList l) {
		super(l);
		
		getVerticalScrollBar().setUnitIncrement(16);
	}
	
	/*
	 *	Methods
	 */
	/* (non-Javadoc)
	 * @see java.awt.Component#toString()
	 */
	public String toString() {
		String resultString = EMPTY_STRING;
		
		if ((this.getComponent(0) != null) &&
				(this.getComponent(0) instanceof JViewport)) {
			Component comp = ((JViewport)this.getComponent(0)).getComponent(0);
			if (comp instanceof JList) {
				resultString = comp.toString();
			}
		}
		return resultString;
	}
}

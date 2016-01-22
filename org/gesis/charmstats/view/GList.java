package org.gesis.charmstats.view;

import java.util.Vector;

import javax.swing.JList;
import javax.swing.ListModel;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class GList  extends JList {
	
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
	public GList() {
		super();
	}
	
	/**
	 * @param v
	 */
	@SuppressWarnings("rawtypes")
	public GList(Vector v) {
		super(v);
	}
	
	/**
	 * @param lm
	 */
	public GList(ListModel lm) {
		super(lm);
	}
	
	/*
	 *	Methods
	 */
	/* (non-Javadoc)
	 * @see java.awt.Component#toString()
	 */
	public String toString() {
		String resultString = EMPTY_STRING;
		
		if (this.getSelectedValue() != null) {
			resultString = this.getSelectedValue().toString();
		}
		
		return resultString;
	}

}

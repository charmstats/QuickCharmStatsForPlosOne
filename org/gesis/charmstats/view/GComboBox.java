package org.gesis.charmstats.view;

import java.util.Vector;

import javax.swing.JComboBox;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class GComboBox extends JComboBox {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 *	Constants
	 */
	private static final String EMPTY_STRING = "";
	
	/*
	 *	Fields
	 */
	private JComboBox	nextStep = null;
	private Object		thisEnum = null;
	
	/*
	 *	Constructor(s)
	 */
	/**
	 * 
	 */
	public GComboBox() {
		super();
	}
	
	/**
	 * @param v
	 */
	@SuppressWarnings("rawtypes")
	public GComboBox(Vector v) {
		super(v);
	}
	
	/*
	 *	Methods
	 */
	/* (non-Javadoc)
	 * @see java.awt.Component#toString()
	 */
	public String toString() {
		String resultString = EMPTY_STRING;
		
		if (this.getSelectedItem() != null)
			resultString = this.getSelectedItem().toString();
		
		return resultString;
	}
	
	/**
	 * @return
	 */
	public JComboBox getNextStep() {
		return nextStep;
	}
	
	/**
	 * @param nextStep
	 */
	public void setNextStep(JComboBox nextStep) {
		this.nextStep = nextStep;
	}

	/**
	 * @param thisEnum
	 */
	public void setThisEnum(Object thisEnum) {
		this.thisEnum = thisEnum;
	}

	/**
	 * @return
	 */
	public Object getThisEnum() {
		return thisEnum;
	}

}

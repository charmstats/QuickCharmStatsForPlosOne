package org.gesis.charmstats.view;

import java.io.File;

/**
 * 
 * @author	Martin Friedrichs
 * @since	0.9.7
 *
 */
public abstract class SaveImageFileFilter extends
	javax.swing.filechooser.FileFilter {

	/*
	 *	Fields
	 */
	public static final int noFilterID		= -1;
	public static final int jpgFilterID		=  1;
	public static final int gifFilterID		=  2;
	public static final int pngFilterID		=  3;
	public static final int bmpFilterID		=  4;
	public static final int wbmpFilterID	=  5;
	
	/*
	 *	Methods
	 */
	/* (non-Javadoc)
	 * @see javax.swing.filechooser.FileFilter#accept(java.io.File)
	 */
	@Override
	public boolean accept(File f) {		
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.filechooser.FileFilter#getDescription()
	 */
	@Override
	public String getDescription() {
		return null;
	}

	/**
	 * @return
	 */
	abstract public int getFilterID();
	
}

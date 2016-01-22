package org.gesis.charmstats.view;

import java.io.File;

/**
 * 
 * @author	Martin Friedrichs
 * @since	0.6.5
 *
 */
public abstract class OpenDialogFileFilter extends
		javax.swing.filechooser.FileFilter {

	/*
	 * Fields
	 */
	public static final int noFilterID		= -1;
	public static final int ddiFilterID		=  1;
	public static final int spssFilterID	=  2;
	public static final int stataFilterID	=  4;
	public static final int sasFilterID		=  8;
	public static final int mplusFilterID	= 16;

	/*
	 * Methods
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

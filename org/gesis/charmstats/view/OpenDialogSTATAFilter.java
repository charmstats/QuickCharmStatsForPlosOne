package org.gesis.charmstats.view;

import java.io.File;

/**
 * 
 * @author	Martin Friedrichs
 * @since	0.6.5
 *
 */
public class OpenDialogSTATAFilter extends OpenDialogFileFilter {
	
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.OpenDialogFileFilter#accept(java.io.File)
	 */
	public boolean accept(File f) {
		return f.getName().toLowerCase().endsWith(".sts") || f.isDirectory();
	}

	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.OpenDialogFileFilter#getDescription()
	 */
	public String getDescription() {
		return "STATA";
	}

	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.OpenDialogFileFilter#getFilterID()
	 */
	@Override
	public int getFilterID() {
		return stataFilterID;
	}

}

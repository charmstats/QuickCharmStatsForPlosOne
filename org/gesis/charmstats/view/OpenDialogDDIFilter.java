package org.gesis.charmstats.view;

import java.io.File;

/**
 * 
 * @author	Martin Friedrichs
 * @since	0.6.5
 *
 */
public class OpenDialogDDIFilter extends OpenDialogFileFilter {

	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.OpenDialogFileFilter#accept(java.io.File)
	 */
	public boolean accept(File f) {
		return f.getName().toLowerCase().endsWith(".ddi2")
				|| f.getName().toLowerCase().endsWith(".ddi3")
				|| f.isDirectory();
	}

	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.OpenDialogFileFilter#getDescription()
	 */
	public String getDescription() {
		return "DDI 3.1";
	}

	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.OpenDialogFileFilter#getFilterID()
	 */
	@Override
	public int getFilterID() {
		return ddiFilterID;
	}
}

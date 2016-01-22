package org.gesis.charmstats.view.Graph;

/**
 * 
 * @author	Martin Friedrichs
 * @since	0.8.1
 *
 */
public interface GraphUpdateListener {
	/**
	 * @param event
	 */
	public void graphUpdateReceived (GraphUpdateEvent event);
}

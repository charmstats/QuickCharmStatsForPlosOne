package org.gesis.charmstats.view.Graph;

import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.VertexRenderer;
import org.jgraph.graph.VertexView;

/**
 * 
 * @author	Martin Friedrichs
 * @since	0.8.1
 *
 */
public class RoundedView extends VertexView {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static RoundedRenderer renderer = new RoundedRenderer();
	
	/**
	 * @param cell
	 */
	public RoundedView (Object cell) {
		super (cell);
	}
	
	/* (non-Javadoc)
	 * @see org.jgraph.graph.VertexView#getRenderer()
	 */
	public CellViewRenderer getRenderer() {
		return renderer;
	}
	
	// Override getPerimeterPoint
	
	static class RoundedRenderer extends VertexRenderer {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		// Override paint
	}
}


package org.gesis.charmstats.view.Graph;

import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.VertexRenderer;
import org.jgraph.graph.VertexView;

/**
 * 
 * @author	Martin Friedrichs
 * @since	0.8.1
 *
 */
public class VariableView extends VertexView{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static VariableRenderer renderer = new VariableRenderer();
	
	/**
	 * @param cell
	 */
	public VariableView (Object cell) {
		super (cell);
	}
	
	/* (non-Javadoc)
	 * @see org.jgraph.graph.VertexView#getRenderer()
	 */
	public CellViewRenderer getRenderer() {
		return renderer;
	}
	
	// Override getPerimeterPoint
	
	static class VariableRenderer extends VertexRenderer {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		/**
		 * Paint the renderer. Overrides superclass paint to add specific painting.
		 */
		/* (non-Javadoc)
		 * @see org.jgraph.graph.VertexRenderer#paint(java.awt.Graphics)
		 */
		public void paint(Graphics g) {
			try {
				if (gradientColor != null && !preview && isOpaque()) {
					setBorder(null);
					setOpaque(false);
					Graphics2D g2d = (Graphics2D) g;
					g2d.setPaint(new GradientPaint(0, 0, getBackground(),
						getWidth(), getWidth()*6/8, gradientColor, true));
					g2d.fillRect(0, 0, getWidth(), getWidth()*6/8);
				}
				super.paint(g);
				paintSelectionBorder(g);
			} catch (IllegalArgumentException e) {
				
			}
		}
		
		/* (non-Javadoc)
		 * @see org.jgraph.graph.VertexRenderer#paintSelectionBorder(java.awt.Graphics)
		 */
		protected void paintSelectionBorder(Graphics g) {
			// DoNothing Yet
		}

	}
}


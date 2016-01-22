package org.gesis.charmstats.view.Graph;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

import javax.swing.JScrollPane;
import javax.swing.RepaintManager;

import org.jgraph.JGraph;

/**
 * 
 * @author	Martin Friedrichs
 * @since	0.8.1, known former as "JGraphScrollPane"
 *
 */
public class GJGraphScrollPane extends JScrollPane implements Printable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JGraph graph;
	
	/* (non-Javadoc)
	 * @see java.awt.print.Printable#print(java.awt.Graphics, java.awt.print.PageFormat, int)
	 */
	@Override
	public int print(Graphics g, PageFormat printFormat, int page)
			throws PrinterException {

		double pageScale = 1.5;
		
		Dimension pSize = getGraph().getPreferredSize(); 
		int w = (int) (printFormat.getWidth() * pageScale);
		int h = (int) (printFormat.getHeight() * pageScale);
		int cols = (int) Math.max(Math.ceil((double) (pSize.width-5) / (double) w), 1);
		int rows = (int) Math.max(Math.ceil((double) (pSize.height-5) / (double) h), 1);
		if (page < cols * rows) {
			// Configures graph for printing
			RepaintManager currentManager =
				RepaintManager.currentManager(this);
			currentManager.setDoubleBufferingEnabled(false);
			double oldScale = getGraph().getScale();
			getGraph().setScale(1 / pageScale);
			int dx = (int) ((page%cols) * printFormat.getWidth());
			int dy = (int) ((page%rows) * printFormat.getHeight());
			g.translate(-dx, -dy);
			g.setClip(dx, dy, (int) (dx + printFormat.getWidth()),
			(int) (dy + printFormat.getHeight()));
	
			// Prints the graph on the graphics
			getGraph().paint(g);
			// Restores graph
			g.translate(dx, dy);
			graph.setScale(oldScale);
			
			currentManager.setDoubleBufferingEnabled(true);
			return PAGE_EXISTS;
		} else {
			return NO_SUCH_PAGE;
		}
	}

	/**
	 * @param graph
	 */
	public void setGraph(JGraph graph) {
		this.graph = graph;
	}
	
	/**
	 * @return
	 */
	public JGraph getGraph() {
		return this.graph;
	}

}

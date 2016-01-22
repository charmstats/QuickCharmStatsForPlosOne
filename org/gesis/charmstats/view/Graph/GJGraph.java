package org.gesis.charmstats.view.Graph;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.jgraph.JGraph;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.GraphModel;

/**
 * 
 * @author	Martin Friedrichs
 * @since	0.8.1
 *
 */
public class GJGraph extends JGraph implements Observer {

	/*
	 *	Eventhandling
	 */
	private GraphUpdate					_graphUpdate	= GraphUpdate.DONE_NOTHING;
	private List<GraphUpdateListener> 	_listeners		= new ArrayList<GraphUpdateListener>();
	
	/**
	 * @param l
	 */
	public synchronized void addGraphUpdateListener(GraphUpdateListener l) {
		_listeners.add(l);
	}
	/**
	 * @param l
	 */
	public synchronized void removeGraphUpdateListener(GraphUpdateListener l) {
		_listeners.remove(l);
	}
	/**
	 * @param graphUpdate
	 */
	protected synchronized void _setGraphUpdate(GraphUpdate graphUpdate) {
		_graphUpdate = graphUpdate;
	}
	/**
	 * 
	 */
	protected synchronized void _fireGraphUpdateEvent() {
		GraphUpdateEvent				graphUpdate	= new GraphUpdateEvent(this, _graphUpdate);
		Iterator<GraphUpdateListener>	iterator	= _listeners.iterator();
		while (iterator.hasNext()) {
			iterator.next().graphUpdateReceived(graphUpdate);
		}
	}
	
	/* Expanding and collapsing category etc. cells */
	private ArrayList<DefaultGraphCell>	characteristicCells	= new ArrayList<DefaultGraphCell>();
	private DefaultGraphCell			collapsedCell		= null; 
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * @param model
	 */
	public GJGraph (GraphModel model) {
		super (model);
	}

	/* expanding and collapsing: */
	/**
	 * @param model
	 * @param cache
	 */
	public GJGraph (GraphModel model, GraphLayoutCache cache) {
		super (model, cache);
	}

	// Adding Tooltip Handling to a Graph
	// Return Cell Label as a Tooltip (Demo)
	/* (non-Javadoc)
	 * @see org.jgraph.JGraph#getToolTipText(java.awt.event.MouseEvent)
	 */
	public String getToolTipText(MouseEvent e)
	{
		if (e != null) {
			// Fetch Cell under Mousepointer
			Object c = getFirstCellForLocation(e.getX(), e.getY());
			if (c != null)
				// Convert Cell to String and Return
				return convertValueToString(c);
		}
		return null;		
	}

	/* expanding and collapsing: */
	/**
	 * @param characteristicCells
	 */
	public void setCharacteristicCells(ArrayList<DefaultGraphCell> characteristicCells) {
		this.characteristicCells = characteristicCells;
	}
	
	/**
	 * @param characteristicCell
	 */
	public void addCharacteristicCell(DefaultGraphCell characteristicCell) {
		characteristicCells.add(characteristicCell);
	}
	
	/**
	 * @param characteristicCell
	 */
	public void removeCharacteristicCell(DefaultGraphCell characteristicCell) {
		characteristicCells.remove(characteristicCell);
	}

	/**
	 * @return
	 */
	public ArrayList<DefaultGraphCell> getCharacteristicCells() {
		return characteristicCells;
	}

	/**
	 * @param cells
	 */
	public void removeCharacteristicCells(Object[] cells) {
		if (cells.length > 0)
			for (int i=0; i<cells.length; i++) {
				if (cells[i] instanceof DefaultGraphCell) {
					characteristicCells.remove(cells[i]);
				}
			}	
	}
	
	/**
	 * @param collapsedCell
	 */
	public void setCollapsedCell(DefaultGraphCell collapsedCell) {
		this.collapsedCell = collapsedCell;
		if (this.collapsedCell != null) {
			GraphConstants.setGradientColor(this.collapsedCell.getAttributes(), Color.RED);
		}
	}

	/**
	 * @return
	 */
	public DefaultGraphCell getCollapsedCell() {
		return collapsedCell;
	}

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}

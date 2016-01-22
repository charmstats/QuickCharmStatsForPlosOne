package org.gesis.charmstats.view;


import java.awt.Component;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.EventObject;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.EventListenerList;
import javax.swing.table.TableCellEditor;
import javax.swing.tree.TreeCellEditor;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class JComponentCellEditor implements TableCellEditor, TreeCellEditor,
Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected EventListenerList listenerList = new EventListenerList();
	transient protected ChangeEvent changeEvent = null;
	
	protected JComponent editorComponent = null;
	protected JComponent container = null;		// Can be tree or table
	
	
	/**
	 * @return
	 */
	public Component getComponent() {
		return editorComponent;
	}
	
	
	/* (non-Javadoc)
	 * @see javax.swing.CellEditor#getCellEditorValue()
	 */
	public Object getCellEditorValue() {
		return editorComponent;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.CellEditor#isCellEditable(java.util.EventObject)
	 */
	public boolean isCellEditable(EventObject anEvent) {
		return true;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.CellEditor#shouldSelectCell(java.util.EventObject)
	 */
	public boolean shouldSelectCell(EventObject anEvent) {
		if( editorComponent != null && anEvent instanceof MouseEvent
			&& ((MouseEvent)anEvent).getID() == MouseEvent.MOUSE_PRESSED )
		{
         Component dispatchComponent = SwingUtilities.getDeepestComponentAt(editorComponent, 3, 3 );
			MouseEvent e = (MouseEvent)anEvent;
			MouseEvent e2 = new MouseEvent( dispatchComponent, MouseEvent.MOUSE_RELEASED,
				e.getWhen() + 100000, e.getModifiers(), 3, 3, e.getClickCount(),
				e.isPopupTrigger() );
			dispatchComponent.dispatchEvent(e2); 
			e2 = new MouseEvent( dispatchComponent, MouseEvent.MOUSE_CLICKED,
				e.getWhen() + 100001, e.getModifiers(), 3, 3, 1,
				e.isPopupTrigger() );
			dispatchComponent.dispatchEvent(e2); 
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.CellEditor#stopCellEditing()
	 */
	public boolean stopCellEditing() {
		fireEditingStopped();
		return true;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.CellEditor#cancelCellEditing()
	 */
	public void cancelCellEditing() {
		fireEditingCanceled();
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.CellEditor#addCellEditorListener(javax.swing.event.CellEditorListener)
	 */
	public void addCellEditorListener(CellEditorListener l) {
		listenerList.add(CellEditorListener.class, l);
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.CellEditor#removeCellEditorListener(javax.swing.event.CellEditorListener)
	 */
	public void removeCellEditorListener(CellEditorListener l) {
		listenerList.remove(CellEditorListener.class, l);
	}
	
	/**
	 * 
	 */
	protected void fireEditingStopped() {
		Object[] listeners = listenerList.getListenerList();
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length-2; i>=0; i-=2) {
			if (listeners[i]==CellEditorListener.class) {
				// Lazily create the event:
				if (changeEvent == null)
					changeEvent = new ChangeEvent(this);
				((CellEditorListener)listeners[i+1]).editingStopped(changeEvent);
			}	       
		}
	}
	
	/**
	 * 
	 */
	protected void fireEditingCanceled() {
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length-2; i>=0; i-=2) {
			if (listeners[i]==CellEditorListener.class) {
				// Lazily create the event:
				if (changeEvent == null)
					changeEvent = new ChangeEvent(this);
				((CellEditorListener)listeners[i+1]).editingCanceled(changeEvent);
			}	       
		}
	}
	
	// implements javax.swing.tree.TreeCellEditor
	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeCellEditor#getTreeCellEditorComponent(javax.swing.JTree, java.lang.Object, boolean, boolean, boolean, int)
	 */
	public Component getTreeCellEditorComponent(JTree tree, Object value,
		boolean isSelected, boolean expanded, boolean leaf, int row) {
		@SuppressWarnings("unused")
		String stringValue = tree.convertValueToText(value, isSelected, expanded, leaf, row, false);
		
		editorComponent = (JComponent)value;
		container = tree;
		return editorComponent;
	}
	
	// implements javax.swing.table.TableCellEditor
	/* (non-Javadoc)
	 * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
	 */
	public Component getTableCellEditorComponent(JTable table, Object value,
		boolean isSelected, int row, int column) {
		
		editorComponent = (JComponent)value;
		container = table;
		return editorComponent;
	}
	
} // End of class JComponentCellEditor
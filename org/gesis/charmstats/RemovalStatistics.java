package org.gesis.charmstats;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.gesis.charmstats.model.Attributes;
import org.gesis.charmstats.model.User;

public class RemovalStatistics {
	private User				removingAdmin;
	private Timestamp			removalDate;
	private List<Attributes>	selection;
	private List<Attributes>	removed;
	private List<Attributes>	excluded; // already in use
	private List<Attributes>	refused; // no access, as locked at that time
	
	/*
	 *	Constructor(s)
	 */
	public RemovalStatistics() {
		java.util.Date now = new java.util.Date();
		
		setRemovalDate(new Timestamp(now.getTime()));
		
		setSelection(new ArrayList<Attributes>());
		setRemoved(new ArrayList<Attributes>());
		setExcluded(new ArrayList<Attributes>());
		setRefused(new ArrayList<Attributes>());
	}
	
	/*
	 *	Methode(s)
	 */
	public User getRemovingAdmin() {
		return removingAdmin;
	}
	
	public void setRemovingAdmin(User removingAdmin) {
		this.removingAdmin = removingAdmin;
	}
	
	public Timestamp getRemovalDate() {
		return removalDate;
	}
	
	public void setRemovalDate(Timestamp importingDate) {
		this.removalDate = importingDate;
	}
	
	public List<Attributes> getSelection() {
		return selection;
	}
	
	private void setSelection(List<Attributes> selection) {
		this.selection = selection;
	}
	
	public void addSelection(Attributes attribute) {
		selection.add(attribute);
	}
	
	public List<Attributes> getRemoved() {
		return removed;
	}
	
	private void setRemoved(List<Attributes> removed) {
		this.removed = removed;
	}
	
	public void addRemoved(Attributes attribute) {
		removed.add(attribute);
	}
	
	public List<Attributes> getExcluded() {
		return excluded;
	}
	
	private void setExcluded(List<Attributes> excluded) {
		this.excluded = excluded;
	}
	
	public void addExcluded(Attributes attribute) {
		excluded.add(attribute);
	}
	
	public List<Attributes> getRefused() {
		return refused;
	}
	
	private void setRefused(List<Attributes> refused) {
		this.refused = refused;
	}
	
	public void addRefused(Attributes attribute) {
		refused.add(attribute);
	}

}



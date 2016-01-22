package org.gesis.charmstats;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.gesis.charmstats.model.Attributes;
import org.gesis.charmstats.model.User;

public class ImportStatistics {

	private User				importingAdmin;
	private Timestamp			importingDate;
	private String				importedDatafile;
	private List<Attributes>	selection;
	private List<String>		originalNames;
	private List<String>		originalLabels;
	private List<Attributes>	imported;
	private List<Attributes>	skipped;
	private List<Attributes>	unhandled;
	private List<Attributes>	duplicated;
	
	/*
	 *	Constructor(s)
	 */
	public ImportStatistics() {
		java.util.Date now = new java.util.Date();
		
		setImportingDate(new Timestamp(now.getTime()));
		
		setSelection(new ArrayList<Attributes>());
		setOriginalNames(new ArrayList<String>());
		setOriginalLabels(new ArrayList<String>());
		setImported(new ArrayList<Attributes>());
		setSkipped(new ArrayList<Attributes>());
		setUnhandled(new ArrayList<Attributes>());
		setDuplicated(new ArrayList<Attributes>());
	}

	/*
	 *	Methode(s)
	 */
	public User getImportingAdmin() {
		return importingAdmin;
	}

	public void setImportingAdmin(User importingAdmin) {
		this.importingAdmin = importingAdmin;
	}

	public Timestamp getImportingDate() {
		return importingDate;
	}

	public void setImportingDate(Timestamp importingDate) {
		this.importingDate = importingDate;
	}

	public String getImportedDatafile() {
		return importedDatafile;
	}

	public void setImportedDatafile(String importedDatafile) {
		this.importedDatafile = importedDatafile;
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
	
	public List<String> getOriginalNames() {
		return originalNames;
	}

	private void setOriginalNames(List<String> originalNames) {
		this.originalNames = originalNames;
	}
	
	public void addOriginalName(String originalName) {
		originalNames.add(originalName);
	}
	
	public List<String> getOriginalLabels() {
		return originalLabels;
	}

	private void setOriginalLabels(List<String> originalLabels) {
		this.originalLabels = originalLabels;
	}
	
	public void addOriginalLabel(String originalLabel) {
		originalLabels.add(originalLabel);
	}

	public List<Attributes> getImported() {
		return imported;
	}

	private void setImported(List<Attributes> imported) {
		this.imported = imported;
	}
	
	public void addImported(Attributes attribute) {
		imported.add(attribute);
	}

	public List<Attributes> getSkipped() {
		return skipped;
	}

	private void setSkipped(List<Attributes> skipped) {
		this.skipped = skipped;
	}
	
	public void addSkipped(Attributes attribute) {
		skipped.add(attribute);
	}

	public List<Attributes> getUnhandled() {
		return unhandled;
	}

	private void setUnhandled(List<Attributes> unhandled) {
		this.unhandled = unhandled;
	}
	
	public void addUnhandled(Attributes attribute) {
		unhandled.add(attribute);
	}

	public List<Attributes> getDuplicated() {
		return duplicated;
	}

	private void setDuplicated(List<Attributes> duplicated) {
		this.duplicated = duplicated;
	}
	
	public void addDuplicated(Attributes attribute) {
		duplicated.add(attribute);
	}
	
}

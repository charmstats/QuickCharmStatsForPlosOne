package org.gesis.charmstats;

import org.gesis.charmstats.model.Project;
import org.gesis.charmstats.persistence.DBEntity;

/**
 *	This is an Entity-Class
 * 
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class Search extends DBEntity {

	/*
	 *	Fields
	 */
	private Project	project;		
	private String	defaultLabel; 

	/* Reference to a search object storing text or a sample object: */
	private Object	reference;
	
	/**
	 *	Constructor 
	 */
	public Search () {
		super();
	}
	
	/*
	 *	Methods
	 */
	/**
	 * @param project
	 */
	public void setProject(Project project) {
		this.project = project;
	}

	/**
	 * @return
	 */
	public Project getProject() {
		return project;
	}
	
	/**
	 * @param defaultLabel
	 */
	public void setDefaultLabel(String defaultLabel) {
		this.defaultLabel = defaultLabel;
	}

	/**
	 * @return
	 */
	public String getDefaultLabel() {
		return defaultLabel;
	}

	/**
	 * @param reference
	 */
	public void setReference(Object reference) {
		this.reference = reference;
	}

	/**
	 * @return
	 */
	public Object getReference() {
		return reference;
	}
	
}

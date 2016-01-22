package org.gesis.charmstats.model;

import org.gesis.charmstats.persistence.ActivityLog;

/**
 * 
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class CStatsModel {

	/*
	 *	Fields
	 */
	private User	user			= new User();
	private Project	project			= new Project();
	private String	agency;
	
	private Boolean isAutocomplete	= false;
	private Boolean isBrowsing		= true;
	
	private ActivityLog actLogNewProject = null; 
	
	/**
	 *	Constructor 
	 */
	public CStatsModel() {
		super();
	}
	
	/*
	 *	Methods
	 */
	/* User */
	/**
	 * @param user
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	/**
	 * @return
	 */
	public User getUser() {
		return user;
	}

	/* Project */
	/**
	 * @return
	 */
	public Project getProject() {
		return project;
	}

	/**
	 * @param project
	 */
	public void setProject(Project project) {
		this.project = project;
	}

	/* Agency */
	/**
	 * @return
	 */
	public String getAgency() {
		return agency;
	}

	/**
	 * @param agency
	 */
	public void setAgency(String agency) {
		this.agency = agency;
	}

	/* Autocomplete status */
	/**
	 * @return
	 */
	public Boolean getIsAutocomplete() {
		return isAutocomplete;
	}

	/**
	 * @param isAutocomplete
	 */
	public void setIsAutocomplete(Boolean isAutocomplete) {
		this.isAutocomplete = isAutocomplete;
	}
	
	/* Browsing status */
	/**
	 * @return
	 */
	public Boolean getIsBrowsing() {
		return isBrowsing;
	}

	/**
	 * @param isBrowsing
	 */
	public void setIsBrowsing(Boolean isBrowsing) {
		this.isBrowsing = isBrowsing;
	}
	
	public ActivityLog getActLogNewProject() {
		return actLogNewProject;
	}

	public void setActLogNewProject(ActivityLog actLogewProject) {
		this.actLogNewProject = actLogewProject;
	}
	
}

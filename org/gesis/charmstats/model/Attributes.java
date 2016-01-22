package org.gesis.charmstats.model;

import java.util.ArrayList;
import java.util.UUID;

import org.gesis.charmstats.persistence.DBEntity;

/**
 * 
 * @author	Martin Friedrichs
 * @since	0.1
 *
 */
public abstract class Attributes extends DBEntity {

	/*
	 * ConDimension, OperaIndicator, (DC)Variable, Measurement will be
	 * sub-classed from this
	 */

	/*
	 * Fields
	 */
	private String ddiSupCodSchemeUUId;
	private String ddiSupCatSchemeUUId;

	/**
	 *	Constructor 
	 */
	public Attributes() {
		super();

		ddiSupCodSchemeUUId = String.valueOf(UUID.randomUUID());
		ddiSupCatSchemeUUId = String.valueOf(UUID.randomUUID());
	}

	/*
	 * Comment
	 */
	/**
	 * @param comment
	 */
	public void setComment(Comment comment) {
	}

	/**
	 * @return
	 */
	public Comment getComment() {
		return null;
	}

	/*
	 * Definition
	 */
	/**
	 * @param definition
	 */
	public void setDefinition(Definition definition) {
	}

	/**
	 * @return
	 */
	public Definition getDefinition() {
		return null;
	}

	/*
	 * Keyword(s)
	 */
	/**
	 * @param keyword
	 */
	public void setKeyword(Keyword keyword) {
	}

	/**
	 * @return
	 */
	public Keyword getKeyword() {
		return null;
	}

	/**
	 * @param keywords
	 */
	public void setKeywords(ArrayList<Keyword> keywords) {
	}

	/**
	 * @param keyword
	 */
	public void addKeyword(Keyword keyword) {
	}

	/**
	 * @return
	 */
	public ArrayList<Keyword> getKeywords() {
		return null;
	}

	/**
	 * @param index
	 * @return
	 */
	public Keyword getKeywordByIndex(int index) {
		return null;
	}

	/**
	 * @param id
	 * @return
	 */
	public Keyword getKeywordByID(int id) {
		return null;
	}

	/**
	 * @param argValue
	 * @return
	 */
	public boolean hasKeyword(Keyword argValue) {
		return false;
	}

	/*
	 * DDI Category Scheme UUID
	 */
	/**
	 * @return
	 */
	public String getDDISupCatSchemeUUId() {
		return ddiSupCatSchemeUUId;
	}

	/**
	 * @param ddiSupCatSchemeUUId
	 */
	public void setDDISupCatSchemeUUId(String ddiSupCatSchemeUUId) {
		this.ddiSupCatSchemeUUId = ddiSupCatSchemeUUId;
	}

	/*
	 * DDI Code Scheme UUID
	 */
	/**
	 * @return
	 */
	public String getDDISupCodSchemeUUId() {
		return ddiSupCodSchemeUUId;
	}

	/**
	 * @param ddiSupCodSchemeUUId
	 */
	public void setDDISupCodSchemeUUId(String ddiSupCodSchemeUUId) {
		this.ddiSupCodSchemeUUId = ddiSupCodSchemeUUId;
	}

}

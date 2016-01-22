package org.gesis.charmstats.model;

import org.gesis.charmstats.persistence.DBEntity;
import org.gesis.charmstats.persistence.EntityType;

/**
 *	This is an Entity-Class 
 * 
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class Country extends DBEntity {

	/* charmstats.countries (MySQL)
		`id`				int(11) 	NOT NULL,
		`label`				varchar(64) 		 default NULL,
		`number`			int(11) 			 default NULL,
		`country_code`		varchar(16) 		 default NULL,
		`country_code_norm` varchar(16) 		 default NULL,
		PRIMARY KEY  (`id`)
	*/
	
	/*
	 *	Fields
	 */
	private String	label;
	private int		number;
	private String	country_code;
	private String	country_code_norm;
	
	/**
	 *	Constructor 
	 */
	public Country () {
		super();
		
		entity_type = EntityType.COUNTRIES;
		
		setLabel("");
		setNumber(-1);
		country_code		= "";
		country_code_norm	= "";
	}
	
	/*
	 *	Methods
	 */
	/**
	 * @param label
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return
	 */
	public String getLabel() {
		return label;
	}
	
	/**
	 * @param number
	 */
	public void setNumber(int number) {
		this.number = number;
	}
	
	/**
	 * @return
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * @param country_code
	 */
	public void setCountryCode(String country_code) {
		this.country_code = country_code;
	}
	
	/**
	 * @return
	 */
	public String getCountryCode() {
		return country_code;
	}
	
	/**
	 * @param country_code_norm
	 */
	public void setCountryCodeNorm(String country_code_norm) {
		this.country_code_norm = country_code_norm;
	}
	
	/**
	 * @return
	 */
	public String getCountryCodeNorm() {
		return country_code_norm;
	}
	
}

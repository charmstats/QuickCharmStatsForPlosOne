package org.gesis.charmstats.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import org.gesis.charmstats.persistence.DBEntity;
import org.gesis.charmstats.persistence.DBField;

/**
 *	This is an Entity-Class
 * 
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class Person extends DBEntity {
	
	/* charmstats.persons (MySQL)
	  `id`				  int(11)	  NOT NULL auto_increment,
	  `name`			  varchar(64) 		   default NULL,
	  `type`			  int(11)	  NOT NULL,
	  `country`			  int(11)			   default NULL,
	  `city`			  varchar(64)		   default NULL,
	  `zip_code`		  varchar(64)		   default NULL,
	  `street` 			  varchar(64)		   default NULL,
	  `street_number`	  varchar(8)		   default NULL,
	  `phone`			  varchar(16)		   default NULL,
	  `email`			  varchar(320)		   default NULL,
	  `instant_messaging` varchar(320)		   default NULL,
	  `date_of_birth`	  datetime			   default NULL,
	  `place_of_birth`	  varchar(64)		   default NULL,
	  `department`		  varchar(255)		   default NULL,
	  `logo_uri`		  varchar(64)		   default NULL,
	  `role`			  int(11)			   default '0',
	  PRIMARY KEY  (`id`)
	*/
	
	/*
	 *	Fields
	 */
	private String	name;
	private PersonType type;
	private PersonRole role;
	/* Address */
	private Country	country;
	private String	city;
	private String	zip_code;
	private String	street;
	private String	street_number;
	/* Communication */
	private String	phone;
	private String	email;
	private String 	instant_messaging;
	/* Individual exclusive */
	private Date	date_of_birth;
	private String	place_of_birth;
	/* Institution exclusive */
	private String	department;
	private String	logo_uri;
	
	/*
	 *	Constructors
	 */
	/**
	 * 
	 */
	public Person() {
		super();
		
		setType(PersonType.NONE);
		setRole(PersonRole.NONE);
		setDateOfBirth(null);
	}
	
	/**
	 * @param id
	 */
	public Person (int id) {
		this();
		
		setEntityID(id);
	}

	/*
	 *	Methods
	 */
	/* Name */
	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/* Type */
	/**
	 * @param type
	 */
	public void setType(PersonType type) {
		this.type = type;
	}
	
	/**
	 * @return
	 */
	public PersonType getType() {
		return type;
	}

	/* Role */
	/* Role is not to be stored with the Person, but stored with PersonRef if needed! */
	/**
	 * @param role
	 */
	public void setRole(PersonRole role) {
		this.role = role;
	}

	/**
	 * @return
	 */
	public PersonRole getRole() {
		return role;
	}

	/* Address */
	/**
	 * @param country
	 */
	public void setCountry(Country country) {
		this.country = country;
	}
	
	/**
	 * @return
	 */
	public Country getCountry() {
		return country;
	}

	/**
	 * @param city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param zip_code
	 */
	public void setZIPCode(String zip_code) {
		this.zip_code = zip_code;
	}

	/**
	 * @return
	 */
	public String getZIPCode() {
		return zip_code;
	}

	/**
	 * @param street
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * @return
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * @param street_number
	 */
	public void setStreetNumber(String street_number) {
		this.street_number = street_number;
	}

	/**
	 * @return
	 */
	public String getStreetNumber() {
		return street_number;
	}
	
	/* Communication */
	/* Phone */
	/**
	 * @param phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return
	 */
	public String getPhone() {
		return phone;
	}

	/* Email */
	/**
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return
	 */
	public String getEmail() {
		return email;
	}
	
	/* Instant Messaging */
	/**
	 * @param instant_messaging
	 */
	public void setInstantMessaging(String instant_messaging) {
		this.instant_messaging = instant_messaging;
	}

	/**
	 * @return
	 */
	public String getInstantMessaging() {
		return instant_messaging;
	}
	
	/* Individual exclusive */
	/* DateOfBirth */
	/**
	 * @param date_of_birth
	 */
	public void setDateOfBirth(Date date_of_birth) {
		this.date_of_birth = date_of_birth;
	}

	/**
	 * @return
	 */
	public Date getDateOfBirth() {
		return date_of_birth;
	}

	/* PlaceOfBirth */
	/**
	 * @param place_of_birth
	 */
	public void setPlaceOfBirth(String place_of_birth) {
		this.place_of_birth = place_of_birth;
	}

	/**
	 * @return
	 */
	public String getPlaceOfBirth() {
		return place_of_birth;
	}
	
	/* Institution exclusive */
	/* Department */
	/**
	 * @param department
	 */
	public void setDepartment(String department) {
		this.department = department;
	}

	/**
	 * @return
	 */
	public String getDepartment() {
		return department;
	}
	
	/* LogoURI */
	/**
	 * @param logo_uri
	 */
	public void setLogoURI(String logo_uri) {
		this.logo_uri = logo_uri;
	}

	/**
	 * @return
	 */
	public String getLogoURI() {
		return logo_uri;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		if (name != null)
			return name;
			
		/*In case of fault:*/
		return super.toString();
	}
	
	/* List of Persons */
	/**
	 * @param con
	 * @return
	 */
	public static ArrayList<Person> getAllPersons(Connection con) {
		ArrayList<Person> persons = new ArrayList<Person>();
		
		java.sql.Statement stmt;
		ResultSet rSet;
		
		if (con != null) {
			try {
				if(!con.isClosed()) {
					stmt = con.createStatement();
					
					String sqlQuery = "select pers.id, pers.name "
						+ "from persons pers";
					
					rSet = stmt.executeQuery( sqlQuery );
					
					while (rSet.next()) {
						Person pers = new Person(rSet.getInt("id"));
						pers.setName(rSet.getString("name"));
						
						persons.add(pers);
					}
					
					stmt.close();
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(
						null, e.getMessage(), "SQLException:",  JOptionPane.ERROR_MESSAGE);
					  	
				/* DEMO ONLY */
				System.err.println("SQLException: " + e.getMessage());
				e.printStackTrace();
			}
		}
		
		return persons;
	}
	
	/**
	 * @param con
	 * @param role
	 * @return
	 */
	public static ArrayList<Person> getAllPersonsWithRole(Connection con, PersonRole role) {
		ArrayList<Person> persons = new ArrayList<Person>();
		
		java.sql.Statement stmt;
		ResultSet rSet;
		
		if (con != null) {
			try {
				if(!con.isClosed()) {
					stmt = con.createStatement();
					
					String sqlQuery = "select pers.id, pers.name "
						+ "from persons pers "
						+ "where pers.role = " + role.getID();
					
					rSet = stmt.executeQuery( sqlQuery );
					
					while (rSet.next()) {
						Person pers = new Person(rSet.getInt("id"));
						pers.setName(rSet.getString("name"));
						
						persons.add(pers);
					}
					
					stmt.close();
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(
						null, e.getMessage(), "SQLException:",  JOptionPane.ERROR_MESSAGE);
					  	
				/* DEMO ONLY */
				System.err.println("SQLException: " + e.getMessage());
				e.printStackTrace();
			}
		}
		
		return persons;
	}
	
	/*
	 *	Load and Store to DB
	 */
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.persistence.DBEntity#entityLoad(java.sql.Connection)
	 */
	public boolean entityLoad(Connection connection) {

		boolean loadStatus = false;

		java.sql.Statement stmt;
		ResultSet rSet;
		
		if (connection.equals(null)) {
			loadStatus = false;
			JOptionPane.showMessageDialog(
					null, "No Connection to DataBase!", "Error:", JOptionPane.ERROR_MESSAGE);
							
			/* DEMO ONLY */
			System.err.println("Error: No Connection to DataBase!");
			
			return loadStatus;
		}
		
		try {
			if(!connection.isClosed()) {
				stmt = connection.createStatement();
					
				String sqlQuery = "SELECT name, type, role, " +
				    "country, city, zip_code, street, street_number, " +
					"phone, email, instant_messaging, " +
					"date_of_birth, place_of_birth, " +
					"department, logo_uri " +
					"FROM persons " +
					"WHERE id=\"" + this.getEntityID() + "\"";
					
				rSet = stmt.executeQuery( sqlQuery );
					
				while (rSet.next()) {
					this.setName(rSet.getString("name"));
					this.setType(PersonType.getItem(rSet.getInt("type")));
					this.setRole(PersonRole.getItem(rSet.getInt("role")));
					
					/* Address */
					country = new Country();
					country.setEntityID(rSet.getInt("country"));
					country.entityLoad(connection);					
					this.setCity(rSet.getString("city"));					
					this.setZIPCode(rSet.getString("zip_code"));
					this.setStreet(rSet.getString("street"));
					this.setStreetNumber(rSet.getString("street_number"));
					
					/* Communication */
					this.setPhone(rSet.getString("phone"));
					this.setEmail(rSet.getString("email"));
					this.setInstantMessaging(rSet.getString("instant_messaging")); 

					/* Individual exclusive */
					setDateOfBirth(rSet.getDate("date_of_birth"));					
					setPlaceOfBirth(rSet.getString("place_of_birth"));
					
					/* Institution exclusive */
					setDepartment(rSet.getString("department"));
					setLogoURI(rSet.getString("logo_uri"));
					
					loadStatus = true;
				}
		
				stmt.close();
			} else {
				loadStatus = false;
				JOptionPane.showMessageDialog(
						null, "No open Connection!", "Error:", JOptionPane.ERROR_MESSAGE);
									
				/* DEMO ONLY */
				System.err.println("Error: No open Connection!");
			}
		} catch (SQLException e) {
			loadStatus = false;
			JOptionPane.showMessageDialog(
					null, e.getMessage(), "SQLException:",  JOptionPane.ERROR_MESSAGE);
				  	
			/* DEMO ONLY */
			System.err.println("SQLException: " + e.getMessage());
			e.printStackTrace();
		}
		
		return loadStatus;
	}
	
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.persistence.DBEntity#entityStore(java.sql.Connection)
	 */
	public boolean entityStore(Connection connection) {

		boolean storeStatus = true;
		int rows = -1;
		
		if (connection.equals(null)) {
			storeStatus = false;
			JOptionPane.showMessageDialog(
					null, "No Connection to DataBase!", "Error:", JOptionPane.ERROR_MESSAGE);
							
			/* DEMO ONLY */
			System.err.println("Error: No Connection to DataBase!");
			
			return storeStatus;
		}
		
		if (entity_id > 0) {
			/*
			 *	Update
			 */
			try {
				connection.setAutoCommit(false);
								
				if (storeStatus) {
					sqlQuery = "UPDATE persons SET "
						+ "name = ?, "
						+ "type = ?, "
						+ "role = ?, "
						
						/* Address */
						+ "country = ?, " 
						+ "city = ?, " 
						+ "zip_code = ?, " 
						+ "street = ?, " 
						+ "street_number = ?, "
						
						/* Communication */
						+ "phone = ?, "
						+ "email = ?, "
						+ "instant_messaging = ?, "
						
						/* Individual exclusive */
						+ "date_of_birth = ?, "
						+ "place_of_birth = ?, "
						
						/* Institution exclusive */
						+ "department = ?, "
						+ "logo_uri = ? "
						
					    + "WHERE id = ?";					
					
					prepStmt = connection.prepareStatement(sqlQuery);
					prepStmt.setString(1, DBField.PER_NAM.truncate(getName()));
					prepStmt.setInt(2, getType().getID());
					prepStmt.setInt(3, getRole().getID());
					/* Address */
					if (getCountry() != null)
						prepStmt.setInt(4, getCountry().getEntityID());
					else
						prepStmt.setNull(4, java.sql.Types.INTEGER);					
					prepStmt.setString(5, DBField.PER_CIT.truncate(getCity()));
					prepStmt.setString(6, DBField.PER_ZIP_COD.truncate(getZIPCode()));
					prepStmt.setString(7, DBField.PER_STR.truncate(getStreet()));
					prepStmt.setString(8, DBField.PER_STR_NUM.truncate(getStreetNumber()));
					/* Communication */
					prepStmt.setString(9, DBField.PER_PHO.truncate(getPhone()));
					prepStmt.setString(10, DBField.PER_EMA.truncate(getEmail()));
					prepStmt.setString(11, DBField.PER_INS_MES.truncate(getInstantMessaging()));
					/* Individual exclusive */
					if (getDateOfBirth() != null)
						prepStmt.setDate(12, new java.sql.Date(getDateOfBirth().getTime()));
					else
						prepStmt.setNull(12, java.sql.Types.DATE);
					prepStmt.setString(13, DBField.PER_PLA_OF_BIR.truncate(getPlaceOfBirth()));
					/* Institution exclusive */
					prepStmt.setString(14, DBField.PER_DEP.truncate(getDepartment()));
					prepStmt.setString(15, DBField.PER_LOG_URI.truncate(getLogoURI()));
										
					prepStmt.setInt(16, this.getEntityID());
					
					rows = prepStmt.executeUpdate();
					
					if (rows < 1)				
						storeStatus = false;
					
					prepStmt.close();
				}
		    } catch (SQLException e) {
		    	storeStatus = false;
		    	JOptionPane.showMessageDialog(
		    			null, e.getMessage(), "SQLException:",  JOptionPane.ERROR_MESSAGE);
		    		  	
		    	/* DEMO ONLY */
		    	System.err.println("SQLException: " + e.getMessage());
		    	e.printStackTrace();
		    }
		} else {
			/*
			 *	Insert
			 */
			try {
				connection.setAutoCommit(false);
								
				if (storeStatus) {
					/*
					 *	Use Insert-Statement
					 */													
					sqlQuery = "INSERT INTO persons (name, type, role, "+
							"country, city, zip_code, street, street_number, "+
							"phone, email, instant_messaging, "+
							"date_of_birth, place_of_birth, " +
							"department, logo_uri";						
						sqlQuery += ") VALUES ("
							+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?";
						sqlQuery += ")";
						
					prepStmt = connection.prepareStatement(sqlQuery);
					prepStmt.setString(1, DBField.PER_NAM.truncate(getName()));
					prepStmt.setInt(2, getType().getID());
					prepStmt.setInt(3, getRole().getID());
					/* Address */
					if (getCountry() != null)
						prepStmt.setInt(4, getCountry().getEntityID());
					else
						prepStmt.setNull(4, java.sql.Types.INTEGER);					
					prepStmt.setString(5, DBField.PER_CIT.truncate(getCity()));
					prepStmt.setString(6, DBField.PER_ZIP_COD.truncate(getZIPCode()));
					prepStmt.setString(7, DBField.PER_STR.truncate(getStreet()));
					prepStmt.setString(8, DBField.PER_STR_NUM.truncate(getStreetNumber()));
					/* Communication */
					prepStmt.setString(9, DBField.PER_PHO.truncate(getPhone()));
					prepStmt.setString(10, DBField.PER_EMA.truncate(getEmail()));
					prepStmt.setString(11, DBField.PER_INS_MES.truncate(getInstantMessaging()));
					/* Individual exclusive */
					if (getDateOfBirth()  != null)
						prepStmt.setDate(12, new java.sql.Date(getDateOfBirth().getTime()));
					else
						prepStmt.setNull(12, java.sql.Types.DATE);
					prepStmt.setString(13, DBField.PER_PLA_OF_BIR.truncate(getPlaceOfBirth()));
					/* Institution exclusive */
					prepStmt.setString(14, DBField.PER_DEP.truncate(getDepartment()));
					prepStmt.setString(15, DBField.PER_LOG_URI.truncate(getLogoURI()));
					
					rows = prepStmt.executeUpdate();
					
					if (rows < 1)
						storeStatus = false;
					else {					
						rsltst = prepStmt.getGeneratedKeys();
		
					    if (rsltst.next()) {
					        this.setEntityID(rsltst.getInt(1));
					    } else {
					    	storeStatus = false;
					    }
					    rsltst.close();
					}
					
					prepStmt.close();
				}
								
			} catch (SQLException e) {
		    	storeStatus = false;
		    	JOptionPane.showMessageDialog(
		    			null, e.getMessage(), "SQLException:",  JOptionPane.ERROR_MESSAGE);
		    		  	
		    	/* DEMO ONLY */
		    	System.err.println("SQLException: " + e.getMessage());
		    	e.printStackTrace();
			}	
		}
   
		if (!storeStatus) {
	    	try {
				connection.rollback();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(
						null, e.getMessage(), "SQLException:",  JOptionPane.ERROR_MESSAGE);
					  	
				/* DEMO ONLY */
				System.err.println("SQLException: " + e.getMessage());
				e.printStackTrace();
			}
		}

		return storeStatus;
	}
}

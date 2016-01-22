package org.gesis.charmstats.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import org.gesis.charmstats.persistence.DBEntity;
import org.gesis.charmstats.persistence.DBField;
import org.gesis.charmstats.persistence.EntityType;

/**
 * This is an Entity Class
 * 
 * @author	Martin Friedrichs
 * @since	0.1
 *
 */
public class BiasMetadata extends DBEntity {
	
	/* charmstats.bias_metadata (MySQL)
		`id`			  int(11) 	   NOT NULL	auto_increment,
		`is_inverse`	  tinyint(1)   			default '0',
		`is_different`	  tinyint(1)   			default '0',
		`rating`		  int(11)	   			default NULL,
		`missings`		  varchar(255) 			default NULL,
		`standard_coding` int(11)	   			default NULL,
		`preference`	  int(11)	   			default NULL,
		PRIMARY KEY  (`id`)
	*/
	
	/*
	 *	Fields
	 */
	private boolean 			inverse;
	private boolean 			difference;
	private BiasRatingType 		rating;
	private String				missings;
	private BiasStandCodingType standCoding;
	private BiasPreferenceType	preference;
	
	/**
	 *	Constructor 
	 */
	public BiasMetadata() {
		super ();
		
		entity_type = EntityType.BIAS_METADATA;
		
		setInverse(false);
		setDifference(false);
		setRating(BiasRatingType.NONE);
		setMissings(new String());
		setStandCoding(BiasStandCodingType.NONE);
		setPreference(BiasPreferenceType.NONE);
	}
	
	/*
	 *	Methods
	 */
	/* Inverse */
	/**
	 * @return
	 */
	public boolean isInverse() {
		return inverse;
	}

	// mySQL only:
	/**
	 * @return
	 */
	public int getInverse() {
		return (inverse ? 1 : 0);
	}
	
	/**
	 * @param inverse
	 */
	public void setInverse(boolean inverse) {
		this.inverse = inverse;
	}

	/* Difference in Metric */
	/**
	 * @return
	 */
	public boolean isDifference() {
		return difference;
	}
	
	// mySQL only:
	/**
	 * @return
	 */
	public int getDifference() {
		return (difference ? 1 : 0);
	}

	/**
	 * @param difference
	 */
	public void setDifference(boolean difference) {
		this.difference = difference;
	}

	/* Bias Rating */
	/**
	 * @return
	 */
	public BiasRatingType getRating() {
		return rating;
	}

	/**
	 * @param rating
	 */
	public void setRating(BiasRatingType rating) {
		this.rating = rating;
	}

	/* Missing Categories/Values which can't be recoded */
	/**
	 * @return
	 */
	public String getMissings() {
		return missings;
	}

	/**
	 * @param missings
	 */
	public void setMissings(String missings) {
		this.missings = missings;
	}

	/* Standard Coding Procedures */
	/**
	 * @return
	 */
	public BiasStandCodingType getStandCoding() {
		return standCoding;
	}

	/**
	 * @param standCoding
	 */
	public void setStandCoding(BiasStandCodingType standCoding) {
		this.standCoding = standCoding;
	}

	/* Similarities Preferences */
	/**
	 * @return
	 */
	public BiasPreferenceType getPreference() {
		return preference;
	}

	/**
	 * @param preference
	 */
	public void setPreference(BiasPreferenceType preference) {
		this.preference = preference;
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
					
				String sqlQuery = "SELECT b.is_inverse, b.is_different, b.rating, b.missings, b.standard_coding, b.preference " +
					"FROM bias_metadata b " +
					"WHERE b.id=\"" + this.getEntityID() + "\"";
				
				rSet = stmt.executeQuery( sqlQuery );
					
				while (rSet.next()) {
					setInverse(rSet.getInt("is_inverse") > 0 ? true: false);
					setDifference(rSet.getInt("is_different") > 0 ? true: false);
					setRating(BiasRatingType.getItem(rSet.getInt("rating")));
					setMissings(rSet.getString("missings"));
					setStandCoding(BiasStandCodingType.getItem(rSet.getInt("standard_coding")));
					setPreference(BiasPreferenceType.getItem(rSet.getInt("preference")));
				}
				
				loadStatus = true;
							
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
					sqlQuery = "UPDATE bias_metadata SET "
						+ "is_inverse = ?, "
						+ "is_different = ?, "
						+ "rating = ?, "
						+ "missings = ?, "
						+ "standard_coding = ?, "
						+ "preference = ?";					
					sqlQuery += " WHERE id = ?";					
					
					prepStmt = connection.prepareStatement(sqlQuery);
					prepStmt.setInt(1, this.getInverse());
					prepStmt.setInt(2, this.getDifference());
					if (getRating() != null)
						prepStmt.setInt(3, getRating().getID());
					else
						prepStmt.setNull(3, java.sql.Types.INTEGER);
					prepStmt.setString(4, DBField.BIA_MIS.truncate(this.getMissings()));
					if (getStandCoding() != null)
						prepStmt.setInt(5, getStandCoding().getID());
					else
						prepStmt.setNull(5, java.sql.Types.INTEGER);
					if (getPreference() != null)
						prepStmt.setInt(6, getPreference().getID());
					else
						prepStmt.setNull(6, java.sql.Types.INTEGER);
					prepStmt.setInt(7, this.getEntityID());
					
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
					sqlQuery = "INSERT INTO bias_metadata (is_inverse, is_different, rating, missings, standard_coding, preference ";
					sqlQuery += ") VALUES ("
						+ "?, ?, ?, ?, ?, ?";
					sqlQuery += ")";
					
					prepStmt = connection.prepareStatement(sqlQuery);
					prepStmt.setInt(1, this.getInverse());
					prepStmt.setInt(2, this.getDifference());
					if (getRating() != null)
						prepStmt.setInt(3, getRating().getID());
					else
						prepStmt.setNull(3, java.sql.Types.INTEGER);
					prepStmt.setString(4, DBField.BIA_MIS.truncate(this.getMissings()));
					if (getStandCoding() != null)
						prepStmt.setInt(5, getStandCoding().getID());
					else
						prepStmt.setNull(5, java.sql.Types.INTEGER);
					if (getPreference() != null)
						prepStmt.setInt(6, getPreference().getID());
					else
						prepStmt.setNull(6, java.sql.Types.INTEGER);
					
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
			
			storeStatus = true;
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

   /* (non-Javadoc)
 * @see org.gesis.charmstats.persistence.DBEntity#entityRemove(java.sql.Connection)
 */
public boolean entityRemove(Connection connection) {
    	
    	boolean removeStatus = true;
    	int rows = -1;
    	
		if (connection.equals(null)) {
			removeStatus = false;
			JOptionPane.showMessageDialog(
					null, "No Connection to DataBase!", "Error:", JOptionPane.ERROR_MESSAGE);
							
			/* DEMO ONLY */
			System.err.println("Error: No Connection to DataBase!");
			
			return removeStatus;
		}
		
		if (entity_id > 0) {
			try {
				sqlQuery = "DELETE from bias_metadata " +
					"WHERE id = " + this.getEntityID();
				
				stmt = connection.createStatement();
				rows = stmt.executeUpdate(sqlQuery);
				
				if (rows < 1)				
					removeStatus = false;
				
				stmt.close();
			} catch (SQLException e) {
		    	removeStatus = false;
		    	JOptionPane.showMessageDialog(
		    			null, e.getMessage(), "SQLException:",  JOptionPane.ERROR_MESSAGE);
		    		  	
		    	/* DEMO ONLY */
		    	System.err.println("SQLException: " + e.getMessage());
		    	e.printStackTrace();
			}
		}
		
    	return removeStatus;
    }
}

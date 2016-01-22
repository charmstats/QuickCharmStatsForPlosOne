package org.gesis.charmstats.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class DBMisc {

	/**
	 * @param username
	 * @param password
	 * @return
	 */
	public static Connection connectToDB(String username, String password) {
		
		Connection con = null;
		String url = getDBURL();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch ( ClassNotFoundException e ) {
			JOptionPane.showMessageDialog(
					null, e.getMessage(), "ClassNotFoundException:",  JOptionPane.ERROR_MESSAGE);
				  	
			/* DEMO ONLY */
			System.err.println("ClassNotFoundException: " + e.getMessage());
			e.printStackTrace();
			
			return con;
		}

		try
		{
			con = DriverManager.getConnection(url, username, password);
			
			if(!con.isClosed())
				System.err.println("Successfully connected to MySQL server " + url + " using TCP/IP...");
		
		} catch ( SQLException e ) {
			JOptionPane.showMessageDialog(
					null, e.getMessage(), "SQLException:",  JOptionPane.ERROR_MESSAGE);
				  	
			/* DEMO ONLY */
			System.err.println("SQLException: " + e.getMessage());
			e.printStackTrace();

			return con;
		}

		return con;
	}
		
	/**
	 * @return
	 */
	public static String getDBUser() {
		return "charmstats_user";
	}
	
	/**
	 * @return
	 */
	public static String getDBPassword() {
		return "(3$$I)@";
	}
	
	/**
	 * @return
	 */
	public static String getDBURL() {
		return "jdbc:mysql://localhost/charmstatsx";
	}
}

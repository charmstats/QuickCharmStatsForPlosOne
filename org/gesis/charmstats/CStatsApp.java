package org.gesis.charmstats;

import java.sql.Connection;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.gesis.charmstats.controller.CStatsCtrl;
import org.gesis.charmstats.model.User;
import org.gesis.charmstats.persistence.DBContent;
import org.gesis.charmstats.persistence.DBMisc;

/**
 * 
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class CStatsApp {

	public static final String ERROR_BUNDLE		= "org.gesis.charmstats.resources.ErrorMessagesBundle";
	public static final String NO_CONNECTION	= "no_connection";
	
	public static final String MODEL_VERSION	= "model_version";
	
	/*
	 * Fields
	 */
	private static User user = new User();
	private static LoginPreference loginPref = new LoginPreference();


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 
		/* Internationalization */
		String language;
		String country;

		if (args.length != 2) {
			language = new String("en");
			country = new String("US");
		} else {
			language = new String(args[0]);
			country = new String(args[1]);
		}

		final Locale currentLocale = new Locale(language, country);
		
		final ResourceBundle errorBundle = ResourceBundle.getBundle(
				ERROR_BUNDLE, currentLocale);

		/* Set System Look'n'Feel: */
		setLookNFeel();

		/* Get a connection from the the MySQL-DB */
		final Connection con = DBMisc.connectToDB(DBMisc.getDBUser(),
				DBMisc.getDBPassword());

		if (con != null) {
			/* Login now */
			setUser(User.getAnonymousUser());

		} else {
			JOptionPane.showMessageDialog(null,
					errorBundle.getObject(NO_CONNECTION));
			System.exit(0);
		}

		DBContent.setConnection(con);

		/*
		 * Schedule a job for the event-dispatching thread: creating and showing
		 * this application's GUI.
		 */
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new CStatsCtrl(user, loginPref, con, currentLocale, errorBundle.getString(MODEL_VERSION));
			}
		});
	}

	/*
	 * Methods
	 */
	/**
	 * @param user
	 */
	public static void setUser(User user) {
		CStatsApp.user = user;
	}

	/**
	 * @return User
	 */
	public static User getUser() {
		return CStatsApp.user;
	}

	/**
	 * 
	 */
	private static void setLookNFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
			// handle exception
		} catch (ClassNotFoundException e) {
			// handle exception
		} catch (InstantiationException e) {
			// handle exception
		} catch (IllegalAccessException e) {
			// handle exception
		}
	}

}

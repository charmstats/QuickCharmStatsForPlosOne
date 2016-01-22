package org.gesis.charmstats.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;

import org.gesis.charmstats.model.Category;
import org.gesis.charmstats.model.Comment;
import org.gesis.charmstats.model.ConDimension;
import org.gesis.charmstats.model.Concept;
import org.gesis.charmstats.model.Measurement;
import org.gesis.charmstats.model.OperaIndicator;
import org.gesis.charmstats.model.ProContent;
import org.gesis.charmstats.model.Project;
import org.gesis.charmstats.model.Question;
import org.gesis.charmstats.model.Variable;
import org.gesis.charmstats.model.WorkStepInstance;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class DBContent {

	static Connection con = null;
	
	/**
	 * @param con
	 */
	public DBContent(Connection con) {
		DBContent.con = con; 
	}
	
	/**
	 * @param con
	 */
	public static void setConnection(Connection con) {
		DBContent.con = con;
	}
	
	/**
	 * @param classID
	 * @param sqlQuery
	 * @return
	 */
	public static ArrayList<Object> search(int classID, String sqlQuery) {
		java.sql.Statement	stmt;
		ResultSet			rSet;
		
		ArrayList<Object>	resultSet	= new ArrayList<Object>();
		Object				object		= null;
		ArrayList<Integer>	primaryKeys	= new ArrayList<Integer>();
		
		if (con.equals(null)) {
			JOptionPane.showMessageDialog(
					null, "No Connection to DataBase!", "Error:", JOptionPane.ERROR_MESSAGE);
							
			/* DEMO ONLY */
			System.err.println("Error: No Connection to DataBase!");
			
			return null;
		}
		
		try {
			if (!con.isClosed()) {
				stmt = con.createStatement();
										
				rSet = stmt.executeQuery( sqlQuery );
				while (rSet.next()) {
					primaryKeys.add(rSet.getInt(1));				   					
				}
				
				stmt.close();
			} else {
				JOptionPane.showMessageDialog(
						null, "No open Connection!", "Error:", JOptionPane.ERROR_MESSAGE);
									
				/* DEMO ONLY */
				System.err.println("Error: No open Connection!");
				return null;
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(
					null, e.getMessage(), "SQLException:",  JOptionPane.ERROR_MESSAGE);
				  				
			return null;
		}
	   
		Iterator<Integer> iterator_i = primaryKeys.iterator();
		Boolean loadStatus = false;
		while (iterator_i.hasNext()) {
			Integer primaryKey = iterator_i.next(); 
			
			if (classID == EntityType.PROJECTS.getID()) {
				Project project = new Project();
				project.setEntityID(primaryKey);
				
				loadStatus = project.entityLoad(con);
				if (loadStatus) {
					ProContent content = project.getContent();
					if (content != null)
						loadStatus = content.entityLoad(project, con);
				}				
				if (loadStatus)
					object = project;
			}
			if (classID == EntityType.CONCEPTS.getID()) {
				Concept concept = new Concept();
				concept.setEntityID(primaryKey);
				
				loadStatus = concept.entityLoad(con);
				if (loadStatus)
					object = concept;
			}
			if (classID == EntityType.MEASUREMENTS.getID()) {
				Measurement measurement = new Measurement();
				measurement.setEntityID(primaryKey);
				
				loadStatus = measurement.entityLoad(con);
				if (loadStatus)
					object = measurement;
			}
			if (classID == EntityType.VARIABLES.getID()) {
				Variable variable = new Variable();
				variable.setEntityID(primaryKey);
				
				loadStatus = variable.entityLoad(con);
				if (loadStatus)
					object = variable;
			}
			if (classID == EntityType.OPERA_INDICATORS.getID()) {
				OperaIndicator indicator = new OperaIndicator();
				indicator.setEntityID(primaryKey);
				
				loadStatus = indicator.entityLoad(con);
				if (loadStatus)
					object = indicator;
			}
			if (classID == EntityType.CON_DIMENSIONS.getID()) {
				ConDimension dimension = new ConDimension();
				dimension.setEntityID(primaryKey);
				
				loadStatus = dimension.entityLoad(con);
				if (loadStatus)
					object = dimension;
			}

			if (loadStatus)
				resultSet.add(object);
		}
		
		return resultSet;
	}
	
	/**
	 * @param sql
	 * @return
	 */
	public static String searchForProjectName(String sql) {
		java.sql.Statement stmt;
		ResultSet rSet;
		
		ArrayList<String> projectName = new ArrayList<String>();
		
		if (con.equals(null)) {
			JOptionPane.showMessageDialog(
					null, "No Connection to DataBase!", "Error:", JOptionPane.ERROR_MESSAGE);
							
			/* DEMO ONLY */
			System.err.println("Error: No Connection to DataBase!");
			
			return "";
		}
		
		try {
			if (!con.isClosed()) {
				stmt = con.createStatement();
										
				rSet = stmt.executeQuery( sql );
				while (rSet.next()) {
					projectName.add(rSet.getString(1));
				}
				
				stmt.close();
			} else {
				JOptionPane.showMessageDialog(
						null, "No open Connection!", "Error:", JOptionPane.ERROR_MESSAGE);
									
				/* DEMO ONLY */
				System.err.println("Error: No open Connection!");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(
					null, e.getMessage(), "SQLException:",  JOptionPane.ERROR_MESSAGE);
				  	
			/* DEMO ONLY */
			System.err.println("SQLException: " + e.getMessage());
			e.printStackTrace();
		}	   

		if (projectName.size() == 0)
			return "";
		
		return projectName.get(0);
	}
	
	/**
	 * @param tableID
	 * @param tableEntryID
	 * @return
	 */
	public static Object getObject(int tableID, int tableEntryID) {
		Object object = null;
		
		switch (tableID) {
			case  100: /* Project */
				Project project = new Project();
				project.setEntityID(tableEntryID);
				if (project.entityLoad(con))
					object = project;
				break;
			case  110: /* Project Layer */
				WorkStepInstance projectLayer = new WorkStepInstance();
				projectLayer.setEntityID(tableEntryID);
				if (projectLayer.entityLoad(con))
					object = projectLayer;
				break;
			case  350: /* Concept */
				Concept concept = new Concept();
				concept.setEntityID(tableEntryID);
				if (concept.entityLoad(con))
					object = concept;
				break;
			case  500: /* Measurement */
				Measurement measurement = new Measurement();
				measurement.setEntityID(tableEntryID);
				if (measurement.entityLoad(con))
					object = measurement;
				break;
//			case  300: /* Universe */
//				UniverseOld universe = new UniverseOld();
//				universe.setEntityID(tableEntryID);
//				if (universe.entityLoad(con))
//					object = universe;
//				break;
			case  850: /* Question */
				Question question = new Question();
				question.setEntityID(tableEntryID);
				if (question.entityLoad(con))
					object = question;
				break;
			case  610: /* Con Dimension */
				ConDimension conDimension = new ConDimension();
				conDimension.setEntityID(tableEntryID);
				if (conDimension.entityLoad(con))
					object = conDimension;
				break;
			case 9888: /* Category */
				Category category = new Category();
				category.setEntityID(tableEntryID);
				if (category.entityLoad(con))
					object = category;
				break;
			case  720: /* Opera Indicator */
				OperaIndicator operaIndicator = new OperaIndicator();
				operaIndicator.setEntityID(tableEntryID);
				if (operaIndicator.entityLoad(con))
					object = operaIndicator;
				break;
			case  800: /* Variable */
				Variable variable = new Variable();
				variable.setEntityID(tableEntryID);
				if (variable.entityLoad(con))
					object = variable;
				break;	
			
			default:
		}
		
		return object;
	}

	/**
	 * @param sql
	 * @return
	 */
	public static String searchForVariableValues(String sql) {
		String retString = "";
		
		java.sql.Statement stmt;
		ResultSet rSet;
		
		ArrayList<String> variableValues = new ArrayList<String>();
		ArrayList<String> variableLabels = new ArrayList<String>();
		
		if (con.equals(null)) {
			JOptionPane.showMessageDialog(
					null, "No Connection to DataBase!", "Error:", JOptionPane.ERROR_MESSAGE);
							
			/* DEMO ONLY */
			System.err.println("Error: No Connection to DataBase!");
			
			return "";
		}
		
		try {
			if (!con.isClosed()) {
				stmt = con.createStatement();
										
				rSet = stmt.executeQuery( sql );
				while (rSet.next()) {
					variableValues.add(rSet.getString(1));
					variableLabels.add(rSet.getString(2));
				}
				
				stmt.close();
			} else {
				JOptionPane.showMessageDialog(
						null, "No open Connection!", "Error:", JOptionPane.ERROR_MESSAGE);
									
				/* DEMO ONLY */
				System.err.println("Error: No open Connection!");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(
					null, e.getMessage(), "SQLException:",  JOptionPane.ERROR_MESSAGE);
				  	
			/* DEMO ONLY */
			System.err.println("SQLException: " + e.getMessage());
			e.printStackTrace();
		}	   
		
		if (variableValues.size() > 0) {
			retString = "<html><ul>";
			for (int i=0; i<variableValues.size(); i++) {
				retString += "<li>"+ variableValues.get(i) +":"+ variableLabels.get(i) + "<br></li>";
			}
			retString += "</ul></html>"; 
		}
					
		return retString;
	}
	
	/**
	 * @param sql
	 * @return
	 */
	public static String searchForMeasurementCategories(String sql) {
		String retString = "";
		
		java.sql.Statement stmt;
		ResultSet rSet;
		
		ArrayList<String> measurementCategories = new ArrayList<String>();
		ArrayList<String> measurementLabels = new ArrayList<String>();
		
		if (con.equals(null)) {
			JOptionPane.showMessageDialog(
					null, "No Connection to DataBase!", "Error:", JOptionPane.ERROR_MESSAGE);
							
			/* DEMO ONLY */
			System.err.println("Error: No Connection to DataBase!");
			
			return "";
		}
		
		try {
			if (!con.isClosed()) {
				stmt = con.createStatement();
										
				rSet = stmt.executeQuery( sql );
				while (rSet.next()) {
					measurementCategories.add(rSet.getString(1));
					measurementLabels.add(rSet.getString(2));
				}
				
				stmt.close();
			} else {
				JOptionPane.showMessageDialog(
						null, "No open Connection!", "Error:", JOptionPane.ERROR_MESSAGE);
									
				/* DEMO ONLY */
				System.err.println("Error: No open Connection!");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(
					null, e.getMessage(), "SQLException:",  JOptionPane.ERROR_MESSAGE);
				  	
			/* DEMO ONLY */
			System.err.println("SQLException: " + e.getMessage());
			e.printStackTrace();
		}	   
		
		if (measurementCategories.size() > 0) {
			retString = "<html><ul>";
			for (int i=0; i<measurementCategories.size(); i++) {
				retString += "<li>"+ measurementCategories.get(i) +":"+ measurementLabels.get(i) + "<br></li>";
			}
			retString += "</ul></html>"; 
		}
					
		return retString;
	}
	
	/**
	 * @param sql
	 * @return
	 */
	public static String searchForProjectNote(String sql) {
		String retString = "";
		
		java.sql.Statement stmt;
		ResultSet rSet;
		
		Comment note = new Comment();
		
		if (con.equals(null)) {
			JOptionPane.showMessageDialog(
					null, "No Connection to DataBase!", "Error:", JOptionPane.ERROR_MESSAGE);
							
			/* DEMO ONLY */
			System.err.println("Error: No Connection to DataBase!");
			
			return "";
		}
		
		try {
			if (!con.isClosed()) {
				stmt = con.createStatement();
										
				rSet = stmt.executeQuery( sql );
				
				boolean loadStatus = true;
				while (rSet.next()) {					
					note.setEntityID(rSet.getInt("id"));				
					if (loadStatus)
						loadStatus = note.entityLoad(con);
				}
				
				stmt.close();
			} else {
				JOptionPane.showMessageDialog(
						null, "No open Connection!", "Error:", JOptionPane.ERROR_MESSAGE);
									
				/* DEMO ONLY */
				System.err.println("Error: No open Connection!");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(
					null, e.getMessage(), "SQLException:",  JOptionPane.ERROR_MESSAGE);
				  	
			/* DEMO ONLY */
			System.err.println("SQLException: " + e.getMessage());
			e.printStackTrace();
		}	   
		
		if (note instanceof Comment) {
//			retString = "<html>";
//			retString += note.getText();
//			retString += "</html>"; 
			retString = note.getText();
		}
					
		return retString;
	}
	
}

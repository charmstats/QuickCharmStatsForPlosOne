package org.gesis.charmstats.persistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.gesis.charmstats.model.Value;
import org.gesis.charmstats.model.Variable;

/**
 * 
 *	@author Martin Friedrichs
 *	@since	0.9.7
 *
 */
public class StataMetadataImport {

	/**
	 * @param file
	 * @return
	 */
	public List<Variable> getVariables(File file) {
		List<Variable> results = new ArrayList<Variable>();
		
		try {
				// use buffering, reading one line at a time
				// FileReader always assumes default encoding is OK!
				BufferedReader input =  new BufferedReader(new FileReader(file));
		      
				try {
					String line = null; //not declared within while loop
					/*
					 * readLine is a bit quirky :
					 * it returns the content of a line MINUS the newline.
					 * it returns null only for the END of the stream.
					 * it returns an empty String if two newlines appear in a row.
					 */
					while (( line = input.readLine()) != null){
						if (line.contains("{")) {
							/* create variable */
							Variable var = new Variable();
							
							line = line.trim();
							String varName = line.substring(0, line.indexOf(" "));
							String varLabel = line.substring(line.indexOf("{")+1, line.indexOf("}"));
														
							var.setName(varName);
							var.setLabel(varLabel);
							
							results.add(var);
						}
					}
				}
				finally {
					input.close();
				}
		} catch (IOException e){
			e.printStackTrace();
		}
		
		return results;
	}
	
	/**
	 * @param file
	 * @param var
	 * @return
	 */
	public List<Value> getValues(File file, Variable var) {
		List<Value> results = new ArrayList<Value>();
		
		try {
				// use buffering, reading one line at a time
				// FileReader always assumes default encoding is OK!
				BufferedReader input =  new BufferedReader(new FileReader(file));
		      
				try {
					String line = null; //not declared within while loop
					/*
					 * readLine is a bit quirky :
					 * it returns the content of a line MINUS the newline.
					 * it returns null only for the END of the stream.
					 * it returns an empty String if two newlines appear in a row.
					 */
					String valName = null;
					while ((line = input.readLine()) != null) {
						line = line.trim();
						
						if ((line.contains(var.getName())) &&
								(line.contains(var.getLabel()))) {
							String[] parts = line.split("\t");
							
							if (parts.length == 4)
								valName = parts[3];							
						}
						
						if (valName != null)
							break;
					}
					
					if (valName != null) {
						while ((line = input.readLine()) != null) {
							line = line.trim();
							
							if (line.contains(valName)) { // are value labels reused between different variables?
								while ((line = input.readLine()) != null) {
									line = line.trim();
									
									if (line.length() > 0) {
										String valValue = line.substring(0, line.indexOf(" "));
										String valLabel = line.substring(line.indexOf("\"")+1, line.length()-1);
																			
										Value val = new Value();
										val.setValue(valValue);
										val.setLabel(valLabel);
											
										results.add(val);
									} else
										break;
									
								}
							}
							
						}
					}
					
				}
				finally {
					input.close();
				}
		} catch (IOException e){
			e.printStackTrace();
		}
		
		return results;
	}
	
}

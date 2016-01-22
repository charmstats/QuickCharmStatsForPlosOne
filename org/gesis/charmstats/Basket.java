package org.gesis.charmstats;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import javax.swing.JOptionPane;

import org.gesis.charmstats.model.ConDimension;
import org.gesis.charmstats.model.ConDimensionRef;
import org.gesis.charmstats.model.Concept;
import org.gesis.charmstats.model.ConceptRef;
import org.gesis.charmstats.model.Measurement;
import org.gesis.charmstats.model.MeasurementRef;
import org.gesis.charmstats.model.OperaIndicator;
import org.gesis.charmstats.model.OperaIndicatorRef;
import org.gesis.charmstats.model.Project;
import org.gesis.charmstats.model.ProjectRef;
import org.gesis.charmstats.model.Question;
import org.gesis.charmstats.model.QuestionRef;
import org.gesis.charmstats.model.User;
import org.gesis.charmstats.model.Variable;
import org.gesis.charmstats.model.VariableRef;
import org.gesis.charmstats.persistence.DBEntity;
import org.gesis.charmstats.persistence.DBField;
import org.gesis.charmstats.persistence.EntityType;
import org.gesis.charmstats.persistence.RefRelationtype;


/**
 *	This is an Entity Class
 * 
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class Basket extends DBEntity {

	/* charmstats.baskets (MySQL)
		`id`		int(11) 		NOT NULL		auto_increment,
  		`label` 	varchar(255)	default NULL,
		`owner`		int(11)			default NULL,
		`parent`	int(11)			default NULL,
		PRIMARY KEY  (`id`)
	*/

	/*
	 *	Fields
	 */
	private String 				label;
	private User 				owner;
	/* Stored and loaded through the use of the "*Ref" construct: */
	private Basket 				parent;		// Not used yet
	private ArrayList<Basket>	childs;		// Not used yet			 
	private ArrayList<Object>	contents;
	/* Used only at runtime, therefore not to be stored: */
	private boolean 			loadstatus;	
	
	private boolean				isTempBasket = true; 
	
	/**
	 *	Constructor 
	 */
	public Basket() {
		super();
		
		entity_type = EntityType.BASKETS;
		
		setLabel(new String());
		setParent(null);
		setChilds(new ArrayList<Basket>());
		setOwner(null);
		setLoadstatus(false);		
		setContents(new ArrayList<Object>());
	}
	
	/*
	 *	Methods
	 */
	/* Label */
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
	
	/* Owner */
	/**
	 * @param owner
	 */
	public void setOwner(User owner) {
		this.owner = owner;
	}

	/**
	 * @return
	 */
	public User getOwner() {
		return owner;
	}

	/* Parent */
	/**
	 * @param parent
	 */
	public void setParent(Basket parent) {
		this.parent = parent;
	}

	/**
	 * @return
	 */
	public Basket getParent() {
		return parent;
	}

	/* Childs */
	/**
	 * @param childs
	 */
	public void setChilds(ArrayList<Basket> childs) {	
		this.childs = childs;
	}

	/**
	 * @param child
	 */
	public void addChild(Basket child) {
		try {
			childs.add(child);
		} catch (Exception e) {
			System.err.println(e.getMessage()); 
		}
	}
	
	/**
	 * @return
	 */
	public ArrayList<Basket> getChilds() {
		return childs;
	}
		
	/**
	 * @param child
	 * @return
	 */
	public boolean removeChild(Basket child) {
		return childs.remove(child);
	}
	
	/**
	 * 
	 */
	public void clearChilds() {
		childs.clear();
	}
	
	/* loadstatus */
	/**
	 * @param loadstatus
	 */
	public void setLoadstatus(boolean loadstatus) {
		this.loadstatus = loadstatus;
	}

	/**
	 * @return
	 */
	public boolean isLoadstatus() {
		return loadstatus;
	}

	/* contents */
	/**
	 * @param contents
	 */
	public void setContents(ArrayList<Object> contents) {
		this.contents = contents;
	}

	/**
	 * @param content
	 */
	public void addContent(Object content) {
		this.contents.add(content);
	}

	/**
	 * @return
	 */
	public ArrayList<Object> getContents() {
		return contents;
	}
	
	/**
	 * @return
	 */
	public ArrayList<Object> getStoredObjects() {
		ArrayList<Object> storedObjects = getContentsWithoutType(Search.class);
		
		return storedObjects;
	}
	
	/**
	 * @return
	 */
	public ArrayList<Object> getSearchResults() {
		ArrayList<Object> searchResults = getContentsByType(Search.class);
		
		return searchResults;
	}
		
	/**
	 * @param classType
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public ArrayList<Object> getContentsWithoutType(Class classType) {
		ArrayList<Object> foundObjects = new ArrayList<Object>();
		
		if (contents != null) {
			Iterator<Object> iterator = contents.iterator();
							
			while(iterator.hasNext()) {
				Object object = iterator.next();
					
				if (!object.getClass().equals(classType))
					foundObjects.add(object);
			}
		}
	
		return foundObjects;
	}
	
	/**
	 * @param classType
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public ArrayList<Object> getContentsByType(Class classType) {
		ArrayList<Object> foundObjects = new ArrayList<Object>();
		
		if (contents != null) {
			Iterator<Object> iterator = contents.iterator();
							
			while(iterator.hasNext()) {
				Object object = iterator.next();
					
				if (object.getClass().equals(classType))
					foundObjects.add(object);
			}
		}
	
		return foundObjects;
	}
	
	/**
	 * @param content
	 * @return
	 */
	public boolean removeContent(Object content) {
		Iterator<Object> it_obj = contents.iterator();
		while (it_obj.hasNext()) {
			Object obj = it_obj.next();
			
			if ( (((DBEntity)obj).getEntityType().equals(((DBEntity)content).getEntityType())) &&
					(((DBEntity)obj).getEntityID() == ((DBEntity)content).getEntityID()) )
				return contents.remove(obj);	
		}
				
		return false;	
	}
	
	/**
	 * @param obj_class
	 * @param id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public boolean removeContentByClassAndID(Class obj_class, int id) {
		Iterator<Object> iter = contents.iterator();
		
		while (iter.hasNext()) {
			Object obj = iter.next();
			
			if ((obj.getClass().equals(obj_class)) &&
					(((DBEntity)obj).getEntityID()== id)) {
				return contents.remove(obj);
			}
		}
		
		return true;
	}
	
	/**
	 * 
	 */
	public void clearContents() {
		contents.clear();
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
			if (!connection.isClosed()) {
				stmt = connection.createStatement();
					
				String sqlQuery = "SELECT label " +
					"FROM baskets " +
					"WHERE id=\"" + this.getEntityID() + "\"";
					
				rSet = stmt.executeQuery( sqlQuery );
					
				while (rSet.next()) {
					this.setLabel(rSet.getString("label"));
					
					loadStatus = true;
				}
				
				/*
				 * parent_basket
				 */
				BasketRef ref = new BasketRef();
				ref.setReferenced(new Basket()); 
				ref.setBasket(this);
				loadStatus = ref.entityLoad(connection);
				
				if (loadStatus) {
					Basket parentBasket = new Basket();
					parentBasket.setEntityID(ref.getReferencedID());
					loadStatus = parentBasket.entityLoad(connection);
					if (loadStatus)
						this.setParent(parentBasket);	
				}
				
				/*
				 * sub_baskets
				 */
				ref = new BasketRef();
				ref.setReferenced(this);
				ArrayList<Integer> subBasketIDs = ref.loadReferences(connection);
				if (subBasketIDs != null) {
					Iterator<Integer> iterator = subBasketIDs.iterator();
									
					while(iterator.hasNext()) {
						Integer id = iterator.next();

						Basket subBasket = new Basket(); 
						subBasket.setEntityID(id);
						loadStatus = subBasket.entityLoad(connection);
						if (loadStatus)
							this.addChild(subBasket);
					}
				}
				
				/*
				 * owner
				 */
				ref = new BasketRef();
				ref.setReferenced(new User());
				ref.setBasket(this);
				loadStatus = ref.entityLoad(connection);
				
				if (loadStatus) {
					User owner = new User();
					ref.getReferencedEntityTypeID();
					owner.setEntityID(ref.getReferencedID());
					loadStatus = owner.entityLoad(connection);
					if (loadStatus)
						this.setOwner(owner);	
				}
				
				/*
				 * contents
				 */
				ProjectRef proRef = new ProjectRef();
				proRef.setReferenced(this);
				ArrayList<Integer> projectIDs = proRef.loadReferences(connection);
				if (projectIDs != null) {
					Iterator<Integer> iterator = projectIDs.iterator();
									
					while(iterator.hasNext()) {
						Integer id = iterator.next();
						
						Project project = new Project(); 
						project.setEntityID(id);
						loadStatus = project.entityLoad(connection);
						if (loadStatus)
							this.addContent(project);
					}
				}
				
				ConceptRef conRef = new ConceptRef();
				conRef.setReferenced(this);
				ArrayList<Integer> conceptIDs = conRef.loadReferences(connection);
				if (conceptIDs != null) {
					Iterator<Integer> iterator = conceptIDs.iterator();
									
					while(iterator.hasNext()) {
						Integer id = iterator.next();

						Concept concept = new Concept(); 
						concept.setEntityID(id);
						loadStatus = concept.entityLoad(connection);
						if (loadStatus)
							this.addContent(concept);
					}
				}
				
				MeasurementRef meaRef = new MeasurementRef();
				meaRef.setReferenced(this);
				ArrayList<Integer> measurementIDs = meaRef.loadReferences(connection);
				ArrayList<Measurement> measurements = new ArrayList<Measurement>(); 
				if (measurementIDs != null) {
					Iterator<Integer> iterator = measurementIDs.iterator();
									
					while(iterator.hasNext()) {
						Integer id = iterator.next();

						Measurement measurement = new Measurement(); 
						measurement.setEntityID(id);
						loadStatus = measurement.entityLoad(connection);
						if (loadStatus)
							measurements.add(measurement); 
					}
				}
				if (measurements != null) {
					Collections.sort(measurements); 
					
					Iterator<Measurement> iterator = measurements.iterator();								
					while(iterator.hasNext())
						this.addContent(iterator.next()); 
				}				
								
				QuestionRef queRef = new QuestionRef();
				queRef.setReferenced(this);
				ArrayList<Integer> questionIDs = queRef.loadReferences(connection);
				if (questionIDs != null) {
					Iterator<Integer> iterator = questionIDs.iterator();
									
					while(iterator.hasNext()) {
						Integer id = iterator.next();

						Question question = new Question(); 
						question.setEntityID(id);
						loadStatus = question.entityLoad(connection);
						if (loadStatus)
							this.addContent(question);
					}
				}
				
				VariableRef varRef = new VariableRef();
				varRef.setReferenced(this);
				ArrayList<Integer> variableIDs = varRef.loadReferences(connection);
				ArrayList<Variable> variables = new ArrayList<Variable>(); 
				if (variableIDs != null) {
					Iterator<Integer> iterator = variableIDs.iterator();
									
					while(iterator.hasNext()) {
						Integer id = iterator.next();

						Variable variable = new Variable(); 
						variable.setEntityID(id);
						loadStatus = variable.entityLoad(connection);
						if (loadStatus)
							variables.add(variable); 
					}
				}

				if (variables != null) {
					Collections.sort(variables);
					
					Iterator<Variable> iterator = variables.iterator();									
					while(iterator.hasNext())
						this.addContent(iterator.next()); 
				}
				
				OperaIndicatorRef o_iRef = new OperaIndicatorRef();
				o_iRef.setReferenced(this);
				ArrayList<Integer> indicatorIDs = o_iRef.loadReferences(connection);
				if (indicatorIDs != null) {
					Iterator<Integer> iterator = indicatorIDs.iterator();
									
					while(iterator.hasNext()) {
						Integer id = iterator.next();

						OperaIndicator indicator = new OperaIndicator(); 
						indicator.setEntityID(id);
						loadStatus = indicator.entityLoad(connection);
						if (loadStatus)
							this.addContent(indicator);
					}
				}
				ConDimensionRef c_dRef = new ConDimensionRef();
				c_dRef.setReferenced(this);
				ArrayList<Integer> dimensionIDs = c_dRef.loadReferences(connection);
				if (dimensionIDs != null) {
					Iterator<Integer> iterator = dimensionIDs.iterator();
									
					while(iterator.hasNext()) {
						Integer id = iterator.next();

						ConDimension dimension = new ConDimension(); 
						dimension.setEntityID(id);
						loadStatus = dimension.entityLoad(connection);
						if (loadStatus)
							this.addContent(dimension);
					}
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
		
		/*
		 *	Insert
		 */
		try {
			connection.setAutoCommit(false);
			
			if (storeStatus) {
				/*
				 *	Use Insert-Statement
				 */		
				sqlQuery = "INSERT INTO baskets (label, owner";
				sqlQuery += ") VALUES ("
					+ "'"	+ DBField.BAS_LAB.truncate(this.getLabel()) + "',"
					+ "'"	+ getOwner().getEntityID() + "'";
				sqlQuery += ")";
				
				stmt = connection.createStatement();
				rows = stmt.executeUpdate(sqlQuery);
				
				if (rows < 1)
					storeStatus = false;
				else {					
				    rsltst = stmt.getGeneratedKeys();
	
				    if (rsltst.next()) {
				        this.setEntityID(rsltst.getInt(1));
				    } else {
				    	storeStatus = false;
				    }
				    rsltst.close();
				}
			
				stmt.close();
								
				/*
				 *	Content
				 */
				ArrayList<Object> contents = this.getContents();
				
				if (contents != null) {
					Iterator<Object> iterator = contents.iterator();
				
					while(iterator.hasNext()) {
						Object content = iterator.next();
					
						if (storeStatus) {
							if (content.getClass().equals(Project.class)) {
								ProjectRef proRef = new ProjectRef();
								proRef.setProject((Project)content);
								proRef.setReferenced(this);
								proRef.setRefRelationtype(RefRelationtype.ASSIGNED);
								
								storeStatus = proRef.entityStore(connection);
							}
							if (content.getClass().equals(Concept.class)) {
								ConceptRef conRef = new ConceptRef();
								conRef.setConcept((Concept)content);
								conRef.setReferenced(this);
								conRef.setRefRelationtype(RefRelationtype.ASSIGNED);
								
								storeStatus = conRef.entityStore(connection);
							}
							if (content.getClass().equals(Measurement.class)) {
								MeasurementRef meaRef = new MeasurementRef();
								meaRef.setMeasurement((Measurement)content);
								meaRef.setReferenced(this);
								meaRef.setRefRelationtype(RefRelationtype.ASSIGNED);
								
								storeStatus = meaRef.entityStore(connection);
							}
							if (content.getClass().equals(Question.class)) {
								QuestionRef queRef = new QuestionRef();
								queRef.setQuestion((Question)content);
								queRef.setReferenced(this);
								queRef.setRefRelationtype(RefRelationtype.ASSIGNED);
								
								storeStatus = queRef.entityStore(connection);
							}
							if (content.getClass().equals(Variable.class)) {
								VariableRef varRef = new VariableRef();
								varRef.setVariable((Variable)content);
								varRef.setReferenced(this);
								varRef.setRefRelationtype(RefRelationtype.ASSIGNED);
								
								storeStatus = varRef.entityStore(connection);
							}
							if (content.getClass().equals(OperaIndicator.class)) {
								OperaIndicatorRef o_iRef = new OperaIndicatorRef();
								o_iRef.setOperaIndicator((OperaIndicator)content);
								o_iRef.setReferenced(this);
								o_iRef.setRefRelationtype(RefRelationtype.ASSIGNED);
								
								storeStatus = o_iRef.entityStore(connection);
							}
							if (content.getClass().equals(ConDimension.class)) {
								ConDimensionRef c_dRef = new ConDimensionRef();
								c_dRef.setConDimension((ConDimension)content);
								c_dRef.setReferenced(this);
								c_dRef.setRefRelationtype(RefRelationtype.ASSIGNED);
								
								storeStatus = c_dRef.entityStore(connection);
							}
						}
						
						if (storeStatus) {
							BasketRef ref = new BasketRef();
							ref.setReferenced(content);
							ref.setBasket(this);
							ref.setRefRelationtype(RefRelationtype.ASSIGNED);
							storeStatus = ref.entityStore(connection);
						}
					}
				}
					
				/*
				 *	Loop SubBasket
				 */
				ArrayList<Basket> subBaskets = this.getChilds();
				
				if (subBaskets != null) {
					Iterator<Basket> iterator = subBaskets.iterator();
				
					while(iterator.hasNext()) {
						Basket basket = iterator.next();
					
						if (storeStatus)
							storeStatus = basket.entityStore(connection);
					}
				}
				
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
				sqlQuery = "DELETE from baskets " +
					"WHERE id = " + this.getEntityID();
				
				stmt = connection.createStatement();
				rows = stmt.executeUpdate(sqlQuery);
				
				if (rows < 1)				
					removeStatus = false;
				
				stmt.close();
				
				if (removeStatus) {
					BasketRef basRef = new BasketRef();
					basRef.setBasket(this);
					
					removeStatus = basRef.entityRemove(connection);
				}
				
				/*
				 *	Content
				 */
				ArrayList<Object> contents = this.getContents();
				
				if (contents != null) {
					Iterator<Object> iterator = contents.iterator();
				
					while(iterator.hasNext()) {
						Object content = iterator.next();
					
						if (removeStatus) {
							if (content.getClass().equals(Project.class)) {
								ProjectRef proRef = new ProjectRef();
								proRef.setProject((Project)content);
								proRef.setReferenced(this);
								
								removeStatus = proRef.entityRemoveSingle(connection);
							}
							if (content.getClass().equals(Concept.class)) {
								ConceptRef conRef = new ConceptRef();
								conRef.setConcept((Concept)content);
								conRef.setReferenced(this);
								
								removeStatus = conRef.entityRemoveSingle(connection);
							}
							if (content.getClass().equals(Measurement.class)) {
								MeasurementRef meaRef = new MeasurementRef();
								meaRef.setMeasurement((Measurement)content);
								meaRef.setReferenced(this);
								
								removeStatus = meaRef.entityRemoveSingle(connection);
							}
							if (content.getClass().equals(Question.class)) {
								QuestionRef queRef = new QuestionRef();
								queRef.setQuestion((Question)content);
								queRef.setReferenced(this);
								
								removeStatus = queRef.entityRemoveSingle(connection);
							}
							if (content.getClass().equals(Variable.class)) {
								VariableRef varRef = new VariableRef();
								varRef.setVariable((Variable)content);
								varRef.setReferenced(this);
								
								removeStatus = varRef.entityRemoveSingle(connection);
							}
							if (content.getClass().equals(OperaIndicator.class)) {
								OperaIndicatorRef o_iRef = new OperaIndicatorRef();
								o_iRef.setOperaIndicator((OperaIndicator)content);
								o_iRef.setReferenced(this);
								
								removeStatus = o_iRef.entityRemoveSingle(connection);
							}
							if (content.getClass().equals(ConDimension.class)) {
								ConDimensionRef c_dRef = new ConDimensionRef();
								c_dRef.setConDimension((ConDimension)content);
								c_dRef.setReferenced(this);
								
								removeStatus = c_dRef.entityRemoveSingle(connection);
							}
						}
					}
				}
				
				/*
				 *	Loop SubBasket
				 */
				ArrayList<Basket> subBaskets = this.getChilds();
				
				if (subBaskets != null) {
					Iterator<Basket> iterator = subBaskets.iterator();
				
					while(iterator.hasNext()) {
						Basket basket = iterator.next();
					
						if (removeStatus)
							removeStatus = basket.entityRemove(connection);
					}
				}
				
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
    
	public boolean isTempBasket() {
		return isTempBasket;
	}

	public void setTempBasket(boolean isTempBasket) {
		this.isTempBasket = isTempBasket;
	}
}

package org.gesis.charmstats.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.Locale;
import java.util.Observable;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.gesis.charmstats.ActionCommandID;
import org.gesis.charmstats.Basket;
import org.gesis.charmstats.IdentifiedParameter;
import org.gesis.charmstats.model.Attributes;
import org.gesis.charmstats.model.CStatsModel;
import org.gesis.charmstats.model.ConDimension;
import org.gesis.charmstats.model.Description;
import org.gesis.charmstats.model.Label;
import org.gesis.charmstats.model.Measurement;
import org.gesis.charmstats.model.OperaIndicator;
import org.gesis.charmstats.model.Project;
import org.gesis.charmstats.model.Variable;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class BasketFrame extends InternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
//	/* RESOURCES */
//	public static final String CONTROL_BOX_ICON	= "org/gesis/charmstats/Resources/basket.GIF";
	
	public static final String BUNDLE			= "org.gesis.charmstats.resources.DesktopBundle";
	public static final String BUNDLE_MODEL		= "org.gesis.charmstats.resources.ModelBundle"; 
	public static final String TITLE			= "basket_frame_title";
	
	public static final String BASKET_RN_LBL	= "basket_rn_lbl";
	public static final String BASKET_NO_RESULT	= "basket_no_result";
	public static final String BASKET_NO_ITEM	= "basket_no_item";
	
	public static final String BASKET_TEMP		= "basket_temp";
	public static final String BASKET_OWN		= "basket_own";
	
	public static final String FB_MEASUREMENT	= "fb_measurement";
	public static final String FB_TEMPLATE_Q	= "fb_template_q";
	public static final String FB_YES			= "fb1_yes";
	public static final String FB_NO			= "fb_no";
	public static final String FB_VARIABLE		= "fb_variable";
	public static final String FB_DEFINITION	= "fb_definition";
	public static final String FB_LEVEL			= "fb_level";
	public static final String FB_SOURCE		= "fb_source";
	public static final String FB_KIND			= "fb_kind";
	
	public static final String FP_TARGET_VARIABLE		= "fp_target_var";
	public static final String FP_DEFINITION			= "fp_definition";
	public static final String FP_CONCEPT				= "fp_concept";

	
	/*
	 *	Fields
	 */
	JTree					basketTree;
	DefaultMutableTreeNode	rootNodeBask		= null;
	Basket 					basket;
	 
	private DefaultMutableTreeNode NO_SEARCH;
	private DefaultMutableTreeNode NO_STORED_ITEM;
	
	private String prefix;
		
	Object localeAddenda;
	
	ResourceBundle			modelBundle; 
	
	/*
	 *	Constructor
	 */
	/**
	 * @param dimension
	 * @param location
	 * @param visible
	 * @param resizable
	 * @param maximizable
	 * @param iconifiable
	 * @param closable
	 * @param locale
	 * @param al
	 * @param addenda
	 */
	public BasketFrame(
			Dimension dimension,
			Point location,
			boolean visible, 
			boolean resizable,
			boolean maximizable,
			boolean iconifiable,
			boolean closable,
			Locale locale,
			ActionListener al,
			Object addenda) {
		super(dimension, location, visible, resizable, maximizable, iconifiable, closable);
		
		localeAddenda = addenda;
			    
		currentLocale	= locale;
		resourceBundle	= ResourceBundle.getBundle(BUNDLE, currentLocale);
		
		modelBundle		= ResourceBundle.getBundle(BUNDLE_MODEL, currentLocale); 
		
		setTitle(resourceBundle.getString(TITLE));
		setTemp();
		
		ImageIcon icon = createImageIcon(CStatsGUI.BASKET_ICON,"");
		setFrameIcon(icon);
		
		rootNodeBask = new DefaultMutableTreeNode("<html><body><h1 style=\"font-size:100%\">"+ resourceBundle.getString(prefix) +  resourceBundle.getString(BASKET_RN_LBL)+"</h1></body></html>");
		basketTree = new JTree(rootNodeBask) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			/* (non-Javadoc)
			 * @see javax.swing.JTree#getToolTipText(java.awt.event.MouseEvent)
			 */
			public String getToolTipText(MouseEvent evt) {
				if (getRowForLocation(evt.getX(), evt.getY()) == -1)
		            return null;
				
		        TreePath tp = getPathForLocation(evt.getX(), evt.getY());
	    		DefaultMutableTreeNode node = null;
	    		if (tp != null)
		    		node = (DefaultMutableTreeNode)tp.getLastPathComponent();
	    		
	    		Attributes attr = null;
	    		if (node != null) {
	    			if (node.getUserObject() != null) {
	    				if (node.getUserObject() instanceof Project) {
	    					String tooltip = "";
	    					
	    					Project prj = (Project)node.getUserObject();
	    					
	    					String prjTitel = prj.getName();
	    					StringBuilder sb = new StringBuilder(prj.getSummary().getText());

	    					int i = 0;
	    					while (i + 80 < sb.length() && (i = sb.lastIndexOf(" ", i + 80)) != -1) {
	    					    sb.replace(i, i + 1, "\n<BR>");
	    					}
	    					
	    					String prjAbstract = resourceBundle.getString(FP_DEFINITION)+ ":<BR><I>"+ sb.toString() +"</I>";

	    					String varName = prj.getTargetName();
	    					String varLabel = prj.getTargetLabel();
	    					
	    					String conName = "-";
	    					if (prj.getConcept().getDefaultLabel() instanceof Label )
	    						conName = prj.getConcept().getDefaultLabel().getLabel().getTextualContent();
	    					String conDefinition = "-";
	    					if (prj.getConcept().getDefaultDescription() instanceof Description) {
	    						sb = new StringBuilder(prj.getConcept().getDefaultDescription().getDescription().getTextualContent());

	    						i = 0;
	    						while (i + 80 < sb.length() && (i = sb.lastIndexOf(" ", i + 80)) != -1) {
	    							sb.replace(i, i + 1, "\n<BR>");
	    						}
	    					
	    						conDefinition = "<BR><I>"+ sb.toString() +"</I>";
	    					}

	    					
	    					tooltip = "<html><B>"+prjTitel+"</B><BR>"+ prjAbstract +"<hr />"+ resourceBundle.getString(FP_TARGET_VARIABLE)+varName +" / \""+ varLabel +"\"<hr />"+ resourceBundle.getString(FP_CONCEPT) +" \""+ conName +"\""+ conDefinition +"</html>";
	    					
	    					return tooltip;
	    				}	    				
	    				
	    				if (node.getUserObject() instanceof Attributes) {
	    					attr = (Attributes)node.getUserObject();
	    				}
	    				
	    				if (attr instanceof Measurement) {
	    					Measurement mea = (Measurement)attr;	    					
	    					
	    					String tooltip = "";
	    					tooltip = "<html>"+ resourceBundle.getString(FB_MEASUREMENT) +"<B>"+ mea.getName() +"</B> / \""+ mea.getLabel() +"\""; 
	    					tooltip += "<BR>"+ resourceBundle.getString(FB_TEMPLATE_Q) + (mea.getIsTemplateImported() > 0 ? "<B>"+resourceBundle.getString(FB_YES)+"</B>": "<B>"+resourceBundle.getString(FB_NO)+"</B>");
	    					tooltip += "<BR><hr />"+ resourceBundle.getString(FB_LEVEL) +": <B>"+ modelBundle.getString(mea.getLevel().getLabel()) +"</B>";
	    					tooltip += "<BR>"+ resourceBundle.getString(FB_KIND) +": <B>"+ modelBundle.getString(mea.getKind().getLabel()) +"</B>";
	    					tooltip += "<BR>"+ resourceBundle.getString(FB_SOURCE) +": <B>"+ modelBundle.getString(mea.getSource().getLabel()) +"</B>";

	    					StringBuilder sb = new StringBuilder(mea.getDefinition().getText());

	    					int i = 0;
	    					while (i + 80 < sb.length() && (i = sb.lastIndexOf(" ", i + 80)) != -1) {
	    					    sb.replace(i, i + 1, "\n<BR>");
	    					}
	    					
	    					tooltip += "<BR><hr />"+ resourceBundle.getString(FB_DEFINITION) +":<BR><I>"+ sb.toString() +"</I>";
	    					tooltip += "</html>";
	    					
	    					return tooltip;
	    				}
	    				if (attr instanceof ConDimension) {
	    					return "Dimension";
	    				}
	    				if (attr instanceof OperaIndicator) {
	    					return "Blueprint";
	    				}
	    				if (attr instanceof Variable) {
	    					Variable var = (Variable)attr;
	    						    					
	    					String tooltip = "";
	    					tooltip = "<html>"+ resourceBundle.getString(FB_VARIABLE) +"<B>"+ var.getName() +"</B> / \""+ var.getLabel() +"\""; 
	    					tooltip += "<BR><hr />"+ resourceBundle.getString(FB_LEVEL) +": <B>"+ modelBundle.getString(var.getLevel().getLabel()) +"</B>";
	    					tooltip += "<BR>"+ resourceBundle.getString(FB_KIND) +": <B>"+ modelBundle.getString(var.getMeasureType().getLabel()) +"</B>";;

	    					StringBuilder sb = new StringBuilder(var.getDefinition().getText());

	    					int i = 0;
	    					while (i + 80 < sb.length() && (i = sb.lastIndexOf(" ", i + 80)) != -1) {
	    					    sb.replace(i, i + 1, "\n<BR>");
	    					}
	    					
	    					tooltip += "<BR><hr />"+ resourceBundle.getString(FB_DEFINITION) +":<BR><I>"+ sb.toString() +"</I>";
	    					tooltip += "</html>";
	    					
	    					return tooltip;
	    				}
	    			}
	    		}
	    		
				return "";
			}
		};
		ToolTipManager.sharedInstance().registerComponent(basketTree);
		
        JScrollPane basketScrollPane = new JScrollPane(basketTree);
        basketScrollPane.getVerticalScrollBar().setUnitIncrement(16);
		setContentPane(basketScrollPane);
		buildBasketTree(resourceBundle, addenda);
		
	}
	
	/*
	 *	Methods
	 */
	/**
	 * @param locale
	 */
	protected void changeLanguage(Locale locale) {
		resourceBundle	= ResourceBundle.getBundle(BUNDLE, locale);		
		currentLocale	= locale;
		
		setTitle(resourceBundle.getString(TITLE));
		rootNodeBask.setUserObject("<html><body><h1 style=\"font-size:100%\">"+ resourceBundle.getString(prefix) +  resourceBundle.getString(BASKET_RN_LBL)+"</h1></body></html>");
		
		buildBasketTree(resourceBundle, localeAddenda);
	}
	
	/**
	 * @param f
	 */
	protected void changeFont(Font f) {
		UIManager.put("InternalFrame.titleFont", f);
		javax.swing.SwingUtilities.updateComponentTreeUI(this);
		
		basketTree.setFont(f);
	}
	
	/**
	 * 
	 */
	public void setOwn() {
		prefix = BASKET_OWN;
		
		if (basket != null)
			basket.setTempBasket(false);
	}
	
	/**
	 * 
	 */
	public void setTemp() {
		prefix = BASKET_TEMP;
		
		if (basket != null)
			basket.setTempBasket(true);
	}
	
	/*
	 *	FrameBasket.update handles the update of the basket tree!
	 */
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.InternalFrame#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		IdentifiedParameter parameter = null;
		
		if (arg instanceof IdentifiedParameter) {
			parameter = (IdentifiedParameter)arg;
		}
		
		if (parameter != null) {
			Object[]	addenda		= parameter.getParameters();
			
			switch (parameter.getID()) {
				case ActionCommandID.MOD_MOD:
					break;
				case ActionCommandID.ADD_TO_BASKET:
					(((CStatsModel)addenda[0]).getUser()).getBasket().setLoadstatus(true);
			        buildBasketTree(resourceBundle, addenda[0]);
   				    
				    basketTree.setModel(new DefaultTreeModel(rootNodeBask));
				    for (int i = 0; i < basketTree.getRowCount(); i++) {
				         basketTree.expandRow(i);
				    }
					break;
				case ActionCommandID.CMD_USER_LOGIN:
					closeBasket(resourceBundle); 
					break;
				case ActionCommandID.CMD_USER_LOGOFF:
					closeBasket(resourceBundle);
					break;
				case ActionCommandID.CMD_BSKT_NEW_BASKET:
					break;
				case ActionCommandID.CMD_BSKT_OPEN_BASKET:
					openBasket(resourceBundle, addenda[0]);
					break;
				case ActionCommandID.CMD_BSKT_CLOSE:
					closeBasket(resourceBundle);
					changeLanguage(currentLocale);
					break;
				case ActionCommandID.CMD_BSKT_CLOSE_ALL:
					break;
				case ActionCommandID.CMD_BSKT_SAVE:
					break;
				case ActionCommandID.CMD_BSKT_SAVE_AS:
					break;
				case ActionCommandID.CMD_BSKT_SAVE_ALL:
					break;
				case ActionCommandID.CMD_BSKT_EMPTY:
					emptyBasket(resourceBundle);
					break;
				case ActionCommandID.CMD_BSKT_EMPTY_TEMP_BASKET:
					emptyBasket(resourceBundle);
					break;
				case ActionCommandID.CMD_EXTRA_UNFINISH:
					changeLanguage(currentLocale);
					break;
				default:
					break;
			}			
		}			
	}
	
	/**
	 * @param bundle
	 */
	private void emptyBasket(ResourceBundle bundle) {
		rootNodeBask.removeAllChildren();
		buildClearBasketTree(bundle);
		
	    basketTree.setModel(new DefaultTreeModel(rootNodeBask));
	    for (int i = 0; i < basketTree.getRowCount(); i++) {
	         basketTree.expandRow(i);
	    }
	}
	
	/**
	 * @param bundle
	 */
	private void closeBasket(ResourceBundle bundle) {
		setTemp();
		
		rootNodeBask.removeAllChildren();
		((DefaultTreeModel)basketTree.getModel()).reload();
		
		buildClearBasketTree(bundle);
		basketTree.setModel(new DefaultTreeModel(rootNodeBask));
	    
	    for (int i = 0; i < basketTree.getRowCount(); i++) {
	         basketTree.expandRow(i);
	    }
	    
	    changeLanguage(currentLocale);
	}

	/**
	 * @param bundle
	 * @param addenda
	 */
	private void buildBasketTree(ResourceBundle bundle, Object addenda) {
		rootNodeBask.removeAllChildren();

//		fillSearch(bundle, addenda);
	    fillStoredObjects(bundle, addenda);
	    
	    ((DefaultTreeModel)basketTree.getModel()).reload();
	    
	    for (int i=0; i<basketTree.getRowCount(); i++) {
	         basketTree.expandRow(i);
	    }
	    

	}
	
	/**
	 * @param bundle
	 * @param addenda
	 */
	@SuppressWarnings("unused")
	private void fillSearch(ResourceBundle bundle, Object addenda) {
	    DefaultMutableTreeNode categorie = null;
	    DefaultMutableTreeNode item = null;   
	    
	    Basket basket = (((CStatsModel)addenda).getUser()).getBasket();
	    
	    categorie =  new DefaultMutableTreeNode(bundle.getString("search"));
	    rootNodeBask.add(categorie);
	    
	    NO_SEARCH = new DefaultMutableTreeNode(bundle.getString(BASKET_NO_RESULT));

		if (basket.getSearchResults() != null) {
			Iterator<Object> iterator = basket.getSearchResults().iterator(); 
					
			if (basket.getSearchResults().size() < 1) {
				item = this.NO_SEARCH;
				categorie.add(item);
			}
			
			while (iterator.hasNext()) {
				Object search = iterator.next();
				
				item = new DefaultMutableTreeNode(search);
				categorie.add(item);
			}
		} else {
			item = this.NO_SEARCH;
			categorie.add(item);			
		}
	}
	
	/**
	 * @param bundle
	 * @param addenda
	 */
	private void fillStoredObjects(ResourceBundle bundle, Object addenda) {
	    DefaultMutableTreeNode categorie = null;
	    DefaultMutableTreeNode item = null;   

	    Basket basket = (((CStatsModel)addenda).getUser()).getBasket();
	    
	    categorie =  new DefaultMutableTreeNode(bundle.getString("stored_object_s"));
	    rootNodeBask.add(categorie); 
	    
	    NO_STORED_ITEM = new DefaultMutableTreeNode(bundle.getString(BASKET_NO_ITEM));

		if (basket.getStoredObjects() != null) {
			Iterator<Object> iterator = basket.getStoredObjects().iterator(); 
					
			if (basket.getStoredObjects().size() < 1) {
				item = this.NO_STORED_ITEM;
				categorie.add(item);
			}
			
			while (iterator.hasNext()) {
				Object object = iterator.next();
				
				item = new DefaultMutableTreeNode(object);
				categorie.add(item);
			}
		} else {
			item = this.NO_STORED_ITEM;
			categorie.add(item);			
		}
	}
	
	/**
	 * @param bundle
	 */
	private void buildClearBasketTree(ResourceBundle bundle) {
		rootNodeBask.removeAllChildren();

//	    DefaultMutableTreeNode categorie_search = null;
//	    DefaultMutableTreeNode item_search = null;   

//	    categorie_search =  new DefaultMutableTreeNode("Search");
//	    rootNodeBask.add(categorie_search); 
//
//	    NO_SEARCH = new DefaultMutableTreeNode(bundle.getString(BASKET_NO_RESULT));
//	    
//		item_search = this.NO_SEARCH;
//		categorie_search.add(item_search);
	    
	    DefaultMutableTreeNode categorie_stored = null;
	    DefaultMutableTreeNode item_stored = null;   

	    categorie_stored =  new DefaultMutableTreeNode(bundle.getString("stored_object_s"));
	    rootNodeBask.add(categorie_stored); 

	    NO_STORED_ITEM = new DefaultMutableTreeNode(bundle.getString(BASKET_NO_ITEM));
	    
		item_stored = this.NO_STORED_ITEM;
		categorie_stored.add(item_stored);
					
	    for (int i=0; i<basketTree.getRowCount(); i++) {
	         basketTree.expandRow(i);
	    }
	}
	
	/**
	 * @param bundle
	 * @param addenda
	 */
	private void openBasket(ResourceBundle bundle, Object addenda) {
		setOwn();
		
		buildBasketTree(bundle, addenda);
		
	    basketTree.setModel(new DefaultTreeModel(rootNodeBask));
	    for (int i = 0; i < basketTree.getRowCount(); i++) {
	         basketTree.expandRow(i);
	    }
	    
	    changeLanguage(currentLocale);
	}
	
	protected static ImageIcon createImageIcon(String resourceURL,
			String description) {
		java.net.URL imgURL = ToolBar.class.getClassLoader().getResource(resourceURL);
		if (imgURL != null) {
	   		return new ImageIcon(imgURL, description);
	   	} else {
	   		System.err.println("Couldn't find file: " + resourceURL);
	   		return null;
	   	}
	}
	
}

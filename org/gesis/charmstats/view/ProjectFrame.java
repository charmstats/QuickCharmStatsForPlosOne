package org.gesis.charmstats.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.Locale;
import java.util.Observable;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.gesis.charmstats.ActionCommandID;
import org.gesis.charmstats.ActionCommandText;
import org.gesis.charmstats.IdentifiedParameter;
import org.gesis.charmstats.model.CStatsModel;
import org.gesis.charmstats.model.Description;
import org.gesis.charmstats.model.Label;
import org.gesis.charmstats.model.Project;
import org.gesis.charmstats.model.User;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class ProjectFrame extends InternalFrame {

//	/* RESOURCES */
//	public static final String CONTROL_BOX_ICON	= "org/gesis/charmstats/Resources/project.GIF";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String BUNDLE	= "org.gesis.charmstats.resources.DesktopBundle";
	public static final String TITLE	= "project_frame_title";
	
	public static final String ALL_PROJECTS_RN_LBL		= "all_projects_rn_lbl";
	public static final String CLOSED_PROJECT_N_LBL		= "closed_project_n_lbl";
	public static final String OPEN_PROJECT_N_LBL		= "open_project_n_lbl";
	public static final String FP_TARGET_VARIABLE		= "fp_target_var";
	public static final String FP_DEFINITION			= "fp_definition";
	public static final String FP_CONCEPT				= "fp_concept";

	public static final String NONE						= "none";
	
	/*
	 *	Fields
	 */
	JTree					projectTree;
	DefaultMutableTreeNode	rootNodeProj	= null;
	
	private DefaultMutableTreeNode NO_UNFIN_PROJECT;
	private DefaultMutableTreeNode NO_FIN_PROJECT;
	
	Object localeAddenda;
	public Object selectedProject;
	
	JButton	openProjectBtn;
	
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
	public ProjectFrame(
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
		
		setTitle(resourceBundle.getString(TITLE));
		
		ImageIcon icon = createImageIcon(CStatsGUI.PROJECT_ICON,"");
		setFrameIcon(icon);
		
		openProjectBtn = new JButton();
		openProjectBtn.setActionCommand(ActionCommandText.BTN_FRM_PRJ_OPN_PRJ);
		openProjectBtn.addActionListener(al);
		openProjectBtn.setVisible(false);
		openProjectBtn.setEnabled(true);
		
		rootNodeProj = new DefaultMutableTreeNode("<html><body><h1 style=\"font-size:100%\">"+ resourceBundle.getString(ALL_PROJECTS_RN_LBL) +"</h1></body></html>");
		projectTree = new JTree(rootNodeProj) {
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
				
				String tooltip = null;
				
		        TreePath tp = getPathForLocation(evt.getX(), evt.getY());
	    		DefaultMutableTreeNode node = null;
	    		if (tp != null)
		    		node = (DefaultMutableTreeNode)tp.getLastPathComponent();
	    		
	    		if (node != null) {
	    			if (node.getUserObject() != null) {
	    				if (node.getUserObject() instanceof Project) {
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
	    					
	    				}
	    			}
	    		}
	    		
				return tooltip;
			}
		};
		ToolTipManager.sharedInstance().registerComponent(projectTree);
	    projectTree.addMouseListener(
	    		new MouseAdapter() {
	    			/* (non-Javadoc)
	    			 * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
	    			 */
	    			public void mouseReleased(MouseEvent e) {
	    				if (e.isPopupTrigger()) {
	    					int selRow = projectTree.getRowForLocation(e.getX(), e.getY());
	    					TreePath selPath = projectTree.getPathForLocation(e.getX(), e.getY());
	    					if (selRow != -1) {
	    						DefaultMutableTreeNode node = 
	    							((DefaultMutableTreeNode)selPath.getLastPathComponent());
	    						Object userObject = 
	    							((DefaultMutableTreeNode)selPath.getLastPathComponent()).getUserObject();
						
	    						if (node.isLeaf() && (userObject.getClass() != String.class)) {
	    							selectedProject = userObject;
	    						}
	    					}
	    				}
	    			}
	    			/* (non-Javadoc)
	    			 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
	    			 */
	    			public void mouseClicked(MouseEvent e) {
	    				if (e.getClickCount() == 2) {
	    					int selRow = projectTree.getRowForLocation(e.getX(), e.getY());
	    					TreePath selPath = projectTree.getPathForLocation(e.getX(), e.getY());
	    					if (selRow != -1) {
	    						DefaultMutableTreeNode node = 
	    							((DefaultMutableTreeNode)selPath.getLastPathComponent());
	    						Object userObject = 
	    							((DefaultMutableTreeNode)selPath.getLastPathComponent()).getUserObject();
						
	    						if (node.isLeaf() && (userObject.getClass() != String.class)) {
	    							selectedProject = userObject;
	    						}
	    					}
	    					openProjectBtn.doClick();
	    				} else {
	    					// DoNothing
	    				}
	    			}

	    		}
	    );
		
        JScrollPane projectScrollPane = new JScrollPane(projectTree);
        projectScrollPane.getVerticalScrollBar().setUnitIncrement(16);
		setContentPane(projectScrollPane);
		buildProjectsTree(resourceBundle, addenda);
	}

	/*
	 *	Methods
	 */
	/**
	 * @param locale
	 */
	protected void changeLanguage(Locale locale) {
		ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, locale);
		
		setTitle(bundle.getString(TITLE));
		rootNodeProj.setUserObject("<html><body><h1 style=\"font-size:100%\">"+ bundle.getString(ALL_PROJECTS_RN_LBL) +"</h1></body></html>");
				
		buildProjectsTree(bundle, localeAddenda);
	}
	
	/**
	 * @param f
	 */
	protected void changeFont(Font f) {
		UIManager.put("InternalFrame.titleFont", f);
		javax.swing.SwingUtilities.updateComponentTreeUI(this);
		
		projectTree.setFont(f);
	}
	
	/*
	 *	FrameProject.update handles the update of the project tree!
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
			
			@SuppressWarnings("unused")
			CStatsModel model		= (CStatsModel)addenda[0];
			
			switch (parameter.getID()) {
				case ActionCommandID.CMD_PRJ_NEW_PROJECT:
				case ActionCommandID.BTN_PRJ_STP_PRJ_TAB_NEXT:
					currentLocale	= (Locale)addenda[1];
					changeLanguage(currentLocale);
				case ActionCommandID.MOD_MOD:
					break;
				case ActionCommandID.CMD_PRJ_FINISH:
					changeLanguage(currentLocale);
					break;
				case ActionCommandID.CMD_PRJ_REMOVE:
					changeLanguage(currentLocale);
					break;
				case ActionCommandID.CMD_USER_LOGIN:
					currentLocale	= (Locale)addenda[1];
					changeLanguage(currentLocale);
					break;
				case ActionCommandID.CMD_USER_LOGOFF:
					currentLocale	= (Locale)addenda[1];
					changeLanguage(currentLocale);
					break;
				default:
					break;
			}			
		}	
		
	}
	
	/**
	 * @param bundle
	 * @param addenda
	 */
	private void buildProjectsTree(ResourceBundle bundle, Object addenda) {
		rootNodeProj.removeAllChildren();
		
		fillUnpublProjects(bundle, addenda);
		fillPublProjects(bundle, addenda);
		
	    ((DefaultTreeModel)projectTree.getModel()).reload();
		
	    for (int i=0; i<projectTree.getRowCount(); i++) {
	         projectTree.expandRow(i);
	    }   
	}
	
	/* Unpublished */
	/**
	 * @param bundle
	 * @param addenda
	 */
	private void fillUnpublProjects(ResourceBundle bundle, Object addenda) {
	    DefaultMutableTreeNode categorie = null;
	    DefaultMutableTreeNode item = null;
	    
	    User user =  ((CStatsModel)addenda).getUser();

	    categorie =  new DefaultMutableTreeNode(bundle.getString(OPEN_PROJECT_N_LBL));
	    rootNodeProj.add(categorie); 
	    
	    NO_UNFIN_PROJECT = new DefaultMutableTreeNode(bundle.getString(NONE));
	    
		if (user.getUnfinProjects() != null) {
			Iterator<Project> iterator = user.getUnfinProjects().iterator(); 
					
			if (user.getUnfinProjects().size() < 1) {
				item = NO_UNFIN_PROJECT;
				categorie.add(item);
			}
			
			while (iterator.hasNext()) {
				Project project = iterator.next();
				
				item = new DefaultMutableTreeNode(project);
				categorie.add(item);
			}
		} else {
			item = NO_UNFIN_PROJECT;
			categorie.add(item);			
		}
	}
	
	/* Published */
	/**
	 * @param bundle
	 * @param addenda
	 */
	private void fillPublProjects(ResourceBundle bundle, Object addenda) {
	    DefaultMutableTreeNode categorie = null;
	    DefaultMutableTreeNode item = null;
	    
	    User user =  ((CStatsModel)addenda).getUser();

	    categorie = new DefaultMutableTreeNode(bundle.getString(CLOSED_PROJECT_N_LBL));
	    rootNodeProj.add(categorie);
	    
	    NO_FIN_PROJECT = new DefaultMutableTreeNode(bundle.getString(NONE));
	    
		if (user.getFinProjects() != null) {
			Iterator<Project> iterator = user.getFinProjects().iterator();
		
			if (user.getFinProjects().size() < 1) {
				item = NO_FIN_PROJECT;
				categorie.add(item);
			}
			
			while (iterator.hasNext()) {
				Project project = iterator.next();
				
				item = new DefaultMutableTreeNode(project);
				categorie.add(item); 
			}
		} else {
			item = NO_FIN_PROJECT;
			categorie.add(item);			
		}
	}
	
	/**
	 * @return
	 */
	public Project getSelectedProject() {
		return (Project)selectedProject;
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

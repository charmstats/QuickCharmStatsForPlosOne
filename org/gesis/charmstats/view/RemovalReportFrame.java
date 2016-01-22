package org.gesis.charmstats.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

import org.gesis.charmstats.RemovalStatistics;
import org.gesis.charmstats.model.Attributes;
import org.gesis.charmstats.model.Measurement;
import org.gesis.charmstats.model.User;
import org.gesis.charmstats.model.Variable;

public class RemovalReportFrame extends InternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
//	/* RESOURCES */
//	public static final String CONTROL_BOX_ICON	= "org/gesis/charmstats/Resources/report.GIF";
	
	public static final String BUNDLE_R	= "org.gesis.charmstats.resources.ReportBundle";
	public static final String TITLE	= "report_frame_title";
	public static final String TEMPLATE	= "removal_template"; 
	public static final String PRINT	= "report_print";
	public static final String SAVE		= "report_save";
	public static final String PROJECT	= "report_project";
	public static final String IMPORT	= "report_import";
	public static final String DEFAULT	= "report_default";
	public static final String EDIT		= "report_edit";
	
	public static final String AUTHOR	= "report_author";
	public static final String EDITOR	= "report_editor";
	
	private static final String BUNDLE_F	= "org.gesis.charmstats.resources.FrameBundle";
	private static final String FILE_NAME	= "file_name";
	
	public static final String NO_PRJ_NAME = "";
	public static final String EMPTY = "";
	
	/*
	 *	Fields
	 */
	JPanel			form;
	JEditorPane		reportPane;
	JPanel			buttonPanel				= new JPanel();
	JButton			printBtn;
	JButton			saveBtn;
	JButton			editBtn;
	
	Object			localeAddenda;
	
	String 			template;
	
	static MessageFormat head = new MessageFormat("");
    static MessageFormat foot = new MessageFormat("");
    
    RemovalStatistics	statistics;
    
    Connection		_connection;
	
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
	public RemovalReportFrame(
			Dimension dimension,
			Point location,
			boolean visible, 
			boolean resizable,
			boolean maximizable,
			boolean iconifiable,
			boolean closable,
			Locale locale,
			ActionListener al,
			Connection con,
			Object addenda) {
		super(dimension, location, visible, resizable, maximizable, iconifiable, closable);

		_connection = con;
		
		localeAddenda	= addenda;
		
		currentLocale	= locale;
		resourceBundle	= ResourceBundle.getBundle(BUNDLE_R, currentLocale);
		
		setTitle(resourceBundle.getString(TITLE));
		
		ImageIcon icon = createImageIcon(CStatsGUI.REPORT_ICON,"");
		setFrameIcon(icon);
		
		form		= new JPanel();
		form.setLayout(new BorderLayout());
		
	    reportPane	= new JEditorPane();
	    reportPane.setEditable(false);
	    reportPane.setContentType("text/html");
	    
		/* Build Button Panel */
		printBtn	= new JButton(resourceBundle.getString(PRINT));
        printBtn.addActionListener(
        		new java.awt.event.ActionListener() {
        			/* (non-Javadoc)
        			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
        			 */
        			public void actionPerformed(java.awt.event.ActionEvent evt) {
        				filePrint();
        			}
        		}
        );
		saveBtn		= new JButton(resourceBundle.getString(SAVE));
//		saveBtn.setActionCommand(ActionCommandText.BTN_FRM_IMP_REP_SAVE_REPORT);
//        saveBtn.addActionListener(al);
   		saveBtn.addActionListener(
   			new java.awt.event.ActionListener() {
			/* (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				saveReport();
			}
   		}
   		);
		editBtn		= new JButton(resourceBundle.getString(EDIT));
        editBtn.addActionListener(
        		new java.awt.event.ActionListener() {
        			/* (non-Javadoc)
        			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
        			 */
        			public void actionPerformed(java.awt.event.ActionEvent evt) {
        				editReport();
        			}
        		}
        );
        buttonPanel.add(editBtn);
		buttonPanel.add(printBtn);
		buttonPanel.add(saveBtn);
		
        JScrollPane reportScrollPane = new JScrollPane(reportPane);
        reportScrollPane.getVerticalScrollBar().setUnitIncrement(16);
	    form.add(reportScrollPane, BorderLayout.CENTER);
	    form.add(buttonPanel, BorderLayout.SOUTH);
	    
	    setContentPane(form);
	    
	    buildReport(resourceBundle, addenda);
	}

	/*
	 *	Methods
	 */
	
	/**
	 * @param locale
	 */
	protected void changeLanguage(Locale locale) {
		ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_R, locale);
		
		currentLocale = locale;
		resourceBundle = bundle;
		
		setTitle(bundle.getString(TITLE));
		buildReport(bundle, localeAddenda);
		editBtn.setText(resourceBundle.getString(EDIT));
		printBtn.setText(resourceBundle.getString(PRINT));
 		saveBtn.setText(resourceBundle.getString(SAVE));
	}
	
	/**
	 * @param f
	 */
	protected void changeFont(Font f) {
		UIManager.put("InternalFrame.titleFont", f);
		javax.swing.SwingUtilities.updateComponentTreeUI(this);
		
		editBtn.setFont(f);		
		printBtn.setFont(f);
 		saveBtn.setFont(f);
	}
	
	/**
	 * @param resourceBundle
	 * @param addenda
	 */
	private void buildReport(ResourceBundle resourceBundle, Object addenda) {
		statistics	= (RemovalStatistics)addenda;
		
		template = resourceBundle.getString(TEMPLATE);
			
		template = insertUserName(template, statistics.getRemovingAdmin());
		template = insertDateOfRemoval(template, statistics.getRemovalDate());
			
		template = insertListOfSelections(template, statistics.getSelection());
		template = insertListOfRemovedOnes(template, statistics.getRemoved());
		template = insertListOfUnhandled(template, statistics.getRefused());
		template = insertListOfExcludedOnes(template, statistics.getExcluded());
			
		reportPane.setContentType("text/html");
					
		String fullTemplate = template;
		reportPane.setText(fullTemplate);
		reportPane.setCaretPosition(0); 
	}
	
	/**
	 * @param template
	 * @param user
	 * @return
	 */
	private String insertUserName(String template, User user) {
		String userName = user.getName();
		
		return template.replaceAll("#USER#", (userName.isEmpty() ? "-" : userName));
	}
	
	private String insertDateOfRemoval(String template, Timestamp importDate) {
		String timeFormat = "MM/dd/yyyy"; // default
		
		String iso3Locale = currentLocale.getISO3Language();
		
		String iso3Ger = Locale.GERMAN.getISO3Language();
		String iso3Eng = Locale.US.getISO3Language();
		
		if (iso3Locale.equals(iso3Ger))
			timeFormat = "dd.MM.yyyy HH:mm:ss";
		if (iso3Locale.equals(iso3Eng))
			timeFormat = "MM/dd/yyyy HH:mm:ss";
		
		String timeString = new SimpleDateFormat(timeFormat).format(importDate);
		
		return template.replaceAll("#DATE_OF_REMOVAL#", timeString);
	}
		
	/**
	 * @param template
	 * @return
	 */
	private String insertListOfSelections(String template, List<Attributes> attributes) {
		String attributeList = getFormatedAttributeList(attributes);
		
		return template.replaceAll("#LIST_OF_SELECTIONS#", (attributeList.isEmpty() ? "-" : attributeList)); 
	}
	
	private String insertListOfRemovedOnes(String template, List<Attributes> attributes) {
		String attributeList = getFormatedAttributeList(attributes);
		
		return template.replaceAll("#LIST_OF_REMOVED_ONES#", (attributeList.isEmpty() ? "-" : attributeList)); 
	}
	
	private String insertListOfUnhandled(String template, List<Attributes> attributes) {
		String attributeList = getFormatedAttributeList(attributes);
		
		return template.replaceAll("#LIST_OF_UNHANDLED#", (attributeList.isEmpty() ? "-" : attributeList)); 
	}
	
	private String insertListOfExcludedOnes(String template, List<Attributes> attributes) {
		String attributeList = getFormatedAttributeList(attributes);
		
		return template.replaceAll("#LIST_OF_EXCLUDED_ONES#", (attributeList.isEmpty() ? "-" : attributeList)); 
	}
		
	private String getFormatedAttributeList(List<Attributes> attributes) {
		String attributeList = new String();
		
		Iterator<Attributes> attrIterator = attributes.iterator();		
		while (attrIterator.hasNext()) {
			Attributes attr = attrIterator.next();
			
			if (attr instanceof Variable) {
				Variable var = (Variable)attr;
				
				attributeList += var.getName() +" - "+ var.getLabel() +"<BR>\n";
			}
			if (attr instanceof Measurement) {
				Measurement mea = (Measurement)attr;
				
				attributeList += mea.getName() +" - "+ mea.getLabel() +"<BR>\n";
			}
			
		}
		return attributeList; 
	}
		
	/**
	 * 
	 */
	protected void filePrint() { 
		
		print(this.reportPane);
	}
	
	/**
	 * @param pane
	 */
	protected void print(JEditorPane pane) {
		/* TODO How to change the printDialog-Language? */
		
		PrinterJob pj = PrinterJob.getPrinterJob();
		if(pj.printDialog()) {
			try {
				pj.setPrintable(pane.getPrintable(head, foot));
	            pj.print();
			} catch (PrinterException e) {
				JOptionPane.showMessageDialog(
						null, e.getMessage(), "PrinterException:",  JOptionPane.ERROR_MESSAGE);
					  	
				/* DEMO ONLY */
				System.err.println("PrinterException: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @return
	 */
	protected boolean saveReport() {
		SyntaxFilter fSyntaxFilter = new SyntaxFilter (); 
		File fFile = new File ("report.html"); 
  
		JComponent.setDefaultLocale(currentLocale);
		
	    JFileChooser fc = new JFileChooser (); 

	    // Start in current directory
//	    fc.setCurrentDirectory (new File ("."));
	    
	    ResourceBundle	resourceBundle	= ResourceBundle.getBundle(BUNDLE_F, currentLocale);
		String			file_name		= resourceBundle.getString(FILE_NAME);
	    
		String path_0 = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		path_0 = path_0.replace("/", "\\\\"); // use File.separator in CharmStatsPro
		path_0 = path_0.replace(file_name, ""); // filename stored in resource FrameBundle
		path_0 = path_0 + "reports\\\\removings\\\\";
//	    fc.setCurrentDirectory (new File (path_0));
					
//		System.out.println(path_0);
		
		File dir = new File (path_0);
		if (!dir.exists()) {
			try{
			    if(dir.mkdirs()) {
			    	dir = new File (path_0);
			    } else {
			        dir = new File (path_0);
			    }
			} catch(Exception e){
			    e.printStackTrace();
			} 
		}
		dir = new File (path_0);
		
//		System.out.println(dir.getAbsolutePath());
		
		fc.setCurrentDirectory (dir);
		
	    // Set filter for Java source files.
	    fc.setFileFilter (fSyntaxFilter); 

	    // Set to a default name for save.
	    fc.setSelectedFile (fFile); 

	    // Open chooser dialog
	    int result = fc.showSaveDialog(this);

	    if (result == JFileChooser.CANCEL_OPTION) {
	    	return true;
	    } else if (result == JFileChooser.APPROVE_OPTION) {
	    	fFile = fc.getSelectedFile ();
	        if (fFile.exists ()) {
	        	int response = JOptionPane.showConfirmDialog (null,
	        			"Overwrite existing file?","Confirm Overwrite",
	        			JOptionPane.OK_CANCEL_OPTION,
	        			JOptionPane.QUESTION_MESSAGE);
	        	if (response == JOptionPane.CANCEL_OPTION) return false;
	        }
	        return writeFile (fFile, this.reportPane.getText());
	    } else {
	    	return false;
	    }
	}

	private class SyntaxFilter extends javax.swing.filechooser.FileFilter
	{
		/* (non-Javadoc)
		 * @see javax.swing.filechooser.FileFilter#accept(java.io.File)
		 */
		public boolean accept (File f) {
			return f.getName().toLowerCase().endsWith(".html") || f.isDirectory();
		}
	 
		/* (non-Javadoc)
		 * @see javax.swing.filechooser.FileFilter#getDescription()
		 */
		public String getDescription () {
			return "HTML files (*.html)";
		}
	}	
	
	/**
	 * 
	 */
	protected void editReport() {
		reportPane.setEditable(true);
	}
			
	/**
	 * @param file
	 * @param dataString
	 * @return
	 */
	private /*static*/ boolean writeFile (File file, String dataString) {
		try {
			PrintWriter out = new PrintWriter (file);
			
			out.print (dataString);
			out.flush ();
			out.close ();
		 } catch (IOException e) {
			JOptionPane.showMessageDialog(
					null, e.getMessage(), "IOException:",  JOptionPane.ERROR_MESSAGE);
					  	
			/* DEMO ONLY */
			System.err.println("IOException: " + e.getMessage());
			e.printStackTrace();
			 
			return false;
		}
		return true;
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


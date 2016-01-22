package org.gesis.charmstats.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JViewport;

import java.awt.event.FocusListener;



import org.gesis.charmstats.model.CStatsModel;
import org.gesis.charmstats.model.Comment;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class Tab extends JPanel implements FocusListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String BUNDLE	= "org.gesis.charmstats.resources.DesktopBundle";
	
	public static final String T_BACK	= "back";
	public static final String T_NOTE	= "comment";
	public static final String T_IMPORT	= "import";
	public static final String T_NEXT	= "next";
	public static final String T_CONFIRM	= "confirm";
	public static final String T_READY	= "ready";
	public static final String T_RESET	= "reset";
	public static final String T_AUTO_C	= "autocomplete";
	
	/*
	 *	Fields 
	 */
	Locale 		  	currentLocale;
	ResourceBundle	resourceBundle;
	
	JScrollPane		scrollPane;
	JPanel			formPanel;
	
	JPanel 			buttonPanel;
	JPanel			leftButtonPanel;
	JPanel			middleButtonPanel;
	JPanel			rightButtonPanel;
	JButton 		backButton;
	JButton			autoCompleteButton;
	JButton			resetButton;
	JButton			noteButton;
	JButton			importButton;
	JButton			confirmButton;
	JButton			nextButton;
	JButton			readyButton;
	
	int				panelIdx;
	
	private Comment	comment;
	
	/*
	 *	Constructor
	 */
	/**
	 * @param locale
	 */
	public Tab(Locale locale) {
		super();
		
		setLayout(new BorderLayout());
		
		formPanel = new JPanel();
		formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
		
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(formPanel);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());
		leftButtonPanel = new JPanel();
		backButton = new JButton();
		resetButton = new JButton();
		resetButton.setEnabled(false);
		resetButton.setVisible(false); 
		leftButtonPanel.add(backButton);
		leftButtonPanel.add(resetButton);
		middleButtonPanel = new JPanel();
		autoCompleteButton = new JButton();
		autoCompleteButton.setVisible(false);
		autoCompleteButton.setEnabled(false);
		noteButton = new JButton();
		importButton = new JButton();
		importButton.setVisible(false);
		importButton.setEnabled(false);
		middleButtonPanel.add(importButton);	
		middleButtonPanel.add(noteButton);
		middleButtonPanel.add(autoCompleteButton);
		rightButtonPanel = new JPanel();
		confirmButton = new JButton();
		confirmButton.setVisible(false);
		confirmButton.setEnabled(false);
		nextButton = new JButton();
		readyButton = new JButton();
		readyButton.setVisible(false);
		readyButton.setEnabled(false);
		rightButtonPanel.add(confirmButton);
		rightButtonPanel.add(nextButton);
		rightButtonPanel.add(readyButton);
		buttonPanel.add(leftButtonPanel, BorderLayout.WEST);
		buttonPanel.add(middleButtonPanel, BorderLayout.CENTER);
		buttonPanel.add(rightButtonPanel, BorderLayout.EAST);		
		
		add(scrollPane, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
	}
	
	/*
	 *	Methods
	 */
	/**
	 * @param locale
	 */
	public void changeLanguage(Locale locale) {
		ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, locale);
		
		backButton.setText(bundle.getString(T_BACK));
		resetButton.setText(bundle.getString(T_RESET));
		autoCompleteButton.setText(bundle.getString(T_AUTO_C));
		noteButton.setText(bundle.getString(T_NOTE));
		importButton.setText(bundle.getString(T_IMPORT));
		confirmButton.setText(bundle.getString(T_CONFIRM));
		nextButton.setText(bundle.getString(T_NEXT));
		readyButton.setText(bundle.getString(T_READY));
	}
	
	/**
	 * @param f
	 */
	public void changeFont(Font f) {
		backButton.setFont(f);
		resetButton.setFont(f);
		autoCompleteButton.setFont(f);
		noteButton.setFont(f);
		importButton.setFont(f);
		confirmButton.setFont(f);
		nextButton.setFont(f);
		readyButton.setFont(f);
	}
	
	/**
	 * @return
	 */
	public HashMap<String, Component> buildHashMap() {
		HashMap<String, Component> hashmap = new HashMap<String, Component>();
		
		handleContainer(this, hashmap);
				
		return hashmap;
	}
		
	/**
	 * @return
	 */
	public int getPanelIdx() {
		return panelIdx;
	}
	
	/**
	 * @param idx
	 * @return
	 */
	protected int setPanelIdx(int idx) {
		return panelIdx = idx;
	}
	
	/**
	 * @param container
	 * @param hashmap
	 */
	private void handleContainer(Container container, HashMap<String, Component> hashmap) {
		
		Component component = null;
		for (int i=0; i<container.getComponentCount(); i++) {
			component = (Component)container.getComponent(i);
			
			if ((component instanceof JPanel) ||
					(component instanceof JScrollPane) ||
					(component instanceof JViewport) ||
					(component instanceof JSplitPane) ||
					(component instanceof JTabbedPane))
				handleContainer((Container)component, hashmap);
			else if (component.getName() != null) {
				hashmap.put(component.getName(), component);
			}
		}		
	}
	
	/**
	 * @param defaults
	 */
	public void setDefaults(Object defaults) {
		
	}

	/**
	 * @return
	 */
	public Comment getComment() {
		return comment;
	}

	/**
	 * @param comment
	 */
	public void setComment(Comment comment) {
		this.comment = comment;
	}
	
	/**
	 * @param model
	 */
	public void fillModel(CStatsModel model) {
		
	}
	
	/**
	 * @param enable
	 */
	public void enableElements(boolean enable) {

	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
	 */
	@Override
	public void focusGained(FocusEvent e) {
		if(e.getSource() instanceof JTextField) {
			((JTextField)e.getSource()).setBackground(Color.yellow);
		}
		if(e.getSource() instanceof JTextArea) {
			((JTextArea)e.getSource()).setBackground(Color.yellow);
		}
		if(e.getSource() instanceof JComboBox) {
			((JComboBox)e.getSource()).setBackground(Color.yellow);
		}
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
	 */
	@Override
	public void focusLost(FocusEvent e) {
		if(e.getSource() instanceof JTextField) {
			((JTextField)e.getSource()).setBackground(Color.white);
		}
		if(e.getSource() instanceof JTextArea) {
			((JTextArea)e.getSource()).setBackground(Color.white);
		}
		if(e.getSource() instanceof JComboBox) {
			((JComboBox)e.getSource()).setBackground(Color.white);
		}
	}
}

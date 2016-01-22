package org.gesis.charmstats.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;

import org.gesis.charmstats.ActionCommandText;
import org.gesis.charmstats.model.CStatsModel;
import org.gesis.charmstats.model.Literature;
import org.gesis.charmstats.model.Person;
import org.gesis.charmstats.model.Project;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1 / CharmStatsPro only
 *
 */
public class TabLiterature extends Tab implements FocusListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String TL_INVISIBLE_STRING	= "        ";
	public static final String TL_ADD_REFERENCE	= "add_reference";
	
	/*
	 *	Fields
	 */
	JTabbedPane	jTabbedPane;
	JPanel		jTabbedPaneControlsPane;
	JButton		addTabBtn;
	
	Font		currentFont;
	
	boolean 	enabled;
	
	int			maxLiteratureCounter;
	int			actLiteratureCounter;
	
	/*
	 *	Constructor
	 */
	/**
	 * @param locale
	 */
	public TabLiterature(Locale locale) {
		super(locale);
		
		setName("TabLiterature");
	}
	
	/**
	 * @param al
	 * @param locale
	 * @param addenda
	 */
	public TabLiterature(ActionListener al, Locale locale, Object addenda) {
		this(locale);
		
		currentLocale	= locale;
		resourceBundle	= ResourceBundle.getBundle(BUNDLE, currentLocale);
		currentFont		= this.getFont();
		
		/* Create Form Components */
		maxLiteratureCounter = 0;
		actLiteratureCounter = 0;
		
		jTabbedPane = new JTabbedPane();
		
		enabled = true;
	
		if (addenda instanceof Project) {
			
			enabled = !(((Project) addenda).getFinishedSince() != null);
			
//			ArrayList<Literature> literatures = ((Project) addenda).getContent().getLiteratures();
			ArrayList<Literature> literatures = ((Project) addenda).getConcept().getLiteratures();
			
			Iterator<Literature> it_lit = literatures.iterator();			
			while (it_lit.hasNext()) {
				Literature lit = it_lit.next();
				
				addLiteratureTab(TL_INVISIBLE_STRING, lit, currentLocale, enabled);
			}
			
			if (literatures.size() == 0)
				addLiteratureTab(TL_INVISIBLE_STRING, null, currentLocale, enabled);
				
		} else
			addLiteratureTab(TL_INVISIBLE_STRING, null, currentLocale, enabled);
		
		jTabbedPaneControlsPane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		addTabBtn = new JButton(resourceBundle.getString(TL_ADD_REFERENCE));
		addTabBtn.setFont(currentFont);
		addTabBtn.addMouseListener(addLiteratureMouseListener);
		addTabBtn.setEnabled(enabled);
		jTabbedPaneControlsPane.add(addTabBtn);
				
		/* Add Form Components to Form Panel */
		formPanel.add(jTabbedPane);
		formPanel.add(jTabbedPaneControlsPane);
		
		/* Setup Buttons */
		backButton.setActionCommand(ActionCommandText.BTN_PRJ_STP_LIT_TAB_BACK);
		backButton.addActionListener(al);
		noteButton.setActionCommand(ActionCommandText.BTN_PRJ_STP_LIT_TAB_NOTE);
		noteButton.addActionListener(al);
		nextButton.setActionCommand(ActionCommandText.BTN_PRJ_STP_LIT_TAB_NEXT);
		nextButton.addActionListener(al);
		
		setPanelIdx(0);
		
		changeLanguage(locale);
	}

	/*
	 *	Methods
	 */
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.Tab#changeLanguage(java.util.Locale)
	 */
	public void changeLanguage(Locale locale) {
		super.changeLanguage(locale);
		
		ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, locale);		
		resourceBundle = bundle;
		currentLocale = locale;
		
		for (int i=0; i<jTabbedPane.getTabCount(); i++) {
			((LiteratureDialog)jTabbedPane.getComponentAt(i)).changeLanguage(locale);
		}
		
		addTabBtn.setText(bundle.getString(TL_ADD_REFERENCE));
		
	}
	
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.Tab#changeFont(java.awt.Font)
	 */
	public void changeFont(Font f) {
		super.changeFont(f);
		
		currentFont = f;
		
		for (int i=0; i<jTabbedPane.getTabCount(); i++) {
			((LiteratureDialog)jTabbedPane.getComponentAt(i)).changeFont(f);
		}
		
		addTabBtn.setFont(f);
	}

	MouseListener addLiteratureMouseListener = new MouseAdapter() {
		/* (non-Javadoc)
		 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
		 */
		public void mouseClicked(MouseEvent mouseEvent) {
			
			addLiteratureTab(TL_INVISIBLE_STRING, null, currentLocale, true);							
        }
	};

	MouseListener delLiteratureMouseListener = new MouseAdapter() {
		/* (non-Javadoc)
		 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
		 */
		public void mouseClicked(MouseEvent mouseEvent) {
			
			if (actLiteratureCounter > 1) {
				actLiteratureCounter--;
			
				deleteLiteratureTab(mouseEvent.getComponent().getParent());
			} else 
			if (actLiteratureCounter == 1) {
				
				deleteLiteratureTab(mouseEvent.getComponent().getParent());
				addLiteratureTab(TL_INVISIBLE_STRING, null, currentLocale, true);
			}				
        }
	};
	
	/**
	 * @param tabName
	 * @param literature
	 * @param locale
	 * @param enabled
	 */
	private void addLiteratureTab(String tabName, Literature literature, Locale locale, boolean enabled) {
		
		jTabbedPane.addTab(tabName, new LiteratureDialog(Integer.toString(maxLiteratureCounter), literature, locale, enabled));
		jTabbedPane.setSelectedIndex(jTabbedPane.getComponentCount()-1);		
		
		maxLiteratureCounter++;
		actLiteratureCounter++;
	}
		
	/**
	 * @param buttonPanel
	 */
	private void deleteLiteratureTab(Container buttonPanel) {
		
		Container literatureDialog	= null;
		Container tabbedPane		= null;
		
		if (buttonPanel != null)
			literatureDialog	= buttonPanel.getParent();
		
		if (literatureDialog != null)
			tabbedPane			= literatureDialog.getParent();
		
		if (tabbedPane instanceof JTabbedPane) {
			((JTabbedPane)tabbedPane).remove(literatureDialog);
		}		
	}
	
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.Tab#focusGained(java.awt.event.FocusEvent)
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
	 * @see org.gesis.charmstats.view.Tab#focusLost(java.awt.event.FocusEvent)
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
		
	class LiteratureDialog extends JPanel implements FocusListener {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		public static final String TL_LITERATURE			= "Literature";		
		public static final String TL_AUTHOR_LBL 			= "author_lbl";
		public static final String TL_AUTHOR				= "Author";
		public static final String TL_ET_AL					= "EtAl_";
		public static final String TL_ET_AL_LBL				= "et_al_lbl";
		public static final String TL_DATE_LBL				= "date_lbl";
		public static final String TL_DATE 					= "Date";
		public static final String TL_TITLE_LBL				= "title_lbl";
		public static final String TL_TITLE 				= "Title";
		public static final String TL_SOURCE_LBL			= "source_lbl";
		public static final String TL_SOURCE 				= "Source";
		public static final String TL_EDITOR_LBL 			= "editor_lbl";
		public static final String TL_EDITOR				= "Editor";
		public static final String TL_PUBLISHER_LBL			= "publisher_lbl";
		public static final String TL_PUBLISHER				= "Publisher";
		public static final String TL_PLACE_LBL				= "place_lbl";
		public static final String TL_PLACE					= "Place";
		public static final String TL_ISSUE_LBL				= "issue_lbl";
		public static final String TL_ISSUE 				= "Issue";
		public static final String TL_PAGES_LBL				= "pages_lbl";
		public static final String TL_PAGES 				= "Pages";
		public static final String TL_WEBADDRESS_LBL		= "webaddress_lbl";
		public static final String TL_WEBADDRESS 			= "Webaddress";		
		public static final String TL_DEL_REFERENCE 		= "del_reference";
		public static final String TL_NAME 					= "Name_";
		public static final String TL_NAME_LBL				= "name_lbl";
			
		/*
		 *	Fields
		 */
		JPanel		authorPane;
		JPanel		authorArea;
		JPanel		author1stPane;
		JLabel		author1stLabel;
		JTextField	author1stTF;
		JButton		del1stAuthorBtn;
		JButton		add1stAuthorBtn;
		JPanel		author2ndPane;
		JLabel		author2ndLabel;
		JTextField	author2ndTF;
		JButton		del2ndAuthorBtn;
		JButton		add2ndAuthorBtn;
		JPanel		author3rdPane;
		JLabel		author3rdLabel;
		JTextField	author3rdTF;
		JButton		del3rdAuthorBtn;	
		JButton		add3rdAuthorBtn;
		JPanel		etalAuthorPane;
		JCheckBox	etalAuthorChkBox;
		int			maxAuthorCounter;
		int			actAuthorCounter;
		
		JPanel		upperTextControlsPane;
		
		JPanel		datePane;
		JLabel		dateLbl;
		JFormattedTextField dateTF;
		
		JPanel		titlePane;
		JLabel		titleLbl;
		JScrollPane titleScrollPane;
		JTextArea	titleTA;
		
		JPanel		sourcePane;
		JLabel		sourceLbl;
		JScrollPane	sourceScrollPane;
		JTextArea	sourceTA;
		
		JPanel		editorPane;
		JPanel 		editorArea;
		JPanel		editor1stPane;
		JLabel		editor1stLabel;
		JTextField	editor1stTF;
		JButton		del1stEditorBtn;
		JButton		add1stEditorBtn;
		JPanel		editor2ndPane;
		JLabel		editor2ndLabel;
		JTextField	editor2ndTF;
		JButton		del2ndEditorBtn;
		JButton		add2ndEditorBtn;			
		JPanel		etalEditorPane;
		JCheckBox	etalEditorChkBox;
		int			maxEditorCounter;
		int			actEditorCounter;
		
		JPanel		lowerTextControlsPane;
		
		JPanel		publisherPane;
		JLabel		publisherLbl;
		JTextField	publisherTF;
		
		JPanel		placePane;
		JLabel		placeLbl;
		JTextField	placeTF;
		
		JPanel		issuePane;
		JLabel		issueLbl;
		JTextField	issueTF;
		
		JPanel		pagesPane;
		JLabel		pagesLbl;
		JTextField	pagesTF;
		
		JPanel		webaddressPane;
		JLabel		webaddressLbl;
		JTextField 	webaddressTF;
		
		JPanel		jTabbedPaneControlsPane;
		JButton		deleteTabBtn;
		
		String 		localeName = "";
		
		/*
		 *	Constructor
		 */
		LiteratureDialog(String TabName, Literature literature, Locale locale, boolean enabled) {
			
			currentLocale	= locale;
			resourceBundle	= ResourceBundle.getBundle(BUNDLE, currentLocale);
			
			localeName = TabName;
			
			/*
			 *	AUTHOR PANE
			 */
			etalAuthorChkBox = new JCheckBox(resourceBundle.getString(TL_ET_AL_LBL));
			etalAuthorChkBox.setFont(currentFont);
			etalAuthorChkBox.setSelected(false);
			etalAuthorChkBox.setEnabled(false);
			if (literature instanceof Literature)
				if (literature.hasAuthorEtAl()) {
					etalAuthorChkBox.setSelected(true);
					etalAuthorChkBox.setEnabled(enabled);
				}
			etalAuthorChkBox.setName(TL_AUTHOR + TL_ET_AL + localeName);
			etalAuthorPane = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
			etalAuthorPane.add(etalAuthorChkBox);
			etalAuthorPane.revalidate();
			
			maxAuthorCounter = 0;
			actAuthorCounter = 0;
			
			authorArea = new JPanel();
			authorArea.setLayout(new BoxLayout(authorArea, BoxLayout.Y_AXIS));
			
			if ((literature instanceof Literature) &&
					(literature.getAuthors() instanceof ArrayList<?>) &&
					(literature.getAuthors().size() > 0)) {
				Iterator<Person> it_author = (literature.getAuthors()).iterator();
				
				while (it_author.hasNext()) {
					Person author = it_author.next();

					if (maxAuthorCounter == 0) 
						authorArea.add(return1stAuthorPanel(author, enabled));
					else if (maxAuthorCounter == 1) 
						authorArea.add(return2ndAuthorPanel(author, enabled));
					else if (maxAuthorCounter == 2) {
						authorArea.add(return3rdAuthorPanel(author, enabled));
						etalAuthorChkBox.setEnabled(enabled);
					}
					
					authorArea.revalidate();
				}			
			} else {
				authorArea.add(return1stAuthorPanel(null, enabled));
				authorArea.revalidate();
			}
			
			authorPane = new JPanel(new BorderLayout());
			authorPane.add(authorArea, BorderLayout.NORTH);
			authorPane.add(etalAuthorPane, BorderLayout.SOUTH);
			authorPane.setPreferredSize(new Dimension(300, 130));
			authorPane.setBorder(
				BorderFactory.createCompoundBorder(
					BorderFactory.createTitledBorder(resourceBundle.getString(TL_AUTHOR_LBL)),
					BorderFactory.createEmptyBorder(5, 5, 5, 5)
				)
			);	
			
			/*
			 *	DATE PANE
			 */
			dateLbl = new JLabel(resourceBundle.getString(TL_DATE_LBL));
			dateLbl.setFont(currentFont);
			
			String DATE_FORMAT = "yyyy";
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
			dateTF = new JFormattedTextField(sdf);
			dateTF.setFont(currentFont);
			
			dateTF.setColumns(4);
			dateTF.setName(TL_DATE + "_" + localeName);
			dateTF.setInputVerifier(new DateFormatVerifier());
			dateTF.addFocusListener(this);
			
			SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
			if (literature instanceof Literature)
				if (literature.getDate() != null)
					dateTF.setText(formatYear.format(literature.getDate()));
			dateTF.setEnabled(enabled);

			datePane = new JPanel(new BorderLayout());
			datePane.add(dateTF, BorderLayout.WEST);
			datePane.setBorder(BorderFactory.createEmptyBorder(3, 5, 2, 5));
		
			/*
			 *	TITLE PANE
			 */
			titleLbl = new JLabel(resourceBundle.getString(TL_TITLE_LBL));
			titleLbl.setFont(currentFont);
			
			titleTA = new JTextArea(2, 65);
			titleTA.setFont(currentFont);
			titleTA.setName(TL_TITLE + "_" + localeName);
			titleTA.addFocusListener(this);
			titleTA.setLineWrap(true);
			titleTA.setWrapStyleWord(true);
			
			if (literature instanceof Literature) {
				String title = literature.getTitle();
				titleTA.setText((title != null ? title : ""));
			}
			titleTA.setEnabled(enabled);
			
			titleScrollPane = 
				new JScrollPane(titleTA, 
						ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			titleScrollPane.getVerticalScrollBar().setUnitIncrement(16);
			
			titlePane = new JPanel(new BorderLayout());
			titlePane.add(titleScrollPane, BorderLayout.WEST);
			titlePane.setBorder(BorderFactory.createEmptyBorder(3, 5, 2, 5));
			
			/*
			 *	SOURCE PANE
			 */
			sourceLbl = new JLabel(resourceBundle.getString(TL_SOURCE_LBL));
			sourceLbl.setFont(currentFont);
			
			sourceTA = new JTextArea(2, 65);
			sourceTA.setFont(currentFont);
			sourceTA.setName(TL_SOURCE + "_" + localeName);
			sourceTA.addFocusListener(this);
			sourceTA.setLineWrap(true);
			sourceTA.setWrapStyleWord(true);
			
			if (literature instanceof Literature) {
				String source = literature.getSource();
				sourceTA.setText((source != null ? source : ""));
			}
			sourceTA.setEnabled(enabled);
			
			sourceScrollPane = 
				new JScrollPane(sourceTA, 
						ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			sourceScrollPane.getVerticalScrollBar().setUnitIncrement(16);
			
			sourcePane = new JPanel(new BorderLayout());
			sourcePane.add(sourceScrollPane, BorderLayout.WEST);
			sourcePane.setBorder(BorderFactory.createEmptyBorder(3, 5, 2, 5));
						
			/*
			 *	EDITOR PANE
			 */
			etalEditorChkBox = new JCheckBox(resourceBundle.getString(TL_ET_AL_LBL));
			etalEditorChkBox.setFont(currentFont);
			etalEditorChkBox.setSelected(false);
			etalEditorChkBox.setEnabled(false);
			if (literature instanceof Literature)
				if (literature.hasEditorEtAl()) {
					etalEditorChkBox.setSelected(true);
					etalEditorChkBox.setEnabled(enabled);
				}
			etalEditorChkBox.setName(TL_EDITOR + TL_ET_AL + localeName);
			etalEditorPane = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
			etalEditorPane.add(etalEditorChkBox);
			etalEditorPane.revalidate();
			
			maxEditorCounter = 0;
			actEditorCounter = 0;
			
			editorArea = new JPanel();
			editorArea.setLayout(new BoxLayout(editorArea, BoxLayout.Y_AXIS));
			
			if ((literature instanceof Literature) &&
					(literature.getEditors() instanceof ArrayList<?>) &&
					(literature.getEditors().size() > 0)) {
				Iterator<Person> it_editor = (literature.getEditors()).iterator();
				
				while (it_editor.hasNext()) {
					Person editor = it_editor.next();
					
					if (maxEditorCounter == 0) 
						editorArea.add(return1stEditorPanel(editor, enabled));
					else if (maxEditorCounter == 1) {
						editorArea.add(return2ndEditorPanel(editor, enabled));
						etalEditorChkBox.setEnabled(enabled);
					}
					
					editorArea.revalidate();
				}			
			} else {
				editorArea.add(return1stEditorPanel(null, enabled));
				editorArea.revalidate();
			}
			
			editorPane = new JPanel(new BorderLayout());
			editorPane.add(editorArea, BorderLayout.NORTH);
			editorPane.add(etalEditorPane, BorderLayout.SOUTH);
			editorPane.setPreferredSize(new Dimension(300, 105)); 
			editorPane.setBorder(
				BorderFactory.createCompoundBorder(
					BorderFactory.createTitledBorder(resourceBundle.getString(TL_EDITOR_LBL)),
					BorderFactory.createEmptyBorder(5, 5, 5, 5)
				)
			);
			
			/*
			 *	PUBLISHER PANE
			 */
			publisherLbl = new JLabel(resourceBundle.getString(TL_PUBLISHER_LBL));
			publisherLbl.setFont(currentFont);
			
			publisherTF = new JTextField();
			publisherTF.setFont(currentFont);
			publisherTF.setColumns(65); 
			publisherTF.setName(TL_PUBLISHER + "_" + localeName);
			publisherTF.addFocusListener(this);
			
			if (literature instanceof Literature) {
				String publisher = literature.getPublisher();
				publisherTF.setText((publisher != null ? publisher : ""));
			}
			publisherTF.setEnabled(enabled);
			
			publisherPane = new JPanel(new BorderLayout());
			publisherPane.add(publisherTF, BorderLayout.WEST);
			publisherPane.setBorder(BorderFactory.createEmptyBorder(3, 5, 2, 5));
			
			/*
			 *	PLACE
			 */
			placeLbl = new JLabel(resourceBundle.getString(TL_PLACE_LBL));
			placeLbl.setFont(currentFont);
			
			placeTF = new JTextField();
			placeTF.setFont(currentFont);
			placeTF.setColumns(65); 
			placeTF.setName(TL_PLACE + "_" + localeName);
			placeTF.addFocusListener(this);
			
			if (literature instanceof Literature) {
				String place = literature.getPlace();
				placeTF.setText((place != null ? place : ""));
			}
			placeTF.setEnabled(enabled);
			
			placePane = new JPanel(new BorderLayout());
			placePane.add(placeTF, BorderLayout.WEST);
			placePane.setBorder(BorderFactory.createEmptyBorder(3, 5, 2, 5));

			/*
			 *	ISSUE PANE
			 */
			issueLbl = new JLabel(resourceBundle.getString(TL_ISSUE_LBL));
			issueLbl.setFont(currentFont);
			
			issueTF = new JTextField(65); 
			issueTF.setFont(currentFont);
			issueTF.setName(TL_ISSUE + "_" + localeName);
			issueTF.addFocusListener(this);
			
			if (literature instanceof Literature) {
				String issue = literature.getIssue();
				issueTF.setText((issue != null ? issue : ""));
			}
			issueTF.setEnabled(enabled);
			
			issuePane = new JPanel(new BorderLayout());
			issuePane.add(issueTF, BorderLayout.WEST);
			issuePane.setBorder(BorderFactory.createEmptyBorder(3, 5, 2, 5));
			
			/*
			 *	PAGES PANE
			 */
			pagesLbl = new JLabel(resourceBundle.getString(TL_PAGES_LBL));
			pagesLbl.setFont(currentFont);
			
			pagesTF = new JTextField(65);
			pagesTF.setFont(currentFont);
			pagesTF.setName(TL_PAGES + "_" + localeName);
			pagesTF.addFocusListener(this);
			
			if (literature instanceof Literature) {
				String pages = literature.getPages();
				pagesTF.setText((pages != null ? pages : ""));
			}
			pagesTF.setEnabled(enabled);
			
			pagesPane = new JPanel(new BorderLayout());
			pagesPane.add(pagesTF, BorderLayout.WEST);
			pagesPane.setBorder(BorderFactory.createEmptyBorder(3, 5, 2, 5));
			
			/*
			 *	WEB ADDRESS PANE
			 */
			webaddressLbl = new JLabel(resourceBundle.getString(TL_WEBADDRESS_LBL));
			webaddressLbl.setFont(currentFont);
			Dimension preferredSize = webaddressLbl.getPreferredSize();
			
			webaddressTF = new JTextField(65); 
			webaddressTF.setFont(currentFont);
			webaddressTF.setName(TL_WEBADDRESS + "_" + localeName);
			webaddressTF.addFocusListener(this);
			
			if (literature instanceof Literature) {
				String webaddress = literature.getWebaddress();
				webaddressTF.setText((webaddress != null ? webaddress : ""));
			}
			webaddressTF.setEnabled(enabled);
			
			webaddressPane = new JPanel(new BorderLayout());
			webaddressPane.add(webaddressTF, BorderLayout.WEST);
			webaddressPane.setBorder(BorderFactory.createEmptyBorder(3, 5, 2, 5));
									
			/*
			 *	LAYOUT
			 */
			GridBagLayout gridBagLayout = new GridBagLayout();
			
			upperTextControlsPane = new JPanel();		
			upperTextControlsPane.setLayout(gridBagLayout);
			
			dateLbl.setPreferredSize(preferredSize);
			titleLbl.setPreferredSize(preferredSize); 
			sourceLbl.setPreferredSize(preferredSize);
			
			JLabel[] textControlLabels = {dateLbl, titleLbl, sourceLbl};
			Component[] textControls = {datePane, titlePane, sourcePane};
			
			addLabelTextControlAsRows(	textControlLabels,
										textControls, 
										gridBagLayout,
										upperTextControlsPane);
			
			gridBagLayout = new GridBagLayout();
			
			lowerTextControlsPane = new JPanel();		
			lowerTextControlsPane.setLayout(gridBagLayout);
			
			publisherLbl.setPreferredSize(preferredSize); 
			placeLbl.setPreferredSize(preferredSize); 
			issueLbl.setPreferredSize(preferredSize); 
			pagesLbl.setPreferredSize(preferredSize);
			webaddressLbl.setPreferredSize(preferredSize);
			
			JLabel[] lowerTextControlLabels = {publisherLbl, placeLbl, issueLbl, pagesLbl, webaddressLbl};
			Component[] lowerTextControls = {publisherPane, placePane, issuePane, pagesPane, webaddressPane};
			
			addLabelTextControlAsRows(	lowerTextControlLabels,
										lowerTextControls, 
										gridBagLayout,
										lowerTextControlsPane);
			
			jTabbedPaneControlsPane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			deleteTabBtn = new JButton(resourceBundle.getString(TL_DEL_REFERENCE));
			deleteTabBtn.addMouseListener(delLiteratureMouseListener);
			deleteTabBtn.setEnabled(enabled);
			jTabbedPaneControlsPane.add(deleteTabBtn);			
			
			this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
			this.add(authorPane);
			this.add(upperTextControlsPane);
			this.add(editorPane);
			this.add(lowerTextControlsPane);
			this.add(jTabbedPaneControlsPane);

			JTextField idTF = new JTextField();
			idTF.setName(TL_LITERATURE + "ID_" + localeName);
			idTF.setVisible(false);
			if (literature instanceof Literature) {
				/* TODO check ??? */
				idTF.setText(Integer.toString(literature.getEntityID()));				
			}
			this.add(idTF); 							
			this.setVisible(true);		
		}
		
		/*
		 *	Methods
		 */
		public void changeLanguage(Locale locale) {
			ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, locale);
			
			authorPane.setBorder(
				BorderFactory.createCompoundBorder(
					BorderFactory.createTitledBorder(bundle.getString(TL_AUTHOR_LBL)),
					BorderFactory.createEmptyBorder(5, 5, 5, 5)
				)
			);
			author1stLabel.setText(bundle.getString(TL_NAME_LBL));
			if (author2ndLabel != null)
				author2ndLabel.setText(bundle.getString(TL_NAME_LBL));
			if (author3rdLabel != null)
				author3rdLabel.setText(bundle.getString(TL_NAME_LBL));
			etalAuthorChkBox.setText(bundle.getString(TL_ET_AL_LBL));

			dateLbl.setText(bundle.getString(TL_DATE_LBL));
			
			titleLbl.setText(bundle.getString(TL_TITLE_LBL));
			
			sourceLbl.setText(bundle.getString(TL_SOURCE_LBL));
			
			editorPane.setBorder(
				BorderFactory.createCompoundBorder(
					BorderFactory.createTitledBorder(bundle.getString(TL_EDITOR_LBL)),
					BorderFactory.createEmptyBorder(5, 5, 5, 5)
				)
			);
			editor1stLabel.setText(bundle.getString(TL_NAME_LBL));
			if (editor2ndLabel != null)
				editor2ndLabel.setText(bundle.getString(TL_NAME_LBL));
			etalEditorChkBox.setText(bundle.getString(TL_ET_AL_LBL));
			
			publisherLbl.setText(bundle.getString(TL_PUBLISHER_LBL));
			
			placeLbl.setText(bundle.getString(TL_PLACE_LBL));
			
			issueLbl.setText(bundle.getString(TL_ISSUE_LBL));
			
			pagesLbl.setText(bundle.getString(TL_PAGES_LBL));
			
			webaddressLbl.setText(bundle.getString(TL_WEBADDRESS_LBL));
			
			deleteTabBtn.setText(bundle.getString(TL_DEL_REFERENCE));
		}
		
		public void changeFont(Font f) {
			author1stLabel.setFont(f);
			if (author2ndLabel != null)
				author2ndLabel.setFont(f);
			if (author3rdLabel != null)
				author3rdLabel.setFont(f);
			etalAuthorChkBox.setFont(f);

			dateLbl.setFont(f);
				
			titleLbl.setFont(f);
				
			sourceLbl.setFont(f);
				
			editor1stLabel.setFont(f);
			if (editor2ndLabel != null)
				editor2ndLabel.setFont(f);
			etalEditorChkBox.setFont(f);
				
			publisherLbl.setFont(f);
				
			placeLbl.setFont(f);
				
			issueLbl.setFont(f);
			
			pagesLbl.setFont(f);
				
			webaddressLbl.setFont(f);
				
			deleteTabBtn.setFont(f);
			
			UIManager.put("TitledBorder.font", f);
			javax.swing.SwingUtilities.updateComponentTreeUI(authorPane);
			javax.swing.SwingUtilities.updateComponentTreeUI(editorPane);
		}
		
		private JPanel return1stAuthorPanel(Person author, boolean enabled) {
			author1stPane = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 1));
			author1stPane.setBorder(null);
		
			author1stLabel = new JLabel(resourceBundle.getString(TL_NAME_LBL));
			author1stLabel.setFont(currentFont);
			author1stPane.add(author1stLabel);
			author1stTF = (JTextField)addRevisedAuthorInputBox(true);
			author1stTF.setFont(currentFont);
			if (author instanceof Person)
				author1stTF.setText(author.getName());
			author1stTF.setEnabled(enabled);
			author1stPane.add(author1stTF);
			author1stPane.add(add1stAuthorButtonPanel());
				
			if (author instanceof Person) {
				JTextField idTF = new JTextField();
				idTF.setName(TL_AUTHOR + "ID_0" + "_" + localeName);
				idTF.setVisible(false);
			
				idTF.setText(Integer.toString(author.getEntityID()));
				author1stPane.add(idTF);
			}
			
			author1stPane.setVisible(true);
			
			maxAuthorCounter = 1;
			actAuthorCounter = 1;
			
			return author1stPane;
		}
		
		private JPanel return2ndAuthorPanel(Person author, boolean enabled) {
			author2ndPane = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 1));
			author2ndPane.setBorder(null);
		
			author2ndLabel = new JLabel(resourceBundle.getString(TL_NAME_LBL));
			author2ndLabel.setFont(currentFont);
			author2ndPane.add(author2ndLabel);
			author2ndTF = (JTextField)addRevisedAuthorInputBox(true);
			author2ndTF.setFont(currentFont);
			if (author instanceof Person)
				author2ndTF.setText(author.getName());
			author2ndTF.setEnabled(enabled);
			author2ndPane.add(author2ndTF);
			author2ndPane.add(add2ndAuthorButtonPanel());
			
			if (author instanceof Person) {
				JTextField idTF = new JTextField();
				idTF.setName(TL_AUTHOR + "ID_1" + "_" + localeName);
				idTF.setVisible(false);
			
				idTF.setText(Integer.toString(author.getEntityID()));
				author2ndPane.add(idTF);
			}
			
			author2ndPane.setVisible(true);
			
			maxAuthorCounter = 2;
			actAuthorCounter = 2;
			
			return author2ndPane;
		}
		
		private JPanel return3rdAuthorPanel(Person author, boolean enabled) {
			author3rdPane = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 1));
			author3rdPane.setBorder(null);
		
			author3rdLabel = new JLabel(resourceBundle.getString(TL_NAME_LBL));
			author3rdLabel.setFont(currentFont);
			author3rdPane.add(author3rdLabel);
			author3rdTF = (JTextField)addRevisedAuthorInputBox(true);
			author3rdTF.setFont(currentFont);
			if (author instanceof Person)
				author3rdTF.setText(author.getName());
			author3rdTF.setEnabled(enabled);
			author3rdPane.add(author3rdTF);
			author3rdPane.add(add3rdAuthorButtonPanel());
			
			if (author instanceof Person) {
				JTextField idTF = new JTextField();
				idTF.setName(TL_AUTHOR + "ID_2" + "_" + localeName);
				idTF.setVisible(false);
			
				idTF.setText(Integer.toString(author.getEntityID()));
				author3rdPane.add(idTF);
			}
			
			author3rdPane.setVisible(true);
			
			maxAuthorCounter = 3;
			actAuthorCounter = 3;
			
			return author3rdPane;
		}
		
		private JPanel return1stEditorPanel(Person editor, boolean enabled) {
			editor1stPane = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 1));
			editor1stPane.setBorder(null);
		
			editor1stLabel = new JLabel(resourceBundle.getString(TL_NAME_LBL));
			editor1stLabel.setFont(currentFont);
			editor1stPane.add(editor1stLabel);
			editor1stTF = (JTextField)addEditorInputBox(true);
			editor1stTF.setFont(currentFont);
			if (editor instanceof Person)
				editor1stTF.setText(editor.getName());
			editor1stTF.setEnabled(enabled);
			editor1stPane.add(editor1stTF);
			editor1stPane.add(add1stEditorButtonPanel());
			
			if (editor instanceof Person) {
				JTextField idTF = new JTextField();
				idTF.setName(TL_EDITOR + "ID_0" + "_" + localeName);
				idTF.setVisible(false);
			
				idTF.setText(Integer.toString(editor.getEntityID()));
				editor1stPane.add(idTF);
			}
			
			editor1stPane.setVisible(true);
			
			maxEditorCounter = 1;
			actEditorCounter = 1;			
			
			return editor1stPane;
		}
		
		private JPanel return2ndEditorPanel(Person editor, boolean enabled) {
			editor2ndPane = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 1));
			editor2ndPane.setBorder(null);
		
			editor2ndLabel = new JLabel(resourceBundle.getString(TL_NAME_LBL));
			editor2ndLabel.setFont(currentFont);
			editor2ndPane.add(editor2ndLabel);
			editor2ndTF = (JTextField)addEditorInputBox(true);
			editor2ndTF.setFont(currentFont);
			if (editor instanceof Person)
				editor2ndTF.setText(editor.getName());
			editor2ndTF.setEnabled(enabled);
			editor2ndPane.add(editor2ndTF);
			editor2ndPane.add(add2ndEditorButtonPanel());
			
			if (editor instanceof Person) {
				JTextField idTF = new JTextField();
				idTF.setName(TL_EDITOR + "ID_1" + "_" + localeName);
				idTF.setVisible(false);
			
				idTF.setText(Integer.toString(editor.getEntityID()));
				editor2ndPane.add(idTF);
			}
			
			editor2ndPane.setVisible(true);
			
			maxEditorCounter = 2;
			actEditorCounter = 2;
			
			return editor2ndPane;
		}
				
		private Component addRevisedAuthorInputBox(boolean isVisible) {		
			JTextField nameTF = new JTextField(55);
			
			nameTF.setName(TL_AUTHOR + TL_NAME + maxAuthorCounter + "_" + localeName);
			nameTF.addFocusListener(this);
			
			return nameTF;
		}
		
		private Component addEditorInputBox(boolean isVisible) {		
			JTextField nameTF = new JTextField(55);
			
			nameTF.setName(TL_EDITOR + TL_NAME + maxEditorCounter + "_" + localeName);
			nameTF.addFocusListener(this);
			
			return nameTF;
		}
		
		private Component add1stAuthorButtonPanel() {
			JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
			panel.setBorder(null);
		
			del1stAuthorBtn = new FormButton(del1stAuthorPaneActionListener, "X", "");
			del1stAuthorBtn.setFont(currentFont);
			del1stAuthorBtn.setEnabled(false);
			
			add1stAuthorBtn = new FormButton(add1stAuthorPaneActionListener, "+", "");
			add1stAuthorBtn.setFont(currentFont);
			add1stAuthorBtn.setEnabled(true);
			
			panel.add(del1stAuthorBtn);
			panel.add(add1stAuthorBtn);		
			
			return panel;
		}
		
		private Component add2ndAuthorButtonPanel() {
			JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
			panel.setBorder(null);
		
			del2ndAuthorBtn = new FormButton(del2ndAuthorPaneActionListener, "X", "");
			del2ndAuthorBtn.setFont(currentFont);
			del2ndAuthorBtn.setEnabled(true);
			
			add2ndAuthorBtn = new FormButton(add2ndAuthorPaneActionListener, "+", "");
			add2ndAuthorBtn.setFont(currentFont);
			add2ndAuthorBtn.setEnabled(true);
			
			panel.add(del2ndAuthorBtn);
			panel.add(add2ndAuthorBtn);		
			
			return panel;
		}
		
		private Component add3rdAuthorButtonPanel() {
			JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
			panel.setBorder(null);
		
			del3rdAuthorBtn = new FormButton(del3rdAuthorPaneActionListener, "X", "");
			del3rdAuthorBtn.setFont(currentFont);
			del3rdAuthorBtn.setEnabled(true);
			
			add3rdAuthorBtn = new FormButton(add3rdAuthorPaneActionListener, "+", "");
			add3rdAuthorBtn.setFont(currentFont);
			add3rdAuthorBtn.setEnabled(false);
			
			panel.add(del3rdAuthorBtn);
			panel.add(add3rdAuthorBtn);		
			
			return panel;
		}
		
		private Component add1stEditorButtonPanel() {
			JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
			panel.setBorder(null);
		
			del1stEditorBtn = new FormButton(del1stEditorPaneActionListener, "X", "");
			del1stEditorBtn.setFont(currentFont);
			del1stEditorBtn.setEnabled(false);
			
			add1stEditorBtn = new FormButton(add1stEditorPaneActionListener, "+", "");
			add1stEditorBtn.setFont(currentFont);
			add1stEditorBtn.setEnabled(true);
			
			panel.add(del1stEditorBtn);
			panel.add(add1stEditorBtn);		
			
			return panel;
		}
		
		private Component add2ndEditorButtonPanel() {
			JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
			panel.setBorder(null);
		
			del2ndEditorBtn = new FormButton(del2ndEditorPaneActionListener, "X", "");
			del2ndEditorBtn.setFont(currentFont);
			del2ndEditorBtn.setEnabled(true);
			
			add2ndEditorBtn = new FormButton(add2ndEditorPaneActionListener, "+", "");
			add2ndEditorBtn.setFont(currentFont);
			add2ndEditorBtn.setEnabled(false);
			
			panel.add(del2ndEditorBtn);
			panel.add(add2ndEditorBtn);		
			
			return panel;
		}
		
		private void removePanel(Container container) {
			
			if (container != null) {
				removeComponents(container);
				
				Container panel = container.getParent();
				panel.remove(container);
				
				((JPanel)panel).revalidate();
				((JPanel)panel).repaint();
			}
			
		}
		
		private void removeComponents(Container container) {
			if (container != null) {
				for (int i=0; i<container.getComponentCount(); i++) {
					if (container.getComponent(i) instanceof Container)
						removeComponents((Container)container.getComponent(i));
			
					container.remove(container.getComponent(i));
				}
			}
		}
												
		private void addLabelTextControlAsRows(	JLabel[] labels,
			 	Component[] textControls,
			 	GridBagLayout gridBagLyout,
			 	Container container)
		{

			GridBagConstraints c = new GridBagConstraints();
			c.anchor = GridBagConstraints.EAST;

			int numberOfLabels = labels.length;

			for (int i = 0; i < numberOfLabels; i++) {
				c.gridwidth = GridBagConstraints.RELATIVE;
				c.fill = GridBagConstraints.NONE;
				c.weightx = 0.0;
				container.add(labels[i], c);

				c.gridwidth = GridBagConstraints.REMAINDER;
				c.fill = GridBagConstraints.HORIZONTAL;
				c.weightx = 1.0;
				container.add(textControls[i], c);
			}
		}
		
		ActionListener add1stAuthorPaneActionListener = new ActionListener () {
            public void actionPerformed(ActionEvent e) {
				authorArea.add(return2ndAuthorPanel(null, enabled));
				authorArea.revalidate();
				
				add1stAuthorBtn.setEnabled(false);
            }
        };
        
		ActionListener add2ndAuthorPaneActionListener = new ActionListener () {
            public void actionPerformed(ActionEvent e) {
				authorArea.add(return3rdAuthorPanel(null, enabled));
				authorArea.revalidate();
				
				etalAuthorChkBox.setEnabled(true);
				
				add2ndAuthorBtn.setEnabled(false);
            }
        };
				
		ActionListener add3rdAuthorPaneActionListener = new ActionListener () {
			public void actionPerformed(ActionEvent e) { }
        };
		
		ActionListener add1stEditorPaneActionListener = new ActionListener () {
			public void actionPerformed(ActionEvent e) {	
				editorArea.add(return2ndEditorPanel(null, enabled));
				editorArea.revalidate();
				
				etalEditorChkBox.setEnabled(true);
				
				add1stEditorBtn.setEnabled(false);
	        }
		};
		
		ActionListener add2ndEditorPaneActionListener = new ActionListener () {
			public void actionPerformed(ActionEvent e) { }
		};
		
		ActionListener del1stAuthorPaneActionListener = new ActionListener () {
			public void actionPerformed(ActionEvent e) { }
		};
		
		ActionListener del2ndAuthorPaneActionListener = new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				
				if (actAuthorCounter == 3) {
					author2ndTF.setText(author3rdTF.getText());
					
					removePanel(author3rdPane);
					actAuthorCounter = 2;
					maxAuthorCounter = 2;
					
					etalAuthorChkBox.setSelected(false);
					etalAuthorChkBox.setEnabled(false);
					
					add2ndAuthorBtn.setEnabled(true);
				} else 
				if (actAuthorCounter == 2) {
					removePanel(author2ndPane);
					actAuthorCounter = 1;
					maxAuthorCounter = 1;
					
					add1stAuthorBtn.setEnabled(true);
				}				
	        }
		};
		
		ActionListener del3rdAuthorPaneActionListener = new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				removePanel(author3rdPane);
				actAuthorCounter = 2;
				maxAuthorCounter = 2;
				
				etalAuthorChkBox.setSelected(false);
				etalAuthorChkBox.setEnabled(false);
				
				add2ndAuthorBtn.setEnabled(true);
	        }
		};
		
		ActionListener del1stEditorPaneActionListener = new ActionListener () {
			public void actionPerformed(ActionEvent e) { }
		};
		
		ActionListener del2ndEditorPaneActionListener = new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				removePanel(editor2ndPane);
				actEditorCounter = 1;
				maxEditorCounter = 1;
				
				etalEditorChkBox.setSelected(false);
				etalEditorChkBox.setEnabled(false);
				
				add1stEditorBtn.setEnabled(true);
	        }
		};
		
		class FormButton extends JButton {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			FormButton(ActionListener al, String l, String tt) {
				super(l);
				
				this.addActionListener(al);
				this.setToolTipText(tt);
			}
		}
		
		class DateFormatVerifier extends InputVerifier {

			@Override
			public boolean verify(JComponent input) {
	    		JTextField tf = (JTextField) input;
	    		
	    		/* length of 4 */
	    		boolean notYYYYFormat = false;
	    		notYYYYFormat = (tf.getText().length() != 4);
	    		
	    		/* No other character than "0123456789" are allowed */
	    		boolean foundForbiddenChars = false;
	    		String	allowedChars = "0123456789";

	    		for (int i=0; i < tf.getText().length(); i++) {
	    			if (!(allowedChars.indexOf(tf.getText().charAt(i)) > -1))
	    				foundForbiddenChars = true;;
	    		}

				return ((!notYYYYFormat) &&
					(!foundForbiddenChars));
			}
			
		}
		
		class NameFormatVerifier extends InputVerifier {
			@Override
			public boolean verify(JComponent input) {
		    		JTextField tf = (JTextField) input;
		    		StringTokenizer st = new StringTokenizer(tf.getText());
			       
		    		/* Given- and Surname is expected */
		    		int countedTokens	= st.countTokens();
			       
		    		/* A maximum of one comma is allowed */
		    		int indexOf			= tf.getText().indexOf(",");
		    		int lastIndexOf		= tf.getText().lastIndexOf(",");
			       
		    		/* No abbr. are allowed */
		    		int indexOfDot		= tf.getText().indexOf(".");
			       
		    		/* No other character than ",", "-" and "a-zA-Z" are allowed */
		    		boolean foundForbiddenChars = false;
		    		String[]	forbiddenChars = 
		    			new String[] {
		    				"0","1","2","3","4","5","6","7","8","9",
		    				";",".","!","?",":","'","\"","`","´",
		    				"(",")","{","[","]","}",
		    				"§","$","%",
		    				"&","/","=","\\","~","#",">","<","|","_",
		    				"*","+",	
		    			};
		    		for (int i=0; i < forbiddenChars.length; i++) {
		    			if (tf.getText().indexOf(forbiddenChars[i]) > -1)
		    				foundForbiddenChars = true;;
		    		}
			       
		    		return (countedTokens >= 2) && 
		    			(indexOf == lastIndexOf) && 
			       		(indexOfDot == -1) && 
			       		!foundForbiddenChars;
		    	}
		}
		    
	    class NominationFormatVerifier extends InputVerifier {
	    	@Override
	        public boolean verify(JComponent input) {
	    		JTextField tf = (JTextField) input;
	    		StringTokenizer st = new StringTokenizer(tf.getText());
		       
	    		/* At least one string is expected */
	    		int countedTokens	= st.countTokens();
		       
	    		return (countedTokens > 0);
	    	}
	    }
	
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

		@SuppressWarnings("rawtypes")
		class NameValuePair implements Comparable {
			
			String	name;
			int 	value;
			
			NameValuePair(String aString, int aValue) {
				name = aString;
				value = aValue;
			}
			public String toString() {
				return name;
			}
			@Override
			public int compareTo(Object o) {

				if (o.getClass().equals(NameValuePair.class))
					return name.compareTo( ((NameValuePair)o).name );
				
				return 0;
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.Tab#setDefaults(java.lang.Object)
	 */
	public void setDefaults(Object defaults) {
		CStatsModel model;
		
		if (defaults instanceof CStatsModel)
			model = (CStatsModel)defaults;
		else {
			model = new CStatsModel();

			/* TODO */
		}
		
		fillModel(model);		
	}
	
	/* (non-Javadoc)
	 * @see org.gesis.charmstats.view.Tab#fillModel(org.gesis.charmstats.model.CStatsModel)
	 */
	public void fillModel(CStatsModel model) {
		Project project; 
		
		if (model instanceof CStatsModel)
			project = ((CStatsModel)model).getProject();
		else
			project = new Project();
		
		boolean enabled = (!(model.getProject().getFinishedSince() != null) && (model.getProject().isEditedByUser()));
		
		jTabbedPane.removeAll();
		
//		ArrayList<Literature> literatures = project.getContent().getLiteratures();
		ArrayList<Literature> literatures = project.getConcept().getLiteratures();
		
		if (literatures != null) {
			Iterator<Literature> it_lit = literatures.iterator();			
			while (it_lit.hasNext()) {
				Literature lit = it_lit.next();
				
				addLiteratureTab(TL_INVISIBLE_STRING, lit, currentLocale, enabled);
			}
			
			if (literatures.size() == 0)
				addLiteratureTab(TL_INVISIBLE_STRING, null, currentLocale, enabled);
		}

	}
}

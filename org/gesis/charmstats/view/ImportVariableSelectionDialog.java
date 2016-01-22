package org.gesis.charmstats.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.gesis.charmstats.model.CStatsModel;
import org.gesis.charmstats.model.Variable;

/**
 * 
 * @author	Martin Friedrichs
 * @since	0.6.5
 *
 */
public class ImportVariableSelectionDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String BUNDLE	= "org.gesis.charmstats.resources.DesktopBundle";
	
	public static final String DIALOG_TITLE			= "dia_imp_var_tit";
	public static final String RETURN				= "dia_imp_var_ret";
	public static final String CANCEL				= "dia_imp_var_can";
	public static final String SELECT_ALL			= "dia_imp_var_sel_all";
	public static final String DESELECT_ALL			= "dia_imp_var_des_all";
	
	public static final String EMPTY_TITLE			= "";
	

	/*
	 *	Fields
	 */
	Locale					currentLocale;
	ResourceBundle			resourceBundle;
	
	private JPanel			controlPane;
    private JScrollPane		scrollPane;
    private JPanel			listPanel;
    private List<Object>	panelList			= new ArrayList<Object>();
    private JPanel			buttonPanel;
    private JPanel			upperButtonPanel;
    private JButton			jBtnSelectAll;
    private JButton			jBtnUnSelectAll;
    private JPanel			lowerButtonPanel;
    private JButton			jBtnCancel;
    private JButton			jBtnReturn;
 
	
	ArrayList<Variable>		selection		= new ArrayList<Variable>();
	
	
	/*
	 *	Constructor
	 */
	/**
	 * @param parent
	 * @param modal
	 * @param variables
	 * @param model
	 * @param locale
	 * @param f
	 */
	public ImportVariableSelectionDialog(java.awt.Frame parent, boolean modal, List<Variable> variables, CStatsModel model, Locale locale, Font f) {
		super(parent, EMPTY_TITLE, modal);
		
		currentLocale	= locale;
		resourceBundle	= ResourceBundle.getBundle(BUNDLE, currentLocale);
						
		initComponents(variables, f);		
	}

	/*
	 *	Methods
	 */
	/**
	 * @param variables
	 * @param f
	 */
	private void initComponents(List<Variable> variables, Font f) {
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		
		setTitle(resourceBundle.getString(DIALOG_TITLE));
		
        controlPane			= new JPanel();
        controlPane.setLayout(new BorderLayout());
		
        listPanel			= new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        
        scrollPane			= new JScrollPane(listPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        jBtnSelectAll		= new JButton();
        jBtnSelectAll.setFont(f);
        jBtnSelectAll.setText(resourceBundle.getString(SELECT_ALL));
        jBtnSelectAll.addActionListener(new java.awt.event.ActionListener() {
            /* (non-Javadoc)
             * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jBtnUnSelectAll		= new JButton();
        jBtnUnSelectAll.setFont(f);
        jBtnUnSelectAll.setText(resourceBundle.getString(DESELECT_ALL));
        jBtnUnSelectAll.addActionListener(new java.awt.event.ActionListener() {
            /* (non-Javadoc)
             * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        
        upperButtonPanel	= new JPanel();
        upperButtonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        upperButtonPanel.add(jBtnSelectAll);
        upperButtonPanel.add(jBtnUnSelectAll);

        jBtnUnSelectAll.setEnabled(false);
        jBtnUnSelectAll.setVisible(false);
                
        jBtnCancel			= new JButton();
        jBtnCancel.setFont(f);
        jBtnCancel.setText(resourceBundle.getString(CANCEL));
        jBtnCancel.addActionListener(new java.awt.event.ActionListener() {
            /* (non-Javadoc)
             * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jBtnReturn			= new JButton();
        jBtnReturn.setFont(f);
        jBtnReturn.setText(resourceBundle.getString(RETURN));
        jBtnReturn.addActionListener(new java.awt.event.ActionListener() {
            /* (non-Javadoc)
             * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        
        lowerButtonPanel	= new JPanel(); 
        lowerButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        lowerButtonPanel.add(jBtnCancel);
        lowerButtonPanel.add(jBtnReturn);
               
        buttonPanel	= new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        
        buttonPanel.add(upperButtonPanel);
        buttonPanel.add(lowerButtonPanel);
        
        controlPane.add(scrollPane,	BorderLayout.CENTER);
        controlPane.add(buttonPanel, BorderLayout.SOUTH);
        
        /* Show list of variables: */
        Iterator<Variable> iter = variables.iterator();
        while (iter.hasNext()) {
        	Variable var = iter.next();
        	
        	JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        	JCheckBox checkBox = new JCheckBox();
        	checkBox.setFont(f);
        	panel.add(checkBox);
        	JLabel contentLbl = new JLabel(var.getName() +" ("+ var.getLabel() +")");
        	contentLbl.setFont(f);
        	panel.add(contentLbl);

        	panelList.add(checkBox);
        	panelList.add(var);
        	
        	listPanel.add(panel);
        }
        
        this.add(controlPane);
        
        this.setMinimumSize(new Dimension(400, 150));
        this.setMaximumSize(new Dimension(400, 250));
        this.setPreferredSize(new Dimension(400, 250));
        pack();
        
        /* Reset after CloseButton to Unselected Selections */
    	this.addWindowListener(new java.awt.event.WindowAdapter() {
    	    @Override
    	    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
    			Iterator<Object> iter = panelList.iterator();
    			while (iter.hasNext()) {
    				JCheckBox	key		= (JCheckBox)iter.next();
    				key.setSelected(false);
    				@SuppressWarnings("unused")
    				Variable	value	= (Variable)iter.next();
    			}
    	    	
    	    	dispose();    
    		}
    	});
    	
        this.setVisible(true);
	}
	
    /* Return empty list after clicking the Cancel button */    
    /**
     * @param evt
     */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
		Iterator<Object> iter = panelList.iterator();
		while (iter.hasNext()) {
			JCheckBox	key		= (JCheckBox)iter.next();
			key.setSelected(false);
			@SuppressWarnings("unused")
			Variable	value	= (Variable)iter.next();
		}
    	
    	dispose();
    }
    
    /* Return list as is after clicking the Return button */
    /**
     * @param evt
     */
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
    	/* DoNothing */
    	
    	dispose();
    }
    
    /* Select all entries in the list after clicking the Select All button */
    /* Replace the Select All button with the UnSelect All button */
    /**
     * @param evt
     */
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
		Iterator<Object> iter = panelList.iterator();
		while (iter.hasNext()) {
			JCheckBox	key		= (JCheckBox)iter.next();
			key.setSelected(true);			
			@SuppressWarnings("unused")
			Variable	value	= (Variable)iter.next();
		}
		
		jBtnSelectAll.setEnabled(false);
		jBtnSelectAll.setVisible(false);
		jBtnUnSelectAll.setEnabled(true);
		jBtnUnSelectAll.setVisible(true);	    	 
    }
    
    /* Unselect all entries in the list after clicking the UnSelect All button */
    /* Replace the UnSelect All button with the Select All button */
    /**
     * @param evt
     */
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
		Iterator<Object> iter = panelList.iterator();
		while (iter.hasNext()) {
			JCheckBox	key		= (JCheckBox)iter.next();
			key.setSelected(false);
			@SuppressWarnings("unused")
			Variable	value	= (Variable)iter.next();
		}
    	 
		jBtnUnSelectAll.setEnabled(false);
		jBtnUnSelectAll.setVisible(false);
		jBtnSelectAll.setEnabled(true);
		jBtnSelectAll.setVisible(true);		
    }

	/**
	 * @return
	 */
	public ArrayList<Variable> getSelections() {
		Iterator<Object> iter = panelList.iterator();
		while (iter.hasNext()) {
			JCheckBox	key		= (JCheckBox)iter.next();
			Variable	value	= (Variable)iter.next();
			
			if (key.isSelected()) 
				selection.add(value);
		}
				
		return selection;
	}
	
}

package org.gesis.charmstats.view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.xml.ws.Action;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public class AboutDialog extends javax.swing.JDialog {
	
	/**
	 * 
	 */	
	private static final long serialVersionUID = 1L;
	
	/*
	 *	Constants
	 */
	public static final String BUNDLE					= "org.gesis.charmstats.resources.DesktopBundle";
	
	public static final String AD_TITLE					= "ad_title";
	public static final String AD_APPLICATION			= "ad_application";
	public static final String AD_DESCRIPTION			= "ad_description";
	public static final String AD_VERSION				= "ad_version";
	public static final String AD_APP_VERSION			= "ad_app_version";
	public static final String AD_VENDOR				= "ad_vendor";
	public static final String AD_APP_VENDOR			= "ad_app_vendor";
	public static final String AD_HOMEPAGE				= "ad_homepage";
	public static final String AD_APP_HOMEPAGE			= "ad_app_homepage";
	public static final String AD_CLOSE					= "ad_close";
	
	public static final String RESOURCE_URL				= "org/gesis/charmstats/resources/quick-charmstats-java.png";
	public static final String RESOURCE_DESCRIPTION		= "nekomimi";
	
	public static final String DIALOG_NAME				= "aboutDialog";
	public static final String IMAGE_LABEL_NAME			= "imageLabel";
	public static final String APP_TITLE_LABEL_NAME		= "appTitleLabel";
	public static final String APP_DESC_LABEL_NAME		= "appDescLabel";
	public static final String VERSION_LABEL_NAME		= "versionLabel";	
	public static final String APP_VERSION_LABEL_NAME	= "appVersionLabel";	
	public static final String VENDOR_LABEL_NAME		= "vendorLabel";	
	public static final String APP_VENDOR_LABEL_NAME	= "appVendorLabel";	
	public static final String HOMEPAGE_LABEL_NAME		= "homepageLabel";	
	public static final String APP_HOMEPAGE_LABEL_NAME	= "appHomepageLabel";	
	public static final String CLOSE_BUTTON_NAME		= "closeButton";	
	
	/*
	 *	Fields
	 */
	Locale 		  				currentLocale;
	ResourceBundle				resourceBundle;
	Font						currentFont;
	
    private javax.swing.JButton closeButton;
    
	/*
	 *	Constructor
	 */
	/**
	 * @param parent
	 * @param locale
	 * @param f
	 */
	public AboutDialog(java.awt.Frame parent, Locale locale, Font f) {
        super(parent);
        
		currentLocale = locale;
		resourceBundle = ResourceBundle.getBundle(BUNDLE, currentLocale);
		currentFont = f;
		
        initComponents();
        getRootPane().setDefaultButton(closeButton);
    }

	/*
	 *	Methods
	 */
    /**
     * 
     */
    private void initComponents() {
   		
        closeButton = new javax.swing.JButton();
        closeButton.setFont(currentFont);
        
        closeButton.addActionListener(actionListener);
        closeButton.addMouseListener(mouseListener);
        
        javax.swing.JLabel appTitleLabel	= new javax.swing.JLabel();
        javax.swing.JLabel versionLabel		= new javax.swing.JLabel();
        javax.swing.JLabel appVersionLabel	= new javax.swing.JLabel();
        javax.swing.JLabel vendorLabel		= new javax.swing.JLabel();
        javax.swing.JLabel appVendorLabel	= new javax.swing.JLabel();
        javax.swing.JLabel homepageLabel	= new javax.swing.JLabel();
        javax.swing.JLabel appHomepageLabel	= new javax.swing.JLabel();
        javax.swing.JLabel appDescLabel		= new javax.swing.JLabel();
        javax.swing.JLabel imageLabel		= new javax.swing.JLabel();
        
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
   
        setTitle(resourceBundle.getString(AD_TITLE));
        setModal(true);
        setName(DIALOG_NAME);
        setResizable(false);

        closeButton.setText(resourceBundle.getString(AD_CLOSE));
        closeButton.setName(CLOSE_BUTTON_NAME);

        appTitleLabel.setFont(appTitleLabel.getFont().deriveFont(appTitleLabel.getFont().getStyle() | java.awt.Font.BOLD, currentFont.getSize()+4)); 
        appTitleLabel.setText(resourceBundle.getString(AD_APPLICATION));
        appTitleLabel.setName(APP_TITLE_LABEL_NAME);

        versionLabel.setFont(versionLabel.getFont().deriveFont(versionLabel.getFont().getStyle() | java.awt.Font.BOLD, currentFont.getSize())); 
        versionLabel.setText(resourceBundle.getString(AD_VERSION)); 
        versionLabel.setName(VERSION_LABEL_NAME); 

        appVersionLabel.setFont(currentFont);
        appVersionLabel.setText(resourceBundle.getString(AD_APP_VERSION));
        appVersionLabel.setName(APP_VERSION_LABEL_NAME); 

        vendorLabel.setFont(vendorLabel.getFont().deriveFont(vendorLabel.getFont().getStyle() | java.awt.Font.BOLD, currentFont.getSize())); 
        vendorLabel.setText(resourceBundle.getString(AD_VENDOR)); 
        vendorLabel.setName(VENDOR_LABEL_NAME); 

        appVendorLabel.setFont(currentFont);
        appVendorLabel.setText(resourceBundle.getString(AD_APP_VENDOR)); 
        appVendorLabel.setName(APP_VENDOR_LABEL_NAME); 

        homepageLabel.setFont(homepageLabel.getFont().deriveFont(homepageLabel.getFont().getStyle() | java.awt.Font.BOLD, currentFont.getSize())); 
        homepageLabel.setText(resourceBundle.getString(AD_HOMEPAGE)); 
        homepageLabel.setName(HOMEPAGE_LABEL_NAME); 

        appHomepageLabel.setFont(currentFont);
        appHomepageLabel.setText(resourceBundle.getString(AD_APP_HOMEPAGE)); 
        appHomepageLabel.setName(APP_HOMEPAGE_LABEL_NAME); 

        appDescLabel.setFont(currentFont);
        appDescLabel.setText(resourceBundle.getString(AD_DESCRIPTION)); 
        appDescLabel.setName(APP_DESC_LABEL_NAME); 
       
        ImageIcon icon = CStatsGUI.createImageIcon(RESOURCE_URL, RESOURCE_DESCRIPTION);
        imageLabel.setFont(currentFont);
        imageLabel.setIcon(icon); 
        imageLabel.setName(IMAGE_LABEL_NAME); 

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(imageLabel)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(versionLabel)
                            .addComponent(vendorLabel)
                            .addComponent(homepageLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(appVersionLabel)
                            .addComponent(appVendorLabel)
                            .addComponent(appHomepageLabel)))
                    .addComponent(appTitleLabel, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(appDescLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
                    .addComponent(closeButton))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(imageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(appTitleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(appDescLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(versionLabel)
                    .addComponent(appVersionLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(vendorLabel)
                    .addComponent(appVendorLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(homepageLabel)
                    .addComponent(appHomepageLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addComponent(closeButton)
                .addContainerGap())
        );

        pack();
    }
    
    /**
     * 
     */
    @Action public void closeAboutBox() {
        dispose();
    }
    
    ActionListener actionListener = new ActionListener() {
    	@Override
    	public void actionPerformed(ActionEvent actionEvent) {
    		dispose();
    	}
    };

    MouseListener mouseListener = new MouseAdapter() {
    	public void mouseReleased(MouseEvent mouseEvent) {
    		dispose();
    	}
    };

}

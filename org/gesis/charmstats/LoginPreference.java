package org.gesis.charmstats;

import java.util.prefs.Preferences;

/**
 * 
 *	@author Martin Friedrichs
 *	@since	0.6.5
 *
 */
public class LoginPreference {
	
	/*
	 *	Fields
	 */
	private	Preferences 			prefs;
	
    private String					ID_1	= "rem";
    private String					ID_2	= "nam";
    private String					ID_3	= "pwd";
    
    private static final boolean	DEF_REM	= false;
    private static final String		DEF_NAM	= "";
    private static final String		DEF_PWD	= "";
    
    private boolean					rem;
    private String					nam;
    private String					pwd;

	/**
	 *	Constructor 
	 */
	public LoginPreference () {
		super();
		
	    // This will define a node in which the preferences can be stored
	    prefs = Preferences.userRoot().node(this.getClass().getName());
	    
	    rem		= prefs.getBoolean(	ID_1, DEF_REM);
	    nam		= prefs.get(		ID_2, DEF_NAM);
	    pwd		= prefs.get(		ID_3, DEF_PWD);	    
	}
	
	/*
	 *	Methods
	 */
	/**
	 * @param par_rem
	 * @param par_nam
	 * @param par_pwd
	 */
	public void setPreference(boolean par_rem, String par_nam, String par_pwd) {
		rem = par_rem;
		nam = par_nam;
		pwd = par_pwd;
		
		prefs.putBoolean(	ID_1, rem);
		if (par_rem) {
			prefs.put(		ID_2, nam);
			prefs.put(		ID_3, pwd);
		} else {
			prefs.put(		ID_2, DEF_NAM);
			prefs.put(		ID_3, DEF_PWD);			  
		}
	}
	
	/**
	 * @return
	 */
	public boolean getRememberMe() {
		return rem;
	}
	
	/**
	 * @return
	 */
	public String getName() {
		return nam;
	}
	
	/**
	 * @return
	 */
	public String getPassword() {
		return pwd;
	}
	
}

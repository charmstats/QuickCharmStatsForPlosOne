package org.gesis.charmstats.persistence;

/**
 *	@author	Martin Friedrichs
 *	@since	0.1
 *
 */
public enum DBField {
	/* activity logs */
	ACT_LOG_ACTION				(  255),
	ACT_LOG_DETAILS 			( 2047), 
	/* baskets */
	BAS_LAB						(  255),
	/* bias_metadata */
	BIA_MIS						( 2047),
	/* categories */
	CAT_COD						(  255),
	CAT_LAB						(  255),
	/* comments */
	CMT_SUB						(  255), /* not in use yet */
	CMT_TXT						(16383),
	CMT_LAN_COD					(   20), /* not in use yet */
	CMT_LAN_COD_NOR				(   20), /* not in use yet */
	/* con_dimensions */
	DIM_LAB						(  255),
	/* con_specifications */
	SPE_LAB						(  255),
	/* concepts */
	CON_NAM						(  255),
	CON_LAB						(  255),
	CON_DES						(16383),
	/* country  */
	COU_LAB						(   64), /* not in use yet */
	COU_COU_COD					(   16), /* not in use yet */
	COU_COU_COD_NOR				(   16), /* not in use yet */
	/* definitions */
	DEF_TXT						(16383),
	DEF_LAN_COD					(   20), /* not in use yet */
	DEF_LAN_COD_NOR				(   20), /* not in use yet */
	/* instances */
	INS_LAB						(  255),
	INS_DES						(16383),
	/* keywords */
	KEY_KEY						(  255),
	KEY_VOC						(  255), /* not in use yet */
	KEY_LAN_COD_NOR				(   20),
	KEY_LAN_COD					(   20),
	/* literatures */
	LIT_TIT						( 2047),
	LIT_PUB						(  255),
	LIT_OTH						(  255), /* not in use yet */
	LIT_ISS						(  255),
	LIT_PLA						(  255),
	LIT_SOU						(  255),
	LIT_PAG						(  255),
	LIT_WEB						( 2083),
	LIT_LAN_COD_NOR				(   20), /* not in use yet */
	LIT_LAN_COD					(   20), /* not in use yet */
	/* measurements */
	MEA_NAM						(  255),
	MEA_LAB						(  255),
	MEA_DAT_SET					( 1023),
	MEA_PID						( 1023),
	/* opera_indicators */
	IND_LAB						(  255),
	/* opera_prescriptions */
	PRE_LAB						(  255),
	PRE_VAL						(  255),
	/* persons */
	PER_NAM						(  255),
	PER_CIT						(  255),
	PER_ZIP_COD					(   16),
	PER_STR						(   64),
	PER_STR_NUM					(    8),
	PER_PHO						(   16),
	PER_EMA						(  320),
	PER_INS_MES					(  320),
	PER_PLA_OF_BIR				(  255),
	PER_DEP						(  255),
	PER_LOG_URI					( 2083),
	/* projects */
	PRO_NAM						(  255),
	PRO_TAR_NAM					(  255),
	PRO_TAR_LAB					(  255),
	PRO_TEM_PAT					( 2083),
	PRO_VER						(    8),
	PRO_MOD_VER					(    8),
	PRO_DOI						( 2047),
	/* questions */
	QUE_NAM 					(  255), 
	QUE_TXT						(16383),
	QUE_INS						(32767),
	QUE_INT						( 4095), /* not in use yet */
	/* studies */
	STU_TIT						( 2047),
	STU_DOI						(  255),
	STU_INS						(  255), /* not in use yet */
	STU_COP						(  255), /* not in use yet */
	STU_PRI_INV					( 2047), /* not in use yet */
	STU_DAT_OF_COL				(  255),
	STU_STU_ARE					( 2047),
	STU_POP						( 2047),
	STU_SEL						( 2047),
	STU_COL_MET					( 2047),
	STU_COL						( 2047),
	STU_SRC_FIL					( 2083),
	STU_DAT						( 2083),
	/* Transformations  */
	TRA_COD						(16383),
	/* users */
	USE_NAM						(  255),
	USE_PAS						(  255),
	/* values */
	VAL_LAB						(  255),
	VAL_VAL						(  255), 
	/* variables */
	VAR_NAM						(  255),
	VAR_LAB						(  255),
	VAR_DAT_SET					( 1023),
	VAR_PID						( 1023),
	VAR_DAT_DAT					(   20),

	/* mult_lang_text */
	MLT_TXT_CON					( 2047),
	/* formated_text */
	FT_TXT_CON					( 2047); 

	/*
	 *	Fields
	 */
	private final int length;
	
	/*
	 *	Constructor
	 */
	/**
	 * @param length
	 */
	DBField(int length) {
        this.length = length;
    }

	/*
	 *	Methods
	 */
	/**
	 * @return
	 */
	public int getLength() {
		return length;
	}
	
	/**
	 * @param string
	 * @return
	 */
	public String truncate(String string) {
		if (!(string != null))
			string = new String();
		
		string = string.substring(0, java.lang.Math.min(string.length(), length));
//	    string = string.replace("'", " "); REMOVED 13.05.2013; preparedStatement should handle it
	    
	    return string;
	}
}

package com.azuga.loadTest.utils;

/**
 * @author Jagannathan
 *
 */
public class MessageHeader {
	
	private String LATITUDE = LoadTestConstants.ZERO;
	private String LONGITUDE = LoadTestConstants.ZERO;
	private String DEVICEID = LoadTestConstants.ZERO;
	private String TIMEINMILLIS = LoadTestConstants.ZERO;
	private String TRIPNUMBER = LoadTestConstants.ZERO;
	private String CURRENTTIMEZONE = LoadTestConstants.ZERO;
	private String ODOREADING = LoadTestConstants.ZERO;
	private String TRIPSTATE = LoadTestConstants.ZERO;
	
	
	public String getLATITUDE() {
		return LATITUDE;
	}
	public void setLATITUDE(String lATITUDE) {
		LATITUDE = lATITUDE;
	}
	public String getLONGITUDE() {
		return LONGITUDE;
	}
	public void setLONGITUDE(String lONGITUDE) {
		LONGITUDE = lONGITUDE;
	}
	public String getDEVICEID() {
		return DEVICEID;
	}
	public void setDEVICEID(String dEVICEID) {
		DEVICEID = dEVICEID;
	}
	public String getTIMEINMILLIS() {
		return TIMEINMILLIS;
	}
	public void setTIMEINMILLIS(String tIMEINMILLIS) {
		TIMEINMILLIS = tIMEINMILLIS;
	}
	public String getTRIPNUMBER() {
		return TRIPNUMBER;
	}
	public void setTRIPNUMBER(String tRIPNUMBER) {
		TRIPNUMBER = tRIPNUMBER;
	}
	public String getCURRENTTIMEZONE() {
		return CURRENTTIMEZONE;
	}
	public void setCURRENTTIMEZONE(String cURRENTTIMEZONE) {
		CURRENTTIMEZONE = cURRENTTIMEZONE;
	}
	public String getODOREADING() {
		return ODOREADING;
	}
	public void setODOREADING(String oDOREADING) {
		ODOREADING = oDOREADING;
	}
	public String getTRIPSTATE() {
		return TRIPSTATE;
	}
	public void setTRIPSTATE(String tRIPSTATE) {
		TRIPSTATE = tRIPSTATE;
	}

}

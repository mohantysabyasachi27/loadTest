package com.azuga.loadTest.utils;

public class Point {
	
	private String latitude;

	

	private String longitude;



	public String getLatitude() {
		return latitude;
	}



	public Point(String latitude, String longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}



	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}



	public String getLongitude() {
		return longitude;
	}



	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	
}

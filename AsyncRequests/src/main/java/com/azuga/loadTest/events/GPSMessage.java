package com.azuga.loadTest.events;

import java.io.IOException;

import net.sourceforge.juint.Int16;
import net.sourceforge.juint.UInt16;
import net.sourceforge.juint.UInt8;

import com.azuga.loadTest.utils.GenericMessage;
import com.azuga.loadTest.utils.LoadTestConstants;
import com.azuga.loadTest.utils.MessageSender;
import com.azuga.loadTest.utils.MessageUtils;

/**
 * @author Jagannathan
 *
 */
public class GPSMessage implements GenericMessage {

	private static final int GPSMESSAGETYPE = 1;
	private String cogValue = LoadTestConstants.ZERO;
	private String height = LoadTestConstants.ZERO;
	private String tripDistance = LoadTestConstants.ZERO;
	private String sogValue = LoadTestConstants.ZERO;
	private String svValue = LoadTestConstants.ZERO;
	private String fixQuality = LoadTestConstants.ZERO;
	private String hDop = LoadTestConstants.ZERO;
	private String obdSpeed = LoadTestConstants.ZERO;
	private String obdMaxSpeed = LoadTestConstants.ZERO;
	private String obdAvgSpeed = LoadTestConstants.ZERO;
	private String tripIdlingTime = LoadTestConstants.ZERO;
	
	private MessageUtils messageUtils;
	private MessageSender messageSender;

	public void createSend(boolean sendDatatoServer, long sysTime) {
		byte[] gpsData = new byte[14];
		int pIndex = 0;
		
		// SOG
		gpsData[pIndex++] =  new UInt8(Integer.parseInt(sogValue)).byteValue(); 
		// COG
		byte[] cogArray = new byte[2];
		cogArray = new Int16(Integer.parseInt(cogValue)).toLittleEndian(); 
		System.arraycopy(cogArray, 0, gpsData, pIndex, cogArray.length);
		pIndex += cogArray.length;
		// SV
		gpsData[pIndex++] = new UInt8(Integer.parseInt(svValue)).byteValue(); 
		// Fixed Quality
		gpsData[pIndex++] = new UInt8(Integer.parseInt(fixQuality)).byteValue(); 
		// Hdop
		gpsData[pIndex++] = new UInt8(Integer.parseInt(hDop)).byteValue(); 
		// Height
		byte[] heightArray = new byte[2];
		//heightValue = (short) Double.parseDouble(height);
		heightArray = new Int16(Integer.parseInt(height)).toLittleEndian(); 
		System.arraycopy(heightArray, 0, gpsData, pIndex, heightArray.length);
		pIndex += heightArray.length;
		// Obd Speed
		gpsData[pIndex++] = new UInt8(Integer.parseInt(obdSpeed)).byteValue();
		// obd max speed
		gpsData[pIndex++] = new UInt8(Integer.parseInt(obdMaxSpeed)).byteValue(); 
		// Obd Avg speed
		gpsData[pIndex++] = new UInt8(Integer.parseInt(obdAvgSpeed)).byteValue(); 
		// Trip Distance.
		byte[] tripDistanceArray = new byte[2];
		tripDistanceArray = new UInt16(Integer.parseInt(tripDistance)).toLittleEndian(); 
		System.arraycopy(tripDistanceArray, 0, gpsData, pIndex,
				tripDistanceArray.length);
		pIndex += tripDistanceArray.length;
		
		// Trip Idling Time.
		gpsData[pIndex++] = new UInt8(Integer.parseInt(tripIdlingTime)).byteValue(); 

		byte[] packet = messageUtils.addHeaderTrailer(gpsData, GPSMESSAGETYPE, sysTime);

		if (sendDatatoServer) {
			try {
				messageSender.sendData(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println(messageUtils.getLATITUDE() + "    " + messageUtils.getLONGITUDE() + "   " + toString());
		}
	}

	public MessageSender getMessageSender() {
		return messageSender;
	}

	public void setMessageSender(MessageSender messageSender) {
		this.messageSender = messageSender;
	}

	public MessageUtils getMessageUtils() {
		return messageUtils;
	}

	public void setMessageUtils(MessageUtils messageUtils) {
		this.messageUtils = messageUtils;
	}

	public String getCogValue() {
		return cogValue;
	}

	public void setCogValue(String cogValue) {
		this.cogValue = cogValue;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getTripDistance() {
		return tripDistance;
	}

	public void setTripDistance(String tripDistance) {
		this.tripDistance = tripDistance;
	}

	public String getSogValue() {
		return sogValue;
	}

	public void setSogValue(String sogValue) {
		this.sogValue = sogValue;
	}

	public String getSvValue() {
		return svValue;
	}

	public void setSvValue(String svValue) {
		this.svValue = svValue;
	}

	public String getFixQuality() {
		return fixQuality;
	}

	public void setFixQuality(String fixQuality) {
		this.fixQuality = fixQuality;
	}

	public String gethDop() {
		return hDop;
	}

	public void sethDop(String hDop) {
		this.hDop = hDop;
	}

	public String getObdSpeed() {
		return obdSpeed;
	}

	public void setObdSpeed(String obdSpeed) {
		this.obdSpeed = obdSpeed;
	}

	public String getObdMaxSpeed() {
		return obdMaxSpeed;
	}

	public void setObdMaxSpeed(String obdMaxSpeed) {
		this.obdMaxSpeed = obdMaxSpeed;
	}

	public String getObdAvgSpeed() {
		return obdAvgSpeed;
	}

	public void setObdAvgSpeed(String obdAvgSpeed) {
		this.obdAvgSpeed = obdAvgSpeed;
	}

	public String getTripIdlingTime() {
		return tripIdlingTime;
	}

	public void setTripIdlingTime(String tripIdlingTime) {
		this.tripIdlingTime = tripIdlingTime;
	}

	@Override
	public String toString() {
		return "GPSMessage [cogValue=" + cogValue + ", height=" + height
				+ ", tripDistance=" + tripDistance + ", sogValue=" + sogValue
				+ ", svValue=" + svValue + ", fixQuality=" + fixQuality
				+ ", hDop=" + hDop + ", obdSpeed=" + obdSpeed
				+ ", obdMaxSpeed=" + obdMaxSpeed + ", obdAvgSpeed="
				+ obdAvgSpeed + ", tripIdlingTime=" + tripIdlingTime
				+  "]";
	}


}

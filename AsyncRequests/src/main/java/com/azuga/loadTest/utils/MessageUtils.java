package com.azuga.loadTest.utils;

import net.sourceforge.juint.Int16;
import net.sourceforge.juint.Int32;
import net.sourceforge.juint.Int8;
import net.sourceforge.juint.UInt32;
import net.sourceforge.juint.UInt8;

/**
 * @author Jagannathan
 * 
 */
public class MessageUtils {

	private String LATITUDE = LoadTestConstants.ZERO;
	private String LONGITUDE = LoadTestConstants.ZERO;
	private String DEVICEID = LoadTestConstants.ZERO;
	private String TIMEINMILLIS = LoadTestConstants.ZERO;
	private String TRIPNUMBER = LoadTestConstants.ZERO;
	private String CURRENTTIMEZONE = LoadTestConstants.ZERO;
	private String ODOREADING = LoadTestConstants.ZERO;
	private String TRIPSTATE = LoadTestConstants.ZERO;

	private final int MESSAGE_HEADER = 22;
	private final int MESSAGE_CHECKSUM = 1;
	private final int PACKET_HEADER = 16; // 16 is new message header, old firmware its 15
	private final int PACKET_TRAILER = 1;

	private final byte TCP_ENCRYPTSEQ = 0;
	private final byte PACKET_START = 0x23;
	private byte[] message;
	private byte[] packet;

	public byte[] addHeaderTrailer(byte[] data, int msgType, long timeinMillies) {

		String packetData;
		message = new byte[MESSAGE_HEADER + data.length + MESSAGE_CHECKSUM];
		message = frameMessage(data, message, msgType, timeinMillies);
		packet = new byte[PACKET_HEADER + message.length + PACKET_TRAILER];
		packet = FramePacket(message, packet);
		packetData = ConvertByteBuffToString(packet);

		return packet;
	}

	public byte[] frameMessage(byte[] data, byte[] message, int messageType, long timeinMillies) {
		int pIndex = 0;

		String curTimeZone = "";
		byte messageCheckSum = 0;
		byte[] timeArray = new byte[4];
		int latitudeVal = 0;
		int longitudeVal = 0;
		message[pIndex++] = new UInt8(messageType).byteValue();
		message[pIndex++] = new UInt8(data.length + MESSAGE_CHECKSUM).byteValue();
		long timeValue = 0L;
		if (getTIMEINMILLIS().equalsIgnoreCase("0")) {

			timeValue = System.currentTimeMillis() / 1000L;

		} else {
			timeValue = Long.parseLong(getTIMEINMILLIS());
		}

		timeValue = timeinMillies;
		System.out.println("Unique " + "    " + timeValue);
		timeArray = new UInt32(timeValue).toLittleEndian();
		System.arraycopy(timeArray, 0, message, pIndex, timeArray.length);
		pIndex += timeArray.length;

		curTimeZone = CURRENTTIMEZONE;

		message[pIndex++] = new Int8(Integer.parseInt(curTimeZone)).byteValue();
		byte[] tripNumberArray = new byte[2];
		tripNumberArray = new Int16(Integer.parseInt(TRIPNUMBER)).toLittleEndian();
		System.arraycopy(tripNumberArray, 0, message, pIndex, tripNumberArray.length);
		pIndex += tripNumberArray.length;
		// Latitude.
		latitudeVal = (int) (Double.parseDouble(LATITUDE) * (Math.pow(10, 7)));
		byte[] latitudeArray = new byte[4];
		latitudeArray = new Int32(latitudeVal).toLittleEndian();
		System.arraycopy(latitudeArray, 0, message, pIndex, latitudeArray.length);
		pIndex += latitudeArray.length;
		// Longitude.
		longitudeVal = (int) (Double.parseDouble(LONGITUDE) * (Math.pow(10, 7)));
		byte[] longitudeArray = new byte[4];
		longitudeArray = new Int32(longitudeVal).toLittleEndian();
		System.arraycopy(longitudeArray, 0, message, pIndex, longitudeArray.length);
		pIndex += longitudeArray.length;
		// ODO.

		byte[] odoArray = new byte[4];
		odoArray = new UInt32(Integer.parseInt(ODOREADING)).toLittleEndian();
		System.arraycopy(odoArray, 0, message, pIndex, odoArray.length);
		pIndex += odoArray.length;
		// TripState.
		message[pIndex++] = new UInt8(Integer.parseInt(TRIPSTATE)).byteValue();

		// Copy GPS Data to Message Buffer.
		System.arraycopy(data, 0, message, pIndex, data.length);
		pIndex += data.length;

		// Take Local Copy to Calculate CheckSum.
		byte[] dataBuffer = new byte[message.length];
		// calculate MessageCheckSum and Copy CheckSumValue.
		System.arraycopy(message, 0, dataBuffer, 0, dataBuffer.length);
		for (int i = 0; i < pIndex; i++) {
			messageCheckSum += dataBuffer[i];

		}
		messageCheckSum ^= (byte) 0xff;
		message[pIndex++] = messageCheckSum;

		return message;

	}

	public byte[] FramePacket(byte[] message, byte[] packet) {
		int pIndex = 0;
		byte headerCheckSum = 0;
		byte packetCheckSum = 0;

		packet[pIndex++] = PACKET_START;
		// Adding Firmware Protocol Version Id, wont work with 8888 port.
		packet[pIndex++] = 1;

		byte[] actDeviceIdArray = new byte[11];
		byte[] deviceIdArray = BitConverter.getBytes(DEVICEID);
		System.arraycopy(deviceIdArray, 0, actDeviceIdArray, 0, deviceIdArray.length);
		System.arraycopy(actDeviceIdArray, 0, packet, pIndex, actDeviceIdArray.length);
		pIndex += actDeviceIdArray.length;
		// TcpEncryption Sequence.
		packet[pIndex++] = TCP_ENCRYPTSEQ;
		// PayLoadSize.
		packet[pIndex++] = new UInt8(message.length).byteValue();
		// HeaderCheckSum
		for (int i = 0; i < pIndex; i++) {

			headerCheckSum += packet[i];
		}
		headerCheckSum ^= (byte) 0xff;
		packet[pIndex++] = headerCheckSum;
		// Copy the MessageData.
		System.arraycopy(message, 0, packet, pIndex, message.length);
		pIndex += message.length;

		// Take Local Copy to Calculate CheckSum;
		byte[] dataBuffer = new byte[packet.length];
		// Calculate MessageCheckSum and Copy CheckSumValue.
		System.arraycopy(packet, 0, dataBuffer, 0, dataBuffer.length);
		// Calculate Packet CheckSum.
		for (int j = 0; j < pIndex; j++) {

			packetCheckSum += dataBuffer[j];
		}
		packetCheckSum ^= (byte) 0xff;
		packet[pIndex++] = packetCheckSum;

		return packet;
	}

	public String ConvertByteBuffToString(byte[] dataFrame) {

		String responseMessage = "";

		for (int iCount = 0; iCount < dataFrame.length; iCount++)
			responseMessage = responseMessage + " " + dataFrame[iCount];

		return responseMessage;
	}

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

	@Override
	public String toString() {
		return "MessageUtils [LATITUDE=" + LATITUDE + ", LONGITUDE=" + LONGITUDE + ", DEVICEID=" + DEVICEID
				+ ", TIMEINMILLIS=" + TIMEINMILLIS + ", TRIPNUMBER=" + TRIPNUMBER + ", CURRENTTIMEZONE=" + CURRENTTIMEZONE
				+ ", ODOREADING=" + ODOREADING + ", TRIPSTATE=" + TRIPSTATE + "]";
	}

}

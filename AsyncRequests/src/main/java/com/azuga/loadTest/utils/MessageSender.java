package com.azuga.loadTest.utils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

/**
 * @author Jagannathan
 *
 */
public class MessageSender {

	private String serverName="127.0.0.1";
	private int portNumber=8889;
	private static long messageCount = 0;

	public synchronized void sendData(byte[] data) throws IOException {

		final DatagramSocket server = new DatagramSocket();
		
		final InetSocketAddress address = new InetSocketAddress(serverName,
				portNumber);
		StringBuilder hex = new StringBuilder();

		for (byte b : data) {
			String s = Integer.toHexString(b & 0xFF);
			hex.append(s.length() == 1 ? "0" + s : s);
			//System.out.print((s.length() == 1 ? "0" + s : s) + " ");

		}
		
		
		messageCount++;
		server.send(new DatagramPacket(data, data.length, address));

		/*
		 * Avoiding the problem of waiting for the response
		 * The response is not read
		 */
	//server.receive(new DatagramPacket(data, data.length, address));
		

		server.close();
	}

	public static long getMessageCount() {
		return messageCount;
	}

	public static void setMessageCount(long messageCount) {
		MessageSender.messageCount = messageCount;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public int getPortNumber() {
		return portNumber;
	}

	public void setPortNumber(int portNumber) {
		this.portNumber = portNumber;
	}

}

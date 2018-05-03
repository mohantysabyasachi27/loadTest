package activemq;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import com.azuga.loadTest.events.GPSMessage;
import com.azuga.loadTest.utils.MessageSender;
import com.azuga.loadTest.utils.MessageUtils;

public class PostToDPWorker implements Runnable {

	volatile static AtomicInteger threadNumber = new AtomicInteger( 1 );
	static MessageUtils messageUtils = new MessageUtils();
	static MessageSender messageSender = new MessageSender();
	static String hostname = "10.19.0.221";
    static int port = 8889;
	
	static {
		messageUtils.setCURRENTTIMEZONE("0");
		messageUtils.setLATITUDE("12.343");
		messageUtils.setLONGITUDE("77.341");
		messageUtils.setODOREADING("0");
		messageUtils.setTRIPNUMBER(String.valueOf(new Random().nextInt(1000)));
		messageUtils.setTRIPSTATE(String.valueOf(new Random().nextBoolean()?0:1));
		messageUtils.setDEVICEID("6090981131");
		
	    messageSender.setServerName(hostname.trim());
		messageSender.setPortNumber(port);
	}
	
	@Override
	public void run() {
		
		GPSMessage gpsMessage = new GPSMessage();
		gpsMessage.setMessageSender(messageSender);
		gpsMessage.setMessageUtils(messageUtils);
		gpsMessage.setSogValue("1");
		gpsMessage.setCogValue("218");
		gpsMessage.setSvValue("3");
		gpsMessage.setFixQuality("3");
		gpsMessage.sethDop("1");
		gpsMessage.setHeight("-200");
		
		gpsMessage.setTripIdlingTime(String.valueOf(1));	
		
		for(int i=0; i< 75; i++ ) {
			gpsMessage.setObdSpeed(String.valueOf(new Random().nextInt(10)));
			gpsMessage.setObdMaxSpeed(String.valueOf(new Random().nextInt(10)));
			gpsMessage.setObdAvgSpeed(String.valueOf(new Random().nextInt(50)));
			gpsMessage.setTripDistance(String.valueOf(new Random().nextInt(10)));
			gpsMessage.createSend(true, System.currentTimeMillis());
		}
		
		System.out.println("ThreadNo: "+threadNumber.getAndIncrement() +" #### "+ Thread.currentThread().getName());
	}

}

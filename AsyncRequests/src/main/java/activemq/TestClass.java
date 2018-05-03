package activemq;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.azuga.locationservice.model.GPSMessageVO;
import com.azuga.locationservice.model.MessageVO;

public class TestClass {

	private static ThreadPoolExecutor messagePool1 = new ThreadPoolExecutor(100, 250, 5000, TimeUnit.MILLISECONDS,
			new LinkedBlockingQueue<Runnable>(),new ThreadPoolExecutor.CallerRunsPolicy());

	private static ThreadPoolExecutor messagePool2 = new ThreadPoolExecutor(100, 250, 5000, TimeUnit.MILLISECONDS,
			new LinkedBlockingQueue<Runnable>(),new ThreadPoolExecutor.CallerRunsPolicy());
	
	private static ThreadPoolExecutor messagePool3 = new ThreadPoolExecutor(100, 150, 5000, TimeUnit.MILLISECONDS,
			new LinkedBlockingQueue<Runnable>(),new ThreadPoolExecutor.CallerRunsPolicy());
	
	private static ThreadPoolExecutor messagePool4 = new ThreadPoolExecutor(100, 150, 5000, TimeUnit.MILLISECONDS,
			new LinkedBlockingQueue<Runnable>(),new ThreadPoolExecutor.CallerRunsPolicy());
	
	private static ThreadPoolExecutor messagePool5 = new ThreadPoolExecutor(100, 150, 5000, TimeUnit.MILLISECONDS,
			new LinkedBlockingQueue<Runnable>(),new ThreadPoolExecutor.CallerRunsPolicy());
	
	private static ThreadPoolExecutor messagePool6 = new ThreadPoolExecutor(100, 150, 5000, TimeUnit.MILLISECONDS,
			new LinkedBlockingQueue<Runnable>(),new ThreadPoolExecutor.CallerRunsPolicy());
	
	
	public static void main(String... args) throws UnknownHostException, IOException, InterruptedException {

	/*	
	for (int i = 0; i < 20000; i++) {
			
			messagePool1.execute(new PostToDPWorker());
			Thread.sleep(25);
			messagePool2.execute(new PostToDPWorker());
			Thread.sleep(25);
			messagePool3.execute(new PostToDPWorker());
			Thread.sleep(25);
			//messagePool4.execute(new PostToDPWorker());
			Thread.sleep(25);
			//messagePool5.execute(new PostToDPWorker());
			//Thread.sleep(25);
			//messagePool6.execute(new PostToDPWorker());
			
			//Thread.sleep(250);
		}
		
		*/
		

		  	String key[] = new String[] { "NODE_1", "F-N-1" };
			MessageVO msg = new GPSMessageVO();
			msg.setQueueName("NODE_1");
			msg.setLatitude(10.123);
			msg.setLongitude(31.213212);
			msg.setFixQuality(1);
			msg.setDeviceSerial("123312423");
			for (int i = 0; i < 20000; i++) {
			
			messagePool1.execute(new PostMessagesToQueuesWorker(key[0], msg));
			messagePool2.execute(new PostMessagesToQueuesWorker(key[0], msg));
			messagePool3.execute(new PostMessagesToQueuesWorker(key[0], msg));
			messagePool4.execute(new PostMessagesToQueuesWorker(key[0], msg));
			messagePool5.execute(new PostMessagesToQueuesWorker(key[0], msg));
			messagePool6.execute(new PostMessagesToQueuesWorker(key[0], msg));
		}
	}

}
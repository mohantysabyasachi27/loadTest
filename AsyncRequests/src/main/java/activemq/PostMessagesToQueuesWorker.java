package activemq;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.azuga.jms.producer.AMQProducerFactory;
import com.azuga.jms.producer.IProducer;
import com.azuga.jms.producer.JMSProducer;
import com.azuga.locationservice.model.MessageVO;

public class PostMessagesToQueuesWorker implements Runnable {

	public static final Logger logger = LoggerFactory.getLogger(PostMessagesToQueuesWorker.class);
	private String nodeKey = null;
	private MessageVO message = null;
	private static final com.azuga.jms.producer.ProducerFactory factory = new com.azuga.jms.producer.ProducerFactory();
	private static Map<String, String> map = new HashMap<String, String>();

	public PostMessagesToQueuesWorker(String nodeKey, MessageVO message) {
		this.nodeKey = nodeKey;
		this.message = message;
	}
	
	static {
		map.put("NODE_1", "failover:(tcp://localhost:61616)?randomize=false&timeout=30000");
		map.put("F-N-1", "failover:(tcp://10.19.0.221:61616)?randomize=false&timeout=30000");
		factory.registerQueueType("AMQ", AMQProducerFactory.class);
		
		//JMSProducer jms = new JMSProducer();
	
	}

	
	@Override
	public void run() {
		try {
				String url = map.get(nodeKey);
				IProducer producer = factory.getProducer(url, "AMQ");	
				boolean status= producer.postMessage(message);
				logger.info("Completed sending messages to key : {} with status : {}",
						new Object[] { nodeKey, status });
				
				
				/*
				//logger.info("sending message to queue : {}, url : {}, type : {} ", new Object[] { name, url, type });
					// Producer producer = factory.getProducer(url);
					// message.setQueueName(name);
					//Collection<String> coll = new ArrayList<>();
					//coll.add("Consumer.events.VirtualTopic.gps");
					//coll.add("Consumer.psl.VirtualTopic.gps");
					//coll.add("Consumer.www.VirtualTopic.gps");				
					//IProducer producer = factory.getProducer(url,coll, "AMQ");
				//boolean status = producer.postMessage(message);	
				
				MessageVO msg1 = new GPSMessageVO();
		        msg1.setQueueName("NODE_1");
				msg1.setTopicName("VirtualTopic.gps");
				List<MessageVO> list = new ArrayList<>();
				list.add(msg1);
				list.add(msg1);
			//	boolean status = producer.publishMessage(list); */
				
		} catch (Exception e) {
			logger.error("exception occured while posting to queue ", e.getMessage(), e);
			logger.error(e.getMessage(), e);

		}
	}
}

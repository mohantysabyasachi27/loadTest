package activemq;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.jms.JMSException;

public class ProducerFactory {

	private Map<String, Producer> producerMap = new ConcurrentHashMap<String, Producer>();
	
	public Producer getProducer(String jmsUrl) {
	
		Producer producer =  producerMap.get(jmsUrl);
		if (producer == null) {
			try {
				producer = new Producer(jmsUrl);
				producerMap.putIfAbsent(jmsUrl, producer);
			} catch (JMSException e) {
				throw new RuntimeException("Failed to create AMQ producer for url :" + jmsUrl);
			}
		}
		return producer;

	}
	
	
}

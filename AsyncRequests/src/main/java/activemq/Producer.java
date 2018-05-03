package activemq;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.jms.pool.PooledConnectionFactory;

import com.azuga.locationservice.model.MessageVO;

public class Producer {

	ActiveMQConnectionFactory connectionFactory;
	PooledConnectionFactory pooledConnectionFactory;
	Connection connection;
	Session session;
	Map<String, MessageProducer> messageProducers = new ConcurrentHashMap<>();
	String jmsUrl;

	public Producer(final String jmsUrl) throws JMSException {
		this.jmsUrl = jmsUrl;
		connectionFactory = new ActiveMQConnectionFactory(jmsUrl);
		pooledConnectionFactory = new PooledConnectionFactory();
		pooledConnectionFactory.setConnectionFactory(connectionFactory);
		pooledConnectionFactory.setMaxConnections(10);
		pooledConnectionFactory.setMaximumActiveSessionPerConnection(5);
	}

	public void getSession() {
		try {
			connection = pooledConnectionFactory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public void destroy() {
		pooledConnectionFactory.stop();

	}

	public MessageProducer getProducer(String queueName) throws JMSException {
		MessageProducer producer = messageProducers.get(queueName);
		if (producer == null) {
			Destination producerDestination = session.createQueue(queueName);
			producer = session.createProducer(producerDestination);
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);
			messageProducers.putIfAbsent(queueName, producer);
		}
		return producer;
	}

	public boolean postMessage(MessageVO messageVO) {
		MessageProducer producer;
		try {
			producer = getProducer(messageVO.getQueueName());
			Message producerMessage = session.createObjectMessage(messageVO);
			producer.send(producerMessage);
			System.out.println("Message sent to the Queue." + messageVO.getQueueName());
		} catch (JMSException e) {
			return false;
		} finally {
			try {
				session.close();
				connection.close();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

}

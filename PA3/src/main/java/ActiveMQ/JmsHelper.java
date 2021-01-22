package ActiveMQ;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.jmx.DestinationViewMBean;

import javax.jms.*;
import java.io.Serializable;

public class JmsHelper {
    public static final String URL = "tcp://localhost:61616?jms.prefetchPolicy.queuePrefetch=15";
    public static final String QUEUE_SORTED = "sorted";
    public static final String QUEUE_PARTIALLY_SORTED = "partially-sorted";
    public static final String QUEUE_UNSORTED = "unsorted";
    public static final ConnectionFactory CONNECTION_FACTORY = new ActiveMQConnectionFactory(URL);

    public static void sendObjectEvent(String queueName, Object object) throws JMSException {
        Connection connection = CONNECTION_FACTORY.createConnection();
        try {
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(queueName);
            MessageProducer producer = session.createProducer(destination);
            ObjectMessage myObjMsg = session.createObjectMessage((Serializable) object);
            producer.send(myObjMsg);
        } catch (JMSException exception) {
            System.err.println("Couldn't send JMS message." + exception);
        }
    }

    public static Object retrieveObjectEvent(String queueName) throws JMSException {
        Connection connection = CONNECTION_FACTORY.createConnection();
        Object object = null;
        try {
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(queueName);
            MessageConsumer consumer = session.createConsumer(destination);
            ObjectMessage message = (ObjectMessage) consumer.receive();
            object = message.getObject();
        } catch (JMSException exception) {
            System.err.println("Couldn't retrieve JMS message." + exception);
        }
        return object;
    }

    public static void setMessageListener(String queueName, MessageListener object) throws JMSException {
        Connection connection = CONNECTION_FACTORY.createConnection();
        try {
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(queueName);
            MessageConsumer consumer = session.createConsumer(destination);
            consumer.setMessageListener(object);
        } catch (JMSException exception) {
            System.err.println("Couldn't retrieve JMS message." + exception);
        }
    }

//    public static void clearQueues() throws JMSException {
//        Connection connection = CONNECTION_FACTORY.createConnection();
//        try {
//
//            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//
//            Destination destination = session.createQueue(JmsHelper.QUEUE_UNSORTED);
//            DestinationViewMBean unsorted = session.createQueue(JmsHelper.QUEUE_UNSORTED);
//
//            MessageConsumer consumer = session.createConsumer(destination);
//            consumer.setMessageListener(object);
//        } catch (JMSException exception) {
//            System.err.println("Couldn't retrieve JMS message." + exception);
//        }
//    }
}

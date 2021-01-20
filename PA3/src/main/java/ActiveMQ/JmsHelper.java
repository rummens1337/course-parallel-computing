package ActiveMQ;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.Serializable;

public class JmsHelper {
    public static final String URL = "tcp://localhost:61616";
    public static final String QUEUE_SORTED = "sorted";
    public static final String QUEUE_UNSORTED = "unsorted";
    public static final ConnectionFactory CONNECTION_FACTORY = new ActiveMQConnectionFactory(URL);

    public static void sendEvent(String queueName, Object object) throws JMSException {
        Connection connection = CONNECTION_FACTORY.createConnection();
        try {
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(queueName);
            MessageProducer producer = session.createProducer(destination);
            ObjectMessage myObjMsg = session.createObjectMessage((Serializable) object);
            producer.send(myObjMsg);
            connection.close();
        } catch (JMSException exception) {
            System.err.println("Couldn't send JMS message." + exception);
        }finally{
            if (connection != null) {
                try {
                    connection.close();
                }catch(JMSException exception) {
                    System.err.println("Couldn't close JMSConnection: " + exception);
                }
            }
        }
    }

    public static Object retrieveEvent(String queueName) throws JMSException {
        Connection connection = CONNECTION_FACTORY.createConnection();
        Object object = null;
        try {
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(queueName);
            MessageConsumer consumer = session.createConsumer(destination);
            ObjectMessage message = (ObjectMessage) consumer.receive();
            object = message.getObject();
            connection.close();
        } catch (JMSException exception) {
            System.err.println("Couldn't send JMS message." + exception);
        }finally{
            if (connection != null) {
                try {
                    connection.close();
                }catch(JMSException exception) {
                    System.err.println("Couldn't close JMSConnection: " + exception);
                }
            }
        }
        return object;
    }
}

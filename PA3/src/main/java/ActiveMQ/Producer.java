package ActiveMQ;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;

public class Producer {

    public Producer() throws JMSException, NamingException {
        // Create environmental properties
        Properties props = new Properties();
        props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        props.setProperty(Context.PROVIDER_URL, "tcp://127.0.0.1:61616");
        props.setProperty("queue.MyQueue", "example.MyQueue");
        props.setProperty("topic.MyTopic", "example.MyTopic");
        javax.naming.Context ctx = new InitialContext(props);

        // Obtain a JNDI connection
        javax.jms.TopicConnectionFactory factory = (javax.jms.TopicConnectionFactory)ctx.lookup("ConnectionFactory");

        // Look up a JMS connection factory
        ConnectionFactory conFactory = (ConnectionFactory) ctx.lookup("ConnectionFactory");
        Connection connection;

        // Getting JMS connection from the server and starting it
        connection = conFactory.createConnection();
        try {
            connection.start();

            // JMS messages are sent and received using a Session. We will
            // create here a non-transactional session object. If you want
            // to use transactions you should set the first parameter to 'true'
            Session session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);

            Destination destination = (Destination) ctx.lookup("MyQueue");

            // MessageProducer is used for sending messages (as opposed
            // to MessageConsumer which is used for receiving them)
            MessageProducer producer = session.createProducer(destination);

            // We will send a small text message saying 'Hello World!'
            TextMessage message = session.createTextMessage("Hello World!");

            // Here we are sending the message!
            producer.send(message);
            System.out.println("Sent message '" + message.getText() + "'");
        } finally {
            connection.close();
        }
    }

    public static void main(String[] args) throws JMSException {
        try {
            BasicConfigurator.configure();
            new Producer();
        } catch (NamingException e) {
            e.printStackTrace();
        }

    }
}
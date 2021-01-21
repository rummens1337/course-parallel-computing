package ActiveMQ;

import javax.jms.*;

import java.net.URI;
import java.net.URISyntaxException;

import Quicksort.GenerateData;
import Quicksort.QuicksortParallel;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;

public class Consumer implements MessageListener{
    private int counter;

    public Consumer(){
        try {
            this.counter = 0;
            JmsHelper.setMessageListener(JmsHelper.QUEUE_UNSORTED, this);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    /**
     * Validates whether an array is correctly sorted.
     * Exits program if not valid.
     * @param numbers array of int values
     */
    private static void validate(int[] numbers) {
        boolean error = false;
        for (int i = 0; i < numbers.length - 1; i++) {
            if (numbers[i] > numbers[i + 1]) {
                error = true;
                break;
            }
        }

        if(error) {
            System.err.println(Thread.currentThread().getStackTrace()[1]);
            System.err.println("Array was not correctly sorted!");
            System.exit(-1);
        }
    }

    @Override
    public void onMessage(Message m) {
        try {
            if (m instanceof ObjectMessage) {
                ObjectMessage message = (ObjectMessage) m;
                QuicksortData qsd = (QuicksortData) message.getObject();
                QuicksortParallel qsp = new QuicksortParallel();

                long start = System.nanoTime();
                qsp.sort(qsd.getArray());
                long end = System.nanoTime();
                long durationMs = (end - start) / 1000000;

                JmsHelper.sendObjectEvent(JmsHelper.QUEUE_SORTED, qsd);

                System.out.println("Duration MS: " + durationMs + " Value: " + qsd.getHigh() + " Count: " + this.counter);
            } else {
                System.out.println("Unable to handle onMessage");
            }
        }catch(JMSException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Consumer();
        Thread.currentThread().join();
    }
}
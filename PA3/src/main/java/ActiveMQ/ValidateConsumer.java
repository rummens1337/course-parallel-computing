package ActiveMQ;

import Quicksort.QuicksortParallel;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.util.Arrays;

import static java.lang.System.exit;

public class ValidateConsumer implements MessageListener, Runnable{
    private int counter;
    private int [] sortedArray;

    public ValidateConsumer(int arrayLength){
        try {
            this.counter = 0;
            this.sortedArray = new int [arrayLength];
            JmsHelper.setMessageListener(JmsHelper.QUEUE_PARTIALLY_SORTED, this);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(Message m) {
        try {
            if (m instanceof ObjectMessage) {
                ObjectMessage message = (ObjectMessage) m;
                QuicksortData qsd = (QuicksortData) message.getObject();
                long start = System.nanoTime();

                for (int i = qsd.getLow(); i < qsd.getHigh() + 1; i++) {
                    this.sortedArray[i] = qsd.getArray()[i];
                }

                if (QuicksortData.validateWithoutError(this.sortedArray)) {
                    QuicksortData sortedArray = new QuicksortData(this.sortedArray, 0, 0);
                    JmsHelper.sendObjectEvent(JmsHelper.QUEUE_SORTED, sortedArray);
                    exit(0); // Exit result when array is validated.
                }

                long end = System.nanoTime();
                long durationMs = (end - start) / 1000000;

                System.out.println("Duration MS: " + durationMs + " Low: " + qsd.getLow() + " High: " + qsd.getHigh() + " Count: " + this.counter);
            } else {
                System.out.println("Unable to handle onMessage");
            }
        }catch (JMSException e){
            e.printStackTrace();
        }
    }



    @Override
    public void run() {
    }
}
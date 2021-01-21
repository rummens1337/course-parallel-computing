package ActiveMQ;

import javax.jms.*;

import java.util.Arrays;

import Quicksort.QuicksortParallel;

public class SortConsumer implements MessageListener{
    private int counter;

    public SortConsumer(){
        try {
            this.counter = 0;
            JmsHelper.setMessageListener(JmsHelper.QUEUE_UNSORTED, this);
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
                QuicksortParallel qsp = new QuicksortParallel();

                long start = System.nanoTime();
                qsp.sort(qsd);
                long end = System.nanoTime();
                long durationMs = (end - start) / 1000000;
                QuicksortData.validate(Arrays.copyOfRange(qsd.getArray(), qsd.getLow(), qsd.getHigh()));

                JmsHelper.sendObjectEvent(JmsHelper.QUEUE_PARTIALLY_SORTED, qsd);

                System.out.println("Duration MS: " + durationMs + " low: " +  qsd.getLow()  + " High: " + qsd.getHigh() + " Count: " + this.counter);
            } else {
                System.out.println("Unable to handle onMessage");
            }
        }catch(JMSException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new SortConsumer();
        Thread.currentThread().join();
    }
}
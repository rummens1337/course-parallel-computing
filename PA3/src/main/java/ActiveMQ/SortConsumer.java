package ActiveMQ;

import javax.jms.*;

import Quicksort.QuicksortDistributed;

public class SortConsumer implements MessageListener{

    public SortConsumer(){
        try {
            JmsHelper.setMessageListener(JmsHelper.QUEUE_UNSORTED, this);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(Message m) {
        try {
            if (m instanceof ObjectMessage) {
                // Retrieve message and fill object.
                ObjectMessage message = (ObjectMessage) m;
                QuicksortData qsd = (QuicksortData) message.getObject();
                QuicksortDistributed quicksortDistributed = new QuicksortDistributed();

                // Some actual sorting.
                quicksortDistributed.sort(qsd);

                // Below statement could be used to validate the partial array, but not a single time did it fail.
                // Therefore it is commented.
                // QuicksortData.validate(Arrays.copyOfRange(qsd.getArray(), qsd.getLow(), qsd.getHigh()));

                JmsHelper.sendObjectEvent(JmsHelper.QUEUE_PARTIALLY_SORTED, qsd);
            } else {
                System.out.println("Unable to handle onMessage SortConsumer.");
            }
        }catch(JMSException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new SortConsumer();
        Thread.currentThread().join(); // Join on itself to run till keyboard interrupt.
    }
}
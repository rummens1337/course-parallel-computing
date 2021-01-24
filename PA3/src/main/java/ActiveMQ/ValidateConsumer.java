package ActiveMQ;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

public class ValidateConsumer implements MessageListener, Runnable {
    private static boolean[] checkSortedArray;
    private static int[] sortedArray;

    public ValidateConsumer(int arrayLength) {
        try {
            // Static arrays are shared among validators, so check if already exists before creating.
            // Validators could also be decoupled from main program, and a topic could be used to communicate amongst
            // validators, whether a certain range has already been validated. However, for the scope of this project
            // that was left out.
            if (sortedArray == null) {
                sortedArray = new int[arrayLength];
                checkSortedArray = new boolean[arrayLength];
            }

            // Fill boolean array with false, this can later be checked in a way faster manner than checking the whole
            // array whether i > i+1
            java.util.Arrays.fill(checkSortedArray, false);
            JmsHelper.setMessageListener(JmsHelper.QUEUE_PARTIALLY_SORTED, this);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(Message m) {
        // Start counting directly when message is received, till when it is handled.
        long start = System.nanoTime();

        try {
            if (m instanceof ObjectMessage) {
                ObjectMessage message = (ObjectMessage) m;
                QuicksortData qsd = (QuicksortData) message.getObject();

                for (int i = qsd.getLow(); i < qsd.getHigh() + 1; i++) {
                    sortedArray[i] = qsd.getArray()[i];
                    checkSortedArray[i] = true;
                }

                // Return once an element has not been checked yet (value will be false).
                for (boolean elem : checkSortedArray) {
                    if (!elem)
                        return;
                }

                // Only validate entire array once every element has been sorted.
                if (QuicksortData.validateWithoutError(sortedArray)) {
                    QuicksortData sortedArray = new QuicksortData(ValidateConsumer.sortedArray, 0, 0);
                    JmsHelper.sendObjectEvent(JmsHelper.QUEUE_SORTED, sortedArray);
                    return; // Exit when array is validated.
                }
            } else {
                System.out.println("Unable to handle onMessage ValidateConsumer. Are you sending the correct object?");
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }

        long end = System.nanoTime();
        long durationMs = (end - start) / 1000000;
        System.out.println("Duration MS: " + durationMs);
    }


    @Override
    public void run() {
    }
}
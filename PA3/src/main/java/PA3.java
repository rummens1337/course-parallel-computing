import ActiveMQ.JmsHelper;
import ActiveMQ.QuicksortData;
import ActiveMQ.ValidateConsumer;
import Quicksort.GenerateData;
import Quicksort.Quicksort;

import javax.jms.JMSException;

import static java.lang.System.exit;

/**
 * ASSIGNMENT
 * Design and implement a threads and locks solution for your case study algorithm.
 * Create a benchmark to collect performance metrics for the various solutions.
 * Use several differently sized data sets (eg. N, 2N, 4N, 8N) to verify the expected runtime complexity.
 * <p>
 * Confirm the validity of, and collect performance metrics for, your threads and locks solution using different numbers
 * of threads (1, 2, 4, 8). Present your results in a graph and compare with the results of the sequential solution.
 * Analyze and discuss the results and diagnose and explain significant deviations from the expected behavior as
 * calculated during the design phase.
 */
public class PA3 {
    private static final int ARRAY_SIZE = 10000000;
    private static final int ARRAY_SEED = 1337;
    private static final int TIME_DIVISION_MS = 1000000;

    public static void main(String[] args) throws JMSException {
        // Execute regular quicksort to get reference array
        long startRegular = System.nanoTime();
        int [] regularArray = GenerateData.randomSeededArray(ARRAY_SIZE, ARRAY_SEED);
        Quicksort quicksort = new Quicksort();
        quicksort.sort(regularArray);
        long endRegular = System.nanoTime();
        long durationMsRegular = (endRegular - startRegular) / TIME_DIVISION_MS;

        // Generate dataset for distributed version
        int[] array = GenerateData.randomSeededArray(ARRAY_SIZE, ARRAY_SEED);
        QuicksortData qd = new QuicksortData(array, 0, array.length - 1);

        // Start time counter for total
        long start = System.nanoTime();

        // Send generated data object to the unsorted queue.
        JmsHelper.sendObjectEvent(JmsHelper.QUEUE_UNSORTED, qd);

        // Start amount of validators needed.
        // Are started from within main program, but could be decoupled with some extra orchestration of data.
        // For sake of this project, validators are bound to the main program as this reduces overhead of syncing -
        // the validation.
        startValidators(new ValidateConsumer(array.length), 1);

        // Call retrieveObjectEvent, which is a busy-wait function that waits for input in the sorted queue.
        // This essentially waits until the entire array is sorted.
        QuicksortData qsd = (QuicksortData) JmsHelper.retrieveObjectEvent(JmsHelper.QUEUE_SORTED);
        long end = System.nanoTime();
        long durationMsDistributed = (end - start) / TIME_DIVISION_MS;

        // Validate (once more) that the array is properly sorted.
        QuicksortData.validate(qsd.getArray());
        QuicksortData.validateEquallySorted(qsd.getArray(), regularArray);

        System.out.println("Duration Distributed: " + durationMsDistributed);
        System.out.println("Duration Regular: " + durationMsRegular);
        System.out.println("SUCCESS. Validated entire array was correctly sorted.");

        // Manually exit the program.
        exit(0);
    }

    public static void startValidators(ActiveMQ.ValidateConsumer vc, int amount){
        for (int i = 0; i < amount; i++) {
            Thread validator = new Thread(vc);
            validator.start();
        }
    }
}

package Quicksort;

import ActiveMQ.JmsHelper;
import ActiveMQ.QuicksortData;

import javax.jms.JMSException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Quicksort method te calculate speed of quicksort.
 *
 * DISCLAIMER
 * This is NOT fully our own work, and is partially copied from
 * https://www.vogella.com/tutorials/JavaAlgorithmsQuicksortParallel/article.html
 * Writing our own Quicksort method is not required per assignment, and is thus copied for sake of ease.
 */
public class QuicksortParallel extends Thread {
    private int nrOfThreads = Runtime.getRuntime().availableProcessors();
    private AtomicInteger nrOfStartedThreads;
    private Semaphore sem;
    private volatile int [] numbers;
    private QuicksortData quicksortData;

//    public void sort(int [] values, int low, int high, int nrOfThreads){
//        this.nrOfThreads = nrOfThreads;
//        sort(values, low, high);
//    }
    public void sort(QuicksortData quicksortData) {
        this.quicksortData = quicksortData;
        int [] values = quicksortData.getArray();
        int low = quicksortData.getLow();
        int high = quicksortData.getHigh();

        // check for empty or null array
        if (values ==null || values.length==0){
            return;
        }

        this.numbers = values;
        int number = values.length;

        sem = new Semaphore(nrOfThreads - 1); // already count current thread
        nrOfStartedThreads = new AtomicInteger(0);

        try {
            // Count current thread as new thread.
            nrOfStartedThreads.incrementAndGet();
            quicksort(low, high);
        } catch (InterruptedException | JMSException e) {
            e.printStackTrace();
        }
    }

    private void quicksort(int low, int high) throws InterruptedException, JMSException {
        int i = low;
        int j = high;
        // Get the pivot element from the middle of the list
        int pivot = numbers[low + (high-low)/2];

        // Divide into two lists
        while (i <= j) {
            // If the current value from the left list is smaller than the pivot
            // element then get the next element from the left list
            while (numbers[i] < pivot) {
                i++;
            }
            // If the current value from the right list is larger than the pivot
            // element then get the next element from the right list
            while (numbers[j] > pivot) {
                j--;
            }

            // If we have found a value in the left list which is larger than
            // the pivot element and if we have found a value in the right list
            // which is smaller than the pivot element then we exchange the
            // values.
            // As we are done we can increase i and j
            if (i <= j) {
                exchange(i, j);
                i++;
                j--;
            }
        }

        // If max enough items for another client, push high to unsorted queue
        if (high - low > 10000) {
            QuicksortData quicksortData = new QuicksortData(numbers, i, high);
            JmsHelper.sendObjectEvent(JmsHelper.QUEUE_UNSORTED ,quicksortData);
            if (low < j) {
                this.quicksortData.setLow(low);
                this.quicksortData.setHigh(j);
                quicksort(low, j);
            }
        }else{
            if (low < j)
                quicksort(low, j);
            if (i < high)
                quicksort(i, high);
        }
    }

    private synchronized void exchange(int i, int j) {
        int temp = numbers[i];
        numbers[i] = numbers[j];
        numbers[j] = temp;
    }

    public int getNrOfStartedThreads() {
        return nrOfStartedThreads.intValue();
    }
}
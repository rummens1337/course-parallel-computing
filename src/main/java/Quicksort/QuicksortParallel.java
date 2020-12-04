package Quicksort;

import java.util.concurrent.Semaphore;

/**
 * Quicksort method te calculate speed of quicksort.
 *
 * DISCLAIMER
 * This is NOT fully our own work, and is partially copied from
 * https://www.vogella.com/tutorials/JavaAlgorithmsQuicksortParallel/article.html
 * Writing our own Quicksort method is not required per assignment, and is thus copied for sake of ease.
 */
public class QuicksortParallel extends Thread {

    final int MAX_THREADS = Runtime.getRuntime().availableProcessors();
    private Semaphore sem;
    private int [] numbers;
    private int number;

    public void sort(int [] values) {
        // check for empty or null array
        if (values ==null || values.length==0){
            return;
        }
        this.numbers = values;
        number = values.length;
        sem = new Semaphore(MAX_THREADS);

        try {
            quicksort(0, number - 1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void quicksort(int low, int high) throws InterruptedException {
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

        // If max threads not used, start thread for sorting
        if (sem.tryAcquire()) {
            // Create semi-final variable x, needed in lambda expression.
            int x = i;
            sem.acquire();

            new Thread(new QuicksortParallel()).start();
            Thread t1 = new Thread(() -> {
                try {
                    // run quicksort here
                    if (x < high) {
                        quicksort(x, high);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            if (low < j)
                quicksort(low, j);

            t1.start();
        }
    }

    private void exchange(int i, int j) {
        int temp = numbers[i];
        numbers[i] = numbers[j];
        numbers[j] = temp;
    }
}
package Quicksort;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * QuicksortParallelTest to check correct implementation of QuicksortParallel.
 *
 * DISCLAIMER
 * This is NOT fully our own work, and is partially copied from
 * https://www.vogella.com/tutorials/JavaAlgorithmsQuicksortParallel/article.html
 * Writing our own Quicksort method is not required per assignment, and is thus copied for sake of ease.
 */
public class QuicksortParallelTest {

    private final static int SIZE = 500000;
    private final static int SEED = 1337;

    @BeforeAll
    static void setUp() {
        System.out.println("\n\n#### QuicksortParallel tests ####");
    }

    @Test
    public void testNull() {
        System.out.println("Run parallel quicksort with 'null' as argument");
        QuicksortParallel sorter = new QuicksortParallel();
        sorter.sort(null);
    }

    @Test
    public void testEmpty() {
        System.out.println("Run parallel quicksort with an empty data set");
        QuicksortParallel sorter = new QuicksortParallel();
        sorter.sort(new int[0]);
    }

    @Test
    public void testSimpleElement() {
        System.out.println("Run parallel quicksort with a data set of 1 element");
        QuicksortParallel sorter = new QuicksortParallel();
        int[] test = new int[1];
        test[0] = 5;
        sorter.sort(test);
    }

    @Test
    public void testSpecial() {
        System.out.println("Run parallel quicksort with duplicate data array");
        QuicksortParallel sorter = new QuicksortParallel();
        int[] test = { 5, 5, 6, 6, 4, 4, 5, 5, 4, 4, 6, 6, 5, 5 };
        sorter.sort(test);
        if (!validate(test)) {
            fail("Array was not correctly sorted.");
        }
    }

    @Test
    public void testQuicksortParallel() {
        System.out.println("Run parallel quicksort with max amount of threads");
        QuicksortParallel sorter = new QuicksortParallel();
        int [] numbers = GenerateData.randomSeededArray(SIZE, SEED);
        sorter.sort(numbers);

        if (!validate(numbers)) {
            fail("Array was not correctly sorted.");
        }
        assertTrue(true);
    }

    @Test
    public void testQuicksortParallel1Threads() {
        System.out.println("Run parallel quicksort with 1 thread");

        final int NR_OF_THREADS = 1;
        QuicksortParallel sorter = new QuicksortParallel();
        int [] numbers = GenerateData.randomSeededArray(SIZE, SEED);
        sorter.sort(numbers, NR_OF_THREADS);

        if (!validate(numbers)) {
            fail("Array was not correctly sorted.");
        }

        assertEquals(NR_OF_THREADS, sorter.getNrOfStartedThreads());
        assertTrue(true);
    }

    @Test
    public void testQuicksortParallel2Threads() {
        System.out.println("Run parallel quicksort with 2 threads");
        final int NR_OF_THREADS = 2;
        QuicksortParallel sorter = new QuicksortParallel();
        int [] numbers = GenerateData.randomSeededArray(SIZE, SEED);
        sorter.sort(numbers, NR_OF_THREADS);

        if (!validate(numbers)) {
            fail("Array was not correctly sorted.");
        }

        assertEquals(NR_OF_THREADS, sorter.getNrOfStartedThreads());
        assertTrue(true);
    }

    @Test
    public void testQuicksortParallel4Threads() {
        System.out.println("Run parallel quicksort with 4 threads");
        final int NR_OF_THREADS = 4;
        QuicksortParallel sorter = new QuicksortParallel();
        int [] numbers = GenerateData.randomSeededArray(SIZE, SEED);
        sorter.sort(numbers, NR_OF_THREADS);

        if (!validate(numbers)) {
            fail("Array was not correctly sorted.");
        }

        assertEquals(NR_OF_THREADS, sorter.getNrOfStartedThreads());
        assertTrue(true);
    }

    @Test
    public void testQuicksortParallel8Threads() {
        System.out.println("Run parallel quicksort with 8 threads");
        final int NR_OF_THREADS = 8;
        QuicksortParallel sorter = new QuicksortParallel();
        int [] numbers = GenerateData.randomSeededArray(SIZE, SEED);
        sorter.sort(numbers, NR_OF_THREADS);

        if (!validate(numbers)) {
            fail("Array was not correctly sorted.");
        }

        assertEquals(NR_OF_THREADS, sorter.getNrOfStartedThreads());
        assertTrue(true);
    }

    /**
     * Validates whether an array is correctly sorted.
     * @param numbers array of int values
     * @return Boolean whether the array was correctly sorted.
     */
    private boolean validate(int[] numbers) {
        for (int i = 0; i < numbers.length - 1; i++) {
            if (numbers[i] > numbers[i + 1]) {
                return false;
            }
        }
        return true;
    }
}
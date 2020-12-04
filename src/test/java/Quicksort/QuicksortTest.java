package Quicksort;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * QuicksortTest to check correct implementation of quicksort.
 *
 * DISCLAIMER
 * This is NOT fully our own work, and is partially copied from
 * https://www.vogella.com/tutorials/JavaAlgorithmsQuicksortParallel/article.html
 * Writing our own Quicksort method is not required per assignment, and is thus copied for sake of ease.
 */
public class QuicksortTest {

    private static int[] numbers;
    private final static int SIZE = 2000000;
    private final static int SEED = 1337;

    @BeforeAll
    static void setUp() {
        System.out.println("#### Quicksort tests ####");
        numbers = GenerateData.randomSeededArray(SIZE, SEED);
    }

    @Test
    public void testNull() {
        Quicksort sorter = new Quicksort();
        sorter.sort(null);
    }

    @Test
    public void testEmpty() {
        Quicksort sorter = new Quicksort();
        sorter.sort(new int[0]);
    }

    @Test
    public void testSimpleElement() {
        Quicksort sorter = new Quicksort();
        int[] test = new int[1];
        test[0] = 5;
        sorter.sort(test);
    }

    @Test
    public void testSpecial() {
        Quicksort sorter = new Quicksort();
        int[] test = { 5, 5, 6, 6, 4, 4, 5, 5, 4, 4, 6, 6, 5, 5 };
        sorter.sort(test);
        if (!validate(test)) {
            fail("Array was not correctly sorted.");
        }
    }

    @Test
    public void testQuickSort() {
        long startTime = System.currentTimeMillis();

        Quicksort sorter = new Quicksort();
        sorter.sort(numbers);

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("Quicksort time elapsed: " + elapsedTime + " milliseconds");

        if (!validate(numbers)) {
            fail("Array was not correctly sorted.");
        }
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
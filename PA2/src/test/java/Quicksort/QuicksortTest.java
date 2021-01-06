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

    private final static int SIZE = 50000;
    private final static int SEED = 1337;

    @BeforeAll
    static void setUp() {
        System.out.println("\n\n#### Quicksort tests ####");
    }

    @Test
    public void testNull() {
        System.out.println("Run quicksort with 'null' as argument");
        Quicksort sorter = new Quicksort();
        sorter.sort(null);
    }

    @Test
    public void testEmpty() {
        System.out.println("Run quicksort with an empty data set");
        Quicksort sorter = new Quicksort();
        sorter.sort(new int[0]);
    }

    @Test
    public void testSimpleElement() {
        System.out.println("Run quicksort with a data set of 1 element");
        Quicksort sorter = new Quicksort();
        int[] test = new int[1];
        test[0] = 5;
        sorter.sort(test);
    }

    @Test
    public void testSpecial() {
        System.out.println("Run quicksort with duplicate data array");
        Quicksort sorter = new Quicksort();
        int[] test = { 5, 5, 6, 6, 4, 4, 5, 5, 4, 4, 6, 6, 5, 5 };
        sorter.sort(test);
        if (!validate(test)) {
            fail("Array was not correctly sorted.");
        }
    }

    @Test
    public void testQuickSort() {
        System.out.println("Run quicksort with generated data set");
        int [] numbers = GenerateData.randomSeededArray(SIZE, SEED);
        Quicksort sorter = new Quicksort();
        sorter.sort(numbers);

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
package Quicksort;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * QuicksortParallelTest to check correct implementation of QuicksortParallel.
 *
 * DISCLAIMER
 * This is NOT fully our own work, and is partially copied from
 * https://www.vogella.com/tutorials/JavaAlgorithmsQuicksortParallel/article.html
 * Writing our own Quicksort method is not required per assignment, and is thus copied for sake of ease.
 */
public class QuicksortParallelTest {

    private final static int SIZE = 50000;
    private final static int SEED = 1337;

    @BeforeAll
    static void setUp() {
        System.out.println("#### QuicksortParallel tests ####");
    }

    @Test
    public void testNull() {
        QuicksortParallel sorter = new QuicksortParallel();
        sorter.sort(null);
    }

    @Test
    public void testEmpty() {
        QuicksortParallel sorter = new QuicksortParallel();
        sorter.sort(new int[0]);
    }

    @Test
    public void testSimpleElement() {
        QuicksortParallel sorter = new QuicksortParallel();
        int[] test = new int[1];
        test[0] = 5;
        sorter.sort(test);
    }

    @Test
    public void testSpecial() {
        QuicksortParallel sorter = new QuicksortParallel();
        int[] test = { 5, 5, 6, 6, 4, 4, 5, 5, 4, 4, 6, 6, 5, 5 };
        sorter.sort(test);
        if (!validate(test)) {
            fail("Array was not correctly sorted.");
        }
    }

    @Test
    public void testQuicksortParallel() {
        QuicksortParallel sorter = new QuicksortParallel();
        int [] numbers = GenerateData.randomSeededArray(SIZE, SEED);
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
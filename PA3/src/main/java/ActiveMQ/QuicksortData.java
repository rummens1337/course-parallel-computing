package ActiveMQ;

import java.io.Serializable;

public class QuicksortData implements Serializable {

    private int[] array;
    private int low;
    private int high;

    public QuicksortData(int[] array, int low, int high) {
        this.array = array;
        this.low = low;
        this.high = high;
    }

    public int getHigh() {
        return high;
    }

    public void setHigh(int high) {
        this.high = high;
    }

    public int getLow() {
        return low;
    }

    public void setLow(int low) {
        this.low = low;
    }

    public int[] getArray() {
        return array;
    }

    public void setArray(int[] array) {
        this.array = array;
    }

    /**
     * Validates whether an array is correctly sorted.
     * Exits program if not valid.
     *
     * @param numbers array of int values
     */
    public static void validate(int[] numbers) {
        boolean error = false;
        for (int i = 0; i < numbers.length - 1; i++) {
            if (numbers[i] > numbers[i + 1]) {
                error = true;
                break;
            }
        }

        if (error) {
            System.err.println(Thread.currentThread().getStackTrace()[1]);
            System.err.println("Array was not correctly sorted!");
            System.exit(-1);
        }
    }

    /**
     * Validates whether an array is correctly sorted.
     * Exits program if not valid.
     *
     * @param numbers array of int values
     */
    public static boolean validateWithoutError(int[] numbers) {
        for (int i = 0; i < numbers.length - 1; i++) {
            if (numbers[i] > numbers[i + 1]) {
                System.out.println("Failure on iteration " + i + " of the target array. Value was: " + numbers[i + 1]);
                return false;
            }
        }

        return true;
    }

    /**
     * Validates whether two arrays are sorted in the same manner.
     * Exits program if not valid.
     * @param sourceArray array to be compared
     * @param targetArray array to be compared against
     */
    public static void validateEquallySorted(int[] sourceArray, int[] targetArray) {
        boolean error = false;

        if(sourceArray.length == targetArray.length){
            for (int i = 0; i < sourceArray.length - 1; i++) {
                if (!(sourceArray[i] == targetArray[i])) {
                    error = true;
                    break;
                }
            }
        }

        if(error) {
            System.err.println(Thread.currentThread().getStackTrace()[1]);
            System.err.println("sourceArray was not correctly sorted in comparison to the targetArray!");
            System.exit(-1);
        }
    }
}

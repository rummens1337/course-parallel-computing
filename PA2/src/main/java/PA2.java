import Quicksort.GenerateData;
import Quicksort.Quicksort;
import Quicksort.QuicksortParallel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.io.File;

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
public class PA2 {
    static volatile int[][] dataArrayParallel;

    public static void main(String[] args) throws InterruptedException {

        int[] nrOfCores = {2, 4, 8};
        XYSeriesCollection dataset = new XYSeriesCollection();
        final int START_AMOUNT = 5000000;
        final int ITERATIONS = 5;

        // Create and instantiate parallel
        QuicksortParallel qsp = new QuicksortParallel();

        // Create and instantiate regular
        Quicksort qs = new Quicksort();
        XYSeries qsData = new XYSeries("Regular Quicksort");
        int[][] dataArrayRegular = GenerateData.twoDimensionalDataArray(START_AMOUNT, ITERATIONS);

//        // Sort and calculate regular
        for (int[] array : dataArrayRegular) {
            Thread.sleep(3000);
            long start = System.nanoTime();
            qs.sort(array);
            long end = System.nanoTime();
            long durationMs = (end - start) / 1000000;
            validate(array); // validate sorted array.
            System.out.println(durationMs);
            qsData.add(array.length, durationMs);
        }

        dataset.addSeries(qsData);

//         Sort and calculate parallel
        for(int cores : nrOfCores) {
            dataArrayParallel = GenerateData.twoDimensionalDataArray(START_AMOUNT, ITERATIONS);
            XYSeries qspData = new XYSeries("Parallel Quicksort: " + cores);

            for (int i = 0; i < dataArrayParallel.length; i++) {
                Thread.sleep(3000);
                long start = System.nanoTime();
                qsp.sort(dataArrayParallel[i], cores);
                long end = System.nanoTime();
                long durationMs = (end - start) / 1000000;
                validate(dataArrayParallel[i]); // validate sorted array.
                validateEquallySorted(dataArrayParallel[i], dataArrayRegular[i]); // validate against regular quicksort
                System.out.println(durationMs);
                qspData.add(dataArrayParallel[i].length, durationMs);
            }
            dataset.addSeries(qspData);
        }

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Quicksort vs Parallel Quicksort",
                "Data size", "Time in Milliseconds",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        exportChartToPng("chart2.png", chart, 800, 800);
    }

    /**
     * Exports a JFreeChart object to a png in the images directory.
     *
     * @param filename Name of the file to export to
     * @param chart JFreeChart object
     * @param width Width of the target image.
     * @param height Height of the target image.
     */
    public static void exportChartToPng(String filename, JFreeChart chart, int width, int height) {
        try {
            String directoryName = "images/";

            File directory = new File(directoryName);
            if (!directory.exists()) {
                if (!directory.mkdir()) {
                    throw new Exception("Failed to create the 'images' directory");
                }
            }

            ChartUtilities.saveChartAsPNG(new File(directoryName + filename), chart, width, height);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Validates whether an array is correctly sorted.
     * Exits program if not valid.
     * @param numbers array of int values
     */
    private static void validate(int[] numbers) {
        boolean error = false;
        for (int i = 0; i < numbers.length - 1; i++) {
            if (numbers[i] > numbers[i + 1]) {
                error = true;
                break;
            }
        }

        if(error) {
            System.err.println(Thread.currentThread().getStackTrace()[1]);
            System.err.println("Array was not correctly sorted!");
            System.exit(-1);
        }
    }

    /**
     * Validates whether two arrays are sorted in the same manner.
     * Exits program if not valid.
     * @param sourceArray array to be compared
     * @param targetArray array to be compared against
     */
    private static void validateEquallySorted(int[] sourceArray, int[] targetArray) {
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
            System.err.println("Array was not correctly sorted in comparison to the regular quicksort method!");
            System.exit(-1);
        }
    }
}

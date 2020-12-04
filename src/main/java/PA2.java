import Quicksort.GenerateData;
import Quicksort.Quicksort;
import Quicksort.QuicksortParallel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.File;
import java.time.Duration;
import java.time.Instant;

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
    public static void main(String[] args) {

        QuicksortParallel qsp = new QuicksortParallel();
        Quicksort qs = new Quicksort();
        int [] data = GenerateData.randomSeededArray(50000000,1337);

//        Instant start2 = Instant.now();
//        qs.sort(data);
//        Instant end2 = Instant.now();
//        Duration timeElapsed2 = Duration.between(start2, end2);
//        System.out.println("Regular: " + timeElapsed2);

        Instant start = Instant.now();
        qsp.sort(data);
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        System.out.println("parallel: " + timeElapsed);
//        6.161098533S
//        PT6.255197507S
//        JFreeChart lineChart = ChartFactory.createLineChart(
//                "title",
//                "Years", "Number of Schools",
//                createDataset(),
//                PlotOrientation.VERTICAL,
//                true, true, false);
//
//        exportChartToPng("chart1.png", lineChart, 800, 800);
    }

//    public static void calculateTime(Object object, int [] array) throws Exception {
//        Instant start = Instant.now();
//        func.call();
//        Instant end = Instant.now();
//        Duration timeElapsed = Duration.between(start, end);
//        return func.call();
//    }

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

    private static DefaultCategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(15, "schools", "1970");
        dataset.addValue(30, "schools", "1980");
        dataset.addValue(60, "schools", "1990");
        dataset.addValue(120, "schools", "2000");
        dataset.addValue(240, "schools", "2010");
        dataset.addValue(300, "schools", "2014");
        return dataset;
    }
}

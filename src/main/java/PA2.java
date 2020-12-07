import Quicksort.GenerateData;
import Quicksort.Quicksort;
import Quicksort.QuicksortParallel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

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

    public static JFreeChart chart;

    public static void main(String[] args) throws InterruptedException {

        QuicksortParallel qsp = new QuicksortParallel();
        XYSeries qspData = new XYSeries("Parallel Quicksort");
        Quicksort qs = new Quicksort();
        XYSeries qsData = new XYSeries("Regular Quicksort");
        int[][] dataArray = GenerateData.twoDimensionalDataArray(50000, 8);
        XYSeriesCollection dataset = new XYSeriesCollection();

        for (int[] array : dataArray) {
            Thread.sleep(5000);
            Instant start = Instant.now();
            qsp.sort(array);
            Instant end2 = Instant.now();
            Duration timeElapsed2 = Duration.between(start, end2);
            qspData.add(array.length, Duration.of(timeElapsed2.getNano(), ChronoUnit.NANOS).toMillis());
        }

        dataset.addSeries(qspData);


//        Instant start2 = Instant.now();
//        qs.sort(dataArray[0]);
//        Instant end2 = Instant.now();
//        Duration timeElapsed2 = Duration.between(start2, end2);
//        System.out.println("Regular: " + timeElapsed2);
//
//        Instant start = Instant.now();
//        qsp.sort(dataArray[0]);
//
//        for (int i: dataArray[0]) {
//            System.out.println(i);
//        }
//        Instant end = Instant.now();
//        Duration timeElapsed = Duration.between(start, end);
//        System.out.println("parallel: " + timeElapsed);
//
        chart = ChartFactory.createXYLineChart(
                "Quicksort vs Parallel Quicksort",
                "Time in Milliseconds", "Data size",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        exportChartToPng("chart1.png", chart, 800, 800);
    }

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

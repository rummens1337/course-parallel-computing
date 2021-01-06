package Quicksort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public abstract class GenerateData {
    public static int[] randomSeededArray(int amount, int seed) {
        ArrayList<Integer> list = new ArrayList<>(amount);

        for (int i = 0; i <= amount - 1; i++) {
            list.add(i);
        }

        Collections.shuffle(list, new Random(seed));
        return list.stream().mapToInt(Integer::intValue).toArray();
    }

    public static int [][] twoDimensionalDataArray(int startAmount, int iterations){
        int [][] dataArray = new int[iterations][];

        for (int i = 0; i < iterations; i++) {
            System.out.println(startAmount);
            dataArray[i] = GenerateData.randomSeededArray(startAmount, 1337);
            startAmount += startAmount;
        }

        return dataArray;
    }
}
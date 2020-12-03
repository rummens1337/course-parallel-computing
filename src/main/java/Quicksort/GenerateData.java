package Quicksort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GenerateData {
    public int[] randomSeededArray(int amount, int seed) {
        ArrayList<Integer> list = new ArrayList<>(amount);

        for (int i = 0; i <= amount - 1; i++) {
            list.add(i);
        }

        Collections.shuffle(list, new Random(seed));
        return list.stream().mapToInt(Integer::intValue).toArray();
    }
}
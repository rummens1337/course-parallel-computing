package ActiveMQ;

import java.io.Serializable;

public class QuicksortData implements Serializable {

    int [] array;
    int low;
    int high;

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
}

package Leet.Solutions;

import java.util.Arrays;
import java.util.HashMap;

//1146. Snapshot Array unsolved
public class SnapshotArray {

    private Integer[] array;
    private HashMap<Integer,Integer[]> map;
    private Integer snapid;
    public SnapshotArray(int length) {
        array = new Integer[length];
        map = new HashMap<>();
        snapid = 0;
    }

    public void set(int index, int val) {
        array[index] = val;
    }

    public int snap() {
        map.put(snapid, Arrays.copyOf(array,array.length));
        return snapid++;
    }

    public int get(int index, int snap_id) {
        return map.get(Integer.valueOf(snap_id))[index];
    }
}
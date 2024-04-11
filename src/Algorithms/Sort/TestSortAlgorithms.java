package Algorithms.Sort;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;


public class TestSortAlgorithms {

    private int[] generateArray() {
        Random rand = new Random(System.nanoTime());

        int arrSize = rand.nextInt(1, 10_000_000);

        int[] arr = new int[arrSize];

        for (int index = 0; index < arrSize; index++) {
            arr[index] = rand.nextInt(2000);
        }

        return arr;
    }

    @Test
    public void testSortFunctionRuntime() {
        long averageTime = 0;
        long defaultAverageTime = 0;
        long averageArraySize = 0;
        MergeSort mergeSort = new MergeSort();

        for (int count = 0; count < 100; count++) {
            int[] arr = generateArray();
            int[] arrCopy1 = new int[arr.length];
            System.arraycopy(arr, 0, arrCopy1, 0, arr.length);
            averageArraySize += arr.length;

            long startTime = System.nanoTime();
            mergeSort.sort(arr);
            averageTime += System.nanoTime() - startTime;

            startTime = System.nanoTime();
            Arrays.sort(arrCopy1);
            defaultAverageTime += System.nanoTime() - startTime;

            Assert.assertArrayEquals(arr, arrCopy1);
        }

        System.out.println("Number of arrays sorted                            : " + 100);
        System.out.println("Average Array size                                 : " + (averageArraySize/100));
        System.out.println("Run Time of merge Sort function  (in milli seconds): " + ((averageTime/100)*Math.pow(10, -6)));
        System.out.println("Run Time of builtin sort function(in milli seconds): " + ((defaultAverageTime/100)*Math.pow(10, -6)));
    }
}

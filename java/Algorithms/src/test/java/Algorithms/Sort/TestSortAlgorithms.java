package Algorithms.Sort;

import java.util.Arrays;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class TestSortAlgorithms {

    private static int MIN_ARRAY_SIZE = 10_000;
    private static int MAX_ARRAY_SIZE = 50_000;

    private int[] generateIntArray() {
        Random rand = new Random(System.nanoTime());

        int arrSize = rand.nextInt(MIN_ARRAY_SIZE, MAX_ARRAY_SIZE);

        int[] arr = new int[arrSize];

        for (int index = 0; index < arrSize; index++) {
            arr[index] = rand.nextInt(2000);
        }

        return arr;
    }

    private Integer[] generateIntegerArray() {
        Random rand = new Random(System.nanoTime());

        int arrSize = rand.nextInt(MIN_ARRAY_SIZE, MAX_ARRAY_SIZE);

        Integer[] arr = new Integer[arrSize];

        for (int index = 0; index < arrSize; index++) {
            arr[index] = rand.nextInt(2000);
        }

        return arr;
    }

    @Test
    public void testMergeSortRuntime() {
        long mergeSortAvgTime = 0;
        long defaultAverageTime = 0;
        long averageArraySize = 0;
        MergeSort mergeSort = new MergeSort();

        for (int count = 0; count < 100; count++) {
            int[] arr = generateIntArray();
            int[] arrCopy1 = new int[arr.length];
            System.arraycopy(arr, 0, arrCopy1, 0, arr.length);
            averageArraySize += arr.length;

            long startTime = System.nanoTime();
            mergeSort.sort(arrCopy1);
            mergeSortAvgTime += System.nanoTime() - startTime;

            startTime = System.nanoTime();
            Arrays.sort(arr);
            defaultAverageTime += System.nanoTime() - startTime;

            Assertions.assertArrayEquals(arr, arrCopy1);
        }

        System.out.println("Number of arrays sorted                            : " + 100);
        System.out.println("Average Array size                                 : " + (averageArraySize / 100));
        System.out.println(
                "Run Time of merge Sort function  (in milli seconds): "
                        + ((mergeSortAvgTime / 100) * Math.pow(10, -6)));
        System.out.println("Run Time of builtin sort function(in milli seconds): "
                + ((defaultAverageTime / 100) * Math.pow(10, -6)));
        System.out.println("Delta Run Time (in milli seconds)                   :"
                + (((mergeSortAvgTime - defaultAverageTime) / 100) * Math.pow(10, -6)));
    }

    @Test
    public void testQuickSortRuntime() {
        long quickSortAvgTime = 0;
        long defaultAverageTime = 0;
        long averageArraySize = 0;
        QuickSort<Integer> quickSort = new QuickSort<>((e1, e2) -> e1 - e2);

        for (int count = 0; count < 100; count++) {
            Integer[] arr = generateIntegerArray();
            Integer[] arrCopy1 = new Integer[arr.length];
            System.arraycopy(arr, 0, arrCopy1, 0, arr.length);
            averageArraySize += arr.length;

            long startTime = System.nanoTime();
            quickSort.sort(arrCopy1);
            quickSortAvgTime += System.nanoTime() - startTime;

            startTime = System.nanoTime();
            Arrays.sort(arr);
            defaultAverageTime += System.nanoTime() - startTime;

            Assertions.assertArrayEquals(arr, arrCopy1);
        }

        System.out.println("Number of arrays sorted                            : " + 100);
        System.out.println("Average Array size                                 : " + (averageArraySize / 100));
        System.out.println(
                "Run Time of quick Sort function  (in milli seconds): "
                        + ((quickSortAvgTime / 100) * Math.pow(10, -6)));
        System.out.println("Run Time of builtin sort function(in milli seconds): "
                + ((defaultAverageTime / 100) * Math.pow(10, -6)));
        System.out.println("Delta Run Time (in milli seconds)                   :"
                + (((quickSortAvgTime - defaultAverageTime) / 100) * Math.pow(10, -6)));
    }
}

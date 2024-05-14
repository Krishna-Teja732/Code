package Algorithms.Sort;

import java.util.Comparator;
import java.util.Random;

public class QuickSort<T> {

    private Comparator<T> comparator;

    public QuickSort(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public void sort(T[] array) {
        sort(array, 0, array.length - 1);
    }

    private void sort(T[] array, int startIndex, int endIndex) {
        if (startIndex >= endIndex) {
            return;
        }

        int pivotIndex = startIndex;
        int currentIndex = startIndex;
        swap(array, endIndex, new Random().nextInt(startIndex, endIndex + 1));
        int pivotElementIndex = endIndex;

        while (currentIndex < endIndex) {
            if (comparator.compare(array[currentIndex], array[pivotElementIndex]) <= 0) {
                swap(array, currentIndex, pivotIndex);
                pivotIndex++;
            }
            currentIndex++;
        }
        swap(array, pivotIndex, pivotElementIndex);

        sort(array, startIndex, pivotIndex - 1);
        sort(array, pivotIndex + 1, endIndex);
    }

    private void swap(T[] array, int index1, int index2) {
        T temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }
}

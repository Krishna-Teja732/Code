package Algorithms.Sort;

public class MergeSort {

    public MergeSort() {}

    public void sort(int[] arr) {
        sort(arr, 0, arr.length-1);
    }

    private void sort(int[] arr, int startIndex, int endIndex) {
        if (startIndex >= endIndex) {
            return;
        }

        if (endIndex - startIndex == 1) {
            if (arr[startIndex] > arr[endIndex]) {
                swapValues(arr, startIndex, endIndex);
            }
            return;
        }

        int mid = (startIndex + endIndex)/2;
        sort(arr, startIndex, mid);
        sort(arr, mid + 1, endIndex);

        int[] tempSortedArr = new int[endIndex - startIndex + 1];
        int sortIndex = 0;
        int ptr1 = startIndex;
        int ptr2 = mid + 1;

        while(ptr1 <= mid && ptr2 <= endIndex) {
            if (arr[ptr1] < arr[ptr2]) {
                tempSortedArr[sortIndex++] = arr[ptr1++];
            }
            else if(arr[ptr1] > arr[ptr2]) {
                tempSortedArr[sortIndex++] = arr[ptr2++];
            }
            else {
                tempSortedArr[sortIndex++] = arr[ptr1++];
                tempSortedArr[sortIndex++] = arr[ptr2++];
            }
        }

        while(ptr1 <= mid) {
            tempSortedArr[sortIndex++] = arr[ptr1++];
        }

        while(ptr2 <= endIndex) {
            tempSortedArr[sortIndex++] = arr[ptr2++];
        }

        System.arraycopy(tempSortedArr, 0, arr, startIndex, tempSortedArr.length);
    }

    private void swapValues(int[] arr, int index1, int index2) {
        int temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }
}

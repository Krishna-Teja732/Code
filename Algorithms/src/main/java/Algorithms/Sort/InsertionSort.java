package Algorithms.Sort;

/**
 * InsertionSort
 */
public class InsertionSort {

    public static void sort(int[] nums) {

        for (int i = 0; i < nums.length - 1; i++) {
            int j = i + 1;
            while (j > 0 && nums[j] < nums[j - 1]) {
                int temp = nums[j];
                nums[j] = nums[j - 1];
                nums[j - 1] = temp;
                j--;
            }
        }

    }
}

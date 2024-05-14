package Algorithms;

import java.util.Arrays;

import Algorithms.Sort.QuickSort;

class Main {
	public static void main(String args[]) {
		QuickSort<Integer> sort = new QuickSort<>((e1, e2) -> e1 - e2);

		Integer[] arr = new Integer[] { 2123, 124, 124, 11, -2141, 0 };
		sort.sort(arr);

		System.out.println(Arrays.toString(arr));
	}
}

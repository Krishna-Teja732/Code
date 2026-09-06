
#include <iostream>
#include <ostream>
#include <utility>
void selection_sort(int arr[], int size) {
	for (int ind1 = 0; ind1 < size; ind1++) {
		for (int ind2 = ind1 + 1; ind2 < size; ind2++) {
			if (arr[ind1] > arr[ind2]) {
				std::swap(arr[ind1], arr[ind2]);
			}
		}
	}
}


void bubble_sort(int arr[], int size) {
	for (int ind1 = size - 1; ind1 > -1; ind1--) {
		for (int ind2 = 0; ind2 < ind1; ind2++) {
			if (arr[ind2] > arr[ind2 + 1]) {
				std::swap(arr[ind2 + 1], arr[ind2]);
			}
		}
	}
}


void merge_sort(int arr[], int start, int end) {
	if (start >= end) {
		return;
	}

	int mid = (start + end) / 2;
	merge_sort(arr, start, mid);
	merge_sort(arr, mid + 1, end);

	int sorted[end - start + 1];
	for (int sind = 0, ind1 = start, ind2 = mid + 1; sind < end - start + 1; sind++) {
		if (ind2 > end || arr[ind1] < arr[ind2]) {
			sorted[sind] = arr[ind1++];
		} else {
			sorted[sind] = arr[ind2++];
		}
	}

	for (int ind = start, sort_ind = 0; ind <= end; ind++, sort_ind++) {
		arr[ind] = sorted[sort_ind];
	}
}


void merge_sort(int arr[], int size) {
	merge_sort(arr, 0, size - 1);
}


void quick_sort(int arr[], int start, int end) {
	if (start >= end) {
		return;
	}

	// start is the pivot index. i.e. array will be partitioned 
	// based on the value of start index
	int cur = start + 1;
	for (int ind = cur; ind < end; ind++) {
		if (arr[start] >= arr[ind]) {
			std::swap(arr[cur], arr[ind]);
			cur++;
		}
	}

	cur--;
	std::swap(arr[cur], arr[start]);

	quick_sort(arr, start, cur);
	quick_sort(arr, cur + 1, end);
}

void quick_sort(int arr[], int size) {
	quick_sort(arr, 0, size);
}

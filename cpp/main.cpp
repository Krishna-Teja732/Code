#include <iostream>
#include "algorithms/sort.cpp"

using namespace std;

int main() {
	int size = 9;
	int arr[] = {11, 3, 4, 1, 4, -1, 3, 6, 7};
	quick_sort(arr, size);

	for (int i = 0; i < size; i++) {
		cout << arr[i] << " ";
	}

	return 0;
}

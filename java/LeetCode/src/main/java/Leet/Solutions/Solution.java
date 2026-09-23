package Leet.Solutions;

import Leet.LeetDS.GraphNode;
import Leet.LeetDS.ListNode;
import Leet.LeetDS.TreeNode;
import Leet.LeetDS.Node;
import Leet.LeetDS.Pair;

import java.util.*;

public class Solution {

	// 110. Balanced Binary Tree Helper
	public Pair<Boolean, Integer> isBalanced(TreeNode root, int level) {
		if (root == null) {
			return new Pair<>(true, level);
		}

		var left = isBalanced(root.left, level + 1);
		var right = isBalanced(root.right, level + 1);

		return new Pair<>(Math.abs(left.val2 - right.val2) < 2 && left.val1 && right.val1,
				Math.max(left.val2, right.val2) + 1);
	}

	// 110. Balanced Binary Tree
	public boolean isBalanced(TreeNode root) {
		var res = isBalanced(root, 0);
		return res.val1;
	}

	// 240. Search a 2D Matrix II helper
	public int colSearch(int[][] matrix, int target, int col, int rowStart, int rowEnd) {
		int mid = rowStart;
		while (rowStart <= rowEnd) {
			mid = (rowStart + rowEnd) / 2;
			if (matrix[mid][col] == target) {
				return mid;
			} else if (matrix[mid][col] < target) {
				rowStart = mid + 1;
			} else {
				rowEnd = mid - 1;
			}
		}

		return matrix[mid][col] < target ? mid + 1 : mid;
	}

	// 240. Search a 2D Matrix II helper
	public int rowSearch(int[][] matrix, int target, int row, int colStart, int colEnd) {
		int mid = colStart;
		while (colStart <= colEnd) {
			mid = (colStart + colEnd) / 2;
			if (matrix[row][mid] == target) {
				return mid;
			} else if (matrix[row][mid] < target) {
				colStart = mid + 1;
			} else {
				colEnd = mid - 1;
			}
		}

		return matrix[row][mid] > target ? mid - 1 : mid;
	}

	// 240. Search a 2D Matrix II
	public boolean searchMatrixII(int[][] matrix, int target) {
		int rowStart = 0, rowEnd = matrix.length - 1, curRow = 0;
		int colStart = 0, colEnd = matrix[rowStart].length - 1, curCol = 0;

		while (rowStart <= rowEnd && colStart <= colEnd) {
			curCol = rowSearch(matrix, target, curRow, colStart, colEnd);
			colEnd = curCol;
			if (curCol < 0 || curCol > matrix[0].length - 1) {
				return false;
			}
			if (matrix[curRow][curCol] == target) {
				return true;
			}

			curRow = colSearch(matrix, target, curCol, rowStart, rowEnd);
			rowStart = curRow;
			if (curRow < 0 || curRow > matrix.length - 1) {
				return false;
			}
			if (matrix[curRow][curCol] == target) {
				return true;
			}
		}

		return false;
	}

	// 34. Find first and last position of Element in sorted array
	public int[] searchRange(int[] nums, int target) {
		if (nums.length == 0) {
			return new int[] { -1, -1 };
		}

		int minInd = -1, maxInd = -1;
		int start = 0, end = nums.length - 1;
		int mid = 0;
		while (start <= end) {
			mid = (start + end) / 2;
			if (nums[mid] >= target) {
				end = mid - 1;
			} else {
				start = mid + 1;
			}
		}
		minInd = nums[mid] < target ? mid + 1 : mid;

		start = 0;
		end = nums.length - 1;
		while (start <= end) {
			mid = (start + end) / 2;
			if (nums[mid] > target) {
				end = mid - 1;
			} else {
				start = mid + 1;
			}
		}
		maxInd = nums[mid] > target ? mid - 1 : mid;

		if (minInd > maxInd) {
			return new int[] { -1, -1 };
		}

		return new int[] { minInd, maxInd };
	}

	// 37. Sudoku Solver helper
	public boolean solveSudoku(int row,
			int col,
			boolean[][] rowVisit,
			boolean[][] colVisit,
			boolean[][] boxVisit,
			char[][] board) {
		if (row == 8 && col == 9) {
			// We have visited all other indices and all indices have a valid entry
			// return true to solved sudoku
			return true;
		}

		if (col == 9) {
			row = row + 1;
			col = 0;
		}

		if (board[row][col] != '.') {
			return solveSudoku(row, col + 1, rowVisit, colVisit, boxVisit, board);
		}

		boolean solved = false;
		for (int elem = 0; elem < 9; elem++) {
			// Skip all invalid elementes
			if (rowVisit[row][elem] || colVisit[col][elem] || boxVisit[(row / 3) * 3 + col / 3][elem]) {
				continue;
			}

			// Pick valid elemnt and set it as visited
			board[row][col] = (char) (elem + 1 + '0');
			rowVisit[row][elem] = true;
			colVisit[col][elem] = true;
			boxVisit[(row / 3) * 3 + col / 3][elem] = true;
			if (solveSudoku(row, col + 1, rowVisit, colVisit, boxVisit, board)) {
				solved = true;
				break;
			}

			// Set the element to unvisited
			board[row][col] = '.';
			rowVisit[row][elem] = false;
			colVisit[col][elem] = false;
			boxVisit[(row / 3) * 3 + col / 3][elem] = false;
		}

		return solved;
	}

	// 37. Sudoku Solver
	public void solveSudoku(char[][] board) {
		// Init visited vars
		boolean[][] rowVisit = new boolean[9][9];
		boolean[][] colVisit = new boolean[9][9];
		boolean[][] boxVisit = new boolean[9][9];

		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				if (board[row][col] == '.') {
					continue;
				}

				rowVisit[row][board[row][col] - '0' - 1] = true;
				colVisit[col][board[row][col] - '0' - 1] = true;
				boxVisit[(row / 3) * 3 + col / 3][board[row][col] - '0' - 1] = true;
			}
		}

		solveSudoku(0, 0, rowVisit, colVisit, boxVisit, board);
	}

	// 4034. Minimum Bishop Moves to Reach Target
	public int minBishopMoves(int[] source, int[] target) {
		if (source[0] == target[0] && source[1] == target[1]) {
			return 0;
		}

		// In same diagonal
		if (source[0] + source[1] == target[0] + target[1]
				|| (source[0] - source[1] + 7 == target[0] - target[1] + 7)) {
			return 1;
		}

		// Opposite colors
		if ((source[0] + source[1]) % 2 != (target[0] + target[1]) % 2) {
			return -1;
		}

		return 2;
	}

	// 51. N-Queens Helper
	public void solveNQueens(int numQ,
			char[][] board,
			int boardSize,
			boolean[] visitCol,
			boolean[] visitMajDiag,
			boolean[] visitMinDiag,
			List<List<String>> solutions) {

		if (numQ == 0) {
			List<String> solution = new ArrayList<>();
			for (char[] row : board) {
				solution.add(new String(row));
			}
			solutions.add(solution);
			return;
		}

		int row = boardSize - numQ;
		for (int col = 0; col < boardSize; col++) {
			if (visitCol[col] || visitMajDiag[row - col + boardSize - 1]
					|| visitMinDiag[row + col]) {
				continue;
			}
			board[row][col] = 'Q';
			visitCol[col] = true;
			visitMajDiag[row - col + boardSize - 1] = true;
			visitMinDiag[row + col] = true;

			solveNQueens(numQ - 1,
					board,
					boardSize,
					visitCol,
					visitMajDiag,
					visitMinDiag,
					solutions);

			visitCol[col] = false;
			visitMajDiag[row - col + boardSize - 1] = false;
			visitMinDiag[row + col] = false;
			board[row][col] = '.';
		}
	}

	// 51. N-Queens
	public List<List<String>> solveNQueens(int n) {
		char[][] board = new char[n][n];
		for (int file = 0; file < n; file++) {
			for (int rank = 0; rank < n; rank++) {
				board[file][rank] = '.';
			}
		}

		List<List<String>> solutions = new ArrayList<>();

		solveNQueens(n,
				board,
				n,
				new boolean[n],
				new boolean[2 * n - 1],
				new boolean[2 * n - 1], solutions);

		return solutions;
	}

	// 451. Sort Characters by frequency
	public String frequencySort(String s) {
		PriorityQueue<Pair<Character, Integer>> freq = new PriorityQueue<>((p1, p2) -> (p2.val2 - p1.val2));

		int[] letters = new int[128];
		for (char ch : s.toCharArray()) {
			letters[ch]++;
		}

		for (int ind = 0; ind < letters.length; ind++) {
			if (letters[ind] != 0) {
				freq.add(new Pair<>((char) ind, letters[ind]));
			}
		}

		StringBuilder result = new StringBuilder();

		while (!freq.isEmpty()) {
			Pair<Character, Integer> letter = freq.poll();
			result.repeat(letter.val1.charValue(), letter.val2);
		}

		return result.toString();
	}

	// 79. Word Search Helper
	public boolean existHelper(char[][] board, int x_ind, int y_ind, int w_ind, String word) {
		if (w_ind >= word.length()) {
			return true;
		}

		if (x_ind < 0 || y_ind < 0 || x_ind >= board.length || y_ind >= board[x_ind].length
				|| board[x_ind][y_ind] != word.charAt(w_ind)) {
			return false;
		}

		board[x_ind][y_ind] = '.';

		boolean result = existHelper(board, x_ind + 1, y_ind, w_ind + 1, word)
				|| existHelper(board, x_ind - 1, y_ind, w_ind + 1, word)
				|| existHelper(board, x_ind, y_ind + 1, w_ind + 1, word)
				|| existHelper(board, x_ind, y_ind - 1, w_ind + 1, word);

		board[x_ind][y_ind] = word.charAt(w_ind);

		return result;
	}

	// 79. Word Search
	public boolean exist(char[][] board, String word) {
		for (int x_ind = 0; x_ind < board.length; x_ind++) {
			for (int y_ind = 0; y_ind < board[x_ind].length; y_ind++) {
				if (existHelper(board, x_ind, y_ind, 0, word)) {
					return true;
				}
			}
		}
		return false;
	}

	// 40. Combination Sum II Helper
	public void combinationSum2Helper(int[] candidateFreq, int target, int ind, List<Integer> combination,
			List<List<Integer>> result) {
		if (target == 0) {
			result.add(new ArrayList<>(combination));
			return;
		}
		if (target < 0 || ind >= candidateFreq.length || ind > target) {
			return;
		}

		if (candidateFreq[ind] > 0) {
			combination.add(ind);
			candidateFreq[ind]--;

			combinationSum2Helper(candidateFreq, target - ind, ind, combination, result);

			combination.removeLast();
			candidateFreq[ind]++;
		}

		combinationSum2Helper(candidateFreq, target, ind + 1, combination, result);
	}

	// 40. Combination Sum II
	public List<List<Integer>> combinationSum2(int[] candidates, int target) {
		List<List<Integer>> result = new ArrayList<>();

		int[] freq = new int[51];
		for (int candidate : candidates) {
			freq[candidate]++;
		}
		combinationSum2Helper(freq, target, 1, new ArrayList<>(), result);

		return result;
	}

	// 39. Combination Sum Helper
	public void combinationSumHelper(int[] candidates,
			int target,
			int ind,
			List<Integer> combination,
			List<List<Integer>> result) {
		if (target == 0) {
			result.add(new ArrayList<>(combination));
			return;
		}
		if (target < 0 || ind >= candidates.length) {
			return;
		}

		combination.add(candidates[ind]);
		combinationSumHelper(candidates, target - candidates[ind], ind, combination, result);

		combination.removeLast();
		combinationSumHelper(candidates, target, ind + 1, combination, result);
	}

	// 39. Combination Sum
	public List<List<Integer>> combinationSum(int[] candidates, int target) {
		List<List<Integer>> result = new ArrayList<>();

		combinationSumHelper(candidates, target, 0, new ArrayList<>(), result);

		return result;
	}

	// 1922. Count Good Numbers
	public int countGoodNumbers(long n) {
		int result = 5;
		int mod = (int) (Math.pow(10, 9) + 7);
		for (long i = 2; i <= n; i++) {
			if (i % 2 == 0) {
				result = (result * 4) % mod;
			} else {
				result = (result * 5) % mod;
			}
		}

		return result;
	}

	// 8. String to Integer (atoi)
	public int myAtoi(String s) {
		// Skip leading spaces and check if the first non empty char is valid
		int ind = 0;
		while (ind < s.length() && s.charAt(ind) == ' ') {
			ind++;
		}
		if (ind >= s.length() || !(s.charAt(ind) == '+' ||
				s.charAt(ind) == '-' ||
				(s.charAt(ind) >= '0' && s.charAt(ind) <= '9'))) {
			return 0;
		}

		// Skip all leading zeroes and check if the non-zero char is valid
		int sign = s.charAt(ind) != '-' ? 1 : -1;
		if (s.charAt(ind) == '+' || s.charAt(ind) == '-') {
			ind++;
		}
		while (ind < s.length() && s.charAt(ind) == '0') {
			ind++;
		}
		if (ind >= s.length() || s.charAt(ind) < '0' || s.charAt(ind) > '9') {
			return 0;
		}

		// Convert to int and check of overflows or underflows
		int result = (s.charAt(ind++) - '0') * sign;
		int max = (1 << 31) - 1, min = 1 << 31;
		int pre_max = max / 10, pre_min = min / 10;
		while (ind < s.length() && (s.charAt(ind) >= '0' && s.charAt(ind) <= '9')) {
			int digit = s.charAt(ind) - '0';

			if (result < 0 && (result < pre_min || result * 10 <= min + digit)) {
				return min;
			} else if (result >= 0 && (result > pre_max || result * 10 >= max - digit)) {
				return max;
			}
			result = result * 10 + digit * sign;

			ind++;
		}

		return result;
	}

	// 138. Copy list with random pointer
	public Node copyRandomList(Node head) {
		Node new_head = new Node(-1);
		Node tail = new_head;
		Node cur = head;
		HashMap<Node, Node> node_map = new HashMap<>();

		while (cur != null) {
			tail.next = new Node(cur.val);
			tail.random = cur.random;
			tail = tail.next;
			node_map.put(cur, tail);
			cur = cur.next;
		}

		cur = new_head.next;
		while (cur != null) {
			cur.random = node_map.get(cur.random);
			cur = cur.next;
		}

		return new_head.next;
	}

	// 61. Rotate List
	public ListNode rotateRight(ListNode head, int k) {
		if (head == null) {
			return null;
		}

		int len = 0;
		for (ListNode cur = head; cur != null; cur = cur.next) {
			len++;
		}
		k = k % len;

		ListNode old_tail = head;
		while (k > 0) {
			old_tail = old_tail.next;
			k--;
		}

		ListNode new_tail = head;
		while (old_tail.next != null) {
			new_tail = new_tail.next;
			old_tail = old_tail.next;
		}

		if (new_tail.next == null) {
			return head;
		}
		ListNode new_head = new_tail.next;
		new_tail.next = null;
		old_tail.next = head;

		return new_head;
	}

	// 148. Sort List Helper
	public ListNode sortList(ListNode head, int len) {
		if (len == 0) {
			return null;
		}
		if (len == 1) {
			head.next = null;
			return head;
		}

		ListNode mid = head;
		int cnt = len / 2;
		while (cnt > 0) {
			cnt--;
			mid = mid.next;
		}

		ListNode l1 = sortList(head, len / 2);
		ListNode l2 = sortList(mid, len - (len / 2));

		ListNode new_head = new ListNode();
		ListNode tail = new_head;
		while (l1 != null && l2 != null) {
			if (l1.val < l2.val) {
				tail.next = l1;
				tail = l1;
				l1 = l1.next;
			} else {
				tail.next = l2;
				tail = l2;
				l2 = l2.next;
			}
		}
		while (l1 != null) {
			tail.next = l1;
			tail = l1;
			l1 = l1.next;
		}
		while (l2 != null) {
			tail.next = l2;
			tail = l2;
			l2 = l2.next;
		}

		return new_head.next;
	}

	// 148. Sort List
	public ListNode sortList(ListNode head) {
		if (head == null || head.next == null) {
			return head;
		}

		ListNode cur = head;
		int len = 0;
		while (cur != null) {
			cur = cur.next;
			len++;
		}

		return sortList(head, len);
	}

	// 151. Reverse words
	public String reverseWords(String s) {
		char[] arr = s.toCharArray();
		for (int ind = 0; ind < arr.length / 2; ind++) {
			char temp = arr[ind];
			arr[ind] = arr[arr.length - 1 - ind];
			arr[arr.length - 1 - ind] = temp;
		}

		int lastInd = arr.length - 1;
		while (lastInd > -1 && arr[lastInd] == ' ') {
			lastInd--;
		}

		int resultInd = 0;
		boolean word = false;
		for (int ind = 0; ind <= lastInd; ind++) {
			if (arr[ind] == ' ') {
				if (word) {
					arr[resultInd++] = ' ';
					word = false;
				}
				continue;
			}

			word = true;
			arr[resultInd++] = arr[ind];
		}

		int startInd = 0;
		while (startInd < resultInd) {
			if (arr[startInd] == ' ') {
				startInd++;
				continue;
			}

			int endInd = startInd + 1;
			while (endInd < resultInd && arr[endInd] != ' ') {
				endInd++;
			}
			int nextInd = endInd;
			endInd--;

			while (startInd < endInd) {
				char temp = arr[endInd];
				arr[endInd] = arr[startInd];
				arr[startInd] = temp;
				startInd++;
				endInd--;
			}

			startInd = nextInd;
		}

		return String.valueOf(arr, 0, resultInd);
	}

	// 2149. Rearrange Array Elements by Sign
	public int[] rearrangeArray(int[] nums) {
		int[] result = new int[nums.length];
		int posInd = 0, negInd = 1;

		for (int ind = 0; ind < nums.length; ind++) {
			if (nums[ind] >= 0) {
				result[posInd] = nums[ind];
				posInd += 2;
			} else {
				result[negInd] = nums[ind];
				negInd += 2;
			}
		}
		return result;
	}

	// 560. Subarray Sum Equals K
	public int subarraySum(int[] nums, int k) {
		int[] sum = new int[nums.length + 1];
		int result = 0;

		for (int ind = 0; ind < nums.length; ind++) {
			sum[ind + 1] = sum[ind] + nums[ind];
		}

		HashMap<Integer, Integer> sumHist = new HashMap<>();
		for (int ind = 0; ind < sum.length; ind++) {
			result += sumHist.getOrDefault(sum[ind] - k, 0);
			sumHist.put(sum[ind], sumHist.getOrDefault(sum[ind], 0) + 1);
		}

		return result;
	}

	// 121. Best Time to Buy and Sell Stock
	public int maxProfit(int[] prices) {
		if (prices.length < 2) {
			return 0;
		}
		int profit = 0;
		int maxNAV = prices[prices.length - 1];

		for (int ind = prices.length - 2; ind > -1; ind--) {
			profit = Math.max(profit, maxNAV - prices[ind]);
			maxNAV = Math.max(maxNAV, prices[ind]);
		}

		return profit;
	}

	// 48. Rotate Image helper
	public void rotate(int[][] matrix, int colIndex, int rowIndex, int size) {
		int[][] position = new int[][] {
				{ colIndex + rowIndex, size - 1 + rowIndex },
				{ size - 1 + rowIndex, size - 1 - colIndex + rowIndex },
				{ size - 1 - colIndex + rowIndex, rowIndex },
				{ 0 + rowIndex, colIndex + rowIndex } };
		int curElement = matrix[position[3][0]][position[3][1]];

		for (int posIndex = 0; posIndex < position.length; posIndex++) {
			int temp = curElement;
			curElement = matrix[position[posIndex][0]][position[posIndex][1]];
			matrix[position[posIndex][0]][position[posIndex][1]] = temp;
		}

	}

	// 48. Rotate Image
	public void rotate(int[][] matrix) {
		/*
		 * Current Approach:
		 * Swap the elements based on the index
		 * Example: For a 5x5 matrix, consider the element at [0,0]
		 * elements with index [0,0], [0,4], [4,4], [4,0] will get rotated.
		 * 
		 * Generalize the above indices for all elements in first row. This will
		 * rotate only the outer elements.
		 *
		 * For swapping the inner elements, do it using a smaller matrix, in this
		 * example, the inner matrix will be a 3x3 matrix. The indices that was
		 * generalized for the first row will work, we just need to add an offset.
		 *
		 *
		 * Easier approach(not implemented):
		 * Transpose the matrix and reverse each row
		 */
		for (int rowIndex = 0, size = matrix.length; size > 1; rowIndex++, size -= 2) {
			for (int colIndex = 0; colIndex < size - 1; colIndex++) {
				rotate(matrix, colIndex, rowIndex, size);
			}
		}
	}

	// 73. Set matrix zeroes
	public void setZeroes(int[][] matrix) {
		// Use first row of matrix to know which colums needs to be set to zero
		boolean rowZero = false;
		int len = matrix.length, width = matrix[0].length;
		for (int colInd = 0; colInd < width; colInd++) {
			if (matrix[0][colInd] == 0) {
				rowZero = true;
				break;
			}
		}

		for (int rowInd = 1; rowInd < len; rowInd++) {
			boolean curRowZero = false;
			for (int colInd = 0; colInd < width; colInd++) {
				if (matrix[rowInd][colInd] == 0) {
					curRowZero = true;
					matrix[0][colInd] = 0;
				}
			}

			if (curRowZero) {
				Arrays.fill(matrix[rowInd], 0);
			}
		}

		for (int colInd = 0; colInd < width; colInd++) {
			if (matrix[0][colInd] != 0) {
				continue;
			}

			for (int rowInd = 0; rowInd < len; rowInd++) {
				matrix[rowInd][colInd] = 0;
			}
		}

		if (rowZero) {
			Arrays.fill(matrix[0], 0);
		}
	}

	// 75. Sort Colors
	public void sortColors(int[] nums) {
		int zeroInd = 0, twoInd = nums.length - 1;
		int curInd = 0;
		// Invariant:
		// 1. for all index i < zeroInd, nums[i] = 0
		// 2. for all index i > twoInd, nums[i] = 2
		// 3. curInd >= zero Ind

		/*
		 * Use curInd to walkthrough the array
		 * Based on the value on curInd (either 0 or 2) swap with
		 * repective pointer and increment the zeroInd or twoInd pointer.
		 * When swapping, do not increment curInd, becuase after the swap,
		 * curInd could have any value.
		 *
		 * In each iteration curInd = max(zeroInd, curInd) because we
		 * made sure that all indices < zeroInd is 0. (if curInd is less
		 * than zeroInd, curInd will never be incremented)
		 */
		while (curInd <= twoInd) {
			if (nums[curInd] == 1) {
				curInd++;
			} else if (nums[curInd] == 0) {
				nums[curInd] = nums[zeroInd];
				nums[zeroInd] = 0;
				zeroInd++;
				curInd = Math.max(curInd, zeroInd);
			} else if (nums[curInd] == 2) {
				nums[curInd] = nums[twoInd];
				nums[twoInd] = 2;
				twoInd--;
			}
		}
	}

	// 3754. Concatenate Non-Zero Digits and Multiply by Sum I
	public long sumAndMultiply(int n) {
		int sum = 0, newN = 0;
		int pow = 1;
		while (n > 0) {
			int digit = n % 10;
			sum = sum + digit;
			n = n / 10;
			if (digit != 0) {
				newN = pow * digit + newN;
				pow = pow * 10;
			}
		}
		return newN * sum;
	}

	// 1331. Rank Transform of an Array
	public int[] arrayRankTransform(int[] arr) {
		if (arr.length == 0) {
			return new int[] {};
		}

		int[][] indexedArr = new int[arr.length][2];
		for (int index = 0; index < arr.length; index++) {
			indexedArr[index] = new int[] { index, arr[index] };
		}
		Arrays.sort(indexedArr, (ent1, ent2) -> ent1[1] - ent2[1]);

		int[] result = new int[arr.length];
		int rank = 1;
		result[indexedArr[0][0]] = rank;
		for (int index = 1; index < arr.length; index++) {
			if (indexedArr[index - 1][1] != indexedArr[index][1]) {
				rank++;
			}
			result[indexedArr[index][0]] = rank;
		}

		return result;
	}

	// 1291. Sequential Digits
	public List<Integer> sequentialDigits(int low, int high) {
		int minSeq = 12, curSeq = 12;
		int increment = 11, digitCount = 2;
		List<Integer> result = new ArrayList<>();

		while (curSeq <= high) {
			if (curSeq >= low) {
				result.add(curSeq);
			}
			curSeq = curSeq + increment;
			if (curSeq % 10 == 0) {
				minSeq = minSeq * 10 + (++digitCount);
				curSeq = minSeq;
				increment = increment * 10 + 1;
			}
		}
		return result;
	}

	// 3310. Remove Methods From Project
	public List<Integer> remainingMethods(int n, int k, int[][] invocations) {
		// Init node for each method
		HashMap<Integer, GraphNode> methodNodeMap = new HashMap<>();
		for (int[] invocation : invocations) {
			var caller = methodNodeMap.computeIfAbsent(invocation[0], (id) -> new GraphNode(id));
			caller.child.add(invocation[1]);
		}

		// Find all the invocations of function k and mark them visited
		LinkedList<Integer> frontier = new LinkedList<>();
		HashSet<Integer> bugMethods = new HashSet<>();
		frontier.add(k);
		while (!frontier.isEmpty()) {
			int methodID = frontier.pollFirst();
			if (bugMethods.contains(methodID)) {
				continue;
			}

			bugMethods.add(methodID);
			if (methodNodeMap.containsKey(methodID)) {
				frontier.addAll(methodNodeMap.get(methodID).child);
			}
		}

		// If any node with visited = false has a node with visited = true,
		// then return result as it. Otherwise, remove all visited nodes
		// and return the remaining as the result
		boolean disjointInokes = true;
		for (int methodID = 0; methodID < n; methodID++) {
			if (!methodNodeMap.containsKey(methodID)) {
				continue;
			}

			var method = methodNodeMap.get(methodID);
			if (bugMethods.contains(methodID)) {
				continue;
			}

			for (int targetMethod : method.child) {
				if (bugMethods.contains(targetMethod)) {
					disjointInokes = false;
					break;
				}
			}

			if (!disjointInokes) {
				break;
			}
		}

		ArrayList<Integer> result = new ArrayList<>();
		for (int methodID = 0; methodID < n; methodID++) {
			if (!disjointInokes || !bugMethods.contains(methodID)) {
				result.add(methodID);
			}
		}

		return result;
	}

	// 3731. Find Missing Elements
	public List<Integer> findMissingElements(int[] nums) {
		List<Integer> result = new ArrayList<>(nums.length);

		boolean[] freq = new boolean[101];
		int small = 101, big = 0;
		for (int i = 0; i < nums.length; i++) {
			freq[nums[i]] = true;
			small = Math.min(small, nums[i]);
			big = Math.max(big, nums[i]);
		}

		while (small < big) {
			if (freq[small]) {
				small++;
				continue;
			}
			result.add(small++);
		}

		return result;
	}

	// 1260. Shift 2D Grid
	public List<List<Integer>> shiftGrid(int[][] grid, int k) {
		int length = grid.length, width = grid[0].length;
		int totalElements = length * width;
		// Find index of the first element as if grid is 1-d array,
		// then convert it into 2-d array ind
		int oneDInd = (totalElements - (k % totalElements)) % totalElements;
		int rowInd = (oneDInd / width), colInd = (oneDInd % width);

		List<List<Integer>> result = new ArrayList<>(length);
		for (int rCount = 0; rCount < length; rCount++) {
			List<Integer> row = new ArrayList<>(width);

			for (int cCount = 0; cCount < width; cCount++) {
				row.add(grid[rowInd][colInd]);
				colInd++;

				if (colInd == width) {
					colInd = 0;
					rowInd = (rowInd + 1) % length;
				}
			}

			result.add(row);
		}

		return result;
	}

	// 3016. Minimum Number of Pushes to Type Word II
	public int minimumPushesII(String word) {
		int freq[] = new int[26];
		for (int ind = 0; ind < word.length(); ind++) {
			freq[word.charAt(ind) - 97]++;
		}
		Arrays.sort(freq);

		int chars = 0;
		int keyPushes = 1;
		int result = 0;

		for (int ind = 25; ind > -1; ind--) {
			if (freq[ind] == 0) {
				continue;
			}
			result = result + freq[ind] * keyPushes;
			chars++;
			if (chars == 8) {
				chars = 0;
				keyPushes++;
			}
		}

		return result;
	}

	// 3014. Minimum Number of Pushes to Type Word I
	public int minimumPushes(String word) {
		int n = word.length() / 8;
		return n * (n + 1) * 4 + (n + 1) * (word.length() % 8);
	}

	// 3517. Smallest Palindromic Rearrangement I
	public String smallestPalindrome(String s) {
		char[] result = new char[s.length()];
		int[] freq = new int[123];
		for (char c : s.toCharArray()) {
			freq[c]++;
		}

		int resStartInd = 0, resEndInd = result.length - 1;
		char freqInd = 97;
		while (resStartInd <= resEndInd && freqInd < freq.length) {
			if (freq[freqInd] <= 1) {
				freqInd++;
				continue;
			}
			result[resStartInd++] = freqInd;
			result[resEndInd--] = freqInd;
			freq[freqInd] -= 2;
		}

		if (s.length() % 2 != 0) {
			result[resStartInd] = s.charAt((int) s.length() / 2);
		}

		return new String(result);
	}

	// 3513. Number of Unique XOR Triplets I
	public int uniqueXorTripletsPart1(int[] nums) {
		int n = nums.length;
		if (n < 3) {
			return n;
		}
		int result = 1;
		while (result <= n) {
			result = result << 1;
		}
		return result;
	}

	// 3536. Maximum Product of Two Digits
	public int maxProduct(int n) {
		int[] freq = new int[10];
		while (n > 0) {
			freq[n % 10]++;
			n = n / 10;
		}
		int result = 0;
		int digit = 9;
		while (digit >= 0) {
			if (freq[digit] == 0) {
				digit--;
				continue;
			}
			result = digit;
			freq[digit]--;
			break;
		}

		while (digit >= 0) {
			if (freq[digit] == 0) {
				digit--;
				continue;
			}
			result = digit * result;
			freq[digit]--;
			break;
		}

		return result;
	}

	// 3514. Number of Unique XOR Triplets II.
	public int uniqueXorTripletsPart2(int[] nums) {
		int result = 0;
		int[] uniqueNum = new int[2048];
		int[] pairXor = new int[2048];
		int[] tripletXor = new int[2048];

		for (int i = 0; i < nums.length; i++) {
			uniqueNum[nums[i]] = 1;
		}

		for (int i = 1; i < uniqueNum.length; i++) {
			if (uniqueNum[i] != 1) {
				continue;
			}
			for (int j = i; j < uniqueNum.length; j++) {
				if (uniqueNum[j] != 1) {
					continue;
				}
				pairXor[i ^ j] = 1;
			}
		}

		for (int i = 1; i < pairXor.length; i++) {
			if (uniqueNum[i] != 1) {
				continue;
			}
			for (int j = 1; j < pairXor.length; j++) {
				if (pairXor[j] != 1) {
					continue;
				}
				tripletXor[i ^ j] = 1;
			}
		}

		for (int i = 0; i < pairXor.length; i++) {
			if (uniqueNum[i] != 0 || tripletXor[i] != 0) {
				result++;
			}
		}

		return result;
	}

	public List<Integer> getDivisors(int n) {
		List<Integer> result = new ArrayList<>();
		for (int div = 1; div < Math.pow(n, 0.5); div++) {
			if (n % div != 0) {
				continue;
			}
			result.add(div);
			result.add(n / div);
		}
		result.sort(Integer::compareTo);
		return result;
	}

	// 9. Palindrome Number
	public boolean isPalindrome(int x) {
		if (x < 0 || (x % 10 == 0 && x != 0)) {
			return false;
		}
		return reverse(x) == x;
	}

	// 7. Reverse Integer
	public int reverse(int n) {
		long result = 0;
		while (n != 0) {
			result = result * 10 + (n % 10);
			n = n / 10;
		}
		result = (result <= Integer.MIN_VALUE || result >= Integer.MAX_VALUE) ? 0 : result;
		return (int) result;
	}

	// 3446. Sort Matrix by Diagonals
	public void sortMatrixHelper(int[][] grid,
			int startX, int startY,
			int endX, int endY,
			Comparator<Integer> comparator) {

		if (startX >= endX || startY >= endY || ((endX - startX) != (endY - startY)) || startX == endX - 1) {
			return;
		}
		int midX = (startX + endX) / 2;
		int midY = (startY + endY) / 2;
		sortMatrixHelper(grid, startX, startY, midX, midY, comparator);
		sortMatrixHelper(grid, midX, midY, endX, endY, comparator);

		int[] sorted = new int[endX - startX];
		int p1x = startX, p1y = startY;
		int p2x = midX, p2y = midY;
		int ps = 0;
		while (p1x < midX || p2x < endX) {
			if (p2x >= endX || (p1x < midX && comparator.compare(grid[p1x][p1y], grid[p2x][p2y]) <= 0)) {
				sorted[ps] = grid[p1x][p1y];
				p1x++;
				p1y++;
			} else {
				sorted[ps] = grid[p2x][p2y];
				p2x++;
				p2y++;
			}
			ps++;
		}

		for (int x = startX, y = startY, sortInd = 0; x < endX && y < endY; x++, y++, sortInd++) {
			grid[x][y] = sorted[sortInd];
		}
	}

	// 3446. Sort Matrix by Diagonals
	public int[][] sortMatrix(int[][] grid) {
		int grid_size = grid.length;
		for (int startX = 0; startX < grid_size; startX++) {
			sortMatrixHelper(grid, startX, 0, grid_size, grid_size - startX, (p1, p2) -> (p2 - p1));
		}
		for (int startY = 1; startY < grid_size; startY++) {
			sortMatrixHelper(grid, 0, startY, grid_size - startY, grid_size, (p1, p2) -> (p1 - p2));
		}
		return grid;
	}

	// 36. Valid Sudoku
	public boolean isValidSudoku(char[][] board) {
		for (int rowIndex = 0; rowIndex < board.length; rowIndex++) {
			boolean[] visited = new boolean[10];
			for (int colIndex = 0; colIndex < board[0].length; colIndex++) {
				if (board[rowIndex][colIndex] == '.') {
					continue;
				}
				if (visited[board[rowIndex][colIndex] - '0']) {
					return false;
				}
				visited[board[rowIndex][colIndex] - '0'] = true;
			}
		}
		for (int colIndex = 0; colIndex < board[0].length; colIndex++) {
			boolean[] visited = new boolean[10];
			for (int rowIndex = 0; rowIndex < board.length; rowIndex++) {
				if (board[rowIndex][colIndex] == '.') {
					continue;
				}
				if (visited[board[rowIndex][colIndex] - '0']) {
					return false;
				}
				visited[board[rowIndex][colIndex] - '0'] = true;
			}
		}
		for (int startX = 0; startX < board.length; startX += 3) {
			for (int startY = 0; startY < board[0].length; startY += 3) {
				boolean[] visited = new boolean[10];
				for (int rowIndex = startX; rowIndex < startX + 3; rowIndex++) {
					for (int colIndex = startY; colIndex < startY + 3; colIndex++) {
						if (board[rowIndex][colIndex] == '.') {
							continue;
						}
						if (visited[board[rowIndex][colIndex] - '0']) {
							return false;
						}
						visited[board[rowIndex][colIndex] - '0'] = true;
					}
				}
			}
		}
		return true;
	}

	// 3000. Maximum Area of Longest Diagonal Rectangle
	public int areaOfMaxDiagonal(int[][] dimensions) {
		int max_area = 0, max_diag = 0;
		for (int[] dimension : dimensions) {
			int cur_diag = dimension[0] * dimension[0] + dimension[1] * dimension[1];
			int cur_area = dimension[0] * dimension[1];
			if (cur_diag > max_diag || (cur_diag == max_diag && cur_area > max_area)) {
				max_area = cur_area;
				max_diag = cur_diag;
			}
		}
		return max_area;
	}

	// 1657. Determine if Two Strings Are Close
	public boolean closeStrings(String word1, String word2) {
		if (word1.length() != word2.length()) {
			return false;
		}
		int[] map1 = new int[26];
		int[] map2 = new int[26];

		for (int index = 0; index < word1.length(); index++) {
			map1[word1.charAt(index) - 'a']++;
		}
		for (int index = 0; index < word2.length(); index++) {
			map2[word2.charAt(index) - 'a']++;
		}

		for (int map1Index = 0; map1Index < 26; map1Index++) {
			if (map1[map1Index] == 0) {
				continue;
			}
			for (int map2Index = map1Index + 1; map2Index < 26; map2Index++) {
				if (map1[map1Index] == map2[map2Index] && map2[map1Index] != 0) {
					int temp = map2[map2Index];
					map2[map2Index] = map2[map1Index];
					map2[map1Index] = temp;
					break;
				}
			}
		}

		for (int index = 0; index < 26; index++) {
			if (map1[index] != map2[index]) {
				return false;
			}
		}

		return true;
	}

	// 2438. Range Product Queries of Powers
	public int[] productQueries(int n, int[][] queries) {
		int[] result = new int[queries.length];
		String binary = Integer.toBinaryString(n);
		int[] powers = new int[binary.length() + 1];
		int powersSize = 1;

		for (int index = binary.length() - 1; index > -1; index--) {
			if (binary.charAt(index) == '0') {
				continue;
			}
			powers[powersSize++] = binary.length() - 1 - index;
		}

		for (int index = 1; index < powersSize; index++) {
			powers[index] += powers[index - 1];
		}

		int modulo = (int) Math.pow(10, 9) + 7;
		for (int index = 0; index < queries.length; index++) {
			int[] query = queries[index];
			result[index] = (int) (Math.pow(2, powers[query[1] + 1] - powers[query[0]]) % modulo);
		}

		return result;
	}

	// 2264. Largest 3-Same-Digit Number in String
	public String largestGoodInteger(char[] arr) {
		int[] count = new int[60];
		int max = '/';

		for (int index = 0; index < 3; index++) {
			count[arr[index]] += 1;
		}
		if (count[arr[2]] == 3) {
			max = Math.max(arr[2], max);
		}

		for (int index = 3; index < arr.length; index++) {
			count[arr[index]] += 1;
			count[arr[index - 3]] -= 1;
			if (count[arr[index]] == 3) {
				max = Math.max(max, arr[index]);
				if (max == '9') {
					break;
				}
			}
		}

		return max == '/' ? "" : String.valueOf((char) max).repeat(3);
	}

	// 2348. Number of Zero-Filled Subarrays
	public long zeroFilledSubarray(int[] nums) {
		long result = 0;
		int index = 0;

		while (index < nums.length) {
			while (index < nums.length && nums[index] != 0) {
				index++;
			}
			if (index >= nums.length) {
				break;
			}
			int count0 = 0;
			while (index < nums.length && nums[index] == 0) {
				count0++;
				index++;
			}
			result += (count0 * (count0 + 1)) / 2;
		}

		return result;
	}

	// 1493. Longest Subarray of 1's After Deleting One Element
	public int longestSubarray(int[] nums) {
		int result = 0;
		int startIndex = 0;
		int curIndex = 1;

		while (curIndex < nums.length) {
			while (curIndex < nums.length && nums[curIndex] == 0) {
				curIndex++;
				startIndex++;
			}
			if (curIndex >= nums.length) {
				break;
			}
			while (curIndex < nums.length && nums[curIndex] == 1) {
				nums[startIndex] += 1;
				curIndex++;
			}
			startIndex++;
			curIndex++;
			nums[startIndex] = 0;
		}

		return result == nums.length ? result - 1 : result;
	}

	// 498. Diagonal Traverse
	public int[] findDiagonalOrder(int[][] mat) {
		int[] result = new int[mat.length * mat[0].length];
		int resultIndex = 0;
		int x_direction = -1, y_direction = +1;
		int x_ind = 0, y_ind = 0;

		while (resultIndex < result.length) {
			if (x_ind < 0 || x_ind >= mat.length || y_ind < 0 || y_ind >= mat[x_ind].length) {
				x_direction *= -1;
				y_direction *= -1;
			}
			result[resultIndex++] = mat[x_ind][y_ind];

			x_ind += x_direction;
			y_ind += y_direction;

			if (x_ind < 0 || x_ind >= mat.length || y_ind < 0 || y_ind >= mat[x_ind].length) {
				x_direction *= -1;
				y_direction *= -1;
			}
			if (x_ind < 0 || x_ind >= mat.length) {
				x_ind += x_direction;
				y_ind += y_direction;
				y_ind++;
			} else if (y_ind < 0 || y_ind >= mat[x_ind].length) {
				x_ind += x_direction;
				y_ind += y_direction;
				x_ind++;
			}
		}
		return result;
	}

	// 3195. Find the Minimum Area to Cover All Ones I
	public int minimumArea(int[][] grid) {
		// Find the points that are furthest to the left, right, top and bottom
		int leftY = grid.length;
		int rightY = 0;
		int topX = grid.length;
		int downX = 0;

		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[x].length; y++) {
				if (grid[x][y] == 0) {
					continue;
				}

				leftY = Math.min(leftY, y);
				rightY = Math.max(rightY, y);
				topX = Math.min(topX, x);
				downX = Math.max(downX, x);
			}
		}

		return (downX - topX + 1) * (rightY - leftY + 1);
	}

	// 1324. Print Words Vertically
	public List<String> printVertically(String s) {
		ArrayList<String> words = new ArrayList<>();
		int wordStartIndex = 0;
		int maxWordLen = 0;
		while (wordStartIndex < s.length()) {
			int workEndIndex = s.indexOf(' ', wordStartIndex);
			if (workEndIndex == -1) {
				workEndIndex = s.length();
			}
			words.add(s.substring(wordStartIndex, workEndIndex));
			maxWordLen = Math.max(maxWordLen, workEndIndex - wordStartIndex);
			wordStartIndex = workEndIndex + 1;
		}

		ArrayList<String> result = new ArrayList<>(maxWordLen);
		for (int letterIndex = 0; letterIndex < maxWordLen; letterIndex++) {
			StringBuilder modWord = new StringBuilder(words.size());
			for (String word : words) {
				char letter = letterIndex >= word.length() ? ' ' : word.charAt(letterIndex);
				modWord.append(letter);
			}
			int endInd = modWord.length() - 1;
			while (modWord.charAt(endInd) == ' ') {
				endInd--;
			}
			result.add(modWord.substring(0, endInd + 1));
		}

		return result;
	}

	// 796. Rotate String
	public boolean rotateString(String s, String goal) {
		if (s.length() != goal.length()) {
			return false;
		}
		if (s.equals(goal)) {
			return true;
		}

		for (int index = 1; index < s.length(); index++) {
			if (goal.equals(s.substring(index) + s.substring(0, index))) {
				return true;
			}
		}

		return false;
	}

	// 205. Isomorphic Strings
	public boolean isIsomorphic(String s, String t) {
		char[] map = new char[128];
		for (int index = 0; index < s.length(); index++) {
			if (map[s.charAt(index)] == '\u0000') {
				map[s.charAt(index)] = t.charAt(index);
				continue;
			}
			if (map[s.charAt(index)] != t.charAt(index)) {
				return false;
			}
		}
		char[] visited = new char[128];
		for (char ch : map) {
			if (ch == '\u0000') {
				continue;
			}
			if (visited[ch] != '\u0000') {
				return false;
			}
			visited[ch] = 1;
		}
		return true;
	}

	// 1903. Larget odd number substring
	public String largestOddNumber(String num) {
		int index = num.length() - 1;
		for (; index >= 0; index--) {
			if (((num.charAt(index) - '0') & 1) == 1) {
				break;
			}
		}
		return index == -1 ? "" : num.substring(0, index + 1);
	}

	// 540. Single Element Sorted Array
	// Hint :
	// If there are no single duplicates in the array, for all odd index i,
	// nums[i] will be equal to nums[i + 1]
	public int singleNonDuplicate(int[] nums) {
		if (nums.length == 1) {
			return nums[0];
		}
		int start = 0;
		int end = nums.length - 1;

		while (start <= end) {
			int mid = (start + end) / 2;
			if ((mid == 0 || nums[mid] != nums[mid - 1])
					&& (mid == nums.length - 1 || nums[mid] != nums[mid + 1])) {
				return nums[mid];
			}

			if (mid % 2 == 0) {
				if (nums[mid] == nums[mid + 1]) {
					start = mid + 1;
				} else {
					end = mid - 1;
				}
			} else {
				if (nums[mid] == nums[mid + 1]) {
					end = mid - 1;
				} else {
					start = mid + 1;
				}
			}
		}

		return -1;
	}

	// 33. Binary search helper
	public int searchRotatedArray(int[] nums, int start, int end, int target) {
		while (start <= end) {
			int mid = (start + end) / 2;
			if (nums[mid] == target) {
				return mid;
			}
			if (nums[mid] > target) {
				end = mid - 1;
			} else {
				start = mid + 1;
			}
		}
		return -1;
	}

	// 33. Search in rotated sorted array
	// Approach:
	// 1. Find pivot index in O(lgn) using binary search
	// 2. If target > nums[nums.length - 1]: then the element is to the left of
	// pivot, else element is to the right of the pivot
	public int searchRotatedArray(int[] nums, int target) {
		int start = 0;
		int end = nums.length - 1;
		// If no pivot index is found, use normal binary search
		int pivotIndex = -1;

		// Finding pivot index
		while (start <= end) {
			int mid = (start + end) / 2;
			if (mid + 1 < nums.length && nums[mid] > nums[mid + 1]) {
				pivotIndex = mid;
				break;
			}
			if (nums[mid] > nums[nums.length - 1]) {
				start = mid + 1;
			} else {
				end = mid - 1;
			}
		}

		if (target > nums[nums.length - 1]) {
			return searchRotatedArray(nums, 0, pivotIndex, target);
		}
		return searchRotatedArray(nums, pivotIndex + 1, nums.length - 1, target);
	}

	// 35. Search Insert Position
	public int searchInsert(int[] nums, int target) {
		int start = 0;
		int end = nums.length - 1;

		while (start < end) {
			int mid = (start + end) / 2;
			if (nums[mid] == target) {
				return mid;
			}

			if (nums[mid] > target) {
				end = mid - 1;
			} else {
				start = mid + 1;
			}
		}

		return nums[start] < target ? start + 1 : start;
	}

	// 328. Odd Even Linked List
	public ListNode oddEvenList(ListNode head) {
		if (head == null || head.next == null) {
			return head;
		}

		ListNode oddTail = head;
		ListNode oddNode = head.next.next;
		ListNode oddNodeParent = head.next;

		while (oddNodeParent != null && oddNode != null) {
			// Delete odd Node
			oddNodeParent.next = oddNode.next;

			// Insert oddNode at tail
			oddNode.next = oddTail.next;
			oddTail.next = oddNode;

			// Update pointers
			oddTail = oddTail.next;
			oddNodeParent = oddNodeParent.next;
			if (oddNodeParent != null) {
				oddNode = oddNodeParent.next;
			}
		}

		return head;
	}

	// 56. Merge intervals
	public int[][] merge(int[][] intervals) {
		// Sort the array based on starting time
		Arrays.sort(intervals, new Comparator<int[]>() {
			public int compare(int[] arg0, int[] arg1) {
				return arg0[0] - arg1[0];
			};
		});

		List<int[]> result = new ArrayList<>(intervals.length);
		int[] currentInterval = intervals[0];
		for (int index = 1; index < intervals.length; index++) {
			if (currentInterval[1] >= intervals[index][0]) {
				currentInterval[1] = Math.max(intervals[index][1], currentInterval[1]);
			} else {
				result.add(currentInterval);
				currentInterval = intervals[index];
			}
		}
		if (currentInterval != null) {
			result.add(currentInterval);
		}

		// Copy to primitive array
		int[][] finalResult = new int[result.size()][];
		for (int index = 0; index < result.size(); index++) {
			finalResult[index] = result.get(index);
		}
		return finalResult;
	}

	// 1020. Number of enclaves, helper
	public void sinkLand(int[][] grid, int x, int y) {
		if (x == grid.length || y == grid[0].length || x < 0 || y < 0) {
			return;
		}

		if (grid[x][y] == 0) {
			return;
		}

		grid[x][y] = 0;

		sinkLand(grid, x + 1, y);
		sinkLand(grid, x - 1, y);
		sinkLand(grid, x, y + 1);
		sinkLand(grid, x, y - 1);
	}

	// 1020. Number of enclaves
	public int numEnclaves(int[][] grid) {
		for (int x = 0; x < grid.length; x++) {
			sinkLand(grid, x, 0);
			sinkLand(grid, x, grid[0].length - 1);
		}

		for (int y = 0; y < grid[0].length; y++) {
			sinkLand(grid, 0, y);
			sinkLand(grid, grid.length - 1, y);
		}

		int result = 0;
		for (int[] row : grid) {
			for (int val : row) {
				result = result + val;
			}
		}

		return result;
	}

	// 990 Satisfiability of equality equations, helper
	private int findParent(int[] values, int index) {
		while (values[index] != -1) {
			index = values[index];
		}

		return index;
	}

	// 990 Satisfiability of equality equations
	public boolean equationsPossible(String[] equations) {
		int[] values = new int[26];
		Arrays.fill(values, -1);

		for (String equation : equations) {
			if (equation.charAt(1) == '!') {
				continue;
			}
			int element1 = equation.charAt(0) - 'a';
			int element2 = equation.charAt(3) - 'a';

			int parent1 = findParent(values, element1);
			int parent2 = findParent(values, element2);

			if (parent1 == parent2) {
				continue;
			}

			values[parent2] = parent1;
		}

		for (String equation : equations) {
			if (equation.charAt(1) == '=') {
				continue;
			}

			int element1 = equation.charAt(0) - 'a';
			int element2 = equation.charAt(3) - 'a';

			int parent1 = findParent(values, element1);
			if (parent1 == findParent(values, element2)) {
				return false;
			}
		}

		return true;
	}

	// 463. Island Perimeter, helper
	private int islandPerimeterHelper(int x, int y, int[][] grid) {
		if (grid[x][y] == 0) {
			return 0;
		}

		int result = 0;
		boolean checkPrevious = x != 0;
		boolean checkNext = x != grid.length - 1;

		if (!checkPrevious || !checkNext) {
			result++;
		}

		if (checkPrevious && grid[x - 1][y] == 0) {
			result++;
		}
		if (checkNext && grid[x + 1][y] == 0) {
			result++;
		}

		checkPrevious = y != 0;
		checkNext = y != grid[x].length - 1;

		if (!checkPrevious || !checkNext) {
			result++;
		}

		if (checkPrevious && grid[x][y - 1] == 0) {
			result++;
		}
		if (checkNext && grid[x][y + 1] == 0) {
			result++;
		}

		return result;
	}

	// 463. Island Perimeter
	public int islandPerimeter(int[][] grid) {
		int result = 0;

		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[x].length; y++) {
				result += islandPerimeterHelper(x, y, grid);
			}
		}

		return result;
	}

	// 2. Add two numbers
	public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		ListNode result = new ListNode();
		ListNode cur = result;
		int carry = 0;

		while (l1 != null || l2 != null || carry != 0) {
			int sum = carry;

			if (l1 != null) {
				sum = sum + l1.val;
				l1 = l1.next;
			}

			if (l2 != null) {
				sum = sum + l2.val;
				l2 = l2.next;
			}

			cur.next = new ListNode(sum % 10);
			cur = cur.next;

			carry = (sum - cur.val) / 10;
		}

		return result.next;
	}

	// 1275. Find winner on a Tic tac toe game, helper
	private boolean checkWinner(int[][] matrix, int turn) {
		boolean majorDiagonal = true;
		boolean minorDiagonal = true;

		for (int[] ints : matrix) {
			boolean flag = true;

			for (int col = 0; col < matrix.length; col++) {
				if (ints[col] != turn) {
					flag = false;
					break;
				}
			}

			if (flag) {
				return true;
			}
		}

		for (int col = 0; col < matrix.length; col++) {
			boolean flag = true;

			for (int[] ints : matrix) {
				if (ints[col] != turn) {
					flag = false;
					break;
				}
			}

			if (flag) {
				return true;
			}
		}

		for (int index = 0; index < matrix.length; index++) {
			majorDiagonal = majorDiagonal & (matrix[index][index] == turn);
			minorDiagonal = minorDiagonal & (matrix[matrix.length - index - 1][index] == turn);
		}

		return majorDiagonal || minorDiagonal;
	}

	// 1275. Find winner on a Tic tac toe game
	public String tictactoe(int[][] moves) {
		int[][] matrix = new int[][] { { -1, -1, -1 }, { -1, -1, -1 }, { -1, -1, -1 } };

		int turn = 0;
		for (int[] move : moves) {
			matrix[move[0]][move[1]] = turn;
			turn = (turn + 1) % 2;
		}

		if (checkWinner(matrix, 0)) {
			return "A";
		} else if (checkWinner(matrix, 1)) {
			return "B";
		}

		return moves.length != 9 ? "Pending" : "Draw";
	}

	// 1502. Can make arithmetic progression from sequence
	public boolean canMakeArithmeticProgression(int[] arr) {
		int min = arr[0];
		int max = arr[0];

		for (int elem : arr) {
			if (elem < min) {
				min = elem;
			} else if (max < elem) {
				max = elem;
			}
		}

		if ((max - min) % (arr.length - 1) != 0) {
			return false;
		}

		int diff = (max - min) / (arr.length - 1);
		if (diff == 0) {
			return true;
		}

		int[] visited = new int[arr.length];
		for (int elem : arr) {
			if ((elem - min) % diff != 0) {
				return false;
			}

			int index = (elem - min) / diff;
			if (visited[index] == 1) {
				return false;
			}
			visited[index] = 1;
		}

		return true;
	}

	// 54. Spiral matrix
	public List<Integer> spiralOrder(int[][] matrix) {
		ArrayList<Integer> result = new ArrayList<>();

		// direction to move
		int[][] directions = new int[][] { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
		int curDirInd = 0;

		// index
		int min_x = -1, max_x = matrix.length;
		int min_y = -1, max_y = matrix[0].length;
		int x = 0, y = 0;

		while (result.size() != matrix.length * matrix[0].length) {

			result.add(matrix[x][y]);

			x = x + directions[curDirInd][0];
			y = y + directions[curDirInd][1];

			if (x == max_x) {
				max_y--;
				x--;
				y--;
				curDirInd = (curDirInd + 1) % 4;
			} else if (y == max_y) {
				min_x++;
				y--;
				x++;
				curDirInd = (curDirInd + 1) % 4;
			} else if (x == min_x) {
				min_y++;
				x++;
				y++;
				curDirInd = (curDirInd + 1) % 4;
			} else if (y == min_y) {
				max_x--;
				y++;
				x--;
				curDirInd = (curDirInd + 1) % 4;
			}
		}

		return result;
	}

	// 13. Roman to Integer, helper (using switch case instead of map)
	private int romanToIntMap(char roman) {
		int result = 1;

		switch (roman) {
			case 'V' -> result = 5;
			case 'X' -> result = 10;
			case 'L' -> result = 50;
			case 'C' -> result = 100;
			case 'D' -> result = 500;
			case 'M' -> result = 1000;
		}

		return result;
	}

	// 13. Roman to Integer
	public int romanToInt(String s) {
		int result = 0;
		int currentIndex = 0;

		while (currentIndex < s.length()) {
			int currentValue = romanToIntMap(s.charAt(currentIndex++));

			if (currentIndex >= s.length()) {
				result = result + currentValue;
				break;
			}

			int nextValue = romanToIntMap(s.charAt(currentIndex));

			if (nextValue > currentValue) {
				result = result + nextValue - currentValue;
				currentIndex++;
			} else {
				result = result + currentValue;
			}
		}

		return result;
	}

	// 67 Add Binary
	public String addBinary(String a, String b) {

		// str a will always be longer than str b
		if (b.length() > a.length()) {
			String temp = b;
			b = a;
			a = temp;
		}

		StringBuilder result = new StringBuilder();
		int carry = 0;
		int indexA = a.length() - 1, indexB = b.length() - 1;

		while (indexA > -1 && indexB > -1) {
			int sum = a.charAt(indexA) + b.charAt(indexB) - 96 + carry;
			carry = sum / 2;
			sum = sum % 2;
			result.append(sum);

			indexA--;
			indexB--;
		}

		while (indexA > -1) {
			int sum = a.charAt(indexA) - 48 + carry;
			carry = sum / 2;
			sum = sum % 2;
			result.append(sum);

			indexA--;
		}

		if (carry == 1) {
			result.append(carry);
		}

		return result.reverse().toString();
	}

	// 459. Repeated SubString pattern
	public boolean repeatedSubstringPattern(String s) {

		// Step 1: Find subString length such that you can split length into equal parts
		// by length,
		// Step 2: Check if all equal length subStrings are same
		// Example: if string length is 9, the possible subString lengths are {3, 1}
		// Example 2: if string length is 16, the possible subString lengths are {8, 4,
		// 2, 1}
		for (int numberOfStrings = 2; numberOfStrings <= s.length(); numberOfStrings++) {
			if (s.length() % numberOfStrings != 0) {
				continue;
			}

			int subStringLength = s.length() / numberOfStrings;
			String subString = s.substring(0, subStringLength);
			boolean misMatch = false;

			for (int index = subStringLength; index < s.length(); index += subStringLength) {
				if (!subString.equals(s.substring(index, index + subStringLength))) {
					misMatch = true;
					break;
				}
			}

			if (!misMatch) {
				return true;
			}
		}

		return false;
	}

	// 1572. Matrix Diagonal Sum
	public int diagonalSum(int[][] mat) {
		int sum = 0;

		for (int x = 0; x < mat.length; x++) {
			sum += mat[x][x];
			sum += mat[x][mat.length - x - 1];
		}

		return mat.length % 2 == 0 ? sum - mat[mat.length / 2][mat.length / 2] : sum;
	}

	// 28. Find the index of the first occurrence of a string
	public int strStr(String haystack, String needle) {
		char[] haystackChars = haystack.toCharArray();
		char[] needleChars = needle.toCharArray();

		int startInd = 0;

		for (; startInd <= haystackChars.length - needleChars.length; startInd++) {
			int needledInd = 0;

			for (; needledInd < needle.length(); needledInd++) {
				if (haystackChars[startInd + needledInd] != needleChars[needledInd]) {
					break;
				}
			}

			if (needledInd == needleChars.length) {
				return startInd;
			}
		}

		return -1;
	}

	// 657. Robot return origin
	public boolean judgeCircle(String moves) {
		int xCoordinate = 0;
		int yCoordinate = 0;

		for (char ch : moves.toCharArray()) {
			switch (ch) {
				case 'L' -> xCoordinate++;
				case 'R' -> xCoordinate--;
				case 'U' -> yCoordinate++;
				case 'D' -> yCoordinate--;
			}
		}

		return xCoordinate == 0 && yCoordinate == 0;
	}

	// 682. Baseball Game
	public int calPoints(String[] operations) {
		int[] scores = new int[operations.length];
		int scoreIndex = 0;

		for (String operation : operations) {
			switch (operation) {
				case "C" -> scoreIndex = scoreIndex - 2;
				case "D" -> scores[scoreIndex] = scores[scoreIndex - 1] * 2;
				case "+" -> scores[scoreIndex] = scores[scoreIndex - 1] + scores[scoreIndex - 2];
				default -> scores[scoreIndex] = Integer.parseInt(operation);
			}
			scoreIndex++;
		}

		int finalScore = 0;

		while (scoreIndex > 0) {
			finalScore = finalScore + scores[--scoreIndex];
		}

		return finalScore;
	}

	// 66. Plus one
	public int[] plusOne(int[] digits) {
		int carry = 1;

		for (int index = digits.length - 1; index > -1; index--) {
			int sum = digits[index] + carry;
			digits[index] = sum % 10;
			carry = sum / 10;
		}

		if (carry == 0) {
			return digits;
		}

		int[] result = new int[digits.length + 1];
		System.arraycopy(digits, 0, result, 1, digits.length);
		result[0] = carry;

		return result;
	}

	// 1750. Minimum Length of strings after deleting similar ends
	public int minimumLength(String s) {
		char[] arr = s.toCharArray();
		int start = 0, end = arr.length - 1;
		while (start < end) {
			if (arr[start] != arr[end]) {
				break;
			}
			int p1 = start, p2 = end;
			while (arr[p1] == arr[start] && p1 < end) {
				p1++;
			}
			start = p1;
			while (arr[p2] == arr[end] && p2 >= start) {
				p2--;
			}
			end = p2;
		}

		return start > end ? 0 : end - start + 1;
	}

	// 2295. Replace Elements in an array
	public int[] arrayChange(int[] nums, int[][] operations) {
		HashMap<Integer, Integer> reverseIndex = new HashMap<>();

		for (int index = 0; index < nums.length; index++) {
			reverseIndex.put(nums[index], index);
		}

		for (int[] operation : operations) {
			int index = reverseIndex.remove(operation[0]);
			nums[index] = operation[1];
			reverseIndex.put(operation[1], index);
		}

		return nums;
	}

	// 841. Keys and Rooms, helper
	private int visitRoom(int roomNumber, List<List<Integer>> rooms, int[] visited, int roomsVisited) {
		visited[roomNumber] = 1;
		roomsVisited++;
		for (int roomKey : rooms.get(roomNumber)) {
			if (visited[roomKey] == 1) {
				continue;
			}
			roomsVisited = visitRoom(roomKey, rooms, visited, roomsVisited);
		}
		return roomsVisited;
	}

	// 841. Keys and Rooms
	public boolean canVisitAllRooms(List<List<Integer>> rooms) {
		return visitRoom(0, rooms, new int[rooms.size()], 0) == rooms.size();
	}

	// 107. Binary Tree Level Order Traversal, helper
	private void levelOrderBottom(TreeNode root, int depth, List<List<Integer>> result) {
		if (root == null) {
			return;
		}

		if (result.size() == depth) {
			result.add(new ArrayList<>());
		}

		result.get(depth).add(root.val);

		levelOrderBottom(root.left, depth + 1, result);
		levelOrderBottom(root.right, depth + 1, result);
	}

	// 107. Binary Tree Level Order Traversal
	public List<List<Integer>> levelOrderBottom(TreeNode root) {
		List<List<Integer>> result = new ArrayList<>();

		levelOrderBottom(root, 0, result);

		return result.reversed();
	}

	/**
	 * Question: 2092 Find all people with secret, helper
	 * Add a person and the set of the person to the secret keepers
	 */
	private void addToSecretKeepers(int person,
			HashMap<Integer, HashSet<Integer>> setToMembers,
			HashMap<Integer, Integer> memberToSet,
			HashSet<Integer> secretKeepers) {
		int setId = memberToSet.getOrDefault(person, -1);

		if (setId == -1) {
			secretKeepers.add(person);
			return;
		}

		HashSet<Integer> members = setToMembers.remove(setId);
		if (members != null) {
			secretKeepers.addAll(members);
		}
	}

	/**
	 * Question: 2092 Find all people with secret, helper
	 * This function performs the set union for people who do not know the secret
	 **/
	private int addMeetingToSet(int person1, int person2, HashMap<Integer, HashSet<Integer>> setToMembers,
			HashMap<Integer, Integer> memberToSet, int setId) {
		if (!memberToSet.containsKey(person1) && !memberToSet.containsKey(person2)) {
			setToMembers.put(setId, new HashSet<>(Arrays.asList(person1, person2)));
			memberToSet.put(person1, setId);
			memberToSet.put(person2, setId);
			setId++;
		} else if (memberToSet.containsKey(person1) && memberToSet.containsKey(person2)) {
			int set1 = memberToSet.get(person1);
			int set2 = memberToSet.get(person2);
			if (set1 == set2) {
				return setId;
			}

			// Update setToMembers
			HashSet<Integer> set2Members = setToMembers.remove(set2);
			HashSet<Integer> set1members = setToMembers.remove(set1);
			set1members.addAll(set2Members);
			setToMembers.put(set1, set1members);

			// Update memberToSet
			for (int member : set2Members) {
				memberToSet.put(member, set1);
			}
		} else if (memberToSet.containsKey(person1)) {
			int person1Set = memberToSet.get(person1);
			HashSet<Integer> set1Members = setToMembers.get(person1Set);
			set1Members.add(person2);

			memberToSet.put(person2, person1Set);
		} else {
			int person2Set = memberToSet.get(person2);
			HashSet<Integer> set2Members = setToMembers.get(person2Set);
			set2Members.add(person1);

			memberToSet.put(person1, person2Set);
		}

		return setId;
	}

	// 2092 Find all people with secret, Main function
	public List<Integer> findAllPeople(int n, int[][] meetings, int firstPerson) {
		/*
		 * Sort the meetings based on time
		 * Create a set for each person,
		 * The first person and 0th person will be in same set (knows secret)
		 * if someone knows the secret, then evey one in the set will know the secret ->
		 * add all of them to the result
		 */
		PriorityQueue<int[]> potentialMeetings = new PriorityQueue<>(
				Comparator.comparingInt(meeting -> meeting[2]));
		potentialMeetings.addAll(Arrays.asList(meetings));
		HashSet<Integer> secretKeepers = new HashSet<>(Arrays.asList(0, firstPerson));

		while (!potentialMeetings.isEmpty()) {
			HashMap<Integer, HashSet<Integer>> setToMembers = new HashMap<>();
			HashMap<Integer, Integer> memberToSet = new HashMap<>();
			int setId = 0;

			int timeStep = potentialMeetings.peek()[2];
			while (!potentialMeetings.isEmpty()) {
				if (potentialMeetings.peek()[2] != timeStep) {
					break;
				}
				int[] meeting = potentialMeetings.poll();

				if (!secretKeepers.contains(meeting[0]) && !secretKeepers.contains(meeting[1])) {
					setId = addMeetingToSet(meeting[0], meeting[1], setToMembers, memberToSet,
							setId);
				} else {
					addToSecretKeepers(meeting[0], setToMembers, memberToSet, secretKeepers);
					addToSecretKeepers(meeting[1], setToMembers, memberToSet, secretKeepers);
				}
			}
		}

		return secretKeepers.stream().toList();
	}

	// 119 Pascal Triangle II
	public List<Integer> getRow(int rowIndex) {
		// Use Binomial theorem. Instead of calculating the factorial
		// for all the numbers in the rage row index,
		// multiply the previous result with the difference
		List<Integer> result = new ArrayList<>();
		result.add(1);

		long coeff = 1;

		for (int r = 1; r <= rowIndex; r++) {
			coeff = coeff * (rowIndex - r + 1) / r;
			result.add((int) coeff);
		}

		return result;
	}

	// 988 Smallest String starting from leaf
	public String smallestFromLeaf(TreeNode root) {
		List<String> result = new ArrayList<>();
		smallestFromLeaf(root, result, new StringBuilder());
		return result.getFirst();
	}

	// 988 Smallest String starting from leaf helper
	private void getSmallerString(String currentString, List<String> result) {
		if (result.isEmpty()) {
			result.add(currentString);
			return;
		}

		if (currentString.compareTo(result.getFirst()) < 0) {
			result.set(0, currentString);
		}
	}

	// 988 Smallest String starting from leaf helper
	public boolean smallestFromLeaf(TreeNode root, List<String> result, StringBuilder currentString) {
		if (root == null) {
			return true;
		}

		if (!result.isEmpty()) {
			if (result.getFirst().length() < currentString.length()) {
				return false;
			}
		}

		currentString.insert(0, (char) (root.val + 'a'));
		boolean noLeftSubTree = smallestFromLeaf(root.left, result, currentString);
		boolean noRightSubTree = smallestFromLeaf(root.right, result, currentString);
		if (noLeftSubTree && noRightSubTree) {
			getSmallerString(currentString.toString(), result);
		}
		currentString.deleteCharAt(0);

		return false;
	}

	// 662 Max Width of binary tree
	public int widthOfBinaryTree(TreeNode root) {
		HashMap<Integer, int[]> widthMap = new HashMap<>();
		widthOfBinaryTree(root, 0, 0, widthMap);
		int result = 0;

		for (int[] width : widthMap.values()) {
			result = Math.max(result, width[1] - width[0] + 1);
		}
		return result;
	}

	// 662 Max Width of binary tree, helper
	private void widthOfBinaryTree(TreeNode root, int height, int index, HashMap<Integer, int[]> widthMap) {
		if (root == null) {
			return;
		}

		int[] width = widthMap.getOrDefault(height, new int[] { index, index });

		width[0] = Math.min(width[0], index);
		width[1] = Math.max(width[1], index);

		widthMap.put(index, width);

		widthOfBinaryTree(root.left, height + 1, index * 2 + 1, widthMap);
		widthOfBinaryTree(root.right, height + 1, index * 2 + 2, widthMap);
	}

	// 890 Helper
	public boolean checkPattern(char[] word, char[] pattern) {
		if (word.length != pattern.length) {
			return false;
		}
		int[] map = new int[27];
		int[] mappedAlphabets = new int[27];
		for (int index = 0; index < pattern.length; index++) {
			int key = pattern[index] - 96;
			if (map[key] == 0 && mappedAlphabets[word[index] - 96] == 0) {
				map[key] = word[index] - 96;
				mappedAlphabets[word[index] - 96] = 1;
			} else if (map[key] == 0 && mappedAlphabets[word[index] - 96] != 0) {
				return false;
			} else if (map[key] != (word[index] - 96)) {
				return false;
			}
		}

		return true;
	}

	// 890
	public List<String> findAndReplacePattern(String[] words, String pattern) {
		List<String> result = new ArrayList<>();
		char[] patternChar = pattern.toCharArray();
		for (String word : words) {
			if (checkPattern(word.toCharArray(), patternChar)) {
				result.add(word);
			}
		}
		return result;
	}

	// 1451.
	public String arrangeWords(String text) {
		String[] words = text.split(" ");
		words[0] = words[0].toLowerCase();
		Arrays.sort(words, Comparator.comparingInt(String::length));
		StringBuilder resultBuilder = new StringBuilder();
		for (String word : words) {
			resultBuilder
					.append(word)
					.append(" ");
		}
		resultBuilder.setCharAt(0, (char) (resultBuilder.charAt(0) - 32));
		resultBuilder.deleteCharAt(resultBuilder.length() - 1);
		return resultBuilder.toString();
	}

	// 274 helper
	public boolean countCitations(int[] citations, int hIndex) {
		int count = 0;
		for (int citation : citations) {
			if (citation >= hIndex) {
				count++;
			}
			if (count >= hIndex) {
				return true;
			}
		}
		return false;
	}

	// 274
	public int hIndex(int[] citations) {
		int hIndex;
		for (hIndex = citations.length; hIndex > -1; hIndex--) {
			if (countCitations(citations, hIndex)) {
				break;
			}
		}
		return hIndex;
	}

	// 150 helper
	public int evalRPN(Stack<Integer> operands, String operator) {
		int operand2 = operands.pop();
		int operand1 = operands.pop();
		return switch (operator) {
			case "+" -> operand1 + operand2;
			case "-" -> operand1 - operand2;
			case "*" -> operand1 * operand2;
			default -> operand1 / operand2;
		};
	}

	// 150
	public int evalRPN(String[] tokens) {
		Stack<Integer> operands = new Stack<>();
		HashSet<String> operators = new HashSet<>(Arrays.asList("+", "-", "*", "/", ""));
		for (String token : tokens) {
			if (!operators.contains(token)) {
				operands.push(Integer.parseInt(token));
				continue;
			}
			operands.push(evalRPN(operands, token));
		}
		return operands.pop();
	}

	// 1282
	public List<List<Integer>> groupThePeople(int[] groupSizes) {
		HashMap<Integer, List<Integer>> count = new HashMap<>();
		List<List<Integer>> result = new ArrayList<>();
		for (int index = 0; index < groupSizes.length; index++) {
			List<Integer> values = count.getOrDefault(groupSizes[index], new ArrayList<>());
			values.add(index);
			if (values.size() == groupSizes[index]) {
				result.add(values);
				count.put(groupSizes[index], new ArrayList<>());
			} else {
				count.put(groupSizes[index], values);
			}
		}
		return result;
	}

	// 1344. Angle Between Hands of a Clock
	public double angleClock(int hour, int minutes) {
		double result = Math.abs(minutes * 6 - (hour * 30 + 0.5 * minutes));
		return (result > 180) ? (360 - result) : result;
	}

	// 49. Group Anagrams
	public List<List<String>> groupAnagrams(String[] strs) {
		HashMap<String, List<String>> map = new HashMap<>();

		for (String str : strs) {
			String key = groupAnagramsHelper(str);
			List<String> val = map.getOrDefault(key, new ArrayList<>());
			val.add(str);
			map.put(key, val);
		}

		return new ArrayList<>(map.values());
	}

	public String groupAnagramsHelper(String str) {
		char[] arr = new char[26];
		for (char ch : str.toCharArray()) {
			arr[ch - 'a']++;
		}
		return new String(arr);
	}

	// 187. Repeated DNA Sequences
	public List<String> findRepeatedDnaSequences(String s) {
		List<String> result = new ArrayList<>();
		if (s.length() < 10) {
			return result;
		}
		HashMap<String, Integer> map = new HashMap<>();
		for (int index = 0; index < s.length() - 10; index++) {
			String key = s.substring(index, index + 10);
			map.put(key, map.getOrDefault(key, 0) + 1);
			if (map.get(key) == 2) {
				result.add(key);
			}
		}
		return result;
	}

	// 129. Sum Root to Leaf Numbers
	public int sumNumbers(TreeNode root) {
		return sumNumbers(root, 0);
	}

	public int sumNumbers(TreeNode root, int curNum) {
		if (root == null) {
			return 0;
		}
		curNum = curNum * 10 + root.val;
		if (root.left == null && root.right == null) {
			return curNum;
		}
		return sumNumbers(root.left, curNum) + sumNumbers(root.right, curNum);
	}

	// 116. Populating Next Right Pointers in Each Node
	public Node connect(Node root) {
		if (root != null) {
			connect(root.left, root.right);
		}
		return root;
	}

	// 116. Populating Next Right Pointers in Each Node
	public void connect(Node left, Node right) {
		if (left == null || right == null) {
			return;
		}
		left.right = right;
		connect(left.left, left.right);
		connect(right.left, right.right);
		connect(left.right, right.left);
	}

	// 120. Triangle
	public int minimumTotal(List<List<Integer>> triangle) {
		return minimumTotal(triangle, 1, triangle.getFirst(), triangle.getFirst().getFirst());
	}

	// 120. Triangle
	// This function gets the input triangle and computes the sum of the current row
	// and returns the minimum of the current row
	public int minimumTotal(List<List<Integer>> triangle, int currentRowIndex, List<Integer> sumOfValues,
			int minimum) {
		if (currentRowIndex == triangle.size()) {
			return minimum;
		}
		List<Integer> nextSumOfValues = new ArrayList<>();
		nextSumOfValues.add(triangle.get(currentRowIndex).getFirst() + sumOfValues.getFirst());
		int minimumForCurrentRow = nextSumOfValues.getFirst();
		for (int index = 1; index < currentRowIndex; index++) {
			int curSum = Math.min(
					triangle.get(currentRowIndex).get(index) + sumOfValues.get(index - 1),
					triangle.get(currentRowIndex).get(index) + sumOfValues.get(index));
			minimumForCurrentRow = Math.min(curSum, minimumForCurrentRow);
			nextSumOfValues.add(curSum);
		}
		if (currentRowIndex != 0) {
			nextSumOfValues
					.add(triangle.get(currentRowIndex).get(currentRowIndex)
							+ sumOfValues.get(currentRowIndex - 1));
			minimumForCurrentRow = Math.min(nextSumOfValues.getLast(), minimumForCurrentRow);
		}
		return minimumTotal(triangle, currentRowIndex + 1, nextSumOfValues, minimumForCurrentRow);
	}

	// 343. Integer Break, function returns the product when the number n is split k
	// times with each split of size splitSize
	double getProduct(int n, int splitSize, int k) {
		return Math.pow(splitSize, k - 1) * (n - (splitSize * (k - 1)));
	}

	// 343. Integer Break
	// Split the number n in k parts(Each part should have max value i,e split
	// equally when possible):
	// - In k-1 parts, with each of size floor(n/k) or ceil(n/k)
	// - The remaining 1 part will be n-floor(n/k) or n-ceil(n/k)
	// The final product will be (product of k-1 parts)*(product of 1 part)
	public int integerBreak(int n) {
		double result = 0;
		for (int k = 2; k <= n / 2 + 1; k++) {
			double product = Math.max(
					getProduct(n, (int) Math.ceil((double) n / k), k),
					getProduct(n, (int) Math.floor((double) n / k), k));
			if (product > result) {
				result = product;
			}
		}
		return (int) result;
	}

	// 1743. Restore the Array From Adjacent Pairs. Helper: Add entry adjacency
	// list(in this case it is a HashMap)
	public static void restoreArrayHelper(int key, int value, HashMap<Integer, List<Integer>> map) {
		List<Integer> values = map.getOrDefault(key, new ArrayList<>());
		values.add(value);
		map.put(key, values);
	}

	// 1743. Restore the Array From Adjacent Pairs
	public int[] restoreArray(int[][] adjacentPairs) {
		int[] result = new int[adjacentPairs.length + 1];

		// Construct adjacency list as a Map
		HashMap<Integer, List<Integer>> map = new HashMap<>();
		for (int[] pair : adjacentPairs) {
			restoreArrayHelper(pair[0], pair[1], map);
			restoreArrayHelper(pair[1], pair[0], map);
		}

		// Get corner element i.e. element with one neighbour
		for (int key : map.keySet()) {
			if (map.get(key).size() == 1) {
				result[0] = key;
				break;
			}
		}

		// Construct original array
		for (int index = 1; index < result.length; index++) {
			int prev = result[index - 1];
			List<Integer> neighbours = map.remove(prev);
			if (neighbours.size() == 1) {
				result[index] = neighbours.getFirst();
				continue;
			}
			for (int neighbour : neighbours) {
				if (map.containsKey(neighbour)) {
					result[index] = neighbour;
				}
			}
		}

		return result;
	}

	// 2191. Sort the Jumbled Numbers, Helper
	public static int getVal(int[] mappings, int val) {
		int res = 0, power = 0;
		do {
			res = res + (int) Math.pow(10, power) * mappings[val % 10];
			power++;
			val = val / 10;
		} while (val > 0);
		return res;
	}

	// 2191. Sort the Jumbled Numbers
	public int[] sortJumbled(int[] mapping, int[] nums) {
		int[][] arr = new int[nums.length][2];
		for (int index = 0; index < nums.length; index++) {
			arr[index][0] = nums[index];
			arr[index][1] = getVal(mapping, nums[index]);
		}
		Arrays.sort(arr, Comparator.comparingInt(o -> o[1]));
		for (int index = 0; index < nums.length; index++) {
			nums[index] = arr[index][0];
		}
		return nums;
	}

	// 108. Convert Sorted Array to Binary Algorithms.Search Tree, Helper
	public void addNode(TreeNode root, TreeNode node) {
		if (root.val < node.val) {
			if (root.right == null) {
				root.right = node;
			}
			addNode(root.right, node);
		} else {
			if (root.left == null) {
				root.left = node;
			}
			addNode(root.left, node);
		}
	}

	// 108. Convert Sorted Array to Binary Algorithms.Search Tree
	public void addNode(TreeNode root, int beg, int end, int[] nums) {
		if (beg == end) {
			addNode(root, new TreeNode(nums[beg]));
			return;
		}
		TreeNode mid = new TreeNode(nums[(beg + end) / 2]);
		addNode(root, mid);
		addNode(mid, beg, ((beg + end) / 2) - 1, nums);
		addNode(root, ((beg + end) / 2) + 1, end, nums);
	}

	// 108. Convert Sorted Array to Binary Algorithms.Search Tree
	public TreeNode sortedArrayToBST(int[] nums) {
		TreeNode root = new TreeNode(nums[(nums.length) / 2]);
		addNode(root, 0, ((nums.length) / 2) - 1, nums);
		addNode(root, ((nums.length) / 2) - 1, nums.length - 1, nums);
		return root;
	}

	// 738. Monotone Increasing Digits
	public int monotoneIncreasingDigits(int n, int cur) {
		if (cur > n)
			return -1;
		int res = -1;
		for (int val = 9; val >= cur % 10; val--) {
			res = monotoneIncreasingDigits(n, cur * 10 + val);
			if (res != -1)
				break;
		}
		return res;
	}

	// 738. Monotone Increasing Digits
	public int monotoneIncreasingDigits(int n) {
		if (n < 10)
			return n;
		return monotoneIncreasingDigits(n, 1);
	}

	// 565. Array Nesting
	public int getNodes(int[] nums, int[] visited, int[] dist, int cur, int step) {
		if (visited[cur] == 1)
			return step;
		if (dist[cur] != 0)
			return dist[cur];
		visited[cur] = 1;
		int temp = getNodes(nums, visited, dist, nums[cur], step + 1);
		if (temp > dist[cur])
			dist[cur] = temp;
		return dist[cur];
	}

	// 565. Array Nesting
	public int arrayNesting(int[] nums) {
		int res = 0;
		int[] dist = new int[nums.length];
		for (int num : nums) {
			int temp = getNodes(nums, new int[nums.length], dist, num, 0);
			if (temp > res)
				res = temp;
		}
		return res;
	}

	// 1702. Maximum Binary String After Change
	public String maximumBinaryString(String binary) {
		int index = 0;
		char[] binaryArr = binary.toCharArray();
		while (index < binaryArr.length) {
			if (binaryArr[index] == '0')
				break;
			index++;
		}
		int count0 = 0;
		for (int curInd = index; curInd < binaryArr.length; curInd++) {
			if (binaryArr[curInd] == '0')
				count0++;
			binaryArr[curInd] = '1';
		}
		if (index < binaryArr.length)
			binaryArr[index + count0 - 1] = '0';
		return String.valueOf(binaryArr);
	}

	// 787. Cheapest Flights Within K Stops
	int finalFlightPrice = -1;

	public void findCheapestPrice(HashMap<Integer, PriorityQueue<int[]>> adjList, int[] visited, int src, int dst,
			int k, int curPrice) {
		if (src == dst) {
			if (finalFlightPrice == -1 || finalFlightPrice > curPrice)
				finalFlightPrice = curPrice;
			return;
		}
		if (curPrice > finalFlightPrice && finalFlightPrice != -1)
			return;
		if (k == 0)
			return;
		for (int[] flight : adjList.get(src)) {
			if (visited[flight[0]] == 1)
				continue;
			visited[flight[0]] = 1;
			findCheapestPrice(adjList, visited, flight[0], dst, k - 1, curPrice + flight[1]);
			visited[flight[0]] = 0;
		}
	}

	// 787. Cheapest Flights Within K Stops
	public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
		HashMap<Integer, PriorityQueue<int[]>> adjList = new HashMap<>();

		for (int index = 0; index < n; index++) {
			adjList.put(index, new PriorityQueue<>(Comparator.comparingInt(o -> o[1])));
		}

		for (int[] flight : flights)
			adjList.get(flight[0]).add(new int[] { flight[1], flight[2] });
		int[] visited = new int[n];
		visited[src] = 1;
		findCheapestPrice(adjList, visited, src, dst, k + 1, 0);
		return finalFlightPrice;
	}

	// 787. Cheapest Flights Within K Stops - sol1 Time Limit
	public void findCheapestPrice1(int[][] adjMat, int src, int dst, int k, int curPrice) {
		if (src == dst) {
			if (finalFlightPrice == -1 || finalFlightPrice > curPrice)
				finalFlightPrice = curPrice;
			return;
		}
		if (curPrice > finalFlightPrice && finalFlightPrice != -1)
			return;
		if (k == 0)
			return;
		for (int index = 0; index < adjMat.length; index++) {
			if (adjMat[src][index] == 0)
				continue;
			int temp = adjMat[src][index];
			adjMat[src][index] = 0;
			findCheapestPrice1(adjMat, index, dst, k - 1, curPrice + temp);
			adjMat[src][index] = temp;
		}
	}

	// 787. Cheapest Flights Within K Stops - sol1 Time Limit
	public int findCheapestPrice1(int n, int[][] flights, int src, int dst, int k) {
		int[][] adjMat = new int[n][n];
		for (int[] flight : flights)
			adjMat[flight[0]][flight[1]] = flight[2];
		findCheapestPrice1(adjMat, src, dst, k + 1, 0);
		return finalFlightPrice;
	}

	// 693. Binary Number with Alternating Bits
	public boolean hasAlternatingBits(int n) {
		int power = 1, flag = n & 1, val = 2;
		while (val <= n) {
			int temp = val & n;
			if (temp > 0)
				temp = 1;
			if (temp == flag)
				return false;
			flag = temp;
			power++;
			val = (int) Math.pow(2, power);
		}
		return true;
	}

	// 1462. Course Schedule I sol12
	public boolean checkPath2(HashMap<Integer, HashSet<Integer>> adjList, int index, int target) {
		List<Integer> q = new ArrayList<>();
		q.add(index);
		while (!q.isEmpty()) {
			int cur = q.removeFirst();
			if (cur == target)
				return true;
			q.addAll(adjList.get(cur));
		}
		return false;
	}

	// 1462. Course Schedule I sol2
	public List<Boolean> checkIfPrerequisite2(int numCourses, int[][] prerequisites, int[][] queries) {
		HashMap<Integer, HashSet<Integer>> adjList = new HashMap<>();
		for (int index = 0; index < numCourses; index++)
			adjList.put(index, new HashSet<>());
		List<Boolean> res = new ArrayList<>();
		for (int[] prereq : prerequisites) {
			adjList.get(prereq[1]).add(prereq[0]);
		}
		for (int[] query : queries) {
			res.add(checkPath2(adjList, query[1], query[0]));
		}
		return res;
	}

	// 1462. Course Schedule I sol1
	public boolean checkPath(GraphNode[] nodes, int index, int target) {
		if (index == target)
			return true;
		if (nodes[index] == null)
			return false;
		for (int id : nodes[index].child) {
			if (checkPath(nodes, id, target))
				return true;
		}
		return false;
	}

	// 1462. Course Schedule I sol1
	public List<Boolean> checkIfPrerequisite(int numCourses, int[][] prerequisites, int[][] queries) {
		GraphNode[] nodes = new GraphNode[numCourses];
		List<Boolean> res = new ArrayList<>();
		for (int[] prereq : prerequisites) {
			if (nodes[prereq[1]] == null) {
				nodes[prereq[1]] = new GraphNode(prereq[0]);
			}
			nodes[prereq[1]].child.add(prereq[0]);
		}
		for (int[] query : queries) {
			res.add(checkPath(nodes, query[1], query[0]));
		}
		return res;
	}

	// 2280. Minimum Lines to Represent a Line Chart
	public int minimumLines(int[][] arr) {
		if (arr.length == 1)
			return 0;
		Arrays.sort(arr, Comparator.comparingInt(o -> o[0]));
		float slope = ((float) arr[0][1] - arr[1][1]) / ((float) arr[0][0] - arr[1][0]);
		float cur = slope;
		System.out.println(cur);
		int lines = 1;
		for (int index = 1; index < arr.length - 1; index++) {
			cur = ((float) arr[index][1] - arr[index + 1][1]) / ((float) arr[index][0] - arr[1 + index][0]);
			System.out.println(cur);
			if (cur != slope) {
				lines++;
				slope = cur;
			}
		}
		if (cur != slope)
			lines++;
		return lines;
	}

	// 322. Coin Change
	public int coinChange(int[] coins, int amount) {
		Arrays.sort(coins);
		int beg = -1, end = coins.length;
		while (++beg < --end) {
			int temp = coins[beg];
			coins[beg] = coins[end];
			coins[end] = temp;
		}
		return coinChange(coins, amount, 0, 0);
	}

	// 322. Coin Change, Helper
	public int coinChange(int[] coins, int amt, int index, int res) {
		if (index == coins.length && amt != 0)
			return -1;
		if (amt == 0)
			return res;
		int curres = -1;
		for (int cur = index; cur < coins.length; cur++) {
			int rem = amt / coins[index];
			while (rem > 0 && curres == -1) {
				curres = coinChange(coins, amt - rem * coins[index], index + 1, rem + res);
				rem--;
			}
			curres = coinChange(coins, amt, index + 1, res);
			if (curres != -1)
				break;
		}
		return curres;
	}

	// 2090. K Radius Subarray Averages
	public int[] getRes(int len) {
		int[] res = new int[len];
		Arrays.fill(res, -1);
		return res;
	}

	public int[] getAverages(int[] nums, int k) {
		int[] res = getRes(nums.length);
		if (k * 2 >= nums.length)
			return res;
		int temp = 2 * k + 1, ind, size = nums.length - 1;
		double sum = 0;
		while (temp-- > 0)
			sum += nums[temp];
		size -= k;
		for (ind = k; ind < size; ind++) {
			res[ind] = (int) (sum / (2 * k + 1));
			sum += nums[ind + k + 1] - nums[ind - k];
		}
		res[ind] = (int) (sum / (2 * k + 1));
		return res;
	}

	// 1162. As Far from Land as Possible, Helper
	public int manhattanDist(int[] coord1, int[] coord2) {
		int res = 0;
		for (int index = 0; index < coord1.length; index++) {
			res += Math.abs(coord1[index] - coord2[index]);
		}
		return res;
	}

	// 1162. As Far from Land as Possible
	public int maxDistance(int[][] grid) {
		List<int[]> land = new ArrayList<>(), water = new ArrayList<>();
		for (int row = 0; row < grid.length; row++) {
			for (int col = 0; col < grid[0].length; col++) {
				water.add(new int[] { row, col });
			}
		}
		int res = grid[water.getFirst()[0]][water.getFirst()[1]];
		for (int[] l : land) {
			for (int[] w : water) {
				int dist = manhattanDist(l, w);
				if (grid[w[0]][w[1]] < dist)
					grid[w[0]][w[1]] = dist;
			}
		}
		for (int[] w : water) {
			if (grid[w[0]][w[1]] > res)
				res = grid[w[0]][w[1]];
		}
		return res;
	}

	// 480. Sliding Window Median, helper
	public static int binarySearch(ArrayList<Integer> arr, int target) {
		int mid = (arr.size() - 1) / 2, beg = 0, end = arr.size() - 1;
		while (beg < end) {
			if (arr.get(mid) == target)
				return mid;
			if (arr.get(mid) > target)
				end = mid - 1;
			else
				beg = mid + 1;
			mid = (beg + end) / 2;
		}
		return mid;
	}

	// 480. Sliding Window Median
	public double[] medianSlidingWindow(int[] nums, int k) {
		double[] res = new double[nums.length + 1 - k];
		int resInd = 0, beg = 0, end = k;
		ArrayList<Integer> window = new ArrayList<>();
		for (int index = 0; index < k; index++)
			window.add(nums[index]);
		window.sort((o1, o2) -> (int) ((double) o1 - o2));
		while (end < nums.length) {
			System.out.println(window);
			if (k % 2 == 0)
				res[resInd++] = window.get(k / 2) / 2.0 + window.get(k / 2 - 1) / 2.0;
			else
				res[resInd++] = window.get(k / 2);
			window.remove(binarySearch(window, nums[beg]));
			int ind = 0;
			while (ind < window.size()) {
				if (window.get(ind) > nums[end])
					break;
				ind++;
			}
			window.add(ind, nums[end]);
			beg++;
			end++;
		}
		if (k % 2 == 0)
			res[resInd++] = window.get(k / 2) / 2.0 + window.get(k / 2 - 1) / 2.0;
		else
			res[resInd++] = window.get(k / 2);
		return res;
	}

	// 576. Out of Boundary Paths, helper
	public int findPaths(int x, int y, int m, int n, int maxMove, int res) {
		if (maxMove == 0) {
			if (x < 0 || y < 0 || x >= m || y >= n) {
				return (++res) % ((int) Math.pow(10, 9) + 7);
			}
			return res;
		}
		findPaths(x + 1, y, m, n, maxMove - 1, res);
		findPaths(x - 1, y, m, n, maxMove - 1, res);
		findPaths(x, y + 1, m, n, maxMove - 1, res);
		findPaths(x, y - 1, m, n, maxMove - 1, res);
		return res;
	}

	// 576. Out of Boundary Paths
	public int findPaths(int m, int n, int maxMove, int startRow, int startColumn) {
		int res = 0;
		return findPaths(startRow, startColumn, m, n, maxMove, res);
	}

	// 2155. All Divisions With the Highest Score of a Binary Array
	public List<Integer> maxScoreIndices(int[] nums) {
		int curScore = 0, size = nums.length + 1;
		List<Integer> res = new ArrayList<>();
		for (int num : nums) {
			if (num == 1)
				curScore += 1;
		}
		int max = curScore;
		for (int index = 1; index < size; index++) {
			if (nums[index - 1] == 1)
				curScore--;
			if (nums[index - 1] == 0)
				curScore++;
			if (max < curScore) {
				max = curScore;
				res.clear();
			}
			if (max == curScore)
				res.add(index);
		}
		return res;
	}

	// 222. Count Complete Tree Nodes, Helper
	public int depth(TreeNode root) {
		int res = 0;
		while (root != null) {
			root = root.left;
			res++;
		}
		return res;
	}

	// 222. Count Complete Tree Nodes, Helper
	public int countNodes(TreeNode n1, TreeNode n2, int res, int depth) {
		if (n1 == null)
			return res;
		int d1 = depth(n1), d2 = depth(n2), count;
		if (d1 == d2) {
			count = countNodes(n2.left, n2.right, res + (int) Math.pow(2, depth), depth - 1);
		} else {
			count = countNodes(n1.left, n1.right, res + (int) Math.pow(2, depth - 1), depth - 1);
		}
		return count;
	}

	// 222. Count Complete Tree Nodes
	public int countNodes(TreeNode root) {
		/*
		 * Total number of nodes in full binary tree is 2^(n)-1
		 * where n is the depth of the tree. recursivley check depth of left and
		 * right subtree. If the depth of right tree is less than left tree,
		 * then left subtree is a full binary tree, next time check using right subtree.
		 * if both the depths are equal then, right subtree is a full binary tree,
		 * next iteration check with the left subtree.
		 */
		if (root == null)
			return 0;
		int d = depth(root);
		return countNodes(root.left, root.right, 1, d - 1);
	}

	// 74. Algorithms. Search 2D matrix
	public static boolean binarySearch(int[] arr, int target) {
		int mid = (arr.length - 1) / 2, beg = 0, end = arr.length - 1;
		while (beg < end) {
			if (arr[mid] == target)
				return true;
			if (arr[mid] > target)
				end = mid - 1;
			else
				beg = mid + 1;
			mid = (beg + end) / 2;
		}
		return arr[mid] == target;
	}

	// 74. Algorithms.Search a 2D Matrix, Helper
	public static int modSearch(int[][] arr, int target) {
		int mid = (arr.length - 1) / 2, beg = 0, end = arr.length - 1;
		while (beg < end) {
			if (arr[mid][0] == target)
				return mid;
			if (arr[mid][0] > target)
				end = mid - 1;
			else
				beg = mid + 1;
			mid = (beg + end) / 2;
		}
		return mid;
	}

	// 74. Algorithms.Search a 2D Matrix
	public boolean searchMatrix(int[][] matrix, int target) {
		int index = modSearch(matrix, target);
		if (matrix[index][0] > target && index != 0)
			index = index - 1;
		return binarySearch(matrix[index], target);
	}

	// 566. Reshape the Matrix
	public int[][] matrixReshape(int[][] mat, int r, int c) {
		if (r == mat.length && c == mat[0].length)
			return mat;
		if (r * c > mat.length * mat[0].length)
			return mat;
		int[][] res = new int[r][c];
		int rcur = 0, ccur = 0;
		for (int[] ints : mat) {
			for (int cind = 0; cind < mat[0].length; cind++) {
				if (ccur == c) {
					rcur++;
					ccur = 0;
				}
				res[rcur][ccur++] = ints[cind];
			}
		}
		return res;
	}

	// 1. Two Sum
	public int[] twoSumOne(int[] nums, int target) {
		HashMap<Integer, Integer> map = new HashMap<>();
		for (int ind = 0; ind < nums.length; ind++) {
			if (map.containsKey(nums[ind])) {
				return new int[] { map.get(nums[ind]), ind };
			}
			map.put(target - nums[ind], ind);
		}
		return new int[2];
	}

	// 88. Merge Sorted Array
	public void merge(int[] nums1, int m, int[] nums2, int n) {
		int resind = m + n - 1, ind1 = m - 1, ind2 = n - 1;
		while (ind1 > -1 && ind2 > -1) {
			if (nums1[ind1] > nums2[ind2]) {
				nums1[resind] = nums1[ind1];
				ind1--;
			} else {
				nums1[resind] = nums2[ind2];
				ind2--;
			}
			resind--;
		}
		while (ind1 > -1) {
			nums1[resind] = nums1[ind1];
			ind1 = ind1 - 1;
			resind = resind - 1;
		}
		while (ind2 > -1) {
			nums1[resind] = nums2[ind2];
			resind = resind - 1;
			ind2 = ind2 - 1;
		}
	}

	// 331. Verify Preorder Serialization of a Binary Tree
	public int verifyPreorder(int index, String[] preorder, Stack<String> stack) {
		if (index == -1)
			return -1;
		if (index >= preorder.length)
			return index;
		if (preorder[index].equals("#"))
			return index + 1;
		if (index + 2 > preorder.length)
			return -1;
		int curIndex = index;
		stack.push(preorder[index]);
		index = verifyPreorder(verifyPreorder(index + 1, preorder, stack), preorder, stack);
		if (stack.peek().equals(preorder[curIndex]))
			stack.pop();
		else
			return preorder.length;
		return index;
	}

	// 331. Verify Preorder Serialization of a Binary Tree
	boolean isValidSerialization(String preorder) {
		String[] arr = preorder.split(",");
		if (arr.length == 1) {
			return arr[0].equals("#");
		}
		if (!arr[arr.length - 1].equals("#") || !arr[arr.length - 2].equals("#"))
			return false;
		Stack<String> stack = new Stack<>();
		int index = verifyPreorder(0, arr, stack);
		if (index < arr.length)
			return false;
		return stack.isEmpty();
	}

	// 1840. Maximum Building Height
	public int maxBuilding(int n, int[][] restrictions) {
		if (restrictions.length == 0)
			return n;
		int[] heights = new int[n];
		int result = 0, index;
		for (index = 0; index < restrictions.length; index++) {
			if (restrictions[index][0] == 2)
				result = index;
			heights[restrictions[index][0]] = restrictions[index][1];
		}
		if (restrictions[result][1] == 0)
			heights[1] = 0;
		else
			heights[1] = 1;
		for (index = 2; index < n - 1; index++) {
			if (heights[index] == 0) {
				heights[index] = heights[index - 1] + 1;
			}

		}
		for (index = 0; index < n; index++) {
			if (result < heights[index])
				result = heights[index];
		}
		return result;
	}

	// 938. Range Sum of BST, Helper
	public int findSum(TreeNode root, int low, int high, int result) {
		if (root == null)
			return 0;
		if (root.val < low)
			result += findSum(root.right, low, high, result);
		else if (root.val > high)
			result += findSum(root.left, low, high, result);
		else
			result += findSum(root.left, low, high, result) + findSum(root.right, low, high, result);
		if (root.val >= low && root.val <= high)
			result = result + root.val;
		return result;
	}

	// 938. Range Sum of BST
	public int rangeSumBST(TreeNode root, int low, int high) {
		return findSum(root, low, high, 0);
	}

	// 1437. Check If All 1's Are at Least Length K Places Away
	public boolean kLengthApart(int[] nums, int k) {
		int cur = -1, prev = -1, i;
		k++;
		for (i = 0; i < nums.length; i++) {
			if (prev == -1 && nums[i] == 1) {
				prev = i;
				continue;
			}
			if (nums[i] == 1) {
				cur = i;
				break;
			}
		}
		++i;
		for (; i < nums.length; i++) {
			if (cur - prev >= k)
				return false;
			if (nums[i] == 1) {
				prev = cur;
				cur = i;
			}
		}
		return !(cur - prev >= k);
	}

	// 350. Intersection of Two Arrays II
	// sol 1
	public int[] intersect(int[] nums1, int[] nums2) {
		HashMap<Integer, Integer> map = new HashMap<>();
		List<Integer> result = new ArrayList<>();
		for (int num : nums1) {
			map.put(num, map.getOrDefault(num, 0) + 1);
		}
		for (int num : nums2) {
			if (map.containsKey(num)) {
				result.add(num);
				if (map.get(num) == 1)
					map.remove(num);
				else
					map.put(num, map.get(num) - 1);
			}
		}
		return result.stream().mapToInt(num -> num).toArray();
	}

	// 350. Intersection of Two Arrays II
	// sol 2
	public int[] intersect1(int[] nums1, int[] nums2) {
		Arrays.sort(nums1);
		Arrays.sort(nums2);
		int ind1 = 0, ind2 = 0;
		List<Integer> result = new ArrayList<>();
		while (ind1 < nums1.length && ind2 < nums2.length) {
			if (nums1[ind1] > nums2[ind2])
				ind2++;
			else if (nums1[ind1] < nums2[ind2])
				ind1++;
			else {
				result.add(nums1[ind1]);
				ind1++;
				ind2++;
			}
		}
		return result.stream().mapToInt(num -> num).toArray();
	}

	// 1268. Algorithms Search Suggestions System, Helper
	// insert in lexicographic order and list max size is 3
	public static void insertInList(List<String> result, String val) {
		int ind;
		if (result.size() < 3) {
			for (ind = 0; ind < result.size(); ind++) {
				if (result.get(ind).compareTo(val) > 0) {
					break;
				}
			}
			result.add(ind, val);
		} else {
			for (ind = 0; ind < 3; ind++) {
				if (result.get(ind).compareTo(val) > 0) {
					result.set(ind, val);
					break;
				}
			}
		}
	}

	// 1268. Algorithms.Search Suggestions System
	public List<List<String>> suggestedProducts(String[] products, String searchWord) {
		List<List<String>> results = new ArrayList<>();
		char[] flags = new char[products.length];
		for (int ind = 0; ind < searchWord.length(); ind++) {
			List<String> result = new ArrayList<>();
			for (int productInd = 0; productInd < products.length; productInd++) {
				if (flags[productInd] == '\0')
					continue;
				if (products[productInd].charAt(ind) == searchWord.charAt(ind)) {
					flags[ind] = '1';
					insertInList(result, products[productInd]);
				}
			}
			results.add(result);
		}
		return results;
	}

	// 2011. Final Value of Variable After Performing Operations
	public int finalValueAfterOperations(String[] operations) {
		int x = 0;
		for (String operation : operations) {
			char s = operation.charAt(0), e = operation.charAt(operation.length() - 1);
			if (s == '+')
				++x;
			else if (s == '-')
				--x;
			else if (e == '+')
				x++;
			else
				x--;
		}
		return x;
	}

	// 1769. Minimum Number of Operations to Move All Balls to Each Box
	public int[] minOperations(String boxes) {
		char[] array = boxes.toCharArray();
		int l = 0, r = 0, size = array.length;
		int[] ans = new int[size];
		for (int ind = 0; ind < size; ind++) {
			if (array[ind] == '1') {
				ans[0] = ans[0] + ind;
				r++;
			}
		}

		for (int ind = 1; ind < size; ind++) {
			if (array[ind - 1] == '1') {
				r--;
				l++;
			}
			ans[ind] = ans[ind - 1] - r + l;
		}
		return ans;
	}

	public int guess(int n) {
		int num = 3;
		if (n > num) {
			return -1;
		} else if (n < num) {
			return 1;
		}
		return 0;
	}

	public int guessNumber(int n) {
		if (n == 1)
			return 1;
		int beg = 1, end = n, mid, guess;
		while (beg <= end) {
			mid = beg / 2;
			mid += end / 2;
			guess = guess(mid);
			if (guess == -1) {
				end = mid - 1;
			} else if (guess == 1) {
				beg = mid + 1;
			} else {
				return mid;
			}
		}
		return -1;
	}

	// 167. Two Sum II - Input Array Is Sorted
	public int[] twoSum(int[] numbers, int target) {
		int ind1 = numbers.length - 1, ind2 = 0, sum = numbers[numbers.length - 1] + numbers[0];
		while (sum != target) {
			if (sum > target) {
				ind1--;
			} else {
				ind2++;
			}
			sum = numbers[ind1] + numbers[ind2];
		}
		return new int[] { ind2 + 1, ind1 + 1 };
	}

	public double average(int[] salary) {
		int min = salary[0], max = salary[0], size = salary.length, cur;
		double sum = 0;
		for (int i : salary) {
			cur = i;
			sum += cur;
			if (cur > max) {
				max = cur;
			} else if (cur < min) {
				min = cur;
			}
		}
		return (sum - max - min) / (size - 2);
	}

	public int trailingZeroes(int n) {
		int count5s = 0, cur;
		for (int num = 1; num <= n; num++) {
			cur = num;
			while (cur % 5 == 0) {
				cur = cur / 5;
				count5s++;
			}
		}
		return count5s;
	}

	public int numSplits(String s) {
		if (s.isEmpty())
			return 0;
		int[] map1 = new int[26], map2 = new int[26];
		int count1 = 1, count2 = 0, res = 0, size = s.length(), ch;
		map1[s.charAt(0) - 'a'] = 1;
		for (int ind = 1; ind < size; ind++) {
			ch = s.charAt(ind) - 'a';
			if (map2[ch] == 0)
				count2++;
			map2[ch]++;
		}
		if (count1 == count2)
			res++;
		for (int ind = 1; ind < size - 1; ind++) {
			ch = s.charAt(ind) - 'a';
			if (map1[ch] == 0) {
				map1[ch]++;
				count1++;
			}
			if (--map2[ch] == 0) {
				count2--;
			}
			if (count1 == count2)
				res++;
		}
		return res;
	}

	public int numOfSubarrays(int[] arr) {
		int[] sum = new int[arr.length];
		int res = 0;
		sum[0] = arr[0];
		if (arr[0] % 2 != 0)
			res++;
		for (int i = 1; i < arr.length; i++) {
			if (arr[i] % 2 != 0)
				res++;
			sum[i] = arr[i] + sum[i - 1];
		}
		if (sum[sum.length - 1] % 2 != 0)
			res++;
		for (int len = 2; len < arr.length - 1; len++) {
			if (sum[len - 1] % 2 != 0)
				res++;
			for (int beg = len + 1; beg < arr.length; beg++) {
				if (sum[beg] - sum[beg - len] % 2 != 0)
					res++;
			}
		}
		return res;
	}
}

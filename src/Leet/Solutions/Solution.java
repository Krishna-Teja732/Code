package Leet.Solutions;
import Leet.DataStructures.GraphNode;
import Leet.DataStructures.ListNode;
import Leet.DataStructures.TreeNode;
import Leet.DataStructures.Node;
import Leet.Main;
import Leet.utils.Helper;

import java.util.*;
import java.util.stream.IntStream;

public class Solution {

    // 988 Smallest String starting from leaf
    public String smallestFromLeaf(TreeNode root) {
        List<String> result = new ArrayList<>();
        smallestFromLeaf(root, result, new StringBuilder());
        return result.getFirst();
    }

    private void getSmallerString(String currentString, List<String> result) {
        if (result.isEmpty()) {
            result.add(currentString);
            return;
        }

        if (currentString.compareTo(result.getFirst()) < 0) {
            result.set(0, currentString);
        }
    }

    // 988 Smallest String starting from leaf
    public boolean smallestFromLeaf(TreeNode root, List<String> result, StringBuilder currentString) {
        if (root == null) {
            return true;
        }

        if (!result.isEmpty()) {
            if (result.getFirst().length() < currentString.length()) {
                return false;
            }
        }

        currentString.insert(0, (char)(root.val+'a'));
        boolean noLeftSubTree = smallestFromLeaf(root.left, result, currentString);
        boolean noRightSubTree = smallestFromLeaf(root.right, result, currentString);
        if ( noLeftSubTree && noRightSubTree) {
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
            result = Math.max(result, width[1]-width[0]+1);
        }
        return result;
    }

    // 662 Max Width of binary tree, helper
    private void widthOfBinaryTree(TreeNode root, int height , int index, HashMap<Integer, int[]> widthMap) {
        if (root == null) {
            return;
        }

        int[] width = widthMap.getOrDefault(height, new int[]{index, index});

        width[0] = Math.min(width[0], index);
        width[1] = Math.max(width[1], index);

        widthMap.put(index, width);

        widthOfBinaryTree(root.left, height+1, index*2+1, widthMap);
        widthOfBinaryTree(root.right, height+1, index*2+2, widthMap);
    }

    // 160
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        HashSet<ListNode> set = new HashSet<>();

        while (headB != null) {
            set.add(headB);
            headB = headB.next;
        }

        while (headA != null) {
            if (set.contains(headA))  {
                break;
            }
            headA = headA.next;
        }

        return headA;
    }

    // 890 Helper
    public boolean checkPattern(char[] word, char[] pattern){
        if (word.length!=pattern.length) {
            return false;
        }
        int[] map = new int[27];
        int[] mappedAlphabets = new int[27];
        for (int index = 0; index<pattern.length; index++) {
            int key = pattern[index]-96;
            if (map[key] == 0 && mappedAlphabets[word[index]-96] == 0) {
                map[key] = word[index]-96;
                mappedAlphabets[word[index]-96] = 1;
            }
            else if (map[key] == 0 && mappedAlphabets[word[index]-96] != 0){
                return false;
            }
            else if (map[key] != (word[index]-96)) {
                return false;
            }
        }

        return true;
    }

    // 890
    public List<String> findAndReplacePattern(String[] words, String pattern) {
        List<String> result = new ArrayList<>();
        char[] patternChar = pattern.toCharArray();
        for (String word: words) {
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
        for (String word: words) {
            resultBuilder
                    .append(word)
                    .append(" ");
        }
        resultBuilder.setCharAt(0, (char) (resultBuilder.charAt(0)-32));
        resultBuilder.deleteCharAt(resultBuilder.length()-1);
        return resultBuilder.toString();
    }

    // 274 helper
    public boolean countCitations(int[] citations, int hIndex) {
        int count = 0;
        for (int citation: citations) {
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
        for (hIndex=citations.length; hIndex>-1; hIndex--) {
            if (countCitations(citations, hIndex)) {
                break;
            }
        }
        return hIndex;
    }

    // 150 helper
    public int evalRPN(Stack<Integer> operands, String operator){
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
        for (String token: tokens) {
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
            }
            else {
                count.put(groupSizes[index], values);
            }
        }
        return result;
    }

    // 1344. Angle Between Hands of a Clock
    public double angleClock(int hour, int minutes) {
        double result = Math.abs(minutes*6 - (hour*30 + 0.5*minutes));
        return (result > 180)? (360 - result): result;
    }


    // 49. Group Anagrams
    public List<List<String>> groupAnagrams(String[] strs) {
        HashMap<String, List<String>> map = new HashMap<>();

        for (String str: strs){
            String key = groupAnagrams(str);
            List<String> val = map.getOrDefault(key, new ArrayList<>());
            val.add(str);
            map.put(key, val);
        }

        return new ArrayList<>(map.values());
    }

    public String groupAnagrams(String str){
        StringBuilder builder = new StringBuilder();
        builder.append("00000000000000000000000000");

        for(char ch: str.toCharArray()){
            int index = ch-'a';
            builder.setCharAt(index, (char)(builder.charAt(index)+1));
        }

        return builder.toString();
    }

    // 187. Repeated DNA Sequences
    public List<String> findRepeatedDnaSequences(String s) {
        List<String> result = new ArrayList<>();
        if (s.length()<10) {
            return result;
        }
        HashMap<String, Integer> map = new HashMap<>();
        for (int index = 0; index < s.length()-10; index++){
            String key = s.substring(index, index+10);
            map.put(key, map.getOrDefault(key, 0)+1);
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

    public int sumNumbers(TreeNode root, int curNum){
        if (root == null) {
            return 0;
        }
        curNum = curNum*10+root.val;
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
        if (left == null || right == null){
            return;
        }
        left.right = right;
        connect(left.left, left.right);
        connect(right.left, right.right);
        connect(left.right, right.left);
    }


    // 120. Triangle
    public int minimumTotal(List<List<Integer>> triangle) {
        return minimumTotal(triangle, 1, triangle.get(0), triangle.get(0).get(0));
    }

    // 120. Triangle
    // This function gets the input triangle and computes the sum of the current row and returns the minimum of the current row
    public int minimumTotal(List<List<Integer>> triangle, int currentRowIndex, List<Integer> sumOfValues, int minimum) {
        if (currentRowIndex == triangle.size()){
            return minimum;
        }
        List<Integer> nextSumOfValues = new ArrayList<>();
        nextSumOfValues.add(triangle.get(currentRowIndex).get(0)+sumOfValues.get(0));
        int minimumForCurrentRow = nextSumOfValues.get(0);
        for(int index = 1; index < currentRowIndex; index++){
            int curSum = Math.min(
                            triangle.get(currentRowIndex).get(index)+sumOfValues.get(index-1),
                            triangle.get(currentRowIndex).get(index)+sumOfValues.get(index));
            minimumForCurrentRow = Math.min(curSum, minimumForCurrentRow);
            nextSumOfValues.add(curSum);
        }
        if (currentRowIndex!=0){
            nextSumOfValues.add(triangle.get(currentRowIndex).get(currentRowIndex)+sumOfValues.get(currentRowIndex-1));
            minimumForCurrentRow = Math.min(nextSumOfValues.get(nextSumOfValues.size()-1), minimumForCurrentRow);
        }
        return minimumTotal(triangle, currentRowIndex+1, nextSumOfValues, minimumForCurrentRow);
    }

    // 343. Integer Break, function returns the product when the number n is split k times with each split of size splitSize
    double getProduct(int n, int splitSize, int k){
        return Math.pow(splitSize, k-1)*(n-(splitSize*(k-1)));
    }

    // 343. Integer Break
    // Split the number n in k parts(Each part should have max value i,e split equally when possible):
    //      - In k-1 parts, with each of size floor(n/k) or ceil(n/k)
    //      - The remaining 1 part will be n-floor(n/k) or n-ceil(n/k)
    // The final product will be (product of k-1 parts)*(product of 1 part)
    public int integerBreak(int n) {
        double result = 0;
        for(int k=2; k<=n/2+1; k++){
            double product = Math.max(
                    getProduct(n, (int)Math.ceil((double) n /k), k),
                    getProduct(n, (int)Math.floor((double) n /k), k));
            if (product>result){
                result = product;
            }
        }
        return (int) result;
    }

    // 1743. Restore the Array From Adjacent Pairs
    public int[] restoreArray(int[][] adjacentPairs) {
        int[] result = new int[adjacentPairs.length+1];

        // Construct adjacency list as a Map
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        for(int[] pair: adjacentPairs){
            Helper.restoreArrayHelper(pair[0], pair[1], map);
            Helper.restoreArrayHelper(pair[1], pair[0], map);
        }

        // Get corner element i.e. element with one neighbour
        for(int key: map.keySet()){
            if (map.get(key).size() == 1){
                result[0] = key;
                break;
            }
        }

        // Construct original array
        for (int index = 1; index < result.length; index++){
            int prev = result[index-1];
            List<Integer> neighbours = map.remove(prev);
            if (neighbours.size() == 1){
                result[index] = neighbours.get(0);
                continue;
            }
            for (int neighbour: neighbours) {
                if (map.containsKey(neighbour)){
                    result[index] = neighbour;
                }
            }
        }

        return result;
    }

    // 2191. Sort the Jumbled Numbers
    public int[] sortJumbled(int[] mapping, int[] nums) {
        int[][] arr = new int[nums.length][2];
        for(int index=0;index<nums.length;index++){
            arr[index][0] = nums[index];
            arr[index][1] = Helper.getVal(mapping, nums[index]);
        }
        Arrays.sort(arr, Comparator.comparingInt(o -> o[1]));
        for(int index=0;index<nums.length;index++){
            nums[index] = arr[index][0];
        }
        return nums;    
    }


    public void addNode(TreeNode root, TreeNode node){
        if(root.val<node.val){
            if(root.right==null){
                root.right = node;
            }
            addNode(root.right, node);
        }
        else{
            if(root.left==null){
                root.left = node;
            }
            addNode(root.left, node);
        }
    }

    // 108. Convert Sorted Array to Binary Search Tree
    public void addNode(TreeNode root, int beg, int end, int[] nums){
        if(beg==end){
            addNode(root, new TreeNode(nums[beg]));
            return;
        }
        TreeNode mid = new TreeNode(nums[(beg+end)/2]);
        addNode(root, mid);
        addNode(mid, beg, ((beg+end)/2) -1, nums);
        addNode(root, ((beg+end)/2) +1, end, nums);
    }

    // 108. Convert Sorted Array to Binary Search Tree
    public TreeNode sortedArrayToBST(int[] nums) {
        TreeNode root = new TreeNode(nums[(nums.length)/2]);
        addNode(root, 0, ((nums.length)/2) -1, nums);
        addNode(root, ((nums.length)/2) -1, nums.length-1, nums);
        return root;
    }

    // 738. Monotone Increasing Digits
    public int monotoneIncreasingDigits(int n, int cur) {
        if(cur>n) return -1; 
        int res = -1;
        for(int val = 9;val>=cur%10;val--){
            res = monotoneIncreasingDigits(n, cur*10 + val);
            if(res!=-1) break;
        }
        return res;
    }

    // 738. Monotone Increasing Digits
    public int monotoneIncreasingDigits(int n) {
        if(n<10) return n;
        return monotoneIncreasingDigits(n, 1);
    }

    // 565. Array Nesting
    public int getNodes(int[] nums, int[] visited, int[] dist, int cur, int step){
        if(visited[cur]==1) return step;
        if(dist[cur]!=0) return dist[cur];
        visited[cur] = 1;
        int temp = getNodes(nums,visited,dist,nums[cur], step+1);
        if(temp>dist[cur]) dist[cur] = temp;
        return dist[cur];
    }

    // 565. Array Nesting
    public int arrayNesting(int[] nums) {
        int res = 0;
        int[] dist = new int[nums.length];
        for(int num:nums){
            int temp = getNodes(nums, new int[nums.length], dist, num, 0);
            if(temp>res) res = temp;
        }
        return res;
    }

    // 1702. Maximum Binary String After Change
    public String maximumBinaryString(String binary) {
        int index = 0;
        char[] binaryArr = binary.toCharArray();
        while(index<binaryArr.length) {
            if(binaryArr[index]=='0') break;
            index++;
        }
        int count0 = 0;
        for(int curInd = index; curInd<binaryArr.length;curInd++){
            if(binaryArr[curInd]=='0') count0++;
            binaryArr[curInd] = '1';
        }
        if(index<binaryArr.length) binaryArr[index+count0-1] = '0';
        return String.valueOf(binaryArr);
    }

    // 787. Cheapest Flights Within K Stops
    int finalFlightPrice = -1;
    public void findCheapestPrice(HashMap<Integer, PriorityQueue<int[]>> adjList, int[] visited, int src, int dst, int k, int curPrice){
        if(src==dst){
            if(finalFlightPrice == -1 || finalFlightPrice>curPrice) finalFlightPrice = curPrice;
            return;
        }
        if(curPrice>finalFlightPrice && finalFlightPrice!=-1) return;
        if(k==0) return;
        for(int[] flight: adjList.get(src)){
            if(visited[flight[0]]==1) continue;
            visited[flight[0]] = 1;
            findCheapestPrice(adjList, visited, flight[0], dst, k-1, curPrice+flight[1]);
            visited[flight[0]] = 0;
        }
    }
    
    // 787. Cheapest Flights Within K Stops
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        HashMap<Integer, PriorityQueue<int[]>> adjList = new HashMap<>();

        for(int index = 0;index<n;index++){
            adjList.put(index, new PriorityQueue<>(Comparator.comparingInt(o -> o[1])));
        }
        
        for(int[] flight: flights) adjList.get(flight[0]).add(new int[]{flight[1], flight[2]});
        int[] visited = new int[n];
        visited[src] = 1;
        findCheapestPrice(adjList, visited, src, dst, k+1, 0);
        return finalFlightPrice;
    }

    // 787. Cheapest Flights Within K Stops - sol1 Time Limit
    public void findCheapestPrice1(int[][] adjMat, int src, int dst, int k, int curPrice){
        if(src==dst){
            if(finalFlightPrice == -1 || finalFlightPrice>curPrice) finalFlightPrice = curPrice;
            return;
        }
        if(curPrice>finalFlightPrice && finalFlightPrice!=-1) return;
        if(k==0) return;
        for(int index=0;index<adjMat.length;index++){
            if(adjMat[src][index]==0) continue;
            int temp = adjMat[src][index];
            adjMat[src][index] = 0;
            findCheapestPrice1(adjMat, index, dst, k-1, curPrice+temp);
            adjMat[src][index] = temp;
        }
    }
    
    // 787. Cheapest Flights Within K Stops - sol1 Time Limit
    public int findCheapestPrice1(int n, int[][] flights, int src, int dst, int k) {
        int[][] adjMat = new int[n][n];
        for(int[] flight: flights) adjMat[flight[0]][flight[1]] = flight[2];
        findCheapestPrice1(adjMat, src, dst, k+1, 0);
        return finalFlightPrice;
    }

    // 693. Binary Number with Alternating Bits
    public boolean hasAlternatingBits(int n) {
        int power = 1, flag=n&1, val = 2;
        while(val<=n){
            int temp = val&n;
            if(temp>0) temp = 1;
            if(temp==flag) return false;
            flag = temp;
            power++;
            val = (int)Math.pow(2, power);
        }
        return true;
    }

    // 1462. Course Schedule I sol12
    public boolean checkPath2(HashMap<Integer, HashSet<Integer>> adjList, int index,int target){
        List<Integer> q = new ArrayList<>();
        q.add(index);
        while(q.size()!=0){
            int cur = q.remove(0);
            if(cur == target) return true;
            q.addAll(adjList.get(cur));
        }
        return false;
    }

    // 1462. Course Schedule I sol2
    public List<Boolean> checkIfPrerequisite2(int numCourses, int[][] prerequisites, int[][] queries) {
        HashMap<Integer, HashSet<Integer>> adjList = new HashMap<>();
        for(int index = 0; index<numCourses; index++) adjList.put(index, new HashSet<>());
        List<Boolean> res = new ArrayList<>();
        for(int[] prereq: prerequisites){
            adjList.get(prereq[1]).add(prereq[0]);
        }
        for(int[] query: queries){
            res.add(checkPath2(adjList, query[1], query[0]));
        }
        return res;
    }

    // 1462. Course Schedule I sol1 
    public boolean checkPath(GraphNode[] nodes, int index, int target){
        if(index==target) return true;
        if(nodes[index]==null) return false;
        for(int id:nodes[index].child){
            if(checkPath(nodes, id, target)) return true;
        }
        return false;
    }

    // 1462. Course Schedule I sol1
    public List<Boolean> checkIfPrerequisite(int numCourses, int[][] prerequisites, int[][] queries) {
        GraphNode[] nodes = new GraphNode[numCourses];
        List<Boolean> res = new ArrayList<>();
        for(int[] prereq: prerequisites){
            if(nodes[prereq[1]]==null){
                nodes[prereq[1]] = new GraphNode(prereq[0]);
            }
            nodes[prereq[1]].child.add(prereq[0]);
        }
        for(int[] query: queries){
            res.add(checkPath(nodes, query[1], query[0]));
        }
        return res;
    }

    // 2280. Minimum Lines to Represent a Line Chart
    public int minimumLines(int[][] arr) {
        if(arr.length == 1) return 0;
        Arrays.sort(arr, Comparator.comparingInt(o -> o[0]));
        float slope = ((float)arr[0][1]-arr[1][1])/((float)arr[0][0]-arr[1][0]);
        float cur = slope;
        System.out.println(cur);
        int lines = 1;
        for(int index = 1;index<arr.length-1;index++){
            cur = ((float)arr[index][1]-arr[index+1][1])/((float)arr[index][0]-arr[1+index][0]);
            System.out.println(cur);
            if(cur!=slope){
                lines++;
                slope = cur;
            }
        }
        if(cur!=slope) lines++;
        return lines;
    }

    // 322. Coin Change
    public int coinChange(int[] coins, int amount) {
        Arrays.sort(coins);
        int beg = -1, end = coins.length;
        while(++beg<--end){
            int temp = coins[beg];
            coins[beg] = coins[end];
            coins[end] = temp;
        }
        return coinChage(coins, amount, 0, 0);
    }
    
    public int coinChage(int[] coins, int amt, int index, int res){
        if(index==coins.length && amt!=0) return -1;
        if(amt==0) return res;
        int curres = -1;
        for(int cur = index; cur<coins.length; cur++){
            int rem = amt/coins[index];
            while(rem>0 && curres==-1){
                curres = coinChage(coins, amt - rem*coins[index], index + 1, rem+res);
                rem--;
            }
            curres = coinChage(coins, amt, index + 1, res);
            if(curres!=-1) break;
        }
        return curres;
    }

    // 2090. K Radius Subarray Averages
    public int[] getRes(int len){
        int[] res = new int[len];
        Arrays.fill(res, -1);
        return res;
    }
    public int[] getAverages(int[] nums, int k) {
        int[] res = getRes(nums.length);
        if(k*2>=nums.length) return res;
        int temp = 2*k+1, ind, size = nums.length-1;
        double sum = 0;
        while(temp-->0) sum+=nums[temp];
        size -= k;
        for(ind = k; ind<size ;ind++){
            res[ind] = (int)(sum/(2*k+1));
            sum += nums[ind+k+1] - nums[ind-k];
        }
        res[ind] = (int)(sum/(2*k+1));
        return res;
    }

    // 1162. As Far from Land as Possible
    public int maxDistance(int[][] grid) {
        List<int[]> land = new ArrayList<>(), water = new ArrayList<>();
        for(int row=0;row<grid.length;row++){
            for(int col=0;col<grid[0].length;col++){
                if(grid[row][col]==0) water.add(new int[]{row, col});
                else water.add(new int[]{row, col});
            }
        }
        int res = grid[water.get(0)[0]][water.get(0)[1]];
        for(int[] l:land){
            for(int[] w:water){
                int dist = new Helper().manhattanDist(l, w);
                if(grid[w[0]][w[1]]<dist) grid[w[0]][w[1]] = dist;
            }
        }
        for(int[] w:water){
            if(grid[w[0]][w[1]]>res) res = grid[w[0]][w[1]];
        }
        return res;
    }

    // 480. Sliding Window Median
    public double[] medianSlidingWindow(int[] nums, int k) {
        double[] res = new double[nums.length+1-k];
        int resInd = 0, beg = 0, end = k;
        ArrayList<Integer> window = new ArrayList<>();
        for(int index = 0;index<k;index++) window.add(nums[index]);
        window.sort((o1, o2) -> (int)((double)o1-o2));
        while(end<nums.length){
            System.out.println(window);
            if(k%2==0) res[resInd++] = window.get(k/2)/2.0+window.get(k/2-1)/2.0;
            else res[resInd++] = window.get(k/2);
            new Helper();
            window.remove(Helper.binarySearch(window, nums[beg]));
            int ind=0;
            while(ind<window.size()) {
                if(window.get(ind)>nums[end]) break;
                ind++;
            }
            window.add(ind, nums[end]);
            beg++;
            end++;
        }
        if(k%2==0) res[resInd++] = window.get(k/2)/2.0+window.get(k/2-1)/2.0;
        else res[resInd++] = window.get(k/2);
        return res;
    }

    //576. Out of Boundary Paths
    public int findPaths(int m, int n, int maxMove, int startRow, int startColumn) {
        int res = 0;
        return new Helper().findPaths(startRow, startColumn, m, n, maxMove, res);
    }

    //2155. All Divisions With the Highest Score of a Binary Array
    public List<Integer> maxScoreIndices(int[] nums) {
        int curScore = 0, size = nums.length+1;
        List<Integer> res = new ArrayList<>();
        for(int num:nums){
            if(num==1) curScore+=1;
        }
        int max = curScore;
        for(int index = 1;index<size;index++){
            if(nums[index-1] == 1) curScore--;
            if(nums[index-1] == 0) curScore++;
            if(max<curScore){
                max = curScore;
                res.clear();
            }
            if(max == curScore) res.add(index);
        }
        return res;
    }

    //222. Count Complete Tree Nodes
    public int countNodes(TreeNode root) {
        /* Totlal number of nodes in full binary tree is 2^(n)-1 
         * where n is the depth of the tree. recursivley check depth of left and
         * right subtree. If the depth of right tree is less than left tree, 
         * then left subtree is a full binary tree, next time check using right subtree. 
         * if both the depths are equal then, right subtree is a full binary tree, 
         * next iteration check with the left subtree.  
        */
        if(root==null) return 0;
        int d = new Helper().depth(root);
        return new Helper().countNodes(root.left, root.right, 1, d-1);
    }
    
    // 74. Search a 2D Matrix
    public boolean searchMatrix(int[][] matrix, int target) {
        int index = Helper.modSearch(matrix, target);
        if(matrix[index][0]>target && index!=0) index=index-1;
        return Helper.binarySearch(matrix[index], target);
    }

    //566. Reshape the Matrix
    public int[][] matrixReshape(int[][] mat, int r, int c) {
        if(r == mat.length && c==mat[0].length) return mat;
        if(r*c > mat.length*mat[0].length) return mat;
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

    //53. Maximum Subarray
    public int maxSubArray(int[] nums) {
        int res = Integer.MIN_VALUE;
        int[] sum = new int[nums.length+1];
        for(int index = 1; index<sum.length; index++){
            sum[index] = nums[index-1] + sum[index-1];
            res = Math.max(res, nums[index-1]);
        }

        int maxSum = Integer.MIN_VALUE;
        int minSum = Integer.MAX_VALUE;
        for (int index = 0; index<sum.length; index++) {
            maxSum = Math.max(maxSum, sum[index]);
            minSum = Math.min(minSum, sum[index]);
        }
        return Math.max(res, maxSum-minSum);
    }

    //1. Two Sum
    public int[] twoSumOne(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for(int ind=0;ind<nums.length;ind++){
            if(map.containsKey(nums[ind])){
                return new int[]{map.get(nums[ind]),ind};
            }
            map.put(target-nums[ind], ind);
        }
        return new int[2];
    }

    //88. Merge Sorted Array
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int resind = m+n-1, ind1 = m-1, ind2=n-1;
        while(ind1>-1&&ind2>-1){
            if(nums1[ind1]>nums2[ind2]){
                nums1[resind] = nums1[ind1];
                ind1--;
            }else{
                nums1[resind] = nums2[ind2];
                ind2--;
            }
            resind--;
        }
        while(ind1>-1){
            nums1[resind]=nums1[ind1];
            ind1=ind1-1;
            resind=resind-1;
        }
        while(ind2>-1){
            nums1[resind]=nums2[ind2];
            resind=resind-1;
            ind2 = ind2-1;
        }
    }

    //1324. Print Words Vertically
    public List<String> printVertically(String s) {
        char[] arr = s.toCharArray();
        List<Integer> indices = new Helper().initIndex(arr);
        IntStream.range(0, indices.size()).parallel().forEach(index->{
            int val = indices.get(index);
            if(arr[val]!=' ') indices.set(index, val+1);
        });
        System.out.println(indices);
        return new ArrayList<>();
    }
    
    //331. Verify Preorder Serialization of a Binary Tree
    boolean isValidSerialization(String preorder) {
        String[] arr = preorder.split(",");
        if(arr.length==1){
            return arr[0].equals("#");
        }
        if(!arr[arr.length-1].equals("#") || !arr[arr.length-2].equals("#")) return false;
        Stack<String> stack = new Stack<>();
        int index = new Helper().verifyPreorder(0, arr, stack);
        if(index<arr.length) return false;
        return stack.isEmpty();
    }

    //1840. Maximum Building Height
    public int maxBuilding(int n, int[][] restrictions) {
        if(restrictions.length==0) return n;
        int[] heights = new int[n];
        int result = 0, index;
        for(index=0;index<restrictions.length;index++){
            if(restrictions[index][0]==2) result = index; 
            heights[restrictions[index][0]] = restrictions[index][1];
        }
        if(restrictions[result][1] == 0) heights[1] = 0;
        else heights[1] = 1;
        for(index=2;index<n-1;index++){
            if(heights[index]==0){
                heights[index] = heights[index-1]+1;
            }

        }
        for(index=0;index<n;index++){
            if(result<heights[index])
                result = heights[index];
        }
        return result;
    }

    //938. Range Sum of BST
    public int rangeSumBST(TreeNode root, int low, int high){
        return new Helper().findSum(root, low, high, 0);
    }
    
    //1437. Check If All 1's Are at Least Length K Places Away
    public boolean kLengthApart(int[] nums, int k) {
        int cur=-1, prev=-1, i;
        k++;
        for(i=0;i<nums.length;i++){
            if(prev==-1&&nums[i]==1){
                prev = i;
                continue;
            }if(nums[i]==1){
                cur = i;
                break;
            }
        }
        ++i;
        for(;i<nums.length;i++){
            if(cur-prev>=k) return false;
            if(nums[i]==1){
                prev = cur;
                cur = i;
            }
        }
        return !(cur-prev>=k);
    }

    // 350. Intersection of Two Arrays II
    //sol 1
    public int[] intersect(int[] nums1, int[] nums2) {
        HashMap<Integer,Integer> map = new HashMap<>();
        List<Integer> result  = new ArrayList<>();
        for(int num:nums1){
            map.put(num,map.getOrDefault(num, 0)+1);
        }
        for(int num:nums2){
            if(map.containsKey(num)){
                result.add(num);
                if(map.get(num)==1) map.remove(num);
                else map.put(num,map.get(num)-1);
            }
        }
        return result.stream().mapToInt(num -> num).toArray();
    }

    // 350. Intersection of Two Arrays II
    //sol 2
    public int[] intersect1(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        int ind1=0, ind2=0;
        List<Integer> result  = new ArrayList<>();
        while(ind1<nums1.length&&ind2<nums2.length){
            if(nums1[ind1]>nums2[ind2]) ind2++;
            else if(nums1[ind1]<nums2[ind2]) ind1++;
            else{
                result.add(nums1[ind1]);
                ind1++;
                ind2++;
            }
        }
        return result.stream().mapToInt(num -> num).toArray();
    }

    //1268. Search Suggestions System
    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        List<List<String>> results = new ArrayList<>();
        char[] flags = new char[products.length];
        for(int ind=0;ind<searchWord.length();ind++){
            List<String> result = new ArrayList<>();
            for(int productInd = 0;productInd<products.length;productInd++){
                if(flags[productInd]=='\0') continue;
                if(products[productInd].charAt(ind)==searchWord.charAt(ind)){
                    flags[ind] = '1';
                    result = Helper.insertInList(result, products[productInd]);
                }
            }
            results.add(result);
        }
        return results;
    }


    //2011. Final Value of Variable After Performing Operations
    public int finalValueAfterOperations(String[] operations) {
        int x = 0;
        for(String operation:operations){
            char s = operation.charAt(0),e = operation.charAt(operation.length()-1);
            if(s=='+') ++x;
            else if(s == '-') --x;
            else if(e == '+') x++;
            else x--;
        }
        return x;
    }
    
    //1769. Minimum Number of Operations to Move All Balls to Each Box
    public int[] minOperations(String boxes) {
        char[] array = boxes.toCharArray();
        int l=0, r=0, size = array.length;
        int[] ans = new int[size];
        for(int ind=0;ind<size;ind++){
            if(array[ind]=='1'){
                ans[0] = ans[0]+ind;
                r++;
            }
        }

        for(int ind=1;ind<size;ind++){
            if(array[ind-1]=='1'){
                r--;
                l++;
            }
            ans[ind] = ans[ind-1]-r+l;
        }
        return ans;
    }

    public int guess(int n){
        int num = 3;
        if(n>num){
            return -1;
        }else if(n<num){
            return 1;
        }
        return 0;
    }
    
    public int guessNumber(int n){
        if(n==1) return 1;
        int beg=1,end=n,mid,guess;
        while(beg<=end){
            mid=beg/2;
            mid+=end/2;
            guess = guess(mid);
            if(guess==-1){
                end = mid-1;
            }else if(guess==1){
                beg = mid+1;
            }else{
                return mid;
            }
        }
        return -1;
    }

    //167. Two Sum II - Input Array Is Sorted
    public int[] twoSum(int[] numbers, int target) {
        int ind1 = numbers.length-1, ind2 = 0, sum = numbers[numbers.length-1]+numbers[0];
        while(sum!=target){
            if(sum>target){
                ind1--;
            }else {
                ind2++;
            }
            sum = numbers[ind1]+numbers[ind2];
        }
        return new int[]{ind2+1,ind1+1};
    }
    
    public double average(int[] salary) {
        int min=salary[0],max=salary[0],size = salary.length,cur;
        double sum =0;
        for (int i : salary) {
            cur = i;
            sum += cur;
            if (cur > max) {
                max = cur;
            } else if (cur < min) {
                min = cur;
            }
        }
        return (sum-max-min)/(size-2);
    }
    
    public int trailingZeroes(int n) {
        int count5s=0,cur;
        for(int num=1;num<=n;num++){
            cur = num;
            while(cur%5==0){
                cur=cur/5;
                count5s++;
            }
        }
        return count5s;
    }
    
    
    public int numSplits(String s){
        if(s.length()==0) return 0;
        int[] map1=new int[26], map2=new int[26];
        int count1=1,count2=0,res=0,size=s.length(),ch;
        map1[s.charAt(0)-'a']=1;
        for(int ind=1;ind<size;ind++){
            ch = s.charAt(ind)-'a';
            if(map2[ch]==0) count2++;
            map2[ch]++;
        }
        if(count1==count2) res++;
        for(int ind=1;ind<size-1;ind++){
            ch = s.charAt(ind)-'a';
            if(map1[ch]==0){
                map1[ch]++;
                count1++;
            }
            if(--map2[ch]==0){
                count2--;
            }
            if(count1==count2) res++;
        }
        return res;
    }
    
    public int numOfSubarrays(int[] arr) {
        int[] sum = new int[arr.length];
        int res=0;
        sum[0] = arr[0];
        if(arr[0]%2!=0) res++;
        for(int i=1;i<arr.length;i++){
            if(arr[i]%2!=0) res++;
            sum[i]=arr[i]+sum[i-1];
        }
        if(sum[sum.length-1]%2!=0) res++;
        for(int len=2;len<arr.length-1;len++){
            if(sum[len-1]%2!=0) res++;
            for(int beg=len+1;beg<arr.length;beg++){
                if(sum[beg]-sum[beg-len]%2!=0)
                   res++;
            }
        }
        return res;
    }    
}

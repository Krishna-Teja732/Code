package Leet.utils;

import Leet.DataStructures.TreeNode;

import java.util.*;

public class Helper {
    // 1743. Restore the Array From Adjacent Pairs. Add entry adjacency list(in this case it is a HashMap)
    public static void restoreArrayHelper(int key, int value, HashMap<Integer, List<Integer>> map){
        List<Integer> values = map.getOrDefault(key, new ArrayList<>());
        values.add(value);
        map.put(key, values);
    }

    // 2191. Sort the Jumbled Numbers
    public static int getVal(int[] mappings, int val){
        int res = 0, power = 0;
        do{
            res = res+(int)Math.pow(10, power)*mappings[val%10];
            power++;
            val = val/10;
        }while(val>0);
        return res;
    }
    

    //1268. Algorithms.Search Suggestions System
    //insert in lexicographic order and list max size is 3
    public static List<String> insertInList(List<String> result, String val){
        int ind;
        if(result.size()<3){
            for(ind=0;ind<result.size();ind++){
                if(result.get(ind).compareTo(val)>0){
                    break;
                }
            }
            result.add(ind, val);
        }else{
            for(ind=0;ind<3;ind++){
                if(result.get(ind).compareTo(val)>0){
                    result.set(ind, val);
                    break;
                }
            }
        }
        return result;
    }

    //938. Range Sum of BST
    public int findSum(TreeNode root, int low, int high, int result){
        if(root==null) return 0;
        if(root.val<low) result+=findSum(root.right, low, high, result);
        else if(root.val>high) result+=findSum(root.left, low, high, result);
        else result+=findSum(root.left, low, high, result)+findSum(root.right, low, high, result);
        if(root.val>=low && root.val<=high) result=result+root.val;
        return result;
    }

    //331. Verify Preorder Serialization of a Binary Tree
    public int verifyPreorder(int index, String[] preorder, Stack<String> stack){
        if(index==-1) return -1;
        if(index>=preorder.length) return index;
        if(preorder[index].equals("#")) return index+1;
        if(index+2>preorder.length) return -1;
        int curIndex = index;
        stack.push(preorder[index]);
        index = verifyPreorder(verifyPreorder(index+1, preorder, stack), preorder, stack);
        if(stack.peek().equals(preorder[curIndex])) stack.pop();
        else return preorder.length;
        return index;
    }

    public List<Integer> initIndex(char[] arr){
        List<Integer> res = new ArrayList<>();
        res.add(0);
        for(int index=1; index<arr.length;index++){
            if(arr[index-1]==' '){
                res.add(index);
            }
        }
        return res;
    }

    public static boolean binarySearch(int[] arr, int target){
        int mid = (arr.length-1)/2, beg = 0, end = arr.length-1;
        while(beg<end){
            if(arr[mid] == target) return true;
            if(arr[mid]>target) end = mid-1;
            else beg = mid+1;
            mid = (beg+end)/2;
        }
        return arr[mid] == target;
    }

    public static int binarySearch(ArrayList<Integer> arr, int target){
        int mid = (arr.size()-1)/2, beg = 0, end = arr.size()-1;
        while(beg<end){
            if(arr.get(mid) == target) return mid;
            if(arr.get(mid)>target) end = mid-1;
            else beg = mid+1;
            mid = (beg+end)/2;
        }
        if(arr.get(mid) == target) return mid;
        return mid;
    }

    // 74. Algorithms.Search a 2D Matrix
    public static int modSearch(int[][] arr, int target){
        int mid = (arr.length-1)/2, beg = 0, end = arr.length-1;
        while(beg<end){
            if(arr[mid][0] == target) return mid;
            if(arr[mid][0] >target) end = mid-1;
            else beg = mid+1;
            mid = (beg+end)/2;
        }
        if(arr[mid][0] == target) return mid;
        return mid;
    }

    //222. Count Complete Tree Nodes
    public int depth(TreeNode root){
        int res = 0;
        while(root!=null){
            root=root.left;
            res++;
        }
        return res;
    }

    //222. Count Complete Tree Nodes
    public int countNodes(TreeNode n1, TreeNode n2, int res, int depth){
        if(n1 == null) return res;
        int d1 = depth(n1), d2 = depth(n2), count;
        if(d1 == d2){
            count = countNodes(n2.left, n2.right, res + (int)Math.pow(2, depth), depth-1);
        }else{
            count = countNodes(n1.left, n1.right, res + (int)Math.pow(2, depth-1), depth-1);
        }
        return count; 
    }
    //576. Out of Boundary Paths
    public int findPaths(int x, int y,int m, int n, int maxMove, int res) {
        if(maxMove==0){
            if(x<0 || y<0 || x>=m || y>=n){
                return (++res)%((int)Math.pow(10, 9)+7);
            }
            return res;
        }
        findPaths(x+1, y, m, n, maxMove-1, res);
        findPaths(x-1, y, m, n, maxMove-1, res);
        findPaths(x, y+1, m, n, maxMove-1, res);
        findPaths(x, y-1, m, n, maxMove-1, res);
        return res;
    }

    public int manhattanDist(int[] coord1, int[] coord2){
        int res = 0;
        for(int index=0;index<coord1.length;index++){
            res+= Math.abs(coord1[index]-coord2[index]);
        }
        return res;
    }
    
}

package Algorithms.Search;

public class BinarySearch {
    static boolean search(int[] arr, int target){
        int mid = (arr.length-1)/2, beg = 0, end = arr.length-1;
        while(beg<end){
            if(arr[mid] == target) return true;
            if(arr[mid]>target) end = mid-1;
            else beg = mid+1;
            mid = (beg+end)/2;
        }
        if(arr[mid] == target) return true;
        return false;
    }

    static int searchInd(int[] arr, int target){
        int mid = (arr.length-1)/2, beg = 0, end = arr.length-1;
        while(beg<end){
            if(arr[mid] == target) return mid;
            if(arr[mid]>target) end = mid-1;
            else beg = mid+1;
            mid = (beg+end)/2;
        }
        if(arr[mid] == target) return mid;
        return mid;
    }

    public static void main(String[] args){
        // for(int target=0;target<10;target++){
            System.out.println(searchInd(new int[]{1,2,3,5,6,7}, 4));
        // }
    }
}

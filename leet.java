import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class leet{
    public static void main(String[] args) throws IOException{
        System.out.print(new Solution().suggestedProducts(inputUtils.getStringArrayInput(),inputUtils.getStringInput()));
    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

class inputUtils{
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public static int[] getintArrayInput() throws IOException{
        String input=reader.readLine();
        String[] strNums =input.substring(1,input.length()-1).split(","); 
        int[] nums = new int[strNums.length];
        for(int ind =0;ind<strNums.length;ind++){
            nums[ind] = Integer.parseInt(strNums[ind].strip());
        }
        return nums;
    }

    public static String[] getStringArrayInput() throws IOException{
        String input=reader.readLine();
        return input.substring(1,input.length()-1).split(",");
    }

    public static String getStringInput() throws IOException{
        return reader.readLine();
    }

    public static int getintInput() throws IOException{
        return Integer.parseInt(reader.readLine().strip());
    } 
}

class Helper{
    //1268. Search Suggestions System
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
}


class Solution {
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
            char s = operation.charAt(0),e = operation.charAt(operation.length());
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
        int ans[] = new int[size];
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
            }else if(sum<target){
                ind2++;
            }
            sum = numbers[ind1]+numbers[ind2];
        }
        return new int[]{ind2+1,ind1+1};
    }
    
    public double average(int[] salary) {
        int min=salary[0],max=salary[0],size = salary.length,cur;
        double sum =0;
        for(int ind=0;ind<size;ind++){
            cur = salary[ind];
            sum+=cur;
            if(cur>max){
                max=cur;
            }else if(cur<min){
                min=cur;
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
        int sum[] = new int[arr.length], res=0;
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


//1146. Snapshot Array unsolved
class SnapshotArray {

    private Integer[] array;
    private HashMap<Integer,Integer[]> map;
    private Integer snapid;
    public SnapshotArray(int length) {
        array = new Integer[length];
        map = new HashMap<>();
        snapid = 0; 
    }
    
    public void set(int index, int val) {
        array[index] = val;
    }
    
    public int snap() {
        map.put(snapid, Arrays.copyOf(array,array.length));
        return snapid++;
    }
    
    public int get(int index, int snap_id) {
        return map.get(Integer.valueOf(snap_id))[index];
    }
}
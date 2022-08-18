package Leet;

import java.io.*;

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
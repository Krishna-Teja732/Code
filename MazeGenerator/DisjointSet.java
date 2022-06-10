package MazeGenerator;

import java.util.Arrays;

public class DisjointSet {
    private int[] set;
    private int size;

    DisjointSet(int Total_sets){
        set = new int[Total_sets];
        Arrays.fill(set, -1);
        size = Total_sets;
    }

    int size(){
        return this.size;
    }

    int findSet(int num){
        if(num<0||num>size){
            throw new ArrayIndexOutOfBoundsException("Range of element "+0+"-"+size);
        }

        while(set[num]!=-1){
            num = set[num];
        }
        return num;
    }

    void union(int e1, int e2){
        if((e1<0||e1>size)&&(e2<1||e2>size)){
            throw new ArrayIndexOutOfBoundsException("Range of element "+0+"-"+size);
        }

        int s1 = findSet(e1);
        int s2 = findSet(e2);

        if(s1 != s2){
            set[s2] = s1;
        }
    }

    void print(){
        System.out.println(Arrays.toString(set));
    }  
}

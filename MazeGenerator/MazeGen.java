package MazeGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MazeGen {
    private int gridSize; 
    private char[] grid;
    private DisjointSet set;
    private int start, dest;
    
    MazeGen(int gridSize){
        this.gridSize = gridSize;
        set = new DisjointSet(gridSize*gridSize);
        grid = new char[gridSize*gridSize];
        Arrays.fill(grid, '#');
        start = gridSize;
        dest = (gridSize*gridSize)-1;
        grid[gridSize] = '.';
        grid[dest-1] = '.';
    }

    void printGrid(){
        for(int ind=0;ind<=dest;ind++){
            if(ind%gridSize==0){
                System.out.println();
            }
            System.out.print(grid[ind]+" ");
        }
        System.out.println();
    }
    
    private boolean isWallRemovable(int index1D){
        if(grid[index1D]!='#'){
            return false;
        }
        if(index1D<gridSize||index1D>gridSize*(gridSize-1)){
            return false;
        }
        if(index1D%gridSize==0 || index1D%gridSize==gridSize-1){
            return false;
        }
        return true;
    }

    private ArrayList<Integer> getNeighbours(int index1D){
        ArrayList<Integer> res = new ArrayList<>();
        if(grid[index1D+gridSize]!='#' && index1D+gridSize<=dest) res.add(index1D+gridSize);
        if(grid[index1D+1]!='#' && index1D+1<=dest && (index1D+1)%gridSize!=0) res.add(index1D+1);
        if(grid[index1D-1]!='#' && index1D-1>=0 && (index1D-1)%gridSize!=gridSize-1) res.add(index1D-1);
        if(grid[index1D-gridSize]!='#' && index1D-gridSize>=0) res.add(index1D-gridSize);
        if(index1D==start+1) res.add(start);
        if(index1D==dest-gridSize) res.add(dest); 
        return res;
    }

    private void removeWall(int index1D){
        if(isWallRemovable(index1D)){
            ArrayList<Integer> neighbours = getNeighbours(index1D);
            for(int neighbour:neighbours){
                set.union(index1D, neighbour);
            }
            grid[index1D] = '.';
        }
    }

    void gen(long seed){
        Random random = new Random(seed);
        while((set.findSet(start)!=set.findSet(dest-1))){
            removeWall(random.nextInt(dest+1));
        }
    }

    char[][] getGrid(){
        char[][] grid2D = new char[gridSize][gridSize];
        int row = 0;
        for(int ind=0;ind<=(gridSize*gridSize)-1;ind++){
            if(ind%gridSize==0&&ind!=0){
                row=row+1;
            }
            grid2D[row][ind%gridSize]= grid[ind];
        }
        return grid2D;
    }

    public static void main(String[] args) {
        MazeGen generator = new MazeGen(20);
        generator.gen(System.currentTimeMillis());
        generator.printGrid();
    }
}
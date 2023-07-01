package Search;
/*1263. Minimum Moves to Move a Box to Their Target Location*/
import java.util.*;

public class A_star {
    public static void main(String[] args) {
        char[][] grid = new char[][]{
            {'#','#','#','#','#','#'},
            {'#','T','#','#','#','#'},
            {'#','.','.','B','.','#'},
            {'#','.','#','#','.','#'},
            {'#','.','.','.','S','#'},
            {'#','#','#','#','#','#'}};
        // char[][] grid1 = new char[][]{
        //     {'#','#','#','#','#','#'},
        //     {'#','T','#','#','#','#'},
        //     {'#','.','.','B','.','#'},
        //     {'#','#','#','#','.','#'},
        //     {'#','.','.','.','S','#'},
        //     {'#','#','#','#','#','#'}};
        // char[][] grid2 = new char[][]{
        //     {'#','#','#','#','#','#'},
        //     {'#','T','.','.','#','#'},
        //     {'#','.','#','B','.','#'},
        //     {'#','.','.','.','.','#'},
        //     {'#','.','.','.','S','#'},
        //     {'#','#','#','#','#','#'}};
        System.out.println(minPushBox(grid));
    }
    
    public static int minPushBox(char[][] grid) {
        Search obj = new Search(grid);
        return obj.search(obj.init_x, obj.init_y, 0);
    }
}

class Search{
    private char grid[][];
    private char visited[][];
    private char playerVisited[][];
    private int target_x,target_y;
    public int init_x, init_y;
    public int player_x,player_y;
    
    Search(char[][] grid){
        this.grid = grid;
        visited = new char[grid.length][grid[0].length];
        playerVisited = new char[grid.length][grid[0].length];
        for(int x=0;x<grid.length;x++){
            for(int y=0;y<grid[0].length;y++){
                if(grid[x][y]=='T'){
                    target_x = x;
                    target_y = y;
                }else if(grid[x][y] == 'B'){
                    init_x = x;
                    init_y = y;
                }
            }
        }
    }

    boolean isTarget(int x_coord,int y_coord){
        return x_coord==target_x && y_coord==target_y;
    }

    void resetVisited(){
        visited = new char[grid.length][grid[0].length];
    }

    void resetPlayerVisited(){
        playerVisited = new char[grid.length][grid[0].length];
    }

    void playerVisit(int x,int y){
        playerVisited[x][y] = '#';
    }

    private int manhattanDistance(int x, int y){
        return Math.abs(x-target_x)+Math.abs(y-target_y);
    }

    void visit(int x,int y){
        visited[x][y] = '#';
    }

    void insert(List<int[]> moves, int[] new_val){
        int i=0;
        while(i<moves.size()){ 
            if(moves.get(i)[2]>new_val[2]) break;
            i++;
        }
        moves.add(i, new_val);
    }
    
    List<int[]> possibleMovesForBlock(int x_coord,int y_coord){
        List<int[]> moves = new ArrayList<>();
        if(grid[x_coord][y_coord]=='#') return null;
        if((grid[x_coord+1][y_coord]=='#'&&grid[x_coord][y_coord-1]=='#')||
            (grid[x_coord+1][y_coord]=='#'&&grid[x_coord][y_coord+1]=='#') ||
            (grid[x_coord-1][y_coord]=='#'&&grid[x_coord][y_coord-1]=='#') ||
            (grid[x_coord-1][y_coord]=='#'&&grid[x_coord][y_coord+1]=='#')
        ){
            return null;
        }
        if(grid[x_coord+1][y_coord]!='#' && visited[x_coord+1][y_coord]!='#')
            insert(moves,new int[]{x_coord+1,y_coord,manhattanDistance(x_coord+1, y_coord)});
        if(grid[x_coord-1][y_coord]!='#' && visited[x_coord-1][y_coord]!='#') 
            insert(moves, new int[]{x_coord-1,y_coord,manhattanDistance(x_coord-1, y_coord)});
        if(grid[x_coord][y_coord+1]!='#' && visited[x_coord][y_coord+1]!='#')
            insert(moves, new int[]{x_coord,y_coord+1,manhattanDistance(x_coord, y_coord+1)});
        if(grid[x_coord][y_coord-1]!='#' && visited[x_coord][y_coord-1]!='#')
            insert(moves, new int[]{x_coord,y_coord-1,manhattanDistance(x_coord, y_coord-1)});
        return moves;
    }


    List<int[]> possibleMovesForPlayer(int x_coord,int y_coord){
        List<int[]> moves = new ArrayList<>();
        if(grid[x_coord][y_coord]=='#') return null;
        if((grid[x_coord+1][y_coord]!='#'||grid[x_coord+1][y_coord]!='B') && playerVisited[x_coord+1][y_coord]!='#'){
            if(searchPlayerPath(x_coord, y_coord, 0, x_coord-1,y_coord)!=-1)
                insert(moves,new int[]{x_coord+1,y_coord,manhattanDistance(x_coord+1, y_coord)});
            else return null;            
        }
        if((grid[x_coord-1][y_coord]!='#'||grid[x_coord-1][y_coord]!='B') && playerVisited[x_coord-1][y_coord]!='#'){ 
            if(searchPlayerPath(x_coord, y_coord, 0, x_coord+1,y_coord)!=-1)
                insert(moves, new int[]{x_coord-1,y_coord,manhattanDistance(x_coord-1, y_coord)});
            else return null;
        }
        if((grid[x_coord][y_coord+1]!='#'||grid[x_coord][y_coord+1]!='B') && playerVisited[x_coord][y_coord+1]!='#'){
            if(searchPlayerPath(x_coord, y_coord, 0, x_coord,y_coord-1)!=-1)
                insert(moves, new int[]{x_coord,y_coord+1,manhattanDistance(x_coord, y_coord+1)});
            else return null;
        }
        if((grid[x_coord][y_coord-1]!='#'||grid[x_coord][y_coord-1]!='B') && playerVisited[x_coord][y_coord-1]!='#'){
            if(searchPlayerPath(x_coord, y_coord, 0, x_coord,y_coord+1)!=-1)
                insert(moves, new int[]{x_coord,y_coord-1,manhattanDistance(x_coord, y_coord-1)});
            else return null;
        }
        return moves;
    }

    int search(int x, int y,int cost){
        if(isTarget(x,y)) return cost;
        List<int[]> possibleMovesForBlock = possibleMovesForBlock(x, y);
        if(possibleMovesForBlock==null) return -1;
        for(int[] move: possibleMovesForBlock){
            visit(move[0], move[1]);
            int res = search(move[0],move[1],cost+1);
            if(res!=-1) return res;
            else resetVisited();
        }
        return -1;
    }

    int searchPlayerPath(int x, int y, int cost, int target_x, int target_y){
        if(x==target_x&&y==target_y) return cost;
        List<int[]> possibleMovesForPlayer = possibleMovesForPlayer(x, y);
        if(possibleMovesForPlayer==null) return -1;
        for(int[] move: possibleMovesForPlayer){
            playerVisit(move[0], move[1]);
            int res = searchPlayerPath(move[0],move[1],cost+1,target_x,target_y);
            if(res!=-1) return res;
            else {
                resetPlayerVisited();
                cost = 0;
            }
        }
        return -1;
    }
}
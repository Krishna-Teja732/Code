package Leet.Solutions;

import java.util.ArrayList;
import java.util.List;

// 3459. Length of Longest V-Shaped Diagonal Segment
public class LengthOfVDiagonal {
    private final int[][] directions = new int[][] { { 1, 1 }, { 1, -1 }, { -1, -1 }, { -1, 1 } };
    private final int[] element = new int[] { 2, 0 };

    public int lenOfVDiagonal(int[][] grid) {
        int result = 0;
        for (int xInd = 0; xInd < grid.length; xInd++) {
            for (int yInd = 0; yInd < grid[xInd].length; yInd++) {
                if (grid[xInd][yInd] == 1) {
                    // result = Math.max(result, );
                }
            }
        }
        return result;
    }

}

package Leet;

import Leet.Solutions.Solution;

public class Main {
    public static void main(String[] args) {
        Solution obj = new Solution();
        for (int i = 1; i < 7; i++) {
            System.out.println(obj.findTheWinner(6, i));
        }
    }
}

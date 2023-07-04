package Leet.DataStructures;

import java.util.HashSet;

public class GraphNode {
    public int id;
    public HashSet<Integer> child;

    public GraphNode(int val) {
        id = val;
        child = new HashSet<>();
    }
}

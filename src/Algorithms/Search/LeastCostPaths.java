package Algorithms.Search;

import java.util.*;

import DS.GraphEdge;

/**
 * Use Dijkstra's algorithm to find the shortest path between source and all other nodes in graph
 * **/
public class LeastCostPaths {

    private final PriorityQueue<GraphEdge> edges;

    private final HashSet<Integer> visited;

    private int[] costMatrix;

    private  HashMap<Integer, Integer> leastCostPath;

    LeastCostPaths() {
        edges = new PriorityQueue<>();
        visited = new HashSet<>();
    }

    private void edgesToExplore(int sourceNode, int[][] adjMatrix, int costToNode) {
        for (int neighbour = 0; neighbour< adjMatrix.length; neighbour++) {
            if (adjMatrix[sourceNode][neighbour]==-1 || visited.contains(neighbour)) {
                continue;
            }
            edges.add(new GraphEdge(sourceNode, neighbour, adjMatrix[sourceNode][neighbour]+costToNode));
        }
    }

    public void findLeastCostPaths(int sourceNode, int[][] adjMatrix){
        costMatrix = new int[adjMatrix[sourceNode].length];
        leastCostPath = new HashMap<>();
        for (int index = 0; index < adjMatrix[sourceNode].length; index++) {
            costMatrix[index] = adjMatrix[sourceNode][index];
            if (adjMatrix[sourceNode][index]!=-1) {
                leastCostPath.put(index, sourceNode);
            } else {
                leastCostPath.put(index, -1);
            }
        }

        edgesToExplore(sourceNode, adjMatrix, 0);

        visited.add(sourceNode);

        findLeastCostPaths(adjMatrix);
    }

    private void findLeastCostPaths(int[][] adjMatrix) {
        while (visited.size() != adjMatrix.length && !edges.isEmpty()) {
            GraphEdge edge = edges.poll();
            int source = edge.source();
            int neighbour = edge.destination();
            int cost = edge.cost();
            if (visited.contains(neighbour)) {
                continue;
            }

            if (costMatrix[neighbour] == -1 || costMatrix[neighbour] > cost) {
                costMatrix[neighbour] = cost;
                leastCostPath.put(neighbour, source);
            }

            edgesToExplore(neighbour, adjMatrix, costMatrix[neighbour]);
            visited.add(neighbour);
        }
    }

    public int[] getCostMatrix() {
        return costMatrix;
    }

    public HashMap<Integer, Integer> getLeastCostPath() {
        return leastCostPath;
    }

    public static void main(String[] args) {
        int[][] adjMatrix =
                new int[][] {
                        {-1, 2, 5, 1, -1, -1},
                        {2, -1, 3, 2, -1, -1},
                        {5, 3, -1, 3, 1, 5},
                        {1, 2, 3, -1, 1, -1},
                        {-1, -1, 1, 1, -1, 2},
                        {-1, -1, 5, -1, 2, -1}
                };

        LeastCostPaths obj = new LeastCostPaths();
        obj.findLeastCostPaths(0, adjMatrix);
        System.out.println(Arrays.toString(obj.getCostMatrix()));
        System.out.println(obj.getLeastCostPath());
    }
}
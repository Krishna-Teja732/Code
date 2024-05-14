package Algorithms.Graph;

import java.util.*;

// 3112. Minimum Time to Visit Disappearing Nodes
public class LeastCostPathsWithTime {
    private List<List<int[]>> adjList;

    private int[] leastCostPaths;

    private PriorityQueue<int[]> edgesToExplore;

    private HashSet<Integer> visited;

    private void init(int n, int[][] edges) {
        this.edgesToExplore = new PriorityQueue<>(Comparator.comparingInt(o -> o[1]));

        this.leastCostPaths = new int[n];
        Arrays.fill(this.leastCostPaths, -1);

        this.visited = new HashSet<>(n);

        this.adjList = new ArrayList<>(n);
        while (adjList.size() < n) {
            adjList.add(new ArrayList<>());
        }

        for (int[] edge: edges) {
            if (edge[0] == edge[1]) {
                continue;
            }

            adjList.get(edge[0]).add(new int[] {edge[1], edge[2]});
            adjList.get(edge[1]).add(new int[] {edge[0], edge[2]});
        }
    }


    private void exploreNodes(int currentNode, int currentCost, int[] minTimeToReachNode) {
        if (currentCost >= minTimeToReachNode[currentNode]) {
            return;
        }

        if (leastCostPaths[currentNode] == -1) {
            leastCostPaths[currentNode] = currentCost;
        } else {
            leastCostPaths[currentNode] = Math.min(leastCostPaths[currentNode], currentCost);
        }


        if (visited.contains(currentNode)) {
            return;
        }
        visited.add(currentNode);

        for (int[] edge: adjList.get(currentNode)) {
            edgesToExplore.add(new int[] {edge[0], currentCost + edge[1]});
        }
    }

    public int[] minimumTime(int n, int[][] edges, int[] disappear) {
        init(n, edges);
        exploreNodes(0, 0, disappear);

        while (!edgesToExplore.isEmpty() && visited.size() < n) {
            int[] edge = edgesToExplore.poll();
            int destinationNode = edge[0];

            exploreNodes(destinationNode, edge[1], disappear);
        }

        return leastCostPaths;
    }
}

package DS;

public record GraphEdge (Integer source, Integer destination, Integer cost) implements Comparable<GraphEdge> {
    @Override
    public int compareTo(GraphEdge edge) {
        return this.cost - edge.cost;
    }
}

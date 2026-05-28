package Leet.Solutions;

import java.util.HashMap;
import java.util.Objects;
import java.util.ArrayList;

// 981. Time Based Key-Value Store
public class TimeMap {

    private HashMap<String, ArrayList<Node>> table;

    TimeMap() {
        table = new HashMap<>();
    }

    public void set(String key, String value, int timestamp) {
        ArrayList<Node> list = this.table.getOrDefault(key, new ArrayList<>());
        list.add(new Node(value, timestamp));
        table.put(key, list);
    }

    public String get(String key, int timestamp) {
        if (!this.table.containsKey(key)) {
            return "";
        }
        ArrayList<Node> queue = this.table.get(key);
        return get(timestamp, queue);
    }

    private String get(int timestamp, ArrayList<Node> list) {
        int start = 0, end = list.size() - 1;
        int mid = (start + end) / 2;
        while (start <= end) {
            mid = (start + end) / 2;
            Node node = list.get(mid);
            if (node.timestamp == timestamp) {
                return node.value;
            } else if (node.timestamp > timestamp) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        if (list.get(mid).timestamp > timestamp) {
            mid = mid - 1;
        }
        return mid == -1 ? "" : list.get(mid).value;
    }

    private class Node implements Comparable<Node> {
        public String value;
        public int timestamp;

        public Node(String value, int timestamp) {
            this.value = value;
            this.timestamp = timestamp;
        }

        @Override
        public int compareTo(Node v) {
            return Integer.compare(this.timestamp, v.timestamp);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value, timestamp);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null || !(obj instanceof Node)) {
                return false;
            }
            Node value = (Node) obj;
            return Objects.equals(value.value, this.value)
                    && Objects.equals(value.timestamp, this.timestamp);
        }

        @Override
        public String toString() {
            return timestamp + ":" + value;
        }
    }
}

package Leet.LeetDS;

public class Node {
    public int key;
    public int val;
    public Node prev;
    public Node next;
    public Node child;

    public Node right;

    public Node left;

    public Node(int key, int val) {
        this.key = key;
        this.val = val;
    }

    public Node(int val) {
        this.val = val;
    }
}

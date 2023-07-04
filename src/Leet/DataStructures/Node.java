package Leet.DataStructures;

public class Node {
    public int val;
    public Node prev;
    public Node next;
    public Node child;

    public Node(int val){
        this.val = val;
    }

    public Node(int val, Node next, Node prev, Node child){
        this.val = val;
        this.next = next;
        this.child = child;
    }
}

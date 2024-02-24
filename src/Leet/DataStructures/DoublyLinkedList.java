package Leet.DataStructures;

public class DoublyLinkedList {
    Node head;
    Node tail;

    public DoublyLinkedList() {
        head = null;
        tail = null;
    }

    public void insertToFront(Node node) {
        if (head == null) {
            head = node;
            tail = node;
            return;
        }
        node.next = head;
        head.prev = node;
        head  = node;
    }

    public Node deleteFromLast() {
        Node result = tail;
        delete(tail);
        return result;
    }

    public void delete(Node node) {
        if (node == head) {
            head = node.next;
            if (head == null) {
                tail = null;
                return;
            }
            head.prev = null;
            return;
        }
        if (node == tail) {
            tail = node.prev;
            tail.next = null;
            return;
        }
        node.prev.next = node.next;
        if (node.next != null) {
            node.next.prev = node.prev;
        }
    }
}
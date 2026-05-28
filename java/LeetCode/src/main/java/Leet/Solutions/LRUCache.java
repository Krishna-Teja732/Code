package Leet.Solutions;

import Leet.LeetDS.Node;

import java.util.HashMap;

// 146 LRU Cache
public class LRUCache {
    int size;
    HashMap<Integer, Node> keyMap;
    DoublyLinkedList ll;

    public LRUCache(int capacity) {
        this.size = capacity;
        keyMap = new HashMap<>();
        ll = new DoublyLinkedList();
    }

    private void moveNodeToFront(Node node) {
        ll.delete(node);
        node.prev = null;
        node.next = null;
        ll.insertToFront(node);
    }

    public int get(int key) {
        Node node = keyMap.getOrDefault(key, null);
        if (node == null) {
            return -1;
        }
        moveNodeToFront(node);
        return node.val;
    }

    public void put(int key, int value) {
        Node node = keyMap.getOrDefault(key, null);
        if (node != null) {
            node.val = value;
            moveNodeToFront(node);
            return;
        }

        node = new Node(key, value);
        ll.insertToFront(node);
        keyMap.put(key, node);
        if (keyMap.size() > size) {
            node = ll.deleteFromLast();
            keyMap.remove(node.key);
        }
    }
}

class DoublyLinkedList {
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
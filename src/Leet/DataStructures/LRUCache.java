package Leet.DataStructures;

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

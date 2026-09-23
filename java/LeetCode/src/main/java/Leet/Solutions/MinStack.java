package Leet.Solutions;

import java.util.Stack;

/**
 * 155. Min Stack
 */
public class MinStack {

	private class Node {
		int val;
		int min;
		Node prev;

		public Node() {
		}

		public Node(int val, int min, Node prev) {
			this.val = val;
			this.min = min;
			this.prev = prev;
		}
	}

	private Node top;

	public MinStack() {
	}

	public void push(int value) {
		if (top == null) {
			top = new Node(value, value, null);
			return;
		}

		top = new Node(value, Math.min(top.min, value), top);
	}

	public void pop() {
		top = top.prev;
	}

	public int top() {
		return top.val;
	}

	public int getMin() {
		return top.min;
	}
}

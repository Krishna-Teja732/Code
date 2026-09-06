package Leet.LeetDS;

public class ListNode {
	public int val;

	public ListNode prev;

	public ListNode next;

	public ListNode(int val) {
		this.val = val;
	}

	public ListNode(int val, ListNode next) {
		this.val = val;
		this.next = next;
	}

	public ListNode(int val, ListNode next, ListNode prev) {
		this.val = val;
		this.next = next;
		this.prev = prev;
	}

	public ListNode() {
	}
}

package com.iteye.wwwcomy.leetcode;

/**
 * Add Two Numbers
 * 
 * Definition for singly-linked list. public class ListNode { int val; ListNode
 * next; ListNode(int x) { val = x; } }
 * 
 * @author liuxingn
 *
 */
public class P002 {

	public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		ListNode n = new ListNode(l1.val + l2.val);
		ListNode it1 = l1.next;
		ListNode it2 = l2.next;
		ListNode current = n;
		while (it1 != null || it2 != null) {
			int x = (it1 == null ? 0 : it1.val) + (it2 == null ? 0 : it2.val);
			if (current.val >= 10) {
				current.val = current.val - 10;
				x = x + 1;
			}
			ListNode tmp = new ListNode(x);

			current.next = tmp;
			it1 = (it1 == null ? it1 : it1.next);
			it2 = (it2 == null ? it2 : it2.next);
			current = tmp;
		}
		if (current.val >= 10) {
			current.val = current.val - 10;
			current.next = new ListNode(1);
		}
		return n;
	}
}

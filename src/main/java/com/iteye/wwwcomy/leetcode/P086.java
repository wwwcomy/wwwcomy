package com.iteye.wwwcomy.leetcode;

public class P086 {
	public ListNode partition(ListNode head, int x) {
		if (head == null)
			return null;
		ListNode firstLessOne = null;
		ListNode lastLessOne = null;
		ListNode lastBiggerOne = null;
		ListNode firstBiggerOne = null;
		ListNode tmp = head;
		do {
			if (tmp.val < x) {
				if (firstLessOne == null) {
					firstLessOne = new ListNode(tmp.val);
					lastLessOne = firstLessOne;
				} else {
					lastLessOne.next = new ListNode(tmp.val);
					lastLessOne = lastLessOne.next;
				}
			} else {
				if (firstBiggerOne == null) {
					firstBiggerOne = new ListNode(tmp.val);
					lastBiggerOne = firstBiggerOne;
				} else {
					lastBiggerOne.next = new ListNode(tmp.val);
					lastBiggerOne = lastBiggerOne.next;
				}
			}
		} while ((tmp = tmp.next) != null);

		if (firstLessOne != null && firstBiggerOne != null) {
			lastLessOne.next = firstBiggerOne;
			return firstLessOne;
		} else if (firstLessOne != null && firstBiggerOne == null) {
			return firstLessOne;
		} else if (firstLessOne == null && firstBiggerOne != null) {
			return firstBiggerOne;
		} else if (firstLessOne == null && firstBiggerOne == null) {
			throw new RuntimeException("WTF?");
		} else {
			throw new RuntimeException("WTF?");
		}
	}
}

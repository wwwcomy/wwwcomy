package com.iteye.wwwcomy;

import java.util.Arrays;
import java.util.Random;

/**
 * 找到列表中最大的几个
 * 
 * @author xingnan.liu
 */
public class MostMax {

	public static void main(String[] args) {
		int[] arr = new int[100];
		Random r = new Random();
		for (int i = 0; i < 90; i++) {
			arr[i] = r.nextInt(100);
		}
		for (int i = 90; i < 100; i++) {
			arr[i] = r.nextInt(200);
		}
		System.out.println(Arrays.toString(arr));

		MinHeap heap = new MinHeap(7);
		for (int i = 0; i < 100; i++) {
			heap.compareAndAdd(arr[i]);
		}
		System.out.println(heap);
	}
}

class MinHeap {
	private int[] queue;

	public MinHeap(int size) {
		queue = new int[size];
		for (int i = 0; i < size; i++) {
			queue[i] = 0;
		}
	}

	public int getRoot() {
		return queue[0];
	}

	public boolean compareAndAdd(int val) {
		if (queue[0] > val)
			return false;
		queue[0] = val;
		reheap();
		System.out.println(this.toString());
		return true;
	}

	private void reheap() {
		int i = 0;
		int tmp = 0;
		while (i < queue.length) {
			int left = 2 * i + 1;
			int right = 2 * i + 2;
			if (left > queue.length - 1)
				return;
			if (queue[i] <= queue[left])
				return;
			if (queue[i] > queue[left] || queue[i] > queue[right]) {
				if (queue[left] < queue[right]) {
					tmp = queue[left];
					queue[left] = queue[i];
					queue[i] = tmp;
					i = left;
				} else {
					tmp = queue[right];
					queue[right] = queue[i];
					queue[i] = tmp;
					i = right;
				}
			}
		}
	}

	@Override
	public String toString() {
		return Arrays.toString(queue);
	}
}

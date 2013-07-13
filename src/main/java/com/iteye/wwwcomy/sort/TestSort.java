package com.iteye.wwwcomy.sort;

public class TestSort {

	public static void main(String[] args) {
		int[] testArray = { 4, 7, 2, 13, 100, 1515, 51, 31, 52, 76, 23, 1, 88 };
		quickSort1(testArray, 0, testArray.length);
		System.out.println("------------");
		// for (int a : testArray)
		// System.out.print(a + "  ");
	}

	/**
	 * 冒泡排序，相互交换，内层循环处理未冒泡的部分
	 * 
	 * @param array
	 */
	public static void bubbleSort(int[] array) {
		int length = array.length;
		int tmp;
		for (int i = 0; i < length - 1; i++) {
			for (int j = 0; j < length - i - 1; j++) {
				if (array[j] > array[j + 1]) {
					tmp = array[j];
					array[j] = array[j + 1];
					array[j + 1] = tmp;
				}
			}
		}
		for (int a : array)
			System.out.print(a + "  ");
	}

	/**
	 * 选择排序，每次选择最小的一个放在最前面
	 * 
	 * @param array
	 */
	public static void selectSort(int[] array) {
		int length = array.length;
		int tmp;
		for (int i = 0; i < length - 2; i++) {
			for (int j = i + 1; j <= length - 1; j++) {
				if (array[i] > array[j]) {
					tmp = array[i];
					array[i] = array[j];
					array[j] = tmp;
				}
			}
		}
		for (int a : array)
			System.out.print(a + "  ");
	}

	public static void quickSort(int[] a, int lo0, int hi0) {
		int lo = lo0;
		int hi = hi0;
		if (lo >= hi)
			return;
		// 确定指针方向的逻辑变量
		boolean transfer = true;

		while (lo != hi) {
			if (a[lo] > a[hi]) {
				// 交换数字
				int temp = a[lo];
				a[lo] = a[hi];
				a[hi] = temp;
				// 决定下标移动，还是上标移动
				transfer = (transfer == true) ? false : true;
			}

			// 将指针向前或者向后移动
			if (transfer)
				hi--;
			else
				lo++;
		}

		// 将数组分开两半，确定每个数字的正确位置
		lo--;
		hi++;
		quickSort(a, lo0, lo);
		quickSort(a, hi, hi0);
	}

	public static void quickSort1(int[] a, int start, int last) {
		int low =start;
		int hi = last;
		int ip = start;
		int temp;
		if (start >= last - 1)
			return;
		while (start < last) {
			if ((a[last - 1] < a[ip] && last - 1 > ip) || (last - 1 < ip && a[last - 1] > a[ip])) {
				temp = a[last - 1];
				a[last - 1] = a[ip];
				a[ip] = temp;
				ip = last - 1;
			}
			if ((a[start] > a[ip] && start < ip) || (start > ip && a[start] < a[ip])) {
				temp = a[start];
				a[start] = a[ip];
				a[ip] = temp;
				ip = start;
			}
			start++;
			last--;
			for (int t : a)
				System.out.print(t + "  ");
			System.out.println();
		}
		quickSort1(a, low, ip);
		quickSort1(a, ip + 1, hi);
	}
}

package com.iteye.wwwcomy.sort;

public class TestSort {

	public static void main(String[] args) {
		int[] testArray = { 4, 7, 2, 13, 100, 1515, 51, 31, 52, 76, 23, 1, 88 };
		// quickSort1(testArray, 0, testArray.length);
		int[] result = mergeSort(testArray);
		System.out.println("------------");
		for (int a : result)
			System.out.print(a + " ");
	}

	/**
	 * 直接插入排序,每次把要排序的临时值插入到已经排序好的结果中,插入的过程就是把所有比临时值大/小的元素右移,
	 * 可以算出最差的情况是每一次比较都要进行移位，则相当于进行了0+1+2+...+N次移位，时间复杂度为等差数列之和：O(n(n+1)/2)，省略低位
	 * ，为O(N^2)
	 * 
	 * @param array
	 */
	public static void insertSort(int[] array) {
		int tmp;
		for (int i = 1; i < array.length; i++) {
			tmp = array[i];
			if (tmp < array[i - 1]) {
				for (int j = i - 1; j >= 0; j--) {
					if (tmp < array[j]) {
						array[j + 1] = array[j];
						array[j] = tmp;
					} else {
						break;
					}
				}
			}
		}
		for (int a : array) {
			System.out.print(a + "  ");
		}
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
		int low = start;
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
			for (int t : a) {
				System.out.print(t + "  ");
			}
			System.out.println();
		}
		quickSort1(a, low, ip);
		quickSort1(a, ip + 1, hi);
	}

	/**
	 * 递归的归并排序，由于两个已经排序的数组融合的算法时间是固定的，只需要递归的向里拆分，融合即可。因为是以二分法拆的，所以最终的计算相当于是一颗树，
	 * 树的高度为logN，假设merge的时间是C(N)，则总复杂度就是O(NlogN)，如果数据大的话会比插入排序快
	 * 
	 * @param input
	 * @return
	 */
	public static int[] mergeSort(int[] input) {
		if (input.length == 1) {
			return input;
		} else {
			int[] a1 = new int[input.length / 2];
			int[] a2 = new int[input.length - a1.length];
			System.arraycopy(input, 0, a1, 0, a1.length);
			System.arraycopy(input, a1.length, a2, 0, a2.length);
			return merge(mergeSort(a1), mergeSort(a2));
		}
	}

	private static int[] merge(int[] a1, int[] a2) {
		int[] result = new int[a1.length + a2.length];
		int i = 0;
		int j = 0;
		int resultIterator = 0;
		while (resultIterator < result.length) {
			if (j == a2.length) {
				result[resultIterator] = a1[i];
				i++;
			} else if (i == a1.length) {
				result[resultIterator] = a2[j];
				j++;
			} else {
				if (a1[i] <= a2[j]) {
					result[resultIterator] = a1[i];
					i++;
				} else if (a1[i] > a2[j]) {
					result[resultIterator] = a2[j];
					j++;
				} else {
					System.out.println("Why here");
				}
			}
			resultIterator++;
		}
		return result;
	}
}

package com.iteye.wwwcomy;

/**
 * 约瑟夫环
 * 
 * @author wwwcomy
 * 
 */
public class FiveOut {

	public static void main(String[] args) {
		int[] array = new int[100];
		for (int i = 0; i < 100; i++)
			array[i] = i + 1;
		int index = new FiveOut().cycleOut(array, 5);
		System.out.println("最后剩下的小孩" + index);
		cycleOut2();
	}

	int cycleOut(int[] array, int i) {
		int re = 0;
		int step1 = 1;
		int step2 = 0;
		int last = 0;
		for (int t = 0; t < 10000; t++) {
			if (step2 >= array.length)
				step2 = 0;
			if (array[step2] == -1) {
				step2++;
				continue;
			}
			if (last == array[step2])
				return array[step2];
			if (step1 == i) {
				System.out.println("chuqude" + step2);
				array[step2] = -1;
				step1 = 0;
			}
			step1++;
			last = array[step2];
			step2++;
		}
		return re;
	}

	public static void cycleOut2() {
		boolean[] b = new boolean[100];
		for (int i = 0; i < 100; i++) {
			b[i] = true;
		}
		int a = 1;
		int count = 0;
		int out = 0;
		while (true) {
			if (out == 99 && b[a - 1]) {
				System.out.println("最终剩下了" + a);
				return;
			}
			if (!b[a - 1]) {
				a++;
				if (a > 100)
					a = 1;
				continue;
			}
			count++;
			if (count == 5) {
				out++;
				b[a - 1] = false;
				count = 0;
				// System.out.println("出去的是" + (a - 1));
			}
			a++;
			if (a > 100)
				a = 1;
		}
	}
}

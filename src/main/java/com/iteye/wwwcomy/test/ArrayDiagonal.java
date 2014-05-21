package com.iteye.wwwcomy.test;

public class ArrayDiagonal {

	public static void main(String[] args) {
		int[][] a = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }, { 13, 14, 15, 16 } };
		print(a);
		System.out.println("**********************************");
		printDiagonal(a);
		System.out.println("**********************************");
		printLeftTop(a);
		System.out.println("**********************************");
		int[][] b = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 } };
		printDiagonal(b);
	}

	/**
	 * 按顺序打印数组
	 * 
	 * @param a
	 */
	public static void print(int[][] a) {
		for (int i = 0; i < a.length; i++) {
			int length = a[i].length;
			for (int j = 0; j < length; j++) {
				System.out.print(a[i][j] + " ");
			}
			System.out.println(",");
		}
	}

	/**
	 * 以右上为起点，按对角线顺序打印<br>
	 * 其实是个数学问题：<br>
	 * 1.打印的allLen行数为colLen * 2-1<br>
	 * 2.应当打印的坐标值的差是一个对称数列,为i- (allLen / 2),如(-3,-2,-1,0,1,2,3)<br>
	 * 3.按照列来看的话,对于要打印第i行的列j时,要打印的原矩阵行号为 (j+i-allLen/2)<br>
	 * 4.判断这个行坐标是不是在原有矩阵里面就可以了
	 * 
	 * @param a
	 */
	public static void printDiagonal(int[][] a) {
		int colLen = a[0].length;
		int allLen = colLen * 2 - 1;
		for (int i = 0; i < allLen; i++) {
			for (int j = 0; j < colLen; j++) {
				if (isValid(j + i - (allLen / 2), a))
					System.out.print(a[j + i - (allLen / 2)][j] + " ");
			}
			System.out.println("");
		}
	}

	/**
	 * 同理，还是找数学对应关系，从左上的话，要求横竖坐标的合正好等于当前行数
	 * 
	 * @param a
	 */
	public static void printLeftTop(int[][] a) {
		int colLen = a[0].length;
		int allLen = colLen * 2 - 1;
		for (int i = 0; i < allLen; i++) {
			for (int j = 0; j < colLen; j++) {
				if (isValid(i - j, a))
					System.out.print(a[i - j][j] + " ");
			}
			System.out.println("");
		}
	}

	private static boolean isValid(int i, int[][] a) {
		return i >= 0 && i < a.length;
	}

}

package com.iteye.wwwcomy.test;

import java.util.ArrayList;
import java.util.List;

import com.iteye.wwwcomy.lxn.utils.VarUtil;

/**
 * 求素数
 * 
 * 高斯猜测，n以内的素数个数大约与n/ln(n)相当，或者说，当n很大时，两者数量级相同。这就是著名的素数定理。
 * 
 * @author wwwcomy
 * 
 */
public class TestPrimeNum {
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		getPrimeNum1(100000);

		long end1 = System.currentTimeMillis();
		getPrimeNum3(100000);

		long end2 = System.currentTimeMillis();

		System.out.println("getPrimeNum1用时" + (end1 - start) + "毫秒");
		System.out.println("getPrimeNum3用时" + (end2 - end1) + "毫秒");
	}

	/**
	 * 从三开始，试除奇数
	 * 
	 * @param range
	 * @return
	 */
	public static List<Integer> getPrimeNum1(int range) {
		List<Integer> result = new ArrayList<Integer>();
		if (range > 2)
			result.add(2);
		for (int i = 2; i < range; i++) {
			if (i % 2 == 0)
				continue;
			// 计算素数,只需要循环到范围的平方根即可
			int sqrtNum = VarUtil.toInt(Math.sqrt(i));
			boolean hasRem = false;
			for (int j = 3; j <= sqrtNum; j = j + 2) {
				if (i % j == 0) {
					hasRem = true;
					break;
				}
			}
			if (!hasRem)
				result.add(i);
		}
		System.out.println(result);
		return result;
	}

	/**
	 * 从三开始，试除前面算出来的素数即可
	 * 
	 * @deprecated 可能是容器缘故，很慢
	 * 
	 * @param range
	 * @return
	 */
	public static List<Integer> getPrimeNum2(int range) {
		List<Integer> result = new ArrayList<Integer>();
		if (range > 2)
			result.add(2);
		for (int i = 2; i < range; i++) {
			if (i % 2 == 0)
				continue;
			// 计算素数,只需要循环到范围的平方根即可
			boolean hasRem = false;
			for (int j = 0; j < result.size(); j++) {
				if (i % result.get(j) == 0) {
					hasRem = true;
					break;
				}
			}
			if (!hasRem)
				result.add(i);
		}
		System.out.println(result);
		return result;
	}

	/**
	 * 使用筛选法
	 * 动态图见http://en.wikipedia.org/wiki/File:Sieve_of_Eratosthenes_animation.gif
	 * 
	 * @param range
	 * @return
	 */
	public static List<Integer> getPrimeNum3(int range) {
		List<Integer> result = new ArrayList<Integer>();
		boolean[] initBoolean = new boolean[range + 1];
		initBoolean[0] = true;
		initBoolean[1] = true;
		int sqrtNum = VarUtil.toInt(Math.sqrt(range));
		for (int i = 2; i <= sqrtNum; i++) {
			if (initBoolean[i])
				continue;
			for (int j = i + i; j <= range; j = j + i) {
				initBoolean[j] = true;
			}
		}
		for (int i = 0; i < initBoolean.length; i++) {
			if (!initBoolean[i])
				result.add(i);
		}
		System.out.println(result);
		return result;
	}
}

package com.iteye.wwwcomy;

import java.util.Random;

/**
 * 一个神奇的求π方式，假设有个半径为1的圆形，外面套着个边长为2的正方形 现在不停地往这个图形里面投射随机点，
 * 那么，落在圆形里面的概率肯定是通过次数可以测得的，而这个概率正好是圆形面积和正方形面积的比，可以反推π
 * 
 * @author wwwcomy
 * 
 */
public class FunnyPai {

	public static double count = 1000000.0;
	public static Random x = new Random();
	public static Random y = new Random();

	public static void main(String[] args) {
		double inCircle = 0.0;
		for (int i = 0; i < count; i++) {
			double randX = x.nextDouble();
			double randY = y.nextDouble();
			if (Math.sqrt(randX * randX + randY * randY) < 1) {
				inCircle = inCircle + 1.0d;
			}
		}
		double result = (4.0 * inCircle / count);
		System.out.println("The value of π is " + result);
	}
}

package com.iteye.wwwcomy.test;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * 测试保留两位小数的四种方式
 * 
 * @author wwwcomy
 * 
 */
public class TestNumFormat {
	double f = 111231.5585;

	/**
	 * 使用BigDecimal的setScale方法。 另外注意这里涉及到了BigDecimal的Rounding
	 * Mode-四舍五入，知识点请查看源代码
	 */
	public void m1() {
		BigDecimal bg = new BigDecimal(f);
		double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		System.out.println(f1);
	}

	/**
	 * DecimalFormat转换最简便
	 */
	public void m2() {
		DecimalFormat df = new DecimalFormat("#.00");
		System.out.println(df.format(f));
	}

	/**
	 * String.format打印最简便
	 */
	public void m3() {
		System.out.println(String.format("%.2f", f));
	}

	public void m4() {
		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(2);
		System.out.println(nf.format(f));
	}

	public static void main(String[] args) {
		TestNumFormat f = new TestNumFormat();
		f.m1();
		f.m2();
		f.m3();
		f.m4();
	}
}

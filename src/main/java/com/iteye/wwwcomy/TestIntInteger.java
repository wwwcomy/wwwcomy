package com.iteye.wwwcomy;

/**
 * int和Integer对象对比:
 * 1. int和Integer对比时，会自动对Integer拆箱
 * 2. Integer和new的Integer肯定是不相等的，都是引用对比
 * 3. Integer直接赋值，会调用Integer.valueOf方法，-128到127有缓存，引用比较也是相等的
 * 4. 网上好多说基本类型存在栈里，这基本就是坑爹呢。个人认为栈是在方法执行的时候出现的局部变量才用到的，成员变量应该都在堆里，这点需要再学习一下：两个成员变量int和Integer的存储区别
 * @author wwwcomy
 *
 */
public class TestIntInteger {

	public static void main(String[] args) {
		Integer a = new Integer(1);
		Integer b = new Integer(1);
		int c = 1;
		Integer e = 1;
		Integer f = 1;
		Integer x = 128;
		Integer y = 128;

		System.out.println("a==b:" + (a == b));// false
		System.out.println("a==c:" + (a == c));// true
		System.out.println("b==c:" + (b == c));// true
		System.out.println("a==e:" + (a == e));// false
		System.out.println("c==e:" + (c == e));// true
		System.out.println("e==c:" + (e == c));// true
		System.out.println("e==f:" + (e == f));// true
		System.out.println("x==y:" + (x == y));// false
	}

}

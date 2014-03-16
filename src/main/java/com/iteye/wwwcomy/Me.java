package com.iteye.wwwcomy;

public class Me {
	public static void main(String[] args) {
		System.out.println(Me.class.getName().replaceAll(".", "/") + ".class");
	}
	
	protected int test() throws RuntimeException{
		return 0;
	}
}

/**
 * 继承 范围修饰符可以大  抛出的异常必须小
 * 
 * @author wwwcomy
 *
 */
class I extends Me{
	public int test() throws IndexOutOfBoundsException{
		return 0;
	}
	
	private native void test1();
	synchronized public void test2(){};
}

package com.iteye.wwwcomy.proxy.cglib;

public class CglibProxyTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Base base = Factory.getInstance(new CglibProxy());
		base.add();
		
		Base base2 = Factory.getInstance(new CglibProxy(),1);
		base2.add();
	}

}

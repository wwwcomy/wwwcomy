package com.iteye.wwwcomy.proxy.plainproxy;

public class HelloImpl implements Hello {

	@Override
	public void sayHello(String to) {
		System.out.println("This is the words said by HelloImpl to " + to);
	}

	@Override
	public void print(String p) {
		System.out.println("This is the String printed by HelloImpl as " + p);
	}

}

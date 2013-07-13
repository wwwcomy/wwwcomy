package com.iteye.wwwcomy.jamon;

import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;

public class TestJamon {

	public static void main(String[] args) {
		Monitor test = MonitorFactory.start("test1");
		test.start();
		for (int i = 0; i < 100000; i++)
			System.out.println("1" + "2" + "3");
		test.stop();
		System.out.println(test);

	}
}

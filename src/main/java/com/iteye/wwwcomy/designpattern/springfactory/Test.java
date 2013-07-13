package com.iteye.wwwcomy.designpattern.springfactory;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {

	public static void main(String[] args) {
		BeanFactory factory = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		moveable m = (moveable) factory.getBean("v");
		m.run();
		

		// Properties p = new Properties();
		// try {
		// p.load(Test.class.getClassLoader().getResourceAsStream(
		// "test/designpattern/springfactory/spring.properties"));
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// String type = p.getProperty("VehicleType");
		// try {
		// Object o = Class.forName(type).newInstance();
		// ((moveable) o).run();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// System.out.println(type);
		// moveable m = new Car();
		// m.run();
	}
}

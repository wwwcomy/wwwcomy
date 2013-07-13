package com.iteye.wwwcomy.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

class Car {
	private String brand;
	private int Speed;

	public Car() {
		this.brand = "default";
		this.Speed = 10;
	}

	public Car(String brand, int speed) {
		this.brand = brand;
		Speed = speed;
	}

	public void introduce() {
		System.out.println("The Car brand is " + this.brand
				+ "; and the Maxspeed is " + this.Speed);
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public int getSpeed() {
		return Speed;
	}

	public void setSpeed(int speed) {
		Speed = speed;
	}

}

public class Reflect {

	public static Car getCar() throws Throwable {

		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		Class<?> clazz = cl.loadClass("com.iteye.wwwcomy.reflect.Car");
		Constructor<?> cs = clazz.getDeclaredConstructor();
		Car car2 = (Car) cs.newInstance();
		Method m1 = clazz.getMethod("getBrand");
		System.out.println(m1.invoke(car2));
		return car2;
	}

	/**
	 * @param args
	 * @throws Throwable
	 */
	public static void main(String[] args) throws Throwable {
		Car car1 = new Car("aa", 100);
		car1.introduce();
		Car car2 = getCar();
		car2.introduce();
	}

}

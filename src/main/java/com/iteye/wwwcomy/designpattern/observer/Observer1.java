package com.iteye.wwwcomy.designpattern.observer;

import java.util.ArrayList;
import java.util.List;

public class Observer1 {

	public void begin() {
		Parent p = new Parent();
		Grand g = new Grand();
		List<actionListener> l = new ArrayList<Observer1.actionListener>();
		l.add(p);
		l.add(g);
		// new Thread(new Child(p)).start();
		// new Thread(new Child(g)).start();
		new Thread(new Child(l)).start();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Observer1().begin();
	}

	class Child implements Runnable {
		boolean wakeUp = false;
		List<actionListener> l = new ArrayList<actionListener>();

		public Child(List<actionListener> l) {
			this.l = l;
		}

		public void wakeUp() {
			wakeUp = true;
			for (actionListener a : l)
				a.actionPerform(new TestEvent());
		}

		@Override
		public void run() {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			wakeUp();
		}
	}

	interface actionListener {
		void actionPerform(TestEvent t);
	}

	class TestEvent {
		TestEvent() {
		}
	}

	class Parent implements actionListener {
		public void actionPerform(TestEvent t) {
			System.out.println("feed the Child");
		}
	}

	class Grand implements actionListener {
		public void actionPerform(TestEvent t) {
			System.out.println("hug the Child");
		}
	}
}

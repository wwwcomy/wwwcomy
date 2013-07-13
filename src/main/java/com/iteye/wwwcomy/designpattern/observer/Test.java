package com.iteye.wwwcomy.designpattern.observer;

import java.util.ArrayList;
import java.util.List;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Button b = new Button();
		b.addListener(/**
		 * @author Administrator 监听实例
		 */
		new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				System.out.println("Button Pressed");

			}
		});
		b.pressed(new ActionEvent());
	}
}

class Button {
	List<ActionListener> l = new ArrayList<ActionListener>();

	public void pressed(ActionEvent ae) {
		for (ActionListener a : l)
			a.actionPerformed(ae);
	}

	public void addListener(ActionListener actionListener) {
		l.add(actionListener);
	}
}

/**
 * 事件
 * 
 * @author Liuxn
 * 
 */
class ActionEvent {
	@SuppressWarnings("unused")
	private Object src;

	public long getWhen() {
		return System.currentTimeMillis();
	}
}

/**
 * 监听接口
 * 
 * @author Liuxn
 * 
 */
interface ActionListener {
	void actionPerformed(ActionEvent ae);
}
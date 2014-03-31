package com.iteye.wwwcomy.designpattern.observer;

import java.util.Observable;
import java.util.Observer;

/**
 * 使用jdk自带的观察者制作的button例子
 * 
 * @author wwwcomy
 * 
 */
public class JdkObserverButton {
	public static void main(String[] args) {
		ObserverButton button = new ObserverButton();
		// 尽管这里添加了两个，但是实际运行的时候并不是按照顺序去触发的
		button.addObserver(new Observer() {

			@Override
			public void update(Observable o, Object arg) {
				System.out.println("1st thing button clicked!");

			}
		});
		button.addObserver(new Observer() {

			@Override
			public void update(Observable o, Object arg) {
				System.out.println("2nd thing button clicked!");

			}
		});
		button.click();
	}
}

class ObserverButton extends Observable {
	void click() {
		// 需要设置changed标记
		setChanged();
		notifyObservers();
	}
}
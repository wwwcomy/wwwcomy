package com.iteye.wwwcomy.designpattern.iterator;

public interface TestCollection<E> {

	public void add(E e);

	public E get(int index);

	public int size();

}

package com.iteye.wwwcomy.designpattern.iterator;

import java.util.Iterator;

public class TestArrayList<E> implements TestCollection<E> {

	private int index;

	private Object[] container = new Object[10];

	public TestArrayList() {
		index = 0;
	}

	public static void main(String[] args) {
		TestArrayList<Integer> ta = new TestArrayList<Integer>();
		for (int i = 0; i < 25; i++)
			ta.add(i);
		System.out.println("The No.7 is: " + ta.get(7));
		System.out.println(ta.size());
		System.out.println(ta);
		Iterator<Integer> i = ta.iterator();
		System.out.println("Iterator:");
		while (i.hasNext())
			System.out.println(i.next());
	}

	@Override
	public void add(E o) {
		if (index < container.length)
			container[index] = o;
		else {
			Object[] c = new Object[container.length * 2];
			System.arraycopy(container, 0, c, 0, container.length);
			container = c;
			container[index] = o;
		}
		index++;
	}

	@SuppressWarnings("unchecked")
	public E get(int index) {
		return (E) container[index];
	}

	@Override
	public int size() {
		return container.length;
	}

	public Iterator<E> iterator() {
		return new TAIterator<E>();
	}

	public String toString() {
		String tmp = "";
		for (int i = 0; i < index; i++)
			tmp += i + " ";
		return tmp;
	}

	@SuppressWarnings("hiding")
	private class TAIterator<E> implements Iterator<E> {
		private int indexIt;

		public TAIterator() {
			indexIt = 0;
		}

		@Override
		public boolean hasNext() {
			return indexIt < index;
		}

		@SuppressWarnings("unchecked")
		@Override
		public E next() {
			return (E) get(indexIt++);
		}

		@Override
		public void remove() {
		}
	}

}

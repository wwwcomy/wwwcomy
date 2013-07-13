package com.iteye.wwwcomy.testcollctions;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class TArrayList<E> implements Collection<E> {

	private Object[] values = new Object[10];

	private int size = 10;

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return values.length > 0;
	}

	@Override
	public boolean contains(Object o) {
		for (int i = 0; i < values.length; i++)
			if (values[i].equals(o))
				return true;
		return false;
	}

	@Override
	public Iterator<E> iterator() {
		return null;
	}

	@Override
	public Object[] toArray() {
		return values;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean add(E e) {
		if (values.length == size) {
			values = Arrays.copyOf(values, size / 2 * 3);
			values[size] = e;
			return true;
		}
		values[values.length] = e;
		return true;
	}

	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

}

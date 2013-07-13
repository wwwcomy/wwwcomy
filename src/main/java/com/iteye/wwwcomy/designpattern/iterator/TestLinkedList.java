package com.iteye.wwwcomy.designpattern.iterator;

import java.util.Iterator;

public class TestLinkedList<E> implements TestCollection<E> {

	private Node first;
	private Node cur;
	private Node last;
	private int size;

	public static void main(String[] args) {
		TestLinkedList<Integer> la = new TestLinkedList<Integer>();
		for (int i = 0; i < 25; i++)
			la.add(i);
		System.out.println("The No.7 is: " + la.get(7));
		System.out.println(la.size());
		System.out.println(la);
		System.out.println("Iterator:");

		Iterator<Integer> i = la.iterator();
		while (i.hasNext())
			System.out.println(i.next());
	}

	public TestLinkedList() {
		this.first = this.last = this.cur = null;
		size = 0;
	}

	@Override
	public void add(E e) {
		if (size == 0) {
			first = new Node(e, null);
			last = first;
			cur = first;
		} else {
			Node n = new Node(e, null);
			cur.nextNode = n;
			cur = n;
		}
		size++;

	}

	@Override
	public int size() {
		return size;
	}

	@SuppressWarnings("unchecked")
	@Override
	public E get(int index) {
		if (index == size - 1)
			return (E) last.o;
		else {
			Node tmp = first;
			for (int i = 0; i < index; i++) {
				tmp = tmp.nextNode;
			}
			return (E) tmp.o;
		}
	}

	public String toString() {
		String sTmp = "";
		Node tmp = first;
		for (int i = 0; i < size; i++) {
			sTmp += tmp.o + " ";
			tmp = tmp.nextNode;
		}
		return sTmp;
	}

	public Iterator<E> iterator() {
		return new TLIterator<E>();
	}

	@SuppressWarnings("hiding")
	class TLIterator<E> implements Iterator<E> {

		private Node curNode;

		public TLIterator() {
			curNode = first;
		}

		@Override
		public boolean hasNext() {
			return (first != null && curNode.nextNode != null);
		}

		@SuppressWarnings("unchecked")
		@Override
		public E next() {
			if (curNode == first) {
				curNode = curNode.nextNode;
				return (E) first.o;
			}
			Node n = curNode.nextNode;
			if (n != null) {
				curNode = n;
				return (E) n.o;
			}
			return null;
		}

		@Override
		public void remove() {
		}

	}

	class Node {
		Object o;
		Node nextNode;

		public Node(Object o, Node n) {
			this.o = o;
			this.nextNode = n;
		}
	}
}

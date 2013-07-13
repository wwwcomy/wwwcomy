package com.iteye.wwwcomy.lxn.utils;

import java.io.Serializable;

/**
 * @author zhouxw
 * 
 *         WeakReference 弱引用对象refValue在GC的时候会被更快的被回(jdk有自带的WeakReference,不太适用)
 * 
 * @param <T>
 */
public class RefParameter<T> implements Serializable { // Externalizable
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public T refValue;

	public RefParameter() {
	}

	public RefParameter(T v) {
		this.refValue = v;
	}

	public T getValue() {
		return this.refValue;
	}

	public void setValue(T v) {
		this.refValue = v;
	}

	public String toString() {
		if (this.refValue == null)
			return null;
		return this.refValue.toString();
	}
}

// public class ByRef<T> extends RefParameter<T> {
//
// }

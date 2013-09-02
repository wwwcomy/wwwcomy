/*
 * Created on 2004-10-11
 */
package com.iteye.wwwcomy.lxn.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * RTTI执行工具。
 * 
 * @author zhouxw
 */
@SuppressWarnings("rawtypes")
public class RttiUtil {

	/**
	 * 通过RIIT调用实例的方法,返回结果.(非静态方法)
	 * 
	 * @param className
	 *            类名
	 * @param staticMethodName
	 *            静态方法名
	 * @param methodParamNames
	 *            方法参数名
	 * @param paramValues
	 *            方法参数值
	 * @return Object
	 * @throws Throwable
	 */
	public static Object invokeInstance(Object obj, String methodName,
			Class[] methodParamTypes, Object[] paramValues) throws Throwable {
		Object result = null; 
		// try {
		Class<? extends Object> cls = obj.getClass();
		Method m = cls.getMethod(methodName, methodParamTypes);
		result = m.invoke(obj, paramValues);
		// } catch (Throwable ex) {
		// throw ex;
		// }
		return result;
	}
	
	/**
	 * 通过RIIT调用类的方法,返回结果.(静态方法)
	 * 
	 * @param className
	 *            类名
	 * @param staticMethodName
	 *            静态方法名
	 * @param methodParamNames
	 *            方法参数名
	 * @param paramValues
	 *            方法参数值
	 * @return Object
	 * @throws Throwable
	 */
	public static Object invoke(String className, String staticMethodName,
			Class[] methodParamTypes, Object[] paramValues, ClassLoader cl) throws Throwable { 
		Object result = null; 
		// try {
		Class<?> cls = null;
		if (cl!=null)
			cls = cl.loadClass(className);
		else
			cls = Class.forName(className);
		Method m = cls.getMethod(staticMethodName, methodParamTypes); 
		result = m.invoke(null, paramValues);
		// } catch (Throwable ex) { 
		// throw ex;
		// }
		return result;
	}

	/**
	 * 通过RIIT调用类的方法,返回结果.(静态方法)
	 * 
	 * @param className
	 *            类名
	 * @param staticMethodName
	 *            静态方法名
	 * @param methodParamNames
	 *            方法参数名
	 * @param paramValues
	 *            方法参数值
	 * @return Object
	 * @throws Throwable
	 */
	public static Object invoke(String className, String staticMethodName,
			Class[] methodParamTypes, Object[] paramValues) throws Throwable {
		return invoke(className,staticMethodName,methodParamTypes,paramValues,null);
	}

	/**
	 * @param className
	 * @return Object
	 * @throws Throwable
	 */
	public static Object instance(String className) throws Throwable {
		return instance(className, new Class[] {}, new Object[] {},Thread.currentThread().getContextClassLoader());
	}
	
	
	/**
	 * @param className
	 * @param cl
	 * @return Object
	 * @throws Throwable
	 */
	public static Object instance(String className,ClassLoader cl) throws Throwable {
		return instance(className, new Class[] {}, new Object[] {},cl);
	}
	
	/**
	 * 对名为className的类，实例化一个对象。
	 * 
	 * (外部程序根据需要，判断是否使用单例模式，这里不加处理)
	 * 
	 * @param className
	 *            类名
	 * @param paramTypes
	 *            构造器参数类型
	 * @param paramValues
	 *            构造器参数值
	 * @return 实例化的对象Object
	 * @throws Throwable
	 */
	public static Object instance(String className, Class[] paramTypes,
			Object[] paramValues,ClassLoader cl) throws Throwable {
		Object result = null;
		// String desc = "RttiUtil.newInstance()";
		// try {
		Class<?> cls = Class.forName(className,true,cl);
		if(cls!=null){
			if (paramTypes == null || paramValues == null)
				result = cls.newInstance();
			else {
				Constructor<?> con = cls.getConstructor(paramTypes);
				result = con.newInstance(paramValues);
			}
		}
		return result;
	}

	/**
	 * @param className
	 * @return Object
	 * @throws Throwable
	 */
	public static Object instanceOnce(String className) throws Throwable {
		Object result = null; 
		// try {
		if (objMap.get(className) == null) {
			synchronized (initLock) {
				try {
					result = Class.forName(className).newInstance();
					objMap.put(className, result);
				} catch (Throwable ex) {
					// ignored ex;
				}
			}
		} else {
			result = objMap.get(className);
		} 
		return result; // 可以返回空 说明此类不存在 然后继续找
	}

	/**
	 * 
	 */
	private final static Object initLock = new Object();

	/**
	 * 
	 */
	private final static HashMap<String, Object> objMap = new HashMap<String, Object>();

}

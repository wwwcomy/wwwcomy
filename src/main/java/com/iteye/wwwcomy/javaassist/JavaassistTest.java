package com.iteye.wwwcomy.javaassist;

import java.io.IOException;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

public class JavaassistTest {

	public static void main(String[] args) throws NotFoundException,
			CannotCompileException, IOException {
		ClassPool pool = ClassPool.getDefault();
		CtClass cc = pool.get("com.iteye.wwwcomy.javaassist.Base");
		CtMethod cm = cc.getDeclaredMethod("add");
		cm.insertBefore("{System.out.println(\"hello javaassist!\");}");
		cc.writeFile("C:\\software\\github\\trunk\\target\\classes");
		
		Base base = new Base(1);
		base.add(2);
	}

}

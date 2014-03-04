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
		CtClass cc = pool.get("test.javaassist.Base");
		CtMethod cm = cc.getDeclaredMethod("add");
		cm.insertBefore("{System.out.println(\"hello javaassist!\");}");
		cc.writeFile("D:/Work/Java/Workspace130821/TESTWeb/WebContent/WEB-INF/classes");
		
		Base base = new Base(1);
		base.add(2);
	}

}

package com.iteye.wwwcomy.asm;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.iteye.wwwcomy.classloader.TestClassLoader;

public class PreCompileProcess {
    @SuppressWarnings("rawtypes")
    public static void main(String[] args) throws Exception {

        TestClassLoader cl1 = new TestClassLoader();
        Thread.currentThread().setContextClassLoader(cl1);

        String className = "com.iteye.wwwcomy.model.Student";

        // 为 Student 添加字段
        AddFieldByClassName add = new AddFieldByClassName(className);
        // 添加一个名为 address，类型为 java.lang.String 的 public 字段
        add.addPublicField("address", "Ljava/lang/String;");
        // 再增加一个名为 tel，类型为 int 的 public 方法
        add.addPublicField("tel", "I");
        // 重新生成 .class 文件
        add.writeByteCode();

        Class studentClass = cl1.findClass(className, new File(
                "C:/software/hibernatetest/target/classes/com/iteye/wwwcomy/model/Student.class"));
        test1(studentClass);
        System.out.println("modify done");

        testMain();
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void testMain() throws Exception {
        Class mainClass = Thread.currentThread().getContextClassLoader().loadClass("com.iteye.wwwcomy.test.asm.Main");
        Class[] c = {String[].class};
        Method method = mainClass.getMethod("main", c);
        Object[] obj = {new String[0]};
        method.invoke(null, obj);
    }

    @SuppressWarnings({"rawtypes"})
    private static void test1(Class classStudent) {
        Object o;
        try {
            o = classStudent.newInstance();
            System.out.println(o.getClass().getClassLoader());
            Field fld = o.getClass().getField("tel");
            fld.setInt(o, 123);
            Object val = fld.get(o);
            System.out.println(val);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

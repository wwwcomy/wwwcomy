package com.iteye.wwwcomy.asm;

import java.lang.reflect.Field;

import com.iteye.wwwcomy.hbase.Student;

public class Main {
    public static void main(String[] args) throws Exception {
        Student student = new Student();
        System.out.println(student.getClass().getClassLoader());
        Field fld = Student.class.getField("tel");
        fld.setInt(student, 123);
        Object val = fld.get(student);
        System.out.println(val);
    }
}

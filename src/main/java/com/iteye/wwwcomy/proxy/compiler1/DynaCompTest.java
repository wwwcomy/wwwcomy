/*
 * File: $RCSfile$
 *
 * Copyright (c) 2001-2011 by Wincor Nixdorf International GmbH,
 * Heinz-Nixdorf-Ring 1, 33106 Paderborn, Germany
 *
 * This software is the confidential and proprietary information
 * of Wincor Nixdorf.
 *
 * You shall not disclose such confidential information and shall
 * use it only in accordance with the terms of the license agreement
 * you entered into with Wincor Nixdorf.
 *
 */

package com.iteye.wwwcomy.proxy.compiler1;

public class DynaCompTest {
    public static void main(String[] args) throws Exception {
        String fullName = "test.DynaClass";
        StringBuilder src = new StringBuilder();
        src.append("package test;\n");
        src.append("public class DynaClass {\n");
        src.append("    public String toString() {\n");
        src.append("        return \"Hello, I am \" + ");
        src.append("this.getClass().getSimpleName();\n");
        src.append("    }\n");
        src.append("}\n");

        System.out.println(src);
        DynamicEngine de = DynamicEngine.getInstance();
        Object instance = de.javaCodeToObject(fullName, src.toString());
        System.out.println(instance);
    }
}

/**
 * History:
 * 
 * $Log$
 * 
 */

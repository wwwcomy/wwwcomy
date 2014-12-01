package com.iteye.wwwcomy.oom;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Using string to make jvm oom
 * 
 * @author xingnan.liu
 */
public class PermGenOOMByString {
    static List<String> list = new ArrayList<String>();

    public static void main(String[] args) {
        try {
            Random r = new Random();
            while (true) {
                String tmp = String.valueOf(r.nextDouble());
                list.add(tmp.intern());
                System.out.println(tmp);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}

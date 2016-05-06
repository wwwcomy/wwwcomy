package com.iteye.wwwcomy.test;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * 生成不重复的随机数
 * 
 * 暂时不考虑边界和负数，随便写写
 * 
 * @author wwwcomy
 *
 */
public class RandomTest {

    public static void main(String[] args) {
        System.out.println(gen1(10, 50));
    }

    public static Set<Integer> gen1(int size, int range) {
        Set<Integer> set = new HashSet<Integer>();
        SecureRandom sr = new SecureRandom();
        while (true) {
            int temp = sr.nextInt(range);
            if (!set.contains(temp)) {
                set.add(temp);
                if (set.size() == size)
                    return set;
            }
        }
    }
}

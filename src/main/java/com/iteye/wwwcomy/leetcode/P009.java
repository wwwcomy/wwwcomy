package com.iteye.wwwcomy.leetcode;

/**
 * Palindrome Number 回文数
 * 
 * @author wwwcomy
 *
 */
public class P009 {
    public static void main(String[] args) {
        // System.out.println(new P009().isPalindrome2(1221));
        // System.out.println(new P009().isPalindrome2(10));
        System.out.println(new P009().isPalindrome2(2222222));
        // System.out.println(new P009().isPalindrome3(2222222));
    }

    /**
     * 通过反转那题来解决
     * 
     * @param x
     * @return
     */
    public boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        }
        int r = new P007().reverse(x);
        if (r == x) {
            return true;
        }
        return false;
    }

    /**
     * 位数一个一个比
     * 
     * @param x
     * @return
     */
    public boolean isPalindrome2(int x) {
        if (x < 0) {
            return false;
        }
        int i = 1;
        while ((x / i) >= 10) {
            i = i * 10;
        }
        int s = 1;
        for (int j = 10; j <= i; j = j * 10, i = i / 10) {
            int first = s == 1 ? (x / i) : (x / i) % 10;
            int last = s == 1 ? (x % j) : (x % j) / s;
            System.out.println(i + "--" + j);
            System.out.println(first + "--" + last);
            s = s * 10;
            if (first != last) {
                return false;
            }
        }
        return true;
    }

    /**
     * 同上 也是位数一个一个比较
     * 
     * @param x
     * @return
     */
    public boolean isPalindrome3(int x) {
        if (x < 0) {
            return false;
        }
        int i = 1;
        while ((x / i) >= 10) {
            i = i * 10;
        }
        for (int j = 10; j <= i; j = j * 10, i = i / 10) {
            int first = (x % j) / (j / 10);
            int last = j == 10 ? (x / i) : (x / i) % (10);
            System.out.println(i + "--" + j);
            System.out.println(first + "--" + last);
            if (first != last) {
                return false;
            }
        }
        return true;
    }
}

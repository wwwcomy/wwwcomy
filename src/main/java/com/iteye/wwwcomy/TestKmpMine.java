package com.iteye.wwwcomy;

import java.util.Arrays;

public class TestKmpMine {
    static String S = "BBC ABCDAB ABCDABCDABDE";
    static String P = "ABCDABD";
    static String P1 = "DABCDABD";
    static String P2 = "ABAB";

    public static void main(String[] args) {
        System.out.println(violateMatchByWhile(S, P));
        System.out.println(violateMatchByWhileJuly(S, P));
        System.out.println(kmpMatch(S, P));
        System.out.println(Arrays.toString(getNextArray(P1)));
        System.out.println(Arrays.toString(getNextArray(P2)));
    }

    /**
     * 暴力循环遍历，一个一个匹配
     * 
     * @param S
     * @param P
     * @return
     */
    public static int violateMatch(String S, String P) {
        int sLength = S.length();
        int pLength = P.length();
        for (int i = 0; i < sLength - pLength; i++) {
            for (int j = 0; j < pLength; j++) {
                if (P.charAt(j) != S.charAt(i + j))
                    break;
                if (j == pLength - 1)
                    return i;
            }
        }
        return -1;
    }

    /**
     * 暴力循环遍历，一个一个匹配,使用while做循环条件，实际上也是个双层循环
     * 
     * 此方法与violateMatch方法相同
     * 
     * @param S
     * @param P
     * @return
     */
    public static int violateMatchByWhile(String S, String P) {
        int sLength = S.length();
        int pLength = P.length();
        int i = 0;
        int j = 0;
        while (j < pLength && i < sLength) {
            int position = i;
            if (P.charAt(j) == S.charAt(position + j)) {
                position++;
                j++;
            } else {
                j = 0;
                i++;
            }
        }
        if (j == pLength)
            return i;
        return -1;
    }

    /**
     * 暴力循环遍历july的写法，实际上和while一样。
     * 
     * @param S
     * @param P
     * @return
     */
    public static int violateMatchByWhileJuly(String S, String P) {
        int sLength = S.length();
        int pLength = P.length();
        int i = 0;
        int j = 0;
        int counter = 0;
        while (j < pLength && i < sLength) {
            counter++;
            if (P.charAt(j) == S.charAt(i)) {
                i++;
                j++;
            } else {
                // 失配后回到最开始的下一个
                i = i - j + 1;
                j = 0;
            }
        }
        System.out.println("July暴力匹配循环次数" + counter);
        if (j == pLength)
            return i - j;
        return -1;
    }

    /**
     * KMP实现，找出P的next数组，当失配的时候，不再重复匹配，而是跳到下一个地方
     * 
     * @param S
     * @param P
     * @return
     */
    public static int kmpMatch(String S, String P) {
        int sLength = S.length();
        int pLength = P.length();
        int i = 0;
        int j = 0;
        int[] nextArray = getNextArray(P);
        int counter = 0;
        while (j < pLength && i < sLength) {
            counter++;
            if (P.charAt(j) == S.charAt(i)) {
                i++;
                j++;
            } else {
                if (j == 0) {
                    i++;
                    j++;
                } else
                    j = nextArray[j - 1];
            }
        }
        System.out.println("KMP循环次数" + counter);
        if (j == pLength)
            return i - j;
        return -1;
    }

    private static int[] getNextArray(String pattern) {
        int len = pattern.length();
        int[] next = new int[len];
        next[0] = 0;
        int i = 1;
        while (i < len) {
            if (pattern.charAt(i) == pattern.charAt(next[i - 1])) {
                next[i] = next[i - 1] + 1;
            } else {
                if (pattern.charAt(i) == pattern.charAt(0))
                    next[i] = 1;
                else
                    next[i] = 0;
            }
            i++;
        }
        return next;
    }
}

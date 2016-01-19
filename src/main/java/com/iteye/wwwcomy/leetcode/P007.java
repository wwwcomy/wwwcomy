package com.iteye.wwwcomy.leetcode;

/**
 * Reverse digits of an integer.
 * 
 * The solution did not do class cast. Need to check int overflow.
 * 
 * @author wwwcomy
 *
 */
public class P007 {
    public static void main(String[] args) {
        System.out.println(Integer.MIN_VALUE);
        System.out.println(Integer.MAX_VALUE);
        System.out.println(new P007().reverse(Integer.MIN_VALUE));
        System.out.println(new P007().reverse(Integer.MAX_VALUE));
        System.out.println(new P007().reverse(123));
        System.out.println(new P007().reverse(-321));
        System.out.println(new P007().reverse(1534236469));
    }

    public int reverse(int x) {
        if (x < -2147483641 || x > 2147483641)
            return 0;

        boolean isNegativeNum = (x < 0 ? true : false);
        if (isNegativeNum) {
            x = 0 - x;
        }
        int result = 0;
        while ((x / 10) > 0) {
            int last = x % 10;
            if (isNegativeNum) {
                if (result > 214748364) {
                    return 0;
                } else if (result == 214748364 && last > 8) {
                    return 0;
                }
            } else {
                if (result > 214748364) {
                    return 0;
                } else if (result == 214748364 && last > 7) {
                    return 0;
                }
            }
            x = x / 10;
            result = result * 10 + last;
        }
        if (isNegativeNum) {
            if (result > 214748364) {
                return 0;
            } else if (result == 214748364 && x > 8) {
                return 0;
            }
        } else {
            if (result > 214748364) {
                return 0;
            } else if (result == 214748364 && x > 7) {
                return 0;
            }
        }
        result = result * 10 + x;
        return isNegativeNum ? (0 - result) : result;
    }

}

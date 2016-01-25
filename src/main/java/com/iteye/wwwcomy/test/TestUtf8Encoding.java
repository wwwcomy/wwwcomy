package com.iteye.wwwcomy.test;

import java.nio.charset.Charset;

/**
 * https://www.zhihu.com/question/23374078
 * 
 * @author wwwcomy
 *
 */
public class TestUtf8Encoding {
    public static void main(String[] args) {
        // "报"的UTF8编码表中对应的编码
        String a = "62a5";
        String binary = Integer.toBinaryString(Integer.valueOf(a, 16));
        System.out.println(binary);
        // 转成2进制后,根据UTF-8的编码规则进行编码，生成一下二进制码
        // 编码规则：
        // 0xxxxxxx
        // 110xxxxx 10xxxxxx
        // 1110xxxx 10xxxxxx 10xxxxxx
        // 11110xxx 10xxxxxx 10xxxxxx 10xxxxxx
        // 111110xx 10xxxxxx 10xxxxxx 10xxxxxx 10xxxxxx
        // 1111110x 10xxxxxx 10xxxxxx 10xxxxxx 10xxxxxx 10xxxxxx
        String bi = getUtf8Binary(binary);
        // 生成二进制码后，按照每8个位一个字节的方式，生成3个byte,进而生成String，可以看到对应的"报"打印出来
        String a1 = bi.substring(0, 8);
        String a2 = bi.substring(8, 16);
        String a3 = bi.substring(16);
        System.out.println(a1);
        System.out.println(a2);
        System.out.println(a3);
        byte[] bs = new byte[3];

        // 这里从二进制码转换成byte貌似在java里面挺麻烦，如果直接使用Byte.parseByte()方法,会产生溢出，所以要先转成Int然后强转成byte才能生成正常的byte
        // 注意java里面byte是-128~127，
        // 八位中的第一位是符号，而int是4个字节，如果1个字节的话，区间就成了0-255了，所以会溢出
        byte v1 = (byte) Integer.parseInt(a1, 2);
        byte v2 = (byte) Integer.parseInt(a2, 2);
        byte v3 = (byte) Integer.parseInt(a3, 2);
        bs[0] = v1;
        bs[1] = v2;
        bs[2] = v3;
        System.out.println(new String(bs, Charset.forName("UTF-8")));
    }

    /**
     * 写成一个动态的，这样就是随便写一个16进制码，先转成2进制，然后自动生成UTF编码后的二进制码，然后生成String
     * 
     * @param initbinary
     * @return
     */
    private static String getUtf8Binary(String initBinary) {
        char[] c = initBinary.toCharArray();
        int len = c.length;
        StringBuilder sb = new StringBuilder();
        if (len <= 7) {
            sb.append("0");
            for (int i = (7 - len); i > 0; i--) {
                sb.append(0);
            }
            sb.append(initBinary);
        } else {
            int unicodeHolder = len / 6;
            for (int i = 0; i < unicodeHolder; i++) {
                //sb.append(c)
                // TODO continue this
            }
        }
        return sb.toString();
    }
}

package com.iteye.wwwcomy.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * Class for get the MicroSoft Access DB password. Refer to
 * http://www.cnblogs.com/diulela/archive/2012/07/30/2615553.html
 * http://wenku.baidu.com/view/914b3bd3b8f67c1cfad6b8de
 * 
 * @author wwwcomy
 *
 */
public class TestGetAccessPassword {
    public static void main(String[] args) throws Exception {
        new TestGetAccessPassword().test();
    }

    private void test() throws Exception {
        byte[] originalBs = getFileBytes("c:/temp/db_no_password.mdb", 66, 34);
        byte[] passwordBs = getFileBytes("c:/temp/pass2.mdb", 66, 34);
        byte[] result = new byte[33];
        for (int i = 0; i < 33; i++) {
            if (i % 2 == 1) {
                // byte tmp = (byte) (originalBs[i] ^ passwordBs[i]);
                // if (tmp != 0) {
                // result[i] = (byte) (tmp ^ high);
                // }
                result[i] = (byte) (originalBs[i] ^ passwordBs[i]);
            } else {
                result[i] = (byte) (originalBs[i] ^ passwordBs[i]);
            }
        }
        // for (int i = 0; i < 16; i++) {
        // byte[] temp = new byte[2];
        // byte b = (byte) (originalBs[i * 2] ^ passwordBs[i * 2]);
        // byte b1 = (byte) (originalBs[i * 2 + 1] ^ passwordBs[i * 2 + 1]);
        // if (i % 2 == 0) {
        // b ^= low;
        // b1 ^= high;
        // }
        // temp[0] = b;
        // temp[1] = b1;
        // sb.append(new String(temp, "utf-8"));
        // }
        // System.out.println(sb.toString());
        // System.out.println("");
        System.out.println(new String(result, Charset.defaultCharset()));
    }

    public byte[] getFileBytes(String filePath, int start, int len) throws Exception {
        File file = new File(filePath);
        InputStream is = new FileInputStream(file);
        byte[] bs = new byte[len];
        int t = 0;
        int i = 0;
        while ((t = is.read()) != -1) {
            if (i >= start && i < start + len) {
                bs[i - start] = (byte) t;
            }
            if (i > (start + len)) {
                break;
            }
            i++;
        }
        is.close();
        return bs;
    }
}

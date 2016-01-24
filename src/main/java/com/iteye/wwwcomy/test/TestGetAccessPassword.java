package com.iteye.wwwcomy.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Class for get the MicroSoft Access DB password. Refer to
 * http://www.cnblogs.com/diulela/archive/2012/07/30/2615553.html
 * 
 * Just begining to learn UltraEdit hex-editor..
 * TODO tasks:
 * 1. Add Util for BCD, octal, decimal, hexadecimal
 * 2. Learn how file was read in java (bytes returned)
 * 3. finish getting encrypt zone from mdb file.
 * 
 * @author wwwcomy
 *
 */
public class TestGetAccessPassword {
	public static void main(String[] args) throws Exception {
		new TestGetAccessPassword().test();
	}

	private void test() throws Exception {
		byte[] bs = getFileBytes("c:/temp/test.txt");
		for (byte b : bs) {
			System.out.println(b);
		}
	}

	public byte[] getFileBytes(String filePath) throws Exception {
		File file = new File(filePath);
		InputStream is = new FileInputStream(file);
		byte[] bs = new byte[20];
		int b;
		int i = 0;
		while ((b = is.read()) != -1) {
			bs[i] = (byte) b;
			i++;
		}
		return bs;
	}
}

package com.iteye.wwwcomy.json;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class foo {
	public String a;
	public String b;

	public static void main(String[] args) throws IOException {
		File a = new File("d:/1/test.txt");

		for (int i = 0; i < 10; i++) {
			FileWriter fw = new FileWriter(a);
			fw.append("1");
			fw.flush();
			fw.close();
		}
	}
}

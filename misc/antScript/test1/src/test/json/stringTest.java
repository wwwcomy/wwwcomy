package test.json;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class stringTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File test = new File("d:/testFile.test");
		long modTime = test.lastModified();
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(modTime);
		SimpleDateFormat sdf = new SimpleDateFormat(
				"修改时间：yyyy年MM月dd日 hh时mm分ss秒");
		System.out.println(sdf.format(cal.getTime()));
	}

	public static void test() {
		String a1 = "abc";
		String a2 = "a" + "bc";
		String a3 = "a" + "b" + "c";
		String b1 = new String("abc");
		String b2 = new String("abc");
		String b3 = new String("a") + new String("bc");
		System.out.println(a1 == a2);
		System.out.println(a2 == a3);
		System.out.println(a1 == a3);

		System.out.println(a1 == b1);

		System.out.println("lai B de ");
		System.out.println(b1 == b2);
		System.out.println(b1 == b3);
		System.out.println(b3 == b2);
	}

	public static void testFile() {
		FileWriter testFile = null;
		try {
			testFile = new FileWriter("d:/testFile.test");
			testFile.write("test content");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				testFile.flush();
				testFile.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}

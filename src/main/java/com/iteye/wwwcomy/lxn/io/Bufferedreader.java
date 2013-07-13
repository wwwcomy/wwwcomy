package com.iteye.wwwcomy.lxn.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Bufferedreader {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		String l;
		while ((l = br.readLine()) != null) {
			if (l.equalsIgnoreCase("exit"))
				System.exit(-1);
			System.out.println("Ur input is: " + l);
		}

	}

}

package com.iteye.wwwcomy.lxn.nio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class TestReadFile {

	public static void main(String[] args) throws IOException {
		new TestReadFile().nIOStart();
	}

	/**
	 * 常规IO方式读取文件
	 * 
	 * @throws IOException
	 */
	public void ordinaryIOStart() throws IOException {
		FileReader fr = null;
		BufferedReader br = null;
		try {
			File inputFile = new File("d:/niotest/test.txt");
			fr = new FileReader(inputFile);
			br = new BufferedReader(fr);
			String line;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null)
				br.close();
			if (fr != null)
				fr.close();
		}
	}

	public void nIOStart() throws IOException {
		FileInputStream fis = null;
		try {
			File inputFile = new File("d:/niotest/test.txt");
			fis = new FileInputStream(inputFile);
			FileChannel c = fis.getChannel();
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			c.read(buffer);
			System.out.println(new String(buffer.array()));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}
	
	public void nIOCopy() throws IOException {
		FileInputStream fis = null;
		try {
			File inputFile = new File("d:/niotest/test.txt");
			fis = new FileInputStream(inputFile);
			FileChannel c = fis.getChannel();
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			c.read(buffer);
			System.out.println(new String(buffer.array()));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}
}

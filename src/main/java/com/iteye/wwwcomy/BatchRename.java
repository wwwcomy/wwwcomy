package com.iteye.wwwcomy;

import java.io.File;

public class BatchRename {
	public static void main(String[] args) {
		rename("E:\\音乐\\BillBorad Top 100\\Billboard Top 100 of 2005");
	}

	public static void rename(String path) {
		File parentFolder = new File(path);
		if (!parentFolder.isDirectory()) {
			System.err.println("应当输入目录!");
			return;
		}
		File[] files = parentFolder.listFiles();
		for (int i = 0; i < files.length; i++) {
			String fileName = files[i].getName();
			String newFileName = fileName.replaceAll("-\\[www\\.edotnew\\.com\\]", "");
			System.out.println(newFileName);
			files[i].renameTo(new File(path + File.separator + newFileName));
		}
	}
}

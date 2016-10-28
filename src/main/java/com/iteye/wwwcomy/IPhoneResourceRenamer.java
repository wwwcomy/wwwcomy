package com.iteye.wwwcomy;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JTextArea;

/**
 * iphone照片的文件名处理,把文件名之前加上修改时间/创建时间
 * 
 * 不递归处理所有子目录，仅单层
 * 
 * @author wwwcomy
 *
 */
public class IPhoneResourceRenamer {

	public final static String FOLDER_NAME = "F:\\iphone视频\\";

	public static void main(String[] args) throws Exception {
		new IPhoneResourceRenamer().beginTask(null, FOLDER_NAME);
	}

	public void beginTask(JTextArea jta, String folderName) throws Exception {
		File folder = new File(folderName);
		if (!folder.exists() || !folder.isDirectory()) {
			System.err.println("Folder not right!");
			return;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		File[] files = folder.listFiles();
		for (File file : files) {
			if (!file.isDirectory()) {
				String fileSuffix = getFileSuffix(file);
				// only handle the mov files and jpg files
				if ("mov".equalsIgnoreCase(fileSuffix) || "jpg".equalsIgnoreCase(fileSuffix)) {
					printOut(jta, file.getName());
					long lastModifyTime = file.lastModified();
					Date date = new Date(lastModifyTime);
					String sDate = sdf.format(date);
					if (file.getName().startsWith(sDate)) {
						printOut(jta, "The file has already been named by the modified date");
						continue;
					}
					String filePath = file.getCanonicalPath().substring(0, file.getCanonicalPath().lastIndexOf("\\"));
					String newFileName = filePath + "/" + sDate + "-" + file.getName();
					boolean r = file.renameTo(new File(newFileName));
					printOut(jta, r + "-->" + filePath + " has been moved to:" + newFileName);
				}
			}
		}
	}

	private String getFileSuffix(File file) {
		String fileName = file.getName();
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}

	private void printOut(JTextArea jta, String output) {
		if (jta != null) {
			jta.append(output + "\n");
		} else {
			System.out.println(output);
		}
	}

}

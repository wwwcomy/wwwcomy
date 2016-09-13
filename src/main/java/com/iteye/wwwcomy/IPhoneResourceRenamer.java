package com.iteye.wwwcomy;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

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
		new IPhoneResourceRenamer().beginTask();
	}

	private void beginTask() throws Exception {
		File folder = new File(FOLDER_NAME);
		if (!folder.exists() || !folder.isDirectory()) {
			System.err.println("Folder not right!");
			return;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		File[] files = folder.listFiles();
		for (File file : files) {
			if (!file.isDirectory()) {
				System.out.println(file.getName());
				long lastModifyTime = file.lastModified();
				Date date = new Date(lastModifyTime);
				String sDate = sdf.format(date);
				if (file.getName().startsWith(sDate)) {
					System.err.println("The file has already been named by the modified date");
					continue;
				}
				String filePath = file.getCanonicalPath().substring(0, file.getCanonicalPath().lastIndexOf("\\"));
				String newFileName = filePath + "/" + sDate + "-" + file.getName();
				boolean r = file.renameTo(new File(newFileName));
				System.out.println(r + "-->" + filePath + " has been moved to:" + newFileName);
			}
		}

	}

}

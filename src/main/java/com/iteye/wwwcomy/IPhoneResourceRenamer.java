package com.iteye.wwwcomy;

import java.io.File;
import java.util.Date;

import com.iteye.wwwcomy.lxn.utils.DateUtil;

/**
 * iphone照片的文件名处理,把文件名之前加上修改时间/创建时间
 * 
 * @author wwwcomy
 *
 */
public class IPhoneResourceRenamer {

	public final static String FOLDER_NAME = "E:\\iphone视频20160521\\";

	public static void main(String[] args) throws Exception {
		new IPhoneResourceRenamer().beginTask();
	}

	private void beginTask() throws Exception {
		File folder = new File(FOLDER_NAME);
		if (!folder.exists() || !folder.isDirectory()) {
			System.err.println("Folder not right!");
			return;
		}
		File[] files = folder.listFiles();
		for (File file : files) {
			System.out.println(file.getName());
			long lastModifyTime = file.lastModified();
			Date date = new Date(lastModifyTime);
			String sDate = DateUtil.format(date, "YYYY-mm-DD");
			String filePath = file.getCanonicalPath().substring(0, file.getCanonicalPath().lastIndexOf("\\"));
			String newFileName = filePath + "/" + sDate + "-" + file.getName();
			boolean r = file.renameTo(new File(newFileName));
			System.out.println(r + "-->" + filePath + " has been moved to:" + newFileName);
		}

	}

}

package com.iteye.wwwcomy.ant;

import java.io.*;
import org.apache.tools.ant.*;

public class GetLatestFolderName extends Task {
	private String parentFolder;
	private String propertyName;

	 public static void main(String[] args) {
//	 GetLatestFolderName test = new GetLatestFolderName();
//	 this.propertyName="test";
//	 test.setParentFolder("\\\\pxy-pc/map_1_4_release/");
//	 test.execute();
		 File f = new File("E:/haiyan.exe.min.201105/WEB-INF/tableconfig_leedon/test");
		 for(File f1 : f.listFiles()){
			 System.out.println(f1.getName());
		 }
		 
	 }

	public void execute() throws BuildException {
		String folderName = "";
		long lastModTime = 0;
		File pFolder = new File(parentFolder);
		if (!pFolder.isDirectory()) {
			System.out.println("Directory '" + parentFolder
					+ "' is not a folder!");
			return;
		}
		for (File f : pFolder.listFiles()) {
			if (f.isDirectory()) {
				if (f.getName().toLowerCase().endsWith("-pass")
						&& f.lastModified() > lastModTime) {
					lastModTime = f.lastModified();
					folderName = f.getName();
				}
			}
		}
		getProject().setNewProperty(this.propertyName, folderName);
		System.out.println("latest folder is : " + folderName);
	}

	public void setProperty(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getParentFolder() {
		return this.parentFolder;
	}

	public void setParentFolder(String folder) {
		this.parentFolder = folder;
	}
}
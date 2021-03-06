import java.io.*;
import org.apache.tools.ant.*;

public class GetLatestFolderName2 extends Task {
	private String parentFolder;
	private String propertyName;

	// public static void main(String[] args) {
	// GetLatestFolderName test = new GetLatestFolderName();
	// this.propertyName="test";
	// test.setParentFolder("\\\\pxy-pc/map_1_4_release/");
	// test.execute();
	// }

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
				if (f.lastModified() > lastModTime) {
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
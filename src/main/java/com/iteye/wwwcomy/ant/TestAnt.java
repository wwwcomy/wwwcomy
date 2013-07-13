package com.iteye.wwwcomy.ant;

import java.io.File;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;

public class TestAnt {

	public static void main(String[] args) {
		testGenerateDB("10060", "D:/worke332/map3/WMS_MODEL.dmp", "B@B");
	}

	public static void testGenerateDB(String DSN, String configDmpPath,
			String adminCode) {
		File buildFile = new File("build.xml");
		Project p = new Project();

		DefaultLogger consoleLogger = new DefaultLogger();
		consoleLogger.setErrorPrintStream(System.err);
		consoleLogger.setOutputPrintStream(System.out);
		consoleLogger.setMessageOutputLevel(Project.MSG_INFO);
		p.addBuildListener(consoleLogger);

		try {
			p.fireBuildStarted();
			p.init();
			ProjectHelper helper = ProjectHelper.getProjectHelper();
			helper.parse(p, buildFile);
			p.setProperty("name", "BigBang =。=");

			p.setProperty("DSN", DSN);
			p.setProperty("newUser", DSN);
			p.setProperty("dmpFilePath", configDmpPath);
			p.setProperty("adminCode", adminCode);
			p.executeTarget(p.getDefaultTarget());
			p.fireBuildFinished(null);
		} catch (BuildException e) {
			p.fireBuildFinished(e);
		}
	}

}

package com.iteye.wwwcomy.hadoop;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class HdfsAPI {

	public static void main(String[] args) throws IOException {
//		put("input/words.txt", "/user/xuefeng/wordcount/input/words.txt");
//		get("output", "/user/xuefeng/wordcount/output");
		delete("/user/xuefeng/wordcount/output");
		
//		put("input/test.txt", "/user/xuefeng/test.txt");
//		get("output/test.txt", "/user/xuefeng/test.txt");
//		delete("/user/xuefeng/test.txt");
	}

	public static void putmerge(String localPath, String hdfsPath)
			throws IOException {
		Configuration conf = new Configuration();
		FileSystem localFS = FileSystem.getLocal(conf);
		FileSystem hdfs = FileSystem.get(conf);

		Path input = new Path(localPath);
		Path output = new Path(hdfsPath);

		FileStatus[] files = localFS.listStatus(input);
		FSDataOutputStream os = hdfs.create(output);
		byte[] buffer = new byte[1024];
		int len = -1;
		for (FileStatus f : files) {
			FSDataInputStream is = localFS.open(f.getPath());
			while ((len = is.read(buffer)) != -1) {
				os.write(buffer, 0, len);
			}
			is.close();
		}
		os.close();
	}

	public static boolean put(String localPath, String hdfsPath)
			throws IOException {
		Configuration conf = new Configuration();
		FileSystem hdfs = FileSystem.get(conf);

		Path src = new Path(localPath);
		Path dst = new Path(hdfsPath);
		hdfs.copyFromLocalFile(src, dst);

		hdfs.close();

		return true;
	}

	public static boolean get(String localPath, String hdfsPath)
			throws IOException {
		Configuration conf = new Configuration();
		FileSystem hdfs = FileSystem.get(conf);

		Path dst = new Path(localPath);
		Path src = new Path(hdfsPath);
		hdfs.copyToLocalFile(src, dst);

		hdfs.close();

		return true;
	}

	public static boolean delete(String hdfsPath) throws IOException {
		Configuration conf = new Configuration();
		FileSystem hdfs = FileSystem.get(conf);

		Path path = new Path(hdfsPath);

		boolean deleted = hdfs.delete(path, true);

		hdfs.close();

		return deleted;
	}

}

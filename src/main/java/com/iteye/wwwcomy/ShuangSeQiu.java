package com.iteye.wwwcomy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * 娱乐一下双色球
 *
 */
public class ShuangSeQiu {
	public static void main(String[] args) throws Exception {
		new ShuangSeQiu().prepareData();
	}

	private List<List<String>> history = new ArrayList<List<String>>();

	private void prepareData() throws Exception {
		String filePath = this.getClass().getClassLoader().getResource("").toString().substring(6) + "2.txt";
		System.out.println(filePath);
		File f = new File(filePath);
		BufferedReader br = new BufferedReader(new FileReader(f));
		String line;
		while ((line = br.readLine()) != null) {
			String[] splits = line.split("\\t");
			List<String> tmp = Arrays.asList(splits);
			history.add(tmp);
		}
		br.close();
	}

	private List<String> getRandomList() {
		List<String> result = new ArrayList<String>();
		result.addAll(getRandom(33, 6));
		result.addAll(getRandom(16, 1));
		return result;
	}

	/**
	 * 获取0到i之间,j个不重复的随机数
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	private List<String> getRandom(int i, int j) {
		List<String> result = new ArrayList<String>();
		return null;
	}

}

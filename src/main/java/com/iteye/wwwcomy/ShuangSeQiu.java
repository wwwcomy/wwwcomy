package com.iteye.wwwcomy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 
 * 娱乐一下双色球
 *
 */
public class ShuangSeQiu {
	public static void main(String[] args) throws Exception {
		new ShuangSeQiu().start();
	}

	private List<List<String>> history = new ArrayList<List<String>>();

	public void start() throws Exception {
		prepareData();
		long outerCounter = 0L;
		int counter = 0;
		while (true) {
			for (List<String> list : history) {
				List<String> randomList = getRandomList();
				outerCounter++;
				if (outerCounter % 1000000 == 0) {
					System.out.println("第" + outerCounter + "次随机数:" + randomList);
				}
				// if (outerCounter % 10000000 == 0) {
				// randomList = new ArrayList<String>();
				// for (String s : list) {
				// randomList.add(s);
				// }
				// System.out.println("第" + outerCounter + "次随机数:" +
				// randomList);
				// }
				if (!equalsIgnoreOrder(randomList, list)) {
					if (counter > 2) {
						System.out.println("第" + counter + "次匹配失败,比对结果如下,重置ing");
						System.out.println("随机数字:" + randomList);
						System.out.println("第" + counter + "期的结果:" + list);
					}
					counter = 0;
					break;
				} else {
					System.err.println("匹配成功!");
				}
				counter++;
			}
			if (counter == 0) {
				continue;
			}
			break;
		}
		System.out.println("第" + counter + "次匹配成功,输出结果!");
		List<String> result = new ArrayList<String>();
		result.addAll(getRandom(33, 6));
		result.addAll(getRandom(16, 1));
		System.out.println(result);
	}

	private void prepareData() throws Exception {
		String filePath = this.getClass().getClassLoader().getResource("").toString().substring(6) + "1.txt";
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
	private List<String> getRandom(int range, int j) {
		List<Integer> input = new ArrayList<Integer>();
		for (int i = 0; i < range; i++) {
			input.add(i);
		}

		List<String> output = new ArrayList<String>();

		int end = range - 1;
		Random random = new Random();
		for (int i = 0; i < j; i++) {
			int num = random.nextInt(end);
			output.add(String.valueOf(input.get(num) + 1));
			if (num != end) {
				input.set(num, input.get(end));
			}
			end--;
		}

		return output;
	}

	private boolean equalsIgnoreOrder(List<String> list1, List<String> list2) {
		if (list1.size() != list2.size())
			return false;
		for (String s : list1) {
			if (!list2.contains(s)) {
				return false;
			}
		}
		return true;
	}

}

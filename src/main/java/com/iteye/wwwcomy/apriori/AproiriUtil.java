package com.iteye.wwwcomy.apriori;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.collections.CollectionUtils;

/**
 * @author Liuxn
 * 
 */
public class AproiriUtil {
	/**
	 * 统计data中item的次数 1st Step
	 * 
	 * @param item
	 * @param data
	 * @param result
	 */
	public static HashMap<HashSet<String>, Integer> stat(
			Set<HashSet<String>> itemData, String[][] tranData) {
		HashMap<HashSet<String>, Integer> result = new HashMap<HashSet<String>, Integer>();
		for (HashSet<String> item : itemData) {
			result.put(item, 0);
		}
		for (int i = 0; i < tranData.length; i++) {
			HashSet<String> h = new HashSet<String>(Arrays.asList(tranData[i]));
			Iterator<HashSet<String>> keyI = result.keySet().iterator();
			while (keyI.hasNext()) {
				HashSet<String> next = keyI.next();
				if (CollectionUtils.intersection(next, h).size() == next.size()) {
					result.put(next, result.get(next) + 1);
					continue;
				}
			}
		}
		return result;
	}

	/**
	 * 按照支持度筛选数据 2nd Step
	 * 
	 * 产生频繁项集
	 * 
	 * @param data
	 * @param support
	 * @param tranDataSize
	 */
	public static HashMap<HashSet<String>, Integer> dataSelection(
			HashMap<HashSet<String>, Integer> data, double support,
			int tranDataSize) {
		Iterator<HashSet<String>> i = data.keySet().iterator();
		while (i.hasNext()) {
			HashSet<String> key = i.next();
			if ((data.get(key).doubleValue() / tranDataSize) < support)
				i.remove();
		}
		return data;
	}

	/**
	 * 由频繁项集生成下一次的候选项集
	 * 
	 * @param lastResult
	 * @param time
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static HashMap<HashSet<String>, Integer> calcNextCandidateData(
			HashMap<HashSet<String>, Integer> lastResult, int time) {
		HashMap<HashSet<String>, Integer> result = new HashMap<HashSet<String>, Integer>();
		Set<HashSet<String>> s = lastResult.keySet();
		Set<HashSet<String>> tmpSet = new HashSet<HashSet<String>>();

		Iterator<HashSet<String>> i = s.iterator();
		while (i.hasNext()) {
			HashSet<String> a = i.next();
			Iterator<HashSet<String>> j = s.iterator();
			while (j.hasNext()) {
				HashSet<String> inner = j.next();
				HashSet<String> tAll = new HashSet<String>(
						CollectionUtils.union(a, inner));
				HashSet<String> yihuo = new HashSet<String>(
						CollectionUtils.disjunction(a, inner));
				if (tAll.size() == time
						&& (tAll.size() == 2 || lastResult.containsKey(yihuo))) {
					result.put(tAll, 0);
					tmpSet.add(tAll);
				}
			}
			i.remove();
		}
		return result;
	}

	/**
	 * 求出从0到集合子集数目（这里为16）之间的数的二进制形式，存放在数组result中
	 * 
	 * @param set
	 * @return
	 */
	public static String[] getBinaryValue(Set<String> set) {
		int size = set.size();
		int m = (int) Math.pow(2, size) - 1;
		String[] result = new String[m + 1];
		for (int i = m; i > -1; i--) {
			StringBuffer sb = new StringBuffer(Integer.toBinaryString(i));
			int length = sb.length();
			if (length < size) {
				for (int j = 0; j < size - length; j++) {
					sb.insert(0, "0");
				}
			}
			result[i] = sb.toString();
		}
		return result;
	}

	/**
	 * 根据二进制字符串生成子集
	 * 
	 * @param set
	 * @return
	 */
	public static ArrayList<Set<String>> getSubset(Set<String> set) {
		ArrayList<Set<String>> result = new ArrayList<Set<String>>();
		// 把集合元素放入数组中，方便存取
		String[] items = new String[set.size()];
		int i = 0;
		for (String item : set) {
			items[i++] = item;
		}
		// 调用二进制字符串生成函数
		String[] binaryValue = getBinaryValue(set);
		// 根据二进制字符串取集合元素构成子集
		for (int j = 0; j < binaryValue.length; j++) {
			String value = binaryValue[j];
			TreeSet<String> subset = new TreeSet<String>();
			for (int k = 0; k < value.length(); k++) {
				if (value.charAt(k) == '1')
					subset.add(items[k]);
			}
			result.add(subset);
		}
		return result;
	}
}

package com.iteye.wwwcomy.apriori;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.collections.CollectionUtils;

/**
 * @author Liuxn
 * 
 */
public class MyAproiri {

	/**
	 * 事务数据
	 */
	private String[][] tranData;

	/**
	 * 数据总量
	 */
	private int tranDataSize = 0;

	/**
	 * 原始数据项,事务数据tranData应当由itemData构成
	 */
	private Set<HashSet<String>> itemData;

	/**
	 * 支持度
	 */
	private double support;

	/**
	 * 置信度
	 */
	private double confidence;

	//private ArrayList<HashMap<HashSet<String>, Integer>> candidateTmp = new ArrayList<HashMap<HashSet<String>, Integer>>();

	/**
	 * 记录次数
	 */
	private HashMap<HashSet<String>, Integer> countTmp;

	private ArrayList<HashMap<HashSet<String>, Integer>> frequentTmp = new ArrayList<HashMap<HashSet<String>, Integer>>();

	// 最大频繁项集
	HashMap<HashSet<String>, Integer> max = new HashMap<HashSet<String>, Integer>();

	TreeMap<String, Integer> candidateSet = new TreeMap<String, Integer>();

	public MyAproiri(String[][] data) {
		this(data, 0.08, 0.6);
	}

	public MyAproiri(String[][] data, double s, double c) {
		this(data, calcItemData(data), s, c);
	}

	public MyAproiri(String[][] data, Set<HashSet<String>> itemData, double s,
			double c) {
		if (s > 1)
			s = 1.0;
		if (c > 1)
			c = 1.0;
		this.tranData = data;
		tranDataSize = data.length;
		// for (String[] a : data) {
		// tranDataSize += a.length;
		// }
		this.itemData = itemData;
		this.support = s;
		this.confidence = c;
	}

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		// String[][] data = new String[][] { { "1", "2", "5" }, { "2", "4" },
		// { "2", "3" }, { "1", "2", "4" }, { "1", "3" }, { "2", "3" },
		// { "1", "3" }, { "1", "2", "3", "5" }, { "1", "2", "3" } };
		String[][] data = new String[][] { { "k", "a", "d", "b" },
				{ "d", "a", "c", "e", "b" }, { "c", "a", "b", "e" },
				{ "b", "a", "d" } };
		MyAproiri myAproiri = new MyAproiri(data,0.6,0.8);
		System.out.println(myAproiri.getItemData());
		System.out.println(myAproiri.getTranDataSize());
		// System.out.println("****Step1统计:****");
		// HashMap<HashSet<String>, Integer> result = AproiriUtil.stat(
		// myAproiri.getItemData(), myAproiri.getTranData());
		// System.out.println(result);
		// System.out.println("****Step2筛选:****");
		// HashMap<HashSet<String>, Integer> result1 =
		// AproiriUtil.dataSelection(
		// result, 0.08, myAproiri.getTranDataSize());
		// System.out.println(result1);
		// System.out.println("****Step3连接:****");
		// HashMap<HashSet<String>, Integer> result2 = AproiriUtil
		// .calcNextCandidateData(result1, 2);
		// System.out.println(result2);
		// System.out.println("****Step4:****");
		// HashMap<HashSet<String>, Integer> result3 = AproiriUtil.stat(
		// result2.keySet(), myAproiri.getTranData());
		// System.out.println(result3);
		// System.out.println("****Step5:****");
		// HashMap<HashSet<String>, Integer> result4 =
		// AproiriUtil.dataSelection(
		// result3, 0.08, myAproiri.getTranDataSize());
		// System.out.println(result4);
		// System.out.println("****Step6:****");
		// HashMap<HashSet<String>, Integer> result5 = AproiriUtil
		// .calcNextCandidateData(result4, 3);
		// System.out.println(result5);
		// System.out.println("****Step7:****");
		// HashMap<HashSet<String>, Integer> result6 = AproiriUtil.stat(
		// result5.keySet(), myAproiri.getTranData());
		// System.out.println(result6);
		// System.out.println("****Step8:****");
		// HashMap<HashSet<String>, Integer> result7 =
		// AproiriUtil.dataSelection(
		// result6, 0.08, myAproiri.getTranDataSize());
		// System.out.println(result7);
		// System.out.println("****Step9:****");
		// HashMap<HashSet<String>, Integer> result8 = AproiriUtil
		// .calcNextCandidateData(result7, 3);
		// System.out.println(result8);
		myAproiri.caculate();
		long end = System.currentTimeMillis();
		System.out.println("共耗时: " + (end - start) + "ms");
		System.out.println("***频繁项集统计***");
		for (int i = 1; i < myAproiri.frequentTmp.size(); i++) {
			System.out.println("第" + i + "频繁项集:");
			System.out.println(myAproiri.frequentTmp.get(i));
		}
		System.out.println("***最大频繁项集***");
		System.out.println(myAproiri.max);
		System.out.println("***统计项集***");
		System.out.println(myAproiri.countTmp);

		System.out.println("***结论***");
		myAproiri.genRules();
	}

	/**
	 * 统计入口
	 */
	public void caculate() {
		HashMap<HashSet<String>, Integer> tmp = new HashMap<HashSet<String>, Integer>();
		tmp = AproiriUtil.stat(itemData, tranData);
		tmp = AproiriUtil.dataSelection(tmp, support, getTranDataSize());
		countTmp = new HashMap<HashSet<String>, Integer>(tmp);
		while (tmp.size() > 0) {
			frequentTmp.add(new HashMap<HashSet<String>, Integer>(tmp));
			tmp = AproiriUtil
					.calcNextCandidateData(tmp, frequentTmp.size() + 1);
			tmp = AproiriUtil.stat(tmp.keySet(), tranData);
			tmp = AproiriUtil.dataSelection(tmp, support, getTranDataSize());
			countTmp.putAll(tmp);
		}
		// 生成最大频繁项集
		genMaxFrequent();
	}

	/**
	 * 根据最大频繁项集生成规则
	 */
	private void genRules() {
		Iterator<HashSet<String>> i = max.keySet().iterator();
		while (i.hasNext()) {
			HashSet<String> key = i.next();
			genRule(key);
		}

	}

	@SuppressWarnings("unchecked")
	private void genRule(HashSet<String> key) {
		ArrayList<Set<String>> subSets = new ArrayList<Set<String>>(
				AproiriUtil.getSubset(key));
		Iterator<Set<String>> i = subSets.iterator();
		while (i.hasNext()) {
			Set<String> set = i.next();
			if (set.size() != 0 && set.size() != key.size()) {
				HashSet<String> _set = new HashSet<String>(
						CollectionUtils.subtract(key, set));
				if (countTmp.get(key)
						/ Double.valueOf((countTmp.get(set).toString())) >= confidence) {
					System.out.println("集合" + set + " => " + _set);
				}
			}
		}
	}

	/**
	 * 生成最大频繁项集
	 */
	private HashMap<HashSet<String>, Integer> genMaxFrequent() {
		HashMap<HashSet<String>, Integer> tmpMax = new HashMap<HashSet<String>, Integer>();
		for (int i = frequentTmp.size(); i > 0; i--) {
			if (i == frequentTmp.size()) {
				max.putAll(frequentTmp.get(i - 1));
			} else if (i > 1) {
				HashMap<HashSet<String>, Integer> item = frequentTmp.get(i - 1);
				Iterator<HashSet<String>> it = item.keySet().iterator();
				while (it.hasNext()) {
					HashSet<String> key = it.next();
					boolean has = true;
					Iterator<HashSet<String>> it2 = max.keySet().iterator();
					while (it2.hasNext()) {
						HashSet<String> key2 = it2.next();
						if (CollectionUtils.union(key, key2).size() == key2
								.size()) {
							has = false;
							break;
						}
					}
					if (has)
						tmpMax.put(key, item.get(key));
				}
				max.putAll(tmpMax);
			}
		}
		return max;
	}

	/**
	 * 根据输入的事务数据生成原始数据项
	 * 
	 * @param tranData
	 * @return
	 */
	public static Set<HashSet<String>> calcItemData(String[][] tranData) {
		HashSet<String> tmp = new HashSet<String>();
		Set<HashSet<String>> result = new HashSet<HashSet<String>>();
		for (String a[] : tranData) {
			for (String c : a)
				tmp.add(String.valueOf(c));
		}
		Iterator<String> i = tmp.iterator();
		while (i.hasNext()) {
			HashSet<String> h = new HashSet<String>();
			h.add(i.next());
			result.add(h);
		}
		return result;
	}

	/**
	 * 统计出现次数
	 * 
	 * @param tranData
	 * @return
	 */
	public static HashSet<String> gatherItemTimes(String[][] tranData,
			Set<HashSet<String>> keys) {
		HashSet<String> result = new HashSet<String>();
		for (String a[] : tranData) {
			for (String c : a)
				result.add(String.valueOf(c));
		}
		return result;
	}

	public String[][] getTranData() {
		return tranData;
	}

	public void setTranData(String[][] tranData) {
		this.tranData = tranData;
	}

	public double getSupport() {
		return support;
	}

	public void setSupport(double support) {
		this.support = support;
	}

	public double getConfidence() {
		return confidence;
	}

	public void setConfidence(double confidence) {
		this.confidence = confidence;
	}

	public void setItemData(Set<HashSet<String>> itemData) {
		this.itemData = itemData;
	}

	public Set<HashSet<String>> getItemData() {
		return itemData;
	}

	public int getTranDataSize() {
		return tranDataSize;
	}
}

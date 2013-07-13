package com.iteye.wwwcomy.apriori;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
public class TheirApriori {
	double minsup = 0.6;// 最小支持度
	double minconf = 0.8;// 最小置信度
	int item_counts = 0;// 候选1项目集大小,即字母的个数
	int frequent_top;
	HashMap rule = new HashMap();
	// String trans_set[] = { "abc", "abc", "acde", "bcdf", "abcd", "abcdf" };//
	// 事务集

	String trans_set[] = { "kadb", "daceb", "cabe", "bad" };

	// String trans_set[] = { "125", "24", "23", "124", "13", "23", "13",
	// "1235",
	// "123" };// 事务集
	TreeSet[] frequent_set = new TreeSet[10];// 频繁项集数组，[0]:代表1频繁集...
	TreeSet max_frequent = new TreeSet();// 最大频繁集
	TreeSet item1_canditate = new TreeSet();// 1候选集
	TreeSet canditate_set[] = new TreeSet[10];// 候选集数组

	public TheirApriori() {
		max_frequent = new TreeSet();
		item_counts = counts();
		// frequent_set[]=new TreeSet[3];
		// frequent_set[]=new TreeSet[item_counts];
		for (int i = 0; i < item_counts; i++) {
			frequent_set[i] = new TreeSet();
			canditate_set[i] = new TreeSet();
		}
		// System.out.println(frequent_set[0].size());
		canditate_set[0] = item1_canditate;
	}

	public static void main(String[] args) {
		TheirApriori x = new TheirApriori();
		x.run();
	}

	// 计算1候选集个数
	int counts() {
		String temp1 = new String("a");
		char temp2 = 'a';
		int i, j, m;
		for (i = 0; i < trans_set.length; i++) {
			temp1 = trans_set[i];
			// System.out.println(temp1);
			// System.out.println(temp1.length());
			// m=temp1.length();
			// System.out.println(m);
			for (j = 0; j < temp1.length(); j++) {
				// System.out.println(j);
				temp2 = temp1.charAt(j);
				// System.out.println(i);
				// System.out.println(j);
				// System.out.println(temp2);
				item1_canditate.add(String.valueOf(temp2));
			}
		}
		return item1_canditate.size();
	}

	// 计算项目集支持度
	double count_sup(String x) {
		int temp = 0;
		for (int i = 0; i < trans_set.length; i++) {
			for (int j = 0; j < x.length(); j++) {
				if (trans_set[i].indexOf(x.charAt(j)) == -1)
					break;
				else if (j == (x.length() - 1))
					temp++;
			}
		}
		return temp;
	}

	// 构造1候选集合＆频繁集
	public void item1_gen() {
		String temp1 = "";
		double m = 0;
		Iterator temp = canditate_set[0].iterator();
		while (temp.hasNext()) {
			temp1 = (String) temp.next();
			m = count_sup(temp1);
			if (m >= minsup * trans_set.length) {
				frequent_set[0].add(temp1);
			}
		}
	}

	// 由k频繁集和1频繁集生成k+1候选集
	public void canditate_gen(int k) {
		String y = "", z = "", m = "";
		char c1 = 'a', c2 = 'a';
		Iterator temp1 = frequent_set[k - 2].iterator();
		Iterator temp2 = frequent_set[0].iterator();
		TreeSet h = new TreeSet();
		while (temp1.hasNext()) {
			y = (String) temp1.next();
			c1 = y.charAt(y.length() - 1);
			// System.out.println("y="+y);
			while (temp2.hasNext()) {
				z = (String) temp2.next();
				// System.out.println("z="+z);
				c2 = z.charAt(0);

				if (c1 >= c2)
					continue;
				else {
					m = y + z;
					h.add(m);
				}
			}
			temp2 = frequent_set[0].iterator();
		}
		canditate_set[k - 1] = h;
	}

	// k候选集=>k频繁集
	public void frequent_gen(int k) {
		String s1 = "";
		Iterator ix = canditate_set[k - 1].iterator();
		while (ix.hasNext()) {
			s1 = (String) ix.next();
			if (count_sup(s1) >= (minsup * trans_set.length)) {
				frequent_set[k - 1].add(s1);
			}
		}
	}

	public boolean is_frequent_empty(int k) {
		if (frequent_set[k - 1].isEmpty())
			return true;
		else
			return false;
	}

	public boolean included(String s1, String s2) {
		for (int i = 0; i < s1.length(); i++) {
			if (s2.indexOf(s1.charAt(i)) == -1)
				return false;
			else if (i == s1.length() - 1)
				return true;
		}
		return true;
	}

	// 该类函数复杂度过高，有待改进算法！！
	public void maxfrequent_gen() {
		int i, j;
		Iterator iterator, iterator1, iterator2;
		String temp = "", temp1 = "", temp2 = "";
		for (i = 0; i < frequent_top; i++) {
			max_frequent.addAll(frequent_set[i]);
		}
		for (i = 0; i < frequent_top; i++) {
			iterator1 = frequent_set[i].iterator();
			while (iterator1.hasNext()) {
				temp1 = (String) iterator1.next();
				for (j = i + 1; j < frequent_top; j++) {
					// System.out.println("i="+i+"\tj="+j);
					// System.out.println(s[j+1]);
					iterator2 = frequent_set[j].iterator();
					while (iterator2.hasNext()) {
						temp2 = (String) iterator2.next();
						if (included(temp1, temp2))
							max_frequent.remove(temp1);
					}
				}
			}
		}
	}

	public void print_maxfrequent() {
		Iterator iterator = max_frequent.iterator();
		System.out.print("最大频繁项集：");
		while (iterator.hasNext()) {
			System.out.print((String) iterator.next() + "\t");
		}
		System.out.println();
	}

	public void subGen(String s) {
		String x = "", y = "";
		for (int i = 1; i < (1 << s.length()) - 1; i++) {
			for (int j = 0; j < s.length(); j++) {
				if (((1 << j) & i) != 0) {
					// System.out.print(s.charAt(j));
					x += s.charAt(j);
				}
			}
			// System.out.print("\t");
			for (int j = 0; j < s.length(); j++) {
				if (((1 << j) & (~i)) != 0) {
					// System.out.print(s.charAt(j));
					y += s.charAt(j);
					// System.out.println(x.trim()+"\t"+y.trim());
				}
			}
			// System.out.println(x+"\t"+y);
			if (count_sup(x + y) / count_sup(x) >= minconf) {
				rule.put(x, y);
			}
			x = "";
			y = "";

		}
	}

	public void ruleGen() {
		String s;
		Iterator iterator = max_frequent.iterator();
		while (iterator.hasNext()) {
			s = (String) iterator.next();
			subGen(s);
		}
	}

	public void rulePrint() {
		String x, y;
		double temp = 0;
		Set hs = rule.keySet();
		Iterator iterator = hs.iterator();
		System.out.println("关联规则：");
		while (iterator.hasNext()) {
			x = (String) iterator.next();
			y = (String) rule.get(x);
			temp = (count_sup(x + y) / count_sup(x));
			System.out.println(x + "==>" + y + "\t" + temp);
		}
	}

	public void run() {
		int k = 1;
		// myapriori run=new myapriori();
		item1_gen();
		do {
			k++;
			canditate_gen(k);
			frequent_gen(k);
		} while (!is_frequent_empty(k));
		frequent_top = k - 1;
		print_canditate();
		maxfrequent_gen();
		print_maxfrequent();
		ruleGen();
		rulePrint();

	}

	// for test
	void print1() {
		Iterator temp = canditate_set[0].iterator();
		while (temp.hasNext())
			System.out.println(temp.next());
	}

	// for test
	void print2() {
		Iterator temp = frequent_set[0].iterator();
		while (temp.hasNext())
			System.out.println((String) temp.next());
	}

	// for test
	void print3() {
		canditate_gen(1);
		frequent_gen(2);
		Iterator temp = canditate_set[1].iterator();
		Iterator temp1 = frequent_set[1].iterator();
		while (temp.hasNext())
			System.out.println("候选" + (String) temp.next());
		while (temp1.hasNext())
			System.out.println("频繁" + (String) temp1.next());
	}

	void print_canditate() {

		for (int i = 0; i < frequent_set[0].size(); i++) {
			Iterator ix = canditate_set[i].iterator();
			Iterator iy = frequent_set[i].iterator();
			System.out.print("候选集" + (i + 1) + ":");
			while (ix.hasNext()) {
				System.out.print((String) ix.next() + "\t");
			}
			System.out.print("\n" + "频繁集" + (i + 1) + ":");
			while (iy.hasNext()) {
				System.out.print((String) iy.next() + "\t");
			}
			System.out.println();
		}
	}

	/*
	 * //main function for test public static void main(String [] args){
	 * myapriori run=new myapriori(); run.item1_gen(); run.print2();
	 * run.print3(); }
	 */

}
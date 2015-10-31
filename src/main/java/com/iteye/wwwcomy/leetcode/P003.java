package com.iteye.wwwcomy.leetcode;

import java.util.HashMap;

public class P003 {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int lengthOfLongestSubstring(String s) {
		HashMap tmpMap = new HashMap();
		HashMap longestMap = new HashMap();
		char[] chars = s.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (tmpMap.get(chars[i]) != null) {
				if (longestMap.size() < tmpMap.size()) {
					// longestMap.clear();
					longestMap = new HashMap(tmpMap);
					int restartIdx = (int) tmpMap.get(chars[i]);
					tmpMap.clear();
					for (int j = restartIdx + 1; j <= i; j++) {
						tmpMap.put(chars[j], j);
					}
				} else {
					// restartIdx must less than i
					int restartIdx = (int) tmpMap.get(chars[i]);
					tmpMap.clear();
					for (int j = restartIdx + 1; j <= i; j++) {
						tmpMap.put(chars[j], j);
					}
				}
			} else {
				tmpMap.put(chars[i], i);
			}
		}
		if (longestMap.size() < tmpMap.size()) {
			// longestMap.clear();
			longestMap = new HashMap(tmpMap);
		}
		return longestMap.size();
	}
}

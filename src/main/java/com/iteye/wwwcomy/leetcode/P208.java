package com.iteye.wwwcomy.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * 前缀树
 * 
 * @author liuxingn
 *
 */
public class P208 {
	public static void main(String[] args) {
		Trie trie = new Trie();
		trie.insert("some");
		System.out.println(trie.search("abc"));
		System.out.println(trie.search("ab"));
		// System.out.println(trie.search("some"));
		// System.out.println(trie.startsWith("som"));
		trie.insert("ab");
		System.out.println(trie.search("ab"));
	}
}

class TrieNode {
	char content;
	Map<Character, TrieNode> childs;
	boolean isWord;

	// Initialize your data structure here.
	public TrieNode(char content) {
		this.content = content;
		childs = new HashMap<Character, TrieNode>();
		isWord = false;
	}
}

class Trie {
	private TrieNode root;

	public Trie() {
		root = new TrieNode((char) 32);
	}

	// Inserts a word into the trie.
	public void insert(String word) {
		char[] chars = word.toCharArray();
		TrieNode current = root;
		for (int i = 0; i < chars.length; i++) {
			TrieNode tmp = current.childs.get(chars[i]);
			if (tmp == null) {
				tmp = new TrieNode(chars[i]);
				current.childs.put(chars[i], tmp);
			}
			current = current.childs.get(chars[i]);
		}
		current.isWord = true;
	}

	// Returns if the word is in the trie.
	public boolean search(String word) {
		char[] chars = word.toCharArray();
		TrieNode current = root;
		for (int i = 0; i < chars.length; i++) {
			current = current.childs.get(chars[i]);
			if (current == null) {
				return false;
			}
		}
		if (!current.isWord) {
			return false;
		}
		return true;
	}

	// Returns if there is any word in the trie
	// that starts with the given prefix.
	public boolean startsWith(String prefix) {
		char[] chars = prefix.toCharArray();
		TrieNode current = root;
		for (int i = 0; i < chars.length; i++) {
			current = current.childs.get(chars[i]);
			if (current == null) {
				return false;
			}
		}
		return true;
	}
}
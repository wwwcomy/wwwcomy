package com.iteye.wwwcomy.datastructure;

/**
 * code snippet for binary tree..,
 * 
 * @author wwwcomy
 *
 */
public class BinaryTreeNode {
	Object value;
	BinaryTreeNode parent;
	BinaryTreeNode left;
	BinaryTreeNode right;

	BinaryTreeNode(Object o) {
		this.value = o;
	}

	void setLeft(BinaryTreeNode n) {
		this.left = n;
	}

	void setRight(BinaryTreeNode n) {
		this.right = n;
	}

	void setAsLeftChild(BinaryTreeNode n) {
		this.parent = n;
		n.setLeft(this);
	}

	void setAsRightChild(BinaryTreeNode n) {
		this.parent = n;
		n.setRight(this);
	}

	BinaryTreeNode getLeft() {
		return left;
	}

	BinaryTreeNode getRight() {
		return right;
	}

	Object getValue() {
		return value;
	}

	/**
	 * 先序遍历
	 * 
	 * @return 先序遍历结果
	 */
	String preOrderTraversal() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.value.toString());
		traversalAppendLeft(this.getLeft(), sb);
		traversalAppendRight(this.getRight(), sb);
		return sb.toString();
	}

	private void traversalAppendLeft(BinaryTreeNode binaryTreeNode, StringBuilder sb) {
		if (binaryTreeNode == null)
			return;
		sb.append(binaryTreeNode.preOrderTraversal());
	}

	private void traversalAppendRight(BinaryTreeNode binaryTreeNode, StringBuilder sb) {
		if (binaryTreeNode == null)
			return;
		sb.append(binaryTreeNode.preOrderTraversal());
	}

	public static void main(String[] args) {
		BinaryTreeNode A = new BinaryTreeNode("A");
		BinaryTreeNode B = new BinaryTreeNode("B");
		BinaryTreeNode C = new BinaryTreeNode("C");
		BinaryTreeNode D = new BinaryTreeNode("D");
		BinaryTreeNode E = new BinaryTreeNode("E");
		BinaryTreeNode F = new BinaryTreeNode("F");
		BinaryTreeNode G = new BinaryTreeNode("G");
		BinaryTreeNode H = new BinaryTreeNode("H");
		BinaryTreeNode I = new BinaryTreeNode("I");
		BinaryTreeNode J = new BinaryTreeNode("J");
		BinaryTreeNode K = new BinaryTreeNode("K");
		A.setLeft(B);
		A.setRight(C);
		B.setLeft(D);
		B.setRight(E);
		D.setLeft(G);
		D.setRight(H);
		C.setLeft(K);
		C.setRight(F);
		F.setLeft(I);
		I.setRight(J);
		System.out.println(A.preOrderTraversal());
	}
}

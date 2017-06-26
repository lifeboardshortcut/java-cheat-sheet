package binarySearchTree;

import java.util.ArrayList;
import java.util.List;

/*
 * Partial implementation of Binary Search Tree
 * See implementation by Sedgewick and Wayne for more details
 * http://algs4.cs.princeton.edu/32bst/BST.java.html
 */

public class BST<T extends Comparable<T>> {
	private BSTNode<T> root;
	private Integer size;

	@SuppressWarnings("hiding")
	private class BSTNode<T extends Comparable<T>> {
		private T value;
		private BSTNode<T> left;
		private BSTNode<T> right;

		public BSTNode(T value) {
			this.value = value;
		}
	}

	public BST() {
		this.root = null;
	}

	public BST(BSTNode<T> root) {
		this.root = root;
	}

	public BSTNode<T> insert(T value) {
		return insert(value, root);
	}

	private BSTNode<T> insert(T value, BSTNode<T> root) {
		if (this.root == null) {
			return this.root = new BSTNode<T>(value);
		} else if (root == null) {
			root = new BSTNode<T>(value);
		} else {
			int compare = value.compareTo(root.value);
			if (compare < 0) {
				root.left = insert(value, root.left);
			} else {
				root.right = insert(value, root.right);
			}
		}
		return root;
	}

	public void printPostOrder() {
		printPostOrder(root);
	}

	private void printPostOrder(BSTNode<T> root) {
		if (root == null)
			return;
		if (root.left != null)
			printPostOrder(root.left);
		if (root.right != null)
			printPostOrder(root.right);
		System.out.println(root.value);
	}

	public void printInOrder() {
		printInOrder(root);
	}

	private void printInOrder(BSTNode<T> root) {
		if (root == null)
			return;
		if (root.left != null)
			printInOrder(root.left);
		System.out.println(root.value);
		if (root.right != null)
			printInOrder(root.right);
	}

	public void printPreOrder() {
		printPreOrder(root);
	}

	public void printPreOrder(BSTNode<T> root) {
		if (root == null)
			return;
		System.out.println(root.value);
		if (root.left != null)
			printPreOrder(root.left);
		if (root.right != null)
			printPreOrder(root.right);
	}
	
	public List<T> traverseInOrder() {
		List<T> list = new ArrayList<T>();
		return traverseInOrder(root, list);
	}

	private List<T> traverseInOrder(BSTNode<T> root, List<T> list) {
		if (root == null)
			return null;
		if (root.left != null)
			traverseInOrder(root.left, list);
		list.add(root.value);
		if (root.right != null)
			traverseInOrder(root.right, list);
		return list;
	}

	public int height() {
		return height(root);
	}

	private int height(BSTNode<T> root) { // root is height 0
		if (root == null)
			return -1;
		return 1 + Math.max(height(root.left), height(root.right));
	}

	public BSTNode<T> getRoot() {
		return root;
	}

	public void setRoot(BSTNode<T> root) {
		this.root = root;
	}
	
	public Integer size(){
		return this.size;
	}
}

package structures;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class BinarySearchTree<T extends Comparable<T>> implements BSTInterface<T> {
	protected BSTNode<T> root;

	public boolean isEmpty() {
		return root == null;
	}

	public int size() {
		return subtreeSize(root);
	}

	private int subtreeSize(BSTNode<T> node) {
		if (node == null) {
			return 0;
		} else {
			return 1 + subtreeSize(node.getLeft())
					+ subtreeSize(node.getRight());
		}
	}

	public boolean contains(T t) {
		return containsHelper(t, root);
	}
	
	private boolean containsHelper(T element, BSTNode<T> tree) {
		 // Returns true if tree contains an element e such that
		 // e.compareTo(element) == 0; otherwise, returns false.
		 if (tree == null)
			 return false; // element is not found
		 else if (element.compareTo(tree.getData()) < 0)
			 return containsHelper(element, tree.getLeft()); // Search left subtree
		 else if (element.compareTo(tree.getData()) > 0)
			 return containsHelper(element, tree.getRight()); // Search right subtree
		 else
			 return true; // element is found
	 }

	public boolean remove(T t) {
		boolean result = contains(t);
		if (result) {
			root = removeFromSubtree(root, t);
		}
		return result;
	}

	private BSTNode<T> removeFromSubtree(BSTNode<T> node, T t) {
		// node must not be null
		int result = t.compareTo(node.getData());
		if (result < 0) {
			node.setLeft(removeFromSubtree(node.getLeft(), t));
			return node;
		} else if (result > 0) {
			node.setRight(removeFromSubtree(node.getRight(), t));
			return node;
		} else { // result == 0
			if (node.getLeft() == null) {
				return node.getRight();
			} else if (node.getRight() == null) {
				return node.getLeft();
			} else { // neither child is null
				T predecessorValue = getHighestValue(node.getLeft());
				node.setLeft(removeRightmost(node.getLeft()));
				node.setData(predecessorValue);
				return node;
			}
		}
	}

	private T getHighestValue(BSTNode<T> node) {
		// node must not be null
		if (node.getRight() == null) {
			return node.getData();
		} else {
			return getHighestValue(node.getRight());
		}
	}

	private BSTNode<T> removeRightmost(BSTNode<T> node) {
		// node must not be null
		if (node.getRight() == null) {
			return node.getLeft();
		} else {
			node.setRight(removeRightmost(node.getRight()));
			return node;
		}
	}

	public T get(T t) {
		return getHelper(t, root);
	}
	
	private T getHelper(T element, BSTNode<T> tree) {
		 // Returns an element e from tree such that e.compareTo(element) == 0;
		 // if no such element exists, returns null.
		 if (tree == null)
			 return null; // element is not found
		 else if (element.compareTo(tree.getData()) < 0)
			 return getHelper(element, tree.getLeft()); // get from left subtree
		 else
			 if (element.compareTo(tree.getData()) > 0)
				 	return getHelper(element, tree.getRight()); // get from right subtree
		 else
			 return tree.getData(); // element is found
	 }
	
//	private T getHelper(T data, BSTNode<T> node) {
//		
//		if (data.equals(node.getData())) {
//			return data;
//		}
//		
//		if(node.getLeft() == null && node.getRight() == null) {
//			return null;
//		}
//		
//		if(node.getLeft() == null) {
//			node = node.getRight();
//			return getHelper(data, node);
//		}
//		
//		if(node.getRight() == null) {
//			node = node.getLeft();
//			return getHelper(data, node);
//		}
//		
//		if (data.compareTo(node.getData()) <= 0) {
//			node = node.getLeft();
//		} else {
//			node = node.getRight();
//		}
//		
//		return getHelper(data, node);
//	}

	public void add(T t) {
		root = addToSubtree(t, root);
	}

	private BSTNode<T> addToSubtree(T t, BSTNode<T> node) {
		if (node == null) {
			return new BSTNode<T>(t, null, null);
		}
		if (t.compareTo(node.getData()) <= 0) {
			node.setLeft(addToSubtree(t, node.getLeft()));
		} else {
			node.setRight(addToSubtree(t, node.getRight()));
		}
		return node;
	}

	@Override
	public T getMinimum() {
		if (this.isEmpty()) {
			return null;
		}
		
		return getMinimumHelper(root);
	}
	
	private T getMinimumHelper(BSTNode<T> node) {
		if (node.getLeft() == null) {
			return node.getData();
		}
		return getMinimumHelper(node.getLeft());
	}

	@Override
	public T getMaximum() {
		if (this.isEmpty()) {
			return null;
		}
		
		return getMaximumHelper(root);
	}
	
	private T getMaximumHelper(BSTNode<T> node) {
		if (node.getRight() == null) {
			return node.getData();
		}
		return getMinimumHelper(node.getRight());
	}

	@Override
	public int height() {
		
		if (this.isEmpty()) {
			return -1;
		} else if (size() == 1) {
			return 0;
		}
		
		return heightHelper(root) - 1;
	}
	
	//FIX
	private int heightHelper(BSTNode<T> tree) {
		if (tree == null) {
			return 0;
		} else {
			
			if (heightHelper(tree.getLeft()) > heightHelper(tree.getRight())) {
				return heightHelper(tree.getLeft()) + 1;
			} else {
				return heightHelper(tree.getRight()) + 1;
			}
		}
	}
	
//	private int heightHelper(BSTNode<T> tree) {
//		if (tree == null) {
//			return 0;
//		} else {
//			int heightLeft = heightHelper(tree.getLeft());
//			int heightRight = heightHelper(tree.getRight());
//			return Math.max(heightLeft, heightRight);
//		}
//	}

	@Override	
	public Iterator<T> preorderIterator() {
		Queue<T> queue = new LinkedList<T>();
		preorderTraverse(queue, root);
		return queue.iterator();
	}
	
	private void preorderTraverse(Queue<T> queue, BSTNode<T> node) {
		if (node != null) {
			preorderTraverse(queue, node.getLeft());
			queue.add(node.getData());
			preorderTraverse(queue, node.getRight());
		}
	}

	@Override
	public Iterator<T> inorderIterator() {
		Queue<T> queue = new LinkedList<T>();
		inorderTraverse(queue, root);
		return queue.iterator();
	}
	
	private void inorderTraverse(Queue<T> queue, BSTNode<T> node) {
		if (node != null) {
			inorderTraverse(queue, node.getLeft());
			queue.add(node.getData());
			inorderTraverse(queue, node.getRight());
		}
	}

	@Override
	public Iterator<T> postorderIterator() {
		Queue<T> queue = new LinkedList<T>();
		postorderTraverse(queue, root);
		return queue.iterator();
	}
	
	private void postorderTraverse(Queue<T> queue, BSTNode<T> node) {
		if (node != null) {
			postorderTraverse(queue, node.getLeft());
			postorderTraverse(queue, node.getRight());
			queue.add(node.getData());
		}
	}

	@Override
	public boolean equals(BSTInterface<T> other) {
		return equalsHelper(this.root, other.getRoot());
	}
	
//	private boolean equalsHelper(BSTNode<T> node, BSTNode<T> node2) {
//		if (node.getData() != node2.getData() || node.getRight() != node2.getRight() || node.getLeft() != node2.getLeft()) {
//			return false;
//		} else {
//			return equalsHelper(node.getLeft(), node.getRight());
//		}
//	}
	
	private boolean equalsHelper(BSTNode<T> node, BSTNode<T> node2) {
		
		if (node == node2) {
			return true;
		} else {
			if (node == null || node2 == null) {
				return false;
			} else {
				boolean checkLeft = equalsHelper(node.getLeft(), node2.getLeft());
				boolean checkRight = equalsHelper(node.getRight(), node2.getRight());
				return node.getData().equals(node2.getData()) && checkLeft && checkRight;
			}
		}
	}

	@Override
	public boolean sameValues(BSTInterface<T> other) {
		
		Iterator<T> iterThis = this.inorderIterator();
		Iterator<T> iterThat = other.inorderIterator();
		
		while (iterThis.hasNext()) {
			if (!iterThis.next().equals(iterThat.next())) {
				return false;
			}
		}
		
		if (iterThis.hasNext() || iterThat.hasNext()) {
			return false;
		}
		
		return true;
	}

	@Override
	public boolean isBalanced() {
		return isBalancedHelper(root);
	}
	
	public boolean isBalancedHelper(BSTNode<T> node) {
		if (node == null) {
			return true;
		} else {
			if (heightHelper(node.getLeft()) > heightHelper(node.getRight()) + 1 || heightHelper(node.getLeft()) < heightHelper(node.getRight()) - 1) {
				return false;
			} else if (!isBalancedHelper(node.getLeft()) || !isBalancedHelper(node.getRight())) {
				return false;
			} else {
				return true;
			}
		}
	}

	@Override
	public void balance() {
		if (this.isEmpty()) {
			return;
		}
		
		Iterator<T> iter = this.inorderIterator();
		
		ArrayList<T> list = new ArrayList<T>();
		
		while (iter.hasNext()) {
			list.add(iter.next());
		}
		
		root = null;
		
		balanceHelper(list);
	}
	
	private void balanceHelper(ArrayList<T> list) {
		
		if (list.isEmpty()) {
			return;
		}
		
		if (list.size() == 1) {
			this.add(list.get(0));
			return;
		}
		
		int middle = (list.size() - 1) / 2;
		this.add(list.get(middle));
		list.remove(middle);
		
		ArrayList<T> leftSide = new ArrayList<T>(list.subList(0, (list.size() - 1) / 2));
		ArrayList<T> rightSide = new ArrayList<T>(list.subList((list.size() - 1) / 2, list.size()));
		
		balanceHelper(leftSide);
		balanceHelper(rightSide);
		
	}

	@Override
	public BSTNode<T> getRoot() {
		// DO NOT MODIFY
		return root;
	}

	public static <T extends Comparable<T>> String toDotFormat(BSTNode<T> root) {
		// DO NOT MODIFY
		// see project description for explanation

		// header
		int count = 0;
		String dot = "digraph G { \n";
		dot += "graph [ordering=\"out\"]; \n";
		// iterative traversal
		Queue<BSTNode<T>> queue = new LinkedList<BSTNode<T>>();
		queue.add(root);
		BSTNode<T> cursor;
		while (!queue.isEmpty()) {
			cursor = queue.remove();
			if (cursor.getLeft() != null) {
				// add edge from cursor to left child
				dot += cursor.getData().toString() + " -> "
						+ cursor.getLeft().getData().toString() + ";\n";
				queue.add(cursor.getLeft());
			} else {
				// add dummy node
				dot += "node" + count + " [shape=point];\n";
				dot += cursor.getData().toString() + " -> " + "node" + count
						+ ";\n";
				count++;
			}
			if (cursor.getRight() != null) {
				// add edge from cursor to right child
				dot += cursor.getData().toString() + " -> "
						+ cursor.getRight().getData().toString() + ";\n";
				queue.add(cursor.getRight());
			} else {
				// add dummy node
				dot += "node" + count + " [shape=point];\n";
				dot += cursor.getData().toString() + " -> " + "node" + count
						+ ";\n";
				count++;
			}

		}
		dot += "};";
		return dot;
	}
}
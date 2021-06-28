package msb.class08;

import java.util.LinkedList;

/**
 * 二叉树的递归套路深度实践
 * 给定一棵二叉树的头节点head，返回这颗二叉树中是不是完全二叉树
 * @Date 2021/5/11 9:42
 */
public class Code06_IsCBT {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int data) {
			this.value = data;
		}
	}

	// 一般解法：宽度优先遍历，需要队列
	// 1)任何节点有右无左 false
	// 2)一旦遇到左右孩子不双全，后续遇到所有节点必须为叶节点
	public static boolean isCBT1(Node head) {
		if (head == null) {
			return true;
		}
		LinkedList<Node> queue = new LinkedList<>();
		// 是否遇到过左右两个孩子不双全的节点
		boolean leaf = false;
		Node l = null;
		Node r = null;
		queue.add(head);
		while (!queue.isEmpty()) {
			head = queue.poll();
			l = head.left;
			r = head.right;
			if (
			// 如果遇到了不双全的节点之后，又发现当前节点不是叶节点
//			(leaf && (l != null || r != null)) || (l == null && r != null)) { 等价写法
			(leaf && !(l == null && r == null)) || (l == null && r != null)) {
				return false;
			}
			if (l != null) {
				queue.add(l);
			}
			if (r != null) {
				queue.add(r);
			}
			if (l == null || r == null) {// 只要有两个孩子不双全的情况，leaf就会设置true，可能会多次执行，leaf多次改为true，但只要变为true，不会再变成false
				leaf = true;
			}
		}
		return true;
	}

	// 二叉树递归套路解法
	// 1)满二叉树，无缺口
	// 2）【最后一层】有缺口，2.1）左子树有缺口 2.2）左子树刚好占满，右子树最后一层空 2.3）右子树有缺口
	public static boolean isCBT2(Node head) {
		if (head == null) {
			return true;
		}
		return process(head).isCBT;
	}

	public static class Info {
		public boolean isFull;
		public boolean isCBT;
		public int height;

		public Info(boolean full, boolean cbt, int h) {
			isFull = full;
			isCBT = cbt;
			height = h;
		}
	}

	public static Info process(Node head) {
		if (head == null) {
			return new Info(true, true, 0);
		}
		Info leftInfo = process(head.left);
		Info rightInfo = process(head.right);
		int height = Math.max(leftInfo.height, rightInfo.height) + 1;
		boolean isFull = leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height;
		boolean isCBT = false;
		if (isFull) {
			isCBT = true;
		} else {
			if (leftInfo.isCBT && rightInfo.isCBT) {
				if (leftInfo.isCBT && !leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height + 1) {
					isCBT = true;
				}
				if (leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height + 1) {
					isCBT = true;
				}
				if (leftInfo.isFull && rightInfo.isCBT && leftInfo.height == rightInfo.height) {
					isCBT = true;
				}
			}
		}
		return new Info(isFull, isCBT, height);
	}

	// for test
	public static Node generateRandomBST(int maxLevel, int maxValue) {
		return generate(1, maxLevel, maxValue);
	}

	// for test
	public static Node generate(int level, int maxLevel, int maxValue) {
		if (level > maxLevel || Math.random() < 0.5) {
			return null;
		}
		Node head = new Node((int) (Math.random() * maxValue));
		head.left = generate(level + 1, maxLevel, maxValue);
		head.right = generate(level + 1, maxLevel, maxValue);
		return head;
	}

	public static void main(String[] args) {
		int maxLevel = 5;
		int maxValue = 100;
		int testTimes = 1000000;
		for (int i = 0; i < testTimes; i++) {
			Node head = generateRandomBST(maxLevel, maxValue);
			if (isCBT1(head) != isCBT2(head)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("finish!");
	}

}

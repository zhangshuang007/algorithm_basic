package msb.class08;

import java.util.ArrayList;

/**
 * 二叉树的递归套路深度实践
 * 给定一棵二叉树的头节点head，返回这颗二叉树中最大的二叉搜索子树的头节点
 * @Author zhangshuang
 * @Date 2021/5/10 16:18
 */
public class Code04_MaxSubBSTSize {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int data) {
			this.value = data;
		}
	}

	public static int getBSTSize(Node head) {
		if (head == null) {
			return 0;
		}
		ArrayList<Node> arr = new ArrayList<Node>();
		in(head, arr);
		for (int i = 1; i < arr.size(); i++) {
			if (arr.get(i).value <= arr.get(i - 1).value) {
				return 0;
			}
		}
		return arr.size();
	}

	public static void in(Node head, ArrayList<Node> arr) {
		if (head == null) {
			return;
		}
		in(head.left, arr);
		arr.add(head);
		in(head.right, arr);
	}

	public static int maxSubBSTSize1(Node head) {
		if (head == null) {
			return 0;
		}
		int h = getBSTSize(head);
		if (h != 0) {
			return h;
		}
		return Math.max(maxSubBSTSize1(head.left), maxSubBSTSize1(head.right));
	}

	public static int maxSubBSTSize2(Node head) {
		if (head == null) {
			return 0;
		}
		return process(head).maxSubBSTSize;
	}

	// 任何子树
	public static class Info {
		public boolean isAllBST;// 整棵子树是否为搜索二叉树
		public int maxSubBSTSize;// 满足搜索二叉树条件的最大子树的节点个数
		public int min;// 整个树最小值
		public int max;

		public Info(boolean is, int size, int mi, int ma) {
			isAllBST = is;
			maxSubBSTSize = size;
			min = mi;
			max = ma;
		}
	}

	// // 二叉树的递归套路:
	// 1）X无关   2）X有关:1.左树整体需要是搜索二叉树2.右树整体需要是搜索二叉树3.左树最大值<X 且右树最小值>X
	public static Info process(Node X) {
		if (X == null) {
			return null;
		}
		Info leftInfo = process(X.left);
		Info rightInfo = process(X.right);
		int min = X.value;
		int max = X.value;
		int maxSubBSTSize = 0;
		if (leftInfo != null) {
			min = Math.min(min, leftInfo.min);
			max = Math.max(max, leftInfo.max);
			maxSubBSTSize = Math.max(maxSubBSTSize, leftInfo.maxSubBSTSize);
		}
		if (rightInfo != null) {
			min = Math.min(min, rightInfo.min);
			max = Math.max(max, rightInfo.max);
			maxSubBSTSize = Math.max(maxSubBSTSize, rightInfo.maxSubBSTSize);
		}
		boolean isBST = false;
		if ((leftInfo == null ? true : (leftInfo.isAllBST && leftInfo.max < X.value))
				&& (rightInfo == null ? true : (rightInfo.isAllBST && rightInfo.min > X.value))) {
			isBST = true;
			maxSubBSTSize = (leftInfo == null ? 0 : leftInfo.maxSubBSTSize)
					+ (rightInfo == null ? 0 : rightInfo.maxSubBSTSize) + 1;
		}
		return new Info(isBST, maxSubBSTSize, min, max);
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
		int maxLevel = 4;
		int maxValue = 100;
		int testTimes = 1000000;
		for (int i = 0; i < testTimes; i++) {
			Node head = generateRandomBST(maxLevel, maxValue);
			if (maxSubBSTSize1(head) != maxSubBSTSize2(head)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("finish!");
	}

}

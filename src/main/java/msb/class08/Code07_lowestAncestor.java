package msb.class08;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * 二叉树的递归套路深度实践
 * 给定一棵二叉树的头节点head，和另外两个节点a和b。返回a和b的最低公共祖先
 *
 * @Date 2021/5/11 9:44
 */
public class Code07_lowestAncestor {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int data) {
			this.value = data;
		}
	}

	// 递归，先填好一张父节点表,O(n),不够简洁
	public static Node lowestAncestor1(Node head, Node o1, Node o2) {
		if (head == null) {
			return null;
		}
		// value是key的父节点
		HashMap<Node, Node> parentMap = new HashMap<>();
		parentMap.put(head, null);
		fillParentMap(head, parentMap);// 维护所有节点对应的父节点的map
		HashSet<Node> o1Set = new HashSet<>();
		Node cur = o1;
		o1Set.add(cur);
		while (parentMap.get(cur) != null) {
			cur = parentMap.get(cur);
			o1Set.add(cur);// 维护o1到根节点沿途所有节点
		}
		cur = o2;
		while (!o1Set.contains(cur)) {// 注：程序输入保证o1和o2在一棵树上，所以肯定有公共祖先
			cur = parentMap.get(cur);
		}
		return cur;
	}

	// O(n)
	public static void fillParentMap(Node head, HashMap<Node, Node> parentMap) {
		if (head.left != null) {
			parentMap.put(head.left, head);
			fillParentMap(head.left, parentMap);
		}
		if (head.right != null) {
			parentMap.put(head.right, head);
			fillParentMap(head.right, parentMap);
		}
	}

	// 1)O1，O2无一个在X上
	// 2)O1，O2只有一个在X上
	// 3)O1，O2都在X为头的树上
	// 3.1 左右各一个
	// 3.2 左O1 O2
	// 3.3 右O1 O2
	// 3.4 X=O1或O2
	public static Node lowestAncestor2(Node head, Node o1, Node o2) {
		return process(head, o1, o2).ans;
	}

	// 任何子树
	public static class Info {
		public Node ans;// O1和O2的最初交汇点，如果不是在这个子树上交汇的，ans=null
		public boolean findO1;// 在这棵子树上是否发现O1
		public boolean findO2;

		public Info(Node a, boolean f1, boolean f2) {
			ans = a;
			findO1 = f1;
			findO2 = f2;
		}
	}

	public static Info process(Node X, Node o1, Node o2) {
		if (X == null) {
			return new Info(null, false, false);
		}
		Info leftInfo = process(X.left, o1, o2);
		Info rightInfo = process(X.right, o1, o2);

		boolean findO1 = X == o1 || leftInfo.findO1 || rightInfo.findO1;
		boolean findO2 = X == o2 || leftInfo.findO2 || rightInfo.findO2;
		// O1和O2最初的交汇点在哪？以下是高度整合后的结果
		// 1)在左树已经提前交汇了
		// 2)在右树已经提前交汇了
		// 3)没有在左树或者右树上提前交汇，O1和O2全了
		// 4)X没有找全O1和O2的情况，维持ans=null的状态
		Node ans = null;
		if (leftInfo.ans != null) {// 如果左树已经发现答案了，答案就是左树交互点，不用再找了
			ans = leftInfo.ans;
		}
		if (rightInfo.ans != null) {
			ans = rightInfo.ans;
		}
		if (ans == null) {
			if (findO1 && findO2) {
				ans = X;// X就是交汇点
			}
		}
		// 如果以上条件都没中，就是没有发现，ans=null
		return new Info(ans, findO1, findO2);
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

	// for test
	public static Node pickRandomOne(Node head) {
		if (head == null) {
			return null;
		}
		ArrayList<Node> arr = new ArrayList<>();
		fillPrelist(head, arr);
		int randomIndex = (int) (Math.random() * arr.size());
		return arr.get(randomIndex);
	}

	// for test
	public static void fillPrelist(Node head, ArrayList<Node> arr) {
		if (head == null) {
			return;
		}
		arr.add(head);
		fillPrelist(head.left, arr);
		fillPrelist(head.right, arr);
	}

	public static void main(String[] args) {
		int maxLevel = 4;
		int maxValue = 100;
		int testTimes = 1000000;
		for (int i = 0; i < testTimes; i++) {
			Node head = generateRandomBST(maxLevel, maxValue);
			Node o1 = pickRandomOne(head);
			Node o2 = pickRandomOne(head);
			if (lowestAncestor1(head, o1, o2) != lowestAncestor2(head, o1, o2)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("finish!");
	}

}

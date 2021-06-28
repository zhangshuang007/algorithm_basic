package msb.class07;

import java.util.Stack;

/**
 * @Description 非递归方式实现二叉树的先序、中序、后序遍历
 * @Author zhangshuang
 * @Date 2021/5/8 9:44
 */
public class Code02_UnRecursiveTraversalBT {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int v) {
			value = v;
		}
	}

	// 先序遍历（头左右）
	public static void pre(Node head) {
		System.out.print("pre-order: ");
		if (head != null) {
			Stack<Node> stack = new Stack<Node>();
			stack.add(head);
			while (!stack.isEmpty()) {
				head = stack.pop();
				System.out.print(head.value + " ");
				if (head.right != null) {
					stack.push(head.right);
				}
				if (head.left != null) {
					stack.push(head.left);
				}
			}
		}
		System.out.println();
	}

	// 中序遍历（左头右）
	public static void in(Node head) {
		System.out.print("in-order: ");
		if (head != null) {
			Stack<Node> stack = new Stack<Node>();
			while (!stack.isEmpty() || head != null) {
				if (head != null) {
					stack.push(head);
					head = head.left;
				} else {
					head = stack.pop();
					System.out.print(head.value + " ");
					head = head.right;
				}
			}
		}
		System.out.println();
	}

	// 后序遍历（左右头）
	public static void pos1(Node head) {
		System.out.print("pos-order: ");
		if (head != null) {
			Stack<Node> s1 = new Stack<Node>();
			Stack<Node> s2 = new Stack<Node>();
			s1.push(head);
			while (!s1.isEmpty()) {
				head = s1.pop();
				s2.push(head);
				if (head.left != null) {
					s1.push(head.left);
				}
				if (head.right != null) {
					s1.push(head.right);
				}
			}
			while (!s2.isEmpty()) {
				System.out.print(s2.pop().value + " ");
			}
		}
		System.out.println();
	}

	// 用一个栈来实现后序遍历（左右头）,c表示当前节点，h表示上次打印的节点
	// 利用c和h卡逻辑，标记左右子树处理完了没有，用h标记栈中头结点c它的子过程有没有完毕
	public static void pos2(Node h) {
		System.out.print("pos-order: ");
		if (h != null) {
			Stack<Node> stack = new Stack<Node>();
			stack.push(h);
			Node c = null;
			while (!stack.isEmpty()) {
				c = stack.peek();
				if (c.left != null && h != c.left && h != c.right) {//左孩子不空，且上次打印的节点不是c的左孩子，也不是c的右孩子，说明c是新到的节点，先处理左树
					stack.push(c.left);
				} else if (c.right != null && h != c.right) {// 此刻左树已经处理完了，如果c的右孩子不等于空并且上次打印的节点不是c的右孩子（代表没有处理右树），处理右树
					stack.push(c.right);
				} else {// 否则代表左右子树都处理完了，处理自己返回
					System.out.print(stack.pop().value + " ");
					h = c;// h来到上次打印的节点
				}
			}
		}

		System.out.println();
	}

	public static void main(String[] args) {
		Node head = new Node(1);
		head.left = new Node(2);
		head.right = new Node(3);
		head.left.left = new Node(4);
		head.left.right = new Node(5);
		head.right.left = new Node(6);
		head.right.right = new Node(7);

		pre(head);
		System.out.println("========");
		in(head);
		System.out.println("========");
		pos1(head);
		System.out.println("========");
		pos2(head);
		System.out.println("========");
	}

}

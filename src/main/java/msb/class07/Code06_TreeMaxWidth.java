package msb.class07;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @Description 统计二叉树最大的宽度
 * @Author zhangshuang
 * @Date 2021/5/8 9:54
 */
public class Code06_TreeMaxWidth {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int data) {
			this.value = data;
		}
	}

	/**
	 * 发现某一个层次开始或结束
	 */
	public static int maxWidthUseMap(Node head) {
		if (head == null) {
			return 0;
		}
		Queue<Node> queue = new LinkedList<>();
		queue.add(head);
		// key在哪一层，value就是几
		HashMap<Node, Integer> levelMap = new HashMap<>();
		levelMap.put(head, 1);
		int curLevel = 1;// 当前你正在统计哪一层的宽度
		int curLevelNodes = 0;// 当前层curLevel层节点数（宽度），进到队列时候不统计，出来之后再++
		int max = 0;// 所有层的最大值，返回
		while (!queue.isEmpty()) {
			Node cur = queue.poll();
			int curNodeLevel = levelMap.get(cur);
			if (cur.left != null) {
				levelMap.put(cur.left, curNodeLevel + 1);
				queue.add(cur.left);
			}
			if (cur.right != null) {
				levelMap.put(cur.right, curNodeLevel + 1);
				queue.add(cur.right);
			}
			if (curNodeLevel == curLevel) {// 当前节点所在的层数等于目前统计层的层数，说明当前层没有过期，节点数++
				curLevelNodes++;
			} else {// 否则，新层首次出现，开始结算上一层，更新max，所以curLevelNodes从1开始计数
				max = Math.max(max, curLevelNodes);
				curLevel++;
				curLevelNodes = 1;
			}
		}
		max = Math.max(max, curLevelNodes);// 单独结算最后一层，更新max
		return max;
	}

	public static int maxWidthNoMap(Node head) {
		if (head == null) {
			return 0;
		}
		Queue<Node> queue = new LinkedList<>();
		queue.add(head);
		Node curEnd = head;// 当前层，最右节点是谁
		Node nextEnd = null;// 下一层，最右节点是谁
		int max = 0;
		int curLevelNodes = 0;// 当前层的节点数
		while (!queue.isEmpty()) {
			Node cur = queue.poll();
			if (cur.left != null) {
				queue.add(cur.left);
				nextEnd = cur.left;
			}
			if (cur.right != null) {
				queue.add(cur.right);
				nextEnd = cur.right;
			}
			curLevelNodes++;
			if (cur == curEnd) {
				// 相比上面，不用单独结算最后一层，因为每一层结束时直接结算
				max = Math.max(max, curLevelNodes);
				curLevelNodes = 0;
				curEnd = nextEnd;
			}
		}
		return max;
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
		int maxLevel = 10;
		int maxValue = 100;
		int testTimes = 1000000;
		for (int i = 0; i < testTimes; i++) {
			Node head = generateRandomBST(maxLevel, maxValue);
			if (maxWidthUseMap(head) != maxWidthNoMap(head)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("finish!");

	}

}

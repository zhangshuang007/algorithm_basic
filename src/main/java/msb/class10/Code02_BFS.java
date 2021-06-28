package msb.class10;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 宽度优先遍历
 * 1，利用队列实现
 * 2，从源节点开始依次按照宽度进队列，然后弹出
 * 3，每弹出一个点，把该节点所有没有进过队列的邻接点放入队列
 * 4，直到队列变空
 *
 * @Date 2021/5/17 15:05
 */
public class Code02_BFS {

	// 从node出发，进行宽度优先遍历
	public static void bfs(Node node) {
		if (node == null) {
			return;
		}
		Queue<Node> queue = new LinkedList<>();
		HashSet<Node> set = new HashSet<>();// 图中可能有环，set作用保证代码不会死循环
		queue.add(node);
		set.add(node);
		while (!queue.isEmpty()) {
			Node cur = queue.poll();
			System.out.println(cur.value);
			for (Node next : cur.nexts) {
				if (!set.contains(next)) {
					set.add(next);
					queue.add(next);
				}
			}
		}
	}

}

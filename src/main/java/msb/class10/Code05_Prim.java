package msb.class10;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * 最小生成树算法之Prim
 * 不需要并查集，因为每次只有一个权重最小的边对应的新节点进入集合，不涉及两个点集合的合并
 * 1）可以从任意节点出发来寻找最小生成树
 * 2）某个点加入到被选取的点中后，解锁这个点出发的所有新的边
 * 3）在所有解锁的边中选最小的边，然后看看这个边会不会形成环
 * 4）如果会，不要当前边，继续考察剩下解锁的边中最小的边，重复3）
 * 5）如果不会，要当前边，将该边的指向点加入到被选取的点中，重复2）
 * 6）当所有点都被选取，最小生成树就得到了
 * @Date 2021/5/19 13:56
 */
// undirected graph only
public class Code05_Prim {

	public static class EdgeComparator implements Comparator<Edge> {

		@Override
		public int compare(Edge o1, Edge o2) {
			return o1.weight - o2.weight;
		}

	}

	public static Set<Edge> primMST(Graph graph) {
		// 解锁的边进入小根堆
		PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(
				new EdgeComparator());
		// 哪些点被解锁出来了
		HashSet<Node> nodeSet = new HashSet<>();
		// 已经考虑过的边不要重复考虑
		HashSet<Edge> edgeSet =  new HashSet<>();
		Set<Edge> result = new HashSet<>(); // 依次挑选的边在result里
		// 最外层的for用于防止森林（即图不是联通的），可用可不用
		for (Node node : graph.nodes.values()) { // 随便挑了一个点
			// node 是开始点
			if (!nodeSet.contains(node)) {
				nodeSet.add(node);
				for (Edge edge : node.edges) { // 由一个点，解锁所有相连的边
					if(!edgeSet.contains(edge)) {// 防止已经放入的边有可能被重新放入，但不会影响结果
						edgeSet.add(edge);
						priorityQueue.add(edge);
					}
				}
				// 由最小边找点，再由点找遍，循环往复
				while (!priorityQueue.isEmpty()) {
					Edge edge = priorityQueue.poll(); // 弹出解锁的边中，最小的边
					Node toNode = edge.to; // 可能的一个新的点
					if (!nodeSet.contains(toNode)) { // 不含有的时候，就是新的点
						nodeSet.add(toNode);
						result.add(edge);
						for (Edge nextEdge : toNode.edges) {
							if(!edgeSet.contains(nextEdge)) {
								edgeSet.add(nextEdge);
								priorityQueue.add(nextEdge);
							}

						}
					}
				}
			}
			break;// 从一个点出发做一遍就够，不考虑森林
		}
		return result;
	}

	// 请保证graph是连通图
	// graph[i][j]表示点i到点j的距离，如果是系统最大值代表无路
	// 返回值是最小连通图的路径之和
	public static int prim(int[][] graph) {
		int size = graph.length;
		int[] distances = new int[size];
		boolean[] visit = new boolean[size];
		visit[0] = true;
		for (int i = 0; i < size; i++) {
			distances[i] = graph[0][i];
		}
		int sum = 0;
		for (int i = 1; i < size; i++) {
			int minPath = Integer.MAX_VALUE;
			int minIndex = -1;
			for (int j = 0; j < size; j++) {
				if (!visit[j] && distances[j] < minPath) {
					minPath = distances[j];
					minIndex = j;
				}
			}
			if (minIndex == -1) {
				return sum;
			}
			visit[minIndex] = true;
			sum += minPath;
			for (int j = 0; j < size; j++) {
				if (!visit[j] && distances[j] > graph[minIndex][j]) {
					distances[j] = graph[minIndex][j];
				}
			}
		}
		return sum;
	}

	public static void main(String[] args) {
		System.out.println("hello world!");
	}

}

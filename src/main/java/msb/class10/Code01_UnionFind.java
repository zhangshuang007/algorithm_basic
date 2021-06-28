package msb.class10;

import java.util.*;

/**
 * @Description 并查集，解决连通性问题
 * @Author zhangshuang
 * @Date 2021/5/14 16:27
 */
public class Code01_UnionFind {

	public static class Node<V> {
		V value;

		public Node(V v) {
			value = v;
		}
	}

	public static class UnionSet<V> {
		// V -> 节点
		public HashMap<V, Node<V>> nodes = new HashMap<>();
		public HashMap<Node<V>, Node<V>> parents = new HashMap<>();
		// 只有一个点它是代表点，才有记录
		public HashMap<Node<V>, Integer> sizeMap = new HashMap<>();

		public UnionSet(List<V> values) {
			for (V cur : values) {
				Node<V> node = new Node<>(cur);
				nodes.put(cur, node);
				parents.put(node, node);
				sizeMap.put(node, 1);
			}
		}


		// 如果调用频繁，比如超过N次，则复杂度单次O(1),不用管证明
		// 从点cur开始,一直往上找，找到不能再往上的代表点，返回
		public Node<V> findFather(Node<V> cur) {
			Stack<Node<V>> path = new Stack<>();
			while (cur != parents.get(cur)) {// 还能继续往上找
				path.push(cur);// 记录沿途的路径，下面的优化要用
				cur = parents.get(cur);// 来到自己父的位置
			}
			// cur 代表点
			while (!path.isEmpty()) {
				parents.put(path.pop(), cur);// 优化：沿途所有节点指向代表点，完成扁平化处理，目的：减少遍历链的高度
			}
			return cur;
		}

		// O(1)
		public boolean isSameSet(V a, V b) {
			// 判断a b是否已经登记了
			if (!nodes.containsKey(a) || !nodes.containsKey(b)) {
				return false;
			}
			return findFather(nodes.get(a)) == findFather(nodes.get(b));// 其他操作都是O(1)，findFather也是O(1)
		}

		// O(1)
		public void union(V a, V b) {
			if (!nodes.containsKey(a) || !nodes.containsKey(b)) {
				return;
			}
			Node<V> aHead = findFather(nodes.get(a));// 其他操作都是O(1)，瓶颈在此
			Node<V> bHead = findFather(nodes.get(b));
			if (aHead != bHead) {
				int aSetSize = sizeMap.get(aHead);// aHead是代表点，拿到集合大小
				int bSetSize = sizeMap.get(bHead);
				// 小挂大
				if (aSetSize >= bSetSize) {
					parents.put(bHead, aHead);
					sizeMap.put(aHead, aSetSize + bSetSize);
					sizeMap.remove(bHead);// 挂到aHead的底下，bHead不再作为代表点，删掉bHead的记录
				} else {
					parents.put(aHead, bHead);
					sizeMap.put(bHead, aSetSize + bSetSize);
					sizeMap.remove(aHead);
				}
			}
		}
	}
}

package msb.class10;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * @Description 并查集练习题
 * @Author Administrator
 * @Date 2021/5/17 14:11
 */
public class Code07_MergeUsers {

    public static class Node<V> {
        V value;

        public Node(V v) {
            value = v;
        }
    }

    public static class UnionSet<V> {
        // V -> 节点
        public HashMap<V, Code01_UnionFind.Node<V>> nodes = new HashMap<>();
        public HashMap<Code01_UnionFind.Node<V>, Code01_UnionFind.Node<V>> parents = new HashMap<>();
        // 只有一个点它是代表点，才有记录
        public HashMap<Code01_UnionFind.Node<V>, Integer> sizeMap = new HashMap<>();

        public UnionSet(List<V> values) {
            for (V cur : values) {
                Code01_UnionFind.Node<V> node = new Code01_UnionFind.Node<>(cur);
                nodes.put(cur, node);
                parents.put(node, node);
                sizeMap.put(node, 1);
            }
        }


        // 如果调用频繁，比如超过N次，则复杂度单次O(1),不用管证明
        // 从点cur开始,一直往上找，找到不能再往上的代表点，返回
        public Code01_UnionFind.Node<V> findFather(Code01_UnionFind.Node<V> cur) {
            Stack<Code01_UnionFind.Node<V>> path = new Stack<>();
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
            Code01_UnionFind.Node<V> aHead = findFather(nodes.get(a));// 其他操作都是O(1)，瓶颈在此
            Code01_UnionFind.Node<V> bHead = findFather(nodes.get(b));
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

        public int getSetNum() {
            return sizeMap.size();
        }
    }



    public static class User {
        public String a;
        public String b;
        public String c;

        public User(String a, String b, String c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

    }

    // (1,10,13) (2,10,37) (400,500,37)
    // 如果两个user，a字段一样、或者b字段一样、或者c字段一样，确认为是一个人
    // 请合并users，返回合并之后的用户数量
    public static int mergeUsers(List<User> users) {
        // users[0] 0 (用下标代替节点V)
        // users[1] 1
        UnionSet<User> unionFind = new UnionSet<>(users);
        HashMap<String, User> mapA = new HashMap<>();
        HashMap<String, User> mapB = new HashMap<>();
        HashMap<String, User> mapC = new HashMap<>();

        for (User user : users) {
            if(!mapA.containsKey(user.a)) {
                mapA.put(user.a, user);
            } else {
                unionFind.union(user, mapA.get(user.a));
            }
            if(!mapB.containsKey(user.b)) {
                mapB.put(user.b, user);
            } else {
                unionFind.union(user, mapB.get(user.b));
            }
            if(!mapC.containsKey(user.c)) {
                mapC.put(user.c, user);
            } else {
                unionFind.union(user, mapC.get(user.c));
            }
        }
        // 向并查集询问，合并之后，还有多少个集合？
        return unionFind.getSetNum();
    }

    public static void main(String[] args) {
        List<User> values = generateNode();
        System.out.println(mergeUsers(values));
    }

    public static List<User> generateNode() {
        List<User> values = new ArrayList<>();
//		for (int i = 0; i < 1000; i++) {
//
//		}
        values.add(new User("1","10","13"));
        values.add(new User("2","10","37"));
        values.add(new User("400","500","37"));
        values.add(new User("ce","true","丙"));
        values.add(new User("12","true","21"));
        return values;
    }
}

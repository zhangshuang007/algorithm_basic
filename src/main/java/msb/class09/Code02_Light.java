package msb.class09;

import java.util.HashSet;

/**
 * 给定一个字符串str，只由‘X’和‘.’两种字符构成。
 * ‘X’表示墙，不能放灯，也不需要点亮
 * ‘.’表示居民点，可以放灯，需要点亮
 * 如果灯放在i位置，可以让i-1，i和i+1三个位置被点亮
 * 返回如果点亮str中所有需要点亮的位置，至少需要几盏灯
 * @Date 2021/5/13 15:08
 */
public class Code02_Light {

	// 暴力解
	public static int minLight1(String road) {
		if (road == null || road.length() == 0) {
			return 0;
		}
		return process(road.toCharArray(), 0, new HashSet<>());
	}

	// str[index....]位置，自由选择放灯还是不放灯
	// str[0..index-1]位置呢？已经做完决定了，那些放了灯的位置，存在lights里
	// 要求选出能照亮所有'.'的方案，并且在这些有效的方案中，返回最少需要几盏灯
	public static int process(char[] str, int index, HashSet<Integer> lights) {
		if (index == str.length) {// 来到终止位置，没有位置可以放灯
			for (int i = 0; i < str.length; i++) {// 验证收集的这种方案，能不能把所有位置照亮
				if (str[i] != 'X') {// 当前位置是‘.’的话，判断i-1，i，i+1位置是否有灯，如果都没有灯，返回无效解，否则有效
					if (!lights.contains(i - 1) && !lights.contains(i) && !lights.contains(i + 1)) {
						return Integer.MAX_VALUE;
					}
				}
			}
			return lights.size();
		} else { // str还没有结束
			// index位置不管是X还是'.'，都可以选择不放灯
			// 只有index位置是'.'，才可以选择在该位置放灯
			int no = process(str, index + 1, lights);// 当前index位置没有放灯，返回后续的最少灯数
			int yes = Integer.MAX_VALUE;
			if (str[index] == '.') {
				lights.add(index);
				yes = process(str, index + 1, lights);// index位置是.,且放灯，返回后续的最少灯数
				lights.remove(index);// 根据遍历原则，加上现场之后，出来的时候要remove掉，因为始终用的一个lights,不恢复遍历平行的分支会有脏数据，导致无法遍历
			}
			return Math.min(no, yes);
		}
	}

	// 贪心
	// 潜台词:i不会被之前位置影响，不会因为之前放灯导致i位置现在是亮的状态
	// 如果i->X，i+1不会被i位置影响,i+1可以继续做决定
	// 如果i->.，i+1->X，i放灯，i+2位置不会被影响
	// 如果i->.，i+1->.，i+1放灯，因为i+2如果是X，或者i+2是.,不会影响到i+3位置
	public static int minLight2(String road) {
		char[] str = road.toCharArray();
		int index = 0;
		int light = 0;
		while (index < str.length) {
			if (str[index] == 'X') {// 1）i位置是X，既不放灯，也不需要点亮，去i+1位置做决定
				index++;
			} else {// 2）i -> .
				light++; // 一定会放灯，可能是i，也可能是i+1位置
				if (index + 1 == str.length) {// i+1位置没有字符，结束了
					break;
				} else {
					if (str[index + 1] == 'X') {// 2.1)i放灯，i+2做决定
						index = index + 2;// i->.,i+1->X ，前面light++表示已经放过灯了，其实是i位置放的灯，去i+2位置继续做决定
					} else {// 2.2)i+1放灯，i+3做决定；2.3）i+1放灯，i+3做决定
						index = index + 3;// i->.,i+1->.,i+2->X或者. ，前面light++表示已经放过灯了，其实是i+1位置放的灯，去i+3位置继续做决定(贪心，当前情况做出最优决定)
					}
				}
			}
		}
		return light;
	}

	// for test
	public static String randomString(int len) {
		char[] res = new char[(int) (Math.random() * len) + 1];
		for (int i = 0; i < res.length; i++) {
			res[i] = Math.random() < 0.5 ? 'X' : '.';
		}
		return String.valueOf(res);
	}

	public static void main(String[] args) {
		int len = 20;
		int testTime = 100000;
		for (int i = 0; i < testTime; i++) {
			String test = randomString(len);
			int ans1 = minLight1(test);
			int ans2 = minLight2(test);
			if (ans1 != ans2) {
				System.out.println("oops!");
			}
		}
		System.out.println("finish!");
	}
}

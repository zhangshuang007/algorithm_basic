package msb.class12;

import java.util.Arrays;
import java.util.HashMap;

/**
 * 给定一个字符串str，给定一个字符串类型的数组arr。
 * arr里的每一个字符串，代表一张贴纸，你可以把单个字符剪开使用，目的是拼出str来。
 * 返回需要至少多少张贴纸可以完成这个任务。
 * 例子：str= "babac"，arr = {"ba","c","abcd"}
 * 至少需要两张贴纸"ba"和"abcd"，因为使用这两张贴纸，把每一个字符单独剪开，含有2个a、2个b、1个c。是可以拼出str的。所以返回2。
 * @Date 2021/6/9 10:33
 */
public class Code02_StickersToSpellWord {
	// 有重复过程的，如
	// 贴纸"aa" 和 "bb"
	// "aaaabbbbcc"->经过"aa"->经过"bb"->剩下"aabbcc"
	// "aaaabbbbcc"->经过"bb"->经过"aa"->剩下"aabbcc"

	// 伪代码
	/*public static int A(String str, String[] arr) {
		mins();
	}

	public static int mins(String rest, String[] arr) {
		if(rest.equals("")) {
			return 0;
		}
		// 搞定rest的第一张贴纸是什么
		int next = 0;
		for (String first: arr) {
			rest-first->nextRst;// 减法操作
			int cur =  mins(nextRest,arr);
			next = Math.min(next, cur);
		}
		return next +1;
	}*/

	// stickers 贴纸，target 目标
	public static int minStickers1(String[] stickers, String target) {
		int n = stickers.length;
		// map在这个问题中可以等价表示stickers -> [26] [26] [26]... n个26一维数组，
		// 因为我们不关心每张贴纸是什么，只关注每张贴纸分别由多少个字母构成
		// 比如其中一个贴纸"abadd" 可以用[2 1 0 2 0..]表示
		int[][] map = new int[n][26];
		for (int i = 0; i < n; i++) {
			char[] str = stickers[i].toCharArray();
			for (char c : str) {
				map[i][c - 'a']++;
			}
		}
		HashMap<String, Integer> dp = new HashMap<>();
		dp.put("", 0);// 空字符返回0张贴纸
		return process1(dp, map, target);
	}

	// dp 傻缓存，如果rest已经算过了，直接返回dp中的值
	// rest 剩余的目标，唯一可变参数
	// map固定
	// 0..N每一个字符串所含字符的词频统计
	// 返回值是-1，map中的贴纸 怎么都无法搞定rest
	public static int process1(HashMap<String, Integer> dp, int[][] map, String rest) {
		if (dp.containsKey(rest)) {
			return dp.get(rest);
		}
		// 以下就是正式的递归调用过程
		int ans = Integer.MAX_VALUE;// ans -> 搞定rest使用的最少的贴纸数量
		int n = map.length;// n种贴纸
		int[] tmap = new int[26];// tmap 去替代rest
		char[] target = rest.toCharArray();
		for (char c : target) {
			tmap[c - 'a']++;
		}

		// map(n种贴纸) -> tmap(剩余字符)

		// "kkkkkyyyyyzzzz"
		for (int i = 0; i < n; i++) {
			// 枚举当前第一张贴纸是谁

			// 当前贴纸需要至少包含target中的一种字符，否则如果贴纸为"xyz",匹配形如"bbbcckk"的剩余目标，匹配完还剩是"bbbcckk"，又匹配"xyz",死循环，堆栈溢出
			// 此处选取了一种方式，枚举所有的贴纸优先匹配target中第一种剩余字符，
			// target[0]-'a' 表示target零位置当前字符
			// map[i][target[0] - 'a']，表示target零位置当前字符的词频，如果target="bbbbbcckkkkk"，则map[i][target[0] - 'a'] ==5
			// 如上面第一个字符是'b'，如果map[i]贴纸不包含b，则map[i][1]==0,所以就跳过，没有继续的必要
			if (map[i][target[0] - 'a'] == 0) {
				continue;
			}
			StringBuilder sb = new StringBuilder();
			// i号贴纸,j枚举a~z字符
			for (int j = 0; j < 26; j++) {// 伪代码中的减法
				if (tmap[j] > 0) { // j这个字符是target需要的
					for (int k = 0; k < Math.max(0, tmap[j] - map[i][j]); k++) {
						sb.append((char) ('a' + j));
					}
				}
			}

			String s = sb.toString();
			int tmp = process1(dp, map, s);// 剩余的字符串被后续贴纸搞定的最小数量
			if (tmp != -1) {
				ans = Math.min(ans, 1 + tmp);
			}
		}
		// ans 系统最大，说明枚举所有贴纸作为第一张贴纸都搞不定rest，返回-1
		dp.put(rest, ans == Integer.MAX_VALUE ? -1 : ans);
		return dp.get(rest);
	}


	// f(srt ,i , arr)，arr贴纸数组（固定），str目标串，当前来到i号贴纸
	// f(str, 0, arr)->主函数
	// str="aaaabbb" ,0号贴纸"ab",1号贴纸"ac"
	// 1)使用0张ab贴纸之后->f("aaaabbb",1,arr)
	// 2)使用1张ab贴纸之后->f("aabbb",1,arr)
	// 3)使用2张ab贴纸之后->f("bbb",1,arr)
	// 所以第二种尝试有2个可变参数，不如方法一只有一个可变参数优秀，因为参数多缓存命中率低，此方法看看就行
	public static int minStickers2(String[] stickers, String target) {
		int n = stickers.length;
		int[][] map = new int[n][26];
		for (int i = 0; i < n; i++) {
			char[] str = stickers[i].toCharArray();
			for (char c : str) {
				map[i][c - 'a']++;
			}
		}
		char[] str = target.toCharArray();
		int[] tmap = new int[26];
		for (char c : str) {
			tmap[c - 'a']++;
		}
		HashMap<String, Integer> dp = new HashMap<>();
		int ans = process2(map, 0, tmap, dp);
		return ans;
	}

	public static int process2(int[][] map, int i, int[] tmap, HashMap<String, Integer> dp) {
		StringBuilder keyBuilder = new StringBuilder();
		keyBuilder.append(i + "_");
		for (int asc = 0; asc < 26; asc++) {
			if (tmap[asc] != 0) {
				keyBuilder.append((char) (asc + 'a') + "_" + tmap[asc] + "_");
			}
		}
		String key = keyBuilder.toString();
		if (dp.containsKey(key)) {
			return dp.get(key);
		}
		boolean finish = true;
		for (int asc = 0; asc < 26; asc++) {
			if (tmap[asc] != 0) {
				finish = false;
				break;
			}
		}
		if (finish) {
			dp.put(key, 0);
			return 0;
		}
		if (i == map.length) {
			dp.put(key, -1);
			return -1;
		}
		int maxZhang = 0;
		for (int asc = 0; asc < 26; asc++) {
			if (map[i][asc] != 0 && tmap[asc] != 0) {
				maxZhang = Math.max(maxZhang, (tmap[asc] / map[i][asc]) + (tmap[asc] % map[i][asc] == 0 ? 0 : 1));
			}
		}
		int[] backup = Arrays.copyOf(tmap, tmap.length);
		int min = Integer.MAX_VALUE;
		int next = process2(map, i + 1, tmap, dp);
		tmap = Arrays.copyOf(backup, backup.length);
		if (next != -1) {
			min = next;
		}
		for (int zhang = 1; zhang <= maxZhang; zhang++) {
			for (int asc = 0; asc < 26; asc++) {
				tmap[asc] = Math.max(0, tmap[asc] - (map[i][asc] * zhang));
			}
			next = process2(map, i + 1, tmap, dp);
			tmap = Arrays.copyOf(backup, backup.length);
			if (next != -1) {
				min = Math.min(min, zhang + next);
			}
		}
		int ans = min == Integer.MAX_VALUE ? -1 : min;
		dp.put(key, ans);
		return ans;
	}

	public static void main(String[] args) {
		String[] arr = {"aaaa","bbaa","ccddd"};
		String str = "abcccccdddddbbbaaaaa";
		System.out.println(minStickers1(arr, str));
		System.out.println(minStickers2(arr, str));
	}

}

package msb.class11;

import java.util.ArrayList;
import java.util.List;

/**
 * 1、打印一个字符串的全部排列
 * 2、打印一个字符串的全部排列，要求不要出现重复的排列(分支限界)
 * @Author zhangshuang
 * @Date 2021/6/1 10:49
 */
public class Code03_PrintAllPermutations {

	public static ArrayList<String> permutation(String str) {
		ArrayList<String> res = new ArrayList<>();
		if (str == null || str.length() == 0) {
			return res;
		}
		char[] chs = str.toCharArray();
		process(chs, 0, res);
		return res;
	}

	// str[0..i-1]已经做好决定的
	// str[i...]都有机会来到i位置
	// i终止位置，str当前的样子，就是一种结果->ans
	public static void process(char[] str, int i, ArrayList<String> ans) {
		if (i == str.length) {
			ans.add(String.valueOf(str));
			return;
		}
		// 如果i没有终止，i...都可以来到i位置
		for (int j = i; j < str.length; j++) {// j i后面所有的字符都有机会
			swap(str, i, j);
			process(str, i + 1, ans);
			swap(str, i, j);// 恢复现场
		}
	}

	public static ArrayList<String> permutationNoRepeat(String str) {
		ArrayList<String> res = new ArrayList<>();
		if (str == null || str.length() == 0) {
			return res;
		}
		char[] chs = str.toCharArray();
		process2(chs, 0, res);
		return res;
	}

	// 分支限界，比展示完所有可能性再过滤的方法要快
	// str[0..i-1]已经做好决定的
	// str[i...]都有机会来到i位置
	// i终止位置，str当前的样子，就是一种结果->ans
	public static void process2(char[] str, int i, ArrayList<String> ans) {
		if (i == str.length) {
			ans.add(String.valueOf(str));
			return;
		}
		// 如果不止26种字符，visit搞成hash表的形式即可
		// visit[0] 0位置a这个字符有没有使用过，
		// visit[1] 1位置b这个字符有没有使用过
		boolean[] visit = new boolean[26]; // visit[0 1 .. 25]
		for (int j = i; j < str.length; j++) {
			// str[j] = 'a'  -> 0  visit[0] -> 'a'
			// str[j] = 'z'  -> 25  visit[25] -> 'z'
			if (!visit[str[j] - 'a']) {// 这种字符没出现过才去走支路
				visit[str[j] - 'a'] = true;// 登记一下，以后再来就算使用过了
				swap(str, i, j);
				process2(str, i + 1, ans);
				swap(str, i, j);
			}
		}
	}

	public static void swap(char[] chs, int i, int j) {
		char tmp = chs[i];
		chs[i] = chs[j];
		chs[j] = tmp;
	}

	public static void main(String[] args) {
		String s = "aac";
		List<String> ans1 = permutation(s);
		for (String str : ans1) {
			System.out.println(str);
		}
		System.out.println("=======");
		List<String> ans2 = permutationNoRepeat(s);
		for (String str : ans2) {
			System.out.println(str);
		}

	}

}

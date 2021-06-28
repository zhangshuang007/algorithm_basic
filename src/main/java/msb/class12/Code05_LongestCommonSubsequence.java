package msb.class12;

/**
 * 多样本位置全对应的尝试模型（模型3）
 * 解法：一个样本做行，一个样本做列
 *
 * 两个字符串的最长公共子序列问题
 * "ab1cd2ef345gh"和"OPQ123RS4tx5YZ" 最长公共子序列是"12345"，所以返回长度5
 * @Date 2021/6/9 10:33
 */
public class Code05_LongestCommonSubsequence {
	// str1="a123bc"   str2="12dea3f"  最长公共子序列 "123"
	//
	// dp[i][j] str1[0..i]与str2[0..j]最长公共子序列多长，目标是返回dp最右下角的值，
	// 即dp[5][6],str1 0到5上的字符串，str2 0到6上的字符串，
	// 先填dp[i][j]第一行，然后再填第一列
	// 1)最大公共子序列可能既不以str1[i]结尾，也不以str2[j]结尾，上面的"123" 不以c结尾，也不以f结尾
	// str1[0..i-1]与str2[0..j-1]有用，则dp[i][j]的解来自于左上角，即dp[i-1][j-1]
	// 2)最大公共子序列可能以str1[i]结尾，不以str2[j]结尾,"ab123"和"c123ef",则解来自于dp[i][j]左侧格子的解，即dp[i][j-1]
	// 3)最大公共子序列可能不以str1[i]结尾，以str2[j]结尾,"a123bc"和"ef123",则解来自于dp[i][j]上方的解，即dp[i-1][j]
	// 4)最大公共子序列可能以str1[i]结尾，又以str2[j]结尾,"a12c3"和"d12ef3",则解来自于dp[i-1][j-1]+1，需要满足str1[i]==str2[j]条件
	// 最后从4种可能性中取最大值：
	// 需要满足str1[i]==str2[j]条件，可能性4的值=可能性1的值+1，先比较可能性2与3的大小，获胜者与4比较最大值
	// 如果条件不成立，理应先比较可能性2与3的大小，获胜者与可能性1取最大值，但其实不用考虑可能性1，因为可能性2和3肯定>=可能性1，
	// 因为每个格子填值都依赖于左上角，左侧，和上方的格子的最大值，可能性1在2的上方，可能性1在3的左边，填2或3的格子的时候已经比较过1了，所以不考虑1
	public static int lcse(char[] str1, char[] str2) {
		int[][] dp = new int[str1.length][str2.length];
		dp[0][0] = str1[0] == str2[0] ? 1 : 0;
		for (int i = 1; i < str1.length; i++) {//填第0列所有值,从第一次相等后面都补1，否则补0
			dp[i][0] = Math.max(dp[i - 1][0], str1[i] == str2[0] ? 1 : 0);
		}
		for (int j = 1; j < str2.length; j++) {//填第0行所有值
			dp[0][j] = Math.max(dp[0][j - 1], str1[0] == str2[j] ? 1 : 0);
		}
		for (int i = 1; i < str1.length; i++) {
			for (int j = 1; j < str2.length; j++) {
				dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
				if (str1[i] == str2[j]) {
					dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - 1] + 1);
				}
//				else {//可以不考虑可能性1
//					dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - 1]);
//				}
			}
		}
		return dp[str1.length - 1][str2.length - 1];
	}

	public static void main(String[] args) {

	}

}

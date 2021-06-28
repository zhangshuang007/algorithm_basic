package msb.class12;

import java.util.HashMap;

/**
 * 给定数组arr，arr中所有的值都为正数且不重复
 * 每个值代表一种面值的货币，每种面值的货币可以使用任意张
 * 再给定一个整数 aim，代表要找的钱数
 * 求组成 aim 的方法数
 * @Date 2021/6/9 10:25
 */
public class Code09_CoinsWay {

	// 一、暴力递归
	// arr中都是正数且无重复值，返回组成aim的方法数
	public static int ways1(int[] arr, int aim) {
		if (arr == null || arr.length == 0 || aim < 0) {
			return 0;
		}
		return process(arr, 0, aim);
	}

	// 可以自由使用arr[index...]的面值，每一种面值都可以使用任意张，组成rest这么多钱，返回方法数
	public static int process(int[] arr, int index, int rest) {
		if (index == arr.length) { // 无面值的时候
			return rest == 0 ? 1 : 0;
		}
		// 有面值的时候
		int ways = 0;
		// arr[index] 当前面值
		for (int zhang = 0; zhang * arr[index] <= rest; zhang++) {
			ways += process(arr, index + 1, rest - zhang * arr[index]);
		}
		return ways;
	}

	// 二、暴力递归改记忆化搜索（已经算是动态规划了）
	//arr =[10,100,50....] ,aim =1000,是否有重复的数据
	// 取5张10,0张100，1张50，之后过程函数index从3开始，即f(3,900)
	// 取0张10,1张100，0张50，之后过程函数index从3开始，也是f(3,900)
	public static int ways2(int[] arr, int aim) {
		if (arr == null || arr.length == 0 || aim < 0) {
			return 0;
		}
		//可以用hash做缓存表，也可以用一个二维数组做缓存，行index范围是0~N（N=arr.length），rest范围是0~aim
		HashMap<String, Integer> map = new HashMap<>();
		// index = 3  rest = 900    map key "3_900" int

		int[][] dp = new int[arr.length+1][aim+1];
		// 一开始所有的过程，都没有计算呢
		// dp[..][..] = -1
		for (int i = 0; i < dp.length; i++) {
			for (int j = 0; j < dp[0].length; j++) {
				dp[i][j] = -1;
			}
		}
		return process2(arr, 0, aim, dp);
	}

	// 如果index和rest的参数组合，是没算过的，dp[index][rest] == -1
	// 如果index和rest的参数组合，是算过的，dp[index][rest] > -1
	public static int process2(int[] arr, int index, int rest, int[][] dp) {
		if(dp[index][rest] != -1) {
			return dp[index][rest];
		}

		if (index == arr.length) { // 无面值的时候
			dp[index][rest] = rest == 0 ? 1 : 0;
			return dp[index][rest];
		}
		// 有面值的时候
		int ways = 0;
		// arr[index] 当前面值
		for (int zhang = 0; zhang * arr[index] <= rest; zhang++) {
			ways += process2(arr, index + 1, rest - zhang * arr[index], dp);
		}
		dp[index][rest] = ways;// 所有的return之前都放到缓存里
		return ways;
	}


	// 三、改经典动态规划
	public static int ways3(int[] arr, int aim) {
		if (arr == null || arr.length == 0 || aim < 0) {
			return 0;
		}
		int N = arr.length;
		int[][] dp = new int[N + 1][aim + 1];

		dp[N][0] = 1;// dp[N][1..aim]=0
		for (int index = N - 1; index >= 0; index--) { // 大顺序 从下往上
			for (int rest = 0; rest <= aim; rest++) {// 每一行从左往右或者从右往左都行，因为每个位置不依赖本行的值，只依赖下一行的值
				int ways = 0;
				// 有枚举行为，后续可以优化
				for (int zhang = 0; zhang * arr[index] <= rest; zhang++) {
					ways += dp[index + 1][rest - zhang * arr[index]];
				}
				dp[index][rest] = ways;
			}
		}

		return dp[0][aim];
	}

	// 四、替代枚举行为
	public static int ways4(int[] arr, int aim) {
		if (arr == null || arr.length == 0 || aim < 0) {
			return 0;
		}
		int N = arr.length;
		int[][] dp = new int[N + 1][aim + 1];
		dp[N][0] = 1;// dp[N][1..aim]=0
		for (int index = N - 1; index >= 0; index--) { // 大顺序 从下往上
			for (int rest = 0; rest <= aim; rest++) {
				dp[index][rest] = dp[index + 1][rest];
				if (rest - arr[index] >= 0) {
					dp[index][rest] += dp[index][rest - arr[index]];
				}
			}
		}
		return dp[0][aim];
	}




	public static void main(String[] args) {
		int[] arr = { 5, 2, 3, 1 };
		int sum = 350;
		System.out.println(ways1(arr, sum));
		System.out.println(ways2(arr, sum));
		System.out.println(ways3(arr, sum));
		System.out.println(ways4(arr, sum));
	}

}

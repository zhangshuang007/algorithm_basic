package msb.class11;

/**
 * 从左往右的尝试模型2
 * 给定两个长度都为N的数组weights和values，
 * weights[i]和values[i]分别代表 i号物品的重量和价值。
 * 给定一个正数bag，表示一个载重bag的袋子，
 * 你装的物品不能超过这个重量。
 * 返回你能装下最多的价值是多少?
 * @Date 2021/6/1 15:12
 */
public class Code07_Knapsack {

	public static int getMaxValue(int[] w, int[] v, int bag) {
		return process(w, v, 0, 0, bag);
	}

	// 不变：w[] v[] bag袋子的总载重
	// index... 最大价值
	// 0...index-1上做了货物的选择，使得你已经达到的重量是多少
	// 如果返回-1，认为没有方案
	// 如果不返回-1，认为返回的信息是真实价值
	public static int process(int[] w, int[] v, int index, int alreadyW, int bag) {
		if (alreadyW > bag) {
			return -1;
		}
		// 重量没超
		if (index == w.length) {// 没货了，因为要的是从index往后的货产生价值
			return 0;
		}
		int p1 = process(w, v, index + 1, alreadyW, bag);// index的位置的货没要
		int p2next = process(w, v, index + 1, alreadyW + w[index], bag);
		int p2 = -1;
		if (p2next != -1) {
			p2 = v[index] + p2next;
		}
		return Math.max(p1, p2);

	}

	public static int maxValue(int[] w, int[] v, int bag) {
		return process(w, v, 0, bag);
	}

	// 只剩下rest的空间了，
	// index...货物自由选择，但是不要超过rest的空间
	// 返回能够获得的最大价值
	public static int process(int[] w, int[] v, int index, int rest) {
		if (rest <= 0) { // base case 1
			return 0;
		}
		// rest >=0
		if (index == w.length) { // base case 2
			return 0;
		}
		// 有货也有空间
		int p1 = process(w, v, index + 1, rest);
		int p2 = Integer.MIN_VALUE;
		if (rest >= w[index]) {
			p2 = v[index] + process(w, v, index + 1, rest - w[index]);
		}
		return Math.max(p1, p2);
	}

	public static int dpWay(int[] w, int[] v, int bag) {
		int N = w.length;
		int[][] dp = new int[N + 1][bag + 1];
		for (int index = N - 1; index >= 0; index--) {
			for (int rest = 1; rest <= bag; rest++) {
				dp[index][rest] = dp[index + 1][rest];
				if (rest >= w[index]) {
					dp[index][rest] = Math.max(dp[index][rest], v[index] + dp[index + 1][rest - w[index]]);
				}
			}
		}
		return dp[0][bag];
	}

	public static void main(String[] args) {
		int[] weights = { 3, 2, 4, 7 };
		int[] values = { 5, 6, 3, 19 };
		int bag = 11;
		System.out.println(getMaxValue(weights, values, bag));
		System.out.println(maxValue(weights, values, bag));
		System.out.println(dpWay(weights, values, bag));
	}

}

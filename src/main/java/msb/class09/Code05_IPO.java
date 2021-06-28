package msb.class09;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 输入: 正数数组costs、正数数组profits、正数K、正数W
 * costs[i]表示i号项目的花费
 * profits[i]表示i号项目在扣除花费之后还能挣到的钱(利润)
 * K表示你只能串行的最多做k个项目
 * W表示你初始的资金
 * 说明: 每做完一个项目，马上获得的收益，可以支持你去做下一个项目。不能并行的做项目。
 * 输出：你最后获得的最大钱数。
 * @Date 2021/5/14 14:29
 */
public class Code05_IPO {

	public static int findMaximizedCapital(int K, int W, int[] Profits, int[] Capital) {
		PriorityQueue<Program> minCostQ = new PriorityQueue<>(new MinCostComparator());// 小根堆（花费）
		PriorityQueue<Program> maxProfitQ = new PriorityQueue<>(new MaxProfitComparator());// 大根堆（利润）
		for (int i = 0; i < Profits.length; i++) {
			minCostQ.add(new Program(Profits[i], Capital[i]));
		}
		for (int i = 0; i < K; i++) {// 最多做够K个项目
			while (!minCostQ.isEmpty() && minCostQ.peek().c <= W) {//当前W能解锁的小根堆中的项目都进大根堆先
				maxProfitQ.add(minCostQ.poll());
			}
			// 1.本钱W不够解锁任何项目，2.可以干7个项目，但是干完2个项目之后发现最新的W不够解锁余下的项目；
			// 3.只有3个项目，但是K=10；   无项目可做，这些情况都需要提前返回
			if (maxProfitQ.isEmpty()) {
				return W;
			}
			W += maxProfitQ.poll().p;// 选取大根堆中对顶项目获得最大利润并弹出，同时将利润加到W上
		}
		return W;
	}

	public static class Program {
		public int p;// 利润
		public int c;// 花费

		public Program(int p, int c) {
			this.p = p;
			this.c = c;
		}
	}

	public static class MinCostComparator implements Comparator<Program> {

		@Override
		public int compare(Program o1, Program o2) {
			return o1.c - o2.c;
		}

	}

	public static class MaxProfitComparator implements Comparator<Program> {

		@Override
		public int compare(Program o1, Program o2) {
			return o2.p - o1.p;
		}

	}

}

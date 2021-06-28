package msb.class11;

/**
 * 范围上尝试的模型
 * 给定一个整型数组arr，代表数值不同的纸牌排成一条线，
 * 玩家A和玩家B依次拿走每张纸牌，
 * 规定玩家A先拿，玩家B后拿，
 * 但是每个玩家每次只能拿走最左或最右的纸牌，
 * 玩家A和玩家B都绝顶聪明。请返回最后获胜者的分数。
 * @Date 2021/6/2 9:39
 */
public class Code08_CardsInLine {

	public static int win1(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		// 先手和后手在0~N-1上获得分数，谁赢了就是获胜者的分数
		return Math.max(f(arr, 0, arr.length - 1), s(arr, 0, arr.length - 1));
	}

	// 先手函数
	public static int f(int[] arr, int L, int R) {
		if (L == R) {// 只剩一张牌
			return arr[L];
		}
		// 选对自己最有利的
		return Math.max(arr[L] + s(arr, L + 1, R), arr[R] + s(arr, L, R - 1));
	}

	// 后手函数
	// L..R
	public static int s(int[] arr, int L, int R) {
		if (L == R) {// 只有一张牌了，你是后手，不轮到你拿
			return 0;
		}
		// 你没得选，对手扔给你的这两个中的最小
		return Math.min(
				f(arr, L + 1, R), // 对手挑了arr[L]，在L+1..R轮到你先手
				f(arr, L, R - 1)// 对手挑了arr[R]，在L..R+1轮到你先手
									// 对手留给你的一定是最差的结果，是零和博弈
		);
		// 后手过程举个例子：100,1,7->{0,1,2}位置
		// 对手B选100,   f(L+1,R)    1,7->max{f(1,2),f(1,1)}=7
		// 对手B选7,     f(L,R-1)  100,1->max{f(0,0),f(1,1)}=100
		// min{f(L+1,R),f(L,R-1)}=min{f(1,2),f(0,1)}=min{max{arr[1]+s(2,2),arr[2]+s(1,1)}, max{arr[0]+s(1,1),arr[1]+s(0,0)}}=min{max{1,7},max{100,1}}=7
		// 对手B一定留给你后续的最小让你挑 即第一种情况，因为第一种情况f 1,7返回的值是7，第二种情况f 100,1返回的值是100，二者取最小值
	}

	// 非递归
	public static int win2(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int[][] f = new int[arr.length][arr.length];
		int[][] s = new int[arr.length][arr.length];
		for (int j = 0; j < arr.length; j++) {
			f[j][j] = arr[j];
			for (int i = j - 1; i >= 0; i--) {
				f[i][j] = Math.max(arr[i] + s[i + 1][j], arr[j] + s[i][j - 1]);
				s[i][j] = Math.min(f[i + 1][j], f[i][j - 1]);
			}
		}
		return Math.max(f[0][arr.length - 1], s[0][arr.length - 1]);
	}

	public static void main(String[] args) {
		int[] arr = { 1, 9, 1 };
		System.out.println(win1(arr));
		System.out.println(win2(arr));

		int[] arr2 = { 4, 7, 9, 5 };
		// A 4 9
		// B 7 5
		System.out.println(f(arr2,0,3));// 先手 获13分
		System.out.println(s(arr2,0,3));// 后手 获12分
	}

}

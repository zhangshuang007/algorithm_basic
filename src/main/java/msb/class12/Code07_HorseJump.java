package msb.class12;

/**
 * 请同学们自行搜索或者想象一个象棋的棋盘，
 * 然后把整个棋盘放入第一象限，棋盘的最左下角是(0,0)位置
 * 那么整个棋盘就是横坐标上9条线、纵坐标上10条线的区域
 * 给你三个 参数 x，y，k
 * 返回“马”从(0,0)位置出发，必须走k步
 * 最后落在(x,y)上的方法数有多少种?
 * @Date 2021/6/18 8:45
 */
public class Code07_HorseJump {

	// 9*10
	// 0~9 y
	// 0~8 x
	public static int ways(int a, int b, int step) {
		return f(0, 0, step, a, b);
	}

	// 正向思维：马在(i,j)位置，还有step步要去跳
	// 返回最终来到(a,b)的方法数
	public static int f(int i, int j, int step, int a, int b) {
		if (i < 0 || i > 8 || j < 0 || j > 9) {
			return 0;
		}
		if (step == 0) {
			return (i == a && j == b) ? 1 : 0;
		}
		return f(i - 2, j + 1, step - 1, a, b) 
				+ f(i - 1, j + 2, step - 1, a, b) 
				+ f(i + 1, j + 2, step - 1, a, b)
				+ f(i + 2, j + 1, step - 1, a, b) 
				+ f(i + 2, j - 1, step - 1, a, b) 
				+ f(i + 1, j - 2, step - 1, a, b)
				+ f(i - 1, j - 2, step - 1, a, b) 
				+ f(i - 2, j - 1, step - 1, a, b);

	}

	public static int waysdp(int a, int b, int s) {
		// (i,j,0~ step)
		int[][][] dp = new int[9][10][s+1];
		dp[a][b][0] = 1;
		for(int step = 1 ; step <= s; step++) { // 按层来
			for(int i = 0 ; i < 9;i++) {
				for(int j = 0 ; j < 10; j++) {
					dp[i][j][step] = getValue(dp,i - 2, j + 1, step - 1) 
							+ getValue(dp,i - 1, j + 2, step - 1) 
							+ getValue(dp,i + 1, j + 2, step - 1)
							+ getValue(dp,i + 2, j + 1, step - 1) 
							+ getValue(dp,i + 2, j - 1, step - 1) 
							+ getValue(dp,i + 1, j - 2, step - 1)
							+ getValue(dp,i - 1, j - 2, step - 1) 
							+ getValue(dp,i - 2, j - 1, step - 1);
				}
			}
		}
		return dp[0][0][s];
	}

	// 在dp表中，得到dp[i][j][step]的值，但如果(i，j)位置越界的话，返回0；
	public static int getValue(int[][][] dp, int i, int j, int step) {
		if (i < 0 || i > 8 || j < 0 || j > 9) {
			return 0;
		}
		return dp[i][j][step];
	}

	public static int ways1(int a, int b, int step) {
		return f1(a, b, step);
	}

	// 逆向思维，马从(x,y)出发，有k步要走，并且一定要走完，最终来到(0,0)位置的方法数是多少
	public static int f1(int x, int y, int k) {
		if(k == 0) {
			if(x == 0 && y == 0) {
				return 1;
			}
			return 0;
		}
		// 还有步数要走
		if(x<0 || x >8 || y<0 || y>9) {
			return 0;
		}

		return f1(x + 2, y + 1, k - 1)
				+ f1(x + 1, y + 2, k - 1)
				+ f1(x - 2, y + 1, k - 1)
				+ f1(x - 1, y + 2, k - 1)
				+ f1(x - 2, y - 1, k - 1)
				+ f1(x - 1, y - 2, k - 1)
				+ f1(x + 2, y - 1, k - 1)
				+ f1(x + 1, y - 2, k - 1);
	}

	public static int waysdp1(int x, int y, int k) {
		// (i,j,0~ step)
		int[][][] dp = new int[9][10][k+1];
		dp[0][0][0] = 1;// dp[..][..][0] = 0

		for (int level = 1; level <= k; level++) {
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 10; j++) {
					dp[i][j][level] = getValue1(dp,i-2,j+1,level-1)
							+ getValue1(dp,i - 1, j + 2, level - 1)
							+ getValue1(dp,i + 1, j + 2, level - 1)
							+ getValue1(dp,i + 2, j + 1, level - 1)
							+ getValue1(dp,i + 2, j - 1, level - 1)
							+ getValue1(dp,i + 1, j - 2, level - 1)
							+ getValue1(dp,i - 1, j - 2, level - 1)
							+ getValue1(dp,i - 2, j - 1, level - 1);

				}
			}
		}
		return dp[x][y][k];
	}

	public static int getValue1(int[][][] dp, int x, int y, int step) {
		if(x<0 || x>8 || y < 0 || y>9) {
			return 0;
		}
		return dp[x][y][step];
	}

	public static void main(String[] args) {
		int x = 8;
		int y = 6;
		int step = 10;
		System.out.println(ways(x, y, step));
		System.out.println(waysdp(x, y, step));
		System.out.println(ways1(x, y, step));
		System.out.println(waysdp1(x, y, step));
	}
}

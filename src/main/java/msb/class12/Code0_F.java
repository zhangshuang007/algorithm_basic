package msb.class12;

/**
 * 求斐波那切数列
 * @Author Administrator
 * @Date 2021/6/3 18:09
 */
public class Code0_F {
    public static int f(int N) {
        if(N==1) {
            return 1;
        }
        if(N==2) {
            return 1;
        }
        return f(N-1) + f(N-2);
    }
}

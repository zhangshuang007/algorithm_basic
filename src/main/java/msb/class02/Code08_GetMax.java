package msb.class02;

/**
 * @Description TODO
 * @Author Administrator
 * @Date 2020/4/20 14:03
 */
public class Code08_GetMax {
    // 求arr中的最大值
    public static int getMax(int[] arr) {
        return process(arr, 0, arr.length - 1);
    }

    // arr[L..R]范围上求最大值  L ... R   N
    public static int process(int[] arr, int L, int R) {
//        if (L == R) { // arr[L..R]范围上只有一个数，直接返回，base case
//            return arr[L];
//        }
//        int mid = L + ((R - L) >> 1); // 中点   	1
//        int leftMax = process(arr, L, mid);
//        int rightMax = process(arr, mid + 1, R);
//        return Math.max(leftMax, rightMax);

        if(L == R) {
            return arr[L];
        }
        int mid = L + ((R-L)>>1);
        int leftMax = process(arr, L , mid);
        int rightMax = process(arr, mid+1, R);
        return Math.max(leftMax, rightMax);
    }

    // for test
    private static int[] generateRandomArray(int maxSize, int maxValue) {
        // Math.random() [0,1)
        // Math.random()*N [0,N)
        // (int)(Math.random()*N) [0,N-1]
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            // [-? , +?]
            arr[i] = (int)((maxValue+1) * Math.random()) - (int)(maxValue * Math.random());
        }
        return arr;
    }

    // for test
    private static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();

    }

    public static void main(String[] args) {
        int maxSize = 7;
        int maxValue = 100;
        int[] arr = generateRandomArray(maxSize, maxValue);
        printArray(arr);
        System.out.println(getMax(arr));
    }
}

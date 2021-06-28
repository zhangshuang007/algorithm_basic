package msb.class01;

/**
 * @Description
 * @Author zhangshuang
 * @Date 2021/1/20 13:50
 */
public class Code07_EvenTimesOddTimes {
    // arr中，只有一种数，出现奇数次，其他数都出现了偶数次，那么找到并打印这种数
    public static void printOddTimesNum1(int[] arr) {
        int eor = 0;
        for (int i = 0; i < arr.length; i++) {
            eor = eor ^ arr[i];
        }
        System.out.println(eor);
    }

    // arr中，有两种数(a,b且a!=b)，出现奇数次，其他数都出现了偶数次，怎么找到并打印这两种数
    public static void printOddTimesNum2(int[] arr) {
        int eor = 0;
        for (int i = 0; i < arr.length; i++) {
            eor ^= arr[i];
        }
        // eor = a ^ b
        // eor != 0
        // eor必然有一个位置上是1
        // 0110010000
        // 0000010000
        // a和b分别属于两个不同的集合（eor=a^b !=0随便找一个第n为位置1，a属于第n位为1的集合，b属于第n位为0的集合）
        int rightOne = eor & (~eor + 1); // 提取出最右的1
        int onlyOne = 0; // eor'
        for (int i = 0 ; i < arr.length;i++) {
            //  arr[1] =  111100011110000
            // rightOne=  000000000010000
            if ((arr[i] & rightOne) != 0) {// 把第n位为1的数据取异或，其他数都是偶数个，最终结果为onlyOne=a
                onlyOne ^= arr[i];
            }
        }
        System.out.println(onlyOne + " " + (eor ^ onlyOne));
    }

    // 数一数整数N中二进制1的个数
    public static int bit1counts(int N) {
        int count = 0;

        //   011011010000
        //   000000010000     1

        //   011011000000
        //



        while(N != 0) {
            int rightOne = N & ((~N) + 1);
            count++;
            N ^= rightOne;
            // N -= rightOne
        }


        return count;

    }


    public static void main(String[] args) {
        int a = 5;
        int b = 7;

        a = a ^ b;
        b = a ^ b;
        a = a ^ b;

        System.out.println(a);
        System.out.println(b);

        int[] arr1 = { 3, 3, 2, 3, 1, 1, 1, 3, 1, 1, 1 };
        printOddTimesNum1(arr1);

        int[] arr2 = { 4, 3, 4, 2, 2, 2, 4, 1, 1, 1, 3, 3, 1, 1, 1, 4, 2, 2 };
        printOddTimesNum2(arr2);

    }
}

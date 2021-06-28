package msb.class01;

import java.util.Arrays;

/**
 * @Description 局部最小值问题
 * @Author zhangshuang
 * @Date 2021/1/19 17:30
 */
public class Code06_BSAwesome {
    public static int getLessIndex(int[] arr) {
        if (arr == null || arr.length == 0) {
            return -1; // no exist
        }
        if (arr.length == 1 || arr[0] < arr[1]) {
            return 0;
        }
        if (arr[arr.length - 1] < arr[arr.length - 2]) {
            return arr.length - 1;
        }
        int left = 1;
        int right = arr.length - 2;
        int mid = 0;
        while (left < right) {
            mid = (left + right) / 2;
            if(arr[mid] > arr[mid-1]){
                right = mid - 1;
            } else if(arr[mid] < arr[mid-1]) {
                left = mid+1;
            } else {
                return mid;
            }
        }
        return left;
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int maxSize = 10;
        int maxValue = 100;
        int[] arr = generateRandomArray(maxSize, maxValue);
        printArray(arr);
        System.out.println(getLessIndex(arr));
    }
}

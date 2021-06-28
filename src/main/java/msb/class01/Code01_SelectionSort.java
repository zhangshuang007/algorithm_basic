package msb.class01;

import java.util.Arrays;

/**
 * @Description 选择排序O(N²)，额外空间复杂度O(1),3个变量，for循环跳出就会释放，然后又重新申请
 * 如果时间复杂度都
 * @Author Administrator
 * @Date 2020/4/20 14:00
 */
public class Code01_SelectionSort {

    // 在一个长度为 n的无序数组中，第一次遍历 n-1 个数找到最小的和第一个数交换。
    // 第二次从下一个数开始遍历 n-2 个数，找到最小的数和第二个数交换。
    // 重复以上操作直到第 n-1 次遍历最小的数和第 n-1 个数交换，排序完成。
    public static void selectionSort(int[] arr) {
        int length = arr.length;
        if(arr == null || length<2) {
            return;
        }
        // 0~N-1
        // 1~N-1
        // 2~
        for (int i = 0; i < length-1; i++) {// i~N-1
            // 最小值在哪个位置上 i~N-1
            int minIndex = i;
            for (int j = i+1; j < length; j++) {// i~N-1上找最小值的下标
                minIndex = arr[minIndex] > arr[j] ? j : minIndex;
            }
            swap(arr, i, minIndex);
        }
    }

    // 交互数组i和j位置的数据
    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
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
    private static int[] copyArray(int[] arr) {
        if(arr == null) {
            return null;
        }
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    // for test
    private static void comparator(int[] arr) {
        Arrays.sort(arr);
    }

    // for test
    private static boolean isEqual(int[] arr1, int[] arr2) {
        if (arr1 == null && arr2 != null || arr1 != null && arr2 == null) {
            return false;
        }
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
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
        int testTime = 500000;
        int maxSize = 5;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            selectionSort(arr1);
            comparator(arr2);
            if(!isEqual(arr1,arr2)) {
                succeed = false;
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");


        int[] arr = generateRandomArray(maxSize, maxValue);
        printArray(arr);
        selectionSort(arr);
        printArray(arr);
    }




}

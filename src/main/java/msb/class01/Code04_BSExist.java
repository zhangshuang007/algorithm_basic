package msb.class01;

/**
 * @Description 二分查找：在一个有序数组中，找某个数是否存在
 * @Author zhangshuang
 * @Date 2021/1/18 18:05
 */
public class Code04_BSExist {
    public static boolean exist(int[] sortedArr, int num) {
        if(sortedArr == null || sortedArr.length == 0) {
            return false;
        }
        int L = 0,R = sortedArr.length-1;
        int mid = 0;
        while (L<R) {
            mid = L + ((R-L)>>1);// mid = (L + R) / 2
            if(sortedArr[mid] == num) {
                return true;
            } else if(sortedArr[mid] > num) {
                R = mid - 1;
            } else {
                L = mid + 1;
            }
        }
        return sortedArr[L] == num;
    }
}

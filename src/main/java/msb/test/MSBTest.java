package msb.test;

import java.util.HashSet;
import java.util.Set;

/**
 * @Description TODO
 * @Author Administrator
 * @Date 2021/4/25 13:37
 */
public class MSBTest {
//    public static void main(String[] args) {
////        String s1 = "acb";
////        String s2 = "d";
////        System.out.println(s1.compareTo(s2));
//        String a = "abcabcbb";
//        String b = "pwwkew";
//        System.out.println(subStrLength(b));
//        System.out.println(lengthOfLongestSubstring(b));
//    }

    public static int subStrLength(String str) {
        if(str == null || str.length() ==0) {
            return 0;
        }
        char[] chars = str.toCharArray();
        int max = 1;
        for (int i = 0; i < chars.length; i++) {
            Set set = new HashSet();
            int count = 1;
            set.add(chars[i]);
            for (int j = i+1; j < chars.length; j++) {
                if(!set.contains(chars[j])) {
                    set.add(chars[j]);
                    count ++;
                } else {
                    break;
                }
            }
            max = max < count ? count : max;
        }
        return max;
    }


    public static int lengthOfLongestSubstring(String s) {
        // 记录字符上一次出现的位置
        int[] last = new int[128];
        for (int i = 0; i < 128; i++) {
            last[i] = -1;
        }
        int n = s.length();

        int res = 0;
        int start = 0; // 窗口开始位置
        for (int i = 0; i < n; i++) {
            int index = s.charAt(i);
            start = Math.max(start, last[index] + 1);
            res = Math.max(res, i - start + 1);
            last[index] = i;
        }

        return res;
    }



    public static void main(String[] args) {
        new s();
        new F();
    }



    static class s extends F{
        public s() {

        }

        public void f1() {
            System.out.println(123);
        }

    }

    static class F{
        public F() {
            System.out.println(this.getClass().getName());
            this.f1();
        }
        public void f1() {
            System.out.println(1234);
        }
    }
}

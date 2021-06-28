package msb.class02;

/**
 * @Description 怎么使用数组实现不超过固定大小的队列和栈
 * 栈：正常使用
 * 队列：环形数组
 * @Author zhangshuang
 * @Date 2021/1/26 18:01
 */
public class Code04_RingArray {
    public static class MyQueue {
        private int[] arr;
        private int pushi;
        private int polli;
        private int size;
        private final int limit;
        public MyQueue(int limit) {
            arr = new int[limit];
            pushi = 0;
            polli = 0;
            size = 0;
            this.limit = limit;
        }

        public void push(int value) {
            if (size == limit) {
                throw new RuntimeException("栈满了，不能再加了");
            }
            size++;
            arr[pushi] = value;
            pushi = nextIndex(pushi);
        }

        public int pop() {
            if (size == 0) {
                throw new RuntimeException("栈空了，不能再拿了");
            }
            size--;
            int ans = arr[polli];
            polli = nextIndex(polli);
            return ans;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        // 如果现在的下标是i，返回下一个位置
        private int nextIndex(int i) {
            return i < limit - 1 ? i + 1 : 0;
        }
    }

    public static void main(String[] args) {
        int value = 100;
        int testTimes = 15;
        MyQueue myQueue = new MyQueue(5);
        for (int i = 0; i < testTimes; i++) {
            int nums = (int) (Math.random() * (value+1));
            if(myQueue.isEmpty()) {
                myQueue.push(nums);
                System.out.println("empty push:" + nums);
            } else {
                if(Math.random() < 0.5) {
                    myQueue.push(nums);
                    System.out.println("push:" + nums);
                } else {
                    System.out.println("pop:" +myQueue.pop());
                }
            }
        }
        for (int i = 0; i < myQueue.arr.length; i++) {
            System.out.print(myQueue.arr[i] + " ");
        }
    }
}

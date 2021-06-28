package msb.class02;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Description 2）如何用队列结构实现栈结构
 * @Author zhangshuang
 * @Date 2021/1/29 17:30
 */
public class Code07_TwoQueueImplementStack {
    public static class TwoQueueStack<T> {
        public Queue<T> queue;
        public Queue<T> help;

        public TwoQueueStack() {
            queue = new LinkedList<T>();
            help = new LinkedList<T>();
        }

        public void push(T data) {
            queue.offer(data);
        }

        public T poll() {
            while (queue.size() > 1) {
                help.offer(queue.poll());
            }
            T ans = queue.poll();
            Queue<T> tmp = queue;
            queue = help;
            help = tmp;
            return ans;
        }

        public T peek() {
            while (queue.size() >1){
                help.offer(queue.poll());
            }
            T ans = queue.poll();
            help.offer(ans);
            Queue tmp = queue;
            queue = help;
            help = tmp;
            return ans;
        }


        public boolean empty() {
            return queue.isEmpty();
        }
    }

    public static void main(String[] args) {
        TwoQueueStack test = new TwoQueueStack();
        test.push(1);
        test.push(2);
        test.push(3);
        System.out.println(test.peek());
        System.out.println(test.poll());
        test.push(5);
        System.out.println(test.peek());
        System.out.println(test.poll());
        System.out.println(test.peek());
        System.out.println(test.poll());
        test.push(7);
        System.out.println(test.peek());
        System.out.println(test.poll());
        System.out.println(test.peek());
        System.out.println(test.poll());
    }
}

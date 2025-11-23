package thread.bounded;

import java.util.ArrayDeque;
import java.util.Queue;

public class BoundedQueueImpl implements BoundedQueue{

    private final Queue<String> queue = new ArrayDeque<>();
    private final int max;

    public BoundedQueueImpl(int max) {
        this.max = max;
    }

    @Override
    public synchronized void put(String data) {
        if (queue.size() == max) {
            /**
             * 소비자가 데이터를 늦게 소비한다면 버려지는 데이터 발생
             */
            System.out.println("[put] 큐가 가득 참, 버림: " + data);
            return;
        }
        queue.offer(data);
    }

    @Override
    public synchronized String take() {
        if (queue.isEmpty()) {
            return null;
        }
        return queue.poll();
    }

    @Override
//    public synchronized String toString() { // 원칙적으로는 synchronized 를 사용해야 하지만, 해당 예제에서는 사용하지 않음.
    public String toString() {
        return queue.toString();
    }
}

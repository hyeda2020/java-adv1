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
//    public synchronized String toString() { // 해당 예제에서는 synchronized 사용하지 않음.
    public String toString() {
        return queue.toString();
    }
}

package thread.bounded;

import utils.ThreadUtils;

import java.util.ArrayDeque;
import java.util.Queue;

import static utils.ThreadUtils.*;

public class BoundedQueueImplV2 implements BoundedQueue{

    private final Queue<String> queue = new ArrayDeque<>();
    private final int max;

    public BoundedQueueImplV2(int max) {
        this.max = max;
    }

    @Override
    public synchronized void put(String data) {
        // 큐에 빈 공간이 생길 때까지 생산자 대기(단순한 방법)
        /**
         * 큐가 꽉 차있을 경우, 생산자는 lock 을 가지고 소비자가
         * 데이터를 가져갈 때까지 계속 대기
         */
        while (queue.size() == max) {
            System.out.println("[put] 큐가 가득 참, 기다림");
            sleep(1000);
        }
        queue.offer(data);
    }

    @Override
    public synchronized String take() {
        // 큐에 데이터가 생길 때까지 소비자 대기(단순한 방법)
        /**
         * 큐가 비어있는 상태라면, 소비자가 lock 을 가지고 큐에 데이터가 생길 때까지
         * 계속 대기하며, 이 때 생산자는 절대 lock 을 가져올 수 없어 영원히 BLOCK 상태에서 대기,
         * 그리고 이 때문에 소비자도 영원히 TIMED_WAIT 상태에서 대기
         *
         * 큐가 꽉 차 있는 상태라면, 생산자가 lock 을 가지고 기다리기 때문에
         * 소비자는 절대로 lock 을 획득할 수 없어 큐의 데이터를 영원히 가져갈 수 없고,
         * 그 때문에 큐는 절대 빌 수 없기 때문에 생산자도 영원히 TIMED_WAIT 상태에서 대기
         */
        while (queue.isEmpty()) {
            System.out.println("[take] 큐에 데이터가 없음, 소비자 대기");
            sleep(1000);
        }
        return queue.poll();
    }

    @Override
//    public synchronized String toString() { // 원칙적으로는 synchronized 를 사용해야 하지만, 해당 예제에서는 사용하지 않음.
    public String toString() {
        return queue.toString();
    }
}

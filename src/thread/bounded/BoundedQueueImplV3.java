package thread.bounded;

import java.util.ArrayDeque;
import java.util.Queue;

import static utils.ThreadUtils.sleep;

public class BoundedQueueImplV3 implements BoundedQueue{

    private final Queue<String> queue = new ArrayDeque<>();
    private final int max;

    public BoundedQueueImplV3(int max) {
        this.max = max;
    }

    @Override
    public synchronized void put(String data) {
        while (queue.size() == max) {
            System.out.println("[put] 큐가 가득 참, 기다림");
            try {
                wait(); // RUNNABLE -> WAITING, 락 반납
                System.out.println("[put] 생산자 다시 깨어남");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        queue.offer(data);
        System.out.println("[put] 생산자 데이터 저장, notify() 호출");
        notify(); // 대기 스레드, WAIT -> BLOCKED
    }

    @Override
    public synchronized String take() {
        while (queue.isEmpty()) {
            System.out.println("[take] 큐에 데이터가 없음, 소비자 대기");
            try {
                wait(); // RUNNABLE -> WAITING, 락 반납
                System.out.println("[take] 소비자 다시 깨어남");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        String data = queue.poll();
        System.out.println("[take] 소비자 데이터 획득, notify() 호출");
        notify(); // 대기 스레드, WAIT -> BLOCKED
        return data;
    }

    @Override
//    public synchronized String toString() { // 원칙적으로는 synchronized 를 사용해야 하지만, 해당 예제에서는 사용하지 않음.
    public String toString() {
        return queue.toString();
    }
}

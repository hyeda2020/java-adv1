package thread.bounded;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedQueueImplV4 implements BoundedQueue{

    private final Lock lock = new ReentrantLock();
    // 생산자/소비자 전용 대기 공간 분리
    private final Condition producerCond = lock.newCondition();
    private final Condition consumerCond = lock.newCondition();

    private final Queue<String> queue = new ArrayDeque<>();
    private final int max;

    public BoundedQueueImplV4(int max) {
        this.max = max;
    }

    @Override
    public void put(String data) {

        lock.lock();
        try {
            while (queue.size() == max) {
                System.out.println("[put] 큐가 가득 참, 기다림");
                try {
                    producerCond.await(); // 지정한 condition 에 현재 스레드를 대기(WAITING) 상태로 보관
                    System.out.println("[put] 생산자 다시 깨어남");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            queue.offer(data);
            System.out.println("[put] 생산자 데이터 저장, producerCond.signal() 호출");
            producerCond.signal(); // 지정한 condition 에서 대기중인 임의의 스레드 하나를 깨움
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String take() {

        lock.lock();
        try {
            while (queue.isEmpty()) {
                System.out.println("[take] 큐에 데이터가 없음, 소비자 대기");
                try {
                    consumerCond.await();
                    System.out.println("[take] 소비자 다시 깨어남");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            String data = queue.poll();
            System.out.println("[take] 소비자 데이터 획득, consumerCond.signal() 호출");
            consumerCond.signal();
            return data;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String toString() {
        return queue.toString();
    }
}

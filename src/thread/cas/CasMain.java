package thread.cas;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CasMain {

    private static final int THREAD_COUNT = 2;

    public static void main(String[] args) throws InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        atomicInteger.compareAndSet(0, 1); // 값이 0 이면 1 로 세팅(이 과정이 원자적으로 실행됨)
        System.out.println(atomicInteger.get());

        // atomicInteger.incrementAndGet() 구현 예시
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                incrementAndGet(atomicInteger);
            }
        };

        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < THREAD_COUNT; i++) {
            Thread thread = new Thread(runnable);
            threadList.add(thread);
            thread.start();
        }

        for (Thread thread : threadList) {
            thread.join();
        }

        int result = atomicInteger.get();
        System.out.println(atomicInteger.getClass().getSimpleName() + " resultValue = " + result);
    }

    // 락을 걸지 않고 CAS 연산을 사용하여 안전하게 값 증가
    private static int incrementAndGet(AtomicInteger atomicInteger) {
        int getValue;
        boolean result;
        do {
            getValue = atomicInteger.get();
            System.out.println("getValue : " + getValue);
            result = atomicInteger.compareAndSet(getValue, getValue + 1);
            System.out.println("result : " + result);
        } while (!result); // result 값이 false 일 경우에는 여러 스레드가 충돌이 난 경우로, 그에 대한 오버헤드가 발생

        // return atomicInteger.get(); // 이 값을 리턴하지 않는 이유는, 리턴 전에 다른 스레드가 값을 바꿔버릴 수 있기 때문.
        return getValue + 1;
    }
}

package thread.sync.lock;

import java.util.concurrent.locks.LockSupport;

import static java.lang.Thread.sleep;

public class LockSupportMain {

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(new ParkTask(), "Thread-1");
        thread1.start();

        sleep(100);

        System.out.println("Thread-1 state: " + thread1.getState());
        System.out.println("main -> unpark(Thread-1)");
        LockSupport.unpark(thread1); // 1. unpark 사용
        //thread1.interrupt(); // 2. interrupt() 사용
    }

    static class ParkTask implements Runnable {
        @Override
        public void run() {
            System.out.println("park 시작");
//            LockSupport.park(); // 스레드를 BLOCK 상태가 아닌, WAITING 상태로 변경하여 나중에 다시 깨울 수 있음(unpark)
            LockSupport.parkNanos(2000_000000); // 2초 동안 park 상태로 재움
            System.out.println("park 종료, state: " + Thread.currentThread().getState());
            System.out.println("인터럽트 상태: " + Thread.currentThread().isInterrupted());
        }
    }
}

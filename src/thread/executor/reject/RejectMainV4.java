package thread.executor.reject;

import thread.executor.RunnableTask;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class RejectMainV4 {

    static void main(String[] args) {
        ExecutorService executor = new ThreadPoolExecutor(1, 1, 0,
                TimeUnit.SECONDS,
                new SynchronousQueue<>(),
                new MyRejectedExecutionHandler()); // 사용자가 RejectedExecutionHandler 인터페이스를 구현하여 자신만의 거절 처리 전략을 정의.

        executor.submit(new RunnableTask("task1"));
        executor.submit(new RunnableTask("task2"));
        executor.submit(new RunnableTask("task3"));

        executor.close();
    }

    static class MyRejectedExecutionHandler implements RejectedExecutionHandler {

        static AtomicInteger count = new AtomicInteger(0);

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            int i = count.getAndIncrement();
            System.out.println("[경고] 거절된 누적 작업 수: " + i);
        }
    }
}

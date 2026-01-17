package thread.executor.reject;

import thread.executor.RunnableTask;

import java.util.concurrent.*;

public class RejectMainV2 {

    static void main(String[] args) {
        ExecutorService executor = new ThreadPoolExecutor(1, 1, 0,
                TimeUnit.SECONDS,
                new SynchronousQueue<>(),
                new ThreadPoolExecutor.DiscardPolicy()); // DiscardPolicy : 거절된 작업을 무시하고 아무런 예외도 발생시키지 않음.

        executor.submit(new RunnableTask("task1"));
        executor.submit(new RunnableTask("task2"));
        executor.submit(new RunnableTask("task3"));

        executor.close();
    }
}

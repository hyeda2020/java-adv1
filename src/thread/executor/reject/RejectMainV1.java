package thread.executor.reject;

import thread.executor.RunnableTask;

import java.util.concurrent.*;

public class RejectMainV1 {

    static void main(String[] args) {
        ExecutorService executor = new ThreadPoolExecutor(1, 1, 0,
                TimeUnit.SECONDS,
                new SynchronousQueue<>(),
                new ThreadPoolExecutor.AbortPolicy()); // AbortPolicy : 새로운 작업을 제출할 때 RejectedExecutionException 을 발생.(기본 정책)

        executor.submit(new RunnableTask("task1"));

        try {
            executor.submit(new RunnableTask("task2"));
        } catch (RejectedExecutionException e) {
            System.out.println("요청 초과");
            // 포기, 다시 시도 등 다양한 고민을 하면 됨
            System.out.println(e);
        }
        executor.close();
    }
}

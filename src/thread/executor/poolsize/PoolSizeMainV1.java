package thread.executor.poolsize;

import thread.executor.RunnableTask;

import java.util.concurrent.*;

import static thread.executor.ExecutorUtils.printState;
import static utils.ThreadUtils.sleep;

public class PoolSizeMainV1 {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(2);
        /**
         * 1. 작업을 요청하면 corePoolSize 만큼 스레드를 생성.
         * 2. core 사이즈를 초과하면 큐에 작업을 넣음.
         * 3. 큐를 초과하면 maximumPoolSize 만큼 임시로 사용되는 초과 스레드 생성.(keepAliveTime 까지 작업이 없다면 임시 초과 스레드는 제거됨.)
         * 4. max 사이즈를 초과하면 예외를 발생시켜서 요청 거절.
         */
        ExecutorService es = new ThreadPoolExecutor(2, 4, 3000,
                TimeUnit.MILLISECONDS, workQueue);
        printState(es);

        es.execute(new RunnableTask("task1"));
        printState(es, "task1");

        es.execute(new RunnableTask("task2"));
        printState(es, "task2");

        es.execute(new RunnableTask("task3"));
        printState(es, "task3");

        es.execute(new RunnableTask("task4"));
        printState(es, "task4");

        es.execute(new RunnableTask("task5"));
        printState(es, "task5");

        es.execute(new RunnableTask("task6"));
        printState(es, "task6");

        try {
            es.execute(new RunnableTask("task7"));
        } catch (RejectedExecutionException e) {
            System.out.println("task7 실행 거절 예외 발생: " + e);
        }

        sleep(3000);
        System.out.println("== 작업 수행 완료 ==");
        printState(es);

        sleep(3000);
        System.out.println("== maximumPoolSize 대기 시간 초과 ==");
        printState(es);

        es.close();
        System.out.println("== shutdown 완료 ==");
        printState(es);
    }
}


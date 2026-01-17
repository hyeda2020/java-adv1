package thread.executor.poolsize;

import thread.executor.RunnableTask;

import java.util.concurrent.*;

import static thread.executor.ExecutorUtils.printState;

public class PoolSizeMainV4 {

    static final int TASK_SIZE = 1100; // 1. 일반 케이스
//    static final int TASK_SIZE = 1200; // 2. 긴급 케이스
//    static final int TASK_SIZE = 1201; // 3. 거절 케이스

    /* 사용자 정의 풀 전략 예시 :
    * 일반적인 상황에는 CPU, 메모리 자원을 예측할 수 있도록 고정 크기의 스레드로 서비스를 안정적으로 운영.
    * 사용자의 요청이 갑자기 증가하면 긴급하게 스레드를 추가로 투입해서 작업을 빠르게 처리.
    * 사용자의 요청이 폭증해서 긴급 대응도 어렵다면 사용자의 요청을 거절.
    *  */
    static void main(String[] args) throws InterruptedException {
        ExecutorService es = new ThreadPoolExecutor(100, 200, 60,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(1000));
        printState(es);

        long startMs = System.currentTimeMillis();
        for (int i = 1; i <= TASK_SIZE; i++) {
            String taskName = "task" + i;
            try {
                es.execute(new RunnableTask(taskName));
                printState(es, taskName);
            } catch (RejectedExecutionException e) {
                System.out.println(taskName + " -> " + e);
            }
        }
        es.close();
        long endMs = System.currentTimeMillis();
        System.out.println("time: " + (endMs - startMs));
    }
}

package thread.executor.poolsize;

import thread.executor.RunnableTask;
import java.util.concurrent.*;
import static thread.executor.ExecutorUtils.printState;

public class PoolSizeMainV2 {

    static void main() {
        /* 고정 스레드 전략 : 안정적인 서비스 운영이 가능하지만, 트래픽이 늘어날수록 사용자의 응답 속도는 느려짐 */
        ExecutorService es = Executors.newFixedThreadPool(2);
        // ExecutorService es = new ThreadPoolExecutor(2, nThreads,
        //                                             0L, TimeUnit.MILLISECONDS,
        //                                             new LinkedBlockingQueue<>());

        System.out.println("pool 생성");
        printState(es);

        for (int i = 1; i <= 6; i++) {
            String taskName = "task" + i;
            es.execute(new RunnableTask(taskName));
            printState(es, taskName);
        }
        es.close();
        System.out.println("== shutdown 완료 ==");
    }
}

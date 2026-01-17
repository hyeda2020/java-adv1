package thread.executor.poolsize;

import thread.executor.RunnableTask;
import java.util.concurrent.*;
import static thread.executor.ExecutorUtils.printState;
import static utils.ThreadUtils.sleep;

public class PoolSizeMainV3 {

    static void main() {
        /* 캐시 풀 전략 : 기본 스레드를 사용하지 않고, 60초 생존 주기를 가진 초과 스레드만 사용(초과 스레드 수는 제한 없음) */
        ExecutorService es = Executors.newCachedThreadPool();
        // ExecutorService es = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
        //                                             60L, TimeUnit.SECONDS
        //                                             , new SynchronousQueue<>());

        System.out.println("pool 생성");
        printState(es);

        for (int i = 1; i <= 4; i++) {
            String taskName = "task" + i;
            es.execute(new RunnableTask(taskName));
            printState(es, taskName);
        }

        sleep(3000);
        System.out.println("== 작업 수행 완료 ==");
        printState(es);

        sleep(3000);
        System.out.println("== maximumPoolSize 대기 시간 초과 == ");
        printState(es);

        es.close();
        System.out.println("== shutdown 완료 ==");
        printState(es);
    }
}

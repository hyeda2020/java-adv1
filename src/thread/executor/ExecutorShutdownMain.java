package thread.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static thread.executor.ExecutorUtils.printState;

public class ExecutorShutdownMain {

    static void main() {
        ExecutorService es = Executors.newFixedThreadPool(2);
        es.execute(new RunnableTask("taskA"));
        es.execute(new RunnableTask("taskB"));
        es.execute(new RunnableTask("taskC"));
        es.execute(new RunnableTask("longTask", 100_000)); // 100초 대기
        printState(es);
        System.out.println("shutdown 시작");
        shutdownAndAwaitTermination(es);
        System.out.println("shutdown 완료");
        printState(es);
    }

    private static void shutdownAndAwaitTermination(ExecutorService es) {
        es.shutdown(); // non-blocking, 새로운 작업은 받지 않으며, 이미 처리중이거나 큐에 대기중인 작업은 처리. 이후 스레드풀 정리.
        try {
            // 이미 대기중인 작업들이 모두 완료될 때 까지 최대 10초 기다림.
            if (!es.awaitTermination(10, TimeUnit.SECONDS)) {
                // 정상 종료가 너무 오래걸리면 강제 종료 시도
                System.out.println("서비스 정상 종료 실패.. 강제 종료 시도");
                es.shutdownNow();

                // 작업이 취소 될 때까지 대기.
                if (!es.awaitTermination(10, TimeUnit.SECONDS)) {
                    System.out.println("서비스가 종료되지 않았습니다.");
                }
            }
        } catch (InterruptedException e) {
            // awaitTermination 으로 대기중인 스레드가 인터럽트 될 수 있음.
        }
    }
}

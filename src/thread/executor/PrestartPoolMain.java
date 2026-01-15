package thread.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import static thread.executor.ExecutorUtils.printState;

public class PrestartPoolMain {
    /**
     * Tip) 응답시간이 아주 중요한 서버라면,
     * 서버가 고객의 처음 요청을 받기 전에 스레드를 스레드 풀에 미리 생성하여
     * 처음 요청에서 사용되는 스레드의 생성 시간을 줄일 수 있음.
     */
    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(1000);
        printState(es);
        ThreadPoolExecutor poolExecutor = (ThreadPoolExecutor) es;
        poolExecutor.prestartAllCoreThreads();
        printState(es);
    }
}

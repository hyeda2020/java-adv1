package thread.executor.future;

import java.util.concurrent.*;

import static utils.ThreadUtils.sleep;

public class FutureExceptionMain {

    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(1);
        System.out.println("작업 전달");
        Future<Integer> future = es.submit(new ExCallable());
        sleep(1000); // 잠시 대기
        try {
            System.out.println("future.get() 호출 시도, future.state(): " + future.state());
            Integer result = future.get();
            System.out.println("result value = " + result);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            System.out.println("e = " + e);
            Throwable cause = e.getCause(); // 원본 예외
            System.out.println("cause = " + cause);
        }
        es.close();
    }

    static class ExCallable implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            System.out.println("Callable 실행, 예외 발생");
            throw new IllegalStateException("ex!");
        }
    }
}

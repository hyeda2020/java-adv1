package thread.executor.future;

import java.util.Random;
import java.util.concurrent.*;

import static utils.ThreadUtils.sleep;

public class CallableMainV2 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(1);
        System.out.println("submit() 호출");
        // Future 참조만 받고, 요청 스레드(main스레드)는 바로 다음 코드 실행
        Future<Integer> future = es.submit(new MyCallable());
        System.out.println("future 즉시 반환, future = " + future);

        System.out.println("future.get() [블로킹] 메서드 호출 시작 -> main 스레드 WAITING");
        /*
            여기서 Future 참조 객체로부터 값을 반환받기까지
            요청 스레드(main스레드)는 블로킹 상태(WAITING).
            이후 Future 스레드가 작업을 완료하여 결과값을 리턴하면
            요청 스레드는 WAITING -> RUNNABLE 상태로 변경.
         */
        Integer result = future.get();
        System.out.println("future.get() [블로킹] 메서드 호출 완료 -> , main 스레드 RUNNABLE");

        System.out.println("result value = " + result);
        System.out.println("future 완료, future = " + future);

        es.close();
    }

    static class MyCallable implements Callable<Integer> {
        @Override
        public Integer call() {
            System.out.println("Callable 시작");
            sleep(2000);
            int value = new Random().nextInt(10);
            System.out.println("create value = " + value);
            System.out.println("Callable 완료");
            return value;
        }
    }
}

package thread.executor.future;

import java.util.Random;
import java.util.concurrent.*;

import static utils.ThreadUtils.sleep;

public class CallableMainV1 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(1);
        Future<Integer> future = es.submit(new MyCallable());
        Integer result = future.get();
        System.out.println("result value = " + result);
        es.close();
    }

    static class MyCallable implements Callable<Integer> {

        @Override
        public Integer call() {
            System.out.println("Callable 시작");
            sleep(2000);
            int value = new Random().nextInt(9);
            System.out.println("create value = " + value);
            System.out.println("Callable 완료");
            return value;
        }
    }
}

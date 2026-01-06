package thread.executor.future;

import java.util.concurrent.*;

import static utils.ThreadUtils.sleep;

public class FutureCancelMain {

//    private static boolean mayInterruptIfRunning = true;
    private static boolean mayInterruptIfRunning = false;

    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(1);
        Future<String> future = es.submit(new MyTask());
        System.out.println("Future.state : " + future.state());

        // 일정 시간 후 취소 시도
        sleep(3000);

        // cancel() 호출
        System.out.println("future.cancel(" + mayInterruptIfRunning + ") 호출");
        // mayInterruptIfRunning이 'false'일 경우엔 진행 중이던 작업을 중단하지는 않음.
        boolean cancelResult = future.cancel(mayInterruptIfRunning);
        System.out.println("cancelResult = " + cancelResult);

        try {
            System.out.println("Future.result = " + future.get());
        } catch (CancellationException e) {
            System.out.println("CancellationException : Future는 이미 취소 상태.");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        es.close();
    }

    static class MyTask implements Callable<String> {

        @Override
        public String call() {
            try {
                for (int i = 0; i < 10; i++) {
                    System.out.println("작업중 : " + i);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                System.out.println("인터럽트 발생");
                return "Interrupted";
            }
            return "Completed";
        }
    }
}

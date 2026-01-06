package thread.executor.future;

import java.util.concurrent.*;

import static utils.ThreadUtils.sleep;

public class SumTaskMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        SumTask sumTask1 = new SumTask(1, 50);
        SumTask sumTask2 = new SumTask(51, 100);

        ExecutorService es = Executors.newFixedThreadPool(2);

        /**
         * 여기서 요청 스레드(main)가 작업을 던지고
         * 바로 다음 코드를 수행함으로써
         * 두 sumTask 스레드들이 동시에 수행될 수 있음.
         */
        Future<Integer> future1 = es.submit(sumTask1); // 작업1 수행 시작
        Future<Integer> future2 = es.submit(sumTask2); // 작업2 수행 시작

        Integer sum1 = future1.get(); // 여기서 블로킹
        Integer sum2 = future2.get(); // 여기서 블로킹

        /*
        // future를 잘못 사용한 예시 1 (2초가 걸릴 작업이 4초가 걸림)
        Future<Integer> future1 = es.submit(sumTask1);
        Integer sum1 = future1.get(); // 여기서 블로킹

        Future<Integer> future2 = es.submit(sumTask2);
        Integer sum2 = future2.get(); // 여기서 블로킹
         */

        /*
        // future를 잘못 사용한 예시 2 (2초가 걸릴 작업이 4초가 걸림)
        Integer sum1 = es.submit(sumTask1).get(); // 여기서 블로킹
        Integer sum2 = es.submit(sumTask2).get(); // 여기서 블로킹
         */

        System.out.println("task1.result = " + sum1);
        System.out.println("task2.result = " + sum2);

        int sumAll = sum1 + sum2;
        System.out.println("task1 + task2 = " + sumAll);

        es.close();
    }

    static class SumTask implements Callable<Integer> {

        int startValue;
        int endValue;

        public SumTask(int startValue, int endValue) {
            this.startValue = startValue;
            this.endValue = endValue;
        }

        @Override
        public Integer call() throws Exception {
            System.out.println("작업 시작");
            sleep(2000);
            int sum = 0;
            for (int i = startValue; i <= endValue; i++) {
                sum += i;
            }
            System.out.println("작업 완료");
            return sum;
        }
    }
}

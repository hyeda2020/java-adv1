package thread.bounded;

import java.util.ArrayList;
import java.util.List;

import static utils.ThreadUtils.*;

public class BoundedMain {

    public static void main(String[] args) {
        // 1. BoundedQueue 선택
//        BoundedQueue queue = new BoundedQueueImpl(2);
//        BoundedQueue queue = new BoundedQueueImplV2(2);
        BoundedQueue queue = new BoundedQueueImplV3(2);

        // 2. 생산자, 소비자 실행 순서 선택(택 1)
        producerFirst(queue);
//        consumerFirst(queue);
    }

    private static void producerFirst(BoundedQueue queue) {
        System.out.println("== [생산자 먼저 실행] 시작, " + queue.getClass().getSimpleName() + " ==");
        List<Thread> threads = new ArrayList<>();
        startProducer(queue, threads);
        printAllState(queue, threads);
        startConsumer(queue, threads);
        printAllState(queue, threads);
        System.out.println("== [생산자 먼저 실행] 종료, " + queue.getClass().getSimpleName() + " ==");
    }

    private static void consumerFirst(BoundedQueue queue) {
        System.out.println("== [소비자 먼저 실행] 시작, " + queue.getClass().getSimpleName() + " ==");
        List<Thread> threads = new ArrayList<>();
        startConsumer(queue, threads);
        printAllState(queue, threads);
        startProducer(queue, threads);
        printAllState(queue, threads);
        System.out.println("== [소비자 먼저 실행] 종료, " + queue.getClass().getSimpleName() + " ==");
    }

    private static void startProducer(BoundedQueue queue, List<Thread> threads) {
        System.out.println();
        System.out.println("생산자 시작");
        for (int i = 1; i <= 3; i++) {
            Thread producer = new Thread(new ProducerTask(queue, "data" + i), "producer" + i);
            threads.add(producer);
            producer.start();
            sleep(100);
        }
    }

    private static void startConsumer(BoundedQueue queue, List<Thread>
            threads) {
        System.out.println();
        System.out.println("소비자 시작");
        for (int i = 1; i <= 3; i++) {
            Thread consumer = new Thread(new ConsumerTask(queue), "consumer" + i);
            threads.add(consumer);
            consumer.start();
            sleep(100);
        }
    }

    private static void printAllState(BoundedQueue queue, List<Thread> threads) {
        System.out.println();
        System.out.println("현재 상태 출력, 큐 데이터: " + queue);
        for (Thread thread : threads) {
            System.out.println(thread.getName() + ": " + thread.getState());
        }
    }
}

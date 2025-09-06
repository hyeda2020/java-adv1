package thread.volatile1;

import static java.lang.Thread.sleep;

public class VolatileFlagMain {

    public static void main(String[] args) throws InterruptedException {
        MyTask task = new MyTask();
        Thread thread = new Thread(task, "work");

        System.out.println("runFlag = " + task.runFlag);

        thread.start();
        sleep(1000);

        System.out.println("runFlag를 false로 변경 시도");

        task.runFlag = false;

        System.out.println("runFlag = " + task.runFlag);
        System.out.println("main 종료");
    }

    static class MyTask implements Runnable {

        /*
            메모리 가시성 문제 해결을 위해
            아래 공유 변수에 대해서는 캐시메모리를 사용하지 않고
            항상 메인메모리에서만 사용함으로써 스레드들 간의 Sync 를 맞춤
        */
        volatile boolean runFlag = true;

        @Override
        public void run() {
            System.out.println("task 시작");
            while (runFlag) {
                // runFlag가 false로 변하면 탈출
            }
            System.out.println("task 종료");
        }
    }
}

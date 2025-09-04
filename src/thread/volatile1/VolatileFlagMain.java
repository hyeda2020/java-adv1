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
        boolean runFlag = true;
        //volatile boolean runFlag = true;
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

package thread.volatile1;

import utils.ThreadUtils;

import static utils.ThreadUtils.*;

public class VolatileCountMain {

    public static void main(String[] args) throws InterruptedException {
        MyTask task = new MyTask();
        Thread thread = new Thread(task, "work");
        thread.start();
        sleep(1000);
        task.flag = false;
        System.out.println("flag = " + task.flag + ", count = " + task.count + " in main");
    }

    static class MyTask implements Runnable {

        /*
        * volatile 을 붙이지 않으면 캐시메모리에서 변수값을 읽으므로
        * 정확한 타이밍에 두 스레드가 종료되지 않을 수 있음.
        * */
//        boolean flag = true;
//        long count;

        volatile boolean flag = true;
        volatile long count;

        @Override
        public void run() {
            while (flag) {
                count++;
                //1억번에 한번씩 출력
                if (count % 100_000_000 == 0) {
                    //주석 처리 한다면...
                    System.out.println("flag = " + flag + ", count = " + count + " in while()");
                }
            }
            System.out.println("flag = " + flag + ", count = " + count + " 종료");
        }
    }
}

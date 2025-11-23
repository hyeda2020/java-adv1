package interrupt;

import static utils.ThreadUtils.*;

public class InterruptMainV3 {

    public static void main(String[] args) {
        MyTask task = new MyTask();
        Thread thread = new Thread(task, "task");

        thread.start();
        System.out.println("작업 시작");

        sleep(50);
        System.out.println("작업 중단 지시 thread.interrupt()");

        // 해당 스레드가 InterruptedException 을 던지는 코드를 수행하는 중이라면 바로 해당 상태(WAIT, TIMED-WAIT) 중지
        thread.interrupt();
        System.out.println("state = " + thread.getState());
    }

    static class MyTask implements Runnable {
        @Override
        public void run() {
            /* Thread.interrupted()
            * 해당 스레드가 인터럽트 상태면 true 를 반환하고, 상태를 정상(false)으로 바꿈.
            * 인터럽트 상태가 아니면 false 를 반환하고 상태를 변경하지 않음.
            * */
            while (!Thread.interrupted()) { // 결과적으로 while 문을 탈출하는 시점에 인터럽트 상태도 false 로 변경
                System.out.println("작업 중");
            }
            System.out.println("work 스레드 입터럽트 상태 : " + Thread.currentThread().isInterrupted());

            try {
                System.out.println("자원 정리 시도");
                Thread.sleep(1000);
                System.out.println("자원 정리 완료");
            } catch (InterruptedException e) {
                System.out.println("자원 정리 실패 - 자원 정리 중 인터럽트 발생");
                System.out.println("work 스레드 인터럽트 상태 = " +
                        Thread.currentThread().isInterrupted());
            }
        }
    }
}

package interrupt;

public class InterruptMain {

    public static void main(String[] args) {
        MyTask task = new MyTask();
        Thread thread = new Thread(task, "task");

        thread.start();
        System.out.println("작업 시작");

        try {
            Thread.sleep(4000);
            System.out.println("작업 중단 지시 thread.interrupt()");

            // 해당 스레드가 InterruptedException 을 던지는 코드를 수행하는 중이라면 바로 해당 상태(WAIT, TIMED-WAIT) 중지
            thread.interrupt();
            System.out.println("state = " + thread.getState());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    static class MyTask implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    System.out.println("작업 중");
                    Thread.sleep(3000);
                }
            } catch (InterruptedException e) {
                System.out.println("work 스레드 입터럽트 상태 : " + Thread.currentThread().isInterrupted());
                System.out.println("interrupt message = " + e.getMessage());

                // interrupt가 걸린 이후엔 스레드가 다시 동작하는 상태(RUNNABLE)로 돌아감
                System.out.println("state = " + Thread.currentThread().getState());
            }
            System.out.println("자원 정리");
            System.out.println("작업 종료");
        }
    }
}

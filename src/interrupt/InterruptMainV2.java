package interrupt;

public class InterruptMainV2 {

    public static void main(String[] args) {
        MyTask task = new MyTask();
        Thread thread = new Thread(task, "task");

        thread.start();
        System.out.println("작업 시작");

        try {
            Thread.sleep(50);
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
            while (Thread.currentThread().isInterrupted()) { // 인터럽트 상태만 확인
                System.out.println("작업 중");
            }
            System.out.println("work 스레드 입터럽트 상태 : " + Thread.currentThread().isInterrupted());

            /*
            * 자바에서는 InterruptException이 발생하면 인터럽트 상태를 true -> false 로 변경
            * 하지만, Thread.isInteruupted() 메서드는 인터럽트 상태만 확인하기 때문에
            * 여전히 인터럽트 상태는 true가 되어 이후 Thread.sleep() 같은 로직이 있으면
            * 바로 의도치 않게 인터럽트 예외 발생
            *  */
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

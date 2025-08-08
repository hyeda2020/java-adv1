package thread;

public class ThreadCreatMain {

    public static void main(String[] args) {

        // 기본적인 스레드 생성 방식
        MyThread myThread = new MyThread();
        myThread.start();

        // 데몬 스레드 생성 방식
        MyDaemonThread myDaemonThread = new MyDaemonThread();
        myDaemonThread.setDaemon(true); // 데몬 스레드임을 반드시 start() 이전에 명시
        myDaemonThread.start();

        // Runnable을 활용한 스레드 생성 방식(Thread 클래스를 굳이 상속받을 필요가 없어 실무에서 권장)
        Thread myRunnableThread = new Thread(new MyRunnable());
        myRunnableThread.start();

        // 여러 스레드 생성하기
        for (int i = 0; i < 100; i++) {
            // 서로 여러 스레드들이 Runnable 객체 인스턴스를 사용
            Thread thread = new Thread(new MyRunnable());
            thread.start();
        }
    }

    public static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + ": run()");
        }
    }

    public static class MyDaemonThread extends Thread {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + ": Daemon run()");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static class MyRunnable implements Runnable {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + ": Runnable run()");
        }
    }
}

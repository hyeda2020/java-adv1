package thread.cas.spinlock;

public class SpinLockMain {

    public static void main(String[] args) {
        SpinLock spinlock = new SpinLock();

        Runnable task = new Runnable() {
            @Override
            public void run() {
                spinlock.lock();
                try {
                    // critical section
                    System.out.println("비즈니스 로직 수행");
                } finally {
                    spinlock.unlock();
                }
            }
        };

        Thread t1 = new Thread(task, "Thread-1");
        Thread t2 = new Thread(task, "Thread-2");

        t1.start();
        t2.start();
    }
}

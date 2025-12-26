package thread.collection.simple.list;

public class SimpleListMain {

    public static void main(String[] args) throws InterruptedException {
//        test(new BasicList());
        test(new SyncProxyList(new BasicList()));
    }

    private static void test(SimpleList list) throws InterruptedException {

        Runnable addA = new Runnable() {
            @Override
            public void run() {
                list.add("A");
                System.out.println("Thread-1: list.add(A)");
            }
        };

        Runnable addB = new Runnable() {
            @Override
            public void run() {
                list.add("B");
                System.out.println("Thread-2: list.add(B)");
            }
        };

        Thread thread1 = new Thread(addA, "Thread-1");
        Thread thread2 = new Thread(addB, "Thread-2");
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
    }
}

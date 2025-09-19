package thread.sync;

import static java.lang.Thread.sleep;

public class BankMain {

    public static void main(String[] args) throws InterruptedException {
        BankAccount account = new BankAccountImpl(1000);
        Thread thread1 = new Thread(new WithdrawTask(account, 800), "thread1");
        Thread thread2 = new Thread(new WithdrawTask(account, 800), "thread2");

        thread1.start();
        thread2.start();

        sleep(500);

        System.out.println("thread1 state: " + thread1.getState());
        System.out.println("thread2 state: " + thread2.getState());

        thread1.join();
        thread2.join();
        System.out.println("최종 잔액 : " + account.getBalance());
    }
}

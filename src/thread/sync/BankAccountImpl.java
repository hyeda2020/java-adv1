package thread.sync;

import static utils.ThreadUtils.*;

public class BankAccountImpl implements BankAccount{

    private int balance;

    public BankAccountImpl(int initialBalance) {
        this.balance = initialBalance;
    }

    @Override
//    public synchronized boolean withdraw(int amount) {
    public boolean withdraw(int amount) {
        System.out.println("거래 시작: " + getClass().getSimpleName());

        /**
         * 메서드 전체에 synchronized 를 적용하는 것 보다는
         * 꼭 필요한 구간에만 적용하는게 성능적으로 더 이득.
         */
        synchronized (this) {
            System.out.println("[검증 시작] 출금액: " + amount + ", 잔액: " + balance);
            if (balance < amount) {
                System.out.println("[검증 실패] 출금액: " + amount + ", 잔액: " + balance);
                return false;
            }
            System.out.println("[검증 완료] 출금액: " + amount + ", 잔액: " + balance);
            balance = balance - amount;
            System.out.println("[출금 완료] 출금액: " + amount + ", 변경 잔액: " + balance);
            sleep(1000); // 출금에 걸리는 시간으로 가정
        }
        System.out.println("거래 종료");
        return true;
    }

    @Override
    public synchronized int getBalance() {
        return this.balance;
    }
}

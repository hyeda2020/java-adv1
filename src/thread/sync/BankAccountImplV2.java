package thread.sync;

import utils.ThreadUtils;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static utils.ThreadUtils.*;

public class BankAccountImplV2 implements BankAccount{

    private int balance;
    private final Lock lock = new ReentrantLock(); // 비공정 모드 락
//     private final Lock fairLock = new ReentrantLock(true); // 공정 모드 락

    public BankAccountImplV2(int initialBalance) {
        this.balance = initialBalance;
    }

    @Override
    public boolean withdraw(int amount) {
        System.out.println("거래 시작: " + getClass().getSimpleName());

        if (!lock.tryLock()) { // 락 획득을 시도하고, 즉시 성공 여부를 반환
            System.out.println("[진입 실패] 이미 처리중인 작업이 있습니다.");
            return false;
        }

        try {
            System.out.println("[검증 시작] 출금액: " + amount + ", 잔액: " + balance);
            if (balance < amount) {
                System.out.println("[검증 실패] 출금액: " + amount + ", 잔액: " + balance);
                return false;
            }
            System.out.println("[검증 완료] 출금액: " + amount + ", 잔액: " + balance);
            balance = balance - amount;
            System.out.println("[출금 완료] 출금액: " + amount + ", 변경 잔액: " + balance);
            sleep(1000); // 출금에 걸리는 시간으로 가정
        } finally {
            // lock 을 했으면 이후에 반드시 unlock 을 해줘야 됨.
            lock.unlock();
        }
        System.out.println("거래 종료");
        return true;
    }

    @Override
    public int getBalance() {
        lock.lock();
        try {
            return this.balance;
        } finally {
            // lock 을 했으면 이후에 반드시 unlock 을 해줘야 됨.
            lock.unlock();
        }
    }
}

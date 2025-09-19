package thread.sync;

public class BankAccountImpl implements BankAccount{

    private int balance;

    public BankAccountImpl(int initialBalance) {
        this.balance = initialBalance;
    }

    @Override
    public boolean withdraw(int amount) {
        System.out.println("거래 시작: " + getClass().getSimpleName());
        System.out.println("[검증 시작] 출금액: " + amount + ", 잔액: " + balance);
        if (balance < amount) {
            System.out.println("[검증 실패] 출금액: " + amount + ", 잔액: " + balance);
            return false;
        }
        System.out.println("[검증 완료] 출금액: " + amount + ", 잔액: " + balance);

        try {
            Thread.sleep(1000); // 출금에 걸리는 시간으로 가정
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        balance = balance - amount;
        System.out.println("[출금 완료] 출금액: " + amount + ", 변경 잔액: " + balance);
        System.out.println("거래 종료");
        return true;
    }

    @Override
    public int getBalance() {
        return this.balance;
    }
}

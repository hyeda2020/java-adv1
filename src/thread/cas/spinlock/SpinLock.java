package thread.cas.spinlock;

import java.util.concurrent.atomic.AtomicBoolean;

public class SpinLock {

    private final AtomicBoolean lock = new AtomicBoolean(false);

    public void lock() {
        // CAS 연산을 통해 락 사용 가능 여부를 원자적으로 확인
        while (!lock.compareAndSet(false, true)) {
            System.out.println("락 획득 실패 - 스핀 대기");
        }
        System.out.println("락 획득 완료");
    }

    public void unlock() {
        lock.set(false);
        System.out.println("락 반납 완료");
    }
}

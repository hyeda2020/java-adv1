package thread.collection.simple.list;

/**
 * ArrayList 뿐만 아니라, LinkedList 에도 동기화를 적용해야 될 경우,
 * 이러한 프록시 패턴을 통해 원본 코드를 그대로 유지하면서
 * 클라이언트는 편리하게 동기화가 적용된 리스트 사용 가능.
 *
 * 즉, 클라이언트 -> SyncProxyList -> ArrayList/LinkedList 구조.
 * 이때, SyncProxyList 가 SimpleList 인터페이스를 구현하고,
 * 대리(Proxy)하고자 하는 대상도 SimpleList 인터페이스 타입이므로
 * 클라이언트 입장에서는 구현체에 의존하기 않고 유연하게 동기화 기능 사용 가능.
 */
public class SyncProxyList implements SimpleList {

    // 추상화, 유연성을 위한 SimpleList 인터페이스에 의존
    private SimpleList target;

    public SyncProxyList(SimpleList target) {
        this.target = target;
    }

    @Override
    public synchronized int size() {
        return target.size();
    }

    @Override
    public synchronized void add(Object e) {
        target.add(e);
    }

    @Override
    public synchronized Object get(int index) {
        return target.get(index);
    }

    @Override
    public synchronized String toString() {
        return target.toString() + " by " + this.getClass().getSimpleName();
    }
}

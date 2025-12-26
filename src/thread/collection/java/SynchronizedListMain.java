package thread.collection.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SynchronizedListMain {

    public static void main(String[] args) {
        /**
         * 자바에서 제공해주는 synchronized 를 대신 적용해 주는 프록시.
         * 단, 모든 메서드에 synchronized 를 적용하므로 이에 따른 오버헤드가 발생하고
         * 정교화된 동기화가 불가능.
         * => java.util.concurrent 패키지의 동시성 컬렉션 사용하여 개선
         */
        List<String> list = Collections.synchronizedList(new ArrayList<>());
        list.add("data1");
        list.add("data2");
        list.add("data3");
        System.out.println(list.getClass());
        System.out.println("list = " + list);
    }
}

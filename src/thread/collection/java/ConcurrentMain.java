package thread.collection.java;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentMain {

    public static void main(String[] args) {

        /**
         * 자바에서 제공하는 동시성 컬렉션들로,
         * synchronized, Lock(ReentrantLock), CAS, 분할 잠금 기술(segment lock)등
         * 다양한 방법을 섞어서 매우 정교한 동기화를 구현하면서 동시에 성능도 최적화.
         */

        // ArrayList 의 대안
        List<Integer> list = new CopyOnWriteArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        System.out.println("list = " + list);

        // HashSet 의 대안
        Set<Integer> copySet = new CopyOnWriteArraySet<>();
        copySet.add(1);
        copySet.add(2);
        copySet.add(3);
        System.out.println("copySet = " + copySet);

        //  TreeSet 의 대안. 데이터의 정렬 순서를 유지
        Set<Integer> skipSet = new ConcurrentSkipListSet<>(); // Comparator 사용 가능
        skipSet.add(3);
        skipSet.add(2);
        skipSet.add(1);
        System.out.println("skipSet = " + skipSet);

        // HashMap 의 대안
        Map<Integer, String> map1 = new ConcurrentHashMap<>();
        map1.put(3, "data3");
        map1.put(2, "data2");
        map1.put(1, "data1");
        System.out.println("map1 = " + map1);

        // TreeMap 의 대안. 데이터의 정렬 순서를 유지
        Map<Integer, String> map2 = new ConcurrentSkipListMap<>(); // Comparator 사용 가능
        map2.put(2, "data2");
        map2.put(3, "data3");
        map2.put(1, "data1");
        System.out.println("map2 = " + map2);
    }
}

package thread.executor;

import java.util.concurrent.Callable;

import static utils.ThreadUtils.sleep;

public class CallableTask implements Callable<Integer> {

    private final String name;
    private int sleepMs = 1000;

    public CallableTask(String name) {
        this.name = name;
    }

    public CallableTask(String name, int sleepMs) {
        this.name = name;
        this.sleepMs = sleepMs;
    }

    @Override
    public Integer call() throws Exception {
        System.out.println(name + " 실행");
        sleep(sleepMs);
        System.out.println(name + " 완료, return = " + sleepMs);
        return sleepMs;
    }
}

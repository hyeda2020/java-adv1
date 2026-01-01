package thread.executor;

import static utils.ThreadUtils.sleep;

public class RunnableTask implements Runnable{

    private final String name;
    private int sleepMs = 1000;

    public RunnableTask(String name) {
        this.name = name;
    }

    public RunnableTask(String name, int sleepMs) {
        this.name = name;
        this.sleepMs = sleepMs;
    }

    @Override
    public void run() {
        System.out.println(name + " 시작");
        sleep(sleepMs); // 작업 시간 시뮬레이션
        System.out.println(name + " 완료");
    }
}

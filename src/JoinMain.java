public class JoinMain {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("main 스레드 작업 시작");
        Job sumJob1 = new Job(1, 50);
        Job sumJob2 = new Job(51, 100);
        Thread thread1 = new Thread(sumJob1, "thread-1");
        Thread thread2 = new Thread(sumJob2, "thread-2");

        thread1.start();
        thread2.start();

        System.out.println("main 스레드 wait 상태 시작");
        // 만약 join 메서드에 time 파라미터를 넣어주지 않으면 main 스레드는무기한 wait 상태가 됨
        thread1.join(2000); // main 스레드에서 thread1이 종료될 때까지 최대 2초간 기다림
        thread2.join(2000); // main 스레드에서 thread2이 종료될 때까지 최대 2초간 기다림
        System.out.println("main 스레드 wait 상태 종료");

        System.out.println("thread-1 : " + sumJob1.result);
        System.out.println("thread-2 : " + sumJob2.result);

        int sumAll = sumJob1.result + sumJob2.result;
        System.out.println("Total Sum : " + sumAll);

        System.out.println("main 스레드 작업 종료");
    }

    static class Job implements Runnable {

        int start;
        int end;
        int result;

        public Job(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            System.out.println("덧셈 스레드 작업 시작");
            for (int i = start; i <= end; i++) {
                result += i;
            }
            System.out.println("덧셈 스레드 작업 종료");
        }
    }
}

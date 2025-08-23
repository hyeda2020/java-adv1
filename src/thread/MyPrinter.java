package thread;

import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MyPrinter {

    public static void main(String[] args) {
        Printer printer = new Printer();
        Thread printerThread = new Thread(printer, "printer");
        printerThread.start();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("프린터할 문서를 입력하세요. 종료 (q): ");
            String input = scanner.nextLine();
            if (input.equals("q")) {
                printerThread.interrupt();
                break;
            }
            printer.addJob(input);
        }
    }

    static class Printer implements Runnable {
        Queue<String> jobQueue = new ConcurrentLinkedQueue<>();

        @Override
        public void run() {

            while (!Thread.interrupted()) {
                if (jobQueue.isEmpty()) {
                    Thread.yield(); // jobQueue 가 비어있으면 다른 스레드에게 CPU 점유 양보
                    continue;
                }

                try {
                    String job = jobQueue.poll();
                    System.out.println("출력 시작: " + job + ", 대기 문서: " + jobQueue);
                    Thread.sleep(3000);
                    System.out.println("출력 완료: " + job);
                } catch (InterruptedException e) {
                    System.out.println("인터럽트 발생");
                }
            }
            System.out.println("프린터 종료");
        }

        public void addJob(String input) {
            jobQueue.offer(input);
        }
    }
}

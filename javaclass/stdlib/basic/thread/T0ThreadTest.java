package stdlib.basic.thread;

public class T0ThreadTest {
    public static void main(String[] args) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1; i++) {
                    System.out.println("run:");
                    System.out.println(i);
                }
            }
        });
        t.start();

        for (int i = 0; i < 1; i++) {
            System.out.println("main");
            System.out.println(i);
        }
    }
}



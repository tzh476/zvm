import java.lang.Runnable;
import java.lang.Thread;

public class CreateAsyncThreadsTest {
    private static int cnt = 0;
    static class Task implements Runnable {
        @Override
        public void run() {
            System.out.print("This is ");
            for(int i=0;i<10;i++){
                cnt++;
            }
            System.out.print(cnt);
            System.out.print( " times to say \"Hello World\"\n");
        }
    }

    public static void main(String[] args){
        for(int i=0;i<10;i++){
            new Thread(new Task()).start();
        }
    }
}

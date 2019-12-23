package gc;
public class GCTest1 {

    private static final int MAX = 2000;

    public static void main(String[] args){
        test0();
        test1();
        test2();
    }


    private static void test0() {
        System.out.println("test0 start");
        String[] strs = new String[20];
        for (int i = 0; i < 20; i++){
            strs[i] = "a" + i;
        }
        System.out.println("test0 start");
    }

    private static void test1() {
        System.out.println("test1 start");
        String[] strs = new String[20];
        for (int i = 0; i < 20; i++){
            strs[i] = "b" + i;
        }
        System.out.println("test1 start");
    }

    private static void test2() {
        System.out.println("test2 start");
        String[] strs = new String[20];
        for (int i = 0; i < 20; i++){
            strs[i] = "c" + i;
        }
        System.out.println("test2 start");
    }
}


package gc;
public class GCTest {
    private String[] strings = null;
    private int cnt = 0;
    private static final int MAX = 2000;
    public static void produceGarbage(){
        for(int i=0;i<1000;i++){ new String(); }
    }
    public static void produceArrayGarbage(){
        for(int i=0;i<1000;i++) { String[] unused = new String[100]; }
    }
    public void fullGC(){
        for(int i=0;i<MAX;i++){ new String(); }
    }
    public void halfGC(){
        strings = new String[MAX/2];
        for(int i=0;i<MAX;i++) {
            if (i % 2 == 0){
                strings[cnt++] = "This is " + i + " times to say hello to you\n";
            }
        }
        cnt=0;
    }
    public void print(){
        for(int i=0;i<MAX/2;i++){
            System.out.println(strings[i]);
        }
    }
    public static void main(String[] args){
        produceGarbage();
        produceArrayGarbage();
        GCTest test = new GCTest();
        test.fullGC();
        test.fullGC();
        test.halfGC();
        test.halfGC();
        test.print();
    }
}


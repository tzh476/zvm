package jvmgo.book.ch09;

public class ArrayDemo {

    public static void main(String[] args) {
        int[] a1 = new int[10];       // newarray
        String[] a2 = new String[10]; // anewarray
        //int[][] a3 = new int[10][10]; // multianewarray
        int x = a1.length;            // arraylength
        a1[0] = 100;                  // iastore
        int y = a1[0];                // iaload
        a2[0] = "0abc";                // aastore
        String s = a2[0];             // aaload
        System.out.println( s);
        a2[1] = "1xxxxyyxyy";
        a2[2] = "2xxxxyyxyy";

        for(int i = 0; i < 3; i++){
            System.out.println(a2[i] + " stringbuilderTest");
        }
    }

}

package jvmgo.book.ch08;

public class DoubleArray1D {
    public static void main(String[] args) {
        double[] oneD = new double[5];
        for (int i = 0; i < 5; ++i) {
            oneD[i] = 100.1 + i;
        }
        for (int i = 0; i < 5; ++i) {
            System.out.println(oneD[i]);
        }
    }
}

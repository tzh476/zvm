package ch08;

public class Array1D {
    public static void main(String[] args) {
        int[] oneD = new int[5];
        for (int i = 0; i < 5; ++i) {
            oneD[i] = 100 + i;
        }
        for (int i = 0; i < 5; ++i) {
            System.out.println(oneD[i]);
        }
    }
}

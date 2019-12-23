package gc;
public class GCTest0 {


    public static void main(String[] args){
        System.out.println("arr1 begin ");
        int[] arr1 = new int[5];
        for (int i = 0; i < 5; ++i) {
            arr1[i] = 100 + i;
        }
        System.out.println("arr1 end ");

        System.out.println("arr2 begin ");
        int[] arr2 = new int[100];
        for (int i = 0; i < 100; ++i) {
            arr2[i] = 100 + i;
        }
        System.out.println("arr2 end ");

        int[] arr3 = new int[200];
        for (int i = 0; i < 200; ++i) {
            arr3[i] = 100 + i;
        }

        for (int i = 0; i < 5; ++i) {
            System.out.println(arr1[i]);
        }
    }



}


package ch08;

public class DoubleBubbleSortTest {
    
    public static void main(String[] args) {
        double[] arr = {
            22.2, 84.4, 77.5, 11.2, 95.3,  9.2, 78.2, 56.2,
            36.1, 97.1, 65.1, 36.1, 10.3, 24.3 ,92.3, 48.3
        };

        //printArray(arr);
        bubbleSort(arr);
        //System.out.println(123456789);
        printArray(arr);
    }
    
    private static void bubbleSort(double[] arr) {
        boolean swapped = true;
        int j = 0;
        double tmp;
        while (swapped) {
            swapped = false;
            j++;
            for (int i = 0; i < arr.length - j; i++) {
                if (arr[i] > arr[i + 1]) {
                    tmp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = tmp;
                    swapped = true;
                }
            }
        }
    }
    
    private static void printArray(double[] arr) {
        for (double i : arr) {
            System.out.println(i);
        }
    }
    
}

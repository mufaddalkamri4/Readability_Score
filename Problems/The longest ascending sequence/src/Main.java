import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);

        int size = scanner.nextInt();

        int[] arr = new int[size];

        for (int i = 0; i < size; i++) {
            arr[i] = scanner.nextInt();
        }

        int maxLength = 1;
        int currentMax = 1;
        for (int i = 0; i < size - 1; i++) {
            if (arr[i] < arr[i + 1]) {
                currentMax++;
                if(currentMax > maxLength){
                    maxLength = currentMax;
                }
            } else {
                currentMax = 1;
            }
        }
        System.out.println(maxLength);
    }
}
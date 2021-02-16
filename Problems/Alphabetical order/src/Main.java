import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        String[] arr = scanner.nextLine().split(" ");
        boolean lexicographicallyAscending = true;

        for (int i = 0; i < arr.length - 1; i++) {
            int flag = arr[i].compareTo(arr[i + 1]);
            if (flag > 0) {
                lexicographicallyAscending = false;
                break;
            }
        }
        System.out.println(lexicographicallyAscending);
    }
}
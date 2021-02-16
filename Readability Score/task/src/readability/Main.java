package readability;

import java.util.Scanner;

enum ReadabilityScore {
    HARD,
    EASY
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String inputString = scanner.nextLine();

        if (inputString.length() > 100) {
            System.out.println("HARD");
        } else {
            System.out.println("EASY");
        }
    }
}

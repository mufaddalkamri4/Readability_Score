package readability;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        File file = new File(args[0]);
        String inputString = "";
        Scanner scanner = null;

        try {
            scanner = new Scanner("./" + file);
        } catch (Exception e) {
            inputString = scanner.nextLine();
        }

        String[] sentences = inputString.split("[\\.!\\?]\\s");

        int totalWordCount = 0;
        
        for (String sentence : sentences) {
            String[] words = sentence.split("\\w+[.?!]?");
//            System.out.printf("%s : %d\n", sentence, words.length);
            totalWordCount += words.length;
        }
        
        double averageWordCount = (double) totalWordCount / sentences.length;
//        System.out.println(averageWordCount);

        if (averageWordCount > 10.0) {
            System.out.println("HARD");
        } else {
            System.out.println("EASY");
        }
    }
}

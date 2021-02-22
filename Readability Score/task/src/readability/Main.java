package readability;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Pattern;

// applying strategy design pattern
interface ReadScoreCalculationStrategy {
    double calculate(int... args);
}

class FleschKincaidStrategy implements ReadScoreCalculationStrategy {
    @Override
    public double calculate(int... args) {
        return 0;
    }
}

class ReadabilityScore {
    long characterCount;
    long wordCount;
    long sentenceCount;
    long syllables;
    long polysyllables;

    public ReadabilityScore(String text) {
        String[] sentences = text.split("[\\.!\\?]\\s");
        this.sentenceCount = sentences.length;
        this.wordCount = 0;
        this.characterCount = String.join("", text.split("\\s")).length();
        this.syllables = 0;
        this.polysyllables = 0;

        for (String sentence : sentences) {
            String[] words = sentence.split(",?\\s");
            this.wordCount += words.length;

            for(String word : words) {
                int tmp = (int) Pattern.compile("([aeiouyAEIOUY][^aeiouyAEIOUY\\s])|([aiouyAIOUY]$)").matcher(word).results().count();
                this.syllables += tmp == 0 ? 1 : tmp;
                if (tmp > 2) {
                    this.polysyllables++;
                }
            }
        }
    }

    public double calculateAri() {
        return (4.71 * characterCount / wordCount) + (0.5 * wordCount / sentenceCount) - 21.43;
    }

    public double calculateFk() {
        return 0.39 * wordCount / sentenceCount + 11.8 * syllables / wordCount - 15.59;
    }

    public double calculateSmog() {
        return 1.043 * Math.sqrt(polysyllables * 30 / sentenceCount) + 3.1291;
    }

    public double calculateCl() {
        return 0.0588 * characterCount / wordCount * 100 - 0.296 * sentenceCount / wordCount * 100 - 15.8;
    }

    public long getCharacterCount() {
        return characterCount;
    }
    public long getWordCount() {
        return wordCount;
    }
    public long getSentenceCount() {
        return sentenceCount;
    }
    public long getSyllablesCount() {
        return syllables;
    }
    public long getPolysyllablesCount() {
        return polysyllables;
    }

    public static int ageGroupToUnderstand(double scoreIn) {
        int score = (int) Math.ceil(scoreIn);

        switch (score) {
            case 1:
                return 6;
            case 2:
                return 7;
            case 3:
                return 9;
            case 4:
                return 10;
            case 5:
                return 11;
            case 6:
                return 12;
            case 7:
                return 13;
            case 8:
                return 14;
            case 9:
                return 15;
            case 10:
                return 16;
            case 11:
                return 17;
            case 12:
                return 18;
            default:
                return 24;

        }
    }
}

public class Main {
    public static String readFileAsString(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String inputString = "";
        ReadabilityScore rs = null;

        try {
            inputString = readFileAsString(args[0]);
//            System.out.println(inputString);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            inputString = "";
        }

        rs = new ReadabilityScore(inputString);

        System.out.printf(
                "The text is:\n%s\n\nWords: %d\nSentences: %d\nCharacters: %d\nSyllables: %d\nPolysyllables: %d\nEnter the score you want to calculate (ARI, FK, SMOG, CL, all): ",
                inputString,
                rs.getWordCount(),
                rs.getSentenceCount(),
                rs.getCharacterCount(),
                rs.getSyllablesCount(),
                rs.getPolysyllablesCount()
        );

        String indexType = scanner.next();
        double score;
        double sum = 0;

        System.out.println();
        switch (indexType) {
            case "ARI":
                score = rs.calculateAri();
                System.out.printf("Automated Readability Index: %f (about %s-year-olds).\n", score, ReadabilityScore.ageGroupToUnderstand(score));
                break;
            case "FK":
                score = rs.calculateFk();
                System.out.printf("Flesch–Kincaid readability tests: %f (about %s-year-olds).\n", score, ReadabilityScore.ageGroupToUnderstand(score));
                break;
            case "SMOG":
                score = rs.calculateSmog();
                System.out.printf("Simple Measure of Gobbledygook: %f (about %s-year-olds).\n", score, ReadabilityScore.ageGroupToUnderstand(score));
                break;
            case "CL":
                score = rs.calculateCl();
                System.out.printf("Coleman–Liau index: %f (about %s-year-olds).\n", score, ReadabilityScore.ageGroupToUnderstand(score));
                break;
            case "all":
                score = rs.calculateAri();
                sum += ReadabilityScore.ageGroupToUnderstand(score);
                System.out.printf("Automated Readability Index: %f (about %s-year-olds).\n", score, ReadabilityScore.ageGroupToUnderstand(score));
                score = rs.calculateFk();
                sum += ReadabilityScore.ageGroupToUnderstand(score);
                System.out.printf("Flesch–Kincaid readability tests: %f (about %s-year-olds).\n", score, ReadabilityScore.ageGroupToUnderstand(score));
                score = rs.calculateSmog();
                sum += ReadabilityScore.ageGroupToUnderstand(score);
                System.out.printf("Simple Measure of Gobbledygook: %f (about %s-year-olds).\n", score, ReadabilityScore.ageGroupToUnderstand(score));
                score = rs.calculateCl();
                sum += ReadabilityScore.ageGroupToUnderstand(score);
                System.out.printf("Coleman–Liau index: %f (about %s-year-olds).\n", score, ReadabilityScore.ageGroupToUnderstand(score));

                System.out.printf("This text should be understood in average by %f-year-olds.", sum / 4.0);

        }
    }
}

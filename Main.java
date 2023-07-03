import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        String inputFolder = "input";
        String outputFolder = "output";

        processFiles(inputFolder, outputFolder);
    }

    public static void processFiles(String inputFolder, String outputFolder) {
        File folder = new File(inputFolder);
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".in")) {
                    String inputFile = file.getAbsolutePath();
                    String outputFile = outputFolder + File.separator + file.getName().replace(".in", ".out");
                    processFile(inputFile, outputFile, file.getName());
                }
            }
        } else {
            System.out.println("Empty folder");
        }
    }

    public static void processFile(String inputFile, String outputFile, String fileName) {
        try {
            Scanner scanner = new Scanner(new File(inputFile));
            PrintWriter writer = new PrintWriter(outputFile);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                String processedLine = processLine(line, fileName);
                writer.printf(processedLine);
            }

            scanner.close();
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String processLine(String fileContent, String fileName) {

        String[] separatedStrings = fileContent.split(" ");

        Integer[] intArray = new Integer[separatedStrings.length];

        for (int i = 0; i < separatedStrings.length; i++) {

            try {
                intArray[i] = Integer.parseInt(separatedStrings[i]);
            } catch (Exception e) {
                System.out.println("Unable to parse string to int: " + e.getMessage());
            }
        }

        MergeSort.mergeSortThreads(intArray, fileName);

        return Arrays.toString(intArray);
    }
}
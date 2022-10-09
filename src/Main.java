import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

public class Main {
    static List<Integer> numbers = new ArrayList<>();

    public static void main(String[] args) {
        readCSV();
        Scanner s = new Scanner(System.in);
        System.out.println("Geben sie einen Teiler ein:");
        int teiler = s.nextInt();
        System.out.println("Wie groß soll ein chunk sein?");
        int threads = s.nextInt();
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
        System.out.println("Size: " + numbers.size());
        int chunksCounter = 0;
        NumberChecker fc = new NumberChecker(numbers.subList(0, threads), teiler);
        executor.execute(fc);
        chunksCounter++;
        boolean chunking = true;
        while (chunking) {
            try {
                NumberChecker nc = new NumberChecker(numbers.subList(threads * chunksCounter + 1, threads * chunksCounter + threads), teiler);
                executor.execute(nc);
                chunksCounter++;
            } catch (IndexOutOfBoundsException e) {
                chunking = false;
                System.out.println("Threads: " + chunksCounter+1);
            }
        }
    }

    public static void readCSV() {
        try {
            List<String> strings = Files.lines(Path.of("numbers.csv")).toList().stream().toList();
            strings.forEach(line -> {
                String[] splitted = line.split(":");
                for (String s : splitted) {
                    try {
                        numbers.add(Integer.parseInt(s));
                    } catch (NumberFormatException ignored) {
                    }
                }
            });
        } catch (IOException e) {
            System.out.println("crashed");
        }
    }
}
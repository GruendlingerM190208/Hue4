import java.util.List;

public class NumberChecker implements Runnable {
    List<Integer> checkNumbers;
    int teiler;

    public NumberChecker(List<Integer> checkNumbers, int teiler) {
        this.checkNumbers = checkNumbers;
        this.teiler = teiler;
    }

    @Override
    public void run() {
        checkNumbers.forEach(m -> {
            if (m % teiler == 0) {
                System.out.println(m);
            }
        });
    }
}

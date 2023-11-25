import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class SumCalculator implements Runnable {
    private final int[] arr;
    private final int start;
    private final int end;
    private int result;

    public SumCalculator(int[] arr, int start, int end) {
        this.arr = arr;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        for (int i = start; i < end; i++) {
            result += arr[i];
        }
    }

    public int getResult() {
        return result;
    }
}

public class Main {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int numThreads = 5;
        int chunkSize = arr.length / numThreads;

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        SumCalculator[] calculators = new SumCalculator[numThreads];
        int startIndex = 0;
        int endIndex = chunkSize;

        // Create and submit tasks to the executor
        for (int i = 0; i < numThreads; i++) {
            calculators[i] = new SumCalculator(arr, startIndex, endIndex);
            executor.submit(calculators[i]);
            startIndex = endIndex;
            endIndex = (i < numThreads - 2) ? endIndex + chunkSize : arr.length;
        }
        executor.shutdown();
        // Calculate total sum
        int totalSum = 0;
        for (SumCalculator calculator : calculators) {
            totalSum += calculator.getResult();
        }
        System.out.println("Total sum: " + totalSum);
    }
}

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class SumTask implements Runnable {

    int arr[];
    int start;
    int end;
    int sum;
    SumTask(int [] arr,int start,int end){
        this.arr=arr;
        this.start=start;
        this.end=end;
    }


    @Override
    public void run() {
        for(int i=start;i<end;i++) {
            sum=sum+arr[i];
        }
    }
    public int getSum() {
        return sum;
    }
}

public class Sum {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int numThread=5;
        int chunks=arr.length/numThread;
        ExecutorService ex=Executors.newFixedThreadPool(numThread);
        SumTask task[]=new SumTask[5];
        int startIndex = 0;
        int endIndex = chunks;
        // Create and submit tasks to the executor
        for (int i = 0; i < numThread; i++) {
            task[i] = new SumTask(arr, startIndex, endIndex);
            ex.submit(task[i]);
            startIndex = endIndex;
            endIndex = (i < numThread - 2) ? endIndex + chunks : arr.length;
        }
        ex.shutdown();
        int totalSum=0;
        for(SumTask sumTask:task) {
            totalSum=totalSum+ sumTask.getSum();
        }
        System.out.println(totalSum);
    }
}

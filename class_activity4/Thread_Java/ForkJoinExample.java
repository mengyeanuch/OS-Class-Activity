import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

// A task that can be split into smaller subtasks
class SumTask extends RecursiveTask<Integer> {

    private int start;
    private int end;

    // Constructor
    public SumTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    // This method defines the task logic
    @Override
    protected Integer compute() {

        // Base case: small enough task
        if (end - start <= 5) {
            int sum = 0;
            for (int i = start; i <= end; i++) {
                sum += i;
            }
            System.out.println(
                Thread.currentThread().getName() +
                " computing sum from " + start + " to " + end
            );
            return sum;
        }

        // Split task into two subtasks
        int mid = (start + end) / 2;

        SumTask leftTask = new SumTask(start, mid);
        SumTask rightTask = new SumTask(mid + 1, end);

        // Fork the left task
        leftTask.fork();

        // Compute the right task
        int rightResult = rightTask.compute();

        // Join the left task result
        int leftResult = leftTask.join();

        return leftResult + rightResult;
    }
}

public class ForkJoinExample {
    public static void main(String[] args) {

        // Create ForkJoinPool
        ForkJoinPool pool = new ForkJoinPool();

        // Create main task
        SumTask task = new SumTask(1, 2000);

        // Start Fork–Join execution
        int result = pool.invoke(task);

        System.out.println("Final Result = " + result);
    }
}

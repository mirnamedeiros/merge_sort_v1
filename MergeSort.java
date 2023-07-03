import java.util.ArrayList;

public class MergeSort {
    // Number of threads
    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();

    // Merge sort using threads
    public static void mergeSortThreads(Integer[] array, String fileName) {
        // Current time in milliseconds
        long time = System.currentTimeMillis();
        final int length = array.length;

        // The array length is divided by the number os threads
        boolean exact = length % NUM_THREADS == 0;
        // If the remaindering value is zero: the numbers are divided equally
        // Else: consider NUM_THREADS - 1 is available and the remaindering elements
        // are assigned to the remaindering thread
        int workload = exact ? length / NUM_THREADS : length / (NUM_THREADS - 1);

        final ArrayList<Worker> threads = new ArrayList<>();
        // Assigning the threads working index ranges
        for (int i = 0; i < length; i += workload) {
            int beg = i;
            int remainder = (length) - i;
            int end = remainder < workload ? i + (remainder - 1) : i + (workload - 1);
            final Worker t = new Worker(array, beg, end);

            // Add the thread references to join them later
            threads.add(t);
        }
        for (Worker t : threads) {
            try {
                // Wait for threads to complete their work
                t.join();
            } catch (InterruptedException ignored) {
            }
        }
        // Merging sorted arrays
        for (int i = 0; i < length; i += workload) {
            int middle = i == 0 ? 0 : i - 1;
            int remainder = (length) - i;
            int end = remainder < workload ? i + (remainder - 1) : i + (workload - 1);

            merge(array, 0, middle, end);
        }
        time = System.currentTimeMillis() - time;
        System.out.println("Time spent for Merge Sort on file " + fileName + ": " + time + "ms");
    }

    // Implementation of recursive merge sort
    public static void mergeSort(Integer[] array, int begin, int end) {
        if (begin < end) {
            int middle = (begin + end) / 2;
            mergeSort(array, begin, middle);
            mergeSort(array, middle + 1, end);
            merge(array, begin, middle, end);
        }
    }

    // Implementation of basic merge of arrays
    public static void merge(Integer[] array, int begin, int middle, int end) {
        Integer[] temp = new Integer[(end - begin) + 1];

        int i = begin, j = middle + 1;
        int k = 0;

        // Add elements from first half or second half based on whichever is lower,
        // do until one of the list is exhausted and no more direct one-to-one
        // comparison could be made
        while (i <= middle && j <= end) {
            if (array[i] <= array[j]) {
                temp[k] = array[i];
                i += 1;
            } else {
                temp[k] = array[j];
                j += 1;
            }
            k += 1;
        }

        // Add remaindering elements to temp array from first half that are left over
        while (i <= middle) {
            temp[k] = array[i];
            i += 1;
            k += 1;
        }

        // Add remaindering elements to temp array from second half that are left over
        while (j <= end) {
            temp[k] = array[j];
            j += 1;
            k += 1;
        }

        for (i = begin, k = 0; i <= end; i++, k++) {
            array[i] = temp[k];
        }
    }
}

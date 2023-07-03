// Custom Thread class
public class Worker extends Thread {
    Worker(Integer[] array, int begin, int end) {
        super(() -> {
            MergeSort.mergeSort(array, begin, end);
        });
        this.start();
    }
}

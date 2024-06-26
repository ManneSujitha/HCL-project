import java.util.Arrays;
import java.util.Scanner;

public class MergeSort {
    public void sort(int[] array) {
        if (array == null || array.length == 0) {
            return;
        }
        int[] tempArray = new int[array.length];
        mergeSort(array, tempArray, 0, array.length - 1);
    }

    private void mergeSort(int[] array, int[] tempArray, int leftStart, int rightEnd) {
        if (leftStart >= rightEnd) {
            return;
        }
        int middle = (leftStart + rightEnd) / 2;
        MergeSortTask leftTask = new MergeSortTask(array, tempArray, leftStart, middle);
        MergeSortTask rightTask = new MergeSortTask(array, tempArray, middle + 1, rightEnd);

        leftTask.start();
        rightTask.start();

        try {
            leftTask.join();
            rightTask.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mergeHalves(array, tempArray, leftStart, rightEnd);
    }

    private void mergeHalves(int[] array, int[] tempArray, int leftStart, int rightEnd) {
        int middle = (leftStart + rightEnd) / 2;
        int leftEnd = middle;
        int rightStart = middle + 1;

        int left = leftStart;
        int right = rightStart;
        int index = leftStart;

        while (left <= leftEnd && right <= rightEnd) {
            if (array[left] <= array[right]) {
                tempArray[index] = array[left];
                left++;
            } else {
                tempArray[index] = array[right];
                right++;
            }
            index++;
        }

        System.arraycopy(array, left, tempArray, index, leftEnd - left + 1);
        System.arraycopy(array, right, tempArray, index, rightEnd - right + 1);
        System.arraycopy(tempArray, leftStart, array, leftStart, rightEnd - leftStart + 1);
    }

    private class MergeSortTask extends Thread {
        private int[] array;
        private int[] tempArray;
        private int leftStart;
        private int rightEnd;

        public MergeSortTask(int[] array, int[] tempArray, int leftStart, int rightEnd) {
            this.array = array;
            this.tempArray = tempArray;
            this.leftStart = leftStart;
            this.rightEnd = rightEnd;
        }

        @Override
        public void run() {
            mergeSort(array, tempArray, leftStart, rightEnd);
        }

        private void mergeSort(int[] array, int[] tempArray, int leftStart, int rightEnd) {
            if (leftStart >= rightEnd) {
                return;
            }
            int middle = (leftStart + rightEnd) / 2;
            MergeSortTask leftTask = new MergeSortTask(array, tempArray, leftStart, middle);
            MergeSortTask rightTask = new MergeSortTask(array, tempArray, middle + 1, rightEnd);

            leftTask.start();
            rightTask.start();

            try {
                leftTask.join();
                rightTask.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            mergeHalves(array, tempArray, leftStart, rightEnd);
        }

        private void mergeHalves(int[] array, int[] tempArray, int leftStart, int rightEnd) {
            int middle = (leftStart + rightEnd) / 2;
            int leftEnd = middle;
            int rightStart = middle + 1;

            int left = leftStart;
            int right = rightStart;
            int index = leftStart;

            while (left <= leftEnd && right <= rightEnd) {
                if (array[left] <= array[right]) {
                    tempArray[index] = array[left];
                    left++;
                } else {
                    tempArray[index] = array[right];
                    right++;
                }
                index++;
            }

            System.arraycopy(array, left, tempArray, index, leftEnd - left + 1);
            System.arraycopy(array, right, tempArray, index, rightEnd - right + 1);
            System.arraycopy(tempArray, leftStart, array, leftStart, rightEnd - leftStart + 1);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of elements in the array:");
        int n = scanner.nextInt();
        int[] array = new int[n];

        System.out.println("Enter the elements of the array:");
        for (int i = 0; i < n; i++) {
            array[i] = scanner.nextInt();
        }

        System.out.println("Original Array: " + Arrays.toString(array));

        MergeSort mergeSort = new MergeSort();
        mergeSort.sort(array);

        System.out.println("Sorted Array: " + Arrays.toString(array));
    }
}

package algos.sort;

import algos.metrics.Metrics;

public class MergeSort {
    private static final int CUTOFF = 32;

    public static void sort(int[] a, Metrics m) {
        int[] buf = new int[a.length];
        m.addAllocations(a.length); // one reusable buffer allocation
        sort(a, 0, a.length, buf, m);
    }

    private static void sort(int[] a, int lo, int hi, int[] buf, Metrics m) {
        int n = hi - lo;
        if (n <= 1) return;
        if (n <= CUTOFF) { insertionSort(a, lo, hi, m); return; }
        m.incDepth();
        int mid = lo + (n >> 1);
        sort(a, lo, mid, buf, m);
        sort(a, mid, hi, buf, m);
        merge(a, lo, mid, hi, buf, m);
        m.decDepth();
    }

    private static void insertionSort(int[] a, int lo, int hi, Metrics m) {
        for (int i = lo + 1; i < hi; i++) {
            int x = a[i];
            int j = i - 1;
            while (j >= lo && (++m.comparisons >= 0) && a[j] > x) {
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = x;
        }
    }

    private static void merge(int[] a, int lo, int mid, int hi, int[] buf, Metrics m) {
        int i = lo, j = mid, k = 0;
        while (i < mid && j < hi) {
            m.comparisons++;
            if (a[i] <= a[j]) buf[k++] = a[i++];
            else buf[k++] = a[j++];
        }
        while (i < mid) buf[k++] = a[i++];
        while (j < hi) buf[k++] = a[j++];
        System.arraycopy(buf, 0, a, lo, k);
    }
}

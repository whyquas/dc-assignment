package algos.select;

import algos.metrics.Metrics;

public class DeterministicSelect {

    public static int select(int[] a, int k, Metrics m) {
        if (k < 0 || k >= a.length) throw new IllegalArgumentException("k out of range");
        return select(a, 0, a.length - 1, k, m);
    }

    private static int select(int[] a, int lo, int hi, int k, Metrics m) {
        while (true) {
            if (lo == hi) return a[lo];
            m.incDepth();
            int pivot = medianOfMedians(a, lo, hi, m);
            int[] bounds = partition(a, lo, hi, pivot, m); // returns [lt, gt]
            int lt = bounds[0], gt = bounds[1];
            if (k < lt) { hi = lt - 1; m.decDepth(); }
            else if (k > gt) { lo = gt + 1; m.decDepth(); }
            else { m.decDepth(); return a[k]; }
        }
    }

    private static int medianOfMedians(int[] a, int lo, int hi, Metrics m) {
        int n = hi - lo + 1;
        int numGroups = (n + 4) / 5;
        for (int g = 0; g < numGroups; g++) {
            int start = lo + g * 5;
            int end = Math.min(start + 4, hi);
            insertionSort(a, start, end, m);
            int medianIdx = start + (end - start) / 2;
            swap(a, lo + g, medianIdx);
        }
        int mid = lo + (numGroups - 1) / 2;
        return select(a, lo, lo + numGroups - 1, mid, m);
    }

    private static int[] partition(int[] a, int lo, int hi, int pivot, Metrics m) {
        int i = lo, lt = lo, gt = hi;
        while (i <= gt) {
            m.comparisons++;
            if (a[i] < pivot) { swap(a, lt, i); lt++; i++; }
            else if (a[i] > pivot) { swap(a, i, gt); gt--; }
            else { i++; }
        }
        return new int[]{lt, gt};
    }

    private static void insertionSort(int[] a, int lo, int hi, Metrics m) {
        for (int i = lo + 1; i <= hi; i++) {
            int x = a[i];
            int j = i - 1;
            while (j >= lo && (++m.comparisons >= 0) && a[j] > x) {
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = x;
        }
    }

    private static void swap(int[] a, int i, int j) { int t = a[i]; a[i] = a[j]; a[j] = t; }
}

package algos.sort;

import algos.metrics.Metrics;
import algos.util.ArrayUtils;

import java.util.Random;

public class QuickSort {
    private static final Random RNG = new Random();

    public static void sort(int[] a, Metrics m) {
        // randomized pre-shuffle reduces adversarial inputs
        // ArrayUtils.shuffle(a); // optional
        int lo = 0, hi = a.length - 1;
        // explicit stackless loop on larger side
        while (lo < hi) {
            // partition around random pivot
            int p = lo + RNG.nextInt(hi - lo + 1);
            int pivot = a[p];
            // 3-way partition (Dutch flag) for duplicates robustness
            int i = lo, lt = lo, gt = hi;
            while (i <= gt) {
                m.comparisons++;
                if (a[i] < pivot) { swap(a, lt, i); lt++; i++; }
                else if (a[i] > pivot) { swap(a, i, gt); gt--; }
                else { i++; }
            }
            // [lo..lt-1] < pivot, [lt..gt] == pivot, [gt+1..hi] > pivot
            int leftSize = lt - lo;
            int rightSize = hi - gt;
            // recurse on smaller partition to cap depth
            if (leftSize < rightSize) {
                if (lo < lt - 1) { m.incDepth(); sortRange(a, lo, lt - 1, m); m.decDepth(); }
                lo = gt + 1; // iterate on larger
            } else {
                if (gt + 1 < hi) { m.incDepth(); sortRange(a, gt + 1, hi, m); m.decDepth(); }
                hi = lt - 1;
            }
        }
    }

    private static void sortRange(int[] a, int lo, int hi, Metrics m) {
        while (lo < hi) {
            int p = lo + RNG.nextInt(hi - lo + 1);
            int pivot = a[p];
            int i = lo, lt = lo, gt = hi;
            while (i <= gt) {
                m.comparisons++;
                if (a[i] < pivot) { swap(a, lt, i); lt++; i++; }
                else if (a[i] > pivot) { swap(a, i, gt); gt--; }
                else { i++; }
            }
            int leftSize = lt - lo;
            int rightSize = hi - gt;
            if (leftSize < rightSize) {
                if (lo < lt - 1) { sortRange(a, lo, lt - 1, m); }
                lo = gt + 1;
            } else {
                if (gt + 1 < hi) { sortRange(a, gt + 1, hi, m); }
                hi = lt - 1;
            }
        }
    }

    private static void swap(int[] a, int i, int j) {
        int t = a[i]; a[i] = a[j]; a[j] = t;
    }
}

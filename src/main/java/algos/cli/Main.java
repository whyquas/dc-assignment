package algos.cli;

import algos.metrics.Metrics;
import algos.sort.MergeSort;
import algos.sort.QuickSort;
import algos.select.DeterministicSelect;
import algos.geometry.ClosestPair2D;
import algos.geometry.ClosestPair2D.Point;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws Exception {
        String algo = "mergesort";
        int n = 100000;
        String csv = null;
        for (int i=0;i<args.length;i++) {
            switch (args[i]) {
                case "--algo": algo = args[++i]; break;
                case "--n": n = Integer.parseInt(args[++i]); break;
                case "--csv": csv = args[++i]; break;
            }
        }
        Metrics m = new Metrics();
        long t0 = System.nanoTime();
        if (algo.equalsIgnoreCase("mergesort")) {
            int[] a = randomInts(n);
            MergeSort.sort(a, m);
            assert isSorted(a);
        } else if (algo.equalsIgnoreCase("quicksort")) {
            int[] a = randomInts(n);
            QuickSort.sort(a, m);
            assert isSorted(a);
        } else if (algo.equalsIgnoreCase("select")) {
            int[] a = randomInts(n);
            int k = n/2;
            int val = DeterministicSelect.select(a, k, m);
            // validate by partial sort
            java.util.Arrays.sort(a);
            assert val == a[k];
        } else if (algo.equalsIgnoreCase("closest")) {
            Point[] pts = randomPoints(n);
            double d = ClosestPair2D.closest(pts, m);
            assert d >= 0.0;
        } else {
            System.err.println("Unknown --algo");
            System.exit(1);
        }
        long t1 = System.nanoTime();
        long time = t1 - t0;
        System.out.printf("algo=%s n=%d time_ns=%d comps=%d depth=%d allocs=%d%n",
                algo, n, time, m.comparisons, m.maxDepth, m.allocations);
        if (csv != null) {
            try (PrintWriter out = new PrintWriter(new FileWriter(csv, true))) {
                out.printf("%s,%d,%d,%d,%d,%d%n", algo, n, time, m.comparisons, m.maxDepth, m.allocations);
            }
        }
    }

    private static int[] randomInts(int n) {
        Random rng = new Random(42);
        int[] a = new int[n];
        for (int i=0;i<n;i++) a[i]=rng.nextInt();
        return a;
    }

    private static ClosestPair2D.Point[] randomPoints(int n) {
        Random rng = new Random(42);
        Point[] p = new Point[n];
        for (int i=0;i<n;i++) p[i]=new Point(rng.nextDouble(), rng.nextDouble());
        return p;
    }

    private static boolean isSorted(int[] a) {
        for (int i=1;i<a.length;i++) if (a[i-1] > a[i]) return false;
        return true;
    }
}

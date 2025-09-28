package algos.sort;

import algos.metrics.Metrics;
import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class QuickSortTest {
    @Test
    void randomAndAdversarial() {
        Random rng = new Random(2);
        // random
        int[] a = new int[20000];
        for (int i=0;i<a.length;i++) a[i]=rng.nextInt();
        int[] b = a.clone();
        java.util.Arrays.sort(b);
        Metrics m = new Metrics();
        QuickSort.sort(a, m);
        assertArrayEquals(b, a);
        // depth bound (heuristic)
        int bound = 2 * (int)Math.floor(Math.log(a.length) / Math.log(2)) + 10;
        assertTrue(m.maxDepth <= bound, "Depth too large: " + m.maxDepth);
        // adversarial: already sorted with many duplicates
        int[] c = new int[5000];
        for (int i=0;i<c.length;i++) c[i]=i/5;
        int[] d = c.clone(); java.util.Arrays.sort(d);
        Metrics m2 = new Metrics();
        QuickSort.sort(c, m2);
        assertArrayEquals(d, c);
        assertTrue(m2.maxDepth <= 2 * (int)Math.floor(Math.log(c.length)/Math.log(2)) + 20);
    }
}

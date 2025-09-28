package algos.sort;

import algos.metrics.Metrics;
import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class MergeSortTest {
    @Test
    void randomCorrectness() {
        Random rng = new Random(1);
        for (int t=0;t<50;t++) {
            int n = 1000;
            int[] a = new int[n];
            for (int i=0;i<n;i++) a[i]=rng.nextInt();
            int[] b = a.clone();
            java.util.Arrays.sort(b);
            Metrics m = new Metrics();
            MergeSort.sort(a, m);
            assertArrayEquals(b, a);
            assertTrue(m.maxDepth >= 0);
        }
    }
}

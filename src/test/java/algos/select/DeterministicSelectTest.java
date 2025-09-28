package algos.select;

import algos.metrics.Metrics;
import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class DeterministicSelectTest {
    @Test
    void compareAgainstSort() {
        Random rng = new Random(3);
        for (int t=0;t<100;t++) {
            int n = 1000;
            int[] a = new int[n];
            for (int i=0;i<n;i++) a[i]=rng.nextInt();
            int[] b = a.clone();
            java.util.Arrays.sort(b);
            int k = rng.nextInt(n);
            Metrics m = new Metrics();
            int val = DeterministicSelect.select(a, k, m);
            assertEquals(b[k], val);
        }
    }
}

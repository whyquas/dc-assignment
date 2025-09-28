package algos.geometry;

import algos.metrics.Metrics;
import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class ClosestPair2DTest {
    @Test
    void smallNValidateAgainstN2() {
        Random rng = new Random(4);
        for (int t=0;t<10;t++) {
            int n = 500;
            ClosestPair2D.Point[] pts = new ClosestPair2D.Point[n];
            for (int i=0;i<n;i++) pts[i] = new ClosestPair2D.Point(rng.nextDouble(), rng.nextDouble());
            double fast = ClosestPair2D.closest(pts, new Metrics());
            double slow = brute(pts);
            assertEquals(slow, fast, 1e-12);
        }
    }

    private static double brute(ClosestPair2D.Point[] pts) {
        double best = Double.POSITIVE_INFINITY;
        for (int i=0;i<pts.length;i++)
            for (int j=i+1;j<pts.length;j++) {
                double dx = pts[i].x()-pts[j].x(), dy = pts[i].y()-pts[j].y();
                double d = Math.hypot(dx, dy);
                if (d < best) best = d;
            }
        return best;
    }
}

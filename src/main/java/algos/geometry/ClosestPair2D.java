package algos.geometry;

import algos.metrics.Metrics;
import java.util.*;

public class ClosestPair2D {

    public static record Point(double x, double y) {}

    public static double closest(Point[] pts, Metrics m) {
        Point[] px = pts.clone();
        Arrays.sort(px, Comparator.comparingDouble(Point::x));
        Point[] py = px.clone();
        return rec(px, py, 0, px.length, m);
    }

    private static double rec(Point[] px, Point[] py, int lo, int hi, Metrics m) {
        int n = hi - lo;
        if (n <= 3) {
            double d = Double.POSITIVE_INFINITY;
            for (int i = lo; i < hi; i++)
                for (int j = i + 1; j < hi; j++) {
                    m.comparisons++;
                    d = Math.min(d, dist(px[i], px[j]));
                }
            Arrays.sort(px, lo, hi, Comparator.comparingDouble(Point::y));
            return d;
        }
        m.incDepth();
        int mid = lo + n/2;
        double midx = px[mid].x();
        // split py by midx while preserving y-order
        Point[] leftY = new Point[n];
        Point[] rightY = new Point[n];
        int li=0, ri=0;
        for (int i=0; i<py.length; i++) {
            if (py[i].x() < midx || (py[i].x()==midx && py[i].y() <= px[mid].y())) leftY[li++] = py[i];
            else rightY[ri++] = py[i];
        }
        double dl = rec(px, leftY, lo, mid, m);
        double dr = rec(px, rightY, mid, hi, m);
        double d = Math.min(dl, dr);
        // build strip
        Point[] strip = new Point[n];
        int s = 0;
        for (int i=0;i<li;i++) if (Math.abs(leftY[i].x()-midx) < d) strip[s++] = leftY[i];
        for (int i=0;i<ri;i++) if (Math.abs(rightY[i].x()-midx) < d) strip[s++] = rightY[i];
        // check up to next 7 points
        for (int i=0; i<s; i++) {
            for (int j=i+1; j<Math.min(i+8, s); j++) {
                m.comparisons++;
                double dij = dist(strip[i], strip[j]);
                if (dij < d) d = dij;
            }
        }
        m.decDepth();
        return d;
    }

    private static double dist(Point a, Point b) {
        double dx = a.x()-b.x(), dy = a.y()-b.y();
        return Math.hypot(dx, dy);
    }
}

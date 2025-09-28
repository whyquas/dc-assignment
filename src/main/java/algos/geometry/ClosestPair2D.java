package algos.geometry;

import algos.metrics.Metrics;
import java.util.*;

public class ClosestPair2D {

    public static record Point(double x, double y) {}

    public static double closest(Point[] pts, Metrics m) {
        Point[] px = pts.clone();
        Arrays.sort(px, Comparator.comparingDouble(Point::x));
        Point[] py = pts.clone();
        Arrays.sort(py, Comparator.comparingDouble(Point::y));
        return rec(px, py, m);
    }

    private static double rec(Point[] px, Point[] py, Metrics m) {
        int n = px.length;
        if (n <= 3) {
            double d = Double.POSITIVE_INFINITY;
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    m.comparisons++;
                    d = Math.min(d, dist(px[i], px[j]));
                }
            }
            return d;
        }

        m.incDepth();
        int mid = n / 2;
        double midx = px[mid].x();


        Point[] pxL = Arrays.copyOfRange(px, 0, mid);
        Point[] pxR = Arrays.copyOfRange(px, mid, n);


        List<Point> pylList = new ArrayList<>(mid);
        List<Point> pyrList = new ArrayList<>(n - mid);
        for (Point p : py) {
            if (p.x() < midx || (p.x() == midx && p.y() <= px[mid].y())) pylList.add(p);
            else pyrList.add(p);
        }
        Point[] pyl = pylList.toArray(new Point[0]);
        Point[] pyr = pyrList.toArray(new Point[0]);

        double dl = rec(pxL, pyl, m);
        double dr = rec(pxR, pyr, m);
        double d = Math.min(dl, dr);


        List<Point> strip = new ArrayList<>();
        for (Point p : py) if (Math.abs(p.x() - midx) < d) strip.add(p);

        int s = strip.size();
        for (int i = 0; i < s; i++) {

            int limit = Math.min(i + 7, s - 1);
            for (int j = i + 1; j <= limit; j++) {
                m.comparisons++;
                double dij = dist(strip.get(i), strip.get(j));
                if (dij < d) d = dij;
            }
        }
        m.decDepth();
        return d;
    }

    private static double dist(Point a, Point b) {
        double dx = a.x() - b.x(), dy = a.y() - b.y();
        return Math.hypot(dx, dy);
    }
}
package algos.metrics;

public class Metrics {
    public long comparisons = 0;
    public long allocations = 0;
    public int maxDepth = 0;
    private int currentDepth = 0;

    public void incDepth() { currentDepth++; if (currentDepth > maxDepth) maxDepth = currentDepth; }
    public void decDepth() { currentDepth--; if (currentDepth < 0) currentDepth = 0; }

    public void addComparisons(long c) { comparisons += c; }
    public void addAllocations(long a) { allocations += a; }

    public void reset() {
        comparisons = 0;
        allocations = 0;
        maxDepth = 0;
        currentDepth = 0;
    }
}

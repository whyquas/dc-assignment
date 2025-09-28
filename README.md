# Assignment 1 – Divide & Conquer

This repo implements MergeSort, QuickSort (randomized, smaller-first recursion), Deterministic Select (Median-of-Medians), and Closest Pair of Points (2D).

## How to run

```bash
mvn -q -e -DskipTests package
java -jar target/dc-assignment-1.0-SNAPSHOT.jar --algo mergesort --n 100000 --csv out.csv
```

Or run tests:
```bash
mvn test
```

## Report (fill this in)

### Architecture notes
- Depth control: QuickSort recurses on the smaller partition; larger handled by tail-iteration.
- Memory: MergeSort uses a reusable buffer passed down recursion.
- Metrics: `Metrics` tracks comparisons, allocations (buffer uses), and recursion depth.

### Recurrences (sketch)
- MergeSort: T(n)=2T(n/2)+Θ(n) ⇒ Θ(n log n) (Master case 2).
- QuickSort (avg): T(n)=T(i)+T(n-i-1)+Θ(n) with randomized pivot ⇒ Θ(n log n) expected; depth ~ Θ(log n).
- Deterministic Select: T(n)=T(n/5)+T(7n/10)+Θ(n) ⇒ Θ(n) (Akra–Bazzi / median-of-medians).
- Closest Pair: T(n)=2T(n/2)+Θ(n) (after presort + strip check) ⇒ Θ(n log n).

### Plots
Use the CLI to emit CSV (n, timeNs, depth, comps) and plot externally.

### Summary
Briefly compare measured growth vs theoretical predictions.

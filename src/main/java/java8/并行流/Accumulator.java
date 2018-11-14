package java8.并行流;

public class Accumulator {
    public long total;

    public void add (long value) {
        total += value;
    }
}

package com.cooltomatos.aoc.y2025.d02;

import com.cooltomatos.aoc.AbstractDay;
import java.util.Arrays;
import java.util.List;
import java.util.function.LongPredicate;
import java.util.stream.LongStream;

public class Day extends AbstractDay {
  private final List<Range> ranges;

  public Day(String dir, String file) {
    super(2025, 2, dir, file);
    ranges = Arrays.stream(input.getFirst().split(",")).map(Range::new).toList();
  }

  @Override
  public Long part1() {
    return ranges.stream()
        .map(
            range ->
                range.getInvalidIds(
                    id -> {
                      int length = 1 + (int) Math.floor(Math.log10(id));
                      int repeats = 2;
                      int chunkSize = length / repeats;
                      if (chunkSize * repeats != length) {
                        return true;
                      }

                      long tail = id % (long) Math.pow(10, chunkSize);
                      return id != tail + tail * Math.pow(10, chunkSize);
                    }))
        .flatMapToLong(LongStream::of)
        .sum();
  }

  @Override
  public Long part2() {
    return ranges.stream()
        .map(
            range ->
                range.getInvalidIds(
                    id -> {
                      int length = 1 + (int) Math.floor(Math.log10(id));
                      for (int chunkSize = 1; chunkSize <= length / 2; chunkSize++) {
                        int repeats = length / chunkSize;
                        if (chunkSize * repeats != length) {
                          continue;
                        }

                        long tail = id % (long) Math.pow(10, chunkSize);
                        if (tail == 0) {
                          continue;
                        }

                        long potential = 0;
                        for (int i = 0; i < repeats; i++) {
                          potential += tail * (long) Math.pow(10, chunkSize * i);
                        }
                        if (id == potential) {
                          return false;
                        }
                      }
                      return true;
                    }))
        .flatMapToLong(LongStream::of)
        .sum();
  }

  private record Range(long left, long right) {
    Range(String input) {
      String[] parts = input.split("-");
      this(Long.parseLong(parts[0]), Long.parseLong(parts[1]));
    }

    long[] getInvalidIds(LongPredicate valid) {
      return LongStream.iterate(left, id -> id <= right, id -> id + 1)
          .filter(valid.negate())
          .toArray();
    }
  }
}

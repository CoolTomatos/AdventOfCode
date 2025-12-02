package com.cooltomatos.aoc.y2025.d02;

import com.cooltomatos.aoc.AbstractDay;
import java.util.Arrays;
import java.util.OptionalInt;
import java.util.function.LongPredicate;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class Day extends AbstractDay {
  public Day(String dir, String file) {
    super(2025, 2, dir, file);
  }

  private static long solve(String line, OptionalInt maxRepeats) {
    return Arrays.stream(line.split(","))
        .map(range -> range.split("-"))
        .map(range -> new long[] {Long.parseLong(range[0]), Long.parseLong(range[1])})
        .flatMapToLong(range -> LongStream.rangeClosed(range[0], range[1]))
        .filter(invalid(maxRepeats))
        .sum();
  }

  private static LongPredicate invalid(OptionalInt maxRepeats) {
    return id -> {
      int length = 1 + (int) Math.floor(Math.log10(id));
      return IntStream.rangeClosed(2, maxRepeats.orElse(length))
          .filter(repeats -> length % repeats == 0)
          .anyMatch(
              repeats -> {
                int chunkSize = length / repeats;
                long power = (long) Math.pow(10, chunkSize);
                long tail = id % power;
                return LongStream.iterate(id, part -> part != 0, part -> part / power)
                    .map(part -> part % power)
                    .allMatch(part -> tail == part);
              });
    };
  }

  @Override
  public Long part1() {
    return solve(input.getFirst(), OptionalInt.of(2));
  }

  @Override
  public Long part2() {
    return solve(input.getFirst(), OptionalInt.empty());
  }
}

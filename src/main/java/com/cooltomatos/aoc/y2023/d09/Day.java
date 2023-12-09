package com.cooltomatos.aoc.y2023.d09;

import com.cooltomatos.aoc.AbstractDay;
import com.google.common.collect.Streams;
import java.util.Arrays;
import java.util.List;

public class Day extends AbstractDay {
  private final List<List<Integer>> histories;

  public Day(String dir, String file) {
    super(2023, 9, dir, file);
    histories =
        input.stream()
            .map(line -> Arrays.stream(line.split(" ")).map(Integer::parseInt).toList())
            .toList();
  }

  @Override
  public Integer part1() {
    return histories.stream().mapToInt(history -> extrapolate(history, true)).sum();
  }

  @Override
  public Integer part2() {
    return histories.stream().mapToInt(history -> extrapolate(history, false)).sum();
  }

  private static int extrapolate(List<Integer> history, boolean next) {
    var base = next ? history.getLast() : history.getFirst();
    if (history.stream().allMatch(base::equals)) {
      return base;
    }
    var underline =
        Streams.mapWithIndex(
                history.stream().skip(1), (value, index) -> value - history.get(((int) index)))
            .toList();
    var extrapolated = extrapolate(underline, next);
    return base + (next ? extrapolated : -extrapolated);
  }
}

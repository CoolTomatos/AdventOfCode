package com.cooltomatos.aoc.y2023.d09;

import com.cooltomatos.aoc.AbstractDay;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
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
    return histories.stream().mapToInt(Day::extrapolate).sum();
  }

  private static int extrapolate(List<Integer> history) {
    if (history.stream().allMatch(history.getFirst()::equals)) {
      return history.getFirst();
    } else {
      var underline = new ArrayList<Integer>();
      for (int i = 1; i < history.size(); i++) {
        underline.add(history.get(i) - history.get(i - 1));
      }
      return history.getLast() + extrapolate(ImmutableList.copyOf(underline));
    }
  }

  @Override
  public Integer part2() {
    return histories.stream().mapToInt(Day::previous).sum();
  }

  private static int previous(List<Integer> history) {
    if (history.stream().allMatch(history.getFirst()::equals)) {
      return history.getFirst();
    } else {
      var underline = new ArrayList<Integer>();
      for (int i = 1; i < history.size(); i++) {
        underline.add(history.get(i) - history.get(i - 1));
      }
      var previous = previous(ImmutableList.copyOf(underline));
      return history.getFirst() - previous;
    }
  }
}

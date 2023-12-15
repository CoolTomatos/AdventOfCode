package com.cooltomatos.aoc.y2023.d15;

import com.cooltomatos.aoc.AbstractDay;
import java.util.Arrays;
import java.util.List;

public class Day extends AbstractDay {
  private List<String> steps;

  public Day(String dir, String file) {
    super(2023, 15, dir, file);
    steps = Arrays.stream(input.getFirst().split(",")).toList();
  }

  @Override
  public Integer part1() {
    return steps.stream().mapToInt(Day::hash).sum();
  }

  @Override
  public Number part2() {
    return null;
  }

  private static int hash(String word) {
    return word.chars().reduce(0, (base, current) -> (base + current) * 17 % 256);
  }
}

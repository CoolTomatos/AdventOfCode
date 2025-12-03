package com.cooltomatos.aoc.y2025.d03;

import com.cooltomatos.aoc.AbstractDay;

public class Day extends AbstractDay {

  public Day(String dir, String file) {
    super(2025, 3, dir, file);
  }

  private static String solve(String battery, int size, int start, int end) {
    if (end - start == size) {
      return battery.substring(start, end);
    }
    for (char i = '9'; i > '0'; i--) {
      int index = battery.indexOf(i, start, end);
      if (index == -1) {
        continue;
      }
      if (size == 1) {
        return "" + i;
      }
      int rightSize = end - index - 1;
      if (rightSize >= size - 1) {
        return i + solve(battery, size - 1, index + 1, end);
      }
      int leftSize = size - 1 - rightSize;
      return solve(battery, leftSize, start, index) + i + solve(battery, rightSize, index + 1, end);
    }
    throw new IllegalStateException();
  }

  @Override
  public Long part1() {
    return input.stream()
        .map(batteries -> solve(batteries, 2, 0, batteries.length()))
        .mapToLong(Long::parseLong)
        .sum();
  }

  @Override
  public Long part2() {
    return input.stream()
        .map(batteries -> solve(batteries, 12, 0, batteries.length()))
        .mapToLong(Long::parseLong)
        .sum();
  }
}

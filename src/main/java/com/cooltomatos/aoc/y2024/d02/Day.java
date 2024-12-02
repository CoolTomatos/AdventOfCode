package com.cooltomatos.aoc.y2024.d02;

import com.cooltomatos.aoc.AbstractDay;
import java.util.Arrays;
import java.util.List;

public class Day extends AbstractDay {
  private final List<Report> reports;

  public Day(String dir, String file) {
    super(2024, 2, dir, file);
    reports =
        input.stream()
            .map(line -> Arrays.stream(line.split("\\s+")).mapToInt(Integer::parseInt).toArray())
            .map(Report::new)
            .toList();
  }

  @Override
  public Long part1() {
    return reports.stream().filter(Report::safe).count();
  }

  @Override
  public Long part2() {
    return reports.stream().filter(Report::lessSafe).count();
  }

  private record Report(int[] levels) {
    boolean safe() {
      boolean increase = levels[0] < levels[1];
      for (var i = 0; i < levels.length - 1; i++) {
        var gap = levels[i] - levels[i + 1];
        if (increase && (gap < -3 || gap > -1) || !increase && (gap < 1 || gap > 3)) {
          return false;
        }
      }
      return true;
    }

    boolean lessSafe() {
      for (var i = 0; i < levels.length; i++) {
        var newLevels = new int[levels.length - 1];
        System.arraycopy(levels, 0, newLevels, 0, i);
        System.arraycopy(levels, i + 1, newLevels, i, newLevels.length - i);
        if (new Report(newLevels).safe()) {
          return true;
        }
      }
      return false;
    }
  }
}

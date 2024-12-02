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
  public Number part2() {
    return null;
  }

  private record Report(int[] levels) {
    boolean safe() {
      boolean increase = levels[0] < levels[1];
      for (var i = 0; i < levels.length - 1; i++) {
        if (increase && levels[i] + 1 <= levels[i + 1] && levels[i] + 3 >= levels[i + 1]
            || !increase && levels[i] - 1 >= levels[i + 1] && levels[i] - 3 <= levels[i + 1]) {
          continue;
        }
        return false;
      }
      return true;
    }
  }
}

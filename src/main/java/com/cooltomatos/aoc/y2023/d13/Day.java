package com.cooltomatos.aoc.y2023.d13;

import com.cooltomatos.aoc.AbstractDay;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.List;

public class Day extends AbstractDay {
  private final List<char[][]> patterns;

  public Day(String dir, String file) {
    super(2023, 13, dir, file);
    var patterns = ImmutableList.<char[][]>builder();
    var pattern = new ArrayList<char[]>();
    for (String line : input) {
      if (line.isEmpty()) {
        patterns.add(pattern.toArray(new char[0][]));
        pattern.clear();
      } else {
        pattern.add(line.toCharArray());
      }
    }
    patterns.add(pattern.toArray(new char[0][]));
    this.patterns = patterns.build();
  }

  @Override
  public Number part1() {
    return patterns.stream().mapToInt(pattern -> solve(pattern, 0)).sum();
  }

  @Override
  public Number part2() {
    return patterns.stream().mapToInt(pattern -> solve(pattern, 1)).sum();
  }

  private static int solve(char[][] pattern, int allowedSmudges) {
    var height = pattern.length;
    var width = pattern[0].length;
    for (int col = 0; col < width - 1; col++) {
      int smudge = 0;
      for (int diff = 0; col - diff >= 0 && col + diff + 1 <= width - 1; diff++) {
        for (int row = 0; row < height; row++) {
          if (pattern[row][col - diff] != pattern[row][col + diff + 1]) {
            smudge++;
            if (smudge > allowedSmudges) {
              break;
            }
          }
        }
      }
      if (smudge == allowedSmudges) {
        return ++col;
      }
    }
    for (int row = 0; row < height - 1; row++) {
      int smudge = 0;
      for (int diff = 0; row - diff >= 0 && row + diff + 1 <= height - 1; diff++) {
        for (int col = 0; col < width; col++) {
          if (pattern[row - diff][col] != pattern[row + diff + 1][col]) {
            smudge++;
            if (smudge > allowedSmudges) {
              break;
            }
          }
        }
      }
      if (smudge == allowedSmudges) {
        return ++row * 100;
      }
    }
    return 0;
  }
}

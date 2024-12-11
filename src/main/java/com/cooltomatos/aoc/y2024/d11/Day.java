package com.cooltomatos.aoc.y2024.d11;

import com.cooltomatos.aoc.AbstractDay;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day extends AbstractDay {
  private final List<Long> stones;

  public Day(String dir, String file) {
    super(2024, 11, dir, file);
    stones = Arrays.stream(input.getFirst().split("\\s+")).map(Long::parseLong).toList();
  }

  @Override
  public Integer part1() {
    List<Long> stones = new ArrayList<>(this.stones);
    for (int i = 0; i < 25; i++) {
      blink(stones);
    }
    return stones.size();
  }

  @Override
  public Number part2() {
    return null;
  }

  private static void blink(List<Long> stones) {
    for (int i = stones.size() - 1; i >= 0; i--) {
      long stone = stones.get(i);
      if (stone == 0) {
        stones.set(i, 1L);
      } else {
        int digits = (int) (Math.floor(Math.log10(stone)) + 1);
        if (digits % 2 == 0) {
          int pow = (int) Math.pow(10, digits / 2);
          stones.set(i, stone % pow);
          stones.add(i, stone / pow);
        } else {
          stones.set(i, stone * 2024);
        }
      }
    }
  }
}

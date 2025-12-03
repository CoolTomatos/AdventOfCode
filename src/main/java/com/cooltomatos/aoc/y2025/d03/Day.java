package com.cooltomatos.aoc.y2025.d03;

import com.cooltomatos.aoc.AbstractDay;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day extends AbstractDay {
  private final List<List<Integer>> banks;

  public Day(String dir, String file) {
    super(2025, 3, dir, file);
    banks =
        input.stream()
            .map(String::chars)
            .map(bank -> bank.map(battery -> battery - '0'))
            .map(IntStream::boxed)
            .map(Stream::toList)
            .toList();
  }

  private static int output(List<Integer> bank) {
    int biggest = -1;
    int biggestIndex = -1;
    for (int i = 9; i > 0; i--) {
      if (bank.contains(i)) {
        biggest = i;
        biggestIndex = bank.indexOf(i);
        break;
      }
    }
    if (biggestIndex == bank.size() - 1) {
      List<Integer> subList = bank.subList(0, biggestIndex);
      for (int i = 9; i > 0; i--) {
        if (subList.contains(i)) {
          return i * 10 + biggest;
        }
      }
    } else {
      List<Integer> subList = bank.subList(biggestIndex + 1, bank.size());
      for (int i = 9; i > 0; i--) {
        if (subList.contains(i)) {
          return biggest * 10 + i;
        }
      }
    }
    return 0;
  }

  @Override
  public Number part2() {
    return null;
  }

  @Override
  public Integer part1() {
    return banks.stream().mapToInt(Day::output).sum();
  }
}

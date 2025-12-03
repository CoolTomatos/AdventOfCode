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
  public Integer part1() {
    return banks.stream().mapToInt(Day::output).sum();
  }

  private static String biggest(List<Integer> bank, int size) {
    if (size == 0) {
      return "";
    }
    String left = "";
    String mid = "";
    String right = "";
    for (int i = 9; i > 0; i--) {
      if (!bank.contains(i)) {
        continue;
      }
      mid = Integer.toString(i);
      var index = bank.indexOf(i);
      var leftSubList = bank.subList(0, index);
      var rightSubList = bank.subList(index + 1, bank.size());
      if (rightSubList.size() >= size - 1) {
        right = biggest(rightSubList, size - 1);
      } else {
        int leftSize = size - 1 - rightSubList.size();
        left = biggest(leftSubList, leftSize);
        right = biggest(rightSubList, rightSubList.size());
      }
      break;
    }

    return left + mid + right;
  }

  @Override
  public Long part2() {
    return banks.stream().map(bank -> biggest(bank, 12)).mapToLong(Long::parseLong).sum();
  }
}

package com.cooltomatos.aoc.y2024.d07;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.ImmutableSetMultimap.toImmutableSetMultimap;

import com.cooltomatos.aoc.AbstractDay;
import com.cooltomatos.aoc.function.EntryPredicate;
import com.google.common.collect.Multimap;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Pattern;

public class Day extends AbstractDay {
  private final Multimap<Long, int[]> equations;

  public Day(String dir, String file) {
    super(2024, 7, dir, file);
    equations =
        input.stream()
            .map(Pattern.compile("(\\d+): (.+)")::matcher)
            .peek(matcher -> checkState(matcher.matches()))
            .collect(
                toImmutableSetMultimap(
                    matcher -> Long.parseLong(matcher.group(1)),
                    matcher ->
                        Arrays.stream(matcher.group(2).split("\\s+"))
                            .mapToInt(Integer::parseInt)
                            .toArray()));
  }

  private static boolean test(long test, int[] numbers) {
    return test(test, numbers, numbers.length - 1);
  }

  private static boolean newTest(long test, int[] numbers) {
    if (numbers.length == 1) {
      return test == numbers[0];
    }
    int lastNumber = numbers[numbers.length - 1];
    int[] newNumbers = Arrays.copyOf(numbers, numbers.length - 1);
    if (test % lastNumber == 0 && newTest(test / lastNumber, newNumbers)) {
      return true;
    } else if (newTest(test - lastNumber, newNumbers)) {
      return true;
    }
    int pow = (int) Math.pow(10, Math.floor(Math.log10(lastNumber)) + 1);
    return test % pow == lastNumber && newTest(test / pow, newNumbers);
  }

  @Override
  public Long part2() {
    return equations.entries().stream()
        .filter((EntryPredicate<Long, int[]>) Day::newTest)
        .mapToLong(Map.Entry::getKey)
        .sum();
  }

  private static boolean test(long test, int[] numbers, int lastIndex) {
    if (lastIndex == 0) {
      return test == numbers[0];
    }
    int lastNumber = numbers[lastIndex--];
    return test % lastNumber == 0
        ? test(test / lastNumber, numbers, lastIndex) || test(test - lastNumber, numbers, lastIndex)
        : test(test - lastNumber, numbers, lastIndex);
  }

  @Override
  public Long part1() {
    return equations.entries().stream()
        .filter((EntryPredicate<Long, int[]>) Day::test)
        .mapToLong(Map.Entry::getKey)
        .sum();
  }
}

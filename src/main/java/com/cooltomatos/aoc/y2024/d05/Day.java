package com.cooltomatos.aoc.y2024.d05;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.ImmutableSetMultimap.toImmutableSetMultimap;

import com.cooltomatos.aoc.AbstractDay;
import com.google.common.collect.ImmutableSetMultimap;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;

public class Day extends AbstractDay {
  private final ImmutableSetMultimap<Integer, Integer> rules;
  private final List<List<Integer>> updates;

    public Day(String dir, String file) {
    super(2024, 5, dir, file);
    int seperator = input.indexOf("");
    rules =
        input.subList(0, seperator).stream()
            .map(Pattern.compile("(\\d+)\\|(\\d+)")::matcher)
            .peek(matcher -> checkState(matcher.matches()))
            .collect(
                toImmutableSetMultimap(
                    matcher -> Integer.parseInt(matcher.group(1)),
                    matcher -> Integer.parseInt(matcher.group(2))));
    updates =
        input.subList(seperator + 1, input.size()).stream()
            .map(line -> Arrays.stream(line.split(",")).map(Integer::parseInt).toList())
            .toList();
    }

    @Override
    public Integer part1() {
    int result = 0;
    for (List<Integer> update : updates) {
      HashSet<Integer> printed = new HashSet<>();
      for (Integer page : update) {
        if (printed.stream().anyMatch(printedPage -> rules.get(page).contains(printedPage))) {
          break;
        }
        printed.add(page);
      }
      if (update.size() == printed.size()) {
        int midPage = update.get((update.size() - 1) / 2);
        result += midPage;
      }
    }
    return result;
    }

    @Override
    public Number part2() {
        return null;
    }
}

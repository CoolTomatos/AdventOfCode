package com.cooltomatos.aoc.y2024.d05;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.ImmutableSetMultimap.toImmutableSetMultimap;
import static java.util.function.Predicate.not;

import com.cooltomatos.aoc.AbstractDay;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Streams;
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
    return updates.stream()
        .filter(this::inOrder)
        .mapToInt(update -> update.get((update.size() - 1) / 2))
        .sum();
  }

  @Override
  public Integer part2() {
    return updates.stream()
        .filter(not(this::inOrder))
        .mapToInt(
            update -> {
              HashSet<Integer> toPrint = new HashSet<>(update);
              while (true) {
                int next =
                    toPrint.stream()
                        .filter(
                            page -> toPrint.stream().noneMatch(p -> rules.get(p).contains(page)))
                        .findAny()
                        .orElseThrow();
                toPrint.remove(next);
                if (toPrint.size() == (update.size() - 1) / 2) {
                  return next;
                }
              }
            })
        .sum();
  }

  private boolean inOrder(List<Integer> update) {
    return Streams.mapWithIndex(
            update.stream(),
            (page, index) ->
                update.subList(0, ((int) index)).stream().noneMatch(rules.get(page)::contains))
        .allMatch(Boolean::booleanValue);
  }
}

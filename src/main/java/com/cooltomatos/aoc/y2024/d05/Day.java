package com.cooltomatos.aoc.y2024.d05;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.ImmutableSetMultimap.toImmutableSetMultimap;

import com.cooltomatos.aoc.AbstractDay;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableSetMultimap;
import java.util.ArrayList;
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
        .filter(
            update -> {
              HashSet<Integer> printed = new HashSet<>();
              for (Integer page : update) {
                if (printed.stream()
                    .anyMatch(printedPage -> rules.get(page).contains(printedPage))) {
                  return false;
                }
                printed.add(page);
              }
              return true;
            })
        .map(update -> update.get((update.size() - 1) / 2))
        .reduce(0, Integer::sum);
  }

  @Override
  public Integer part2() {
    return updates.stream()
        .filter(
            update -> {
              HashSet<Integer> printed = new HashSet<>();
              for (Integer page : update) {
                if (printed.stream()
                    .anyMatch(printedPage -> rules.get(page).contains(printedPage))) {
                  return true;
                }
                printed.add(page);
              }
              return false;
            })
        .map(
            update -> {
              HashMultimap<Integer, Integer> invertedRules = HashMultimap.create();
              HashSet<Integer> toPrint = new HashSet<>(update);
              rules.inverse().entries().stream()
                  .filter(
                      entry ->
                          toPrint.contains(entry.getKey()) && toPrint.contains(entry.getValue()))
                  .forEach(entry -> invertedRules.put(entry.getKey(), entry.getValue()));
              ArrayList<Integer> newUpdate = new ArrayList<>(update.size());
              while (!toPrint.isEmpty()) {
                int next =
                    toPrint.stream()
                        .filter(page -> invertedRules.get(page).isEmpty())
                        .findFirst()
                        .orElseThrow();
                toPrint.remove(next);
                newUpdate.add(next);
                invertedRules.removeAll(next);
                for (Integer key : new HashSet<>(invertedRules.keySet())) {
                  invertedRules.remove(key, next);
                }
              }
              return newUpdate;
            })
        .map(update -> update.get((update.size() - 1) / 2))
        .reduce(0, Integer::sum);
  }
}

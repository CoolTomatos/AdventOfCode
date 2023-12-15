package com.cooltomatos.aoc.y2023.d15;

import static com.google.common.base.Preconditions.checkState;

import com.cooltomatos.aoc.AbstractDay;
import com.cooltomatos.aoc.function.EntryFunction;
import com.google.common.collect.Streams;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day extends AbstractDay {
  private final List<String> steps;

  public Day(String dir, String file) {
    super(2023, 15, dir, file);
    steps = Arrays.stream(input.getFirst().split(",")).toList();
  }

  @Override
  public Integer part1() {
    return steps.stream().mapToInt(Day::hash).sum();
  }

  @Override
  public Integer part2() {
    Map<Integer, LinkedHashMap<String, Integer>> boxes = new HashMap<>();
    steps.stream()
        .map(Pattern.compile("(\\w+)([-=])([1-9]?)")::matcher)
        .peek(matcher -> checkState(matcher.matches()))
        .forEach(
            matcher -> {
              var label = matcher.group(1);
              var box = boxes.computeIfAbsent(hash(label) + 1, unused -> new LinkedHashMap<>());
              switch (matcher.group(2).charAt(0)) {
                case '-' -> box.remove(label);
                case '=' -> box.put(label, Integer.parseInt(matcher.group(3)));
              }
            });
    return boxes.entrySet().stream()
        .flatMap(
            (EntryFunction<Integer, LinkedHashMap<String, Integer>, Stream<Integer>>)
                (boxNumber, box) ->
                    Streams.mapWithIndex(
                        box.values().stream(),
                        (focus, index) -> boxNumber * ((int) index + 1) * focus))
        .reduce(0, Integer::sum);
  }

  private static int hash(String word) {
    return word.chars().reduce(0, (base, current) -> (base + current) * 17 % 256);
  }
}

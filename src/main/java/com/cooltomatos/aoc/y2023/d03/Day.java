package com.cooltomatos.aoc.y2023.d03;

import static com.google.common.collect.ImmutableSet.toImmutableSet;

import com.cooltomatos.aoc.AbstractDay;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.SetMultimap;
import java.util.Objects;

public class Day extends AbstractDay {
  private final SetMultimap<Number, Coordinate> numbers;
  private final SetMultimap<Character, Coordinate> symbols;

  public Day(String dir, String file) {
    super(2023, 3, dir, file);
    int numberCount = 0;
    var numbers = ImmutableSetMultimap.<Number, Coordinate>builder();
    var symbols = ImmutableSetMultimap.<Character, Coordinate>builder();
    for (int i = 0; i < input.size(); i++) {
      var line = input.get(i);
      for (int j = 0; j < line.length(); j++) {
        var c = line.charAt(j);
        if (Character.isDigit(c)) {
          int k = j + 1;
          while (k <= line.length() - 1 && Character.isDigit(line.charAt(k))) {
            k++;
          }
          var number = new Number(numberCount++, Integer.parseInt(line.substring(j, k)));
          for (int l = j; l < k; l++) {
            numbers.put(number, new Coordinate(i, l));
          }
          j = k - 1;
        } else if (c != '.') {
          symbols.put(c, new Coordinate(i, j));
        }
      }
    }
    this.numbers = numbers.build();
    this.symbols = symbols.build();
  }

  @Override
  public Integer part1() {
    return Multimaps.filterValues(
            numbers, coordinate -> symbols.values().stream().anyMatch(coordinate::adjacent))
        .keySet()
        .stream()
        .mapToInt(Number::value)
        .sum();
  }

  @Override
  public Integer part2() {
    return symbols.get('*').stream()
        .map(
            coordinate ->
                Multimaps.filterValues(numbers, coordinate::adjacent).keySet().stream()
                    .map(Number::value)
                    .collect(toImmutableSet()))
        .filter(set -> set.size() == 2)
        .mapToInt(set -> set.stream().reduce(1, (l, r) -> l * r))
        .sum();
  }

  record Number(int id, int value) {
    @Override
    public boolean equals(Object o) {
      return this == o || o instanceof Number other && id == other.id;
    }

    @Override
    public int hashCode() {
      return Objects.hash(id);
    }
  }

  record Coordinate(int x, int y) {
    boolean adjacent(Coordinate other) {
      return Math.abs(x - other.x) <= 1 && Math.abs(y - other.y) <= 1;
    }
  }
}

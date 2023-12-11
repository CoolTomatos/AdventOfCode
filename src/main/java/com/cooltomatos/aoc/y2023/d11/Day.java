package com.cooltomatos.aoc.y2023.d11;

import static com.cooltomatos.aoc.common.MathFunctions.manhattanDistance;
import static com.google.common.collect.ImmutableSet.toImmutableSet;
import static com.google.common.collect.ImmutableTable.toImmutableTable;
import static java.util.function.Predicate.not;

import com.cooltomatos.aoc.AbstractDay;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import com.google.common.collect.Table.Cell;
import java.util.Set;
import java.util.stream.Stream;

public class Day extends AbstractDay {
  private final Table<Integer, Integer, Integer> galaxies;
  private final Set<Integer> emptyRows;
  private final Set<Integer> emptyColumns;

  public Day(String dir, String file) {
    super(2023, 11, dir, file);
    int count = 1;
    var galaxies = ImmutableTable.<Integer, Integer, Integer>builder();
    for (int rowKey = 0; rowKey < input.size(); rowKey++) {
      for (int columnKey = 0; columnKey < input.get(rowKey).length(); columnKey++) {
        if (input.get(rowKey).charAt(columnKey) == '#') {
          galaxies.put(rowKey, columnKey, count++);
        }
      }
    }
    this.galaxies = galaxies.build();
    emptyRows =
        Stream.iterate(0, row -> row + 1)
            .limit(input.size())
            .filter(not(this.galaxies.rowKeySet()::contains))
            .collect(toImmutableSet());
    emptyColumns =
        Stream.iterate(0, column -> column + 1)
            .limit(input.getFirst().length())
            .filter(not(this.galaxies.columnKeySet()::contains))
            .collect(toImmutableSet());
  }

  @Override
  public Long part1() {
    return solve(2);
  }

  @Override
  public Long part2() {
    return solve(1000000);
  }

  @VisibleForTesting
  long solve(long times) {
    var expanded =
        galaxies.cellSet().stream()
            .collect(
                toImmutableTable(
                    cell ->
                        cell.getRowKey()
                            + (times - 1)
                                * emptyRows.stream().filter(row -> row < cell.getRowKey()).count(),
                    cell ->
                        cell.getColumnKey()
                            + (times - 1)
                                * emptyColumns.stream()
                                    .filter(column -> column < cell.getColumnKey())
                                    .count(),
                    Cell::getValue));

    long result = 0;
    var cells = ImmutableList.copyOf(expanded.cellSet());
    for (int i = 0; i < cells.size(); i++) {
      for (int j = i + 1; j < cells.size(); j++) {
        var left = cells.get(i);
        var right = cells.get(j);
        result +=
            manhattanDistance(
                left.getRowKey(), left.getColumnKey(),
                right.getRowKey(), right.getColumnKey());
      }
    }
    return result;
  }
}

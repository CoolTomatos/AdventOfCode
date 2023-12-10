package com.cooltomatos.aoc.y2023.d10;

import static com.google.common.collect.ImmutableSet.toImmutableSet;

import com.cooltomatos.aoc.AbstractDay;
import com.cooltomatos.aoc.common.Coordinate;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Set;

public class Day extends AbstractDay {
  private final Table<Integer, Integer, Character> grid = HashBasedTable.create();
  private final Coordinate start;
  private final Table<Integer, Integer, Integer> mainLoop = HashBasedTable.create();

  public Day(String dir, String file) {
    super(2023, 10, dir, file);
    Coordinate start = new Coordinate(-1, -1);
    for (int i = 0; i < input.size(); i++) {
      for (int j = 0; j < input.get(i).length(); j++) {
        grid.put(
            i,
            j,
            switch (input.get(i).charAt(j)) {
              case '|' -> '│';
              case '-' -> '─';
              case 'L' -> '└';
              case 'J' -> '┘';
              case '7' -> '┐';
              case 'F' -> '┌';
              case 'S' -> {
                start = new Coordinate(i, j);
                yield '┼';
              }
              default -> ' ';
            });
      }
    }
    this.start = start;
    mainLoop.put(start.x(), start.y(), 0);
  }

  @Override
  public Integer part1() {
    while (true) {
      var looseEnds =
          grid.cellSet().stream()
              .filter(cell -> !connected(cell.getRowKey(), cell.getColumnKey()))
              .collect(toImmutableSet());
      if (looseEnds.isEmpty()) {
        break;
      }
      looseEnds.forEach(cell -> grid.remove(cell.getRowKey(), cell.getColumnKey()));
    }
    printTable(grid);
    var toVisit = new LinkedList<Coordinate>();
    toVisit.add(start);
    while (!toVisit.isEmpty()) {
      var current = toVisit.removeFirst();
      if (connectedTop(current.x(), current.y())) {
        if (!mainLoop.contains(current.x() - 1, current.y())) {
          toVisit.add(new Coordinate(current.x() - 1, current.y()));
          mainLoop.put(current.x() - 1, current.y(), 1 + mainLoop.get(current.x(), current.y()));
        }
      }
      if (connectedBottom(current.x(), current.y())) {
        if (!mainLoop.contains(current.x() + 1, current.y())) {
          toVisit.add(new Coordinate(current.x() + 1, current.y()));
          mainLoop.put(current.x() + 1, current.y(), 1 + mainLoop.get(current.x(), current.y()));
        }
      }
      if (connectedLeft(current.x(), current.y())) {
        if (!mainLoop.contains(current.x(), current.y() - 1)) {
          toVisit.add(new Coordinate(current.x(), current.y() - 1));
          mainLoop.put(current.x(), current.y() - 1, 1 + mainLoop.get(current.x(), current.y()));
        }
      }
      if (connectedRight(current.x(), current.y())) {
        if (!mainLoop.contains(current.x(), current.y() + 1)) {
          toVisit.add(new Coordinate(current.x(), current.y() + 1));
          mainLoop.put(current.x(), current.y() + 1, 1 + mainLoop.get(current.x(), current.y()));
        }
      }
    }
    var redundant =
        grid.cellSet().stream()
            .filter(cell -> !mainLoop.contains(cell.getRowKey(), cell.getColumnKey()))
            .collect(toImmutableSet());
    redundant.forEach(cell -> grid.remove(cell.getRowKey(), cell.getColumnKey()));
    printTable(grid);
    return mainLoop.values().stream().max(Integer::compareTo).orElseThrow();
  }

  @Override
  public Number part2() {
    return null;
  }

  private boolean connected(int row, int column) {
    return switch (grid.get(row, column)) {
      case '┼' -> true;
      case '│' -> connectedTop(row, column) && connectedBottom(row, column);
      case '┘' -> connectedTop(row, column) && connectedLeft(row, column);
      case '└' -> connectedTop(row, column) && connectedRight(row, column);
      case '┐' -> connectedBottom(row, column) && connectedLeft(row, column);
      case '┌' -> connectedBottom(row, column) && connectedRight(row, column);
      case '─' -> connectedLeft(row, column) && connectedRight(row, column);
      case null -> false;
      default -> false;
    };
  }

  private boolean connectedTop(int row, int column) {
    var top = Objects.requireNonNullElse(grid.get(row - 1, column), ' ');
    if (Set.of('┼', '┐', '│', '┌').contains(top)) {
      return true;
    }
    return false;
  }

  private boolean connectedBottom(int row, int column) {
    var bottom = Objects.requireNonNullElse(grid.get(row + 1, column), ' ');
    if (Set.of('┼', '┘', '│', '└').contains(bottom)) {
      return true;
    }
    return false;
  }

  private boolean connectedLeft(int row, int column) {
    var left = Objects.requireNonNullElse(grid.get(row, column - 1), ' ');
    if (Set.of('┼', '└', '─', '┌').contains(left)) {
      return true;
    }
    return false;
  }

  private boolean connectedRight(int row, int column) {
    var right = Objects.requireNonNullElse(grid.get(row, column + 1), ' ');
    if (Set.of('┼', '┘', '─', '┐').contains(right)) {
      return true;
    }
    return false;
  }

  private void printTable(Table<Integer, Integer, ?> table) {
    int minRow = table.rowKeySet().stream().min(Integer::compare).orElseThrow();
    int maxRow = table.rowKeySet().stream().max(Integer::compare).orElseThrow();
    int minColumn = table.columnKeySet().stream().min(Integer::compare).orElseThrow();
    int maxColumn = table.columnKeySet().stream().max(Integer::compare).orElseThrow();
    for (int c = minColumn; c <= maxColumn; c++) {
      System.out.print("*");
    }
    System.out.println();
    for (int r = minRow; r <= maxRow; r++) {
      for (int c = minColumn; c <= maxColumn; c++) {
        System.out.print(Objects.requireNonNullElse(table.get(r, c), ' '));
      }
      System.out.println();
    }
  }
}

package com.cooltomatos.aoc.y2023.d10;

import static com.google.common.base.Predicates.not;
import static com.google.common.collect.ImmutableSet.toImmutableSet;

import com.cooltomatos.aoc.AbstractDay;
import com.cooltomatos.aoc.common.Coordinate;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Day extends AbstractDay {
  private final Table<Integer, Integer, Character> grid = HashBasedTable.create();
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
    System.out.println("Finished importing the grid:");
    printTable(grid);
    mainLoop.put(start.x(), start.y(), 0);

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
    System.out.println("Finished removing loose ends:");
    printTable(grid);

    char startTile;
    if (connectedTop(start.x(), start.y()) && connectedBottom(start.x(), start.y())) {
      startTile = '│';
    } else if (connectedTop(start.x(), start.y()) && connectedLeft(start.x(), start.y())) {
      startTile = '┘';
    } else if (connectedTop(start.x(), start.y())) {
      startTile = '└';
    } else if (connectedBottom(start.x(), start.y()) && connectedLeft(start.x(), start.y())) {
      startTile = '┐';
    } else if (connectedBottom(start.x(), start.y())) {
      startTile = '┌';
    } else {
      startTile = '─';
    }
    grid.put(start.x(), start.y(), startTile);

    var toVisit = new LinkedList<Coordinate>();
    toVisit.add(start);
    while (!toVisit.isEmpty()) {
      var current = toVisit.removeFirst();
      var nextDistance = 1 + Objects.requireNonNull(mainLoop.get(current.x(), current.y()));
      if (connectedTop(current.x(), current.y())
          && !mainLoop.contains(current.x() - 1, current.y())) {
        toVisit.add(new Coordinate(current.x() - 1, current.y()));
        mainLoop.put(current.x() - 1, current.y(), nextDistance);
      }
      if (connectedBottom(current.x(), current.y())
          && !mainLoop.contains(current.x() + 1, current.y())) {
        toVisit.add(new Coordinate(current.x() + 1, current.y()));
        mainLoop.put(current.x() + 1, current.y(), nextDistance);
      }
      if (connectedLeft(current.x(), current.y())
          && !mainLoop.contains(current.x(), current.y() - 1)) {
        toVisit.add(new Coordinate(current.x(), current.y() - 1));
        mainLoop.put(current.x(), current.y() - 1, nextDistance);
      }
      if (connectedRight(current.x(), current.y())
          && !mainLoop.contains(current.x(), current.y() + 1)) {
        toVisit.add(new Coordinate(current.x(), current.y() + 1));
        mainLoop.put(current.x(), current.y() + 1, nextDistance);
      }
    }
    var redundant =
        grid.cellSet().stream()
            .filter(cell -> !mainLoop.contains(cell.getRowKey(), cell.getColumnKey()))
            .collect(toImmutableSet());
    redundant.forEach(cell -> grid.remove(cell.getRowKey(), cell.getColumnKey()));
    System.out.println("Finished removing irrelevant circles");
    printTable(grid);
  }

  @Override
  public Integer part1() {
    return mainLoop.values().stream().max(Integer::compareTo).orElseThrow();
  }

  @Override
  public Long part2() {
    for (int rowKey : grid.rowKeySet()) {
      var row =
          Maps.filterValues(grid.row(rowKey), not(Character.valueOf('─')::equals))
              .entrySet()
              .stream()
              .sorted(Entry.comparingByKey())
              .collect(Collectors.toList());
      boolean inside = false;
      int previousFlip = -1;
      while (!row.isEmpty()) {
        var first = row.removeFirst();
        if (first.getValue().equals('│')) {
          for (int columnKey = previousFlip + 1; columnKey < first.getKey(); columnKey++) {
            if (!grid.contains(rowKey, columnKey)) {
              grid.put(rowKey, columnKey, inside ? 'I' : 'O');
            }
          }
          inside = !inside;
          previousFlip = first.getKey();
        } else {
          var second = row.removeFirst();
          if (first.getValue().equals('└') && second.getValue().equals('┐')
              || first.getValue().equals('┌') && second.getValue().equals('┘')) {
            for (int columnKey = previousFlip + 1; columnKey < first.getKey(); columnKey++) {
              if (!grid.contains(rowKey, columnKey)) {
                grid.put(rowKey, columnKey, inside ? 'I' : 'O');
              }
            }
            inside = !inside;
            previousFlip = second.getKey();
          }
        }
      }
    }
    printTable(grid);
    return grid.values().stream().filter(Character.valueOf('I')::equals).count();
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
    return Set.of('┼', '┐', '│', '┌').contains(top);
  }

  private boolean connectedBottom(int row, int column) {
    var bottom = Objects.requireNonNullElse(grid.get(row + 1, column), ' ');
    return Set.of('┼', '┘', '│', '└').contains(bottom);
  }

  private boolean connectedLeft(int row, int column) {
    var left = Objects.requireNonNullElse(grid.get(row, column - 1), ' ');
    return Set.of('┼', '└', '─', '┌').contains(left);
  }

  private boolean connectedRight(int row, int column) {
    var right = Objects.requireNonNullElse(grid.get(row, column + 1), ' ');
    return Set.of('┼', '┘', '─', '┐').contains(right);
  }

  private void printTable(Table<Integer, Integer, Character> table) {
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
        char tile = Objects.requireNonNullElse(table.get(r, c), ' ');
        if (tile == 'O') {
          tile = ' ';
        } else if (tile == 'I') {
          tile = '█';
        }
        System.out.print(tile);
      }
      System.out.println();
    }
    for (int c = minColumn; c <= maxColumn; c++) {
      System.out.print("*");
    }
    System.out.println();
  }
}

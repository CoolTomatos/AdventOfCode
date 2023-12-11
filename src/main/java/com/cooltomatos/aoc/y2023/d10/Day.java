package com.cooltomatos.aoc.y2023.d10;

import static com.google.common.collect.MoreCollectors.onlyElement;

import com.cooltomatos.aoc.AbstractDay;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import java.util.Arrays;
import java.util.LinkedList;

public class Day extends AbstractDay {
  private final char[][] grid;
  private final Table<Integer, Integer, Integer> mainLoop = HashBasedTable.create();

  public Day(String dir, String file) {
    super(2023, 10, dir, file);
    grid = new char[input.size()][input.getFirst().length()];
    importTheGrid();
    cleanupTheGrid();
    buildMainLoop();
    markInnerTiles();
  }

  @Override
  public Integer part1() {
    return mainLoop.values().stream().reduce(0, Integer::max);
  }

  @Override
  public Integer part2() {
    int count = 0;
    for (char[] row : grid) {
      for (char tile : row) {
        if (tile == 'I') {
          count++;
        }
      }
    }
    return count;
  }

  private void importTheGrid() {
    for (int row = 0; row < grid.length; row++) {
      for (int col = 0; col < grid[row].length; col++) {
        grid[row][col] = input.get(row).charAt(col);
        if (grid[row][col] == 'S') {
          mainLoop.put(row, col, 0);
        }
      }
    }
    System.out.println("Finished importing the grid");
    printGrid();
  }

  private void cleanupTheGrid() {
    boolean cleanedUp;
    do {
      cleanedUp = true;
      for (int row = 0; row < grid.length; row++) {
        for (int col = 0; col < grid[row].length; col++) {
          if (grid[row][col] != '.' && !connected(row, col)) {
            grid[row][col] = '.';
            cleanedUp = false;
          }
        }
      }
    } while (!cleanedUp);
    System.out.println("Finished removing loose ends");
    printGrid();
  }

  private void buildMainLoop() {
    var start = mainLoop.cellSet().stream().collect(onlyElement());
    grid[start.getRowKey()][start.getColumnKey()] =
        resolveStart(start.getRowKey(), start.getColumnKey());

    var toVisit = new LinkedList<>(mainLoop.cellSet());
    while (!toVisit.isEmpty()) {
      var current = toVisit.removeFirst();
      var nextDistance = 1 + current.getValue();
      var tile = grid[current.getRowKey()][current.getColumnKey()];
      if (ImmutableSet.of('|', 'J', 'L').contains(tile)) {
        var top =
            Tables.immutableCell(current.getRowKey() - 1, current.getColumnKey(), nextDistance);
        if (!mainLoop.contains(top.getRowKey(), top.getColumnKey())) {
          toVisit.add(top);
          mainLoop.put(top.getRowKey(), top.getColumnKey(), top.getValue());
        }
      }
      if (ImmutableSet.of('|', '7', 'F').contains(tile)) {
        var bottom =
            Tables.immutableCell(current.getRowKey() + 1, current.getColumnKey(), nextDistance);
        if (!mainLoop.contains(bottom.getRowKey(), bottom.getColumnKey())) {
          toVisit.add(bottom);
          mainLoop.put(bottom.getRowKey(), bottom.getColumnKey(), bottom.getValue());
        }
      }
      if (ImmutableSet.of('J', '7', '-').contains(tile)) {
        var left =
            Tables.immutableCell(current.getRowKey(), current.getColumnKey() - 1, nextDistance);
        if (!mainLoop.contains(left.getRowKey(), left.getColumnKey())) {
          toVisit.add(left);
          mainLoop.put(left.getRowKey(), left.getColumnKey(), left.getValue());
        }
      }
      if (ImmutableSet.of('L', 'F', '-').contains(tile)) {
        var right =
            Tables.immutableCell(current.getRowKey(), current.getColumnKey() + 1, nextDistance);
        if (!mainLoop.contains(right.getRowKey(), right.getColumnKey())) {
          toVisit.add(right);
          mainLoop.put(right.getRowKey(), right.getColumnKey(), right.getValue());
        }
      }
    }
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        if (!mainLoop.contains(i, j)) {
          grid[i][j] = '.';
        }
      }
    }
    System.out.println("Finished building main loop");
    printGrid();
  }

  private char resolveStart(int row, int col) {
    var top = connectedTop(row, col);
    var bottom = connectedBottom(row, col);
    var right = connectedLeft(row, col);
    if (top && bottom) {
      return '|';
    } else if (top && right) {
      return 'J';
    } else if (top) {
      return 'L';
    } else if (bottom && right) {
      return '7';
    } else if (bottom) {
      return 'F';
    } else {
      return '-';
    }
  }

  private void markInnerTiles() {
    for (int row = 0; row < grid.length; row++) {
      int previousFlip = -1;
      int previousHalf = -1;
      boolean upperHalf = true;
      boolean inside = true;
      for (int col = 0; col < grid[row].length; col++) {
        var tile = grid[row][col];
        if (tile == '-' || tile == '.' || upperHalf && tile == 'J' || !upperHalf && tile == '7') {
          continue;
        }
        if (tile == 'L' || tile == 'F') {
          previousHalf = col;
          upperHalf = tile == 'L';
          continue;
        }
        inside = !inside;
        for (int i = previousFlip + 1; inside && i < (tile == '|' ? col : previousHalf); i++) {
          if (grid[row][i] == '.') {
            grid[row][i] = 'I';
          }
        }
        previousFlip = col;
      }
    }
    System.out.println("Finished marking inside tiles");
    printGrid();
  }

  private boolean connected(int row, int col) {
    return switch (grid[row][col]) {
      case '|' -> connectedTop(row, col) && connectedBottom(row, col);
      case 'J' -> connectedTop(row, col) && connectedLeft(row, col);
      case 'L' -> connectedTop(row, col) && connectedRight(row, col);
      case '7' -> connectedBottom(row, col) && connectedLeft(row, col);
      case 'F' -> connectedBottom(row, col) && connectedRight(row, col);
      case '-' -> connectedLeft(row, col) && connectedRight(row, col);
      case 'S' -> true;
      default -> false;
    };
  }

  private boolean connectedTop(int row, int col) {
    return row > 0 && ImmutableSet.of('|', '7', 'F', 'S').contains(grid[row - 1][col]);
  }

  private boolean connectedBottom(int row, int col) {
    return row < grid.length - 1
        && ImmutableSet.of('|', 'J', 'L', 'S').contains(grid[row + 1][col]);
  }

  private boolean connectedLeft(int row, int col) {
    return col > 0 && ImmutableSet.of('-', 'F', 'L', 'S').contains(grid[row][col - 1]);
  }

  private boolean connectedRight(int row, int col) {
    return col < grid[0].length - 1
        && ImmutableSet.of('-', '7', 'J', 'S').contains(grid[row][col + 1]);
  }

  private void printGrid() {
    char[] gap = new char[grid[0].length];
    Arrays.fill(gap, '*');
    var stars = String.valueOf(gap);
    System.out.println(stars);
    for (char[] chars : grid) {
      System.out.println(
          String.valueOf(chars)
              .replace('|', '┃')
              .replace('-', '━')
              .replace('L', '┗')
              .replace('J', '┛')
              .replace('7', '┓')
              .replace('F', '┏')
              .replace('.', ' ')
              .replace('S', '╋')
              .replace('I', '▓'));
    }
    System.out.println(stars);
  }
}

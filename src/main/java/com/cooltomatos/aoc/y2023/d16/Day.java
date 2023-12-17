package com.cooltomatos.aoc.y2023.d16;

import static com.google.common.base.Preconditions.checkState;

import com.cooltomatos.aoc.AbstractDay;
import com.cooltomatos.aoc.common.Coordinate;
import com.google.common.collect.HashMultimap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class Day extends AbstractDay {
  private final char[][] grid;

  public Day(String dir, String file) {
    super(2023, 16, dir, file);
    grid = new char[input.size()][input.getFirst().length()];
    for (int row = 0; row < grid.length; row++) {
      for (int col = 0; col < grid[row].length; col++) {
        grid[row][col] = input.get(row).charAt(col);
      }
    }
  }

  @Override
  public Integer part1() {
    var visited = new HashSet<Coordinate>();
    var visitedTurns = HashMultimap.<Coordinate, Direction>create();
    var toVisit = new LinkedHashMap<Coordinate, Direction>();

    reachRight(new Coordinate(0, -1), visited, toVisit);

    while (!toVisit.isEmpty()) {
      var firstEntry = toVisit.pollFirstEntry();
      var current = firstEntry.getKey();
      var direction = firstEntry.getValue();

      if (visitedTurns.get(current).contains(direction)) {
        continue;
      }
      visitedTurns.put(current, direction);

      switch (grid[current.x()][current.y()]) {
        case '/' -> {
          switch (direction) {
            case UP -> reachRight(current, visited, toVisit);
            case DOWN -> reachLeft(current, visited, toVisit);
            case LEFT -> reachDown(current, visited, toVisit);
            case RIGHT -> reachUp(current, visited, toVisit);
          }
        }
        case '\\' -> {
          switch (direction) {
            case UP -> reachLeft(current, visited, toVisit);
            case DOWN -> reachRight(current, visited, toVisit);
            case LEFT -> reachUp(current, visited, toVisit);
            case RIGHT -> reachDown(current, visited, toVisit);
          }
        }
        case '|' -> {
          checkState(Set.of(Direction.LEFT, Direction.RIGHT).contains(direction));
          reachUp(current, visited, toVisit);
          reachDown(current, visited, toVisit);
        }
        case '-' -> {
          checkState(Set.of(Direction.UP, Direction.DOWN).contains(direction));
          reachLeft(current, visited, toVisit);
          reachRight(current, visited, toVisit);
        }
      }
    }
    return visited.size();
  }

  private void reachRight(
      Coordinate current, Set<Coordinate> visited, Map<Coordinate, Direction> toVisit) {
    for (int col = current.y() + 1; col < grid[0].length; col++) {
      var right = new Coordinate(current.x(), col);
      visited.add(right);
      if (!Set.of('.', '-').contains(grid[current.x()][col])) {
        toVisit.put(right, Direction.RIGHT);
        return;
      }
    }
  }

  private void reachLeft(
      Coordinate current, Set<Coordinate> visited, Map<Coordinate, Direction> toVisit) {
    for (int col = current.y() - 1; col >= 0; col--) {
      var left = new Coordinate(current.x(), col);
      visited.add(left);
      if (!Set.of('.', '-').contains(grid[current.x()][col])) {
        toVisit.put(left, Direction.LEFT);
        return;
      }
    }
  }

  private void reachUp(
      Coordinate current, Set<Coordinate> visited, Map<Coordinate, Direction> toVisit) {
    for (int row = current.x() - 1; row >= 0; row--) {
      var above = new Coordinate(row, current.y());
      visited.add(above);
      if (!Set.of('.', '|').contains(grid[row][current.y()])) {
        toVisit.put(above, Direction.UP);
        return;
      }
    }
  }

  private void reachDown(
      Coordinate current, Set<Coordinate> visited, Map<Coordinate, Direction> toVisit) {
    for (int row = current.x() + 1; row < grid.length; row++) {
      var below = new Coordinate(row, current.y());
      visited.add(below);
      if (!Set.of('.', '|').contains(grid[row][current.y()])) {
        toVisit.put(below, Direction.DOWN);
        return;
      }
    }
  }

  @Override
  public Integer part2() {
    return null;
  }

  enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT
  }
}

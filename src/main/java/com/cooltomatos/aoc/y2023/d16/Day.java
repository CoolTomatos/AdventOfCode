package com.cooltomatos.aoc.y2023.d16;

import com.cooltomatos.aoc.AbstractDay;
import com.cooltomatos.aoc.common.Coordinate;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.SetMultimap;
import java.util.ArrayList;
import java.util.List;

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
    return solve(new Beam(new Coordinate(0, 0), Direction.RIGHT));
  }

  private int solve(Beam start) {
    SetMultimap<Coordinate, Direction> visited = HashMultimap.create();
    var toVisit = Lists.newArrayList(start);
    while (!toVisit.isEmpty()) {
      var first = toVisit.removeFirst();
      var current = first.coordinate;
      var sign = grid[current.x()][current.y()];
      var direction = first.direction;
      if (!visited.put(current, direction)) {
        continue;
      }
      switch (sign) {
        case '|' -> {
          if (direction == Direction.LEFT || direction == Direction.RIGHT) {
            visited.put(current, Direction.LEFT);
            visited.put(current, Direction.RIGHT);
            if (current.x() > 0) {
              toVisit.add(new Beam(new Coordinate(current.x() - 1, current.y()), Direction.UP));
            }
            if (current.x() < grid.length - 1) {
              toVisit.add(new Beam(new Coordinate(current.x() + 1, current.y()), Direction.DOWN));
            }
            continue;
          }
        }
        case '-' -> {
          if (direction == Direction.UP || direction == Direction.DOWN) {
            visited.put(current, Direction.UP);
            visited.put(current, Direction.DOWN);
            if (current.y() > 0) {
              toVisit.add(new Beam(new Coordinate(current.x(), current.y() - 1), Direction.LEFT));
            }
            if (current.y() < grid[0].length - 1) {
              toVisit.add(new Beam(new Coordinate(current.x(), current.y() + 1), Direction.RIGHT));
            }
            continue;
          }
        }
        case '\\' -> {
          visited.put(current, direction);
          if (direction == Direction.UP && current.y() > 0) {
            toVisit.add(new Beam(new Coordinate(current.x(), current.y() - 1), Direction.LEFT));
          } else if (direction == Direction.DOWN && current.y() < grid[0].length - 1) {
            toVisit.add(new Beam(new Coordinate(current.x(), current.y() + 1), Direction.RIGHT));
          } else if (direction == Direction.LEFT && current.x() > 0) {
            toVisit.add(new Beam(new Coordinate(current.x() - 1, current.y()), Direction.UP));
          } else if (direction == Direction.RIGHT && current.x() < grid.length - 1) {
            toVisit.add(new Beam(new Coordinate(current.x() + 1, current.y()), Direction.DOWN));
          }
          continue;
        }
        case '/' -> {
          visited.put(current, direction);
          if (direction == Direction.UP && current.y() < grid[0].length - 1) {
            toVisit.add(new Beam(new Coordinate(current.x(), current.y() + 1), Direction.RIGHT));
          } else if (direction == Direction.DOWN && current.y() > 0) {
            toVisit.add(new Beam(new Coordinate(current.x(), current.y() - 1), Direction.LEFT));
          } else if (direction == Direction.LEFT && current.x() < grid.length - 1) {
            toVisit.add(new Beam(new Coordinate(current.x() + 1, current.y()), Direction.DOWN));
          } else if (direction == Direction.RIGHT && current.x() > 0) {
            toVisit.add(new Beam(new Coordinate(current.x() - 1, current.y()), Direction.UP));
          }
          continue;
        }
      }
      if (direction == Direction.UP && current.x() > 0) {
        toVisit.add(new Beam(new Coordinate(current.x() - 1, current.y()), direction));
      } else if (direction == Direction.DOWN && current.x() < grid.length - 1) {
        toVisit.add(new Beam(new Coordinate(current.x() + 1, current.y()), direction));
      } else if (direction == Direction.LEFT && current.y() > 0) {
        toVisit.add(new Beam(new Coordinate(current.x(), current.y() - 1), direction));
      } else if (direction == Direction.RIGHT && current.y() < grid[0].length - 1) {
        toVisit.add(new Beam(new Coordinate(current.x(), current.y() + 1), direction));
      }
    }
    return visited.keySet().size();
  }

  @Override
  public Integer part2() {
    List<Beam> starts = new ArrayList<>();
    for (int i = 0; i < grid.length; i++) {
      starts.add(new Beam(new Coordinate(i, 0), Direction.RIGHT));
      starts.add(new Beam(new Coordinate(i, grid[0].length - 1), Direction.LEFT));
    }
    for (int i = 0; i < grid[0].length; i++) {
      starts.add(new Beam(new Coordinate(0, i), Direction.DOWN));
      starts.add(new Beam(new Coordinate(grid.length - 1, i), Direction.UP));
    }
    return starts.stream().map(this::solve).reduce(Integer::max).orElse(0);
  }

  private record Beam(Coordinate coordinate, Direction direction) {}

  private enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT;
  }
}

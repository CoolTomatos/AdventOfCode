package com.cooltomatos.aoc.y2023.d16;

import com.cooltomatos.aoc.AbstractDay;
import com.cooltomatos.aoc.common.Coordinate;
import java.util.ArrayList;
import java.util.HashSet;
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
  public Long part1() {
    return solve(new Beam(new Coordinate(0, 0), Direction.RIGHT));
  }

  private long solve(Beam start) {
    var toVisit = new ArrayList<Beam>();
    toVisit.add(start);
    var visited = new HashSet<Beam>();
    while (!toVisit.isEmpty()) {
      var current = toVisit.removeFirst();
      var coordinate = current.coordinate;
      var direction = current.direction;

      if (!visited.add(current)) {
        continue;
      }

      switch (grid[coordinate.x()][coordinate.y()]) {
        case '|' -> {
          if (direction == Direction.LEFT || direction == Direction.RIGHT) {
            visited.add(new Beam(coordinate, Direction.LEFT));
            visited.add(new Beam(coordinate, Direction.RIGHT));
            if (coordinate.x() > 0) {
              toVisit.add(
                  new Beam(new Coordinate(coordinate.x() - 1, coordinate.y()), Direction.UP));
            }
            if (coordinate.x() < grid.length - 1) {
              toVisit.add(
                  new Beam(new Coordinate(coordinate.x() + 1, coordinate.y()), Direction.DOWN));
            }
            continue;
          }
        }
        case '-' -> {
          if (direction == Direction.UP || direction == Direction.DOWN) {
            visited.add(new Beam(coordinate, Direction.UP));
            visited.add(new Beam(coordinate, Direction.DOWN));
            if (coordinate.y() > 0) {
              toVisit.add(
                  new Beam(new Coordinate(coordinate.x(), coordinate.y() - 1), Direction.LEFT));
            }
            if (coordinate.y() < grid[0].length - 1) {
              toVisit.add(
                  new Beam(new Coordinate(coordinate.x(), coordinate.y() + 1), Direction.RIGHT));
            }
            continue;
          }
        }
        case '\\' -> {
          if (direction == Direction.UP && coordinate.y() > 0) {
            toVisit.add(
                new Beam(new Coordinate(coordinate.x(), coordinate.y() - 1), Direction.LEFT));
          } else if (direction == Direction.DOWN && coordinate.y() < grid[0].length - 1) {
            toVisit.add(
                new Beam(new Coordinate(coordinate.x(), coordinate.y() + 1), Direction.RIGHT));
          } else if (direction == Direction.LEFT && coordinate.x() > 0) {
            toVisit.add(new Beam(new Coordinate(coordinate.x() - 1, coordinate.y()), Direction.UP));
          } else if (direction == Direction.RIGHT && coordinate.x() < grid.length - 1) {
            toVisit.add(
                new Beam(new Coordinate(coordinate.x() + 1, coordinate.y()), Direction.DOWN));
          }
          continue;
        }
        case '/' -> {
          if (direction == Direction.UP && coordinate.y() < grid[0].length - 1) {
            toVisit.add(
                new Beam(new Coordinate(coordinate.x(), coordinate.y() + 1), Direction.RIGHT));
          } else if (direction == Direction.DOWN && coordinate.y() > 0) {
            toVisit.add(
                new Beam(new Coordinate(coordinate.x(), coordinate.y() - 1), Direction.LEFT));
          } else if (direction == Direction.LEFT && coordinate.x() < grid.length - 1) {
            toVisit.add(
                new Beam(new Coordinate(coordinate.x() + 1, coordinate.y()), Direction.DOWN));
          } else if (direction == Direction.RIGHT && coordinate.x() > 0) {
            toVisit.add(new Beam(new Coordinate(coordinate.x() - 1, coordinate.y()), Direction.UP));
          }
          continue;
        }
      }
      if (direction == Direction.UP && coordinate.x() > 0) {
        toVisit.add(new Beam(new Coordinate(coordinate.x() - 1, coordinate.y()), direction));
      } else if (direction == Direction.DOWN && coordinate.x() < grid.length - 1) {
        toVisit.add(new Beam(new Coordinate(coordinate.x() + 1, coordinate.y()), direction));
      } else if (direction == Direction.LEFT && coordinate.y() > 0) {
        toVisit.add(new Beam(new Coordinate(coordinate.x(), coordinate.y() - 1), direction));
      } else if (direction == Direction.RIGHT && coordinate.y() < grid[0].length - 1) {
        toVisit.add(new Beam(new Coordinate(coordinate.x(), coordinate.y() + 1), direction));
      }
    }
    return visited.stream().map(Beam::coordinate).distinct().count();
  }

  @Override
  public Long part2() {
    List<Beam> starts = new ArrayList<>();
    for (int i = 0; i < grid.length; i++) {
      starts.add(new Beam(new Coordinate(i, 0), Direction.RIGHT));
      starts.add(new Beam(new Coordinate(i, grid[0].length - 1), Direction.LEFT));
    }
    for (int i = 0; i < grid[0].length; i++) {
      starts.add(new Beam(new Coordinate(0, i), Direction.DOWN));
      starts.add(new Beam(new Coordinate(grid.length - 1, i), Direction.UP));
    }
    return starts.stream().map(this::solve).reduce(Long::max).orElse(0L);
  }

  private record Beam(Coordinate coordinate, Direction direction) {}

  private enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT
  }
}

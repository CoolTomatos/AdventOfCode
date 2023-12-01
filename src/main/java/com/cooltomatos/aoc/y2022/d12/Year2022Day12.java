package com.cooltomatos.aoc.y2022.d12;

import static com.google.common.base.Preconditions.checkArgument;

import com.cooltomatos.aoc.AbstractDay;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Year2022Day12 extends AbstractDay {
  private final int[][] heights;
  private final int[][] steps;
  private final int width;
  private final int length;
  private Coordinate source;
  private Coordinate target;

  public Year2022Day12(String dir, String file) {
    super(2022, 12, dir, file);
    width = input.size();
    length = input.get(0).length();
    heights = new int[width][length];
    for (int i = 0; i < input.size(); i++) {
      char[] row = input.get(i).toCharArray();
      for (int j = 0; j < row.length; j++) {
        if (row[j] == 'S') {
          source = new Coordinate(i, j);
          heights[i][j] = 0;
        } else if (row[j] == 'E') {
          target = new Coordinate(i, j);
          heights[i][j] = 'z' - 'a';
        } else {
          heights[i][j] = row[j] - 'a';
        }
      }
    }
    checkArgument(source != null);
    checkArgument(target != null);
    steps = new int[width][length];
    for (int[] row : steps) {
      Arrays.fill(row, Integer.MAX_VALUE);
    }
    steps[target.x()][target.y()] = 0;
  }

  @Override
  public Integer part1() {
    Queue<Coordinate> queue = new LinkedList<>();
    queue.add(target);
    while (!queue.isEmpty()) {
      Coordinate current = queue.poll();
      if (current.x > 0) {
        Coordinate top = new Coordinate(current.x - 1, current.y);
        if (steps[top.x][top.y] > steps[current.x][current.y] + 1) {
          if (heights[top.x][top.y] + 1 >= heights[current.x][current.y]) {
            steps[top.x][top.y] = steps[current.x][current.y] + 1;
            queue.add(top);
          }
        }
      }

      if (current.x < width - 1) {
        Coordinate bot = new Coordinate(current.x + 1, current.y);
        if (steps[bot.x][bot.y] > steps[current.x][current.y] + 1) {
          if (heights[bot.x][bot.y] + 1 >= heights[current.x][current.y]) {
            steps[bot.x][bot.y] = steps[current.x][current.y] + 1;
            queue.add(bot);
          }
        }
      }

      if (current.y > 0) {
        Coordinate left = new Coordinate(current.x, current.y - 1);
        if (steps[left.x][left.y] > steps[current.x][current.y] + 1) {
          if (heights[left.x][left.y] + 1 >= heights[current.x][current.y]) {
            steps[left.x][left.y] = steps[current.x][current.y] + 1;
            queue.add(left);
          }
        }
      }

      if (current.y < length - 1) {
        Coordinate right = new Coordinate(current.x, current.y + 1);
        if (steps[right.x][right.y] > steps[current.x][current.y] + 1) {
          if (heights[right.x][right.y] + 1 >= heights[current.x][current.y]) {
            steps[right.x][right.y] = steps[current.x][current.y] + 1;
            queue.add(right);
          }
        }
      }
    }
    return steps[source.x()][source.y()];
  }

  @Override
  public Object part2() {
    Queue<Coordinate> queue = new LinkedList<>();
    queue.add(target);
    while (!queue.isEmpty()) {
      Coordinate current = queue.poll();
      if (current.x > 0) {
        Coordinate top = new Coordinate(current.x - 1, current.y);
        if (steps[top.x][top.y] > steps[current.x][current.y] + 1) {
          if (heights[top.x][top.y] + 1 >= heights[current.x][current.y]) {
            steps[top.x][top.y] = steps[current.x][current.y] + 1;
            queue.add(top);
          }
        }
      }

      if (current.x < width - 1) {
        Coordinate bot = new Coordinate(current.x + 1, current.y);
        if (steps[bot.x][bot.y] > steps[current.x][current.y] + 1) {
          if (heights[bot.x][bot.y] + 1 >= heights[current.x][current.y]) {
            steps[bot.x][bot.y] = steps[current.x][current.y] + 1;
            queue.add(bot);
          }
        }
      }

      if (current.y > 0) {
        Coordinate left = new Coordinate(current.x, current.y - 1);
        if (steps[left.x][left.y] > steps[current.x][current.y] + 1) {
          if (heights[left.x][left.y] + 1 >= heights[current.x][current.y]) {
            steps[left.x][left.y] = steps[current.x][current.y] + 1;
            queue.add(left);
          }
        }
      }

      if (current.y < length - 1) {
        Coordinate right = new Coordinate(current.x, current.y + 1);
        if (steps[right.x][right.y] > steps[current.x][current.y] + 1) {
          if (heights[right.x][right.y] + 1 >= heights[current.x][current.y]) {
            steps[right.x][right.y] = steps[current.x][current.y] + 1;
            queue.add(right);
          }
        }
      }
    }

    int shortest = Integer.MAX_VALUE;
    for (int i = 0; i < heights.length; i++) {
      for (int j = 0; j < heights[i].length; j++) {
        if (heights[i][j]==0) {
          shortest = Math.min(shortest, steps[i][j]);
        }
      }
    }
    return shortest;
  }

  record Coordinate(int x, int y) {}
}

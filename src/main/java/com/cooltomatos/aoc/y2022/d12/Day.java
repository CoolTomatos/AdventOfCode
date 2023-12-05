package com.cooltomatos.aoc.y2022.d12;

import com.cooltomatos.aoc.AbstractDay;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Day extends AbstractDay {
  private final Grid grid;
  private Cell source;
  private Cell target;

  public Day(String dir, String file) {
    super(2022, 12, dir, file);
    grid = new Grid(input.size(), input.getFirst().length());
    for (int i = 0; i < input.size(); i++) {
      char[] row = input.get(i).toCharArray();
      for (int j = 0; j < row.length; j++) {
        char current = row[j];
        char height = current == 'S' ? 'a' : current == 'E' ? 'z' : current;
        Cell cell = new Cell(grid, i, j, height);
        if (current == 'S') {
          source = cell;
        } else if (current == 'E') {
          target = cell;
          target.steps = 0;
        }
      }
    }
  }

  private void traverse() {
    Queue<Cell> queue = new LinkedList<>();
    queue.add(target);
    while (!queue.isEmpty()) {
      Cell current = queue.poll();
      check(queue, current, current.left());
      check(queue, current, current.right());
      check(queue, current, current.top());
      check(queue, current, current.bot());
    }
  }

  private static void check(Queue<Cell> queue, Cell current, Cell potential) {
    if (potential != null
        && potential.height + 1 >= current.height
        && potential.steps > current.steps + 1) {
      queue.add(potential);
      potential.steps = current.steps + 1;
    }
  }

  @Override
  public Integer part1() {
    traverse();
    return source.steps;
  }

  @Override
  public Integer part2() {
    traverse();
    return Arrays.stream(grid.cells)
        .flatMap(Arrays::stream)
        .filter(cell -> cell.height == 'a')
        .mapToInt(cell -> cell.steps)
        .min()
        .orElseThrow();
  }
}

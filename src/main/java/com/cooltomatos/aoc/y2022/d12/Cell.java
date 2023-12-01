package com.cooltomatos.aoc.y2022.d12;

import javax.annotation.Nullable;

class Cell {
  final Grid grid;
  final int x;
  final int y;
  final char height;
  int steps = Integer.MAX_VALUE;

  Cell(Grid grid, int x, int y, char height) {
    this.grid = grid;
    this.x = x;
    this.y = y;
    grid.cells[x][y] = this;
    this.height = height;
  }

  @Nullable
  Cell left() {
    return y > 0 ? grid.cells[x][y - 1] : null;
  }

  @Nullable
  Cell right() {
    return y < grid.column - 1 ? grid.cells[x][y + 1] : null;
  }

  @Nullable
  Cell top() {
    return x > 0 ? grid.cells[x - 1][y] : null;
  }

  @Nullable
  Cell bot() {
    return x < grid.row - 1 ? grid.cells[x + 1][y] : null;
  }
}

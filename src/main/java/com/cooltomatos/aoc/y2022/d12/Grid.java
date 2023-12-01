package com.cooltomatos.aoc.y2022.d12;

 class Grid {
  final int row;
  final int column;
  final Cell[][] cells;

   Grid(int row, int column) {
    this.row = row;
    this.column = column;
    cells = new Cell[row][column];
  }
}

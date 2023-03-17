package com.cooltomatos.y2022;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

public class Day08 extends Day {
  private final int[][] trees;
  private final int rowSize;
  private final int columSize;

  public Day08() throws IOException {
    super(8);
    rowSize = input.size();
    columSize = input.get(0).length();
    trees = new int[rowSize][columSize];
    for (int i = 0; i < rowSize; i++) {
      for (int j = 0; j < columSize; j++) {
        trees[i][j] = input.get(i).charAt(j) - '0';
      }
    }
  }

  @Override
  public int part1() {
    int[] lefts = new int[rowSize];
    Arrays.fill(lefts, columSize);
    int[] rights = new int[rowSize];
    Arrays.fill(rights, -1);
    int[] ups = new int[columSize];
    Arrays.fill(ups, columSize);
    int[] downs = new int[columSize];
    Arrays.fill(downs, -1);

    record Coordinate(int col, int row) {}
    var visible = new HashSet<Coordinate>();

    for (int h = 9; h >= 0; h--) {
      for (int i = 0; i < rowSize; i++) {
        for (int j = 0; j < lefts[i]; j++) {
          if (trees[i][j] == h) {
            visible.add(new Coordinate(i, j));
            lefts[i] = j;
            break;
          }
        }
        for (int j = columSize - 1; j > rights[i]; j--) {
          if (trees[i][j] == h) {
            visible.add(new Coordinate(i, j));
            rights[i] = j;
            break;
          }
        }
      }
      for (int j = 0; j < columSize; j++) {
        for (int i = 0; i < ups[j]; i++) {
          if (trees[i][j] == h) {
            visible.add(new Coordinate(i, j));
            ups[j] = i;
            break;
          }
        }
        for (int i = rowSize - 1; i > downs[j]; i--) {
          if (trees[i][j] == h) {
            visible.add(new Coordinate(i, j));
            downs[j] = i;
            break;
          }
        }
      }
    }
    return visible.size();
  }

  @Override
  public int part2() {
    throw new UnsupportedOperationException();
  }
}

package com.cooltomatos.aoc.y2024.d09;

import com.cooltomatos.aoc.AbstractDay;
import com.google.common.collect.Streams;
import java.util.ArrayList;

public class Day extends AbstractDay {
  public Day(String dir, String file) {
    super(2024, 9, dir, file);
  }

  @Override
  public Long part1() {
    var list = new ArrayList<Integer>();
    int id = 0;
    boolean space = true;
    for (var c : input.get(0).toCharArray()) {
      space = !space;
      if (!space) {
        for (var i = 0; i < c - '0'; i++) {
          list.add(id);
        }
        id++;
      } else {
        for (var i = 0; i < c - '0'; i++) {
          list.add(null);
        }
      }
    }
    for (var i = 0; i < list.size(); i++) {
      if (list.get(i) != null) {
        continue;
      }
      Integer last;
      do {
        last = list.removeLast();
      } while (last == null);
      if (i >= list.size()) {
        list.add(last);
      } else {
        list.set(i, last);
      }
    }
    return Streams.mapWithIndex(list.stream(), (i, index) -> i * index).reduce(0L, Long::sum);
  }

  @Override
  public Number part2() {
    return null;
  }
}

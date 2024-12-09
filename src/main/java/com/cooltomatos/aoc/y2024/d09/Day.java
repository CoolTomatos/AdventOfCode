package com.cooltomatos.aoc.y2024.d09;

import com.cooltomatos.aoc.AbstractDay;
import com.google.common.collect.Streams;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Stream;

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
  public Long part2() {
    var files = new HashMap<Integer, Space>();
    var gaps = new HashSet<Space>();
    int id = 0;
    boolean filled = false;
    int total = 0;
    for (var c : input.get(0).toCharArray()) {
      filled = !filled;
      var length = c - '0';
      if (filled) {
        files.put(id, new Space(id, total, length));
        id++;
      } else {
        gaps.add(new Space(null, total, length));
      }
      total += length;
    }
    for (var i = id - 1; i >= 0; i--) {
      var current = files.remove(i);
      var leftMost =
          gaps.stream()
              .filter(gap -> gap.length() >= current.length)
              .filter(gap -> gap.startIndex < current.startIndex)
              .min(Comparator.comparingInt(Space::startIndex));
      if (leftMost.isPresent()) {
        var first = leftMost.get();
        gaps.remove(first);
        files.put(i, new Space(i, first.startIndex(), current.length));
        var newLength = first.length() - current.length();
        if (newLength > 0) {
          gaps.add(new Space(null, first.startIndex + current.length, newLength));
        }
      } else {
        files.put(i, current);
      }
    }
    return files.values().stream()
        .flatMapToLong(
            file ->
                Stream.iterate(file.startIndex, i -> i + 1)
                    .limit(file.length)
                    .mapToLong(index -> (long) index * file.id))
        .sum();
  }

  private record Space(Integer id, int startIndex, int length) {}
}

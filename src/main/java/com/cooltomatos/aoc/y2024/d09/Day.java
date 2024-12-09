package com.cooltomatos.aoc.y2024.d09;

import static java.util.Map.Entry.comparingByKey;

import com.cooltomatos.aoc.AbstractDay;
import com.google.common.collect.Streams;
import java.util.ArrayList;
import java.util.HashMap;
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
    for (var c : input.getFirst().toCharArray()) {
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
    var files = new HashMap<Integer, File>();
    var gaps = new HashMap<Integer, Integer>();
    int id = 0;
    boolean filled = false;
    int total = 0;
    for (var c : input.getFirst().toCharArray()) {
      filled = !filled;
      var length = c - '0';
      if (filled) {
        files.put(id, new File(id++, total, length));
      } else {
        gaps.put(total, length);
      }
      total += length;
    }
    for (var i = id - 1; i >= 0; i--) {
      var current = files.get(i);
      gaps.entrySet().stream()
          .filter(gap -> gap.getKey() < current.startIndex && gap.getValue() >= current.length)
          .min(comparingByKey())
          .ifPresent(
              first -> {
                gaps.remove(first.getKey());
                files.put(current.id, new File(current.id, first.getKey(), current.length));
                if (first.getValue() > current.length) {
                  gaps.put(first.getKey() + current.length, first.getValue() - current.length);
                }
              });
    }
    return files.values().stream()
        .flatMapToLong(
            file ->
                Stream.iterate(file.startIndex, index -> index + 1)
                    .limit(file.length)
                    .mapToLong(index -> (long) index * file.id))
        .sum();
  }

  private record File(int id, int startIndex, int length) {}
}

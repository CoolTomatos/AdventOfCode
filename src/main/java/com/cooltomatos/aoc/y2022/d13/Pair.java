package com.cooltomatos.aoc.y2022.d13;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public record Pair(int index, List<?> left, List<?> right) {
  static List<?> parse(String input) {
    var packet = new ArrayList<>();
    parse(input.toCharArray(), 1, packet);
    return packet;
  }

  private static int parse(char[] chars, int startIndex, List<Object> outerList) {
    var i = startIndex;
    var str = new StringBuilder();
    for (; i < chars.length; i++) {
      var current = chars[i];
      if (current == '[') {
        var innerList = new ArrayList<>();
        outerList.add(innerList);
        i = parse(chars, i + 1, innerList);
      } else if (current == ']') {
        if (!str.isEmpty()) {
          outerList.add(Integer.parseInt(str.toString()));
        }
        break;
      } else if (current == ',') {
        if (!str.isEmpty()) {
          outerList.add(Integer.parseInt(str.toString()));
          str = new StringBuilder();
        }
      } else {
        str.append(current);
      }
    }
    return i;
  }

  static final Comparator<List<?>> packetComparator =
      new Comparator<>() {
        @Override
        public int compare(List<?> o1, List<?> o2) {
          for (int i = 0; i < o1.size(); i++) {
            Object left = o1.get(i);
            if (i > o2.size() - 1) {
              return 1;
            }
            Object right = o2.get(i);
            int compared;
            if (left instanceof Integer leftInt && right instanceof Integer rightInt) {
              compared = Integer.compare(leftInt, rightInt);
            } else if (left instanceof List<?> leftList && right instanceof List<?> rightList) {
              compared = compare(leftList, rightList);
            } else if (left instanceof Integer && right instanceof List<?> rightList) {
              compared = compare(List.of(left), rightList);
            } else if (left instanceof List<?> leftList && right instanceof Integer) {
              compared = compare(leftList, List.of(right));
            } else {
              throw new IllegalStateException();
            }
            if (compared != 0) {
              return compared;
            }
          }
          return o1.size() == o2.size() ? 0 : -1;
        }
      };

  boolean inOrder() {
    return packetComparator.compare(left, right) <= 0;
  }
}

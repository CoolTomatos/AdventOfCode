package com.cooltomatos.aoc;

import com.cooltomatos.aoc.y2023.d04.Day;

public class Main {
  public static void main(String[] args) {
    AbstractDay day = new Day("main", "input");
    System.out.println(day.part1());
    System.out.println(day.part2());
  }
}

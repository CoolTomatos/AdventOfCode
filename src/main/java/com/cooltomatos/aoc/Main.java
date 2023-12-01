package com.cooltomatos.aoc;

import com.cooltomatos.aoc.y2023.d01.Year2023Day01;

public class Main {
  public static void main(String[] args) {
    AbstractDay day = new Year2023Day01("main", "input");
    System.out.println(day.part1());
    System.out.println(day.part2());
  }
}

package com.cooltomatos.aoc;

import com.cooltomatos.aoc.y2022.d12.Year2022Day12;
import com.cooltomatos.aoc.y2023.d02.Year2023Day02;

public class Main {
  public static void main(String[] args) {
    AbstractDay day = new Year2023Day02("main", "input");
    System.out.println(day.part1());
    System.out.println(day.part2());
  }
}

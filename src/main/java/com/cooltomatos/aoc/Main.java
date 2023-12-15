package com.cooltomatos.aoc;

import com.cooltomatos.aoc.y2023.d15.Day;
import java.time.Clock;
import java.time.Duration;

public class Main {
  private static final Clock CLOCK = Clock.systemUTC();

  public static void main(String[] args) {
    AbstractDay day = new Day("main", "input");

    var part1Start = CLOCK.instant();
    var part1 = day.part1();
    var part1End = CLOCK.instant();
    System.out.println("Part 1:");
    System.out.println("\tTime: " + Duration.between(part1Start, part1End).toMillis() + "ms");
    System.out.println("\tResult: " + part1);

    var part2Start = CLOCK.instant();
    var part2 = day.part2();
    var part2End = CLOCK.instant();
    System.out.println("Part 2:");
    System.out.println("\tTime: " + Duration.between(part2Start, part2End).toMillis() + "ms");
    System.out.println("\tResult: " + part2);
  }
}

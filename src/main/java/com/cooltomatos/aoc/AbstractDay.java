package com.cooltomatos.aoc;

import com.cooltomatos.aoc.y2023.d19.Day;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Clock;
import java.time.Duration;
import java.util.List;

public abstract class AbstractDay {
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

  protected final List<String> input;

  protected AbstractDay(int year, int day, String dir, String file) {
    Path path =
        Path.of(
            "src", dir, "resources", String.format("%04d", year), String.format("%02d", day), file);
    try {
      input = Files.readAllLines(path);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public abstract Number part1();

  public abstract Number part2();
}

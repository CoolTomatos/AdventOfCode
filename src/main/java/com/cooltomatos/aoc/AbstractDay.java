package com.cooltomatos.aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public abstract class AbstractDay {
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

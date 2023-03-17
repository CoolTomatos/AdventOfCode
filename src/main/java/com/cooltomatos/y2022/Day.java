package com.cooltomatos.y2022;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

public abstract class Day {
  protected final List<String> input;

  public Day(int day) throws IOException {
    var inputFileName = String.format("2022/day%02d.txt", day);
    var resource = getClass().getClassLoader().getResource(inputFileName);
    var path = Paths.get(Optional.ofNullable(resource).orElseThrow().getFile());
    input = Files.readAllLines(path);
  }

  public abstract int part1();

  public abstract int part2();

  public static void main(String[] args) throws IOException {
    var day = new Day08();
    System.out.println(day.part1());
  }
}

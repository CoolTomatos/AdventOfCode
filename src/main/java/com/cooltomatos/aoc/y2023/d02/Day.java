package com.cooltomatos.aoc.y2023.d02;

import static com.google.common.collect.ImmutableList.toImmutableList;

import com.cooltomatos.aoc.AbstractDay;
import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Map;

public class Day extends AbstractDay {

  private final List<Game> games;

  public Day(String dir, String file) {
    super(2023, 2, dir, file);
    games = input.stream().map(Game::parse).collect(toImmutableList());
  }

  @Override
  public Integer part1() {
    Map<Color, Integer> cubes = ImmutableMap.of(Color.RED, 12, Color.GREEN, 13, Color.BLUE, 14);
    return games.stream().filter(game -> game.possible(cubes)).mapToInt(Game::id).sum();
  }

  @Override
  public Integer part2() {
    return games.stream().mapToInt(Game::power).sum();
  }
}

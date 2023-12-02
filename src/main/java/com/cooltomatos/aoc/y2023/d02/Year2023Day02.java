package com.cooltomatos.aoc.y2023.d02;

import static com.google.common.collect.ImmutableList.toImmutableList;
import static com.google.common.collect.Maps.toImmutableEnumMap;

import com.cooltomatos.aoc.AbstractDay;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.ToIntFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Year2023Day02 extends AbstractDay {

  private final List<Game> games;

  public Year2023Day02(String dir, String file) {
    super(2023, 2, dir, file);
    games =
        input.stream()
            .map(
                line -> {
                  Matcher gameMatcher = Pattern.compile("Game (\\d*):(.*)").matcher(line);
                  gameMatcher.matches();
                  int id = Integer.parseInt(gameMatcher.group(1));
                  ImmutableList<CubeSet> sets =
                      Arrays.stream(gameMatcher.group(2).split(";"))
                          .map(
                              set ->
                                  new CubeSet(
                                      Arrays.stream(set.split(","))
                                          .map(Pattern.compile(" (\\d+) (red|green|blue)")::matcher)
                                          .peek(Matcher::matches)
                                          .collect(
                                              toImmutableEnumMap(
                                                  matcher ->
                                                      Color.valueOf(matcher.group(2).toUpperCase()),
                                                  matcher -> Integer.parseInt(matcher.group(1))))))
                          .collect(toImmutableList());
                  return new Game(id, sets);
                })
            .collect(toImmutableList());
  }

  @Override
  public Integer part1() {
    Map<Color, Integer> max =
        ImmutableMap.of(
            Color.RED, 12,
            Color.GREEN, 13,
            Color.BLUE, 14);
    return games.stream()
        .filter(
            game ->
                game.sets.stream()
                    .map(cubes -> cubes.cubes)
                    .map(Map::entrySet)
                    .flatMap(Collection::stream)
                    .allMatch(entry -> max.get(entry.getKey()) >= entry.getValue()))
        .mapToInt(game -> game.id)
        .sum();
  }

  @Override
  public Integer part2() {
    return games.stream()
        .map(game -> game.sets)
        .mapToInt(
            new ToIntFunction<List<CubeSet>>() {
              @Override
              public int applyAsInt(List<CubeSet> value) {
                int red =
                    value.stream()
                        .mapToInt(cube -> cube.cubes.getOrDefault(Color.RED, 0))
                        .max()
                        .orElseThrow();
                int green =
                    value.stream()
                        .mapToInt(cube -> cube.cubes.getOrDefault(Color.GREEN, 0))
                        .max()
                        .orElseThrow();
                int blue =
                    value.stream()
                        .mapToInt(cube -> cube.cubes.getOrDefault(Color.BLUE, 0))
                        .max()
                        .orElseThrow();
                return red * green * blue;
              }
            })
        .sum();
  }

  // Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
  private record Game(int id, List<CubeSet> sets) {}

  // 3 blue, 4 red
  private record CubeSet(Map<Color, Integer> cubes) {}

  private enum Color {
    RED,
    GREEN,
    BLUE
  }
}

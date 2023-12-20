package com.cooltomatos.aoc.y2023.d19;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class DayTest {
  @Test
  void part1() {
    assertThat(new Day("test", "example").part1()).isEqualTo(19114);
    assertThat(new Day("main", "input").part1()).isEqualTo(492702);
  }

  @Test
  void part2() {
    assertThat(new Day("test", "example").part2()).isEqualTo(167_409_079_868_000L);
    assertThat(new Day("main", "input").part2()).isEqualTo(138_616_621_185_978L);
  }
}

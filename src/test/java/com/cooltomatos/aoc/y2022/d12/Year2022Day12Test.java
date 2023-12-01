package com.cooltomatos.aoc.y2022.d12;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class Year2022Day12Test {

  @Test
  void part1() {
    assertThat(new Year2022Day12("test", "example").part1()).isEqualTo(31);
  }

  @Test
  void part2() {
    assertThat(new Year2022Day12("test", "example").part2()).isEqualTo(29);
  }
}

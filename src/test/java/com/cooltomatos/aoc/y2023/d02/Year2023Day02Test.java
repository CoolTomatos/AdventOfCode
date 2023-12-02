package com.cooltomatos.aoc.y2023.d02;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class Year2023Day02Test {

  @Test
  void part1() {
    assertThat(new Year2023Day02("test", "example").part1()).isEqualTo(8);
  }

  @Test
  void part2() {
    assertThat(new Year2023Day02("test", "example").part2()).isEqualTo(2286);
  }
}

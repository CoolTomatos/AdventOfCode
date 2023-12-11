package com.cooltomatos.aoc.y2023.d10;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DayTest {

  @Test
  void part1example1() {
    assertThat(new Day("test", "example1").part1()).isEqualTo(4);
  }

  @Test
  void part1example2() {
    assertThat(new Day("test", "example2").part1()).isEqualTo(8);
  }

  @Test
  void part2example3() {
    assertThat(new Day("test", "example3").part2()).isEqualTo(4);
  }

  @Test
  void part2example4() {
    assertThat(new Day("test", "example4").part2()).isEqualTo(4);
  }

  @Test
  void part2example5() {
    assertThat(new Day("test", "example5").part2()).isEqualTo(8);
  }

  @Test
  void part2example6() {
    assertThat(new Day("test", "example6").part2()).isEqualTo(10);
  }
}

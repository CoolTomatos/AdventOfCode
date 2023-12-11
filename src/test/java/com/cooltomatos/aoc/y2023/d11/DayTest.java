package com.cooltomatos.aoc.y2023.d11;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DayTest {
  private Day day;

  @BeforeEach
  void setUp() {
    day = new Day("test", "example");
  }

  @Test
  void solve1() {
    assertThat(day.solve(2)).isEqualTo(374);
  }

  @Test
  void solve2() {
    assertThat(day.solve(10)).isEqualTo(1030);
  }

  @Test
  void solve3() {
    assertThat(day.solve(100)).isEqualTo(8410);
  }
}

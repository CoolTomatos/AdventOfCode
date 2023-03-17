package com.cooltomatos.y2022;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Day08Test {
  private Day day;

  @BeforeEach
  void setUp() throws IOException {
    day = new Day08();
  }

  @Test
  void part1() {
    assertThat(day.part1()).isEqualTo(21);
  }

  @Test
  void part2() {
    assertThat(day.part2()).isEqualTo(8);
  }
}

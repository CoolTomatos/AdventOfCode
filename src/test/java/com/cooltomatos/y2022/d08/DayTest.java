package com.cooltomatos.y2022.d08;

import static org.assertj.core.api.Assertions.assertThat;

import com.cooltomatos.y2022.AbstractDay;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DayTest {
  private AbstractDay day;

  @BeforeEach
  void setUp() throws IOException {
    day = new Day();
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

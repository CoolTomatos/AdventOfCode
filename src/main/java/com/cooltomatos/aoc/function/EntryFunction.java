package com.cooltomatos.aoc.function;

import java.util.Map.Entry;
import java.util.function.Function;

@FunctionalInterface
public interface EntryFunction<K, V, R> extends Function<Entry<K, V>, R> {
  R apply(K key, V value);

  @Override
  default R apply(Entry<K, V> entry) {
    return apply(entry.getKey(), entry.getValue());
  }
}

package com.cooltomatos.aoc.function;

import java.util.Map.Entry;
import java.util.function.Predicate;

@FunctionalInterface
public interface EntryPredicate<K, V> extends Predicate<Entry<K, V>> {
  boolean test(K key, V value);

  @Override
  default boolean test(Entry<K, V> entry) {
    return test(entry.getKey(), entry.getValue());
  }
}

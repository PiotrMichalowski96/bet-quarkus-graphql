package com.piter.bet.graphql.filter.common;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import lombok.Getter;

@Getter
public abstract sealed class FilterContainer<T> permits BooleanFilter, NumberFilter, TextFilter {

  private final String operator;
  private final T value;

  public FilterContainer(String operator, T value) {
    this.operator = operator;
    this.value = value;
  }

  public abstract Predicate criteria(CriteriaBuilder builder, Path<T> field);
}

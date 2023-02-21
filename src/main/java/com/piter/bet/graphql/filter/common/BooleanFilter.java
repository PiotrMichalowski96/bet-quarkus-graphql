package com.piter.bet.graphql.filter.common;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

public final class BooleanFilter extends FilterContainer<Boolean> {

  @JsonbCreator
  public BooleanFilter(@JsonbProperty("value") Boolean value) {
    super(null, value);
  }

  @Override
  public Predicate criteria(CriteriaBuilder builder, Path<Boolean> field) {
    return builder.equal(field, super.getValue());
  }
}

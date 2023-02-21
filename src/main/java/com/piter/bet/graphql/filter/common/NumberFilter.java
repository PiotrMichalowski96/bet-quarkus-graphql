package com.piter.bet.graphql.filter.common;

import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

public non-sealed class NumberFilter extends FilterContainer<Number> {

  public NumberFilter(@JsonbProperty("operator") String operator, Number value) {
    super(operator, value);
  }

  @Override
  public Predicate criteria(CriteriaBuilder builder, Path<Number> field) {
    return switch (super.getOperator()) {
        case "lt" -> builder.lt(field, super.getValue());
        case "le" -> builder.le(field, super.getValue());
        case "gt" -> builder.gt(field, super.getValue());
        case "ge" -> builder.ge(field, super.getValue());
        case "eq" -> builder.equal(field, super.getValue());
        default -> null;
      };
  }
}

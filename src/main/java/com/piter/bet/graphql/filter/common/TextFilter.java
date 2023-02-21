package com.piter.bet.graphql.filter.common;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

public final class TextFilter extends FilterContainer<String> {

  @JsonbCreator
  public TextFilter(@JsonbProperty("operator") String operator, @JsonbProperty("value") String value) {
    super(operator, value);
  }

  @Override
  public Predicate criteria(CriteriaBuilder builder, Path<String> field) {
    return switch (super.getOperator()) {
        case "endsWith" -> builder.like(field, "%" + super.getValue());
        case "startsWith" -> builder.like(field, super.getValue() + "%");
        case "contains" -> builder.like(field, "%" + super.getValue() + "%");
        case "eq" -> builder.equal(field, super.getValue());
        default -> null;
      };
  }
}

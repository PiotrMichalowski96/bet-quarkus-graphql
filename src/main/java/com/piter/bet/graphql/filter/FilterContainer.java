package com.piter.bet.graphql.filter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import lombok.Data;

@Data
public class FilterContainer {

  private String operator;
  private String value;

  public Predicate criteria(CriteriaBuilder builder, Path field) {
    try {
      int number = Integer.parseInt(value);
      return switch (operator) {
        case "lt" -> builder.lt(field, number);
        case "le" -> builder.le(field, number);
        case "gt" -> builder.gt(field, number);
        case "ge" -> builder.ge(field, number);
        case "eq" -> builder.equal(field, number);
        default -> null;
      };
    } catch (NumberFormatException e) {
      return switch (operator) {
        case "endsWith" -> builder.like(field, "%" + value);
        case "startsWith" -> builder.like(field, value + "%");
        case "contains" -> builder.like(field, "%" + value + "%");
        case "eq" -> builder.equal(field, value);
        default -> null;
      };
    }
  }
}

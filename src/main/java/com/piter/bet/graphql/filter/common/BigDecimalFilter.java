package com.piter.bet.graphql.filter.common;

import java.math.BigDecimal;
import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class BigDecimalFilter extends NumberFilter {

  @JsonbCreator
  public BigDecimalFilter(@JsonbProperty("operator") String operator, @JsonbProperty("value") BigDecimal value) {
    super(operator, value);
  }
}

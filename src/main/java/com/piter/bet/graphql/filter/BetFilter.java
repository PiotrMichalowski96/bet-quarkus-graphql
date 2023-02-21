package com.piter.bet.graphql.filter;

import com.piter.bet.graphql.filter.common.BigDecimalFilter;
import com.piter.bet.graphql.filter.common.BooleanFilter;
import lombok.Data;

@Data
public class BetFilter {

  private BooleanFilter correct;
  private BigDecimalFilter betAmount;
}

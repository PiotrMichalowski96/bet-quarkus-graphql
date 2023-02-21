package com.piter.bet.graphql.filter;

import lombok.Data;

@Data
public class BetFilter {

  private FilterContainer correct;
  private FilterContainer betAmount;
}

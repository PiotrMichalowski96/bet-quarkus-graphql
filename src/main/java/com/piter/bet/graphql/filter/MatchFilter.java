package com.piter.bet.graphql.filter;

import com.piter.bet.graphql.filter.common.TextFilter;
import lombok.Data;

@Data
public class MatchFilter {

  private TextFilter homeTeam;
  private TextFilter awayTeam;
}

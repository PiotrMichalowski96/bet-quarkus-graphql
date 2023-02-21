package com.piter.bet.graphql.filter;

import com.piter.bet.graphql.filter.common.TextFilter;

public record MatchFilter(
    TextFilter homeTeam,
    TextFilter awayTeam
) {

}

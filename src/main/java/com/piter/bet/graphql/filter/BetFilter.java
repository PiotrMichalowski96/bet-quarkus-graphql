package com.piter.bet.graphql.filter;

import com.piter.bet.graphql.filter.common.BigDecimalFilter;
import com.piter.bet.graphql.filter.common.BooleanFilter;

public record BetFilter(
    BooleanFilter correct,
    BigDecimalFilter betAmount
) {

}

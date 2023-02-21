package com.piter.bet.graphql.input;

import java.math.BigDecimal;

public record BetInput(
    int homeTeamGoalsPred,
    int awayTeamGoalsPred,
    boolean correct,
    BigDecimal amount,
    long matchId,
    long tournamentId
) {

}

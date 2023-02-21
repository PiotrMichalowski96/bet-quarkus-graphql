package com.piter.bet.graphql.input;

import java.time.LocalDateTime;

public record MatchInput(
    String homeTeam,
    String awayTeam,
    LocalDateTime startTime,
    long tournamentId
) {

}
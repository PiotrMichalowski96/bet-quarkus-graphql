package com.piter.bet.graphql.input;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BetInput {

  private int homeTeamGoalsPred;
  private int awayTeamGoalsPred;
  private boolean correct;
  private BigDecimal amount;
  private long matchId;
  private long tournamentId;
}

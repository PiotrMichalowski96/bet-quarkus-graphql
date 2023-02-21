package com.piter.bet.graphql.input;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchInput {

  private String homeTeam;
  private String awayTeam;
  private LocalDateTime startTime;
  private long tournamentId;
}

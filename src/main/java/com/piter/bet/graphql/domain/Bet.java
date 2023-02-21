package com.piter.bet.graphql.domain;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Bet {

  @Id
  @GeneratedValue
  @EqualsAndHashCode.Include
  private Long id;
  private Integer homeTeamGoalsPred;
  private Integer awayTeamGoalsPred;
  private Boolean correct;
  private BigDecimal betAmount;
  @ManyToOne(fetch = FetchType.LAZY)
  private Match match;
  @ManyToOne(fetch = FetchType.LAZY)
  private Tournament tournament;
}

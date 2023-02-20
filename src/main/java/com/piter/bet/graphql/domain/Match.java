package com.piter.bet.graphql.domain;

import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Match {

  @Id
  @GeneratedValue
  @EqualsAndHashCode.Include
  private Long id;
  private String homeTeam;
  private String awayTeam;
  private LocalDateTime startTime;
  @OneToMany
  private Set<Bet> bets;
  @ManyToOne(fetch = FetchType.LAZY)
  private Tournament tournament;
}

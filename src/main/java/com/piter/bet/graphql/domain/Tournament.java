package com.piter.bet.graphql.domain;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Tournament {

  @Id
  @GeneratedValue
  @EqualsAndHashCode.Include
  private Long id;
  private String name;
  @OneToMany
  private Set<Bet> bets;
  @OneToMany
  private Set<Match> matches;
}

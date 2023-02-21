package com.piter.bet.graphql.service;

import com.piter.bet.graphql.domain.Bet;
import com.piter.bet.graphql.domain.Match;
import com.piter.bet.graphql.domain.Tournament;
import com.piter.bet.graphql.filter.BetFilter;
import com.piter.bet.graphql.input.BetInput;
import com.piter.bet.graphql.repository.BetRepository;
import com.piter.bet.graphql.repository.MatchRepository;
import com.piter.bet.graphql.repository.TournamentRepository;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class BetService {

  private final BetRepository betRepository;
  private final MatchRepository matchRepository;
  private final TournamentRepository tournamentRepository;

  public List<Bet> findAll() {
    return betRepository.listAll();
  }

  public Bet findById(Long id) {
    return betRepository.findById(id);
  }

  public List<Bet> findByFilter(BetFilter betFilter) {
    return betRepository.findByCriteria(betFilter);
  }

  @Transactional
  public Bet saveBet(BetInput betInput) {
    Tournament tournament = tournamentRepository.findById(betInput.tournamentId());
    Match match = matchRepository.findById(betInput.matchId());
    var bet = Bet.builder()
        .homeTeamGoalsPred(betInput.homeTeamGoalsPred())
        .awayTeamGoalsPred(betInput.awayTeamGoalsPred())
        .correct(betInput.correct())
        .betAmount(betInput.amount())
        .match(match)
        .tournament(tournament)
        .build();
    betRepository.persist(bet);
    return bet;
  }
}

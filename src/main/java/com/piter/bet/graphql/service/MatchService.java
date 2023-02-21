package com.piter.bet.graphql.service;

import com.piter.bet.graphql.domain.Match;
import com.piter.bet.graphql.domain.Tournament;
import com.piter.bet.graphql.filter.MatchFilter;
import com.piter.bet.graphql.input.MatchInput;
import com.piter.bet.graphql.repository.MatchRepository;
import com.piter.bet.graphql.repository.TournamentRepository;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class MatchService {

  private final MatchRepository matchRepository;
  private final TournamentRepository tournamentRepository;

  public List<Match> findAll() {
    return matchRepository.findAllByCriteria();
  }

  public Match findById(Long id) {
    return matchRepository.findByIdAndCriteria(id);
  }

  public List<Match> findByFilter(MatchFilter matchFilter) {
    return matchRepository.findByCriteria(matchFilter);
  }

  @Transactional
  public Match saveMatch(MatchInput matchInput) {
    Tournament tournament = tournamentRepository.findById(matchInput.getTournamentId());
    var match = Match.builder()
        .homeTeam(matchInput.getHomeTeam())
        .awayTeam(matchInput.getAwayTeam())
        .startTime(matchInput.getStartTime())
        .tournament(tournament)
        .build();
    matchRepository.persist(match);
    return match;
  }
}

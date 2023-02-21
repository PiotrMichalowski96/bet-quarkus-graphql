package com.piter.bet.graphql.service;

import com.piter.bet.graphql.domain.Tournament;
import com.piter.bet.graphql.input.TournamentInput;
import com.piter.bet.graphql.repository.TournamentRepository;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class TournamentService {

  private final TournamentRepository tournamentRepository;

  public List<Tournament> findAll() {
    return tournamentRepository.listAll();
  }

  public Tournament findById(Long id) {
    return tournamentRepository.findById(id);
  }

  @Transactional
  public Tournament saveTournament(TournamentInput tournamentInput) {
    var tournament = Tournament.builder()
        .name(tournamentInput.getName())
        .build();
    tournamentRepository.persist(tournament);
    return tournament;
  }
}

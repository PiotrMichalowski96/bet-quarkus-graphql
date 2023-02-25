package com.piter.bet.graphql.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.piter.bet.graphql.domain.Tournament;
import com.piter.bet.graphql.input.TournamentInput;
import com.piter.bet.graphql.repository.TournamentRepository;
import io.quarkus.test.junit.QuarkusTest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

@QuarkusTest
@RequiredArgsConstructor
class TournamentServiceTest {

  private final TournamentRepository tournamentRepository;
  private final TournamentService tournamentService;

  @Test
  void shouldFindTournamentById() {
    //given
    var id = 1L;

    //when
    var tournament = tournamentService.findById(id);

    //then
    assertThat(tournament.getId()).isEqualTo(id);
  }

  @Test
  void shouldFindAllTournaments() {
    //given
    //when
    List<Tournament> tournaments = tournamentService.findAll();

    //then
    assertThat(tournaments).isNotEmpty();
  }

  @Test
  void shouldSaveTournament() {
    //given
    var tournamentInput = new TournamentInput("Premier League");

    //when
    Tournament tournament = tournamentService.saveTournament(tournamentInput);

    //then
    assertTournamentIsPresentInDb(tournament);
  }

  private void assertTournamentIsPresentInDb(Tournament tournament) {
    Tournament tournamentFromDb = tournamentRepository.findById(tournament.getId());
    assertThat(tournamentFromDb).isNotNull();
  }
}
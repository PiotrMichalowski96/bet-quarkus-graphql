package com.piter.bet.graphql.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.piter.bet.graphql.domain.Match;
import com.piter.bet.graphql.filter.MatchFilter;
import com.piter.bet.graphql.filter.common.TextFilter;
import com.piter.bet.graphql.input.MatchInput;
import com.piter.bet.graphql.repository.MatchRepository;
import io.quarkus.test.junit.QuarkusTest;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

@QuarkusTest
@RequiredArgsConstructor
class MatchServiceTest {

  private final MatchRepository matchRepository;
  private final MatchService matchService;

  @Test
  void shouldFindMatchesWithFilter() {
    //given
    var homeTeam = "Real Madrid";
    var awayTeamStartsWith = "Liv";
    var homeTeamFilter = new TextFilter("eq", homeTeam);
    var awayTeamFilter = new TextFilter("startsWith", awayTeamStartsWith);
    var matchFilter = new MatchFilter(homeTeamFilter, awayTeamFilter);

    //when
    List<Match> matches = matchService.findByFilter(matchFilter);

    //then
    assertThat(matches).hasSize(1);

    var match = matches.get(0);
    assertThat(match.getHomeTeam()).isEqualTo(homeTeam);
    assertThat(match.getAwayTeam()).startsWith(awayTeamStartsWith);
  }

  @Test
  void shouldSaveMatch() {
    //given
    var matchInput = new MatchInput("Arsenal", "Chelsea", LocalDateTime.now(), 1L);

    //when
    Match match = matchService.saveMatch(matchInput);

    //then
    assertMatchIsPresentInDb(match);
  }

  private void assertMatchIsPresentInDb(Match match) {
    Match matchFromDb = matchRepository.findById(match.getId());
    assertThat(matchFromDb).isNotNull();
  }
}
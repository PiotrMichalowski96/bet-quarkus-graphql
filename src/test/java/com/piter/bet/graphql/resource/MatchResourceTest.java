package com.piter.bet.graphql.resource;

import static org.assertj.core.api.Assertions.assertThat;

import com.piter.bet.graphql.domain.Match;
import com.piter.bet.graphql.domain.Tournament;
import io.quarkus.test.junit.QuarkusTest;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@QuarkusTest
class MatchResourceTest extends AbstractResourceTest {

  @Test
  void shouldFindAllMatches() {
    //given
    final var request = """
        {
          "query": "{
            matches {
              id
              homeTeam
              awayTeam
              tournament {
                name
              }
            }
          }"
        }
        """;

    //when
    final List<Match> matches = send(request)
        .jsonPath()
        .getList("data.matches", Match.class);

    //then
    assertThat(matches).hasSize(6);
  }

  @Test
  void shouldFindMatchById() {
    //given
    final var request = """
        {
          "query": "{
            match(id: 3) {
              id
              homeTeam
              awayTeam
              tournament {
                name
              }
            }
          }"
        }
        """;

    final var expectedMatch = Match.builder()
        .id(3L)
        .homeTeam("Real Madrid")
        .awayTeam("Liverpool")
        .tournament(Tournament.builder()
            .name("Champions League")
            .build())
        .build();

    //when
    final Match match = send(request)
        .jsonPath()
        .getObject("data.match", Match.class);

    //then
    assertThat(match).usingRecursiveComparison().isEqualTo(expectedMatch);
  }

  @ParameterizedTest
  @MethodSource("provideRequestAndExpectedMatches")
  void shouldFindMatchesFiltering(String request, List<Match> expectedMatches) {
    //given input

    //when
    final List<Match> matches = send(request)
        .jsonPath()
        .getList("data.matchesByFilter", Match.class);

    //then
    assertThat(matches).hasSameElementsAs(expectedMatches);
  }

  private static Stream<Arguments> provideRequestAndExpectedMatches() {
    final var requestFilterByHomeTeam = """
        {
          "query": "{
             matchesByFilter(filter: {
               homeTeam: {
                 operator: \\\"eq\\\",
                 value: \\\"Real Madrid\\\"
               }
             }) {
               id
               homeTeam
               awayTeam
             }
           }"
        }
        """;

    final var requestFilterByHomeTeamAndAwayTeam = """
        {
          "query": "{
             matchesByFilter(filter: {
               homeTeam: {
                 operator: \\\"eq\\\",
                 value: \\\"Real Madrid\\\"
               },
               awayTeam: {
                 operator: \\\"startsWith\\\",
                 value: \\\"Liv\\\"
               }
             }) {
               id
               homeTeam
               awayTeam
               tournament {
                 name
               }
             }
           }"
        }
        """;

    final List<Match> expectedMatchesFilterByHomeTeam = List.of(
      Match.builder()
          .id(3L)
          .homeTeam("Real Madrid")
          .awayTeam("Liverpool")
          .build(),
        Match.builder()
            .id(6L)
            .homeTeam("Real Madrid")
            .awayTeam("Atletico Madrid")
            .build()
    );

    final List<Match> expectedMatchesFilterByHomeTeamAndAwayTeam = List.of(
        Match.builder()
            .id(3L)
            .homeTeam("Real Madrid")
            .awayTeam("Liverpool")
            .tournament(Tournament.builder()
                .name("Champions League")
                .build())
            .build()
    );

    return Stream.of(
        Arguments.of(requestFilterByHomeTeam, expectedMatchesFilterByHomeTeam),
        Arguments.of(requestFilterByHomeTeamAndAwayTeam, expectedMatchesFilterByHomeTeamAndAwayTeam)
    );
  }
}
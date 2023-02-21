package com.piter.bet.graphql.resource;

import static org.assertj.core.api.Assertions.assertThat;

import com.piter.bet.graphql.domain.Tournament;
import io.quarkus.test.junit.QuarkusTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@QuarkusTest
class TournamentResourceTest extends AbstractResourceTest {

  @Test
  void shouldFindAllTournaments() {
    //given
    final var request = """
        {
          "query": "{
            tournaments {
              id
              name
            }
          }"
        }
        """;

    //when
    final List<Tournament> tournaments = send(request)
        .jsonPath()
        .getList("data.tournaments", Tournament.class);

    //then
    assertThat(tournaments).hasSize(2);
  }

  @Test
  void shouldFindTournamentById() {
    //given
    final var request = """
        {
          "query": "{
            tournament(id: 1) {
              id
              name
            }
          }"
        }
        """;

    final var expectedTournament = Tournament.builder()
        .id(1L)
        .name("Champions League")
        .build();

    //when
    final Tournament tournament = send(request)
        .jsonPath()
        .getObject("data.tournament", Tournament.class);

    //then
    assertThat(tournament).usingRecursiveComparison().isEqualTo(expectedTournament);
  }
}
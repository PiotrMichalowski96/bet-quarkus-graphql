package com.piter.bet.graphql.resource;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.piter.bet.graphql.domain.Bet;
import com.piter.bet.graphql.domain.Match;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.Test;

@QuarkusTest
class BetResourceTest {

  @Test
  void shouldFindAllBets() {
    //given
    final var request = """
        {
          "query": "{
            bets {
              id
              match {
                id
              }
            }
          }"
        }
        """;

    //when
    final List<Bet> bets = send(request)
        .jsonPath()
        .getList("data.bets", Bet.class);

    //then
    assertThat(bets).hasSize(10);
  }

  @Test
  void shouldFindBetById() {
    //given
    final var request = """
        {
          "query": "{
            bet(id: 9) {
              id
              homeTeamGoalsPred
              awayTeamGoalsPred
              match {
                id
                homeTeam
                awayTeam
              }
            }
          }"
        }
        """;

    final var expectedBet = Bet.builder()
        .id(9L)
        .homeTeamGoalsPred(1)
        .awayTeamGoalsPred(1)
        .match(Match.builder()
            .id(3L)
            .homeTeam("Real Madrid")
            .awayTeam("Liverpool")
            .build())
        .build();

    //when
    final Bet bet = send(request)
        .jsonPath()
        .getObject("data.bet", Bet.class);

    //then
    assertThat(bet).usingRecursiveComparison().isEqualTo(expectedBet);
  }

  private Response send(String request) {
    return given()
        .contentType(ContentType.JSON)
        .body(request)
        .when()
        .post("/graphql")
        .then()
        .assertThat()
        .statusCode(200)
        .and()
        .extract()
        .response();
  }
}
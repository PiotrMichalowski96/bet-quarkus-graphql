package com.piter.bet.graphql.resource;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.piter.bet.graphql.domain.Bet;
import com.piter.bet.graphql.domain.Match;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

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

  @ParameterizedTest
  @MethodSource("provideRequestAndExpectedBets")
  void shouldFindBetsFilteringByAmount(String request, List<Bet> expectedBets) {
    //given input

    //when
    final List<Bet> bets = send(request)
        .jsonPath()
        .getList("data.betsByFilter", Bet.class);

    //then
    assertThat(bets).hasSameElementsAs(expectedBets);
  }

  private static Stream<Arguments> provideRequestAndExpectedBets() {
    final var requestFilterByAmount = """
        {
          "query": "{
             betsByFilter(filter: {
               betAmount: {
                 operator: \\\"gt\\\",
                 value: \\\"900\\\"
               }
             }) {
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

    final var requestFilterByCorrectAndAmount = """
        {
          "query": "{
             betsByFilter(filter: {
               betAmount: {
                 operator: \\\"lt\\\",
                 value: \\\"100\\\"
               },
               correct: {
                 value: true
               }
             }) {
               id
               homeTeamGoalsPred
               awayTeamGoalsPred
               correct
               betAmount
               match {
                 id
                 homeTeam
                 awayTeam
               }
             }
           }"
        }
        """;

    final var expectedBetsFilterByAmount = List.of(
        Bet.builder()
            .id(18L)
            .homeTeamGoalsPred(0)
            .awayTeamGoalsPred(4)
            .match(Match.builder()
                .id(8L)
                .homeTeam("Almeria")
                .awayTeam("FC Barcelona")
                .build())
            .build()
    );

    final var expectedBetsFilterByCorrectAndAmount = List.of(
        Bet.builder()
            .id(17L)
            .homeTeamGoalsPred(0)
            .awayTeamGoalsPred(5)
            .correct(true)
            .betAmount(BigDecimal.valueOf(90L))
            .match(Match.builder()
                .id(8L)
                .homeTeam("Almeria")
                .awayTeam("FC Barcelona")
                .build())
            .build()
    );

    return Stream.of(
        Arguments.of(requestFilterByAmount, expectedBetsFilterByAmount),
        Arguments.of(requestFilterByCorrectAndAmount, expectedBetsFilterByCorrectAndAmount)
    );
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
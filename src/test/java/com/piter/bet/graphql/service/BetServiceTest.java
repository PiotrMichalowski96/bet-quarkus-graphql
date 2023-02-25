package com.piter.bet.graphql.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.piter.bet.graphql.domain.Bet;
import com.piter.bet.graphql.filter.BetFilter;
import com.piter.bet.graphql.filter.common.BigDecimalFilter;
import com.piter.bet.graphql.filter.common.BooleanFilter;
import com.piter.bet.graphql.input.BetInput;
import com.piter.bet.graphql.repository.BetRepository;
import io.quarkus.test.junit.QuarkusTest;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

@QuarkusTest
@RequiredArgsConstructor
class BetServiceTest {

  private final BetRepository betRepository;
  private final BetService betService;

  @Test
  void shouldFindBetById() {
    //given
    var id = 9L;

    //when
    var bet = betService.findById(id);

    //then
    assertThat(bet.getId()).isEqualTo(id);
  }

  @Test
  void shouldFindAllBets() {
    //given
    var expectedBetListSize = 10;

    //when
    List<Bet> bets = betService.findAll();

    //then
    assertThat(bets).hasSize(expectedBetListSize);
  }

  @Test
  void shouldFindBetsWithFilter() {
    //given
    var amount = BigDecimal.valueOf(900);
    var greaterThanOperator = "gt";

    var betFilter = new BetFilter(
        new BooleanFilter(true),
        new BigDecimalFilter(greaterThanOperator, amount)
    );

    //when
    List<Bet> bets = betService.findByFilter(betFilter);

    //then
    assertThat(bets).hasSize(1);

    var bet = bets.get(0);
    assertThat(bet.getCorrect()).isTrue();
    assertThat(bet.getBetAmount()).isGreaterThan(amount);
  }

  @Test
  void shouldSaveBet() {
    //given
    var betInput = new BetInput(1, 1, true, BigDecimal.valueOf(10), 8L, 2L);

    //when
    Bet bet = betService.saveBet(betInput);

    //then
    assertBetIsPresentInDb(bet);
  }

  private void assertBetIsPresentInDb(Bet bet) {
    Bet betFromDb = betRepository.findById(bet.getId());
    assertThat(betFromDb).isNotNull();
  }
}
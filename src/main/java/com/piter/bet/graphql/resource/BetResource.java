package com.piter.bet.graphql.resource;

import com.piter.bet.graphql.domain.Bet;
import com.piter.bet.graphql.filter.BetFilter;
import com.piter.bet.graphql.input.BetInput;
import com.piter.bet.graphql.service.BetService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;

@GraphQLApi
@RequiredArgsConstructor
public class BetResource {

  private final BetService betService;

  @Query("bets")
  public List<Bet> findAll() {
    return betService.findAll();
  }

  @Query("bet")
  public Bet findById(@Name("id") Long id) {
    return betService.findById(id);
  }

  @Query("betsByFilter")
  public List<Bet> findAllByFilter(@Name("filter") BetFilter betFilter) {
    return betService.findByFilter(betFilter);
  }

  @Mutation("newBet")
  public Bet newBet(@Name("input") BetInput betInput) {
    return betService.saveBet(betInput);
  }
}

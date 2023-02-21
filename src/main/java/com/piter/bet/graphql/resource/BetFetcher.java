package com.piter.bet.graphql.resource;

import com.piter.bet.graphql.domain.Bet;
import com.piter.bet.graphql.filter.BetFilter;
import com.piter.bet.graphql.repository.BetRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;

@GraphQLApi
@RequiredArgsConstructor
public class BetFetcher {

  private final BetRepository betRepository;

  @Query("bets")
  public List<Bet> findAll() {
    return betRepository.listAll();
  }

  @Query("bet")
  public Bet findById(@Name("id") Long id) {
    return betRepository.findById(id);
  }

  @Query("betsByFilter")
  public List<Bet> findAllByFilter(@Name("filter")BetFilter betFilter) {
    return betRepository.findByCriteria(betFilter);
  }
}

package com.piter.bet.graphql.resource;

import com.piter.bet.graphql.domain.Match;
import com.piter.bet.graphql.filter.MatchFilter;
import com.piter.bet.graphql.input.MatchInput;
import com.piter.bet.graphql.service.MatchService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;

@GraphQLApi
@RequiredArgsConstructor
public class MatchResource {

  private final MatchService matchService;

  @Query("matches")
  public List<Match> findAll() {
    return matchService.findAll();
  }

  @Query("match")
  public Match findById(@Name("id") Long id) {
    return matchService.findById(id);
  }

  @Query("matchesByFilter")
  public List<Match> findAllByFilter(@Name("filter") MatchFilter matchFilter) {
    return matchService.findByFilter(matchFilter);
  }

  @Mutation("newMatch")
  public Match newMatch(@Name("input")MatchInput matchInput) {
    return matchService.saveMatch(matchInput);
  }
}

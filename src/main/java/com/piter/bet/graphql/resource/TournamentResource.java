package com.piter.bet.graphql.resource;

import com.piter.bet.graphql.domain.Tournament;
import com.piter.bet.graphql.input.TournamentInput;
import com.piter.bet.graphql.service.TournamentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;

@GraphQLApi
@RequiredArgsConstructor
public class TournamentResource {

  private final TournamentService tournamentService;

  @Query("tournaments")
  public List<Tournament> findAll() {
    return tournamentService.findAll();
  }

  @Query("tournament")
  public Tournament findById(@Name("id") Long id) {
    return tournamentService.findById(id);
  }

  @Mutation("newTournament")
  public Tournament newTournament(@Name("input")TournamentInput tournamentInput) {
    return tournamentService.saveTournament(tournamentInput);
  }
}

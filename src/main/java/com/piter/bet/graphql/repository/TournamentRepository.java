package com.piter.bet.graphql.repository;

import com.piter.bet.graphql.domain.Tournament;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TournamentRepository implements PanacheRepository<Tournament> {
}

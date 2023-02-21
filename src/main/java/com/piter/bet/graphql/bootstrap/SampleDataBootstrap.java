package com.piter.bet.graphql.bootstrap;

import com.piter.bet.graphql.domain.Bet;
import com.piter.bet.graphql.domain.Match;
import com.piter.bet.graphql.domain.Tournament;
import com.piter.bet.graphql.repository.BetRepository;
import com.piter.bet.graphql.repository.MatchRepository;
import com.piter.bet.graphql.repository.TournamentRepository;
import io.quarkus.arc.profile.IfBuildProfile;
import io.quarkus.runtime.StartupEvent;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@IfBuildProfile(anyOf = {"dev", "test"})
@RequiredArgsConstructor
public class SampleDataBootstrap {

  private final BetRepository betRepository;
  private final MatchRepository matchRepository;
  private final TournamentRepository tournamentRepository;

  @Transactional
  void onStartup(@Observes StartupEvent startupEvent) {
    log.info("Populate sample data");

    Tournament t1 = new Tournament(null, "Champions League", null, null);
    Tournament t2 = new Tournament(null, "La Liga", null, null);
    tournamentRepository.persist(t1);
    tournamentRepository.persist(t2);

    Match m1 = new Match(null, "Real Madrid", "Liverpool", LocalDateTime.now(), null, t1);
    Match m2 = new Match(null, "Eintracht Frankfurt", "Napoli", LocalDateTime.now(), null, t1);
    Match m3 = new Match(null, "Inter", "FC Porto", LocalDateTime.now(), null, t1);
    Match m4 = new Match(null, "Real Madrid", "Atletico Madrid", LocalDateTime.now(), null, t2);
    Match m5 = new Match(null, "Valencia", "Real Sociedad", LocalDateTime.now(), null, t2);
    Match m6 = new Match(null, "Almeria", "FC Barcelona", LocalDateTime.now(), null, t2);
    matchRepository.persist(m1);
    matchRepository.persist(m2);
    matchRepository.persist(m3);
    matchRepository.persist(m4);
    matchRepository.persist(m5);
    matchRepository.persist(m6);

    Bet b1 = new Bet(null, 1, 1, true, BigDecimal.valueOf(100), m1, t1);
    Bet b2 = new Bet(null, 2, 1, false, BigDecimal.valueOf(123), m1, t1);
    Bet b3 = new Bet(null, 0, 3, true, BigDecimal.valueOf(321), m2, t1);
    Bet b4 = new Bet(null, 1, 2, false, BigDecimal.valueOf(444), m3, t1);
    Bet b5 = new Bet(null, 1, 1, true, BigDecimal.valueOf(567), m3, t1);
    Bet b6 = new Bet(null, 2, 1, true, BigDecimal.valueOf(101), m4, t2);
    Bet b7 = new Bet(null, 1, 2, false, BigDecimal.valueOf(201), m4, t2);
    Bet b8 = new Bet(null, 0, 2, true, BigDecimal.valueOf(222), m5, t2);
    Bet b9 = new Bet(null, 0, 5, true, BigDecimal.valueOf(90), m6, t2);
    Bet b10 = new Bet(null, 0, 4, true, BigDecimal.valueOf(1000), m6, t2);
    betRepository.persist(b1);
    betRepository.persist(b2);
    betRepository.persist(b3);
    betRepository.persist(b4);
    betRepository.persist(b5);
    betRepository.persist(b6);
    betRepository.persist(b7);
    betRepository.persist(b8);
    betRepository.persist(b9);
    betRepository.persist(b10);
  }
}

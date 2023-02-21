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
import java.util.List;
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

    List<Tournament> tournaments = createTournaments();
    tournamentRepository.persist(tournaments);

    List<Match> matches = createMatches(tournaments);
    matchRepository.persist(matches);

    List<Bet> bets = createBets(tournaments, matches);
    betRepository.persist(bets);
  }

  private List<Tournament> createTournaments() {
    return List.of(
        Tournament.builder()
            .name("Champions League")
            .build(),
        Tournament.builder()
            .name("La Liga")
            .build()
    );
  }

  private List<Match> createMatches(List<Tournament> tournaments) {
    Tournament t1 = tournaments.get(0);
    Tournament t2 = tournaments.get(1);

    return List.of(
        Match.builder()
            .homeTeam("Real Madrid")
            .awayTeam("Liverpool")
            .startTime(LocalDateTime.now())
            .tournament(t1)
            .build(),
        Match.builder()
            .homeTeam("Eintracht Frankfurt")
            .awayTeam("Napoli")
            .startTime(LocalDateTime.now())
            .tournament(t1)
            .build(),
        Match.builder()
            .homeTeam("Inter")
            .awayTeam("FC Porto")
            .startTime(LocalDateTime.now())
            .tournament(t1)
            .build(),
        Match.builder()
            .homeTeam("Real Madrid")
            .awayTeam("Atletico Madrid")
            .startTime(LocalDateTime.now())
            .tournament(t2)
            .build(),
        Match.builder()
            .homeTeam("Valencia")
            .awayTeam("Real Sociedad")
            .startTime(LocalDateTime.now())
            .tournament(t2)
            .build(),
        Match.builder()
            .homeTeam("Almeria")
            .awayTeam("FC Barcelona")
            .startTime(LocalDateTime.now())
            .tournament(t2)
            .build()
    );
  }

  private List<Bet> createBets(List<Tournament> tournaments, List<Match> matches) {
    Tournament t1 = tournaments.get(0);
    Tournament t2 = tournaments.get(1);

    Match m1 = matches.get(0);
    Match m2 = matches.get(1);
    Match m3 = matches.get(2);
    Match m4 = matches.get(3);
    Match m5 = matches.get(4);
    Match m6 = matches.get(5);

    return List.of(
        Bet.builder()
            .homeTeamGoalsPred(1)
            .awayTeamGoalsPred(1)
            .correct(true)
            .betAmount(BigDecimal.valueOf(100))
            .match(m1)
            .tournament(t1)
            .build(),
        Bet.builder()
            .homeTeamGoalsPred(2)
            .awayTeamGoalsPred(1)
            .correct(false)
            .betAmount(BigDecimal.valueOf(123))
            .match(m1)
            .tournament(t1)
            .build(),
        Bet.builder()
            .homeTeamGoalsPred(0)
            .awayTeamGoalsPred(3)
            .correct(true)
            .betAmount(BigDecimal.valueOf(321))
            .match(m2)
            .tournament(t1)
            .build(),
        Bet.builder()
            .homeTeamGoalsPred(1)
            .awayTeamGoalsPred(2)
            .correct(false)
            .betAmount(BigDecimal.valueOf(444))
            .match(m3)
            .tournament(t1)
            .build(),
        Bet.builder()
            .homeTeamGoalsPred(1)
            .awayTeamGoalsPred(1)
            .correct(true)
            .betAmount(BigDecimal.valueOf(567))
            .match(m3)
            .tournament(t1)
            .build(),
        Bet.builder()
            .homeTeamGoalsPred(2)
            .awayTeamGoalsPred(1)
            .correct(true)
            .betAmount(BigDecimal.valueOf(101))
            .match(m4)
            .tournament(t2)
            .build(),
        Bet.builder()
            .homeTeamGoalsPred(1)
            .awayTeamGoalsPred(2)
            .correct(false)
            .betAmount(BigDecimal.valueOf(201))
            .match(m4)
            .tournament(t2)
            .build(),
        Bet.builder()
            .homeTeamGoalsPred(0)
            .awayTeamGoalsPred(2)
            .correct(true)
            .betAmount(BigDecimal.valueOf(222))
            .match(m5)
            .tournament(t2)
            .build(),
        Bet.builder()
            .homeTeamGoalsPred(0)
            .awayTeamGoalsPred(5)
            .correct(true)
            .betAmount(BigDecimal.valueOf(90))
            .match(m6)
            .tournament(t2)
            .build(),
        Bet.builder()
            .homeTeamGoalsPred(0)
            .awayTeamGoalsPred(4)
            .correct(true)
            .betAmount(BigDecimal.valueOf(1000))
            .match(m6)
            .tournament(t2)
            .build()
    );
  }
}

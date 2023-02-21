package com.piter.bet.graphql.repository;

import com.piter.bet.graphql.domain.Match;
import com.piter.bet.graphql.filter.MatchFilter;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.DataFetchingFieldSelectionSet;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.smallrye.graphql.api.Context;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class MatchRepository implements PanacheRepository<Match> {

  private final EntityManager entityManager;
  private final Context context;

  public List<Match> findAllByCriteria() {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Match> criteriaQuery = builder.createQuery(Match.class);
    Root<Match> root = criteriaQuery.from(Match.class);
    DataFetchingEnvironment dataFetchingEnv = context.unwrap(DataFetchingEnvironment.class);
    DataFetchingFieldSelectionSet selectionSet = dataFetchingEnv.getSelectionSet();
    if (selectionSet.contains("bets")) {
      root.fetch("bets", JoinType.LEFT);
    }
    if (selectionSet.contains("tournaments")) {
      root.fetch("tournaments", JoinType.LEFT);
    }
    criteriaQuery.select(root).distinct(true);
    return entityManager.createQuery(criteriaQuery).getResultList();
  }

  public Match findByIdAndCriteria(Long id) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Match> criteriaQuery = builder.createQuery(Match.class);
    Root<Match> root = criteriaQuery.from(Match.class);
    DataFetchingEnvironment dataFetchingEnv = context.unwrap(DataFetchingEnvironment.class);
    DataFetchingFieldSelectionSet selectionSet = dataFetchingEnv.getSelectionSet();
    if (selectionSet.contains("bets")) {
      root.fetch("bets", JoinType.LEFT);
    }
    if (selectionSet.contains("tournaments")) {
      root.fetch("tournaments", JoinType.LEFT);
    }
    criteriaQuery.where(builder.equal(root.get("id"), id));
    return entityManager.createQuery(criteriaQuery).getSingleResult();
  }

  public List<Match> findByCriteria(MatchFilter matchFilter) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Match> criteriaQuery = builder.createQuery(Match.class);
    Root<Match> root = criteriaQuery.from(Match.class);
    Predicate predicate = null;
    if (matchFilter.getHomeTeam() != null) {
      predicate = matchFilter.getHomeTeam().criteria(builder, root.get("homeTeam"));
    }
    if (matchFilter.getAwayTeam() != null) {
      predicate = (predicate == null ?
          matchFilter.getAwayTeam().criteria(builder, root.get("awayTeam")) :
          builder.and(predicate, matchFilter.getAwayTeam().criteria(builder, root.get("awayTeam"))));
    }
    if (predicate != null) {
      criteriaQuery.where(predicate);
    }
    return entityManager.createQuery(criteriaQuery).getResultList();
  }
}

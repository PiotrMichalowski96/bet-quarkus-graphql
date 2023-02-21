package com.piter.bet.graphql.repository;

import com.piter.bet.graphql.domain.Match;
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
}

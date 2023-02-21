package com.piter.bet.graphql.repository;

import com.piter.bet.graphql.domain.Bet;
import com.piter.bet.graphql.filter.BetFilter;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class BetRepository implements PanacheRepository<Bet> {

  private final EntityManager entityManager;

  public List<Bet> findByCriteria(BetFilter betFilter) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Bet> criteriaQuery = builder.createQuery(Bet.class);
    Root<Bet> root = criteriaQuery.from(Bet.class);
    Predicate predicate = null;
    if (betFilter.betAmount() != null) {
      predicate = betFilter.betAmount().criteria(builder, root.get("betAmount"));
    }
    if (betFilter.correct() != null) {
      predicate = (predicate == null ?
          betFilter.correct().criteria(builder, root.get("correct")) :
          builder.and(predicate, betFilter.correct().criteria(builder, root.get("correct"))));
    }
    if (predicate != null) {
      criteriaQuery.where(predicate);
    }
    return entityManager.createQuery(criteriaQuery).getResultList();
  }

}

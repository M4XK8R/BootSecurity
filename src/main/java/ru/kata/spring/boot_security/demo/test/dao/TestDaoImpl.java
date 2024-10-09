package ru.kata.spring.boot_security.demo.test.dao;

import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TestDaoImpl implements TestDao {

  @PersistenceContext
  private final EntityManager entityManager;

  @Override
  public Optional<Long> getLastUserId() {
    return Optional.ofNullable(
        (Long) entityManager.createQuery(
            "SELECT MAX(user.id) FROM User user"
        ).getSingleResult()
    );
  }
}

package ru.kata.spring.boot_security.demo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

@Repository
@RequiredArgsConstructor
public class UserDaoHibernateJpaImpl implements UserDao {

  @PersistenceContext
  private final EntityManager entityManager;

  @Override
  public void createTable() {
    entityManager.createNativeQuery(
        "CREATE TABLE IF NOT EXISTS users (" +
            "id BIGINT NOT NULL AUTO_INCREMENT, " +
            "name VARCHAR(255), " +
            "second_name VARCHAR(255), " +
            "PRIMARY KEY (id)" +
            ")"
    ).executeUpdate();
  }

  @Override
  public void dropTable() {
    entityManager.createNativeQuery(
        "DROP TABLE IF EXISTS users"
    ).executeUpdate();
  }

  @Override
  public void save(User user) {
    entityManager.persist(user);
  }

  @Override
  public void update(User user) {
    boolean isUserInDatabase = entityManager.find(
        User.class,
        user.getId()
    ) != null;
    if (isUserInDatabase) {
      entityManager.merge(user);
    }
  }

  @Override
  public void removeById(long id) {
    User user = entityManager.find(User.class, id);
    if (user != null) {
      entityManager.remove(user);
    }
  }

  @Override
  public Optional<User> getById(long id) {
    return Optional.ofNullable(
        entityManager.find(User.class, id)
    );
  }

  @Override
  public Optional<User> getByEmail(String email) {
    return Optional.ofNullable(
        (User) entityManager.createQuery(
            "FROM User WHERE email = :email"
        ).setParameter(
            "email",
            email
        ).getSingleResult()
    );
  }

  @Override
  public List<User> getAll() {
    return new ArrayList<>(
        entityManager.createQuery(
            "from User",
            User.class
        ).getResultList()
    );
  }

  @Override
  public void cleanTable() {
    entityManager.createNativeQuery(
        "DELETE FROM " + User.SUB_TABLE_NAME
    ).executeUpdate();

    entityManager.createQuery(
        "DELETE FROM User"
    ).executeUpdate();
  }
}

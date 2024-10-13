package ru.kata.spring.boot_security.demo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.util.DevelopHelper;

//@Repository
//@RequiredArgsConstructor
public class UserDaoHibernateJpaImplOld implements UserDao {

//  private final EntityManagerFactory entityManagerFactory;

  @Override
  public void createTable() {
    run(entityManager ->
        entityManager.createNativeQuery(
            "CREATE TABLE IF NOT EXISTS users (" +
                "id BIGINT NOT NULL AUTO_INCREMENT, " +
                "name VARCHAR(255), " +
                "second_name VARCHAR(255), " +
                "PRIMARY KEY (id)" +
                ")"
        ).executeUpdate()
    );
  }

  @Override
  public void dropTable() {
    run(entityManager ->
        entityManager.createNativeQuery(
            "DROP TABLE IF EXISTS users"
        ).executeUpdate()
    );
  }

  @Override
  public void save(User user) {
    run(entityManager ->
        entityManager.persist(user));
  }

  @Override
  public void update(User user) {
    run(entityManager -> {
      boolean isUserInDatabase = entityManager.find(
          User.class,
          user.getId()
      ) != null;
      if (isUserInDatabase) {
        entityManager.merge(user);
      }
    });
  }

  @Override
  public void removeById(long id) {
    run(entityManager -> {
      User user = entityManager.find(User.class, id);
      if (user != null) {
        entityManager.remove(user);
      }
    });
  }

  @Override
  public Optional<User> getById(long id) {
    return Optional.empty();
  }

  @Override
  public Optional<User> getByEmail(String email) {
    return Optional.empty();
  }

  @Override
  public List<User> getAll() {
    List<User> users = new ArrayList<>();

    run(entityManager ->
        users.addAll(
            entityManager.createQuery(
                "from User",
                User.class
            ).getResultList()
        )
    );

    return users;
  }

  @Override
  public void cleanTable() {
    run(entityManager ->
        entityManager.createQuery(
            "DELETE FROM User"
        ).executeUpdate()
    );
  }

  /*
Private sector
 */
  private void run(Consumer<EntityManager> entityManagerConsumer) {
    if (entityManagerConsumer == null) {
      return;
    }
    EntityManager entityManager = null;
    EntityTransaction transaction = null;

    try {
//      entityManager = entityManagerFactory.createEntityManager();
      transaction = entityManager.getTransaction();
      entityManagerConsumer.accept(entityManager);
      transaction.commit();
    } catch (Exception e) {
      DevelopHelper.printExceptionInfo(
          "UserDaoHibernateJpaImpl",
          "run",
          e
      );
      if (transaction != null && transaction.isActive()) {
        transaction.rollback();
      }
      throw (e);
    } finally {
      if (entityManager != null && entityManager.isOpen()) {
        try {
          entityManager.close();
        } catch (Exception e) {
          DevelopHelper.printExceptionInfo(
              "UserDaoHibernateJpaImpl",
              "run",
              e
          );
          throw (e);
        }
      }
    }
  }
}
